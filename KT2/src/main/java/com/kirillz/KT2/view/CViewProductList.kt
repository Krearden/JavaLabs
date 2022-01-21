package com.kirillz.KT2.view

import com.kirillz.KT2.modelfx.CProductFX
import com.kirillz.KT2.viewmodel.CViewModelProductList
import com.kirillz.KT2.Main
import tornadofx.*

class CViewProductList : View("Товары") {
    val viewModelProductList : CViewModelProductList by inject()
    val table = tableview(viewModelProductList.products) {
        readonlyColumn("ID", CProductFX::id)
        column("Категория", CProductFX::propertyCategory).makeEditable()
        column("Цена", CProductFX::propertyPrice).makeEditable()
        column("Имя", CProductFX::propertyName).makeEditable()
        readonlyColumn("Количество заказов у товара", CProductFX::orderCount)
    }

    override val root = borderpane {
        top {
            menubar {
                menu("База данных") {
                    item("Заполнить базу данных из файла").action {
                        Main.loadInfo(true)
                        viewModelProductList.save()
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
                    }
                    label("")
                }
                label("")
                fieldset ("Действия") {
                    label("")
                    button ("Сохранить") {
                        useMaxWidth = true
                        action {
                            viewModelProductList.save()
                        }
                        tooltip("Сохраняет изменения в базу данных")
                    }
                    label("")
                    button ("Удалить") {
                        useMaxWidth = true
                        action {
                            viewModelProductList.delete(table.selectedItem)
                        }

                    }
                    label("")
                }
            }
        }
    }
}
