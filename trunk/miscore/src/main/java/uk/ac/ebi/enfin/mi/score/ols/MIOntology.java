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
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Class using the OLS service to retrieve ontology terms for the MI ontology
 * User: rafael
 * Date: 17-May-2010
 * Time: 15:11:50
 */
public class MIOntology {
    static Logger logger = Logger.getLogger(MIOntology.class);
    private Map<String,String> mapIdName;

    /**
     * Get all the children terms in the MI ontology for one specific MI ontology
     * term id.
     * @param parentTerm
     * @return a map "ontology term id":"ontology term name"
     */
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
                    //todo: is there a way to distinguish between wrong Ontology ID and no children?
                    logger.warn(termId + ", wrong Ontology term ID or no children for this parent term");
                }
            }
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return mapIdName;
    }

    /**
     * Recursive method to get children terms from children terms in a
     * flat key value map with term ids and term names.
     * @param termChildren
     */
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
                //logger.debug("No children for " + termId);
            }
        }
    }

    /**
     * Get all the children terms in the MI ontology for more than one MI ontology
     * term id.
     * @param termsToQuery Parent ontology terms
     * @return a map "queried ontology term id":"children terms". The children
     * terms are represented in a map "ontology term id":"ontology term name".
     */
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
