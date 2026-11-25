package org.jetbrains.kotlin.com.intellij.util.graph;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public final class CachingSemiGraph<Node> implements InboundSemiGraph<Node> {
    private final Map<Node, List<Node>> myIn;
    private final Set<Node> myNodes;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/graph/CachingSemiGraph";
        } else {
            objArr[0] = "original";
        }
        if (i == 2) {
            objArr[1] = "getNodes";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/graph/CachingSemiGraph";
        } else {
            objArr[1] = "getIn";
        }
        if (i == 1) {
            objArr[2] = "<init>";
        } else if (i != 2 && i != 3) {
            objArr[2] = ResponseCacheMiddleware.CACHE;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static <T> InboundSemiGraph<T> cache(InboundSemiGraph<T> inboundSemiGraph) {
        if (inboundSemiGraph == null) {
            $$$reportNull$$$0(0);
        }
        return new CachingSemiGraph(inboundSemiGraph);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private CachingSemiGraph(InboundSemiGraph<Node> inboundSemiGraph) {
        if (inboundSemiGraph == 0) {
            $$$reportNull$$$0(1);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(inboundSemiGraph.getNodes());
        this.myNodes = linkedHashSet;
        this.myIn = new HashMap();
        for (Object obj : linkedHashSet) {
            Iterator in = inboundSemiGraph.getIn(obj);
            if (in.hasNext()) {
                ArrayList arrayList = new ArrayList();
                while (in.hasNext()) {
                    arrayList.add(in.next());
                }
                this.myIn.put((Node) obj, arrayList);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.graph.InboundSemiGraph
    public Collection<Node> getNodes() {
        Set<Node> set = this.myNodes;
        if (set == null) {
            $$$reportNull$$$0(2);
        }
        return set;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.graph.InboundSemiGraph
    public Iterator<Node> getIn(Node node) {
        Iterator<Node> itEmptyIterator;
        List<Node> list = this.myIn.get(node);
        if (list != null) {
            itEmptyIterator = list.iterator();
        } else {
            itEmptyIterator = Collections.emptyIterator();
        }
        if (itEmptyIterator == null) {
            $$$reportNull$$$0(3);
        }
        return itEmptyIterator;
    }
}
