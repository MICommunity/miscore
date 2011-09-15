package uk.ac.ebi.enfin.mi.score.ols;

import java.util.Map;
import org.junit.Assert;
import org.junit.Test;



/**
 * Created by IntelliJ IDEA.
 * User: rafael
 * Date: 17-May-2010
 * Time: 15:21:06
 * To change this template use File | Settings | File Templates.
 */
public class TestMIOntology {

    @Test
    public void testFalseChildren(){
        MIOntology MIO = new MIOntology();
        Map<String,String> children = MIO.getJsonChildren("MI:99999999");
        Assert.assertTrue(children.size() == 0);
    }

    @Test
    public void testGetJsonChildren(){
        MIOntology MIO = new MIOntology();
        Map<String,String> children = MIO.getJsonChildren("MI:0660");
        Assert.assertTrue(children.size() > 6);
    }

}

