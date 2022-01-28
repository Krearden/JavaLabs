package com.kirillz.rest

import com.kirillz.repositories.IRepositoryOrders
import com.kirillz.repositories.IRepositoryProducts
import com.kirillz.repositories.IRepositoryUsers
import com.kirillz.services.CServiceFileDataLoader
import com.kirillz.services.CServiceFileDataLoader.orders
import com.kirillz.services.CServiceFileDataLoader.products
import com.kirillz.services.CServiceFileDataLoader.users
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@RestController
@RequestMapping("upload_excel_file")
class CControllerUploadData {
    @Autowired
    private lateinit var repositoryOrders: IRepositoryOrders

    @Autowired
    private lateinit var repositoryUsers: IRepositoryUsers

    @Autowired
    private lateinit var repositoryProducts: IRepositoryProducts

    @PostMapping
    fun handleFileUpload(@RequestParam("file") file: MultipartFile)
    {
        val file_byte_array_input_stream = ByteArrayInputStream(file.bytes)
        val excel = XSSFWorkbook(file_byte_array_input_stream)
        CServiceFileDataLoader.loadInfo(excel)
        //если списки, заполненные из файла, не пустые, то пишем информацию в БД
        if (users.size != 0 && products.size != 0 && orders.size != 0)
        {
            repositoryUsers.saveAll(users)
            repositoryProducts.saveAll(products)
            repositoryOrders.saveAll(orders)
        }

    }

}