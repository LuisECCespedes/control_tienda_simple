package com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.desarrollo.cursania.control_tienda_simple.data.modelo.Cliente;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Producto;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaCabecera;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaDetalle;
import com.desarrollo.cursania.control_tienda_simple.data.util.Metodos;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.ConexionSqliteHelper;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.ClienteTabla;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.ProductoTabla;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.VentaCabeceraTabla;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.VentaDetalleTabla;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("Recycle")
public class Select {
    private static SQLiteDatabase db = null;
    private static ConexionSqliteHelper con = null;
    /*
    En esta clase crearemos los metodos que nos permitan interactuar con las tablas
    * */

    //region Listar tabla y lista por cliente
    private static List<Object> selectRegistros(Context context, String cTabla) {
        List<Object> listaObjectos = new ArrayList<>();
        /*
            Este metodo nos retornara una lista de objetos, los cuales seran los datos de la tabla, segun sea el caso
        * */
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        //cursor con los datos
        Cursor cLista = db.query(cTabla, null, null, null, null, null, null);

        // recorrer y cargar la lista
        while (cLista.moveToNext())
        {
            // segun el caso se acondiciona

            switch (cTabla) {
                case ClienteTabla.TABLA:   //tabla de cliente y proveedores
                    listaObjectos.add(
                            new Cliente(
                                    cLista.getInt(cLista.getColumnIndex(ClienteTabla.CLIE_ID)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NOMBRE)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NUM_CEL)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_EMAIL)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_DIRECCION))
                            )
                    );
                    break;

                case ProductoTabla.TABLA:    //tabla de productos
                    //Eliminado logico
                        listaObjectos.add(
                                new Producto(
                                        cLista.getInt(cLista.getColumnIndex(ProductoTabla.PROD_ID)),
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_NOMBRE)),
                                        cLista.getDouble(cLista.getColumnIndex(ProductoTabla.PROD_PRECIO)),
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_RUTA_FOTO)),
                                        false
                                )
                        );
                    break;
            }
        }
        //una ves cargada la lista la retornamos
        return listaObjectos;
    }

    public static Object buscaRegistro(Context context, int codigo, String cTabla){
        Object objCarga = null;
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();
        /*
        Este metodo retornara un objeto, el cual el equivalente a una fila de una tabla
        * */
        String column = cTabla.equals(ClienteTabla.TABLA) ? ClienteTabla.CLIE_ID:
                                cTabla.equals(ProductoTabla.TABLA) ? ProductoTabla.PROD_ID : ProductoTabla.PROD_ID;

        Cursor cLista = db.rawQuery("SELECT * FROM " + cTabla + " WHERE "+ column + " = ?", new String[] {codigo + ""});

        //posicionar al primer registro
        cLista.moveToFirst();

        // segun el caso se acondiciona
        switch (cTabla) {
            case ClienteTabla.TABLA:   //tabla de cliente y proveedores
                    objCarga = new Cliente(
                            cLista.getInt(cLista.getColumnIndex(ClienteTabla.CLIE_ID)),
                            cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NOMBRE)),
                            cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NUM_CEL)),
                            cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_EMAIL)),
                            cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_DIRECCION))
                    );
                break;

            case ProductoTabla.TABLA:
                objCarga =  new Producto(
                        cLista.getInt(cLista.getColumnIndex(ProductoTabla.PROD_ID)),
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_NOMBRE)),
                        cLista.getDouble(cLista.getColumnIndex(ProductoTabla.PROD_PRECIO)),
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_RUTA_FOTO)),
                        false);

                break;
        }
        return objCarga;
    }

    //endregion

    //region Listar tablas especificas
    // listar clientes
    public static void selectCliente(Context context, List<Cliente> lista, String buscar){
        lista.clear();

        List<Object> obj = Select.selectRegistros(context, ClienteTabla.TABLA);

        //recorrido y descarga
        for (Object item : obj) {
            Cliente _item = (Cliente)item;

            //filtro de busqueda
            if (buscar.length() > 0 ) {

                //si el nombre es es menor a la cadena no ingresa
                if (_item.getClie_nombre().length() >= buscar.length()){

                    //recortamos la cadena para igualarla con los datos de la tabla
                    String cadenaRecortada = _item.getClie_nombre().substring(0,buscar.length());

                    //si la cadena buscada es igual
                    if (buscar.equals(cadenaRecortada)) {
                        lista.add(_item);
                    }
                }
            }else
            {
                lista.add(_item);
            }
        }
    }

    public static void selectProducto(Context context, List<Producto> lista, String buscar){
        lista.clear();

        List<Object> obj = Select.selectRegistros(context, ProductoTabla.TABLA);

        //recorrido y descarga
        for (Object item : obj) {
            Producto _item = (Producto)item;

            //filtro de busqueda
            if (buscar.length() > 0 ) {

                //si el nombre es es menor a la cadena no ingresa
                if (_item.getProd_nombre().length() >= buscar.length()){

                    //recortamos la cadena para igualarla con los datos de la tabla
                    String cadenaRecortada = _item.getProd_nombre().substring(0,buscar.length());

                    //si la cadena buscada es igual
                    if (buscar.equals(cadenaRecortada)) {
                        lista.add(_item);
                    }
                }
            }else
            {
                lista.add(_item);
            }
        }
    }

    //region venta

    public static void selectVentaCabecera(Context context, List<VentaCabecera> lista, String fecha, String _busqueda) {

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        //cursor con los datos
        Cursor cLista;
         //cLista = db.query(VentaCabeceraTabla.TABLA, null, null, null, null, null, null);
        String query = Metodos.Concatenar(new Object[]{"select * from ", VentaCabeceraTabla.TABLA
                ," where " , VentaCabeceraTabla.VC_FECHA , " = ? " ," order by " + VentaCabeceraTabla.VC_FECHA , " , " , VentaCabeceraTabla.VC_HORA , " desc "});

        cLista = db.rawQuery(query, new String[]{String.valueOf(fecha)});

        // recorrer y cargar la lista
        while (cLista.moveToNext()) {

            VentaCabecera _item = new VentaCabecera(
                    cLista.getInt(cLista.getColumnIndex(VentaCabeceraTabla.VC_ID)),
                    cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.VC_FECHA)),
                    cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.VC_HORA)),
                    cLista.getDouble(cLista.getColumnIndex(VentaCabeceraTabla.VC_MONTO)),
                    cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.VC_COMENTARIO)),
                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NOMBRE)));

            //filtro de busqueda
            if (_busqueda.length() > 0 ) {

                //si el nombre es es menor a la cadena no ingresa
                if (_item.getClie_nombre().length() >= _busqueda.length()) {

                    //recortamos la cadena para igualarla con los datos de la tabla
                    String cadenaRecortada = _item.getClie_nombre().substring(0, _busqueda.length()).toLowerCase();

                    //si la cadena buscada es igual
                    if (cadenaRecortada.equals(_busqueda.toLowerCase())) {
                        lista.add(_item);
                    }
                }
            }else {
                lista.add(_item);
            }
        }
    }

    public static void selectVentaDetalle(Context context, List<VentaDetalle> lista, int vc_id) {

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        //cursor con los datos
        Cursor cLista;

        String query = Metodos.Concatenar(new Object[]{"select * from ", VentaDetalleTabla.TABLA
                ," where " , VentaDetalleTabla.VC_ID , " = ? " ," order by " + ProductoTabla.PROD_NOMBRE , " desc "});

        cLista = db.rawQuery(query, new String[]{String.valueOf(vc_id)});

        // recorrer y cargar la lista
        while (cLista.moveToNext())
        {
            lista.add(
                    new VentaDetalle(
                            cLista.getInt(cLista.getColumnIndex(VentaDetalleTabla.VD_ID)),
                            cLista.getInt(cLista.getColumnIndex(VentaDetalleTabla.VD_CANTIDAD)),
                            cLista.getDouble(cLista.getColumnIndex(VentaDetalleTabla.VD_PRECIO)),
                            cLista.getInt(cLista.getColumnIndex(VentaDetalleTabla.VC_ID)),
                            cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_NOMBRE)),
                            cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_RUTA_FOTO))
                    )
            );
        }
    }

    //endregion

    //region Backup y restore
    //creacion de backup
    public static void Backup(Context context) {
        String compresion = "";
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        for (String tabla : new String[] {ProductoTabla.TABLA }) {

            compresion += "/////" + tabla.toUpperCase() +"////"+ "\n";
            //cursor con los datos
            Cursor cLista = db.query(tabla, null, null, null, null, null, null);

            // recorrer y cargar la lista
            while (cLista.moveToNext())
            {
                // segun el caso se acondiciona
                switch (tabla) {
                    case ClienteTabla.TABLA:
                        compresion += new Cliente(
                                cLista.getInt(cLista.getColumnIndex(ClienteTabla.CLIE_ID)),
                                cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NOMBRE)),
                                cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NUM_CEL)),
                                cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_EMAIL)),
                                cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_DIRECCION))
                        ).Componer("|") + "\n";

                        break;

                    case ProductoTabla.TABLA:
                        compresion += new Producto(
                                cLista.getInt(cLista.getColumnIndex(ProductoTabla.PROD_ID)),
                                cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_NOMBRE)),
                                cLista.getDouble(cLista.getColumnIndex(ProductoTabla.PROD_PRECIO)),
                                cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_RUTA_FOTO))
                        ).Componer("|") + "\n";
                        break;
                }
            }
        }

        try {
            String nombre = "tienda.txt";
            File tarjeta = Environment.getExternalStorageDirectory();
            File file = new File(tarjeta.getAbsolutePath(),nombre);

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
            osw.write(compresion);
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion
}

