package org.jetbrains.kotlin.psi;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes6.dex */
public class KtVisitorVoidWithParameter<P> extends KtVisitor<Void, P> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 101:
                objArr[0] = "dcl";
                break;
            case 2:
            case 102:
                objArr[0] = "klass";
                break;
            case 3:
            case 4:
            case 103:
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                break;
            case 5:
            case 104:
                objArr[0] = "function";
                break;
            case 6:
            case 105:
                objArr[0] = "property";
                break;
            case 7:
            case 106:
                objArr[0] = "multiDeclaration";
                break;
            case 8:
            case 107:
                objArr[0] = "multiDeclarationEntry";
                break;
            case 9:
            case 108:
                objArr[0] = "file";
                break;
            case 10:
            case 109:
                objArr[0] = "script";
                break;
            case 11:
            case 110:
                objArr[0] = "importDirective";
                break;
            case 12:
                objArr[0] = "importAlias";
                break;
            case 13:
            case 111:
                objArr[0] = "importList";
                break;
            case 14:
            case 112:
                objArr[0] = "classBody";
                break;
            case 15:
            case 19:
            case 22:
            case 24:
            case 32:
            case 73:
            case 76:
            case 113:
            case 116:
            case 119:
            case 121:
            case 129:
            case 171:
            case 174:
                objArr[0] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 16:
            case 114:
                objArr[0] = "annotation";
                break;
            case 17:
            case 115:
                objArr[0] = "annotationEntry";
                break;
            case 18:
                objArr[0] = "constructorCalleeExpression";
                break;
            case 20:
            case 23:
            case 117:
            case 120:
                objArr[0] = "parameter";
                break;
            case 21:
            case 118:
                objArr[0] = "enumEntry";
                break;
            case 25:
            case 26:
            case 28:
            case 122:
            case 123:
            case 125:
                objArr[0] = "specifier";
                break;
            case 27:
            case 29:
            case 124:
            case 126:
                objArr[0] = "call";
                break;
            case 30:
            case 127:
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                break;
            case 31:
            case 128:
                objArr[0] = "typeReference";
                break;
            case 33:
            case 130:
                objArr[0] = "argument";
                break;
            case 34:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
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
            case 70:
            case 71:
            case 72:
            case 82:
            case 83:
            case 88:
            case 89:
            case 131:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 168:
            case 169:
            case 170:
            case 180:
            case 181:
            case 186:
                objArr[0] = "expression";
                break;
            case 35:
            case 132:
                objArr[0] = "loopExpression";
                break;
            case 67:
            case 165:
                objArr[0] = "catchClause";
                break;
            case 68:
            case 166:
                objArr[0] = "finallySection";
                break;
            case 69:
            case 167:
                objArr[0] = "typeArgumentList";
                break;
            case 74:
            case 172:
                objArr[0] = "initializer";
                break;
            case 75:
            case 173:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
            case 77:
            case 175:
                objArr[0] = "constraint";
                break;
            case 78:
            case 79:
            case 80:
            case 81:
            case 176:
            case 177:
            case 178:
            case 179:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 84:
            case 93:
            case 182:
            case 190:
                objArr[0] = "declaration";
                break;
            case 85:
            case 183:
                objArr[0] = "nullableType";
                break;
            case 86:
            case 184:
                objArr[0] = "typeProjection";
                break;
            case 87:
            case 185:
                objArr[0] = "ktWhenEntry";
                break;
            case 90:
            case 91:
            case 92:
            case 187:
            case 188:
            case 189:
                objArr[0] = "condition";
                break;
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 191:
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
                objArr[0] = "entry";
                break;
            case 100:
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtVisitorVoidWithParameter";
        switch (i) {
            case 1:
                objArr[2] = "visitDeclarationVoid";
                break;
            case 2:
                objArr[2] = "visitClassVoid";
                break;
            case 3:
                objArr[2] = "visitSecondaryConstructorVoid";
                break;
            case 4:
                objArr[2] = "visitPrimaryConstructorVoid";
                break;
            case 5:
                objArr[2] = "visitNamedFunctionVoid";
                break;
            case 6:
                objArr[2] = "visitPropertyVoid";
                break;
            case 7:
                objArr[2] = "visitDestructuringDeclarationVoid";
                break;
            case 8:
                objArr[2] = "visitDestructuringDeclarationEntryVoid";
                break;
            case 9:
                objArr[2] = "visitKtFileVoid";
                break;
            case 10:
                objArr[2] = "visitScriptVoid";
                break;
            case 11:
                objArr[2] = "visitImportDirectiveVoid";
                break;
            case 12:
                objArr[2] = "visitImportAliasVoid";
                break;
            case 13:
                objArr[2] = "visitImportListVoid";
                break;
            case 14:
                objArr[2] = "visitClassBodyVoid";
                break;
            case 15:
                objArr[2] = "visitModifierListVoid";
                break;
            case 16:
                objArr[2] = "visitAnnotationVoid";
                break;
            case 17:
                objArr[2] = "visitAnnotationEntryVoid";
                break;
            case 18:
                objArr[2] = "visitConstructorCalleeExpressionVoid";
                break;
            case 19:
                objArr[2] = "visitTypeParameterListVoid";
                break;
            case 20:
                objArr[2] = "visitTypeParameterVoid";
                break;
            case 21:
                objArr[2] = "visitEnumEntryVoid";
                break;
            case 22:
                objArr[2] = "visitParameterListVoid";
                break;
            case 23:
                objArr[2] = "visitParameterVoid";
                break;
            case 24:
                objArr[2] = "visitSuperTypeListVoid";
                break;
            case 25:
                objArr[2] = "visitSuperTypeListEntryVoid";
                break;
            case 26:
                objArr[2] = "visitDelegatedSuperTypeEntryVoid";
                break;
            case 27:
                objArr[2] = "visitSuperTypeCallEntryVoid";
                break;
            case 28:
                objArr[2] = "visitSuperTypeEntryVoid";
                break;
            case 29:
                objArr[2] = "visitConstructorDelegationCallVoid";
                break;
            case 30:
                objArr[2] = "visitPropertyDelegateVoid";
                break;
            case 31:
                objArr[2] = "visitTypeReferenceVoid";
                break;
            case 32:
                objArr[2] = "visitValueArgumentListVoid";
                break;
            case 33:
                objArr[2] = "visitArgumentVoid";
                break;
            case 34:
                objArr[2] = "visitExpressionVoid";
                break;
            case 35:
                objArr[2] = "visitLoopExpressionVoid";
                break;
            case 36:
                objArr[2] = "visitConstantExpressionVoid";
                break;
            case 37:
                objArr[2] = "visitSimpleNameExpressionVoid";
                break;
            case 38:
                objArr[2] = "visitReferenceExpressionVoid";
                break;
            case 39:
                objArr[2] = "visitLabeledExpressionVoid";
                break;
            case 40:
                objArr[2] = "visitPrefixExpressionVoid";
                break;
            case 41:
                objArr[2] = "visitPostfixExpressionVoid";
                break;
            case 42:
                objArr[2] = "visitUnaryExpressionVoid";
                break;
            case 43:
                objArr[2] = "visitBinaryExpressionVoid";
                break;
            case 44:
                objArr[2] = "visitReturnExpressionVoid";
                break;
            case 45:
                objArr[2] = "visitExpressionWithLabelVoid";
                break;
            case 46:
                objArr[2] = "visitThrowExpressionVoid";
                break;
            case 47:
                objArr[2] = "visitBreakExpressionVoid";
                break;
            case 48:
                objArr[2] = "visitContinueExpressionVoid";
                break;
            case 49:
                objArr[2] = "visitIfExpressionVoid";
                break;
            case 50:
                objArr[2] = "visitWhenExpressionVoid";
                break;
            case 51:
                objArr[2] = "visitTryExpressionVoid";
                break;
            case 52:
                objArr[2] = "visitForExpressionVoid";
                break;
            case 53:
                objArr[2] = "visitWhileExpressionVoid";
                break;
            case 54:
                objArr[2] = "visitDoWhileExpressionVoid";
                break;
            case 55:
                objArr[2] = "visitLambdaExpressionVoid";
                break;
            case 56:
                objArr[2] = "visitAnnotatedExpressionVoid";
                break;
            case 57:
                objArr[2] = "visitCallExpressionVoid";
                break;
            case 58:
                objArr[2] = "visitArrayAccessExpressionVoid";
                break;
            case 59:
                objArr[2] = "visitQualifiedExpressionVoid";
                break;
            case 60:
                objArr[2] = "visitDoubleColonExpressionVoid";
                break;
            case 61:
                objArr[2] = "visitCallableReferenceExpressionVoid";
                break;
            case 62:
                objArr[2] = "visitClassLiteralExpressionVoid";
                break;
            case 63:
                objArr[2] = "visitDotQualifiedExpressionVoid";
                break;
            case 64:
                objArr[2] = "visitSafeQualifiedExpressionVoid";
                break;
            case 65:
                objArr[2] = "visitObjectLiteralExpressionVoid";
                break;
            case 66:
                objArr[2] = "visitBlockExpressionVoid";
                break;
            case 67:
                objArr[2] = "visitCatchSectionVoid";
                break;
            case 68:
                objArr[2] = "visitFinallySectionVoid";
                break;
            case 69:
                objArr[2] = "visitTypeArgumentListVoid";
                break;
            case 70:
                objArr[2] = "visitThisExpressionVoid";
                break;
            case 71:
                objArr[2] = "visitSuperExpressionVoid";
                break;
            case 72:
                objArr[2] = "visitParenthesizedExpressionVoid";
                break;
            case 73:
                objArr[2] = "visitInitializerListVoid";
                break;
            case 74:
                objArr[2] = "visitAnonymousInitializerVoid";
                break;
            case 75:
                objArr[2] = "visitPropertyAccessorVoid";
                break;
            case 76:
                objArr[2] = "visitTypeConstraintListVoid";
                break;
            case 77:
                objArr[2] = "visitTypeConstraintVoid";
                break;
            case 78:
                objArr[2] = "visitUserTypeVoid";
                break;
            case 79:
                objArr[2] = "visitDynamicTypeVoid";
                break;
            case 80:
                objArr[2] = "visitFunctionTypeVoid";
                break;
            case 81:
                objArr[2] = "visitSelfTypeVoid";
                break;
            case 82:
                objArr[2] = "visitBinaryWithTypeRHSExpressionVoid";
                break;
            case 83:
                objArr[2] = "visitStringTemplateExpressionVoid";
                break;
            case 84:
                objArr[2] = "visitNamedDeclarationVoid";
                break;
            case 85:
                objArr[2] = "visitNullableTypeVoid";
                break;
            case 86:
                objArr[2] = "visitTypeProjectionVoid";
                break;
            case 87:
                objArr[2] = "visitWhenEntryVoid";
                break;
            case 88:
                objArr[2] = "visitCollectionLiteralExpressionVoid";
                break;
            case 89:
                objArr[2] = "visitIsExpressionVoid";
                break;
            case 90:
                objArr[2] = "visitWhenConditionIsPatternVoid";
                break;
            case 91:
                objArr[2] = "visitWhenConditionInRangeVoid";
                break;
            case 92:
                objArr[2] = "visitWhenConditionWithExpressionVoid";
                break;
            case 93:
                objArr[2] = "visitObjectDeclarationVoid";
                break;
            case 94:
                objArr[2] = "visitStringTemplateEntryVoid";
                break;
            case 95:
                objArr[2] = "visitStringTemplateEntryWithExpressionVoid";
                break;
            case 96:
                objArr[2] = "visitBlockStringTemplateEntryVoid";
                break;
            case 97:
                objArr[2] = "visitSimpleNameStringTemplateEntryVoid";
                break;
            case 98:
                objArr[2] = "visitLiteralStringTemplateEntryVoid";
                break;
            case 99:
                objArr[2] = "visitEscapeStringTemplateEntryVoid";
                break;
            case 100:
                objArr[2] = "visitKtElement";
                break;
            case 101:
                objArr[2] = "visitDeclaration";
                break;
            case 102:
                objArr[2] = "visitClass";
                break;
            case 103:
                objArr[2] = "visitPrimaryConstructor";
                break;
            case 104:
                objArr[2] = "visitNamedFunction";
                break;
            case 105:
                objArr[2] = "visitProperty";
                break;
            case 106:
                objArr[2] = "visitDestructuringDeclaration";
                break;
            case 107:
                objArr[2] = "visitDestructuringDeclarationEntry";
                break;
            case 108:
                objArr[2] = "visitKtFile";
                break;
            case 109:
                objArr[2] = "visitScript";
                break;
            case 110:
                objArr[2] = "visitImportDirective";
                break;
            case 111:
                objArr[2] = "visitImportList";
                break;
            case 112:
                objArr[2] = "visitClassBody";
                break;
            case 113:
                objArr[2] = "visitModifierList";
                break;
            case 114:
                objArr[2] = "visitAnnotation";
                break;
            case 115:
                objArr[2] = "visitAnnotationEntry";
                break;
            case 116:
                objArr[2] = "visitTypeParameterList";
                break;
            case 117:
                objArr[2] = "visitTypeParameter";
                break;
            case 118:
                objArr[2] = "visitEnumEntry";
                break;
            case 119:
                objArr[2] = "visitParameterList";
                break;
            case 120:
                objArr[2] = "visitParameter";
                break;
            case 121:
                objArr[2] = "visitSuperTypeList";
                break;
            case 122:
                objArr[2] = "visitSuperTypeListEntry";
                break;
            case 123:
                objArr[2] = "visitDelegatedSuperTypeEntry";
                break;
            case 124:
                objArr[2] = "visitSuperTypeCallEntry";
                break;
            case 125:
                objArr[2] = "visitSuperTypeEntry";
                break;
            case 126:
                objArr[2] = "visitConstructorDelegationCall";
                break;
            case 127:
                objArr[2] = "visitPropertyDelegate";
                break;
            case 128:
                objArr[2] = "visitTypeReference";
                break;
            case 129:
                objArr[2] = "visitValueArgumentList";
                break;
            case 130:
                objArr[2] = "visitArgument";
                break;
            case 131:
                objArr[2] = "visitExpression";
                break;
            case 132:
                objArr[2] = "visitLoopExpression";
                break;
            case 133:
                objArr[2] = "visitConstantExpression";
                break;
            case 134:
                objArr[2] = "visitSimpleNameExpression";
                break;
            case 135:
                objArr[2] = "visitReferenceExpression";
                break;
            case 136:
                objArr[2] = "visitLabeledExpression";
                break;
            case 137:
                objArr[2] = "visitPrefixExpression";
                break;
            case 138:
                objArr[2] = "visitPostfixExpression";
                break;
            case 139:
                objArr[2] = "visitUnaryExpression";
                break;
            case 140:
                objArr[2] = "visitBinaryExpression";
                break;
            case 141:
                objArr[2] = "visitReturnExpression";
                break;
            case 142:
                objArr[2] = "visitExpressionWithLabel";
                break;
            case 143:
                objArr[2] = "visitThrowExpression";
                break;
            case 144:
                objArr[2] = "visitBreakExpression";
                break;
            case 145:
                objArr[2] = "visitContinueExpression";
                break;
            case 146:
                objArr[2] = "visitIfExpression";
                break;
            case 147:
                objArr[2] = "visitWhenExpression";
                break;
            case 148:
                objArr[2] = "visitCollectionLiteralExpression";
                break;
            case 149:
                objArr[2] = "visitTryExpression";
                break;
            case 150:
                objArr[2] = "visitForExpression";
                break;
            case 151:
                objArr[2] = "visitWhileExpression";
                break;
            case 152:
                objArr[2] = "visitDoWhileExpression";
                break;
            case 153:
                objArr[2] = "visitLambdaExpression";
                break;
            case 154:
                objArr[2] = "visitAnnotatedExpression";
                break;
            case 155:
                objArr[2] = "visitCallExpression";
                break;
            case 156:
                objArr[2] = "visitArrayAccessExpression";
                break;
            case 157:
                objArr[2] = "visitQualifiedExpression";
                break;
            case 158:
                objArr[2] = "visitDoubleColonExpression";
                break;
            case 159:
                objArr[2] = "visitCallableReferenceExpression";
                break;
            case 160:
                objArr[2] = "visitClassLiteralExpression";
                break;
            case 161:
                objArr[2] = "visitDotQualifiedExpression";
                break;
            case 162:
                objArr[2] = "visitSafeQualifiedExpression";
                break;
            case 163:
                objArr[2] = "visitObjectLiteralExpression";
                break;
            case 164:
                objArr[2] = "visitBlockExpression";
                break;
            case 165:
                objArr[2] = "visitCatchSection";
                break;
            case 166:
                objArr[2] = "visitFinallySection";
                break;
            case 167:
                objArr[2] = "visitTypeArgumentList";
                break;
            case 168:
                objArr[2] = "visitThisExpression";
                break;
            case 169:
                objArr[2] = "visitSuperExpression";
                break;
            case 170:
                objArr[2] = "visitParenthesizedExpression";
                break;
            case 171:
                objArr[2] = "visitInitializerList";
                break;
            case 172:
                objArr[2] = "visitAnonymousInitializer";
                break;
            case 173:
                objArr[2] = "visitPropertyAccessor";
                break;
            case 174:
                objArr[2] = "visitTypeConstraintList";
                break;
            case 175:
                objArr[2] = "visitTypeConstraint";
                break;
            case 176:
                objArr[2] = "visitUserType";
                break;
            case 177:
                objArr[2] = "visitDynamicType";
                break;
            case 178:
                objArr[2] = "visitFunctionType";
                break;
            case 179:
                objArr[2] = "visitSelfType";
                break;
            case 180:
                objArr[2] = "visitBinaryWithTypeRHSExpression";
                break;
            case 181:
                objArr[2] = "visitStringTemplateExpression";
                break;
            case 182:
                objArr[2] = "visitNamedDeclaration";
                break;
            case 183:
                objArr[2] = "visitNullableType";
                break;
            case 184:
                objArr[2] = "visitTypeProjection";
                break;
            case 185:
                objArr[2] = "visitWhenEntry";
                break;
            case 186:
                objArr[2] = "visitIsExpression";
                break;
            case 187:
                objArr[2] = "visitWhenConditionIsPattern";
                break;
            case 188:
                objArr[2] = "visitWhenConditionInRange";
                break;
            case 189:
                objArr[2] = "visitWhenConditionWithExpression";
                break;
            case 190:
                objArr[2] = "visitObjectDeclaration";
                break;
            case 191:
                objArr[2] = "visitStringTemplateEntry";
                break;
            case 192:
                objArr[2] = "visitStringTemplateEntryWithExpression";
                break;
            case 193:
                objArr[2] = "visitBlockStringTemplateEntry";
                break;
            case 194:
                objArr[2] = "visitSimpleNameStringTemplateEntry";
                break;
            case 195:
                objArr[2] = "visitLiteralStringTemplateEntry";
                break;
            case 196:
                objArr[2] = "visitEscapeStringTemplateEntry";
                break;
            default:
                objArr[2] = "visitKtElementVoid";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public void visitKtElementVoid(KtElement ktElement, P p) {
        if (ktElement == null) {
            $$$reportNull$$$0(0);
        }
        super.visitKtElement(ktElement, p);
    }

    public void visitDeclarationVoid(KtDeclaration ktDeclaration, P p) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(1);
        }
        super.visitDeclaration(ktDeclaration, p);
    }

    public void visitClassVoid(KtClass ktClass, P p) {
        if (ktClass == null) {
            $$$reportNull$$$0(2);
        }
        super.visitClass(ktClass, p);
    }

    public void visitSecondaryConstructorVoid(KtSecondaryConstructor ktSecondaryConstructor, P p) {
        if (ktSecondaryConstructor == null) {
            $$$reportNull$$$0(3);
        }
        super.visitSecondaryConstructor(ktSecondaryConstructor, p);
    }

    public void visitPrimaryConstructorVoid(KtPrimaryConstructor ktPrimaryConstructor, P p) {
        if (ktPrimaryConstructor == null) {
            $$$reportNull$$$0(4);
        }
        super.visitPrimaryConstructor(ktPrimaryConstructor, p);
    }

    public void visitNamedFunctionVoid(KtNamedFunction ktNamedFunction, P p) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(5);
        }
        super.visitNamedFunction(ktNamedFunction, p);
    }

    public void visitPropertyVoid(KtProperty ktProperty, P p) {
        if (ktProperty == null) {
            $$$reportNull$$$0(6);
        }
        super.visitProperty(ktProperty, p);
    }

    public void visitDestructuringDeclarationVoid(KtDestructuringDeclaration ktDestructuringDeclaration, P p) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(7);
        }
        super.visitDestructuringDeclaration(ktDestructuringDeclaration, p);
    }

    public void visitDestructuringDeclarationEntryVoid(KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry, P p) {
        if (ktDestructuringDeclarationEntry == null) {
            $$$reportNull$$$0(8);
        }
        super.visitDestructuringDeclarationEntry(ktDestructuringDeclarationEntry, p);
    }

    public void visitKtFileVoid(KtFile ktFile, P p) {
        if (ktFile == null) {
            $$$reportNull$$$0(9);
        }
        super.visitKtFile(ktFile, p);
    }

    public void visitScriptVoid(KtScript ktScript, P p) {
        if (ktScript == null) {
            $$$reportNull$$$0(10);
        }
        super.visitScript(ktScript, p);
    }

    public void visitImportDirectiveVoid(KtImportDirective ktImportDirective, P p) {
        if (ktImportDirective == null) {
            $$$reportNull$$$0(11);
        }
        super.visitImportDirective(ktImportDirective, p);
    }

    public void visitImportAliasVoid(KtImportAlias ktImportAlias, P p) {
        if (ktImportAlias == null) {
            $$$reportNull$$$0(12);
        }
        super.visitImportAlias(ktImportAlias, p);
    }

    public void visitImportListVoid(KtImportList ktImportList, P p) {
        if (ktImportList == null) {
            $$$reportNull$$$0(13);
        }
        super.visitImportList(ktImportList, p);
    }

    public void visitClassBodyVoid(KtClassBody ktClassBody, P p) {
        if (ktClassBody == null) {
            $$$reportNull$$$0(14);
        }
        super.visitClassBody(ktClassBody, p);
    }

    public void visitModifierListVoid(KtModifierList ktModifierList, P p) {
        if (ktModifierList == null) {
            $$$reportNull$$$0(15);
        }
        super.visitModifierList(ktModifierList, p);
    }

    public void visitAnnotationVoid(KtAnnotation ktAnnotation, P p) {
        if (ktAnnotation == null) {
            $$$reportNull$$$0(16);
        }
        super.visitAnnotation(ktAnnotation, p);
    }

    public void visitAnnotationEntryVoid(KtAnnotationEntry ktAnnotationEntry, P p) {
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(17);
        }
        super.visitAnnotationEntry(ktAnnotationEntry, p);
    }

    public void visitConstructorCalleeExpressionVoid(KtConstructorCalleeExpression ktConstructorCalleeExpression, P p) {
        if (ktConstructorCalleeExpression == null) {
            $$$reportNull$$$0(18);
        }
        super.visitConstructorCalleeExpression(ktConstructorCalleeExpression, p);
    }

    public void visitTypeParameterListVoid(KtTypeParameterList ktTypeParameterList, P p) {
        if (ktTypeParameterList == null) {
            $$$reportNull$$$0(19);
        }
        super.visitTypeParameterList(ktTypeParameterList, p);
    }

    public void visitTypeParameterVoid(KtTypeParameter ktTypeParameter, P p) {
        if (ktTypeParameter == null) {
            $$$reportNull$$$0(20);
        }
        super.visitTypeParameter(ktTypeParameter, p);
    }

    public void visitEnumEntryVoid(KtEnumEntry ktEnumEntry, P p) {
        if (ktEnumEntry == null) {
            $$$reportNull$$$0(21);
        }
        super.visitEnumEntry(ktEnumEntry, p);
    }

    public void visitParameterListVoid(KtParameterList ktParameterList, P p) {
        if (ktParameterList == null) {
            $$$reportNull$$$0(22);
        }
        super.visitParameterList(ktParameterList, p);
    }

    public void visitParameterVoid(KtParameter ktParameter, P p) {
        if (ktParameter == null) {
            $$$reportNull$$$0(23);
        }
        super.visitParameter(ktParameter, p);
    }

    public void visitSuperTypeListVoid(KtSuperTypeList ktSuperTypeList, P p) {
        if (ktSuperTypeList == null) {
            $$$reportNull$$$0(24);
        }
        super.visitSuperTypeList(ktSuperTypeList, p);
    }

    public void visitSuperTypeListEntryVoid(KtSuperTypeListEntry ktSuperTypeListEntry, P p) {
        if (ktSuperTypeListEntry == null) {
            $$$reportNull$$$0(25);
        }
        super.visitSuperTypeListEntry(ktSuperTypeListEntry, p);
    }

    public void visitDelegatedSuperTypeEntryVoid(KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry, P p) {
        if (ktDelegatedSuperTypeEntry == null) {
            $$$reportNull$$$0(26);
        }
        super.visitDelegatedSuperTypeEntry(ktDelegatedSuperTypeEntry, p);
    }

    public void visitSuperTypeCallEntryVoid(KtSuperTypeCallEntry ktSuperTypeCallEntry, P p) {
        if (ktSuperTypeCallEntry == null) {
            $$$reportNull$$$0(27);
        }
        super.visitSuperTypeCallEntry(ktSuperTypeCallEntry, p);
    }

    public void visitSuperTypeEntryVoid(KtSuperTypeEntry ktSuperTypeEntry, P p) {
        if (ktSuperTypeEntry == null) {
            $$$reportNull$$$0(28);
        }
        super.visitSuperTypeEntry(ktSuperTypeEntry, p);
    }

    public void visitConstructorDelegationCallVoid(KtConstructorDelegationCall ktConstructorDelegationCall, P p) {
        if (ktConstructorDelegationCall == null) {
            $$$reportNull$$$0(29);
        }
        super.visitConstructorDelegationCall(ktConstructorDelegationCall, p);
    }

    public void visitPropertyDelegateVoid(KtPropertyDelegate ktPropertyDelegate, P p) {
        if (ktPropertyDelegate == null) {
            $$$reportNull$$$0(30);
        }
        super.visitPropertyDelegate(ktPropertyDelegate, p);
    }

    public void visitTypeReferenceVoid(KtTypeReference ktTypeReference, P p) {
        if (ktTypeReference == null) {
            $$$reportNull$$$0(31);
        }
        super.visitTypeReference(ktTypeReference, p);
    }

    public void visitValueArgumentListVoid(KtValueArgumentList ktValueArgumentList, P p) {
        if (ktValueArgumentList == null) {
            $$$reportNull$$$0(32);
        }
        super.visitValueArgumentList(ktValueArgumentList, p);
    }

    public void visitArgumentVoid(KtValueArgument ktValueArgument, P p) {
        if (ktValueArgument == null) {
            $$$reportNull$$$0(33);
        }
        super.visitArgument(ktValueArgument, p);
    }

    public void visitExpressionVoid(KtExpression ktExpression, P p) {
        if (ktExpression == null) {
            $$$reportNull$$$0(34);
        }
        super.visitExpression(ktExpression, p);
    }

    public void visitLoopExpressionVoid(KtLoopExpression ktLoopExpression, P p) {
        if (ktLoopExpression == null) {
            $$$reportNull$$$0(35);
        }
        super.visitLoopExpression(ktLoopExpression, p);
    }

    public void visitConstantExpressionVoid(KtConstantExpression ktConstantExpression, P p) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(36);
        }
        super.visitConstantExpression(ktConstantExpression, p);
    }

    public void visitSimpleNameExpressionVoid(KtSimpleNameExpression ktSimpleNameExpression, P p) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(37);
        }
        super.visitSimpleNameExpression(ktSimpleNameExpression, p);
    }

    public void visitReferenceExpressionVoid(KtReferenceExpression ktReferenceExpression, P p) {
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(38);
        }
        super.visitReferenceExpression(ktReferenceExpression, p);
    }

    public void visitLabeledExpressionVoid(KtLabeledExpression ktLabeledExpression, P p) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(39);
        }
        super.visitLabeledExpression(ktLabeledExpression, p);
    }

    public void visitPrefixExpressionVoid(KtPrefixExpression ktPrefixExpression, P p) {
        if (ktPrefixExpression == null) {
            $$$reportNull$$$0(40);
        }
        super.visitPrefixExpression(ktPrefixExpression, p);
    }

    public void visitPostfixExpressionVoid(KtPostfixExpression ktPostfixExpression, P p) {
        if (ktPostfixExpression == null) {
            $$$reportNull$$$0(41);
        }
        super.visitPostfixExpression(ktPostfixExpression, p);
    }

    public void visitUnaryExpressionVoid(KtUnaryExpression ktUnaryExpression, P p) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(42);
        }
        super.visitUnaryExpression(ktUnaryExpression, p);
    }

    public void visitBinaryExpressionVoid(KtBinaryExpression ktBinaryExpression, P p) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(43);
        }
        super.visitBinaryExpression(ktBinaryExpression, p);
    }

    public void visitReturnExpressionVoid(KtReturnExpression ktReturnExpression, P p) {
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(44);
        }
        super.visitReturnExpression(ktReturnExpression, p);
    }

    public void visitExpressionWithLabelVoid(KtExpressionWithLabel ktExpressionWithLabel, P p) {
        if (ktExpressionWithLabel == null) {
            $$$reportNull$$$0(45);
        }
        super.visitExpressionWithLabel(ktExpressionWithLabel, p);
    }

    public void visitThrowExpressionVoid(KtThrowExpression ktThrowExpression, P p) {
        if (ktThrowExpression == null) {
            $$$reportNull$$$0(46);
        }
        super.visitThrowExpression(ktThrowExpression, p);
    }

    public void visitBreakExpressionVoid(KtBreakExpression ktBreakExpression, P p) {
        if (ktBreakExpression == null) {
            $$$reportNull$$$0(47);
        }
        super.visitBreakExpression(ktBreakExpression, p);
    }

    public void visitContinueExpressionVoid(KtContinueExpression ktContinueExpression, P p) {
        if (ktContinueExpression == null) {
            $$$reportNull$$$0(48);
        }
        super.visitContinueExpression(ktContinueExpression, p);
    }

    public void visitIfExpressionVoid(KtIfExpression ktIfExpression, P p) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(49);
        }
        super.visitIfExpression(ktIfExpression, p);
    }

    public void visitWhenExpressionVoid(KtWhenExpression ktWhenExpression, P p) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(50);
        }
        super.visitWhenExpression(ktWhenExpression, p);
    }

    public void visitTryExpressionVoid(KtTryExpression ktTryExpression, P p) {
        if (ktTryExpression == null) {
            $$$reportNull$$$0(51);
        }
        super.visitTryExpression(ktTryExpression, p);
    }

    public void visitForExpressionVoid(KtForExpression ktForExpression, P p) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(52);
        }
        super.visitForExpression(ktForExpression, p);
    }

    public void visitWhileExpressionVoid(KtWhileExpression ktWhileExpression, P p) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(53);
        }
        super.visitWhileExpression(ktWhileExpression, p);
    }

    public void visitDoWhileExpressionVoid(KtDoWhileExpression ktDoWhileExpression, P p) {
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(54);
        }
        super.visitDoWhileExpression(ktDoWhileExpression, p);
    }

    public void visitLambdaExpressionVoid(KtLambdaExpression ktLambdaExpression, P p) {
        if (ktLambdaExpression == null) {
            $$$reportNull$$$0(55);
        }
        super.visitLambdaExpression(ktLambdaExpression, p);
    }

    public void visitAnnotatedExpressionVoid(KtAnnotatedExpression ktAnnotatedExpression, P p) {
        if (ktAnnotatedExpression == null) {
            $$$reportNull$$$0(56);
        }
        super.visitAnnotatedExpression(ktAnnotatedExpression, p);
    }

    public void visitCallExpressionVoid(KtCallExpression ktCallExpression, P p) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(57);
        }
        super.visitCallExpression(ktCallExpression, p);
    }

    public void visitArrayAccessExpressionVoid(KtArrayAccessExpression ktArrayAccessExpression, P p) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(58);
        }
        super.visitArrayAccessExpression(ktArrayAccessExpression, p);
    }

    public void visitQualifiedExpressionVoid(KtQualifiedExpression ktQualifiedExpression, P p) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(59);
        }
        super.visitQualifiedExpression(ktQualifiedExpression, p);
    }

    public void visitDoubleColonExpressionVoid(KtDoubleColonExpression ktDoubleColonExpression, P p) {
        if (ktDoubleColonExpression == null) {
            $$$reportNull$$$0(60);
        }
        super.visitDoubleColonExpression(ktDoubleColonExpression, p);
    }

    public void visitCallableReferenceExpressionVoid(KtCallableReferenceExpression ktCallableReferenceExpression, P p) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(61);
        }
        super.visitCallableReferenceExpression(ktCallableReferenceExpression, p);
    }

    public void visitClassLiteralExpressionVoid(KtClassLiteralExpression ktClassLiteralExpression, P p) {
        if (ktClassLiteralExpression == null) {
            $$$reportNull$$$0(62);
        }
        super.visitClassLiteralExpression(ktClassLiteralExpression, p);
    }

    public void visitDotQualifiedExpressionVoid(KtDotQualifiedExpression ktDotQualifiedExpression, P p) {
        if (ktDotQualifiedExpression == null) {
            $$$reportNull$$$0(63);
        }
        super.visitDotQualifiedExpression(ktDotQualifiedExpression, p);
    }

    public void visitSafeQualifiedExpressionVoid(KtSafeQualifiedExpression ktSafeQualifiedExpression, P p) {
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(64);
        }
        super.visitSafeQualifiedExpression(ktSafeQualifiedExpression, p);
    }

    public void visitObjectLiteralExpressionVoid(KtObjectLiteralExpression ktObjectLiteralExpression, P p) {
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(65);
        }
        super.visitObjectLiteralExpression(ktObjectLiteralExpression, p);
    }

    public void visitBlockExpressionVoid(KtBlockExpression ktBlockExpression, P p) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(66);
        }
        super.visitBlockExpression(ktBlockExpression, p);
    }

    public void visitCatchSectionVoid(KtCatchClause ktCatchClause, P p) {
        if (ktCatchClause == null) {
            $$$reportNull$$$0(67);
        }
        super.visitCatchSection(ktCatchClause, p);
    }

    public void visitFinallySectionVoid(KtFinallySection ktFinallySection, P p) {
        if (ktFinallySection == null) {
            $$$reportNull$$$0(68);
        }
        super.visitFinallySection(ktFinallySection, p);
    }

    public void visitTypeArgumentListVoid(KtTypeArgumentList ktTypeArgumentList, P p) {
        if (ktTypeArgumentList == null) {
            $$$reportNull$$$0(69);
        }
        super.visitTypeArgumentList(ktTypeArgumentList, p);
    }

    public void visitThisExpressionVoid(KtThisExpression ktThisExpression, P p) {
        if (ktThisExpression == null) {
            $$$reportNull$$$0(70);
        }
        super.visitThisExpression(ktThisExpression, p);
    }

    public void visitSuperExpressionVoid(KtSuperExpression ktSuperExpression, P p) {
        if (ktSuperExpression == null) {
            $$$reportNull$$$0(71);
        }
        super.visitSuperExpression(ktSuperExpression, p);
    }

    public void visitParenthesizedExpressionVoid(KtParenthesizedExpression ktParenthesizedExpression, P p) {
        if (ktParenthesizedExpression == null) {
            $$$reportNull$$$0(72);
        }
        super.visitParenthesizedExpression(ktParenthesizedExpression, p);
    }

    public void visitInitializerListVoid(KtInitializerList ktInitializerList, P p) {
        if (ktInitializerList == null) {
            $$$reportNull$$$0(73);
        }
        super.visitInitializerList(ktInitializerList, p);
    }

    public void visitAnonymousInitializerVoid(KtAnonymousInitializer ktAnonymousInitializer, P p) {
        if (ktAnonymousInitializer == null) {
            $$$reportNull$$$0(74);
        }
        super.visitAnonymousInitializer(ktAnonymousInitializer, p);
    }

    public void visitPropertyAccessorVoid(KtPropertyAccessor ktPropertyAccessor, P p) {
        if (ktPropertyAccessor == null) {
            $$$reportNull$$$0(75);
        }
        super.visitPropertyAccessor(ktPropertyAccessor, p);
    }

    public void visitTypeConstraintListVoid(KtTypeConstraintList ktTypeConstraintList, P p) {
        if (ktTypeConstraintList == null) {
            $$$reportNull$$$0(76);
        }
        super.visitTypeConstraintList(ktTypeConstraintList, p);
    }

    public void visitTypeConstraintVoid(KtTypeConstraint ktTypeConstraint, P p) {
        if (ktTypeConstraint == null) {
            $$$reportNull$$$0(77);
        }
        super.visitTypeConstraint(ktTypeConstraint, p);
    }

    public void visitUserTypeVoid(KtUserType ktUserType, P p) {
        if (ktUserType == null) {
            $$$reportNull$$$0(78);
        }
        super.visitUserType(ktUserType, p);
    }

    public void visitDynamicTypeVoid(KtDynamicType ktDynamicType, P p) {
        if (ktDynamicType == null) {
            $$$reportNull$$$0(79);
        }
        super.visitDynamicType(ktDynamicType, p);
    }

    public void visitFunctionTypeVoid(KtFunctionType ktFunctionType, P p) {
        if (ktFunctionType == null) {
            $$$reportNull$$$0(80);
        }
        super.visitFunctionType(ktFunctionType, p);
    }

    public void visitSelfTypeVoid(KtSelfType ktSelfType, P p) {
        if (ktSelfType == null) {
            $$$reportNull$$$0(81);
        }
        super.visitSelfType(ktSelfType, p);
    }

    public void visitBinaryWithTypeRHSExpressionVoid(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, P p) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(82);
        }
        super.visitBinaryWithTypeRHSExpression(ktBinaryExpressionWithTypeRHS, p);
    }

    public void visitStringTemplateExpressionVoid(KtStringTemplateExpression ktStringTemplateExpression, P p) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(83);
        }
        super.visitStringTemplateExpression(ktStringTemplateExpression, p);
    }

    public void visitNamedDeclarationVoid(KtNamedDeclaration ktNamedDeclaration, P p) {
        if (ktNamedDeclaration == null) {
            $$$reportNull$$$0(84);
        }
        super.visitNamedDeclaration(ktNamedDeclaration, p);
    }

    public void visitNullableTypeVoid(KtNullableType ktNullableType, P p) {
        if (ktNullableType == null) {
            $$$reportNull$$$0(85);
        }
        super.visitNullableType(ktNullableType, p);
    }

    public void visitTypeProjectionVoid(KtTypeProjection ktTypeProjection, P p) {
        if (ktTypeProjection == null) {
            $$$reportNull$$$0(86);
        }
        super.visitTypeProjection(ktTypeProjection, p);
    }

    public void visitWhenEntryVoid(KtWhenEntry ktWhenEntry, P p) {
        if (ktWhenEntry == null) {
            $$$reportNull$$$0(87);
        }
        super.visitWhenEntry(ktWhenEntry, p);
    }

    public void visitCollectionLiteralExpressionVoid(KtCollectionLiteralExpression ktCollectionLiteralExpression, P p) {
        if (ktCollectionLiteralExpression == null) {
            $$$reportNull$$$0(88);
        }
        super.visitCollectionLiteralExpression(ktCollectionLiteralExpression, p);
    }

    public void visitIsExpressionVoid(KtIsExpression ktIsExpression, P p) {
        if (ktIsExpression == null) {
            $$$reportNull$$$0(89);
        }
        super.visitIsExpression(ktIsExpression, p);
    }

    public void visitWhenConditionIsPatternVoid(KtWhenConditionIsPattern ktWhenConditionIsPattern, P p) {
        if (ktWhenConditionIsPattern == null) {
            $$$reportNull$$$0(90);
        }
        super.visitWhenConditionIsPattern(ktWhenConditionIsPattern, p);
    }

    public void visitWhenConditionInRangeVoid(KtWhenConditionInRange ktWhenConditionInRange, P p) {
        if (ktWhenConditionInRange == null) {
            $$$reportNull$$$0(91);
        }
        super.visitWhenConditionInRange(ktWhenConditionInRange, p);
    }

    public void visitWhenConditionWithExpressionVoid(KtWhenConditionWithExpression ktWhenConditionWithExpression, P p) {
        if (ktWhenConditionWithExpression == null) {
            $$$reportNull$$$0(92);
        }
        super.visitWhenConditionWithExpression(ktWhenConditionWithExpression, p);
    }

    public void visitObjectDeclarationVoid(KtObjectDeclaration ktObjectDeclaration, P p) {
        if (ktObjectDeclaration == null) {
            $$$reportNull$$$0(93);
        }
        super.visitObjectDeclaration(ktObjectDeclaration, p);
    }

    public void visitStringTemplateEntryVoid(KtStringTemplateEntry ktStringTemplateEntry, P p) {
        if (ktStringTemplateEntry == null) {
            $$$reportNull$$$0(94);
        }
        super.visitStringTemplateEntry(ktStringTemplateEntry, p);
    }

    public void visitStringTemplateEntryWithExpressionVoid(KtStringTemplateEntryWithExpression ktStringTemplateEntryWithExpression, P p) {
        if (ktStringTemplateEntryWithExpression == null) {
            $$$reportNull$$$0(95);
        }
        super.visitStringTemplateEntryWithExpression(ktStringTemplateEntryWithExpression, p);
    }

    public void visitBlockStringTemplateEntryVoid(KtBlockStringTemplateEntry ktBlockStringTemplateEntry, P p) {
        if (ktBlockStringTemplateEntry == null) {
            $$$reportNull$$$0(96);
        }
        super.visitBlockStringTemplateEntry(ktBlockStringTemplateEntry, p);
    }

    public void visitSimpleNameStringTemplateEntryVoid(KtSimpleNameStringTemplateEntry ktSimpleNameStringTemplateEntry, P p) {
        if (ktSimpleNameStringTemplateEntry == null) {
            $$$reportNull$$$0(97);
        }
        super.visitSimpleNameStringTemplateEntry(ktSimpleNameStringTemplateEntry, p);
    }

    public void visitLiteralStringTemplateEntryVoid(KtLiteralStringTemplateEntry ktLiteralStringTemplateEntry, P p) {
        if (ktLiteralStringTemplateEntry == null) {
            $$$reportNull$$$0(98);
        }
        super.visitLiteralStringTemplateEntry(ktLiteralStringTemplateEntry, p);
    }

    public void visitEscapeStringTemplateEntryVoid(KtEscapeStringTemplateEntry ktEscapeStringTemplateEntry, P p) {
        if (ktEscapeStringTemplateEntry == null) {
            $$$reportNull$$$0(99);
        }
        super.visitEscapeStringTemplateEntry(ktEscapeStringTemplateEntry, p);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitKtElement(KtElement ktElement, P p) {
        if (ktElement == null) {
            $$$reportNull$$$0(100);
        }
        visitKtElementVoid(ktElement, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDeclaration(KtDeclaration ktDeclaration, P p) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(101);
        }
        visitDeclarationVoid(ktDeclaration, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitClass(KtClass ktClass, P p) {
        if (ktClass == null) {
            $$$reportNull$$$0(102);
        }
        visitClassVoid(ktClass, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitPrimaryConstructor(KtPrimaryConstructor ktPrimaryConstructor, P p) {
        if (ktPrimaryConstructor == null) {
            $$$reportNull$$$0(103);
        }
        visitPrimaryConstructorVoid(ktPrimaryConstructor, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitNamedFunction(KtNamedFunction ktNamedFunction, P p) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(104);
        }
        visitNamedFunctionVoid(ktNamedFunction, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitProperty(KtProperty ktProperty, P p) {
        if (ktProperty == null) {
            $$$reportNull$$$0(105);
        }
        visitPropertyVoid(ktProperty, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDestructuringDeclaration(KtDestructuringDeclaration ktDestructuringDeclaration, P p) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(106);
        }
        visitDestructuringDeclarationVoid(ktDestructuringDeclaration, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDestructuringDeclarationEntry(KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry, P p) {
        if (ktDestructuringDeclarationEntry == null) {
            $$$reportNull$$$0(107);
        }
        visitDestructuringDeclarationEntryVoid(ktDestructuringDeclarationEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitKtFile(KtFile ktFile, P p) {
        if (ktFile == null) {
            $$$reportNull$$$0(108);
        }
        visitKtFileVoid(ktFile, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitScript(KtScript ktScript, P p) {
        if (ktScript == null) {
            $$$reportNull$$$0(109);
        }
        visitScriptVoid(ktScript, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitImportDirective(KtImportDirective ktImportDirective, P p) {
        if (ktImportDirective == null) {
            $$$reportNull$$$0(110);
        }
        visitImportDirectiveVoid(ktImportDirective, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitImportList(KtImportList ktImportList, P p) {
        if (ktImportList == null) {
            $$$reportNull$$$0(111);
        }
        visitImportListVoid(ktImportList, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitClassBody(KtClassBody ktClassBody, P p) {
        if (ktClassBody == null) {
            $$$reportNull$$$0(112);
        }
        visitClassBodyVoid(ktClassBody, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitModifierList(KtModifierList ktModifierList, P p) {
        if (ktModifierList == null) {
            $$$reportNull$$$0(113);
        }
        visitModifierListVoid(ktModifierList, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitAnnotation(KtAnnotation ktAnnotation, P p) {
        if (ktAnnotation == null) {
            $$$reportNull$$$0(114);
        }
        visitAnnotationVoid(ktAnnotation, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitAnnotationEntry(KtAnnotationEntry ktAnnotationEntry, P p) {
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(115);
        }
        visitAnnotationEntryVoid(ktAnnotationEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeParameterList(KtTypeParameterList ktTypeParameterList, P p) {
        if (ktTypeParameterList == null) {
            $$$reportNull$$$0(116);
        }
        visitTypeParameterListVoid(ktTypeParameterList, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeParameter(KtTypeParameter ktTypeParameter, P p) {
        if (ktTypeParameter == null) {
            $$$reportNull$$$0(117);
        }
        visitTypeParameterVoid(ktTypeParameter, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitEnumEntry(KtEnumEntry ktEnumEntry, P p) {
        if (ktEnumEntry == null) {
            $$$reportNull$$$0(118);
        }
        visitEnumEntryVoid(ktEnumEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitParameterList(KtParameterList ktParameterList, P p) {
        if (ktParameterList == null) {
            $$$reportNull$$$0(119);
        }
        visitParameterListVoid(ktParameterList, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitParameter(KtParameter ktParameter, P p) {
        if (ktParameter == null) {
            $$$reportNull$$$0(120);
        }
        visitParameterVoid(ktParameter, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSuperTypeList(KtSuperTypeList ktSuperTypeList, P p) {
        if (ktSuperTypeList == null) {
            $$$reportNull$$$0(121);
        }
        visitSuperTypeListVoid(ktSuperTypeList, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSuperTypeListEntry(KtSuperTypeListEntry ktSuperTypeListEntry, P p) {
        if (ktSuperTypeListEntry == null) {
            $$$reportNull$$$0(122);
        }
        visitSuperTypeListEntryVoid(ktSuperTypeListEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDelegatedSuperTypeEntry(KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry, P p) {
        if (ktDelegatedSuperTypeEntry == null) {
            $$$reportNull$$$0(123);
        }
        visitDelegatedSuperTypeEntryVoid(ktDelegatedSuperTypeEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSuperTypeCallEntry(KtSuperTypeCallEntry ktSuperTypeCallEntry, P p) {
        if (ktSuperTypeCallEntry == null) {
            $$$reportNull$$$0(124);
        }
        visitSuperTypeCallEntryVoid(ktSuperTypeCallEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSuperTypeEntry(KtSuperTypeEntry ktSuperTypeEntry, P p) {
        if (ktSuperTypeEntry == null) {
            $$$reportNull$$$0(125);
        }
        visitSuperTypeEntryVoid(ktSuperTypeEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitConstructorDelegationCall(KtConstructorDelegationCall ktConstructorDelegationCall, P p) {
        if (ktConstructorDelegationCall == null) {
            $$$reportNull$$$0(126);
        }
        visitConstructorDelegationCallVoid(ktConstructorDelegationCall, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitPropertyDelegate(KtPropertyDelegate ktPropertyDelegate, P p) {
        if (ktPropertyDelegate == null) {
            $$$reportNull$$$0(127);
        }
        visitPropertyDelegateVoid(ktPropertyDelegate, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeReference(KtTypeReference ktTypeReference, P p) {
        if (ktTypeReference == null) {
            $$$reportNull$$$0(128);
        }
        visitTypeReferenceVoid(ktTypeReference, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitValueArgumentList(KtValueArgumentList ktValueArgumentList, P p) {
        if (ktValueArgumentList == null) {
            $$$reportNull$$$0(129);
        }
        visitValueArgumentListVoid(ktValueArgumentList, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitArgument(KtValueArgument ktValueArgument, P p) {
        if (ktValueArgument == null) {
            $$$reportNull$$$0(130);
        }
        visitArgumentVoid(ktValueArgument, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitExpression(KtExpression ktExpression, P p) {
        if (ktExpression == null) {
            $$$reportNull$$$0(131);
        }
        visitExpressionVoid(ktExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitLoopExpression(KtLoopExpression ktLoopExpression, P p) {
        if (ktLoopExpression == null) {
            $$$reportNull$$$0(132);
        }
        visitLoopExpressionVoid(ktLoopExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitConstantExpression(KtConstantExpression ktConstantExpression, P p) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(133);
        }
        visitConstantExpressionVoid(ktConstantExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, P p) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(134);
        }
        visitSimpleNameExpressionVoid(ktSimpleNameExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitReferenceExpression(KtReferenceExpression ktReferenceExpression, P p) {
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(135);
        }
        visitReferenceExpressionVoid(ktReferenceExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitLabeledExpression(KtLabeledExpression ktLabeledExpression, P p) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(136);
        }
        visitLabeledExpressionVoid(ktLabeledExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitPrefixExpression(KtPrefixExpression ktPrefixExpression, P p) {
        if (ktPrefixExpression == null) {
            $$$reportNull$$$0(137);
        }
        visitPrefixExpressionVoid(ktPrefixExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitPostfixExpression(KtPostfixExpression ktPostfixExpression, P p) {
        if (ktPostfixExpression == null) {
            $$$reportNull$$$0(138);
        }
        visitPostfixExpressionVoid(ktPostfixExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitUnaryExpression(KtUnaryExpression ktUnaryExpression, P p) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(139);
        }
        visitUnaryExpressionVoid(ktUnaryExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitBinaryExpression(KtBinaryExpression ktBinaryExpression, P p) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(140);
        }
        visitBinaryExpressionVoid(ktBinaryExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitReturnExpression(KtReturnExpression ktReturnExpression, P p) {
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(141);
        }
        visitReturnExpressionVoid(ktReturnExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitExpressionWithLabel(KtExpressionWithLabel ktExpressionWithLabel, P p) {
        if (ktExpressionWithLabel == null) {
            $$$reportNull$$$0(142);
        }
        visitExpressionWithLabelVoid(ktExpressionWithLabel, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitThrowExpression(KtThrowExpression ktThrowExpression, P p) {
        if (ktThrowExpression == null) {
            $$$reportNull$$$0(143);
        }
        visitThrowExpressionVoid(ktThrowExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitBreakExpression(KtBreakExpression ktBreakExpression, P p) {
        if (ktBreakExpression == null) {
            $$$reportNull$$$0(144);
        }
        visitBreakExpressionVoid(ktBreakExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitContinueExpression(KtContinueExpression ktContinueExpression, P p) {
        if (ktContinueExpression == null) {
            $$$reportNull$$$0(145);
        }
        visitContinueExpressionVoid(ktContinueExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitIfExpression(KtIfExpression ktIfExpression, P p) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(146);
        }
        visitIfExpressionVoid(ktIfExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhenExpression(KtWhenExpression ktWhenExpression, P p) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(147);
        }
        visitWhenExpressionVoid(ktWhenExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitCollectionLiteralExpression(KtCollectionLiteralExpression ktCollectionLiteralExpression, P p) {
        if (ktCollectionLiteralExpression == null) {
            $$$reportNull$$$0(148);
        }
        visitCollectionLiteralExpressionVoid(ktCollectionLiteralExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTryExpression(KtTryExpression ktTryExpression, P p) {
        if (ktTryExpression == null) {
            $$$reportNull$$$0(149);
        }
        visitTryExpressionVoid(ktTryExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitForExpression(KtForExpression ktForExpression, P p) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(150);
        }
        visitForExpressionVoid(ktForExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhileExpression(KtWhileExpression ktWhileExpression, P p) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(151);
        }
        visitWhileExpressionVoid(ktWhileExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression, P p) {
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(152);
        }
        visitDoWhileExpressionVoid(ktDoWhileExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitLambdaExpression(KtLambdaExpression ktLambdaExpression, P p) {
        if (ktLambdaExpression == null) {
            $$$reportNull$$$0(153);
        }
        visitLambdaExpressionVoid(ktLambdaExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, P p) {
        if (ktAnnotatedExpression == null) {
            $$$reportNull$$$0(154);
        }
        visitAnnotatedExpressionVoid(ktAnnotatedExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitCallExpression(KtCallExpression ktCallExpression, P p) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(155);
        }
        visitCallExpressionVoid(ktCallExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression, P p) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(156);
        }
        visitArrayAccessExpressionVoid(ktArrayAccessExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitQualifiedExpression(KtQualifiedExpression ktQualifiedExpression, P p) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(157);
        }
        visitQualifiedExpressionVoid(ktQualifiedExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDoubleColonExpression(KtDoubleColonExpression ktDoubleColonExpression, P p) {
        if (ktDoubleColonExpression == null) {
            $$$reportNull$$$0(158);
        }
        visitDoubleColonExpressionVoid(ktDoubleColonExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression, P p) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(159);
        }
        visitCallableReferenceExpressionVoid(ktCallableReferenceExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitClassLiteralExpression(KtClassLiteralExpression ktClassLiteralExpression, P p) {
        if (ktClassLiteralExpression == null) {
            $$$reportNull$$$0(160);
        }
        visitClassLiteralExpressionVoid(ktClassLiteralExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDotQualifiedExpression(KtDotQualifiedExpression ktDotQualifiedExpression, P p) {
        if (ktDotQualifiedExpression == null) {
            $$$reportNull$$$0(161);
        }
        visitDotQualifiedExpressionVoid(ktDotQualifiedExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSafeQualifiedExpression(KtSafeQualifiedExpression ktSafeQualifiedExpression, P p) {
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(162);
        }
        visitSafeQualifiedExpressionVoid(ktSafeQualifiedExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitObjectLiteralExpression(KtObjectLiteralExpression ktObjectLiteralExpression, P p) {
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(163);
        }
        visitObjectLiteralExpressionVoid(ktObjectLiteralExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitBlockExpression(KtBlockExpression ktBlockExpression, P p) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(164);
        }
        visitBlockExpressionVoid(ktBlockExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitCatchSection(KtCatchClause ktCatchClause, P p) {
        if (ktCatchClause == null) {
            $$$reportNull$$$0(165);
        }
        visitCatchSectionVoid(ktCatchClause, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitFinallySection(KtFinallySection ktFinallySection, P p) {
        if (ktFinallySection == null) {
            $$$reportNull$$$0(166);
        }
        visitFinallySectionVoid(ktFinallySection, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeArgumentList(KtTypeArgumentList ktTypeArgumentList, P p) {
        if (ktTypeArgumentList == null) {
            $$$reportNull$$$0(167);
        }
        visitTypeArgumentListVoid(ktTypeArgumentList, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitThisExpression(KtThisExpression ktThisExpression, P p) {
        if (ktThisExpression == null) {
            $$$reportNull$$$0(168);
        }
        visitThisExpressionVoid(ktThisExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSuperExpression(KtSuperExpression ktSuperExpression, P p) {
        if (ktSuperExpression == null) {
            $$$reportNull$$$0(169);
        }
        visitSuperExpressionVoid(ktSuperExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression, P p) {
        if (ktParenthesizedExpression == null) {
            $$$reportNull$$$0(170);
        }
        visitParenthesizedExpressionVoid(ktParenthesizedExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitInitializerList(KtInitializerList ktInitializerList, P p) {
        if (ktInitializerList == null) {
            $$$reportNull$$$0(171);
        }
        visitInitializerListVoid(ktInitializerList, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitAnonymousInitializer(KtAnonymousInitializer ktAnonymousInitializer, P p) {
        if (ktAnonymousInitializer == null) {
            $$$reportNull$$$0(172);
        }
        visitAnonymousInitializerVoid(ktAnonymousInitializer, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitPropertyAccessor(KtPropertyAccessor ktPropertyAccessor, P p) {
        if (ktPropertyAccessor == null) {
            $$$reportNull$$$0(173);
        }
        visitPropertyAccessorVoid(ktPropertyAccessor, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeConstraintList(KtTypeConstraintList ktTypeConstraintList, P p) {
        if (ktTypeConstraintList == null) {
            $$$reportNull$$$0(174);
        }
        visitTypeConstraintListVoid(ktTypeConstraintList, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeConstraint(KtTypeConstraint ktTypeConstraint, P p) {
        if (ktTypeConstraint == null) {
            $$$reportNull$$$0(175);
        }
        visitTypeConstraintVoid(ktTypeConstraint, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitUserType(KtUserType ktUserType, P p) {
        if (ktUserType == null) {
            $$$reportNull$$$0(176);
        }
        visitUserTypeVoid(ktUserType, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitDynamicType(KtDynamicType ktDynamicType, P p) {
        if (ktDynamicType == null) {
            $$$reportNull$$$0(177);
        }
        visitDynamicTypeVoid(ktDynamicType, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitFunctionType(KtFunctionType ktFunctionType, P p) {
        if (ktFunctionType == null) {
            $$$reportNull$$$0(178);
        }
        visitFunctionTypeVoid(ktFunctionType, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSelfType(KtSelfType ktSelfType, P p) {
        if (ktSelfType == null) {
            $$$reportNull$$$0(179);
        }
        visitSelfTypeVoid(ktSelfType, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, P p) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(180);
        }
        visitBinaryWithTypeRHSExpressionVoid(ktBinaryExpressionWithTypeRHS, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitStringTemplateExpression(KtStringTemplateExpression ktStringTemplateExpression, P p) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(181);
        }
        visitStringTemplateExpressionVoid(ktStringTemplateExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitNamedDeclaration(KtNamedDeclaration ktNamedDeclaration, P p) {
        if (ktNamedDeclaration == null) {
            $$$reportNull$$$0(182);
        }
        visitNamedDeclarationVoid(ktNamedDeclaration, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitNullableType(KtNullableType ktNullableType, P p) {
        if (ktNullableType == null) {
            $$$reportNull$$$0(183);
        }
        visitNullableTypeVoid(ktNullableType, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeProjection(KtTypeProjection ktTypeProjection, P p) {
        if (ktTypeProjection == null) {
            $$$reportNull$$$0(184);
        }
        visitTypeProjectionVoid(ktTypeProjection, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhenEntry(KtWhenEntry ktWhenEntry, P p) {
        if (ktWhenEntry == null) {
            $$$reportNull$$$0(185);
        }
        visitWhenEntryVoid(ktWhenEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitIsExpression(KtIsExpression ktIsExpression, P p) {
        if (ktIsExpression == null) {
            $$$reportNull$$$0(186);
        }
        visitIsExpressionVoid(ktIsExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhenConditionIsPattern(KtWhenConditionIsPattern ktWhenConditionIsPattern, P p) {
        if (ktWhenConditionIsPattern == null) {
            $$$reportNull$$$0(187);
        }
        visitWhenConditionIsPatternVoid(ktWhenConditionIsPattern, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhenConditionInRange(KtWhenConditionInRange ktWhenConditionInRange, P p) {
        if (ktWhenConditionInRange == null) {
            $$$reportNull$$$0(188);
        }
        visitWhenConditionInRangeVoid(ktWhenConditionInRange, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhenConditionWithExpression(KtWhenConditionWithExpression ktWhenConditionWithExpression, P p) {
        if (ktWhenConditionWithExpression == null) {
            $$$reportNull$$$0(189);
        }
        visitWhenConditionWithExpressionVoid(ktWhenConditionWithExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitObjectDeclaration(KtObjectDeclaration ktObjectDeclaration, P p) {
        if (ktObjectDeclaration == null) {
            $$$reportNull$$$0(190);
        }
        visitObjectDeclarationVoid(ktObjectDeclaration, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitStringTemplateEntry(KtStringTemplateEntry ktStringTemplateEntry, P p) {
        if (ktStringTemplateEntry == null) {
            $$$reportNull$$$0(191);
        }
        visitStringTemplateEntryVoid(ktStringTemplateEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitStringTemplateEntryWithExpression(KtStringTemplateEntryWithExpression ktStringTemplateEntryWithExpression, P p) {
        if (ktStringTemplateEntryWithExpression == null) {
            $$$reportNull$$$0(192);
        }
        visitStringTemplateEntryWithExpressionVoid(ktStringTemplateEntryWithExpression, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitBlockStringTemplateEntry(KtBlockStringTemplateEntry ktBlockStringTemplateEntry, P p) {
        if (ktBlockStringTemplateEntry == null) {
            $$$reportNull$$$0(193);
        }
        visitBlockStringTemplateEntryVoid(ktBlockStringTemplateEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSimpleNameStringTemplateEntry(KtSimpleNameStringTemplateEntry ktSimpleNameStringTemplateEntry, P p) {
        if (ktSimpleNameStringTemplateEntry == null) {
            $$$reportNull$$$0(194);
        }
        visitSimpleNameStringTemplateEntryVoid(ktSimpleNameStringTemplateEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitLiteralStringTemplateEntry(KtLiteralStringTemplateEntry ktLiteralStringTemplateEntry, P p) {
        if (ktLiteralStringTemplateEntry == null) {
            $$$reportNull$$$0(195);
        }
        visitLiteralStringTemplateEntryVoid(ktLiteralStringTemplateEntry, p);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitEscapeStringTemplateEntry(KtEscapeStringTemplateEntry ktEscapeStringTemplateEntry, P p) {
        if (ktEscapeStringTemplateEntry == null) {
            $$$reportNull$$$0(196);
        }
        visitEscapeStringTemplateEntryVoid(ktEscapeStringTemplateEntry, p);
        return null;
    }
}
