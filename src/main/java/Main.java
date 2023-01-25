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
        AlumnadoEntity josejuanjo=new AlumnadoEntity("Setso" , "perez", new Date());
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
