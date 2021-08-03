package ru.dariamikhailukova.task2.view

interface View {
    fun initView()
    fun updateViewData()
    fun showToast(resId: Int)
    fun sendIntent(name: String, text: String)
}