package uk.ac.ebi.enfin.mi.score;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.enfin.mi.score.scores.MethodScore;
import uk.ac.ebi.enfin.mi.score.scores.TypeScore;
import java.util.ArrayList;
import java.util.List;


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
        return tmp /p;
    }
    /**
     * An interaction annotated with two “biophysical”, three “biochemical” methods
     * 0.81
     */
    @Test
    public void testExample01(){
        List<String> input = new ArrayList<>();
        input.add("MI:0013");
        input.add("MI:0013");
        input.add("MI:0401");
        input.add("MI:0401");
        input.add("MI:0401");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertEquals(0, scoreRounded.compareTo(0.81f));
    }

    /**
     * An interaction annotated with two “biophysical”, one “protein complementation assay”, one “biochemical” and one “imagining technique” methods
     * 0.77
     */
    @Test
    public void testExample02(){
        List<String> input = new ArrayList<>();
        input.add("MI:0013");
        input.add("MI:0013");
        input.add("MI:0090");
        input.add("MI:0401");
        input.add("MI:0428");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertEquals(0, scoreRounded.compareTo(0.76f));
    }


    /**
     * An interaction annotated with two “protein complementation assay”, two “imagining technique” methods
     * 0.60
     */
    @Test
    public void testExample03(){
        List<String> input = new ArrayList<>();
        input.add("MI:0090");
        input.add("MI:0090");
        input.add("MI:0428");
        input.add("MI:0428");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertEquals(0, scoreRounded.compareTo(0.60f));
    }


    /**
     * An interaction annotated with two “genetic interference”, one “post transcriptional interference” and one “imagining technique” methods
     * 0.31
     */
    @Test
    public void testExample04(){
        List<String> input = new ArrayList<>();
        input.add("MI:0254");
        input.add("MI:0254");
        input.add("MI:0255");
        input.add("MI:0428");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertEquals(0, scoreRounded.compareTo(0.31f));
    }

    /**
     * An interaction annotated with one “genetic interference”, one “post transcriptional interference” methods
     * 0.12
     */
    @Test
    public void testExample05(){
        List<String> input = new ArrayList<>();
        input.add("MI:0254");
        input.add("MI:0255");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertEquals(0, scoreRounded.compareTo(0.12f));
    }

    /**
     * An interaction annotated with a method which is “uknown”: 0.04
     */
    @Test
    public void testExample06(){
        List<String> input = new ArrayList<>();
        input.add("unknown");
        MethodScore mS = new MethodScore(input);
        Float score = mS.getScore();
        Float scoreRounded = Round( score, 2 );
        Assert.assertEquals(0, scoreRounded.compareTo(0.03f));
    }



}