package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BoxedVsPrimitiveBranchedValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B'\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J=\u0010\u0013\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\f0\u0015H\u0082\bR\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/BoxedToPrimitiveEquality;", "Lorg/jetbrains/kotlin/codegen/NumberLikeCompare;", "leftBoxed", "Lorg/jetbrains/kotlin/codegen/StackValue;", "rightPrimitive", "primitiveType", "Lorg/jetbrains/org/objectweb/asm/Type;", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "(Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/codegen/FrameMap;)V", "boxedType", "condJump", "", "jumpLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "jumpIfFalse", "", "doJump", "putArg1", "Lkotlin/Function0;", "putArg2", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BoxedToPrimitiveEquality extends NumberLikeCompare {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Type boxedType;
    private final FrameMap frameMap;

    public /* synthetic */ BoxedToPrimitiveEquality(StackValue stackValue, StackValue stackValue2, Type type, FrameMap frameMap, DefaultConstructorMarker defaultConstructorMarker) {
        this(stackValue, stackValue2, type, frameMap);
    }

    @JvmStatic
    public static final BranchedValue create(IElementType iElementType, StackValue stackValue, Type type, StackValue stackValue2, Type type2, FrameMap frameMap) {
        return INSTANCE.create(iElementType, stackValue, type, stackValue2, type2, frameMap);
    }

    @JvmStatic
    public static final boolean isApplicable(IElementType iElementType, Type type, Type type2) {
        return INSTANCE.isApplicable(iElementType, type, type2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private BoxedToPrimitiveEquality(StackValue stackValue, StackValue stackValue2, Type type, FrameMap frameMap) {
        KtSingleValueToken EQEQ = KtTokens.EQEQ;
        Intrinsics.checkNotNullExpressionValue(EQEQ, "EQEQ");
        super(stackValue, stackValue2, type, EQEQ);
        this.frameMap = frameMap;
        Type type2 = getArg1().type;
        Intrinsics.checkNotNullExpressionValue(type2, "type");
        this.boxedType = type2;
    }

    @Override // org.jetbrains.kotlin.codegen.BranchedValue
    public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
        int iIntValue;
        int iIntValue2;
        Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
        Intrinsics.checkNotNullParameter(v, "v");
        StackValue arg2 = getArg2();
        Intrinsics.checkNotNull(arg2);
        if (arg2.canHaveSideEffects()) {
            int iEnterTemp = this.frameMap.enterTemp(getOperandType());
            Label label = new Label();
            Label label2 = new Label();
            getArg1().put(this.boxedType, v);
            getArg2().put(getOperandType(), v);
            v.store(iEnterTemp, getOperandType());
            AsmUtil.dup(v, this.boxedType);
            v.ifnonnull(label);
            AsmUtil.pop(v, this.boxedType);
            if (jumpIfFalse) {
                v.goTo(jumpLabel);
            } else {
                v.goTo(label2);
            }
            v.mark(label);
            StackValue.coerce(this.boxedType, getOperandType(), v);
            v.load(iEnterTemp, getOperandType());
            if (jumpIfFalse) {
                iIntValue2 = getOpcode();
            } else {
                Integer num = BranchedValue.INSTANCE.getNegatedOperations().get(Integer.valueOf(getOpcode()));
                Intrinsics.checkNotNull(num);
                iIntValue2 = num.intValue();
            }
            v.visitJumpInsn(patchOpcode(iIntValue2, v), jumpLabel);
            v.mark(label2);
            this.frameMap.leaveTemp(getOperandType());
            return;
        }
        Label label3 = new Label();
        Label label4 = new Label();
        getArg1().put(this.boxedType, v);
        AsmUtil.dup(v, this.boxedType);
        v.ifnonnull(label3);
        AsmUtil.pop(v, this.boxedType);
        if (jumpIfFalse) {
            v.goTo(jumpLabel);
        } else {
            v.goTo(label4);
        }
        v.mark(label3);
        StackValue.coerce(this.boxedType, getOperandType(), v);
        getArg2().put(getOperandType(), v);
        if (jumpIfFalse) {
            iIntValue = getOpcode();
        } else {
            Integer num2 = BranchedValue.INSTANCE.getNegatedOperations().get(Integer.valueOf(getOpcode()));
            Intrinsics.checkNotNull(num2);
            iIntValue = num2.intValue();
        }
        v.visitJumpInsn(patchOpcode(iIntValue, v), jumpLabel);
        v.mark(label4);
    }

    /* JADX INFO: compiled from: BoxedVsPrimitiveBranchedValues.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J8\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0007J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/BoxedToPrimitiveEquality$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "left", "Lorg/jetbrains/kotlin/codegen/StackValue;", "leftType", "Lorg/jetbrains/org/objectweb/asm/Type;", "right", "rightType", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "isApplicable", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final BranchedValue create(IElementType opToken, StackValue left, Type leftType, StackValue right, Type rightType, FrameMap frameMap) {
            Intrinsics.checkNotNullParameter(opToken, "opToken");
            Intrinsics.checkNotNullParameter(left, "left");
            Intrinsics.checkNotNullParameter(leftType, "leftType");
            Intrinsics.checkNotNullParameter(right, "right");
            Intrinsics.checkNotNullParameter(rightType, "rightType");
            Intrinsics.checkNotNullParameter(frameMap, "frameMap");
            if (!isApplicable(opToken, leftType, rightType)) {
                throw new IllegalArgumentException("Not applicable for " + opToken + ", " + leftType + ", " + rightType);
            }
            if (Intrinsics.areEqual(opToken, KtTokens.EQEQ)) {
                return new BoxedToPrimitiveEquality(left, right, rightType, frameMap, null);
            }
            if (Intrinsics.areEqual(opToken, KtTokens.EXCLEQ)) {
                return new Invert(new BoxedToPrimitiveEquality(left, right, rightType, frameMap, null));
            }
            throw new AssertionError("Unexpected opToken: " + opToken);
        }

        @JvmStatic
        public final boolean isApplicable(IElementType opToken, Type leftType, Type rightType) {
            Intrinsics.checkNotNullParameter(opToken, "opToken");
            Intrinsics.checkNotNullParameter(leftType, "leftType");
            Intrinsics.checkNotNullParameter(rightType, "rightType");
            return (Intrinsics.areEqual(opToken, KtTokens.EQEQ) || Intrinsics.areEqual(opToken, KtTokens.EXCLEQ)) && AsmUtil.isIntOrLongPrimitive(rightType) && AsmUtil.isBoxedTypeOf(leftType, rightType);
        }
    }
}
