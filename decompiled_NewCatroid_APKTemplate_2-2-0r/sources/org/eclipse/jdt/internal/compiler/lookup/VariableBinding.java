package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.impl.Constant;

/* JADX INFO: loaded from: classes14.dex */
public abstract class VariableBinding extends Binding {
    protected Constant constant;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public int f627id;
    public int modifiers;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public char[] f628name;
    public long tagBits;
    public TypeBinding type;

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public abstract AnnotationBinding[] getAnnotations();

    public VariableBinding(char[] cArr, TypeBinding typeBinding, int i, Constant constant) {
        this.f628name = cArr;
        this.type = typeBinding;
        this.modifiers = i;
        this.constant = constant;
        if (typeBinding != null) {
            this.tagBits = (typeBinding.tagBits & 128) | this.tagBits;
        }
    }

    public Constant constant() {
        return this.constant;
    }

    public Constant constant(Scope scope) {
        return constant();
    }

    public final boolean isBlankFinal() {
        return (this.modifiers & 67108864) != 0;
    }

    public final boolean isFinal() {
        return (this.modifiers & 16) != 0;
    }

    public final boolean isEffectivelyFinal() {
        return (this.tagBits & 2048) != 0;
    }

    public boolean isNonNull() {
        if ((this.tagBits & 72057594037927936L) != 0) {
            return true;
        }
        TypeBinding typeBinding = this.type;
        return (typeBinding == null || (typeBinding.tagBits & 72057594037927936L) == 0) ? false : true;
    }

    public boolean isNullable() {
        if ((this.tagBits & 36028797018963968L) != 0) {
            return true;
        }
        TypeBinding typeBinding = this.type;
        return (typeBinding == null || (typeBinding.tagBits & 36028797018963968L) == 0) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        return this.f628name;
    }

    public void setConstant(Constant constant) {
        this.constant = constant;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(10);
        ASTNode.printModifiers(this.modifiers, stringBuffer);
        if ((this.modifiers & 33554432) != 0) {
            stringBuffer.append("[unresolved] ");
        }
        TypeBinding typeBinding = this.type;
        stringBuffer.append(typeBinding != null ? typeBinding.debugName() : "<no type>");
        stringBuffer.append(" ");
        stringBuffer.append(this.f628name != null ? new String(this.f628name) : "<no name>");
        return stringBuffer.toString();
    }
}
