package com.android.tools.r8.shaking;

import com.android.tools.r8.experimental.graphinfo.GraphConsumer;
import com.android.tools.r8.experimental.graphinfo.GraphNode;
import com.android.tools.r8.internal.C1349Sj;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.t, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3798t implements GraphConsumer {
    private final GraphConsumer a;
    private final HashMap b = new HashMap();

    public C3798t(GraphConsumer graphConsumer) {
        this.a = graphConsumer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map b(GraphNode graphNode) {
        return new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set c(GraphNode graphNode) {
        return new HashSet();
    }

    public Set<GraphNode> a() {
        return this.b.keySet();
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphConsumer
    public final void acceptEdge(GraphNode graphNode, GraphNode graphNode2, C1349Sj c1349Sj) {
        ((Set) ((Map) this.b.computeIfAbsent(graphNode2, new Function() { // from class: com.android.tools.r8.shaking.t$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3798t.b((GraphNode) obj);
            }
        })).computeIfAbsent(graphNode, new Function() { // from class: com.android.tools.r8.shaking.t$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3798t.c((GraphNode) obj);
            }
        })).add(c1349Sj);
        GraphConsumer graphConsumer = this.a;
        if (graphConsumer != null) {
            graphConsumer.acceptEdge(graphNode, graphNode2, c1349Sj);
        }
    }

    public Map<GraphNode, Set<C1349Sj>> a(GraphNode graphNode) {
        return (Map) this.b.get(graphNode);
    }
}
