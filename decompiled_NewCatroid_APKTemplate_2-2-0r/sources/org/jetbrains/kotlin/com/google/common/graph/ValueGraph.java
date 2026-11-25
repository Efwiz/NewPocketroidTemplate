package org.jetbrains.kotlin.com.google.common.graph;

import java.util.Optional;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public interface ValueGraph<N, V> extends BaseGraph<N> {
    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    Set<N> adjacentNodes(N n);

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    boolean allowsSelfLoops();

    Graph<N> asGraph();

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    int degree(N n);

    Optional<V> edgeValue(N n, N n2);

    Optional<V> edgeValue(EndpointPair<N> endpointPair);

    V edgeValueOrDefault(N n, N n2, V v);

    V edgeValueOrDefault(EndpointPair<N> endpointPair, V v);

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    Set<EndpointPair<N>> edges();

    boolean equals(Object obj);

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    boolean hasEdgeConnecting(N n, N n2);

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    boolean hasEdgeConnecting(EndpointPair<N> endpointPair);

    int hashCode();

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    int inDegree(N n);

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    ElementOrder<N> incidentEdgeOrder();

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    Set<EndpointPair<N>> incidentEdges(N n);

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    boolean isDirected();

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    ElementOrder<N> nodeOrder();

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    Set<N> nodes();

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    int outDegree(N n);

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    Set<N> predecessors(N n);

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    Set<N> successors(N n);
}
