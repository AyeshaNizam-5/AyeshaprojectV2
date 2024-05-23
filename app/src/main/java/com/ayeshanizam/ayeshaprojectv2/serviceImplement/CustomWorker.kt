package com.ayeshanizam.ayeshaprojectv2.serviceImplement
import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters


class CustomWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val username = inputData.getString("username") ?: "No Username Passed"
        Utils.sendNotification(applicationContext, "Task executed for user: $username")
        return Result.success()
    }
}
