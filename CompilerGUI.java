import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * Filename: CompilerGraph.java
 * Author: Tea
 * Date: Oct 11, 2016
 * Purpose: Creates a GUI for testing the CompilerGraph and related classes. 
 */
public class CompilerGUI extends JFrame {
    private static final int TEXT_FIELD_WIDTH = 7;
    CompilerGraph graph;
    boolean initialized = false;
    
    public CompilerGUI() {
        super("Class Dependency Graph");
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 10));
        
        //Initialize JPanels
        JPanel inputFrame = new JPanel(new BorderLayout(25,0));
        inputFrame.setBorder(new EmptyBorder(10, 20, 0, 20));
        JPanel outputFrame = new JPanel(new BorderLayout());
        
        JPanel inputLabels = new JPanel(new BorderLayout());
        JPanel inputFields = new JPanel(new BorderLayout());
        JPanel inputButtons = new JPanel(new BorderLayout(10,5));
        
        //Initialize components
        //Labels
        JLabel fileLabel = new JLabel("Input file name: ");
        JLabel classLabel = new JLabel("Class to recompile: ");
        
        //Fields
        JTextField fileField = new JTextField(TEXT_FIELD_WIDTH);
        JTextField classField = new JTextField(TEXT_FIELD_WIDTH);
        
        //Buttons
        JButton buildButton = new JButton("Build Directed Graph");
        JButton topoButton = new JButton("Topological Order");
        
        //Output area
        JLabel recompileLabel = new JLabel("Recompilation Order: ");
        
        JTextArea outputArea = new JTextArea(11, 50);
        JScrollPane scrollPane = new JScrollPane(outputArea); 
        outputArea.setEditable(false);
        
        //Listeners
        buildButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                
                Scanner fileIn = null;
                String fileName = fileField.getText();

                try {
                    fileIn = new Scanner(new BufferedReader(
                            new FileReader(fileName)));
                    graph = new CompilerGraph();
                    graph.initializeGraph(fileIn);
                    initialized = true;
                    JOptionPane.showMessageDialog(null, 
                            "Graph built succesfully!", "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    
                }
                catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "\"" + fileName + "\"" +
                            " is not a valid input. Please enter a valid filename.", 
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
                catch (CycleDetectedException e) {
                    JOptionPane.showMessageDialog(null, "A cycle has been"
                                + "detected. Please try a different input.", 
                                "Cycle Detected", JOptionPane.ERROR_MESSAGE);
                }

                finally {
                    
                    
                    if (fileIn != null) {
                        fileIn.close();
                        
                    }
                }
                
                
                
            }
            
        });
        
        topoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (!initialized) {
                    JOptionPane.showMessageDialog(null, "No graph has been " +
                            "initialized. Please initialize a graph.", 
                            "Invalid Graph", JOptionPane.ERROR_MESSAGE);
                }
                
                else {
                    try {
                        outputArea.setText(graph.topologicalOrder(
                                classField.getText()));
                    }
                    catch(InvalidClassNameException exc) {
                        JOptionPane.showMessageDialog(null, exc.getClassName() + 
                                " is not a valid class name. Please enter a valid"
                                + " class name.", "Invalid Class Name", 
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
            
        
        //Add components to panels
        inputLabels.add(fileLabel, BorderLayout.NORTH);
        inputLabels.add(classLabel, BorderLayout.SOUTH);
        
        inputFields.add(fileField, BorderLayout.NORTH);
        inputFields.add(classField, BorderLayout.SOUTH);
        
        inputButtons.add(buildButton, BorderLayout.NORTH);
        inputButtons.add(topoButton, BorderLayout.SOUTH);
        
        inputFrame.add(inputLabels, BorderLayout.WEST);
        inputFrame.add(inputFields, BorderLayout.CENTER);
        inputFrame.add(inputButtons, BorderLayout.EAST);
        
        outputFrame.add(recompileLabel, BorderLayout.NORTH);
        outputFrame.add(outputArea, BorderLayout.SOUTH);
        
        add(inputFrame, BorderLayout.NORTH);
        add(outputFrame, BorderLayout.SOUTH);
    }

    
    
    public static void main(String[] args) {
        CompilerGUI gui = new CompilerGUI();
        gui.pack();
        gui.setVisible(true);
    }
    
}
