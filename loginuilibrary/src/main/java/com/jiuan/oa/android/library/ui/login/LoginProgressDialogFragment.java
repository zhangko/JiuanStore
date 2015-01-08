package com.jiuan.oa.android.library.ui.login;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public class LoginProgressDialogFragment extends DialogFragment {

    private onKeyBackListener mListener;

    public void setOnKeyBackListener(onKeyBackListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity(), getTheme());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (mListener != null) {
                        mListener.onKeyBack();
                    }
                    return true;
                }
                return false;
            }
        });
        return progressDialog;
    }

    public interface onKeyBackListener {

        void onKeyBack();
    }
}
