/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs331_greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author morri
 */
public class Graph_Old {

    private int nodes;
    private ArrayList<int[]> edges = new ArrayList<int[]>();

    public Graph_Old(int n) {
        nodes = n;
    }

    public ArrayList getEdges() {
        sortEdges();
        return edges;
    }

    public void sortEdges() {
        Collections.sort(edges, new Comparator<int[]>() {
            @Override
            public int compare(int[] edge1, int[] edge2) {
                return Integer.compare(edge1[2], edge2[2]);
            }
        });
    }

    /**
     * Returns the weight of the edge from one node to another.
     * 
     * @param f the "from" node
     * @param t the "to" node
     * @return the weight of the edge if it exists, -1 if it doesn't
     */
    public int getWeight(int f, int t) {
        for (int i = 0; i < edges.size(); i++) {
            if ((edges.get(i)[0] == f && edges.get(i)[1] == t)
                    || (edges.get(i)[0] == t && edges.get(i)[1] == f)) {
                //these 2 nodes already have an edge
                return edges.get(i)[2];
            }
        }
        return -1;
    }

    public void addEdge(int f, int t, int w) {
        if (f > nodes || t > nodes) {
            System.out.println("ERROR: Node does not exist.");
            System.exit(1);
        }
        
//        int currentWeight = getWeight(f, t);
//        if (currentWeight == -1) {
//            edges.add(new int[]{f, t, w});
//        } else if (w < currentWeight) {
//            
//        }
        
        //checks if edge is already in the graph
        //if it is, and the new edge has a lower weight,
        //the edge is replaced with the new edge
        for (int i = 0; i < edges.size(); i++) {
            int[] edge = edges.get(i);
            if ((edge[0] == f && edge[1] == t)
                    || (edge[0] == t && edge[1] == f)) {
                //these 2 nodes already have an edge
                if (w < edge[2]) {
                    edge[2] = w;
                    //sortEdges();
                }
                return;
            }
        }
        
        //the edge is not already in the graph; add it
        edges.add(new int[]{f, t, w});
        //sortEdges();
    }
}
