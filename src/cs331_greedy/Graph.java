/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs331_greedy;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author morri
 */
public class Graph {
    public int size;
    private byte[][] edges = null;
    /*         
                j
       0     1     2     3
    0      [0to1][0to2][0to3]
 i  1            [1to2][1to3]
    2                  [2to3]
          
                    j
        0              1           2
    0 [i to j+i+1][i to j+i+1][i to j+i+1]
 i  1 [i to j+i+1][i to j+i+1]
    2 [i to j+i+1]
    
    */
    public Graph(int n) {
        size = n;
        edges = new byte[n][];
        
        for (int i = 0; i < n; i++) {
            edges[i] = new byte[n - 1 - i];
        }
    }
    
    public byte getWeight(int f, int t) {
        if (f == t) {
            return 0;
        }
        int i = Math.min(f, t);
        int j = Math.max(f, t) - i - 1;
        return edges[i][j];
    }
    
    public void addEdge(int f, int t, byte w) {
        if (f > edges.length || t > edges.length) {
            System.out.println("ERROR: Node does not exist.");
            System.exit(1);
        }
        
        if (w == 0) {
            System.out.println("ERROR: Don't add an edge of weight 0!");
            System.exit(1);
        }
        
        if (f == t) {
            System.out.println("WARNING: No path from node to itself.");
            return;
        }
        
        
        int from = Math.min(f, t);
        int to   = Math.max(f, t) - from - 1;
        
        if (edges[from][to] == 0) {
            edges[from][to] = w;
        } else {
            edges[from][to] = (byte) Math.min(edges[from][to], w);
        }
        
    }
    
    public ArrayList getEdges() {
        //make arraylist of edges
        ArrayList<Edge> sortedEdges = new ArrayList<>();
        
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].length; j++) {
                if (edges[i][j] != 0) {
                    Edge edge = new Edge();
                    edge.from = i;
                    edge.to = j + i + 1;
                    edge.weight = edges[i][j];
                    sortedEdges.add(edge);
                }
            }
        }
        
        sortedEdges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return Byte.compare(edge1.weight, edge2.weight);
            }  
        });
        
        return sortedEdges;
    }

    
}
