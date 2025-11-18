package borsanova;

import java.util.Objects;

/**
 * La classe {@code VariazioneVocali} implementa l'interfaccia madre {@link PoliticaPrezzo}.
 * <p>
 * Essa controlla la politica di prezzo vocali o lettera. Il suo costruttore {@link VariazioneVocali#VariazioneVocali(String)}
 * setta la lettera che verrà usata per controllare "l'idoneità" di una azione alla politica:
 * <p>
 * Quando una azione ha le iniziali o della azienda o della borsa uguali alla lettera oppure quest'ultime sono delle vocali
 * allora l'azione si considera idonea alla politica
 * <p>
 * La lettera non può essere null o vuota e se una azione non è idonea allora il suo prezzo rimarrà invariato
 * @see PoliticaPrezzo
 */
public class VariazioneVocali implements PoliticaPrezzo{

    /**Lettera di controllo*/
     private final char lettera;
    /**vocali*/
     private final String vocali = "aeiou";


    /*
     * AF:
     *   Un'istanza di "VariazioneVocali" rappresenta una politica di prezzo che dipende dalle iniziali della "borsa" e della "azienda"
     *   a cui "l'azione" è riferita, quest'ultime vengono usate per verificare "l'idoneità" di una azione attraverso il confronto
     *   con vocali e con la lettera di controllo.
     *   - "lettera" --> La lettera di controllo che, confrontata con le iniziali di "azienda" e "borsa", verifica l'idoneità della "azione".
     *   - "vocali" --> le vocali confrontate con le iniziali di "azienda" e "borsa" per verificare l'idoneità della "azione"
     *
     * RI:
     *   - "lettera" --> è minuscola, non è nulla e non è vuota.
     *
     *   Gli argomenti passati ai metodi devono rispettare le seguenti condizioni:
     *  - "azione" --> non deve essere null.
     *  - Il prezzo risultante da qualsiasi operazione non può essere inferiore a 1.
     *
     *   Tutte le lettere che vengono confrontate devono essere minuscole per evitare case sensitivity, quindi
     *   Le iniziali di azienda, borsa, la lettera di controllo e le vocali sono tutte passate come minuscole
     *   o devono essere convertite in minuscole prima del confronto.
     */

    /**
     * Il costruttore della classe {@code VariazioneVocali} richiede una stringa contenente una singola lettera
     * di controllo che verrà utilizzata per confrontare le iniziali di borsa e azienda. La lettera viene convertita
     * in minuscolo per evitare la distinzione tra maiuscole e minuscole.
     * <p>
     * Requisiti:
     * <ul>
     * <li> {@code lettera} --> non deve essere null o vuota e deve contenere una sola lettera
     * <li> {@code lettera} --> deve essere convertita in minuscolo.
     * </ul>
     * @param lettera la stringa contenente la lettera (non null, length == 1, non vuota)
     * @throws IllegalArgumentException Se la stringa non contiene solo 1 lettera o se quest'ultima è vuota
     * @throws NullPointerException Se la stringa è null
     */
    public VariazioneVocali(String lettera){
        if (lettera.length() != 1) {
            throw new IllegalArgumentException("è consentita solo una lettera");
        } else if (lettera.isBlank()) {
            throw new IllegalArgumentException("La stringa contenente la lettera non può essere vuota");
        }
        this.lettera = Objects.requireNonNull(lettera, "La stringa è null").toLowerCase().charAt(0);
    }

    /**
     * Applica la politica di prezzo vocali per l'acquisto:
     * <p>
     * se l'iniziale dell'azienda o della borsa è una vocale o la lettera di controllo, allora il prezzo verrà raddoppiato altrimenti rimarrà invariato
     * <p>
     * le iniziali sia dell'azienda sia della borsa vengono, prima di venir confrontare, rese minuscole così da evitare case sensitivity
     * Requisiti:
     * <ul>
     * <li> {@code azione} --> non deve essere null
     * </ul>
     * @param azione L'azione da cui otteniamo il prezzo e le iniziali di borsa e azienda (non nullo)
     * @param quantity il numero delle azioni acquistate (in questa politica di prezzo non viene usato)
     * @return il nuovo prezzo (raddoppiato o invariato in base all'idoneità dell'azione)
     * @throws NullPointerException se l'azione è null
     */
    @Override
    public int applicaPoliticaAcquisto(Borsa.Azione azione, int quantity) {
        if (azione == null) {throw new NullPointerException("L'azione non può essere null");}
        char letteraBorsa = azione.getNomeBorsa().toLowerCase().charAt(0);
        char letteraAzienda = azione.getNomeAzienda().toLowerCase().charAt(0);

        if (letteraBorsa == lettera || letteraAzienda == lettera ) {
            return azione.getPrezzo()*2;
        }
        else {
            for (char ch : vocali.toCharArray()) {
                if (ch == letteraBorsa || ch == letteraAzienda) {
                    return azione.getPrezzo()*2;
                }
            }
        }
        return azione.getPrezzo();
    }

    /**
     * Applica la politica di prezzo vocali per la vendita:
     * <p>
     * se l'iniziale dell'azienda o della borsa è una vocale o la lettera di controllo, allora il prezzo verrà dimezzato altrimenti rimarrà invariato
     * <p>
     * le iniziali sia dell'azienda sia della borsa vengono, prima di venir confrontare, rese minuscole così da evitare case sensitivity
     * Requisiti:
     * <ul>
     * <li> {@code azione} --> non deve essere null
     * </ul>
     * @param azione L'azione da cui otteniamo il prezzo e le iniziali di borsa e azienda (non nullo)
     * @param quantity il numero delle azioni acquistate (in questa politica di prezzo non viene usato)
     * @return il nuovo prezzo (dimezzato o invariato in base all'idoneità dell'azione)
     * @throws NullPointerException se l'azione è null
     */
    @Override
    public int applicaPoliticaVendita(Borsa.Azione azione, int quantity) {
        if (azione == null) {throw new NullPointerException("L'azione non può essere null");}
        char letteraBorsa = azione.getNomeBorsa().toLowerCase().charAt(0);
        char letteraAzienda = azione.getNomeAzienda().toLowerCase().charAt(0);

        if (letteraBorsa == lettera || letteraAzienda == lettera ) {
            return Math.max(azione.getPrezzo() / 2, 1);
        }
        else {
            for (char ch : vocali.toCharArray()) {
                if (ch == letteraBorsa || ch == letteraAzienda) {
                    return Math.max(azione.getPrezzo() / 2, 1);
                }
            }
        }
        return azione.getPrezzo();
    }
}
