package uk.ac.ebi.enfin.mi.score;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.enfin.mi.score.scores.MethodScore;
import uk.ac.ebi.enfin.mi.score.scores.TypeScore;
import java.util.ArrayList;


/**
 * Test examples available in the documentation
 * https://docs.google.com/document/d/1KPE1tv8pxugH7TiAqaqzUg1F5tOFUhDruD8tJC5cnDc/edit?hl=en_US
 *
 * @author Rafael Jimenez (rafael@ebi.ac.uk)
 * @version $Id$
 * @since 1.3.2
 */
public class TestMethodScoreExamples {
    /**
     *  Round numbers
     */
    private static float Round(float Rval, int Rpl) {
        float p = (float)Math.pow(10,Rpl);
        Rval = Rval * p;
        float tmp = Math.round(Rval);
        return (float)tmp/p;
    }
    /**
     * An interaction annotated with two “biophysical”, three “biochemical” methods
     * 0.81
     */
    @Test
    public void testExample01(){
        ArrayList input = new ArrayList();
        input.add("MI:0013");
        input.add("MI:0013");
        input.add("MI:0401");
        input.add("MI:0401");
        input.add("MI:0401");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertTrue(scoreRounded.compareTo(new Float("0.81")) == 0);
    }

    /**
     * An interaction annotated with two “biophysical”, one “protein complementation assay”, one “biochemical” and one “imagining technique” methods
     * 0.77
     */
    @Test
    public void testExample02(){
        ArrayList input = new ArrayList();
        input.add("MI:0013");
        input.add("MI:0013");
        input.add("MI:0090");
        input.add("MI:0401");
        input.add("MI:0428");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertTrue(scoreRounded.compareTo(new Float("0.76")) == 0);
    }


    /**
     * An interaction annotated with two “protein complementation assay”, two “imagining technique” methods
     * 0.60
     */
    @Test
    public void testExample03(){
        ArrayList input = new ArrayList();
        input.add("MI:0090");
        input.add("MI:0090");
        input.add("MI:0428");
        input.add("MI:0428");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertTrue(scoreRounded.compareTo(new Float("0.60")) == 0);
    }


    /**
     * An interaction annotated with two “genetic interference”, one “post transcriptional interference” and one “imagining technique” methods
     * 0.31
     */
    @Test
    public void testExample04(){
        ArrayList input = new ArrayList();
        input.add("MI:0254");
        input.add("MI:0254");
        input.add("MI:0255");
        input.add("MI:0428");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertTrue(scoreRounded.compareTo(new Float("0.31")) == 0);
    }

    /**
     * An interaction annotated with one “genetic interference”, one “post transcriptional interference” methods
     * 0.12
     */
    @Test
    public void testExample05(){
        ArrayList input = new ArrayList();
        input.add("MI:0254");
        input.add("MI:0255");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertTrue(scoreRounded.compareTo(new Float("0.12")) == 0);
    }

    /**
     * An interaction annotated with a method which is “uknown”: 0.04
     */
    @Test
    public void testExample06(){
        ArrayList input = new ArrayList();
        input.add("unknown");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertTrue(scoreRounded.compareTo(new Float("0.03")) == 0);
    }



}