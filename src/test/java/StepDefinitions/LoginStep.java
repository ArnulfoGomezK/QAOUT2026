package StepDefinitions;

import com.colprepo.pratica.Utilidades.Url;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginStep {

    @Managed(driver = "chrone")

    private ChromeDriver driver;

    private final Actor actor = Actor.named("usuario");

    Url url = new Url();
    @Before
    public void setUp() {
        actor.can(BrowseTheWeb.with(driver));
    }
    @Given("Ingresamos a la url de Colprepo")
    public void ingresamos_a_la_url_de_Colprepo() {
        actor.wasAbleTo(Open.browserOn(url));
    }

    @When("Ingrsamos usuario")
    public void ingrsamosUsuario() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("Ingrsamos contraseña")
    public void ingrsamosContraseña() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("Damos clic en el boton acceder")
    public void damosClicEnElBotonAcceder() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("Damos clic en el boton acceder de la extension")
    public void damosClicEnElBotonAccederDeLaExtension() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("Damos clic en el boton si")
    public void damosClicEnElBotonSi() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("Validamos que estemos dentro de Colprepo")
    public void validamosQueEstemosDentroDeColprepo() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
