package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: PromisedValue.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016J \u0010\u000f\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0005H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanConstant;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "value", "", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;Z)V", "getValue", "()Z", "discard", "", "jumpIfFalse", "target", "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfTrue", "materializeAt", "Lorg/jetbrains/org/objectweb/asm/Type;", "irTarget", "Lorg/jetbrains/kotlin/ir/types/IrType;", "castForReified", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BooleanConstant extends BooleanValue {
    private final boolean value;

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
    public void discard() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BooleanConstant(ExpressionCodegen codegen, boolean z) {
        super(codegen);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        this.value = z;
    }

    public final boolean getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
    public void jumpIfFalse(Label target) {
        Intrinsics.checkNotNullParameter(target, "target");
        if (this.value) {
            return;
        }
        getMv().goTo(target);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
    public void jumpIfTrue(Label target) {
        Intrinsics.checkNotNullParameter(target, "target");
        if (this.value) {
            getMv().goTo(target);
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue, org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
    public void materializeAt(Type target, IrType irTarget, boolean castForReified) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(irTarget, "irTarget");
        getMv().iconst(this.value ? 1 : 0);
        if (Intrinsics.areEqual(Type.BOOLEAN_TYPE, target)) {
            return;
        }
        StackValue.coerce(Type.BOOLEAN_TYPE, target, getMv());
    }
}
