# MI-Score

## Local psimiOntology.json file building

To generate the JSON used when we use the local calculation option, in order to update it, run the following commands
```bash
mvn clean package -DskipTests -P json-generation
java -jar target/local-ontology-json-generator-exec.jar
mv psimiOntology.json src/resources
mvn clean test
```
