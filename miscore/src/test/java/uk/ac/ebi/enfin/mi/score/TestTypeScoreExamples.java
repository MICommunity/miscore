package uk.ac.ebi.enfin.mi.score;

import org.junit.Assert;
import org.junit.Test;
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
public class TestTypeScoreExamples {
    /**
     * Round numbers
     */
    private static float Round(float Rval, int Rpl) {
        float p = (float) Math.pow(10, Rpl);
        Rval = Rval * p;
        float tmp = Math.round(Rval);
        return tmp / p;
    }

    /**
     * An interaction annotated with three “direct interaction” types
     * 0.82
     */
    @Test
    public void testExample01() {
        List<String> input = new ArrayList<>();
        input.add("MI:0407");
        input.add("MI:0407");
        input.add("MI:0407");
        TypeScore tS = new TypeScore(input);
        Float score = tS.getScore();
        Float scoreRounded = Round(score, 2);
        Assert.assertEquals(0, scoreRounded.compareTo(0.82f));
    }

    /**
     * An interaction annotated with one “direct interaction”, one “association”, one “physical association” and one “colocalization” types
     * 0.77
     */
    @Test
    public void testExample02() {
        List<String> input = new ArrayList<>();
        input.add("MI:0407");
        input.add("MI:0914");
        input.add("MI:0915");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        Float score = tS.getScore();
        Float scoreRounded = Round(score, 2);
        Assert.assertEquals(0, scoreRounded.compareTo(0.77f));
    }


    /**
     * An interaction annotated with one “physical association” and one “colocalization” types
     * 0.56
     */
    @Test
    public void testExample03() {
        List<String> input = new ArrayList<>();
        input.add("MI:0915");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        Float score = tS.getScore();
        Float scoreRounded = Round(score, 2);
        Assert.assertEquals(0, scoreRounded.compareTo(0.56f));
    }


    /**
     * An interaction annotated with two “genetic interaction” and one “colocalization” types
     * 0.39
     */
    @Test
    public void testExample04() {
        List<String> input = new ArrayList<>();
        input.add("MI:0208");
        input.add("MI:0208");
        input.add("MI:0403");
        TypeScore tS = new TypeScore(input);
        Float score = tS.getScore();
        Float scoreRounded = Round(score, 2);
        Assert.assertEquals(0, scoreRounded.compareTo(0.39f));
    }

    /**
     * An interaction annotated with three “genetic interaction” types
     * 0.26
     */
    @Test
    public void testExample05() {
        List<String> input = new ArrayList<>();
        input.add("MI:0208");
        input.add("MI:0208");
        input.add("MI:0208");
        TypeScore tS = new TypeScore(input);
        Float score = tS.getScore();
        Float scoreRounded = Round(score, 2);
        Assert.assertEquals(0, scoreRounded.compareTo(0.26f));
    }

    /**
     * An interaction annotated with a type which is “unknown”
     * 0.05
     */
    @Test
    public void testExample06() {
        List<String> input = new ArrayList<>();
        input.add("unknown");
        TypeScore tS = new TypeScore(input);
        Float score = tS.getScore();
        Float scoreRounded = Round(score, 2);
        Assert.assertEquals(0, scoreRounded.compareTo(0.05f));
    }


}