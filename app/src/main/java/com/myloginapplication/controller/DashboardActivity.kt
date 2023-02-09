package com.myloginapplication.controller

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myloginapplication.R
import com.myloginapplication.databinding.ActivityDashboardBinding
import com.myloginapplication.classes.BaseActivity
import com.myloginapplication.classes.MyApp
import com.myloginapplication.util.ProgressBarUtil
import com.myloginapplication.util.UserUtil

class DashboardActivity : BaseActivity() {

    private lateinit var binding : ActivityDashboardBinding
    private lateinit var countDownTimer : CountDownTimer
    private val timeLeftTextBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lblUserFullName.text = UserUtil.CURRENT_USER._fullName

        binding.btnLogout.setOnClickListener { view ->
            showConfirmationDialog()
        }

        startCountDown()
    }

    override fun onBackPressed() {
        showConfirmationDialog()
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(applicationContext.getString(R.string.logoutDialogTitle))
            .setMessage(applicationContext.getString(R.string.logoutDialogMessage))
            .setNegativeButton(applicationContext.getString(R.string.btnNo)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(applicationContext.getString(R.string.btnYes)) { dialog, which ->
                ProgressBarUtil.getInstance().runWithProgressBar(binding.progressBarLayout) {
                    startMyActivity(LoginActivity::class.java)
                    UserUtil.CURRENT_USER = null;
                }
                dialog.dismiss()
            }
            .setOnCancelListener { dialog ->
                dialog.dismiss()
            }

            .show()
    }

    override fun onStart() {
        super.onStart()
        binding.progressBarLayout.visibility = View.INVISIBLE
    }

    private fun startCountDown() {
        stopCountDown();
        countDownTimer = object:CountDownTimer(MyApp.DELAY, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var milliSeconds = millisUntilFinished
                if (milliSeconds % 2L == 0L) {
                    milliSeconds--
                }
                val minutes = (milliSeconds / 60000)
                val seconds = milliSeconds % 60000 / 1000 + 1
                binding.countDownText.text = getFormattedTimeLeft(minutes, seconds)
            }

            override fun onFinish() {
            }
        }.start()
    }

    private fun getFormattedTimeLeft(minutes : Long, seconds : Long) : String {
        var m = minutes
        var s = seconds
        timeLeftTextBuilder.clear()
        if (s == 60L) {
            m++
            s = 0
        }
        if (m < 10) {
            timeLeftTextBuilder.append("0")
        }
        timeLeftTextBuilder.append(m.toString())
        timeLeftTextBuilder.append(":")
        if (s < 10) {
            timeLeftTextBuilder.append("0")
        }
        timeLeftTextBuilder.append(s)
        return timeLeftTextBuilder.toString()
    }

    private fun stopCountDown() {
        if (this::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        startCountDown()
    }


}