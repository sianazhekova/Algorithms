package uk.ac.cam.sz373.Algorithms.Tick3;

public interface MaxFlow {
    void maximize(LabelledGraph g, int s, int t);
    int value();
    Iterable<LabelledGraph.Edge> flows();
    java.util.Set<Integer> cut();
}