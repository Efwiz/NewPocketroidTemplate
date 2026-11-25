package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: KClassJavaPrimitiveTypeProperty.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/KClassJavaPrimitiveTypeProperty;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicPropertyGetter;", "()V", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/codegen/StackValue;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "receiver", "isPrimitiveOrWrapper", "", "lhsType", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KClassJavaPrimitiveTypeProperty extends IntrinsicPropertyGetter {
    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicPropertyGetter
    public StackValue generate(ResolvedCall<?> resolvedCall, final ExpressionCodegen codegen, Type returnType, StackValue receiver) {
        final KtExpression receiverExpression;
        final DoubleColonLHS doubleColonLHS;
        KotlinType type;
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNull(resolvedCall);
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        ExpressionReceiver expressionReceiver = extensionReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) extensionReceiver : null;
        if (expressionReceiver == null) {
            return null;
        }
        KtExpression expression = expressionReceiver.getExpression();
        KtClassLiteralExpression ktClassLiteralExpression = expression instanceof KtClassLiteralExpression ? (KtClassLiteralExpression) expression : null;
        if (ktClassLiteralExpression == null || (receiverExpression = ktClassLiteralExpression.getReceiverExpression()) == null || (doubleColonLHS = (DoubleColonLHS) codegen.getBindingContext().get(BindingContext.DOUBLE_COLON_LHS, receiverExpression)) == null || TypeUtils.isTypeParameter(doubleColonLHS.getType())) {
            return null;
        }
        if ((doubleColonLHS instanceof DoubleColonLHS.Expression) && !((DoubleColonLHS.Expression) doubleColonLHS).getIsObjectQualifier() && ((type = codegen.getBindingContext().getType(receiverExpression)) == null || !KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType(type))) {
            return null;
        }
        final Type typeAsmType = codegen.asmType(doubleColonLHS.getType());
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        return StackValue.operation(returnType, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.KClassJavaPrimitiveTypeProperty.generate.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                invoke2(instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstructionAdapter instructionAdapter) {
                DoubleColonLHS doubleColonLHS2 = doubleColonLHS;
                if ((doubleColonLHS2 instanceof DoubleColonLHS.Expression) && !((DoubleColonLHS.Expression) doubleColonLHS2).getIsObjectQualifier()) {
                    codegen.gen(receiverExpression).put(typeAsmType, instructionAdapter);
                    AsmUtil.pop(instructionAdapter, typeAsmType);
                    if (InlineClassesUtilsKt.isInlineClassType(doubleColonLHS.getType())) {
                        instructionAdapter.aconst(null);
                        return;
                    } else {
                        instructionAdapter.getstatic(AsmUtil.boxType(typeAsmType).getInternalName(), "TYPE", "Ljava/lang/Class;");
                        return;
                    }
                }
                if (!InlineClassesUtilsKt.isInlineClassType(doubleColonLHS.getType()) && this.isPrimitiveOrWrapper(typeAsmType)) {
                    instructionAdapter.getstatic(AsmUtil.boxType(typeAsmType).getInternalName(), "TYPE", "Ljava/lang/Class;");
                } else {
                    instructionAdapter.aconst(null);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isPrimitiveOrWrapper(Type lhsType) {
        return AsmUtil.isPrimitive(lhsType) || AsmUtil.unboxPrimitiveTypeOrNull(lhsType) != null || Intrinsics.areEqual(AsmTypes.VOID_WRAPPER_TYPE, lhsType);
    }
}
