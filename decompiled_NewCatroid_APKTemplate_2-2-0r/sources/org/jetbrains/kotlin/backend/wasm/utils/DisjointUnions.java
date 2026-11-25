package org.jetbrains.kotlin.backend.wasm.utils;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: compiled from: DisjointUnions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001\u001fB\u0005¢\u0006\u0002\u0010\u0003J'\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u00002\u0010\u0010\f\u001a\f0\bR\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0002¢\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\u00020\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010J\u0006\u0010\u0011\u001a\u00020\nJ\u0016\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010\u0014J!\u0010\u0015\u001a\u000e\u0018\u00010\bR\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0016\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0017J.\u0010\u0015\u001a\f0\bR\b\u0012\u0004\u0012\u00028\u00000\u00002\u0010\u0010\u0016\u001a\f0\bR\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0002J\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u00102\u0006\u0010\u0013\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010\u001bJ6\u0010\u001c\u001a\f0\bR\b\u0012\u0004\u0012\u00028\u00000\u00002\u0010\u0010\u001d\u001a\f0\bR\b\u0012\u0004\u0012\u00028\u00000\u00002\u0010\u0010\u001e\u001a\f0\bR\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0006\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u000e\u0012\f0\bR\b\u0012\u0004\u0012\u00028\u00000\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "dirty", "", "leafParents", "", "Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions$Node;", "addToRoot", "", "leaf", "root", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions$Node;)V", "addUnion", PathTree.NAME_elements, "", "compress", "contains", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/lang/Object;)Z", "findRoot", "node", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions$Node;", "pathWeight", "", "get", "(Ljava/lang/Object;)Ljava/util/List;", "mergeRoots", "root1", "root2", "Node", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DisjointUnions<T> {
    private boolean dirty;
    private final Map<T, DisjointUnions<T>.Node> leafParents = new LinkedHashMap();

    /* JADX INFO: compiled from: DisjointUnions.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR&\u0010\t\u001a\u000e\u0018\u00010\u0000R\b\u0012\u0004\u0012\u00028\u00000\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions$Node;", "", "rank", "", "leafs", "", "(Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions;ILjava/util/List;)V", "getLeafs", "()Ljava/util/List;", "parent", "Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions;", "getParent", "()Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions$Node;", "setParent", "(Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions$Node;)V", "getRank", "()I", "setRank", "(I)V", "toString", "", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Node {
        private final List<T> leafs;
        private DisjointUnions<T>.Node parent;
        private int rank;
        final /* synthetic */ DisjointUnions<T> this$0;

        public Node(DisjointUnions disjointUnions, int i, List<T> leafs) {
            Intrinsics.checkNotNullParameter(leafs, "leafs");
            this.this$0 = disjointUnions;
            this.rank = i;
            this.leafs = leafs;
        }

        public /* synthetic */ Node(DisjointUnions disjointUnions, int i, ArrayList arrayList, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(disjointUnions, (i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? new ArrayList() : arrayList);
        }

        public final List<T> getLeafs() {
            return this.leafs;
        }

        public final int getRank() {
            return this.rank;
        }

        public final void setRank(int i) {
            this.rank = i;
        }

        public final DisjointUnions<T>.Node getParent() {
            return this.parent;
        }

        public final void setParent(DisjointUnions<T>.Node node) {
            this.parent = node;
        }

        public String toString() {
            return (this.parent == null ? "ROOT " : "") + "Node with " + this.leafs.size() + " leafs and " + this.rank + " rank";
        }
    }

    private final DisjointUnions<T>.Node findRoot(T node) {
        DisjointUnions<T>.Node node2 = this.leafParents.get(node);
        if (node2 != null) {
            return findRoot$default(this, node2, 0, 2, null);
        }
        return null;
    }

    static /* synthetic */ Node findRoot$default(DisjointUnions disjointUnions, Node node, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return disjointUnions.findRoot(node, i);
    }

    private final DisjointUnions<T>.Node findRoot(DisjointUnions<T>.Node node, int pathWeight) {
        DisjointUnions<T>.Node parent = node.getParent();
        if (parent == null) {
            return node;
        }
        int size = pathWeight + node.getLeafs().size();
        DisjointUnions<T>.Node nodeFindRoot = findRoot(parent, size);
        if (!Intrinsics.areEqual(nodeFindRoot, node)) {
            List<T> leafs = node.getLeafs();
            node.setRank(node.getRank() - size);
            if (node.getRank() < 0) {
                throw new IllegalStateException("Check failed.".toString());
            }
            nodeFindRoot.getLeafs().addAll(leafs);
            leafs.clear();
            node.setParent(nodeFindRoot);
        }
        return nodeFindRoot;
    }

    private final void addToRoot(T leaf, DisjointUnions<T>.Node root) {
        this.leafParents.put(leaf, root);
        root.setRank(root.getRank() + 1);
        root.getLeafs().add(leaf);
    }

    private final DisjointUnions<T>.Node mergeRoots(DisjointUnions<T>.Node root1, DisjointUnions<T>.Node root2) {
        if (Intrinsics.areEqual(root1, root2)) {
            return root1;
        }
        if (root1.getParent() != null || root2.getParent() != null) {
            throw new IllegalArgumentException("Merge is possible only for root nodes".toString());
        }
        if (Intrinsics.areEqual(root2.getParent(), root1)) {
            return root1;
        }
        if (Intrinsics.areEqual(root1.getParent(), root2)) {
            return root2;
        }
        if (root1.getRank() > root2.getRank()) {
            root2 = root1;
            root1 = root2;
        }
        root1.setParent(root2);
        List<T> leafs = root1.getLeafs();
        root2.setRank(root2.getRank() + leafs.size());
        root1.setRank(root1.getRank() - leafs.size());
        if (root1.getRank() < 0) {
            throw new IllegalStateException("Check failed.".toString());
        }
        root2.getLeafs().addAll(leafs);
        leafs.clear();
        return root2;
    }

    public final void addUnion(List<? extends T> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        this.dirty = true;
        DisjointUnions<T>.Node node = null;
        for (T t : elements) {
            DisjointUnions<T>.Node node2 = this.leafParents.get(t);
            if (node2 == null) {
                if (node == null || (node = findRoot$default(this, node, 0, 2, null)) == null) {
                    node = new Node(this, 0, null, 3, null);
                }
                addToRoot(t, node);
            } else {
                DisjointUnions<T>.Node nodeFindRoot$default = findRoot$default(this, node2, 0, 2, null);
                node = node != null ? mergeRoots(node, nodeFindRoot$default) : nodeFindRoot$default;
            }
        }
    }

    public final void compress() {
        if (this.dirty) {
            Iterator<T> it = this.leafParents.keySet().iterator();
            while (it.hasNext()) {
                findRoot(it.next());
            }
            this.dirty = false;
        }
    }

    public final boolean contains(T element) {
        return this.leafParents.containsKey(element);
    }

    public final List<T> get(T element) {
        if (this.dirty) {
            throw new IllegalArgumentException("Call compress before getting union".toString());
        }
        DisjointUnions<T>.Node nodeFindRoot = findRoot(element);
        if (nodeFindRoot == null) {
            throw new IllegalArgumentException("Element not contains in any union".toString());
        }
        if (nodeFindRoot.getRank() != nodeFindRoot.getLeafs().size()) {
            throw new IllegalStateException("Invalid tree state after compress".toString());
        }
        return nodeFindRoot.getLeafs();
    }
}
