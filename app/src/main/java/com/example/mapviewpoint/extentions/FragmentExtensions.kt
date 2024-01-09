package com.example.mapviewpoint.extentions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun Fragment.hideKeyboard() {
    val activity = requireActivity()
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = activity.currentFocus
    if (view != null) {
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun <T : ViewBinding> Fragment.viewBinding(bind: (View) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T> {
        private var binding: T? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            binding?.let { return it }

            val view = thisRef.requireView()
            return bind(view).also {
                thisRef.viewLifecycleOwnerLiveData.observe(thisRef) { lifecycleOwner ->
                    lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
                        override fun onStateChanged(
                            source: LifecycleOwner,
                            event: Lifecycle.Event
                        ) {
                            if (event == Lifecycle.Event.ON_DESTROY) {
                                binding = null
                            }
                        }
                    })
                }
                binding = it
            }
        }
    }

fun EditText.onTextChanged(listener: (String) -> Unit) {
    this.addTextChangedListener {
        val text = it?.toString()?.trim() ?: ""
        listener.invoke(text)
    }
}

fun Fragment.isValidPassword(password: String): Boolean {
    if (password.length < 8) {
        return false
    }
    // Check for at least one uppercase letter
    val uppercasePattern = "[A-Z]".toRegex()
    if (!uppercasePattern.containsMatchIn(password)) {
        return false
    }

    // Check for at least one digit
    val digitPattern = "\\d".toRegex()
    if (!digitPattern.containsMatchIn(password)) {
        return false
    }

    // Check for at least one special character
    val specialCharacterPattern = "[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]".toRegex()
    if (!specialCharacterPattern.containsMatchIn(password)) {
        return false
    }
    return true
}

fun  Fragment.isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun Fragment.isEmailAndPasswordValid(
    emailEditText: String,
    passwordEditText: String
) : Boolean{
    val isEmailValid =
        android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText).matches() && emailEditText.endsWith(".com")
    val isPasswordValid = passwordEditText.isNotEmpty()

    return isEmailValid && isPasswordValid
}

fun Fragment.isEmailValid(
    emailEditText: String
) : Boolean{
    val isEmailValid =
        android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText).matches() && emailEditText.endsWith(".com")

    return isEmailValid
}