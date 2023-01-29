package Entidades;

import Entidades.AlumnadoEntity;

import javax.persistence.*;

@Entity
@Table (name = "Matricula")
public class MatriculaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idMatricula;

    @Column (name = "Asignatura")
    private String asignatura;

    @Column (name = "Curso")
    private int curso;

    @ManyToOne
    @JoinColumn(name="idAlumnado")
    private AlumnadoEntity alumno;

    @ManyToOne
    @JoinColumn(name = "idProfesorado")
    private ProfesoresEntity profesor;

    /**
     * Constructores
     */
    public MatriculaEntity(){}

    public MatriculaEntity(String asignatura, int curso){
        this.asignatura=asignatura;
        this.curso=curso;
    }

    /**
     * Getters y Setters
     */
    //Getters

    public int getIdMatricula() {
        return idMatricula;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public int getCurso() {
        return curso;
    }

    public AlumnadoEntity getAlumno() {
        return alumno;
    }

    public ProfesoresEntity getProfesor() {
        return profesor;
    }

    //Setters

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public void setAlumno(AlumnadoEntity alumno) {
        this.alumno = alumno;
    }

    public void setProfesor(ProfesoresEntity profesor) {
        this.profesor = profesor;
    }

    public String toString(){
        return getIdMatricula() + ": " +  getAsignatura() + ", " + getCurso();
    }
}


