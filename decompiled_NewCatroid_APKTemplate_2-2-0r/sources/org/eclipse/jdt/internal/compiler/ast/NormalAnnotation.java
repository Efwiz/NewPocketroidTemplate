package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.ElementValuePair;

/* JADX INFO: loaded from: classes14.dex */
public class NormalAnnotation extends Annotation {
    public MemberValuePair[] memberValuePairs;

    public NormalAnnotation(TypeReference typeReference, int i) {
        this.type = typeReference;
        this.sourceStart = i;
        this.sourceEnd = typeReference.sourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation
    public ElementValuePair[] computeElementValuePairs() {
        MemberValuePair[] memberValuePairArr = this.memberValuePairs;
        int length = memberValuePairArr == null ? 0 : memberValuePairArr.length;
        if (length == 0) {
            return Binding.NO_ELEMENT_VALUE_PAIRS;
        }
        ElementValuePair[] elementValuePairArr = new ElementValuePair[length];
        for (int i = 0; i < length; i++) {
            elementValuePairArr[i] = this.memberValuePairs[i].compilerElementPair;
        }
        return elementValuePairArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation
    public MemberValuePair[] memberValuePairs() {
        MemberValuePair[] memberValuePairArr = this.memberValuePairs;
        return memberValuePairArr == null ? NoValuePairs : memberValuePairArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        super.printExpression(i, stringBuffer);
        stringBuffer.append('(');
        MemberValuePair[] memberValuePairArr = this.memberValuePairs;
        if (memberValuePairArr != null) {
            int length = memberValuePairArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (i2 > 0) {
                    stringBuffer.append(',');
                }
                this.memberValuePairs[i2].print(i, stringBuffer);
            }
        }
        stringBuffer.append(')');
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.type != null) {
                this.type.traverse(aSTVisitor, blockScope);
            }
            MemberValuePair[] memberValuePairArr = this.memberValuePairs;
            if (memberValuePairArr != null) {
                int length = memberValuePairArr.length;
                for (int i = 0; i < length; i++) {
                    this.memberValuePairs[i].traverse(aSTVisitor, blockScope);
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.type != null) {
                this.type.traverse(aSTVisitor, classScope);
            }
            MemberValuePair[] memberValuePairArr = this.memberValuePairs;
            if (memberValuePairArr != null) {
                int length = memberValuePairArr.length;
                for (int i = 0; i < length; i++) {
                    this.memberValuePairs[i].traverse(aSTVisitor, classScope);
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
