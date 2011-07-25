package uk.ac.ebi.enfin.mi.score;

import org.apache.log4j.Logger;
import uk.ac.ebi.enfin.mi.score.ols.MIOntology;
import uk.ac.ebi.enfin.mi.score.scores.MIScore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: rafael
 * Date: 06-May-2010
 * Time: 12:02:57
 * To change this template use File | Settings | File Templates.
 */
public class TestMIScore extends TestCase {
    private static String bout = "Bad output for";
    static Logger logger = Logger.getLogger(TestMIScore.class);

    public void testGetScore(){
        Float score = null;

        ArrayList methodInput = new ArrayList();
        methodInput.add("MI:0051");
        methodInput.add("MI:0042");

        ArrayList typeInput = new ArrayList();
        typeInput.add("MI:0208");
        typeInput.add("MI:0403");
        typeInput.add("MI:0407");
        typeInput.add("MI:0407");

        MIScore tS = new MIScore();
        tS.setMethodScore(methodInput);
        tS.setTypeScore(typeInput);
        tS.setPublicationScore(4);

        logger.info(tS.getPublicationScore());

        score = tS.getScore();

        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(score);
        assertTrue(bout + this.getName(), score >= 0 && score <= 1);
    }

    public void testGetScoreWithLessQueriesToOLS(){
        Float score = null;
        MIOntology MIO = new MIOntology();
        /* Method information */
        ArrayList methodInput = new ArrayList();
        methodInput.add("MI:0051");
        methodInput.add("MI:0042");

        ArrayList<String> methodParentTerms = new ArrayList<String>();
        methodParentTerms.add("MI:0013");
        methodParentTerms.add("MI:0090");
        methodParentTerms.add("MI:0254");
        methodParentTerms.add("MI:0255");
        methodParentTerms.add("MI:0401");
        methodParentTerms.add("MI:0428");
        Map<String, Map<String,String>> mapOfMethodTerms = MIO.getMapOfTerms(methodParentTerms);


        /* Type information */
        ArrayList typeInput = new ArrayList();
        typeInput.add("MI:0208");
        typeInput.add("MI:0403");
        typeInput.add("MI:0407");

        ArrayList<String> typeParentTerms = new ArrayList<String>();
        typeParentTerms.add("MI:0208");
        typeParentTerms.add("MI:0403");
        typeParentTerms.add("MI:0407");
        Map<String, Map<String,String>> mapOfTypeTerms = MIO.getMapOfTerms(typeParentTerms);



        MIScore tS = new MIScore();
        tS.setMethodScore(methodInput, mapOfMethodTerms);
        tS.setTypeScore(typeInput, mapOfTypeTerms);
        tS.setPublicationScore(4);

        logger.info(tS.getPublicationScore());

        score = tS.getScore();

        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(score);
        assertTrue(bout + this.getName(), score >= 0 && score <= 1);
    }

    public void testGetScore2(){
        Float score = null;

        ArrayList methodInput = new ArrayList();
        methodInput.add("MI:0051");
        methodInput.add("MI:0042");

        ArrayList typeInput = new ArrayList();
        typeInput.add("MI:0208");
        typeInput.add("MI:0403");
        typeInput.add("MI:0407");
        typeInput.add("MI:0407");

        MIScore tS = new MIScore();
        tS.setMethodScore(methodInput);
        tS.setTypeScore(typeInput);
        tS.setPublicationScore(4);

        logger.info(tS.getPublicationScore());

        score = tS.getScore();

        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(score);
        assertTrue(bout + this.getName(), score >= 0 && score <= 1);
    }

    public void testSetTypeScore(){
        Float score = null;
        MIOntology MIO = new MIOntology();
        /* Method information */
        ArrayList methodInput = new ArrayList();
        methodInput.add("MI:0051");
        methodInput.add("MI:0042");

        ArrayList<String> methodParentTerms = new ArrayList<String>();
        methodParentTerms.add("MI:0013");
        methodParentTerms.add("MI:0090");
        methodParentTerms.add("MI:0254");
        methodParentTerms.add("MI:0255");
        methodParentTerms.add("MI:0401");
        methodParentTerms.add("MI:0428");
        Map<String, Map<String,String>> mapOfMethodTerms = MIO.getMapOfTerms(methodParentTerms);


        /* Type information */
        ArrayList typeInput = new ArrayList();
        typeInput.add("MI:0208");
        typeInput.add("MI:0403");
        typeInput.add("MI:0407");

        ArrayList<String> typeParentTerms = new ArrayList<String>();
        typeParentTerms.add("MI:0208");
        typeParentTerms.add("MI:0403");
        typeParentTerms.add("MI:0407");
        Map<String, Map<String,String>> mapOfTypeTerms = MIO.getMapOfTerms(typeParentTerms);

        /* Rewrite default type score values */
        Map<String,Float> customOntologyTypeScores = new HashMap<String,Float>();
        customOntologyTypeScores.put("MI:0208", 0.05f);
        customOntologyTypeScores.put("MI:0403", 0.20f);
        customOntologyTypeScores.put("MI:0914", 0.20f);
        customOntologyTypeScores.put("MI:0915", 0.40f);
        customOntologyTypeScores.put("MI:0407", 1.00f);
        customOntologyTypeScores.put("unknown", 0.02f);

        MIScore tS = new MIScore();
        tS.setMethodScore(methodInput, mapOfMethodTerms);
        tS.setTypeScore(typeInput, mapOfTypeTerms, customOntologyTypeScores);
        tS.setPublicationScore(4);

        logger.info(tS.getPublicationScore());

        score = tS.getScore();

        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(score);
        assertTrue(bout + this.getName(), score >= 0 && score <= 1);    }
}
