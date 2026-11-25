package org.jetbrains.kotlin.js.translate.operation;

import java.util.Arrays;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.context.TemporaryVariable;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.reference.AccessTranslationUtils;
import org.jetbrains.kotlin.js.translate.reference.AccessTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: loaded from: classes5.dex */
public abstract class IncrementTranslator extends AbstractTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final JsBlock accessBlock;
    protected final AccessTranslator accessTranslator;
    protected final KtUnaryExpression expression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 7 || i == 8 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 7 || i == 8 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
            case 9:
                objArr[0] = "context";
                break;
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/js/translate/operation/IncrementTranslator";
                break;
            case 5:
            default:
                objArr[0] = "expression";
                break;
            case 10:
                objArr[0] = "toCallMethodUpon";
                break;
        }
        if (i == 2 || i == 3 || i == 4) {
            objArr[1] = "translate";
        } else if (i == 7) {
            objArr[1] = "asPrefix";
        } else if (i == 8) {
            objArr[1] = "asPostfix";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/operation/IncrementTranslator";
        } else {
            objArr[1] = "variableReassignment";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 11:
                break;
            case 5:
            case 6:
                objArr[2] = "<init>";
                break;
            case 9:
            case 10:
                objArr[2] = "variableReassignment";
                break;
            default:
                objArr[2] = "translate";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 7 && i != 8 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    abstract JsExpression operationExpression(TranslationContext translationContext, JsExpression jsExpression);

    public static boolean isIncrement(IElementType iElementType) {
        return OperatorConventions.INCREMENT_OPERATIONS.contains(iElementType);
    }

    public static JsExpression translate(KtUnaryExpression ktUnaryExpression, TranslationContext translationContext) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        if (isDynamic(translationContext, ktUnaryExpression)) {
            JsExpression jsExpressionDoTranslate = DynamicIncrementTranslator.doTranslate(ktUnaryExpression, translationContext);
            if (jsExpressionDoTranslate == null) {
                $$$reportNull$$$0(2);
            }
            return jsExpressionDoTranslate;
        }
        if (TranslationUtils.hasCorrespondingFunctionIntrinsic(translationContext, ktUnaryExpression)) {
            JsExpression jsExpressionTranslateIncrementExpression = new IntrinsicIncrementTranslator(ktUnaryExpression, translationContext).translateIncrementExpression();
            if (jsExpressionTranslateIncrementExpression == null) {
                $$$reportNull$$$0(3);
            }
            return jsExpressionTranslateIncrementExpression;
        }
        JsExpression jsExpressionTranslateIncrementExpression2 = new OverloadedIncrementTranslator(ktUnaryExpression, translationContext).translateIncrementExpression();
        if (jsExpressionTranslateIncrementExpression2 == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpressionTranslateIncrementExpression2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected IncrementTranslator(KtUnaryExpression ktUnaryExpression, TranslationContext translationContext) {
        super(translationContext);
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(5);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(6);
        }
        JsBlock jsBlock = new JsBlock();
        this.accessBlock = jsBlock;
        this.expression = ktUnaryExpression;
        this.accessTranslator = AccessTranslationUtils.getAccessTranslator(PsiUtils.getBaseExpression(ktUnaryExpression), context().innerBlock(jsBlock)).getCached();
    }

    protected JsExpression translateIncrementExpression() {
        if (PsiUtils.isPrefix(this.expression)) {
            return asPrefix();
        }
        return asPostfix();
    }

    private JsExpression asPrefix() {
        JsExpression jsExpressionSource = variableReassignment(context().innerBlock(this.accessBlock), this.accessTranslator.translateAsGet().source((Object) this.expression)).source((Object) this.expression);
        this.accessBlock.getStatements().add(JsAstUtils.asSyntheticStatement(jsExpressionSource));
        JsExpression jsExpressionSource2 = this.accessTranslator.translateAsGet().source((Object) this.expression);
        if (this.accessBlock.getStatements().size() == 1) {
            jsExpressionSource2 = new JsBinaryOperation(JsBinaryOperator.COMMA, jsExpressionSource, jsExpressionSource2);
        } else {
            context().getCurrentBlock().getStatements().addAll(this.accessBlock.getStatements());
        }
        MetadataProperties.setSynthetic(jsExpressionSource2, true);
        if (jsExpressionSource2 == null) {
            $$$reportNull$$$0(7);
        }
        return jsExpressionSource2;
    }

    private JsExpression asPostfix() {
        JsExpression jsExpressionReference;
        TemporaryVariable temporaryVariableDeclareTemporary = context().declareTemporary(this.accessTranslator.translateAsGet().source((Object) this.expression), this.expression);
        this.accessBlock.getStatements().add(temporaryVariableDeclareTemporary.assignmentStatement());
        JsExpression jsExpressionSource = variableReassignment(context().innerBlock(this.accessBlock), temporaryVariableDeclareTemporary.reference()).source((Object) this.expression);
        this.accessBlock.getStatements().add(JsAstUtils.asSyntheticStatement(jsExpressionSource.source((Object) this.expression)));
        if (this.accessBlock.getStatements().size() == 2) {
            jsExpressionReference = JsAstUtils.newSequence(Arrays.asList(temporaryVariableDeclareTemporary.assignmentExpression(), jsExpressionSource, temporaryVariableDeclareTemporary.reference()));
        } else {
            context().getCurrentBlock().getStatements().addAll(this.accessBlock.getStatements());
            jsExpressionReference = temporaryVariableDeclareTemporary.reference();
        }
        MetadataProperties.setSynthetic(jsExpressionReference, true);
        if (jsExpressionReference == null) {
            $$$reportNull$$$0(8);
        }
        return jsExpressionReference;
    }

    private JsExpression variableReassignment(TranslationContext translationContext, JsExpression jsExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(9);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(10);
        }
        JsExpression jsExpressionTranslateAsSet = this.accessTranslator.translateAsSet(operationExpression(translationContext, jsExpression));
        if (jsExpressionTranslateAsSet == null) {
            $$$reportNull$$$0(11);
        }
        return jsExpressionTranslateAsSet;
    }

    private static boolean isDynamic(TranslationContext translationContext, KtUnaryExpression ktUnaryExpression) {
        return DynamicCallsKt.isDynamic(BindingUtils.getCallableDescriptorForOperationExpression(translationContext.bindingContext(), ktUnaryExpression));
    }
}
