package uk.ac.ebi.enfin.mi.score;

/**
 * Abstract class extended by all the scores
 *
 * User: rafael
 * Date: 21-Apr-2010
 * Time: 14:43:30
 */
/**
 * Superclass for all the confidence score subclasses
 */
public abstract class ConfidenceScore {
    public abstract Float getScore();

    /**
     * Calculates logarithm
     * @param base The dividend of log: log<sub>base</sub> = ln(num) / ln (base)
     * @param num The quotient of log: log<sub>base</sub> = ln(num) / ln (base)
     * @return logarithm value
     */
    protected Float logOfBase(Float base, Float num) {
        Double result = Math.log(num) / Math.log(base);
        return result.floatValue();
    }

    /**
     * Checks the result is a normalized value [0-1]
     * @param score Score to check
     * @return true or false
     */
    protected boolean isValidScore(Float score){
        if(score >= 0 && score <= 1){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getScore().toString();
    }
}