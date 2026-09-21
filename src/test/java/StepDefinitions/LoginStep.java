package StepDefinitions;

import com.colprepo.pratica.Interfaces.LoginPage;
import com.colprepo.pratica.Utilidades.Url;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import net.serenitybdd.screenplay.actions.Enter;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import org.openqa.selenium.By;

public class LoginStep {

    // Instancia del WebDriver utilizada para controlar y consultar el navegador Chrome.
    private RemoteWebDriver driver;

    // Actor principal de Serenity Screenplay que ejecutará las acciones de automatización.
    private final Actor actor = Actor.named("usuario");

    // Objeto encargado de gestionar la URL utilizada durante la automatización.
    Url url = new Url();

    // Propiedades utilizadas para almacenar y consultar las credenciales del usuario.
    private Properties propiedades = new Properties();

    /**
     * Configura el entorno de ejecución antes de cada escenario de Cucumber.
     *
     * Inicializa manualmente ChromeDriver, abre Chrome utilizando un perfil
     * específico y establece la conexión de Selenium con la instancia de
     * Chrome que ya fue iniciada.
     */
    @Before
    public void setUp() throws IOException, InterruptedException, MalformedURLException {

        // Ruta del ejecutable de Google Chrome instalado en el equipo.
        String chromePath =
                "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";

        // Directorio de datos utilizado por Chrome para conservar la sesión,
        // cookies, configuraciones y demás información del perfil de automatización.
        String userDataDir =
                "C:\\Users\\Arni Gomez\\AppData\\Local\\Google\\Chrome\\ChromeAutomationData";

        // Ruta del ejecutable de ChromeDriver utilizado para controlar Chrome.
        String chromeDriverPath =
                "C:\\Users\\Arni Gomez\\.cache\\selenium\\chromedriver\\win64\\153.0.8010.52\\chromedriver.exe";

        // Inicia el proceso de ChromeDriver en el puerto 9515.
        ProcessBuilder chromeDriver = new ProcessBuilder(
                chromeDriverPath,
                "--port=9515"
        );

        chromeDriver.start();

        // Se espera brevemente para permitir que ChromeDriver quede disponible
        // antes de intentar establecer la conexión con él.
        Thread.sleep(2000);

        // Inicia Google Chrome utilizando el directorio de datos y el perfil
        // configurado para la automatización.
        ProcessBuilder chrome = new ProcessBuilder(
                chromePath,
                "--user-data-dir=" + userDataDir,
                "--profile-directory=Profile 7",
                "--remote-debugging-port=9222",
                "--remote-allow-origins=*"
        );

        chrome.start();

        // Se espera a que Chrome termine de iniciar y exponga el puerto
        // de depuración remota antes de realizar la conexión.
        Thread.sleep(3000);

        // Configuración específica de Chrome para Selenium.
        ChromeOptions options = new ChromeOptions();

        // Indica a Selenium que debe conectarse a la instancia de Chrome
        // que ya está ejecutándose mediante el puerto de depuración remota.
        options.setExperimentalOption(
                "debuggerAddress",
                "localhost:9222"
        );

        // Crea la conexión entre Selenium y el servidor ChromeDriver
        // que fue iniciado previamente en el puerto 9515.
        driver = new RemoteWebDriver(
                new URL("http://localhost:9515"),
                options
        );

        // Asocia el WebDriver con el Actor de Serenity Screenplay,
        // permitiendo que el Actor pueda ejecutar acciones sobre el navegador.
        actor.can(BrowseTheWeb.with(driver));

        // Carga las credenciales almacenadas en el archivo de propiedades.
        cargarCredenciales();
    }

    /**
     * Carga las credenciales utilizadas durante el proceso de autenticación.
     *
     * Las propiedades se obtienen desde el archivo usuario.properties,
     * evitando escribir directamente usuario y contraseña dentro del código.
     */
    private void cargarCredenciales() throws IOException {

        // Ruta del archivo que contiene las credenciales de prueba.
        String rutaArchivo =
                "src/test/resources/datos/usuario.properties";

        // Abre el archivo de propiedades para lectura.
        FileInputStream archivo = new FileInputStream(rutaArchivo);

        // Carga las propiedades en memoria para poder consultarlas
        // posteriormente mediante propiedades.getProperty().
        propiedades.load(archivo);

        // Cierra el archivo después de finalizar la lectura.
        archivo.close();
    }

    /**
     * Navega hacia la URL inicial de Colprepo.
     *
     * Corresponde al paso definido en el escenario de Cucumber:
     * "Ingresamos a la url de Colprepo".
     */
    @Given("Ingresamos a la url de Colprepo")
    public void ingresamos_a_la_url_de_Colprepo() throws InterruptedException {

        // Utiliza Screenplay para abrir la URL configurada.
        actor.wasAbleTo(Open.browserOn(url));

        // Espera unos segundos para permitir que la página termine de cargar.
        Thread.sleep(3000);

        // Muestra en consola la URL donde se encuentra actualmente el navegador.
        System.out.println("URL ACTUAL: " + driver.getCurrentUrl());

        // Muestra en consola el título actual de la página.
        System.out.println("TITULO: " + driver.getTitle());
    }

    /**
     * Ingresa el nombre de usuario en el formulario de autenticación.
     *
     * Antes de localizar el campo de usuario, cambia al iframe correspondiente
     * donde se encuentra el formulario de inicio de sesión.
     */
    @When("Ingresamos usuario")
    public void ingrsamosUsuario() {

        // Crea una espera explícita de hasta 30 segundos para sincronizar
        // las acciones con la carga de los elementos de la página.
        WebDriverWait wait = new WebDriverWait(
                driver,
                java.time.Duration.ofSeconds(30)
        );

        // Espera hasta que el iframe principal esté disponible y cambia
        // el contexto del WebDriver hacia dicho iframe.
        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                        By.id("navegadorPrincipal")
                )
        );

        // Cambia al segundo iframe disponible dentro del contexto actual.
        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(1)
        );

        // Busca el campo de usuario y espera hasta que sea visible.
        org.openqa.selenium.WebElement usuario =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("usuario")
                        )
                );

        // Selecciona el campo de usuario.
        usuario.click();

        // Ingresa el usuario obtenido desde el archivo de propiedades.
        usuario.sendKeys(propiedades.getProperty("usuario"));

        // Presiona TAB para pasar el foco al siguiente elemento del formulario.
        usuario.sendKeys(org.openqa.selenium.Keys.TAB);
    }

    /**
     * Ingresa la contraseña correspondiente al usuario autenticado.
     */
    @And("Ingresamos contraseña")
    public void ingrsamosContraseña() {

        // Espera hasta que el campo de contraseña sea visible
        // antes de intentar interactuar con él.
        org.openqa.selenium.WebElement contraseña =
                new WebDriverWait(
                        driver,
                        java.time.Duration.ofSeconds(30)
                ).until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("palabraSecreta")
                        )
                );

        // Obtiene la contraseña desde el archivo de propiedades
        // y la introduce en el campo correspondiente.
        contraseña.sendKeys(propiedades.getProperty("contrasena"));
    }

    /**
     * Ejecuta el clic sobre el botón principal de acceso.
     *
     * El botón está definido previamente como un Target de Serenity
     * dentro de la clase LoginPage.
     */
    @And("Damos clic en el boton acceder")
    public void damosClicEnElBotonAcceder() {

        // Utiliza el patrón Screenplay para ejecutar la acción de clic
        // sobre el botón de acceso definido en LoginPage.
        actor.attemptsTo(
                Click.on(LoginPage.BTN_ACCEDER)
        );
    }

    /**
     * Ejecuta el clic sobre el botón "Acceder" correspondiente
     * a la extensión o componente adicional del sistema.
     */
    @And("Damos clic en el boton acceder de la extension")
    public void damosClicEnElBotonAccederDeLaExtension() {

        // Crea una espera explícita de hasta 15 segundos.
        WebDriverWait wait = new WebDriverWait(
                driver,
                java.time.Duration.ofSeconds(15)
        );

        // Espera hasta que el botón esté disponible y permita interacción.
        WebElement boton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("btn-acceder")
                )
        );

        // Ejecuta el clic sobre el botón.
        boton.click();
    }

    /**
     * Confirma la acción mediante el botón "Sí" mostrado
     * en la ventana de confirmación del sistema.
     */
    @And("Damos clic en el boton si")
    public void damosClicEnElBotonSi() {

        // Crea una espera explícita de hasta 15 segundos.
        WebDriverWait wait = new WebDriverWait(
                driver,
                java.time.Duration.ofSeconds(15)
        );

        // Localizador del botón "Sí" dentro del cuadro de confirmación.
        // Se utiliza XPath para identificar el botón por su clase y texto visible.
        By botonSi = By.xpath(
                "//button[contains(@class,'swal2-confirm') and normalize-space(.)='Si']"
        );

        // Espera hasta que el botón sea visible y permita realizar el clic.
        WebElement elemento = wait.until(
                ExpectedConditions.elementToBeClickable(botonSi)
        );

        // Confirma la acción haciendo clic sobre el botón "Sí".
        elemento.click();
    }

    /**
     * Realiza la validación final del proceso de autenticación.
     *
     * La validación no depende únicamente del título de la página.
     * Se verifica la existencia de un elemento que pertenece a la interfaz
     * interna de Colprepo y que solamente debe estar disponible después
     * de completar correctamente el inicio de sesión.
     */
    @Then("Validamos que estemos dentro de Colprepo")
    public void validamosQueEstemosDentroDeColprepo() {

        // Crea una espera explícita de hasta 15 segundos para permitir
        // que la pantalla posterior al inicio de sesión termine de cargar.
        WebDriverWait wait = new WebDriverWait(
                driver,
                java.time.Duration.ofSeconds(15)
        );

        // Busca el encabezado "Nombre servicio", utilizado como elemento
        // de referencia para confirmar que la pantalla interna de Colprepo
        // se encuentra disponible.
        WebElement elemento = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//th[normalize-space(.)='Nombre servicio']")
                )
        );

        // Verifica adicionalmente que el elemento encontrado esté visible.
        // Si no está visible, el escenario se considera fallido.
        if (!elemento.isDisplayed()) {
            throw new AssertionError(
                    "No se encontró el elemento 'Nombre servicio' en Colprepo"
            );
        }

        // Información de la validación final en la consola de ejecución.
        System.out.println("===== VALIDACION FINAL =====");
        System.out.println("Ingreso a Colprepo exitoso");
        System.out.println("Elemento encontrado: " + elemento.getText());
    }
}

