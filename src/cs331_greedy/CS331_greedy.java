/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs331_greedy;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author morri
 */
public class CS331_greedy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Graph graph = generateGraph(2000, true);     
        
        long startTime = System.nanoTime();
        ArrayList<Edge> pathk = kruskal(graph);
        double kruskalTime = System.nanoTime() - startTime;
        kruskalTime /= 1000000;
        
        int pathkWeight = 0;
        for (int i = 0; i < pathk.size(); i++) {
            pathkWeight += pathk.get(i).weight;
        }
        System.out.println("KRUSKALS:");
        for (int i = 0; i < pathk.size(); i++) {
            System.out.println(i + ": From node " + pathk.get(i).from +
                    " to node " +
                    pathk.get(i).to +
                    " with weight " +
                    pathk.get(i).weight);
        }
        System.out.println("Total weight: " + pathkWeight);        
        
        
        
        startTime = System.nanoTime();
        ArrayList<Edge> pathp = prim(graph);
        double primTime = System.nanoTime() - startTime;
        primTime /= 1000000;
        
        int pathpWeight = 0;
        for (int i = 0; i < pathp.size(); i++) {
            pathpWeight += pathp.get(i).weight;
        }
        System.out.println("\nPRIMS:");
        for (int i = 0; i < pathp.size(); i++) {
            System.out.println(i + ": From node " + pathp.get(i).from +
                    " to node " +
                    pathp.get(i).to +
                    " with weight " +
                    pathp.get(i).weight);
        }
        System.out.println("Total weight: " + pathpWeight);
        pathp = null;
        
        
        
        
        
        
        
        
        System.out.println("Time in Kruskal's algorithm: " + kruskalTime + " ms.");
        System.out.println("Time in Prim's algorithm: " + primTime + " ms.");
        
    }
    
    public static Graph generateGraph(int numNodes, boolean dense) {
        Graph graph = new Graph(numNodes);
        
        //add nodes to make graph connected
        for (int i = 1; i < numNodes; i++) {
            int to = (int)(Math.random() * i);
            
            byte weight = (byte)((byte)(Math.random() * 101) + (byte)1);
            graph.addEdge(i, to, weight);
        }
        
        double probability;
        probability = dense ? 0.80 : 0.05;
        
        for (int i = 0; i < numNodes; i++) {
            for (int j = i; j < numNodes; j++) {
                if (i == j) {continue;}
                double chance = Math.random();
                if (probability > chance) {
                    byte weight = (byte)((byte)(Math.random() * 101) + (byte)1);
                    graph.addEdge(i, j, weight);
                }
            }
        }
        
        return graph;
    }
    
    static ArrayList<Edge> kruskal(Graph graph) {
        ArrayList<Edge> sortedEdges = graph.getEdges();
        ArrayList<Edge> result = new ArrayList<>();
        DisjointSet sets = new DisjointSet(graph.size);
         
        //result.ensureCapacity(0);
        while (result.size() != graph.size - 1) {
            Edge edge = sortedEdges.get(0);
            int uset = sets.find(edge.from);
            int vset = sets.find(edge.to);
            
            if (uset != vset) {
                sets.merge(uset, vset);
                result.add(edge);
                //System.out.println(result.size());
            }
            
            sortedEdges.remove(0);
        }
        return result;
    }
    
    
    static ArrayList<Edge> prim(Graph graph) {
        int     parent[]    = new int[graph.size];
        byte    key[]       = new byte[graph.size];
        boolean included[]    = new boolean[graph.size];
        
        for (int i = 0; i < graph.size; i++) {
            key[i] = Byte.MAX_VALUE;
            included[i] = false;
        }
        
        key[0] = 0;
        parent[0] = -1;
        
        for (int count = 0; count < graph.size - 1; count++) {
            //find vertex with minimum key
            byte min = Byte.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < graph.size; i++) {
                if (!included[i] && key[i] < min) {
                    min = key[i];
                    minIndex = i;
                }
            }
            
            //add picked vertex
            included[minIndex] = true;
            
            for (int i = 0; i < graph.size; i++) {
                byte weight = graph.getWeight(minIndex, i);
                if (weight != 0 && !included[i] && weight < key[i]) {
                    parent[i] = minIndex;
                    key[i] = weight;
                }
            }   
        }
        
        ArrayList<Edge> result  = new ArrayList<>();
        for (int i = 1; i < graph.size; i++) {
            Edge edge = new Edge();
            edge.from = parent[i];
            edge.to = i;
            edge.weight = graph.getWeight(edge.from, edge.to);
            result.add(edge);
        }
        
        return result;
    }
    
    
}
