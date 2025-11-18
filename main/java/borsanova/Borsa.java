package borsanova;



import java.util.*;

/**
 * La classe <strong>borsa</strong> è caratterizzata da un nome (non vuoto), tiene traccia delle aziende quotate e delle loro azioni,
 * gestisce le richieste di acquisto e di vendita, le politiche di prezzo e comparatori per l'ordinamento.
 * Mantiene una mappa degli operatori che hanno fatto acquisti o vendite nelle azioni quotate e una mappa per le istanze.
 * Essa contiene, oltre ai suoi personali metodi setter, getter e costruttore, metodi per:
 * <ul>
 * <li>Gestire le istanze e verificare l'unicità {@link #of(String)}
 * <li>Impostare e far valere la politica di prezzo
 * <li>Gestire le richieste di acquisto e vendita {@link #richiestaAcquisto(Operatore, int, Azione)} {@link #richiestaVendita(Operatore, int, Azienda)}
 * <li>Gestire la quotazione {@link #azioneQuotata(Azienda, int, int)}
 * <li>Estrarre elementi dalla mappa Operatore-AzioniPossedute {@link #getAzioneOp(Operatore, Azienda, Borsa)}
 * </ul>
 * <p>
 * La classe borsa è l'unica classe che può accedere a costruttore e setter della classe Azione.
 * Ha una subclass, VariazionePoliticaPrezzo, che, in base al comando passato,
 * estrae l'incremento e il decremento della politica di prezzo
 */
public class Borsa  implements Comparable<Borsa> {
    /**Mappa delle istanze della borsa*/
    private static final SortedMap<String, Borsa> INSTANCES = new TreeMap<>();
    /**Mappa degli operatori che hanno fatto acquisti o vendite in borsa e delle loro relative azioni {@link Azione}*/
    private final SortedMap<Operatore, SortedSet<Azione>> azioniOperatori = new TreeMap<>();
    /**Lista delle aziende quotate in borsa*/
    private final SortedSet<Azienda> aziende = new TreeSet<>();
    /**Lista delle azioni delle aziende quotate {@link Azione}*/
    private final SortedSet<Azione> azioni = new TreeSet<>();
    /**Nome della borsa (sua unicità)*/
    private final String name;
    /**Contenitore della politica prezzo, se settata applicherà la politica ad acquisti e vendite*/
    private PoliticaPrezzo politicaPrezzo;

    /*
     * AF:
     *   Un'istanza di "Borsa" rappresenta una borsa valori che tiene traccia di:
     *   - "name" --> un nome che la identifica.
     *   - "aziende" --> elenco di aziende che hanno azioni quotate nella borsa ordinate alfabeticamente.
     *   - "azioni" --> elenco delle azioni relative alle aziende quotate ordinate alfabeticamente secondo i nomi delle aziende.
     *   - `azioniOperatori` --> una mappa degli operatori e delle azioni che possiedono
     *                           ordinate rispettivamente: alfabeticamente per gli operatori,
     *                           e alfabeticamente secondo i nomi delle aziende per le azioni
     *   - "politicaPrezzo" --> un contenitore della politica di prezzo per regolare le variazioni di prezzo su acquisti e vendite.
     *   - Una classe interna "Azione" per rappresentare i dettagli delle azioni quotate.
     *
     * RI:
     *   - "name" --> non deve essere null o vuoto.
     *   - "aziende" e "azioni" --> non devono essere null.
     *   - "azioniOperatori" --> non deve essere null.
     *   - Gli elementi in "aziende", "azioni" e "azioniOperatori" --> non devono essere null.
     *   - Ogni chiave in "azioniOperatori" deve essere un oggetto "Operatore" valido.
     *   - Ogni valore in "azioniOperatori" deve essere un insieme ordinato ("SortedSet") di oggetti "Azione".
     *   - Se presente, "politicaPrezzo" deve essere un'istanza valida di una classe che implementa l'interfaccia "PoliticaPrezzo".
     *   - Le azioni contenute in "azioni" devono essere associate a una "azienda" e a una "borsa".
     *   - Il prezzo e il numero di ogni azione devono essere >= 1.
     */

    /**
     * Controlla l'esistenza nelle istanze di una borsa attraverso il suo nome (unicità di ogni borsa)
     * @param name nome della borsa da controllare
     * @return una nuova istanza della borsa non esisteva prima, altrimenti l'istanza della borsa pre-esistente
     * @throws IllegalArgumentException se il nome è vuoto
     * @throws NullPointerException se il nome è null
     */
    public static Borsa of(String name) {
        if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        if (!INSTANCES.containsKey(name)) INSTANCES.put(name, new Borsa(name));
        return INSTANCES.get(name);
    }


    /**
     * Costruttore della classe {@link Borsa}
     * @param input nome della borsa (unicità di quest'ultima)
     */
    private Borsa(String input) {
        name = input;
    }

    /**
     * Setta la politica di prezzo a incremento costante o decremento costante
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code politicaPrezzo} --> il campo che verrà sovrascritto per accomodare la politica prezzo.
     * </ul>
     * @param quantity la politica di prezzo costante da settare
     */
    public void setPoliticaCostante(int quantity) {
        politicaPrezzo = new VariazioneCostante(quantity);

    }

    /**
     * Setta la politica di prezzo a variazione costante
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code politicaPrezzo} --> il campo che verrà sovrascritto per accomodare la politica prezzo.
     * </ul>
     * @param incremento incremento costante
     * @param decremento decremento costante
     */
    public void setPoliticaCostante(int incremento, int decremento) {
        politicaPrezzo = new VariazioneCostante(incremento, decremento);

    }

    /**
     * Setta la politica prezzo soglia
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code politicaPrezzo} --> il campo che verrà sovrascritto per accomodare la politica prezzo.
     * </ul>
     * @param soglia soglia da settare
     */
    public void setPoliticaSoglia(int soglia) {
        politicaPrezzo = new VariazioneSoglia(soglia);
    }

    /**
     * Setta la politica prezzo vocali o lettera
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code politicaPrezzo} --> il campo che verrà sovrascritto per accomodare la politica prezzo.
     * </ul>
     * @param lettera Stringa che contiene la lettera da utilizzare per la politica prezzo vocali e lettera
     */
    public void setPoliticaVocali(String lettera) {
        politicaPrezzo = new VariazioneVocali(lettera);
    }


    /**
     * Cambia il prezzo dell'azione in base alla politica di prezzo per l'acquisto
     * <p>
     * se non è stata settata alcuna politica di prezzo ({@code politicaPrezzo} è {@code null}), il metodo non modificherà nulla
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code azione} --> non deve essere null
     * <li> {@code quantity} --> deve essere maggiore di 0
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> Se la politica prezzo è diversa da null, modifica {@code azione.prezzo}
     * </ul>
     * @param azione azione a cui cambiare il prezzo
     * @param quantity numero delle azioni acquistate
     */
    private void cambiaPrezzoAcquisto(Azione azione, int quantity) {
        if (politicaPrezzo != null) {azione.prezzo = politicaPrezzo.applicaPoliticaAcquisto(azione, quantity);}
    }

    /**
     * Cambia il prezzo dell'azione in base alla politica di prezzo pre la vendita,
     * il metodo si assicura che il prezzo non possa scendere sotto 1
     * <p>
     * se non è stata settata alcuna politica di prezzo ({@code politicaPrezzo} è {@code null}), il metodo non modificherà nulla
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code azione} --> non deve essere null
     * <li> {@code quantity} --> deve essere maggiore di 0
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> Se la politica prezzo è diversa da null, modifica {@code azione.prezzo}
     * </ul>
     * @param azione azione a cui cambiare il prezzo
     * @param quantity numero delle azioni vendute
     */
    private void cambiaPrezzoVendita(Azione azione, int quantity) {
        if (politicaPrezzo != null) {azione.prezzo = politicaPrezzo.applicaPoliticaVendita(azione, quantity);}
    }

    /**
     * Riceve una richiesta di acquisto e modifica le azioni coinvolte. Fa un controllo in mappa Operatore-AzioniPossedute:
     * <ul>
     * <li> Se l'operatore non possedeva questo oggetto, allora ne crea uno nuovo e lo inserisce nella lista delle azioni possedute dell'operatore
     * <li>Se l'operatore possedeva questo oggetto, allora ne aumenta il numero in base alla richiesta della acquisto
     * </ul>
     * <p>
     * Se la richiesta è maggiore del numero disponibile all'acquisto, allora il numero verrà ridotto al massimo di azioni disponibili.
     * Se il numero delle azioni disponibili all'acquisto è 0 (non ci sono azioni disponibili per l'acquisto),
     * allora il codice passerà senza errore ma senza cambiamenti alle azioni
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code op} --> non deve essere null
     * <li> {@code azienda} --> non deve essere null
     * <li> {@code numeroAcquisti} --> deve essere maggiore di 0
     * <li> {@code azione} --> deve esistere nella lista di azioni quotate
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code azione} --> riduce il numero delle azioni disponibili {@link #azioni}
     * <li> {@code azioniOperatori} --> aggiunge o aggiorna l'azione all'insieme delle azioni possedute dall'operatore {@link #azioniOperatori}
     * <li> {@code azione.prezzo} --> se presente, aggiorna il prezzo applicando la politica di prezzo {@link #cambiaPrezzoAcquisto(Azione, int)}
     * </ul>
     * @param op l'operatore che fa la richiesta
     * @param numeroAcquisti il numero di acquisti della richiesta
     * @param azione l'azione alla quale è posta la richiesta di acquisto
     * @return il numero di acquisti corretto, oppure 0 se non ci sono azioni disponibili all'acquisto
     * @throws NullPointerException se almeno uno dei parametri è nullo
     * @throws IllegalArgumentException se il numero degli acquisti è negativo
     *
     */
    public int richiestaAcquisto(Operatore op, int numeroAcquisti, Azione azione) {
        if (op == null || numeroAcquisti == 0 || azione == null) {
            throw new NullPointerException("Almeno uno dei parametri è nullo");
        } else if (numeroAcquisti < 0) {
            throw new IllegalArgumentException("Numero degli acquisti non può essere negativo");
        } else if (azione.getNumero() == 0) {
            return 0;
        }
        int azioneNumero = azione.getNumero();
        int newNumeroAcquisti;
        if (azioneNumero - numeroAcquisti < 0) {
            azione.setNumero(0);
            newNumeroAcquisti = numeroAcquisti - (numeroAcquisti - azioneNumero);
        } else if (azioneNumero - numeroAcquisti == 0) {
            azione.setNumero(0);
            newNumeroAcquisti = numeroAcquisti;
        } else {
            azione.setNumero(azioneNumero - numeroAcquisti);
            newNumeroAcquisti = numeroAcquisti;
        }
        if (getAzioneOp(op, azione.getAzienda(), azione.getBorsa()) != null) {
            Azione opazione = getAzioneOp(op, azione.getAzienda(), azione.getBorsa());
            opazione.setNumero(opazione.getNumero() + newNumeroAcquisti);
        } else {
            Azione opazione = new Azione(azione.getAzienda(), azione.getBorsa(), azione.getPrezzo(), newNumeroAcquisti);
            this.addAzioniOperatori(op, opazione);

        }
        this.cambiaPrezzoAcquisto(azione, newNumeroAcquisti);
        return newNumeroAcquisti;
    }

    /**
     * Riceve una richiesta di vendita, controlla che il numero di vendite sia corretto e modifica le azioni coinvolte.
     * Nel caso in cui l'azione dell'operatore dovesse azzerarsi, essa verrà l'eliminata dalla lista delle azioni possedute da quest'ultimo.
     * <p>
     * Nel caso in cui la richiesta di vendita dovesse essere maggiore del numero disponibile, allora la richiesta verrà ridotta e aggiornata.
     * Se la richiesta viene da una azienda che non aveva azioni disponibili, crea una nuova azione nella lista delle azioni quotate all'azienda
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code op} --> non deve essere null
     * <li> {@code azienda} --> non deve essere null
     * <li> {@code numeroVendita} --> deve essere maggiore di 0
     * <li> L'operatore {@code op} deve possedere azioni dell'azienda specificate nella borsa
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code azioneOp.numero} --> riduce il numero delle azioni possedute dall'operatore {@link #azioniOperatori}
     * <li> {@code azioniOperatori} --> rimuove l'azione dalle azioni possedute dall'operatore se le vende tutte {@link #azioniOperatori}
     * <li> {@code azioneBorsa.numero} --> aumenta il numero delle azioni disponibili in borsa {@link #azioni}
     * <li> {@code azioneBorsa.prezzo} --> se presente, modifica il prezzo dell'azione con la politica di prezzo {@link #cambiaPrezzoVendita(Azione, int)}
     * </ul>
     * @param op operatore che fa la richiesta
     * @param numeroVendita numero di azioni da vendere in richiesta
     * @param azienda azienda da cui provengono le azioni da vendere
     * @return il numero di vendita aggiornato
     * @throws NullPointerException se almeno uno dei parametri è nullo o se l'operatore non possiede L'oggetto Azione
     * @throws IllegalArgumentException se il numero delle vendite è negativo
     */
    public int richiestaVendita(Operatore op, int numeroVendita, Azienda azienda) {
        if (op == null || numeroVendita == 0 || azienda == null) {
            throw new NullPointerException("Almeno uno dei parametri è nullo");
        } else if (numeroVendita < 0) {
            throw new IllegalArgumentException("Numero delle vendite non può essere negativo");
        }
        Azione azioneOp = Objects.requireNonNull(this.getAzioneOp(op, azienda, this));

        int newNumeroVendita;
        if (azioneOp.getNumero() - numeroVendita < 0) {
            newNumeroVendita = numeroVendita - (numeroVendita - azioneOp.getNumero());
            getAzioniOperatori().get(op).remove(azioneOp);
        } else if (azioneOp.getNumero() - numeroVendita == 0) {
            newNumeroVendita = numeroVendita;
            getAzioniOperatori().get(op).remove(azioneOp);
        } else {
            newNumeroVendita = numeroVendita;
        }
        Azione azioneBorsa = this.getAzione(azioneOp.getAzienda(), azioneOp.getBorsa());
        this.cambiaPrezzoVendita(azioneBorsa, newNumeroVendita);
        azioneBorsa.setNumero(azioneBorsa.getNumero() + numeroVendita);
        azioneOp.setNumero(azioneOp.getNumero() - newNumeroVendita);
        return newNumeroVendita;
    }

    /**
     * Estrae dalla mappa Operatore-azioniPossedute l'azione con borsa e azienda uguali ai parametri in ingresso
     * controlla che la mappa contenga la chiave giusta, altrimenti ritorna null.
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code op} --> non deve essere null
     * <li> {@code azienda} --> non deve essere null
     * <li> {@code borsa} --> non deve essere null
     * <li> Se l'operatore {@code op} non è presente nella mappa {@code azioniOperatori}, il metodo restituisce null.
     * </ul>
     * @param op Operatore (il key della mappa)
     * @param azienda azienda, parametro da comparare con le azioni possedute
     * @param borsa borsa, parametro da comparare con le azioni possedute
     * @return null se non trova l'azione, altrimenti l'azione desiderata
     * @throws IllegalArgumentException se uno dei parametri è nullo
     */
    public Azione getAzioneOp(Operatore op, Azienda azienda, Borsa borsa) {
        if (op == null || azienda == null || borsa == null) {
            throw new IllegalArgumentException("almeno uno dei parametri è nullo");
        }
        if (azioniOperatori.containsKey(op)) {
            for (Azione azione : azioniOperatori.get(op)) {
                if (azione.getAzienda().equals(azienda) && azione.getBorsa().equals(borsa)) {
                    return azione;
                }
            }
        }
        return null;
    }

    /**
     * Estrae la mappa azioniOperatori
     * @return la mappa
     */
    public SortedMap<Operatore, SortedSet<Azione>> getAzioniOperatori() {
        return azioniOperatori;
    }

    /**
     * Aggiunge un nuova azione alla mappa azioniOperatori.
     * <ul>
     * <li>Se l'operatore è già esistente in mappa allora aggiunge l'azione alla lista di quest'ultimo
     * <li>Se l'operatore non esiste in lista allora crea una nuova key con quest'ultimo e aggiunge l'azione
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code azioniOperatori} --> se l'operatore esisteva in mappa, allora aggiunge alle azioni possedute l'oggetto {@code Azione} altrimenti crea una nuova chiave {@code Operatore} e un nuovo elenco associato che contiene l'oggetto {@code Azione}
     * </ul>
     * @param operatore l'operatore (key della mappa)
     * @param azione l'azione da aggiungere (value della mappa)
     */
    private void addAzioniOperatori(Operatore operatore, Azione azione) {
        SortedSet<Azione> newListAzioni = new TreeSet<>();
        newListAzioni.add(azione);
        if (getAzioniOperatori().containsKey(operatore)) {
            getAzioniOperatori().get(operatore).add(azione);
        } else {
            getAzioniOperatori().put(operatore, newListAzioni);
        }
    }

    /**
     * Riceve la quotazione dell'azienda ne crea l'azione adeguata. Le eccezioni sono gestite nel metodo Quotazione dell'azienda
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code azienda} --> non deve essere null
     * <li> {@code prezzo} --> deve essere >= 1
     * <li> {@code numero} --> deve essere >= 1
     * </ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> {@code aziende} --> aggiunge l'azienda all'elenco delle aziende quotate se non presente
     * <li> {@code azioni} --> aggiunge una nuova azione associata all'azienda
     * </ul>
     * @param azienda azienda quotata
     * @param prezzo prezzo dell'azione
     * @param numero numero di azioni
     * @return ritorna l'oggetto "azione" riferito a una azienda in una borsa specifica
     * @throws IllegalArgumentException se l'azienda è nulla oppure se il prezzo o il numero è negativo
     * @see Azienda#quotazione(Borsa, int, int)
     */
    public Azione azioneQuotata(Azienda azienda, int prezzo, int numero) {
        if (azienda == null ) {
            throw new IllegalArgumentException("L'azienda è nulla");
        } else if (prezzo < 0 || numero < 0) {
            throw new IllegalArgumentException("Il prezzo e il numero di azioni non può essere negativo");
        }
        Azione azione = new Azione(azienda, this, numero, prezzo);
        this.aziende.add(azienda);
        this.azioni.add(azione);
        return azione;
    }

    /**
     * Estrae il nome della borsa
     * @return nome della borsa
     */
    public String getName() {
        return name;
    }

    /**
     * Estrae le azioni quotate in ordine alfabetico secondo l'azienda da cui provengono
     * @return azioni ordinate
     */
    public SortedSet<Azione> getAzioni() {
        return azioni;
    }

    /**
     * Estrae le aziende quotate in ordine alfabetico
     * @return aziende ordinate
     */
    public SortedSet<Azienda> getAziende() {
        return aziende;
    }

    /**
     * Trova l'azione dalla lista delle azioni quotate che ha la stessa "azienda" e "borsa" passati come parametri.
     * @param azienda azienda da comparare (non null)
     * @param borsa borsa da comparare (non null)
     * @throws NullPointerException se uno dei parametri è null
     * @return ritorna l'azione trovata, se non trova nessuna azione ritorna null
     */
    public Azione getAzione(Azienda azienda, Borsa borsa) {
        if (azienda == null || borsa == null) {
            throw new NullPointerException("i parametri non possono essere null");
        }
        for (Azione azione : azioni) {
            if (azione.getAzienda().equals(azienda) && azione.getBorsa().equals(borsa)) {
                return azione;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Borsa emp) {
            return (emp.name.equals(this.name));
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Borsa o) {
        return this.name.compareTo(o.name);
    }

    /**
     *La classe <strong>Azione</strong> fa sempre riferimento a una azienda e una borsa da cui essa proviene, ma questo non implica
     * una uguaglianza tra azioni che hanno gli stessi riferimenti, poiché l'unicità di una azione è decisa esclusivamente
     * da chi la mantiene: se l'azione si trova nella lista delle azioni della Borsa allora il suo proprietario è l'azienda,
     * altrimenti, se si trova nella mappa Operatore-AzioniPossedute sempre della Borsa, allora il suo proprietario è l'operatore chiave della mappa ({@link Borsa})
     * <p>
     * La classe inoltre mantiene il prezzo della singola azione e il numero delle azioni totali.
     * I costruttori e i setter sono private, il che significa che l'accesso è riservato
     * solo alla borsa, la quale può creare nuove azioni o modificare pre esistenti.
     */
    public static class Azione implements Comparable<Azione> {
        /**Azienda quotata da cui proviene l'azione*/
        private final Azienda azienda;
        /**Borsa alla quale l'azienda è quotata*/
        private final Borsa borsa;
        /**Prezzo della singola azione*/
        private int prezzo;
        /**Quantità di azioni disponibili*/
        private int numero;

        /*
         * AF:
         *   Un'istanza di "Azione" rappresenta una singola azione quotata in borsa che mantiene:
         *   - "azienda" --> l'azienda a cui appartiene
         *   - "borsa" --> la borsa in cui è quotata
         *   - "prezzo" --> il prezzo della singola azione
         *   - "numero" --> il numero di azioni disponibili / possedute
         *   L'accesso e la modifica dello stato dell'oggetto "Azione" sono limitati alla classe "Borsa".
         * RI:
         *   - "azienda" e "borsa" --> non devono essere null.
         *   - "prezzo" --> deve essere >= 1.
         *   - "numero" --> deve essere >= 1.
         */



        /**
         * Costruttore della classe
         * <p>
         * Questo costruttore è privato e accessibile solo dalla classe `Borsa`, per garantire
         * che le azioni possano essere create e modificate solo nel contesto della borsa.
         * <p>
         * Requisiti:
         * <ul>
         * <li> {@code azienda} --> non deve essere null
         * <li> {@code borsa} --> non deve essere null
         * <li> {@code prezzo} --> deve essere >= 1
         * <li> {@code numero} --> deve essere >= 1
         * </ul>
         * @param azienda azienda quotata da cui proviene l'azione (non null)
         * @param borsa borsa alla quale l'azienda è quotata (non null)
         * @param prezzo prezzo della singola azione (deve essere >= 1)
         * @param numero numero di azioni (deve essere >= 1)
         * @throws NullPointerException se la borsa o l'azienda sono null
         * @throws IllegalArgumentException se il prezzo o il numero di azioni è minore di 1
         * @see Borsa
         */
        private Azione(Azienda azienda, Borsa borsa, int prezzo, int numero) {
            if (prezzo < 1 || numero < 1) {
                throw new IllegalArgumentException("il prezzo o il numero non possono essere inferiori a 1");
            }
            this.azienda = Objects.requireNonNull(azienda);
            this.prezzo = prezzo;
            this.numero = numero;
            this.borsa = Objects.requireNonNull(borsa);
        }

        /**
         * Estrae il nome della azienda da cui proviene l'azione
         * @return nome azienda
         */
        public String getNomeAzienda() {
            return azienda.getName();
        }

        /**
         * Estrae l'azienda da cui proviene l'azione
         * @return azienda
         */
        public Azienda getAzienda() {
            return azienda;
        }

        /**
         * Estrae il nome della borsa a cui l'azienda dell'azione è quotata
         * @return nome borsa
         */
        public String getNomeBorsa() {
            return borsa.getName();
        }

        /**
         * Estrae la borsa a cui l'azienda dell'azione è quotata
         * @return borsa
         */
        public Borsa getBorsa() {
            return borsa;
        }

        /**
         * Imposta il numero delle azioni (Borsa only)
         * <p>
         * Requisiti:
         * <ul>
         * <li> {@code numero} --> deve essere >= 0
         * </ul>
         * <p>
         * Effetti collaterali:
         * <ul>
         * <li> {@code this.numero} --> aggiorna il valore del numero di questa azione
         * </ul>
         * @param numero nuovo numero delle azioni da impostare
         * @see Borsa
         */
        private void setNumero(int numero) {
            this.numero = numero;
        }

        /**
         * Estrae il numero delle azioni
         * @return numero azioni
         */
        public int getNumero() {
            return numero;
        }

        /**
         * Estrae il prezzo della singola azione
         * @return prezzo azione
         */
        public int getPrezzo() {
            return prezzo;
        }

        /**
         * Stampa l'azione secondo richiesta del client:
         * <p>
         * *nomeAzienda*, prezzo, numeroAzioni
         * <p>
         * Effetti collaterali:
         * <ul>
         * <li> Stampa su {@code System.out}
         * </ul>
         */
        public void Stampa() {
            System.out.println(azienda.getName() + ", " + prezzo + ", " + numero);
        }

        @Override
        public int compareTo(Azione o) {
            return this.getNomeAzienda().compareTo(o.getNomeAzienda());
        }
    }
}



