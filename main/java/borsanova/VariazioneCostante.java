package borsanova;

/**
 * La classe {@code VariazioneCostante} implementa l'interfaccia madre {@link PoliticaPrezzo}.
 * <p>
 * Essa controlla le politiche di prezzo a incremento e decremento costante, la classe ha due costruttori:
 * <ul>
 *  <li> {@link VariazioneCostante#VariazioneCostante(int)} Che gestisce o l'incremento costante o il decremento costante
 *  <li> {@link VariazioneCostante#VariazioneCostante(int, int)} Che gestisce la variazione costante (incremento e decremento)
 * </ul>
 * <p>
 * nel caso in cui l'incremento o il decremento non vengano settati dal costruttore
 * allora nell'applicazione della politica di prezzo non modificherà il prezzo dell'azione
 * @see PoliticaPrezzo
 */
public class VariazioneCostante implements PoliticaPrezzo {

    /**L'incremento costante (inizia a 0 e non modifica il prezzo se rimane tale)*/
    private int incremento = 0;
    /**Il decremento costante (inizia a 0 e non modifica il prezzo se rimane tale)*/
    private int decremento = 0;

    /*
     * AF:
     *   Un'istanza di "VariazioneCostante" rappresenta una politica di prezzo che modifica il valore di un'azione
     *   in base a incrementi o decrementi costanti.
     *   - "incremento" --> Rappresenta il valore da aggiungere al prezzo di un'azione in caso di acquisto.
     *   - "decremento" --> Rappresenta il valore da sottrarre al prezzo di un'azione in caso di vendita.
     *     (se il decremento porta il prezzo sotto 1, il prezzo è fissato a 1).
     *
     *
     * RI:
     *   - "incremento" --> è maggiore o uguale a 0.
     *   - "decremento" --> è minore o uguale a 0.
     *   Gli argomenti passati ai metodi devono rispettare le seguenti condizioni:
     *   - "azione" --> non deve essere null
     *   - Il prezzo risultante da qualsiasi operazione non può essere inferiore a 1.
     */


    /**
     * Costruttore della classe {@link VariazioneCostante} che configura una politica di prezzo con un singolo valore di variazione.
     * Se il valore è positivo, viene interpretato come incremento; se negativo, come decremento.
     * @param quantity valore della variazione (positivo per incremento, negativo per decremento).
     *                 Se pari a 0, la politica non modifica il prezzo.
     */
    public VariazioneCostante(int quantity) {
        if (quantity > 0) {this.incremento = quantity;}
        else if (quantity < 0) {this.decremento = quantity;}
    }

    /**
     * Costruttore della classe {@link VariazioneCostante} che configura una politica di prezzo a variazione costante
     * specificando sia l'incremento che il decremento. Se entrambi i valori (incremento e decremento) sono pari a 0,
     * la politica non modifica il prezzo.
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code incremento} --> deve essere >= 0
     * <li> {@code decremento} --> deve essere >= 0
     * </ul>
     * @param incremento il valore costante da aggiungere al prezzo di un'azione durante un acquisto. (deve essere maggiore o uguale 0)
     * @param decremento il valore costante da sottrarre al prezzo di un'azione durante una vendita. (deve essere minore o uguale 0)
     * @throws IllegalArgumentException Se l'incremento è negativo o il decremento è positivo
     */
    public VariazioneCostante(int incremento, int decremento) {
        if (incremento < 0) {throw new IllegalArgumentException("L'incremento deve essere positivo");}
        if (decremento > 0) {throw new IllegalArgumentException("L'decremento deve essere negativo");}
        this.incremento = incremento;
        this.decremento = decremento;
    }

    /**
     * Applica la politica di prezzo a incremento costante, somma prezzo e incremento
     * e restituisce il valore così ottenuto
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code azione} --> non deve essere null
     * </ul>
     * @param azione azione da cui ottiene il prezzo (non null)
     * @param quantity numero di azioni comprate (ignorato in questa implementazione)
     * @return il nuovo prezzo dell'azione
     * @throws IllegalArgumentException se l'azione è nulla
     */
    @Override
    public int applicaPoliticaAcquisto(Borsa.Azione azione, int quantity) {
        if (azione == null) {throw new IllegalArgumentException("L'azione non può essere null");}
        return azione.getPrezzo() + this.incremento;
    }

    /**
     * Applica la politica di prezzo a decremento costante, sottrae prezzo e decremento e restituisce il valore così ottenuto,
     * se il valore è minore di 1 allora ritorna 1
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code azione} --> non deve essere null
     * </ul>
     * @param azione azione da cui ottiene il prezzo (non null)
     * @param quantity numero di azioni comprate (ignorato in questa implementazione)
     * @return il nuovo prezzo dell'azione (se minore di 1 allora ritorna 1)
     * @throws IllegalArgumentException se l'azione è nulla
     */
    @Override
    public int applicaPoliticaVendita(Borsa.Azione azione, int quantity) {
        if (azione == null) {throw new IllegalArgumentException("L'azione non può essere null");}
        return Math.max((azione.getPrezzo() + this.decremento), 1);
    }
}
