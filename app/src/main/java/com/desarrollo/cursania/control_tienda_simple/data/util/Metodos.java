package com.desarrollo.cursania.control_tienda_simple.data.util;

import android.annotation.SuppressLint;


import com.desarrollo.cursania.control_tienda_simple.data.modelo.Producto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Metodos {
    public  static String CadenasComponer(String caracter , Object[] lista)
    {
        String cadena = "";
        if (!caracter.isEmpty())
        {
            for (Object ItemLista: lista) {
                cadena += ItemLista.toString() + caracter;
            }
        }

        return cadena.substring(0,cadena.length()-1);
    }
    public static String ComillaSimple(String cadena)
    {
        return "'" + cadena + "'";
    }
    public static String Concatenar(Object[] lista)
    {
        String cadena = "";
        for (Object ItemLista: lista) {
            cadena += ItemLista.toString();
        }
        return cadena;
    }
    public  static String CadenasDescomponer(String cadena , int posicion, String caracter)
    {   //cadena
        String cadenaRetorno = cadena;
        // si se pide una posicion mas
        if (posicion > 1)
        {   //recorrido para quitar los demsa
            for (int i = 0; i<posicion-1; i++)
            {   // desconposicion
                cadenaRetorno = cadenaRetorno.substring(cadenaRetorno.indexOf(caracter)+1);
            }
        }//cuando encuentre el caracter
        if (cadenaRetorno.indexOf(caracter) > 0)
        {   //posicion
            cadenaRetorno = cadenaRetorno.substring(0,cadenaRetorno.indexOf(caracter));
        }
        return cadenaRetorno;
    }

    //region fecha y hora
    @SuppressLint("SimpleDateFormat")
    public static String getHora(){
        return new SimpleDateFormat("HH:mm").format(new Date());
    }
    public static String getFecha(){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
    //endregion

    //region metodos gson

    // convertir una lista a Json, para la comunicacion entre actividades
    public  static String convertirProductoListaTexto(List<Producto> _lista)
    {   // convertir un List a formato Json
        Gson gson = new Gson();

        // retornamos
        return  gson.toJson(_lista);
    }

    // convertir un formato Json, a una lista especifica
    public  static List<Producto> convertirProductoTextoLista(String _gson)
    {   //objeto tipo Gson
        Gson gson = new Gson();

        // objeto tipo Type, para la convesion de json a list
        Type lista = new TypeToken<List<Producto>>(){}.getType();

        // retornamos
        return  gson.fromJson(_gson,lista);
    }
    //endregion
}