package com.kirillz.services

import com.kirillz.model.COrder
import com.kirillz.model.CProduct
import com.kirillz.model.CUser
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.wp.usermodel.HeaderFooterType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xwpf.usermodel.*
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


object CServiceFileDataLoader {
    val users = ArrayList<CUser>()
    val products = ArrayList<CProduct>()
    val orders = ArrayList<COrder>()

    //чтение пользователей
    private fun loadUsersExcel(work_book : XSSFWorkbook) {
        try {
            work_book.use { wb ->
                val sheet: Sheet = wb.getSheet("Пользователи")
                val rows = sheet.lastRowNum
                var row: Row?
                var cell: Cell
                //переменные для данных пользователя
                var id: UUID?
                var temp: String?
                var login: String?
                var name: String?
                var gender: String?
                var dateOfBirth: LocalDate?
                for (i in 1..rows) {
                    row = sheet.getRow(i)
                    if (row == null) continue
                    //id
                    cell = row.getCell(0)
                    temp = cell.stringCellValue
                    id = UUID.fromString(temp)
                    //login
                    cell = row.getCell(1)
                    login = cell.stringCellValue
                    //name
                    cell = row.getCell(2)
                    name = cell.stringCellValue
                    //gender
                    cell = row.getCell(3)
                    gender = cell.stringCellValue
                    if (gender == "м") gender = "Male" else if (gender == "ж") gender = "Female"
                    //birth date
                    cell = row.getCell(4)
                    dateOfBirth = cell.dateCellValue.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    val ord = Arrays.asList(COrder())
                    users.add(CUser(id, login, name, gender, dateOfBirth, ord))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //чтение товаров
    private fun loadProductsExcel(work_book : XSSFWorkbook) {
        try {
            work_book.use { wb ->
                val sheet: Sheet = wb.getSheet("Товары")
                val rows = sheet.lastRowNum
                var row: Row?
                var cell: Cell
                //переменные для данных товара
                var product_id: UUID?
                var temp: String?
                var product_name: String?
                var price: Int
                var category: String?
                for (i in 1..rows) {
                    row = sheet.getRow(i)
                    if (row == null) continue
                    //product_id
                    cell = row.getCell(0)
                    temp = cell.stringCellValue
                    product_id = UUID.fromString(temp)
                    //product_name
                    cell = row.getCell(1)
                    product_name = cell.stringCellValue
                    //price
                    cell = row.getCell(2)
                    price = cell.numericCellValue.toInt()
                    //category
                    cell = row.getCell(3)
                    category = cell.stringCellValue
                    val ord = Arrays.asList(COrder())
                    products.add(CProduct(product_id, product_name, price, category, ord))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //чтение заказов
    private fun loadOrdersExcel(work_book : XSSFWorkbook) {
        try {
            work_book.use { wb ->
                val sheet: Sheet = wb.getSheet("Покупки")
                val rows = sheet.lastRowNum
                var row: Row?
                var cell: Cell
                //переменные для данных заказа
                var user_id: UUID
                var product_id: UUID?
                var temp: String?
                var purchase_date_time: LocalDateTime
                var purchase_date: LocalDate?
                for (i in 1..rows) {
                    row = sheet.getRow(i)
                    if (row == null) continue
                    //user_id
                    cell = row.getCell(0)
                    temp = cell.stringCellValue
                    user_id = UUID.fromString(temp)
                    //product_id
                    cell = row.getCell(1)
                    temp = cell.stringCellValue
                    product_id = UUID.fromString(temp)
                    //purchase date and time
                    cell = row.getCell(2)
                    purchase_date_time = cell.localDateTimeCellValue
                    purchase_date = purchase_date_time.toLocalDate()
                    val prod = Arrays.asList(CProduct())
                    orders.add(COrder(null, getCUser_by_id(user_id), purchase_date, prod))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //заполнить продукты у заказов
    private fun loadProductsInOrders(work_book : XSSFWorkbook) {
        for (order in orders) {
            val owners_uuid = order.owner.id
            val local_products: MutableList<CProduct> = ArrayList()
            try {
                work_book.use { wb ->
                    val sheet: Sheet = wb.getSheet("Покупки")
                    val rows = sheet.lastRowNum
                    var row: Row?
                    var cell: Cell
                    //переменные для данных заказа
                    var user_id: UUID
                    var product_id: UUID
                    var temp: String?
                    for (i in 1..rows) {
                        row = sheet.getRow(i)
                        if (row == null) continue
                        //user_id
                        cell = row.getCell(0)
                        temp = cell.stringCellValue
                        user_id = UUID.fromString(temp)
                        //product_id
                        cell = row.getCell(1)
                        temp = cell.stringCellValue
                        product_id = UUID.fromString(temp)
                        if (user_id == owners_uuid) {
                            //находится объект класса CProduct по его UUID и добавляется в список
                            local_products.add(getCProductById(product_id))
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            //заполененный список присваевается агрументу класса COrder
            //теперь конкретный заказ "понимает" какие в нем находятся товары
            order.products = local_products
        }
    }

    //заполнить заказы у продуктов
    private fun loadOrdersInProducts(work_book : XSSFWorkbook) {
        for (product in products) {
            val products_uuid = product.id
            val local_orders: MutableList<COrder> = ArrayList()
            try {
                work_book.use { wb ->
                    val sheet: Sheet = wb.getSheet("Покупки")
                    val rows = sheet.lastRowNum
                    var row: Row?
                    var cell: Cell
                    //переменные для данных заказа
                    var user_id: UUID
                    var product_id: UUID
                    var temp: String?
                    for (i in 1..rows) {
                        row = sheet.getRow(i)
                        if (row == null) continue
                        //user_id
                        cell = row.getCell(0)
                        temp = cell.stringCellValue
                        user_id = UUID.fromString(temp)
                        //product_id
                        cell = row.getCell(1)
                        temp = cell.stringCellValue
                        product_id = UUID.fromString(temp)
                        if (products_uuid == product_id) {
                            local_orders.add(getCOrderByUserId(user_id))
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            product.orders = local_orders
        }
    }

    //заполнить заказы у пользователей
    private fun loadOrdersInUsers() {
        for (user in users) {
            val user_uuid = user.id
            val local_orders: MutableList<COrder> = ArrayList()
            for (order in orders) {
                val owners_uuid = order.owner.id
                if (user_uuid == owners_uuid) {
                    local_orders.add(order)
                }
            }
            user.orders = local_orders
        }
    }

    //загрузка информации из excel и заполнение аргументов классов, отвечающих за связь между ними
    fun loadInfo(work_book : XSSFWorkbook ) {
        //заполняем списки, определенные в начале файла. аргументы представленных классов, ссылающиеся друг на друга, пока пусты.
        loadProductsExcel(work_book)
        loadUsersExcel(work_book)
        loadOrdersExcel(work_book)
        //заполняем аргументы классов в списках, которые ссылаются друг на друга (чтобы hibernate сам расставил все ссылки в БД)
        loadProductsInOrders(work_book)
        loadOrdersInProducts(work_book)
        loadOrdersInUsers()
    }

    //возвращает пользователя по его UUID
    private fun getCUser_by_id(user_id: UUID): CUser {
        var answer_cUser = CUser()
        for (i in users.indices) {
            if (users[i].id == user_id) answer_cUser = users[i]
        }
        return answer_cUser
    }

    //возвращает COrder по id его владельца
    private fun getCOrderByUserId(user_id: UUID): COrder {
        var answer_cOrder = COrder()
        for (order in orders) {
            if (order.owner.id == user_id) {
                answer_cOrder = order
            }
        }
        return answer_cOrder
    }

    //возвращает продукт по его UUID
    private fun getCProductById(product_id: UUID): CProduct {
        var answer_cProduct = CProduct()
        for (product in products) {
            if (product.id == product_id) {
                answer_cProduct = product
            }
        }
        return answer_cProduct
    }

    //проверка, является ли дата пятницой
    private fun fridayCheck(purchase_date: LocalDate): Boolean
    {
        val day = purchase_date.dayOfWeek
        return day == DayOfWeek.FRIDAY
    }

    fun getFridayUsers(corders : List<COrder>)
    {
        val friday_cusers : MutableList<CUser> = mutableListOf()
        for (corder in corders)
        {
            if (fridayCheck(corder.purchase_date))
            {
                friday_cusers.add(corder.owner)
            }
        }
        if (friday_cusers.size != 0)
        {
            createReport(friday_cusers)
        }
    }

    //функция записи результатов работы программы в WORD файл
    fun createReport(friday_cusers : List<CUser>)
    {
        try {
            XWPFDocument().use { document ->
                //хедер
                val head = document.createHeader(HeaderFooterType.DEFAULT)
                head.createParagraph()
                        .createRun()
                        .setText("КТ-3 Технологии Java; Выполнил Кирилл Запорожченко (2 курс, Физфак, ПМИ, ФЗ-12)")
                //заголовок документа
                val paragraph = document.createParagraph()
                paragraph.alignment = ParagraphAlignment.CENTER
                val run = paragraph.createRun()
                run.isBold = true
                run.fontFamily = "Courier"
                run.fontSize = 15
                run.setText("\nНиже представлены пользователи, которые совершили покупки в пятницу\n")
                //создаем табцицу
                val table = document.createTable()
                //первая строка таблицы с заголовками столбцов
                val tableRowOne = table.getRow(0)
                tableRowOne.getCell(0).text = "ID"
                tableRowOne.addNewTableCell().text = "Date_of_birth"
                tableRowOne.addNewTableCell().text = "Gender"
                tableRowOne.addNewTableCell().text = "Login"
                tableRowOne.addNewTableCell().text = "Name"
                for (friday_cuser in friday_cusers) {
                    val tableRow = table.createRow()
                    tableRow.getCell(0).text = friday_cuser.id.toString()
                    tableRow.addNewTableCell().text = friday_cuser.dateOfBirth.toString()
                    tableRow.addNewTableCell().text = friday_cuser.gender
                    tableRow.addNewTableCell().text = friday_cuser.login
                    tableRow.addNewTableCell().text = friday_cuser.name
                }
                try {
                    FileOutputStream("KT3_friday_users_report.docx").use { out -> document.write(out) }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}