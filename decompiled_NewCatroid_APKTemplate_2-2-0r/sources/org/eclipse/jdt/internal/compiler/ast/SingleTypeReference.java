package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class SingleTypeReference extends TypeReference {
    public char[] token;

    public SingleTypeReference(char[] cArr, long j) {
        this.token = cArr;
        this.sourceStart = (int) (j >>> 32);
        this.sourceEnd = (int) (j & 4294967295L);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeReference augmentTypeWithAdditionalDimensions(int i, Annotation[][] annotationArr, boolean z) {
        ArrayTypeReference arrayTypeReference = new ArrayTypeReference(this.token, dimensions() + i, getMergedAnnotationsOnDimensions(i, annotationArr), ((long) this.sourceEnd) + (((long) this.sourceStart) << 32));
        arrayTypeReference.annotations = this.annotations;
        arrayTypeReference.bits |= this.bits & 1048576;
        if (!z) {
            arrayTypeReference.extendedDimensions = i;
        }
        return arrayTypeReference;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[] getLastToken() {
        return this.token;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding getTypeBinding(Scope scope) {
        if (this.resolvedType != null) {
            return this.resolvedType;
        }
        this.resolvedType = scope.getType(this.token);
        if (this.resolvedType instanceof TypeVariableBinding) {
            TypeVariableBinding typeVariableBinding = (TypeVariableBinding) this.resolvedType;
            if (typeVariableBinding.declaringElement instanceof SourceTypeBinding) {
                scope.tagAsAccessingEnclosingInstanceStateOf((ReferenceBinding) typeVariableBinding.declaringElement, true);
            }
        } else if (this.resolvedType instanceof LocalTypeBinding) {
            LocalTypeBinding localTypeBinding = (LocalTypeBinding) this.resolvedType;
            MethodScope methodScope = scope.methodScope();
            if (methodScope != null && !methodScope.isStatic) {
                methodScope.tagAsAccessingEnclosingInstanceStateOf(localTypeBinding, false);
            }
        }
        if (scope.kind == 3 && this.resolvedType.isValidBinding() && ((ClassScope) scope).detectHierarchyCycle(this.resolvedType, this)) {
            return null;
        }
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[][] getTypeName() {
        return new char[][]{this.token};
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public boolean isBaseTypeReference() {
        return this.token == BYTE || this.token == SHORT || this.token == INT || this.token == LONG || this.token == FLOAT || this.token == DOUBLE || this.token == CHAR || this.token == BOOLEAN || this.token == NULL || this.token == VOID;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        if (this.annotations != null && this.annotations[0] != null) {
            printAnnotations(this.annotations[0], stringBuffer);
            stringBuffer.append(StringUtil.SPACE);
        }
        return stringBuffer.append(this.token);
    }

    public TypeBinding resolveTypeEnclosing(BlockScope blockScope, ReferenceBinding referenceBinding) {
        boolean z;
        this.resolvedType = blockScope.getMemberType(this.token, referenceBinding);
        resolveAnnotations(blockScope, 0);
        TypeBinding typeBindingClosestMatch = this.resolvedType;
        if (typeBindingClosestMatch.isValidBinding()) {
            z = false;
        } else {
            blockScope.problemReporter().invalidEnclosingType(this, typeBindingClosestMatch, referenceBinding);
            typeBindingClosestMatch = ((ReferenceBinding) typeBindingClosestMatch).closestMatch();
            if (typeBindingClosestMatch == null) {
                return null;
            }
            z = true;
        }
        if (isTypeUseDeprecated(typeBindingClosestMatch, blockScope)) {
            reportDeprecatedType(typeBindingClosestMatch, blockScope);
        }
        TypeBinding typeBindingConvertToRawType = blockScope.environment().convertToRawType(typeBindingClosestMatch, false);
        if (typeBindingConvertToRawType.isRawType() && (this.bits & 1073741824) == 0 && blockScope.compilerOptions().getSeverity(CompilerOptions.RawTypeReference) != 256) {
            blockScope.problemReporter().rawTypeReference(this, typeBindingConvertToRawType);
        }
        if (z) {
            return typeBindingConvertToRawType;
        }
        this.resolvedType = typeBindingConvertToRawType;
        return typeBindingConvertToRawType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope) && this.annotations != null) {
            Annotation[] annotationArr = this.annotations[0];
            int length = annotationArr == null ? 0 : annotationArr.length;
            for (int i = 0; i < length; i++) {
                annotationArr[i].traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope) && this.annotations != null) {
            Annotation[] annotationArr = this.annotations[0];
            int length = annotationArr == null ? 0 : annotationArr.length;
            for (int i = 0; i < length; i++) {
                annotationArr[i].traverse(aSTVisitor, classScope);
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
