package uk.ac.ebi.enfin.mi.score.scores;

import org.apache.log4j.Logger;
import uk.ac.ebi.enfin.mi.score.ConfidenceScore;

/**
 * unnormalised publication score
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/03/11</pre>
 */

public class UnNormalizedPublicationScore extends ConfidenceScore{

    private static final Logger logger = Logger.getLogger(UnNormalizedPublicationScore.class);
    private Integer numberOfPublications = 0;

    public UnNormalizedPublicationScore(Integer numberOfPublications) {
        this.numberOfPublications = numberOfPublications;
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
        float score = numberOfPublications;


        logger.info("- - - - - - -");
        logger.info("# "+ this.getClass().getName());
        logger.info("score=" + score + ", " + " | Number of publications: " + numberOfPublications);

        return score;
    }
}
