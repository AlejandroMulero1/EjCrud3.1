import Entidades.AlumnadoEntity;
import Entidades.MatriculaEntity;
import Entidades.ProfesoresEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Crud {
    /**
     * Metodo que obtiene el listado de objetos de la base de datos a través de una query sql y retorna una list con todas las
     * personas
     * @return List PersonasEntity
     */
    private static List<Objects> obtenerListado(SessionFactory sessionFactory, Class claseEntity){
        Session session = sessionFactory.openSession();
        String table;

        //Bloque de ifs que asignan en en funcion a la clase pasada la tabla de la que obtener el listado
        if (claseEntity== AlumnadoEntity.class){
            table="Alumnado";
        } else if (claseEntity == ProfesoresEntity.class){
            table="Profesores";
        }   else if (claseEntity == MatriculaEntity.class){
            table="Matricula";
        }   else {
            table="";
        }

        //Guardo la consulta en una lista a partir de una query sql, le indico con el addEntity que los datos son de la clase
        //PersonaEntity y con el .list convierto los datos en una lista
        List listado=(List<Objects>) session.createSQLQuery("SELECT * FROM " + table).addEntity(claseEntity).list();
        session.close();
        return listado;
    }

    /**
     * Método que forma un menu con el listado de objetos obtenido del método obtenerListaPersonas(), recoge un int que es la id
     * del objeto seleccionado
     * @return
     */
    public static int menuElegirPersona(SessionFactory sessionFactory, Class claseEntity){
        Scanner sc=new Scanner(System.in);
        List<Objects> listado=obtenerListado(sessionFactory, claseEntity);
        System.out.println("Elija la fila a leer");
        for (Object objetoListado:listado){
            System.out.println(objetoListado.toString());
        }
        int opcion=sc.nextInt();
        return opcion;
    }

    /**
     * Método que guarda una persona en la base de datos a través de abrir una sesión, a partir de la sesión abrir una transacción
     * y dentro de esa transacción utiliza el .save de la clase Session, la cual guarda en la tabla el objeto, si todo sale
     * bien, hace un commit, si hay alguna excepción, hace un rollback
     * @param obj de la clase Objeto que posee los datos de la persona a insertar
     */
    public static void guardar(Object obj, SessionFactory sessionFactory) {
        //Abro la sesion
        Session session = sessionFactory.openSession();
        //Inicio la transaccion
        Transaction transaction = session.beginTransaction();
        try {
            //Inserto en la BD
            session.save(obj);

            //Commit si no genera errores
            transaction.commit();

            //Cierro la sesion
            session.close();
        } catch (Exception e) {
            //Rollback si aparece algun error
            transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Método que lee una persona en la base de datos a través de abrir una sesión, a partir de la sesión abrir una transacción
     * y dentro de esa transacción utiliza el .load de la clase Session para cargar la persona a leer de la base de datos con la id que se le
     * pasa al metodo, si todo sale bien, hace un commit, si hay alguna excepción, hace un rollback
     * @param id id de la persona a leer
     */
    public static void leerPersona(int id, SessionFactory sessionFactory, Class claseEntity) {

        Session session = sessionFactory.openSession();
        // start a transaction
        Transaction transaction = session.beginTransaction();
        try{
            Object persona = session.load(claseEntity, id);
            System.out.println(persona.toString());
            // commit transaction
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Metodo que recibe un objeto perteneciente a la base de datos ya actualizado y lo guarda
     * si todo sale bien, hace un commit, si hay alguna excepción, hace un rollback
     * @param
     */
    public static void actualizarPersona( SessionFactory sessionFactory, Object objActualizar) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            // update the student object
            session.update(objActualizar);
            transaction.commit();

        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Metodos de creacion de las entidades del ejercicio
     * @return
     */
    public static AlumnadoEntity crearAlumno(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Escriba el nombre");
        String nombre= sc.next();
        System.out.println("Escriba el apellido");
        sc.nextLine();
        String apellido=sc.nextLine();


        return new AlumnadoEntity(nombre,apellido, new Date());
    }
    public static ProfesoresEntity crearProfesor(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Escriba el nombre");
        String nombre= sc.next();
        System.out.println("Escriba el apellido");
        sc.nextLine();
        String apellido=sc.nextLine();
        System.out.println("EsCRIBA LA antiguedad");
        int antiguedad=sc.nextInt();

        return new ProfesoresEntity(nombre,apellido, new Date(), antiguedad);
    }

    public static MatriculaEntity crearMatricula(AlumnadoEntity alumnado, ProfesoresEntity profesor){
        Scanner sc=new Scanner(System.in);
        System.out.println("Escriba la asignatura");
        String asignatura= sc.next();
        System.out.println("Escriba el curso");
        int curso=sc.nextInt();
        MatriculaEntity matricula= new MatriculaEntity(asignatura,curso);
        matricula.setAlumno(alumnado);
        matricula.setProfesor(profesor);

        return matricula;
    }

    /**
     * Metodo que borra una persona a través de de abrir una sesión, a partir de la sesión abrir una transacción
     * y dentro de esa transacción utiliza el load de la clase Session para obtener la persona a borrar, luego con el .delete
     * borro a la persona si todo sale bien, hace un commit, si hay alguna excepción, hace un rollback
     * @param id id de la persona a borrar
     */
    public static void deletePersona(int id, SessionFactory sessionFactory, Class claseEntity) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Object obj = session.load(claseEntity, id);
            session.delete(obj);
            System.out.println("Se ha borrado la fila");

            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            System.err.println("No se ha encontrado ninguna persona con esa id, vuelva a intentarlo mas tarde");
        }
    }
}
