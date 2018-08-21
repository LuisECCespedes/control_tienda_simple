package com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas;

public class VentaDetalleTabla {
    // contantes que definene el nombre de tabla y columna
    public static final String TABLA    = "venta_detalle";
    public static final String VD_ID    = "vd_id";
    public static final String VD_CANTIDAD  = "vd_cantidad";
    public static final String VD_PRECIO    = "vd_precio";
    public static final String VC_ID    = "vc_id";
    public static final String PROD_NOMBRE  = "prod_nombre";
    public static final String PROD_RUTA_FOTO   = "prod_ruta_foto";


    //creacion de tabla
    public static final String CREAR_TABLA_VENTA_DETALLE
            = "CREATE TABLE " + TABLA + "("
            + VD_ID + " INT PRIMARY KEY,"
            + VD_CANTIDAD + " INT ,"
            + VD_PRECIO + " NUMERIC ,"
            + VC_ID + " NT ,"
            + PROD_NOMBRE + " TEXT ,"
            + PROD_RUTA_FOTO + " TEXT "
            + ")";

    public static final String ELIMINAR_TABLA_VENTA_DETALLE = "DROP TABLE IF EXISTS" + TABLA + ";";
}
