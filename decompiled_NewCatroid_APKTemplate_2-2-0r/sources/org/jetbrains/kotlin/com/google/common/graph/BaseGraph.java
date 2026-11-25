package org.jetbrains.kotlin.com.google.common.graph;

import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
interface BaseGraph<N> {
    Set<N> adjacentNodes(N n);

    boolean allowsSelfLoops();

    int degree(N n);

    Set<EndpointPair<N>> edges();

    boolean hasEdgeConnecting(N n, N n2);

    boolean hasEdgeConnecting(EndpointPair<N> endpointPair);

    int inDegree(N n);

    ElementOrder<N> incidentEdgeOrder();

    Set<EndpointPair<N>> incidentEdges(N n);

    boolean isDirected();

    ElementOrder<N> nodeOrder();

    Set<N> nodes();

    int outDegree(N n);

    @Override // 
    Set<N> predecessors(N n);

    @Override // 
    Set<N> successors(N n);
}
