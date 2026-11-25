package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: InplaceArgumentsMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010*\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0004\u001f !\"B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J&\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0014H\u0002J&\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0014H\u0002J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0018\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0014\u0010\u001d\u001a\u00020\u001e*\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "collectLvtEntryInstructions", "", "methodContext", "Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer$MethodContext;", "collectStartToEnd", "argContext", "Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer$ArgContext;", "callContext", "Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer$CallContext;", "collectSuspensionPoints", "moveInplaceArgumentsFromStoresToLoads", "parseArg", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "start", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "iter", "", "parseCall", "parseMethodOrNull", "stripMarkers", "transform", "internalClassName", "", "transformCall", "transformMethod", "isUnsafeToMove", "", "ArgContext", "CallContext", "MethodContext", "ParseErrorException", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InplaceArgumentsMethodTransformer extends MethodTransformer {
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        MethodContext methodOrNull = parseMethodOrNull(methodNode);
        if (methodOrNull != null) {
            if (methodOrNull.getCalls().isEmpty()) {
                return;
            }
            collectStartToEnd(methodOrNull);
            collectLvtEntryInstructions(methodOrNull);
            collectSuspensionPoints(methodOrNull);
            transformMethod(methodOrNull);
            InplaceArgumentsMethodTransformerKt.fixupLVT(methodNode);
            UtilKt.removeUnusedLocalVariables(methodNode);
            UtilKt.updateMaxStack(methodNode);
        }
        stripMarkers(methodNode);
    }

    /* JADX INFO: compiled from: InplaceArgumentsMethodTransformer.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR-\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r`\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R-\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000bj\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f`\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R!\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u00170\u0016j\b\u0012\u0004\u0012\u00020\u0017`\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR-\u0010\u001b\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00060\u000bj\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u0006`\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0010¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer$MethodContext;", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "calls", "", "Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer$CallContext;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Ljava/util/List;)V", "getCalls", "()Ljava/util/List;", "lvtEntryForInstruction", "Ljava/util/HashMap;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;", "Lkotlin/collections/HashMap;", "getLvtEntryForInstruction", "()Ljava/util/HashMap;", "getMethodNode", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "startArgToEndArg", "getStartArgToEndArg", "suspensionJumpLabels", "Ljava/util/HashSet;", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "Lkotlin/collections/HashSet;", "getSuspensionJumpLabels", "()Ljava/util/HashSet;", "varInstructionMoved", "getVarInstructionMoved", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class MethodContext {
        private final List<CallContext> calls;
        private final HashMap<AbstractInsnNode, LocalVariableNode> lvtEntryForInstruction;
        private final MethodNode methodNode;
        private final HashMap<AbstractInsnNode, AbstractInsnNode> startArgToEndArg;
        private final HashSet<LabelNode> suspensionJumpLabels;
        private final HashMap<AbstractInsnNode, CallContext> varInstructionMoved;

        public MethodContext(MethodNode methodNode, List<CallContext> calls) {
            Intrinsics.checkNotNullParameter(methodNode, "methodNode");
            Intrinsics.checkNotNullParameter(calls, "calls");
            this.methodNode = methodNode;
            this.calls = calls;
            this.startArgToEndArg = new HashMap<>();
            this.lvtEntryForInstruction = new HashMap<>();
            this.varInstructionMoved = new HashMap<>();
            this.suspensionJumpLabels = new HashSet<>();
        }

        public final MethodNode getMethodNode() {
            return this.methodNode;
        }

        public final List<CallContext> getCalls() {
            return this.calls;
        }

        public final HashMap<AbstractInsnNode, AbstractInsnNode> getStartArgToEndArg() {
            return this.startArgToEndArg;
        }

        public final HashMap<AbstractInsnNode, LocalVariableNode> getLvtEntryForInstruction() {
            return this.lvtEntryForInstruction;
        }

        public final HashMap<AbstractInsnNode, CallContext> getVarInstructionMoved() {
            return this.varInstructionMoved;
        }

        public final HashSet<LabelNode> getSuspensionJumpLabels() {
            return this.suspensionJumpLabels;
        }
    }

    /* JADX INFO: compiled from: InplaceArgumentsMethodTransformer.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00000\u0006¢\u0006\u0002\u0010\tR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00000\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer$CallContext;", "", "callStartMarker", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "callEndMarker", "args", "", "Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer$ArgContext;", "calls", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Ljava/util/List;Ljava/util/List;)V", "getArgs", "()Ljava/util/List;", "getCallEndMarker", "()Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getCallStartMarker", "getCalls", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CallContext {
        private final List<ArgContext> args;
        private final AbstractInsnNode callEndMarker;
        private final AbstractInsnNode callStartMarker;
        private final List<CallContext> calls;

        public CallContext(AbstractInsnNode callStartMarker, AbstractInsnNode callEndMarker, List<ArgContext> args, List<CallContext> calls) {
            Intrinsics.checkNotNullParameter(callStartMarker, "callStartMarker");
            Intrinsics.checkNotNullParameter(callEndMarker, "callEndMarker");
            Intrinsics.checkNotNullParameter(args, "args");
            Intrinsics.checkNotNullParameter(calls, "calls");
            this.callStartMarker = callStartMarker;
            this.callEndMarker = callEndMarker;
            this.args = args;
            this.calls = calls;
        }

        public final AbstractInsnNode getCallStartMarker() {
            return this.callStartMarker;
        }

        public final AbstractInsnNode getCallEndMarker() {
            return this.callEndMarker;
        }

        public final List<ArgContext> getArgs() {
            return this.args;
        }

        public final List<CallContext> getCalls() {
            return this.calls;
        }
    }

    /* JADX INFO: compiled from: InplaceArgumentsMethodTransformer.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer$ArgContext;", "", "argStartMarker", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "argEndMarker", "calls", "", "Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer$CallContext;", "storeInsn", "Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Ljava/util/List;Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;)V", "getArgEndMarker", "()Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getArgStartMarker", "getCalls", "()Ljava/util/List;", "loadOpcode", "", "getLoadOpcode", "()I", "getStoreInsn", "()Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", "varIndex", "getVarIndex", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ArgContext {
        private final AbstractInsnNode argEndMarker;
        private final AbstractInsnNode argStartMarker;
        private final List<CallContext> calls;
        private final int loadOpcode;
        private final VarInsnNode storeInsn;
        private final int varIndex;

        public ArgContext(AbstractInsnNode argStartMarker, AbstractInsnNode argEndMarker, List<CallContext> calls, VarInsnNode varInsnNode) {
            Intrinsics.checkNotNullParameter(argStartMarker, "argStartMarker");
            Intrinsics.checkNotNullParameter(argEndMarker, "argEndMarker");
            Intrinsics.checkNotNullParameter(calls, "calls");
            this.argStartMarker = argStartMarker;
            this.argEndMarker = argEndMarker;
            this.calls = calls;
            this.storeInsn = varInsnNode;
            this.loadOpcode = varInsnNode != null ? varInsnNode.getOpcode() - 33 : -1;
            this.varIndex = varInsnNode != null ? varInsnNode.var : -1;
        }

        public final AbstractInsnNode getArgStartMarker() {
            return this.argStartMarker;
        }

        public final AbstractInsnNode getArgEndMarker() {
            return this.argEndMarker;
        }

        public final List<CallContext> getCalls() {
            return this.calls;
        }

        public final VarInsnNode getStoreInsn() {
            return this.storeInsn;
        }

        public final int getLoadOpcode() {
            return this.loadOpcode;
        }

        public final int getVarIndex() {
            return this.varIndex;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, java.util.ListIterator] */
    private final MethodContext parseMethodOrNull(MethodNode methodNode) {
        ?? it = methodNode.instructions.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            try {
                AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
                Intrinsics.checkNotNull(abstractInsnNode);
                if (InlineArgumentsInPlaceKt.isInplaceCallStartMarker(abstractInsnNode)) {
                    arrayList.add(parseCall(methodNode, abstractInsnNode, it));
                } else if (InlineArgumentsInPlaceKt.isInplaceCallEndMarker(abstractInsnNode) || InlineArgumentsInPlaceKt.isInplaceArgumentStartMarker(abstractInsnNode) || InlineArgumentsInPlaceKt.isInplaceArgumentEndMarker(abstractInsnNode)) {
                    throw new ParseErrorException();
                }
            } catch (ParseErrorException unused) {
                return null;
            }
        }
        return new MethodContext(methodNode, arrayList);
    }

    private final CallContext parseCall(MethodNode methodNode, AbstractInsnNode start, ListIterator<? extends AbstractInsnNode> iter) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        while (iter.hasNext()) {
            AbstractInsnNode next = iter.next();
            if (InlineArgumentsInPlaceKt.isInplaceCallStartMarker(next)) {
                arrayList2.add(parseCall(methodNode, next, iter));
            } else {
                if (InlineArgumentsInPlaceKt.isInplaceCallEndMarker(next)) {
                    return new CallContext(start, next, arrayList, arrayList2);
                }
                if (InlineArgumentsInPlaceKt.isInplaceArgumentStartMarker(next)) {
                    arrayList.add(parseArg(methodNode, next, iter));
                } else if (InlineArgumentsInPlaceKt.isInplaceArgumentEndMarker(next)) {
                    throw new ParseErrorException();
                }
            }
        }
        throw new ParseErrorException();
    }

    private final ArgContext parseArg(MethodNode methodNode, AbstractInsnNode start, ListIterator<? extends AbstractInsnNode> iter) {
        VarInsnNode varInsnNode;
        int opcode;
        ArrayList arrayList = new ArrayList();
        while (iter.hasNext()) {
            AbstractInsnNode next = iter.next();
            if (InlineArgumentsInPlaceKt.isInplaceCallStartMarker(next)) {
                arrayList.add(parseCall(methodNode, next, iter));
            } else {
                if (InlineArgumentsInPlaceKt.isInplaceArgumentEndMarker(next)) {
                    AbstractInsnNode next2 = next.getNext();
                    if ((next2 instanceof VarInsnNode) && 54 <= (opcode = (varInsnNode = (VarInsnNode) next2).getOpcode()) && opcode < 59) {
                        iter.next();
                        return new ArgContext(start, next, arrayList, varInsnNode);
                    }
                    AbstractInsnNode previous = next.getPrevious();
                    Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
                    if (InlineArgumentsInPlaceKt.isInplaceArgumentStartMarker(previous)) {
                        return new ArgContext(start, next, arrayList, null);
                    }
                    throw new ParseErrorException();
                }
                if (InlineArgumentsInPlaceKt.isInplaceCallEndMarker(next) || InlineArgumentsInPlaceKt.isInplaceArgumentStartMarker(next)) {
                    throw new ParseErrorException();
                }
            }
        }
        throw new ParseErrorException();
    }

    /* JADX INFO: compiled from: InplaceArgumentsMethodTransformer.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InplaceArgumentsMethodTransformer$ParseErrorException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "()V", "fillInStackTrace", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ParseErrorException extends RuntimeException {
        @Override // java.lang.Throwable
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    private final void collectStartToEnd(MethodContext methodContext) {
        Iterator<CallContext> it = methodContext.getCalls().iterator();
        while (it.hasNext()) {
            collectStartToEnd(methodContext, it.next());
        }
    }

    private final void collectStartToEnd(MethodContext methodContext, CallContext callContext) {
        Iterator<ArgContext> it = callContext.getArgs().iterator();
        while (it.hasNext()) {
            collectStartToEnd(methodContext, it.next());
        }
        Iterator<CallContext> it2 = callContext.getCalls().iterator();
        while (it2.hasNext()) {
            collectStartToEnd(methodContext, it2.next());
        }
    }

    private final void collectStartToEnd(MethodContext methodContext, ArgContext argContext) {
        methodContext.getStartArgToEndArg().put(argContext.getArgStartMarker(), argContext.getArgEndMarker());
        Iterator<CallContext> it = argContext.getCalls().iterator();
        while (it.hasNext()) {
            collectStartToEnd(methodContext, it.next());
        }
    }

    private final void collectLvtEntryInstructions(MethodContext methodContext) {
        int opcode;
        InsnList insnList = methodContext.getMethodNode().instructions;
        AbstractInsnNode[] array = insnList.toArray();
        for (LocalVariableNode localVariableNode : methodContext.getMethodNode().localVariables) {
            int iIndexOf = insnList.indexOf(localVariableNode.end);
            for (int iIndexOf2 = insnList.indexOf(localVariableNode.start); iIndexOf2 < iIndexOf; iIndexOf2++) {
                AbstractInsnNode abstractInsnNode = array[iIndexOf2];
                int opcode2 = abstractInsnNode.getOpcode();
                if ((21 <= opcode2 && opcode2 < 26) || (54 <= (opcode = abstractInsnNode.getOpcode()) && opcode < 59)) {
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    if (((VarInsnNode) abstractInsnNode).var == localVariableNode.index) {
                        HashMap<AbstractInsnNode, LocalVariableNode> lvtEntryForInstruction = methodContext.getLvtEntryForInstruction();
                        Intrinsics.checkNotNull(localVariableNode);
                        lvtEntryForInstruction.put(abstractInsnNode, localVariableNode);
                    }
                } else if (abstractInsnNode.getOpcode() == 132) {
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.IincInsnNode");
                    if (((IincInsnNode) abstractInsnNode).var == localVariableNode.index) {
                        HashMap<AbstractInsnNode, LocalVariableNode> lvtEntryForInstruction2 = methodContext.getLvtEntryForInstruction();
                        Intrinsics.checkNotNull(localVariableNode);
                        lvtEntryForInstruction2.put(abstractInsnNode, localVariableNode);
                    }
                }
            }
        }
    }

    private final void collectSuspensionPoints(MethodContext methodContext) {
        AbstractInsnNode first = methodContext.getMethodNode().instructions.getFirst();
        do {
            Intrinsics.checkNotNull(first);
            if (first.getOpcode() == 184 && (first instanceof MethodInsnNode)) {
                MethodInsnNode methodInsnNode = (MethodInsnNode) first;
                if (Intrinsics.areEqual(methodInsnNode.owner, "kotlin/coroutines/intrinsics/IntrinsicsKt") && Intrinsics.areEqual(methodInsnNode.f1015name, "getCOROUTINE_SUSPENDED") && Intrinsics.areEqual(methodInsnNode.desc, "()Ljava/lang/Object;")) {
                    while (first != null) {
                        if (first.getOpcode() != 170 || first.getPrevious().getOpcode() != 180) {
                            first = first.getNext();
                        } else {
                            AbstractInsnNode previous = first.getPrevious();
                            Intrinsics.checkNotNull(previous, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.FieldInsnNode");
                            FieldInsnNode fieldInsnNode = (FieldInsnNode) previous;
                            if (!Intrinsics.areEqual(fieldInsnNode.f1010name, CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME) || !Intrinsics.areEqual(fieldInsnNode.desc, "I")) {
                                first = first.getNext();
                            } else {
                                TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) first;
                                methodContext.getSuspensionJumpLabels().addAll(tableSwitchInsnNode.labels);
                                methodContext.getSuspensionJumpLabels().add(tableSwitchInsnNode.dflt);
                                return;
                            }
                        }
                    }
                    return;
                }
            }
            first = first.getNext();
        } while (first != null);
    }

    private final void transformMethod(MethodContext methodContext) {
        Iterator<CallContext> it = methodContext.getCalls().iterator();
        while (it.hasNext()) {
            transformCall(methodContext, it.next());
        }
    }

    private final void transformCall(MethodContext methodContext, CallContext callContext) {
        Iterator<ArgContext> it = callContext.getArgs().iterator();
        while (it.hasNext()) {
            Iterator<CallContext> it2 = it.next().getCalls().iterator();
            while (it2.hasNext()) {
                transformCall(methodContext, it2.next());
            }
        }
        Iterator<CallContext> it3 = callContext.getCalls().iterator();
        while (it3.hasNext()) {
            transformCall(methodContext, it3.next());
        }
        List<ArgContext> args = callContext.getArgs();
        if (!(args instanceof Collection) || !args.isEmpty()) {
            Iterator<T> it4 = args.iterator();
            while (it4.hasNext()) {
                if (isUnsafeToMove((ArgContext) it4.next(), methodContext)) {
                    InsnList insnList = methodContext.getMethodNode().instructions;
                    for (ArgContext argContext : callContext.getArgs()) {
                        insnList.remove(argContext.getArgStartMarker());
                        insnList.remove(argContext.getArgEndMarker());
                    }
                    insnList.remove(callContext.getCallStartMarker());
                    insnList.remove(callContext.getCallEndMarker());
                    return;
                }
            }
        }
        moveInplaceArgumentsFromStoresToLoads(methodContext, callContext);
    }

    private final boolean isUnsafeToMove(ArgContext argContext, MethodContext methodContext) {
        InsnSequence insnSequence = new InsnSequence(argContext.getArgStartMarker(), argContext.getArgEndMarker());
        HashSet hashSet = new HashSet();
        for (AbstractInsnNode abstractInsnNode : insnSequence) {
            if (abstractInsnNode instanceof LabelNode) {
                hashSet.add(abstractInsnNode);
            }
        }
        HashSet hashSet2 = hashSet;
        for (AbstractInsnNode abstractInsnNode2 : insnSequence) {
            if (!UtilKt.isStoreOperation(abstractInsnNode2) && !CollectionsKt.contains(methodContext.getSuspensionJumpLabels(), abstractInsnNode2)) {
                if (abstractInsnNode2.getOpcode() == 167) {
                    Intrinsics.checkNotNull(abstractInsnNode2, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                    if (!hashSet2.contains(((JumpInsnNode) abstractInsnNode2).label)) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    private final void moveInplaceArgumentsFromStoresToLoads(MethodContext methodContext, CallContext callContext) {
        int opcode;
        InsnList insnList = methodContext.getMethodNode().instructions;
        List<ArgContext> args = callContext.getArgs();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(args, 10)), 16));
        for (Object obj : args) {
            linkedHashMap.put(Integer.valueOf(((ArgContext) obj).getVarIndex()), obj);
        }
        AbstractInsnNode callStartMarker = callContext.getCallStartMarker();
        int i = 0;
        while (!Intrinsics.areEqual(callStartMarker, callContext.getCallEndMarker())) {
            if (InlineArgumentsInPlaceKt.isInplaceArgumentStartMarker(callStartMarker)) {
                AbstractInsnNode abstractInsnNode = methodContext.getStartArgToEndArg().get(callStartMarker);
                Intrinsics.checkNotNull(abstractInsnNode);
                callStartMarker = abstractInsnNode;
            } else {
                int opcode2 = callStartMarker.getOpcode();
                if (21 <= opcode2 && opcode2 < 26) {
                    Intrinsics.checkNotNull(callStartMarker, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    VarInsnNode varInsnNode = (VarInsnNode) callStartMarker;
                    int i2 = varInsnNode.var;
                    ArgContext argContext = (ArgContext) linkedHashMap.get(Integer.valueOf(i2));
                    if (argContext == null || argContext.getLoadOpcode() != varInsnNode.getOpcode()) {
                        callStartMarker = varInsnNode.getNext();
                        Intrinsics.checkNotNullExpressionValue(callStartMarker, "getNext(...)");
                    } else {
                        AbstractInsnNode next = argContext.getArgStartMarker().getNext();
                        while (!Intrinsics.areEqual(next, argContext.getArgEndMarker())) {
                            int opcode3 = next.getOpcode();
                            if ((21 <= opcode3 && opcode3 < 26) || ((54 <= (opcode = next.getOpcode()) && opcode < 59) || next.getOpcode() == 132)) {
                                HashMap<AbstractInsnNode, CallContext> varInstructionMoved = methodContext.getVarInstructionMoved();
                                Intrinsics.checkNotNull(next);
                                varInstructionMoved.put(next, callContext);
                            }
                            AbstractInsnNode next2 = next.getNext();
                            insnList.remove(next);
                            insnList.insertBefore(varInsnNode, next);
                            next = next2;
                        }
                        insnList.remove(argContext.getStoreInsn());
                        AbstractInsnNode next3 = varInsnNode.getNext();
                        Intrinsics.checkNotNullExpressionValue(next3, "getNext(...)");
                        insnList.remove(varInsnNode);
                        while (next3.getOpcode() == varInsnNode.getOpcode()) {
                            VarInsnNode varInsnNode2 = (VarInsnNode) next3;
                            if (varInsnNode2.var != i2) {
                                break;
                            }
                            if (varInsnNode2.getOpcode() == 22 || varInsnNode2.getOpcode() == 24) {
                                insnList.insertBefore(next3, new InsnNode(92));
                            } else {
                                insnList.insertBefore(next3, new InsnNode(89));
                            }
                            AbstractInsnNode next4 = varInsnNode2.getNext();
                            insnList.remove(next3);
                            Intrinsics.checkNotNull(next4);
                            next3 = next4;
                        }
                        insnList.remove(argContext.getArgStartMarker());
                        insnList.remove(argContext.getArgEndMarker());
                        i++;
                        if (i >= callContext.getArgs().size()) {
                            break;
                        } else {
                            callStartMarker = next3;
                        }
                    }
                } else {
                    callStartMarker = callStartMarker.getNext();
                    Intrinsics.checkNotNullExpressionValue(callStartMarker, "getNext(...)");
                }
            }
        }
        insnList.remove(callContext.getCallStartMarker());
        insnList.remove(callContext.getCallEndMarker());
    }

    private final void stripMarkers(MethodNode methodNode) {
        AbstractInsnNode first = methodNode.instructions.getFirst();
        while (first != null) {
            if (InlineArgumentsInPlaceKt.isInplaceCallStartMarker(first) || InlineArgumentsInPlaceKt.isInplaceCallEndMarker(first) || InlineArgumentsInPlaceKt.isInplaceArgumentStartMarker(first) || InlineArgumentsInPlaceKt.isInplaceArgumentEndMarker(first)) {
                AbstractInsnNode next = first.getNext();
                methodNode.instructions.remove(first);
                first = next;
            } else {
                first = first.getNext();
            }
        }
    }
}
