package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.ExpressionContext;

/* JADX INFO: loaded from: classes14.dex */
public interface InvocationSite {
    void acceptPotentiallyCompatibleMethods(MethodBinding[] methodBindingArr);

    boolean checkingPotentialCompatibility();

    InferenceContext18 freshInferenceContext(Scope scope);

    TypeBinding[] genericTypeArguments();

    ExpressionContext getExpressionContext();

    TypeBinding invocationTargetType();

    boolean isQualifiedSuper();

    boolean isSuperAccess();

    boolean isTypeAccess();

    boolean receiverIsImplicitThis();

    void setActualReceiverType(ReferenceBinding referenceBinding);

    void setDepth(int i);

    void setFieldIndex(int i);

    int sourceEnd();

    int sourceStart();

    public static class EmptyWithAstNode implements InvocationSite {
        ASTNode node;

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public void acceptPotentiallyCompatibleMethods(MethodBinding[] methodBindingArr) {
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public boolean checkingPotentialCompatibility() {
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public InferenceContext18 freshInferenceContext(Scope scope) {
            return null;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public TypeBinding[] genericTypeArguments() {
            return null;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public TypeBinding invocationTargetType() {
            return null;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public boolean isQualifiedSuper() {
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public boolean isSuperAccess() {
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public boolean isTypeAccess() {
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public boolean receiverIsImplicitThis() {
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public void setActualReceiverType(ReferenceBinding referenceBinding) {
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public void setDepth(int i) {
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public void setFieldIndex(int i) {
        }

        public EmptyWithAstNode(ASTNode aSTNode) {
            this.node = aSTNode;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public int sourceEnd() {
            return this.node.sourceEnd;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public int sourceStart() {
            return this.node.sourceStart;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
        public ExpressionContext getExpressionContext() {
            return ExpressionContext.VANILLA_CONTEXT;
        }
    }
}
