package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BinaryOp.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/BinaryOp;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "opcode", "", "(I)V", "shift", "", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryOp extends IntrinsicMethod {
    private final int opcode;

    public BinaryOp(int i) {
        this.opcode = i;
    }

    private final boolean shift() {
        int i = this.opcode;
        return i == 120 || i == 122 || i == 124;
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Type type;
        final Type typeNumberFunctionOperandType;
        Type type2;
        Type INT_TYPE;
        Intrinsics.checkNotNullParameter(method, "method");
        final Type returnType = method.getReturnType();
        method.getValueParameters().size();
        if (!Intrinsics.areEqual(method.getOwner(), Type.CHAR_TYPE)) {
            typeNumberFunctionOperandType = AsmUtil.numberFunctionOperandType(returnType);
            Intrinsics.checkNotNullExpressionValue(typeNumberFunctionOperandType, "numberFunctionOperandType(...)");
            if (shift()) {
                INT_TYPE = Type.INT_TYPE;
                Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
            } else {
                INT_TYPE = typeNumberFunctionOperandType;
            }
            type2 = typeNumberFunctionOperandType;
            type = INT_TYPE;
        } else {
            Type CHAR_TYPE = Type.CHAR_TYPE;
            Intrinsics.checkNotNullExpressionValue(CHAR_TYPE, "CHAR_TYPE");
            Type asmType = method.getValueParameters().get(0).getAsmType();
            Intrinsics.checkNotNullExpressionValue(asmType, "getAsmType(...)");
            Type typeNumberFunctionOperandType2 = AsmUtil.numberFunctionOperandType(returnType);
            Intrinsics.checkNotNullExpressionValue(typeNumberFunctionOperandType2, "numberFunctionOperandType(...)");
            type = asmType;
            typeNumberFunctionOperandType = typeNumberFunctionOperandType2;
            type2 = CHAR_TYPE;
        }
        return IntrinsicCallableKt.createBinaryIntrinsicCallable$default(returnType, type, type2, null, new Function2<IntrinsicCallable, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.BinaryOp.toCallable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IntrinsicCallable intrinsicCallable, InstructionAdapter instructionAdapter) {
                invoke2(intrinsicCallable, instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IntrinsicCallable createBinaryIntrinsicCallable, InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(createBinaryIntrinsicCallable, "$this$createBinaryIntrinsicCallable");
                Intrinsics.checkNotNullParameter(v, "v");
                v.visitInsn(returnType.getOpcode(this.opcode));
                StackValue.coerce(typeNumberFunctionOperandType, returnType, v);
            }
        }, 8, null);
    }
}
