package com.cwg.perfectalertdialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author .: Oriaghan Uyi
 * @email ..: oriaghan3@gmail.com, uyi.oriaghan@cwg-plc.com
 * @created : 4/20/18 11:17 AM
 */
public class PerfectDialog {

    private Activity activity;
    private Context context;
    private View inflatedView;
    private boolean isCancelable = true;
    private AlertDialog dialog;

    public PerfectDialog(Activity activity) {
        this.activity = activity;
        this.context = activity;
        initViews(R.style.DialogAnimation);
    }

    public PerfectDialog(Activity activity, @StyleRes int resId) {
        this.activity = activity;
        this.context = activity;
        initViews(resId);
    }

    private void initViews(@StyleRes int resId) {
        dialog = new AlertDialog.Builder(context).create();

        try {
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setWindowAnimations(resId);
            dialog.getWindow().setGravity(Gravity.TOP);
        } catch (Exception e) {

        }
    }

    public View setUpView(@LayoutRes int resource) {
        //inflate view
        inflatedView = LayoutInflater.from(context).inflate(resource, null);
        dialog.setView(inflatedView, 100, 200, 100, 100);
        return inflatedView;
    }

    public PerfectDialog setUpView(@LayoutRes int resource, OnViewSetUp onViewSetUp) {
        onViewSetUp.setUpView(setUpView(resource));
        return this;
    }

    public PerfectDialog addView(@LayoutRes int resource) {
        setUpView(resource);
        return this;
    }

    public PerfectDialog addViewSetupInterface(OnViewSetUp onViewSetUp) {
        onViewSetUp.setUpView(inflatedView);
        return this;
    }

    public boolean isOpen() {
        return dialog.isShowing();
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public PerfectDialog setCancelable(final boolean cancelable) {
        this.isCancelable = cancelable;
        dialog.setCancelable(cancelable);
        return this;
    }

    public PerfectDialog updateView(@LayoutRes int resource) {
        //remove animation
        forceCloseDialog();
        dialog = null;
        return new PerfectDialog(activity, R.style.NoDialogAnimationIn).
                addView(resource);
    }

    public PerfectDialog updateView(@LayoutRes int resource, OnViewSetUp onViewSetUp) {
        forceCloseDialog();
        return new PerfectDialog(activity).
                setUpView(resource, onViewSetUp);
    }

    public PerfectDialog openDialog() {
        openDialog(null);
        return this;
    }

    public void openDialog(@Nullable PerfectDialogToggleListener listener) {
        if (isOpen()) {
//            AppUtil.error("cant open dialog: Custom dialog is still open");
            return;
        }

        dialog.show();
    }

    public void forceCloseDialog() {
        forceCloseDialog(null);
    }

    public void forceCloseDialog(@Nullable PerfectDialogToggleListener listener) {
        if (!isCancelable()) {
            setCancelable(true);
        }

        closeDialog(listener);
    }

    public void closeDialog() {
        closeDialog(null);
    }

    public void closeDialog(@Nullable PerfectDialogToggleListener listener) {
        if (!isOpen()) {
            return;
        }

        if (!isCancelable()) {
            return;
        }

        dialog.cancel();

    }

    public interface OnViewSetUp {
        void setUpView(View view);
    }
}