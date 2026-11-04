package cl.voltech.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.OffsetDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "run", nullable = false, unique = true, length = 100)
    private String run;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String contraseña;

    @Column(name = "calle", nullable = false, length = 100)
    private String calle;

    @Column(name = "depto", nullable = false, length = 100)
    private String depto;

    @Column(name = "rol", nullable = true, length = 20)
    @ColumnDefault("'cliente'")
    private String rol = "cliente";

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = true, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime fechaCreacion;
}
