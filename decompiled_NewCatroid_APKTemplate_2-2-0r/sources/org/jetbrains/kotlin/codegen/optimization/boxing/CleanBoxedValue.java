package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;

/* JADX INFO: compiled from: BoxedBasicValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u0013\u001a\u00020\u0001H\u0016R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/CleanBoxedValue;", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedBasicValue;", "boxedType", "Lorg/jetbrains/org/objectweb/asm/Type;", "boxingInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "progressionIterator", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/ProgressionIteratorBasicValue;", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/kotlin/codegen/optimization/boxing/ProgressionIteratorBasicValue;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "descriptor", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedValueDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedValueDescriptor;", "getGenerationState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "tainted", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/TaintedBoxedValue;", "taint", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CleanBoxedValue extends BoxedBasicValue {
    private final BoxedValueDescriptor descriptor;
    private final GenerationState generationState;
    private TaintedBoxedValue tainted;

    public final GenerationState getGenerationState() {
        return this.generationState;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CleanBoxedValue(Type boxedType, AbstractInsnNode boxingInsn, ProgressionIteratorBasicValue progressionIteratorBasicValue, GenerationState generationState) {
        super(boxedType);
        Intrinsics.checkNotNullParameter(boxedType, "boxedType");
        Intrinsics.checkNotNullParameter(boxingInsn, "boxingInsn");
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        this.generationState = generationState;
        this.descriptor = new BoxedValueDescriptor(boxedType, boxingInsn, progressionIteratorBasicValue, generationState);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxedBasicValue
    public BoxedValueDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxedBasicValue
    public BoxedBasicValue taint() {
        TaintedBoxedValue taintedBoxedValue = this.tainted;
        if (taintedBoxedValue == null) {
            taintedBoxedValue = new TaintedBoxedValue(this);
            this.tainted = taintedBoxedValue;
        }
        return taintedBoxedValue;
    }
}
