package com.elink.iiflassingment.bottomsheetdialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.elink.iiflassingment.R
import com.elink.iiflassingment.model.ChildWatchListEntity
import com.elink.iiflassingment.utilies.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Nitin Sabale
 * @since 05-08-2022. 19:39
 */
class WatchListDetailsBottomSheet : BottomSheetDialogFragment() {
    // child watch list entity class
    var childWatchListEntity: ChildWatchListEntity? = null

    //Bottom Sheet Callback
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        //Get the content View
        val contentView = View.inflate(context, R.layout.bottom_sheet_mcm_sync_data, null)
        dialog.setContentView(contentView)
        //Set the coordinator layout behavior
        val params = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior
        //Set callback
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        childWatchListEntity= arguments?.getParcelable(Constants.WATCHLIST_DETAILS)

        val txtDayHigh = dialog.findViewById<TextView>(R.id.txtDayHigh)
        txtDayHigh.text = "${childWatchListEntity!!.dayHigh}"

         val txtShortName = dialog.findViewById<TextView>(R.id.textViewShortName)
        txtShortName.text = "${childWatchListEntity!!.shortName}"

        val txtDayLow = dialog.findViewById<TextView>(R.id.txtDayLow)
        txtDayLow.text = "${childWatchListEntity!!.dayLow}"

        val txtNseBseCode = dialog.findViewById<TextView>(R.id.txtNseBseCode)
        txtNseBseCode.text = "${childWatchListEntity!!.nseBseCode}"

        val tvScripCode = dialog.findViewById<TextView>(R.id.tvScripCode)
        tvScripCode.text = "${childWatchListEntity!!.scripCode}"

    }
}