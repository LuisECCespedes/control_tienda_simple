package com.desarrollo.cursania.control_tienda_simple.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.adaptador.VentaProductoHistorialItemRecycler;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaCabecera;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaDetalle;
import com.desarrollo.cursania.control_tienda_simple.data.util.Mensaje;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Delete;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaHistorialDetalleActivity extends AppCompatActivity {
    @BindView(R.id.avhdTietCliente)
    TextInputEditText cliente;

    @BindView(R.id.avhdTietFechaHora)
    TextInputEditText fechaHora;

    @BindView(R.id.avhdTietObservacion)
    TextInputEditText observacion;

    @BindView(R.id.avhdTietTotal)
    TextInputEditText total;

    @BindView(R.id.avhdRvProducto)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    VentaProductoHistorialItemRecycler adapter;

    List<VentaDetalle> ventaDetalleList = new ArrayList<>();

    //Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Boolean bCancelado = false;

    VentaCabecera ventaCabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_historial_detalle);

        //libreria butterKnife
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        //pantalla completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // ocultar teclado al iniciar
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //cargamos el toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        if (getIntent().hasExtra("itemVenta")){
            ventaCabecera = (VentaCabecera)getIntent().getSerializableExtra("itemVenta");
            cliente.setText(ventaCabecera.getClie_nombre());
            fechaHora.setText(ventaCabecera.getVc_fecha().concat(" ").concat(ventaCabecera.getVc_hora()));
            observacion.setText(ventaCabecera.getVc_coment().length() > 0 ? ventaCabecera.getVc_coment() :"Ninguna");
        }

        cargarDetalle();
    }

    @OnClick(R.id.avhdBtCancelar)
    void cancelarVenta(){

        new AlertDialog.Builder(this)
                .setTitle("Productos")
                .setMessage("¿Deseas eliminar este Producto?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Delete.eliminarVenta(getApplicationContext(), ventaCabecera.getVc_id(), ventaDetalleList);

                        new Mensaje(getApplicationContext()).mensajeToas("Venta Cancelada");
                        bCancelado = true;
                        salirActivity();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void cargarDetalle() {
        Select.selectVentaDetalle(getApplicationContext(), ventaDetalleList, ventaCabecera.getVc_id());
        cargarRecycleView();
    }

    private void cargarRecycleView() {
        adapter = new VentaProductoHistorialItemRecycler(ventaDetalleList, total);

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        adapter.sumarItems();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                salirActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);}
    }

    @Override
    public void onBackPressed() {
        salirActivity();
    }

    private void salirActivity() {
        if (bCancelado){
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK,intent);
            // llamamos a la actividad
        }
        finish();
    }
}
