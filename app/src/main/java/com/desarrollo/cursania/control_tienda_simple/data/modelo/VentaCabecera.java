package com.desarrollo.cursania.control_tienda_simple.data.modelo;

import com.desarrollo.cursania.control_tienda_simple.data.util.Metodos;

import java.io.Serializable;

public class VentaCabecera implements Serializable {
    private int vc_id;
    private String vc_fecha;
    private String vc_hora;
    private Double vc_monto;
    private String vc_coment;
    private String clie_nombre;

    public int getVc_id() {
        return vc_id;
    }

    public void setVc_id(int vc_id) {
        this.vc_id = vc_id;
    }

    public String getVc_fecha() {
        return vc_fecha;
    }

    public void setVc_fecha(String vc_fecha) {
        this.vc_fecha = vc_fecha;
    }

    public String getVc_hora() {
        return vc_hora;
    }

    public void setVc_hora(String vc_hora) {
        this.vc_hora = vc_hora;
    }

    public Double getVc_monto() {
        return vc_monto;
    }

    public void setVc_monto(Double vc_monto) {
        this.vc_monto = vc_monto;
    }

    public String getVc_coment() {
        return vc_coment;
    }

    public void setVc_coment(String vc_coment) {
        this.vc_coment = vc_coment;
    }

    public String getClie_nombre() {
        return clie_nombre;
    }

    public void setClie_nombre(String clie_nombre) {
        this.clie_nombre = clie_nombre;
    }

    public VentaCabecera() {
    }

    public VentaCabecera(int vc_id, String vc_fecha, String vc_hora, Double vc_monto, String vc_coment, String clie_nombre) {
        this.vc_id = vc_id;
        this.vc_fecha = vc_fecha;
        this.vc_hora = vc_hora;
        this.vc_monto = vc_monto;
        this.vc_coment = vc_coment;
        this.clie_nombre = clie_nombre;
    }

    public String componer(String caracter)
    {
        return Metodos.CadenasComponer(caracter, new Object[]{vc_id, vc_fecha, vc_hora, vc_monto, clie_nombre});
    }

    public VentaCabecera ventaCabecera(String cadena, String caracter)
    {
        return new VentaCabecera(  Integer.parseInt(Metodos.CadenasDescomponer(cadena,1,caracter)),
                Metodos.CadenasDescomponer(cadena,2,caracter),
                Metodos.CadenasDescomponer(cadena,3,caracter),
                Double.parseDouble(Metodos.CadenasDescomponer(cadena,4,caracter)),
                Metodos.CadenasDescomponer(cadena,5,caracter),
                Metodos.CadenasDescomponer(cadena,6,caracter));
    }
}
