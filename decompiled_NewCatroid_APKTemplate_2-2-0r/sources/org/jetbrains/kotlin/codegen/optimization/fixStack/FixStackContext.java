package org.jetbrains.kotlin.codegen.optimization.fixStack;

import com.reandroid.dex.sections.Marker;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsn;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsnsKt;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: FixStackContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010'\u001a\u00020\fJ\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0012H\u0002J\u0006\u0010+\u001a\u00020\fJ\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u0012H\u0002J\u0010\u0010/\u001a\u00020-2\u0006\u0010.\u001a\u00020\u0012H\u0002J\u0010\u00100\u001a\u00020-2\u0006\u0010.\u001a\u00020\u0012H\u0002R!\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001e\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR!\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u00120\u0011j\b\u0012\u0004\u0012\u00020\u0012`\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R!\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00120\u0011j\b\u0012\u0004\u0012\u00020\u0012`\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0011\u0010\u0018\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR-\u0010\u001b\u001a\u001e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u001cj\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0012`\u001d¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR9\u0010 \u001a*\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120!0\u001cj\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120!`\u001d¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001fR\u001d\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120$¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackContext;", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)V", "breakContinueGotoNodes", "Ljava/util/LinkedHashSet;", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "Lkotlin/collections/LinkedHashSet;", "getBreakContinueGotoNodes", "()Ljava/util/LinkedHashSet;", "<set-?>", "", "consistentInlineMarkers", "getConsistentInlineMarkers", "()Z", "fakeAlwaysFalseIfeqMarkers", "Ljava/util/ArrayList;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lkotlin/collections/ArrayList;", "getFakeAlwaysFalseIfeqMarkers", "()Ljava/util/ArrayList;", "fakeAlwaysTrueIfeqMarkers", "getFakeAlwaysTrueIfeqMarkers", "isThereAnyTryCatch", "getMethodNode", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "openingInlineMethodMarker", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getOpeningInlineMethodMarker", "()Ljava/util/HashMap;", "restoreStackMarkersForSaveMarker", "", "getRestoreStackMarkersForSaveMarker", "saveStackMarkerForRestoreMarker", "", "getSaveStackMarkerForRestoreMarker", "()Ljava/util/Map;", "hasAnyMarkers", "indexOf", "", "node", "isAnalysisRequired", "visitFakeAlwaysFalseIfeq", "", "insnNode", "visitFakeAlwaysTrueIfeq", "visitFixStackBeforeJump", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FixStackContext {
    private final LinkedHashSet<JumpInsnNode> breakContinueGotoNodes;
    private boolean consistentInlineMarkers;
    private final ArrayList<AbstractInsnNode> fakeAlwaysFalseIfeqMarkers;
    private final ArrayList<AbstractInsnNode> fakeAlwaysTrueIfeqMarkers;
    private final boolean isThereAnyTryCatch;
    private final MethodNode methodNode;
    private final HashMap<AbstractInsnNode, AbstractInsnNode> openingInlineMethodMarker;
    private final HashMap<AbstractInsnNode, List<AbstractInsnNode>> restoreStackMarkersForSaveMarker;
    private final Map<AbstractInsnNode, AbstractInsnNode> saveStackMarkerForRestoreMarker;

    public FixStackContext(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        this.methodNode = methodNode;
        this.breakContinueGotoNodes = new LinkedHashSet<>();
        this.fakeAlwaysTrueIfeqMarkers = new ArrayList<>();
        this.fakeAlwaysFalseIfeqMarkers = new ArrayList<>();
        Map<AbstractInsnNode, AbstractInsnNode> mapInsertTryCatchBlocksMarkers = AnalyzeTryCatchBlocksKt.insertTryCatchBlocksMarkers(methodNode);
        this.saveStackMarkerForRestoreMarker = mapInsertTryCatchBlocksMarkers;
        this.restoreStackMarkersForSaveMarker = new HashMap<>();
        this.openingInlineMethodMarker = new HashMap<>();
        this.consistentInlineMarkers = true;
        this.isThereAnyTryCatch = true ^ mapInsertTryCatchBlocksMarkers.isEmpty();
        for (Map.Entry<AbstractInsnNode, AbstractInsnNode> entry : mapInsertTryCatchBlocksMarkers.entrySet()) {
            AbstractInsnNode key = entry.getKey();
            AbstractInsnNode value = entry.getValue();
            HashMap<AbstractInsnNode, List<AbstractInsnNode>> map = this.restoreStackMarkersForSaveMarker;
            SmartList smartList = map.get(value);
            if (smartList == null) {
                smartList = new SmartList();
                map.put(value, smartList);
            }
            smartList.add(key);
        }
        Stack stack = new Stack();
        InsnList instructions = this.methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (AbstractInsnNode abstractInsnNode : new InsnSequence(instructions)) {
            PseudoInsn pseudoInsnOrNull = PseudoInsnsKt.parsePseudoInsnOrNull(abstractInsnNode);
            if (pseudoInsnOrNull == PseudoInsn.FIX_STACK_BEFORE_JUMP) {
                visitFixStackBeforeJump(abstractInsnNode);
            } else if (pseudoInsnOrNull == PseudoInsn.FAKE_ALWAYS_TRUE_IFEQ) {
                visitFakeAlwaysTrueIfeq(abstractInsnNode);
            } else if (pseudoInsnOrNull == PseudoInsn.FAKE_ALWAYS_FALSE_IFEQ) {
                visitFakeAlwaysFalseIfeq(abstractInsnNode);
            } else if (InlineCodegenUtilsKt.isBeforeInlineMarker(abstractInsnNode)) {
                stack.push(abstractInsnNode);
            } else if (InlineCodegenUtilsKt.isAfterInlineMarker(abstractInsnNode)) {
                stack.isEmpty();
                AbstractMap abstractMap = this.openingInlineMethodMarker;
                Object objPop = stack.pop();
                Intrinsics.checkNotNullExpressionValue(objPop, "pop(...)");
                abstractMap.put(abstractInsnNode, objPop);
            }
        }
        if (stack.isEmpty()) {
            return;
        }
        this.consistentInlineMarkers = false;
    }

    public final MethodNode getMethodNode() {
        return this.methodNode;
    }

    public final LinkedHashSet<JumpInsnNode> getBreakContinueGotoNodes() {
        return this.breakContinueGotoNodes;
    }

    public final ArrayList<AbstractInsnNode> getFakeAlwaysTrueIfeqMarkers() {
        return this.fakeAlwaysTrueIfeqMarkers;
    }

    public final ArrayList<AbstractInsnNode> getFakeAlwaysFalseIfeqMarkers() {
        return this.fakeAlwaysFalseIfeqMarkers;
    }

    /* JADX INFO: renamed from: isThereAnyTryCatch, reason: from getter */
    public final boolean getIsThereAnyTryCatch() {
        return this.isThereAnyTryCatch;
    }

    public final Map<AbstractInsnNode, AbstractInsnNode> getSaveStackMarkerForRestoreMarker() {
        return this.saveStackMarkerForRestoreMarker;
    }

    public final HashMap<AbstractInsnNode, List<AbstractInsnNode>> getRestoreStackMarkersForSaveMarker() {
        return this.restoreStackMarkersForSaveMarker;
    }

    public final HashMap<AbstractInsnNode, AbstractInsnNode> getOpeningInlineMethodMarker() {
        return this.openingInlineMethodMarker;
    }

    public final boolean getConsistentInlineMarkers() {
        return this.consistentInlineMarkers;
    }

    private final void visitFixStackBeforeJump(AbstractInsnNode insnNode) {
        AbstractInsnNode next = insnNode.getNext();
        next.getOpcode();
        LinkedHashSet<JumpInsnNode> linkedHashSet = this.breakContinueGotoNodes;
        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
        linkedHashSet.add((JumpInsnNode) next);
    }

    private final void visitFakeAlwaysTrueIfeq(AbstractInsnNode insnNode) {
        insnNode.getNext().getOpcode();
        this.fakeAlwaysTrueIfeqMarkers.add(insnNode);
    }

    private final void visitFakeAlwaysFalseIfeq(AbstractInsnNode insnNode) {
        insnNode.getNext().getOpcode();
        this.fakeAlwaysFalseIfeqMarkers.add(insnNode);
    }

    private final int indexOf(AbstractInsnNode node) {
        return this.methodNode.instructions.indexOf(node);
    }

    public final boolean hasAnyMarkers() {
        return (this.breakContinueGotoNodes.isEmpty() && this.fakeAlwaysTrueIfeqMarkers.isEmpty() && this.fakeAlwaysFalseIfeqMarkers.isEmpty() && !this.isThereAnyTryCatch && this.openingInlineMethodMarker.isEmpty()) ? false : true;
    }

    public final boolean isAnalysisRequired() {
        return (this.breakContinueGotoNodes.isEmpty() && !this.isThereAnyTryCatch && this.openingInlineMethodMarker.isEmpty()) ? false : true;
    }
}
