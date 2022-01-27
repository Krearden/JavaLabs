package com.kirillz.rest

import com.kirillz.repositories.IRepositoryOrders
import com.kirillz.repositories.IRepositoryUsers
import com.kirillz.services.CServiceFileDataLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("receive_report_data")
class CControllerReceiveData {
    @Autowired
    private lateinit var repositoryOrders: IRepositoryOrders

    @Autowired
    private lateinit var repositoryUsers: IRepositoryUsers

    @GetMapping("")
    fun getAllInfoForReport()
    {
        var corders = repositoryOrders.findAll()
        var cusers = repositoryUsers.findAll()
        //если в БД есть информация, то создать на ее основании отчет (вар. 15)
        if (cusers.size != 0 && corders.size != 0)
        {
            //внутри фукнции вызывается другая, отвечающая за создание отчета
            CServiceFileDataLoader.getFridayUsers(cusers, corders)
        }

    }
}