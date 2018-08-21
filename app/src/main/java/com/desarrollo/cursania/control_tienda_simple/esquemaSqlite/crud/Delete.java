package com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaDetalle;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.ConexionSqliteHelper;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.ClienteTabla;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.ProductoTabla;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.VentaCabeceraTabla;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.VentaDetalleTabla;

import java.util.List;

public class Delete {

    private static SQLiteDatabase db = null;
    private static ConexionSqliteHelper con = null;
    /*
    Esta clase se encargara de eliminar los datos de las tablas
    Las tabla de productos y cliente necesitan tener una validacion. ya que no se las puede eliminar por motivos de relacionamiento
    En esta clase se realizara la validacion
    * */
    public static void eliminar(Context context, int codigo, String cTabla)
    {   //Eliminamos el dato
        con = new ConexionSqliteHelper(context);

        db =con.getWritableDatabase();

        // segun el caso se acondiciona
        switch (cTabla)
        {
            case ClienteTabla.TABLA:
                db.delete(ClienteTabla.TABLA, ClienteTabla.CLIE_ID + " = ?", new String[]{String.valueOf(codigo)});
                break;

            case ProductoTabla.TABLA:
                // validacion producto ausente en kardex

                //if (!comprobarExistenciaEnKardex(codigo)) {
                db.delete(ProductoTabla.TABLA, ProductoTabla.PROD_ID + " = ?", new String[]{String.valueOf(codigo)});
                //}
                break;

            case VentaCabeceraTabla.TABLA:
                // validacion producto ausente en kardex

                //if (!comprobarExistenciaEnKardex(codigo)) {
                db.delete(VentaCabeceraTabla.TABLA, VentaCabeceraTabla.VC_ID + " = ?", new String[]{String.valueOf(codigo)});
                //}
                break;

            case VentaDetalleTabla.TABLA:
                // validacion producto ausente en kardex

                //if (!comprobarExistenciaEnKardex(codigo)) {
                db.delete(VentaDetalleTabla.TABLA, VentaDetalleTabla.VD_ID + " = ?", new String[]{String.valueOf(codigo)});
                //}
                break;
        }

    }

    public static void eliminarVenta(Context context, int codigo, List<VentaDetalle> ventaProductos){

        //eliminar de cabecera
        eliminar(context, codigo, VentaCabeceraTabla.TABLA);

        //eliminar de detalle
        for(VentaDetalle item : ventaProductos){
            eliminar(context, item.getVc_id(), VentaDetalleTabla.TABLA);
        }
    }

    public static void eliminarTodo(Context context){
        //Eliminamos el dato
        con = new ConexionSqliteHelper(context);

        db =con.getWritableDatabase();

        // segun el caso se acondiciona
        db.delete(ClienteTabla.TABLA, ClienteTabla.CLIE_ID + " = ?", new String[]{ClienteTabla.CLIE_ID});

        // validacion producto ausente en kardex
        db.delete(ProductoTabla.TABLA, ProductoTabla.PROD_ID + " = ?", new String[]{ProductoTabla.PROD_ID});
    }
}
