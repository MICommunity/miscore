package uk.ac.ebi.enfin.mi.score;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This abstract class is used for scores relaying on ontology terms
 *
 * User: rafael
 * Date: 04-May-2010
 * Time: 09:40:00
 */

public abstract class CategoryScore extends ConfidenceScore{
    private static final Logger logger = Logger.getLogger(CategoryScore.class);
    protected List<String> ontologyTermsQuery;
    protected Map<String,Float> ontologyScore = new HashMap<String,Float>();
    protected Map<String,Integer> mainCategories = new HashMap<String,Integer>();
    protected Map<String,String> mappingParentTerms = new HashMap<String,String>();



    /**
     * This class requires a list of ontology terms as input
     * @param listOfOntologyTerms
     */
    public CategoryScore(ArrayList<String> listOfOntologyTerms) {
        ontologyTermsQuery = listOfOntologyTerms;
//        setDefaultOntologyScore();
//        setDefaultMainCategories();
    }

    /**
     * Calculate and return score
     */
    public Float getScore(){
        Float score = null;

        /* Calculate a */
        Float a = 0.0f;
        for(String oTerm:ontologyTermsQuery){
            String parentTerm = "unknown";
            if(mappingParentTerms.get(oTerm) != null){
                parentTerm = mappingParentTerms.get(oTerm);
            }
            Float parentScore = getOntologyScore(parentTerm);
            a = a + parentScore;
        }

        /* Calculate categoryToHighestScore, categoryToHighestScoreTerm, categoryCount */
        HashMap<Integer,Float> categoryToHighestScore = new HashMap<Integer, Float>();
        HashMap<Integer,String> categoryToHighestScoreTerm = new HashMap<Integer, String>();
        HashMap<Integer,Integer> categoryCount = new HashMap<Integer, Integer>();
        HashMap<String,Integer> parentTermCount = new HashMap<String, Integer>();
        /* Get the highest category scores */
        for(String oTerm:mainCategories.keySet()){
            Float oScore = getOntologyScore(oTerm);
            Integer category = getMainCategories(oTerm);
            parentTermCount.put(oTerm,0);
            if(categoryToHighestScore.get(category) == null){
                categoryToHighestScore.put(category,oScore);
                categoryToHighestScoreTerm.put(category,oTerm);
                categoryCount.put(category,1);
            } else {
                /* Replace score if the new score is higher */
                Float cScore = categoryToHighestScore.get(category);
                if(oScore > cScore){
                    categoryToHighestScore.put(category, oScore);
                    categoryToHighestScoreTerm.put(category,oTerm);
                }
                /* Count elements for one category */
                Integer repetitions = categoryCount.get(category);
                categoryCount.put(category, repetitions+1);
            }
        }

        /* Count repetition for parent terms */
        //HashMap<String,Integer> parentTermCount = new HashMap<String, Integer>();
        for(String oTerm:ontologyTermsQuery){
            String parentTerm = mappingParentTerms.get(oTerm);
            if(parentTermCount.get(parentTerm) == null){
                parentTermCount.put(parentTerm, 1);
            } else {
                Integer repetitions = parentTermCount.get(parentTerm);
                parentTermCount.put(parentTerm, repetitions+1);
            }
        }

        /* Calculate b */
        Float b = 0.0f;
        for(Integer category:categoryToHighestScoreTerm.keySet()){
            if(categoryCount.get(category) == 1){
                /* Find categories with just one term and add scores */
                String oTerm = categoryToHighestScoreTerm.get(category);
                if(parentTermCount.get(oTerm) != null){
                    b = b + ((parentTermCount.get(oTerm)+1)*categoryToHighestScore.get(category));
                }
            } else {
                /* Find categories with more than one term and add scores */
                b = b + categoryToHighestScore.get(category);
                Integer totalNumberOfTerms = 0;
                Float c = 0.0f;
                for(String oTerm:mainCategories.keySet()){
                    if(mainCategories.get(oTerm) == category && parentTermCount.get(oTerm) != 0){
                        c = c + (parentTermCount.get(oTerm) * getOntologyScore(oTerm));
                    }
                }
                b = b + c;
            }
        }
        b = b+1.0f;
        a = a+1.0f;
        score = logOfBase(b,a);
        logger.info("- - - - - - -");
        logger.info("# "+ this.getClass().getName());
        logger.info("score=logOfBase(a,b) ... score=" + score + ", a=" + a + ", b=" + b + " | List of terms: " + ontologyTermsQuery);
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

    /**
     * Set new categories form a list
     * @param mainCategories
     */
    public void setMainCategories(Map<String, Integer> mainCategories) {
        this.mainCategories = mainCategories;
    }

    /**
     * Add categories
     * @param ontologyTerm
     * @param Score
     */
    public void setMainCategories(String ontologyTerm, Integer Score) {
        mainCategories.put(ontologyTerm,Score);
    }

    /**
     * 
     * @return
     */
    public Map<String, Integer> getMainCategories() {
        return mainCategories;
    }

    /**
     * Get a category for an ontology term
     * @param ontologyTerm
     * @return
     */
    public Integer getMainCategories(String ontologyTerm) {
        return mainCategories.get(ontologyTerm);
    }

}

