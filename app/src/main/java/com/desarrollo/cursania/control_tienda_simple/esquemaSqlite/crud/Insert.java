package com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.desarrollo.cursania.control_tienda_simple.data.modelo.Cliente;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Producto;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.ProductoVenta;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaCabecera;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaDetalle;
import com.desarrollo.cursania.control_tienda_simple.data.preferencia.SessionPreferences;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.ConexionSqliteHelper;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.ClienteTabla;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.ProductoTabla;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.VentaCabeceraTabla;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.VentaDetalleTabla;

import java.util.List;

public class Insert {

    /*
    Esta clase nos servira para guardar los datos en las tablas
    * */
    public  static void registrar(Context context, Object objParam, String cTabla)
    {
        ConexionSqliteHelper con = new ConexionSqliteHelper(context);

        SQLiteDatabase db = con.getWritableDatabase();

        ContentValues values = new ContentValues();

        int codigoDetalle;
        // segun el caso se acondiciona
        switch (cTabla)
        {
            case ClienteTabla.TABLA:
                Cliente clie = (Cliente)objParam;

                values.put(ClienteTabla.CLIE_ID, clie.getClie_id());
                values.put(ClienteTabla.CLIE_NOMBRE, clie.getClie_nombre());
                values.put(ClienteTabla.CLIE_NUM_CEL, clie.getClie_num_cel());
                values.put(ClienteTabla.CLIE_EMAIL, clie.getClie_email());
                values.put(ClienteTabla.CLIE_DIRECCION, clie.getClie_direccion());

                db.insert(ClienteTabla.TABLA,  ClienteTabla.CLIE_ID,  values);
                SessionPreferences.get(context).setCliente(clie.getClie_id());
                break;

            case ProductoTabla.TABLA:

                Producto prod = (Producto)objParam;

                values.put(ProductoTabla.PROD_ID, prod.getProd_id());
                values.put(ProductoTabla.PROD_NOMBRE, prod.getProd_nombre());
                values.put(ProductoTabla.PROD_PRECIO, prod.getProd_precio());
                values.put(ProductoTabla.PROD_RUTA_FOTO, prod.getProd_ruta_foto());

                db.insert(ProductoTabla.TABLA, ProductoTabla.PROD_ID,  values);
                SessionPreferences.get(context).setProducto(prod.getProd_id());
                break;

            case VentaCabeceraTabla.TABLA:

                VentaCabecera vc = (VentaCabecera)objParam;

                values.put(VentaCabeceraTabla.VC_ID, vc.getVc_id());
                values.put(VentaCabeceraTabla.VC_FECHA, vc.getVc_fecha());
                values.put(VentaCabeceraTabla.VC_HORA, vc.getVc_hora());
                values.put(VentaCabeceraTabla.VC_MONTO, vc.getVc_monto());
                values.put(VentaCabeceraTabla.VC_COMENTARIO, vc.getVc_coment());
                values.put(VentaCabeceraTabla.CLIE_NOMBRE, vc.getClie_nombre());

                db.insert(VentaCabeceraTabla.TABLA,  VentaCabeceraTabla.VC_ID,  values);
                SessionPreferences.get(context).setVentaCabecera(vc.getVc_id());
                break;

            case VentaDetalleTabla.TABLA:
                VentaDetalle vd = (VentaDetalle)objParam;

                codigoDetalle = SessionPreferences.get(context).getVentaDetalle();
                values.put(VentaDetalleTabla.VD_ID, codigoDetalle);
                values.put(VentaDetalleTabla.VC_ID, vd.getVc_id());
                values.put(VentaDetalleTabla.VD_CANTIDAD, vd.getVd_cantidad());
                values.put(VentaDetalleTabla.VD_PRECIO, vd.getVd_precio());
                values.put(VentaDetalleTabla.PROD_NOMBRE, vd.getProd_nombre());
                values.put(VentaDetalleTabla.PROD_RUTA_FOTO, vd.getProd_ruta_foto());

                db.insert(VentaDetalleTabla.TABLA, VentaDetalleTabla.VD_ID,  values);
                SessionPreferences.get(context).setVentaDetalle(codigoDetalle);
                break;
        }
    }

    public static void registrarVentaDetalle(Context context , List<ProductoVenta> objVenta, int vc_id) {

        for (ProductoVenta item : objVenta){
            registrar(context, new VentaDetalle(0,
                    item.getProd_cantidad(),
                    item.getProd_precio(),
                    vc_id,
                    item.getProd_nombre(),
                    item.getProd_ruta_foto()), VentaDetalleTabla.TABLA);
        }
    }
}
