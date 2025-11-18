package borsanova.managerOperazioni;

import borsanova.Azienda;
import borsanova.Borsa;
import borsanova.Operatore;

/**
 * La classe {@code ManagerOperazioni} gestisce le operazioni finanziarie come acquisti, vendite, depositi e prelievi.
 * <p>
 * È utilizzata come helper per evitare dipendenze cicliche tra le altre classi del sistema.
 * Il metodo {@code operazione} esegue l'operazione richiesta in base al tipo di operazione specificato.
 */
public final class ManagerOperazioni {
    /*
     * AF:
     *   La classe è statica e senza stato, e si occupa di invocare i metodi appropriati sugli oggetti
     *   {@code Operatore}, {@code Borsa} e {@code Azienda} in base al tipo di operazione richiesta.
     *   Le operazioni che possono essere eseguite sono:
     *   - "b" --> per acquistare azioni
     *   - "s" --> per vendere azioni
     *   - "w" --> per effettuare un prelievo
     *   - "d" --> per effettuare un deposito
     *
     * RI:
     *   I parametri passati nel metodo {@code operazione} devo rispettare una serie di requisiti:
     *   - "op" --> non deve essere null
     *   - "operazione" --> non può essere null o vuoto, deve essere un simbolo riconosciuto come operazione (b, s, w d)
     *   - "borsa" --> non può essere null
     *   - "azienda" --> non può essere null
     *   - "numero" --> non può uguale o minore di 0
     */

    /**
     * Costruttore della classe, non lo utilizziamo poiché la classe è un helper per formattare ed eseguire correttamente le operazioni
     */
    private ManagerOperazioni() {}
    /**
     * Esegue l'operazione passata come parametro usando gli altri parametri in base al tipo di operazione:
     *<ul>
     * <li> "b" Acquista azioni {@link Operatore#acquistaAzione(Borsa, int, Borsa.Azione)}
     * <li> "s" Vendi azioni {@link Operatore#vendiAzione(Borsa, Azienda, int)}
     * <li> "w" Prelievo {@link Operatore#prelievo(int)}
     * <li> "d" Deposito {@link Operatore#deposito(int)}
     *</ul>
     * <p>
     * Effetti collaterali:
     * <ul>
     * <li> In base all'operazione verrà chiamato il corrispondete metodo che modificherà lo stato dei relativi oggetti
     * </ul>
     * Il metodo gestisce solo le eccezioni relative al parametro numero e operazione, gli altri parametri verranno controllati
     * nei metodi {@code acquistaAzione(Borsa, int, Azione)} e {@code vendiAzione(Borsa, String, int)}
     * @param op Operatore che eseguirà l'operazione (acquisto, vendita, deposito e prelievo) (non nullo, vuoto o con simbolo errato)
     * @param operazione operazione da eseguire (non nullo)
     * @param borsa borsa (per la richiesta di acquisto e vendita) (non nullo)
     * @param azienda azienda (per la richiesta di acquisto e vendita) (non nullo)
     * @param numero (per la richiesta di acquisto, vendita, deposito e prelievo) (Deve essere > 0)
     * @throws IllegalArgumentException se il simbolo dell'operazione non è corretto (null, vuoto o errato), oppure se il numero dell'operazione è minore di 0
     * @see Operatore

     */
    public static void operazione(Operatore op, String operazione, Borsa borsa, Azienda azienda, int numero) {
        if (operazione == null || operazione.isBlank()) {
            throw new IllegalArgumentException("Il simbolo dell'operazione dev'essere specificato");
        } else if (numero <= 0) {
            throw new IllegalArgumentException("Il numero dell'operazione dev'essere maggiore di 0");
        }
        switch (operazione) {
            case "b":
                Borsa.Azione azioneAcquisto = borsa.getAzione(azienda, borsa);
                op.acquistaAzione(borsa, numero, azioneAcquisto);
                break;
            case "s":
                op.vendiAzione(borsa, azienda, numero);
                break;
            case "w":
                op.prelievo(numero);
                break;
            case "d":
                op.deposito(numero);
                break;
            default:
                throw new IllegalArgumentException("Il simbolo dell'operazione è errato");
        }
    }
}
