package borsanova;

/**L'interfaccia {@code PoliticaPrezzo}, funge da interfaccia madre per tutte le politiche di prezzo.
 * <p>
 * Al suo interno contiene i due metodi per l'acquisto e la vendita:
 * <ul>
 * <li>{@link PoliticaPrezzo#applicaPoliticaAcquisto(Borsa.Azione, int)}
 * <li>{@link PoliticaPrezzo#applicaPoliticaVendita(Borsa.Azione, int)}
 *</ul>
 * tutte le classi che gestiscono una politica di prezzo implementano questa interfaccia, la {@link Borsa} mantiene un contenitore di {@code PoliticaPrezzo}.
 * <p>
 * Sfruttando quindi il principio di sostituzione della Liskov, l'implementazione della politica di prezzo viene fatta
 * dalle rispettive classi che gestiscono quest'ultima mentre, la borsa, mantiene un contenitore generico PoliticaPrezzo
 * che all'occorrenza viene settato con la politica adeguata
 * */
public interface PoliticaPrezzo {

    /*
     * AF:
     *   Un'istanza di una classe che implementa l'interfaccia "PoliticaPrezzo" rappresenta una strategia astratta
     *   per determinare come il prezzo di un'azione cambia in seguito a operazioni di acquisto o vendita.
     *   - "applicaPoliticaAcquisto" --> definisce una regola per modificare il prezzo dopo un acquisto.
     *   - "applicaPoliticaVendita" --> definisce una regola per modificare il prezzo dopo una vendita.
     *   Ogni implementazione dell'interfaccia fornisce la logica specifica per tali operazioni.
     * RI:
     *   Poiché "PoliticaPrezzo" è un'interfaccia, non mantiene stato interno.
     *   Tuttavia, le implementazioni che derivano da questa interfaccia devono rispettare i seguenti vincoli:
     *   - "azione" --> parametro passato ai metodi non deve essere null.
     *   - "quantity" --> parametro passato ai metodi deve essere maggiore di 0.
     *   - Il prezzo risultante dai metodi "applicaPoliticaAcquisto" e "applicaPoliticaVendita" deve essere valido
     *     secondo i vincoli del dominio dell'applicazione (ad esempio, non può essere negativo, se il prezzo minimo è 0 o 1).
     */

    /**
     * Applica una certa politica di prezzo per l'acquisto
     * @param azione azione da cui ottenere informazioni utili: prezzo, azienda, borsa
     * @param quantity la quantità di azioni comprate (il numero di azioni comprate)
     * @return il nuovo prezzo dopo l'applicazione della politica di prezzo (solitamente incrementa)
     */
    int applicaPoliticaAcquisto(Borsa.Azione azione, int quantity);

    /**
     * Applica una certa politica di prezzo per la vendita
     * @param azione azione da cui ottenere informazioni utili: prezzo, azienda, borsa
     * @param quantity la quantità di azioni vendute (il numero di azioni vendute)
     * @return il nuovo prezzo dopo l'applicazione della politica di prezzo (solitamente decrementa)
     */
    int applicaPoliticaVendita(Borsa.Azione azione, int quantity);
}
