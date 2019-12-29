package de.htw.spaetiapp.util;

import android.widget.EditText;
import android.widget.TextView;

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.view.EditSpaetiFragment;

public class InputValidator {
    public static boolean checkForEmptyFields(EditSpaetiFragment fragment) {

        if (isEmpty(fragment.getSpaetiName())) {
            fragment.getSpaetiName().setError("Name is required");
            return true;
        }

        if (isEmpty(fragment.getAdress())) {
            fragment.getAdress().setError("Address is required");
            return true;
        }

        if (isEmpty(fragment.getNumber())) {
            fragment.getNumber().setError("Street number is required");
            return true;
        }

        if (isEmpty(fragment.getZip())) {
            fragment.getZip().setError("Postal code is required");
            return true;
        }

        if (isEmpty(fragment.getCity())) {
            fragment.getCity().setError("City is required");
            return true;
        }

        if (isEmpty(fragment.getCountry())) {
            fragment.getCountry().setError("Country is required");
            return true;
        }

        if (isEmpty(fragment.getOpenT())) {
            fragment.getOpenT().setText("n/a");
        }

        if (isEmpty(fragment.getCloseT())) {
            fragment.getCloseT().setText("n/a");
        }
        return false;
    }

    public static void clearFields(EditSpaetiFragment fragment){
        fragment.getSpaetiName().getText().clear();
        fragment.getOpenT().setText(R.string.hint_opening_time);
        fragment.getCloseT().setText(R.string.hint_closing_time);
        fragment.getDescription().getText().clear();
        fragment.getAdress().getText().clear();
        fragment.getNumber().getText().clear();
        fragment.getZip().getText().clear();
        fragment.getCity().getText().clear();
        fragment.getCountry().getText().clear();
    }

    private static boolean isEmpty(EditText editText) {

        return editText.getText().toString().trim().length() == 0;

    }

    private static boolean isEmpty(TextView textView) {

        return textView.getText().toString().equalsIgnoreCase("Opening Time") || textView.getText().toString().equalsIgnoreCase("Closing Time");

    }
}