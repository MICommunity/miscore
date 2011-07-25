package uk.ac.ebi.enfin.mi.score.scores;

import uk.ac.ebi.enfin.mi.score.CategoryScore;
import uk.ac.ebi.enfin.mi.score.ols.MIOntology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Method score
 *
 * User: rafael
 * Date: 04-May-2010
 * Time: 12:35:57
 */
public class MethodScore extends CategoryScore{
     /**
     * This class requires a list of ontology terms as input
     *
     * @param listOfOntologyTerms
     */
    public MethodScore(ArrayList<String> listOfOntologyTerms) {
        super(listOfOntologyTerms);
        setOntologyMethodScores();
        setOntologyMethodCategories();

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

    public MethodScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfMethodTerms) {
        super(listOfOntologyTerms);
        setOntologyMethodScores();
        setOntologyMethodCategories();
        setMappingParentTerms(mapOfMethodTerms);
    }

    private void setOntologyMethodScores(){
        /* SET ONTOLOGY SCORE */
        Map<String, Float> defaultOntologyScore = new HashMap<String, Float>();
        defaultOntologyScore.put("MI:0013", 1.00f); // cv1 // biophysical
        defaultOntologyScore.put("MI:0090", 0.66f); // cv2 // protein complementation assay
        defaultOntologyScore.put("MI:0254", 0.10f); // cv3 // genetic interference
        defaultOntologyScore.put("MI:0255", 0.10f); // cv4 // post transcriptional interference
        defaultOntologyScore.put("MI:0401", 1.00f); // cv5 // biochemical
        defaultOntologyScore.put("MI:0428", 0.33f); // cv6 // imagining technique
        /* Ontology terms not present in OLS will be consider null and classify as "unknown" terms */
        defaultOntologyScore.put("unknown", 0.05f); // cv7 // unknown
        setOntologyScore(defaultOntologyScore);
    }

    private void setOntologyMethodCategories(){
        /* SET ONTOLOGY CATEGORIES */
        Map<String,Integer> defaultMainCategories = new HashMap<String, Integer>();
        defaultMainCategories.put("MI:0013", 1); // cv1 // biophysical
        defaultMainCategories.put("MI:0090", 2); // cv2 // protein complementation assay
        defaultMainCategories.put("MI:0254", 3); // cv3 // genetic interference
        defaultMainCategories.put("MI:0255", 4); // cv4 // post transcriptional interference
        defaultMainCategories.put("MI:0401", 5); // cv5 // biochemical
        defaultMainCategories.put("MI:0428", 6); // cv6 // imagining technique
        defaultMainCategories.put("unknown", 3); // cv7 // unknown
        setMainCategories(defaultMainCategories);
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
