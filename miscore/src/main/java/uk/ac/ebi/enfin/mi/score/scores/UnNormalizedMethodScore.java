package uk.ac.ebi.enfin.mi.score.scores;

import uk.ac.ebi.enfin.mi.score.ols.MIOntology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The unnormalized method score
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/03/11</pre>
 */

public class UnNormalizedMethodScore extends UnNormalizedCategoryScore {
     /**
     * This class requires a list of ontology terms as input
     *
     * @param listOfOntologyTerms
     */
    public UnNormalizedMethodScore(ArrayList<String> listOfOntologyTerms) {
        super(listOfOntologyTerms);
        setOntologyMethodScores();

         /* Terms to include automatically using OLS */
        ArrayList<String> parentTerms = new ArrayList<String>();
        parentTerms.add("MI:0013");
        parentTerms.add("MI:0090");
        parentTerms.add("MI:0254");
        parentTerms.add("MI:0255");
        parentTerms.add("MI:0401");
        parentTerms.add("MI:0428");

        Map<String, Map<String, String>> mapOfMethodTerms;
        MIOntology MIO = new MIOntology();
        mapOfMethodTerms = MIO.getMapOfTerms(parentTerms);

        setMappingParentTerms(mapOfMethodTerms);
    }

    public UnNormalizedMethodScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfMethodTerms) {
        super(listOfOntologyTerms);
        setOntologyMethodScores();
        setMappingParentTerms(mapOfMethodTerms);
    }

    private void setOntologyMethodScores(){
        /* SET ONTOLOGY SCORE */
        Map<String, Float> defaultOntologyScore = new HashMap<String, Float>();
        defaultOntologyScore.put("MI:0013", 3f); // cv1 // biophysical
        defaultOntologyScore.put("MI:0090", 2f); // cv2 // protein complementation assay
        defaultOntologyScore.put("MI:0254", 0.3f); // cv3 // genetic interference
        defaultOntologyScore.put("MI:0255", 0.3f); // cv4 // post transcriptional interference
        defaultOntologyScore.put("MI:0401", 3f); // cv5 // biochemical
        defaultOntologyScore.put("MI:0428", 0.6f); // cv6 // imagining technique
        /* Ontology terms not present in OLS will be consider null and classify as "unknown" terms */
        defaultOntologyScore.put("unknown", 0.15f); // cv7 // unknown
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
        // No terms to add manually
    }
}
