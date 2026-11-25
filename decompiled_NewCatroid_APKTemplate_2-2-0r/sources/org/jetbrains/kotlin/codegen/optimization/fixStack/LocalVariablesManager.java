package org.jetbrains.kotlin.codegen.optimization.fixStack;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: LocalVariablesManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001:\u0001)B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J&\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002J\u001c\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017J\u001c\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017J\u000e\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u0018J\u000e\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\tJ\b\u0010 \u001a\u00020\u000fH\u0002J\u000e\u0010!\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020\tJ\u000e\u0010#\u001a\u00020$2\u0006\u0010\u001f\u001a\u00020\tJ\u0010\u0010%\u001a\u00020$2\u0006\u0010\u0015\u001a\u00020\tH\u0002J\u000e\u0010&\u001a\u00020$2\u0006\u0010\"\u001a\u00020\tJ\u0010\u0010'\u001a\u00020$2\u0006\u0010(\u001a\u00020\u000fH\u0002R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/LocalVariablesManager;", "", "context", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackContext;", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "(Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackContext;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)V", "allocatedHandles", "Ljava/util/HashMap;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/LocalVariablesManager$AllocatedHandle;", "Lkotlin/collections/HashMap;", "getContext", "()Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackContext;", "initialMaxLocals", "", "getMethodNode", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "allocateNewHandle", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/SavedStackDescriptor;", "numRestoreStackMarkers", "saveStackMarker", "savedStackValues", "", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", "allocateVariablesForBeforeInlineMarker", "beforeInlineMarker", "allocateVariablesForSaveStackMarker", "createReturnValueVariable", "returnValue", "getBeforeInlineDescriptor", "afterInlineMarker", "getFirstUnusedLocalVariableIndex", "getSavedStackDescriptor", "restoreStackMarker", "markAfterInlineMarkerEmitted", "", "markEmitted", "markRestoreStackMarkerEmitted", "updateMaxLocals", "newValue", "AllocatedHandle", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalVariablesManager {
    private final HashMap<AbstractInsnNode, AllocatedHandle> allocatedHandles;
    private final FixStackContext context;
    private final int initialMaxLocals;
    private final MethodNode methodNode;

    /* JADX INFO: compiled from: LocalVariablesManager.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u0010R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/LocalVariablesManager$AllocatedHandle;", "", "savedStackDescriptor", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/SavedStackDescriptor;", "numRestoreMarkers", "", "(Lorg/jetbrains/kotlin/codegen/optimization/fixStack/SavedStackDescriptor;I)V", "getNumRestoreMarkers", "()I", "setNumRestoreMarkers", "(I)V", "getSavedStackDescriptor", "()Lorg/jetbrains/kotlin/codegen/optimization/fixStack/SavedStackDescriptor;", "isFullyEmitted", "", "markRestoreNodeEmitted", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class AllocatedHandle {
        private int numRestoreMarkers;
        private final SavedStackDescriptor savedStackDescriptor;

        public AllocatedHandle(SavedStackDescriptor savedStackDescriptor, int i) {
            Intrinsics.checkNotNullParameter(savedStackDescriptor, "savedStackDescriptor");
            this.savedStackDescriptor = savedStackDescriptor;
            this.numRestoreMarkers = i;
        }

        public final int getNumRestoreMarkers() {
            return this.numRestoreMarkers;
        }

        public final SavedStackDescriptor getSavedStackDescriptor() {
            return this.savedStackDescriptor;
        }

        public final void setNumRestoreMarkers(int i) {
            this.numRestoreMarkers = i;
        }

        public final boolean isFullyEmitted() {
            return this.numRestoreMarkers == 0;
        }

        public final void markRestoreNodeEmitted() {
            this.numRestoreMarkers--;
        }
    }

    public LocalVariablesManager(FixStackContext context, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        this.context = context;
        this.methodNode = methodNode;
        this.initialMaxLocals = methodNode.maxLocals;
        this.allocatedHandles = new HashMap<>();
    }

    public final FixStackContext getContext() {
        return this.context;
    }

    public final MethodNode getMethodNode() {
        return this.methodNode;
    }

    private final void updateMaxLocals(int newValue) {
        MethodNode methodNode = this.methodNode;
        methodNode.maxLocals = Math.max(methodNode.maxLocals, newValue);
    }

    public final SavedStackDescriptor allocateVariablesForSaveStackMarker(AbstractInsnNode saveStackMarker, List<? extends FixStackValue> savedStackValues) {
        Intrinsics.checkNotNullParameter(saveStackMarker, "saveStackMarker");
        Intrinsics.checkNotNullParameter(savedStackValues, "savedStackValues");
        List<AbstractInsnNode> list = this.context.getRestoreStackMarkersForSaveMarker().get(saveStackMarker);
        Intrinsics.checkNotNull(list);
        return allocateNewHandle(list.size(), saveStackMarker, savedStackValues);
    }

    private final SavedStackDescriptor allocateNewHandle(int numRestoreStackMarkers, AbstractInsnNode saveStackMarker, List<? extends FixStackValue> savedStackValues) {
        List<? extends FixStackValue> list = savedStackValues;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (((FixStackValue) it.next()) == FixStackValue.UNINITIALIZED) {
                    throw new AssertionError("Uninitialized value on stack at " + this.methodNode.instructions.indexOf(saveStackMarker) + ": " + savedStackValues);
                }
            }
        }
        SavedStackDescriptor savedStackDescriptor = new SavedStackDescriptor(savedStackValues, getFirstUnusedLocalVariableIndex());
        updateMaxLocals(savedStackDescriptor.getFirstUnusedLocalVarIndex());
        this.allocatedHandles.put(saveStackMarker, new AllocatedHandle(savedStackDescriptor, numRestoreStackMarkers));
        return savedStackDescriptor;
    }

    public final SavedStackDescriptor getSavedStackDescriptor(AbstractInsnNode restoreStackMarker) {
        Intrinsics.checkNotNullParameter(restoreStackMarker, "restoreStackMarker");
        AllocatedHandle allocatedHandle = this.allocatedHandles.get(this.context.getSaveStackMarkerForRestoreMarker().get(restoreStackMarker));
        Intrinsics.checkNotNull(allocatedHandle);
        return allocatedHandle.getSavedStackDescriptor();
    }

    private final int getFirstUnusedLocalVariableIndex() {
        Collection<AllocatedHandle> collectionValues = this.allocatedHandles.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        int iMax = this.initialMaxLocals;
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            iMax = Math.max(iMax, ((AllocatedHandle) it.next()).getSavedStackDescriptor().getFirstUnusedLocalVarIndex());
        }
        return iMax;
    }

    public final void markRestoreStackMarkerEmitted(AbstractInsnNode restoreStackMarker) {
        Intrinsics.checkNotNullParameter(restoreStackMarker, "restoreStackMarker");
        AbstractInsnNode abstractInsnNode = this.context.getSaveStackMarkerForRestoreMarker().get(restoreStackMarker);
        Intrinsics.checkNotNull(abstractInsnNode);
        markEmitted(abstractInsnNode);
    }

    public final SavedStackDescriptor allocateVariablesForBeforeInlineMarker(AbstractInsnNode beforeInlineMarker, List<? extends FixStackValue> savedStackValues) {
        Intrinsics.checkNotNullParameter(beforeInlineMarker, "beforeInlineMarker");
        Intrinsics.checkNotNullParameter(savedStackValues, "savedStackValues");
        return allocateNewHandle(1, beforeInlineMarker, savedStackValues);
    }

    public final SavedStackDescriptor getBeforeInlineDescriptor(AbstractInsnNode afterInlineMarker) {
        Intrinsics.checkNotNullParameter(afterInlineMarker, "afterInlineMarker");
        AllocatedHandle allocatedHandle = this.allocatedHandles.get(this.context.getOpeningInlineMethodMarker().get(afterInlineMarker));
        Intrinsics.checkNotNull(allocatedHandle);
        return allocatedHandle.getSavedStackDescriptor();
    }

    public final void markAfterInlineMarkerEmitted(AbstractInsnNode afterInlineMarker) {
        Intrinsics.checkNotNullParameter(afterInlineMarker, "afterInlineMarker");
        AbstractInsnNode abstractInsnNode = this.context.getOpeningInlineMethodMarker().get(afterInlineMarker);
        Intrinsics.checkNotNull(abstractInsnNode);
        markEmitted(abstractInsnNode);
    }

    private final void markEmitted(AbstractInsnNode saveStackMarker) {
        AllocatedHandle allocatedHandle = this.allocatedHandles.get(saveStackMarker);
        Intrinsics.checkNotNull(allocatedHandle);
        AllocatedHandle allocatedHandle2 = allocatedHandle;
        allocatedHandle2.markRestoreNodeEmitted();
        if (allocatedHandle2.isFullyEmitted()) {
            this.allocatedHandles.remove(saveStackMarker);
        }
    }

    public final int createReturnValueVariable(FixStackValue returnValue) {
        Intrinsics.checkNotNullParameter(returnValue, "returnValue");
        int firstUnusedLocalVariableIndex = getFirstUnusedLocalVariableIndex();
        updateMaxLocals(returnValue.get_size() + firstUnusedLocalVariableIndex);
        return firstUnusedLocalVariableIndex;
    }
}
