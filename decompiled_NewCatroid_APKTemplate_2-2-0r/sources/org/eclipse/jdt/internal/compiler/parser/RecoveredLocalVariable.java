package org.eclipse.jdt.internal.compiler.parser;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Statement;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredLocalVariable extends RecoveredStatement {
    public boolean alreadyCompletedLocalInitialization;
    public int annotationCount;
    public RecoveredAnnotation[] annotations;
    public LocalDeclaration localDeclaration;
    public int modifiers;
    public int modifiersStart;

    public RecoveredLocalVariable(LocalDeclaration localDeclaration, RecoveredElement recoveredElement, int i) {
        super(localDeclaration, recoveredElement, i);
        this.localDeclaration = localDeclaration;
        this.alreadyCompletedLocalInitialization = localDeclaration.initialization != null;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(Statement statement, int i) {
        if (this.alreadyCompletedLocalInitialization || !(statement instanceof Expression)) {
            return super.add(statement, i);
        }
        this.alreadyCompletedLocalInitialization = true;
        this.localDeclaration.initialization = (Expression) statement;
        this.localDeclaration.declarationSourceEnd = statement.sourceEnd;
        this.localDeclaration.declarationEnd = statement.sourceEnd;
        return this;
    }

    public void attach(RecoveredAnnotation[] recoveredAnnotationArr, int i, int i2, int i3) {
        if (i > 0) {
            Annotation[] annotationArr = this.localDeclaration.annotations;
            if (annotationArr != null) {
                this.annotations = new RecoveredAnnotation[i];
                this.annotationCount = 0;
                for (int i4 = 0; i4 < i; i4++) {
                    int i5 = 0;
                    while (true) {
                        if (i5 < annotationArr.length) {
                            if (recoveredAnnotationArr[i4].annotation == annotationArr[i5]) {
                                break;
                            } else {
                                i5++;
                            }
                        } else {
                            RecoveredAnnotation[] recoveredAnnotationArr2 = this.annotations;
                            int i6 = this.annotationCount;
                            this.annotationCount = i6 + 1;
                            recoveredAnnotationArr2[i6] = recoveredAnnotationArr[i4];
                            break;
                        }
                    }
                }
            } else {
                this.annotations = recoveredAnnotationArr;
                this.annotationCount = i;
            }
        }
        if (i2 != 0) {
            this.modifiers = i2;
            this.modifiersStart = i3;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public ASTNode parseTree() {
        return this.localDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public int sourceEnd() {
        return this.localDeclaration.declarationSourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public String toString(int i) {
        return String.valueOf(tabString(i)) + "Recovered local variable:\n" + ((Object) this.localDeclaration.print(i + 1, new StringBuffer(10)));
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement
    public Statement updatedStatement(int i, Set set) {
        if (this.modifiers != 0) {
            this.localDeclaration.modifiers |= this.modifiers;
            if (this.modifiersStart < this.localDeclaration.declarationSourceStart) {
                this.localDeclaration.declarationSourceStart = this.modifiersStart;
            }
        }
        if (this.annotationCount > 0) {
            int length = this.localDeclaration.annotations == null ? 0 : this.localDeclaration.annotations.length;
            Annotation[] annotationArr = new Annotation[this.annotationCount + length];
            if (length > 0) {
                System.arraycopy(this.localDeclaration.annotations, 0, annotationArr, this.annotationCount, length);
            }
            for (int i2 = 0; i2 < this.annotationCount; i2++) {
                annotationArr[i2] = this.annotations[i2].updatedAnnotationReference();
            }
            this.localDeclaration.annotations = annotationArr;
            int i3 = this.annotations[0].annotation.sourceStart;
            if (i3 < this.localDeclaration.declarationSourceStart) {
                this.localDeclaration.declarationSourceStart = i3;
            }
        }
        return this.localDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnClosingBrace(int i, int i2) {
        if (this.bracketBalance <= 0) {
            return this.parent != null ? this.parent.updateOnClosingBrace(i, i2) : this;
        }
        this.bracketBalance--;
        if (this.bracketBalance == 0) {
            this.alreadyCompletedLocalInitialization = true;
        }
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnOpeningBrace(int i, int i2) {
        if (this.localDeclaration.declarationSourceEnd == 0 && (((this.localDeclaration.type instanceof ArrayTypeReference) || (this.localDeclaration.type instanceof ArrayQualifiedTypeReference)) && !this.alreadyCompletedLocalInitialization)) {
            this.bracketBalance++;
            return null;
        }
        updateSourceEndIfNecessary(i - 1, i2 - 1);
        return this.parent.updateOnOpeningBrace(i, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateParseTree() {
        updatedStatement(0, new HashSet());
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateSourceEndIfNecessary(int i, int i2) {
        if (this.localDeclaration.declarationSourceEnd == 0) {
            this.localDeclaration.declarationSourceEnd = i2;
            this.localDeclaration.declarationEnd = i2;
        }
    }
}
