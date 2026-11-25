package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BoxedVsPrimitiveBranchedValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u0018\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/PrimitiveToObjectEquality;", "Lorg/jetbrains/kotlin/codegen/PrimitiveToSomethingEquality;", "leftPrimitive", "Lorg/jetbrains/kotlin/codegen/StackValue;", "rightObject", "primitiveType", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/org/objectweb/asm/Type;)V", "boxedType", "coerceRightToPrimitive", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "jumpIfCanCompareTopWithPrimitive", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/Label;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveToObjectEquality extends PrimitiveToSomethingEquality {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Type boxedType;

    public /* synthetic */ PrimitiveToObjectEquality(StackValue stackValue, StackValue stackValue2, Type type, DefaultConstructorMarker defaultConstructorMarker) {
        this(stackValue, stackValue2, type);
    }

    @JvmStatic
    public static final BranchedValue create(IElementType iElementType, StackValue stackValue, Type type, StackValue stackValue2, Type type2) {
        return INSTANCE.create(iElementType, stackValue, type, stackValue2, type2);
    }

    @JvmStatic
    public static final boolean isApplicable(IElementType iElementType, Type type, Type type2) {
        return INSTANCE.isApplicable(iElementType, type, type2);
    }

    private PrimitiveToObjectEquality(StackValue stackValue, StackValue stackValue2, Type type) {
        super(stackValue, stackValue2, type);
        Type typeBoxType = AsmUtil.boxType(type);
        Intrinsics.checkNotNullExpressionValue(typeBoxType, "boxType(...)");
        this.boxedType = typeBoxType;
    }

    @Override // org.jetbrains.kotlin.codegen.PrimitiveToSomethingEquality
    protected void jumpIfCanCompareTopWithPrimitive(InstructionAdapter v, Label label) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(label, "label");
        v.instanceOf(this.boxedType);
        v.ifne(label);
    }

    @Override // org.jetbrains.kotlin.codegen.PrimitiveToSomethingEquality
    protected void coerceRightToPrimitive(InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        StackValue.coerce(getRightType(), this.boxedType, v);
        StackValue.coerce(this.boxedType, getPrimitiveType(), v);
    }

    /* JADX INFO: compiled from: BoxedVsPrimitiveBranchedValues.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\nH\u0007J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/PrimitiveToObjectEquality$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "left", "Lorg/jetbrains/kotlin/codegen/StackValue;", "leftType", "Lorg/jetbrains/org/objectweb/asm/Type;", "right", "rightType", "isApplicable", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final BranchedValue create(IElementType opToken, StackValue left, Type leftType, StackValue right, Type rightType) {
            Intrinsics.checkNotNullParameter(opToken, "opToken");
            Intrinsics.checkNotNullParameter(left, "left");
            Intrinsics.checkNotNullParameter(leftType, "leftType");
            Intrinsics.checkNotNullParameter(right, "right");
            Intrinsics.checkNotNullParameter(rightType, "rightType");
            if (!isApplicable(opToken, leftType, rightType)) {
                throw new IllegalArgumentException("Not applicable for " + opToken + ", " + leftType + ", " + rightType);
            }
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (Intrinsics.areEqual(opToken, KtTokens.EQEQ)) {
                return new PrimitiveToObjectEquality(left, right, leftType, defaultConstructorMarker);
            }
            if (Intrinsics.areEqual(opToken, KtTokens.EXCLEQ)) {
                return new Invert(new PrimitiveToObjectEquality(left, right, leftType, defaultConstructorMarker));
            }
            throw new AssertionError("Unexpected opToken: " + opToken);
        }

        @JvmStatic
        public final boolean isApplicable(IElementType opToken, Type leftType, Type rightType) {
            Intrinsics.checkNotNullParameter(opToken, "opToken");
            Intrinsics.checkNotNullParameter(leftType, "leftType");
            Intrinsics.checkNotNullParameter(rightType, "rightType");
            return (Intrinsics.areEqual(opToken, KtTokens.EQEQ) || Intrinsics.areEqual(opToken, KtTokens.EXCLEQ)) && AsmUtil.isIntOrLongPrimitive(leftType) && rightType.getSort() == 10;
        }
    }
}
