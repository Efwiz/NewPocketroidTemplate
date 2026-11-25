package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class AbstractVariableDeclaration extends Statement implements InvocationSite {
    public static final int ENUM_CONSTANT = 3;
    public static final int FIELD = 1;
    public static final int INITIALIZER = 2;
    public static final int LOCAL_VARIABLE = 4;
    public static final int PARAMETER = 5;
    public static final int TYPE_PARAMETER = 6;
    public Annotation[] annotations;
    public int declarationEnd;
    public int declarationSourceEnd;
    public int declarationSourceStart;
    public int hiddenVariableDepth;
    public Expression initialization;
    public int modifiers;
    public int modifiersSourceStart;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public char[] f617name;
    public TypeReference type;

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public InferenceContext18 freshInferenceContext(Scope scope) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding[] genericTypeArguments() {
        return null;
    }

    public abstract int getKind();

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isSuperAccess() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isTypeAccess() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setActualReceiverType(ReferenceBinding referenceBinding) {
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setFieldIndex(int i) {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printAsExpression(i, stringBuffer);
        if (getKind() == 3) {
            return stringBuffer.append(',');
        }
        return stringBuffer.append(';');
    }

    public StringBuffer printAsExpression(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer);
        printModifiers(this.modifiers, stringBuffer);
        Annotation[] annotationArr = this.annotations;
        if (annotationArr != null) {
            printAnnotations(annotationArr, stringBuffer);
            stringBuffer.append(StringUtil.SPACE);
        }
        TypeReference typeReference = this.type;
        if (typeReference != null) {
            typeReference.print(0, stringBuffer).append(StringUtil.SPACE);
        }
        stringBuffer.append(this.f617name);
        if (getKind() == 3) {
            Expression expression = this.initialization;
            if (expression != null) {
                expression.printExpression(i, stringBuffer);
            }
        } else if (this.initialization != null) {
            stringBuffer.append(" = ");
            this.initialization.printExpression(i, stringBuffer);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setDepth(int i) {
        this.hiddenVariableDepth = i;
    }
}
