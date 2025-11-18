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
import borsanova.Quotazioni.Quotazione;

import java.util.*;

/** Client di test per alcune funzionalità relative alle <strong>quotazioni</strong>. */
public class QuotazioneClient {

  /** . */
  private QuotazioneClient() {}

  public static void main(String[] args) {
    StringBuilder inputLines = new StringBuilder();
    Scanner sc = new Scanner(System.in);
    while (sc.hasNextLine()) {
      inputLines.append(sc.nextLine()).append("\n");
    }
    String[] lines = inputLines.toString().split("\n");

    Quotazione quotazione = new Quotazione(null, null);
    quotazione.quotazioni(lines);

    SortedSet<Azienda> aziende = quotazione.getAziende();
    SortedSet<Borsa> borse = quotazione.getBorse();

    for (Azienda azienda : aziende) {
      System.out.println(azienda.getName());
      for (Borsa borsa : azienda.getBorse()) {
          System.out.println("- " + borsa.getName());
      }
    }

    for (Borsa borsa : borse) {
      System.out.println(borsa.getName());
      for (Azienda azienda : borsa.getAziende()) {
          System.out.println("- " + azienda.getName());
      }
    }

  }

  /*-
   * Scriva un {@code main} che legge dal flusso di ingresso una sequenza di
   * linee della forma
   *
   *    nome_azienda nome_borsa quantità prezzo
   *
   * Assuma che i nomi non contengano spazi. Dopo aver quotato le aziende nelle
   * borse emette nel flusso d'uscita
   *
   * - per ciascuna azienda, l'elenco delle borse in cui è quotata, poi
   * - per ciascuna borsa, l'elenco di aziende in essa quotate;
   *
   * I nomi delle borse e delle aziende devono essere uno per linea, in ordine
   * alfabetico; i nomi di borsa nel primo elenco e i azienda nel secondo devono
   * essere prefissati da "- ".
   */
}
