package uk.ac.ebi.enfin.mi.score;

import junit.framework.TestCase;
import uk.ac.ebi.enfin.mi.score.ols.MIOntology;
import uk.ac.ebi.enfin.mi.score.scores.TypeScore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: rafael
 * Date: 21-Apr-2010
 * Time: 15:09:09
 * To change this template use File | Settings | File Templates.
 */
public class TestTypeScore extends TestCase {
    private static String bout = "Bad output for";
    static Logger logger = Logger.getLogger(TestTypeScore.class);

    public void testGetScore(){
        ArrayList input = new ArrayList();
        input.add("MI:0208");
        input.add("MI:0403");
        input.add("MI:0407");
        //input.add("MI:0915");
        TypeScore tS = new TypeScore(input);
        Float score = tS.getScore();
        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(score);
        assertTrue(bout + this.getName(), score >= 0 && score <= 1);
    }

    public void testCustomScores(){
        ArrayList input = new ArrayList();
        input.add("MI:0208");
        input.add("MI:0403");
        input.add("MI:0407");
        //input.add("MI:0915");
        TypeScore tS = new TypeScore(input);
        Map<String,Float> customOntologyTypeScores = new HashMap<String,Float>();
        customOntologyTypeScores.put("MI:0208", 0.05f);
        customOntologyTypeScores.put("MI:0403", 0.20f);
        customOntologyTypeScores.put("MI:0914", 0.20f);
        customOntologyTypeScores.put("MI:0915", 0.40f);
        customOntologyTypeScores.put("MI:0407", 1.00f);
        customOntologyTypeScores.put("unknown", 0.02f);
        tS.setNewOntologyScore(customOntologyTypeScores);
        Float score = tS.getScore();
        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(score);
        assertTrue(bout + this.getName(), score >= 0 && score <= 1);
    }

    public void testTwoTypesQueriesOneOntologyQuery(){
        ArrayList input1 = new ArrayList();
        input1.add("MI:0208");
        input1.add("MI:0403");
        input1.add("MI:0407");

        ArrayList input2 = new ArrayList();
        input2.add("MI:0208");
        input2.add("MI:0403");
        input2.add("MI:0407");
        input2.add("MI:0915");

        ArrayList<String> parentTerms = new ArrayList<String>();
        parentTerms.add("MI:0208");
        parentTerms.add("MI:0403");
        parentTerms.add("MI:0407");

        Map<String, Map<String,String>> mapOfTypeTerms;
        MIOntology MIO = new MIOntology();
        mapOfTypeTerms = MIO.getMapOfTerms(parentTerms);

        TypeScore tS1 = new TypeScore(input1, mapOfTypeTerms);
        Float score1 = tS1.getScore();
        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(score1);
        assertTrue(bout + this.getName(), score1 >= 0 && score1 <= 1);

        TypeScore tS2 = new TypeScore(input2, mapOfTypeTerms);
        Float score2 = tS2.getScore();
        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(score2);
        assertTrue(bout + this.getName(), score2 >= 0 && score2 <= 1);

    }

    public void testGetOntologyScoreMappingKey(){
        ArrayList input = new ArrayList();
        input.add("MI:0208");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        Float result = tS.getOntologyScore("MI:0403");
        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(result);
        assertEquals(bout + this.getName(), 0.33f, result);
    }

    public void testGetOntologyScoreMappingUndefinedKey(){
        ArrayList input = new ArrayList();
        input.add("MI:0208");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        tS.getScore();
        Float result = tS.getOntologyScore("MI:0000");
        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(result);
        assertEquals(bout + this.getName(), null, result);
    }

    public void testGetOntologyScoreMapping(){
        ArrayList input = new ArrayList();
        input.add("MI:0208");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        tS.getScore();
        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        for(String term:tS.getOntologyScore().keySet()){
            logger.info(term);
        }
        assertTrue(bout + this.getName(), tS.getOntologyScore().size() > 0);
    }



    public void testCompareDefaultOntologyMapping(){
        ArrayList input = new ArrayList();
        input.add("MI:0208");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        Float result1 = tS.getOntologyScore("MI:0208");
        tS.setNewOntologyScore("MI:0208", 0.7f);
        Float result2 = tS.getOntologyScore("MI:0208");
        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(this.getName());
        logger.info(result1);
        logger.info(result2);
        assertFalse(bout + this.getName(), result2.equals(result1));
    }


    public void testQueryUnknownOntologyTerms(){
        ArrayList input = new ArrayList();
        input.add("MI:0059");
        TypeScore tS = new TypeScore(input);
        Float score = tS.getScore();
        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(score);
        assertTrue(bout + this.getName(), score >= 0 && score <= 1);
    }





//    public void testGetMapOfTypeTerms(){
//        getMapOfTypeTerms
//    }
}
