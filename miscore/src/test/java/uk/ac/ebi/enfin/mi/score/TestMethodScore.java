package uk.ac.ebi.enfin.mi.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.enfin.mi.score.ols.MIOntology;
import uk.ac.ebi.enfin.mi.score.scores.MethodScore;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: rafael
 * Date: 04-May-2010
 * Time: 15:34:39
 * To change this template use File | Settings | File Templates.
 */

public class TestMethodScore {
    @Test
    public void testGetScore(){
        List<String> input = new ArrayList<>();
        input.add("MI:0051");
        input.add("MI:0042");
        input.add("MI:0013");
        input.add("MI:0013");
        input.add("MI:0013");
        input.add("MI:0254");
        input.add("MI:0090");
        MethodScore tS = new MethodScore(input);
        Float score = tS.getScore();
        Assert.assertTrue(score >= 0 && score <= 1);
    }

    @Test
    public void testTwoMethodQueriesOneOntologyQuery(){
        List<String> input1 = new ArrayList<>();
        input1.add("MI:0051");
        input1.add("MI:0042");
        input1.add("MI:0090");

        List<String> input2 = new ArrayList<>();
        input2.add("MI:0013");

        List<String> parentTerms = new ArrayList<>();
        parentTerms.add("MI:0013");
        parentTerms.add("MI:0090");
        parentTerms.add("MI:0254");
        parentTerms.add("MI:0255");
        parentTerms.add("MI:0401");
        parentTerms.add("MI:0428");

        Map<String, Map<String,String>> mapOfMethodTerms;
        MIOntology MIO = new MIOntology();
        mapOfMethodTerms = MIO.getMapOfTerms(parentTerms);

        MethodScore tS1 = new MethodScore(input1, mapOfMethodTerms);
        Float score1 = tS1.getScore();
        Assert.assertTrue(score1 >= 0 && score1 <= 1);

        MethodScore tS2 = new MethodScore(input2, mapOfMethodTerms);
        Float score2 = tS2.getScore();
        Assert.assertTrue(score2 >= 0 && score2 <= 1);


    }

    @Test
    public void testQueryUnknownOntologyTerms(){
        List<String> input = new ArrayList<>();
        input.add("MI:0059");
        MethodScore tS = new MethodScore(input);
        Float score = tS.getScore();
        Assert.assertTrue(score >= 0 && score <= 1);
    }
}