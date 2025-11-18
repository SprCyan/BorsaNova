package borsanova.Quotazioni;

import borsanova.Azienda;
import borsanova.Borsa;

import java.util.*;

/**
 * La classe <strong>Quotazione</strong> mantiene due set ordinati:
 * <ul>
 * <li>Uno per le aziende quotate {@link Quotazione#getAziende()}
 * <li>Uno per le borse quotate {@link Quotazione#getBorse()}
 * </ul>
 * <p>
 * La classe riceve un elenco di comandi che traduce in quotazioni e crea elenchi quotati.
 */
public class Quotazione {
    /**Lista delle aziende quotate nelle borse (aziende che hanno fatto una quotazione)*/
    private SortedSet<Azienda> aziende;
    /**Lista delle borse che hanno ricevuto delle quotazioni dalle aziende*/
    private SortedSet<Borsa> borse;

    /*
     * AF:
     *   Un'istanza di "Quotazione" rappresenta due elenchi: uno di aziende (quotate nelle borse)
     *   e uno borse (che ricevono le quotazioni), ordinate naturalmente e quotate.
     *   - "aziende" --> Contiene l'insieme delle aziende quotate.
     *   - "borse" --> Contiene l'insieme delle borse che hanno ricevuto le quotazioni.
     *   La classe permette di tradurre una lista di comandi in nuove quotazioni e aggiornare questi insiemi.
     * RI:
     *   - "aziende" e "borse" non devono essere null.
     *   - "aziende" e "borse" devono essere insiemi ordinati ("SortedSet").
     *   - Gli elementi in "aziende" e "borse" non devono essere null.
     *   - Dopo l'invocazione del metodo "quotazioni", entrambe le liste ("aziende" e "borse") devono contenere almeno un elemento.
     */

    /**
     * Costruttore della classe
     * @param aziende lista aziende quotate
     * @param borse borse che hanno ricevuto le quotazioni
     */
    public Quotazione(SortedSet<Azienda> aziende, SortedSet<Borsa> borse) {
        this.aziende = aziende;
        this.borse = borse;
    }

    /**
     * Traduce ogni quotazione dalla lista e compie le dovute procedure per realizzarla.
     * Alla fine del metodo, le liste delle aziende e delle borse partecipi nelle quotazioni
     * vengono salvate nella classe. ({@code borse} e {@code aziende})
     * Fa uso del metodo quotazione della classe Azienda {@link Azienda#quotazione(Borsa, int, int)}
     * <p>
     Ogni comando deve essere formattato come:
     * <p>
     *   {@code nomeAzienda} {@code nomeBorsa} {@code numeroAzioni} {@code prezzoPerAzione}
     * <p>
     * Dove:
     * <ul>
     * <li> "{@code nomeBorsa}" --> è il nome univoco della borsa
     * <li> "{@code nomeAzienda}" --> è il nome univoco dell'azienda
     * <li> "{@code numeroAzioni}" --> è un intero positivo che rappresenta il numero di azioni quotate
     * <li> "{@code prezzoPerAzione}" --> è un intero positivo che rappresenta il prezzo per l'azione
     * </ul>
     * <p>
     * Aggiorna i campi `aziende` e `borse` con i nuovi dati.
     * @param lines lista delle quotazioni
     * @throws IllegalArgumentException se i comandi ricevuti non sono formattati correttamente
     * @throws NullPointerException se alla fine del metodo le liste delle quotazioni sono vuote
     * @see Azienda#quotazione(Borsa, int, int)
     */
    public void quotazioni(String[] lines) {
        SortedSet<Borsa> listBorse = new TreeSet<>();
        SortedSet<Azienda> listAziende = new TreeSet<>();

        for (String line : lines) {
            String[] tokens = line.split(" ");
            if (tokens.length != 4) {
                throw new IllegalArgumentException("I comandi ricevuti non sono formattati correttamente per la quotazione");
            }
            Azienda azienda = Azienda.of(tokens[0]);
            Borsa borsa = Borsa.of(tokens[1]);
            azienda.quotazione(borsa, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
            listAziende.add(azienda);
            listBorse.add(borsa);
        }

        this.aziende = Objects.requireNonNull(listAziende);
        this.borse = Objects.requireNonNull(listBorse);

    }

    /**
     * Ritorna le aziende quotate
     * @return le aziende quotate (mai null)
     */
    public SortedSet<Azienda> getAziende() {
        return aziende;
    }

    /**
     * Ritorna le borse quotate
     * @return le borse quotate (mai null)
     */
    public SortedSet<Borsa> getBorse() {
        return borse;
    }
}
