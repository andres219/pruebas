package ejemplo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class logger {
     
    String logfile = "/reportes/reporte.log";
    static Date fecha = new Date();
 
    private static String obtenerFecha() 
    {
    	SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        String fechaAc = formato.format(fecha);
        
        return fechaAc;
    }
    
    public static void imprimirLog(String[] datos) throws IOException
    {
    	Logger log = Logger.getLogger(logger.class);
        String fechaA = obtenerFecha();
        System.out.println(fechaA);
        PatternLayout defaultLayout = new PatternLayout("%p: %d{HH:mm:ss} --> %m%n");
        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        rollingFileAppender.setFile("/reportes/reporte_"+fechaA+".log", true, false, 0);
        rollingFileAppender.setLayout(defaultLayout);
 
        log.removeAllAppenders();
        log.addAppender(rollingFileAppender);
        log.setAdditivity(false);
 
        int aux = datos.length;
        log.info("");
        log.info("");         
               
        for (int i=0;i<=aux-1;i++){
        	if(datos[i]!=null){
        		String subStr=datos[i].substring(0, 1);
        		if(subStr.equals("&"))
        		{
        			if(datos[i].substring(1, 20).equals("org.openqa.selenium"))
        				log.error("Tiempo de respuesta agotado. Para mas detalles consultar las im�genes en la carpeta 'capturas'.");
        			else
        				log.error(datos[i].substring(1));
        		}
        		else{
        			log.info(datos[i]); 
        		}
        	}    
        }
    }
    
    public static void captureScreen(String fileName) throws Exception 
    { 
		   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		   Rectangle screenRectangle = new Rectangle(screenSize);
		   Robot robot = new Robot();
		   String fechaA = obtenerFecha();
		   BufferedImage image = robot.createScreenCapture(screenRectangle);
		   ImageIO.write(image, "gif", new File("/reportes/capturas/"+fechaA+"_"+fileName+".gif"));
	}
}
