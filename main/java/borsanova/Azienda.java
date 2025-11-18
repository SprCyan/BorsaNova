package borsanova;

import borsanova.Quotazioni.Quotazione;

import java.util.*;


/**
 * La classe <strong>azienda</strong> è caratterizzata da un nome (non vuoto).
 * L'oggetto, poi, mantiene una lista delle borse in cui essa è quotata.
 * <p>
 * Oltre al costruttore e il metodo getter per le borse,
 * è in grado di quotarsi in una borsa {@link #quotazione(Borsa, int, int)}.
 * <p>
 * Il metodo Quotazione permette all'azienda di quotarsi in una borsa (aggiungendo la borsa alla lista di borse quotate)
 * e successivamente invia le informazioni per le azioni che intende quotare alla borsa, la quale costruirà l'oggetto e lo manterrà
 * <p>
 * (per ulteriori informazioni su come questo avviene {@link Borsa#azioneQuotata(Azienda, int, int)})
 *
 */
public class Azienda implements Comparable<Azienda> {

    /**Mappa delle istanze dell'azienda*/
    private static final SortedMap<String, Azienda> INSTANCES = new TreeMap<>();
    /**Lista delle {@link Borsa} in cui l'azienda è quotata*/
    private final SortedSet<Borsa> borse = new TreeSet<>();
    /**Nome dell'azienda (la sua unicità)*/
    private final String name;

    /*
     * AF:
     *   Un'istanza di "Azienda" rappresenta un'azienda con un nome unico e la lista delle borse in cui è quotata.
     *   - "INSTANCES" --> Una mappa, tiene traccia di tutte le istanze esistenti di "Azienda", garantendo l'unicità per nome.
     *   - "borse" --> Un elenco, contiene le borse in cui l'azienda è attualmente quotata.
     *   - "name" --> Nome dell'azienda, la sua unicità.
     *
     * RI:
     *   - "name" --> non è null e non è vuoto.
     *   - "borse" --> non contiene elementi null e mantiene l'ordine naturale.
     *   - "INSTANCES" --> contiene tutte le istanze create della classe "Azienda".
     */


    /**
     * Ritorna un'istanza di {@code Azienda} con il nome specificato (sua unicità).
     * Se esiste già un'azienda con il nome fornito, ritorna l'istanza esistente; altrimenti ne crea una nuova.
     * @param name nome della azienda da controllare (non null e non vuoto)
     * @return una nuova istanza di {@code Azienda} non esisteva prima, altrimenti l'istanza di "Azienda" pre-esistente
     * @throws NullPointerException se il nome è null
     * @throws IllegalArgumentException se il nome è vuoto

     */
    public static Azienda of(String name) {
        if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        if (!INSTANCES.containsKey(name)) INSTANCES.put(name, new Azienda(name));
        return INSTANCES.get(name);
    }

    /**
     * Costruttore privato della classe {@link Azienda}.
     * <p>
     * Questo costruttore viene utilizzato internamente dal metodo statico {@link #of(String)} per garantire che
     * ogni azienda abbia un nome unico e che tutte le istanze siano gestite tramite la mappa {@link #INSTANCES}.
     * @param input nome dell'azienda (non null e non vuoto)
     */
    private Azienda(String input) {
       name = input;
   }

    /**
     * Quota questa azienda in una determinata borsa:
     * <p>
     * Aggiunge questa azienda alla lista delle aziende quotate in una borsa specifica,
     * con un determinato numero di azioni e un prezzo unitario. Se la borsa non è già presente
     * nella lista, viene aggiunta.
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code borse} --> aggiunge la borsa alla lista di borse in cui questa azienda è quotata
     * <li> {@code borsa} --> modifica lo stato dell'oggetto invocando il metodo {@link Borsa#azioneQuotata(Azienda, int, int)}.
     * </ul>
     *
     * @param borsa borsa a cui quotare l'azienda in stringa (non null)
     * @param numero il numero di azioni da quotare (deve essere >= 1)
     * @param prezzo il prezzo di ogni azione da quotare (deve essere >= 1)
     * @throws NullPointerException se il parametro "borsa" è null
     * @throws IllegalArgumentException se "prezzo" o "numero" sono minori di 1
     * @see Quotazione
     */
   public void quotazione(Borsa borsa, int numero, int prezzo) {
       if (prezzo < 1 || numero < 1) {
           throw new IllegalArgumentException("Il prezzo e il numero delle azioni non possono essere minori di 1");
       }
       this.borse.add(Objects.requireNonNull(borsa, "Il parametro della borsa non può essere null"));
       borsa.azioneQuotata(this, numero, prezzo);
   }

    /**
     *Restituisce la lista delle borse ordinate naturalmente in cui questa azienda è quotata
     * @return borse quotate (mai null)
     */
    public SortedSet<Borsa> getBorse() {
        return borse;
    }

    /**
     * Estrae il nome dell'azienda
     * @return nome azienda
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Azienda emp) {
            return (emp.name.equals(this.name));
        }
        else {
            return false;
        }
    }

    public int hashCode() {return Objects.hash(this.name);}

    @Override
    public int compareTo(Azienda o) {
        return this.name.compareTo(o.name);
    }
}
