package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.ElementValuePair;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.parser.Parser;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotationMethodDeclaration extends MethodDeclaration {
    public Expression defaultValue;
    public int extendedDimensions;

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public boolean isAnnotationMethod() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.MethodDeclaration, org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public boolean isMethod() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.MethodDeclaration, org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void parseStatements(Parser parser, CompilationUnitDeclaration compilationUnitDeclaration) {
    }

    public AnnotationMethodDeclaration(CompilationResult compilationResult) {
        super(compilationResult);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void generateCode(ClassFile classFile) {
        classFile.generateMethodInfoHeader(this.binding);
        classFile.completeMethodInfo(this.binding, classFile.contentsOffset, classFile.generateMethodInfoAttributes(this.binding, this));
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer);
        printModifiers(this.modifiers, stringBuffer);
        if (this.annotations != null) {
            printAnnotations(this.annotations, stringBuffer);
            stringBuffer.append(StringUtil.SPACE);
        }
        TypeParameter[] typeParameterArrTypeParameters = typeParameters();
        if (typeParameterArrTypeParameters != null) {
            stringBuffer.append('<');
            int length = typeParameterArrTypeParameters.length - 1;
            for (int i2 = 0; i2 < length; i2++) {
                typeParameterArrTypeParameters[i2].print(0, stringBuffer);
                stringBuffer.append(", ");
            }
            typeParameterArrTypeParameters[length].print(0, stringBuffer);
            stringBuffer.append('>');
        }
        printReturnType(0, stringBuffer).append(this.selector).append('(');
        if (this.arguments != null) {
            for (int i3 = 0; i3 < this.arguments.length; i3++) {
                if (i3 > 0) {
                    stringBuffer.append(", ");
                }
                this.arguments[i3].print(0, stringBuffer);
            }
        }
        stringBuffer.append(')');
        if (this.thrownExceptions != null) {
            stringBuffer.append(" throws ");
            for (int i4 = 0; i4 < this.thrownExceptions.length; i4++) {
                if (i4 > 0) {
                    stringBuffer.append(", ");
                }
                this.thrownExceptions[i4].print(0, stringBuffer);
            }
        }
        if (this.defaultValue != null) {
            stringBuffer.append(" default ");
            this.defaultValue.print(0, stringBuffer);
        }
        printBody(i + 1, stringBuffer);
        return stringBuffer;
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0066  */
    @Override // org.eclipse.jdt.internal.compiler.ast.MethodDeclaration, org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void resolveStatements() {
        TypeBinding typeBinding;
        super.resolveStatements();
        if (this.arguments != null) {
            this.scope.problemReporter().annotationMembersCannotHaveParameters(this);
        }
        if (this.typeParameters != null) {
            this.scope.problemReporter().annotationMembersCannotHaveTypeParameters(this);
        }
        if (this.extendedDimensions != 0) {
            this.scope.problemReporter().illegalExtendedDimensions(this);
        }
        if (this.binding == null || (typeBinding = this.binding.returnType) == null) {
            return;
        }
        TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
        if (typeBinding.dimensions() <= 1) {
            int i = typeBindingLeafComponentType.erasure().f626id;
            if (i != 2 && i != 3 && i != 4 && i != 5 && i != 16) {
                switch (i) {
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        break;
                    default:
                        if (!typeBindingLeafComponentType.isEnum() && !typeBindingLeafComponentType.isAnnotationType()) {
                            this.scope.problemReporter().invalidAnnotationMemberType(this);
                        }
                        break;
                }
            }
        } else {
            this.scope.problemReporter().invalidAnnotationMemberType(this);
        }
        if (this.defaultValue != null) {
            MemberValuePair memberValuePair = new MemberValuePair(this.selector, this.sourceStart, this.sourceEnd, this.defaultValue);
            memberValuePair.binding = this.binding;
            if (memberValuePair.value.resolvedType == null) {
                memberValuePair.resolveTypeExpecting(this.scope, typeBinding);
            }
            this.binding.setDefaultValue(ElementValuePair.getValue(this.defaultValue));
            return;
        }
        this.binding.setDefaultValue(null);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.MethodDeclaration, org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    this.annotations[i].traverse(aSTVisitor, this.scope);
                }
            }
            if (this.returnType != null) {
                this.returnType.traverse(aSTVisitor, this.scope);
            }
            Expression expression = this.defaultValue;
            if (expression != null) {
                expression.traverse(aSTVisitor, this.scope);
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
