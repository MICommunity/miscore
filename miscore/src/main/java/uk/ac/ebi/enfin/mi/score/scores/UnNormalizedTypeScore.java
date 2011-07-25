package uk.ac.ebi.enfin.mi.score.scores;

import uk.ac.ebi.enfin.mi.score.ols.MIOntology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Type score without normalization
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/03/11</pre>
 */

public class UnNormalizedTypeScore extends UnNormalizedCategoryScore {

     /**
     * This class requires a list of query ontology terms as input
     *
     * @param listOfOntologyTerms
     */
    public UnNormalizedTypeScore(ArrayList<String> listOfOntologyTerms) {
        super(listOfOntologyTerms);
        setOntologyTypeScores();

        /* RETRIEVE ONTOLGY TERMS FOR PARENT */
        ArrayList<String> parentTerms = new ArrayList<String>();
        parentTerms.add("MI:0208");
        parentTerms.add("MI:0403");
        parentTerms.add("MI:0407");
        Map<String, Map<String, String>> mapOfTypeTerms;
        MIOntology MIO = new MIOntology();
        mapOfTypeTerms = MIO.getMapOfTerms(parentTerms);
        setMappingParentTerms(mapOfTypeTerms);
    }


    public UnNormalizedTypeScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfTypeTerms) {
        super(listOfOntologyTerms);
        setOntologyTypeScores();
        setMappingParentTerms(mapOfTypeTerms);
    }

    private void setOntologyTypeScores(){
        /* SET ONTOLOGY SCORE */
        Map<String, Float> defaultOntologyScore = new HashMap<String, Float>();
        defaultOntologyScore.put("MI:0208", 0.24f); // cv1 // genetic interaction
        defaultOntologyScore.put("MI:0403", 0.15f); // cv2 // colocalization
        defaultOntologyScore.put("MI:0914", 1f); // cv3 // association
        defaultOntologyScore.put("MI:0915", 2f); // cv4 // physical association
        defaultOntologyScore.put("MI:0407", 3f); // cv5 // direct interaction
        /* Ontology terms not present in OLS will be consider null and classify as "unknown" terms */
        defaultOntologyScore.put("unknown", 0.1f); // cv6 // unknown
        setOntologyScore(defaultOntologyScore);
    }

    private void setMappingParentTerms(Map<String, Map<String, String>> mapOfTypeTerms){
        /* Update mapping automatically */
        for(String term:mapOfTypeTerms.keySet()){
            Map<String,String> children = mapOfTypeTerms.get(term);
            for(String child:children.keySet()){
                mappingParentTerms.put(child, term);
            }
            /* Add parents */
            mappingParentTerms.put(term,term);
        }
        /* Update mapping manually */
        mappingParentTerms.put("MI:0914","MI:0914");
        mappingParentTerms.put("MI:0915","MI:0915");
    }
}
