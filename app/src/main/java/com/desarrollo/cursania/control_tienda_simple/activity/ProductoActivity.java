package com.desarrollo.cursania.control_tienda_simple.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.adaptador.ProductoItemRecycler;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Producto;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductoActivity extends AppCompatActivity {

    //referenciamos al Recycler
    @BindView(R.id.apRvProducto)
    RecyclerView recyclerView;

    //variables para el manejo del recyclerView
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    //lista de ProductosConStock
    private List<Producto> listProductos = new ArrayList<>();

    @BindView(R.id.apEtBuscador)
    EditText buscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        ButterKnife.bind(this);

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

    @OnClick(R.id.apFabNuevo)
    void nuevoProducto(){
        irActivity(true, new Producto());
    }


    private void cargarLista() {
        Select.selectProducto(getApplicationContext(), listProductos, buscador.getText().toString());
        cargarRecyclerView(listProductos);
    }

    void cargarRecyclerView(List<Producto> paramProductoList){
        adapter = new ProductoItemRecycler(listProductos, new ProductoItemRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(Producto productoConStock, int posicion) {
                irActivity(false, productoConStock);
            }
        });

        //acomodar los datos de la lista
        recyclerView.setHasFixedSize(true);

        //animacion por defecto
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //cargar el layout
        recyclerView.setLayoutManager(layoutManager);

        //cargamos el adaptador
        recyclerView.setAdapter(adapter);
    }

    void irActivity(boolean bnuevo, Producto itemProducto){
        //Enviamos a la actividad Producto // Nueva Intencion
        Intent intent = new Intent(getApplicationContext(), ProductoDetalleActivity.class);
        intent.putExtra("itemProducto", itemProducto);
        intent.putExtra("bNuevo", bnuevo);
        //limpiamos la cola de actividades
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

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