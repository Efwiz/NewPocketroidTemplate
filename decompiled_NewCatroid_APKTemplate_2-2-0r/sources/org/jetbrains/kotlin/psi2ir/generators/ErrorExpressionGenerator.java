package org.jetbrains.kotlin.psi2ir.generators;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorCallExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: ErrorExpressionGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J+\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\n0\u0013H\u0082\bJ\u000e\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/ErrorExpressionGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "ignoreErrors", "", "getIgnoreErrors", "()Z", "generateErrorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ktCall", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "generateErrorExpression", "ktElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "e", "", "body", "Lkotlin/Function0;", "generateErrorSimpleName", "ktName", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "getErrorExpressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "ktExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorExpressionGenerator extends StatementGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ErrorExpressionGenerator(StatementGenerator statementGenerator) {
        super(statementGenerator);
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getIgnoreErrors() {
        return getContext().getConfiguration().getIgnoreErrors();
    }

    private final KotlinType getErrorExpressionType(KtExpression ktExpression) {
        KotlinType typeInferredByFrontend = GeneratorKt.getTypeInferredByFrontend(this, ktExpression);
        return typeInferredByFrontend == null ? ErrorUtils.createErrorType(ErrorTypeKind.ERROR_EXPRESSION_TYPE, new String[0]) : typeInferredByFrontend;
    }

    public final IrExpression generateErrorExpression(KtElement ktElement, Throwable e) {
        ErrorType errorTypeCreateErrorType;
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        Intrinsics.checkNotNullParameter(e, "e");
        if (getIgnoreErrors()) {
            if (ktElement instanceof KtExpression) {
                errorTypeCreateErrorType = getErrorExpressionType((KtExpression) ktElement);
            } else {
                errorTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.TYPE_FOR_GENERATED_ERROR_EXPRESSION, new String[0]);
            }
            KtElement ktElement2 = ktElement;
            int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktElement2);
            int endOffset = PsiUtilsKt.getEndOffset(ktElement2);
            IrType irType = toIrType(errorTypeCreateErrorType);
            String message = e.getMessage();
            if (message == null) {
                message = ktElement.getText();
            }
            Intrinsics.checkNotNull(message);
            return new IrErrorExpressionImpl(startOffsetSkippingComments, endOffset, irType, message);
        }
        throw new ErrorExpressionException(ktElement, e);
    }

    public final IrExpression generateErrorCall(KtCallExpression ktCall) {
        Intrinsics.checkNotNullParameter(ktCall, "ktCall");
        if (getIgnoreErrors()) {
            IrType irType = toIrType(getErrorExpressionType(ktCall));
            KtCallExpression ktCallExpression = ktCall;
            int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktCallExpression);
            int endOffset = PsiUtilsKt.getEndOffset(ktCallExpression);
            String text = ktCall.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            IrErrorCallExpressionImpl irErrorCallExpressionImpl = new IrErrorCallExpressionImpl(startOffsetSkippingComments, endOffset, irType, text);
            PsiElement parent = ktCall.getLightParent();
            KtDotQualifiedExpression ktDotQualifiedExpression = parent instanceof KtDotQualifiedExpression ? (KtDotQualifiedExpression) parent : null;
            irErrorCallExpressionImpl.setExplicitReceiver(ktDotQualifiedExpression != null ? genExpr(ktDotQualifiedExpression.getReceiverExpression()) : null);
            List<KtValueArgument> valueArguments = ktCall.getValueArguments();
            Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
            List<KtLambdaArgument> lambdaArguments = ktCall.getLambdaArguments();
            Intrinsics.checkNotNullExpressionValue(lambdaArguments, "getLambdaArguments(...)");
            Iterator it = CollectionsKt.plus((Collection) valueArguments, (Iterable) lambdaArguments).iterator();
            while (it.hasNext()) {
                KtExpression argumentExpression = ((KtValueArgument) it.next()).getArgumentExpression();
                if (argumentExpression != null) {
                    irErrorCallExpressionImpl.addArgument(genExpr(argumentExpression));
                }
            }
            return irErrorCallExpressionImpl;
        }
        throw new ErrorExpressionException(ktCall, null);
    }

    public final IrExpression generateErrorSimpleName(KtSimpleNameExpression ktName) {
        Intrinsics.checkNotNullParameter(ktName, "ktName");
        IrExpression irExpressionGenExpr = null;
        if (getIgnoreErrors()) {
            IrType irType = toIrType(getErrorExpressionType(ktName));
            KtSimpleNameExpression ktSimpleNameExpression = ktName;
            int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktSimpleNameExpression);
            int endOffset = PsiUtilsKt.getEndOffset(ktSimpleNameExpression);
            String text = ktName.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            IrErrorCallExpressionImpl irErrorCallExpressionImpl = new IrErrorCallExpressionImpl(startOffsetSkippingComments, endOffset, irType, text);
            PsiElement parent = ktName.getLightParent();
            KtDotQualifiedExpression ktDotQualifiedExpression = parent instanceof KtDotQualifiedExpression ? (KtDotQualifiedExpression) parent : null;
            if (ktDotQualifiedExpression != null && !Intrinsics.areEqual(ktDotQualifiedExpression.getReceiverExpression(), ktName)) {
                irExpressionGenExpr = genExpr(ktDotQualifiedExpression.getReceiverExpression());
            }
            irErrorCallExpressionImpl.setExplicitReceiver(irExpressionGenExpr);
            return irErrorCallExpressionImpl;
        }
        throw new ErrorExpressionException(ktName, null);
    }
}
