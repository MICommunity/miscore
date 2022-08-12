package uk.ac.ebi.enfin.mi.score.scores;

import uk.ac.ebi.enfin.mi.score.ols.MIOntology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public UnNormalizedTypeScore(List<String> listOfOntologyTerms) {
        this(listOfOntologyTerms, true);
    }

    /**
     * This class requires a list of query ontology terms as input
     *
     * @param listOfOntologyTerms
     * @param useOls
     */
    public UnNormalizedTypeScore(List<String> listOfOntologyTerms, boolean useOls) {
        super(listOfOntologyTerms);
        setOntologyTypeScores();

        /* RETRIEVE ONTOLGY TERMS FOR PARENT */
        List<String> parentTerms = new ArrayList<>();
        parentTerms.add(categoryScores.getProperty("type.cv1.id"));
        parentTerms.add(categoryScores.getProperty("type.cv5.id"));
        Map<String, Map<String, String>> mapOfTypeTerms;
        MIOntology MIO = new MIOntology(useOls);
        mapOfTypeTerms = MIO.getMapOfTerms(parentTerms);
        setMappingParentTerms(mapOfTypeTerms);
        /* No need to look for MI:0914 and "MI:0915 in OLS since they are
        parent terms of MI:0407. No need to look for MI:0403 since it has
        no children. Just include them in the mapping */
        mappingParentTerms.put(categoryScores.getProperty("type.cv3.id"), categoryScores.getProperty("type.cv3.id"));
        mappingParentTerms.put(categoryScores.getProperty("type.cv4.id"), categoryScores.getProperty("type.cv4.id"));
        mappingParentTerms.put(categoryScores.getProperty("type.cv3.id"), categoryScores.getProperty("type.cv2.id"));
    }


    public UnNormalizedTypeScore(List<String> listOfOntologyTerms, Map<String, Map<String, String>> mapOfTypeTerms) {
        super(listOfOntologyTerms);
        setOntologyTypeScores();
        setMappingParentTerms(mapOfTypeTerms);
    }

    public void restoreInitialState(List<String> listOfOntologyTerms, Map<String, Map<String, String>> mapOfTypeTerms) {
        this.restoreInitialState(listOfOntologyTerms);
        this.setOntologyTypeScores();
        this.setMappingParentTerms(mapOfTypeTerms);
    }

    private void setOntologyTypeScores() {
        /* SET ONTOLOGY SCORE */
        Map<String, Float> defaultOntologyScore = new HashMap<>();
        defaultOntologyScore.put(categoryScores.getProperty("type.cv1.id"), Float.parseFloat(categoryScores.getProperty("type.cv1.unNormalizedScore"))); // cv1 // genetic interaction
        defaultOntologyScore.put(categoryScores.getProperty("type.cv2.id"), Float.parseFloat(categoryScores.getProperty("type.cv2.unNormalizedScore"))); // cv2 // colocalization
        defaultOntologyScore.put(categoryScores.getProperty("type.cv3.id"), Float.parseFloat(categoryScores.getProperty("type.cv3.unNormalizedScore"))); // cv3 // association
        defaultOntologyScore.put(categoryScores.getProperty("type.cv4.id"), Float.parseFloat(categoryScores.getProperty("type.cv4.unNormalizedScore"))); // cv4 // physical association
        defaultOntologyScore.put(categoryScores.getProperty("type.cv5.id"), Float.parseFloat(categoryScores.getProperty("type.cv5.unNormalizedScore"))); // cv5 // direct interaction
        /* Ontology terms not present in OLS will be consider null and classify as "unknown" terms */
        defaultOntologyScore.put(categoryScores.getProperty("type.cv6.id"), Float.parseFloat(categoryScores.getProperty("type.cv6.unNormalizedScore"))); // cv6 // unknown
        setOntologyScore(defaultOntologyScore);
    }

    public void setMapOfTypeTerms(Map<String, Map<String, String>> mapOfTypeTerms) {
        setMappingParentTerms(mapOfTypeTerms);
    }
}
