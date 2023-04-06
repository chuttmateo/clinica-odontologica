package com.ClinicaOdontologicaCHUTT.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "pacientes")
public class Paciente {
    //atrtibutos de la clase
    //agregar inyeccion de dependencias, long por integer.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String cedula;
    @Column
    private LocalDate fechaDeIngreso; //usamos el localdate de java .time pq es mas actual.
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id",referencedColumnName = "id") //es el id de dom
    private Domicilio domicilio; //deberia actuar como una columna vinculante y no como una clave
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL) //Elimino un paciente y elimino todos los turnos asociados
    @JsonIgnore
    private Set<Turno> turnos;
    @Column(unique = true)//unique= true, igual seria a nivel base, cuando tenias claves candidatas.
    private String email;

    public Set<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Set<Turno> turnos) {
        this.turnos = turnos;
    }

    public Paciente(String nombre, String apellido, String cedula, LocalDate fechaDeIngreso, Domicilio domicilio, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.fechaDeIngreso = fechaDeIngreso;
        this.domicilio = domicilio;
        this.email=email;
    }
    //generamos dos constructores para poder recibir el id de la bdd y el otro para recuperarlo.
    public Paciente(Long id, String nombre, String apellido, String cedula, LocalDate fechaDeIngreso, Domicilio domicilio, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.fechaDeIngreso = fechaDeIngreso;
        this.domicilio = domicilio;
        this.email=email;
    }
    public Paciente(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFechaDeIngreso() {
        return fechaDeIngreso;
    }

    public void setFechaDeIngreso(LocalDate fechaDeIngreso) {
        this.fechaDeIngreso = fechaDeIngreso;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
