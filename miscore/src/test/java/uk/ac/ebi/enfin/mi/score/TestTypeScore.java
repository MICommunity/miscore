package uk.ac.ebi.enfin.mi.score;

import uk.ac.ebi.enfin.mi.score.ols.MIOntology;
import uk.ac.ebi.enfin.mi.score.scores.TypeScore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by IntelliJ IDEA.
 * User: rafael
 * Date: 21-Apr-2010
 * Time: 15:09:09
 * To change this template use File | Settings | File Templates.
 */
public class TestTypeScore {
    @Test
    public void testGetScore(){
        List<String> input = new ArrayList<>();
        input.add("MI:0208");
        input.add("MI:0403");
        input.add("MI:0407");
        TypeScore tS = new TypeScore(input);
        Float score = tS.getScore();
        Assert.assertTrue(score >= 0 && score <= 1);
    }
    @Test
    public void testCustomScores(){
        List<String> input = new ArrayList<>();
        input.add("MI:0208");
        input.add("MI:0403");
        input.add("MI:0407");
        TypeScore tS = new TypeScore(input);
        Map<String,Float> customOntologyTypeScores = new HashMap<>();
        customOntologyTypeScores.put("MI:0208", 0.05f);
        customOntologyTypeScores.put("MI:0403", 0.20f);
        customOntologyTypeScores.put("MI:0914", 0.20f);
        customOntologyTypeScores.put("MI:0915", 0.40f);
        customOntologyTypeScores.put("MI:0407", 1.00f);
        customOntologyTypeScores.put("unknown", 0.02f);
        tS.setNewOntologyScore(customOntologyTypeScores);
        Float score = tS.getScore();
        Assert.assertTrue(score >= 0 && score <= 1);
    }
    @Test
    public void testTwoTypesQueriesOneOntologyQuery(){
        List<String> input1 = new ArrayList<>();
        input1.add("MI:0208");
        input1.add("MI:0403");
        input1.add("MI:0407");

        List<String> input2 = new ArrayList<>();
        input2.add("MI:0208");
        input2.add("MI:0403");
        input2.add("MI:0407");
        input2.add("MI:0915");

        ArrayList<String> parentTerms = new ArrayList<>();
        parentTerms.add("MI:0208");
        parentTerms.add("MI:0403");
        parentTerms.add("MI:0407");

        Map<String, Map<String,String>> mapOfTypeTerms;
        MIOntology MIO = new MIOntology();
        mapOfTypeTerms = MIO.getMapOfTerms(parentTerms);

        TypeScore tS1 = new TypeScore(input1, mapOfTypeTerms);
        Float score1 = tS1.getScore();
        Assert.assertTrue(score1 >= 0 && score1 <= 1);

        TypeScore tS2 = new TypeScore(input2, mapOfTypeTerms);
        Float score2 = tS2.getScore();
        Assert.assertTrue(score2 >= 0 && score2 <= 1);

    }
    @Test
    public void testGetOntologyScoreMappingKey(){
        List<String> input = new ArrayList<>();
        input.add("MI:0208");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        Float result = tS.getOntologyScore("MI:0403");
        Assert.assertTrue(0.33f == result);
    }
    @Test
    public void testGetOntologyScoreMappingUndefinedKey(){
        List<String> input = new ArrayList<>();
        input.add("MI:0208");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        tS.getScore();
        Float result = tS.getOntologyScore("MI:0000");
        Assert.assertEquals(null, result);
    }
    @Test
    public void testGetOntologyScoreMapping(){
        List<String> input = new ArrayList<>();
        input.add("MI:0208");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        tS.getScore();
        Assert.assertTrue(tS.getOntologyScore().size() > 0);
    }


    @Test
    public void testCompareDefaultOntologyMapping(){
        List<String> input = new ArrayList<>();
        input.add("MI:0208");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        Float result1 = tS.getOntologyScore("MI:0208");
        Assert.assertEquals(0.1f, result1, 0.1);
        tS.setNewOntologyScore("MI:0208", 0.7f);
        Float result2 = tS.getOntologyScore("MI:0208");
        Assert.assertEquals(0.7f, result2, 0.1);
    }

    @Test
    public void testQueryUnknownOntologyTerms(){
        List<String> input = new ArrayList<>();
        input.add("MI:0059");
        TypeScore tS = new TypeScore(input);
        Float score = tS.getScore();
        Assert.assertTrue(score >= 0 && score <= 1);
    }
}
