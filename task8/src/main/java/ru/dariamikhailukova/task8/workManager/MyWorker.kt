package ru.dariamikhailukova.task8.workManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

/**
 * Класс, имитирующий бэкап данных
 *
 * @param context контекст вью
 * @param workerParams передаваемые параметры
 */
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams)  {

    override fun doWork(): Result {
        Log.d(TAG, "doWork: start")

        val name = inputData.getString("name")
        val text = inputData.getString("text")

        Log.d(TAG, "Name: " + name!!)
        Log.d(TAG, "Text: " + text!!)

        Log.d(TAG, "doWork: end")

        return Result.success()
    }

    companion object {
        const val TAG = "WORK_MANAGER"
    }

}