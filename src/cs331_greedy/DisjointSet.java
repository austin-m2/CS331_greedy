/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs331_greedy;

/**
 *
 * @author morri
 */
public class DisjointSet {
    private int[] sets = null;
    private int[] rank = null;
    
    public DisjointSet(int n) {
        sets = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            sets[i] = i;
        }
    }
    
    public int find(int x) {
        int r = x;
        while (sets[r] != r) {
            r = sets[r];
        }
        int i = x;
        while (i != r) {
            int j = sets[i];
            sets[i] = r;
            i = j;
        }
        return r;
    }
    
    public void merge(int a, int b) {
        if (rank[a] == rank[b]) {
            rank[a] = rank[a] + 1;
            sets[b] = a;
        } else {
            if (rank[a] > rank[b]) {
                sets[b] = a;
            } else {
                sets[a] = b;
            }
        }
    }
    
    
    
    
}
