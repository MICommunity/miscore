package uk.ac.ebi.enfin.mi.score;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.enfin.mi.score.ols.MIOntology;
import uk.ac.ebi.enfin.mi.score.scores.UnNormalizedMIScore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit tester of UnNormalizedMIScore
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/03/11</pre>
 */

public class UnNormalizedMiScoreTest {
    @Test
    public void testGetScore(){

        List<String> methodInput = new ArrayList<>();
        methodInput.add("MI:0051"); // 3
        methodInput.add("MI:0042"); // 3
        // total = 6

        List<String> typeInput = new ArrayList<>();
        typeInput.add("MI:0208"); // 0.24
        typeInput.add("MI:0403"); // 0.15
        typeInput.add("MI:0407"); // 3
        typeInput.add("MI:0407"); // 3
        // total = 6.39

        UnNormalizedMIScore tS = new UnNormalizedMIScore();
        tS.setMethodScore(methodInput);
        tS.setTypeScore(typeInput);
        tS.setPublicationScore( 4 );
        float score = tS.getScore();
        Assert.assertEquals(16.4f, score, 0.0);
    }

    @Test
    public void testGetScoreRealValues(){

        List<String> methodInput = new ArrayList<>();
        methodInput.add("MI:0007"); // 3
        methodInput.add("MI:0007"); // 3
        // total = 6

        List<String> typeInput = new ArrayList<>();
        typeInput.add("MI:0407"); // 3
        typeInput.add("MI:0407"); // 3
        // total = 6

        UnNormalizedMIScore tS = new UnNormalizedMIScore();
        tS.setMethodScore(methodInput);
        tS.setTypeScore(typeInput);
        float score = tS.getScore();
        Assert.assertEquals(12f, score, 0.0);
    }

    @Test
    public void testGetScoreUsingLocalOntology(){

        List<String> methodInput = new ArrayList<>();
        methodInput.add("MI:0051"); // 3
        methodInput.add("MI:0042"); // 3
        // total = 6

        List<String> typeInput = new ArrayList<>();
        typeInput.add("MI:0208"); // 0.24
        typeInput.add("MI:0403"); // 0.15
        typeInput.add("MI:0407"); // 3
        typeInput.add("MI:0407"); // 3
        // total = 6.39

        UnNormalizedMIScore tS = new UnNormalizedMIScore(false);
        tS.setMethodScore(methodInput);
        tS.setTypeScore(typeInput);
        tS.setPublicationScore( 4 );
        float score = tS.getScore();
        Assert.assertEquals(10.7f, score, 0.1);
    }

    @Test
    public void testGetScoreWithLessQueriesToOLS(){
        MIOntology MIO = new MIOntology();
        /* Method information */
        List<String> methodInput = new ArrayList<>();
        methodInput.add("MI:0051");
        methodInput.add("MI:0042");

        ArrayList<String> methodParentTerms = new ArrayList<>();
        methodParentTerms.add("MI:0013");
        methodParentTerms.add("MI:0090");
        methodParentTerms.add("MI:0254");
        methodParentTerms.add("MI:0255");
        methodParentTerms.add("MI:0401");
        methodParentTerms.add("MI:0428");
        Map<String, Map<String,String>> mapOfMethodTerms = MIO.getMapOfTerms(methodParentTerms);


        /* Type information */
        List<String> typeInput = new ArrayList<>();
        typeInput.add("MI:0208");
        typeInput.add("MI:0403");
        typeInput.add("MI:0407");

        ArrayList<String> typeParentTerms = new ArrayList<>();
        typeParentTerms.add("MI:0208");
        typeParentTerms.add("MI:0403");
        typeParentTerms.add("MI:0407");
        Map<String, Map<String,String>> mapOfTypeTerms = MIO.getMapOfTerms(typeParentTerms);



        UnNormalizedMIScore tS = new UnNormalizedMIScore();
        tS.setMethodScore(methodInput, mapOfMethodTerms);
        tS.setTypeScore(typeInput, mapOfTypeTerms);
        tS.setPublicationScore(4);
        float score = tS.getScore();
        Assert.assertTrue(score >= 1);
    }
    @Test
    public void testGetScore2(){

        List<String> methodInput = new ArrayList<>();
        methodInput.add("MI:0051");
        methodInput.add("MI:0042");

        List<String> typeInput = new ArrayList<>();
        typeInput.add("MI:0208");
        typeInput.add("MI:0403");
        typeInput.add("MI:0407");
        typeInput.add("MI:0407");

        UnNormalizedMIScore tS = new UnNormalizedMIScore();
        tS.setMethodScore(methodInput);
        tS.setTypeScore(typeInput);
        tS.setPublicationScore(4);
        Float score = tS.getScore();
        Assert.assertTrue( score >= 0 && score >= 1 );
    }
    @Test
    public void testSetTypeScore(){
        MIOntology MIO = new MIOntology();
        /* Method information */
        List<String> methodInput = new ArrayList<>();
        methodInput.add("MI:0051");
        methodInput.add("MI:0042");

        ArrayList<String> methodParentTerms = new ArrayList<>();
        methodParentTerms.add("MI:0013");
        methodParentTerms.add("MI:0090");
        methodParentTerms.add("MI:0254");
        methodParentTerms.add("MI:0255");
        methodParentTerms.add("MI:0401");
        methodParentTerms.add("MI:0428");
        Map<String, Map<String,String>> mapOfMethodTerms = MIO.getMapOfTerms(methodParentTerms);


        /* Type information */
        List<String> typeInput = new ArrayList<>();
        typeInput.add("MI:0208");
        typeInput.add("MI:0403");
        typeInput.add("MI:0407");

        ArrayList<String> typeParentTerms = new ArrayList<>();
        typeParentTerms.add("MI:0208");
        typeParentTerms.add("MI:0403");
        typeParentTerms.add("MI:0407");
        Map<String, Map<String,String>> mapOfTypeTerms = MIO.getMapOfTerms(typeParentTerms);

        /* Rewrite default type score values */
        Map<String,Float> customOntologyTypeScores = new HashMap<>();
        customOntologyTypeScores.put("MI:0208", 0.05f);
        customOntologyTypeScores.put("MI:0403", 0.20f);
        customOntologyTypeScores.put("MI:0914", 0.20f);
        customOntologyTypeScores.put("MI:0915", 0.40f);
        customOntologyTypeScores.put("MI:0407", 1.00f);
        customOntologyTypeScores.put("unknown", 0.02f);

        UnNormalizedMIScore tS = new UnNormalizedMIScore();
        tS.setMethodScore(methodInput, mapOfMethodTerms);
        tS.setTypeScore(typeInput, mapOfTypeTerms, customOntologyTypeScores);
        tS.setPublicationScore(4);
        Float score = tS.getScore();
        Assert.assertTrue( score >= 0 && score >= 1 );    }
}
