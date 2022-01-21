package com.kirillz.KT2.view

import com.kirillz.KT2.modelfx.CUserFX
import com.kirillz.KT2.viewmodel.CViewModelUserList
import com.kirillz.KT2.Main
import tornadofx.*

class CViewUserList : View("Пользователи") {
    val viewModelUserList : CViewModelUserList by inject()
    val table = tableview(viewModelUserList.users) {
                    readonlyColumn("ID", CUserFX::id)
                    column("Логин", CUserFX::propertyLogin).makeEditable()
                    column("Имя", CUserFX::propertyName).makeEditable()
                    column("Пол", CUserFX::propertyGender).makeEditable()
                    column("Дата рождения", CUserFX::propertyDateOfBirth).makeEditable()
                    readonlyColumn("Возраст", CUserFX::age)
                    readonlyColumn("Количество заказов", CUserFX::orderCount)
                }

    override val root = borderpane {
        top {
            menubar {
                menu("База данных") {
                    item("Заполнить базу данных из файла").action {
                        Main.loadInfo(true)
                        viewModelUserList.save()
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
                        action {
                            viewModelUserList.save()
                        }
                        tooltip("Сохраняет изменения в базу данных")
                    }
                    label("")
                    button ("Удалить") {
                        useMaxWidth = true
                        action {
                            viewModelUserList.delete(table.selectedItem)

                        }

                    }
                    label("")
                    button("Сгенерировать отчет")
                    {
                        useMaxWidth = true
                        action {
                            Main.createWord()

                        }
                    }
                    label("")
                }
            }
        }
    }
}
