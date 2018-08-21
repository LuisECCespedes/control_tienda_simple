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
import com.desarrollo.cursania.control_tienda_simple.adaptador.ClienteItemRecycler;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Cliente;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClienteActivity extends AppCompatActivity {

    @BindView(R.id.acRvCliente)
    RecyclerView recyclerView;

    @BindView(R.id.acEtBuscador)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;
    ClienteItemRecycler adapter;

    List<Cliente> clienteList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
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

    @OnClick(R.id.acFabNuevo)
    void nuevoCliente(){

        irActivity(true, new Cliente());
    }

    private void cargarLista() {
        Select.selectCliente(getApplicationContext(), clienteList, buscador.getText().toString());
        cargarRecyclerView(clienteList);
    }

    private void cargarRecyclerView(List<Cliente> paramClienteList) {

        adapter = new ClienteItemRecycler(paramClienteList, new ClienteItemRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(Cliente cliente, int posicion) {
                irActivity(false, cliente);
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

    void irActivity(boolean paramNuevo, Cliente paramCliente){
        //Enviamos a la actividad Producto // Nueva Intencion
        Intent intent = new Intent(getApplicationContext(), ClienteDetalleActivity.class);

        //parametros de envio
        intent.putExtra("bNuevo", paramNuevo);
        intent.putExtra("itemCliente", paramCliente);

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

