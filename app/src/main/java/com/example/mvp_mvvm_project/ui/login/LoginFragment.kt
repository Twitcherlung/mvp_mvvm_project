package com.example.mvp_mvvm_project.ui.login

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.mvp_mvvm_project.R
import com.example.mvp_mvvm_project.app
import com.example.mvp_mvvm_project.ui.BaseFragment
import com.example.mvp_mvvm_project.ui.NavigationActivity
import com.example.mvp_mvvm_project.ui.forget_password.ForgetPasswordFragment
import com.example.mvp_mvvm_project.ui.registration.RegistrationFragment
import com.example.mvp_mvvm_project.data.database.LoginException
import com.example.mvp_mvvm_project.data.database.PasswordException
import com.example.mvp_mvvm_project.data.database.SignInException
import com.example.mvp_mvvm_project.databinding.FragmentLoginBinding
import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.utils.AppState
import com.example.mvp_mvvm_project.utils.ViewState

class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val VIEW_STATE_KEY = "VIEW_STATE_KEY"
    private var viewState: ViewState = ViewState.INIT
    private var viewModel: LoginContract.ViewModel? = null

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.app?.let { LoginViewModel(it.loginUseCase) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectSignals()

        savedInstanceState?.let {
            viewState = ViewState.fromInt(
                it.getInt(VIEW_STATE_KEY, 0)
            )
        }
        restoreStateUi()
    }

    private fun connectSignals() {
        viewModel?.getLiveData()?.subscribe(Handler(Looper.getMainLooper())) { state ->
            renderData(state)
        }
        binding.forgetPasswordButton.setOnClickListener {
            activity?.let {
                if (it is NavigationActivity) {
                    it.navigationTo(ForgetPasswordFragment.newInstance(), true)
                }
            }
        }

        binding.registrationButton.setOnClickListener {
            activity?.let {
                if (it is NavigationActivity) {
                    it.navigationTo(RegistrationFragment.newInstance(), true)
                }
            }
        }

        binding.signInButton.setOnClickListener {
            viewModel?.onLogin(
                binding.loginTextView.text.toString(),
                binding.passwordTextView.text.toString()
            )
        }
    }

    private fun renderData(result: AppState) {
        binding.progressBar.isVisible = false
        when (result) {
            is AppState.Loading -> {
                binding.progressBar.isVisible = true
            }
            is AppState.Success -> {
                loadAccountData(result.account)
            }
            is AppState.Error -> {
                showError(result.error)
            }
        }
    }

    private fun showError(error: Exception) {
        val text = when (error) {
            is SignInException -> {
                getString(R.string.error_sign_in)
            }
            is PasswordException -> {
                getString(R.string.error_password_empty)
            }
            is LoginException -> {
                getString(R.string.error_login_empty)
            }
            else -> {
                getString(R.string.unexpected_error_occurred) + error.toString()
            }
        }
        viewState = ViewState.ERROR
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        binding.root.setBackgroundColor(Color.RED)
    }

    private fun loadAccountData(account: UserProfile) {
        viewState = ViewState.IS_SUCCESS
        binding.root.setBackgroundColor(Color.GREEN)
        Toast.makeText(context, getString(R.string.success_sign_in), Toast.LENGTH_SHORT).show()
    }

    private fun restoreStateUi() {
        when (viewState) {
            ViewState.INIT -> {}
            ViewState.ERROR -> {
                binding.root.setBackgroundColor(Color.RED)
            }
            ViewState.IS_SUCCESS -> {
                binding.root.setBackgroundColor(Color.GREEN)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(VIEW_STATE_KEY, viewState.value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel?.getLiveData()?.unsubscribeAll()
    }
}