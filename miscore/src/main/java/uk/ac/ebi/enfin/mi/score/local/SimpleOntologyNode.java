package uk.ac.ebi.enfin.mi.score.local;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class SimpleOntologyNode {
    private String id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SimpleOntologyNode> children;
}
