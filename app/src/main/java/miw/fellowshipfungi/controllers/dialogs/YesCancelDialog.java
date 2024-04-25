package miw.fellowshipfungi.controllers.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

import miw.fellowshipfungi.R;

public class YesCancelDialog extends DialogFragment {

    private YesNoDialogCallback callback;
    private String titleResId;
    private String messageResId;

    public YesCancelDialog() {
        // Constructor vacío necesario para DialogFragment
    }

    public static YesCancelDialog newInstance(String titleResId, String messageResId) {
        YesCancelDialog dialog = new YesCancelDialog();
        dialog.setTitleResId(titleResId);
        dialog.setMessageResId(messageResId);
        return dialog;
    }

    private void setTitleResId(String titleResId) {
        this.titleResId = titleResId;
    }

    private void setMessageResId(String messageResId) {
        this.messageResId = messageResId;
    }

    public void setCallback(YesNoDialogCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            callback.onPositiveClick();
                        }
                    }
                })
                .setNegativeButton(R.string.CANCELAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            callback.onNegativeClick();
                        }
                    }
                });

        return builder.create();
    }

    public interface YesNoDialogCallback {
        void onPositiveClick();

        default void onNegativeClick() {
            // No hacer nada, simplemente cerrar el diálogo
        }
    }
}
