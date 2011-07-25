package uk.ac.ebi.enfin.mi.score.scores;

import org.apache.log4j.Logger;
import uk.ac.ebi.enfin.mi.score.ConfidenceScore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Category score without normalization
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/03/11</pre>
 */

public class UnNormalizedCategoryScore extends ConfidenceScore {

    protected List<String> ontologyTermsQuery;
    protected Map<String,Float> ontologyScore = new HashMap<String,Float>();
    protected Map<String,String> mappingParentTerms = new HashMap<String,String>();

    private static final Logger logger = Logger.getLogger(UnNormalizedCategoryScore.class);

    /**
     * This class requires a list of ontology terms as input
     *
     * @param listOfOntologyTerms
     */
    public UnNormalizedCategoryScore(ArrayList<String> listOfOntologyTerms) {
        ontologyTermsQuery = listOfOntologyTerms;
    }

    @Override
    protected boolean isValidScore(Float score){
        if(score >= 0){
            return true;
        }
        return false;
    }

    /**
     * Calculate and return score
     */
    public Float getScore(){
        Float score = 0.0f;

        /* Calculate score = sum(ni * ontologysScore) */
        for(String oTerm:ontologyTermsQuery){

            // if this term has a score, had it to the global score
            if (ontologyScore.containsKey(oTerm)){
                score += ontologyScore.get(oTerm);
            }
            // otherwise take the parent score
            else {
                String parentTerm = "unknown";

                if(mappingParentTerms.get(oTerm) != null){
                    parentTerm = mappingParentTerms.get(oTerm);
                }

                score += getOntologyScore(parentTerm);
            }
        }

        logger.info("- - - - - - -");
        logger.info("# "+ this.getClass().getName());
        logger.info("score=" + score + " | List of terms: " + ontologyTermsQuery);
        return score;
    }

    /**
     * Take default ontology score mapping
     */
    protected void setOntologyScore(Map<String,Float> ontologyScore){
        this.ontologyScore = ontologyScore;
    }

    /**
     * Sets the ontology score mapping using a complete mapping object
     * @param ontologyScoreMapping
     */
    public void setNewOntologyScore(Map<String,Float> ontologyScoreMapping){
        if(ontologyScoreMapping != null){
            if(ontologyScoreMapping.size() >0 ){
                this.ontologyScore = ontologyScoreMapping;
            }
        }
    }

    /**
     * Adds o replaces scores for ontology terms
     * @param ontologyTerm
     * @param score
     */
    public void setNewOntologyScore(String ontologyTerm, Float score){
        if(ontologyTerm != null && score != null){
            ontologyScore.put(ontologyTerm, score);
        }
    }

    /**
     * Get ontology score mapping
     * @return
     */
    public Map<String,Float> getOntologyScore() {
        return ontologyScore;
    }

    /**
     * Get score for an ontology term
     * @param ontologyTerm
     * @return
     */
    public Float getOntologyScore(String ontologyTerm) {
        Float score = ontologyScore.get(ontologyTerm);
        return score;
    }

    /**
     * Get parent terms for any ontology term for interaction type
     * @param ontologyTerm
     * @return
     */
    public String getMappingParentTerms(String ontologyTerm){
        String parent = mappingParentTerms.get(ontologyTerm);
        return parent;
    }

    /**
     * Get the list of parent terms for interaction type
     * @return
     */
    public Map<String,String> getMappingParentTerms(){
        return mappingParentTerms;
    }
}
