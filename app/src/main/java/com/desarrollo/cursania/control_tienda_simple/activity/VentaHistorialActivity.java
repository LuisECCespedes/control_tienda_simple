package com.desarrollo.cursania.control_tienda_simple.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.adaptador.VentaHistorialItemRecycler;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaCabecera;
import com.desarrollo.cursania.control_tienda_simple.data.util.Metodos;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaHistorialActivity extends AppCompatActivity {

    int anio, mes, dia;
    String fecha = "";

    //recycler
    @BindView(R.id.avhRvVentaHistorial)
    RecyclerView recyclerView;

    @BindView(R.id.avhEtBuscador)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;
    VentaHistorialItemRecycler adapter;

    List<VentaCabecera> ventaCabeceraList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_historial);
        ButterKnife.bind(this);

        // ocultar teclado al iniciar
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        calendarioCargarVariables();

        //cargamos el layout
        layoutManager = new LinearLayoutManager(getApplicationContext());

        filtrarVentas();

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

    private void filtrarVentas() {
        cargarLista();
    }

    @OnClick(R.id.avhFabNuevo)
    public void buscarVenta(){
        muestraCalendario().show();
    }

    private void cargarLista() {
        ventaCabeceraList.clear();
        Select.selectVentaCabecera(getApplicationContext(), ventaCabeceraList, fecha, buscador.getText().toString());
        cargarReciclerViewVenta(ventaCabeceraList);
    }

    private void cargarReciclerViewVenta(List<VentaCabecera> paramVentaCabeceralist) {
        adapter = new VentaHistorialItemRecycler(paramVentaCabeceralist, new VentaHistorialItemRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(VentaCabecera ventaCabecera, int posicion) {
                irActivity(ventaCabecera);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private Dialog muestraCalendario() {
        return new DatePickerDialog(VentaHistorialActivity.this, calendario, anio, mes, dia);
    }

    private DatePickerDialog.OnDateSetListener calendario = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("DefaultLocale")
        @Override
        public void onDateSet(DatePicker vista, int nanio, int nmes, int ndia) {
            anio=nanio;
            mes=nmes;
            dia=ndia;
            fecha = nanio + "-" + String.format("%02d",(nmes + 1)) + "-" + String.format("%02d",ndia);

            filtrarVentas();
        }
    };
    //cargar las variables para el calendario
    private void calendarioCargarVariables() {
        final Calendar calendar = Calendar.getInstance();
        anio = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        fecha = Metodos.getFecha();
    }

    void irActivity(VentaCabecera paramVentaCabecera){
        //Enviamos a la actividad Producto // Nueva Intencion
        Intent intent = new Intent(getApplicationContext(), VentaHistorialDetalleActivity.class);

        //limpiamos la cola de actividades
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("itemVenta", paramVentaCabecera);

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