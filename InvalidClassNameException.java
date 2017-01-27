/**
 * Filename: Vertex.java
 * Author: Tea
 * Date: Oct 13, 2016
 * Purpose: Implements an Exception for when the user inputs the name of a class
 * * * not found in the graph. The illegalCLass field allows the GUI to pull the 
 * * * offending class name. 
 */
public class InvalidClassNameException extends RuntimeException {
    private String illegalClass = "";

    /**
     * Creates a new instance of <code>CycleDetectedException</code> without
     * detail message.
     */
    public InvalidClassNameException() {
    }

    /**
     * Constructs an instance of <code>CycleDetectedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidClassNameException(String className) {
        illegalClass = className;
    }
    
    public String getClassName() {
        return illegalClass;
    }
}
