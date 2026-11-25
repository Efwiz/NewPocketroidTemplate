package org.jetbrains.org.objectweb.asm.util;

import java.util.EnumSet;
import org.jetbrains.org.objectweb.asm.signature.SignatureVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class CheckSignatureAdapter extends SignatureVisitor {
    private boolean canBeVoid;
    private final SignatureVisitor signatureVisitor;
    private State state;
    private final int type;
    private static final EnumSet<State> VISIT_FORMAL_TYPE_PARAMETER_STATES = EnumSet.of(State.EMPTY, State.FORMAL, State.BOUND);
    private static final EnumSet<State> VISIT_CLASS_BOUND_STATES = EnumSet.of(State.FORMAL);
    private static final EnumSet<State> VISIT_INTERFACE_BOUND_STATES = EnumSet.of(State.FORMAL, State.BOUND);
    private static final EnumSet<State> VISIT_SUPER_CLASS_STATES = EnumSet.of(State.EMPTY, State.FORMAL, State.BOUND);
    private static final EnumSet<State> VISIT_INTERFACE_STATES = EnumSet.of(State.SUPER);
    private static final EnumSet<State> VISIT_PARAMETER_TYPE_STATES = EnumSet.of(State.EMPTY, State.FORMAL, State.BOUND, State.PARAM);
    private static final EnumSet<State> VISIT_RETURN_TYPE_STATES = EnumSet.of(State.EMPTY, State.FORMAL, State.BOUND, State.PARAM);
    private static final EnumSet<State> VISIT_EXCEPTION_TYPE_STATES = EnumSet.of(State.RETURN);

    private enum State {
        EMPTY,
        FORMAL,
        BOUND,
        SUPER,
        PARAM,
        RETURN,
        SIMPLE_TYPE,
        CLASS_TYPE,
        END
    }

    public CheckSignatureAdapter(int i, SignatureVisitor signatureVisitor) {
        this(589824, i, signatureVisitor);
    }

    protected CheckSignatureAdapter(int i, int i2, SignatureVisitor signatureVisitor) {
        super(i);
        this.type = i2;
        this.state = State.EMPTY;
        this.signatureVisitor = signatureVisitor;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitFormalTypeParameter(String str) {
        if (this.type == 2 || !VISIT_FORMAL_TYPE_PARAMETER_STATES.contains(this.state)) {
            throw new IllegalStateException();
        }
        checkIdentifier(str, "formal type parameter");
        this.state = State.FORMAL;
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        if (signatureVisitor != null) {
            signatureVisitor.visitFormalTypeParameter(str);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitClassBound() {
        if (this.type == 2 || !VISIT_CLASS_BOUND_STATES.contains(this.state)) {
            throw new IllegalStateException();
        }
        this.state = State.BOUND;
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        return new CheckSignatureAdapter(2, signatureVisitor == null ? null : signatureVisitor.visitClassBound());
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitInterfaceBound() {
        if (this.type == 2 || !VISIT_INTERFACE_BOUND_STATES.contains(this.state)) {
            throw new IllegalStateException();
        }
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        return new CheckSignatureAdapter(2, signatureVisitor == null ? null : signatureVisitor.visitInterfaceBound());
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitSuperclass() {
        if (this.type != 0 || !VISIT_SUPER_CLASS_STATES.contains(this.state)) {
            throw new IllegalStateException();
        }
        this.state = State.SUPER;
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        return new CheckSignatureAdapter(2, signatureVisitor == null ? null : signatureVisitor.visitSuperclass());
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitInterface() {
        if (this.type != 0 || !VISIT_INTERFACE_STATES.contains(this.state)) {
            throw new IllegalStateException();
        }
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        return new CheckSignatureAdapter(2, signatureVisitor == null ? null : signatureVisitor.visitInterface());
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitParameterType() {
        if (this.type != 1 || !VISIT_PARAMETER_TYPE_STATES.contains(this.state)) {
            throw new IllegalStateException();
        }
        this.state = State.PARAM;
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        return new CheckSignatureAdapter(2, signatureVisitor == null ? null : signatureVisitor.visitParameterType());
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitReturnType() {
        if (this.type != 1 || !VISIT_RETURN_TYPE_STATES.contains(this.state)) {
            throw new IllegalStateException();
        }
        this.state = State.RETURN;
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        CheckSignatureAdapter checkSignatureAdapter = new CheckSignatureAdapter(2, signatureVisitor == null ? null : signatureVisitor.visitReturnType());
        checkSignatureAdapter.canBeVoid = true;
        return checkSignatureAdapter;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitExceptionType() {
        if (this.type != 1 || !VISIT_EXCEPTION_TYPE_STATES.contains(this.state)) {
            throw new IllegalStateException();
        }
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        return new CheckSignatureAdapter(2, signatureVisitor == null ? null : signatureVisitor.visitExceptionType());
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitBaseType(char c) {
        if (this.type != 2 || this.state != State.EMPTY) {
            throw new IllegalStateException();
        }
        if (c == 'V') {
            if (!this.canBeVoid) {
                throw new IllegalArgumentException("Base type descriptor can't be V");
            }
        } else if ("ZCBSIFJD".indexOf(c) == -1) {
            throw new IllegalArgumentException("Base type descriptor must be one of ZCBSIFJD");
        }
        this.state = State.SIMPLE_TYPE;
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        if (signatureVisitor != null) {
            signatureVisitor.visitBaseType(c);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitTypeVariable(String str) {
        if (this.type != 2 || this.state != State.EMPTY) {
            throw new IllegalStateException();
        }
        checkIdentifier(str, "type variable");
        this.state = State.SIMPLE_TYPE;
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        if (signatureVisitor != null) {
            signatureVisitor.visitTypeVariable(str);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitArrayType() {
        if (this.type != 2 || this.state != State.EMPTY) {
            throw new IllegalStateException();
        }
        this.state = State.SIMPLE_TYPE;
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        return new CheckSignatureAdapter(2, signatureVisitor == null ? null : signatureVisitor.visitArrayType());
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitClassType(String str) {
        if (this.type != 2 || this.state != State.EMPTY) {
            throw new IllegalStateException();
        }
        checkClassName(str, "class name");
        this.state = State.CLASS_TYPE;
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        if (signatureVisitor != null) {
            signatureVisitor.visitClassType(str);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitInnerClassType(String str) {
        if (this.state != State.CLASS_TYPE) {
            throw new IllegalStateException();
        }
        checkIdentifier(str, "inner class name");
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        if (signatureVisitor != null) {
            signatureVisitor.visitInnerClassType(str);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitTypeArgument() {
        if (this.state != State.CLASS_TYPE) {
            throw new IllegalStateException();
        }
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        if (signatureVisitor != null) {
            signatureVisitor.visitTypeArgument();
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitTypeArgument(char c) {
        if (this.state != State.CLASS_TYPE) {
            throw new IllegalStateException();
        }
        if ("+-=".indexOf(c) == -1) {
            throw new IllegalArgumentException("Wildcard must be one of +-=");
        }
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        return new CheckSignatureAdapter(2, signatureVisitor == null ? null : signatureVisitor.visitTypeArgument(c));
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitEnd() {
        if (this.state != State.CLASS_TYPE) {
            throw new IllegalStateException();
        }
        this.state = State.END;
        SignatureVisitor signatureVisitor = this.signatureVisitor;
        if (signatureVisitor != null) {
            signatureVisitor.visitEnd();
        }
    }

    private void checkClassName(String str, String str2) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Invalid " + str2 + " (must not be null or empty)");
        }
        for (int i = 0; i < str.length(); i++) {
            if (".;[<>:".indexOf(str.charAt(i)) != -1) {
                throw new IllegalArgumentException("Invalid " + str2 + " (must not contain . ; [ < > or :): " + str);
            }
        }
    }

    private void checkIdentifier(String str, String str2) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Invalid " + str2 + " (must not be null or empty)");
        }
        for (int i = 0; i < str.length(); i++) {
            if (".;[/<>:".indexOf(str.charAt(i)) != -1) {
                throw new IllegalArgumentException("Invalid " + str2 + " (must not contain . ; [ / < > or :): " + str);
            }
        }
    }
}
