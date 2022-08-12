import uk.ac.ebi.enfin.mi.score.scores.MIScore;

import java.util.ArrayList;
import java.util.List;

/**
 * This example show how to use MIscore to obtain confidence scores for
 * molecular interactions using standard information easy to find in any database
 * adopting the PSI-MITAB format:
 *   - Experimental detection methods found for the interaction
 *   - Number of publications
 *   - Interaction types found for the interaction
 *
 * More information in:
 *   - http://code.google.com/p/miscore/
 *
 * User: rafael
 * Date: 16-Sep-2011
 * Time: 09:43:20
 */
public class MIScoreClient {
      public static void main(String [] args){
        /* METHOD ONTOLOGY TERMS */
          /** If the same method has been reported for one interaction more than once
           * in the same publication, then the method should be included in MIscore just once.
           * If the same method has been reported for one interaction more than once
           * in different publication, then the method should be included in MIscore as many times
           *  as it appears in different publications. */
        List<String> methodInput = new ArrayList<>();
        methodInput.add("MI:0051");
        methodInput.add("MI:0051");
        methodInput.add("MI:0042");

        /* TYPE ONTOLOGY TERMS */
          /** If the same type has been reported for one interaction more than once
           * in the same publication, then the type should be included in MIscore just once.
           * If the same type has been reported for one interaction more than once
           *  in different publication, then the type should be included in MIscore as many times
           *  as it appears in different publications. */
        List<String> typeInput = new ArrayList<>();
        typeInput.add("MI:0208");
        typeInput.add("MI:0403");
        typeInput.add("MI:0407");

        /* NUMBER OF PUBMED PUBLICATION */
        Integer publicationInput = 4;

        /* SET CONFIDENCE SCORE */
        MIScore mis = new MIScore();
        mis.setMethodScore(methodInput);
        mis.setTypeScore(typeInput);
        mis.setPublicationScore(publicationInput);

        /* PRINT SCORES */
          /*  - Molecular interaction confidence score */
          System.out.println("- Molecular interaction confidence score: " + mis.getScore());
          /*  - Method score */
          System.out.println("  * Method score: " + mis.getMethodScore());
          /*  - Type score */
          System.out.println("  * Type score: " + mis.getTypeScore());
          /*  - Publication score */
          System.out.println("  * Publication score: " + mis.getPublicationScore());

      }
}
