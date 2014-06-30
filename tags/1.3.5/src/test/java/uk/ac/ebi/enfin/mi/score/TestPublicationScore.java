package uk.ac.ebi.enfin.mi.score;
import uk.ac.ebi.enfin.mi.score.scores.PublicationScore;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: rafael
 * Date: 06-May-2010
 * Time: 12:22:51
 * To change this template use File | Settings | File Templates.
 */
public class TestPublicationScore {

        @Test
        public void testGetScore(){
            Integer input = 1;
            PublicationScore tS = new PublicationScore(input);
            Float score = tS.getScore();
            Assert.assertTrue(score >= 0 && score <= 1);
        }
}

