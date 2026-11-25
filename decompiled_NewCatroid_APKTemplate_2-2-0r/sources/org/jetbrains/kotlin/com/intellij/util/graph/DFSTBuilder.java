package org.jetbrains.kotlin.com.intellij.util.graph;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArrayList;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntStack;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class DFSTBuilder<Node> {
    private final Node[] allNodes;
    private final DFSTBuilderAwareGraph<Node> graphAdapter;
    private Map.Entry<Node, Node> myBackEdge;
    private final Node[] myInvN;
    private final Node[] myInvT;
    private Comparator<Node> myNComparator;
    private final ToIntFunction<Node> myNodeToNNumber;
    private final ToIntFunction<Node> myNodeToTNumber;
    private final IntList mySCCs;
    private Comparator<Node> myTComparator;

    public interface DFSTBuilderAwareGraph<Node> {
        int[] buildOuts(ToIntFunction<? super Node> toIntFunction, Node node);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/graph/DFSTBuilder";
                break;
            default:
                objArr[0] = "graph";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
                objArr[1] = "comparator";
                break;
            case 8:
                objArr[1] = "getNodeByNNumber";
                break;
            case 9:
                objArr[1] = "getNodeByTNumber";
                break;
            case 10:
                objArr[1] = "getSCCs";
                break;
            case 11:
                objArr[1] = "getComponents";
                break;
            case 12:
                objArr[1] = "getSortedNodes";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/graph/DFSTBuilder";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DFSTBuilder(Graph<Node> graph) {
        this((Graph<Object>) graph, (Object) null);
        if (graph == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DFSTBuilder(Graph<Node> graph, Node node) {
        this((OutboundSemiGraph) graph, (Object) node);
        if (graph == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DFSTBuilder(OutboundSemiGraph<Node> outboundSemiGraph, Node node) {
        this(outboundSemiGraph, node, false);
        if (outboundSemiGraph == null) {
            $$$reportNull$$$0(3);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DFSTBuilder(final OutboundSemiGraph<Node> outboundSemiGraph, Node node, boolean z) {
        this(outboundSemiGraph.getNodes().toArray(), node, z, new DFSTBuilderAwareGraph<Node>() { // from class: org.jetbrains.kotlin.com.intellij.util.graph.DFSTBuilder.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "nodeIndex";
                } else {
                    objArr[0] = "node";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/graph/DFSTBuilder$1";
                objArr[2] = "buildOuts";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.graph.DFSTBuilder.DFSTBuilderAwareGraph
            public int[] buildOuts(ToIntFunction<? super Node> toIntFunction, Node node2) {
                if (toIntFunction == null) {
                    $$$reportNull$$$0(0);
                }
                if (node2 == null) {
                    $$$reportNull$$$0(1);
                }
                IntArrayList intArrayList = new IntArrayList();
                Iterator<Node> out = outboundSemiGraph.getOut(node2);
                while (out.hasNext()) {
                    intArrayList.add(toIntFunction.applyAsInt(out.next()));
                }
                return intArrayList.isEmpty() ? ArrayUtilRt.EMPTY_INT_ARRAY : intArrayList.toIntArray();
            }
        });
        if (outboundSemiGraph == null) {
            $$$reportNull$$$0(4);
        }
    }

    public DFSTBuilder(Node[] nodeArr, Node node, boolean z, DFSTBuilderAwareGraph<Node> dFSTBuilderAwareGraph) {
        this.mySCCs = new IntArrayList();
        this.allNodes = nodeArr;
        if (node != null) {
            int iIndexOfIdentity = z ? ArrayUtil.indexOfIdentity(nodeArr, node) : ArrayUtil.indexOf(nodeArr, node);
            if (iIndexOfIdentity != -1) {
                ArrayUtil.swap(nodeArr, 0, iIndexOfIdentity);
            }
        }
        int length = nodeArr.length;
        this.myInvN = (Node[]) new Object[length];
        this.myInvT = (Node[]) new Object[length];
        this.graphAdapter = dFSTBuilderAwareGraph;
        if (z) {
            Reference2IntOpenHashMap reference2IntOpenHashMap = new Reference2IntOpenHashMap(length * 2, 0.5f);
            Reference2IntOpenHashMap reference2IntOpenHashMap2 = new Reference2IntOpenHashMap();
            this.myNodeToNNumber = reference2IntOpenHashMap;
            this.myNodeToTNumber = reference2IntOpenHashMap2;
            Objects.requireNonNull(reference2IntOpenHashMap2);
            DFSTBuilder$$ExternalSyntheticLambda0 dFSTBuilder$$ExternalSyntheticLambda0 = new DFSTBuilder$$ExternalSyntheticLambda0(reference2IntOpenHashMap2);
            Objects.requireNonNull(reference2IntOpenHashMap);
            new Tarjan(dFSTBuilder$$ExternalSyntheticLambda0, new DFSTBuilder$$ExternalSyntheticLambda0(reference2IntOpenHashMap), nodeArr, true);
            return;
        }
        final Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap(length * 2, 0.5f);
        final Object2IntOpenHashMap object2IntOpenHashMap2 = new Object2IntOpenHashMap();
        this.myNodeToNNumber = object2IntOpenHashMap;
        this.myNodeToTNumber = object2IntOpenHashMap2;
        Objects.requireNonNull(object2IntOpenHashMap2);
        ObjIntConsumer objIntConsumer = new ObjIntConsumer() { // from class: org.jetbrains.kotlin.com.intellij.util.graph.DFSTBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.ObjIntConsumer
            public final void accept(Object obj, int i) {
                object2IntOpenHashMap2.put(obj, i);
            }
        };
        Objects.requireNonNull(object2IntOpenHashMap);
        new Tarjan(objIntConsumer, new ObjIntConsumer() { // from class: org.jetbrains.kotlin.com.intellij.util.graph.DFSTBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.ObjIntConsumer
            public final void accept(Object obj, int i) {
                object2IntOpenHashMap.put(obj, i);
            }
        }, nodeArr, false);
    }

    private static final class TarjanFrame<Node> {
        private final Node[] allNodes;
        int nextUnexploredIndex;
        private final int nodeI;
        private final int[] out;

        TarjanFrame(int i, Node[] nodeArr, int[] iArr) {
            this.nodeI = i;
            this.allNodes = nodeArr;
            this.out = iArr;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.allNodes[this.nodeI]).append(" -> [");
            for (int i : this.out) {
                sb.append(this.allNodes[i]).append(", ");
            }
            return sb.append(']').toString();
        }
    }

    private final class Tarjan {
        private int dfsIndex;
        private final Deque<TarjanFrame<Node>> frames;
        private ToIntFunction<? super Node> getNodeIndex;
        private final int[] index;
        private final boolean[] isOnStack;
        private final int[] lowLink;
        private final IntStack nodesOnStack;
        private int sccsSizeCombined;
        private final IntList topo;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "getNodeIndex";
            } else if (i != 2) {
                objArr[0] = "putNodeIndex";
            } else {
                objArr[0] = "sccs";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/graph/DFSTBuilder$Tarjan";
            if (i != 2) {
                objArr[2] = JsonPOJOBuilder.DEFAULT_BUILD_METHOD;
            } else {
                objArr[2] = "strongConnect";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private Tarjan(ObjIntConsumer<Node> objIntConsumer, ObjIntConsumer<Node> objIntConsumer2, Node[] nodeArr, boolean z) {
            this.lowLink = new int[DFSTBuilder.this.myInvN.length];
            int[] iArr = new int[DFSTBuilder.this.myInvN.length];
            this.index = iArr;
            this.nodesOnStack = new IntArrayList();
            this.isOnStack = new boolean[iArr.length];
            this.frames = new ArrayDeque();
            this.topo = new IntArrayList(iArr.length);
            if (z) {
                Reference2IntOpenHashMap reference2IntOpenHashMap = new Reference2IntOpenHashMap(nodeArr.length);
                Objects.requireNonNull(reference2IntOpenHashMap);
                build(objIntConsumer, objIntConsumer2, nodeArr, new DFSTBuilder$$ExternalSyntheticLambda0(reference2IntOpenHashMap), reference2IntOpenHashMap);
            } else {
                final Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap(nodeArr.length);
                Objects.requireNonNull(object2IntOpenHashMap);
                build(objIntConsumer, objIntConsumer2, nodeArr, new ObjIntConsumer() { // from class: org.jetbrains.kotlin.com.intellij.util.graph.DFSTBuilder$Tarjan$$ExternalSyntheticLambda0
                    @Override // java.util.function.ObjIntConsumer
                    public final void accept(Object obj, int i) {
                        object2IntOpenHashMap.put(obj, i);
                    }
                }, object2IntOpenHashMap);
            }
        }

        private void build(ObjIntConsumer<Node> objIntConsumer, ObjIntConsumer<Node> objIntConsumer2, Node[] nodeArr, ObjIntConsumer<Node> objIntConsumer3, ToIntFunction<? super Node> toIntFunction) {
            int i = 0;
            if (objIntConsumer3 == null) {
                $$$reportNull$$$0(0);
            }
            if (toIntFunction == null) {
                $$$reportNull$$$0(1);
            }
            this.getNodeIndex = toIntFunction;
            Arrays.fill(this.index, -1);
            for (int i2 = 0; i2 < nodeArr.length; i2++) {
                objIntConsumer3.accept(nodeArr[i2], i2);
            }
            int i3 = 0;
            while (true) {
                int[] iArr = this.index;
                if (i3 >= iArr.length) {
                    break;
                }
                if (iArr[i3] == -1) {
                    this.frames.addLast(new TarjanFrame<>(i3, nodeArr, DFSTBuilder.this.graphAdapter.buildOuts(toIntFunction, nodeArr[i3])));
                    ArrayList arrayList = new ArrayList();
                    strongConnect(arrayList, nodeArr);
                    for (List<Node> list : arrayList) {
                        int size = list.size();
                        DFSTBuilder.this.mySCCs.add(size);
                        int length = (this.index.length - this.sccsSizeCombined) - size;
                        int iIndexOf = list.indexOf(nodeArr[i3]);
                        if (iIndexOf != -1) {
                            Node node = list.get(iIndexOf);
                            list.set(iIndexOf, list.get(0));
                            list.set(0, node);
                        }
                        for (int i4 = 0; i4 < list.size(); i4++) {
                            Node node2 = list.get(i4);
                            int i5 = length + i4;
                            DFSTBuilder.this.myInvT[i5] = node2;
                            objIntConsumer.accept(node2, i5);
                        }
                        this.sccsSizeCombined += size;
                    }
                }
                i3++;
            }
            for (int i6 = 0; i6 < this.topo.size(); i6++) {
                Node node3 = nodeArr[this.topo.getInt(i6)];
                objIntConsumer2.accept(node3, (this.index.length - 1) - i6);
                DFSTBuilder.this.myInvN[(this.index.length - 1) - i6] = node3;
            }
            for (int size2 = DFSTBuilder.this.mySCCs.size() - 1; i < size2; size2--) {
                int i7 = DFSTBuilder.this.mySCCs.getInt(i);
                DFSTBuilder.this.mySCCs.set(i, DFSTBuilder.this.mySCCs.getInt(size2));
                DFSTBuilder.this.mySCCs.set(size2, i7);
                i++;
            }
        }

        private void strongConnect(List<? super List<Node>> list, Node[] nodeArr) {
            int iPopInt;
            if (list == null) {
                $$$reportNull$$$0(2);
            }
            int i = -1;
            while (!this.frames.isEmpty()) {
                TarjanFrame<Node> tarjanFramePeekLast = this.frames.peekLast();
                int i2 = ((TarjanFrame) tarjanFramePeekLast).nodeI;
                int[] iArr = this.index;
                if (iArr[i2] == -1) {
                    int i3 = this.dfsIndex;
                    iArr[i2] = i3;
                    this.lowLink[i2] = i3;
                    this.dfsIndex = i3 + 1;
                    this.nodesOnStack.push(i2);
                    this.isOnStack[i2] = true;
                }
                if (ArrayUtil.indexOf(((TarjanFrame) tarjanFramePeekLast).out, i) != -1) {
                    int[] iArr2 = this.lowLink;
                    iArr2[i2] = Math.min(iArr2[i2], iArr2[i]);
                }
                while (true) {
                    if (tarjanFramePeekLast.nextUnexploredIndex < ((TarjanFrame) tarjanFramePeekLast).out.length) {
                        int[] iArr3 = ((TarjanFrame) tarjanFramePeekLast).out;
                        int i4 = tarjanFramePeekLast.nextUnexploredIndex;
                        tarjanFramePeekLast.nextUnexploredIndex = i4 + 1;
                        int i5 = iArr3[i4];
                        int i6 = this.index[i5];
                        if (i6 == -1) {
                            this.frames.addLast(new TarjanFrame<>(i5, nodeArr, DFSTBuilder.this.graphAdapter.buildOuts(this.getNodeIndex, nodeArr[i5])));
                            break;
                        } else if (this.isOnStack[i5]) {
                            int[] iArr4 = this.lowLink;
                            iArr4[i2] = Math.min(iArr4[i2], i6);
                            if (DFSTBuilder.this.myBackEdge == null) {
                                DFSTBuilder.this.myBackEdge = new AbstractMap.SimpleImmutableEntry(nodeArr[i5], nodeArr[i2]);
                            }
                        }
                    } else {
                        this.frames.removeLast();
                        this.topo.add(i2);
                        if (this.lowLink[i2] != this.index[i2]) {
                            break;
                        }
                        ArrayList arrayList = new ArrayList();
                        do {
                            iPopInt = this.nodesOnStack.popInt();
                            Node node = nodeArr[iPopInt];
                            this.isOnStack[iPopInt] = false;
                            arrayList.add(node);
                        } while (iPopInt != i2);
                        list.add(arrayList);
                        break;
                    }
                }
                i = i2;
            }
        }
    }

    public Comparator<Node> comparator() {
        Comparator<Node> comparator = comparator(isAcyclic());
        if (comparator == null) {
            $$$reportNull$$$0(5);
        }
        return comparator;
    }

    public Comparator<Node> comparator(boolean z) {
        if (z) {
            if (this.myNComparator == null) {
                this.myNComparator = Comparator.comparingInt(this.myNodeToNNumber);
            }
            Comparator<Node> comparator = this.myNComparator;
            if (comparator == null) {
                $$$reportNull$$$0(6);
            }
            return comparator;
        }
        if (this.myTComparator == null) {
            this.myTComparator = Comparator.comparingInt(this.myNodeToTNumber);
        }
        Comparator<Node> comparator2 = this.myTComparator;
        if (comparator2 == null) {
            $$$reportNull$$$0(7);
        }
        return comparator2;
    }

    public Map.Entry<Node, Node> getCircularDependency() {
        return this.myBackEdge;
    }

    public boolean isAcyclic() {
        return getCircularDependency() == null;
    }
}
