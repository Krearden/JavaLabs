package com.kirillz.KT2.view

import com.kirillz.KT2.Main
import com.kirillz.KT2.modelfx.COrderFX
import com.kirillz.KT2.viewmodel.CViewModelOrderList
import com.kirillz.KT2.viewmodel.CViewModelProductList
import tornadofx.*

class CViewOrderList : View("Заказы") {
    val viewModelOrderList : CViewModelOrderList by inject()
    val table = tableview(viewModelOrderList.orders) {
        readonlyColumn("ID Заказа", COrderFX::order_id)
        column("Дата покупки", COrderFX::propertyPurchase_date)
        readonlyColumn("ID Владельца заказа", COrderFX::owner_id)
        readonlyColumn("Количество заказов у товара", COrderFX::productCount)
    }
    override val root = borderpane {
        top {
            menubar {
                menu("База данных") {
                    item("Заполнить базу данных из файла").action {
                        Main.loadInfo(true)
                        viewModelOrderList.save()
                    }
                }
            }
        }

        center {
            this += table
        }

        right {
            form {
                fieldset ("Выбор таблицы") {
                    label("")
                    button ("Пользователи") {
                        useMaxWidth = true
                        action {
                            replaceWith<CViewUserList>()
                        }
                    }
                    label("")
                    button ("Товары") {
                        useMaxWidth = true
                        action {
                            replaceWith<CViewProductList>()
                        }
                    }
                    label("")
                    button ("Заказы") {
                        useMaxWidth = true
                        action {
                            replaceWith<CViewOrderList>()
                        }
                    }
                    label("")
                }
                label("")
                fieldset ("Действия") {
                    label("")
                    button ("Сохранить") {
                        useMaxWidth = true
                        tooltip("Сохраняет изменения в базу данных")
                        isDisable = true
                    }
                    label("")
                    button ("Удалить") {
                        useMaxWidth = true
                        action {
                            viewModelOrderList.delete(table.selectedItem)
                        }

                    }
                    label("")
                }
            }
        }

    }
}
