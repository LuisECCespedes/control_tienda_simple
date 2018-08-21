package com.desarrollo.cursania.control_tienda_simple.data.preferencia;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionPreferences {
    //region constantes Preferencias
    private static final String PREFS_NAME       = "DATA_SESSION";
    private static final String PREFS_CLAVE      = "PREFS_CLAVE";
    private static final String PREFS_LOGIN      = "PREFS_LOGIN";
    private static final String PREFS_PRODUCTO   = "PREFS_PRODUCTO";
    private static final String PREFS_CLIENTE    = "PREFS_CLIENTE";
    private static final String PREF_VENTA_CABECERA     = "PREF_VENTA_CABECERA";
    private static final String PREF_VENTA_DETALLE      = "PREF_VENTA_DETALLE";

    //endregion
    //region Base Preferencia
    // variable y constantes necesarias para el manejo de preferencia
    private final SharedPreferences mPrefs;
    private static SessionPreferences INSTANCE;
    private boolean mIsLoggedIn = false;

    public static SessionPreferences get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionPreferences(context);
        }
        return INSTANCE;
    }

    private SessionPreferences(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    //endregion

    //region codigo

    public void setProducto(int productoCodigo)
    {
        SharedPreferences.Editor objEditor = mPrefs.edit();
        objEditor.putInt(PREFS_PRODUCTO,productoCodigo + 1);
        objEditor.apply();
    }
    public int getProducto()
    {
        return mPrefs.getInt(PREFS_PRODUCTO,1);

    }

    public void setCliente(int clienteCodigo)
    {
        SharedPreferences.Editor objEditor = mPrefs.edit();
        objEditor.putInt(PREFS_CLIENTE,clienteCodigo+1);
        objEditor.apply();
    }
    public int getCliente()
    {
        return mPrefs.getInt(PREFS_CLIENTE,1);

    }

    public void setVentaCabecera(int ventaCabeceraCodigo)
    {
        SharedPreferences.Editor objEditor = mPrefs.edit();
        objEditor.putInt(PREF_VENTA_CABECERA,ventaCabeceraCodigo+1);
        objEditor.apply();
    }
    public int getVentaCabecera()
    {
        return mPrefs.getInt(PREF_VENTA_CABECERA,1);

    }

    public void setVentaDetalle(int ventaDetalleCodigo)
    {
        SharedPreferences.Editor objEditor = mPrefs.edit();
        objEditor.putInt(PREF_VENTA_DETALLE,ventaDetalleCodigo+1);
        objEditor.apply();
    }
    public int getVentaDetalle()
    {
        return mPrefs.getInt(PREF_VENTA_DETALLE,1);

    }

    public void setClave(String MovimientoCodigo)
    {
        SharedPreferences.Editor objEditor = mPrefs.edit();
        objEditor.putString(PREFS_CLAVE,MovimientoCodigo);
        objEditor.apply();
    }
    public String getClave()
    {
        return mPrefs.getString(PREFS_CLAVE,"123");
        //return mPrefs.getString(PREFS_CLAVE,"cristina");

    }

    public void setSession(Boolean abrirCerrar)
    {
        SharedPreferences.Editor objEditor = mPrefs.edit();
        objEditor.putBoolean(PREFS_LOGIN,abrirCerrar);
        objEditor.apply();
    }
    public Boolean getSession()
    {
        return mPrefs.getBoolean("PREFS_LOGIN",false);

    }
    //endregion
}
