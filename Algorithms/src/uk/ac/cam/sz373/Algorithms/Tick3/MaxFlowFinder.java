package uk.ac.cam.sz373.Algorithms.Tick3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set; /*
import uk.ac.cam.cl.tester.Algorithms.MaxFlow;
import uk.ac.cam.cl.tester.Algorithms.LabelledGraph;  */

import uk.ac.cam.sz373.Algorithms.Tick3.LabelledGraph.Edge;

public class MaxFlowFinder implements MaxFlow {

    private LabelledGraph g;
    private int source;
    private int[][] flow;
    private boolean[] cut;

    @Override
    public int value() {
        int v = 0;
        for (int j=0; j<g.numVertices(); j++) v += flow[source][j];
        for (int i=0; i<g.numVertices(); i++) v -= flow[i][source];
        return v;
    }
    
   @Override
    public Iterable<LabelledGraph.Edge> flows() {
        List<LabelledGraph.Edge> res = new ArrayList<LabelledGraph.Edge>();
        for (int i=0; i<g.numVertices(); i++) for (int j=0; j<g.numVertices(); j++) if (flow[i][j]>0)
            res.add(new LabelledGraph.Edge(i, j, flow[i][j]));
        return res;
    }
   
   @Override
    public Set<Integer> cut() {
        Set<Integer> res = new HashSet<Integer>();
        for (int i=0; i<cut.length; i++) if (cut[i]) res.add(i);
        return res;
    }
   
   @Override
    public void maximize(LabelledGraph g, int s, int t) {
        this.g = g;
        this.source = source;
        int n = g.numVertices();
        this.flow = new int[n][n];
        this.cut = new boolean[n];
        // TODO: run Ford-Fulkerson to fill in flow[][] and cut[]
        int deltaVar = Integer.MAX_VALUE;
        List <Integer> path = augmentPath(s, t);  // get the augmented path in the form of a list of Ints
        while(path != null){
            for (int index = 0; index < path.size() - 1; index ++){  // forward pass
                if ( flow[path.get(index)][path.get(index + 1)] < g.capacity(path.get(index),path.get(index + 1))){ // if it does not exceed the max capacity of a given edge
                    deltaVar = Math.min(deltaVar, (g.capacity(path.get(index),path.get(index + 1))-flow[path.get(index)][path.get(index + 1)]));
                }  // backward pass of flow
                else if (flow[path.get(index + 1)][path.get(index)] > 0 ){ deltaVar = Math.min( deltaVar, flow[path.get(index + 1)][path.get(index)]);}
                else {  System.out.println("There's an error"); }
            }
            for (int ind =0; ind < path.size()-1; ind++){
            	if ( flow[path.get(ind)][path.get(ind + 1)] < g.capacity(path.get(ind),path.get(ind+1)) ){
            		flow[path.get(ind)][path.get(ind + 1)] = flow[path.get(ind)][path.get(ind +1)] + deltaVar;
                    }
            	else { flow[path.get(ind + 1)][path.get(ind)] = flow[path.get(ind + 1)][path.get(ind)] - deltaVar;}
            }
            path = augmentPath(s, t);  // augment the current path
       }
    }
    // TODO: use BFS to find an augmenting path from src to dst
    public List<Integer> augmentPath(int src, int dst) {
    	
        List<Integer> path =new ArrayList<>();
        boolean[] visitedVertices = new boolean[g.numVertices()]; // Initially set all the vertices to False --> not traversed yet
        int [] originList = new int[g.numVertices()];
        for (int i=0; i<g.numVertices(); i++){
            visitedVertices[i] = false;
            originList[i] = -1;
        }
        Queue<Integer> toExplore=new LinkedList<>();
        toExplore.add(src);  // queue is initialised with having s as the starting element
        visitedVertices[src]=true; //once visited, flag it --> visited
        while(!toExplore.isEmpty()) {
            int v = toExplore.remove();  // here we will extract the vertex v from the toExplore queue whose neighbours are to be visited
            for (int i = 0; i < g.numVertices(); i++) {
                if (g.capacity(v, i) > flow[v][i] || (flow[i][v] > 0)) {
                    if (!visitedVertices[i]) {
                    	
                        toExplore.add(i);
                        visitedVertices[i] = true;
                        originList[i] =v;
                        
                    }
                }
            }

        }
        if (originList[dst]==-1){
            cut = visitedVertices;
            return null;
        }
        else{
            path.add(dst);
            while(originList[path.get(path.size()-1)] != src){
                path.add(originList[path.get(path.size() - 1)]);
            }
            path.add(src);
            int temp=0;
            for (int j = 0; j < path.size()/2; j++){
                temp = path.get(j);
                path.set(j, path.get(path.size()-(1+j)));
                path.set((path.size()-(j+1)), temp);
            }
            return path;
        }
    }

    // TODO: use BFS to find an augmenting path from src to dst
    //public abstract List<Integer> getAugmentingPath(int src, int dst);
    public static void main(String[] args) {
        System.out.println();
        try {
        	LabelledGraph myGraph = new LabelledGraph("https://www.cl.cam.ac.uk/teaching/1819/Algorithms/ticks/res/flownetwork_11.csv");
        	MaxFlowFinder flowFinder=new MaxFlowFinder();
        	flowFinder.maximize(myGraph, 0, myGraph.numVertices() -1);
        	
        	}
        catch(IOException e){System.out.println("Empty handler");}

    }
}
