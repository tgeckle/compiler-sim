/**
 * Filename: Vertex.java
 * Author: Tea
 * Date: Oct 12, 2016
 * Purpose: Implements an Exception for when there is a cycle detected within 
 * * * the inputted graph. 
 */
public class CycleDetectedException extends RuntimeException {
    private String cycleVert = "";

    /**
     * Creates a new instance of <code>CycleDetectedException</code> without
     * detail message.
     */
    public CycleDetectedException() {
    }

    /**
     * Constructs an instance of <code>CycleDetectedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CycleDetectedException(String vertName) {
        cycleVert = vertName;
    }
    
    public String getCycleVertex() {
        return cycleVert;
    }
}
