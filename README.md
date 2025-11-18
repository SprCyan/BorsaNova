# BorsaNova

Questo repository contiene il progetto d'esame dell'insegnamento di **"Programmazione II" presso l'Università degli Studi di Milano all'a.a 2024/2025**.

Obiettivo del progetto è modellare il comportamento di alcuni **operatori** che scambiano *azioni* di alcune **aziende** quotate in varie **borse**.

Le entità coinvolte nel progetto sono: **aziende, operatori, borse e azioni**
## Azienda
Una azienda è caratterizzata da un nome (non vuoto);

ogni azienda può decidere di quotarsi in una o più delle borse presenti (al più una volta per ciascuna di esse), nel farlo decide per ciascuna borsa sia il numero di azioni che intende emettere che il loro prezzo unitario iniziale (entrambe le grandezze sono per semplicità rappresentate da numeri interi positivi). Ogni azienda tiene traccia delle borse in cui è quotata.

## Operatore
Un operatore (di borsa) è caratterizzato da un nome (non vuoto) e da un budget (rappresentato per semplicità da un numero intero non negativo, inizialmente pari a 0).

Ogni operatore può decidere di acquistare o vendere azioni di una o più aziende presenti in una o più borse. L'acquisto di azioni a fonte di una certa cifra investita comporta una diminuzione del budget pari al prezzo corrente dell'azione per il numero di azioni acquistabili (che deve essere un numero intero positivo e non può eccedere quello di azioni disponibili ancora in possesso della borsa); tale operazione, nel caso in cui la cifra investita non sia un multiplo intero del prezzo corrente dell'azione, genera un resto (che non deve mai superare il valore corrente dell'azione) che resta nel budget dell'operatore. La vendita di un dato numero di azioni comporta viceversa un aumento del budget pari al prezzo corrente dell'azione per il numero di azioni vendute (che deve essere un numero intero positivo).

Il budget può essere inoltre aumentato grazie a un deposito e diminuito grazie a un prelievo (entrambi rappresentati da numeri interi positivi), ma in nessun caso (compreso l'acquisto di azioni) può diventare negativo; è responsabilità esclusiva dell'operatore garantire che il suo budget non diventi mai negativo.

L'operatore conosce le azioni da esso correntemente possedute (ma non necessariamente dell'intera storia degli acquisti e delle vendite); grazie a tale conoscenza è in grado di determinare il suo capitale totale dato dalla somma del suo budget e del valore corrente delle azioni da esso possedute.

## Borsa e azione
Una borsa è caratterizzata da un nome (non vuoto); la borsa si occupa delle quotazioni delle varie aziende emettendo le relative azioni.

La borsa tiene inoltre traccia delle allocazioni delle azioni emesse agli operatori, in modo da poter sapere per ciascuna azienda quotata di quante azioni è in possesso ciascun operatore e quante siano ancora disponibili (ossia non siano allocate ad alcun operatore); è responsabilità esclusiva della borsa garantire che un operatore non venda più azioni di quante gliene siano correntemente allocate, o compri più azioni di quante siano disponibili al momento dell'acquisto.

La borsa può cambiare il prezzo delle azioni in seguito ad ogni vendita, o acquisto; per farlo segue una politica di prezzo che, data l'azione e il numero di quante ne sono state vendute, o comprate, indica il nuovo prezzo dell'azione; la politica di prezzo può cambiare in ogni momento, viceversa il prezzo delle azioni può cambiare esclusivamente nel momento di una compravendita (ed esclusivamente tramite l'applicazione della politica di prezzo corrente). Ad esempio, una politica di prezzo

1. **A incremento costante** è tale per cui ad ogni acquisto, il prezzo dell'azione aumenta di unità, restando invariato in caso di vendita;

2. **A decremento costante** è tale per cui ad ogni vendita il prezzo dell'azione diminuisce di unità, restando invariato in caso di acquisto;

3. **A variazione costante** che si comporta come un incremento costante (in caso di acquisto) e come un decremento costante (in caso di vendita);
  
4. altre politiche possono cambiare il prezzo a seconda dell'azione e del numero di esse che vengono scambiate: ad esempio aumentando in modo lineare il prezzo tanto più quante azioni sono vendute, oppure diminuendo il prezzo delle azioni di aziende il cui nome inizia per vocale.
