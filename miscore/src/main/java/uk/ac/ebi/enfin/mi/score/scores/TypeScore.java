package uk.ac.ebi.enfin.mi.score.scores;
import uk.ac.ebi.enfin.mi.score.CategoryScore;
import uk.ac.ebi.enfin.mi.score.ols.MIOntology;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: rafael
 * Date: 21-Apr-2010
 * Time: 14:45:48
 * To change this template use File | Settings | File Templates.
 */
public class TypeScore extends CategoryScore {
     /**
     * This class requires a list of query ontology terms as input
     *
     * @param listOfOntologyTerms
     */
    public TypeScore(ArrayList<String> listOfOntologyTerms) {
        super(listOfOntologyTerms);
        setOntologyTypeScores();
        setOntologyTypeCategories();

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


    public TypeScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfTypeTerms) {
        super(listOfOntologyTerms);
        setOntologyTypeScores();
        setOntologyTypeCategories();
        setMappingParentTerms(mapOfTypeTerms);
    }

    private void setOntologyTypeScores(){
        /* SET ONTOLOGY SCORE */
        Map<String, Float> defaultOntologyScore = new HashMap<String, Float>();
        defaultOntologyScore.put("MI:0208", 0.10f); // cv1 // genetic interaction
        defaultOntologyScore.put("MI:0403", 0.33f); // cv2 // colocalization
        defaultOntologyScore.put("MI:0914", 0.33f); // cv3 // association
        defaultOntologyScore.put("MI:0915", 0.66f); // cv4 // physical association
        defaultOntologyScore.put("MI:0407", 1.00f); // cv5 // direct interaction
        /* Ontology terms not present in OLS will be consider null and classify as "unknown" terms */
        defaultOntologyScore.put("unknown", 0.05f); // cv6 // unknown
        setOntologyScore(defaultOntologyScore);
    }

    private void setOntologyTypeCategories(){
         /* SET ONTOLOGY CATEGORIES */
        Map<String,Integer> defaultMainCategories = new HashMap<String, Integer>();
        defaultMainCategories.put("MI:0208", 1); // cv1 // genetic interaction
        defaultMainCategories.put("MI:0403", 2); // cv2 // colocalization
        defaultMainCategories.put("MI:0914", 3); // cv3 // association
        defaultMainCategories.put("MI:0915", 3); // cv4 // physical association
        defaultMainCategories.put("MI:0407", 3); // cv5 // direct interaction
        defaultMainCategories.put("unknown", 1); // cv6 // unknown
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
        mappingParentTerms.put("MI:0914","MI:0914");
        mappingParentTerms.put("MI:0915","MI:0915");
    }


}
