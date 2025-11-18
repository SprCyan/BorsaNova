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

import borsanova.Borsa;

import java.util.*;

/** Client di test per alcune funzionalità relative alle <strong>aziende</strong>. */
public class OmonimiaBorsaClient {

  /** . */
  private OmonimiaBorsaClient() {}

  public static void main(String[] args) {
    Comparator<Borsa> borsaNome = Comparator.comparing(Borsa::getName);
    TreeSet<Borsa> borsa = new TreeSet<>(borsaNome);
    Scanner sc = new Scanner(System.in);
    while(sc.hasNextLine()) {
      borsa.add(Borsa.of(sc.nextLine()));
    }


    for(Borsa b : borsa) {
      System.out.println(b.getName());
    }
  }



  /*-
   * Scriva un {@code main} che legge dal flusso di ingresso una sequenza di
   * linee, ciascuna delle quali corrispondente a un nome di borsa ed emette
   * nel flusso d'uscita l'elenco di tali nomi di borsa in ordine alfabetico e
   * senza ripetizioni.
   */

}
