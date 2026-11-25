package org.jetbrains.kotlin.js.translate.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.expression.LocalFunctionCollector;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.mutator.LastExpressionMutator;
import org.jetbrains.kotlin.js.translate.utils.mutator.Mutator;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: loaded from: classes5.dex */
public final class FunctionBodyTranslator extends AbstractTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KtDeclarationWithBody declaration;
    private final FunctionDescriptor descriptor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 9 || i == 14 || i == 11 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 9 || i == 14 || i == 11 || i == 12) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "declarationWithBody";
                break;
            case 2:
                objArr[0] = "functionBodyContext";
                break;
            case 3:
            case 6:
            default:
                objArr[0] = "descriptor";
                break;
            case 4:
            case 8:
                objArr[0] = "context";
                break;
            case 5:
            case 9:
            case 11:
            case 12:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/js/translate/utils/FunctionBodyTranslator";
                break;
            case 7:
                objArr[0] = "declaration";
                break;
            case 10:
            case 13:
                objArr[0] = "body";
                break;
        }
        if (i == 5) {
            objArr[1] = "setDefaultValueForArguments";
        } else if (i == 9) {
            objArr[1] = "translate";
        } else if (i == 14) {
            objArr[1] = "lastExpressionReturned";
        } else if (i == 11 || i == 12) {
            objArr[1] = "mayBeWrapWithReturn";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/utils/FunctionBodyTranslator";
        }
        switch (i) {
            case 3:
            case 4:
                objArr[2] = "setDefaultValueForArguments";
                break;
            case 5:
            case 9:
            case 11:
            case 12:
            case 14:
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "<init>";
                break;
            case 10:
                objArr[2] = "mayBeWrapWithReturn";
                break;
            case 13:
                objArr[2] = "lastExpressionReturned";
                break;
            default:
                objArr[2] = "translateFunctionBody";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 9 && i != 14 && i != 11 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static JsBlock translateFunctionBody(FunctionDescriptor functionDescriptor, KtDeclarationWithBody ktDeclarationWithBody, TranslationContext translationContext) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (ktDeclarationWithBody == null) {
            $$$reportNull$$$0(1);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        HashMap map = new HashMap();
        LocalFunctionCollector localFunctionCollector = new LocalFunctionCollector(translationContext.bindingContext());
        ktDeclarationWithBody.acceptChildren(localFunctionCollector, null);
        for (FunctionDescriptor functionDescriptor2 : localFunctionCollector.getFunctions()) {
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(NameSuggestion.sanitizeName(functionDescriptor2.getShortName().isSpecial() ? "lambda" : functionDescriptor2.getShortName().asString()));
            MetadataProperties.setDescriptor(jsNameDeclareTemporaryName, functionDescriptor2);
            map.put(functionDescriptor2, JsAstUtils.pureFqn(jsNameDeclareTemporaryName, (JsExpression) null));
        }
        if (!map.isEmpty()) {
            translationContext = translationContext.innerContextWithDescriptorsAliased(map);
        }
        return new FunctionBodyTranslator(functionDescriptor, ktDeclarationWithBody, translationContext).translate();
    }

    public static List<JsStatement> setDefaultValueForArguments(FunctionDescriptor functionDescriptor, TranslationContext translationContext) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(4);
        }
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        List<ValueParameterDescriptor> functionParametersForDefaultValueGeneration = CodegenUtil.getFunctionParametersForDefaultValueGeneration(functionDescriptor, translationContext.bindingTrace());
        ArrayList arrayList = new ArrayList(valueParameters.size());
        for (int i = 0; i < valueParameters.size(); i++) {
            ValueParameterDescriptor valueParameterDescriptor = valueParameters.get(i);
            ValueParameterDescriptor valueParameterDescriptor2 = functionParametersForDefaultValueGeneration.get(i);
            if (valueParameterDescriptor2.declaresDefaultValue()) {
                JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(valueParameterDescriptor, translationContext);
                KtExpression defaultArgument = BindingUtils.getDefaultArgument(valueParameterDescriptor2);
                JsBlock jsBlock = new JsBlock();
                JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(defaultArgument, translationContext, jsBlock);
                PsiElement psi = PsiSourceElementKt.getPsi(valueParameterDescriptor.getSourceElement());
                JsStatement jsStatementMergeStatementInBlockIfNeeded = JsAstUtils.mergeStatementInBlockIfNeeded(JsAstUtils.assignment(jsExpressionTranslateAsValueReference, jsExpressionTranslateAsExpression).source((Object) psi).makeStmt(), jsBlock);
                JsBinaryOperation jsBinaryOperationEquality = JsAstUtils.equality(jsExpressionTranslateAsValueReference, Namer.getUndefinedExpression());
                jsBinaryOperationEquality.source((Object) psi);
                JsIf jsIfNewJsIf = JsAstUtils.newJsIf(jsBinaryOperationEquality, jsStatementMergeStatementInBlockIfNeeded);
                jsIfNewJsIf.setSource(jsBinaryOperationEquality.getSource());
                arrayList.add(jsIfNewJsIf);
            }
        }
        return arrayList;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private FunctionBodyTranslator(FunctionDescriptor functionDescriptor, KtDeclarationWithBody ktDeclarationWithBody, TranslationContext translationContext) {
        super(translationContext);
        if (functionDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (ktDeclarationWithBody == null) {
            $$$reportNull$$$0(7);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(8);
        }
        this.descriptor = functionDescriptor;
        this.declaration = ktDeclarationWithBody;
    }

    private JsBlock translate() {
        KtExpression bodyExpression = this.declaration.getBodyExpression();
        JsBlock jsBlock = new JsBlock();
        jsBlock.getStatements().addAll(mayBeWrapWithReturn(Translation.translateExpression(bodyExpression, context(), jsBlock)).getStatements());
        if ((bodyExpression instanceof KtBlockExpression) && this.descriptor.getReturnType() != null && KotlinBuiltIns.isUnit(this.descriptor.getReturnType()) && !KotlinBuiltIns.isUnit(TranslationUtils.getReturnTypeForCoercion(this.descriptor))) {
            JsReturn jsReturn = new JsReturn(ReferenceTranslator.translateAsValueReference(context().getCurrentModule().getBuiltIns().getUnit(), context()));
            jsReturn.setSource(UtilsKt.getFinalElement(this.declaration));
            jsBlock.getStatements().add(jsReturn);
        }
        return jsBlock;
    }

    private JsBlock mayBeWrapWithReturn(JsNode jsNode) {
        if (jsNode == null) {
            $$$reportNull$$$0(10);
        }
        if (!mustAddReturnToGeneratedFunctionBody()) {
            JsBlock jsBlockConvertToBlock = JsAstUtils.convertToBlock(jsNode);
            if (jsBlockConvertToBlock == null) {
                $$$reportNull$$$0(11);
            }
            return jsBlockConvertToBlock;
        }
        JsBlock jsBlockConvertToBlock2 = JsAstUtils.convertToBlock(lastExpressionReturned(jsNode));
        if (jsBlockConvertToBlock2 == null) {
            $$$reportNull$$$0(12);
        }
        return jsBlockConvertToBlock2;
    }

    private boolean mustAddReturnToGeneratedFunctionBody() {
        return !this.declaration.hasBlockBody() && (KotlinBuiltIns.mayReturnNonUnitValue(this.descriptor) || this.descriptor.isSuspend());
    }

    private JsNode lastExpressionReturned(JsNode jsNode) {
        if (jsNode == null) {
            $$$reportNull$$$0(13);
        }
        JsStatement jsStatementMutateLastExpression = LastExpressionMutator.mutateLastExpression(jsNode, new Mutator() { // from class: org.jetbrains.kotlin.js.translate.utils.FunctionBodyTranslator$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.js.translate.utils.mutator.Mutator
            public final JsNode mutate(JsNode jsNode2) {
                return this.f$0.m7303x66f2a3e5(jsNode2);
            }
        });
        if (jsStatementMutateLastExpression == null) {
            $$$reportNull$$$0(14);
        }
        return jsStatementMutateLastExpression;
    }

    /* JADX INFO: renamed from: lambda$lastExpressionReturned$0$org-jetbrains-kotlin-js-translate-utils-FunctionBodyTranslator, reason: not valid java name */
    /* synthetic */ JsNode m7303x66f2a3e5(JsNode jsNode) {
        if (!(jsNode instanceof JsExpression)) {
            return jsNode;
        }
        JsReturn jsReturn = new JsReturn(TranslationUtils.coerce(context(), (JsExpression) jsNode, TranslationUtils.getReturnTypeForCoercion(this.descriptor)));
        jsReturn.setSource(this.declaration.getBodyExpression());
        MetadataProperties.setReturnTarget(jsReturn, this.descriptor);
        return jsReturn;
    }
}
