import java.util.*;
import java.io.*;

/**
 * Filename: CompilerGraph.java
 * Author: Tea
 * Date: Oct 11, 2016
 * Purpose: Implements the fields and methods necessary to build a directed 
 * * * graph of vertices. 
 */
public class CompilerGraph <T> {
    private HashMap<Integer, String> vertices;
    private ArrayList<LinkedList<Integer>> adjacencies;
    private int curIdx = 0;
    private HashMap<String, Vertex> vertexMap = new HashMap<>();
    private ArrayList<String> headList;
    private Stack<Vertex> vertStack = new Stack();
    
    public CompilerGraph() {
        vertices = new HashMap<>();
        vertexMap = new HashMap<>();
        adjacencies = new ArrayList<>();
        headList = new ArrayList<>();
    }
    
    // Initializes the graph and builds the agacency list.
    public void initializeGraph(Scanner input) {
        Scanner curLine;
        String curName;
        Vertex<String> curVertex;
        
        while (input.hasNextLine()) {
            curLine = new Scanner(input.nextLine());
            curName = curLine.next();
            headList.add(curName);
            
            if (vertexMap.containsKey(curName)) {
                curVertex = vertexMap.get(curName);
                if (curVertex.isDiscovered()) {
                    throw new CycleDetectedException(curVertex.getValue());
                }
                else {
                    curVertex.setDiscovered();
                }
            }
            else {
                vertexMap.put(curName, new Vertex<String>(curName));
            }
            
            while(curLine.hasNext()) {
                String newName = curLine.next();
                if (vertexMap.containsKey(newName)) {
                    Vertex<String> newVertex = vertexMap.get(newName);
                    vertexMap.get(curName).addChild(newVertex);
                    if (newVertex.isDiscovered()) {
                        throw new CycleDetectedException(newVertex.getValue());
                    }
                    else {
                        newVertex.setDiscovered();
                    }
                }
                
                else {
                    vertexMap.put(newName, new Vertex<String>(newName));
                    vertexMap.get(curName).addChild(vertexMap.get(newName));
                }
            }
        }
        
        int vertIdx = 0;
        String newName = "";
        
        for (String head : headList) {
            curVertex = vertexMap.get(head);
            buildAdjacencyList(curVertex);
        }
        
    }
    
    // Takes a single vertex and adds to the adjacency list that
    // vertex and all of its children, utilizing the addEdge() method
    public void buildAdjacencyList(Vertex<String> vertex) {
        String curName = "";
        int vertIdx = 0;
        String newName = "";
        
        curName = vertex.getValue();
        if (vertices.containsValue(curName)) {
            vertIdx = getKey(curName);
        }
        else {
            vertIdx = curIdx;
            vertices.put(curIdx, curName);
            curIdx++;
            adjacencies.add(new LinkedList<Integer>());
        }
        if (vertex.hasChildren()){
            for (Vertex<String> child : vertex.getChildren()) {
                addEdge(vertex, child);
                buildAdjacencyList(child);
            }
        }
    }
    
    // Adds an edge to the corresponding adjacency list
    public void addEdge(Vertex<String> source, Vertex<String> destination) {
        int sourceIdx = getKey(source.getValue());
        int destIdx;
        if (vertices.containsValue(destination.getValue())) {
            destIdx = getKey(destination.getValue());
        }
        else {
            destIdx = curIdx;
            curIdx++;
            vertices.put(destIdx, destination.getValue());
            adjacencies.add(new LinkedList<Integer>());
        }
        
        adjacencies.get(sourceIdx).add(destIdx);
        
    }
    
    // Helper method to simplify getting the corresponding key for a given value
    // in the vertices HashMap
    public Integer getKey(String value) {
        for(HashMap.Entry<Integer, String> entry : vertices.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return -1;
    }
    
    // Returns as a String the topological order emmanating from the given class
    // name. Throws an Exception if the class name does not exist in the graph. 
    // Initializes the depthFirstSearch() method.
    public String topologicalOrder(String vertName) {
        // Resets the discovered and finished flags for each vertex
        for (HashMap.Entry<String, Vertex> entry : vertexMap.entrySet()) {
            entry.getValue().reset();
        }
        String result = "";
        
        if (vertices.containsValue(vertName)){
        
            depthFirstSearch(vertexMap.get(vertName));

            while (!vertStack.isEmpty()) {
                result += vertStack.pop().getValue() + " ";
            }

            return result;
        }
        
        else {
            throw new InvalidClassNameException(vertName);
        }
    }
    
    // Utilizes a depth first search algorithm to generate the topological order 
    // from a given vertex.  
    public void depthFirstSearch(Vertex<String> vertex) {
        if (vertex.isDiscovered()) {
            throw new CycleDetectedException(vertex.getValue());
        }
        if (vertex.isFinished()) {
            
            return;
        }
        
        vertex.setDiscovered();
        
        if (vertex.hasChildren()) {
            for (Vertex<String> child : vertex.getChildren()) {
                depthFirstSearch(child);
            }
        
        }
        
        vertex.setFinished();
        
        vertStack.push(vertex);
        
    }

}
