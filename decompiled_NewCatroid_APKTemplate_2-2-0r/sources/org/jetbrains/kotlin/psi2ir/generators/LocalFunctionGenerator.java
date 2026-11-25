package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;

/* JADX INFO: compiled from: LocalFunctionGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/LocalFunctionGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "generateFunction", "Lorg/jetbrains/kotlin/ir/IrStatement;", "ktFun", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "generateFunctionDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "generateLambda", "ktLambda", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalFunctionGenerator extends StatementGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalFunctionGenerator(StatementGenerator statementGenerator) {
        super(statementGenerator);
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
    }

    public final IrStatement generateLambda(KtLambdaExpression ktLambda) {
        Intrinsics.checkNotNullParameter(ktLambda, "ktLambda");
        KtFunctionLiteral functionLiteral = ktLambda.getFunctionLiteral();
        Intrinsics.checkNotNullExpressionValue(functionLiteral, "getFunctionLiteral(...)");
        KtLambdaExpression ktLambdaExpression = ktLambda;
        return new IrFunctionExpressionImpl(ktLambda.getStartOffset(), PsiUtilsKt.getEndOffset(ktLambda), toIrType(GeneratorKt.getTypeInferredByFrontendOrFail(this, ktLambdaExpression)), new FunctionGenerator(getContext()).generateLambdaFunctionDeclaration(functionLiteral, (getContext().getLanguageVersionSettings().supportsFeature(LanguageFeature.BreakContinueInInlineLambdas) && BindingContextUtilsKt.isInlineableFunctionLiteral(ktLambdaExpression, getContext().getBindingContext())) ? getStatementGenerator().getBodyGenerator() : null), IrStatementOrigin.LAMBDA.INSTANCE);
    }

    public final IrStatement generateFunction(KtNamedFunction ktFun) {
        Intrinsics.checkNotNullParameter(ktFun, "ktFun");
        IrSimpleFunction irSimpleFunctionGenerateFunctionDeclaration = generateFunctionDeclaration(ktFun);
        if (ktFun.get$name() != null) {
            return irSimpleFunctionGenerateFunctionDeclaration;
        }
        IrType irType = toIrType(GeneratorKt.getTypeInferredByFrontendOrFail(this, ktFun));
        KtNamedFunction ktNamedFunction = ktFun;
        return new IrFunctionExpressionImpl(PsiUtilsKt.getStartOffset(ktNamedFunction), PsiUtilsKt.getEndOffset(ktNamedFunction), irType, irSimpleFunctionGenerateFunctionDeclaration, IrStatementOrigin.ANONYMOUS_FUNCTION.INSTANCE);
    }

    private final IrSimpleFunction generateFunctionDeclaration(KtNamedFunction ktFun) {
        return new FunctionGenerator(getContext()).generateFunctionDeclaration(ktFun, (getContext().getLanguageVersionSettings().supportsFeature(LanguageFeature.BreakContinueInInlineLambdas) && BindingContextUtilsKt.isInlineableFunctionLiteral(ktFun, getContext().getBindingContext())) ? getStatementGenerator().getBodyGenerator() : null, IrDeclarationOrigin.LOCAL_FUNCTION.INSTANCE);
    }
}
