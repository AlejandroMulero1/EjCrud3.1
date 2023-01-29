
import Entidades.AlumnadoEntity;

import Entidades.MatriculaEntity;
import Entidades.ProfesoresEntity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


import java.util.Date;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static SessionFactory sessionFactory;
    public static void main(String[] args) {

        /**
         * Codigo para deshabilitar los warnings de hibernate
         */
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
        try{
            setUp();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in);
        int opcion;
        do {
            Class claseEntity=mostrarOpciones();
            mostrarCrud(claseEntity);
            System.out.println("Desea continuar? Escriba 1");
            opcion=sc.nextInt();
        } while (opcion==1);
    }

    /**
     * Método que configura la conexión con la base de datos
     * @throws Exception
     */
    protected static void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // por defecto: hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    /**
     * Metodo que le pregunta al usuario con que entidad desea trabajar
     * @return clase de la entidad solicitada
     */
    public static Class mostrarOpciones(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Que entidad desea manipular:");
        System.out.println("1. Alumnado");
        System.out.println("2. Profesorado");
        System.out.println("3. Matriculas");
        Class classEntity=null;
        int opcion= sc.nextInt();
        switch (opcion) {
            case 1:
                classEntity= AlumnadoEntity.class;
                break;

            case 2:
                classEntity=ProfesoresEntity.class;
                break;

            case 3:
                classEntity= MatriculaEntity.class;
                break;
        }

        return classEntity;
    }

    /**
     * Método que gestiona el programa llamando a los métodos necesarios en función de la clase proveída por parámetro
     * @param classEntity clase de la Entidad con la que trabajar
     */
    public static void mostrarCrud(Class classEntity){
        int id; //Variable para el read y el delete
        Scanner sc=new Scanner(System.in);
        System.out.println("Que desea hacer");
        System.out.println("1. Crear");
        System.out.println("2. Leer");
        System.out.println("3. Actualizar");
        System.out.println("4. Borrar");
        int opcion= sc.nextInt();
        switch (opcion){
            case 1: //Create
                Object obj;
                if (classEntity== AlumnadoEntity.class){
                    obj=Crud.crearAlumno();
                } else if (classEntity == ProfesoresEntity.class){
                    obj=Crud.crearProfesor();
                }   else if (classEntity == MatriculaEntity.class){
                    obj=Crud.crearMatricula(new AlumnadoEntity(),new ProfesoresEntity());
                }   else {
                    obj=null;
                }
                Crud.guardar(obj, sessionFactory);
                break;

            case 2: //Read
                 id=Crud.menuElegirPersona(sessionFactory, classEntity);
                Crud.leerPersona(id, sessionFactory, classEntity);
                break;

            case 3: //Update
                id=Crud.menuElegirPersona(sessionFactory, classEntity);
                Session session=sessionFactory.openSession();
                Object objActualizado=null;

                /*
                Cada apartado dentro del if se divide en 3 partes
                1:Obtengo 2 objetos, uno con lo que quiero actualizar y otro con los datos actualizados
                2:Como el primero guarda la id, pongo los parámetros actualizados del segundo en el primero con el get
                3: Igualo el primer objeto ya actualizado con un obj que será insertado en la bd
                 */
                if (classEntity== AlumnadoEntity.class){
                    AlumnadoEntity alumnoActualizar= (AlumnadoEntity) session.load(classEntity, id);
                    AlumnadoEntity alumnoActualizado=Crud.crearAlumno();

                    alumnoActualizar.setNombre(alumnoActualizado.getNombre());
                    alumnoActualizar.setApellidos(alumnoActualizado.getApellidos());

                    objActualizado=alumnoActualizar;

                } else if (classEntity == ProfesoresEntity.class){
                    ProfesoresEntity profesorActualizar= (ProfesoresEntity) session.load(classEntity, id);
                    ProfesoresEntity profesorActualizado=Crud.crearProfesor();

                    profesorActualizar.setNombre(profesorActualizado.getNombre());
                    profesorActualizar.setApellidos(profesorActualizado.getApellidos());
                    profesorActualizar.setAntiguedad(profesorActualizado.getAntiguedad());

                    objActualizado=profesorActualizar;
                }   else if (classEntity == MatriculaEntity.class){
                    MatriculaEntity matriculaActualizar= (MatriculaEntity) session.load(classEntity, id);
                    MatriculaEntity matriculaActualizada=Crud.crearMatricula(new AlumnadoEntity(), new ProfesoresEntity());

                    matriculaActualizar.setAsignatura(matriculaActualizada.getAsignatura());
                    matriculaActualizar.setCurso(matriculaActualizada.getCurso());

                    objActualizado=matriculaActualizar;
                }   else {
                    objActualizado=null;
                }
                session.close();
                Crud.actualizarPersona(sessionFactory, objActualizado);
                break;

            case 4: //Delete
                 id=Crud.menuElegirPersona(sessionFactory, classEntity);
                Crud.deletePersona(id,sessionFactory, classEntity);
                break;
        }
    }

}
