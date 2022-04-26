package com.example.mvp_mvvm_project.ui.registration

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.mvp_mvvm_project.R
import com.example.mvp_mvvm_project.app
import com.example.mvp_mvvm_project.ui.BaseFragment
import com.example.mvp_mvvm_project.data.database.EmailException
import com.example.mvp_mvvm_project.data.database.LoginException
import com.example.mvp_mvvm_project.data.database.PasswordException
import com.example.mvp_mvvm_project.data.database.RegistrationException
import com.example.mvp_mvvm_project.databinding.FragmentRegistrationBinding
import com.example.mvp_mvvm_project.domain.entities.UserProfile


class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate),
    RegistrationContract.RegistrationViewInterface {

    private var presenter: RegistrationContract.RegistrationPresenterInterface? = null

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        presenter = activity?.app?.let { RegistrationPresenter(it.registrationUseCase) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter?.onAttachView(this)

        binding.buttonCreate.setOnClickListener {
            presenter?.onRegistration(
                binding.loginTextView.text.toString(),
                binding.passwordTextView.text.toString(),
                binding.emailTextView.text.toString(),
            )
        }
    }

    override fun showProgress() {
        binding.progressBar.isVisible = true
    }

    override fun hideProgress() {
        binding.progressBar.isVisible = false
    }

    override fun setSuccess() {
        binding.root.setBackgroundColor(Color.GREEN)
    }

    override fun showError(error: Exception) {
        val text = when (error) {
            is RegistrationException -> {
                getString(R.string.error_registration)
            }
            is PasswordException -> {
                getString(R.string.error_password_empty)
            }
            is LoginException -> {
                getString(R.string.error_login_empty)
            }
            is EmailException -> {
                getString(R.string.error_email_empty)
            }
            else -> {
                getString(R.string.unexpected_error_occurred) + error.toString()
            }
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        binding.root.setBackgroundColor(Color.RED)
    }

    override fun loadAccountData(account: UserProfile) {
        Toast.makeText(context, getString(R.string.success_registration), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDetach()
    }
}