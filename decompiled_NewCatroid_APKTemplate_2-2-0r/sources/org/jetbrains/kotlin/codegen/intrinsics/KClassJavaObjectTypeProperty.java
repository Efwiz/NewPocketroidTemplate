package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.BaseExpressionCodegenKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: KClassJavaObjectTypeProperty.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/KClassJavaObjectTypeProperty;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicPropertyGetter;", "()V", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/codegen/StackValue;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "receiver", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KClassJavaObjectTypeProperty extends IntrinsicPropertyGetter {
    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicPropertyGetter
    public StackValue generate(ResolvedCall<?> resolvedCall, final ExpressionCodegen codegen, Type returnType, StackValue receiver) {
        final KtExpression receiverExpression;
        final DoubleColonLHS doubleColonLHS;
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
        if (ktClassLiteralExpression == null || (receiverExpression = ktClassLiteralExpression.getReceiverExpression()) == null || (doubleColonLHS = (DoubleColonLHS) codegen.getBindingContext().get(BindingContext.DOUBLE_COLON_LHS, receiverExpression)) == null) {
            return null;
        }
        return StackValue.operation(returnType, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.KClassJavaObjectTypeProperty.generate.1
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
                    StackValue stackValueGen = codegen.gen(receiverExpression);
                    Type type = stackValueGen.type;
                    Intrinsics.checkNotNullExpressionValue(type, "type");
                    stackValueGen.put(type, instructionAdapter);
                    if (Intrinsics.areEqual(type, Type.VOID_TYPE)) {
                        instructionAdapter.aconst(AsmTypes.UNIT_TYPE);
                        return;
                    } else if (AsmUtil.isPrimitive(type) || AsmUtil.unboxPrimitiveTypeOrNull(type) != null) {
                        AsmUtil.pop(instructionAdapter, type);
                        instructionAdapter.aconst(AsmUtil.boxType(type));
                        return;
                    } else {
                        instructionAdapter.invokevirtual(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "getClass", "()Ljava/lang/Class;", false);
                        return;
                    }
                }
                if (TypeUtils.isTypeParameter(doubleColonLHS.getType())) {
                    TypeUtils.isReifiedTypeParameter(doubleColonLHS.getType());
                    BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(codegen, doubleColonLHS.getType(), ReifiedTypeInliner.OperationKind.JAVA_CLASS);
                }
                instructionAdapter.aconst(AsmUtil.boxType(codegen.mapTypeAsDeclaration(doubleColonLHS.getType())));
            }
        });
    }
}
