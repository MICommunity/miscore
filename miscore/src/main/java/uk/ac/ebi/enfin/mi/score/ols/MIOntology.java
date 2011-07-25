package uk.ac.ebi.enfin.mi.score.ols;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.rmi.RemoteException;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Class using the OLS service to retrieve ontology terms for the MI ontology
 *
 * User: rafael
 * Date: 17-May-2010
 * Time: 15:11:50
 */
public class MIOntology {
    static Logger logger = Logger.getLogger(MIOntology.class);
    private Map<String,String> mapIdName;

// Deprecated. We are using the OLS REST service instead.
//    /**
//     * Gets all the children ontology terms for one term quering the MI ontology
//     * @param parentTerm
//     * @return
//     */
//    public Map<String,String> getChildren(String parentTerm){
//        HashMap results = null;
//        // Initialising OLS query service
//        QueryService olsQueryService = new QueryServiceLocator();
//        try {
//            Query olsQuery = olsQueryService.getOntologyQuery();
//            results = olsQuery.getTermChildren(parentTerm,"MI",-1,null);
//            logger.info("Query " + parentTerm + " in OLS");
//        } catch (ServiceException e) {
//            logger.error(e);
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (RemoteException e) {
//            logger.error(e);
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        return results;
//    }

    public Map<String,String> getJsonChildren(String parentTerm){
        mapIdName = new HashMap<String,String>();
        String jsonQuery = "http://www.ebi.ac.uk/ontology-lookup/json/termchildren?termId="+parentTerm+"&ontology=MI&depth=1000";
        String jsonText = "";
        try {
            URL url = new URL(jsonQuery);
            URLConnection olsConnection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(olsConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                jsonText += inputLine;
                in.close();
                break;
            }
            if(jsonText.length() > 0){
                JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonText);
                String termId = json.getString("id");
                String termName = json.getString("name");
                try{
                    JSONArray termChildren = json.getJSONArray("children");
                    //mapIdName.put(termId,termName); // Include the parentTerm
                    getMapIdNameFromJsonArray(termChildren);
                } catch (JSONException e){
                    logger.warn("No children for " + termId);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return mapIdName;
    }

    private void getMapIdNameFromJsonArray(JSONArray termChildren){
        for (int i = 0; i < termChildren.size(); ++i) {
            JSONObject child = termChildren.getJSONObject(i);
            String termId = child.getString("id");
            String termName = child.getString("name");
            mapIdName.put(termId,termName);
            try{
                JSONArray nextTermChildren = child.getJSONArray("children");
                if(nextTermChildren.size() > 0){
                    getMapIdNameFromJsonArray(nextTermChildren);
                }
            } catch (JSONException e){
                logger.debug("No children for " + termId);
                logger.debug(e.getMessage());
            }
        }
    }

    public Map<String,Map<String,String>> getMapOfTerms(ArrayList<String> termsToQuery){
        Map<String,Map<String,String>> mapOfTerms = new HashMap<String,Map<String,String>>();
        for(String term:termsToQuery){
            /* Add children */
            Map<String,String> children = getJsonChildren(term);
            mapOfTerms.put(term, children);
        }
        return mapOfTerms;
    }
}
