package uk.ac.ebi.enfin.mi.score.scores;

import uk.ac.ebi.enfin.mi.score.ols.MIOntology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public UnNormalizedMethodScore(List<String> listOfOntologyTerms) {
        this(listOfOntologyTerms, true);
    }

    /**
     * This class requires a list of ontology terms as input
     *
     * @param listOfOntologyTerms
     */
    public UnNormalizedMethodScore(List<String> listOfOntologyTerms, boolean useOls) {
        super(listOfOntologyTerms);
        setOntologyMethodScores();

        /* Terms to include automatically using OLS */
        List<String> parentTerms = new ArrayList<>();
        parentTerms.add(categoryScores.getProperty("method.cv1.id"));
        parentTerms.add(categoryScores.getProperty("method.cv2.id"));
        parentTerms.add(categoryScores.getProperty("method.cv3.id"));
        parentTerms.add(categoryScores.getProperty("method.cv4.id"));
        parentTerms.add(categoryScores.getProperty("method.cv5.id"));
        parentTerms.add(categoryScores.getProperty("method.cv6.id"));
        parentTerms.add(categoryScores.getProperty("method.cv7.id"));

        Map<String, Map<String, String>> mapOfMethodTerms;
        MIOntology MIO = new MIOntology(useOls);
        mapOfMethodTerms = MIO.getMapOfTerms(parentTerms);

        setMappingParentTerms(mapOfMethodTerms);
    }

    public UnNormalizedMethodScore(List<String> listOfOntologyTerms, Map<String, Map<String, String>> mapOfMethodTerms) {
        super(listOfOntologyTerms);
        setOntologyMethodScores();
        setMappingParentTerms(mapOfMethodTerms);
    }

    public void restoreInitialState(List<String> listOfOntologyTerms, Map<String, Map<String, String>> mapOfMethodTerms) {
        this.restoreInitialState(listOfOntologyTerms);
        this.setOntologyMethodScores();
        this.setMappingParentTerms(mapOfMethodTerms);
    }

    private void setOntologyMethodScores() {
        /* SET ONTOLOGY SCORE */
        Map<String, Float> defaultOntologyScore = new HashMap<>();
        defaultOntologyScore.put(categoryScores.getProperty("method.cv1.id"), Float.parseFloat(categoryScores.getProperty("method.cv1.unNormalizedScore"))); // cv1 // biophysical
        defaultOntologyScore.put(categoryScores.getProperty("method.cv2.id"), Float.parseFloat(categoryScores.getProperty("method.cv2.unNormalizedScore"))); // cv2 // protein complementation assay
        defaultOntologyScore.put(categoryScores.getProperty("method.cv3.id"), Float.parseFloat(categoryScores.getProperty("method.cv3.unNormalizedScore"))); // cv3 // genetic interference
        defaultOntologyScore.put(categoryScores.getProperty("method.cv4.id"), Float.parseFloat(categoryScores.getProperty("method.cv4.unNormalizedScore"))); // cv4 // post transcriptional interference
        defaultOntologyScore.put(categoryScores.getProperty("method.cv5.id"), Float.parseFloat(categoryScores.getProperty("method.cv5.unNormalizedScore"))); // cv5 // biochemical
        defaultOntologyScore.put(categoryScores.getProperty("method.cv6.id"), Float.parseFloat(categoryScores.getProperty("method.cv6.unNormalizedScore"))); // cv6 // imagining technique
        /* Ontology terms not present in OLS will be consider null and classify as "unknown" terms */
        defaultOntologyScore.put(categoryScores.getProperty("method.cv7.id"), Float.parseFloat(categoryScores.getProperty("method.cv7.unNormalizedScore"))); // cv7 // unknown
        setOntologyScore(defaultOntologyScore);
    }

}
