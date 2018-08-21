package com.desarrollo.cursania.control_tienda_simple.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.adaptador.ProductoVentaItemRecycler;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Cliente;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Producto;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.ProductoVenta;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaCabecera;
import com.desarrollo.cursania.control_tienda_simple.data.preferencia.SessionPreferences;
import com.desarrollo.cursania.control_tienda_simple.data.util.Mensaje;
import com.desarrollo.cursania.control_tienda_simple.data.util.Metodos;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Insert;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Select;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.VentaCabeceraTabla;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaRealizarActivity extends AppCompatActivity {
    //region declaracion de variables

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avrRvProducto)
    RecyclerView recyclerView;

    //variables para el manejo del recyclerView
    RecyclerView.LayoutManager layoutManager;
    ProductoVentaItemRecycler adapter;

    @BindView(R.id.avrActvCliente)
    AutoCompleteTextView cliente;

    @BindView(R.id.avrTietObservacion)
    EditText observacion;

    @BindView(R.id.avrTietTotal)
    EditText total;

    List<ProductoVenta> productoVentaList = new ArrayList<>();

    List<Cliente> clienteList = new ArrayList<>();

    Cliente clienteSeleccionado;

    boolean bModificado;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_realizar);

        ButterKnife.bind(this);

        // ocultar teclado al iniciar
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // ButterKnife
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        cargarBusqueda();

        //pantalla completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //cargamos el toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //si no se agrega y hay una mintencion
        if (getIntent().hasExtra("listaProducto")) {
            //cargamos la intencion del producto
            List<Producto> productoList = Metodos.convertirProductoTextoLista(getIntent().getStringExtra("listaProducto"));
            for (Producto itemProducto : productoList){
                productoVentaList.add(new ProductoVenta(itemProducto, itemProducto.getProd_precio(), 0, itemProducto.getProd_precio()));
            }
            cargarRecyclerView();
        }
    }

    void cargarRecyclerView(){

        adapter = new ProductoVentaItemRecycler(productoVentaList, total);

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        adapter.sumarItems();
    }

    @OnClick(R.id.avrBtVenta)
    void procederVenta(View view){
        if (productoVentaList.size() > 0 ){
            if(cliente.getText().length() > 0){
                if (validarCliente()) {
                    bModificado = true;
                    //generar venta cabecera
                    int codigoVenta = SessionPreferences.get(getApplicationContext()).getVentaCabecera();

                    Insert.registrar(getApplicationContext(), new VentaCabecera(codigoVenta,
                            Metodos.getFecha(),
                            Metodos.getHora(),
                            Double.parseDouble(total.getText().toString()),
                            observacion.getText().toString(),
                            clienteSeleccionado.getClie_nombre()), VentaCabeceraTabla.TABLA);

                    //generar venta detalle
                    Insert.registrarVentaDetalle(getApplicationContext(), productoVentaList, codigoVenta);
                    new Mensaje(getApplicationContext()).mensajeToas("Venta");
                    salirActivity();
                }
            }else{
                new Mensaje(getApplicationContext()).mensajeToas("Seleccione un cliente");
            }
        }else{
            new Mensaje(getApplicationContext()).mensajeToas("No hay productos en lista");

        }
    }

    private boolean validarCliente() {
        //segun el texto ingresado en el AutoCompleteTextView, lo comparamos con el texto
        for (Cliente itemCliente : clienteList){
            if (cliente.getText().toString().equals(itemCliente.getClie_nombre())){
                clienteSeleccionado = itemCliente;
                return true;
            }
        }
        new Mensaje(getApplicationContext()).mensajeToas("El cliente no esta registrado");
        return false;
    }


    private void cargarBusqueda() {

        // cargamos nuestro array
        Select.selectCliente(getApplicationContext(),clienteList,"");

        String[] inputs = new String[clienteList.size()];

        int i = 0;
        for (Cliente item : clienteList)
        {
            inputs[i] = item.getClie_nombre();
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,inputs);

        cliente.setAdapter(adapter);
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
        if (bModificado){
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK,intent);
            // llamamos a la actividad
        }
        finish();
    }
}
