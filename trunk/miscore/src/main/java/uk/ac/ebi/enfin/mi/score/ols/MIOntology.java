package uk.ac.ebi.enfin.mi.score.ols;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Class using the OLS service to retrieve ontology terms for the MI ontology
 * User: rafael
 * Date: 17-May-2010
 * Time: 15:11:50
 */
public class MIOntology {
    static Logger logger = Logger.getLogger(MIOntology.class);
    private Map<String,String> mapIdName;
    private final String psimiJson = "psimiOntology.json";
    boolean useOLS;

    public MIOntology() {
        this.useOLS = true;
    }

    public MIOntology(boolean useOLS) {
        this.useOLS = useOLS;

    }


    /**
     * Get all the children terms in the MI ontology for one specific MI ontology
     * term id from OLS.
     * @param parentTerm
     * @return a map "ontology term id":"ontology term name"
     */
    private Map<String,String> getJsonChildrenFromOLS(String parentTerm){
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
                JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonText);
                return getMapIdNameFromJsonObject(jsonObject);
            }
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return mapIdName;
    }



    /**
     * Get all the children terms in the MI ontology for one specific MI ontology
     * term id from OLS.
     * @param jsonObject
     * @return a map "ontology term id":"ontology term name"
     */
    private Map<String,String> getMapIdNameFromJsonObject(JSONObject jsonObject){
        mapIdName = new HashMap<String,String>();
        if(jsonObject != null){
            try{
                JSONArray termChildren = jsonObject.getJSONArray("children");
                //mapIdName.put(termId,termName); // Include the parentTerm
                getMapIdNameFromJsonArray(termChildren);
            } catch (JSONException e){
                logger.info("wrong Ontology term ID or no children for this parent term");
            }
        }
        return mapIdName;
    }

    /**
     * Get all the children terms in the MI ontology for one specific MI ontology
     * term id from file.
     * @param parentTerm
     * @return a map "ontology term id":"ontology term name"
     */
    private Map<String,String> getJsonChildrenFromFile(String parentTerm){
        String json = "";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(this.psimiJson);
        json = getStringFromInputStream(is);
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(json);
        //jsonObject.get("children").toString();
        JSONObject jsonResults = findOntologyTerm(jsonObject, parentTerm);
        if(jsonResults == null){
            jsonResults = new JSONObject();
            jsonResults.put(parentTerm, "unknown");
        }
        return getMapIdNameFromJsonObject(jsonObject);
    }

    /**
     * Find ontology term recursive method
     * @param jsonObject
     * @param ontologyTerm
     * @return
     */
    private JSONObject findOntologyTerm(JSONObject jsonObject,String ontologyTerm){
        String id = jsonObject.getString("id");
        if(id.equalsIgnoreCase(ontologyTerm)){
            return jsonObject;
        } else {
            try{
                JSONArray termChildren = jsonObject.getJSONArray("children");
                for(Object child:termChildren){
                    JSONObject jsonChild = (JSONObject) JSONSerializer.toJSON(child);
                    id = jsonChild.getString("id");
                    if(id.equalsIgnoreCase(ontologyTerm)){
                        return jsonChild;
                    }
                }
                for(Object child:termChildren){
                    JSONObject jsonChild = (JSONObject) JSONSerializer.toJSON(child);
                    JSONObject jsonChildObject = findOntologyTerm(jsonChild,ontologyTerm);
                    if(jsonChildObject != null){
                        return jsonChildObject;
                    }
                }
            } catch (JSONException e){
                //no children
            }
        }
        return null;
    }


    /**
     * Get all the children terms in the MI ontology for one specific MI ontology
     * term id.
     * @param parentTerm
     * @return a map "ontology term id":"ontology term name"
     */
    public Map<String,String> getJsonChildren(String parentTerm){
        if(useOLS){
            return getJsonChildrenFromOLS(parentTerm);
        } else {
            return getJsonChildrenFromFile(parentTerm);
        }
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



    /**
     * convert InputStream to String
     * @param is
     * @return
     */
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

}
