package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.codegen.BranchedValue;
import org.jetbrains.kotlin.codegen.NumberCompare;
import org.jetbrains.kotlin.codegen.ObjectCompare;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: CompareTo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/BooleanComparison;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "op", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "a", "Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lcom/intellij/psi/tree/IElementType;Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;)V", "getA", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "getB", "getOp", "()Lcom/intellij/psi/tree/IElementType;", "discard", "", "jumpIfFalse", "target", "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfTrue", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BooleanComparison extends BooleanValue {
    private final MaterialValue a;
    private final MaterialValue b;
    private final IElementType op;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BooleanComparison(IElementType op, MaterialValue a, MaterialValue b) {
        super(a.getCodegen());
        Intrinsics.checkNotNullParameter(op, "op");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        this.op = op;
        this.a = a;
        this.b = b;
    }

    public final MaterialValue getA() {
        return this.a;
    }

    public final MaterialValue getB() {
        return this.b;
    }

    public final IElementType getOp() {
        return this.op;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
    public void jumpIfFalse(Label target) {
        int iPatchOpcode;
        Intrinsics.checkNotNullParameter(target, "target");
        if (this.a.getType().getSort() == 10) {
            iPatchOpcode = ObjectCompare.INSTANCE.getObjectCompareOpcode(this.op);
        } else {
            iPatchOpcode = NumberCompare.INSTANCE.patchOpcode(NumberCompare.INSTANCE.getNumberCompareOpcode(this.op), getMv(), this.op, this.a.getType());
        }
        getMv().visitJumpInsn(iPatchOpcode, target);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
    public void jumpIfTrue(Label target) {
        Integer numValueOf;
        Intrinsics.checkNotNullParameter(target, "target");
        if (this.a.getType().getSort() == 10) {
            Integer num = BranchedValue.INSTANCE.getNegatedOperations().get(Integer.valueOf(ObjectCompare.INSTANCE.getObjectCompareOpcode(this.op)));
            Intrinsics.checkNotNull(num);
            numValueOf = num;
        } else {
            NumberCompare.Companion companion = NumberCompare.INSTANCE;
            Integer num2 = BranchedValue.INSTANCE.getNegatedOperations().get(Integer.valueOf(NumberCompare.INSTANCE.getNumberCompareOpcode(this.op)));
            Intrinsics.checkNotNull(num2);
            numValueOf = Integer.valueOf(companion.patchOpcode(num2.intValue(), getMv(), this.op, this.a.getType()));
        }
        Intrinsics.checkNotNull(numValueOf);
        getMv().visitJumpInsn(numValueOf.intValue(), target);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
    public void discard() {
        this.b.discard();
        this.a.discard();
    }
}
