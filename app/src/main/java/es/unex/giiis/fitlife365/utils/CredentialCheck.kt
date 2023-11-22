package es.unex.giiis.fitlife365.utils

class CredentialCheck private constructor() {

    var fail: Boolean = false
    var msg: String = ""
    var error: CredentialError = CredentialError.PasswordError

    companion object{

        private val TAG = CredentialCheck::class.java.canonicalName
        private val MINCHARS = 4

        private val checks = arrayOf(
            CredentialCheck().apply {
                fail = false
                msg = "Your credentials are OK"
                error = CredentialError.Success
            },
            CredentialCheck().apply {
                fail = true
                msg = "Invalid username"
                error = CredentialError.UsernameError
            },
            CredentialCheck().apply {
                fail = true
                msg = "Invalid password"
                error = CredentialError.PasswordError
            },
            CredentialCheck().apply {
                fail = true
                msg = "Passwords do not match"
                error = CredentialError.PasswordError
            }

        )

        fun login(username: String, password: String): CredentialCheck {
            //mock
            return checks[0]
//            return if (username.isBlank() || username.length < MINCHARS) checks[1]
//            else if (password.isBlank() || password.length < MINCHARS) checks[2]
//            else checks[0]
        }

        fun join(username: String, password: String, repassword: String): CredentialCheck {
            return if (username.isBlank() || username.length < MINCHARS) checks[1]
            else if (password.isBlank() || password.length < MINCHARS) checks[2]
            else if (password!=repassword) checks[3]
            else checks[0]
        }

        fun passwordOk(passwoord1: String, password2: String): CredentialCheck {
            return if (passwoord1!=password2) checks[3]
            else checks[0]
        }
    }

    enum class CredentialError {
        PasswordError, UsernameError, Success
    }
}