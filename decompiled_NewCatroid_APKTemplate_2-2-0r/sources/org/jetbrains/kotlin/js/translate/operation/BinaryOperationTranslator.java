package org.jetbrains.kotlin.js.translate.operation;

import java.util.Collections;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsic;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: loaded from: classes5.dex */
public final class BinaryOperationTranslator extends AbstractTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KtBinaryExpression expression;
    private final KtExpression leftKtExpression;
    private final CallableDescriptor operationDescriptor;
    private final KtToken operationToken;
    private final KtExpression rightKtExpression;

    /* JADX WARN: Code duplicated, block: B:35:0x0067  */
    /* JADX WARN: Code duplicated, block: B:9:0x0014  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 5 && i != 18 && i != 19) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 5 && i != 18 && i != 19) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
            case 7:
                objArr[0] = "context";
                break;
            case 2:
            case 5:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
                objArr[0] = "org/jetbrains/kotlin/js/translate/operation/BinaryOperationTranslator";
                break;
            case 3:
            case 6:
            default:
                objArr[0] = "expression";
                break;
            case 17:
                objArr[0] = "result";
                break;
        }
        if (i == 2) {
            objArr[1] = "translate";
        } else if (i == 5) {
            objArr[1] = "translateAsOverloadedCall";
        } else if (i != 18 && i != 19) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                    objArr[1] = "translate";
                    break;
                case 12:
                case 13:
                    objArr[1] = "translateElvis";
                    break;
                case 14:
                    objArr[1] = "translateAsUnOverloadableBinaryOperation";
                    break;
                case 15:
                case 16:
                    objArr[1] = "getReceiver";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/js/translate/operation/BinaryOperationTranslator";
                    break;
            }
        } else {
            objArr[1] = "mayBeWrapWithNegation";
        }
        switch (i) {
            case 2:
            case 5:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
                break;
            case 3:
            case 4:
                objArr[2] = "translateAsOverloadedCall";
                break;
            case 6:
            case 7:
                objArr[2] = "<init>";
                break;
            case 17:
                objArr[2] = "mayBeWrapWithNegation";
                break;
            default:
                objArr[2] = "translate";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5 && i != 18 && i != 19) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public static JsExpression translate(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        JsExpression jsExpressionSource = new BinaryOperationTranslator(ktBinaryExpression, translationContext).translate().source((Object) ktBinaryExpression);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(2);
        }
        return jsExpressionSource;
    }

    static JsExpression translateAsOverloadedCall(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(3);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(4);
        }
        JsExpression jsExpressionSource = new BinaryOperationTranslator(ktBinaryExpression, translationContext).translateAsOverloadedBinaryOperation().source((Object) ktBinaryExpression);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(5);
        }
        return jsExpressionSource;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private BinaryOperationTranslator(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        super(translationContext);
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(6);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(7);
        }
        this.expression = ktBinaryExpression;
        this.leftKtExpression = ktBinaryExpression.getLeft();
        this.rightKtExpression = ktBinaryExpression.getRight();
        this.operationToken = PsiUtils.getOperationToken(ktBinaryExpression);
        this.operationDescriptor = BindingUtils.getCallableDescriptorForOperationExpression(bindingContext(), ktBinaryExpression);
    }

    private JsExpression translate() {
        JsExpression jsExpressionTryApplyIntrinsic = tryApplyIntrinsic();
        if (jsExpressionTryApplyIntrinsic != null) {
            if (jsExpressionTryApplyIntrinsic == null) {
                $$$reportNull$$$0(8);
            }
            return jsExpressionTryApplyIntrinsic;
        }
        if (this.operationToken == KtTokens.ELVIS) {
            return translateElvis();
        }
        if (AssignmentTranslator.isAssignmentOperator(this.operationToken)) {
            JsExpression jsExpressionTranslate = AssignmentTranslator.translate(this.expression, context());
            if (jsExpressionTranslate == null) {
                $$$reportNull$$$0(9);
            }
            return jsExpressionTranslate;
        }
        if (isNotOverloadable()) {
            return translateAsUnOverloadableBinaryOperation();
        }
        if (CompareToTranslator.isCompareToCall(this.operationToken, this.operationDescriptor)) {
            JsExpression jsExpressionTranslate2 = CompareToTranslator.translate(this.expression, context());
            if (jsExpressionTranslate2 == null) {
                $$$reportNull$$$0(10);
            }
            return jsExpressionTranslate2;
        }
        if (isEquals()) {
            JsExpression jsExpressionTranslateEquals = translateEquals();
            if (jsExpressionTranslateEquals == null) {
                $$$reportNull$$$0(11);
            }
            return jsExpressionTranslateEquals;
        }
        return translateAsOverloadedBinaryOperation();
    }

    private JsExpression translateElvis() {
        JsIf jsIfNewJsIf;
        JsExpression jsExpressionCacheExpressionIfNeeded;
        KotlinType type = context().bindingContext().getType(this.expression);
        JsExpression jsExpressionCoerce = TranslationUtils.coerce(context(), Translation.translateAsExpression(this.leftKtExpression, context()), TypeUtilsKt.makeNullable(type));
        JsBlock jsBlock = new JsBlock();
        JsExpression jsExpressionCoerce2 = TranslationUtils.coerce(context(), Translation.translateAsExpression(this.rightKtExpression, context(), jsBlock), type);
        if (jsBlock.isEmpty()) {
            JsConditional jsConditionalNotNullConditional = TranslationUtils.notNullConditional(jsExpressionCoerce, jsExpressionCoerce2, context());
            if (jsConditionalNotNullConditional == null) {
                $$$reportNull$$$0(12);
            }
            return jsConditionalNotNullConditional;
        }
        if (BindingContextUtilsKt.isUsedAsExpression(this.expression, context().bindingContext())) {
            jsExpressionCacheExpressionIfNeeded = context().cacheExpressionIfNeeded(jsExpressionCoerce);
            JsBinaryOperation jsBinaryOperationIsNullCheck = TranslationUtils.isNullCheck(jsExpressionCacheExpressionIfNeeded);
            jsBlock.getStatements().add(JsAstUtils.assignment(jsExpressionCacheExpressionIfNeeded, jsExpressionCoerce2).makeStmt());
            jsIfNewJsIf = JsAstUtils.newJsIf(jsBinaryOperationIsNullCheck, jsBlock);
        } else {
            JsNullLiteral jsNullLiteral = new JsNullLiteral();
            JsBinaryOperation jsBinaryOperationIsNullCheck2 = TranslationUtils.isNullCheck(jsExpressionCoerce);
            jsBlock.getStatements().add(jsExpressionCoerce2.makeStmt());
            jsIfNewJsIf = JsAstUtils.newJsIf(jsBinaryOperationIsNullCheck2, jsBlock);
            jsExpressionCacheExpressionIfNeeded = jsNullLiteral;
        }
        jsIfNewJsIf.setSource(this.expression);
        context().addStatementToCurrentBlock(jsIfNewJsIf);
        if (jsExpressionCacheExpressionIfNeeded == null) {
            $$$reportNull$$$0(13);
        }
        return jsExpressionCacheExpressionIfNeeded;
    }

    private JsExpression tryApplyIntrinsic() {
        BinaryOperationIntrinsic binaryOperationIntrinsic = context().intrinsics().getBinaryOperationIntrinsic(this.expression, context());
        if (binaryOperationIntrinsic == null) {
            return null;
        }
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(this.leftKtExpression, context());
        JsBlock jsBlock = new JsBlock();
        JsExpression jsExpressionTranslateAsExpression2 = Translation.translateAsExpression(this.rightKtExpression, context(), jsBlock);
        if (jsBlock.isEmpty()) {
            return binaryOperationIntrinsic.invoke(this.expression, jsExpressionTranslateAsExpression, jsExpressionTranslateAsExpression2, context());
        }
        JsExpression jsExpressionCacheExpressionIfNeeded = context().cacheExpressionIfNeeded(jsExpressionTranslateAsExpression);
        context().addStatementsToCurrentBlockFrom(jsBlock);
        return binaryOperationIntrinsic.invoke(this.expression, jsExpressionCacheExpressionIfNeeded, jsExpressionTranslateAsExpression2, context());
    }

    private boolean isNotOverloadable() {
        return OperatorConventions.NOT_OVERLOADABLE.contains(this.operationToken);
    }

    private JsExpression translateAsUnOverloadableBinaryOperation() {
        JsIf jsIfNewJsIf;
        JsBinaryOperator binaryOperator = OperatorTable.getBinaryOperator(this.operationToken);
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(this.leftKtExpression, context());
        JsBlock jsBlock = new JsBlock();
        JsExpression jsExpressionTranslateAsExpression2 = Translation.translateAsExpression(this.rightKtExpression, context(), jsBlock);
        if (jsBlock.isEmpty()) {
            return new JsBinaryOperation(binaryOperator, jsExpressionTranslateAsExpression, jsExpressionTranslateAsExpression2);
        }
        if (OperatorConventions.IDENTITY_EQUALS_OPERATIONS.contains(this.operationToken)) {
            context().addStatementsToCurrentBlockFrom(jsBlock);
            return new JsBinaryOperation(binaryOperator, jsExpressionTranslateAsExpression, jsExpressionTranslateAsExpression2);
        }
        boolean zEquals = this.operationToken.equals(KtTokens.OROR);
        JsExpression jsExpressionSource = new JsBooleanLiteral(zEquals).source((Object) this.rightKtExpression);
        if (zEquals) {
            jsExpressionTranslateAsExpression = JsAstUtils.not(jsExpressionTranslateAsExpression);
        }
        if (BindingContextUtilsKt.isUsedAsExpression(this.expression, context().bindingContext())) {
            if (!(jsExpressionTranslateAsExpression2 instanceof JsNameRef)) {
                JsNameRef jsNameRefReference = context().declareTemporary(null, this.rightKtExpression).reference();
                jsBlock.getStatements().add(JsAstUtils.asSyntheticStatement(JsAstUtils.assignment(jsNameRefReference.deepCopy(), jsExpressionTranslateAsExpression2).source((Object) this.rightKtExpression)));
                jsExpressionTranslateAsExpression2 = jsNameRefReference;
            }
            jsIfNewJsIf = JsAstUtils.newJsIf(jsExpressionTranslateAsExpression, jsBlock, JsAstUtils.asSyntheticStatement(JsAstUtils.assignment(jsExpressionTranslateAsExpression2.deepCopy(), jsExpressionSource).source((Object) this.rightKtExpression)));
            MetadataProperties.setSynthetic(jsIfNewJsIf, true);
        } else {
            jsIfNewJsIf = JsAstUtils.newJsIf(jsExpressionTranslateAsExpression, jsBlock);
            jsExpressionTranslateAsExpression2 = new JsNullLiteral();
        }
        jsIfNewJsIf.source(this.expression);
        context().addStatementToCurrentBlock(jsIfNewJsIf);
        if (jsExpressionTranslateAsExpression2 == null) {
            $$$reportNull$$$0(14);
        }
        return jsExpressionTranslateAsExpression2;
    }

    private boolean isEquals() {
        return this.operationToken == KtTokens.EQEQ || this.operationToken == KtTokens.EXCLEQ;
    }

    private JsExpression translateEquals() {
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(this.leftKtExpression, context());
        JsExpression jsExpressionTranslateAsExpression2 = Translation.translateAsExpression(this.rightKtExpression, context());
        if ((jsExpressionTranslateAsExpression instanceof JsNullLiteral) || (jsExpressionTranslateAsExpression2 instanceof JsNullLiteral)) {
            return new JsBinaryOperation(this.operationToken == KtTokens.EXCLEQ ? JsBinaryOperator.NEQ : JsBinaryOperator.EQ, jsExpressionTranslateAsExpression, jsExpressionTranslateAsExpression2);
        }
        KotlinType type = context().bindingContext().getType(this.leftKtExpression);
        KotlinType type2 = context().bindingContext().getType(this.rightKtExpression);
        if ((type != null && TypeUtils.isNullableType(type)) || (type2 != null && TypeUtils.isNullableType(type2))) {
            return mayBeWrapWithNegation(TopLevelFIF.KOTLIN_EQUALS.apply(jsExpressionTranslateAsExpression, Collections.singletonList(jsExpressionTranslateAsExpression2), context()));
        }
        return translateAsOverloadedBinaryOperation();
    }

    private JsExpression translateAsOverloadedBinaryOperation() {
        return mayBeWrapWithNegation(CallTranslator.translate(context(), CallUtilKt.getFunctionResolvedCallWithAssert(this.expression, bindingContext()), getReceiver()));
    }

    private JsExpression getReceiver() {
        if (KtPsiUtil.isInOrNotInOperation(this.expression)) {
            JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(this.rightKtExpression, context());
            if (jsExpressionTranslateAsExpression == null) {
                $$$reportNull$$$0(15);
            }
            return jsExpressionTranslateAsExpression;
        }
        JsExpression jsExpressionTranslateAsExpression2 = Translation.translateAsExpression(this.leftKtExpression, context());
        if (jsExpressionTranslateAsExpression2 == null) {
            $$$reportNull$$$0(16);
        }
        return jsExpressionTranslateAsExpression2;
    }

    private JsExpression mayBeWrapWithNegation(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(17);
        }
        if (!PsiUtils.isNegatedOperation(this.expression)) {
            if (jsExpression == null) {
                $$$reportNull$$$0(19);
            }
            return jsExpression;
        }
        JsPrefixOperation jsPrefixOperationNot = JsAstUtils.not(jsExpression);
        if (jsPrefixOperationNot == null) {
            $$$reportNull$$$0(18);
        }
        return jsPrefixOperationNot;
    }
}
