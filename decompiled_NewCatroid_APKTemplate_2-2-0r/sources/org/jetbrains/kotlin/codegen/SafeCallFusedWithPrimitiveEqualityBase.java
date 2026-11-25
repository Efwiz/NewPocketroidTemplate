package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BoxedVsPrimitiveBranchedValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH$J \u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000bH\u0016J\"\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SafeCallFusedWithPrimitiveEqualityBase;", "Lorg/jetbrains/kotlin/codegen/NumberLikeCompare;", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "operandType", "Lorg/jetbrains/org/objectweb/asm/Type;", "left", "Lorg/jetbrains/kotlin/codegen/StackValue;", "right", "(Lcom/intellij/psi/tree/IElementType;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;)V", "trueIfEqual", "", "cleanupOnNullReceiver", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "condJump", "jumpLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfFalse", "putSelector", ModuleXmlParser.TYPE, "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SafeCallFusedWithPrimitiveEqualityBase extends NumberLikeCompare {
    private final boolean trueIfEqual;

    protected abstract void cleanupOnNullReceiver(InstructionAdapter v);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SafeCallFusedWithPrimitiveEqualityBase(IElementType opToken, Type operandType, StackValue left, StackValue right) {
        super(left, right, operandType, opToken);
        Intrinsics.checkNotNullParameter(opToken, "opToken");
        Intrinsics.checkNotNullParameter(operandType, "operandType");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        this.trueIfEqual = Intrinsics.areEqual(opToken, KtTokens.EQEQ) || Intrinsics.areEqual(opToken, KtTokens.EQEQEQ);
    }

    @Override // org.jetbrains.kotlin.codegen.BranchedValue
    public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
        int iIntValue;
        Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
        Intrinsics.checkNotNullParameter(v, "v");
        Label label = new Label();
        getArg1().put(getOperandType(), v);
        StackValue arg2 = getArg2();
        Intrinsics.checkNotNull(arg2);
        arg2.put(getOperandType(), v);
        if (jumpIfFalse) {
            iIntValue = getOpcode();
        } else {
            Integer num = BranchedValue.INSTANCE.getNegatedOperations().get(Integer.valueOf(getOpcode()));
            Intrinsics.checkNotNull(num);
            iIntValue = num.intValue();
        }
        v.visitJumpInsn(patchOpcode(iIntValue, v), jumpLabel);
        v.goTo(label);
        cleanupOnNullReceiver(v);
        if (jumpIfFalse == this.trueIfEqual) {
            v.goTo(jumpLabel);
        }
        v.mark(label);
    }

    @Override // org.jetbrains.kotlin.codegen.BranchedValue, org.jetbrains.kotlin.codegen.StackValue
    public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(v, "v");
        Label label = new Label();
        Label label2 = new Label();
        getArg1().put(getOperandType(), v);
        StackValue arg2 = getArg2();
        Intrinsics.checkNotNull(arg2);
        arg2.put(getOperandType(), v);
        v.visitJumpInsn(patchOpcode(getOpcode(), v), label);
        if (!this.trueIfEqual) {
            Label label3 = new Label();
            v.goTo(label3);
            cleanupOnNullReceiver(v);
            v.mark(label3);
        }
        v.iconst(1);
        v.goTo(label2);
        if (this.trueIfEqual) {
            cleanupOnNullReceiver(v);
        }
        v.mark(label);
        v.iconst(0);
        v.mark(label2);
        coerceTo(type, kotlinType, v);
    }
}
