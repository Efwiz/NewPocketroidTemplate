package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: CompareTo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\fH\u0016J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\fH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/PrimitiveToObjectComparison;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "op", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "leftIsPrimitive", "", "left", "Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "right", "(Lcom/intellij/psi/tree/IElementType;ZLorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;)V", "checkTypeAndCompare", "onWrongType", "Lorg/jetbrains/org/objectweb/asm/Label;", "discard", "", "jumpIfFalse", "target", "jumpIfTrue", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveToObjectComparison extends BooleanValue {
    private final MaterialValue left;
    private final boolean leftIsPrimitive;
    private final IElementType op;
    private final MaterialValue right;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PrimitiveToObjectComparison(IElementType op, boolean z, MaterialValue left, MaterialValue right) {
        super(left.getCodegen());
        Intrinsics.checkNotNullParameter(op, "op");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        this.op = op;
        this.leftIsPrimitive = z;
        this.left = left;
        this.right = right;
    }

    private final BooleanValue checkTypeAndCompare(Label onWrongType) {
        int iEnterTemp;
        Label label = new Label();
        if (this.leftIsPrimitive) {
            iEnterTemp = -1;
        } else {
            iEnterTemp = getCodegen().getFrameMap().enterTemp(this.right.getType());
            getMv().store(iEnterTemp, this.right.getType());
        }
        getMv().dup();
        if (AsmUtil.isBoxedPrimitiveType((this.leftIsPrimitive ? this.right : this.left).getType())) {
            getMv().ifnonnull(label);
        } else {
            getMv().instanceOf(AsmUtil.boxType((this.leftIsPrimitive ? this.left : this.right).getType()));
            getMv().ifne(label);
        }
        if (this.leftIsPrimitive) {
            this.right.discard();
        }
        this.left.discard();
        getMv().goTo(onWrongType);
        getMv().mark(label);
        if (this.leftIsPrimitive) {
            IElementType iElementType = this.op;
            MaterialValue materialValue = this.left;
            return new BooleanComparison(iElementType, materialValue, PromisedValueKt.materializedAt$default(this.right, materialValue.getType(), this.right.getIrType(), false, 4, null));
        }
        MaterialValue materialValueMaterializedAt$default = PromisedValueKt.materializedAt$default(this.left, this.right.getType(), this.left.getIrType(), false, 4, null);
        getMv().load(iEnterTemp, this.right.getType());
        getCodegen().getFrameMap().leaveTemp(this.right.getType());
        return new BooleanComparison(this.op, materialValueMaterializedAt$default, this.right);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
    public void jumpIfFalse(Label target) {
        Intrinsics.checkNotNullParameter(target, "target");
        checkTypeAndCompare(target).jumpIfFalse(target);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
    public void jumpIfTrue(Label target) {
        Intrinsics.checkNotNullParameter(target, "target");
        Label label = new Label();
        checkTypeAndCompare(label).jumpIfTrue(target);
        getMv().mark(label);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
    public void discard() {
        this.right.discard();
        this.left.discard();
    }
}
