package uk.ac.ebi.enfin.mi.score.local;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import uk.ac.ebi.enfin.mi.score.scores.UnNormalizedPublicationScore;
import uk.ac.ebi.pride.utilities.ols.web.service.client.OLSClient;
import uk.ac.ebi.pride.utilities.ols.web.service.config.OLSWsConfig;
import uk.ac.ebi.pride.utilities.ols.web.service.model.Identifier;
import uk.ac.ebi.pride.utilities.ols.web.service.model.Identifier.IdentifierType;
import uk.ac.ebi.pride.utilities.ols.web.service.model.Term;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

public class LocalJSONGenerator {
    private static final Logger logger = Logger.getLogger(UnNormalizedPublicationScore.class);
    private static final OLSClient client = new OLSClient(new OLSWsConfig());
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        Term root = client.getTermById(new Identifier("MI:0000", IdentifierType.OBO), "MI");
        try {
            mapper.writeValue(new File("psimiOntology.json"), extractCoreData(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static SimpleOntologyNode extractCoreData(Term term) {
        logger.debug("Processing " + term.getOboId() + " : " + term.getName());
        return SimpleOntologyNode.builder()
                .id(term.getOboId().getIdentifier())
                .name(term.getName())
                .children(client.getTermChildren(term.getTermOBOId(), "MI", 1).stream()
                        .map(LocalJSONGenerator::extractCoreData)
                        .collect(Collectors.toList()))
                .build();
    }
}
