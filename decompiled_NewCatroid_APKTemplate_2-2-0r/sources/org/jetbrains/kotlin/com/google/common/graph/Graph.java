package org.jetbrains.kotlin.com.google.common.graph;

import java.util.Set;
import org.jetbrains.kotlin.com.google.errorprone.annotations.DoNotMock;

/* JADX INFO: loaded from: classes3.dex */
@DoNotMock("Use GraphBuilder to create a real instance")
public interface Graph<N> extends BaseGraph<N> {
    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    Set<N> adjacentNodes(N n);

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    boolean allowsSelfLoops();

    @Override // org.jetbrains.kotlin.com.google.common.graph.BaseGraph
    int degree(N n);

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
