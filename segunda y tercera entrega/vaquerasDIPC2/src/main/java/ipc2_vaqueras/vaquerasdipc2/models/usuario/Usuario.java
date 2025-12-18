/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.usuario;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author helder
 */
public class Usuario {
    
    private int usuario_id;
    private String nombre;
    private String email;
    private String contraseña;
    private LocalDate fecha_nacimiento;
    private EnumUsuario rol;
    private String telefono;
    private byte[] avatar;
    private String pais;
    private int empresa_id;

    public Usuario(String nombre, String email, String contraseña, LocalDate fecha_nacimiento, EnumUsuario rol, String telefono, byte[] avatar, String pais) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = incriptarContraseña(contraseña);
        this.fecha_nacimiento = fecha_nacimiento;
        this.rol = rol;
        this.telefono = telefono;
        this.avatar = avatar;
        this.pais = pais;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public EnumUsuario getRol() {
        return rol;
    }

    public void setRol(EnumUsuario rol) {
        this.rol = rol;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(nombre)
                && StringUtils.isNotBlank(email)
                && StringUtils.isNotBlank(contraseña) 
                && fecha_nacimiento != null
                && rol != null; 
    }

    private String incriptarContraseña (String contraseña) {
        byte[] message = contraseña.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(message);
    }
    
}
