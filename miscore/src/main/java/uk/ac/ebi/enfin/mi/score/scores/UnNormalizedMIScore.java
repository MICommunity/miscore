package uk.ac.ebi.enfin.mi.score.scores;

import org.apache.log4j.Logger;
import uk.ac.ebi.enfin.mi.score.ols.MIOntology;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class computes the mi score without normalizing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/03/11</pre>
 */

public class UnNormalizedMIScore extends MIScore {
    private static final Logger logger = Logger.getLogger(UnNormalizedMIScore.class);

    public UnNormalizedMIScore(boolean remoteOntology) {
        super(remoteOntology);
    }

    public UnNormalizedMIScore() {
        super();
    }

    @Override
    protected boolean isValidScore(Float score){
        if(score >= 0){
            return true;
        }
        return false;
    }

    @Override
    public Float getScore() {
        Float score = 0.0f;

        if(getTypeScore() != null) {
            score += (((UnNormalizedTypeScore)getTypeScore()).getScore()*getTypeWeight());
        }
        if(getMethodScore() != null) {
            score += (((UnNormalizedMethodScore)getMethodScore()).getScore()*getMethodWeight());
        }
        if(getPublicationScore() != null) {
            score += (getPublicationScore()*getPublicationWeight());
        }
        logger.debug("MIscore UnNormalized = " + score );
        return score;
    }

    /**
     * Set the type score using a list of ontology terms
     * @param listOfOntologyTerms
     */
    @Override
    public void setTypeScore(ArrayList<String> listOfOntologyTerms){
        UnNormalizedTypeScore tS;
        if (this.typeScore == null) {
            tS = new UnNormalizedTypeScore(listOfOntologyTerms, this.useOls);
        }
        else {
            tS = (UnNormalizedTypeScore) this.typeScore;
        }
        this.typeScore = tS;
    }

    /**
     * Set the type score using a list of ontology terms to query and specifying the parent type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param parentTerms
     */
    @Override
    public void setTypeScore(ArrayList<String> listOfOntologyTerms, ArrayList<String> parentTerms){
        Map<String, Map<String, String>> mapOfTypeTerms;
        MIOntology MIO = new MIOntology(this.useOls);
        mapOfTypeTerms = MIO.getMapOfTerms(parentTerms);

        UnNormalizedTypeScore tS;
        if (this.typeScore == null) {
            tS = new UnNormalizedTypeScore(listOfOntologyTerms, mapOfTypeTerms);
        }
        else {
            tS = (UnNormalizedTypeScore) this.typeScore;
        }
        this.typeScore = tS;
    }

    /**
     * Set the type score using a list of ontology terms to query and specifying the parent type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param parentTerms
     */
    @Override
    public void setTypeScore(ArrayList<String> listOfOntologyTerms, ArrayList<String> parentTerms, Map<String,Float> customOntologyScores){
        Map<String, Map<String, String>> mapOfTypeTerms;
        MIOntology MIO = new MIOntology(this.useOls);
        mapOfTypeTerms = MIO.getMapOfTerms(parentTerms);

        UnNormalizedTypeScore tS;
        if (this.typeScore == null) {
            tS = new UnNormalizedTypeScore(listOfOntologyTerms, mapOfTypeTerms);
        }
        else {
            tS = (UnNormalizedTypeScore) this.typeScore;
        }
        tS.setNewOntologyScore(customOntologyScores);
        this.typeScore = tS;
    }

    /**
     * Set the type score using a list of ontology terms to query and specifying a map of type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param mapOfTypeTerms
     */
    @Override
    public void setTypeScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfTypeTerms){
        UnNormalizedTypeScore tS;
        if (this.typeScore == null) {
            tS = new UnNormalizedTypeScore(listOfOntologyTerms, mapOfTypeTerms);
        }
        else {
            tS = (UnNormalizedTypeScore) this.typeScore;
        }
        this.typeScore = tS;
    }

    /**
     * Set the type score using a list of ontology terms to query and specifying a map of type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param mapOfTypeTerms
     * @param customOntologyScores
     */
    @Override
    public void setTypeScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfTypeTerms, Map<String,Float> customOntologyScores){
        UnNormalizedTypeScore tS;
        if (this.typeScore == null) {
            tS = new UnNormalizedTypeScore(listOfOntologyTerms, mapOfTypeTerms);
        }
        else {
            tS = (UnNormalizedTypeScore) this.typeScore;
        }
        tS.setNewOntologyScore(customOntologyScores);
        this.typeScore = tS;
    }

    /**
     * Set the method score using a list of ontology terms
     * @param listOfOntologyTerms
     */
    @Override
    public void setMethodScore(ArrayList<String> listOfOntologyTerms){
        UnNormalizedMethodScore mS;
        if(this.methodScore == null) {
            mS = new UnNormalizedMethodScore(listOfOntologyTerms, this.useOls);
        }
        else{
            mS = (UnNormalizedMethodScore) this.methodScore;
        }
        this.methodScore = mS;
    }

    /**
     * Set the method score using a list of ontology terms to query and specifying the parent type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param parentTerms
     */
    @Override
    public void setMethodScore(ArrayList<String> listOfOntologyTerms, ArrayList<String> parentTerms){
        Map<String, Map<String, String>> mapOfMethodTerms;
        MIOntology MIO = new MIOntology(this.useOls);
        mapOfMethodTerms = MIO.getMapOfTerms(parentTerms);

        UnNormalizedMethodScore mS;
        if(this.methodScore == null) {
            mS = new UnNormalizedMethodScore(listOfOntologyTerms, mapOfMethodTerms);
        }
        else{
            mS = (UnNormalizedMethodScore) this.methodScore;
        }
        this.methodScore = mS;
    }

    /**
     * Set the method score using a list of ontology terms to query and specifying the parent type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param parentTerms
     */
    @Override
    public void setMethodScore(ArrayList<String> listOfOntologyTerms, ArrayList<String> parentTerms, Map<String,Float> customOntologyScores){
        Map<String, Map<String, String>> mapOfMethodTerms;
        MIOntology MIO = new MIOntology(this.useOls);
        mapOfMethodTerms = MIO.getMapOfTerms(parentTerms);

        UnNormalizedMethodScore mS;
        if(this.methodScore == null) {
            mS = new UnNormalizedMethodScore(listOfOntologyTerms, mapOfMethodTerms);
        }
        else{
            mS = (UnNormalizedMethodScore) this.methodScore;
        }
        mS.setNewOntologyScore(customOntologyScores);
        this.methodScore = mS;
    }

    /**
     * Set the method score using a list of ontology terms to query and specifying a map of type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param mapOfMethodTerms
     */
    @Override
    public void setMethodScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfMethodTerms){
        UnNormalizedMethodScore mS;
        if(this.methodScore == null) {
            mS = new UnNormalizedMethodScore(listOfOntologyTerms, mapOfMethodTerms);
        }
        else{
            mS = (UnNormalizedMethodScore) this.methodScore;
        }
        this.methodScore = mS;
    }

    /**
     * Set the method score using a list of ontology terms to query and specifying a map of type terms to take into account for the analysis
     * @param listOfOntologyTerms
     * @param mapOfMethodTerms
     */
    @Override
    public void setMethodScore(ArrayList<String> listOfOntologyTerms, Map<String, Map<String,String>> mapOfMethodTerms, Map<String,Float> customOntologyScores){
        UnNormalizedMethodScore mS;
        if(this.methodScore == null) {
            mS = new UnNormalizedMethodScore(listOfOntologyTerms, mapOfMethodTerms);
        }
        else{
            mS = (UnNormalizedMethodScore) this.methodScore;
        }
        mS.setNewOntologyScore(customOntologyScores);
        this.methodScore = mS;
    }

    /**
     * Set the publication score using the total number of publications reported in the interaction
     * @param numberOfPublications
     */
    @Override
    public void setPublicationScore(Integer numberOfPublications){
        UnNormalizedPublicationScore pS = new UnNormalizedPublicationScore(numberOfPublications);
        this.publicationScore = pS.getScore();
    }

    /**
     * Set the publication score using the total number of publications reported in the interaction
     * @param numberOfPublications
     * @param publicationNumberWithHighestScore
     */
    @Override
    public void setPublicationScore(Integer numberOfPublications, Integer publicationNumberWithHighestScore){
        UnNormalizedPublicationScore pS = new UnNormalizedPublicationScore(numberOfPublications);
        if(publicationNumberWithHighestScore != null){
            logger.warn("The highest number of publication is ignored (" + publicationNumberWithHighestScore + ") when using the unnormalized publication score");
        }
        this.publicationScore = pS.getScore();
    }
}
