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

        Graph graph = generateGraph(1000, false);     
        
        long startTime = System.nanoTime();
        ArrayList<Edge> pathp = honkhonkprims(graph);
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
        
        
        startTime = System.nanoTime();
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
        probability = dense ? 0.90 : 0.10;
        
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
         
        while (result.size() != graph.size - 1) {
            Edge edge = sortedEdges.get(0);
            int uset = sets.find(edge.from);
            int vset = sets.find(edge.to);
            
            if (uset != vset) {
                sets.merge(uset, vset);
                result.add(edge);
                System.out.println(result.size());
            }
            
            sortedEdges.remove(0);
        }
        return result;
    }
    
    static ArrayList<Edge> prim(Graph graph) {
        ArrayList<Edge> result  = new ArrayList<>();
        int[]           nearest = new int[graph.size];
        byte[]          minDist = new byte[graph.size];
        
        for (int i = 1; i < graph.size; i++) {
            nearest[i] = 0;
            minDist[i] = graph.getWeight(i, 0);
        }   
        
        for (int n = 1; n < graph.size; n++) {
            int min = Integer.MAX_VALUE;
            int k = 0;
            
            for (int j = 1; j < graph.size; j++) {
                if (minDist[j] != 0 && minDist[j] < min) {
                    min = minDist[j];
                    k = j;
                }
            }
            
            Edge edge = new Edge();
            edge.from = nearest[k];
            edge.to = k;
            edge.weight = graph.getWeight(edge.from, edge.to);
            result.add(edge);
            minDist[k] = -1;
            
            for (int j = 1; j < graph.size; j++) {
                if (graph.getWeight(j, k) != 0 && graph.getWeight(j, k) < minDist[j]) {
                    minDist[j] = graph.getWeight(j, k);
                    nearest[j] = k;
                }
            }
        }
        return result;
    }
    
    static ArrayList<Edge> honkhonkprims(Graph graph) {
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
    
    static ArrayList<Edge> prims(Graph graph) {
        int[] minDist = new int[graph.size];
        int[] nearest = new int[graph.size];
        
        for (int i = 0; i < graph.size; i++) {
            minDist[i] = Integer.MAX_VALUE;
            nearest[i] = -1;
        }
        
        minDist[0] = 0;
        
        ArrayList<Edge> result = new ArrayList<>();
        ArrayList<Edge> edges = graph.getEdges();
        ArrayList<Integer> remainingVertices = new ArrayList<>();
        
        for (int i = 0; i < graph.size; i++) {
            remainingVertices.add(i);
        }
        
        //initialize
//        remainingVertices.remove(Integer.valueOf(0));
//        for (int i = 0; i < edges.size(); i++) {
//            Edge edge = edges.get(i);
//            if (edge.from == 0)
//            
//        }
        
        while (!remainingVertices.isEmpty()) {
            
        }
        
        minDist[0] = 0;
        

        
        return result;
                
    }
    
    
//    public static Graph_Old generateGraphOld(int numNodes, boolean dense) {
//        Graph_Old graph = new Graph_Old(numNodes);
//        
//        //add nodes to make graph connected
//        for (int i = 1; i < numNodes; i++) {
//            int node = (int)(Math.random() * i);
//            int weight = (int)(Math.random() * 101) + 1;
//            graph.addEdge(i, node, weight);
//            
//
//            System.out.println("initial edge: " + i);
//
//        }
//        
//        double probability;
//        probability = dense ? 0.90 : 0.10;
//        
//        for (int i = 0; i < numNodes; i++) {
//            for (int j = i; j < numNodes; j++) {
//                double chance = Math.random();
//                if (probability > chance) {
//                    int weight = (int)(Math.random() * 101) + 1;
//                    graph.addEdge(i, j, weight);
//                }
//            }
//            
//            System.out.println("additional edge: " + i);
//            
//        }
//  
//        return graph;
//    }
    
}
