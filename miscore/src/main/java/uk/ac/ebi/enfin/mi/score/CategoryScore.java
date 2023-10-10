package uk.ac.ebi.enfin.mi.score;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.*;

/**
 * Class for calculating scores relaying
 * on categories defined by ontology terms
 *
 * User: rafael
 * Date: 04-May-2010
 * Time: 09:40:00
 */
public abstract class CategoryScore extends ConfidenceScore{
    protected static final Logger logger = Logger.getLogger(CategoryScore.class);
    protected List<String> ontologyTermsQuery;
    protected Map<String,Float> ontologyScore = new HashMap<>(); // "parent ontology Id":"score"
    protected Map<String,Integer> mainCategories = new HashMap<>(); // "parent ontology Id":"category Id"
    protected Map<String,String> mappingParentTerms = new HashMap<>(); // "child ontology Id":"parent ontology Id"
    private static final String categoryScorePropertiesFile = "scoreCategories.properties";
    protected Properties categoryScores;


    /**
     * @param listOfOntologyTerms List of ontology terms used
     * to describe one property of one interaction
     */
    public CategoryScore(List<String> listOfOntologyTerms) {
        ontologyTermsQuery = listOfOntologyTerms;
        categoryScores = getCategoryScores(categoryScorePropertiesFile);
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
        logger.debug( "Category score=" + score + ", a=" + a + ", b=" + b + " | List of terms: " + ontologyTermsQuery );
        return score;
    }

    /**
     * Get category score properties from the scoreCategories.properties
     * file.
     * @return properties object
     */
    private Properties getCategoryScores(String categoryScorePropertiesFile){
        if (this.categoryScores == null) {
            this.categoryScores = new Properties();
            try {
                this.categoryScores.load(this.getClass().getClassLoader().getResourceAsStream(categoryScorePropertiesFile));
            } catch (IOException e) {
                logger.error("Error getting Properties file", e);
            }
        }
        return this.categoryScores;
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
     * @param ontologyScore Source ontology
     */
    protected void setOntologyScore(Map<String,Float> ontologyScore){
        this.ontologyScore = ontologyScore;
    }

    /**
     * Sets the ontology score mapping using a complete mapping object
     * @param ontologyScoreMapping The ontology score mapping to set
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
     * @param ontologyTerm The ontology term id to link to the score
     * @param score Score to link to ontology term, between 0 and 1
     */
    public void setNewOntologyScore(String ontologyTerm, Float score){
        if(ontologyTerm != null && score != null){
            ontologyScore.put(ontologyTerm, score);
        }
    }

    /**
     * Get ontology score mapping
     * @return score
     */
    public Map<String,Float> getOntologyScore() {
        return ontologyScore;
    }

    /**
     * Get score for an ontology term
     * @param ontologyTerm The ontology term id to find the associated score
     * @return score
     */
    public Float getOntologyScore(String ontologyTerm) {
        return ontologyScore.get(ontologyTerm);
    }

    /**
     * Get parent terms for any ontology term for interaction type
     * @param ontologyTerm Ontology term ID of which you fetch the parent
     * @return "parent ontology Id" for a "child ontology Id"
     */
    public String getMappingParentTerms(String ontologyTerm){
        return mappingParentTerms.get(ontologyTerm);
    }

    /**
     * Get the list of parent terms for interaction type
     * @return  a map "child ontology Id":"parent ontology Id"
     */
    public Map<String,String> getMappingParentTerms(){
        return mappingParentTerms;
    }

    /**
     * Set new categories form a list
     * @param mainCategories To be set
     */
    public void setMainCategories(Map<String, Integer> mainCategories) {
        this.mainCategories = mainCategories;
    }

    /**
     * Add categories
     * @param ontologyTerm Ontology term id
     * @param score score to associate to category ontology
     */
    public void setMainCategories(String ontologyTerm, Integer score) {
        mainCategories.put(ontologyTerm,score);
    }

    /**
     * Get main categories
     * @return a map "parent ontology Id":"category Id"
     */
    public Map<String, Integer> getMainCategories() {
        return mainCategories;
    }

    /**
     * Get a category for an ontology term
     * @param ontologyTerm The ontology term id of which you search the parent category
     * @return category Id for a parent ontology Id
     */
    public Integer getMainCategories(String ontologyTerm) {
        return mainCategories.get(ontologyTerm);
    }

}

