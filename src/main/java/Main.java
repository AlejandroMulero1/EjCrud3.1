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
        AlumnadoEntity josejuanjo=new AlumnadoEntity(1, "Setso" , "perez", new Date());
        guardar(josejuanjo);
    }
    /**
     * Método que guarda una persona en la base de datos a través de abrir una sesión, a partir de la sesión abrir una transacción
     * y dentro de esa transacción utiliza el .save de la clase Session, la cual guarda en la tabla el objeto, si todo sale
     * bien, hace un commit, si hay alguna excepción, hace un rollback
     * @param persona  objeto de la clase PersonaEntity que posee los datos de la persona a insertar
     */
    private static void guardar(AlumnadoEntity persona) {
        //Abro la sesion
        Session session = sessionFactory.openSession();
        //Inicio la transaccion
        Transaction transaction = session.beginTransaction();
        try {
            //Inserto en la BD
            session.save(persona);

            //Commit si no genera errores
            transaction.commit();

            //Cierro la sesion
            session.close();
        }
        catch (Exception e){
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
    public static void leerPersona(int id) {

        Session session = sessionFactory.openSession();
        // start a transaction
        Transaction transaction = session.beginTransaction();
        try{
            AlumnadoEntity persona = session.load(AlumnadoEntity.class, id);
            System.out.println(persona.getNombre());
            System.out.println(persona.getApellidos());

            // commit transaction
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Método que edita una persona en la base de datos a través de abrir una sesión, a partir de la sesión abrir una transacción
     * y dentro de esa transacción utiliza el load de la clase Session, asi carga la persona a editar, cuando se realiza el
     * cambio de sus campos, utilizo el .update para actualizar la persona
     * si todo sale bien, hace un commit, si hay alguna excepción, hace un rollback
     * @param id de la persona a editar
     */
    public static void actualizarPersona(int id) {

        Session session = sessionFactory.openSession();
        AlumnadoEntity persona = session.load(AlumnadoEntity.class, id);
        Transaction transaction = session.beginTransaction();

        try {
            // do changes
            Scanner sc=new Scanner(System.in);
            System.out.println("Escriba el nombre");
            String nombre=sc.nextLine();
            System.out.println("Escriba el saldo");
            String apellido=sc.nextLine();
            persona.setNombre(nombre);
            persona.setApellidos(apellido);

            // update the student object
            session.update(persona);
            transaction.commit();

        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Metodo que borra una persona a través de de abrir una sesión, a partir de la sesión abrir una transacción
     * y dentro de esa transacción utiliza el load de la clase Session para obtener la persona a borrar, luego con el .delete
     * borro a la persona si todo sale bien, hace un commit, si hay alguna excepción, hace un rollback
     * @param id id de la persona a borrar
     */
    public static void deletePersona(int id) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            AlumnadoEntity persona = session.load(AlumnadoEntity.class, id);
            session.delete(persona);
            System.out.println("Se ha borrado la persona");

            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            System.err.println("No se ha encontrado ninguna persona con esa id, vuelva a intentarlo mas tarde");
        }
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

}
