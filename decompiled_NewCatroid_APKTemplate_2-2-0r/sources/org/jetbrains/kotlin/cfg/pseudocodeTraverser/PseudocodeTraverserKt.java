package org.jetbrains.kotlin.cfg.pseudocodeTraverser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.ControlFlowInfo;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.InlinedLocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineEnterInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineSinkInstruction;

/* JADX INFO: compiled from: PseudocodeTraverser.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000l\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\n\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0002\u001a>\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00052\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f\u001at\u0010\u0011\u001a\u00020\u0012\"\u0014\b\u0000\u0010\u0013*\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00142\u0006\u0010\u0003\u001a\u00020\u00022\u000e\u0010\u0015\u001a\n\u0012\u0004\u0012\u0002H\u0013\u0018\u00010\u00162\u000e\u0010\u0017\u001a\n\u0012\u0004\u0012\u0002H\u0013\u0018\u00010\u00162\u0018\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u00160\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t0\u0019H\u0002\u001a\u008d\u0001\u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u00160\u001c\"\u0014\b\u0000\u0010\u0013*\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0014*\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u00052$\u0010\u001e\u001a \u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u00160\u001f2\u001e\u0010 \u001a\u001a\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u00130!2\u0006\u0010\"\u001a\u0002H\u0013¢\u0006\u0002\u0010#\u001a´\u0001\u0010$\u001a\u00020\u0012\"\u0014\b\u0000\u0010\u0013*\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0014*\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u00052\u0018\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u00160\u00192$\u0010\u001e\u001a \u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u00160\u001f2\u001e\u0010 \u001a\u001a\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u00130!2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t0\u00192\u0006\u0010%\u001a\u00020\tH\u0002\u001a\u0018\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00020'*\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0012\u0010(\u001a\u00020\u0002*\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0018\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0018\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0012\u0010+\u001a\u00020\u0002*\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0012\u0010,\u001a\u00020\t*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005\u001a&\u0010-\u001a\u00020\u0012*\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00120\u000f\u001aR\u0010-\u001a\u00020\u0012\"\u0004\b\u0000\u0010/*\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u00052\u0018\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H/0\u00160\u001c2\u001e\u0010.\u001a\u001a\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H/\u0012\u0004\u0012\u0002H/\u0012\u0004\u0012\u00020\u00120!\u001a\u001e\u00100\u001a\u00020\u0012*\u00020\u001d2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00120\u000f¨\u00061"}, d2 = {"getPreviousIncludingSubGraphInstructions", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "instruction", "traversalOrder", "Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/TraversalOrder;", "startInstruction", "previousSubGraphInstructions", "traverseFollowingInstructions", "", "rootInstruction", "visited", "", "order", "handler", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/TraverseInstructionResult;", "updateEdgeDataForInstruction", "", "I", "Lorg/jetbrains/kotlin/cfg/ControlFlowInfo;", "previousValue", "Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/Edges;", "newValue", "edgesMap", "", "changed", "collectData", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "mergeEdges", "Lkotlin/Function2;", "updateEdge", "Lkotlin/Function3;", "initialInfo", "(Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/TraversalOrder;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;Lorg/jetbrains/kotlin/cfg/ControlFlowInfo;)Ljava/util/Map;", "collectDataFromSubgraph", "isLocal", "getInstructions", "", "getLastInstruction", "getNextInstructions", "getPreviousInstructions", "getStartInstruction", "isStartInstruction", "traverse", "analyzeInstruction", "D", "traverseIncludingDeadCode", "cfg"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PseudocodeTraverserKt {

    /* JADX INFO: compiled from: PseudocodeTraverser.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TraverseInstructionResult.values().length];
            try {
                iArr[TraverseInstructionResult.CONTINUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TraverseInstructionResult.SKIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TraverseInstructionResult.HALT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final void traverse(Pseudocode pseudocode, TraversalOrder traversalOrder, Function1<? super Instruction, Unit> analyzeInstruction) {
        Intrinsics.checkNotNullParameter(pseudocode, "<this>");
        Intrinsics.checkNotNullParameter(traversalOrder, "traversalOrder");
        Intrinsics.checkNotNullParameter(analyzeInstruction, "analyzeInstruction");
        for (Instruction instruction : getInstructions(pseudocode, traversalOrder)) {
            if (instruction instanceof LocalFunctionDeclarationInstruction) {
                traverse(((LocalFunctionDeclarationInstruction) instruction).getBody(), traversalOrder, analyzeInstruction);
            }
            analyzeInstruction.invoke(instruction);
        }
    }

    public static final <D> void traverse(Pseudocode pseudocode, TraversalOrder traversalOrder, Map<Instruction, ? extends Edges<? extends D>> edgesMap, Function3<? super Instruction, ? super D, ? super D, Unit> analyzeInstruction) {
        Intrinsics.checkNotNullParameter(pseudocode, "<this>");
        Intrinsics.checkNotNullParameter(traversalOrder, "traversalOrder");
        Intrinsics.checkNotNullParameter(edgesMap, "edgesMap");
        Intrinsics.checkNotNullParameter(analyzeInstruction, "analyzeInstruction");
        for (Instruction instruction : getInstructions(pseudocode, traversalOrder)) {
            if (instruction instanceof LocalFunctionDeclarationInstruction) {
                traverse(((LocalFunctionDeclarationInstruction) instruction).getBody(), traversalOrder, edgesMap, analyzeInstruction);
            }
            Edges<? extends D> edges = edgesMap.get(instruction);
            if (edges != null) {
                analyzeInstruction.invoke(instruction, edges.getIncoming(), edges.getOutgoing());
            }
        }
    }

    public static final void traverseIncludingDeadCode(Pseudocode pseudocode, Function1<? super Instruction, Unit> analyzeInstruction) {
        Intrinsics.checkNotNullParameter(pseudocode, "<this>");
        Intrinsics.checkNotNullParameter(analyzeInstruction, "analyzeInstruction");
        for (Instruction instruction : pseudocode.getInstructionsIncludingDeadCode()) {
            if (instruction instanceof LocalFunctionDeclarationInstruction) {
                traverseIncludingDeadCode(((LocalFunctionDeclarationInstruction) instruction).getBody(), analyzeInstruction);
            }
            analyzeInstruction.invoke(instruction);
        }
    }

    public static final <I extends ControlFlowInfo<?, ?, ?>> Map<Instruction, Edges<I>> collectData(Pseudocode pseudocode, TraversalOrder traversalOrder, Function2<? super Instruction, ? super Collection<? extends I>, ? extends Edges<? extends I>> mergeEdges, Function3<? super Instruction, ? super Instruction, ? super I, ? extends I> updateEdge, I initialInfo) {
        Intrinsics.checkNotNullParameter(pseudocode, "<this>");
        Intrinsics.checkNotNullParameter(traversalOrder, "traversalOrder");
        Intrinsics.checkNotNullParameter(mergeEdges, "mergeEdges");
        Intrinsics.checkNotNullParameter(updateEdge, "updateEdge");
        Intrinsics.checkNotNullParameter(initialInfo, "initialInfo");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(getStartInstruction(pseudocode, traversalOrder), new Edges(initialInfo, initialInfo));
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        loop0: while (true) {
            List listEmptyList = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(listEmptyList, "emptyList(...)");
            collectDataFromSubgraph(pseudocode, traversalOrder, linkedHashMap, mergeEdges, updateEdge, listEmptyList, linkedHashMap2, false);
            if (!linkedHashMap2.isEmpty()) {
                Iterator it = linkedHashMap2.entrySet().iterator();
                while (it.hasNext()) {
                    if (((Boolean) ((Map.Entry) it.next()).getValue()).booleanValue()) {
                    }
                }
                break loop0;
            }
            break;
        }
        return linkedHashMap;
    }

    private static final <I extends ControlFlowInfo<?, ?, ?>> void collectDataFromSubgraph(Pseudocode pseudocode, TraversalOrder traversalOrder, Map<Instruction, Edges<I>> map, Function2<? super Instruction, ? super Collection<? extends I>, ? extends Edges<? extends I>> function2, Function3<? super Instruction, ? super Instruction, ? super I, ? extends I> function3, Collection<? extends Instruction> collection, Map<Instruction, Boolean> map2, boolean z) {
        Object lastInstruction;
        List<Instruction> instructions = getInstructions(pseudocode, traversalOrder);
        Instruction startInstruction = getStartInstruction(pseudocode, traversalOrder);
        for (Instruction instruction : instructions) {
            boolean zIsStartInstruction = isStartInstruction(instruction, traversalOrder);
            if (z || !zIsStartInstruction) {
                Collection<Instruction> previousIncludingSubGraphInstructions = getPreviousIncludingSubGraphInstructions(instruction, traversalOrder, startInstruction, collection);
                if (instruction instanceof LocalFunctionDeclarationInstruction) {
                    Pseudocode body = ((LocalFunctionDeclarationInstruction) instruction).getBody();
                    collectDataFromSubgraph(body, traversalOrder, map, function2, function3, previousIncludingSubGraphInstructions, map2, true);
                    if ((instruction instanceof InlinedLocalFunctionDeclarationInstruction) && traversalOrder == TraversalOrder.FORWARD) {
                        lastInstruction = body.getExitInstruction();
                    } else {
                        lastInstruction = getLastInstruction(body, traversalOrder);
                    }
                    Edges<I> edges = map.get(instruction);
                    Edges<I> edges2 = map.get(lastInstruction);
                    updateEdgeDataForInstruction(instruction, edges, edges2 != null ? new Edges(function3.invoke(lastInstruction, instruction, edges2.getIncoming()), function3.invoke(lastInstruction, instruction, edges2.getOutgoing())) : null, map, map2);
                } else {
                    Edges<I> edges3 = map.get(instruction);
                    if (edges3 != null) {
                        Collection<Instruction> collection2 = previousIncludingSubGraphInstructions;
                        if (!(collection2 instanceof Collection) || !collection2.isEmpty()) {
                            Iterator<T> it = collection2.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (!Intrinsics.areEqual((Object) map2.get((Instruction) it.next()), (Object) false)) {
                                    }
                                }
                            }
                        }
                        map2.put(instruction, false);
                    }
                    HashSet hashSet = new HashSet();
                    for (Instruction instruction2 : previousIncludingSubGraphInstructions) {
                        Edges<I> edges4 = map.get(instruction2);
                        if (edges4 != null) {
                            hashSet.add(function3.invoke(instruction2, instruction, edges4.getOutgoing()));
                        }
                    }
                    updateEdgeDataForInstruction(instruction, edges3, function2.invoke(instruction, hashSet), map, map2);
                }
            }
        }
    }

    private static final Collection<Instruction> getPreviousIncludingSubGraphInstructions(Instruction instruction, TraversalOrder traversalOrder, Instruction instruction2, Collection<? extends Instruction> collection) {
        Collection<Instruction> previousInstructions = getPreviousInstructions(instruction, traversalOrder);
        if (!Intrinsics.areEqual(instruction, instruction2) || collection.isEmpty()) {
            return previousInstructions;
        }
        ArrayList arrayList = new ArrayList(previousInstructions);
        arrayList.addAll(collection);
        return arrayList;
    }

    private static final <I extends ControlFlowInfo<?, ?, ?>> void updateEdgeDataForInstruction(Instruction instruction, Edges<? extends I> edges, Edges<? extends I> edges2, Map<Instruction, Edges<I>> map, Map<Instruction, Boolean> map2) {
        if (!Intrinsics.areEqual(edges, edges2) && edges2 != null) {
            map2.put(instruction, true);
            map.put(instruction, edges2);
        } else {
            map2.put(instruction, false);
        }
    }

    public static /* synthetic */ boolean traverseFollowingInstructions$default(Instruction instruction, Set set, TraversalOrder traversalOrder, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            set = new HashSet();
        }
        if ((i & 4) != 0) {
            traversalOrder = TraversalOrder.FORWARD;
        }
        return traverseFollowingInstructions(instruction, set, traversalOrder, function1);
    }

    public static final boolean traverseFollowingInstructions(Instruction rootInstruction, Set<Instruction> visited, TraversalOrder order, Function1<? super Instruction, ? extends TraverseInstructionResult> function1) {
        TraverseInstructionResult traverseInstructionResultInvoke;
        Intrinsics.checkNotNullParameter(rootInstruction, "rootInstruction");
        Intrinsics.checkNotNullParameter(visited, "visited");
        Intrinsics.checkNotNullParameter(order, "order");
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(rootInstruction);
        while (!arrayDeque.isEmpty()) {
            Instruction instruction = (Instruction) arrayDeque.pop();
            Intrinsics.checkNotNull(instruction);
            if (visited.add(instruction)) {
                if (function1 == null || (traverseInstructionResultInvoke = function1.invoke(instruction)) == null) {
                    traverseInstructionResultInvoke = TraverseInstructionResult.CONTINUE;
                }
                int i = WhenMappings.$EnumSwitchMapping$0[traverseInstructionResultInvoke.ordinal()];
                if (i == 1) {
                    Iterator<T> it = getNextInstructions(instruction, order).iterator();
                    while (it.hasNext()) {
                        arrayDeque.push((Instruction) it.next());
                    }
                } else if (i == 3) {
                    return false;
                }
            }
        }
        return true;
    }

    public static final Instruction getStartInstruction(Pseudocode pseudocode, TraversalOrder traversalOrder) {
        Intrinsics.checkNotNullParameter(pseudocode, "<this>");
        Intrinsics.checkNotNullParameter(traversalOrder, "traversalOrder");
        return traversalOrder == TraversalOrder.FORWARD ? pseudocode.getEnterInstruction() : pseudocode.getSinkInstruction();
    }

    public static final Instruction getLastInstruction(Pseudocode pseudocode, TraversalOrder traversalOrder) {
        Intrinsics.checkNotNullParameter(pseudocode, "<this>");
        Intrinsics.checkNotNullParameter(traversalOrder, "traversalOrder");
        return traversalOrder == TraversalOrder.FORWARD ? pseudocode.getSinkInstruction() : pseudocode.getEnterInstruction();
    }

    public static final List<Instruction> getInstructions(Pseudocode pseudocode, TraversalOrder traversalOrder) {
        Intrinsics.checkNotNullParameter(pseudocode, "<this>");
        Intrinsics.checkNotNullParameter(traversalOrder, "traversalOrder");
        return traversalOrder == TraversalOrder.FORWARD ? pseudocode.getInstructions() : pseudocode.getReversedInstructions();
    }

    public static final Collection<Instruction> getNextInstructions(Instruction instruction, TraversalOrder traversalOrder) {
        Intrinsics.checkNotNullParameter(instruction, "<this>");
        Intrinsics.checkNotNullParameter(traversalOrder, "traversalOrder");
        return traversalOrder == TraversalOrder.FORWARD ? instruction.getNextInstructions() : instruction.getPreviousInstructions();
    }

    public static final Collection<Instruction> getPreviousInstructions(Instruction instruction, TraversalOrder traversalOrder) {
        Intrinsics.checkNotNullParameter(instruction, "<this>");
        Intrinsics.checkNotNullParameter(traversalOrder, "traversalOrder");
        return traversalOrder == TraversalOrder.FORWARD ? instruction.getPreviousInstructions() : instruction.getNextInstructions();
    }

    public static final boolean isStartInstruction(Instruction instruction, TraversalOrder traversalOrder) {
        Intrinsics.checkNotNullParameter(instruction, "<this>");
        Intrinsics.checkNotNullParameter(traversalOrder, "traversalOrder");
        return traversalOrder == TraversalOrder.FORWARD ? instruction instanceof SubroutineEnterInstruction : instruction instanceof SubroutineSinkInstruction;
    }
}
