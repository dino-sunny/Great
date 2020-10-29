package com.dino.great.utilities

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.dino.great.R

object AlertHandler {
    fun showAlertWithRetry(context: Context, listener: RetryResponseListener?) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(context.getString(R.string.timeout_message))
            .setPositiveButton(context.getString(R.string.retry)) { dialog, _ ->
                dialog.dismiss()
                listener?.onRetry() }

        val alert = builder.create()
        if (!alert.isShowing) try {
            alert.setTitle(context.getString(R.string.timeout))
            alert.show()
        } catch (e: Exception) {
            Log.d("Exception: ", "Activity is not Running!")
        }
    }

    /**
     * Call Back for Alert with Retry
     */
    interface RetryResponseListener {
        fun onRetry()
    }
}
