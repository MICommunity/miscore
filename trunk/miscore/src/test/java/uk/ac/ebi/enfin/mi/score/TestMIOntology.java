package uk.ac.ebi.enfin.mi.score;

import junit.framework.TestCase;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ebi.enfin.mi.score.ols.MIOntology;

/**
 * Created by IntelliJ IDEA.
 * User: rafael
 * Date: 17-May-2010
 * Time: 15:21:06
 * To change this template use File | Settings | File Templates.
 */
public class TestMIOntology extends TestCase {
    private static String bout = "Bad output for";
    static Logger logger = Logger.getLogger(TestMethodScore.class);

// Deprecated. It sued the OLS library
//    public void testGetChildren(){
//        MIOntology MIO = new MIOntology();
////        Map<String,String> children = MIO.getChildren("MI:0362");
//        Map<String,String> children = MIO.getChildren("MI:0660");
//        logger.info("- - - - - - -");
//        logger.info("# "+ this.getName());
//        logger.info(children.size() + " children");
//        assertNotNull(bout + this.getName(), children);
//    }

    public void testFalseChildren(){
        MIOntology MIO = new MIOntology();
        Map<String,String> children = MIO.getJsonChildren("MI:99999999");
        logger.info("- - - - - - -");
        logger.info("# "+ this.getName());
        logger.info(children.size() + " children");
        assertNotNull(bout + this.getName(), children);
    }

    public void testGetJsonChildren(){
        MIOntology MIO = new MIOntology();
//        Map<String,String> children = MIO.getJsonChildren("MI:0362");
        Map<String,String> children = MIO.getJsonChildren("MI:0660");
        assertNotNull(bout + this.getName(), children);
    }

}
