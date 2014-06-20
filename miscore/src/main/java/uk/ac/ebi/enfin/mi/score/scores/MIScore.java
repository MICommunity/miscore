package uk.ac.ebi.enfin.mi.score.scores;

import org.apache.log4j.Logger;
import uk.ac.ebi.enfin.mi.score.ConfidenceScore;
import uk.ac.ebi.enfin.mi.score.ols.MIOntology;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: rafael
 * Date: 05-May-2010
 * Time: 15:42:15
 * To change this template use File | Settings | File Templates.
 */
public class MIScore extends ConfidenceScore{
    private static final Logger logger = Logger.getLogger(MIScore.class);
    protected ConfidenceScore typeScore = null;
    protected ConfidenceScore methodScore = null;
    protected Float publicationScore = null;
    private Float typeWeight = 1.0f;
    private Float methodWeight = 1.0f;
    private Float publicationWeight = 1.0f;
    protected boolean useOls;


    public MIScore(){
        this.useOls = true;
    }

    public MIScore(boolean useRemoteOntology){
        this.useOls = useRemoteOntology;
    }


    public boolean isUseOls() {
        return useOls;
    }


    /**
     * Get the molecular interaction confidence score
     * @return score
     */
    public Float getScore() {
        Float a = 0.0f;
        Float b = 0.0f;
        Float score = 0.0f;

        if(typeScore != null) {
            a= a + (typeScore.getScore()*typeWeight);
            b= b + typeWeight;
        }
        if(methodScore != null) {
            a= a + (methodScore.getScore()*methodWeight);
            b= b + methodWeight;
        }
        if(publicationScore != null) {
            a= a + (publicationScore*publicationWeight);
            b= b + publicationWeight;
        }

        score = a/b;
        logger.debug("MIscore=" + score + ", a=" + a + ", b=" + b);
        return score;
    }

    /**
     * Get the score for type
     * @return score
     */
    public ConfidenceScore getTypeScore() { return typeScore; }

    /**
     * Set the type score using a score value
     * @param typeScore
     */
    public void setTypeScore(TypeScore typeScore) {
        this.typeScore = typeScore;
    }

    /**
     * Set the type score using a list of ontology terms
     * @param listOfOntologyTerms
     */
    public void setTypeScore(ArrayList<String> listOfOntologyTerms){
        TypeScore tS = new TypeScore(listOfOntologyTerms, this.useOls);
        this.typeScore = tS;
    }

    /**
     * Set the type score using a list of ontology terms to query and specifying the parent type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param parentTerms
     */
    public void setTypeScore(ArrayList<String> listOfOntologyTerms, ArrayList<String> parentTerms){
        Map<String, Map<String, String>> mapOfTypeTerms;
        MIOntology MIO = new MIOntology(this.useOls);
        mapOfTypeTerms = MIO.getMapOfTerms(parentTerms);

        TypeScore tS = new TypeScore(listOfOntologyTerms, mapOfTypeTerms);
        this.typeScore = tS;
    }

    /**
     * Set the type score using a list of ontology terms to query and specifying the parent type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param parentTerms
     */
    public void setTypeScore(ArrayList<String> listOfOntologyTerms, ArrayList<String> parentTerms, Map<String,Float> customOntologyScores){
        Map<String, Map<String, String>> mapOfTypeTerms;
        MIOntology MIO = new MIOntology(this.useOls);
        mapOfTypeTerms = MIO.getMapOfTerms(parentTerms);

        TypeScore tS = new TypeScore(listOfOntologyTerms, mapOfTypeTerms);
        tS.setNewOntologyScore(customOntologyScores);
        this.typeScore = tS;
    }

    /**
     * Set the type score using a list of ontology terms to query and specifying a map of type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param mapOfTypeTerms
     */
    public void setTypeScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfTypeTerms){
        TypeScore tS = new TypeScore(listOfOntologyTerms, mapOfTypeTerms);
        this.typeScore = tS;
    }

    /**
     * Set the type score using a list of ontology terms to query and specifying a map of type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param mapOfTypeTerms
     * @param customOntologyScores
     */
    public void setTypeScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfTypeTerms, Map<String,Float> customOntologyScores){
        TypeScore tS = new TypeScore(listOfOntologyTerms, mapOfTypeTerms);
        tS.setNewOntologyScore(customOntologyScores);
        this.typeScore = tS;
    }


    /**
     * Set the method score using a score value
     * @param methodScore
     */
    public void setMethodScore(MethodScore methodScore) {
        this.methodScore = methodScore;
    }

    /**
     * Get the score for method
     * @return score
     */
    public ConfidenceScore getMethodScore() { return methodScore; }


    /**
     * Set the method score using a list of ontology terms
     * @param listOfOntologyTerms
     */
    public void setMethodScore(ArrayList<String> listOfOntologyTerms){
        MethodScore mS = new MethodScore(listOfOntologyTerms,this.useOls);
        this.methodScore = mS;
    }

    /**
     * Set the method score using a list of ontology terms to query and specifying the parent type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param parentTerms
     */
    public void setMethodScore(ArrayList<String> listOfOntologyTerms, ArrayList<String> parentTerms){
        Map<String, Map<String, String>> mapOfMethodTerms;
        MIOntology MIO = new MIOntology(this.useOls);
        mapOfMethodTerms = MIO.getMapOfTerms(parentTerms);

        MethodScore mS = new MethodScore(listOfOntologyTerms, mapOfMethodTerms);
        this.methodScore = mS;
    }

    /**
     * Set the method score using a list of ontology terms to query and specifying the parent type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param parentTerms
     */
    public void setMethodScore(ArrayList<String> listOfOntologyTerms, ArrayList<String> parentTerms, Map<String,Float> customOntologyScores){
        Map<String, Map<String, String>> mapOfMethodTerms;
        MIOntology MIO = new MIOntology(this.useOls);
        mapOfMethodTerms = MIO.getMapOfTerms(parentTerms);

        MethodScore mS = new MethodScore(listOfOntologyTerms, mapOfMethodTerms);
        mS.setNewOntologyScore(customOntologyScores);
        this.methodScore = mS;
    }

    /**
     * Set the method score using a list of ontology terms to query and specifying a map of type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param mapOfMethodTerms
     */
    public void setMethodScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfMethodTerms){
        MethodScore mS = new MethodScore(listOfOntologyTerms, mapOfMethodTerms);
        this.methodScore = mS;
    }

    /**
     * Set the method score using a list of ontology terms to query and specifying a map of type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param mapOfMethodTerms
     */
    public void setMethodScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfMethodTerms, Map<String,Float> customOntologyScores){
        MethodScore mS = new MethodScore(listOfOntologyTerms, mapOfMethodTerms);
        mS.setNewOntologyScore(customOntologyScores);
        this.methodScore = mS;
    }

    public void setMethodScore(Map<String, Map<String,String>> mapOfMethodTerms) {
        ((MethodScore)this.methodScore).setMapOfMethodTerms(mapOfMethodTerms);
    }

    /**
     * Get the score for publication
     * @return  score
     */
    public Float getPublicationScore() {
        return publicationScore;
    }

    /**
     * Set the publication score using a score value
     * @param publicationScore
     */
    public void setPublicationScore(Float publicationScore) {
        this.publicationScore = publicationScore;
    }

    /**
     * Set the publication score using the total number of publications reported in the interaction 
     * @param numberOfPublications
     */
    public void setPublicationScore(Integer numberOfPublications){
        PublicationScore pS = new PublicationScore(numberOfPublications);
        this.publicationScore = pS.getScore();
    }

    /**
     * Set the publication score using the total number of publications reported in the interaction
     * @param numberOfPublications
     * @param publicationNumberWithHighestScore
     */
    public void setPublicationScore(Integer numberOfPublications, Integer publicationNumberWithHighestScore){
        PublicationScore pS = new PublicationScore(numberOfPublications);
        if(publicationNumberWithHighestScore != null){
            pS.setPublicationNumberWithHighestScore(publicationNumberWithHighestScore);
        }
        this.publicationScore = pS.getScore();
    }

    /**
     * Return type weigh factor
     * @return  weigh between 0 and 1 asigned for type score
     */
    public Float getTypeWeight() {
        return typeWeight;
    }

    /**
     * Define how much weigh the type score has in the equation using an score between 0 and 1
     * @param typeWeight
     */
    public void setTypeWeight(Float typeWeight) {
        this.typeWeight = typeWeight;
    }

    /**
     * Return method weigh factor
     * @return weigh between 0 and 1 asigned for method score
     */
    public Float getMethodWeight() {
        return methodWeight;
    }

    /**
     * Define how much weigh the method score has in the equation using an score between 0 and 1
     * @param methodWeight
     */
    public void setMethodWeight(Float methodWeight) {
        this.methodWeight = methodWeight;
    }

    /**
     * Return publication weigh factor
     * @return  weigh between 0 and 1 asigned for publication score
     */
    public Float getPublicationWeight() {
        return publicationWeight;
    }

    /**
     * Define how much weigh the publication score has in the equation using an score between 0 and 1
     * @param publicationWeight
     */
    public void setPublicationWeight(Float publicationWeight) {
        this.publicationWeight = publicationWeight;
    }
}
