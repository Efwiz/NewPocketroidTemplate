package org.jetbrains.kotlin.js.translate.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.declaration.ClassTranslator;
import org.jetbrains.kotlin.js.translate.declaration.PropertyTranslatorKt;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.general.TranslatorVisitor;
import org.jetbrains.kotlin.js.translate.operation.BinaryOperationTranslator;
import org.jetbrains.kotlin.js.translate.operation.UnaryOperationTranslator;
import org.jetbrains.kotlin.js.translate.reference.AccessTranslationUtils;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;
import org.jetbrains.kotlin.js.translate.reference.CallExpressionTranslator;
import org.jetbrains.kotlin.js.translate.reference.CallableReferenceTranslator;
import org.jetbrains.kotlin.js.translate.reference.QualifiedExpressionTranslator;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.ErrorReportingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.js.translate.utils.mutator.CoercionMutator;
import org.jetbrains.kotlin.js.translate.utils.mutator.LastExpressionMutator;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtBreakExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtContinueExpression;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDoWhileExpression;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtExpressionWithLabel;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtPrefixExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtThrowExpression;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;

/* JADX INFO: loaded from: classes5.dex */
public final class ExpressionVisitor extends TranslatorVisitor<JsNode> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final FqName primitiveClassesFqName = new FqName("kotlin.reflect.js.internal.PrimitiveClasses");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 6:
            case 9:
            case 14:
            case 19:
            case 20:
            case 23:
            case 26:
            case 29:
            case 32:
            case 36:
            case 41:
            case 44:
            case 45:
            case 48:
            case 51:
            case 54:
            case 57:
            case 58:
            case 61:
            case 66:
            case 71:
            case 74:
            case 77:
            case 80:
            case 85:
            case 88:
            case 91:
            case 94:
            case 97:
            case 100:
            case 103:
            case 106:
            case 109:
            case 112:
            case 115:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 6:
            case 9:
            case 14:
            case 19:
            case 20:
            case 23:
            case 26:
            case 29:
            case 32:
            case 36:
            case 41:
            case 44:
            case 45:
            case 48:
            case 51:
            case 54:
            case 57:
            case 58:
            case 61:
            case 66:
            case 71:
            case 74:
            case 77:
            case 80:
            case 85:
            case 88:
            case 91:
            case 94:
            case 97:
            case 100:
            case 103:
            case 106:
            case 109:
            case 112:
            case 115:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
            case 15:
            case 17:
            case 21:
            case 24:
            case 27:
            case 30:
            case 39:
            case 42:
            case 46:
            case 49:
            case 52:
            case 55:
            case 59:
            case 62:
            case 64:
            case 67:
            case 69:
            case 72:
            case 75:
            case 78:
            case 81:
            case 83:
            case 86:
            case 89:
            case 92:
            case 95:
            case 98:
            case 101:
            case 104:
            case 107:
            case 110:
            case 113:
            case 116:
            case 118:
                objArr[0] = "expression";
                break;
            case 2:
            case 5:
            case 8:
            case 11:
            case 13:
            case 16:
            case 18:
            case 22:
            case 25:
            case 28:
            case 31:
            case 33:
            case 34:
            case 38:
            case 40:
            case 43:
            case 47:
            case 50:
            case 53:
            case 56:
            case 60:
            case 63:
            case 65:
            case 68:
            case 70:
            case 73:
            case 76:
            case 79:
            case 82:
            case 84:
            case 87:
            case 90:
            case 93:
            case 96:
            case 99:
            case 102:
            case 105:
            case 108:
            case 111:
            case 114:
            case 117:
            default:
                objArr[0] = "context";
                break;
            case 3:
            case 6:
            case 9:
            case 14:
            case 19:
            case 20:
            case 23:
            case 26:
            case 29:
            case 32:
            case 36:
            case 41:
            case 44:
            case 45:
            case 48:
            case 51:
            case 54:
            case 57:
            case 58:
            case 61:
            case 66:
            case 71:
            case 74:
            case 77:
            case 80:
            case 85:
            case 88:
            case 91:
            case 94:
            case 97:
            case 100:
            case 103:
            case 106:
            case 109:
            case 112:
            case 115:
                objArr[0] = "org/jetbrains/kotlin/js/translate/expression/ExpressionVisitor";
                break;
            case 7:
                objArr[0] = "ktBlockExpression";
                break;
            case 10:
                objArr[0] = "multiDeclaration";
                break;
            case 12:
                objArr[0] = "ktReturnExpression";
                break;
            case 35:
                objArr[0] = "name";
                break;
            case 37:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 119:
                objArr[0] = "klass";
                break;
            case 120:
                objArr[0] = "typeAlias";
                break;
            case 121:
                objArr[0] = "declaration";
                break;
            case 122:
                objArr[0] = "descriptor";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "visitConstantExpression";
                break;
            case 6:
                objArr[1] = "translateConstantExpression";
                break;
            case 9:
                objArr[1] = "visitBlockExpression";
                break;
            case 14:
                objArr[1] = "visitReturnExpression";
                break;
            case 19:
            case 20:
                objArr[1] = "visitParenthesizedExpression";
                break;
            case 23:
                objArr[1] = "visitBinaryExpression";
                break;
            case 26:
                objArr[1] = "visitProperty";
                break;
            case 29:
                objArr[1] = "visitCallableReferenceExpression";
                break;
            case 32:
                objArr[1] = "getObjectKClass";
                break;
            case 36:
                objArr[1] = "getKotlinPrimitiveClassRef";
                break;
            case 41:
                objArr[1] = "visitCallExpression";
                break;
            case 44:
            case 45:
                objArr[1] = "visitIfExpression";
                break;
            case 48:
                objArr[1] = "visitSimpleNameExpression";
                break;
            case 51:
                objArr[1] = "visitWhileExpression";
                break;
            case 54:
                objArr[1] = "visitDoWhileExpression";
                break;
            case 57:
            case 58:
                objArr[1] = "visitStringTemplateExpression";
                break;
            case 61:
                objArr[1] = "resolveAsTemplate";
                break;
            case 66:
                objArr[1] = "visitDotQualifiedExpression";
                break;
            case 71:
                objArr[1] = "visitPrefixExpression";
                break;
            case 74:
                objArr[1] = "visitPostfixExpression";
                break;
            case 77:
                objArr[1] = "visitIsExpression";
                break;
            case 80:
                objArr[1] = "visitSafeQualifiedExpression";
                break;
            case 85:
                objArr[1] = "visitBinaryWithTypeRHSExpression";
                break;
            case 88:
                objArr[1] = "visitBreakExpression";
                break;
            case 91:
                objArr[1] = "visitContinueExpression";
                break;
            case 94:
                objArr[1] = "visitLambdaExpression";
                break;
            case 97:
                objArr[1] = "visitNamedFunction";
                break;
            case 100:
                objArr[1] = "visitThisExpression";
                break;
            case 103:
                objArr[1] = "visitArrayAccessExpression";
                break;
            case 106:
                objArr[1] = "visitSuperExpression";
                break;
            case 109:
                objArr[1] = "visitForExpression";
                break;
            case 112:
                objArr[1] = "visitTryExpression";
                break;
            case 115:
                objArr[1] = "visitThrowExpression";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/js/translate/expression/ExpressionVisitor";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "visitConstantExpression";
                break;
            case 3:
            case 6:
            case 9:
            case 14:
            case 19:
            case 20:
            case 23:
            case 26:
            case 29:
            case 32:
            case 36:
            case 41:
            case 44:
            case 45:
            case 48:
            case 51:
            case 54:
            case 57:
            case 58:
            case 61:
            case 66:
            case 71:
            case 74:
            case 77:
            case 80:
            case 85:
            case 88:
            case 91:
            case 94:
            case 97:
            case 100:
            case 103:
            case 106:
            case 109:
            case 112:
            case 115:
                break;
            case 4:
            case 5:
                objArr[2] = "translateConstantExpression";
                break;
            case 7:
            case 8:
                objArr[2] = "visitBlockExpression";
                break;
            case 10:
            case 11:
                objArr[2] = "visitDestructuringDeclaration";
                break;
            case 12:
            case 13:
                objArr[2] = "visitReturnExpression";
                break;
            case 15:
            case 16:
                objArr[2] = "getNonLocalReturnTarget";
                break;
            case 17:
            case 18:
                objArr[2] = "visitParenthesizedExpression";
                break;
            case 21:
            case 22:
                objArr[2] = "visitBinaryExpression";
                break;
            case 24:
            case 25:
                objArr[2] = "visitProperty";
                break;
            case 27:
            case 28:
                objArr[2] = "visitCallableReferenceExpression";
                break;
            case 30:
                objArr[2] = "visitClassLiteralExpression";
                break;
            case 31:
                objArr[2] = "getObjectKClass";
                break;
            case 33:
                objArr[2] = "getPrimitiveClass";
                break;
            case 34:
            case 35:
                objArr[2] = "getKotlinPrimitiveClassRef";
                break;
            case 37:
                objArr[2] = "isPrimitiveClassLiteral";
                break;
            case 38:
                objArr[2] = "findPrimitiveClassesObject";
                break;
            case 39:
            case 40:
                objArr[2] = "visitCallExpression";
                break;
            case 42:
            case 43:
                objArr[2] = "visitIfExpression";
                break;
            case 46:
            case 47:
                objArr[2] = "visitSimpleNameExpression";
                break;
            case 49:
            case 50:
                objArr[2] = "visitWhileExpression";
                break;
            case 52:
            case 53:
                objArr[2] = "visitDoWhileExpression";
                break;
            case 55:
            case 56:
                objArr[2] = "visitStringTemplateExpression";
                break;
            case 59:
            case 60:
                objArr[2] = "resolveAsTemplate";
                break;
            case 62:
            case 63:
                objArr[2] = "resolveAsStringConstant";
                break;
            case 64:
            case 65:
                objArr[2] = "visitDotQualifiedExpression";
                break;
            case 67:
            case 68:
                objArr[2] = "visitLabeledExpression";
                break;
            case 69:
            case 70:
                objArr[2] = "visitPrefixExpression";
                break;
            case 72:
            case 73:
                objArr[2] = "visitPostfixExpression";
                break;
            case 75:
            case 76:
                objArr[2] = "visitIsExpression";
                break;
            case 78:
            case 79:
                objArr[2] = "visitSafeQualifiedExpression";
                break;
            case 81:
            case 82:
                objArr[2] = "visitWhenExpression";
                break;
            case 83:
            case 84:
                objArr[2] = "visitBinaryWithTypeRHSExpression";
                break;
            case 86:
            case 87:
                objArr[2] = "visitBreakExpression";
                break;
            case 89:
            case 90:
                objArr[2] = "visitContinueExpression";
                break;
            case 92:
            case 93:
                objArr[2] = "visitLambdaExpression";
                break;
            case 95:
            case 96:
                objArr[2] = "visitNamedFunction";
                break;
            case 98:
            case 99:
                objArr[2] = "visitThisExpression";
                break;
            case 101:
            case 102:
                objArr[2] = "visitArrayAccessExpression";
                break;
            case 104:
            case 105:
                objArr[2] = "visitSuperExpression";
                break;
            case 107:
            case 108:
                objArr[2] = "visitForExpression";
                break;
            case 110:
            case 111:
                objArr[2] = "visitTryExpression";
                break;
            case 113:
            case 114:
                objArr[2] = "visitThrowExpression";
                break;
            case 116:
            case 117:
                objArr[2] = "visitObjectLiteralExpression";
                break;
            case 118:
                objArr[2] = "visitAnnotatedExpression";
                break;
            case 119:
                objArr[2] = "visitClass";
                break;
            case 120:
                objArr[2] = "visitTypeAlias";
                break;
            case 121:
            case 122:
            case 123:
                objArr[2] = "translateClassOrObject";
                break;
            default:
                objArr[2] = "emptyResult";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 6:
            case 9:
            case 14:
            case 19:
            case 20:
            case 23:
            case 26:
            case 29:
            case 32:
            case 36:
            case 41:
            case 44:
            case 45:
            case 48:
            case 51:
            case 54:
            case 57:
            case 58:
            case 61:
            case 66:
            case 71:
            case 74:
            case 77:
            case 80:
            case 85:
            case 88:
            case 91:
            case 94:
            case 97:
            case 100:
            case 103:
            case 106:
            case 109:
            case 112:
            case 115:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.general.TranslatorVisitor
    public JsNode emptyResult(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(0);
        }
        return new JsNullLiteral();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitConstantExpression(KtConstantExpression ktConstantExpression, TranslationContext translationContext) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(1);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        JsNode jsNodeSource = translateConstantExpression(ktConstantExpression, translationContext).source(ktConstantExpression);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(3);
        }
        return jsNodeSource;
    }

    private static JsNode translateConstantExpression(KtConstantExpression ktConstantExpression, TranslationContext translationContext) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(4);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(5);
        }
        JsExpression jsExpressionTranslateConstant = Translation.translateConstant(ConstantExpressionEvaluator.getConstant(ktConstantExpression, translationContext.bindingContext()), ktConstantExpression, translationContext);
        if (jsExpressionTranslateConstant == null) {
            throw new AssertionError(ErrorReportingUtils.message(ktConstantExpression, "Unsupported constant expression: " + ktConstantExpression.getText() + " "));
        }
        if (jsExpressionTranslateConstant == null) {
            $$$reportNull$$$0(6);
        }
        return jsExpressionTranslateConstant;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitBlockExpression(KtBlockExpression ktBlockExpression, TranslationContext translationContext) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(7);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(8);
        }
        List<KtExpression> statements = ktBlockExpression.getStatements();
        JsBlock jsBlock = new JsBlock();
        Iterator<KtExpression> it = statements.iterator();
        while (it.hasNext()) {
            JsStatement jsStatementConvertToStatement = JsAstUtils.convertToStatement(Translation.translateExpression(it.next(), translationContext, jsBlock));
            if (!JsAstUtils.isEmptyStatement(jsStatementConvertToStatement)) {
                jsBlock.getStatements().add(jsStatementConvertToStatement);
            }
        }
        if (statements.isEmpty()) {
            jsBlock.getStatements().add(JsAstUtils.asSyntheticStatement(ReferenceTranslator.translateAsValueReference(translationContext.getCurrentModule().getBuiltIns().getUnit(), translationContext)));
        }
        return jsBlock;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitDestructuringDeclaration(KtDestructuringDeclaration ktDestructuringDeclaration, TranslationContext translationContext) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(10);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(11);
        }
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(ktDestructuringDeclaration.getInitializer(), translationContext);
        JsName jsNameDeclareTemporary = JsScope.declareTemporary();
        JsVars jsVarsNewVar = JsAstUtils.newVar(jsNameDeclareTemporary, jsExpressionTranslateAsExpression);
        MetadataProperties.setSynthetic(jsVarsNewVar, true);
        translationContext.addStatementToCurrentBlock(jsVarsNewVar);
        return DestructuringDeclarationTranslator.translate(ktDestructuringDeclaration, JsAstUtils.pureFqn(jsNameDeclareTemporary, (JsExpression) null), translationContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitReturnExpression(KtReturnExpression ktReturnExpression, TranslationContext translationContext) {
        JsReturn jsReturn;
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(12);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(13);
        }
        KtExpression returnedExpression = ktReturnExpression.getReturnedExpression();
        if (((KtDeclarationWithBody) PsiTreeUtil.getParentOfType(ktReturnExpression, KtDeclarationWithBody.class)) instanceof KtSecondaryConstructor) {
            return new JsReturn(ReferenceTranslator.translateAsValueReference(translationContext.getClassDescriptor().getThisAsReceiverParameter(), translationContext).source((Object) ktReturnExpression));
        }
        FunctionDescriptor nonLocalReturnTarget = getNonLocalReturnTarget(ktReturnExpression, translationContext);
        if (returnedExpression == null) {
            jsReturn = new JsReturn((nonLocalReturnTarget == null || !KotlinBuiltIns.mayReturnNonUnitValue(nonLocalReturnTarget)) ? null : ReferenceTranslator.translateAsValueReference(translationContext.getCurrentModule().getBuiltIns().getUnit(), translationContext));
        } else {
            JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(returnedExpression, translationContext);
            translationContext.bindingContext().getType(returnedExpression);
            CallableDescriptor callableDescriptor = nonLocalReturnTarget == null ? (CallableDescriptor) translationContext.getDeclarationDescriptor() : nonLocalReturnTarget;
            if (callableDescriptor != null) {
                jsExpressionTranslateAsExpression = TranslationUtils.coerce(translationContext, jsExpressionTranslateAsExpression, TranslationUtils.getReturnTypeForCoercion(callableDescriptor));
            }
            jsReturn = new JsReturn(jsExpressionTranslateAsExpression);
        }
        MetadataProperties.setReturnTarget(jsReturn, nonLocalReturnTarget);
        JsNode jsNodeSource = jsReturn.source(ktReturnExpression);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(14);
        }
        return jsNodeSource;
    }

    private static FunctionDescriptor getNonLocalReturnTarget(KtReturnExpression ktReturnExpression, TranslationContext translationContext) {
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(15);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(16);
        }
        DeclarationDescriptor declarationDescriptor = translationContext.getDeclarationDescriptor();
        KtSimpleNameExpression targetLabel = ktReturnExpression.getTargetLabel();
        if (ExpressionTypingUtils.isFunctionLiteral(declarationDescriptor) || ExpressionTypingUtils.isFunctionExpression(declarationDescriptor)) {
            if (targetLabel == null) {
                if (ExpressionTypingUtils.isFunctionLiteral(declarationDescriptor)) {
                    return BindingContextUtils.getContainingFunctionSkipFunctionLiterals(declarationDescriptor, true).getFirst();
                }
            } else {
                declarationDescriptor = (DeclarationDescriptor) translationContext.bindingContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, (PsiElement) translationContext.bindingContext().get(BindingContext.LABEL_TARGET, targetLabel));
            }
        }
        return (FunctionDescriptor) declarationDescriptor;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression, TranslationContext translationContext) {
        if (ktParenthesizedExpression == null) {
            $$$reportNull$$$0(17);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(18);
        }
        KtExpression expression = ktParenthesizedExpression.getExpression();
        if (expression != null) {
            JsNode jsNodeTranslateExpression = Translation.translateExpression(expression, translationContext);
            if (jsNodeTranslateExpression == null) {
                $$$reportNull$$$0(19);
            }
            return jsNodeTranslateExpression;
        }
        JsEmpty jsEmpty = JsEmpty.INSTANCE;
        if (jsEmpty == null) {
            $$$reportNull$$$0(20);
        }
        return jsEmpty;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitBinaryExpression(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(21);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(22);
        }
        JsExpression jsExpressionTranslate = BinaryOperationTranslator.translate(ktBinaryExpression, translationContext);
        if (jsExpressionTranslate == null) {
            $$$reportNull$$$0(23);
        }
        return jsExpressionTranslate;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitProperty(KtProperty ktProperty, TranslationContext translationContext) {
        if (ktProperty == null) {
            $$$reportNull$$$0(24);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(25);
        }
        CallableDescriptor callableDescriptor = (VariableDescriptor) BindingContextUtils.getNotNull(translationContext.bindingContext(), BindingContext.VARIABLE, ktProperty);
        JsExpression jsExpressionTranslateInitializerForProperty = TranslationUtils.translateInitializerForProperty(ktProperty, translationContext);
        KtExpression delegateExpression = ktProperty.getDelegateExpression();
        JsName nameForDescriptor = translationContext.getNameForDescriptor(callableDescriptor);
        if (delegateExpression != null) {
            jsExpressionTranslateInitializerForProperty = PropertyTranslatorKt.translateDelegateOrInitializerExpression(translationContext, ktProperty);
        } else if (translationContext.isBoxedLocalCapturedInClosure(callableDescriptor)) {
            JsNameRef capturedVarAccessor = Namer.getCapturedVarAccessor(nameForDescriptor.makeRef());
            if (jsExpressionTranslateInitializerForProperty == null) {
                jsExpressionTranslateInitializerForProperty = new JsNullLiteral();
            }
            jsExpressionTranslateInitializerForProperty = JsAstUtils.wrapValue(capturedVarAccessor, jsExpressionTranslateInitializerForProperty);
        }
        JsNode jsNodeSource = JsAstUtils.newVar(nameForDescriptor, jsExpressionTranslateInitializerForProperty).source(ktProperty);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(26);
        }
        return jsNodeSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression, TranslationContext translationContext) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(27);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(28);
        }
        JsExpression jsExpressionTranslate = CallableReferenceTranslator.INSTANCE.translate(ktCallableReferenceExpression, translationContext);
        if (jsExpressionTranslate == null) {
            $$$reportNull$$$0(29);
        }
        return jsExpressionTranslate;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitClassLiteralExpression(KtClassLiteralExpression ktClassLiteralExpression, TranslationContext translationContext) {
        JsExpression primitiveClass;
        if (ktClassLiteralExpression == null) {
            $$$reportNull$$$0(30);
        }
        KtExpression receiverExpression = ktClassLiteralExpression.getReceiverExpression();
        DoubleColonLHS doubleColonLHS = (DoubleColonLHS) translationContext.bindingContext().get(BindingContext.DOUBLE_COLON_LHS, receiverExpression);
        ClassifierDescriptor this$0 = doubleColonLHS.getType().getConstructor().getThis$0();
        if ((doubleColonLHS instanceof DoubleColonLHS.Expression) && !((DoubleColonLHS.Expression) doubleColonLHS).getIsObjectQualifier()) {
            JsExpression jsExpressionCoerce = TranslationUtils.coerce(translationContext, Translation.translateAsExpression(receiverExpression, translationContext), translationContext.getCurrentModule().getBuiltIns().getAnyType());
            return (!isPrimitiveClassLiteral(doubleColonLHS.getType()) || (primitiveClass = getPrimitiveClass(translationContext, this$0)) == null) ? new JsInvocation(translationContext.namer().kotlin(Namer.GET_KCLASS_FROM_EXPRESSION), jsExpressionCoerce) : JsAstUtils.newSequence(Arrays.asList(jsExpressionCoerce, primitiveClass));
        }
        return getObjectKClass(translationContext, this$0);
    }

    public static JsExpression getObjectKClass(TranslationContext translationContext, ClassifierDescriptor classifierDescriptor) {
        if (translationContext == null) {
            $$$reportNull$$$0(31);
        }
        JsExpression primitiveClass = getPrimitiveClass(translationContext, classifierDescriptor);
        if (primitiveClass != null) {
            if (primitiveClass == null) {
                $$$reportNull$$$0(32);
            }
            return primitiveClass;
        }
        JsName nameForIntrinsic = translationContext.getNameForIntrinsic(SpecialFunction.GET_KCLASS.getSuggestedName());
        MetadataProperties.setSpecialFunction(nameForIntrinsic, SpecialFunction.GET_KCLASS);
        return new JsInvocation(nameForIntrinsic.makeRef(), UtilsKt.getReferenceToJsClass(classifierDescriptor, translationContext));
    }

    public static JsExpression getPrimitiveClass(TranslationContext translationContext, ClassifierDescriptor classifierDescriptor) {
        if (translationContext == null) {
            $$$reportNull$$$0(33);
        }
        if (!translationContext.getConfig().isAtLeast(LanguageVersion.KOTLIN_1_2) || findPrimitiveClassesObject(translationContext) == null || !(classifierDescriptor instanceof ClassDescriptor)) {
            return null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptor;
        FqName fqNameSafe = DescriptorUtilsKt.getFqNameSafe(classDescriptor);
        String strAsString = fqNameSafe.asString();
        strAsString.hashCode();
        switch (strAsString) {
            case "kotlin.Any":
            case "kotlin.Int":
            case "kotlin.Nothing":
            case "kotlin.Byte":
            case "kotlin.IntArray":
            case "kotlin.FloatArray":
            case "kotlin.Array":
            case "kotlin.Float":
            case "kotlin.Short":
            case "kotlin.LongArray":
            case "kotlin.DoubleArray":
            case "kotlin.Double":
            case "kotlin.Number":
            case "kotlin.CharArray":
            case "kotlin.String":
            case "kotlin.Boolean":
            case "kotlin.ByteArray":
            case "kotlin.ShortArray":
            case "kotlin.Throwable":
            case "kotlin.BooleanArray":
                return getKotlinPrimitiveClassRef(translationContext, StringUtil.decapitalize(fqNameSafe.shortName().asString()) + "Class");
            default:
                if (classDescriptor instanceof FunctionClassDescriptor) {
                    FunctionClassDescriptor functionClassDescriptor = (FunctionClassDescriptor) classDescriptor;
                    if (functionClassDescriptor.getFunctionTypeKind() == FunctionTypeKind.Function.INSTANCE) {
                        return new JsInvocation(JsAstUtils.pureFqn(translationContext.getInlineableInnerNameForDescriptor(DescriptorUtils.getFunctionByName(findPrimitiveClassesObject(translationContext).getUnsubstitutedMemberScope(), Name.identifier("functionClass"))), (JsExpression) null), new JsIntLiteral(functionClassDescriptor.getArity()));
                    }
                }
                return null;
        }
    }

    private static JsExpression getKotlinPrimitiveClassRef(TranslationContext translationContext, String str) {
        if (translationContext == null) {
            $$$reportNull$$$0(34);
        }
        if (str == null) {
            $$$reportNull$$$0(35);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(translationContext.getInlineableInnerNameForDescriptor(DescriptorUtils.getPropertyByName(findPrimitiveClassesObject(translationContext).getUnsubstitutedMemberScope(), Name.identifier(str))), (JsExpression) null);
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(36);
        }
        return jsNameRefPureFqn;
    }

    private static boolean isPrimitiveClassLiteral(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(37);
        }
        return KotlinBuiltIns.isPrimitiveType(kotlinType) || KotlinBuiltIns.isArray(kotlinType) || KotlinBuiltIns.isPrimitiveArray(kotlinType);
    }

    private static ClassDescriptor findPrimitiveClassesObject(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(38);
        }
        return FindClassInModuleKt.findClassAcrossModuleDependencies(translationContext.getCurrentModule(), ClassId.topLevel(primitiveClassesFqName));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitCallExpression(KtCallExpression ktCallExpression, TranslationContext translationContext) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(39);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(40);
        }
        JsNode jsNodeSource = CallExpressionTranslator.translate(ktCallExpression, null, translationContext).source(ktCallExpression);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(41);
        }
        return jsNodeSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitIfExpression(KtIfExpression ktIfExpression, TranslationContext translationContext) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(42);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(43);
        }
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(ktIfExpression.getCondition(), translationContext);
        KotlinType type = translationContext.bindingContext().getType(ktIfExpression);
        boolean zIsUsedAsExpression = BindingContextUtilsKt.isUsedAsExpression(ktIfExpression, translationContext.bindingContext());
        KtExpression then = ktIfExpression.getThen();
        KtExpression ktExpression = ktIfExpression.getElse();
        JsStatement jsStatementTranslateAsStatementAndMergeInBlockIfNeeded = then != null ? Translation.translateAsStatementAndMergeInBlockIfNeeded(then, translationContext) : null;
        JsStatement jsStatementTranslateAsStatementAndMergeInBlockIfNeeded2 = ktExpression != null ? Translation.translateAsStatementAndMergeInBlockIfNeeded(ktExpression, translationContext) : null;
        if (type != null) {
            if (jsStatementTranslateAsStatementAndMergeInBlockIfNeeded != null) {
                jsStatementTranslateAsStatementAndMergeInBlockIfNeeded = LastExpressionMutator.mutateLastExpression(jsStatementTranslateAsStatementAndMergeInBlockIfNeeded, new CoercionMutator(type, translationContext));
            }
            if (jsStatementTranslateAsStatementAndMergeInBlockIfNeeded2 != null) {
                jsStatementTranslateAsStatementAndMergeInBlockIfNeeded2 = LastExpressionMutator.mutateLastExpression(jsStatementTranslateAsStatementAndMergeInBlockIfNeeded2, new CoercionMutator(type, translationContext));
            }
        }
        if (zIsUsedAsExpression) {
            JsExpression jsExpressionExtractExpressionFromStatement = JsAstUtils.extractExpressionFromStatement(jsStatementTranslateAsStatementAndMergeInBlockIfNeeded);
            JsExpression jsExpressionExtractExpressionFromStatement2 = JsAstUtils.extractExpressionFromStatement(jsStatementTranslateAsStatementAndMergeInBlockIfNeeded2);
            if (jsExpressionExtractExpressionFromStatement != null && jsExpressionExtractExpressionFromStatement2 != null) {
                JsExpression jsExpressionSource = new JsConditional(jsExpressionTranslateAsExpression, jsExpressionExtractExpressionFromStatement, jsExpressionExtractExpressionFromStatement2).source((Object) ktIfExpression);
                if (jsExpressionSource == null) {
                    $$$reportNull$$$0(44);
                }
                return jsExpressionSource;
            }
        }
        if (jsStatementTranslateAsStatementAndMergeInBlockIfNeeded == null) {
            jsStatementTranslateAsStatementAndMergeInBlockIfNeeded = JsEmpty.INSTANCE;
        }
        JsNode jsNodeSource = new JsIf(jsExpressionTranslateAsExpression, jsStatementTranslateAsStatementAndMergeInBlockIfNeeded, jsStatementTranslateAsStatementAndMergeInBlockIfNeeded2).source(ktIfExpression);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(45);
        }
        return jsNodeSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsExpression visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, TranslationContext translationContext) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(46);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(47);
        }
        JsExpression jsExpressionSource = ReferenceTranslator.translateSimpleName(ktSimpleNameExpression, translationContext).source((Object) ktSimpleNameExpression);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(48);
        }
        return jsExpressionSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitWhileExpression(KtWhileExpression ktWhileExpression, TranslationContext translationContext) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(49);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(50);
        }
        JsNode jsNodeCreateWhile = LoopTranslator.createWhile(false, ktWhileExpression, translationContext);
        if (jsNodeCreateWhile == null) {
            $$$reportNull$$$0(51);
        }
        return jsNodeCreateWhile;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression, TranslationContext translationContext) {
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(52);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(53);
        }
        JsNode jsNodeCreateWhile = LoopTranslator.createWhile(true, ktDoWhileExpression, translationContext);
        if (jsNodeCreateWhile == null) {
            $$$reportNull$$$0(54);
        }
        return jsNodeCreateWhile;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitStringTemplateExpression(KtStringTemplateExpression ktStringTemplateExpression, TranslationContext translationContext) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(55);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(56);
        }
        JsStringLiteral jsStringLiteralResolveAsStringConstant = resolveAsStringConstant(ktStringTemplateExpression, translationContext);
        if (jsStringLiteralResolveAsStringConstant != null) {
            JsExpression jsExpressionSource = jsStringLiteralResolveAsStringConstant.source((Object) ktStringTemplateExpression);
            if (jsExpressionSource == null) {
                $$$reportNull$$$0(57);
            }
            return jsExpressionSource;
        }
        JsNode jsNodeSource = resolveAsTemplate(ktStringTemplateExpression, translationContext).source(ktStringTemplateExpression);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(58);
        }
        return jsNodeSource;
    }

    private static JsNode resolveAsTemplate(KtStringTemplateExpression ktStringTemplateExpression, TranslationContext translationContext) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(59);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(60);
        }
        JsExpression jsExpressionTranslate = StringTemplateTranslator.translate(ktStringTemplateExpression, translationContext);
        if (jsExpressionTranslate == null) {
            $$$reportNull$$$0(61);
        }
        return jsExpressionTranslate;
    }

    private static JsStringLiteral resolveAsStringConstant(KtExpression ktExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(62);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(63);
        }
        Object compileTimeValue = BindingUtils.getCompileTimeValue(translationContext.bindingContext(), ktExpression);
        if (compileTimeValue == null) {
            return null;
        }
        return new JsStringLiteral((String) compileTimeValue);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitDotQualifiedExpression(KtDotQualifiedExpression ktDotQualifiedExpression, TranslationContext translationContext) {
        if (ktDotQualifiedExpression == null) {
            $$$reportNull$$$0(64);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(65);
        }
        JsNode jsNodeTranslateQualifiedExpression = QualifiedExpressionTranslator.translateQualifiedExpression(ktDotQualifiedExpression, translationContext);
        if (jsNodeTranslateQualifiedExpression == null) {
            $$$reportNull$$$0(66);
        }
        return jsNodeTranslateQualifiedExpression;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitLabeledExpression(KtLabeledExpression ktLabeledExpression, TranslationContext translationContext) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(67);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(68);
        }
        KtExpression baseExpression = ktLabeledExpression.getBaseExpression();
        if (BindingContextUtilsKt.isUsedAsExpression(ktLabeledExpression, translationContext.bindingContext())) {
            return Translation.translateAsExpression(baseExpression, translationContext).source((Object) ktLabeledExpression);
        }
        JsFunctionScope jsFunctionScope = (JsFunctionScope) translationContext.scope();
        String referencedName = getReferencedName(ktLabeledExpression.getTargetLabel());
        JsName jsNameEnterLabel = jsFunctionScope.enterLabel(referencedName, NameSuggestion.sanitizeName(referencedName));
        JsStatement jsStatementTranslateAsStatement = Translation.translateAsStatement(baseExpression, translationContext);
        jsFunctionScope.exitLabel();
        return new JsLabel(jsNameEnterLabel, jsStatementTranslateAsStatement).source(ktLabeledExpression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitPrefixExpression(KtPrefixExpression ktPrefixExpression, TranslationContext translationContext) {
        if (ktPrefixExpression == null) {
            $$$reportNull$$$0(69);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(70);
        }
        JsExpression jsExpressionSource = UnaryOperationTranslator.translate(ktPrefixExpression, translationContext).source((Object) ktPrefixExpression);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(71);
        }
        return jsExpressionSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitPostfixExpression(KtPostfixExpression ktPostfixExpression, TranslationContext translationContext) {
        if (ktPostfixExpression == null) {
            $$$reportNull$$$0(72);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(73);
        }
        JsExpression jsExpressionSource = UnaryOperationTranslator.translate(ktPostfixExpression, translationContext).source((Object) ktPostfixExpression);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(74);
        }
        return jsExpressionSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitIsExpression(KtIsExpression ktIsExpression, TranslationContext translationContext) {
        if (ktIsExpression == null) {
            $$$reportNull$$$0(75);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(76);
        }
        JsExpression jsExpressionSource = Translation.patternTranslator(translationContext).translateIsExpression(ktIsExpression).source((Object) ktIsExpression);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(77);
        }
        return jsExpressionSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitSafeQualifiedExpression(KtSafeQualifiedExpression ktSafeQualifiedExpression, TranslationContext translationContext) {
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(78);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(79);
        }
        JsNode jsNodeSource = QualifiedExpressionTranslator.translateQualifiedExpression(ktSafeQualifiedExpression, translationContext).source(ktSafeQualifiedExpression);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(80);
        }
        return jsNodeSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitWhenExpression(KtWhenExpression ktWhenExpression, TranslationContext translationContext) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(81);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(82);
        }
        return WhenTranslator.translate(ktWhenExpression, translationContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, TranslationContext translationContext) {
        JsExpression jsExpressionTranslateAsExpression;
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(83);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(84);
        }
        if (PatternTranslator.isCastExpression(ktBinaryExpressionWithTypeRHS)) {
            jsExpressionTranslateAsExpression = PatternTranslator.newInstance(translationContext).translateCastExpression(ktBinaryExpressionWithTypeRHS);
        } else {
            jsExpressionTranslateAsExpression = Translation.translateAsExpression(ktBinaryExpressionWithTypeRHS.getLeft(), translationContext);
        }
        JsExpression jsExpressionSource = jsExpressionTranslateAsExpression.source((Object) ktBinaryExpressionWithTypeRHS);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(85);
        }
        return jsExpressionSource;
    }

    private static String getReferencedName(KtSimpleNameExpression ktSimpleNameExpression) {
        return ktSimpleNameExpression.getReferencedName().replaceAll("^@", "").replaceAll("(?:^`(.*)`$)", "$1");
    }

    private static JsNameRef getTargetLabel(KtExpressionWithLabel ktExpressionWithLabel, TranslationContext translationContext) {
        KtSimpleNameExpression targetLabel = ktExpressionWithLabel.getTargetLabel();
        if (targetLabel == null) {
            return null;
        }
        return ((JsFunctionScope) translationContext.scope()).findLabel(getReferencedName(targetLabel)).makeRef();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitBreakExpression(KtBreakExpression ktBreakExpression, TranslationContext translationContext) {
        if (ktBreakExpression == null) {
            $$$reportNull$$$0(86);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(87);
        }
        JsNode jsNodeSource = new JsBreak(getTargetLabel(ktBreakExpression, translationContext)).source(ktBreakExpression);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(88);
        }
        return jsNodeSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitContinueExpression(KtContinueExpression ktContinueExpression, TranslationContext translationContext) {
        if (ktContinueExpression == null) {
            $$$reportNull$$$0(89);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(90);
        }
        JsNode jsNodeSource = new JsContinue(getTargetLabel(ktContinueExpression, translationContext)).source(ktContinueExpression);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(91);
        }
        return jsNodeSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitLambdaExpression(KtLambdaExpression ktLambdaExpression, TranslationContext translationContext) {
        if (ktLambdaExpression == null) {
            $$$reportNull$$$0(92);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(93);
        }
        JsExpression jsExpressionSource = new LiteralFunctionTranslator(translationContext).translate(ktLambdaExpression.getFunctionLiteral()).source((Object) ktLambdaExpression);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(94);
        }
        return jsExpressionSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitNamedFunction(KtNamedFunction ktNamedFunction, TranslationContext translationContext) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(95);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(96);
        }
        JsExpression jsExpressionTranslate = new LiteralFunctionTranslator(translationContext).translate(ktNamedFunction);
        FunctionDescriptor functionDescriptor = BindingUtils.getFunctionDescriptor(translationContext.bindingContext(), ktNamedFunction);
        JsNameRef jsNameRef = (JsNameRef) ReferenceTranslator.translateAsValueReference(functionDescriptor, translationContext);
        if (InlineUtil.isInline(functionDescriptor)) {
            MetadataProperties.setStaticRef(jsNameRef.getName(), jsExpressionTranslate);
        }
        JsNode jsNodeNewVar = jsExpressionTranslate;
        if (!BindingContextUtilsKt.isUsedAsExpression(ktNamedFunction, translationContext.bindingContext())) {
            jsNodeNewVar = JsAstUtils.newVar(jsNameRef.getName(), jsExpressionTranslate);
        }
        JsNode jsNodeSource = jsNodeNewVar.source(ktNamedFunction);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(97);
        }
        return jsNodeSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitThisExpression(KtThisExpression ktThisExpression, TranslationContext translationContext) {
        if (ktThisExpression == null) {
            $$$reportNull$$$0(98);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(99);
        }
        JsExpression jsExpressionSource = translationContext.getDispatchReceiver(JsDescriptorUtils.getReceiverParameterForDeclaration(BindingUtils.getDescriptorForReferenceExpression(translationContext.bindingContext(), ktThisExpression.getInstanceReference()))).source((Object) ktThisExpression);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(100);
        }
        return jsExpressionSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression, TranslationContext translationContext) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(101);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(102);
        }
        JsExpression jsExpressionTranslateAsGet = AccessTranslationUtils.translateAsGet(ktArrayAccessExpression, translationContext);
        if (jsExpressionTranslateAsGet == null) {
            $$$reportNull$$$0(103);
        }
        return jsExpressionTranslateAsGet;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitSuperExpression(KtSuperExpression ktSuperExpression, TranslationContext translationContext) {
        if (ktSuperExpression == null) {
            $$$reportNull$$$0(104);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(105);
        }
        JsExpression dispatchReceiver = translationContext.getDispatchReceiver((ReceiverParameterDescriptor) CallUtilKt.getResolvedCallWithAssert(ktSuperExpression, translationContext.bindingContext()).getResultingDescriptor());
        if (dispatchReceiver == null) {
            $$$reportNull$$$0(106);
        }
        return dispatchReceiver;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitForExpression(KtForExpression ktForExpression, TranslationContext translationContext) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(107);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(108);
        }
        JsNode jsNodeSource = LoopTranslator.translateForExpression(ktForExpression, translationContext).source(ktForExpression);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(109);
        }
        return jsNodeSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitTryExpression(KtTryExpression ktTryExpression, TranslationContext translationContext) {
        if (ktTryExpression == null) {
            $$$reportNull$$$0(110);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(111);
        }
        JsTry jsTryTranslate = new TryTranslator(ktTryExpression, translationContext).translate();
        if (jsTryTranslate == null) {
            $$$reportNull$$$0(112);
        }
        return jsTryTranslate;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitThrowExpression(KtThrowExpression ktThrowExpression, TranslationContext translationContext) {
        if (ktThrowExpression == null) {
            $$$reportNull$$$0(113);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(114);
        }
        JsNode jsNodeSource = new JsThrow(Translation.translateAsExpression(ktThrowExpression.getThrownExpression(), translationContext)).source(ktThrowExpression);
        if (jsNodeSource == null) {
            $$$reportNull$$$0(115);
        }
        return jsNodeSource;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitObjectLiteralExpression(KtObjectLiteralExpression ktObjectLiteralExpression, TranslationContext translationContext) {
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(116);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(117);
        }
        ClassDescriptor classDescriptor = BindingUtils.getClassDescriptor(translationContext.bindingContext(), ktObjectLiteralExpression.getObjectDeclaration());
        translateClassOrObject(ktObjectLiteralExpression.getObjectDeclaration(), classDescriptor, translationContext);
        JsExpression jsExpressionTranslateAsTypeReference = ReferenceTranslator.translateAsTypeReference(classDescriptor, translationContext);
        List<DeclarationDescriptor> classOrConstructorClosure = translationContext.getClassOrConstructorClosure(classDescriptor);
        ArrayList arrayList = new ArrayList();
        if (classOrConstructorClosure != null) {
            for (DeclarationDescriptor declarationDescriptor : classOrConstructorClosure) {
                arrayList.add(translationContext.getArgumentForClosureConstructor(declarationDescriptor));
                if (declarationDescriptor instanceof TypeParameterDescriptor) {
                    arrayList.add(translationContext.getTypeArgumentForClosureConstructor((TypeParameterDescriptor) declarationDescriptor));
                }
            }
        }
        ResolvedCall<FunctionDescriptor> superCall = BindingUtils.getSuperCall(translationContext.bindingContext(), ktObjectLiteralExpression.getObjectDeclaration());
        if (superCall != null) {
            arrayList.addAll(CallArgumentTranslator.translate(superCall, null, translationContext).getTranslateArguments());
        }
        return new JsNew(jsExpressionTranslateAsTypeReference, arrayList);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, TranslationContext translationContext) {
        ClassDescriptor annotationClass;
        KtExpression baseExpression;
        if (ktAnnotatedExpression == null) {
            $$$reportNull$$$0(118);
        }
        Iterator<KtAnnotationEntry> it = ktAnnotatedExpression.getAnnotationEntries().iterator();
        while (it.hasNext()) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) translationContext.bindingContext().get(BindingContext.ANNOTATION, it.next());
            if (annotationDescriptor != null && (annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor)) != null && DescriptorUtilsKt.getAnnotationRetention(annotationClass) == KotlinRetention.SOURCE && (baseExpression = ktAnnotatedExpression.getBaseExpression()) != null) {
                return (JsNode) baseExpression.accept(this, translationContext);
            }
        }
        return (JsNode) super.visitAnnotatedExpression(ktAnnotatedExpression, translationContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitClass(KtClass ktClass, TranslationContext translationContext) {
        if (ktClass == null) {
            $$$reportNull$$$0(119);
        }
        translateClassOrObject(ktClass, BindingUtils.getClassDescriptor(translationContext.bindingContext(), ktClass), translationContext);
        return JsEmpty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public JsNode visitTypeAlias(KtTypeAlias ktTypeAlias, TranslationContext translationContext) {
        if (ktTypeAlias == null) {
            $$$reportNull$$$0(120);
        }
        return JsEmpty.INSTANCE;
    }

    private static void translateClassOrObject(KtClassOrObject ktClassOrObject, ClassDescriptor classDescriptor, TranslationContext translationContext) {
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(121);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(122);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(123);
        }
        ClassTranslator.translate(ktClassOrObject, translationContext.innerWithUsageTracker(classDescriptor));
    }
}
