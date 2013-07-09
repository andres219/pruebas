package ejemplo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.IOException;

public class CrearPersonaCETest {
	private static WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private final static long sleep = 5000L;
	private final static long longSleep = 7000L;
	private static String dbUrl="jdbc:oracle:thin:@197.0.2.202:1521:PREVDESA";
	private static String username="PREVCLIENT10_11";
	private static String password="PREVCLIENT10_11";
  
	private static String tipoI = "C�dula de extranjer�a";   //Tipo identificaci�n 
	private static String numeroI = "500000000";             //Cedula
	private static String primerA = "S�nchez";               //Primer apellido
	private static String primerN = "Pedro";                 //Primer nombre
	private static String segundoN = "Pablo";                //Segundo nombre
	private static String genero = "Masculino";              //Genero
	private static String condicion = "Vivo";                //Condici�n de la persona
	private static String fechaCondicion = "05/06/2013";     //Fecha condicion
	private static String fechaExpedicionI = "21/12/2000";   //Fecha expedicion de identificacion
	private static String paisExpedicionI = "COLOMBIA";      //Pais expedicion de identificacion
	private static String deptoExpedicionI = "Bogot� D.C.";  //Departamento de expedicion de identificacion
	private static String ciudadExpedicionI = "Bogota";      //Ciudad expedicion de identificacion
	private static String datos[] = new String[21];     //Vector para almacenar todos los errores que se van a mostrar
	private static Integer i = 0;

	@Before
	public void setUp() throws Exception {
	  System.setProperty("webdriver.ie.driver", "D:/IEDriverServer.exe");
	  driver = new InternetExplorerDriver();  
	  baseUrl = "http://197.0.6.67:81/";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	  //Elimina el registro de prueba en caso que se encuentre previamente en BD
	  String id = ""; 
	  id = obtenerIdPersona(numeroI, tipoI);
	  if(!id.equals(""))
		  eliminar(id);
    }

	@Test
	public void testCrearPersona() throws Exception {
	  driver.get(baseUrl + "PrevisionaWEB/login.seam");
	  datos[i] = "\n***************TestCase "+getClass().getName()+".java***************";
	  i++;
	  
	  try{
		  //Ingresar datos de login
		  driver.findElement(By.id("login:username")).clear();
		  driver.findElement(By.id("login:username")).sendKeys("Previsiona_User");
		  driver.findElement(By.id("login:password")).clear();
		  driver.findElement(By.id("login:password")).sendKeys("Previsiona_User");
		  driver.findElement(By.id("login:loginId")).click();
		  //Ingresar desde el men�
		  Thread.sleep(sleep);
		  //Ingresar al formulario Gestionar Personas
		  driver.get(baseUrl + "/PrevisionaWEB/personas/busqueda_persona.seam");
		  Thread.sleep(sleep);
		  //Ingresar datos para consultar persona
		  new Select(driver.findElement(By.id("formBusquedaPersona:tipoIdentificacionField:elementoFoco"))).selectByVisibleText(tipoI);
		  Thread.sleep(sleep);
		  driver.findElement(By.id("formBusquedaPersona:numeroIdentificacionField:numeroIdentificacion")).clear();
		  driver.findElement(By.id("formBusquedaPersona:numeroIdentificacionField:numeroIdentificacion")).sendKeys(numeroI);
		  Thread.sleep(sleep);
		  driver.findElement(By.id("formBusquedaPersona:decButtonsBusquedaPersona:botonBuscar")).click();
		  Thread.sleep(longSleep);
		  	 
		  //Confirmar que se va a crear la persona
		  driver.findElement(By.id("formCrearPersona:buttonModalConfirmarCrear")).click();
		  Thread.sleep(longSleep);
		  //Ingresar datos de la persona
		  new Select(driver.findElement(By.id("formCreacionPersona:tipoIdentificacionPNField:elementoFoco"))).selectByVisibleText(tipoI);
		  driver.findElement(By.id("formCreacionPersona:numeroIdentificacionPNField:numeroIdentificacion")).clear();
		  driver.findElement(By.id("formCreacionPersona:numeroIdentificacionPNField:numeroIdentificacion")).sendKeys(numeroI);
		  driver.findElement(By.id("formCreacionPersona:primerApellidoField:primerApellido")).clear();
		  driver.findElement(By.id("formCreacionPersona:primerApellidoField:primerApellido")).sendKeys(primerA);
		  driver.findElement(By.id("formCreacionPersona:primerNombreField:primerNombre")).clear();
		  driver.findElement(By.id("formCreacionPersona:primerNombreField:primerNombre")).sendKeys(primerN);
		  driver.findElement(By.id("formCreacionPersona:segundoNombreField:segundoNombre")).clear();
		  driver.findElement(By.id("formCreacionPersona:segundoNombreField:segundoNombre")).sendKeys(segundoN);
		  new Select(driver.findElement(By.id("formCreacionPersona:generoField:listaGenero"))).selectByVisibleText(genero);
		  new Select(driver.findElement(By.id("formCreacionPersona:condicionPersonaField:listaCondicionPersona"))).selectByVisibleText(condicion);
		  driver.findElement(By.id("formCreacionPersona:fechaCondicionField:fechaCondicionInputDate")).sendKeys(Keys.HOME);
		  driver.findElement(By.id("formCreacionPersona:fechaCondicionField:fechaCondicionInputDate")).sendKeys(fechaCondicion);
		  driver.findElement(By.id("formCreacionPersona:fechaExpedicionIdField:fechaExpedicionIdInputDate")).sendKeys(Keys.HOME);
		  driver.findElement(By.id("formCreacionPersona:fechaExpedicionIdField:fechaExpedicionIdInputDate")).sendKeys(fechaExpedicionI);
		  new Select(driver.findElement(By.id("formCreacionPersona:paisExpedicionIdField:listaPaises"))).selectByVisibleText(paisExpedicionI);
		  Thread.sleep(sleep);
		  new Select(driver.findElement(By.id("formCreacionPersona:departamentoExpedicionIdField:listaDeptos"))).selectByVisibleText(deptoExpedicionI);
		  Thread.sleep(sleep);
		  new Select(driver.findElement(By.id("formCreacionPersona:ciudadExpedicionIdField:listaCiudades"))).selectByVisibleText(ciudadExpedicionI);
		  Thread.sleep(sleep);
		  driver.findElement(By.id("formCreacionPersona:ButtonsPerson:botonIngresar")).click();
		  Thread.sleep(sleep);
		  //No crear rol
		  driver.findElement(By.id("formCrearRolPersona:buttonModalCancelarCrear")).click();
			  
		  Thread.sleep(longSleep);
		  try{
			  //Conexion a la base de datos
			  Class.forName("oracle.jdbc.driver.OracleDriver");
			  Connection con = DriverManager.getConnection (dbUrl,username,password);
			  Statement stmt = con.createStatement();
			  
			  //Verifica si se guardaron correctamente los valores ingresados
			  verificar(stmt, con);
		  } catch (Exception e){
			  datos[i]="&"+e;
			  i++;
			  //Toma pantallazo del error
			  logger.captureScreen(getClass().getName());
			  //Imprimir errores en el log
			  logger.imprimirLog(datos);			  
		  }	
	  } catch (Exception error){
		  datos[i]="&"+error;
		  i++;
		  //Si se presenta la pantalla de error de la aplicaci�n, hacer clic en el bot�n "Ver Detalles",
		  //obtener la traza del error y copiarla en el log 
    	  try
    	  {
    		  driver.findElement(By.id("form1:boton1Decorate:botonDetalle")).click();
    		  Thread.sleep(longSleep);
     		  String mensaje = driver.findElement(By.id("form1:areaError")).getText();
    		  Thread.sleep(longSleep);
    		  datos[i]="&"+mensaje;
    		  i++;
    	  }catch (Exception ex){}
		  //Toma pantallazo del error
		  logger.captureScreen(getClass().getName());
		  //Imprimir errores en el log
		  logger.imprimirLog(datos);
	  }	  
	}
	
	@After
	public void tearDown() throws Exception {
	  driver.quit();
	  String verificationErrorString = verificationErrors.toString();
	  if (!"".equals(verificationErrorString)) {
	    fail(verificationErrorString);
	  }
	}
  
  private static void verificar(Statement stmt, Connection con) throws ClassNotFoundException, SQLException, IOException, InterruptedException
  {  
	String persona ="SELECT IDE.TIDDSDESCRIPCION, PERCDNUMEROIDENTIFICACION, " +
			"PERDSPRIMERNOMBRE, PERDSSEGUNDONOMBRE, PERDSPRIMERAPELLIDO, " +
			"PERDSSEGUNDOAPELLIDO, PERDSRAZONSOCIAL, PERDSSEXO, COND.TCPDSNOMBRECONDICION, " +
			"to_char(PERFEFECHACONDICION,'DD/MM/yyyy'), to_char(PERFEEXPEDICIONIDENTIFICA,'DD/MM/yyyy'), PAIS.PAIDSNOMBREPAIS, " +
			"DEP.DEPDSNOMBREDEPARTAMENTO, CIU.CIUDSNOMBRECIUDAD, PERCDNUMEROCERTIFICADEFUNCION, " +
			"PERDSORIGENASIGNANIVELSEGURI, PERDSTIPOPERSONA, PERSNINDINFORMECOINCINOMBRE, " +
			"PERSNINDLISTAESPECIAL, PERID " +
			"FROM TPER_PERSONA, TENE_TIPO_IDENTIFICACIONES IDE, TPER_TIPO_CONDICION_PERNAT COND, " +
			"TENE_PAIS PAIS, TENE_DEPARTAMENTO_ESTADO DEP, TENE_CIUDAD CIU " +
			"WHERE PERCDNUMEROIDENTIFICACION = '"+numeroI+"' " +
			"AND TIDDSDESCRIPCION = '"+tipoI+"' " +
			"AND PERIDTIPOIDENTIFICACION = IDE.TIDID " +
			"AND COND.TCPID=PERIDCONDICIONPERSONANAT " +
			"AND CIU.CIUID=PERIDCIUDADEXPEDIIDENTIFICA " +
			"AND CIU.CIUIDPAIS=PAIS.PAIID " +
			"AND CIU.CIUIDDEPARTAMENTO=DEP.DEPID ";
	ResultSet rsPersona = stmt.executeQuery(persona);
	Thread.sleep(longSleep);
	Integer j = i;
	if(rsPersona.next()){
		System.out.print("Validando...");
		if(!tipoI.equals(rsPersona.getString(1))){
			datos[i] = "&El tipo de identificaci�n guardado ("+rsPersona.getString(1)+") no corresponde con el ingresado ("+tipoI+")";
			i++;
		}
		if(!numeroI.equals(rsPersona.getString(2)))	{
			datos[i] = "&El n�mero de identificaci�n guadado ("+rsPersona.getString(2)+") no corresponde con el ingresado ("+numeroI+")";
			i++;
		}	
		if(!primerN.equals(rsPersona.getString(3)))	{
			datos[i] = "&El primer nombre de la persona guardado ("+rsPersona.getString(3)+") no corresponde con el ingresado ("+primerN+")";
			i++;
		}
		if(segundoN==null){
			if(!segundoN.equals(rsPersona.getString(4))||segundoN!=(rsPersona.getString(4))) {	
				datos[i] = "&El segundo nombre de la persona guardado ("+rsPersona.getString(4)+") no corresponde con el ingresado ("+segundoN+")";
				i++;
			}
		} else {
			if(!segundoN.equals(rsPersona.getString(4))) {	
				datos[i] = "&El segundo nombre de la persona guardado ("+rsPersona.getString(4)+") no corresponde con el ingresado ("+segundoN+")";
				i++;
			}
		}
		if(!primerA.equals(rsPersona.getString(5)))	{
			datos[i] = "&El primer apellido de la persona guardado ("+rsPersona.getString(5)+") no corresponde con el ingresado ("+primerA+")";
			i++;
		}
		if(!genero.toUpperCase().equals(rsPersona.getString(8)))	{
			datos[i] = "&El genero de la persona guardado ("+rsPersona.getString(8)+") no corresponde con el ingresado ("+genero+")";
			i++;
		}
		if(!condicion.equals(rsPersona.getString(9))) {	
			datos[i] = "&La condici�n de la persona guardada ("+rsPersona.getString(9)+") no corresponde con el ingresada ("+condicion+")";
			i++;
		}
		if(fechaCondicion==null){
			if(fechaCondicion!=(rsPersona.getString(10)))	{
				datos[i] = "&La fecha de la condici�n de la persona guardada ("+rsPersona.getString(10)+") no corresponde con el ingresada ("+fechaCondicion+")";
				i++;
			}
		} else {
			if(!fechaCondicion.equals(rsPersona.getString(10)))	{
				datos[i] = "&La fecha de la condici�n de la persona guardada ("+rsPersona.getString(10)+") no corresponde con el ingresada ("+fechaCondicion+")";
				i++;
			}
		}	
		if(fechaExpedicionI==null){
			if(fechaExpedicionI!=(rsPersona.getString(11))) {	
				datos[i] = "&La fecha de expedici�n de la identificaci�n guardada ("+rsPersona.getString(11)+") no corresponde con el ingresada ("+fechaExpedicionI+")";
				i++;
			}
		} else{
			if(!fechaExpedicionI.equals(rsPersona.getString(11))) {	
				datos[i] = "&La fecha de expedici�n de la identificaci�n guardada ("+rsPersona.getString(11)+") no corresponde con el ingresada ("+fechaExpedicionI+")";
				i++;
			}
		}	
		if(paisExpedicionI==null){
			if(paisExpedicionI!=(rsPersona.getString(12))) {	
				datos[i] = "&El pais de expedici�n de la identificaci�n guardada ("+rsPersona.getString(12)+") no corresponde con el ingresada ("+paisExpedicionI+")";
				i++;
			}
		} else{
			if(!paisExpedicionI.equals(rsPersona.getString(12))) {	
				datos[i] = "&El pais de expedici�n de la identificaci�n guardada ("+rsPersona.getString(12)+") no corresponde con el ingresada ("+paisExpedicionI+")";
				i++;
			}
		}	
		if(deptoExpedicionI==null){
			if(deptoExpedicionI!=(rsPersona.getString(13))) {	
				datos[i] = "&El departamento de expedici�n de la identificaci�n guardada ("+rsPersona.getString(13)+") no corresponde con el ingresado ("+deptoExpedicionI+")";
				i++;
			}
		} else {
			if(!deptoExpedicionI.equals(rsPersona.getString(13))) {	
				datos[i] = "&El departamento de expedici�n de la identificaci�n guardada ("+rsPersona.getString(13)+") no corresponde con el ingresado ("+deptoExpedicionI+")";
				i++;
			}
		}	
		if(ciudadExpedicionI==null){
			if(ciudadExpedicionI!=(rsPersona.getString(14))){	
				datos[i] = "&La ciudad de expedici�n de la identificaci�n guardada ("+rsPersona.getString(14)+") no corresponde con el ingresada ("+ciudadExpedicionI+")";
				i++;
			}
		} else {
			if(!ciudadExpedicionI.equals(rsPersona.getString(14))){	
				datos[i] = "&La ciudad de expedici�n de la identificaci�n guardada ("+rsPersona.getString(14)+") no corresponde con el ingresada ("+ciudadExpedicionI+")";
				i++;
			}
		}
		if(!rsPersona.getString(16).equals("PROCESO_MASIVO")) {	
			datos[i] = "&El nivel de seguridad guardado ("+rsPersona.getString(16)+") no corresponde (PROCESO_MASIVO)";
			i++;
		}
		if(!rsPersona.getString(17).equals("NATURAL"))	{
			datos[i] = "&El tipo de persona guardado ("+rsPersona.getString(17)+") no corresponde (NATURAL)";
			i++;
		}
		if(!rsPersona.getString(18).equals("0")) {	
			datos[i] = "&El algoritmo de coincidencia guardado ("+rsPersona.getString(18)+") no corresponde (0)";
			i++;
		}
	}	
	
	if(i==j)
	{
		datos[i] = "\nLa persona ingresada se almaceno en la base de datos exitosamente...";
		i++;
	}
	else
	{
		datos[i] = "\nSe presentaron "+(i-2)+" errores al almacenar la persona en la base de datos. A continuaci�n se presentan los errores";
		i++;
	}
	
	con.close();
	Thread.sleep(longSleep);
	//Elimina la persona creada
	String id = ""; 
	id = obtenerIdPersona(numeroI, tipoI);
	if(!id.equals(""))
		eliminar(id);
	
	Thread.sleep(sleep);
	//Cerrar sesi�n
	driver.findElement(By.id("j_id54")).click();
	Thread.sleep(longSleep);
	
	//Imprimir errores en el log
	 logger.imprimirLog(datos);
  } 
  
  private static void eliminar(String id) throws ClassNotFoundException, SQLException, IOException
  {
	  Connection con1 = DriverManager.getConnection (dbUrl,username,password);			
	  Statement stmt1 = con1.createStatement();
	  String delete = "DELETE FROM TPER_PERSONA " +
	  				  "WHERE PERID = '"+id+"'";
	  stmt1.executeQuery(delete);
	  con1.commit();
	  con1.close();
  }

  private static String obtenerIdPersona(String numero, String tipo) throws ClassNotFoundException, SQLException, IOException
  {
		Connection conId = DriverManager.getConnection (dbUrl,username,password);			
		Statement stmtId = conId.createStatement();
		String id = "";
		String consultarId = "SELECT PERID " +
					         "FROM TPER_PERSONA, TENE_TIPO_IDENTIFICACIONES " +
					         "WHERE PERCDNUMEROIDENTIFICACION = '"+numero+"' " +
					         "AND TIDDSDESCRIPCION = '"+tipo+"' " +
					         "AND TPER_PERSONA.PERIDTIPOIDENTIFICACION = TENE_TIPO_IDENTIFICACIONES.TIDID ";
		ResultSet rsId = stmtId.executeQuery(consultarId);
	    if(rsId.next())
	    {
	    	id = rsId.getString(1);
	  	    System.out.print("Id Eliminar:"+id);
	    }
	    //Si no coincide tipo y n�mero buscar solo por n�mero de id:
	    else
	    {
			consultarId = "SELECT PERID " +
 						  "FROM TPER_PERSONA " +
						  "WHERE PERCDNUMEROIDENTIFICACION = '"+numero+"' ";
			ResultSet rsId1 = stmtId.executeQuery(consultarId);
			if(rsId.next())
			{
				id = rsId1.getString(1);
				System.out.print("Id Eliminar 1:"+id);
			}
	    }
	    conId.close();
	    return id;
  }

}


