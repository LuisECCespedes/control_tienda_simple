package com.desarrollo.cursania.control_tienda_simple.adaptador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.ProductoVenta;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductoVentaItemRecycler extends RecyclerView.Adapter<ProductoVentaItemRecycler.ViewHolderProductoVenta> {
    ///objetos para el manejo general de la clase
    private List<ProductoVenta> listProducto;
    private TextView textView;
    private Context context;

    //Constructor cargando los elementos necesarios
    public ProductoVentaItemRecycler(List<ProductoVenta> listProducto, TextView textView) {
        this.listProducto = listProducto;
        this.textView = textView;
    }

    public void sumarItems() {

        Double aDouble = 0.0;
        for (ProductoVenta item : listProducto){
            aDouble += item.getProd_total();
        }
        textView.setText(String.valueOf(aDouble));
    }

    private void quitarItem(int position) {
        listProducto.remove(position);
        notifyItemRemoved(position);
        sumarItems();
    }

    private void editarItem(EditText cantidad, EditText precio, TextView total, int posicion) {
        if (cantidad.length() > 0){
            if (precio.length() > 0){
                int num1 = Integer.parseInt(cantidad.getText().toString());
                Double num2 = Double.parseDouble(precio.getText().toString());

                total.setText(String.valueOf(num1 * num2));
                listProducto.get(posicion).setProd_precio_venta(Double.parseDouble(precio.getText().toString()));
                listProducto.get(posicion).setProd_cantidad(Integer.parseInt(cantidad.getText().toString()));
                listProducto.get(posicion).setProd_total(Double.parseDouble(total.getText().toString()));
                sumarItems();
            }
        }
    }

    //Metodo que inicia el objeto luego del constructor
    @Override
    public ViewHolderProductoVenta onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_producto_venta,parent,false);
        return new ViewHolderProductoVenta(view);
    }

    //metodo con el que se cargan a la lista
    @Override
    public void onBindViewHolder(ViewHolderProductoVenta holder, int position) {
        holder.bind(listProducto.get(position));
    }

    @Override
    public int getItemCount() {
        return listProducto.size();
    }

    //clase ViewHolder encargada de manejar la referencia con los controles
    public class ViewHolderProductoVenta extends RecyclerView.ViewHolder {
        //objetos contenedores de controles
        ImageView img;
        TextView nombre, total;
        EditText precio, cantidad;
        Button quitar;

        //cargamos el Holder
        ViewHolderProductoVenta(View itemView) {
            super(itemView);
            quitar  = itemView.findViewById(R.id.ripvBtQuitar);
            img     = itemView.findViewById(R.id.ripvIvProducto);
            nombre  = itemView.findViewById(R.id.ripvTvNombre);
            cantidad= itemView.findViewById(R.id.ripvEtCantidad);
            precio  = itemView.findViewById(R.id.ripvEtPrecio);
            total   = itemView.findViewById(R.id.ripvTvPrecioTotal);

            //region changeListener
            cantidad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    editarItem(cantidad, precio, total, getAdapterPosition());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            precio.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    editarItem(cantidad, precio, total, getAdapterPosition());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            quitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quitarItem(getAdapterPosition());
                }
            });
            //endregio
        }

        //metodo encargado de cargar el recycler con los datos de la lista
        @SuppressLint("SetTextI18n")
        public void bind(final ProductoVenta productoVenta){

            nombre.setText(productoVenta.getProd_nombre());
            precio.setText(productoVenta.getProd_precio().toString());
            total.setText(productoVenta.getProd_precio().toString());

            if (productoVenta.getProd_ruta_foto().length() <= 1 || productoVenta.getProd_ruta_foto().isEmpty()) {
                //si no hay uma imagen seleccionada
                Picasso.get().load(R.drawable.caja_producto).into(img);
            }else {
                Picasso.get().load(productoVenta.getProd_ruta_foto())
                        .resize(65, 65)
                        .error(R.drawable.caja_producto_error)
                        .centerCrop()
                        .into(img);
            }

        }
    }
}