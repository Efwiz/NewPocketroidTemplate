package org.jetbrains.kotlin.psi;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes6.dex */
public class KtVisitor<R, D> extends PsiElementVisitor {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "dcl";
                break;
            case 2:
                objArr[0] = "klass";
                break;
            case 3:
            case 95:
                objArr[0] = "declaration";
                break;
            case 4:
                objArr[0] = "classOrObject";
                break;
            case 5:
            case 6:
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                break;
            case 7:
                objArr[0] = "function";
                break;
            case 8:
                objArr[0] = "property";
                break;
            case 9:
                objArr[0] = "multiDeclaration";
                break;
            case 10:
                objArr[0] = "multiDeclarationEntry";
                break;
            case 11:
                objArr[0] = "typeAlias";
                break;
            case 12:
                objArr[0] = "file";
                break;
            case 13:
                objArr[0] = "script";
                break;
            case 14:
                objArr[0] = "importAlias";
                break;
            case 15:
                objArr[0] = "importDirective";
                break;
            case 16:
                objArr[0] = "importList";
                break;
            case 17:
                objArr[0] = "fileAnnotationList";
                break;
            case 18:
                objArr[0] = "classBody";
                break;
            case 19:
            case 24:
            case 27:
            case 29:
            case 38:
            case 80:
            case 86:
                objArr[0] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 20:
                objArr[0] = "annotation";
                break;
            case 21:
                objArr[0] = "annotationEntry";
                break;
            case 22:
                objArr[0] = "annotationTarget";
                break;
            case 23:
                objArr[0] = "constructorCalleeExpression";
                break;
            case 25:
            case 28:
                objArr[0] = "parameter";
                break;
            case 26:
                objArr[0] = "enumEntry";
                break;
            case 30:
            case 31:
            case 33:
                objArr[0] = "specifier";
                break;
            case 32:
            case 35:
                objArr[0] = "call";
                break;
            case 34:
                objArr[0] = "contextReceiverList";
                break;
            case 36:
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                break;
            case 37:
                objArr[0] = "typeReference";
                break;
            case 39:
                objArr[0] = "argument";
                break;
            case 40:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 77:
            case 78:
            case 79:
            case 93:
            case 94:
            case 100:
                objArr[0] = "expression";
                break;
            case 41:
                objArr[0] = "loopExpression";
                break;
            case 74:
                objArr[0] = "catchClause";
                break;
            case 75:
                objArr[0] = "finallySection";
                break;
            case 76:
                objArr[0] = "typeArgumentList";
                break;
            case 81:
            case 82:
            case 83:
                objArr[0] = "initializer";
                break;
            case 84:
            case 85:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
            case 87:
                objArr[0] = "constraint";
                break;
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 96:
                objArr[0] = "nullableType";
                break;
            case 97:
                objArr[0] = "definitelyNotNullType";
                break;
            case 98:
                objArr[0] = "typeProjection";
                break;
            case 99:
                objArr[0] = "ktWhenEntry";
                break;
            case 101:
            case 102:
            case 103:
                objArr[0] = "condition";
                break;
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
                objArr[0] = "entry";
                break;
            case 110:
                objArr[0] = "directive";
                break;
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtVisitor";
        switch (i) {
            case 1:
                objArr[2] = "visitDeclaration";
                break;
            case 2:
                objArr[2] = "visitClass";
                break;
            case 3:
                objArr[2] = "visitObjectDeclaration";
                break;
            case 4:
                objArr[2] = "visitClassOrObject";
                break;
            case 5:
                objArr[2] = "visitSecondaryConstructor";
                break;
            case 6:
                objArr[2] = "visitPrimaryConstructor";
                break;
            case 7:
                objArr[2] = "visitNamedFunction";
                break;
            case 8:
                objArr[2] = "visitProperty";
                break;
            case 9:
                objArr[2] = "visitDestructuringDeclaration";
                break;
            case 10:
                objArr[2] = "visitDestructuringDeclarationEntry";
                break;
            case 11:
                objArr[2] = "visitTypeAlias";
                break;
            case 12:
                objArr[2] = "visitKtFile";
                break;
            case 13:
                objArr[2] = "visitScript";
                break;
            case 14:
                objArr[2] = "visitImportAlias";
                break;
            case 15:
                objArr[2] = "visitImportDirective";
                break;
            case 16:
                objArr[2] = "visitImportList";
                break;
            case 17:
                objArr[2] = "visitFileAnnotationList";
                break;
            case 18:
                objArr[2] = "visitClassBody";
                break;
            case 19:
                objArr[2] = "visitModifierList";
                break;
            case 20:
                objArr[2] = "visitAnnotation";
                break;
            case 21:
                objArr[2] = "visitAnnotationEntry";
                break;
            case 22:
                objArr[2] = "visitAnnotationUseSiteTarget";
                break;
            case 23:
                objArr[2] = "visitConstructorCalleeExpression";
                break;
            case 24:
                objArr[2] = "visitTypeParameterList";
                break;
            case 25:
                objArr[2] = "visitTypeParameter";
                break;
            case 26:
                objArr[2] = "visitEnumEntry";
                break;
            case 27:
                objArr[2] = "visitParameterList";
                break;
            case 28:
                objArr[2] = "visitParameter";
                break;
            case 29:
                objArr[2] = "visitSuperTypeList";
                break;
            case 30:
                objArr[2] = "visitSuperTypeListEntry";
                break;
            case 31:
                objArr[2] = "visitDelegatedSuperTypeEntry";
                break;
            case 32:
                objArr[2] = "visitSuperTypeCallEntry";
                break;
            case 33:
                objArr[2] = "visitSuperTypeEntry";
                break;
            case 34:
                objArr[2] = "visitContextReceiverList";
                break;
            case 35:
                objArr[2] = "visitConstructorDelegationCall";
                break;
            case 36:
                objArr[2] = "visitPropertyDelegate";
                break;
            case 37:
                objArr[2] = "visitTypeReference";
                break;
            case 38:
                objArr[2] = "visitValueArgumentList";
                break;
            case 39:
                objArr[2] = "visitArgument";
                break;
            case 40:
                objArr[2] = "visitExpression";
                break;
            case 41:
                objArr[2] = "visitLoopExpression";
                break;
            case 42:
                objArr[2] = "visitConstantExpression";
                break;
            case 43:
                objArr[2] = "visitSimpleNameExpression";
                break;
            case 44:
                objArr[2] = "visitReferenceExpression";
                break;
            case 45:
                objArr[2] = "visitLabeledExpression";
                break;
            case 46:
                objArr[2] = "visitPrefixExpression";
                break;
            case 47:
                objArr[2] = "visitPostfixExpression";
                break;
            case 48:
                objArr[2] = "visitUnaryExpression";
                break;
            case 49:
                objArr[2] = "visitBinaryExpression";
                break;
            case 50:
                objArr[2] = "visitReturnExpression";
                break;
            case 51:
                objArr[2] = "visitExpressionWithLabel";
                break;
            case 52:
                objArr[2] = "visitThrowExpression";
                break;
            case 53:
                objArr[2] = "visitBreakExpression";
                break;
            case 54:
                objArr[2] = "visitContinueExpression";
                break;
            case 55:
                objArr[2] = "visitIfExpression";
                break;
            case 56:
                objArr[2] = "visitWhenExpression";
                break;
            case 57:
                objArr[2] = "visitCollectionLiteralExpression";
                break;
            case 58:
                objArr[2] = "visitTryExpression";
                break;
            case 59:
                objArr[2] = "visitForExpression";
                break;
            case 60:
                objArr[2] = "visitWhileExpression";
                break;
            case 61:
                objArr[2] = "visitDoWhileExpression";
                break;
            case 62:
                objArr[2] = "visitLambdaExpression";
                break;
            case 63:
                objArr[2] = "visitAnnotatedExpression";
                break;
            case 64:
                objArr[2] = "visitCallExpression";
                break;
            case 65:
                objArr[2] = "visitArrayAccessExpression";
                break;
            case 66:
                objArr[2] = "visitQualifiedExpression";
                break;
            case 67:
                objArr[2] = "visitDoubleColonExpression";
                break;
            case 68:
                objArr[2] = "visitCallableReferenceExpression";
                break;
            case 69:
                objArr[2] = "visitClassLiteralExpression";
                break;
            case 70:
                objArr[2] = "visitDotQualifiedExpression";
                break;
            case 71:
                objArr[2] = "visitSafeQualifiedExpression";
                break;
            case 72:
                objArr[2] = "visitObjectLiteralExpression";
                break;
            case 73:
                objArr[2] = "visitBlockExpression";
                break;
            case 74:
                objArr[2] = "visitCatchSection";
                break;
            case 75:
                objArr[2] = "visitFinallySection";
                break;
            case 76:
                objArr[2] = "visitTypeArgumentList";
                break;
            case 77:
                objArr[2] = "visitThisExpression";
                break;
            case 78:
                objArr[2] = "visitSuperExpression";
                break;
            case 79:
                objArr[2] = "visitParenthesizedExpression";
                break;
            case 80:
                objArr[2] = "visitInitializerList";
                break;
            case 81:
                objArr[2] = "visitAnonymousInitializer";
                break;
            case 82:
                objArr[2] = "visitScriptInitializer";
                break;
            case 83:
                objArr[2] = "visitClassInitializer";
                break;
            case 84:
                objArr[2] = "visitPropertyAccessor";
                break;
            case 85:
                objArr[2] = "visitBackingField";
                break;
            case 86:
                objArr[2] = "visitTypeConstraintList";
                break;
            case 87:
                objArr[2] = "visitTypeConstraint";
                break;
            case 88:
                objArr[2] = "visitTypeElement";
                break;
            case 89:
                objArr[2] = "visitUserType";
                break;
            case 90:
                objArr[2] = "visitDynamicType";
                break;
            case 91:
                objArr[2] = "visitFunctionType";
                break;
            case 92:
                objArr[2] = "visitSelfType";
                break;
            case 93:
                objArr[2] = "visitBinaryWithTypeRHSExpression";
                break;
            case 94:
                objArr[2] = "visitStringTemplateExpression";
                break;
            case 95:
                objArr[2] = "visitNamedDeclaration";
                break;
            case 96:
                objArr[2] = "visitNullableType";
                break;
            case 97:
                objArr[2] = "visitIntersectionType";
                break;
            case 98:
                objArr[2] = "visitTypeProjection";
                break;
            case 99:
                objArr[2] = "visitWhenEntry";
                break;
            case 100:
                objArr[2] = "visitIsExpression";
                break;
            case 101:
                objArr[2] = "visitWhenConditionIsPattern";
                break;
            case 102:
                objArr[2] = "visitWhenConditionInRange";
                break;
            case 103:
                objArr[2] = "visitWhenConditionWithExpression";
                break;
            case 104:
                objArr[2] = "visitStringTemplateEntry";
                break;
            case 105:
                objArr[2] = "visitStringTemplateEntryWithExpression";
                break;
            case 106:
                objArr[2] = "visitBlockStringTemplateEntry";
                break;
            case 107:
                objArr[2] = "visitSimpleNameStringTemplateEntry";
                break;
            case 108:
                objArr[2] = "visitLiteralStringTemplateEntry";
                break;
            case 109:
                objArr[2] = "visitEscapeStringTemplateEntry";
                break;
            case 110:
                objArr[2] = "visitPackageDirective";
                break;
            default:
                objArr[2] = "visitKtElement";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public R visitKtElement(KtElement ktElement, D d) {
        if (ktElement == null) {
            $$$reportNull$$$0(0);
        }
        visitElement(ktElement);
        return null;
    }

    public R visitDeclaration(KtDeclaration ktDeclaration, D d) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(1);
        }
        return visitExpression(ktDeclaration, d);
    }

    public R visitClass(KtClass ktClass, D d) {
        if (ktClass == null) {
            $$$reportNull$$$0(2);
        }
        return visitClassOrObject(ktClass, d);
    }

    public R visitObjectDeclaration(KtObjectDeclaration ktObjectDeclaration, D d) {
        if (ktObjectDeclaration == null) {
            $$$reportNull$$$0(3);
        }
        return visitClassOrObject(ktObjectDeclaration, d);
    }

    public R visitClassOrObject(KtClassOrObject ktClassOrObject, D d) {
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(4);
        }
        return visitNamedDeclaration(ktClassOrObject, d);
    }

    public R visitSecondaryConstructor(KtSecondaryConstructor ktSecondaryConstructor, D d) {
        if (ktSecondaryConstructor == null) {
            $$$reportNull$$$0(5);
        }
        return visitNamedDeclaration(ktSecondaryConstructor, d);
    }

    public R visitPrimaryConstructor(KtPrimaryConstructor ktPrimaryConstructor, D d) {
        if (ktPrimaryConstructor == null) {
            $$$reportNull$$$0(6);
        }
        return visitNamedDeclaration(ktPrimaryConstructor, d);
    }

    public R visitNamedFunction(KtNamedFunction ktNamedFunction, D d) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(7);
        }
        return visitNamedDeclaration(ktNamedFunction, d);
    }

    public R visitProperty(KtProperty ktProperty, D d) {
        if (ktProperty == null) {
            $$$reportNull$$$0(8);
        }
        return visitNamedDeclaration(ktProperty, d);
    }

    public R visitDestructuringDeclaration(KtDestructuringDeclaration ktDestructuringDeclaration, D d) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(9);
        }
        return visitDeclaration(ktDestructuringDeclaration, d);
    }

    public R visitDestructuringDeclarationEntry(KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry, D d) {
        if (ktDestructuringDeclarationEntry == null) {
            $$$reportNull$$$0(10);
        }
        return visitNamedDeclaration(ktDestructuringDeclarationEntry, d);
    }

    public R visitTypeAlias(KtTypeAlias ktTypeAlias, D d) {
        if (ktTypeAlias == null) {
            $$$reportNull$$$0(11);
        }
        return visitNamedDeclaration(ktTypeAlias, d);
    }

    public R visitKtFile(KtFile ktFile, D d) {
        if (ktFile == null) {
            $$$reportNull$$$0(12);
        }
        visitFile(ktFile);
        return null;
    }

    public R visitScript(KtScript ktScript, D d) {
        if (ktScript == null) {
            $$$reportNull$$$0(13);
        }
        return visitDeclaration(ktScript, d);
    }

    public R visitImportAlias(KtImportAlias ktImportAlias, D d) {
        if (ktImportAlias == null) {
            $$$reportNull$$$0(14);
        }
        return visitKtElement(ktImportAlias, d);
    }

    public R visitImportDirective(KtImportDirective ktImportDirective, D d) {
        if (ktImportDirective == null) {
            $$$reportNull$$$0(15);
        }
        return visitKtElement(ktImportDirective, d);
    }

    public R visitImportList(KtImportList ktImportList, D d) {
        if (ktImportList == null) {
            $$$reportNull$$$0(16);
        }
        return visitKtElement(ktImportList, d);
    }

    public R visitFileAnnotationList(KtFileAnnotationList ktFileAnnotationList, D d) {
        if (ktFileAnnotationList == null) {
            $$$reportNull$$$0(17);
        }
        return visitKtElement(ktFileAnnotationList, d);
    }

    public R visitClassBody(KtClassBody ktClassBody, D d) {
        if (ktClassBody == null) {
            $$$reportNull$$$0(18);
        }
        return visitKtElement(ktClassBody, d);
    }

    public R visitModifierList(KtModifierList ktModifierList, D d) {
        if (ktModifierList == null) {
            $$$reportNull$$$0(19);
        }
        return visitKtElement(ktModifierList, d);
    }

    public R visitAnnotation(KtAnnotation ktAnnotation, D d) {
        if (ktAnnotation == null) {
            $$$reportNull$$$0(20);
        }
        return visitKtElement(ktAnnotation, d);
    }

    public R visitAnnotationEntry(KtAnnotationEntry ktAnnotationEntry, D d) {
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(21);
        }
        return visitKtElement(ktAnnotationEntry, d);
    }

    public R visitAnnotationUseSiteTarget(KtAnnotationUseSiteTarget ktAnnotationUseSiteTarget, D d) {
        if (ktAnnotationUseSiteTarget == null) {
            $$$reportNull$$$0(22);
        }
        return visitKtElement(ktAnnotationUseSiteTarget, d);
    }

    public R visitConstructorCalleeExpression(KtConstructorCalleeExpression ktConstructorCalleeExpression, D d) {
        if (ktConstructorCalleeExpression == null) {
            $$$reportNull$$$0(23);
        }
        return visitKtElement(ktConstructorCalleeExpression, d);
    }

    public R visitTypeParameterList(KtTypeParameterList ktTypeParameterList, D d) {
        if (ktTypeParameterList == null) {
            $$$reportNull$$$0(24);
        }
        return visitKtElement(ktTypeParameterList, d);
    }

    public R visitTypeParameter(KtTypeParameter ktTypeParameter, D d) {
        if (ktTypeParameter == null) {
            $$$reportNull$$$0(25);
        }
        return visitNamedDeclaration(ktTypeParameter, d);
    }

    public R visitEnumEntry(KtEnumEntry ktEnumEntry, D d) {
        if (ktEnumEntry == null) {
            $$$reportNull$$$0(26);
        }
        return visitClass(ktEnumEntry, d);
    }

    public R visitParameterList(KtParameterList ktParameterList, D d) {
        if (ktParameterList == null) {
            $$$reportNull$$$0(27);
        }
        return visitKtElement(ktParameterList, d);
    }

    public R visitParameter(KtParameter ktParameter, D d) {
        if (ktParameter == null) {
            $$$reportNull$$$0(28);
        }
        return visitNamedDeclaration(ktParameter, d);
    }

    public R visitSuperTypeList(KtSuperTypeList ktSuperTypeList, D d) {
        if (ktSuperTypeList == null) {
            $$$reportNull$$$0(29);
        }
        return visitKtElement(ktSuperTypeList, d);
    }

    public R visitSuperTypeListEntry(KtSuperTypeListEntry ktSuperTypeListEntry, D d) {
        if (ktSuperTypeListEntry == null) {
            $$$reportNull$$$0(30);
        }
        return visitKtElement(ktSuperTypeListEntry, d);
    }

    public R visitDelegatedSuperTypeEntry(KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry, D d) {
        if (ktDelegatedSuperTypeEntry == null) {
            $$$reportNull$$$0(31);
        }
        return visitSuperTypeListEntry(ktDelegatedSuperTypeEntry, d);
    }

    public R visitSuperTypeCallEntry(KtSuperTypeCallEntry ktSuperTypeCallEntry, D d) {
        if (ktSuperTypeCallEntry == null) {
            $$$reportNull$$$0(32);
        }
        return visitSuperTypeListEntry(ktSuperTypeCallEntry, d);
    }

    public R visitSuperTypeEntry(KtSuperTypeEntry ktSuperTypeEntry, D d) {
        if (ktSuperTypeEntry == null) {
            $$$reportNull$$$0(33);
        }
        return visitSuperTypeListEntry(ktSuperTypeEntry, d);
    }

    public R visitContextReceiverList(KtContextReceiverList ktContextReceiverList, D d) {
        if (ktContextReceiverList == null) {
            $$$reportNull$$$0(34);
        }
        return visitKtElement(ktContextReceiverList, d);
    }

    public R visitConstructorDelegationCall(KtConstructorDelegationCall ktConstructorDelegationCall, D d) {
        if (ktConstructorDelegationCall == null) {
            $$$reportNull$$$0(35);
        }
        return visitKtElement(ktConstructorDelegationCall, d);
    }

    public R visitPropertyDelegate(KtPropertyDelegate ktPropertyDelegate, D d) {
        if (ktPropertyDelegate == null) {
            $$$reportNull$$$0(36);
        }
        return visitKtElement(ktPropertyDelegate, d);
    }

    public R visitTypeReference(KtTypeReference ktTypeReference, D d) {
        if (ktTypeReference == null) {
            $$$reportNull$$$0(37);
        }
        return visitKtElement(ktTypeReference, d);
    }

    public R visitValueArgumentList(KtValueArgumentList ktValueArgumentList, D d) {
        if (ktValueArgumentList == null) {
            $$$reportNull$$$0(38);
        }
        return visitKtElement(ktValueArgumentList, d);
    }

    public R visitArgument(KtValueArgument ktValueArgument, D d) {
        if (ktValueArgument == null) {
            $$$reportNull$$$0(39);
        }
        return visitKtElement(ktValueArgument, d);
    }

    public R visitExpression(KtExpression ktExpression, D d) {
        if (ktExpression == null) {
            $$$reportNull$$$0(40);
        }
        return visitKtElement(ktExpression, d);
    }

    public R visitLoopExpression(KtLoopExpression ktLoopExpression, D d) {
        if (ktLoopExpression == null) {
            $$$reportNull$$$0(41);
        }
        return visitExpression(ktLoopExpression, d);
    }

    public R visitConstantExpression(KtConstantExpression ktConstantExpression, D d) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(42);
        }
        return visitExpression(ktConstantExpression, d);
    }

    public R visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, D d) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(43);
        }
        return visitReferenceExpression(ktSimpleNameExpression, d);
    }

    public R visitReferenceExpression(KtReferenceExpression ktReferenceExpression, D d) {
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(44);
        }
        return visitExpression(ktReferenceExpression, d);
    }

    public R visitLabeledExpression(KtLabeledExpression ktLabeledExpression, D d) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(45);
        }
        return visitExpressionWithLabel(ktLabeledExpression, d);
    }

    public R visitPrefixExpression(KtPrefixExpression ktPrefixExpression, D d) {
        if (ktPrefixExpression == null) {
            $$$reportNull$$$0(46);
        }
        return visitUnaryExpression(ktPrefixExpression, d);
    }

    public R visitPostfixExpression(KtPostfixExpression ktPostfixExpression, D d) {
        if (ktPostfixExpression == null) {
            $$$reportNull$$$0(47);
        }
        return visitUnaryExpression(ktPostfixExpression, d);
    }

    public R visitUnaryExpression(KtUnaryExpression ktUnaryExpression, D d) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(48);
        }
        return visitExpression(ktUnaryExpression, d);
    }

    public R visitBinaryExpression(KtBinaryExpression ktBinaryExpression, D d) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(49);
        }
        return visitExpression(ktBinaryExpression, d);
    }

    public R visitReturnExpression(KtReturnExpression ktReturnExpression, D d) {
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(50);
        }
        return visitExpressionWithLabel(ktReturnExpression, d);
    }

    public R visitExpressionWithLabel(KtExpressionWithLabel ktExpressionWithLabel, D d) {
        if (ktExpressionWithLabel == null) {
            $$$reportNull$$$0(51);
        }
        return visitExpression(ktExpressionWithLabel, d);
    }

    public R visitThrowExpression(KtThrowExpression ktThrowExpression, D d) {
        if (ktThrowExpression == null) {
            $$$reportNull$$$0(52);
        }
        return visitExpression(ktThrowExpression, d);
    }

    public R visitBreakExpression(KtBreakExpression ktBreakExpression, D d) {
        if (ktBreakExpression == null) {
            $$$reportNull$$$0(53);
        }
        return visitExpressionWithLabel(ktBreakExpression, d);
    }

    public R visitContinueExpression(KtContinueExpression ktContinueExpression, D d) {
        if (ktContinueExpression == null) {
            $$$reportNull$$$0(54);
        }
        return visitExpressionWithLabel(ktContinueExpression, d);
    }

    public R visitIfExpression(KtIfExpression ktIfExpression, D d) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(55);
        }
        return visitExpression(ktIfExpression, d);
    }

    public R visitWhenExpression(KtWhenExpression ktWhenExpression, D d) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(56);
        }
        return visitExpression(ktWhenExpression, d);
    }

    public R visitCollectionLiteralExpression(KtCollectionLiteralExpression ktCollectionLiteralExpression, D d) {
        if (ktCollectionLiteralExpression == null) {
            $$$reportNull$$$0(57);
        }
        return visitExpression(ktCollectionLiteralExpression, d);
    }

    public R visitTryExpression(KtTryExpression ktTryExpression, D d) {
        if (ktTryExpression == null) {
            $$$reportNull$$$0(58);
        }
        return visitExpression(ktTryExpression, d);
    }

    public R visitForExpression(KtForExpression ktForExpression, D d) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(59);
        }
        return visitLoopExpression(ktForExpression, d);
    }

    public R visitWhileExpression(KtWhileExpression ktWhileExpression, D d) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(60);
        }
        return visitLoopExpression(ktWhileExpression, d);
    }

    public R visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression, D d) {
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(61);
        }
        return visitLoopExpression(ktDoWhileExpression, d);
    }

    public R visitLambdaExpression(KtLambdaExpression ktLambdaExpression, D d) {
        if (ktLambdaExpression == null) {
            $$$reportNull$$$0(62);
        }
        return visitExpression(ktLambdaExpression, d);
    }

    public R visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, D d) {
        if (ktAnnotatedExpression == null) {
            $$$reportNull$$$0(63);
        }
        return visitExpression(ktAnnotatedExpression, d);
    }

    public R visitCallExpression(KtCallExpression ktCallExpression, D d) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(64);
        }
        return visitReferenceExpression(ktCallExpression, d);
    }

    public R visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression, D d) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(65);
        }
        return visitReferenceExpression(ktArrayAccessExpression, d);
    }

    public R visitQualifiedExpression(KtQualifiedExpression ktQualifiedExpression, D d) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(66);
        }
        return visitExpression(ktQualifiedExpression, d);
    }

    public R visitDoubleColonExpression(KtDoubleColonExpression ktDoubleColonExpression, D d) {
        if (ktDoubleColonExpression == null) {
            $$$reportNull$$$0(67);
        }
        return visitExpression(ktDoubleColonExpression, d);
    }

    public R visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression, D d) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(68);
        }
        return visitDoubleColonExpression(ktCallableReferenceExpression, d);
    }

    public R visitClassLiteralExpression(KtClassLiteralExpression ktClassLiteralExpression, D d) {
        if (ktClassLiteralExpression == null) {
            $$$reportNull$$$0(69);
        }
        return visitDoubleColonExpression(ktClassLiteralExpression, d);
    }

    public R visitDotQualifiedExpression(KtDotQualifiedExpression ktDotQualifiedExpression, D d) {
        if (ktDotQualifiedExpression == null) {
            $$$reportNull$$$0(70);
        }
        return visitQualifiedExpression(ktDotQualifiedExpression, d);
    }

    public R visitSafeQualifiedExpression(KtSafeQualifiedExpression ktSafeQualifiedExpression, D d) {
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(71);
        }
        return visitQualifiedExpression(ktSafeQualifiedExpression, d);
    }

    public R visitObjectLiteralExpression(KtObjectLiteralExpression ktObjectLiteralExpression, D d) {
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(72);
        }
        return visitExpression(ktObjectLiteralExpression, d);
    }

    public R visitBlockExpression(KtBlockExpression ktBlockExpression, D d) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(73);
        }
        return visitExpression(ktBlockExpression, d);
    }

    public R visitCatchSection(KtCatchClause ktCatchClause, D d) {
        if (ktCatchClause == null) {
            $$$reportNull$$$0(74);
        }
        return visitKtElement(ktCatchClause, d);
    }

    public R visitFinallySection(KtFinallySection ktFinallySection, D d) {
        if (ktFinallySection == null) {
            $$$reportNull$$$0(75);
        }
        return visitKtElement(ktFinallySection, d);
    }

    public R visitTypeArgumentList(KtTypeArgumentList ktTypeArgumentList, D d) {
        if (ktTypeArgumentList == null) {
            $$$reportNull$$$0(76);
        }
        return visitKtElement(ktTypeArgumentList, d);
    }

    public R visitThisExpression(KtThisExpression ktThisExpression, D d) {
        if (ktThisExpression == null) {
            $$$reportNull$$$0(77);
        }
        return visitExpressionWithLabel(ktThisExpression, d);
    }

    public R visitSuperExpression(KtSuperExpression ktSuperExpression, D d) {
        if (ktSuperExpression == null) {
            $$$reportNull$$$0(78);
        }
        return visitExpressionWithLabel(ktSuperExpression, d);
    }

    public R visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression, D d) {
        if (ktParenthesizedExpression == null) {
            $$$reportNull$$$0(79);
        }
        return visitExpression(ktParenthesizedExpression, d);
    }

    public R visitInitializerList(KtInitializerList ktInitializerList, D d) {
        if (ktInitializerList == null) {
            $$$reportNull$$$0(80);
        }
        return visitKtElement(ktInitializerList, d);
    }

    public R visitAnonymousInitializer(KtAnonymousInitializer ktAnonymousInitializer, D d) {
        if (ktAnonymousInitializer == null) {
            $$$reportNull$$$0(81);
        }
        return visitDeclaration(ktAnonymousInitializer, d);
    }

    public R visitScriptInitializer(KtScriptInitializer ktScriptInitializer, D d) {
        if (ktScriptInitializer == null) {
            $$$reportNull$$$0(82);
        }
        return visitAnonymousInitializer(ktScriptInitializer, d);
    }

    public R visitClassInitializer(KtClassInitializer ktClassInitializer, D d) {
        if (ktClassInitializer == null) {
            $$$reportNull$$$0(83);
        }
        return visitAnonymousInitializer(ktClassInitializer, d);
    }

    public R visitPropertyAccessor(KtPropertyAccessor ktPropertyAccessor, D d) {
        if (ktPropertyAccessor == null) {
            $$$reportNull$$$0(84);
        }
        return visitDeclaration(ktPropertyAccessor, d);
    }

    public R visitBackingField(KtBackingField ktBackingField, D d) {
        if (ktBackingField == null) {
            $$$reportNull$$$0(85);
        }
        return visitDeclaration(ktBackingField, d);
    }

    public R visitTypeConstraintList(KtTypeConstraintList ktTypeConstraintList, D d) {
        if (ktTypeConstraintList == null) {
            $$$reportNull$$$0(86);
        }
        return visitKtElement(ktTypeConstraintList, d);
    }

    public R visitTypeConstraint(KtTypeConstraint ktTypeConstraint, D d) {
        if (ktTypeConstraint == null) {
            $$$reportNull$$$0(87);
        }
        return visitKtElement(ktTypeConstraint, d);
    }

    private R visitTypeElement(KtTypeElement ktTypeElement, D d) {
        if (ktTypeElement == null) {
            $$$reportNull$$$0(88);
        }
        return visitKtElement(ktTypeElement, d);
    }

    public R visitUserType(KtUserType ktUserType, D d) {
        if (ktUserType == null) {
            $$$reportNull$$$0(89);
        }
        return visitTypeElement(ktUserType, d);
    }

    public R visitDynamicType(KtDynamicType ktDynamicType, D d) {
        if (ktDynamicType == null) {
            $$$reportNull$$$0(90);
        }
        return visitTypeElement(ktDynamicType, d);
    }

    public R visitFunctionType(KtFunctionType ktFunctionType, D d) {
        if (ktFunctionType == null) {
            $$$reportNull$$$0(91);
        }
        return visitTypeElement(ktFunctionType, d);
    }

    public R visitSelfType(KtSelfType ktSelfType, D d) {
        if (ktSelfType == null) {
            $$$reportNull$$$0(92);
        }
        return visitTypeElement(ktSelfType, d);
    }

    public R visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, D d) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(93);
        }
        return visitExpression(ktBinaryExpressionWithTypeRHS, d);
    }

    public R visitStringTemplateExpression(KtStringTemplateExpression ktStringTemplateExpression, D d) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(94);
        }
        return visitExpression(ktStringTemplateExpression, d);
    }

    public R visitNamedDeclaration(KtNamedDeclaration ktNamedDeclaration, D d) {
        if (ktNamedDeclaration == null) {
            $$$reportNull$$$0(95);
        }
        return visitDeclaration(ktNamedDeclaration, d);
    }

    public R visitNullableType(KtNullableType ktNullableType, D d) {
        if (ktNullableType == null) {
            $$$reportNull$$$0(96);
        }
        return visitTypeElement(ktNullableType, d);
    }

    public R visitIntersectionType(KtIntersectionType ktIntersectionType, D d) {
        if (ktIntersectionType == null) {
            $$$reportNull$$$0(97);
        }
        return visitTypeElement(ktIntersectionType, d);
    }

    public R visitTypeProjection(KtTypeProjection ktTypeProjection, D d) {
        if (ktTypeProjection == null) {
            $$$reportNull$$$0(98);
        }
        return visitKtElement(ktTypeProjection, d);
    }

    public R visitWhenEntry(KtWhenEntry ktWhenEntry, D d) {
        if (ktWhenEntry == null) {
            $$$reportNull$$$0(99);
        }
        return visitKtElement(ktWhenEntry, d);
    }

    public R visitIsExpression(KtIsExpression ktIsExpression, D d) {
        if (ktIsExpression == null) {
            $$$reportNull$$$0(100);
        }
        return visitExpression(ktIsExpression, d);
    }

    public R visitWhenConditionIsPattern(KtWhenConditionIsPattern ktWhenConditionIsPattern, D d) {
        if (ktWhenConditionIsPattern == null) {
            $$$reportNull$$$0(101);
        }
        return visitKtElement(ktWhenConditionIsPattern, d);
    }

    public R visitWhenConditionInRange(KtWhenConditionInRange ktWhenConditionInRange, D d) {
        if (ktWhenConditionInRange == null) {
            $$$reportNull$$$0(102);
        }
        return visitKtElement(ktWhenConditionInRange, d);
    }

    public R visitWhenConditionWithExpression(KtWhenConditionWithExpression ktWhenConditionWithExpression, D d) {
        if (ktWhenConditionWithExpression == null) {
            $$$reportNull$$$0(103);
        }
        return visitKtElement(ktWhenConditionWithExpression, d);
    }

    public R visitStringTemplateEntry(KtStringTemplateEntry ktStringTemplateEntry, D d) {
        if (ktStringTemplateEntry == null) {
            $$$reportNull$$$0(104);
        }
        return visitKtElement(ktStringTemplateEntry, d);
    }

    public R visitStringTemplateEntryWithExpression(KtStringTemplateEntryWithExpression ktStringTemplateEntryWithExpression, D d) {
        if (ktStringTemplateEntryWithExpression == null) {
            $$$reportNull$$$0(105);
        }
        return visitStringTemplateEntry(ktStringTemplateEntryWithExpression, d);
    }

    public R visitBlockStringTemplateEntry(KtBlockStringTemplateEntry ktBlockStringTemplateEntry, D d) {
        if (ktBlockStringTemplateEntry == null) {
            $$$reportNull$$$0(106);
        }
        return visitStringTemplateEntryWithExpression(ktBlockStringTemplateEntry, d);
    }

    public R visitSimpleNameStringTemplateEntry(KtSimpleNameStringTemplateEntry ktSimpleNameStringTemplateEntry, D d) {
        if (ktSimpleNameStringTemplateEntry == null) {
            $$$reportNull$$$0(107);
        }
        return visitStringTemplateEntryWithExpression(ktSimpleNameStringTemplateEntry, d);
    }

    public R visitLiteralStringTemplateEntry(KtLiteralStringTemplateEntry ktLiteralStringTemplateEntry, D d) {
        if (ktLiteralStringTemplateEntry == null) {
            $$$reportNull$$$0(108);
        }
        return visitStringTemplateEntry(ktLiteralStringTemplateEntry, d);
    }

    public R visitEscapeStringTemplateEntry(KtEscapeStringTemplateEntry ktEscapeStringTemplateEntry, D d) {
        if (ktEscapeStringTemplateEntry == null) {
            $$$reportNull$$$0(109);
        }
        return visitStringTemplateEntry(ktEscapeStringTemplateEntry, d);
    }

    public R visitPackageDirective(KtPackageDirective ktPackageDirective, D d) {
        if (ktPackageDirective == null) {
            $$$reportNull$$$0(110);
        }
        return visitKtElement(ktPackageDirective, d);
    }
}
