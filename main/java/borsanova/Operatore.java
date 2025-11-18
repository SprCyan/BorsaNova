package borsanova;

import java.util.*;

/**
 * La classe <strong>Operatore</strong> è caratterizzata da un nome (non vuoto), mantiene un bilancio,
 * una lista di borse in cui le sue azioni sono quotate e la mappa per le istanze.
 * Oltre al suo costruttore e getter, l'operatore ha metodi per:
 * <ul>
 * <li>Fare richieste di acquisto e vendita {@link Operatore#acquistaAzione(Borsa, int, Borsa.Azione)} {@link Operatore#vendiAzione(Borsa, Azienda, int)}
 * <li>Depositare e prelevare dal bilancio {@link Operatore#prelievo(int)} {@link Operatore#deposito(int)}
 * <li>Controllare che il bilancio sia sempre valido {@link Operatore#checkBudget(int)}
 * <li>Contare il valore totale delle azioni possedute {@link Operatore#getValoreAzioni()}
 * </ul>
 */
public class Operatore implements Comparable<Operatore> {
    /**Mappa delle istanze dell'operatore*/
    private static final SortedMap<String, Operatore> INSTANCES = new TreeMap<>();
    /**Lista borse in cui l'operatore ha fatto acquisti o vendite*/
    private final SortedSet<Borsa> borse = new TreeSet<>();
    /**Il nome dell'operatore, (la sua unicità)*/
    private final String name;
    /**Bilancio dell'operatore (se non specificato inizia a 0)*/
    private int budget;

    /*
     * AF:
     *   Un'istanza di "Operatore" rappresenta un individuo o un'entità che mantiene:
     *   - "name" --> un nome unico per identificarlo.
     *   - "budget" --> un bilancio finanziario, che può essere positivo o 0.
     *   - "borse" --> una lista di borse in cui esso ha fatto operazioni di acquisto o vendita (ordinate alfabeticamente)
     *   - Può acquistare e vendere azioni facendo richiesta alla borsa.
     * RI:
     *   - "name" --> non deve essere null o vuoto.
     *   - "budget" --> deve essere maggiore o uguale a 0.
     *   - "borse" --> non deve essere null e non deve contenere elementi null.
     *   - Ogni elemento in "borse" deve essere un'istanza valida di "Borsa".
     */


    /**
     * Controlla l'esistenza nelle istanze di un operatore attraverso il suo nome (unicità di ogni operatore)
     * <p>
     * Questo costruttore permette, nel caso in cui una nuova istanza dell'operatore dev'essere creata, di scegliere il bilancio di partenza
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code name} --> non deve essere null o vuoto
     * <li> {@code budget} --> non deve essere negativo
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code INSTANCES} --> aggiunge un nuovo operatore alla mappa delle istanze se non presente
     * </ul>
     * @param name nome dell'operatore da controllare
     * @param budget bilancio iniziale dell'operatore
     * @return una nuova istanza se l'operatore non esisteva prima, altrimenti l'istanza dell'operatore pre-esistente
     * @throws IllegalArgumentException se il nome è vuoto oppure se il bilancio è minore di 0
     * @throws NullPointerException se il nome è null
     */
    public static Operatore of(String name, int budget) {
        if (budget < 0) {
            throw new IllegalArgumentException("il budget non può essere inferiore a 0");
        }
        if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        if (!INSTANCES.containsKey(name)) INSTANCES.put(name, new Operatore(name, budget));
        return INSTANCES.get(name);
    }

    /**
     * Controlla l'esistenza nelle istanze di un operatore attraverso il suo nome (unicità di ogni operatore)
     * <p>
     * Questo costruttore non richiede il bilancio dell'operatore, se l'istanza dell'operatore non esiste e quindi ne va creata una nuova,
     * il nuovo operatore partirà da un bilancio di 0.
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code name} --> non deve essere null o vuoto
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code INSTANCES} --> aggiunge un nuovo operatore alla mappa delle istanze se non presente
     * </ul>
     * @param name nome dell'operatore da controllare
     * @return una nuova istanza se l'operatore non esisteva prima, altrimenti l'istanza dell'operatore pre-esistente
     * @throws IllegalArgumentException se il nome è vuoto
     * @throws NullPointerException se il nome è null
     */
    public static Operatore of(String name) {
        if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        if (!INSTANCES.containsKey(name)) INSTANCES.put(name, new Operatore(name, 0));
        return INSTANCES.get(name);
    }
    /**
     * Costruttore della classe {@link Operatore}
     * @param name nome dell'operatore
     * @param budget bilancio iniziale
     */
    private Operatore(String name, int budget) {
        this.name = name;
        this.budget = budget;
    }

    /**
     * Ritorna il nome dell'operatore
     * @return nome operatore
     */
    public String getName() {
        return name;
    }

    /**
     * Ritorna il bilancio
     * @return bilancio (mai negativo)
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Restituisce l'elenco delle borse in ordine alfabetico
     * @return borse ordinate
     */
    public SortedSet<Borsa> getBorse() {
        return borse;
    }

    /**
     * Aggiunge una borsa alla lista delle borse in cui l'operatore ha fatto acquisti
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code borsa} --> non deve essere null
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code borse} --> aggiunge la borsa alla lista se non già presente
     * </ul>
     * @param borsa borsa da aggiungere
     * @throws NullPointerException se la borsa da aggiungere è nulla
     */
    private void addBorsa(Borsa borsa) {
        borse.add(Objects.requireNonNull(borsa, "Borsa non deve essere null"));
    }

    /**
     * Estrae il capitale totale dell'operatore, vale a dire il bilancio + il valore di tutte le sue azioni possedute
     * @return capitale totale
     */
    public int capitaleTotale() {
        return getBudget() + getValoreAzioni();
    }

    /**
     * Scorre la lista di borse, somma i valori di tutte le azioni possedute
     * @return il valore delle azioni possedute
     */
    public int getValoreAzioni() {
        int valoreAzioni = 0;
        for (Borsa borsa : borse) {
            if (borsa.getAzioniOperatori().containsKey(this)) {
                for (Borsa.Azione azione : borsa.getAzioniOperatori().get(this)) {
                    valoreAzioni += azione.getNumero()*azione.getPrezzo();
                }
            }
        }
        return valoreAzioni;
    }

    /**
     * Richiesta alla borsa per l'acquisto di un certo numero di azioni, se accettato preleva i soldi necessari dal bilancio.
     * L'operatore fa la richiesta e dichiara quanto vuole spendere, se c'è del resto, tornerà all'operatore.
     * Questo metodo modifica il bilancio dell'operatore {@link Operatore#prelievo(int)}
     * <p>
     * poiché il bilancio viene modificato viene chiamato {@link Operatore#checkBudget(int)}
     * per assicurarsi che non vada in negativo
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code borsaToBuy} e {@code azione} --> non devono essere null
     * <li> {@code prezzoTotale} --> deve essere >= 0
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code budget} --> riduce il budget in base al costo delle azioni acquistate
     * <li> {@code borse} --> aggiunge la borsa se non già presente nella lista
     * </ul>
     * @param borsaToBuy borsa a cui fare richiesta
     * @param azione le azioni da acquistare
     * @param prezzoTotale il prezzo totale che l'operatore spende nell'acquisto
     * @throws IllegalArgumentException se il prezzo è negativo
     * @throws NullPointerException se la borsa o l'azione sono null
     * @see Borsa#richiestaAcquisto(Operatore, int, Borsa.Azione)
     * @see borsanova.managerOperazioni.ManagerOperazioni
     */
    public void acquistaAzione(Borsa borsaToBuy, int prezzoTotale, Borsa.Azione azione) {
        if (azione == null) {
            throw new NullPointerException("azione non deve essere null");
        } else if (prezzoTotale < 0) {
            throw new IllegalArgumentException("Il prezzo non può essere negativo");
        }
        addBorsa(Objects.requireNonNull(borsaToBuy, "Borsa non deve essere null"));
        int resto = prezzoTotale % azione.getPrezzo();
        int numeroAcquisti = (prezzoTotale - resto) / azione.getPrezzo();
        numeroAcquisti = borsaToBuy.richiestaAcquisto(this, numeroAcquisti, azione);
        prelievo(numeroAcquisti * azione.getPrezzo());
    }

    /**
     * Richiesta alla borsa per la vendita di azioni possedute dall'operatore, se accettato deposita nel suo bilancio il valore della vendita.
     * Questo metodo modifica il bilancio {@link Operatore#deposito(int)}
     *<p>
     *poiché il bilancio viene modificato viene chiamato {@link Operatore#checkBudget(int)}
     *per assicurarsi che non vada in negativo
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code borsaToSell} e {@code aziendaToCheck} --> non devono essere null
     * <li> {@code numeroVendite} --> deve essere >= 0
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code budget} --> aumenta il bilancio in base al numero di azioni vendute
     * </ul>
     * @param borsaToSell borsa alla quale fare la richiesta di vendita
     * @param aziendaToCheck azienda dalla quale provengono le azioni che vogliamo vendere
     * @param numeroVendite il numero di azioni che vogliamo vendere
     * @throws IllegalArgumentException se il numero delle vendite è negativo
     * @throws NullPointerException se la borsa o l'azione sono null
     * @see Borsa#richiestaVendita(Operatore, int, Azienda)
     * @see borsanova.managerOperazioni.ManagerOperazioni
     */
    public void vendiAzione(Borsa borsaToSell, Azienda aziendaToCheck, int numeroVendite) {
        if (borsaToSell == null || aziendaToCheck == null) {
            throw new NullPointerException("Borsa o azione sono nulli");
        } else if (numeroVendite < 0) {
            throw new IllegalArgumentException("Il numero delle vendite è negativo");
        }
        int azionePrezzo = borsaToSell.getAzione(aziendaToCheck, borsaToSell).getPrezzo();
        numeroVendite = borsaToSell.richiestaVendita(this, numeroVendite, aziendaToCheck);
        deposito(azionePrezzo * numeroVendite);
    }

    /**
     * Deposita un certo valore nel bilancio
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code depositare} --> deve essere >= 0
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code budget} --> il bilancio aumenta in bases al valore {@code depositare}, il budget viene controllato per assicurarsi che rimanga positivo {@link #checkBudget(int)}
     * </ul>
     * @param depositare valore da depositare
     * @throws IllegalArgumentException se il valore da depositare è negativo
     */
    public void deposito(int depositare) {
        if (depositare < 0) {
            throw new IllegalArgumentException("Il valore da depositare non può essere negativo");
        }
        budget += depositare;
        checkBudget(budget);
    }

    /**
     * Preleva un certo valore dal bilancio
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code prelevare} --> deve essere >= 0
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code budget} --> riduce il budget del valore {@code prelevare}, il budget viene controllato per assicurarsi che rimanga positivo {@link #checkBudget(int)}
     * </ul>
     * @param prelevare valore da prelevare
     * @throws IllegalArgumentException se il valore da prelevare è negativo
     */
    public void prelievo(int prelevare) {
        if (prelevare < 0) {
            throw new IllegalArgumentException("Il valore da prelevare non può essere negativo");
        }
        budget -= prelevare;
        checkBudget(budget);
    }

    /**
     * Ogni volta che il bilancio viene modificato, dev'essere controllato per assicurarsi che non sia andato in negativo
     * @param budget bilancio corrente
     * @throws IllegalStateException se il bilancio va in negativo
     */
    private void checkBudget(int budget) {
        if (budget < 0) {
            throw new IllegalStateException("Il budget non può andare in negativo");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Operatore emp) {
            return (emp.name.equals(this.name));
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public int compareTo(Operatore o) {
        return this.name.compareTo(o.name);
    }
}
