/*Denne klassen er et GUI til admin. 
 Upprättar AdminLege och AdminMedisin.
 Laget av Adrian Westlund s198571.
 Siste versjon 14-05-2014*/
package View;

import View.util.Komponent;
import Model.*;
import Controller.*;
import java.awt.*;
import java.io.*;
import java.util.TreeSet;
import javax.swing.*;

public class AdminVindu extends LegeRegSuper {

    private JTabbedPane legeGUI, medisinGUI;
    private Medisinregister medisinregister;
    private Legeregister legeregister;
    private Pasientregister pasientregister;

    public AdminVindu(Legeregister lreg, Pasientregister preg, Medisinregister mreg) {
        super("Admin");

        setLayout(new GridLayout(0, 1, 5, 5));

        legeregister = lreg;
        pasientregister = preg;
        medisinregister = mreg;
        //lesFil();

        legeGUI = new AdminLege(this);
        medisinGUI = new AdminMedisin(this);

        JTabbedPane gruppFane = new JTabbedPane();
        gruppFane.addTab("Lege", legeGUI);
        gruppFane.addTab("Medisin", medisinGUI);

        add(gruppFane);

        Komponent.endreFont(this);
        Komponent.bilde(this);
        pack();
        setVisible(true);
    }

    //Använder Legeregister sin finnLegeEpost och retunerar ett Lege objekt
    public Lege finnLege(String epost) {
        return legeregister.finnLegeEpost(epost);
    }

    //Registrerer lege med Legeregister sin settInn metode.
    @Override
    public boolean registrerLege(String fornavn, String etternavn, String ep,
            String gadresse, String pNr, String psted, String as, char[] pass) {

        return legeregister.settInn(fornavn, etternavn, ep, gadresse, pNr, psted, as, pass);
    }

    //Skriver Legeregister sin toString()
    public String skrivLegeListe() {
        return legeregister.toString();
    }

    //Returnerer medisinregisteret.
    public String[] getAlleMedisinnavn() {
        return medisinregister.getAlleMedisinnavn();
    }

    /*Finner og returnerer en Stringarray med alle pakninger for en medisin 
     med en gitt styrke og en gitt legemiddelform*/
    public String[] getAlleMedisinPakninger(String navn, String styrke, String form) {
        return medisinregister.getAlleMedisinPakninger(navn, styrke, form);
    }

    /*Finner og returnerer en Stringarray med alle legemiddelformer for 
     en medisin med en gitt styrke*/
    public String[] getAlleMedisinFormer(String navn, String styrke) {
        return medisinregister.getAlleMedisinFormer(navn, styrke);
    }

    //Finner og returnerer en Stringarray med alle styrker for en medisin
    public String[] getAlleMedisinStyrker(String navn) {
        return medisinregister.getAlleMedisinStyrker(navn);
    }

    //Använder Medisinregister sin finnMedisin för att hitta en medisin
    public Medisin finnMedisin(String navn, String styrke, String form, String pakning) {
        return medisinregister.finnMedisin(navn, styrke, form, pakning);
    }

    //Registrer medisin med Medisinregister sin settInn metode
    public boolean registrerMedisin(String navn, String kat, String gruppe, String styrke, String form, String pakning, String atcNr) {
        return medisinregister.settInn(navn, kat, gruppe, styrke, form, pakning, atcNr);
    }

    //
    public String[] skrivKatArray() {
        return medisinregister.getAlleKategorier();
    }

    //Skriver Medisinregister sin toString()
    public String skrivMedisinListe() {
        return medisinregister.toString();
    }

    @Override
    //Skriver medisinregister, legeregister og pasientregister til fil.
    public void skrivTilFil() {
        try (ObjectOutputStream utfil = new ObjectOutputStream(new FileOutputStream(Komponent.dataFil))) {
            utfil.writeObject(medisinregister);
            utfil.writeObject(legeregister);
            utfil.writeObject(pasientregister);
        } catch (NotSerializableException nse) {
            String melding = "Objektet er ikke serialisert!\n" + nse.getMessage();
            Komponent.popup(this, melding);
        } catch (IOException ioe) {
            String melding = "Problemer med utskrift til fil\n" + ioe.getMessage();
            Komponent.popup(this, melding);
        }
    }
}
