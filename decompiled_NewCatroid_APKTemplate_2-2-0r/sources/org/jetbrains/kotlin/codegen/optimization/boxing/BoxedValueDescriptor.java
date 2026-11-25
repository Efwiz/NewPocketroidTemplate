package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;

/* JADX INFO: compiled from: BoxedBasicValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u001c\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u0005J\u000e\u0010-\u001a\u00020+2\u0006\u0010.\u001a\u00020\u0000J\u0016\u0010/\u001a\u00020+2\u0006\u00100\u001a\u00020\u00052\u0006\u00101\u001a\u00020\u0003J\u000e\u00102\u001a\u00020+2\u0006\u00103\u001a\u00020\u0010J\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u00050%J\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u000006J\u0006\u00107\u001a\u00020\u0010J\u0018\u00108\u001a\n 9*\u0004\u0018\u00010\u00030\u00032\b\u0010:\u001a\u0004\u0018\u00010;J\u0018\u0010<\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030)0=J\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00100%J\u0006\u0010?\u001a\u00020\u0019J\u0006\u0010@\u001a\u00020+J\u001a\u0010A\u001a\u00020+2\u0012\u0010B\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100CJ\u001a\u0010D\u001a\u00020+2\u0012\u0010B\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100CR\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\u00050\fj\b\u0012\u0004\u0012\u00020\u0005`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u00100\u000fj\b\u0012\u0004\u0012\u00020\u0010`\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001e\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u0019@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u001e\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u00000\u000fj\b\u0012\u0004\u0012\u00020\u0000`\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u001e\u001a\u0004\u0018\u00010\u001f¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00030%¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R6\u0010(\u001a*\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030)0\fj\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030)`\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedValueDescriptor;", "", "boxedType", "Lorg/jetbrains/org/objectweb/asm/Type;", "boxingInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "progressionIterator", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/ProgressionIteratorBasicValue;", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/kotlin/codegen/optimization/boxing/ProgressionIteratorBasicValue;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "associatedInsns", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "associatedVariables", "Ljava/util/HashSet;", "", "Lkotlin/collections/HashSet;", "getBoxedType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getBoxingInsn", "()Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getGenerationState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "<set-?>", "", "isSafeToRemove", "()Z", "isValueClassValue", "mergedWith", "multiFieldValueClassUnboxInfo", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$MultiFieldValueClassUnboxInfo;", "getMultiFieldValueClassUnboxInfo", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState$MultiFieldValueClassUnboxInfo;", "getProgressionIterator", "()Lorg/jetbrains/kotlin/codegen/optimization/boxing/ProgressionIteratorBasicValue;", "unboxedTypes", "", "getUnboxedTypes", "()Ljava/util/List;", "unboxingWithCastInsns", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Pair;", "addInsn", "", "insnNode", "addMergedWith", "descriptor", "addUnboxingWithCastTo", "insn", ModuleXmlParser.TYPE, "addVariableIndex", "index", "getAssociatedInsns", "getMergedWith", "", "getTotalUnboxSize", "getUnboxTypeOrOtherwiseMethodReturnType", JvmProtoBufUtil.PLATFORM_TYPE_ID, "methodInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;", "getUnboxingWithCastInsns", "", "getVariablesIndexes", "isFromProgressionIterator", "markAsUnsafeToRemove", "sortAssociatedInsns", "indexes", "", "sortUnboxingWithCastInsns", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BoxedValueDescriptor {
    private final LinkedHashSet<AbstractInsnNode> associatedInsns;
    private final HashSet<Integer> associatedVariables;
    private final Type boxedType;
    private final AbstractInsnNode boxingInsn;
    private final GenerationState generationState;
    private boolean isSafeToRemove;
    private final boolean isValueClassValue;
    private final HashSet<BoxedValueDescriptor> mergedWith;
    private final GenerationState.MultiFieldValueClassUnboxInfo multiFieldValueClassUnboxInfo;
    private final ProgressionIteratorBasicValue progressionIterator;
    private final List<Type> unboxedTypes;
    private final LinkedHashSet<Pair<AbstractInsnNode, Type>> unboxingWithCastInsns;

    public BoxedValueDescriptor(Type boxedType, AbstractInsnNode boxingInsn, ProgressionIteratorBasicValue progressionIteratorBasicValue, GenerationState generationState) {
        Intrinsics.checkNotNullParameter(boxedType, "boxedType");
        Intrinsics.checkNotNullParameter(boxingInsn, "boxingInsn");
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        this.boxedType = boxedType;
        this.boxingInsn = boxingInsn;
        this.progressionIterator = progressionIteratorBasicValue;
        this.generationState = generationState;
        this.associatedInsns = new LinkedHashSet<>();
        this.unboxingWithCastInsns = new LinkedHashSet<>();
        this.associatedVariables = new HashSet<>();
        this.mergedWith = new HashSet<>();
        this.isSafeToRemove = true;
        GenerationState.MultiFieldValueClassUnboxInfo multiFieldValueClassUnboxInfo = BoxedBasicValueKt.getMultiFieldValueClassUnboxInfo(boxedType, generationState);
        this.multiFieldValueClassUnboxInfo = multiFieldValueClassUnboxInfo;
        this.unboxedTypes = BoxedBasicValueKt.getUnboxedTypes(boxedType, generationState, multiFieldValueClassUnboxInfo);
        this.isValueClassValue = BoxedBasicValueKt.isValueClassValue(boxedType);
    }

    public final Type getBoxedType() {
        return this.boxedType;
    }

    public final AbstractInsnNode getBoxingInsn() {
        return this.boxingInsn;
    }

    public final ProgressionIteratorBasicValue getProgressionIterator() {
        return this.progressionIterator;
    }

    public final GenerationState getGenerationState() {
        return this.generationState;
    }

    /* JADX INFO: renamed from: isSafeToRemove, reason: from getter */
    public final boolean getIsSafeToRemove() {
        return this.isSafeToRemove;
    }

    public final GenerationState.MultiFieldValueClassUnboxInfo getMultiFieldValueClassUnboxInfo() {
        return this.multiFieldValueClassUnboxInfo;
    }

    public final List<Type> getUnboxedTypes() {
        return this.unboxedTypes;
    }

    public final Type getUnboxTypeOrOtherwiseMethodReturnType(MethodInsnNode methodInsnNode) {
        Type type = (Type) CollectionsKt.singleOrNull((List) this.unboxedTypes);
        if (type != null) {
            return type;
        }
        Intrinsics.checkNotNull(methodInsnNode);
        return Type.getReturnType(methodInsnNode.desc);
    }

    /* JADX INFO: renamed from: isValueClassValue, reason: from getter */
    public final boolean getIsValueClassValue() {
        return this.isValueClassValue;
    }

    public final List<AbstractInsnNode> getAssociatedInsns() {
        return CollectionsKt.toList(this.associatedInsns);
    }

    public final void sortAssociatedInsns(final Map<AbstractInsnNode, Integer> indexes) {
        Intrinsics.checkNotNullParameter(indexes, "indexes");
        List listSortedWith = CollectionsKt.sortedWith(this.associatedInsns, new Comparator() { // from class: org.jetbrains.kotlin.codegen.optimization.boxing.BoxedValueDescriptor$sortAssociatedInsns$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                Object obj = indexes.get((AbstractInsnNode) t);
                Intrinsics.checkNotNull(obj);
                Object obj2 = indexes.get((AbstractInsnNode) t2);
                Intrinsics.checkNotNull(obj2);
                return ComparisonsKt.compareValues((Integer) obj, (Integer) obj2);
            }
        });
        this.associatedInsns.clear();
        this.associatedInsns.addAll(listSortedWith);
    }

    public final void addInsn(AbstractInsnNode insnNode) {
        Intrinsics.checkNotNullParameter(insnNode, "insnNode");
        this.associatedInsns.add(insnNode);
    }

    public final void addVariableIndex(int index) {
        this.associatedVariables.add(Integer.valueOf(index));
    }

    public final List<Integer> getVariablesIndexes() {
        return new ArrayList(this.associatedVariables);
    }

    public final void addMergedWith(BoxedValueDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.mergedWith.add(descriptor);
    }

    public final Iterable<BoxedValueDescriptor> getMergedWith() {
        return this.mergedWith;
    }

    public final void markAsUnsafeToRemove() {
        this.isSafeToRemove = false;
    }

    public final int getTotalUnboxSize() {
        Iterator<T> it = this.unboxedTypes.iterator();
        int size = 0;
        while (it.hasNext()) {
            size += ((Type) it.next()).getSize();
        }
        return size;
    }

    public final boolean isFromProgressionIterator() {
        return this.progressionIterator != null;
    }

    public final void addUnboxingWithCastTo(AbstractInsnNode insn, Type type) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(type, "type");
        this.unboxingWithCastInsns.add(Pair.create(insn, type));
    }

    public final Set<Pair<AbstractInsnNode, Type>> getUnboxingWithCastInsns() {
        return this.unboxingWithCastInsns;
    }

    public final void sortUnboxingWithCastInsns(final Map<AbstractInsnNode, Integer> indexes) {
        Intrinsics.checkNotNullParameter(indexes, "indexes");
        List listSortedWith = CollectionsKt.sortedWith(this.unboxingWithCastInsns, new Comparator() { // from class: org.jetbrains.kotlin.codegen.optimization.boxing.BoxedValueDescriptor$sortUnboxingWithCastInsns$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                Object obj = indexes.get(((Pair) t).first);
                Intrinsics.checkNotNull(obj);
                Object obj2 = indexes.get(((Pair) t2).first);
                Intrinsics.checkNotNull(obj2);
                return ComparisonsKt.compareValues((Integer) obj, (Integer) obj2);
            }
        });
        this.unboxingWithCastInsns.clear();
        this.unboxingWithCastInsns.addAll(listSortedWith);
    }
}
