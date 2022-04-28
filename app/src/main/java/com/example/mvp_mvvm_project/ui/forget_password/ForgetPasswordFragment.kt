package com.example.mvp_mvvm_project.ui.forget_password

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.mvp_mvvm_project.R
import com.example.mvp_mvvm_project.app
import com.example.mvp_mvvm_project.domain.entities.UserProfile
import com.example.mvp_mvvm_project.data.database.EmailException
import com.example.mvp_mvvm_project.data.database.ForgetPasswordException
import com.example.mvp_mvvm_project.ui.BaseFragment
import com.example.mvp_mvvm_project.databinding.FragmentForgetPasswordBinding
import com.example.mvp_mvvm_project.utils.AppState
import com.example.mvp_mvvm_project.utils.ViewState

class ForgetPasswordFragment :
    BaseFragment<FragmentForgetPasswordBinding>(FragmentForgetPasswordBinding::inflate){
    private val VIEW_STATE_KEY = "VIEW_STATE_KEY"
    private var viewState: ViewState = ViewState.INIT
    private var viewModel: ForgetPasswordContract.ViewModel? = null


    companion object {
        fun newInstance() = ForgetPasswordFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.app?.let { ForgetPasswordViewModel(it.forgetPasswordUseCase) }
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


    private fun showError(error: Exception) {
        val text = when (error) {
            is ForgetPasswordException -> {
                getString(R.string.error_forget_password)
            }
            is EmailException -> {
                getString(R.string.error_email_empty)
            }
            else -> {
                getString(R.string.unexpected_error_occurred) + error.toString()
            }
        }
        viewState = ViewState.ERROR
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        binding.root.setBackgroundColor(Color.RED)
    }

    private fun forgetPasswordData(account: UserProfile) {
        viewState = ViewState.IS_SUCCESS
        binding.root.setBackgroundColor(Color.GREEN)
        Toast.makeText(context, getString(R.string.success_forget_password), Toast.LENGTH_SHORT)
            .show()
    }

    private fun restoreStateUi() {
        when (viewState) {
            ViewState.INIT -> {
            }
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

    private fun connectSignals() {
        viewModel?.getLiveData()?.subscribe(Handler(Looper.getMainLooper())) { state ->
            renderData(state)
        }
        binding.restoreButton.setOnClickListener {
            viewModel?.findAccount(
                binding.emailTextView.text.toString()
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
                forgetPasswordData(result.account)
            }
            is AppState.Error -> {
                showError(result.error)
            }
        }
    }
}