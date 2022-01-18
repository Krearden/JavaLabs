package com.kirillz.KT2

import com.kirillz.KT2.view.CViewUserList
import javafx.scene.Scene
import tornadofx.App
import tornadofx.UIComponent

class CApplication : App(CViewUserList::class)
{
    override fun createPrimaryScene(view: UIComponent) = Scene(view.root, 950.0, 600.0)
}