package org.jetbrains.kotlin.js.translate.initializer;

import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.declaration.PropertyTranslatorKt;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.general.TranslatorVisitor;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes5.dex */
public final class InitializerVisitor extends TranslatorVisitor<Void> {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Code duplicated, block: B:28:0x0045  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "property";
        } else if (i == 8) {
            objArr[0] = "expression";
        } else if (i == 10) {
            objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
        } else if (i == 3) {
            objArr[0] = "property";
        } else if (i == 4) {
            objArr[0] = "lateInitDefault";
        } else if (i == 5) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/initializer/InitializerVisitor";
        } else if (i != 6) {
            objArr[0] = "context";
        } else {
            objArr[0] = "initializer";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/initializer/InitializerVisitor";
        } else {
            objArr[1] = "generateDefaultValue";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "visitProperty";
                break;
            case 3:
            case 4:
                objArr[2] = "generateDefaultValue";
                break;
            case 5:
                break;
            case 6:
            case 7:
                objArr[2] = "visitAnonymousInitializer";
                break;
            case 8:
            case 9:
                objArr[2] = "visitDeclaration";
                break;
            case 10:
                objArr[2] = "visitSecondaryConstructor";
                break;
            default:
                objArr[2] = "emptyResult";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.general.TranslatorVisitor
    public Void emptyResult(TranslationContext translationContext) {
        if (translationContext != null) {
            return null;
        }
        $$$reportNull$$$0(0);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitDeclaration(KtDeclaration ktDeclaration, TranslationContext translationContext) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(8);
        }
        if (translationContext != null) {
            return null;
        }
        $$$reportNull$$$0(9);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitSecondaryConstructor(KtSecondaryConstructor ktSecondaryConstructor, TranslationContext translationContext) {
        if (ktSecondaryConstructor != null) {
            return null;
        }
        $$$reportNull$$$0(10);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitProperty(KtProperty ktProperty, TranslationContext translationContext) {
        JsStatement jsStatementMakeStmt;
        KotlinType returnTypeForCoercion;
        if (ktProperty == null) {
            $$$reportNull$$$0(1);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        PropertyDescriptor propertyDescriptor = BindingUtils.getPropertyDescriptor(translationContext.bindingContext(), ktProperty);
        JsExpression jsExpressionTranslateDelegateOrInitializerExpression = PropertyTranslatorKt.translateDelegateOrInitializerExpression(translationContext, ktProperty);
        KtExpression initializer = ktProperty.getInitializer();
        KtExpression delegateExpression = ktProperty.getDelegateExpression();
        if (initializer != null) {
            if (TranslationUtils.isReferenceToSyntheticBackingField(propertyDescriptor)) {
                returnTypeForCoercion = propertyDescriptor.getType();
            } else {
                returnTypeForCoercion = TranslationUtils.getReturnTypeForCoercion(propertyDescriptor);
            }
            jsStatementMakeStmt = InitializerUtils.generateInitializerForProperty(translationContext, propertyDescriptor, TranslationUtils.coerce(translationContext, jsExpressionTranslateDelegateOrInitializerExpression, returnTypeForCoercion));
        } else if (delegateExpression != null) {
            jsStatementMakeStmt = InitializerUtils.generateInitializerForDelegate(translationContext, propertyDescriptor, jsExpressionTranslateDelegateOrInitializerExpression);
        } else if (Boolean.TRUE.equals(translationContext.bindingContext().get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor))) {
            jsStatementMakeStmt = TranslationUtils.assignmentToBackingField(translationContext, propertyDescriptor, generateDefaultValue(propertyDescriptor, TranslationUtils.backingFieldReference(translationContext, propertyDescriptor))).source((Object) ktProperty).makeStmt();
        } else if (JsDescriptorUtils.isSimpleFinalProperty(propertyDescriptor)) {
            JsNameRef jsNameRef = new JsNameRef(translationContext.getNameForDescriptor(propertyDescriptor), new JsThisRef());
            jsStatementMakeStmt = JsAstUtils.assignment(jsNameRef, generateDefaultValue(propertyDescriptor, jsNameRef)).source((Object) ktProperty).makeStmt();
        } else {
            jsStatementMakeStmt = null;
        }
        if (jsStatementMakeStmt != null && !JsAstUtils.isEmptyStatement(jsStatementMakeStmt)) {
            translationContext.addStatementsToCurrentBlock(JsAstUtils.flattenStatement(jsStatementMakeStmt));
        }
        return null;
    }

    private static JsExpression generateDefaultValue(PropertyDescriptor propertyDescriptor, JsExpression jsExpression) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(4);
        }
        if (propertyDescriptor.isLateInit()) {
            JsExpression jsExpressionDeepCopy = jsExpression.deepCopy();
            if (jsExpressionDeepCopy == null) {
                $$$reportNull$$$0(5);
            }
            return jsExpressionDeepCopy;
        }
        KotlinType type = propertyDescriptor.getType();
        if (KotlinBuiltIns.isInt(type) || KotlinBuiltIns.isFloat(type) || KotlinBuiltIns.isDouble(type) || KotlinBuiltIns.isByte(type) || KotlinBuiltIns.isShort(type)) {
            return new JsIntLiteral(0);
        }
        if (KotlinBuiltIns.isBoolean(type)) {
            return new JsBooleanLiteral(false);
        }
        return new JsNullLiteral();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitAnonymousInitializer(KtAnonymousInitializer ktAnonymousInitializer, TranslationContext translationContext) {
        if (ktAnonymousInitializer == null) {
            $$$reportNull$$$0(6);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(7);
        }
        KtExpression body = ktAnonymousInitializer.getBody();
        if (body == null) {
            return null;
        }
        translationContext.addStatementsToCurrentBlock(JsAstUtils.flattenStatement(Translation.translateAsStatementAndMergeInBlockIfNeeded(body, translationContext)));
        return null;
    }
}
