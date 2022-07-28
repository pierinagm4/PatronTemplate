package oscarblancarte.ipd.templetemethod;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import oscarblancarte.ipd.templetemethod.impl.DrugstoreFileProcess;
import oscarblancarte.ipd.templetemethod.impl.GroceryFileProcess;
import oscarblancarte.ipd.templetemethod.impl.PaymentFileProcess;

/**
 * @author Oscar Javier Blancarte Iturralde
 * @see http://www.oscarblancarteblog.com
 */
public class TempleteMethodMain extends TimerTask {
    
    //D:/Template/01_Pagos
    //C:/files/drugstore
    
    private static final String[] PATHS = 
        new String[]{"C:/Users/pieri/Desktop/TempleteMethod/Templates/drugstore", "C:/Users/pieri/Desktop/TempleteMethod/Templates/grocery", "C:/Users/pieri/Desktop/TempleteMethod/Templates/payments"};
    private static final String LOG_DIR = "C:/Users/pieri/Desktop/TempleteMethod/Templates/logs";
    private static final String PROCESS_DIR = "C:/Users/pieri/Desktop/TempleteMethod/Templates/process";

    public static void main(String[] args) {
        new TempleteMethodMain().start();
    }

    public void start() {
        try {
            Timer timer = new Timer();
            timer.schedule(this, new Date(), (long) 1000 * 10);
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("> Monitoring start");
        File f = new File(PATHS[0]);
        if(!f.exists()){
            throw new RuntimeException("El path '"+PATHS[0]+"' no existe");
        }
        System.out.println("> Read Path " + PATHS[0]);
        File[] drugstoreFiles = f.listFiles();
        for (File file : drugstoreFiles) {
            try {
                System.out.println("> File found > " + file.getName());
                new DrugstoreFileProcess(file,LOG_DIR,PROCESS_DIR).execute();
                System.out.println("Processed file > " + file.getName());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        
        f = new File(PATHS[1]);
        if(!f.exists()){
            throw new RuntimeException("El path '"+PATHS[1]+"' no existe");
        }
        System.out.println("> Read Path " + PATHS[1]);
        File[] groceryFiles = f.listFiles();
        for (File file : groceryFiles) {
            try {
                System.out.println("> File found > " + file.getName());
                new GroceryFileProcess(file,LOG_DIR,PROCESS_DIR).execute();
                System.out.println("Processed file > " + file.getName());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        
        // NUEVO LBASANTES 
        f = new File(PATHS[2]);
        if(!f.exists()){
            throw new RuntimeException("El path '"+PATHS[2]+"' no existe");
        }
        System.out.println("> Read Path " + PATHS[2]);
        File[] pagosFiles = f.listFiles();
        for (File file : pagosFiles) {
            try {
                System.out.println("> File found > " + file.getName());
                new PaymentFileProcess(file, LOG_DIR, PROCESS_DIR).execute();
                System.out.println("Processed file > " + file.getName());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}