package uk.ac.ebi.enfin.mi.score.scores;

import org.apache.log4j.Logger;
import uk.ac.ebi.enfin.mi.score.ConfidenceScore;

import java.io.IOException;
import java.util.*;

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
    private static final String categoryScorePropertiesFile = "scoreCategories.properties";
    protected Properties categoryScores;

    /**
     * This class requires a list of ontology terms as input
     *
     * @param listOfOntologyTerms
     */
    public UnNormalizedCategoryScore(ArrayList<String> listOfOntologyTerms) {
        ontologyTermsQuery = listOfOntologyTerms;
        categoryScores = getCategoryScores(categoryScorePropertiesFile);
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
        logger.debug( "Category UnNormalized score=" + score + " | List of terms: " + ontologyTermsQuery );
        return score;
    }

    /**
     * Set a mapping between ontology children terms and root parents.
     * It will fill up the class attribute "mappingParentTermskey"
     * map "child ontology Id":"parent ontology Id".
     *
     * @param mapOfTerms A map "queried ontology term id":"children terms". Children
     * terms are represented in nested map "ontology term id":"ontology term name".
     */
    protected void setMappingParentTerms(Map<String, Map<String, String>> mapOfTerms){
        for(String parentTermId:mapOfTerms.keySet()){
            Map<String,String> children = mapOfTerms.get(parentTermId);
            /* Add children */
            for(String childTermId:children.keySet()){
                mappingParentTerms.put(childTermId, parentTermId);
            }
            /* Add parents */
            mappingParentTerms.put(parentTermId,parentTermId);
        }
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
     * @return  a map "parent ontology Id":"score"
     */
    public Map<String,Float> getOntologyScore() {
        return ontologyScore;
    }

    /**
     * Get score for an ontology term
     * @param ontologyTerm
     * @return  score
     */
    public Float getOntologyScore(String ontologyTerm) {
        Float score = ontologyScore.get(ontologyTerm);
        return score;
    }

    /**
     * Get parent terms for any ontology term for interaction type
     * @param ontologyTerm
     * @return "parent ontology Id" for a "child ontology Id"
     */
    public String getMappingParentTerms(String ontologyTerm){
        String parent = mappingParentTerms.get(ontologyTerm);
        return parent;
    }

    /**
     * Get the list of parent terms for interaction type
     * @return a map "child ontology Id":"parent ontology Id"
     */
    public Map<String,String> getMappingParentTerms(){
        return mappingParentTerms;
    }

    /**
     * Get category score properties from the scoreCategories.properties
     * file.
     * @return properties object
     */
    private Properties getCategoryScores(String categoryScorePropertiesFile){
        Properties properties = new Properties();
        try {

            properties.load(this.getClass().getClassLoader().getResourceAsStream(categoryScorePropertiesFile));
        } catch (IOException e) {
            logger.error("Error getting Properties file", e);
        }
        return properties;
    }
}
