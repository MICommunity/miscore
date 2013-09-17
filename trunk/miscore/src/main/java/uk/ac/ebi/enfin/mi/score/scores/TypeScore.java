package uk.ac.ebi.enfin.mi.score.scores;
import uk.ac.ebi.enfin.mi.score.CategoryScore;
import uk.ac.ebi.enfin.mi.score.ols.MIOntology;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;


/**
 * Calculate type score
 *
 * User: rafael
 * Date: 21-Apr-2010
 * Time: 14:45:48
 */
public class TypeScore extends CategoryScore {
     /**
      * Process type score using a list of interaction types
      * @param listOfOntologyTerms List of ontology term IDs defining
      * interaction types for one interaction
     */
    public TypeScore(ArrayList<String> listOfOntologyTerms) {
        this(listOfOntologyTerms,true);
    }
    public TypeScore(ArrayList<String> listOfOntologyTerms,boolean useOls) {
        super(listOfOntologyTerms);
        setOntologyTypeScores();
        setOntologyTypeCategories();

        /* RETRIEVE ONTOLOGY TERMS FOR PARENT */
        ArrayList<String> parentTerms = new ArrayList<String>();
        parentTerms.add(categoryScores.getProperty("type.cv1.id"));
        parentTerms.add(categoryScores.getProperty("type.cv5.id"));
        Map<String, Map<String, String>> mapOfTypeTerms;
        MIOntology MIO = new MIOntology(useOls);
        mapOfTypeTerms = MIO.getMapOfTerms(parentTerms);
        /* No need to look for MI:0914 and "MI:0915 in OLS since they are parent terms of MI:0407 */
        mapOfTypeTerms.put(categoryScores.getProperty("type.cv3.id"), new HashMap<String, String>());
        mapOfTypeTerms.put(categoryScores.getProperty("type.cv4.id"), new HashMap<String, String>());
        /* No need to look for MI:0403 since it has no children. Just include them in the mappingParentTerms */
        mapOfTypeTerms.put(categoryScores.getProperty("type.cv2.id"), new HashMap<String, String>());
        setMappingParentTerms(mapOfTypeTerms);
    }

    /**
     * Process type score using a list of interaction types.
     * @param listOfOntologyTerms  List of ontology terms defining interaction types for one interaction
     * @param mapOfTypeTerms  Mapping between ontology children terms and root parents. A map "queried ontology term id":"children terms". Children
     * terms are represented in nested map "ontology term id":"ontology term name"
     */
    public TypeScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfTypeTerms) {
        super(listOfOntologyTerms);
        setOntologyTypeScores();
        setOntologyTypeCategories();
        setMappingParentTerms(mapOfTypeTerms);
    }

    /**
     * Set default scores for selected ontology term from the MI ontology
     */
    private void setOntologyTypeScores(){
        Map<String, Float> defaultOntologyScore = new HashMap<String, Float>();
        defaultOntologyScore.put(categoryScores.getProperty("type.cv1.id"), new Float(categoryScores.getProperty("type.cv1.score"))); // cv1 // genetic interaction
        defaultOntologyScore.put(categoryScores.getProperty("type.cv2.id"), new Float(categoryScores.getProperty("type.cv2.score"))); // cv2 // colocalization
        defaultOntologyScore.put(categoryScores.getProperty("type.cv3.id"), new Float(categoryScores.getProperty("type.cv3.score"))); // cv3 // association
        defaultOntologyScore.put(categoryScores.getProperty("type.cv4.id"), new Float(categoryScores.getProperty("type.cv4.score"))); // cv4 // physical association
        defaultOntologyScore.put(categoryScores.getProperty("type.cv5.id"), new Float(categoryScores.getProperty("type.cv5.score"))); // cv5 // direct interaction
        /* Ontology terms not present in OLS will be consider null and classify as "unknown" terms */
        defaultOntologyScore.put(categoryScores.getProperty("type.cv6.id"), new Float(categoryScores.getProperty("type.cv6.score"))); // cv6 // unknown
        setOntologyScore(defaultOntologyScore);
    }

    /**
     * Set default categories for selected ontology term from the MI ontology
     */
    private void setOntologyTypeCategories(){
        Map<String,Integer> defaultMainCategories = new HashMap<String, Integer>();
        defaultMainCategories.put(categoryScores.getProperty("type.cv1.id"), Integer.parseInt(categoryScores.getProperty("type.cv1.category"))); // cv1 // genetic interaction
        defaultMainCategories.put(categoryScores.getProperty("type.cv2.id"), Integer.parseInt(categoryScores.getProperty("type.cv2.category"))); // cv2 // colocalization
        defaultMainCategories.put(categoryScores.getProperty("type.cv3.id"), Integer.parseInt(categoryScores.getProperty("type.cv3.category"))); // cv3 // association
        defaultMainCategories.put(categoryScores.getProperty("type.cv4.id"), Integer.parseInt(categoryScores.getProperty("type.cv4.category"))); // cv4 // physical association
        defaultMainCategories.put(categoryScores.getProperty("type.cv5.id"), Integer.parseInt(categoryScores.getProperty("type.cv5.category"))); // cv5 // direct interaction
        /* Ontology terms not present in OLS will be consider null and classify as "unknown" terms */
        defaultMainCategories.put(categoryScores.getProperty("type.cv6.id"), Integer.parseInt(categoryScores.getProperty("type.cv6.category"))); // cv6 // unknown
        setMainCategories(defaultMainCategories);
    }





}
