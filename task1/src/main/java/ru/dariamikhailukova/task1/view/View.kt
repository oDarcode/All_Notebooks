package ru.dariamikhailukova.task1.view

interface View {
    fun initView()
    fun updateViewData()
    fun showToast(resId: String)
}