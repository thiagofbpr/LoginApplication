package com.myloginapplication.controller

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.myloginapplication.R
import com.myloginapplication.dao.UserDAO
import com.myloginapplication.databinding.ActivityLoginBinding
import com.myloginapplication.util.AlertDialogUtil
import com.myloginapplication.classes.BaseActivity
import com.myloginapplication.util.ProgressBarUtil
import com.myloginapplication.util.UserUtil

class LoginActivity : BaseActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var fadeIn : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fadeIn = AnimationUtils.loadAnimation(binding.root.context, R.anim.fade_in)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root.rootView) { v, insets ->
            val isKeyboardVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            changeLogoDimension(isKeyboardVisible)
            // Return the insets to keep going down this event to the view hierarchy
            insets
        }

        binding.txtPassword.doOnTextChanged { text, start, before, count ->
            clearPasswordError()
        }

        binding.txtUserName.doOnTextChanged { text, start, before, count ->
            clearUsernameError()
        }

        binding.lblSignUp.setOnClickListener {
            ProgressBarUtil.getInstance().runWithProgressBar(binding.progressBarLayout) {
                startMyActivity(NewUserAccountActivity::class.java)
            }
        }

        binding.btnLogin.setOnClickListener {view ->
            if (binding.txtUserName.hasValidData() && binding.txtPassword.hasValidData()) {
                val user = UserDAO(applicationContext).selectUser(binding.txtUserName.text.toString().trim())
                if (user != null) {
                    if (!user._password.equals(binding.txtPassword.text.toString())) {
                        binding.txtInputLayoutPassword.error = applicationContext.getString(R.string.errorInvalidPassword)
                    } else {
                        UserUtil.CURRENT_USER = user;
                        ProgressBarUtil.getInstance().runWithProgressBar(binding.progressBarLayout) {
                            startMyActivity(DashboardActivity::class.java)
                        }
                    }
                } else {
                    binding.txtInputLayoutUserName.error = applicationContext.getString(R.string.errorNonExistentUser)
                }
            }
        }
    }

    private fun clearUsernameError() {
        binding.txtInputLayoutUserName.error = null
    }

    private fun clearPasswordError() {
        binding.txtInputLayoutPassword.error = null
    }

    private fun changeLogoDimension(isKeyboardVisible: Boolean) {
        if (isKeyboardVisible) {
            binding.logo.startAnimation(fadeIn)
            binding.logo.layoutParams.height = getScreenWidthPercent(40);
            binding.logo.requestLayout();
        } else {
            binding.logo.startAnimation(fadeIn)
            binding.logo.layoutParams.height = getScreenWidthPercent(50);
            binding.logo.requestLayout();
        }
    }

    private fun getScreenWidthPercent(pct : Int): Int {
        return (getScreenWidth() * pct) / 100
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    override fun onStart() {
        super.onStart()
        clearUsernameError()
        clearPasswordError()
        binding.progressBarLayout.visibility = View.INVISIBLE
        if (myApp.isSessionExpired) {
            AlertDialogUtil.showAlertDialog(this, applicationContext.getString(R.string.logoutDialogTitle), applicationContext.getString(R.string.autoLogoutDialogMessage)) {
                myApp.isSessionExpired = false;
            };
        }
    }


}