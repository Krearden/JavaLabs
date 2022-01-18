package com.kirillz.KT2.viewmodel


import  com.kirillz.KT2.services.CServiceUsers
import  com.kirillz.KT2.modelfx.CUserFX
import tornadofx.ViewModel

class CViewModelUserList : ViewModel() {
    val serviceUsers : CServiceUsers by inject()
    val users = serviceUsers.getAll()
    fun save()
    {

    }
}