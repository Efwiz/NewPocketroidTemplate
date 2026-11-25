package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;

/* JADX INFO: loaded from: classes14.dex */
public class Receiver extends Argument {
    public NameReference qualifyingName;

    @Override // org.eclipse.jdt.internal.compiler.ast.LocalDeclaration
    public boolean isReceiver() {
        return true;
    }

    public Receiver(char[] cArr, long j, TypeReference typeReference, NameReference nameReference, int i) {
        super(cArr, j, typeReference, i);
        this.qualifyingName = nameReference;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Argument, org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer);
        printModifiers(this.modifiers, stringBuffer);
        if (this.type == null) {
            stringBuffer.append("<no type> ");
        } else {
            this.type.print(0, stringBuffer).append(StringUtil.SPACE);
        }
        NameReference nameReference = this.qualifyingName;
        if (nameReference != null) {
            nameReference.print(i, stringBuffer);
            stringBuffer.append('.');
        }
        return stringBuffer.append(this.f617name);
    }
}
