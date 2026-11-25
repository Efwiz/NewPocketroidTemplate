package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BoxedVsPrimitiveBranchedValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0014R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SafeCallToPrimitiveEquality;", "Lorg/jetbrains/kotlin/codegen/SafeCallFusedWithPrimitiveEqualityBase;", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "operandType", "Lorg/jetbrains/org/objectweb/asm/Type;", "left", "Lorg/jetbrains/kotlin/codegen/StackValue;", "right", "safeReceiverType", "safeReceiverIsNull", "Lorg/jetbrains/org/objectweb/asm/Label;", "(Lcom/intellij/psi/tree/IElementType;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Label;)V", "cleanupOnNullReceiver", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SafeCallToPrimitiveEquality extends SafeCallFusedWithPrimitiveEqualityBase {
    private final Label safeReceiverIsNull;
    private final Type safeReceiverType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SafeCallToPrimitiveEquality(IElementType opToken, Type operandType, StackValue left, StackValue right, Type safeReceiverType, Label safeReceiverIsNull) {
        super(opToken, operandType, left, right);
        Intrinsics.checkNotNullParameter(opToken, "opToken");
        Intrinsics.checkNotNullParameter(operandType, "operandType");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(safeReceiverType, "safeReceiverType");
        Intrinsics.checkNotNullParameter(safeReceiverIsNull, "safeReceiverIsNull");
        this.safeReceiverType = safeReceiverType;
        this.safeReceiverIsNull = safeReceiverIsNull;
    }

    @Override // org.jetbrains.kotlin.codegen.SafeCallFusedWithPrimitiveEqualityBase
    protected void cleanupOnNullReceiver(InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        v.mark(this.safeReceiverIsNull);
        AsmUtil.pop(v, this.safeReceiverType);
    }
}
