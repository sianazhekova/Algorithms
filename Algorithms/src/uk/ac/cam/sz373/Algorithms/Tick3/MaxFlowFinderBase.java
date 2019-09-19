package uk.ac.cam.sz373.Algorithms.Tick3;

/*import uk.ac.cam.cl.tester.Algorithms.MaxFlow;
import uk.ac.cam.cl.tester.Algorithms.LabelledGraph; */
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;


public abstract class MaxFlowFinderBase implements MaxFlow {

    private LabelledGraph g;
    private int source;
    private int[][] flow;
    private boolean[] cut;

    public int value() {
        int v = 0;
        for (int j=0; j<g.numVertices(); j++) v += flow[source][j];
        for (int i=0; i<g.numVertices(); i++) v -= flow[i][source];
        return v;
    }

    public Iterable<LabelledGraph.Edge> flows() {
        List<LabelledGraph.Edge> res = new ArrayList<LabelledGraph.Edge>();
        for (int i=0; i<g.numVertices(); i++) for (int j=0; j<g.numVertices(); j++) if (flow[i][j]>0)
            res.add(new LabelledGraph.Edge(i, j, flow[i][j]));
        return res;
    }

    public Set<Integer> cut() {
        Set<Integer> res = new HashSet<Integer>();
        for (int i=0; i<cut.length; i++) if (cut[i]) res.add(i);
        return res;
    }

    public void maximize(LabelledGraph g, int s, int t) {
        this.g = g;
        this.source = source;
        int n = g.numVertices();
        this.flow = new int[n][n];
        this.cut = new boolean[n];
        // TODO: run Ford-Fulkerson to fill in flow[][] and cut[]
    }

    // TODO: use BFS to find an augmenting path from src to dst
    public abstract List<Integer> getAugmentingPath(int src, int dst);
}