package com.myloginapplication.controller

import android.database.sqlite.SQLiteConstraintException
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.myloginapplication.R
import com.myloginapplication.dao.UserDAO
import com.myloginapplication.databinding.ActivityNewUserAccountBinding
import com.myloginapplication.model.User
import com.myloginapplication.util.AlertDialogUtil
import com.myloginapplication.classes.BaseActivity
import com.myloginapplication.util.ProgressBarUtil

class NewUserAccountActivity : BaseActivity() {

    private lateinit var binding : ActivityNewUserAccountBinding
    private var avd : AnimatedVectorDrawable? = null
    private var avdCompat : AnimatedVectorDrawableCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtFullName.doOnTextChanged { text, start, before, count ->
            binding.txtInputLayoutFullName.error = null
        }

        binding.txtUserName.doOnTextChanged { text, start, before, count ->
            binding.txtInputLayoutUserName.error = null
        }

        binding.txtPassword.doOnTextChanged { text, start, before, count ->
            binding.txtInputLayoutPassword.error = null
        }

        binding.btnBack.setOnClickListener { view ->
            goBackWithProgressBar()
        }

        binding.btnSignUp.setOnClickListener {
            if (binding.txtFullName.hasValidData() && binding.txtUserName.hasValidData() && binding.txtPassword.hasValidData()) {

                val fullName = binding.txtFullName.text.toString().trim()
                val username = binding.txtUserName.text.toString().trim()
                val password = binding.txtPassword.text.toString().trim()
                val user = User(fullName, username, password)

                try {
                    UserDAO(applicationContext).addUser(user)
                } catch (e : SQLiteConstraintException) {
                    binding.txtInputLayoutUserName.error = applicationContext.getString(R.string.errorExistingUsername)
                    return@setOnClickListener
                } catch (e : Exception) {
                    AlertDialogUtil.showAlertDialog(this, applicationContext.getString(R.string.errorDialogTitle), applicationContext.getString(R.string.errorSavingNewUser) + "\n\n" + e.message, null)
                    return@setOnClickListener
                }

                showDoneAnimation(true)

                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    showDoneAnimation(false)
                    ProgressBarUtil.getInstance().runWithProgressBar(binding.progressBarLayout) {
                        startMyActivity(LoginActivity::class.java)
                    }
                }, 1500)
            }
        }
    }

    private fun showDoneAnimation(show : Boolean) {
        if (show) {
            binding.doneLayout.visibility = View.VISIBLE
            val drawable = binding.done.drawable;
            if (drawable is AnimatedVectorDrawable) {
                avd = drawable;
                avd!!.start();
            } else if (drawable is AnimatedVectorDrawableCompat) {
                avdCompat = drawable;
                avdCompat!!.start();
            }
        } else {
            binding.doneLayout.visibility = View.INVISIBLE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goBackWithProgressBar()
    }

    private fun goBackWithProgressBar() {
        ProgressBarUtil.getInstance().runWithProgressBar(binding.progressBarLayout) {
            finish()
        }
    }


}