package com.desarrollo.cursania.control_tienda_simple.data.util;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.List;

public class Controles {
    public static boolean EditTextVacio(Context context, List<EditText> etLista, String[] mensaje)
    {
        boolean estado = false;
        int pos = 0;
        for (EditText etNombre : etLista) {
            if (!estado && etNombre.getText().length() == 0) {
                new Mensaje(context).mensajeToas(mensaje[pos]);
                etNombre.requestFocus();
                estado = true;
                break;
            }
            pos++;
        }
        return estado;
    }

    public static boolean TextInputEditTextVacio(Context context, List<TextInputEditText> etLista, String[] mensaje)
    {
        boolean estado = false;
        int pos = 0;
        for (TextInputEditText etNombre : etLista) {
            if (!estado && etNombre.getText().length() == 0) {
                new Mensaje(context).mensajeToas(mensaje[pos]);
                etNombre.requestFocus();
                estado = true;
                break;
            }
            pos++;
        }
        return estado;
    }

    public static boolean AutoCompleteTextViewVacio(Context context, List<AutoCompleteTextView> etLista, String[] mensaje)
    {
        boolean estado = false;
        int pos = 0;
        for (AutoCompleteTextView etNombre : etLista) {
            if (!estado && etNombre.getText().length() == 0) {
                new Mensaje(context).mensajeToas(mensaje[pos]);
                etNombre.requestFocus();
                estado = true;
                break;
            }
            pos++;
        }
        return estado;
    }
}
