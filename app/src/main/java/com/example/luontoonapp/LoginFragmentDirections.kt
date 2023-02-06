package com.example.luontoonapp
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

class LoginFragmentDirections private constructor() {
    companion object {
        fun actionGlobalLoginFragment(): NavDirections =
            ActionOnlyNavDirections(R.id.action_global_loginFragment)
    }
}