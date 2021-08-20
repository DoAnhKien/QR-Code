package com.example.qr_code

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

class BiometricAuthenticationActivity : AppCompatActivity() {

    private lateinit var buttonShowAuthenticator: Button
    private lateinit var biometricPrompt: BiometricPrompt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric_authentication)

        buttonShowAuthenticator = findViewById(R.id.buttonShowAuthenticator)
        biometricPrompt = createBiometricPrompt()
        BiometricManager.from(this)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)

        buttonShowAuthenticator.setOnClickListener {
            val promptInfo = createPromptInfo()
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Title")
            .setDescription("Description")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .setConfirmationRequired(true)
            .build()
    }

    private fun createBiometricPrompt(): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(this)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                Toast.makeText(
                    this@BiometricAuthenticationActivity,
                    errString.toString(),
                    Toast.LENGTH_SHORT
                ).show();

                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                Toast.makeText(
                    this@BiometricAuthenticationActivity,
                    "Authentication Success",
                    Toast.LENGTH_SHORT
                )
                    .show();

                super.onAuthenticationSucceeded(result)
            }

            override fun onAuthenticationFailed() {
                Toast.makeText(
                    this@BiometricAuthenticationActivity,
                    "Authentication Failed",
                    Toast.LENGTH_SHORT
                )
                    .show();

                super.onAuthenticationFailed()
            }
        }

        return BiometricPrompt(this, executor, callback)
    }
}