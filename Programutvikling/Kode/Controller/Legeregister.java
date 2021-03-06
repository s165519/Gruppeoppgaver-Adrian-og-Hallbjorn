package Controller;

/**
 * Denne klassen legger leger inn i et register(TreeSet) og gjør operasjoner mot
 * den. 
 * Laget av Hallbjørn Storruste s165519
 * Siste versjon: 08-04-2014
 *
 * @author Hallbjørn
 */
import Model.*;
import java.io.*;
import java.util.*;

public class Legeregister implements Serializable {

    private static final long serialVersionUID = 1008L;
    private Comparator komp;
    private SortedSet<Lege> legeregister;

    public Legeregister() {
        komp = new PersonComparator();
        legeregister = new TreeSet<>(komp);
    }

    /*Oppretter en ny lege og setter den inn i registeret.
     Returnerer true hvis det er vellykket.*/
    public boolean settInn(String fornavn, String etternavn, String ep,
            String gadresse, String pNr, String psted, String as, char[] pass) {

        Lege ny = new Lege(fornavn, etternavn, ep, gadresse, pNr, psted, as, pass);

        return settInn(ny);
    }
    /*Setter inn ny lege i registeret. Returnerer true hvis vellykket*/

    public boolean settInn(Lege ny) {
        if (ny == null) {
            return false;
        }
        return legeregister.add(ny);
    }

    //Finner en eller flere leger basert på etternavn og fornavn.
    public TreeSet<Lege> finnLege(String etternavn, String fornavn) {
        Iterator<Lege> iterator = legeregister.iterator();
        TreeSet<Lege> legeSet = new TreeSet<>(komp);
        Lege runner;

        while (iterator.hasNext()) {
            runner = iterator.next();
            if (runner.getEtternavn().matches(etternavn)
                    && runner.getFornavn().matches(fornavn)) {
                legeSet.add(runner);
            }

        }
        return legeSet;
    }

    /*Finner en eller flere leger som begynner med det aktuelle navn. 
     Søker først gjennom etternavn og så fornavn.*/
    public TreeSet<Lege> finnLege(String navn) {
        TreeSet<Lege> etternavnarray = finnLege(navn + ".*", ".*");
        TreeSet<Lege> fornavnarray = finnLege(".*", navn + ".*");

        TreeSet<Lege> legearray = etternavnarray;
        legearray.addAll(fornavnarray);

        return legearray;
    }

    /*Finner og returnerer en lege i registeret basert på epost.
     Returnerer 'null' hvis ikke den finnes. */
    public Lege finnLegeEpost(String epost) {
        Iterator<Lege> iterator = legeregister.iterator();
        Lege runner = null;

        while (iterator.hasNext()) {
            runner = iterator.next();
            if (runner.getEPost().equals(epost)) {
                return runner;
            }
        }
        return null;
    }

    /*Tar imot en epostadresse og ett passord og sjekker om dette
     stemmer med det tilhørende lege-objektet. Returnerer true hvis det stemmer,
     false hvis det ikke stemmer eller epostadressen ikke fins i registeret.*/
    public boolean riktigPassord(String epost, char[] passord) {
        Lege lege = finnLegeEpost(epost);
        if (lege == null) {
            return false;
        }

        boolean riktig = riktigPassord(lege, passord);

        return riktig;
    }

    /*Tar imot et lege-objekt og et passord som et char-array,
     sjekker om passordet stemmer med passordet til legen.
     Returnerer true hvis det er likt.
     */
    public static boolean riktigPassord(Lege lege, char[] passord) {
        if (lege == null) {
            return false;
        }
        boolean riktig;
        char[] kontroll = lege.getPassord();
        if (kontroll.length == passord.length) {
            riktig = Arrays.equals(passord, kontroll);
        } else {
            riktig = false;
        }

        return riktig;
    }
    /*Finner alle leger som har foreskrevet minst ett av
     medisinene som sendes med som parameter.*/

    public Lege[] finnLegeMedisin(Medisin[] medisin) {

        Iterator<Lege> iterator = legeregister.iterator();
        TreeSet<Lege> leger = new TreeSet<>(komp);
        Lege runner;

        while (iterator.hasNext()) {
            runner = iterator.next();
            TreeSet<Pasient> pasienter = (TreeSet<Pasient>) runner.getPasientliste().getRegister();
            Iterator<Pasient> iterator2 = pasienter.iterator();
            Pasient runner2;
            while (iterator2.hasNext()) {
                runner2 = iterator2.next();

                Reseptregister tempRegister = runner2.getReseptliste();

                Resept[] resepter = tempRegister.finnReseptMedisin(medisin, runner);
                if (resepter.length > 0) {
                    leger.add(runner);
                    break;
                }
            }
        }

        Lege[] legeListe = new Lege[leger.size()];
        return leger.toArray(legeListe);
    }
    /*Finner en eller flere leger basert på etternavn og fornavn og som
     har foreskrevet et av medisinene som sendes med som parameter.*/

    public Lege[] finnLegeNavn(String etternavn, String fornavn, Medisin[] medisin) {

        TreeSet<Lege> pasientSet = finnLege(etternavn, fornavn);

        Iterator<Lege> iterator = pasientSet.iterator();
        TreeSet<Lege> leger = new TreeSet<>(komp);
        Lege runner;

        while (iterator.hasNext()) {
            runner = iterator.next();
            TreeSet<Pasient> pasienter = (TreeSet<Pasient>) runner.getPasientliste().getRegister();
            Iterator<Pasient> iterator2 = pasienter.iterator();
            Pasient runner2;
            while (iterator2.hasNext()) {
                runner2 = iterator2.next();

                Reseptregister tempRegister = runner2.getReseptliste();

                Resept[] resepter = tempRegister.finnReseptMedisin(medisin, runner);
                if (resepter.length > 0) {
                    leger.add(runner);
                    break;
                }
            }

        }

        Lege[] legeListe = new Lege[leger.size()];
        return leger.toArray(legeListe);
    }

    //Finner en eller flere pasienter basert på etternavn og fornavn.
    public Lege[] finnLegeNavn(String etternavn, String fornavn) {
        TreeSet<Lege> legeSet = finnLege(etternavn, fornavn);
        Lege[] pasienter = new Lege[legeSet.size()];
        return legeSet.toArray(pasienter);
    }

    //Returner legeregisteret som et array.
    public Lege[] getAlleLeger() {
        Lege[] leger = new Lege[legeregister.size()];
        return legeregister.toArray(leger);
    }

    //Returnerer legeregisteret
    public SortedSet<Lege> getRegister() {
        return legeregister;
    }

//Returnerer en string med all informasjon om hver lege.
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Iterator<Lege> iter = legeregister.iterator();

        while (iter.hasNext()) {
            str.append(iter.next());
            str.append("\n");
        }

        return str.toString();
    }
}
