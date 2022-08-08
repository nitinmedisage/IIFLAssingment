package com.elink.iiflassingment.bottomsheetdialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.elink.iiflassingment.R
import com.elink.iiflassingment.interfaces.OnSortListener
import com.elink.iiflassingment.model.ChildWatchListEntity
import com.elink.iiflassingment.utilies.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * Created by Nitin Sabale
 * @since 05-08-2022. 19:39
 */
class SortBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {
    var childWatchListEntity: ChildWatchListEntity? = null
    lateinit var onSortListener: OnSortListener

    //Bottom Sheet Callback
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }
    var radioGroup: RadioGroup? = null
    lateinit var btnApply: Button
    lateinit var radioButton: RadioButton

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        //Get the content View
        val contentView = View.inflate(context, R.layout.bottom_sheet_sort, null)
        dialog.setContentView(contentView)
        //Set the coordinator layout behavior
        val params = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior
        //Set callback
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        setRadioButtonCheckStatus(arguments?.getInt(Constants.SORT_BY))

        // Assigning id of RadioGroup
        radioGroup = dialog.findViewById(R.id.radioGroup1)
        btnApply = dialog.findViewById(R.id.btnApply)
        btnApply.setOnClickListener(this)


    }

    private fun setRadioButtonCheckStatus(sortStatus: Int?) {
        var rbVolumeHigh = dialog!!.findViewById<RadioButton>(R.id.rbVolumeHigh)
        var rbVolumeLow = dialog!!.findViewById<RadioButton>(R.id.rbVolumeLow)
        var rbLastTradeHigh = dialog!!.findViewById<RadioButton>(R.id.rbLastTradeHigh)
        var rbLastTradeLow = dialog!!.findViewById<RadioButton>(R.id.rbLastTradeLow)
        var rbPCloseHigh = dialog!!.findViewById<RadioButton>(R.id.rbPCloseHigh)
        var rbPCloseLow = dialog!!.findViewById<RadioButton>(R.id.rbPCloseLow)

        if (rbVolumeHigh.text.toString().toInt() == sortStatus) {
            rbVolumeHigh.isChecked = true
        } else if (rbVolumeLow.text.toString().toInt() == sortStatus) {
            rbVolumeLow.isChecked = true
        } else if (rbLastTradeHigh.text.toString().toInt() == sortStatus) {
            rbLastTradeHigh.isChecked = true
        } else if (rbLastTradeLow.text.toString().toInt() == sortStatus) {
            rbLastTradeLow.isChecked = true
        } else if (rbPCloseHigh.text.toString().toInt() == sortStatus) {
            rbPCloseHigh.isChecked = true
        } else if (rbPCloseLow.text.toString().toInt() == sortStatus) {
            rbPCloseLow.isChecked = true
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onSortListener = (activity as OnSortListener?)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnApply -> {
                val selectedOption: Int = radioGroup!!.checkedRadioButtonId
                radioButton = dialog!!.findViewById(selectedOption)
                onSortListener.onSortListener(radioButton.text.toString().toInt())
                dismiss()
            }
        }
    }
}