package com.desarrollo.cursania.control_tienda_simple.data.util;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.cursania.control_tienda_simple.R;

public class Mensaje {
    private Context context;

    public Mensaje(Context context) {
        this.context = context;
    }

    public  static Dialog mensajeDialogo(Context cnt, LayoutInflater inflater, String _line1, String _line2)
    {
        //LayoutInflater inflater = getLayoutInflater();

        View dialoglayout = inflater.inflate(R.layout.progres_bar, null);

        TextView etAsunto = dialoglayout.findViewById(R.id.etLinea1);
        TextView etMensaje =dialoglayout.findViewById(R.id.etLinea2);

        etAsunto.setText(_line1);
        etMensaje.setText(_line2);

        AlertDialog.Builder builder = new AlertDialog.Builder(cnt);
        builder.setView(dialoglayout);
        Dialog dialog= builder.create();
        return dialog;
    }

    public void mensajeToas(Object _mensaje)
    {
        Toast.makeText(context, _mensaje.toString(), Toast.LENGTH_LONG).show();
    }

    public void mensajeToasGuardar()
    {
        mensajeToas("La información se guardo correctamente");
    }

    public void mensajeToasEliminar()
    {
        mensajeToas("La información se elimino correctamente");
    }

}
