package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BoxedVsPrimitiveBranchedValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/NumberLikeCompare;", "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "left", "Lorg/jetbrains/kotlin/codegen/StackValue;", "right", "operandType", "Lorg/jetbrains/org/objectweb/asm/Type;", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "(Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/org/objectweb/asm/Type;Lcom/intellij/psi/tree/IElementType;)V", "patchOpcode", "", "opcode", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class NumberLikeCompare extends BranchedValue {
    private final IElementType opToken;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NumberLikeCompare(StackValue left, StackValue right, Type operandType, IElementType opToken) {
        super(left, right, operandType, NumberCompare.INSTANCE.getNumberCompareOpcode(opToken));
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(operandType, "operandType");
        Intrinsics.checkNotNullParameter(opToken, "opToken");
        this.opToken = opToken;
    }

    @Override // org.jetbrains.kotlin.codegen.BranchedValue
    protected int patchOpcode(int opcode, InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        return NumberCompare.INSTANCE.patchOpcode(opcode, v, this.opToken, getOperandType());
    }
}
