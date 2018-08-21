package com.desarrollo.cursania.control_tienda_simple.adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.Cliente;

import java.util.List;

public class ClienteItemRecycler extends RecyclerView.Adapter<ClienteItemRecycler.ViewHolderClient>{
    ///objetos para el manejo general de la clase
    private List<Cliente> listCliente;
    private OnItemClickListener itemClickListener;

    //Constructor cargando los elementos necesarios
    public ClienteItemRecycler(List<Cliente> listCliente, OnItemClickListener listener) {
        this.listCliente = listCliente;
        this.itemClickListener = listener;
    }

    //Metodo que inicia el objeto luego del constructor
    @Override
    public ViewHolderClient onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_cliente, parent, false);
        return new ViewHolderClient(view);
    }

    //metodo con el que se cargan a la lista
    @Override
    public void onBindViewHolder(ViewHolderClient holder, int position) {
        holder.bind(listCliente.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listCliente.size();
    }

    //Interfaz para el manejo de los Click
    public interface OnItemClickListener
    {
        void onItemClick(Cliente cliente, int posicion);
    }

    //clase ViewHolder encargada de manejar la referencia con los controles
    class ViewHolderClient extends RecyclerView.ViewHolder {
        //objetos contenedores de controles
        ImageView celular, direccion, mail;
        TextView nombre;

        //cargamos el Holder
        ViewHolderClient(View itemView) {
            super(itemView);
            //tomamos la referencia a los controles y las asignamos a las variables creadas
            nombre      = itemView.findViewById(R.id.ricTvNombre);
            celular     = itemView.findViewById(R.id.ricIvCelular);
            direccion   = itemView.findViewById(R.id.ricIvDireccion);
            mail        = itemView.findViewById(R.id.ricIvEmail);
        }

        //metodo encargado de cargar el recycler con los datos de la lista
        void bind(final Cliente cliente, final OnItemClickListener listener){
            //cargamos los elementos
            nombre.setText(cliente.getClie_nombre());
            mail.setVisibility(cliente.getClie_email().length() > 0 ? View.VISIBLE : View.INVISIBLE);
            direccion.setVisibility(cliente.getClie_direccion().length() > 0 ? View.VISIBLE : View.INVISIBLE);
            celular.setVisibility(cliente.getClie_num_cel().length() > 0 ? View.VISIBLE : View.INVISIBLE);

            //cargamos el evento click, de los controles
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(cliente,getAdapterPosition());
                }
            });

        }
    }
}
