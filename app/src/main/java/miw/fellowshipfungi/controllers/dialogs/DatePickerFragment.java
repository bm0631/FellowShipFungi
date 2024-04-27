package miw.fellowshipfungi.controllers.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import miw.fellowshipfungi.R;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String selectedDate = formatDate(year, month, day);

        EditText etPlannedDate = getActivity().findViewById(R.id.etDate);
        etPlannedDate.setText(selectedDate);
    }

    private String formatDate(int year, int month, int day) {
        return day + "-" + (month + 1) + "-" + year;
    }

}
