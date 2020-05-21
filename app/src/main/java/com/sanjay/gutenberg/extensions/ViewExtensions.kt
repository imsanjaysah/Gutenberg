/*
 * ViewExtensions.kt
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.extensions

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.sanjay.gutenberg.R


/**
 * Created by Sanjay Sah.
 */

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is EditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}

fun EditText.makeClearableEditText(
    onIsNotEmpty: (() -> Unit)?,
    onClear: (() -> Unit)?,
    clearDrawable: Drawable
) {
    val updateRightDrawable = {
        this.setCompoundDrawables(
            compoundDrawables[COMPOUND_DRAWABLE_LEFT_INDEX], null,
            if (text.isNotEmpty()) clearDrawable else null,
            null
        )
    }
    updateRightDrawable()
    this.afterTextChanged {
        if (it.isNotEmpty()) {
            onIsNotEmpty?.invoke()
        }
        updateRightDrawable()
    }
    this.onRightDrawableClicked {
        this.text.clear()
        this.setCompoundDrawables(null, null, null, null)
        onClear?.invoke()
        this.requestFocus()
    }
}

private const val COMPOUND_DRAWABLE_LEFT_INDEX = 0
private const val COMPOUND_DRAWABLE_RIGHT_INDEX = 2

fun EditText.makeClearableEditText(
    onIsNotEmpty: (() -> Unit)? = null,
    onCleared: (() -> Unit)? = null
) {
    compoundDrawables[COMPOUND_DRAWABLE_RIGHT_INDEX]?.let { clearDrawable ->
        makeClearableEditText(onIsNotEmpty, onCleared, clearDrawable)
    }
}


fun Toolbar.changeToolbarFont(){
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is TextView && view.text == title) {
            view.typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)
            break
        }
    }
}


