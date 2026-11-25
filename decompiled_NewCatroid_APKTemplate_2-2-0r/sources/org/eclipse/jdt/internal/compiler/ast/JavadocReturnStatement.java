package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class JavadocReturnStatement extends ReturnStatement {
    public JavadocReturnStatement(int i, int i2) {
        super(null, i, i2);
        this.bits |= 294912;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ReturnStatement, org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        Binding binding;
        MethodScope methodScope = blockScope.methodScope();
        if (methodScope.referenceContext instanceof AbstractMethodDeclaration) {
            MethodBinding methodBinding = ((AbstractMethodDeclaration) methodScope.referenceContext).binding;
            binding = methodBinding == null ? null : methodBinding.returnType;
        } else {
            binding = TypeBinding.VOID;
        }
        if (binding == null || binding == TypeBinding.VOID) {
            blockScope.problemReporter().javadocUnexpectedTag(this.sourceStart, this.sourceEnd);
        } else if ((this.bits & 262144) != 0) {
            blockScope.problemReporter().javadocEmptyReturnTag(this.sourceStart, this.sourceEnd, blockScope.getDeclarationModifiers());
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ReturnStatement, org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("return");
        if ((this.bits & 262144) == 0) {
            stringBuffer.append(StringUtil.SPACE).append(" <not empty>");
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ReturnStatement, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }

    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        aSTVisitor.visit(this, classScope);
        aSTVisitor.endVisit(this, classScope);
    }
}
