package org.jetbrains.kotlin.com.google.common.graph;

import java.util.Optional;
import java.util.Set;
import org.jetbrains.kotlin.com.google.errorprone.annotations.DoNotMock;

/* JADX INFO: loaded from: classes3.dex */
@DoNotMock("Use NetworkBuilder to create a real instance")
public interface Network<N, E> {
    Set<E> adjacentEdges(E e);

    Set<N> adjacentNodes(N n);

    boolean allowsParallelEdges();

    boolean allowsSelfLoops();

    Graph<N> asGraph();

    int degree(N n);

    Optional<E> edgeConnecting(N n, N n2);

    Optional<E> edgeConnecting(EndpointPair<N> endpointPair);

    E edgeConnectingOrNull(N n, N n2);

    E edgeConnectingOrNull(EndpointPair<N> endpointPair);

    ElementOrder<E> edgeOrder();

    Set<E> edges();

    Set<E> edgesConnecting(N n, N n2);

    Set<E> edgesConnecting(EndpointPair<N> endpointPair);

    boolean equals(Object obj);

    boolean hasEdgeConnecting(N n, N n2);

    boolean hasEdgeConnecting(EndpointPair<N> endpointPair);

    int hashCode();

    int inDegree(N n);

    Set<E> inEdges(N n);

    Set<E> incidentEdges(N n);

    EndpointPair<N> incidentNodes(E e);

    boolean isDirected();

    ElementOrder<N> nodeOrder();

    Set<N> nodes();

    int outDegree(N n);

    Set<E> outEdges(N n);

    Set<N> predecessors(N n);

    Set<N> successors(N n);
}
