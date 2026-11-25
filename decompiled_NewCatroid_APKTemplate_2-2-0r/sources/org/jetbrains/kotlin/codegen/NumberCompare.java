package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BranchedValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/NumberCompare;", "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "operandType", "Lorg/jetbrains/org/objectweb/asm/Type;", "left", "Lorg/jetbrains/kotlin/codegen/StackValue;", "right", "(Lcom/intellij/psi/tree/IElementType;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;)V", "patchOpcode", "", "opcode", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NumberCompare extends BranchedValue {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final IElementType opToken;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NumberCompare(IElementType opToken, Type operandType, StackValue left, StackValue right) {
        super(left, right, operandType, INSTANCE.getNumberCompareOpcode(opToken));
        Intrinsics.checkNotNullParameter(opToken, "opToken");
        Intrinsics.checkNotNullParameter(operandType, "operandType");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        this.opToken = opToken;
    }

    @Override // org.jetbrains.kotlin.codegen.BranchedValue
    protected int patchOpcode(int opcode, InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        return INSTANCE.patchOpcode(opcode, v, this.opToken, getOperandType());
    }

    /* JADX INFO: compiled from: BranchedValue.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J&\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/NumberCompare$Companion;", "", "()V", "getNumberCompareOpcode", "", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "patchOpcode", "opcode", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "operandType", "Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getNumberCompareOpcode(IElementType opToken) {
            Intrinsics.checkNotNullParameter(opToken, "opToken");
            if (Intrinsics.areEqual(opToken, KtTokens.EQEQ) ? true : Intrinsics.areEqual(opToken, KtTokens.EQEQEQ)) {
                return 154;
            }
            if (Intrinsics.areEqual(opToken, KtTokens.EXCLEQ) ? true : Intrinsics.areEqual(opToken, KtTokens.EXCLEQEQEQ)) {
                return 153;
            }
            if (Intrinsics.areEqual(opToken, KtTokens.GT)) {
                return 158;
            }
            if (Intrinsics.areEqual(opToken, KtTokens.GTEQ)) {
                return 155;
            }
            if (Intrinsics.areEqual(opToken, KtTokens.LT)) {
                return 156;
            }
            if (Intrinsics.areEqual(opToken, KtTokens.LTEQ)) {
                return 157;
            }
            throw new UnsupportedOperationException("Don't know how to generate this condJump: " + opToken);
        }

        public final int patchOpcode(int opcode, InstructionAdapter v, IElementType opToken, Type operandType) {
            Intrinsics.checkNotNullParameter(v, "v");
            Intrinsics.checkNotNullParameter(opToken, "opToken");
            Intrinsics.checkNotNullParameter(operandType, "operandType");
            if (Intrinsics.areEqual(operandType, Type.FLOAT_TYPE) ? true : Intrinsics.areEqual(operandType, Type.DOUBLE_TYPE)) {
                if (Intrinsics.areEqual(opToken, KtTokens.GT) || Intrinsics.areEqual(opToken, KtTokens.GTEQ)) {
                    v.cmpl(operandType);
                    return opcode;
                }
                v.cmpg(operandType);
                return opcode;
            }
            if (!Intrinsics.areEqual(operandType, Type.LONG_TYPE)) {
                return opcode + 6;
            }
            v.lcmp();
            return opcode;
        }
    }
}
