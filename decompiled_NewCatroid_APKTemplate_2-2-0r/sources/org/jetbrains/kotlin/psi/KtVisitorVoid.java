package org.jetbrains.kotlin.psi;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes6.dex */
public class KtVisitorVoid extends KtVisitor<Void, Void> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 108:
                objArr[0] = "dcl";
                break;
            case 2:
            case 109:
                objArr[0] = "klass";
                break;
            case 3:
            case 110:
                objArr[0] = "classOrObject";
                break;
            case 4:
            case 5:
            case 111:
            case 112:
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                break;
            case 6:
            case 113:
                objArr[0] = "function";
                break;
            case 7:
            case 114:
                objArr[0] = "property";
                break;
            case 8:
            case 115:
                objArr[0] = "typeAlias";
                break;
            case 9:
                objArr[0] = "destructuringDeclaration";
                break;
            case 10:
            case 117:
                objArr[0] = "multiDeclarationEntry";
                break;
            case 11:
            case 118:
                objArr[0] = "file";
                break;
            case 12:
            case 119:
                objArr[0] = "script";
                break;
            case 13:
                objArr[0] = "importAlias";
                break;
            case 14:
            case 120:
                objArr[0] = "importDirective";
                break;
            case 15:
            case 121:
                objArr[0] = "importList";
                break;
            case 16:
            case 122:
                objArr[0] = "classBody";
                break;
            case 17:
            case 21:
            case 24:
            case 26:
            case 35:
            case 77:
            case 82:
            case 123:
            case 127:
            case 130:
            case 132:
            case 140:
            case 182:
            case 185:
                objArr[0] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 18:
            case 124:
                objArr[0] = "annotation";
                break;
            case 19:
            case 125:
                objArr[0] = "annotationEntry";
                break;
            case 20:
            case 126:
                objArr[0] = "constructorCalleeExpression";
                break;
            case 22:
            case 25:
            case 128:
            case 131:
                objArr[0] = "parameter";
                break;
            case 23:
            case 129:
                objArr[0] = "enumEntry";
                break;
            case 27:
            case 28:
            case 30:
            case 133:
            case 134:
            case 136:
                objArr[0] = "specifier";
                break;
            case 29:
            case 32:
            case 135:
            case 137:
                objArr[0] = "call";
                break;
            case 31:
                objArr[0] = "contextReceiverList";
                break;
            case 33:
            case 138:
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                break;
            case 34:
            case 139:
                objArr[0] = "typeReference";
                break;
            case 36:
            case 141:
                objArr[0] = "argument";
                break;
            case 37:
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
            case 74:
            case 75:
            case 76:
            case 88:
            case 89:
            case 95:
            case 142:
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
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 179:
            case 180:
            case 181:
            case 191:
            case 192:
            case 198:
                objArr[0] = "expression";
                break;
            case 38:
            case 143:
                objArr[0] = "loopExpression";
                break;
            case 59:
                objArr[0] = "lambdaExpression";
                break;
            case 71:
            case 176:
                objArr[0] = "catchClause";
                break;
            case 72:
            case 177:
                objArr[0] = "finallySection";
                break;
            case 73:
            case 178:
                objArr[0] = "typeArgumentList";
                break;
            case 78:
            case 79:
            case 80:
            case 183:
            case 210:
            case 211:
                objArr[0] = "initializer";
                break;
            case 81:
            case 184:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
            case 83:
            case 186:
                objArr[0] = "constraint";
                break;
            case 84:
            case 85:
            case 86:
            case 87:
            case 187:
            case 188:
            case 189:
            case 190:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 90:
            case 99:
            case 193:
            case 202:
                objArr[0] = "declaration";
                break;
            case 91:
            case 194:
                objArr[0] = "nullableType";
                break;
            case 92:
            case 195:
                objArr[0] = "intersectionType";
                break;
            case 93:
            case 196:
                objArr[0] = "typeProjection";
                break;
            case 94:
            case 197:
                objArr[0] = "ktWhenEntry";
                break;
            case 96:
            case 97:
            case 98:
            case 199:
            case 200:
            case 201:
                objArr[0] = "condition";
                break;
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 208:
                objArr[0] = "entry";
                break;
            case 106:
            case 209:
                objArr[0] = "directive";
                break;
            case 107:
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 116:
                objArr[0] = "multiDeclaration";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtVisitorVoid";
        switch (i) {
            case 1:
            case 108:
                objArr[2] = "visitDeclaration";
                break;
            case 2:
            case 109:
                objArr[2] = "visitClass";
                break;
            case 3:
            case 110:
                objArr[2] = "visitClassOrObject";
                break;
            case 4:
            case 111:
                objArr[2] = "visitSecondaryConstructor";
                break;
            case 5:
            case 112:
                objArr[2] = "visitPrimaryConstructor";
                break;
            case 6:
            case 113:
                objArr[2] = "visitNamedFunction";
                break;
            case 7:
            case 114:
                objArr[2] = "visitProperty";
                break;
            case 8:
            case 115:
                objArr[2] = "visitTypeAlias";
                break;
            case 9:
            case 116:
                objArr[2] = "visitDestructuringDeclaration";
                break;
            case 10:
            case 117:
                objArr[2] = "visitDestructuringDeclarationEntry";
                break;
            case 11:
            case 118:
                objArr[2] = "visitKtFile";
                break;
            case 12:
            case 119:
                objArr[2] = "visitScript";
                break;
            case 13:
                objArr[2] = "visitImportAlias";
                break;
            case 14:
            case 120:
                objArr[2] = "visitImportDirective";
                break;
            case 15:
            case 121:
                objArr[2] = "visitImportList";
                break;
            case 16:
            case 122:
                objArr[2] = "visitClassBody";
                break;
            case 17:
            case 123:
                objArr[2] = "visitModifierList";
                break;
            case 18:
            case 124:
                objArr[2] = "visitAnnotation";
                break;
            case 19:
            case 125:
                objArr[2] = "visitAnnotationEntry";
                break;
            case 20:
            case 126:
                objArr[2] = "visitConstructorCalleeExpression";
                break;
            case 21:
            case 127:
                objArr[2] = "visitTypeParameterList";
                break;
            case 22:
            case 128:
                objArr[2] = "visitTypeParameter";
                break;
            case 23:
            case 129:
                objArr[2] = "visitEnumEntry";
                break;
            case 24:
            case 130:
                objArr[2] = "visitParameterList";
                break;
            case 25:
            case 131:
                objArr[2] = "visitParameter";
                break;
            case 26:
            case 132:
                objArr[2] = "visitSuperTypeList";
                break;
            case 27:
            case 133:
                objArr[2] = "visitSuperTypeListEntry";
                break;
            case 28:
            case 134:
                objArr[2] = "visitDelegatedSuperTypeEntry";
                break;
            case 29:
            case 135:
                objArr[2] = "visitSuperTypeCallEntry";
                break;
            case 30:
            case 136:
                objArr[2] = "visitSuperTypeEntry";
                break;
            case 31:
                objArr[2] = "visitContextReceiverList";
                break;
            case 32:
            case 137:
                objArr[2] = "visitConstructorDelegationCall";
                break;
            case 33:
            case 138:
                objArr[2] = "visitPropertyDelegate";
                break;
            case 34:
            case 139:
                objArr[2] = "visitTypeReference";
                break;
            case 35:
            case 140:
                objArr[2] = "visitValueArgumentList";
                break;
            case 36:
            case 141:
                objArr[2] = "visitArgument";
                break;
            case 37:
            case 142:
                objArr[2] = "visitExpression";
                break;
            case 38:
            case 143:
                objArr[2] = "visitLoopExpression";
                break;
            case 39:
            case 144:
                objArr[2] = "visitConstantExpression";
                break;
            case 40:
            case 145:
                objArr[2] = "visitSimpleNameExpression";
                break;
            case 41:
            case 146:
                objArr[2] = "visitReferenceExpression";
                break;
            case 42:
            case 147:
                objArr[2] = "visitLabeledExpression";
                break;
            case 43:
            case 148:
                objArr[2] = "visitPrefixExpression";
                break;
            case 44:
            case 149:
                objArr[2] = "visitPostfixExpression";
                break;
            case 45:
            case 150:
                objArr[2] = "visitUnaryExpression";
                break;
            case 46:
            case 151:
                objArr[2] = "visitBinaryExpression";
                break;
            case 47:
            case 152:
                objArr[2] = "visitReturnExpression";
                break;
            case 48:
            case 153:
                objArr[2] = "visitExpressionWithLabel";
                break;
            case 49:
            case 154:
                objArr[2] = "visitThrowExpression";
                break;
            case 50:
            case 155:
                objArr[2] = "visitBreakExpression";
                break;
            case 51:
            case 156:
                objArr[2] = "visitContinueExpression";
                break;
            case 52:
            case 157:
                objArr[2] = "visitIfExpression";
                break;
            case 53:
            case 158:
                objArr[2] = "visitWhenExpression";
                break;
            case 54:
            case 159:
                objArr[2] = "visitCollectionLiteralExpression";
                break;
            case 55:
            case 160:
                objArr[2] = "visitTryExpression";
                break;
            case 56:
            case 161:
                objArr[2] = "visitForExpression";
                break;
            case 57:
            case 162:
                objArr[2] = "visitWhileExpression";
                break;
            case 58:
            case 163:
                objArr[2] = "visitDoWhileExpression";
                break;
            case 59:
            case 164:
                objArr[2] = "visitLambdaExpression";
                break;
            case 60:
            case 165:
                objArr[2] = "visitAnnotatedExpression";
                break;
            case 61:
            case 166:
                objArr[2] = "visitCallExpression";
                break;
            case 62:
            case 167:
                objArr[2] = "visitArrayAccessExpression";
                break;
            case 63:
            case 168:
                objArr[2] = "visitQualifiedExpression";
                break;
            case 64:
            case 169:
                objArr[2] = "visitDoubleColonExpression";
                break;
            case 65:
            case 170:
                objArr[2] = "visitCallableReferenceExpression";
                break;
            case 66:
            case 171:
                objArr[2] = "visitClassLiteralExpression";
                break;
            case 67:
            case 172:
                objArr[2] = "visitDotQualifiedExpression";
                break;
            case 68:
            case 173:
                objArr[2] = "visitSafeQualifiedExpression";
                break;
            case 69:
            case 174:
                objArr[2] = "visitObjectLiteralExpression";
                break;
            case 70:
            case 175:
                objArr[2] = "visitBlockExpression";
                break;
            case 71:
            case 176:
                objArr[2] = "visitCatchSection";
                break;
            case 72:
            case 177:
                objArr[2] = "visitFinallySection";
                break;
            case 73:
            case 178:
                objArr[2] = "visitTypeArgumentList";
                break;
            case 74:
            case 179:
                objArr[2] = "visitThisExpression";
                break;
            case 75:
            case 180:
                objArr[2] = "visitSuperExpression";
                break;
            case 76:
            case 181:
                objArr[2] = "visitParenthesizedExpression";
                break;
            case 77:
            case 182:
                objArr[2] = "visitInitializerList";
                break;
            case 78:
            case 183:
                objArr[2] = "visitAnonymousInitializer";
                break;
            case 79:
            case 210:
                objArr[2] = "visitScriptInitializer";
                break;
            case 80:
            case 211:
                objArr[2] = "visitClassInitializer";
                break;
            case 81:
            case 184:
                objArr[2] = "visitPropertyAccessor";
                break;
            case 82:
            case 185:
                objArr[2] = "visitTypeConstraintList";
                break;
            case 83:
            case 186:
                objArr[2] = "visitTypeConstraint";
                break;
            case 84:
            case 187:
                objArr[2] = "visitUserType";
                break;
            case 85:
            case 188:
                objArr[2] = "visitDynamicType";
                break;
            case 86:
            case 189:
                objArr[2] = "visitFunctionType";
                break;
            case 87:
            case 190:
                objArr[2] = "visitSelfType";
                break;
            case 88:
            case 191:
                objArr[2] = "visitBinaryWithTypeRHSExpression";
                break;
            case 89:
            case 192:
                objArr[2] = "visitStringTemplateExpression";
                break;
            case 90:
            case 193:
                objArr[2] = "visitNamedDeclaration";
                break;
            case 91:
            case 194:
                objArr[2] = "visitNullableType";
                break;
            case 92:
            case 195:
                objArr[2] = "visitIntersectionType";
                break;
            case 93:
            case 196:
                objArr[2] = "visitTypeProjection";
                break;
            case 94:
            case 197:
                objArr[2] = "visitWhenEntry";
                break;
            case 95:
            case 198:
                objArr[2] = "visitIsExpression";
                break;
            case 96:
            case 199:
                objArr[2] = "visitWhenConditionIsPattern";
                break;
            case 97:
            case 200:
                objArr[2] = "visitWhenConditionInRange";
                break;
            case 98:
            case 201:
                objArr[2] = "visitWhenConditionWithExpression";
                break;
            case 99:
            case 202:
                objArr[2] = "visitObjectDeclaration";
                break;
            case 100:
            case 203:
                objArr[2] = "visitStringTemplateEntry";
                break;
            case 101:
            case 204:
                objArr[2] = "visitStringTemplateEntryWithExpression";
                break;
            case 102:
            case 205:
                objArr[2] = "visitBlockStringTemplateEntry";
                break;
            case 103:
            case 206:
                objArr[2] = "visitSimpleNameStringTemplateEntry";
                break;
            case 104:
            case 207:
                objArr[2] = "visitLiteralStringTemplateEntry";
                break;
            case 105:
            case 208:
                objArr[2] = "visitEscapeStringTemplateEntry";
                break;
            case 106:
            case 209:
                objArr[2] = "visitPackageDirective";
                break;
            case 107:
            default:
                objArr[2] = "visitKtElement";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public void visitKtElement(KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(0);
        }
        super.visitKtElement(ktElement, (Object) null);
    }

    public void visitDeclaration(KtDeclaration ktDeclaration) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(1);
        }
        super.visitDeclaration(ktDeclaration, (Object) null);
    }

    public void visitClass(KtClass ktClass) {
        if (ktClass == null) {
            $$$reportNull$$$0(2);
        }
        super.visitClass(ktClass, (Object) null);
    }

    public void visitClassOrObject(KtClassOrObject ktClassOrObject) {
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(3);
        }
        super.visitClassOrObject(ktClassOrObject, (Object) null);
    }

    public void visitSecondaryConstructor(KtSecondaryConstructor ktSecondaryConstructor) {
        if (ktSecondaryConstructor == null) {
            $$$reportNull$$$0(4);
        }
        super.visitSecondaryConstructor(ktSecondaryConstructor, (Object) null);
    }

    public void visitPrimaryConstructor(KtPrimaryConstructor ktPrimaryConstructor) {
        if (ktPrimaryConstructor == null) {
            $$$reportNull$$$0(5);
        }
        super.visitPrimaryConstructor(ktPrimaryConstructor, (Object) null);
    }

    public void visitNamedFunction(KtNamedFunction ktNamedFunction) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(6);
        }
        super.visitNamedFunction(ktNamedFunction, (Object) null);
    }

    public void visitProperty(KtProperty ktProperty) {
        if (ktProperty == null) {
            $$$reportNull$$$0(7);
        }
        super.visitProperty(ktProperty, (Object) null);
    }

    public void visitTypeAlias(KtTypeAlias ktTypeAlias) {
        if (ktTypeAlias == null) {
            $$$reportNull$$$0(8);
        }
        super.visitTypeAlias(ktTypeAlias, (Object) null);
    }

    public void visitDestructuringDeclaration(KtDestructuringDeclaration ktDestructuringDeclaration) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(9);
        }
        super.visitDestructuringDeclaration(ktDestructuringDeclaration, (Object) null);
    }

    public void visitDestructuringDeclarationEntry(KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry) {
        if (ktDestructuringDeclarationEntry == null) {
            $$$reportNull$$$0(10);
        }
        super.visitDestructuringDeclarationEntry(ktDestructuringDeclarationEntry, (Object) null);
    }

    public void visitKtFile(KtFile ktFile) {
        if (ktFile == null) {
            $$$reportNull$$$0(11);
        }
        super.visitKtFile(ktFile, (Object) null);
    }

    public void visitScript(KtScript ktScript) {
        if (ktScript == null) {
            $$$reportNull$$$0(12);
        }
        super.visitScript(ktScript, (Object) null);
    }

    public void visitImportAlias(KtImportAlias ktImportAlias) {
        if (ktImportAlias == null) {
            $$$reportNull$$$0(13);
        }
        super.visitImportAlias(ktImportAlias, null);
    }

    public void visitImportDirective(KtImportDirective ktImportDirective) {
        if (ktImportDirective == null) {
            $$$reportNull$$$0(14);
        }
        super.visitImportDirective(ktImportDirective, (Object) null);
    }

    public void visitImportList(KtImportList ktImportList) {
        if (ktImportList == null) {
            $$$reportNull$$$0(15);
        }
        super.visitImportList(ktImportList, (Object) null);
    }

    public void visitClassBody(KtClassBody ktClassBody) {
        if (ktClassBody == null) {
            $$$reportNull$$$0(16);
        }
        super.visitClassBody(ktClassBody, (Object) null);
    }

    public void visitModifierList(KtModifierList ktModifierList) {
        if (ktModifierList == null) {
            $$$reportNull$$$0(17);
        }
        super.visitModifierList(ktModifierList, (Object) null);
    }

    public void visitAnnotation(KtAnnotation ktAnnotation) {
        if (ktAnnotation == null) {
            $$$reportNull$$$0(18);
        }
        super.visitAnnotation(ktAnnotation, (Object) null);
    }

    public void visitAnnotationEntry(KtAnnotationEntry ktAnnotationEntry) {
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(19);
        }
        super.visitAnnotationEntry(ktAnnotationEntry, (Object) null);
    }

    public void visitConstructorCalleeExpression(KtConstructorCalleeExpression ktConstructorCalleeExpression) {
        if (ktConstructorCalleeExpression == null) {
            $$$reportNull$$$0(20);
        }
        super.visitConstructorCalleeExpression(ktConstructorCalleeExpression, (Object) null);
    }

    public void visitTypeParameterList(KtTypeParameterList ktTypeParameterList) {
        if (ktTypeParameterList == null) {
            $$$reportNull$$$0(21);
        }
        super.visitTypeParameterList(ktTypeParameterList, (Object) null);
    }

    public void visitTypeParameter(KtTypeParameter ktTypeParameter) {
        if (ktTypeParameter == null) {
            $$$reportNull$$$0(22);
        }
        super.visitTypeParameter(ktTypeParameter, (Object) null);
    }

    public void visitEnumEntry(KtEnumEntry ktEnumEntry) {
        if (ktEnumEntry == null) {
            $$$reportNull$$$0(23);
        }
        super.visitEnumEntry(ktEnumEntry, (Object) null);
    }

    public void visitParameterList(KtParameterList ktParameterList) {
        if (ktParameterList == null) {
            $$$reportNull$$$0(24);
        }
        super.visitParameterList(ktParameterList, (Object) null);
    }

    public void visitParameter(KtParameter ktParameter) {
        if (ktParameter == null) {
            $$$reportNull$$$0(25);
        }
        super.visitParameter(ktParameter, (Object) null);
    }

    public void visitSuperTypeList(KtSuperTypeList ktSuperTypeList) {
        if (ktSuperTypeList == null) {
            $$$reportNull$$$0(26);
        }
        super.visitSuperTypeList(ktSuperTypeList, (Object) null);
    }

    public void visitSuperTypeListEntry(KtSuperTypeListEntry ktSuperTypeListEntry) {
        if (ktSuperTypeListEntry == null) {
            $$$reportNull$$$0(27);
        }
        super.visitSuperTypeListEntry(ktSuperTypeListEntry, (Object) null);
    }

    public void visitDelegatedSuperTypeEntry(KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry) {
        if (ktDelegatedSuperTypeEntry == null) {
            $$$reportNull$$$0(28);
        }
        super.visitDelegatedSuperTypeEntry(ktDelegatedSuperTypeEntry, (Object) null);
    }

    public void visitSuperTypeCallEntry(KtSuperTypeCallEntry ktSuperTypeCallEntry) {
        if (ktSuperTypeCallEntry == null) {
            $$$reportNull$$$0(29);
        }
        super.visitSuperTypeCallEntry(ktSuperTypeCallEntry, (Object) null);
    }

    public void visitSuperTypeEntry(KtSuperTypeEntry ktSuperTypeEntry) {
        if (ktSuperTypeEntry == null) {
            $$$reportNull$$$0(30);
        }
        super.visitSuperTypeEntry(ktSuperTypeEntry, (Object) null);
    }

    public void visitContextReceiverList(KtContextReceiverList ktContextReceiverList) {
        if (ktContextReceiverList == null) {
            $$$reportNull$$$0(31);
        }
        super.visitContextReceiverList(ktContextReceiverList, null);
    }

    public void visitConstructorDelegationCall(KtConstructorDelegationCall ktConstructorDelegationCall) {
        if (ktConstructorDelegationCall == null) {
            $$$reportNull$$$0(32);
        }
        super.visitConstructorDelegationCall(ktConstructorDelegationCall, (Object) null);
    }

    public void visitPropertyDelegate(KtPropertyDelegate ktPropertyDelegate) {
        if (ktPropertyDelegate == null) {
            $$$reportNull$$$0(33);
        }
        super.visitPropertyDelegate(ktPropertyDelegate, (Object) null);
    }

    public void visitTypeReference(KtTypeReference ktTypeReference) {
        if (ktTypeReference == null) {
            $$$reportNull$$$0(34);
        }
        super.visitTypeReference(ktTypeReference, (Object) null);
    }

    public void visitValueArgumentList(KtValueArgumentList ktValueArgumentList) {
        if (ktValueArgumentList == null) {
            $$$reportNull$$$0(35);
        }
        super.visitValueArgumentList(ktValueArgumentList, (Object) null);
    }

    public void visitArgument(KtValueArgument ktValueArgument) {
        if (ktValueArgument == null) {
            $$$reportNull$$$0(36);
        }
        super.visitArgument(ktValueArgument, (Object) null);
    }

    public void visitExpression(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(37);
        }
        super.visitExpression(ktExpression, (Object) null);
    }

    public void visitLoopExpression(KtLoopExpression ktLoopExpression) {
        if (ktLoopExpression == null) {
            $$$reportNull$$$0(38);
        }
        super.visitLoopExpression(ktLoopExpression, (Object) null);
    }

    public void visitConstantExpression(KtConstantExpression ktConstantExpression) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(39);
        }
        super.visitConstantExpression(ktConstantExpression, (Object) null);
    }

    public void visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(40);
        }
        super.visitSimpleNameExpression(ktSimpleNameExpression, (Object) null);
    }

    public void visitReferenceExpression(KtReferenceExpression ktReferenceExpression) {
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(41);
        }
        super.visitReferenceExpression(ktReferenceExpression, (Object) null);
    }

    public void visitLabeledExpression(KtLabeledExpression ktLabeledExpression) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(42);
        }
        super.visitLabeledExpression(ktLabeledExpression, (Object) null);
    }

    public void visitPrefixExpression(KtPrefixExpression ktPrefixExpression) {
        if (ktPrefixExpression == null) {
            $$$reportNull$$$0(43);
        }
        super.visitPrefixExpression(ktPrefixExpression, (Object) null);
    }

    public void visitPostfixExpression(KtPostfixExpression ktPostfixExpression) {
        if (ktPostfixExpression == null) {
            $$$reportNull$$$0(44);
        }
        super.visitPostfixExpression(ktPostfixExpression, (Object) null);
    }

    public void visitUnaryExpression(KtUnaryExpression ktUnaryExpression) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(45);
        }
        super.visitUnaryExpression(ktUnaryExpression, (Object) null);
    }

    public void visitBinaryExpression(KtBinaryExpression ktBinaryExpression) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(46);
        }
        super.visitBinaryExpression(ktBinaryExpression, (Object) null);
    }

    public void visitReturnExpression(KtReturnExpression ktReturnExpression) {
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(47);
        }
        super.visitReturnExpression(ktReturnExpression, (Object) null);
    }

    public void visitExpressionWithLabel(KtExpressionWithLabel ktExpressionWithLabel) {
        if (ktExpressionWithLabel == null) {
            $$$reportNull$$$0(48);
        }
        super.visitExpressionWithLabel(ktExpressionWithLabel, (Object) null);
    }

    public void visitThrowExpression(KtThrowExpression ktThrowExpression) {
        if (ktThrowExpression == null) {
            $$$reportNull$$$0(49);
        }
        super.visitThrowExpression(ktThrowExpression, (Object) null);
    }

    public void visitBreakExpression(KtBreakExpression ktBreakExpression) {
        if (ktBreakExpression == null) {
            $$$reportNull$$$0(50);
        }
        super.visitBreakExpression(ktBreakExpression, (Object) null);
    }

    public void visitContinueExpression(KtContinueExpression ktContinueExpression) {
        if (ktContinueExpression == null) {
            $$$reportNull$$$0(51);
        }
        super.visitContinueExpression(ktContinueExpression, (Object) null);
    }

    public void visitIfExpression(KtIfExpression ktIfExpression) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(52);
        }
        super.visitIfExpression(ktIfExpression, (Object) null);
    }

    public void visitWhenExpression(KtWhenExpression ktWhenExpression) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(53);
        }
        super.visitWhenExpression(ktWhenExpression, (Object) null);
    }

    public void visitCollectionLiteralExpression(KtCollectionLiteralExpression ktCollectionLiteralExpression) {
        if (ktCollectionLiteralExpression == null) {
            $$$reportNull$$$0(54);
        }
        super.visitCollectionLiteralExpression(ktCollectionLiteralExpression, (Object) null);
    }

    public void visitTryExpression(KtTryExpression ktTryExpression) {
        if (ktTryExpression == null) {
            $$$reportNull$$$0(55);
        }
        super.visitTryExpression(ktTryExpression, (Object) null);
    }

    public void visitForExpression(KtForExpression ktForExpression) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(56);
        }
        super.visitForExpression(ktForExpression, (Object) null);
    }

    public void visitWhileExpression(KtWhileExpression ktWhileExpression) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(57);
        }
        super.visitWhileExpression(ktWhileExpression, (Object) null);
    }

    public void visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression) {
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(58);
        }
        super.visitDoWhileExpression(ktDoWhileExpression, (Object) null);
    }

    public void visitLambdaExpression(KtLambdaExpression ktLambdaExpression) {
        if (ktLambdaExpression == null) {
            $$$reportNull$$$0(59);
        }
        super.visitLambdaExpression(ktLambdaExpression, (Object) null);
    }

    public void visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression) {
        if (ktAnnotatedExpression == null) {
            $$$reportNull$$$0(60);
        }
        super.visitAnnotatedExpression(ktAnnotatedExpression, (Object) null);
    }

    public void visitCallExpression(KtCallExpression ktCallExpression) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(61);
        }
        super.visitCallExpression(ktCallExpression, (Object) null);
    }

    public void visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(62);
        }
        super.visitArrayAccessExpression(ktArrayAccessExpression, (Object) null);
    }

    public void visitQualifiedExpression(KtQualifiedExpression ktQualifiedExpression) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(63);
        }
        super.visitQualifiedExpression(ktQualifiedExpression, (Object) null);
    }

    public void visitDoubleColonExpression(KtDoubleColonExpression ktDoubleColonExpression) {
        if (ktDoubleColonExpression == null) {
            $$$reportNull$$$0(64);
        }
        super.visitDoubleColonExpression(ktDoubleColonExpression, (Object) null);
    }

    public void visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(65);
        }
        super.visitCallableReferenceExpression(ktCallableReferenceExpression, (Object) null);
    }

    public void visitClassLiteralExpression(KtClassLiteralExpression ktClassLiteralExpression) {
        if (ktClassLiteralExpression == null) {
            $$$reportNull$$$0(66);
        }
        super.visitClassLiteralExpression(ktClassLiteralExpression, (Object) null);
    }

    public void visitDotQualifiedExpression(KtDotQualifiedExpression ktDotQualifiedExpression) {
        if (ktDotQualifiedExpression == null) {
            $$$reportNull$$$0(67);
        }
        super.visitDotQualifiedExpression(ktDotQualifiedExpression, (Object) null);
    }

    public void visitSafeQualifiedExpression(KtSafeQualifiedExpression ktSafeQualifiedExpression) {
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(68);
        }
        super.visitSafeQualifiedExpression(ktSafeQualifiedExpression, (Object) null);
    }

    public void visitObjectLiteralExpression(KtObjectLiteralExpression ktObjectLiteralExpression) {
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(69);
        }
        super.visitObjectLiteralExpression(ktObjectLiteralExpression, (Object) null);
    }

    public void visitBlockExpression(KtBlockExpression ktBlockExpression) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(70);
        }
        super.visitBlockExpression(ktBlockExpression, (Object) null);
    }

    public void visitCatchSection(KtCatchClause ktCatchClause) {
        if (ktCatchClause == null) {
            $$$reportNull$$$0(71);
        }
        super.visitCatchSection(ktCatchClause, (Object) null);
    }

    public void visitFinallySection(KtFinallySection ktFinallySection) {
        if (ktFinallySection == null) {
            $$$reportNull$$$0(72);
        }
        super.visitFinallySection(ktFinallySection, (Object) null);
    }

    public void visitTypeArgumentList(KtTypeArgumentList ktTypeArgumentList) {
        if (ktTypeArgumentList == null) {
            $$$reportNull$$$0(73);
        }
        super.visitTypeArgumentList(ktTypeArgumentList, (Object) null);
    }

    public void visitThisExpression(KtThisExpression ktThisExpression) {
        if (ktThisExpression == null) {
            $$$reportNull$$$0(74);
        }
        super.visitThisExpression(ktThisExpression, (Object) null);
    }

    public void visitSuperExpression(KtSuperExpression ktSuperExpression) {
        if (ktSuperExpression == null) {
            $$$reportNull$$$0(75);
        }
        super.visitSuperExpression(ktSuperExpression, (Object) null);
    }

    public void visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression) {
        if (ktParenthesizedExpression == null) {
            $$$reportNull$$$0(76);
        }
        super.visitParenthesizedExpression(ktParenthesizedExpression, (Object) null);
    }

    public void visitInitializerList(KtInitializerList ktInitializerList) {
        if (ktInitializerList == null) {
            $$$reportNull$$$0(77);
        }
        super.visitInitializerList(ktInitializerList, (Object) null);
    }

    public void visitAnonymousInitializer(KtAnonymousInitializer ktAnonymousInitializer) {
        if (ktAnonymousInitializer == null) {
            $$$reportNull$$$0(78);
        }
        super.visitAnonymousInitializer(ktAnonymousInitializer, (Object) null);
    }

    public void visitScriptInitializer(KtScriptInitializer ktScriptInitializer) {
        if (ktScriptInitializer == null) {
            $$$reportNull$$$0(79);
        }
        super.visitScriptInitializer(ktScriptInitializer, (Object) null);
    }

    public void visitClassInitializer(KtClassInitializer ktClassInitializer) {
        if (ktClassInitializer == null) {
            $$$reportNull$$$0(80);
        }
        super.visitClassInitializer(ktClassInitializer, (Object) null);
    }

    public void visitPropertyAccessor(KtPropertyAccessor ktPropertyAccessor) {
        if (ktPropertyAccessor == null) {
            $$$reportNull$$$0(81);
        }
        super.visitPropertyAccessor(ktPropertyAccessor, (Object) null);
    }

    public void visitTypeConstraintList(KtTypeConstraintList ktTypeConstraintList) {
        if (ktTypeConstraintList == null) {
            $$$reportNull$$$0(82);
        }
        super.visitTypeConstraintList(ktTypeConstraintList, (Object) null);
    }

    public void visitTypeConstraint(KtTypeConstraint ktTypeConstraint) {
        if (ktTypeConstraint == null) {
            $$$reportNull$$$0(83);
        }
        super.visitTypeConstraint(ktTypeConstraint, (Object) null);
    }

    public void visitUserType(KtUserType ktUserType) {
        if (ktUserType == null) {
            $$$reportNull$$$0(84);
        }
        super.visitUserType(ktUserType, (Object) null);
    }

    public void visitDynamicType(KtDynamicType ktDynamicType) {
        if (ktDynamicType == null) {
            $$$reportNull$$$0(85);
        }
        super.visitDynamicType(ktDynamicType, (Object) null);
    }

    public void visitFunctionType(KtFunctionType ktFunctionType) {
        if (ktFunctionType == null) {
            $$$reportNull$$$0(86);
        }
        super.visitFunctionType(ktFunctionType, (Object) null);
    }

    public void visitSelfType(KtSelfType ktSelfType) {
        if (ktSelfType == null) {
            $$$reportNull$$$0(87);
        }
        super.visitSelfType(ktSelfType, (Object) null);
    }

    public void visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(88);
        }
        super.visitBinaryWithTypeRHSExpression(ktBinaryExpressionWithTypeRHS, (Object) null);
    }

    public void visitStringTemplateExpression(KtStringTemplateExpression ktStringTemplateExpression) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(89);
        }
        super.visitStringTemplateExpression(ktStringTemplateExpression, (Object) null);
    }

    public void visitNamedDeclaration(KtNamedDeclaration ktNamedDeclaration) {
        if (ktNamedDeclaration == null) {
            $$$reportNull$$$0(90);
        }
        super.visitNamedDeclaration(ktNamedDeclaration, (Object) null);
    }

    public void visitNullableType(KtNullableType ktNullableType) {
        if (ktNullableType == null) {
            $$$reportNull$$$0(91);
        }
        super.visitNullableType(ktNullableType, (Object) null);
    }

    public void visitIntersectionType(KtIntersectionType ktIntersectionType) {
        if (ktIntersectionType == null) {
            $$$reportNull$$$0(92);
        }
        super.visitIntersectionType(ktIntersectionType, (Object) null);
    }

    public void visitTypeProjection(KtTypeProjection ktTypeProjection) {
        if (ktTypeProjection == null) {
            $$$reportNull$$$0(93);
        }
        super.visitTypeProjection(ktTypeProjection, (Object) null);
    }

    public void visitWhenEntry(KtWhenEntry ktWhenEntry) {
        if (ktWhenEntry == null) {
            $$$reportNull$$$0(94);
        }
        super.visitWhenEntry(ktWhenEntry, (Object) null);
    }

    public void visitIsExpression(KtIsExpression ktIsExpression) {
        if (ktIsExpression == null) {
            $$$reportNull$$$0(95);
        }
        super.visitIsExpression(ktIsExpression, (Object) null);
    }

    public void visitWhenConditionIsPattern(KtWhenConditionIsPattern ktWhenConditionIsPattern) {
        if (ktWhenConditionIsPattern == null) {
            $$$reportNull$$$0(96);
        }
        super.visitWhenConditionIsPattern(ktWhenConditionIsPattern, (Object) null);
    }

    public void visitWhenConditionInRange(KtWhenConditionInRange ktWhenConditionInRange) {
        if (ktWhenConditionInRange == null) {
            $$$reportNull$$$0(97);
        }
        super.visitWhenConditionInRange(ktWhenConditionInRange, (Object) null);
    }

    public void visitWhenConditionWithExpression(KtWhenConditionWithExpression ktWhenConditionWithExpression) {
        if (ktWhenConditionWithExpression == null) {
            $$$reportNull$$$0(98);
        }
        super.visitWhenConditionWithExpression(ktWhenConditionWithExpression, (Object) null);
    }

    public void visitObjectDeclaration(KtObjectDeclaration ktObjectDeclaration) {
        if (ktObjectDeclaration == null) {
            $$$reportNull$$$0(99);
        }
        super.visitObjectDeclaration(ktObjectDeclaration, (Object) null);
    }

    public void visitStringTemplateEntry(KtStringTemplateEntry ktStringTemplateEntry) {
        if (ktStringTemplateEntry == null) {
            $$$reportNull$$$0(100);
        }
        super.visitStringTemplateEntry(ktStringTemplateEntry, (Object) null);
    }

    public void visitStringTemplateEntryWithExpression(KtStringTemplateEntryWithExpression ktStringTemplateEntryWithExpression) {
        if (ktStringTemplateEntryWithExpression == null) {
            $$$reportNull$$$0(101);
        }
        super.visitStringTemplateEntryWithExpression(ktStringTemplateEntryWithExpression, (Object) null);
    }

    public void visitBlockStringTemplateEntry(KtBlockStringTemplateEntry ktBlockStringTemplateEntry) {
        if (ktBlockStringTemplateEntry == null) {
            $$$reportNull$$$0(102);
        }
        super.visitBlockStringTemplateEntry(ktBlockStringTemplateEntry, (Object) null);
    }

    public void visitSimpleNameStringTemplateEntry(KtSimpleNameStringTemplateEntry ktSimpleNameStringTemplateEntry) {
        if (ktSimpleNameStringTemplateEntry == null) {
            $$$reportNull$$$0(103);
        }
        super.visitSimpleNameStringTemplateEntry(ktSimpleNameStringTemplateEntry, (Object) null);
    }

    public void visitLiteralStringTemplateEntry(KtLiteralStringTemplateEntry ktLiteralStringTemplateEntry) {
        if (ktLiteralStringTemplateEntry == null) {
            $$$reportNull$$$0(104);
        }
        super.visitLiteralStringTemplateEntry(ktLiteralStringTemplateEntry, (Object) null);
    }

    public void visitEscapeStringTemplateEntry(KtEscapeStringTemplateEntry ktEscapeStringTemplateEntry) {
        if (ktEscapeStringTemplateEntry == null) {
            $$$reportNull$$$0(105);
        }
        super.visitEscapeStringTemplateEntry(ktEscapeStringTemplateEntry, (Object) null);
    }

    public void visitPackageDirective(KtPackageDirective ktPackageDirective) {
        if (ktPackageDirective == null) {
            $$$reportNull$$$0(106);
        }
        super.visitPackageDirective(ktPackageDirective, (Object) null);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitKtElement(KtElement ktElement, Void r2) {
        if (ktElement == null) {
            $$$reportNull$$$0(107);
        }
        visitKtElement(ktElement);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDeclaration(KtDeclaration ktDeclaration, Void r2) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(108);
        }
        visitDeclaration(ktDeclaration);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitClass(KtClass ktClass, Void r2) {
        if (ktClass == null) {
            $$$reportNull$$$0(109);
        }
        visitClass(ktClass);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitClassOrObject(KtClassOrObject ktClassOrObject, Void r2) {
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(110);
        }
        visitClassOrObject(ktClassOrObject);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSecondaryConstructor(KtSecondaryConstructor ktSecondaryConstructor, Void r2) {
        if (ktSecondaryConstructor == null) {
            $$$reportNull$$$0(111);
        }
        visitSecondaryConstructor(ktSecondaryConstructor);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitPrimaryConstructor(KtPrimaryConstructor ktPrimaryConstructor, Void r2) {
        if (ktPrimaryConstructor == null) {
            $$$reportNull$$$0(112);
        }
        visitPrimaryConstructor(ktPrimaryConstructor);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitNamedFunction(KtNamedFunction ktNamedFunction, Void r2) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(113);
        }
        visitNamedFunction(ktNamedFunction);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitProperty(KtProperty ktProperty, Void r2) {
        if (ktProperty == null) {
            $$$reportNull$$$0(114);
        }
        visitProperty(ktProperty);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeAlias(KtTypeAlias ktTypeAlias, Void r2) {
        if (ktTypeAlias == null) {
            $$$reportNull$$$0(115);
        }
        visitTypeAlias(ktTypeAlias);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDestructuringDeclaration(KtDestructuringDeclaration ktDestructuringDeclaration, Void r2) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(116);
        }
        visitDestructuringDeclaration(ktDestructuringDeclaration);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDestructuringDeclarationEntry(KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry, Void r2) {
        if (ktDestructuringDeclarationEntry == null) {
            $$$reportNull$$$0(117);
        }
        visitDestructuringDeclarationEntry(ktDestructuringDeclarationEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitKtFile(KtFile ktFile, Void r2) {
        if (ktFile == null) {
            $$$reportNull$$$0(118);
        }
        visitKtFile(ktFile);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitScript(KtScript ktScript, Void r2) {
        if (ktScript == null) {
            $$$reportNull$$$0(119);
        }
        visitScript(ktScript);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitImportDirective(KtImportDirective ktImportDirective, Void r2) {
        if (ktImportDirective == null) {
            $$$reportNull$$$0(120);
        }
        visitImportDirective(ktImportDirective);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitImportList(KtImportList ktImportList, Void r2) {
        if (ktImportList == null) {
            $$$reportNull$$$0(121);
        }
        visitImportList(ktImportList);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitClassBody(KtClassBody ktClassBody, Void r2) {
        if (ktClassBody == null) {
            $$$reportNull$$$0(122);
        }
        visitClassBody(ktClassBody);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitModifierList(KtModifierList ktModifierList, Void r2) {
        if (ktModifierList == null) {
            $$$reportNull$$$0(123);
        }
        visitModifierList(ktModifierList);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitAnnotation(KtAnnotation ktAnnotation, Void r2) {
        if (ktAnnotation == null) {
            $$$reportNull$$$0(124);
        }
        visitAnnotation(ktAnnotation);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitAnnotationEntry(KtAnnotationEntry ktAnnotationEntry, Void r2) {
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(125);
        }
        visitAnnotationEntry(ktAnnotationEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitConstructorCalleeExpression(KtConstructorCalleeExpression ktConstructorCalleeExpression, Void r2) {
        if (ktConstructorCalleeExpression == null) {
            $$$reportNull$$$0(126);
        }
        visitConstructorCalleeExpression(ktConstructorCalleeExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeParameterList(KtTypeParameterList ktTypeParameterList, Void r2) {
        if (ktTypeParameterList == null) {
            $$$reportNull$$$0(127);
        }
        visitTypeParameterList(ktTypeParameterList);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeParameter(KtTypeParameter ktTypeParameter, Void r2) {
        if (ktTypeParameter == null) {
            $$$reportNull$$$0(128);
        }
        visitTypeParameter(ktTypeParameter);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitEnumEntry(KtEnumEntry ktEnumEntry, Void r2) {
        if (ktEnumEntry == null) {
            $$$reportNull$$$0(129);
        }
        visitEnumEntry(ktEnumEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitParameterList(KtParameterList ktParameterList, Void r2) {
        if (ktParameterList == null) {
            $$$reportNull$$$0(130);
        }
        visitParameterList(ktParameterList);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitParameter(KtParameter ktParameter, Void r2) {
        if (ktParameter == null) {
            $$$reportNull$$$0(131);
        }
        visitParameter(ktParameter);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSuperTypeList(KtSuperTypeList ktSuperTypeList, Void r2) {
        if (ktSuperTypeList == null) {
            $$$reportNull$$$0(132);
        }
        visitSuperTypeList(ktSuperTypeList);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSuperTypeListEntry(KtSuperTypeListEntry ktSuperTypeListEntry, Void r2) {
        if (ktSuperTypeListEntry == null) {
            $$$reportNull$$$0(133);
        }
        visitSuperTypeListEntry(ktSuperTypeListEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDelegatedSuperTypeEntry(KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry, Void r2) {
        if (ktDelegatedSuperTypeEntry == null) {
            $$$reportNull$$$0(134);
        }
        visitDelegatedSuperTypeEntry(ktDelegatedSuperTypeEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSuperTypeCallEntry(KtSuperTypeCallEntry ktSuperTypeCallEntry, Void r2) {
        if (ktSuperTypeCallEntry == null) {
            $$$reportNull$$$0(135);
        }
        visitSuperTypeCallEntry(ktSuperTypeCallEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSuperTypeEntry(KtSuperTypeEntry ktSuperTypeEntry, Void r2) {
        if (ktSuperTypeEntry == null) {
            $$$reportNull$$$0(136);
        }
        visitSuperTypeEntry(ktSuperTypeEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitConstructorDelegationCall(KtConstructorDelegationCall ktConstructorDelegationCall, Void r2) {
        if (ktConstructorDelegationCall == null) {
            $$$reportNull$$$0(137);
        }
        visitConstructorDelegationCall(ktConstructorDelegationCall);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitPropertyDelegate(KtPropertyDelegate ktPropertyDelegate, Void r2) {
        if (ktPropertyDelegate == null) {
            $$$reportNull$$$0(138);
        }
        visitPropertyDelegate(ktPropertyDelegate);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeReference(KtTypeReference ktTypeReference, Void r2) {
        if (ktTypeReference == null) {
            $$$reportNull$$$0(139);
        }
        visitTypeReference(ktTypeReference);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitValueArgumentList(KtValueArgumentList ktValueArgumentList, Void r2) {
        if (ktValueArgumentList == null) {
            $$$reportNull$$$0(140);
        }
        visitValueArgumentList(ktValueArgumentList);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitArgument(KtValueArgument ktValueArgument, Void r2) {
        if (ktValueArgument == null) {
            $$$reportNull$$$0(141);
        }
        visitArgument(ktValueArgument);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitExpression(KtExpression ktExpression, Void r2) {
        if (ktExpression == null) {
            $$$reportNull$$$0(142);
        }
        visitExpression(ktExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitLoopExpression(KtLoopExpression ktLoopExpression, Void r2) {
        if (ktLoopExpression == null) {
            $$$reportNull$$$0(143);
        }
        visitLoopExpression(ktLoopExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitConstantExpression(KtConstantExpression ktConstantExpression, Void r2) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(144);
        }
        visitConstantExpression(ktConstantExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, Void r2) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(145);
        }
        visitSimpleNameExpression(ktSimpleNameExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitReferenceExpression(KtReferenceExpression ktReferenceExpression, Void r2) {
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(146);
        }
        visitReferenceExpression(ktReferenceExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitLabeledExpression(KtLabeledExpression ktLabeledExpression, Void r2) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(147);
        }
        visitLabeledExpression(ktLabeledExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitPrefixExpression(KtPrefixExpression ktPrefixExpression, Void r2) {
        if (ktPrefixExpression == null) {
            $$$reportNull$$$0(148);
        }
        visitPrefixExpression(ktPrefixExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitPostfixExpression(KtPostfixExpression ktPostfixExpression, Void r2) {
        if (ktPostfixExpression == null) {
            $$$reportNull$$$0(149);
        }
        visitPostfixExpression(ktPostfixExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitUnaryExpression(KtUnaryExpression ktUnaryExpression, Void r2) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(150);
        }
        visitUnaryExpression(ktUnaryExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitBinaryExpression(KtBinaryExpression ktBinaryExpression, Void r2) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(151);
        }
        visitBinaryExpression(ktBinaryExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitReturnExpression(KtReturnExpression ktReturnExpression, Void r2) {
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(152);
        }
        visitReturnExpression(ktReturnExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitExpressionWithLabel(KtExpressionWithLabel ktExpressionWithLabel, Void r2) {
        if (ktExpressionWithLabel == null) {
            $$$reportNull$$$0(153);
        }
        visitExpressionWithLabel(ktExpressionWithLabel);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitThrowExpression(KtThrowExpression ktThrowExpression, Void r2) {
        if (ktThrowExpression == null) {
            $$$reportNull$$$0(154);
        }
        visitThrowExpression(ktThrowExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitBreakExpression(KtBreakExpression ktBreakExpression, Void r2) {
        if (ktBreakExpression == null) {
            $$$reportNull$$$0(155);
        }
        visitBreakExpression(ktBreakExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitContinueExpression(KtContinueExpression ktContinueExpression, Void r2) {
        if (ktContinueExpression == null) {
            $$$reportNull$$$0(156);
        }
        visitContinueExpression(ktContinueExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitIfExpression(KtIfExpression ktIfExpression, Void r2) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(157);
        }
        visitIfExpression(ktIfExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhenExpression(KtWhenExpression ktWhenExpression, Void r2) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(158);
        }
        visitWhenExpression(ktWhenExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitCollectionLiteralExpression(KtCollectionLiteralExpression ktCollectionLiteralExpression, Void r2) {
        if (ktCollectionLiteralExpression == null) {
            $$$reportNull$$$0(159);
        }
        visitCollectionLiteralExpression(ktCollectionLiteralExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTryExpression(KtTryExpression ktTryExpression, Void r2) {
        if (ktTryExpression == null) {
            $$$reportNull$$$0(160);
        }
        visitTryExpression(ktTryExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitForExpression(KtForExpression ktForExpression, Void r2) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(161);
        }
        visitForExpression(ktForExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhileExpression(KtWhileExpression ktWhileExpression, Void r2) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(162);
        }
        visitWhileExpression(ktWhileExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression, Void r2) {
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(163);
        }
        visitDoWhileExpression(ktDoWhileExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitLambdaExpression(KtLambdaExpression ktLambdaExpression, Void r2) {
        if (ktLambdaExpression == null) {
            $$$reportNull$$$0(164);
        }
        visitLambdaExpression(ktLambdaExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, Void r2) {
        if (ktAnnotatedExpression == null) {
            $$$reportNull$$$0(165);
        }
        visitAnnotatedExpression(ktAnnotatedExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitCallExpression(KtCallExpression ktCallExpression, Void r2) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(166);
        }
        visitCallExpression(ktCallExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression, Void r2) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(167);
        }
        visitArrayAccessExpression(ktArrayAccessExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitQualifiedExpression(KtQualifiedExpression ktQualifiedExpression, Void r2) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(168);
        }
        visitQualifiedExpression(ktQualifiedExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDoubleColonExpression(KtDoubleColonExpression ktDoubleColonExpression, Void r2) {
        if (ktDoubleColonExpression == null) {
            $$$reportNull$$$0(169);
        }
        visitDoubleColonExpression(ktDoubleColonExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression, Void r2) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(170);
        }
        visitCallableReferenceExpression(ktCallableReferenceExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitClassLiteralExpression(KtClassLiteralExpression ktClassLiteralExpression, Void r2) {
        if (ktClassLiteralExpression == null) {
            $$$reportNull$$$0(171);
        }
        visitClassLiteralExpression(ktClassLiteralExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitDotQualifiedExpression(KtDotQualifiedExpression ktDotQualifiedExpression, Void r2) {
        if (ktDotQualifiedExpression == null) {
            $$$reportNull$$$0(172);
        }
        visitDotQualifiedExpression(ktDotQualifiedExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSafeQualifiedExpression(KtSafeQualifiedExpression ktSafeQualifiedExpression, Void r2) {
        if (ktSafeQualifiedExpression == null) {
            $$$reportNull$$$0(173);
        }
        visitSafeQualifiedExpression(ktSafeQualifiedExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitObjectLiteralExpression(KtObjectLiteralExpression ktObjectLiteralExpression, Void r2) {
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(174);
        }
        visitObjectLiteralExpression(ktObjectLiteralExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitBlockExpression(KtBlockExpression ktBlockExpression, Void r2) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(175);
        }
        visitBlockExpression(ktBlockExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitCatchSection(KtCatchClause ktCatchClause, Void r2) {
        if (ktCatchClause == null) {
            $$$reportNull$$$0(176);
        }
        visitCatchSection(ktCatchClause);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitFinallySection(KtFinallySection ktFinallySection, Void r2) {
        if (ktFinallySection == null) {
            $$$reportNull$$$0(177);
        }
        visitFinallySection(ktFinallySection);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeArgumentList(KtTypeArgumentList ktTypeArgumentList, Void r2) {
        if (ktTypeArgumentList == null) {
            $$$reportNull$$$0(178);
        }
        visitTypeArgumentList(ktTypeArgumentList);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitThisExpression(KtThisExpression ktThisExpression, Void r2) {
        if (ktThisExpression == null) {
            $$$reportNull$$$0(179);
        }
        visitThisExpression(ktThisExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSuperExpression(KtSuperExpression ktSuperExpression, Void r2) {
        if (ktSuperExpression == null) {
            $$$reportNull$$$0(180);
        }
        visitSuperExpression(ktSuperExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression, Void r2) {
        if (ktParenthesizedExpression == null) {
            $$$reportNull$$$0(181);
        }
        visitParenthesizedExpression(ktParenthesizedExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitInitializerList(KtInitializerList ktInitializerList, Void r2) {
        if (ktInitializerList == null) {
            $$$reportNull$$$0(182);
        }
        visitInitializerList(ktInitializerList);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitAnonymousInitializer(KtAnonymousInitializer ktAnonymousInitializer, Void r2) {
        if (ktAnonymousInitializer == null) {
            $$$reportNull$$$0(183);
        }
        visitAnonymousInitializer(ktAnonymousInitializer);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitPropertyAccessor(KtPropertyAccessor ktPropertyAccessor, Void r2) {
        if (ktPropertyAccessor == null) {
            $$$reportNull$$$0(184);
        }
        visitPropertyAccessor(ktPropertyAccessor);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeConstraintList(KtTypeConstraintList ktTypeConstraintList, Void r2) {
        if (ktTypeConstraintList == null) {
            $$$reportNull$$$0(185);
        }
        visitTypeConstraintList(ktTypeConstraintList);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeConstraint(KtTypeConstraint ktTypeConstraint, Void r2) {
        if (ktTypeConstraint == null) {
            $$$reportNull$$$0(186);
        }
        visitTypeConstraint(ktTypeConstraint);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitUserType(KtUserType ktUserType, Void r2) {
        if (ktUserType == null) {
            $$$reportNull$$$0(187);
        }
        visitUserType(ktUserType);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitDynamicType(KtDynamicType ktDynamicType, Void r2) {
        if (ktDynamicType == null) {
            $$$reportNull$$$0(188);
        }
        visitDynamicType(ktDynamicType);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitFunctionType(KtFunctionType ktFunctionType, Void r2) {
        if (ktFunctionType == null) {
            $$$reportNull$$$0(189);
        }
        visitFunctionType(ktFunctionType);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSelfType(KtSelfType ktSelfType, Void r2) {
        if (ktSelfType == null) {
            $$$reportNull$$$0(190);
        }
        visitSelfType(ktSelfType);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, Void r2) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(191);
        }
        visitBinaryWithTypeRHSExpression(ktBinaryExpressionWithTypeRHS);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitStringTemplateExpression(KtStringTemplateExpression ktStringTemplateExpression, Void r2) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(192);
        }
        visitStringTemplateExpression(ktStringTemplateExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitNamedDeclaration(KtNamedDeclaration ktNamedDeclaration, Void r2) {
        if (ktNamedDeclaration == null) {
            $$$reportNull$$$0(193);
        }
        visitNamedDeclaration(ktNamedDeclaration);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitNullableType(KtNullableType ktNullableType, Void r2) {
        if (ktNullableType == null) {
            $$$reportNull$$$0(194);
        }
        visitNullableType(ktNullableType);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitIntersectionType(KtIntersectionType ktIntersectionType, Void r2) {
        if (ktIntersectionType == null) {
            $$$reportNull$$$0(195);
        }
        visitIntersectionType(ktIntersectionType);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitTypeProjection(KtTypeProjection ktTypeProjection, Void r2) {
        if (ktTypeProjection == null) {
            $$$reportNull$$$0(196);
        }
        visitTypeProjection(ktTypeProjection);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhenEntry(KtWhenEntry ktWhenEntry, Void r2) {
        if (ktWhenEntry == null) {
            $$$reportNull$$$0(197);
        }
        visitWhenEntry(ktWhenEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitIsExpression(KtIsExpression ktIsExpression, Void r2) {
        if (ktIsExpression == null) {
            $$$reportNull$$$0(198);
        }
        visitIsExpression(ktIsExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhenConditionIsPattern(KtWhenConditionIsPattern ktWhenConditionIsPattern, Void r2) {
        if (ktWhenConditionIsPattern == null) {
            $$$reportNull$$$0(199);
        }
        visitWhenConditionIsPattern(ktWhenConditionIsPattern);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhenConditionInRange(KtWhenConditionInRange ktWhenConditionInRange, Void r2) {
        if (ktWhenConditionInRange == null) {
            $$$reportNull$$$0(200);
        }
        visitWhenConditionInRange(ktWhenConditionInRange);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitWhenConditionWithExpression(KtWhenConditionWithExpression ktWhenConditionWithExpression, Void r2) {
        if (ktWhenConditionWithExpression == null) {
            $$$reportNull$$$0(201);
        }
        visitWhenConditionWithExpression(ktWhenConditionWithExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitObjectDeclaration(KtObjectDeclaration ktObjectDeclaration, Void r2) {
        if (ktObjectDeclaration == null) {
            $$$reportNull$$$0(202);
        }
        visitObjectDeclaration(ktObjectDeclaration);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitStringTemplateEntry(KtStringTemplateEntry ktStringTemplateEntry, Void r2) {
        if (ktStringTemplateEntry == null) {
            $$$reportNull$$$0(203);
        }
        visitStringTemplateEntry(ktStringTemplateEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitStringTemplateEntryWithExpression(KtStringTemplateEntryWithExpression ktStringTemplateEntryWithExpression, Void r2) {
        if (ktStringTemplateEntryWithExpression == null) {
            $$$reportNull$$$0(204);
        }
        visitStringTemplateEntryWithExpression(ktStringTemplateEntryWithExpression);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitBlockStringTemplateEntry(KtBlockStringTemplateEntry ktBlockStringTemplateEntry, Void r2) {
        if (ktBlockStringTemplateEntry == null) {
            $$$reportNull$$$0(205);
        }
        visitBlockStringTemplateEntry(ktBlockStringTemplateEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitSimpleNameStringTemplateEntry(KtSimpleNameStringTemplateEntry ktSimpleNameStringTemplateEntry, Void r2) {
        if (ktSimpleNameStringTemplateEntry == null) {
            $$$reportNull$$$0(206);
        }
        visitSimpleNameStringTemplateEntry(ktSimpleNameStringTemplateEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitLiteralStringTemplateEntry(KtLiteralStringTemplateEntry ktLiteralStringTemplateEntry, Void r2) {
        if (ktLiteralStringTemplateEntry == null) {
            $$$reportNull$$$0(207);
        }
        visitLiteralStringTemplateEntry(ktLiteralStringTemplateEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public final Void visitEscapeStringTemplateEntry(KtEscapeStringTemplateEntry ktEscapeStringTemplateEntry, Void r2) {
        if (ktEscapeStringTemplateEntry == null) {
            $$$reportNull$$$0(208);
        }
        visitEscapeStringTemplateEntry(ktEscapeStringTemplateEntry);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitPackageDirective(KtPackageDirective ktPackageDirective, Void r2) {
        if (ktPackageDirective == null) {
            $$$reportNull$$$0(209);
        }
        visitPackageDirective(ktPackageDirective);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitScriptInitializer(KtScriptInitializer ktScriptInitializer, Void r2) {
        if (ktScriptInitializer == null) {
            $$$reportNull$$$0(210);
        }
        visitScriptInitializer(ktScriptInitializer);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitClassInitializer(KtClassInitializer ktClassInitializer, Void r2) {
        if (ktClassInitializer == null) {
            $$$reportNull$$$0(211);
        }
        visitClassInitializer(ktClassInitializer);
        return null;
    }
}
