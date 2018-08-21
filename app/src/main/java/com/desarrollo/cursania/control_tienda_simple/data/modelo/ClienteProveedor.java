package com.desarrollo.cursania.control_tienda_simple.data.modelo;

import com.desarrollo.cursania.control_tienda_simple.data.util.Metodos;

import java.io.Serializable;

public class ClienteProveedor implements Serializable {
    private int clie_prov_id;
    private String clie_prov_nombre;
    private String clie_prov_alias;
    private String clie_prov_num_cel_1;
    private String clie_prov_num_cel_2;
    private String clie_prov_email;
    private String clie_prov_direccion;
    private String clie_prov_info;
    private int clie_prov_tipo;
    private int clie_prov_eliminado;

    public int getClie_prov_id() {
        return clie_prov_id;
    }

    public void setClie_prov_id(int clie_prov_id) {
        this.clie_prov_id = clie_prov_id;
    }

    public String getClie_prov_nombre() {
        return clie_prov_nombre;
    }

    public void setClie_prov_nombre(String clie_prov_nombre) {
        this.clie_prov_nombre = clie_prov_nombre;
    }

    public String getClie_prov_alias() {
        return clie_prov_alias;
    }

    public void setClie_prov_alias(String clie_prov_alias) {
        this.clie_prov_alias = clie_prov_alias;
    }

    public String getClie_prov_num_cel_1() {
        return clie_prov_num_cel_1;
    }

    public void setClie_prov_num_cel_1(String clie_prov_num_cel_1) {
        this.clie_prov_num_cel_1 = clie_prov_num_cel_1;
    }

    public String getClie_prov_num_cel_2() {
        return clie_prov_num_cel_2;
    }

    public void setClie_prov_num_cel_2(String clie_prov_num_cel_2) {
        this.clie_prov_num_cel_2 = clie_prov_num_cel_2;
    }

    public String getClie_prov_email() {
        return clie_prov_email;
    }

    public void setClie_prov_email(String clie_prov_email) {
        this.clie_prov_email = clie_prov_email;
    }

    public String getClie_prov_direccion() {
        return clie_prov_direccion;
    }

    public void setClie_prov_direccion(String clie_prov_direccion) {
        this.clie_prov_direccion = clie_prov_direccion;
    }

    public String getClie_prov_info() {
        return clie_prov_info;
    }

    public void setClie_prov_info(String clie_prov_info) {
        this.clie_prov_info = clie_prov_info;
    }

    public int getClie_prov_tipo() {
        return clie_prov_tipo;
    }

    public void setClie_prov_tipo(int clie_prov_tipo) {
        this.clie_prov_tipo = clie_prov_tipo;
    }

    public int getClie_prov_eliminado() {
        return clie_prov_eliminado;
    }

    public void setClie_prov_eliminado(int clie_prov_eliminado) {
        this.clie_prov_eliminado = clie_prov_eliminado;
    }

    public ClienteProveedor() {
    }

    public ClienteProveedor(int clie_prov_id, String clie_prov_nombre, String clie_prov_alias, String clie_prov_num_cel_1, String clie_prov_num_cel_2, String clie_prov_email, String clie_prov_direccion, String clie_prov_info, int clie_prov_tipo, int clie_prov_eliminado) {
        this.clie_prov_id = clie_prov_id;
        this.clie_prov_nombre = clie_prov_nombre;
        this.clie_prov_alias = clie_prov_alias;
        this.clie_prov_num_cel_1 = clie_prov_num_cel_1;
        this.clie_prov_num_cel_2 = clie_prov_num_cel_2;
        this.clie_prov_email = clie_prov_email;
        this.clie_prov_direccion = clie_prov_direccion;
        this.clie_prov_info = clie_prov_info;
        this.clie_prov_tipo = clie_prov_tipo;
        this.clie_prov_eliminado = clie_prov_eliminado;
    }

    public String Componer(String caracter)
    {
        return Metodos.CadenasComponer(caracter,new String[]{
                String.valueOf(clie_prov_id),
                clie_prov_nombre,
                clie_prov_alias,
                clie_prov_num_cel_1,
                clie_prov_num_cel_2,
                clie_prov_email,
                clie_prov_direccion,
                clie_prov_info,
                String.valueOf(clie_prov_tipo)
        });

    }

    public ClienteProveedor Cliente(String cadena, String caracter)
    {
        return new ClienteProveedor(
                Integer.parseInt(Metodos.CadenasDescomponer(cadena,1,caracter)),
                Metodos.CadenasDescomponer(cadena,2,caracter),
                Metodos.CadenasDescomponer(cadena,3,caracter),
                Metodos.CadenasDescomponer(cadena,4,caracter),
                Metodos.CadenasDescomponer(cadena,5,caracter),
                Metodos.CadenasDescomponer(cadena,9,caracter),
                Metodos.CadenasDescomponer(cadena,7,caracter),
                Metodos.CadenasDescomponer(cadena,8,caracter),
                Integer.parseInt(Metodos.CadenasDescomponer(cadena,9,caracter)),
                Integer.parseInt(Metodos.CadenasDescomponer(cadena,10,caracter)));
    }
}