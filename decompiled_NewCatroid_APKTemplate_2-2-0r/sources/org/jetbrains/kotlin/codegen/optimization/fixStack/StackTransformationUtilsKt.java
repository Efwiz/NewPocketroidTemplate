package org.jetbrains.kotlin.codegen.optimization.fixStack;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: StackTransformationUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u0003\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u001e\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\u001a\u0016\u0010\r\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0005\u001a\u0016\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0005\u001a,\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014\u001a\u001e\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a.\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u0012\u001a\u001e\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a)\u0010\u001c\u001a\u0004\u0018\u0001H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u001e*\b\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010 \u001a\u00020\u0012¢\u0006\u0002\u0010!\u001a*\u0010\"\u001a\n\u0012\u0004\u0012\u0002H\u001d\u0018\u00010\u0014\"\b\b\u0000\u0010\u001d*\u00020\u001e*\b\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010#\u001a\u00020\u0012\u001a2\u0010\"\u001a\n\u0012\u0004\u0012\u0002H\u001d\u0018\u00010\u0014\"\b\b\u0000\u0010\u001d*\u00020\u001e*\b\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010$\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u0012\u001a<\u0010&\u001a\u00020\u0012\"\b\b\u0000\u0010\u001d*\u00020\u001e*\b\u0012\u0004\u0012\u0002H\u001d0\u001f2\f\u0010'\u001a\b\u0012\u0004\u0012\u0002H\u001d0(2\u0006\u0010#\u001a\u00020\u00122\b\b\u0002\u0010)\u001a\u00020\u0012H\u0002\u001a!\u0010\u000b\u001a\u0004\u0018\u0001H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u001e*\b\u0012\u0004\u0012\u0002H\u001d0\u001f¢\u0006\u0002\u0010*¨\u0006+"}, d2 = {"generateLoadInstructions", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "savedStackDescriptor", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/SavedStackDescriptor;", "generateStoreInstructions", "getPopInstruction", "Lorg/jetbrains/org/objectweb/asm/tree/InsnNode;", "top", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "removeAlwaysFalseIfeq", "node", "replaceAlwaysTrueIfeqWithGoto", "replaceMarkerWithPops", "expectedStackSize", "", "stackContent", "", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", "restoreStack", "restoreStackWithReturnValue", "nodeToReplace", "returnValue", "returnValueLocalVarIndex", "saveStack", "peek", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", TypedValues.CycleType.S_WAVE_OFFSET, "(Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;I)Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "peekWords", "size", "size1", "size2", "peekWordsTo", "dest", "", "offset0", "(Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;)Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class StackTransformationUtilsKt {
    public static final <V extends Value> V top(Frame<V> frame) {
        Intrinsics.checkNotNullParameter(frame, "<this>");
        return (V) peek(frame, 0);
    }

    public static final <V extends Value> V peek(Frame<V> frame, int i) {
        Intrinsics.checkNotNullParameter(frame, "<this>");
        if (frame.getStackSize() > i) {
            return (V) frame.getStack((frame.getStackSize() - i) - 1);
        }
        return null;
    }

    static /* synthetic */ int peekWordsTo$default(Frame frame, List list, int i, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        return peekWordsTo(frame, list, i, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <V extends Value> int peekWordsTo(Frame<V> frame, List<V> list, int i, int i2) {
        int i3 = 0;
        while (i3 < i) {
            int i4 = i2 + 1;
            Value valuePeek = peek(frame, i2);
            if (valuePeek == null) {
                return -1;
            }
            list.add(valuePeek);
            i3 += valuePeek.get_size();
            i2 = i4;
        }
        if (i3 > i) {
            return -1;
        }
        return i2;
    }

    public static final <V extends Value> List<V> peekWords(Frame<V> frame, int i) {
        Intrinsics.checkNotNullParameter(frame, "<this>");
        ArrayList arrayList = new ArrayList(i);
        if (peekWordsTo$default(frame, arrayList, i, 0, 4, null) < 0) {
            return null;
        }
        return arrayList;
    }

    public static final <V extends Value> List<V> peekWords(Frame<V> frame, int i, int i2) {
        Intrinsics.checkNotNullParameter(frame, "<this>");
        ArrayList arrayList = new ArrayList(i + i2);
        int iPeekWordsTo$default = peekWordsTo$default(frame, arrayList, i, 0, 4, null);
        if (iPeekWordsTo$default >= 0 && peekWordsTo(frame, arrayList, i2, iPeekWordsTo$default) >= 0) {
            return arrayList;
        }
        return null;
    }

    public static final void saveStack(MethodNode methodNode, AbstractInsnNode nodeToReplace, SavedStackDescriptor savedStackDescriptor) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(nodeToReplace, "nodeToReplace");
        Intrinsics.checkNotNullParameter(savedStackDescriptor, "savedStackDescriptor");
        InsnList insnList = methodNode.instructions;
        generateStoreInstructions(methodNode, nodeToReplace, savedStackDescriptor);
        insnList.remove(nodeToReplace);
    }

    public static final void restoreStack(MethodNode methodNode, AbstractInsnNode location, SavedStackDescriptor savedStackDescriptor) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(savedStackDescriptor, "savedStackDescriptor");
        InsnList insnList = methodNode.instructions;
        generateLoadInstructions(methodNode, location, savedStackDescriptor);
        insnList.remove(location);
    }

    public static final void restoreStackWithReturnValue(MethodNode methodNode, AbstractInsnNode nodeToReplace, SavedStackDescriptor savedStackDescriptor, FixStackValue returnValue, int i) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(nodeToReplace, "nodeToReplace");
        Intrinsics.checkNotNullParameter(savedStackDescriptor, "savedStackDescriptor");
        Intrinsics.checkNotNullParameter(returnValue, "returnValue");
        InsnList insnList = methodNode.instructions;
        insnList.insertBefore(nodeToReplace, new VarInsnNode(returnValue.getStoreOpcode(), i));
        generateLoadInstructions(methodNode, nodeToReplace, savedStackDescriptor);
        insnList.insertBefore(nodeToReplace, new VarInsnNode(returnValue.getLoadOpcode(), i));
        insnList.remove(nodeToReplace);
    }

    public static final void generateLoadInstructions(MethodNode methodNode, AbstractInsnNode location, SavedStackDescriptor savedStackDescriptor) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(savedStackDescriptor, "savedStackDescriptor");
        int firstLocalVarIndex = savedStackDescriptor.getFirstLocalVarIndex();
        for (FixStackValue fixStackValue : savedStackDescriptor.getSavedValues()) {
            methodNode.instructions.insertBefore(location, new VarInsnNode(fixStackValue.getLoadOpcode(), firstLocalVarIndex));
            firstLocalVarIndex += fixStackValue.get_size();
        }
    }

    public static final void generateStoreInstructions(MethodNode methodNode, AbstractInsnNode location, SavedStackDescriptor savedStackDescriptor) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(savedStackDescriptor, "savedStackDescriptor");
        int firstUnusedLocalVarIndex = savedStackDescriptor.getFirstUnusedLocalVarIndex();
        for (FixStackValue fixStackValue : CollectionsKt.asReversed(savedStackDescriptor.getSavedValues())) {
            firstUnusedLocalVarIndex -= fixStackValue.get_size();
            methodNode.instructions.insertBefore(location, new VarInsnNode(fixStackValue.getStoreOpcode(), firstUnusedLocalVarIndex));
        }
    }

    public static final InsnNode getPopInstruction(BasicValue top) {
        int i;
        Intrinsics.checkNotNullParameter(top, "top");
        int i2 = top.get_size();
        if (i2 == 1) {
            i = 87;
        } else {
            if (i2 != 2) {
                throw new AssertionError("Unexpected value type size");
            }
            i = 88;
        }
        return new InsnNode(i);
    }

    public static final void removeAlwaysFalseIfeq(MethodNode methodNode, AbstractInsnNode node) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(node, "node");
        InsnList insnList = methodNode.instructions;
        insnList.remove(node.getNext());
        insnList.remove(node);
    }

    public static final void replaceAlwaysTrueIfeqWithGoto(MethodNode methodNode, AbstractInsnNode node) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(node, "node");
        InsnList insnList = methodNode.instructions;
        AbstractInsnNode next = node.getNext();
        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
        JumpInsnNode jumpInsnNode = (JumpInsnNode) next;
        insnList.insertBefore(node, new JumpInsnNode(167, jumpInsnNode.label));
        insnList.remove(node);
        insnList.remove(jumpInsnNode);
    }

    public static final void replaceMarkerWithPops(MethodNode methodNode, AbstractInsnNode node, int i, List<? extends FixStackValue> stackContent) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(stackContent, "stackContent");
        InsnList insnList = methodNode.instructions;
        Iterator<? extends FixStackValue> it = stackContent.subList(i, stackContent.size()).iterator();
        while (it.hasNext()) {
            insnList.insert(node, new InsnNode(it.next().getPopOpcode()));
        }
        insnList.remove(node);
    }
}
