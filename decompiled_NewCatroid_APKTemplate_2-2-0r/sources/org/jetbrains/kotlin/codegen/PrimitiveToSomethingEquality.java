package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BoxedVsPrimitiveBranchedValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u001f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH$J \u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0012H$R\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/codegen/PrimitiveToSomethingEquality;", "Lorg/jetbrains/kotlin/codegen/NumberLikeCompare;", "leftPrimitive", "Lorg/jetbrains/kotlin/codegen/StackValue;", "right", "primitiveType", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/org/objectweb/asm/Type;)V", "getPrimitiveType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "rightType", "getRightType", "coerceRightToPrimitive", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "condJump", "jumpLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfFalse", "", "jumpIfCanCompareTopWithPrimitive", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PrimitiveToSomethingEquality extends NumberLikeCompare {
    private final Type primitiveType;
    private final Type rightType;

    protected abstract void coerceRightToPrimitive(InstructionAdapter v);

    protected abstract void jumpIfCanCompareTopWithPrimitive(InstructionAdapter v, Label label);

    /* JADX WARN: Illegal instructions before constructor call */
    protected PrimitiveToSomethingEquality(StackValue leftPrimitive, StackValue right, Type primitiveType) {
        Intrinsics.checkNotNullParameter(leftPrimitive, "leftPrimitive");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(primitiveType, "primitiveType");
        KtSingleValueToken EQEQ = KtTokens.EQEQ;
        Intrinsics.checkNotNullExpressionValue(EQEQ, "EQEQ");
        super(leftPrimitive, right, primitiveType, EQEQ);
        Type type = leftPrimitive.type;
        Intrinsics.checkNotNullExpressionValue(type, "type");
        this.primitiveType = type;
        Type type2 = right.type;
        Intrinsics.checkNotNullExpressionValue(type2, "type");
        this.rightType = type2;
    }

    protected final Type getPrimitiveType() {
        return this.primitiveType;
    }

    protected final Type getRightType() {
        return this.rightType;
    }

    @Override // org.jetbrains.kotlin.codegen.BranchedValue
    public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
        int iIntValue;
        Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
        Intrinsics.checkNotNullParameter(v, "v");
        Label label = new Label();
        Label label2 = new Label();
        getArg1().put(this.primitiveType, v);
        StackValue arg2 = getArg2();
        Intrinsics.checkNotNull(arg2);
        arg2.put(this.rightType, v);
        AsmUtil.dup(v, this.rightType);
        jumpIfCanCompareTopWithPrimitive(v, label);
        InstructionAdapter instructionAdapter = v;
        AsmUtil.pop(instructionAdapter, this.rightType);
        AsmUtil.pop(instructionAdapter, this.primitiveType);
        if (jumpIfFalse) {
            v.goTo(jumpLabel);
        } else {
            v.goTo(label2);
        }
        v.mark(label);
        coerceRightToPrimitive(v);
        if (jumpIfFalse) {
            iIntValue = getOpcode();
        } else {
            Integer num = BranchedValue.INSTANCE.getNegatedOperations().get(Integer.valueOf(getOpcode()));
            Intrinsics.checkNotNull(num);
            iIntValue = num.intValue();
        }
        v.visitJumpInsn(patchOpcode(iIntValue, v), jumpLabel);
        v.mark(label2);
    }
}
