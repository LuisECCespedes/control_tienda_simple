package com.desarrollo.cursania.control_tienda_simple.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.adaptador.ProductoItemRecycler;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Producto;
import com.desarrollo.cursania.control_tienda_simple.data.util.Mensaje;
import com.desarrollo.cursania.control_tienda_simple.data.util.Metodos;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaActivity extends AppCompatActivity {
    //region declaracion de variables

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avRvProducto)
    RecyclerView recyclerView;

    //variables para el manejo del recyclerView
    RecyclerView.LayoutManager layoutManager;
    ProductoItemRecycler adapter;

    @BindView(R.id.avEtBuscador)
    EditText buscador;

    List<Producto> productoList = new ArrayList<>();

    List<Producto> productoListSelect = new ArrayList<>();

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // ocultar teclado al iniciar
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //cargamos con un nuevo objeto a nuestro layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());

        cargarLista();

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cargarLista();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.avFabNuevo)
    void nuevoCliente(){
        productoListSelect.clear();
        for (Producto productoItem : productoList){
            if (productoItem.getProd_seleccionado())productoListSelect.add(productoItem);
        }
        if(productoListSelect.size() > 0){
            irActivity();
        }else {
            new Mensaje(getApplicationContext()).mensajeToas("No ha seleccionado productos");
        }
    }

    private void cargarLista() {
        Select.selectProducto(getApplicationContext(), productoList, buscador.getText().toString());
        cargarRecyclerView(productoList);
    }

    void cargarRecyclerView(List<Producto> paramProductoList){

        adapter = new ProductoItemRecycler(paramProductoList, new ProductoItemRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(Producto productoConStock, int posicion) {
                productoList.get(posicion).setProd_seleccionado(!productoList.get(posicion).getProd_seleccionado());
                cargarRecyclerView(productoList);
            }
        });

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void filtrarProducto() {

    }

    void irActivity(){
        //Enviamos a la actividad Producto // Nueva Intencion
        Intent intent = new Intent(getApplicationContext(), VentaRealizarActivity.class);

        //limpiamos la cola de actividades
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("listaProducto", Metodos.convertirProductoListaTexto(productoListSelect));

        //ir a la actividad
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            cargarLista();
        }
    }
}