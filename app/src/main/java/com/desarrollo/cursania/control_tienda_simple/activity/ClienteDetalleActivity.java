package com.desarrollo.cursania.control_tienda_simple.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Cliente;
import com.desarrollo.cursania.control_tienda_simple.data.preferencia.SessionPreferences;
import com.desarrollo.cursania.control_tienda_simple.data.util.Mensaje;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Delete;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Insert;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Update;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.ClienteTabla;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClienteDetalleActivity extends AppCompatActivity {
    @BindView(R.id.acdTietNombre)
    EditText nombre;

    @BindView(R.id.acdTietCelular)
    EditText celular;

    @BindView(R.id.acdTietEmail)
    EditText email;

    @BindView(R.id.acdTietDireccion)
    EditText direccion;

    boolean bNuevo;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.acdLlAgregar)
    LinearLayout agregar;

    @BindView(R.id.acdLlModificarEliminar)
    LinearLayout modificarEliminar;

    Cliente cliente;

    boolean bModificado = false;

    Mensaje mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalle);


        // ocultar teclado al iniciar
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // ButterKnife
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //pantalla completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //cargamos el toolbar y boton de regresar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mensaje = new Mensaje(getApplicationContext());

        //tomamos el parametro enviado por la intencion, este parametro nos indicara si es nuevo o se modificara un producto
        if (getIntent().hasExtra("bNuevo")){
            bNuevo  = getIntent().getBooleanExtra("bNuevo",true);
        }

        //segun el parametro se modificaran los controles
        agregar.setVisibility(bNuevo ? View.VISIBLE: View.INVISIBLE);
        modificarEliminar.setVisibility(bNuevo ? View.INVISIBLE:View.VISIBLE);

        //si no se agrega y hay una mintencion
        if (!bNuevo)
        {   //cargamos la intencion del producto
            cliente = (Cliente)getIntent().getSerializableExtra("itemCliente");
            cargarVista(cliente);
        }
        nombre.requestFocus();
    }

    private void cargarVista(Cliente paramCliente) {
        nombre.setText(paramCliente.getClie_nombre());
        celular.setText(paramCliente.getClie_num_cel());
        email.setText(paramCliente.getClie_email());
        direccion.setText(paramCliente.getClie_direccion());
        nombre.requestFocus();
    }

    @OnClick(R.id.acdBtAgregar)
    public void agregar(){
        if (nombre.getText().length() > 0){
            int codigo = SessionPreferences.get(getApplicationContext()).getCliente();
            Insert.registrar(getApplicationContext(),new Cliente(codigo,
                    nombre.getText().toString(),
                    celular.getText().toString(),
                    email.getText().toString(),
                    direccion.getText().toString()), ClienteTabla.TABLA);
            bModificado = true;
            mensaje.mensajeToasGuardar();
            cargarVista(new Cliente(0,"","","",""));
        }else {
            mensaje.mensajeToas("Ingrese un nombre");
            nombre.requestFocus();
        }
    }

    @OnClick(R.id.acdBtModificar)
    public void modifica(){
        if (nombre.getText().length() > 0){

            Update.Actualizar(getApplicationContext(),new Cliente(cliente.getClie_id(),
                    nombre.getText().toString(),
                    celular.getText().toString(),
                    email.getText().toString(),
                    direccion.getText().toString()), ClienteTabla.TABLA);
            bModificado = true;
            mensaje.mensajeToasGuardar();
            salirActivity();
        }else {
            mensaje.mensajeToas("Ingrese un nombre");
            nombre.requestFocus();
        }

    }

    @OnClick(R.id.acdBtEliminar)
    public void eliminar(){

        new AlertDialog.Builder(this)
                .setTitle("Marca de producto")
                .setMessage("¿Deseas eliminar marca?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        Delete.eliminar(getApplicationContext(),cliente.getClie_id(),ClienteTabla.TABLA);
                        bModificado = true;
                        mensaje.mensajeToasEliminar();
                        salirActivity();
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                salirActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        salirActivity();
    }

    private void salirActivity() {
        if(bModificado){
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK,intent);
        }
        finish();
    }
}
