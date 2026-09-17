package com.colprepo.pratica.Interfaces;


import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class LoginPage {

    public static final Target TXT_USUARIO = Target.the("campo para ingresar el usuario")
            .locatedBy("#usuario");

    public static final Target TXT_PALABRA_SECRETA = Target.the("campo para ingresar la palabra secreta")
            .locatedBy("#palabraSecreta");

    public static final Target BTN_ACCEDER = Target.the("botón Acceder de la pantalla")
            .locatedBy("#btn-acceder");

    public static final Target BTN_ACCEDER_EXTENSION = Target.the("botón Acceder  de la pantalla de extension")
            .locatedBy("#btn-acceder");

    public static final Target BTN_SI = Target.the("botón Sí de confirmación")
            .locatedBy("//button[contains(@class,'swal2-confirm') and normalize-space(.)='Si']");
}
