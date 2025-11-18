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
import borsanova.Borsa;
import borsanova.Operatore;
import borsanova.managerOperazioni.ManagerOperazioni;

import java.util.Scanner;

/** Client di test per alcune funzionalità relative alle <strong>borse</strong>. */
public class PoliticaPrezzoSogliaClient {

  /** . */
  private PoliticaPrezzoSogliaClient() {}

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    StringBuilder lines = new StringBuilder();

    while (sc.hasNextLine()) {
      lines.append(sc.nextLine()).append("\n");
    }

    String[] sections = lines.toString().split("--\\s");
    Borsa borsa = Borsa.of(args[0]);
    borsa.setPoliticaSoglia(Integer.parseInt(args[1]));

    for (String line : sections[0].split("\n")) {
      String[] parts = line.split(" ");
      Azienda azienda = Azienda.of(parts[0]);
      azienda.quotazione(borsa, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    for (String line : sections[1].split("\n")) {
      String[] parts = line.split(" ");
      Operatore op = Operatore.of(args[2], Integer.parseInt(args[3]));
      ManagerOperazioni.operazione(op, parts[0], borsa, Azienda.of(parts[1]), Integer.parseInt(parts[2]));

    }

    for (Borsa.Azione azione : borsa.getAzioni()) {
      System.out.println(azione.getNomeAzienda() + ", " + azione.getPrezzo());
    }
  }
  /*-
   * Scriva un [@code main} che riceve come parametri sulla linea di comando
   *
   *      nome_borsa soglia nome_operatore budget_iniziale
   *
   * il secondo parametro è un intero che determina la politica di prezzo della
   * borsa come segue: se l'acquisto coinvolge un numero di azioni maggiore
   * della soglia, il prezzo dell'azione raddoppia (altrimenti resta identico),
   * se la vendita coinvolge un numero di azioni maggiore della soglia, il
   * prezzo dell'azione viene diviso per due (ma senza scendere mai sotto 1,
   * resta invariato nel caso in cui la soglia non sia superata).
   *
   * Il programma quindi procede esattamente come nel caso della classe
   * PoliticaPrezzoClient, ossia: legge dal flusso in ingresso una sequenza di
   * due gruppi di linee (separati tra loro dalla linea contenente solo --)
   * ciascuno della forma descritta di seguito:
   *
   *     nome_azienda numero prezzo_unitario
   *     ...
   *     --
   *     b nome_azienda prezzo_totale
   *     ... [oppure]
   *     s nome_azienda numero_azioni
   *
   * in base al contenuto del primo blocco, quota le azioni delle aziende
   * specificate nella borsa (definita dal primo parametro sulla linea di
   * comando) secondo il numero e prezzo unitario specificati, in base al
   * contenuto del secondo blocco — una volta creato un operatore (di nome e
   * budget iniziale specificati dal terzo e quarto parametro sulla linea di
   * comando) — esegue le operazioni a seconda che il carattere che segue il
   * nome dell'operatore sia:
   *
   * - b compra azioni (dell'azienda specificata, impegnano il prezzo totale
   *   specificato),
   * - s vende azioni (dell'azienda specificata, nel numero specificato).
   *
   * Al termine della lettura il programma emette nel flusso d'uscita l'elenco
   * delle azioni (in ordine alfabetico) seguite dal prezzo (separato da una
   * virgola).
   */

}
