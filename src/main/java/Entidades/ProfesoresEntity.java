package Entidades;

import Entidades.MatriculaEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Profesores")
public class ProfesoresEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProfesor")
    private int idProfesor;

    @Column (name = "nombre")
    private String nombre;

    @Column (name = "Apellidos")
    private String apellidos;

    @Column (name="FechaNacimiento")
    private Date fechaNacimiento;

    @Column (name = "Antiguedad")
    private int antiguedad;

    @OneToMany
    @JoinColumn(name = "idProfesorado")
    private List<MatriculaEntity> matriculas;

    /**
     * Constructores
     */
    public ProfesoresEntity(){}

    public ProfesoresEntity(String nombre, String apellidos, Date fechaNacimiento, int antiguedad){
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.fechaNacimiento=fechaNacimiento;
        this.antiguedad=antiguedad;
    }

    /**
     * Getters y Setters
     */
    //Getters

    public int getIdProfesor() {
        return idProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public List<MatriculaEntity> getMatriculas() {
        return matriculas;
    }

    //Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public void setMatriculas(List<MatriculaEntity> matriculas) {
        this.matriculas = matriculas;
    }

    public String toString(){
        return getIdProfesor() + ": " +  getNombre() + ", " + getApellidos() + ", " +getFechaNacimiento();
    }
}
