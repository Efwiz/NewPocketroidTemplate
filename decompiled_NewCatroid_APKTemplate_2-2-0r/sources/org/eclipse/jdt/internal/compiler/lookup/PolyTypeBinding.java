package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;

/* JADX INFO: loaded from: classes14.dex */
public class PolyTypeBinding extends TypeBinding {
    Expression expression;
    boolean vanillaCompatibilty = true;

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isPolyType() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        return 65540;
    }

    public PolyTypeBinding(Expression expression) {
        this.expression = expression;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public PackageBinding getPackage() {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        return this.vanillaCompatibilty ? this.expression.isCompatibleWith(typeBinding, scope) : this.expression.isBoxingCompatibleWith(typeBinding, scope);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isPotentiallyCompatibleWith(TypeBinding typeBinding, Scope scope) {
        return this.expression.isPotentiallyCompatibleWith(typeBinding, scope);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isFunctionalType() {
        return this.expression.isFunctionalType();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] qualifiedSourceName() {
        return readableName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] sourceName() {
        return readableName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        return this.expression.printExpression(0, new StringBuffer()).toString().toCharArray();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        Expression expression = this.expression;
        return expression instanceof LambdaExpression ? ((LambdaExpression) expression).printExpression(0, new StringBuffer(), true).toString().toCharArray() : readableName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean sIsMoreSpecific(TypeBinding typeBinding, TypeBinding typeBinding2, Scope scope) {
        return this.expression.sIsMoreSpecific(typeBinding, typeBinding2, scope);
    }

    public String toString() {
        return this.expression.printExpression(0, new StringBuffer("PolyTypeBinding for: ")).toString();
    }

    public TypeBinding computeBoxingType() {
        PolyTypeBinding polyTypeBinding = new PolyTypeBinding(this.expression);
        polyTypeBinding.vanillaCompatibilty = !this.vanillaCompatibilty;
        return polyTypeBinding;
    }
}
