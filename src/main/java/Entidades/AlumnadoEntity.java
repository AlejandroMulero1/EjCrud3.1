package Entidades;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "Alumnado")
public class AlumnadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlumnado")
    private int idAlumnado;

    @Column(name = "Nombre")
    private String nombre;

    @Column (name = "Apellidos")
    private String apellidos;

    @Column (name = "FechaNacimiento")
    private Date fechaNacimiento;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAlumnado")
    private List<MatriculaEntity> matriculas;

    /**
     * Constructores
     */
    public AlumnadoEntity(){}

    public AlumnadoEntity(String nombre, String apellidos, Date fechaNacimiento){

        this.nombre=nombre;
        this.apellidos=apellidos;
        this.fechaNacimiento=fechaNacimiento;
    }

    /**
     * Getters y Setters
     */
    //Getters

    public int getId() {
        return idAlumnado;
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

    public void setMatriculas(List<MatriculaEntity> matriculas) {
        this.matriculas = matriculas;
    }

    @Override
    public String toString(){
        return getId() + ": " +  getNombre() + ", " + getApellidos() + ", " +getFechaNacimiento();
    }
}
