package com.desarrollo.cursania.control_tienda_simple.adaptador;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desarrollo.cursania.control_tienda_simple.R;
import com.desarrollo.cursania.control_tienda_simple.data.modelo.VentaCabecera;

import java.util.List;

public class VentaHistorialItemRecycler extends RecyclerView.Adapter<VentaHistorialItemRecycler.ViewHolderVentaHistorial>{
    private List<VentaCabecera> ventaCabeceraList;
    OnItemClickListener itemClickListener;

    public VentaHistorialItemRecycler(List<VentaCabecera> ventaCabeceraList, OnItemClickListener itemClickListener) {
        this.ventaCabeceraList = ventaCabeceraList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolderVentaHistorial onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_venta_historial, parent, false);
        return new ViewHolderVentaHistorial(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderVentaHistorial holder, int position) {
        holder.bind(ventaCabeceraList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return ventaCabeceraList.size();
    }

    //Interfaz para el manejo de los click
    public interface OnItemClickListener{
        void onItemClick(VentaCabecera ventaCabecera, int posicion);
    }

    class ViewHolderVentaHistorial extends RecyclerView.ViewHolder{
        TextView cliente, fechaHora, montoTotal;

        public ViewHolderVentaHistorial(View itemView) {
            super(itemView);
            cliente = itemView.findViewById(R.id.rivhTvCliente);
            fechaHora = itemView.findViewById(R.id.rivhTvFechaHora);
            montoTotal = itemView.findViewById(R.id.rivhTvTotal);
        }
        @SuppressLint("SetTextI18n")
        void bind(final VentaCabecera ventaCabecera, final OnItemClickListener onItemClickListener){
            cliente.setText(ventaCabecera.getClie_nombre());
            fechaHora.setText(" " + ventaCabecera.getVc_fecha().concat( " ").concat(ventaCabecera.getVc_hora()));
            montoTotal.setText(" " + String.valueOf(ventaCabecera.getVc_monto()));

            //cargamos el click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(ventaCabecera, getAdapterPosition());
                }
            });
        }
    }

}
