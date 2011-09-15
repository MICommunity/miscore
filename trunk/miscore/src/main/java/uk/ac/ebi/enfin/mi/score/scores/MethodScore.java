package uk.ac.ebi.enfin.mi.score.scores;

import uk.ac.ebi.enfin.mi.score.CategoryScore;
import uk.ac.ebi.enfin.mi.score.ols.MIOntology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculate method score
 *
 * User: rafael
 * Date: 04-May-2010
 * Time: 12:35:57
 */
public class MethodScore extends CategoryScore{
     /**
      * Process method score using a list of detection methods
      * @param listOfOntologyTerms List of ontology term IDs defining
      * detection methods for one interaction
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

    /**
     * Process method score using a list of detection methods
     * @param listOfOntologyTerms listOfOntologyTerms List of ontology term IDs defining
     * detection methods for one interaction
     * @param mapOfMethodTerms Mapping between ontology children terms and root parents. A map "queried ontology term id":"children terms". Children
     * terms are represented in nested map "ontology term id":"ontology term name"
     */
    public MethodScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfMethodTerms) {
        super(listOfOntologyTerms);
        setOntologyMethodScores();
        setOntologyMethodCategories();
        setMappingParentTerms(mapOfMethodTerms);
    }

    /**
     * Set default scores for selected ontology term from the MI ontology
     */
    private void setOntologyMethodScores(){
        Map<String, Float> defaultOntologyScore = new HashMap<String, Float>();
        defaultOntologyScore.put(categoryScores.getProperty("method.cv1.id"), new Float(categoryScores.getProperty("method.cv1.score"))); // cv1 // biophysical
        defaultOntologyScore.put(categoryScores.getProperty("method.cv2.id"), new Float(categoryScores.getProperty("method.cv2.score"))); // cv2 // protein complementation assay
        defaultOntologyScore.put(categoryScores.getProperty("method.cv3.id"), new Float(categoryScores.getProperty("method.cv3.score"))); // cv3 // genetic interference
        defaultOntologyScore.put(categoryScores.getProperty("method.cv4.id"), new Float(categoryScores.getProperty("method.cv4.score"))); // cv4 // post transcriptional interference
        defaultOntologyScore.put(categoryScores.getProperty("method.cv5.id"), new Float(categoryScores.getProperty("method.cv5.score"))); // cv5 // biochemical
        defaultOntologyScore.put(categoryScores.getProperty("method.cv6.id"), new Float(categoryScores.getProperty("method.cv6.score"))); // cv6 // imagining technique
        /* Ontology terms not present in OLS will be consider null and classify as "unknown" terms */
        defaultOntologyScore.put(categoryScores.getProperty("method.cv7.id"), new Float(categoryScores.getProperty("method.cv7.score"))); // cv7 // unknown
        setOntologyScore(defaultOntologyScore);
    }

    /**
     * Set default categories for selected ontology term from the MI ontology
     */
    private void setOntologyMethodCategories(){
        Map<String,Integer> defaultMainCategories = new HashMap<String, Integer>();
        defaultMainCategories.put(categoryScores.getProperty("method.cv1.id"), Integer.parseInt(categoryScores.getProperty("method.cv1.category"))); // cv1 // biophysical
        defaultMainCategories.put(categoryScores.getProperty("method.cv2.id"), Integer.parseInt(categoryScores.getProperty("method.cv2.category"))); // cv2 // protein complementation assay
        defaultMainCategories.put(categoryScores.getProperty("method.cv3.id"), Integer.parseInt(categoryScores.getProperty("method.cv3.category"))); // cv3 // genetic interference
        defaultMainCategories.put(categoryScores.getProperty("method.cv4.id"), Integer.parseInt(categoryScores.getProperty("method.cv4.category"))); // cv4 // post transcriptional interference
        defaultMainCategories.put(categoryScores.getProperty("method.cv5.id"), Integer.parseInt(categoryScores.getProperty("method.cv5.category"))); // cv5 // biochemical
        defaultMainCategories.put(categoryScores.getProperty("method.cv6.id"), Integer.parseInt(categoryScores.getProperty("method.cv6.category"))); // cv6 // imagining technique
        defaultMainCategories.put(categoryScores.getProperty("method.cv7.id"), Integer.parseInt(categoryScores.getProperty("method.cv7.category"))); // cv7 // unknown
        setMainCategories(defaultMainCategories);
    }

}
