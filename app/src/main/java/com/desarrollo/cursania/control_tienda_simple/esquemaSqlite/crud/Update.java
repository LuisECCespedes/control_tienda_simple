package com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.desarrollo.cursania.control_tienda_simple.data.modelo.Cliente;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Producto;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.ConexionSqliteHelper;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.ClienteTabla;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.ProductoTabla;

public class Update {

    public  static void Actualizar(Context context, Object ObjParam, String cTabla)
    {
        ConexionSqliteHelper con = new ConexionSqliteHelper(context);

        SQLiteDatabase db = con.getWritableDatabase();

        // segun el caso se acondiciona
        switch (cTabla)
        {
            case ClienteTabla.TABLA:
                Cliente clie = (Cliente)ObjParam;

                ContentValues values1 = new ContentValues();
                values1.put(ClienteTabla.CLIE_ID,clie.getClie_id());
                values1.put(ClienteTabla.CLIE_NOMBRE,clie.getClie_nombre());
                values1.put(ClienteTabla.CLIE_NUM_CEL,clie.getClie_num_cel());
                values1.put(ClienteTabla.CLIE_EMAIL,clie.getClie_email());
                values1.put(ClienteTabla.CLIE_DIRECCION,clie.getClie_direccion());

                db.update(ClienteTabla.TABLA, values1, ClienteTabla.CLIE_ID + " = ?" , new String[] {String.valueOf(clie.getClie_id())});

                break;

            case ProductoTabla.TABLA:

                Producto prod = (Producto)ObjParam;

                ContentValues values = new ContentValues();
                values.put(ProductoTabla.PROD_NOMBRE,prod.getProd_nombre());
                values.put(ProductoTabla.PROD_PRECIO,prod.getProd_precio());
                values.put(ProductoTabla.PROD_RUTA_FOTO,prod.getProd_ruta_foto());

                db.update(ProductoTabla.TABLA,values, ProductoTabla.PROD_ID + " = ?", new String[] {String.valueOf(prod.getProd_id())});

                break;
        }

    }
}
