package de.htw.spaetiapp.view;

import android.widget.EditText;
import android.widget.TextView;

public interface EditSpaetiFragment {
    EditText getSpaetiName();
    EditText getAdress();
    EditText getNumber();
    EditText getZip();
    EditText getCity();
    EditText getCountry();
    EditText getDescription();
    TextView getOpenT();
    TextView getCloseT();
}
