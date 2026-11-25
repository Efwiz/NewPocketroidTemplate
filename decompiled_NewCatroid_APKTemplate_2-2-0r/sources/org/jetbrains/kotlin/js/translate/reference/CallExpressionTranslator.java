package org.jetbrains.kotlin.js.translate.reference;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.com.google.gwt.dev.js.ThrowExceptionOnErrorReporter;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.backend.ast.DelegatingJsFunctionScopeWithTemporaryParent;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsRootScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.RewriteUtilsKt;
import org.jetbrains.kotlin.js.parser.ParserUtilsKt;
import org.jetbrains.kotlin.js.resolve.BindingContextSlicesJsKt;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsCallChecker;
import org.jetbrains.kotlin.js.sourceMap.PsiUtils;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.FunctionImportedFromObject;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: loaded from: classes5.dex */
public final class CallExpressionTranslator extends AbstractCallExpressionTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 7 || i == 8 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 7 || i == 8 || i == 12) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 6:
                objArr[0] = "context";
                break;
            case 2:
            case 4:
                objArr[0] = "descriptor";
                break;
            case 5:
            default:
                objArr[0] = "expression";
                break;
            case 7:
            case 8:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/js/translate/reference/CallExpressionTranslator";
                break;
            case 9:
                objArr[0] = "scope";
                break;
            case 10:
                objArr[0] = "name";
                break;
            case 11:
                objArr[0] = "jsCodeExpression";
                break;
        }
        if (i == 7) {
            objArr[1] = "translate";
        } else if (i == 8) {
            objArr[1] = "translateJsCode";
        } else if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/reference/CallExpressionTranslator";
        } else {
            objArr[1] = "parseJsCode";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
                objArr[2] = "shouldBeInlined";
                break;
            case 5:
            case 6:
                objArr[2] = "<init>";
                break;
            case 7:
            case 8:
            case 12:
                break;
            case 9:
            case 10:
                objArr[2] = "getVariableByName";
                break;
            case 11:
                objArr[2] = "parseJsCode";
                break;
            default:
                objArr[2] = "translate";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 8 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static JsNode translate(KtCallExpression ktCallExpression, JsExpression jsExpression, TranslationContext translationContext) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        if (JsCallChecker.isJsCall(CallUtilKt.getFunctionResolvedCallWithAssert(ktCallExpression, translationContext.bindingContext()))) {
            return new CallExpressionTranslator(ktCallExpression, jsExpression, translationContext).translateJsCode();
        }
        return new CallExpressionTranslator(ktCallExpression, jsExpression, translationContext).translate();
    }

    public static boolean shouldBeInlined(CallableDescriptor callableDescriptor, TranslationContext translationContext) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
        if (translationContext.getConfig().getConfiguration().getBoolean(CommonConfigurationKeys.DISABLE_INLINE)) {
            return false;
        }
        return shouldBeInlined(callableDescriptor);
    }

    public static boolean shouldBeInlined(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if ((callableDescriptor instanceof SimpleFunctionDescriptor) || (callableDescriptor instanceof PropertyAccessorDescriptor) || (callableDescriptor instanceof FunctionImportedFromObject)) {
            return InlineUtil.isInline(callableDescriptor);
        }
        return (callableDescriptor instanceof ValueParameterDescriptor) && InlineUtil.isInline(callableDescriptor.getContainingModule()) && InlineUtil.isInlineParameter((ParameterDescriptor) callableDescriptor) && !((ValueParameterDescriptor) callableDescriptor).getIsCrossinline();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private CallExpressionTranslator(KtCallExpression ktCallExpression, JsExpression jsExpression, TranslationContext translationContext) {
        super(ktCallExpression, jsExpression, translationContext);
        if (ktCallExpression == null) {
            $$$reportNull$$$0(5);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(6);
        }
    }

    private JsExpression translate() {
        JsExpression jsExpressionTranslate = CallTranslator.translate(context(), this.resolvedCall, this.receiver);
        if (jsExpressionTranslate == null) {
            $$$reportNull$$$0(7);
        }
        return jsExpressionTranslate;
    }

    private JsNode translateJsCode() {
        JsStatement expression;
        List<JsStatement> jsCode = parseJsCode(this.expression.getValueArguments().get(0).getArgumentExpression());
        int size = jsCode.size();
        if (size == 0) {
            expression = new JsNullLiteral();
        } else if (size > 1) {
            expression = new JsBlock(jsCode);
        } else {
            expression = jsCode.get(0);
            if (expression instanceof JsExpressionStatement) {
                expression = ((JsExpressionStatement) expression).getExpression();
            }
        }
        LexicalScope lexicalScope = (LexicalScope) context().bindingContext().get(BindingContextSlicesJsKt.LEXICAL_SCOPE_FOR_JS, this.resolvedCall);
        HashMap map = new HashMap();
        if (lexicalScope != null) {
            Set<JsName> setCollectUsedNames = CollectUtilsKt.collectUsedNames(expression);
            setCollectUsedNames.removeAll(CollectUtilsKt.collectDefinedNames(expression));
            for (JsName jsName : setCollectUsedNames) {
                VariableDescriptor variableByName = getVariableByName(lexicalScope, Name.identifier(jsName.getIdent()));
                if (variableByName != null) {
                    map.put(jsName, ReferenceTranslator.translateAsValueReference(variableByName, context()));
                }
            }
            if (!map.isEmpty()) {
                expression = RewriteUtilsKt.replaceNames(expression, map);
            }
        }
        if (expression == null) {
            $$$reportNull$$$0(8);
        }
        return expression;
    }

    private static VariableDescriptor getVariableByName(LexicalScope lexicalScope, Name name2) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(9);
        }
        if (name2 == null) {
            $$$reportNull$$$0(10);
        }
        while (true) {
            Collection<? extends VariableDescriptor> contributedVariables = lexicalScope.getContributedVariables(name2, NoLookupLocation.FROM_BACKEND);
            if (!contributedVariables.isEmpty()) {
                if (contributedVariables.size() == 1) {
                    return contributedVariables.iterator().next();
                }
                return null;
            }
            if (!(lexicalScope.getParent() instanceof LexicalScope)) {
                break;
            }
            LexicalScope lexicalScope2 = (LexicalScope) lexicalScope.getParent();
            if (lexicalScope.getOwnerDescriptor() != lexicalScope2.getOwnerDescriptor()) {
                break;
            }
            lexicalScope = lexicalScope2;
        }
        return null;
    }

    private List<JsStatement> parseJsCode(KtExpression ktExpression) {
        JsLocation jsLocation;
        if (ktExpression == null) {
            $$$reportNull$$$0(11);
        }
        String strExtractStringValue = JsCallChecker.extractStringValue(ConstantExpressionEvaluator.getConstant(ktExpression, context().bindingContext()));
        DelegatingJsFunctionScopeWithTemporaryParent delegatingJsFunctionScopeWithTemporaryParent = new DelegatingJsFunctionScopeWithTemporaryParent((JsFunctionScope) context().scope(), new JsRootScope(new JsProgram()));
        try {
            jsLocation = PsiUtils.extractLocationFromPsi(ktExpression, context().getSourceFilePathResolver());
        } catch (IOException unused) {
            jsLocation = new JsLocation(ktExpression.getContainingKtFile().getName(), 0, 0);
        }
        List<JsStatement> expressionOrStatement = ParserUtilsKt.parseExpressionOrStatement(strExtractStringValue, ThrowExceptionOnErrorReporter.INSTANCE, delegatingJsFunctionScopeWithTemporaryParent, new CodePosition(jsLocation.getStartLine(), jsLocation.getStartChar()), jsLocation.getFile());
        if (expressionOrStatement == null) {
            expressionOrStatement = Collections.emptyList();
        }
        if (expressionOrStatement == null) {
            $$$reportNull$$$0(12);
        }
        return expressionOrStatement;
    }
}
