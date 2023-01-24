

import javax.persistence.*;

@Entity
@Table (name = "Matricula")
public class MatriculaEntity {
    @Id
    private int idMatricula;

    @Column (name = "idAlumnado")
    private int idAlumno;

    @Column (name = "idProfesorado")
    private int idProfesor;

    @Column (name = "Asignatura")
    private String asignatura;

    @Column (name = "Curso")
    private int curso;

    @ManyToOne
    @JoinColumn(name="idAlumnado")
    private AlumnadoEntity alumno;

    @ManyToOne
    @JoinColumn(name = "idProfesor")
    private ProfesoresEntity profesor;

    /**
     * Constructores
     */
    public MatriculaEntity(){}

    public MatriculaEntity(int idMatricula, int idProfesor, int idAlumno, String asignatura, int curso){
        this.idMatricula=idMatricula;
        this.idProfesor=idProfesor;
        this.idAlumno=idAlumno;
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

    public int getIdAlumno() {
        return idAlumno;
    }

    public int getIdProfesor() {
        return idProfesor;
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
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

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
}
