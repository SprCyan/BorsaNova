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

import java.util.*;

/** Client di test per alcune funzionalità relative alle <strong>azioni</strong>. */
public class AzioneClient {

  /** . */
  private AzioneClient() {}

  public static void main(String[] args) {
    Borsa borsa = Borsa.of(args[0]);
    Scanner sc = new Scanner(System.in);
    Azienda azienda;

    while(sc.hasNextLine()) {
      String temp = sc.nextLine();
      String[] data = temp.split(" ");
      azienda = Azienda.of(data[0]);
      azienda.quotazione(borsa, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
    }

    for (Borsa.Azione azione : borsa.getAzioni()) {
      azione.Stampa();
    }
  }


  /*-
   * Scriva un [@code main] che, ricevuto un nome di borsa come parametro sulla
   * linea di comando, legge dal flusso in ingresso una sequenza di linee della
   * forma
   *
   *     nome_azienda numero prezzo_unitario
   *
   * E dopo aver quotato l'azienda nella borsa specificata come parametro
   * produce le azioni quotate emettendo per ciascuna di esse, nel flusso
   * d'uscita, il nome dell'azienda (in ordine alfabetico) seguito da prezzo e
   * numero (separati da virgole). Assuma che il nome dell'azienda non contenga
   * spazi.
   */
}
