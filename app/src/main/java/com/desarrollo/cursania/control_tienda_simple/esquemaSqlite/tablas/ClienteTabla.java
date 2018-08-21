package com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas;

public class ClienteTabla {

    // contantes que definene el nombre de tabla y columna
    public static final String TABLA    = "cliente";
    public static final String CLIE_ID = "clie_id";
    public static final String CLIE_NOMBRE      = "clie_nombre";
    public static final String CLIE_NUM_CEL     = "clie_num_cel_1";
    public static final String CLIE_EMAIL       = "clie_email";
    public static final String CLIE_DIRECCION   = "clie_direccion";

    //creacion de tabla
    public static final String CREAR_TABLA_CLIENTE
            = "CREATE TABLE " + TABLA + "("
            + CLIE_ID + " INT PRIMARY KEY,"
            + CLIE_NOMBRE + " TEXT ,"
            + CLIE_NUM_CEL + " TEXT ,"
            + CLIE_EMAIL + " TEXT ,"
            + CLIE_DIRECCION + " TEXT"
            + ")";

    //eliminar la tabla si existe
    public static final String ELIMINAR_TABLA_CLIENTE = "DROP TABLE IF EXISTS" + TABLA + ";";

}
