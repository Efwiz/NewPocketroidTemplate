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
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B'\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/Ieee754Equality;", "Lorg/jetbrains/kotlin/codegen/NumberLikeCompare;", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "left", "Lorg/jetbrains/kotlin/codegen/StackValue;", "right", "operandType", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/kotlin/codegen/FrameMap;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/org/objectweb/asm/Type;)V", "leftType", "rightType", "condJump", "", "jumpLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "jumpIfFalse", "", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Ieee754Equality extends NumberLikeCompare {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FrameMap frameMap;
    private final Type leftType;
    private final Type rightType;

    public /* synthetic */ Ieee754Equality(FrameMap frameMap, StackValue stackValue, StackValue stackValue2, Type type, DefaultConstructorMarker defaultConstructorMarker) {
        this(frameMap, stackValue, stackValue2, type);
    }

    @JvmStatic
    public static final BranchedValue create(FrameMap frameMap, StackValue stackValue, StackValue stackValue2, Type type, IElementType iElementType) {
        return INSTANCE.create(frameMap, stackValue, stackValue2, type, iElementType);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private Ieee754Equality(FrameMap frameMap, StackValue stackValue, StackValue stackValue2, Type type) {
        KtSingleValueToken EQEQ = KtTokens.EQEQ;
        Intrinsics.checkNotNullExpressionValue(EQEQ, "EQEQ");
        super(stackValue, stackValue2, type, EQEQ);
        this.frameMap = frameMap;
        if (!Intrinsics.areEqual(type, Type.FLOAT_TYPE)) {
            Intrinsics.areEqual(type, Type.DOUBLE_TYPE);
        }
        Type type2 = stackValue.type;
        Intrinsics.checkNotNullExpressionValue(type2, "type");
        this.leftType = type2;
        Type type3 = stackValue2.type;
        Intrinsics.checkNotNullExpressionValue(type3, "type");
        this.rightType = type3;
    }

    @Override // org.jetbrains.kotlin.codegen.BranchedValue
    public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
        StackValue.Local local;
        StackValue.Local local2;
        Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
        Intrinsics.checkNotNullParameter(v, "v");
        boolean zIsPrimitive = AsmUtil.isPrimitive(this.leftType);
        boolean zIsPrimitive2 = AsmUtil.isPrimitive(this.rightType);
        FrameMap frameMap = this.frameMap;
        StackValue arg1 = getArg1();
        Type type = this.leftType;
        if (arg1.canHaveSideEffects()) {
            StackValue.Local local3 = StackValue.local(frameMap.enterTemp(type), type);
            local3.store(arg1, v);
            Intrinsics.checkNotNullExpressionValue(local3, "apply(...)");
            local = local3;
        } else {
            local = arg1;
        }
        FrameMap frameMap2 = this.frameMap;
        StackValue arg2 = getArg2();
        Intrinsics.checkNotNull(arg2);
        Type type2 = this.rightType;
        if (arg2.canHaveSideEffects()) {
            StackValue.Local local4 = StackValue.local(frameMap2.enterTemp(type2), type2);
            local4.store(arg2, v);
            Intrinsics.checkNotNullExpressionValue(local4, "apply(...)");
            local2 = local4;
        } else {
            local2 = arg2;
        }
        Label label = new Label();
        Label label2 = new Label();
        if (!zIsPrimitive && !zIsPrimitive2) {
            Label label3 = new Label();
            local.put(this.leftType, v);
            v.ifnonnull(label3);
            local2.put(this.rightType, v);
            v.ifnonnull(jumpIfFalse ? jumpLabel : label);
            if (jumpIfFalse) {
                v.goTo(label);
            } else {
                v.goTo(jumpLabel);
            }
            v.mark(label3);
            local2.put(this.rightType, v);
            v.ifnull(jumpIfFalse ? jumpLabel : label);
        } else if (!zIsPrimitive) {
            local.put(this.leftType, v);
            v.ifnull(jumpIfFalse ? jumpLabel : label);
        } else if (!zIsPrimitive2) {
            local2.put(this.rightType, v);
            v.ifnull(jumpIfFalse ? jumpLabel : label);
        }
        v.mark(label2);
        local.put(getOperandType(), v);
        local2.put(getOperandType(), v);
        v.cmpg(getOperandType());
        if (jumpIfFalse) {
            v.ifne(jumpLabel);
        } else {
            v.ifeq(jumpLabel);
        }
        v.mark(label);
        if (!Intrinsics.areEqual(local2, arg2)) {
            frameMap2.leaveTemp(type2);
        }
        if (Intrinsics.areEqual(local, arg1)) {
            return;
        }
        frameMap.leaveTemp(type);
    }

    /* JADX INFO: compiled from: BoxedVsPrimitiveBranchedValues.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/Ieee754Equality$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "left", "Lorg/jetbrains/kotlin/codegen/StackValue;", "right", "comparisonType", "Lorg/jetbrains/org/objectweb/asm/Type;", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final BranchedValue create(FrameMap frameMap, StackValue left, StackValue right, Type comparisonType, IElementType opToken) {
            Intrinsics.checkNotNullParameter(frameMap, "frameMap");
            Intrinsics.checkNotNullParameter(left, "left");
            Intrinsics.checkNotNullParameter(right, "right");
            Intrinsics.checkNotNullParameter(comparisonType, "comparisonType");
            Intrinsics.checkNotNullParameter(opToken, "opToken");
            Ieee754Equality ieee754Equality = new Ieee754Equality(frameMap, left, right, comparisonType, null);
            if (Intrinsics.areEqual(opToken, KtTokens.EQEQ)) {
                return ieee754Equality;
            }
            if (Intrinsics.areEqual(opToken, KtTokens.EXCLEQ)) {
                return new Invert(ieee754Equality);
            }
            throw new AssertionError("Unexpected operator: " + opToken);
        }
    }
}
