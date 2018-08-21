package com.desarrollo.cursania.control_tienda_simple.adaptador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Producto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductoItemRecycler extends RecyclerView.Adapter<ProductoItemRecycler.ViewHolderProducto>{
    ///objetos para el manejo general de la clase
    private List<Producto> listProducto;
    private OnItemClickListener itemClickListener;
    private Context context;

    //Constructor cargando los elementos necesarios
    public ProductoItemRecycler(List<Producto> listProducto, OnItemClickListener listener) {
        this.listProducto = listProducto;
        this.itemClickListener = listener;
    }

    //Metodo que inicia el objeto luego del constructor
    @Override
    public ViewHolderProducto onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_producto,parent,false);
        return new ViewHolderProducto(view);
    }

    //metodo con el que se cargan a la lista
    @Override
    public void onBindViewHolder(ViewHolderProducto holder, int position) {
        holder.bind(listProducto.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listProducto.size();
    }

    //Interfaz para el manejo de los Click
    public interface OnItemClickListener
    {
        void onItemClick(Producto productoConStock, int posicion);
    }

    //clase ViewHolder encargada de manejar la referencia con los controles
    public class ViewHolderProducto extends RecyclerView.ViewHolder {
        //objetos contenedores de controles
        CardView cardUno;
        ImageView img;
        TextView nombre, precio;
        LinearLayout linearLayout;

        //cargamos el Holder
        ViewHolderProducto(View itemView) {
            super(itemView);
            //tomamos la referencia a los controles y las asignamos a las variables creadas
            nombre  = itemView.findViewById(R.id.ripTvNombre);
            precio  = itemView.findViewById(R.id.ripTvPrecio);
            img     = itemView.findViewById(R.id.ripIvProducto);
            linearLayout    = itemView.findViewById(R.id.ripLlRecyclerProducto);

        }

        //metodo encargado de cargar el recycler con los datos de la lista
        @SuppressLint("SetTextI18n")
        public void bind(final Producto producto, final OnItemClickListener listener){

            //cargamos los elementos
            nombre.setText(producto.getProd_nombre());
            precio.setText(String.valueOf(producto.getProd_precio()));

            linearLayout.setBackground(ContextCompat.getDrawable(context ,
                    producto.getProd_seleccionado() ? R.color.productoSeleccion :
                                    R.color.blanco));

            if (producto.getProd_ruta_foto().length() <= 1 || producto.getProd_ruta_foto().isEmpty()) {
                //si no hay uma imagen seleccionada
                Picasso.get().load(R.drawable.caja_producto).into(img);
            }else {
                Picasso.get().load(producto.getProd_ruta_foto())
                        .resize(65, 65)
                        .error(R.drawable.caja_producto_error)
                        .centerCrop()
                        .into(img);
            }

            //cargamos el evento click, de los controles
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(producto,getAdapterPosition());
                }
            });

        }
    }
}