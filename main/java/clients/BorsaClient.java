/*

Copyright 2024 Massimo Santini

This file is part of "Programmazione 2 @ UniMI" teaching material.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This material is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this file.  If not, see <https://www.gnu.org/licenses/>.

*/

package clients;

import borsanova.Azienda;
import borsanova.Quotazioni.Quotazione;
import borsanova.managerOperazioni.ManagerOperazioni;
import borsanova.Operatore;
import borsanova.Borsa;

import java.util.*;

/** Client di test per alcune funzionalità relative alle <strong>borse</strong>. */
public class BorsaClient {

  /** . */
  private BorsaClient() {}

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    StringBuilder lines = new StringBuilder();

    while (sc.hasNextLine()) {
      lines.append(sc.nextLine()).append("\n");
    }

    String[] sections = lines.toString().split("--\\s");
    Quotazione quotazioni = new Quotazione(null, null);
    quotazioni.quotazioni(sections[0].split("\n"));

    Comparator<Operatore> operatoreNome = Comparator.comparing(Operatore::getName);
    List<Operatore> operatori = new ArrayList<>();

    for (String line : sections[1].split("\n")) {
      String[] tokens = line.split(" ");
      operatori.add(Operatore.of(tokens[0], Integer.parseInt(tokens[1])));
    }

    operatori.sort(operatoreNome);

    for (String line : sections[2].split("\n")) {
      String[] tokens = line.split(" ");
      Operatore op = operatori.get(operatori.indexOf(Operatore.of(tokens[0], 0)));
      ManagerOperazioni.operazione(op, tokens[1], Borsa.of(tokens[2]), Azienda.of(tokens[3]), Integer.parseInt(tokens[4]));
    }

    for (Borsa borsa : quotazioni.getBorse()) {
      System.out.println(borsa.getName());
      for (Azienda azienda : borsa.getAziende()) {
        System.out.println("- " + azienda.getName() + " " + borsa.getAzione(azienda, borsa).getNumero());
        for (Map.Entry<Operatore, SortedSet<Borsa.Azione>> operatore : borsa.getAzioniOperatori().entrySet()) {
          Operatore op = operatore.getKey();
          for (Borsa.Azione azione : operatore.getValue()) {
            if (azione.getBorsa().equals(borsa) && azione.getAzienda().equals(azienda)) {
              System.out.println("= " + op.getName() + " " + azione.getNumero());
            }
          }
        }
      }
    }
  }


   /*-
   * Scriva un [@code main} che legge dal flusso in ingresso una sequenza di tre
   * gruppi di linee (separati tra loro dalla linea contenente solo --) ciascuno
   * della forma descritta di seguito:
   *
   *     nome_azienda nome_borsa numero prezzo_unitario
   *     ...
   *     --
   *     nome_operatore budget_iniziale
   *     ...
   *     --
   *     nome_operatore b nome_borsa nome_azienda prezzo_totale
   *     ... [oppure]
   *     nome_operatore s nome_borsa nome_azienda numero_azioni
   *
   * Assuma che i nomi non contengano spazi. Iqn base al contenuto del primo
   * blocco, quota le azioni delle aziende nelle borse secondo il numero e
   * prezzo unitario specificati, in base al secondo blocco crea gli operatori
   * specificati con il budget iniziale specificato e in base al terzo blocco
   * esegue le operazioni, a seconda che il carattere che segue il nome
   * dell'operatore sia:
   *
   * - b compra azioni (quotate nella borsa e dell'azienda specificata,
   *   impegnano il prezzo totale specificato),
   * - s vende azioni (quotate nella borsa e dell'azienda specificata, nel
   *   numero specificato).
   *
   * Osservi che l'acquisto può determinare un resto, nel caso in cui il prezzo
   * totale non sia un multiplo esatto del prezzo dell'azione; tale resto rimane
   * a disposizione dell'operatore per eventuali operazioni successive.
   *
   * Al termine della lettura il programma emette nel flusso d'uscita (una per
   * linea) l'elenco delle borse coinvolte (in ordine alfabetico), per ogni
   * borsa emette l'elenco delle azioni in essa quotate (in ordine alfabetico,
   * prefissate da - e seguite dal numero di azioni ancora disponibili), e per
   * ognuna di esse i nomi degli operatori e delle quantità che ne possiedono
   * (in ordine alfabetico, prefissati da =).
   */
}
