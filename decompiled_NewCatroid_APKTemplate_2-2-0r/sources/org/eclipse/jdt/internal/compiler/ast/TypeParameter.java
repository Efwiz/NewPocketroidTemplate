package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import java.util.List;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class TypeParameter extends AbstractVariableDeclaration {
    public TypeVariableBinding binding;
    public TypeReference[] bounds;

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration
    public int getKind() {
        return 6;
    }

    public void checkBounds(Scope scope) {
        if (this.type != null) {
            this.type.checkBounds(scope);
        }
        TypeReference[] typeReferenceArr = this.bounds;
        if (typeReferenceArr != null) {
            int length = typeReferenceArr.length;
            for (int i = 0; i < length; i++) {
                this.bounds[i].checkBounds(scope);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v8 */
    public void getAllAnnotationContexts(int i, int i2, List list) {
        boolean z;
        int i3;
        boolean zIsInterface;
        TypeReference.AnnotationCollector annotationCollector = new TypeReference.AnnotationCollector(this, i, i2, list);
        int i4 = 0;
        if (this.annotations != null) {
            int length = this.annotations.length;
            for (int i5 = 0; i5 < length; i5++) {
                this.annotations[i5].traverse(annotationCollector, (BlockScope) null);
            }
        }
        int i6 = annotationCollector.targetType;
        if (i6 == 0) {
            annotationCollector.targetType = 17;
        } else if (i6 == 1) {
            annotationCollector.targetType = 18;
        }
        if (this.type != null) {
            zIsInterface = this.type.resolvedType.isInterface();
            if ((this.type.bits & 1048576) != 0) {
                z = zIsInterface;
                annotationCollector.info2 = zIsInterface ? 1 : 0;
                this.type.traverse(annotationCollector, (BlockScope) null);
                z = zIsInterface;
            }
        } else {
            z = false;
        }
        z = zIsInterface;
        TypeReference[] typeReferenceArr = this.bounds;
        if (typeReferenceArr != null) {
            int length2 = typeReferenceArr.length;
            while (i4 < length2) {
                TypeReference typeReference = this.bounds[i4];
                if ((typeReference.bits & 1048576) == 0) {
                    i3 = z;
                } else {
                    i3 = z;
                    i3++;
                    annotationCollector.info2 = i3;
                    typeReference.traverse(annotationCollector, (BlockScope) null);
                }
                i4++;
                i3 = i3;
            }
            i3 = z;
        }
    }

    private void internalResolve(Scope scope, boolean z) {
        Binding binding;
        if (this.binding != null && (binding = scope.parent.getBinding(this.f617name, 4, this, false)) != null && this.binding != binding && binding.isValidBinding() && (binding.kind() != 4100 || !z)) {
            scope.problemReporter().typeHiding(this, binding);
        }
        if (this.annotations != null || scope.environment().usesNullTypeAnnotations()) {
            resolveAnnotations(scope);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration, org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        internalResolve(blockScope, blockScope.methodScope().isStatic);
    }

    public void resolve(ClassScope classScope) {
        internalResolve(classScope, classScope.enclosingSourceType().isStatic());
    }

    public void resolveAnnotations(Scope scope) {
        TypeVariableBinding typeVariableBinding;
        BlockScope blockScopeTypeAnnotationsResolutionScope = Scope.typeAnnotationsResolutionScope(scope);
        if (blockScopeTypeAnnotationsResolutionScope != null) {
            AnnotationBinding[] annotationBindingArrResolveAnnotations = resolveAnnotations(blockScopeTypeAnnotationsResolutionScope, this.annotations, this.binding, false);
            boolean z = scope.environment().globalOptions.isAnnotationBasedNullAnalysisEnabled;
            if (annotationBindingArrResolveAnnotations != null && annotationBindingArrResolveAnnotations.length > 0) {
                this.binding.setTypeAnnotations(annotationBindingArrResolveAnnotations, z);
                scope.referenceCompilationUnit().compilationResult.hasAnnotations = true;
            }
            if (z && (typeVariableBinding = this.binding) != null && typeVariableBinding.isValidBinding()) {
                this.binding.evaluateNullAnnotations(scope, this);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration, org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        if (this.annotations != null) {
            printAnnotations(this.annotations, stringBuffer);
            stringBuffer.append(StringUtil.SPACE);
        }
        stringBuffer.append(this.f617name);
        if (this.type != null) {
            stringBuffer.append(" extends ");
            this.type.print(0, stringBuffer);
        }
        if (this.bounds != null) {
            for (int i2 = 0; i2 < this.bounds.length; i2++) {
                stringBuffer.append(" & ");
                this.bounds[i2].print(0, stringBuffer);
            }
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    this.annotations[i].traverse(aSTVisitor, blockScope);
                }
            }
            if (this.type != null) {
                this.type.traverse(aSTVisitor, blockScope);
            }
            TypeReference[] typeReferenceArr = this.bounds;
            if (typeReferenceArr != null) {
                int length2 = typeReferenceArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.bounds[i2].traverse(aSTVisitor, blockScope);
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    this.annotations[i].traverse(aSTVisitor, classScope);
                }
            }
            if (this.type != null) {
                this.type.traverse(aSTVisitor, classScope);
            }
            TypeReference[] typeReferenceArr = this.bounds;
            if (typeReferenceArr != null) {
                int length2 = typeReferenceArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.bounds[i2].traverse(aSTVisitor, classScope);
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
