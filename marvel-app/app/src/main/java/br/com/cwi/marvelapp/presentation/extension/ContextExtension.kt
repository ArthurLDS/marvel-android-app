package br.com.cwi.marvelapp.presentation.extension

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import br.com.cwi.marvelapp.R

fun Context.showAlertDialog(
    errorMessage: String,
    cancelable: Boolean = true,
    listener: (dialog: DialogInterface, which: Int) -> Unit = { _, _ -> }
) {
    val dialog: AlertDialog = AlertDialog.Builder(this)
        .setCancelable(cancelable)
        .setMessage(errorMessage)
        .setPositiveButton(getString(R.string.txt_ok), listener)
        .create()

    dialog.show()

    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
}