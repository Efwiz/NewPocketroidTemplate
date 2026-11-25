package org.jetbrains.org.objectweb.asm.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import okhttp3.HttpUrl;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.org.objectweb.asm.signature.SignatureVisitor;

/* JADX INFO: loaded from: classes6.dex */
public final class TraceSignatureVisitor extends SignatureVisitor {
    private static final Map<Character, String> BASE_TYPES;
    private int argumentStack;
    private int arrayStack;
    private final StringBuilder declaration;
    private StringBuilder exceptions;
    private boolean formalTypeParameterVisited;
    private boolean interfaceBoundVisited;
    private boolean interfaceVisited;
    private final boolean isInterface;
    private boolean parameterTypeVisited;
    private StringBuilder returnType;
    private String separator;

    static {
        HashMap map = new HashMap();
        map.put('Z', "boolean");
        map.put(Character.valueOf(Util.C_BYTE), "byte");
        map.put(Character.valueOf(Util.C_CHAR), PsiKeyword.CHAR);
        map.put(Character.valueOf(Util.C_SHORT), "short");
        map.put('I', "int");
        map.put(Character.valueOf(Util.C_LONG), "long");
        map.put(Character.valueOf(Util.C_FLOAT), "float");
        map.put(Character.valueOf(Util.C_DOUBLE), "double");
        map.put(Character.valueOf(Util.C_VOID), PsiKeyword.VOID);
        BASE_TYPES = Collections.unmodifiableMap(map);
    }

    public TraceSignatureVisitor(int i) {
        super(589824);
        this.separator = "";
        this.isInterface = (i & 512) != 0;
        this.declaration = new StringBuilder();
    }

    private TraceSignatureVisitor(StringBuilder sb) {
        super(589824);
        this.separator = "";
        this.isInterface = false;
        this.declaration = sb;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitFormalTypeParameter(String str) {
        this.declaration.append(this.formalTypeParameterVisited ? ", " : "<").append(str);
        this.formalTypeParameterVisited = true;
        this.interfaceBoundVisited = false;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitClassBound() {
        this.separator = " extends ";
        startType();
        return this;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitInterfaceBound() {
        this.separator = this.interfaceBoundVisited ? ", " : " extends ";
        this.interfaceBoundVisited = true;
        startType();
        return this;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitSuperclass() {
        endFormals();
        this.separator = " extends ";
        startType();
        return this;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitInterface() {
        if (this.interfaceVisited) {
            this.separator = ", ";
        } else {
            this.separator = this.isInterface ? " extends " : " implements ";
            this.interfaceVisited = true;
        }
        startType();
        return this;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitParameterType() {
        endFormals();
        if (this.parameterTypeVisited) {
            this.declaration.append(", ");
        } else {
            this.declaration.append('(');
            this.parameterTypeVisited = true;
        }
        startType();
        return this;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitReturnType() {
        endFormals();
        if (this.parameterTypeVisited) {
            this.parameterTypeVisited = false;
        } else {
            this.declaration.append('(');
        }
        this.declaration.append(')');
        StringBuilder sb = new StringBuilder();
        this.returnType = sb;
        return new TraceSignatureVisitor(sb);
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitExceptionType() {
        StringBuilder sb = this.exceptions;
        if (sb == null) {
            this.exceptions = new StringBuilder();
        } else {
            sb.append(", ");
        }
        return new TraceSignatureVisitor(this.exceptions);
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitBaseType(char c) {
        String str = BASE_TYPES.get(Character.valueOf(c));
        if (str == null) {
            throw new IllegalArgumentException();
        }
        this.declaration.append(str);
        endType();
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitTypeVariable(String str) {
        this.declaration.append(this.separator).append(str);
        this.separator = "";
        endType();
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitArrayType() {
        startType();
        this.arrayStack |= 1;
        return this;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitClassType(String str) {
        if (!TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME.equals(str) || this.argumentStack % 2 != 0 || this.parameterTypeVisited) {
            this.declaration.append(this.separator).append(str.replace('/', '.'));
        }
        this.separator = "";
        this.argumentStack *= 2;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitInnerClassType(String str) {
        if (this.argumentStack % 2 != 0) {
            this.declaration.append('>');
        }
        this.argumentStack /= 2;
        this.declaration.append('.');
        this.declaration.append(this.separator).append(str.replace('/', '.'));
        this.separator = "";
        this.argumentStack *= 2;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitTypeArgument() {
        int i = this.argumentStack;
        if (i % 2 == 0) {
            this.argumentStack = i + 1;
            this.declaration.append('<');
        } else {
            this.declaration.append(", ");
        }
        this.declaration.append(MangleConstant.Q_MARK);
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public SignatureVisitor visitTypeArgument(char c) {
        int i = this.argumentStack;
        if (i % 2 == 0) {
            this.argumentStack = i + 1;
            this.declaration.append('<');
        } else {
            this.declaration.append(", ");
        }
        if (c == '+') {
            this.declaration.append(PsiWildcardType.EXTENDS_PREFIX);
        } else if (c == '-') {
            this.declaration.append(PsiWildcardType.SUPER_PREFIX);
        }
        startType();
        return this;
    }

    @Override // org.jetbrains.org.objectweb.asm.signature.SignatureVisitor
    public void visitEnd() {
        if (this.argumentStack % 2 != 0) {
            this.declaration.append('>');
        }
        this.argumentStack /= 2;
        endType();
    }

    public String getDeclaration() {
        return this.declaration.toString();
    }

    public String getReturnType() {
        StringBuilder sb = this.returnType;
        if (sb == null) {
            return null;
        }
        return sb.toString();
    }

    public String getExceptions() {
        StringBuilder sb = this.exceptions;
        if (sb == null) {
            return null;
        }
        return sb.toString();
    }

    private void endFormals() {
        if (this.formalTypeParameterVisited) {
            this.declaration.append('>');
            this.formalTypeParameterVisited = false;
        }
    }

    private void startType() {
        this.arrayStack *= 2;
    }

    private void endType() {
        int i = this.arrayStack;
        if (i % 2 == 0) {
            this.arrayStack = i / 2;
            return;
        }
        while (true) {
            int i2 = this.arrayStack;
            if (i2 % 2 == 0) {
                return;
            }
            this.arrayStack = i2 / 2;
            this.declaration.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        }
    }
}
