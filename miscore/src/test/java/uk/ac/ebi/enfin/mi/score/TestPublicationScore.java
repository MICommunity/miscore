package uk.ac.ebi.enfin.mi.score;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import uk.ac.ebi.enfin.mi.score.scores.PublicationScore;

/**
 * Created by IntelliJ IDEA.
 * User: rafael
 * Date: 06-May-2010
 * Time: 12:22:51
 * To change this template use File | Settings | File Templates.
 */
public class TestPublicationScore extends TestCase {
        private static String bout = "Bad output for";
        static Logger logger = Logger.getLogger(TestPublicationScore.class);

        public void testGetScore(){
            Integer input = 1;
            PublicationScore tS = new PublicationScore(input);
            Float score = tS.getScore();
            logger.info("- - - - - - -");
            logger.info("# "+ this.getName());
            logger.info(score);
            assertTrue(bout + this.getName(), score >= 0 && score <= 1);
        }
}

