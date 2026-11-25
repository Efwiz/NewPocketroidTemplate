package org.eclipse.jdt.internal.compiler.parser;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.MarkerAnnotation;
import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;
import org.eclipse.jdt.internal.compiler.ast.NormalAnnotation;
import org.eclipse.jdt.internal.compiler.ast.SingleMemberAnnotation;
import org.eclipse.jdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredAnnotation extends RecoveredElement {
    public static final int MARKER = 0;
    public static final int NORMAL = 1;
    public static final int SINGLE_MEMBER = 2;
    public Annotation annotation;
    public boolean hasPendingMemberValueName;
    private int identifierLengthPtr;
    private int identifierPtr;
    private int kind;
    public int memberValuPairEqualEnd;
    private int sourceStart;

    public RecoveredAnnotation(int i, int i2, int i3, RecoveredElement recoveredElement, int i4) {
        super(recoveredElement, i4);
        this.memberValuPairEqualEnd = -1;
        this.kind = 0;
        this.identifierPtr = i;
        this.identifierLengthPtr = i2;
        this.sourceStart = i3;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(TypeDeclaration typeDeclaration, int i) {
        return (this.annotation != null || (typeDeclaration.bits & 512) == 0) ? super.add(typeDeclaration, i) : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement addAnnotationName(int i, int i2, int i3, int i4) {
        return new RecoveredAnnotation(i, i2, i3, this, i4);
    }

    public RecoveredElement addAnnotation(Annotation annotation, int i) {
        this.annotation = annotation;
        return this.parent != null ? this.parent : this;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x00f6  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0, types: [org.eclipse.jdt.internal.compiler.parser.RecoveredAnnotation] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10, types: [org.eclipse.jdt.internal.compiler.ast.Annotation] */
    /* JADX WARN: Type inference failed for: r2v12, types: [org.eclipse.jdt.internal.compiler.ast.MarkerAnnotation] */
    /* JADX WARN: Type inference failed for: r2v13, types: [org.eclipse.jdt.internal.compiler.ast.NormalAnnotation] */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v9 */
    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateFromParserState() {
        MemberValuePair memberValuePair;
        MemberValuePair[] memberValuePairArr;
        int i;
        boolean z;
        Parser rVar = parser();
        if (this.annotation != null || this.identifierPtr > rVar.identifierPtr) {
            return;
        }
        ?? markerAnnotation = 0;
        if (!this.hasPendingMemberValueName || this.identifierPtr >= rVar.identifierPtr) {
            memberValuePair = null;
        } else {
            char[] cArr = rVar.identifierStack[this.identifierPtr + 1];
            long j = rVar.identifierPositionStack[this.identifierPtr + 1];
            int i2 = (int) (j >>> 32);
            int i3 = (int) j;
            int i4 = this.memberValuPairEqualEnd;
            if (i4 <= -1) {
                i4 = i3;
            }
            long j2 = i4;
            memberValuePair = new MemberValuePair(cArr, i2, i3, new SingleNameReference(RecoveryScanner.FAKE_IDENTIFIER, ((1 + j2) << 32) + j2));
        }
        rVar.identifierPtr = this.identifierPtr;
        rVar.identifierLengthPtr = this.identifierLengthPtr;
        TypeReference annotationType = rVar.getAnnotationType();
        int i5 = this.kind;
        if (i5 == 1) {
            if (rVar.astPtr <= -1 || !(rVar.astStack[rVar.astPtr] instanceof MemberValuePair)) {
                z = false;
            } else {
                int i6 = rVar.astLengthStack[rVar.astLengthPtr];
                int i7 = (rVar.astPtr - i6) + 1;
                if (i6 > 0) {
                    if (memberValuePair != null) {
                        memberValuePairArr = new MemberValuePair[i6 + 1];
                        System.arraycopy(rVar.astStack, i7, memberValuePairArr, 0, i6);
                        rVar.astLengthPtr--;
                        rVar.astPtr -= i6;
                        memberValuePairArr[i6] = memberValuePair;
                        i = memberValuePair.sourceEnd;
                    } else {
                        memberValuePairArr = new MemberValuePair[i6];
                        System.arraycopy(rVar.astStack, i7, memberValuePairArr, 0, i6);
                        rVar.astLengthPtr--;
                        rVar.astPtr -= i6;
                        MemberValuePair memberValuePair2 = memberValuePairArr[i6 - 1];
                        if (memberValuePair2.value != null) {
                            if (memberValuePair2.value instanceof Annotation) {
                                i = ((Annotation) memberValuePair2.value).declarationSourceEnd;
                            } else {
                                i = memberValuePair2.value.sourceEnd;
                            }
                        } else {
                            i = memberValuePair2.sourceEnd;
                        }
                    }
                    NormalAnnotation normalAnnotation = new NormalAnnotation(annotationType, this.sourceStart);
                    normalAnnotation.memberValuePairs = memberValuePairArr;
                    normalAnnotation.declarationSourceEnd = i;
                    normalAnnotation.bits |= 32;
                    z = true;
                    markerAnnotation = normalAnnotation;
                } else {
                    z = false;
                }
            }
        } else if (i5 == 2 && rVar.expressionPtr > -1) {
            Expression[] expressionArr = rVar.expressionStack;
            int i8 = rVar.expressionPtr;
            rVar.expressionPtr = i8 - 1;
            Expression expression = expressionArr[i8];
            SingleMemberAnnotation singleMemberAnnotation = new SingleMemberAnnotation(annotationType, this.sourceStart);
            singleMemberAnnotation.memberValue = expression;
            singleMemberAnnotation.declarationSourceEnd = expression.sourceEnd;
            singleMemberAnnotation.bits |= 32;
            markerAnnotation = singleMemberAnnotation;
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            if (memberValuePair != null) {
                markerAnnotation = new NormalAnnotation(annotationType, this.sourceStart);
                markerAnnotation.memberValuePairs = new MemberValuePair[]{memberValuePair};
                markerAnnotation.declarationSourceEnd = memberValuePair.value.sourceEnd;
                markerAnnotation.bits |= 32;
            } else {
                markerAnnotation = new MarkerAnnotation(annotationType, this.sourceStart);
                markerAnnotation.declarationSourceEnd = markerAnnotation.sourceEnd;
                markerAnnotation.bits |= 32;
            }
        }
        rVar.currentElement = addAnnotation(markerAnnotation, this.identifierPtr);
        rVar.annotationRecoveryCheckPoint(markerAnnotation.sourceStart, markerAnnotation.declarationSourceEnd);
        if (this.parent != null) {
            this.parent.updateFromParserState();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public ASTNode parseTree() {
        return this.annotation;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void resetPendingModifiers() {
        if (this.parent != null) {
            this.parent.resetPendingModifiers();
        }
    }

    public void setKind(int i) {
        this.kind = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public int sourceEnd() {
        Annotation annotation = this.annotation;
        if (annotation == null) {
            Parser rVar = parser();
            if (this.identifierPtr < rVar.identifierPositionStack.length) {
                return (int) rVar.identifierPositionStack[this.identifierPtr];
            }
            return this.sourceStart;
        }
        return annotation.declarationSourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public String toString(int i) {
        if (this.annotation != null) {
            return String.valueOf(tabString(i)) + "Recovered annotation:\n" + ((Object) this.annotation.print(i + 1, new StringBuffer(10)));
        }
        return String.valueOf(tabString(i)) + "Recovered annotation: identiferPtr=" + this.identifierPtr + " identiferlengthPtr=" + this.identifierLengthPtr + "\n";
    }

    public Annotation updatedAnnotationReference() {
        return this.annotation;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnClosingBrace(int i, int i2) {
        if (this.bracketBalance <= 0) {
            return this.parent != null ? this.parent.updateOnClosingBrace(i, i2) : this;
        }
        this.bracketBalance--;
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateParseTree() {
        updatedAnnotationReference();
    }
}
