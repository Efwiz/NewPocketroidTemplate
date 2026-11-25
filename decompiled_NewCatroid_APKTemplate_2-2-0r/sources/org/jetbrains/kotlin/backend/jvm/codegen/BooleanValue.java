package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: PromisedValue.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J \u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;)V", "jumpIfFalse", "", "target", "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfTrue", "materializeAt", "Lorg/jetbrains/org/objectweb/asm/Type;", "irTarget", "Lorg/jetbrains/kotlin/ir/types/IrType;", "castForReified", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BooleanValue extends PromisedValue {
    public abstract void jumpIfFalse(Label target);

    public abstract void jumpIfTrue(Label target);

    /* JADX WARN: Illegal instructions before constructor call */
    public BooleanValue(ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Type BOOLEAN_TYPE = Type.BOOLEAN_TYPE;
        Intrinsics.checkNotNullExpressionValue(BOOLEAN_TYPE, "BOOLEAN_TYPE");
        super(codegen, BOOLEAN_TYPE, codegen.getContext().getIrBuiltIns().getBooleanType());
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
    public void materializeAt(Type target, IrType irTarget, boolean castForReified) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(irTarget, "irTarget");
        Label label = new Label();
        Label label2 = new Label();
        jumpIfFalse(label);
        getMv().iconst(1);
        getMv().goTo(label2);
        getMv().mark(label);
        getMv().iconst(0);
        getMv().mark(label2);
        if (Intrinsics.areEqual(Type.BOOLEAN_TYPE, target)) {
            return;
        }
        StackValue.coerce(Type.BOOLEAN_TYPE, target, getMv());
    }
}
