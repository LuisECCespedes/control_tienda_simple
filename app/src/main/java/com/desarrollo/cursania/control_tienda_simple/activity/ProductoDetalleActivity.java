package com.desarrollo.cursania.control_tienda_simple.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Producto;
import com.desarrollo.cursania.control_tienda_simple.data.preferencia.SessionPreferences;
import com.desarrollo.cursania.control_tienda_simple.data.util.Controles;
import com.desarrollo.cursania.control_tienda_simple.data.util.Mensaje;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Delete;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Insert;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.crud.Update;
import com.desarrollo.cursania.control_tienda_simple.esquemaSqlite.tablas.ProductoTabla;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductoDetalleActivity extends AppCompatActivity {

    //region variables
    //Controles
    // TextInputEditText
    @BindView(R.id.apdTietNombre)
    TextInputEditText nombre;

    @BindView(R.id.apdTietPrecio)
    TextInputEditText precio;

    @BindView(R.id.apdIvProducto)
    ImageView foto;

    //Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.apdLlAgregar)
    LinearLayout agregar;

    @BindView(R.id.apdLlModificarEliminar)
    LinearLayout modificar;

    //variable para el control
    boolean bNuevo = true;
    private Producto paramProducto;

    //variables seleccion de imagen
    private final int SELECT_PICTURE = 200;
    private final String TEMPORAL_PICTURE_NAME = "temporal.jpg";
    private final String APP_DIRECTORY = "myPictureApp/";
    private final String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private final int PHOTO_CODE = 100;
    private String pathUri = "";

    //variables para los campos obligatorios
    List<TextInputEditText> listTextInputEditText= new ArrayList<>();

    String[] listMensaje = new String[2];

    private boolean bModificado = false;

    Mensaje mensaje;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);

        // ButterKnife
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //pantalla completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // ocultar teclado al iniciar
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //cargamos el toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mensaje = new Mensaje(getApplicationContext());

        cargarControlesComprobar();

        //tomamos el parametro enviado por la intencion, este parametro nos indicara si es nuevo o se modificara un producto
        if (getIntent().hasExtra("bNuevo")){
            bNuevo = getIntent().getBooleanExtra("bNuevo",true);
        }

        agregar.setVisibility(bNuevo ? View.VISIBLE:View.INVISIBLE);
        modificar.setVisibility(bNuevo ? View.INVISIBLE:View.VISIBLE);

        if (!bNuevo){
            paramProducto = (Producto) getIntent().getSerializableExtra("itemProducto");
            cargarVista(paramProducto);
        }
        nombre.requestFocus();
    }


    //region Click
    //buscar una imagen en la galeria
    @OnClick(R.id.apdBtGaleria)
    public void galeriaFoto(){
        //seleccion de imagen
        selecImagen();
    }

    //quitar la imagen
    @OnClick(R.id.apdBtQuitar)
    public void quitarFoto(){
        //Picaso para la carga
        Picasso.get().load(R.drawable.caja_producto)
                .resize(180,160)
                .centerCrop()
                .into(foto);
        pathUri="";
    }

    //agregar producto
    @OnClick(R.id.apdBtAgregar)
    public void agregarProducto(){
        //controles con campo obligatorio
        if(!Controles.TextInputEditTextVacio(getApplicationContext(), listTextInputEditText, listMensaje)){
            //registrar el producto
            int codigoProducto = SessionPreferences.get(getApplicationContext()).getProducto();
            Insert.registrar(getApplicationContext(),new Producto(codigoProducto,
                            nombre.getText().toString(),
                            Double.parseDouble(precio.getText().toString()),
                            pathUri),
                    ProductoTabla.TABLA);

            mensaje.mensajeToasGuardar();
            bModificado = true;
            nuevoProducto();
            nombre.requestFocus();
        }
    }

    @OnClick(R.id.apdBtModificar)
    public void modificarProducto(){
        if(!Controles.TextInputEditTextVacio(getApplicationContext(), listTextInputEditText, listMensaje)){

            Update.Actualizar(getApplicationContext(),new Producto(paramProducto.getProd_id(),
                    nombre.getText().toString(),
                    Double.parseDouble(precio.getText().toString()),
                    pathUri),ProductoTabla.TABLA);

            mensaje.mensajeToasGuardar();
            bModificado = true;
            salirActivity();
        }
    }

    @OnClick(R.id.apdBtEliminar)
    public void eliminarProducto(View view){

        new AlertDialog.Builder(this)
                .setTitle("Productos")
                .setMessage("¿Deseas eliminar este Producto?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Delete.eliminar(getApplicationContext(), paramProducto.getProd_id(), ProductoTabla.TABLA);

                        mensaje.mensajeToasEliminar();
                        bModificado = true;
                        salirActivity();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    //endregion

    private void cargarVista(Producto paramProducto) {
        nombre.setText(paramProducto.getProd_nombre());
        precio.setText(String.valueOf(paramProducto.getProd_precio()));

        if (paramProducto.getProd_ruta_foto().length() <= 1 || paramProducto.getProd_ruta_foto().isEmpty()) {
            //si no hay uma imagen seleccionada
            Picasso.get().load(R.drawable.caja_producto)
                    .resize(180,160)
                    .error(R.drawable.caja_producto_error)
                    .centerCrop()
                    .into(foto);
        }else
        {
            Picasso.get().load(paramProducto.getProd_ruta_foto())
                    .resize(180,160)
                    .error(R.drawable.caja_producto_error)
                    .centerCrop()
                    .into(foto);
            //imProducto.setImageURI(Uri.parse(objProducto.getRutaFoto()));
            pathUri = paramProducto.getProd_ruta_foto();
        }
    }

    private void nuevoProducto() {
        nombre.setText("");
        precio.setText("");
        pathUri = "";

        Picasso.get().load(R.drawable.caja_producto)
                .resize(180,160)
                .error(R.drawable.caja_producto_error)
                .centerCrop()
                .into(foto);
    }

    void cargarControlesComprobar(){
        listTextInputEditText.add(nombre);
        listTextInputEditText.add(precio);
        listMensaje[0] = "Ingrese el nombre del producto";
        listMensaje[1] = "Ingrese el precio";
    }

    //region Imagen del producto
    public void selecImagen(){
        Intent selectImagen = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selectImagen.setType("image/*");
        startActivityForResult(Intent.createChooser(selectImagen,"Seleciona app de imagen"),SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK)
                {
                    String dir = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                    decodeBitmap(dir);
                }
                break;
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK)
                {
                    Uri path = data.getData();
                    assert path != null;
                    pathUri = path.toString();

                    Picasso.get().load(pathUri)
                            .resize(180,160)
                            .centerCrop()
                            .into(foto);
                }
        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        foto.setImageBitmap(bitmap);
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
    //endregion
}
