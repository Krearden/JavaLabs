package com.kirillz.KT2.view

import com.kirillz.KT2.modelfx.CUserFX
import com.kirillz.KT2.viewmodel.CViewModelUserList
import tornadofx.*

class CViewUserList : View("Пользователи") {
    val viewModelUserList : CViewModelUserList by inject()
    override val root = borderpane {
        center {
            tableview(viewModelUserList.users) {
                readonlyColumn("ID", CUserFX::id)
                column("Логин", CUserFX::propertyLogin).makeEditable()
                column("Имя", CUserFX::propertyName).makeEditable()
                column("Пол", CUserFX::propertyGender).makeEditable()
                column("Дата рождения", CUserFX::propertyDateOfBirth).makeEditable()
                readonlyColumn("Возраст", CUserFX::age)
                readonlyColumn("Количество заказов", CUserFX::orderCount)
            }
        }

        right {
            form {
                fieldset ("Выбор таблицы") {
                    label("")
                    button ("Пользователи") {
                        useMaxWidth = true
                    }
                    label("")
                    button ("Товары") {
                        useMaxWidth = true
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
                    }
                    label("")
                    button ("Отмена") {
                        useMaxWidth = true
                        action {viewModelUserList.save()}
                    }
                    label("")
                }
            }
        }
    }
}
