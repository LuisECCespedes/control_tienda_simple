package com.desarrollo.cursania.control_tienda_simple.adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaDetalle;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VentaProductoHistorialItemRecycler extends RecyclerView.Adapter<VentaProductoHistorialItemRecycler.ViewHolderVentaProductoHistorialItemRecycler>{
    ///objetos para el manejo general de la clase
    private List<VentaDetalle> ventaDetalleList;
    private TextView textView;

    //Constructor cargando los elementos necesarios
    public VentaProductoHistorialItemRecycler(List<VentaDetalle> ventaDetalleList, TextView textView) {
            this.ventaDetalleList = ventaDetalleList;
            this.textView = textView;
    }

    //Metodo que inicia el objeto luego del constructor
    @Override
    public ViewHolderVentaProductoHistorialItemRecycler onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_producto_venta_historial, parent, false);
            return new ViewHolderVentaProductoHistorialItemRecycler(view);
    }


    //metodo con el que se cargan a la lista
    @Override
    public void onBindViewHolder(ViewHolderVentaProductoHistorialItemRecycler holder, int position) {
        holder.bind(ventaDetalleList.get(position));
    }

    @Override
    public int getItemCount() {
            return ventaDetalleList.size();
    }

    public void sumarItems() {

            Double aDouble = 0.0;
            for (VentaDetalle item : ventaDetalleList) {
                aDouble += (item.getVd_precio() * item.getVd_cantidad());
            }
            textView.setText(String.valueOf(aDouble));
    }

    //clase ViewHolder encargada de manejar la referencia con los controles
    public class ViewHolderVentaProductoHistorialItemRecycler extends RecyclerView.ViewHolder {
        //objetos contenedores de controles
        ImageView img;
        TextView nombre, cantidad, precio, total;

        //cargamos el Holder
        public ViewHolderVentaProductoHistorialItemRecycler(View itemView) {
            super(itemView);
            //tomamos la referencia a los controles y las asignamos a las variables creadas
            nombre = itemView.findViewById(R.id.ripvhTvNombre);
            cantidad = itemView.findViewById(R.id.ripvhTvCantidad);
            precio = itemView.findViewById(R.id.ripvhTvPrecio);
            total = itemView.findViewById(R.id.ripvhTvTotal);
            img = itemView.findViewById(R.id.ripvhCivProducto);
        }

        //metodo encargado de cargar el recycler con los datos de la lista
        public void bind(VentaDetalle ventaProducto){
            nombre.setText(ventaProducto.getProd_nombre());
            cantidad.setText(String.valueOf(ventaProducto.getVd_cantidad()));
            precio.setText(String.valueOf(ventaProducto.getVd_precio()));
            total.setText(String.valueOf((ventaProducto.getVd_precio() * ventaProducto.getVd_cantidad())));

            if (ventaProducto.getProd_ruta_foto().length() <= 1 || ventaProducto.getProd_ruta_foto().isEmpty()) {
                //si no hay uma imagen seleccionada
                Picasso.get().load(R.drawable.caja_producto).into(img);
            }else {
                Picasso.get().load(ventaProducto.getProd_ruta_foto())
                        .resize(65, 65)
                        .error(R.drawable.caja_producto_error)
                        .centerCrop()
                        .into(img);
            }
        }
    }
}