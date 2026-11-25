package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class JavadocArgumentExpression extends Expression {
    public Argument argument;
    public char[] token;

    public JavadocArgumentExpression(char[] cArr, int i, int i2, TypeReference typeReference) {
        this.token = cArr;
        this.sourceStart = i;
        this.sourceEnd = i2;
        this.argument = new Argument(cArr, (((long) i) << 32) + ((long) i2), typeReference, 0);
        this.bits |= 32768;
    }

    private TypeBinding internalResolveType(Scope scope) {
        TypeReference typeReference;
        this.constant = Constant.NotAConstant;
        if (this.resolvedType != null) {
            if (this.resolvedType.isValidBinding()) {
                return this.resolvedType;
            }
            return null;
        }
        Argument argument = this.argument;
        if (argument == null || (typeReference = argument.type) == null) {
            return null;
        }
        this.resolvedType = typeReference.getTypeBinding(scope);
        typeReference.resolvedType = this.resolvedType;
        if (this.resolvedType == null) {
            return null;
        }
        if ((typeReference instanceof SingleTypeReference) && this.resolvedType.leafComponentType().enclosingType() != null && scope.compilerOptions().complianceLevel <= ClassFileConstants.JDK1_4) {
            scope.problemReporter().javadocInvalidMemberTypeQualification(this.sourceStart, this.sourceEnd, scope.getDeclarationModifiers());
        } else if ((typeReference instanceof QualifiedTypeReference) && (referenceBindingEnclosingType = this.resolvedType.leafComponentType().enclosingType()) != null) {
            int i = 2;
            while (true) {
                ReferenceBinding referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
                if (referenceBindingEnclosingType == null) {
                    break;
                }
                i++;
            }
            int length = typeReference.getTypeName().length;
            if (length != i && length != i + this.resolvedType.getPackage().compoundName.length) {
                scope.problemReporter().javadocInvalidMemberTypeQualification(typeReference.sourceStart, typeReference.sourceEnd, scope.getDeclarationModifiers());
            }
        }
        if (!this.resolvedType.isValidBinding()) {
            scope.problemReporter().javadocInvalidType(typeReference, this.resolvedType, scope.getDeclarationModifiers());
            return null;
        }
        if (isTypeUseDeprecated(this.resolvedType, scope)) {
            scope.problemReporter().javadocDeprecatedType(this.resolvedType, typeReference, scope.getDeclarationModifiers());
        }
        TypeBinding typeBindingConvertToRawType = scope.environment().convertToRawType(this.resolvedType, true);
        this.resolvedType = typeBindingConvertToRawType;
        return typeBindingConvertToRawType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        Argument argument = this.argument;
        if (argument == null) {
            char[] cArr = this.token;
            if (cArr != null) {
                stringBuffer.append(cArr);
            }
        } else {
            argument.print(i, stringBuffer);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        Argument argument = this.argument;
        if (argument != null) {
            argument.resolve(blockScope);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        return internalResolveType(blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(ClassScope classScope) {
        return internalResolveType(classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        Argument argument;
        if (aSTVisitor.visit(this, blockScope) && (argument = this.argument) != null) {
            argument.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        Argument argument;
        if (aSTVisitor.visit(this, classScope) && (argument = this.argument) != null) {
            argument.traverse(aSTVisitor, classScope);
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
