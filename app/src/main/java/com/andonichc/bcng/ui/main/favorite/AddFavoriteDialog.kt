package com.andonichc.bcng.ui.main.favorite


import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.annotation.ColorRes
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.andonichc.bcng.R
import com.andonichc.bcng.presentation.model.*
import com.andonichc.bcng.util.setTint
import kotlinx.android.synthetic.main.dialog_favorite_add.*


class AddFavoriteDialog : DialogFragment(), View.OnClickListener {
    companion object {
        @ColorRes
        private const val SELECTED_COLOR = R.color.red
        private const val UNSELECTED_COLOR = R.color.black

        fun newInstance(): AddFavoriteDialog =
                AddFavoriteDialog()
    }

    var favoriteSelectListener: FavoriteSelectedListener? = null

    var selectedIcon: String = SCHOOL

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_favorite_add, container)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdd.setOnClickListener(this)

        ivHome.setOnClickListener(this)
        ivSchool.setOnClickListener(this)
        ivGym.setOnClickListener(this)
        ivWork.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        unselectImages()
        when (view?.id) {
            btnAdd.id -> onClickAdd()

            ivHome.id -> {
                onClickIcon(HOME)
                ivHome.setTint(SELECTED_COLOR)
            }
            ivSchool.id -> {
                onClickIcon(SCHOOL)
                ivSchool.setTint(SELECTED_COLOR)
            }
            ivGym.id -> {
                onClickIcon(GYM)
                ivGym.setTint(SELECTED_COLOR)
            }
            ivWork.id -> {
                onClickIcon(WORK)
                ivWork.setTint(SELECTED_COLOR)
            }
        }
    }

    private fun unselectImages() {
        ivHome.setTint(UNSELECTED_COLOR)
        ivSchool.setTint(UNSELECTED_COLOR)
        ivGym.setTint(UNSELECTED_COLOR)
        ivWork.setTint(UNSELECTED_COLOR)
    }

    private fun onClickIcon(icon: String) {
        selectedIcon = icon
    }

    private fun onClickAdd() {
        val name = etName.text.toString().trim()
        if (!name.isEmpty()) {
            favoriteSelectListener?.onFavoriteSelected(
                    FavoritePresentationModel(-1, name,selectedIcon, mutableListOf()))
        }
        dismiss()
    }
}