package borsanova;

/**
 * La classe {@code VariazioneSoglia} implementa l'interfaccia madre {@link PoliticaPrezzo}.
 * <p>
 * Essa controlla la politica di prezzo soglia. Il suo costruttore {@link VariazioneSoglia#VariazioneSoglia(int)}
 * setta la soglia sopra la quale la politica verrà applicata
 * <p>
 * La soglia non può essere negativa, e se quest'ultima è uguale a 0
 * allora verrà applicata per tutti gli acquisti e vendite che utilizzano questa politica
 * @see PoliticaPrezzo
 */
public class VariazioneSoglia implements PoliticaPrezzo {

    /**Soglia sulla quale basare la politica*/
    private final int soglia;

     /*
     * AF:
     *   Un'istanza di "VariazioneSoglia" rappresenta una politica di prezzo che dipende dal numero di azioni
     *   acquistate o vendute rispetto a una soglia predefinita.
     *   - "soglia" --> Indica il numero minimo di azioni che deve essere superato affinché la politica modifichi il prezzo.
     *                  Se la soglia è 0, la politica si applica sempre.
     *
     * RI:
     *   - "soglia" --> è maggiore o uguale a 0.
     *
     *   Gli argomenti passati ai metodi devono rispettare le seguenti condizioni:
     *   - "azione" --> non deve essere null.
     *   - Il prezzo risultante da qualsiasi operazione non può essere inferiore a 1.
     *
     */



    /**
     * Costruttore della classe {@link VariazioneSoglia}
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code soglia} --> deve essere >= 0
     * </ul>
     * @param soglia il minimo numero necessario di azioni per applicare la politica
     *               se la soglia è 0, la politica si applica sempre
     * @throws IllegalArgumentException Se la soglia è negativa
     */
    public VariazioneSoglia(int soglia) {
        if (soglia < 0) {throw new IllegalArgumentException("La soglia non può essere negativa");}
        this.soglia = soglia;
    }

    /**
     * Applica la politica di prezzo soglia, se il numero di azioni acquistate è maggiore della {@code soglia}
     * allora il prezzo viene raddoppiato, altrimenti il prezzo rimarrà invariato
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code azione} --> non deve essere null
     * <li> {@code numeroAzioni} --> deve essere >= 0
     * </ul>
     * @param azione azione da cui ottiene il prezzo (non null)
     * @param numeroAzioni il numero di azioni acquistate (deve essere >= 0)
     * @return il nuovo prezzo (raddoppiato o invariato in base alla soglia)
     * @throws NullPointerException se l'azione è null
     * @throws IllegalArgumentException Se le azioni comprate sono negative
     */
    @Override
    public int applicaPoliticaAcquisto(Borsa.Azione azione, int numeroAzioni) {
        if (azione == null) {throw new IllegalArgumentException("L'azione non può essere null");}
        if (numeroAzioni < 0) {throw new IllegalArgumentException("Le azione acquistate non possono essere negative");}
        if (numeroAzioni > soglia) {return azione.getPrezzo()*2;}
        return azione.getPrezzo();
    }

    /**
     * Applica la politica di prezzo soglia, se il numero di azioni vendute è maggiore della {@code soglia}
     * allora il prezzo viene dimezzato, altrimenti il prezzo rimarrà invariato
     * <p>
     * se il nuovo prezzo è minore di 1, allora il prezzo diventa 1
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code azione} --> non deve essere null
     * <li> {@code numeroAzioni} --> deve essere >= 0
     * </ul>
     * @param azione azione da cui ottiene il prezzo (non null)
     * @param numeroAzioni numero di azioni vendute (deve essere >= 0)
     * @return il nuovo prezzo o 1 (dimezzato o invariato in base alla soglia)
     * @throws NullPointerException se l'azione è null
     * @throws IllegalArgumentException Se le azioni vendute sono negative
     */
    @Override
    public int applicaPoliticaVendita(Borsa.Azione azione, int numeroAzioni) {
        if (azione == null) {throw new NullPointerException("L'azione non può essere null");}
        if (numeroAzioni < 0) {throw new IllegalArgumentException("Le azione vendute non possono essere negative");}
        if (numeroAzioni > soglia) {return Math.max(azione.getPrezzo() / 2, 1);}
        return azione.getPrezzo();
    }
}
