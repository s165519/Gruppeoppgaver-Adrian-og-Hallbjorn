/*Dette er GUI for innlogging og registering av lege. Klassen arver
 JTabbedPane og innholder fanene login og registrer. Den er en del av 
 Legekontorvinduet(Brukes av LegekontorVindu).
 Laget av Hallbjørn Storruste s165519
 Siste versjon 28-04-2014 */
package View;

import View.util.Komponent;
import Model.Lege;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

public class LegekontorLogin extends JTabbedPane {

    private LegekontorVindu parentFrame;
    private JTextField epostLoginFelt;
    private JPasswordField passordLoginFelt;
    private JButton loginKnapp;
    private JLabel error;
    private String[] labeltekst = {"E-post", "Passord"};
    private final int TEKSTFELTLENGDE = 20;

    private KnappeLytter knappeLytter;
    private FeltLytter feltLytter;

    /*Konstruktøren tar imot LegekontorVindu, dvs vindusklassen som oppretter
     objektet av denne klassen. Dette gjøres for å kunne benytte seg av metodene
     som ligger i dette objektet og kunne få tilgang til bl.a registerne, samt
     kunne gå videre/logge inn. Det vil si: å tegne vinduet på nytt med 
     brukergrensesnittet for å finne en pasient.*/
    public LegekontorLogin(LegekontorVindu p) {
        super();
        parentFrame = p;

        knappeLytter = new KnappeLytter();
        feltLytter = new FeltLytter();

        JPanel login = new JPanel(new FlowLayout());
        login.add(loginGUI());

        JPanel registrer = new JPanel(new FlowLayout());
        LegeRegistrer registrerGUI = new LegeRegistrer(parentFrame);
        registrer.add(registrerGUI);

        addTab("Innlogging", login);
        addTab("Registrer", registrer);
    }

    //Oppretter alt i fanen for login.
    private JPanel loginGUI() {
        epostLoginFelt = new JTextField(TEKSTFELTLENGDE);
        epostLoginFelt.addFocusListener(feltLytter);
        JPanel epostLogin = (JPanel) Komponent.labelFieldRow(labeltekst[0], epostLoginFelt);

        passordLoginFelt = new JPasswordField(TEKSTFELTLENGDE);
        passordLoginFelt.addActionListener(knappeLytter);
        JPanel passordLogin = (JPanel) Komponent.labelFieldRow(labeltekst[1], passordLoginFelt);

        error = new JLabel("");
        error.setForeground(Komponent.feilTekst);

        loginKnapp = new JButton("Logg inn");
        loginKnapp.addActionListener(knappeLytter);

        JPanel loginKnappPanel = new JPanel(new BorderLayout());
        loginKnappPanel.add(error, BorderLayout.LINE_START);
        loginKnappPanel.add(loginKnapp, BorderLayout.LINE_END);

        JPanel login = new JPanel(new GridLayout(0, 1, 5, 5));
        login.add(epostLogin);
        login.add(passordLogin);
        login.add(loginKnappPanel);

        return login;
    }

    /*Denne metoden sjekker om epost og passord er skrevet riktig inn.
     Og sender eventuelt videre til login.*/
    public void login() {
        String epost = epostLoginFelt.getText();
        char[] passord = passordLoginFelt.getPassword();
        Lege innlogget = parentFrame.login(epost, passord);

        if (innlogget == null) {
            String melding = "Feil epost eller passord!";
            Komponent.popup(parentFrame, melding);
        } else {
            parentFrame.tegnFinnPasientGUI(innlogget);
        }

    }

    private class KnappeLytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginKnapp || e.getSource() == passordLoginFelt) {
                login();
            }
        }
    }

    private class FeltLytter extends FocusAdapter {

        @Override
        public void focusLost(FocusEvent e) {
            if (e.getSource() == epostLoginFelt) {
                if (Komponent.riktigEpost(epostLoginFelt.getText())) {
                    epostLoginFelt.setForeground(Komponent.rettTekst);
                    error.setText("");
                } else {
                    epostLoginFelt.setForeground(Komponent.feilTekst);
                    error.setText("Vennligst skriv epost på formen noen@eksempel.no.");
                }
            }

        }
    }
}