package com.desarrollo.cursania.control_tienda_simple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.desarrollo.cursania.control_tienda_simple.activity.ClienteActivity;
import com.desarrollo.cursania.control_tienda_simple.activity.ProductoActivity;
import com.desarrollo.cursania.control_tienda_simple.activity.VentaActivity;
import com.desarrollo.cursania.control_tienda_simple.activity.VentaHistorialActivity;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.ConexionSqliteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ConexionSqliteHelper conn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        conn = new ConexionSqliteHelper(MenuActivity.this);

    }

    @OnClick(R.id.amImBtProducto)
    public void clickProducto(){
        irActivity(ProductoActivity.class,1);
    }

    @OnClick(R.id.amImBttCliente)
    public void clickCliente(){
        irActivity(ClienteActivity.class,2);
    }

    @OnClick(R.id.amImBtVenta)
    public void clickVenta(){
        irActivity(VentaActivity.class,3);
    }

    @OnClick(R.id.amImBtVentaHistorial)
    public void clickVentaHistorial(){
        irActivity(VentaHistorialActivity.class,4);
    }

    void irActivity(Class<?> aClass, int identificador){
        //Enviamos a la actividad Producto // Nueva Intencion
        Intent intent = new Intent(getApplicationContext(), aClass);

        //limpiamos la cola de actividades
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //ir a la actividad
        startActivityForResult(intent,identificador);
    }

    @Override
    protected void onDestroy() {
        conn.close();
        super.onDestroy();
    }
}
