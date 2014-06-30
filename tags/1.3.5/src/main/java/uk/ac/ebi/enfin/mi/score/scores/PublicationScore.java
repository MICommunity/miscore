package uk.ac.ebi.enfin.mi.score.scores;

import uk.ac.ebi.enfin.mi.score.ConfidenceScore;
import org.apache.log4j.Logger;


/**
 * Publication score
 *
 * User: rafael
 * Date: 05-May-2010
 * Time: 15:14:55
 */
public class PublicationScore extends ConfidenceScore{
    private static final Logger logger = Logger.getLogger(PublicationScore.class);
    private Integer numberOfPublications = 0;
    private Integer publicationNumberWithHighestScore = 7;

    public PublicationScore(Integer numberOfPublications){
        this.numberOfPublications = numberOfPublications;
    }

    /**
     * Get publication score 
     * @return  score
     */
    public Float getScore() {
        Float a = 0.0f;
        Float b = 0.0f;
        Float score = 0.0f;
        if(numberOfPublications > publicationNumberWithHighestScore){
            score = 1.0f;
        } else {
            b = publicationNumberWithHighestScore+1.0f;
            a = numberOfPublications+1.0f;
            score = logOfBase(b, a);
        }
        logger.debug("Publication score=" + score + ", a=" + a + ", b=" + b + " | Number of publications: " + numberOfPublications);
        return score;
    }

    /**
     * Get number of publications
     * @return number of publications
     */
    public Integer getNumberOfPublications() {
        return numberOfPublications;
    }

    /**
     * Get the number of publications which will get the highest score
     * @return number of publications
     */
    public Integer getPublicationNumberWithHighestScore() {
        return publicationNumberWithHighestScore;
    }

    /**
     * Set the number of publications which will get the highest score
     * @param publicationNumberWithHighestScore
     */
    public void setPublicationNumberWithHighestScore(Integer publicationNumberWithHighestScore) {
        this.publicationNumberWithHighestScore = publicationNumberWithHighestScore;
    }
}
