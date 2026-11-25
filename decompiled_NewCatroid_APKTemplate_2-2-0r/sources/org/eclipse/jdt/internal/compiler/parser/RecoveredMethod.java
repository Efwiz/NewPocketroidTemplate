package org.eclipse.jdt.internal.compiler.parser;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.Block;
import org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.SuperReference;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredMethod extends RecoveredElement implements TerminalTokens {
    public int annotationCount;
    public RecoveredAnnotation[] annotations;
    public boolean discardBody;
    public int localTypeCount;
    public RecoveredType[] localTypes;
    public RecoveredBlock methodBody;
    public AbstractMethodDeclaration methodDeclaration;
    public int modifiers;
    public int modifiersStart;
    int pendingAnnotationCount;
    RecoveredAnnotation[] pendingAnnotations;
    int pendingModifersSourceStart;
    int pendingModifiers;

    public RecoveredMethod(AbstractMethodDeclaration abstractMethodDeclaration, RecoveredElement recoveredElement, int i, Parser parser) {
        super(recoveredElement, i, parser);
        this.discardBody = true;
        this.pendingModifersSourceStart = -1;
        this.methodDeclaration = abstractMethodDeclaration;
        this.foundOpeningBrace = !bodyStartsAtHeaderEnd();
        if (this.foundOpeningBrace) {
            this.bracketBalance++;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(Block block, int i) {
        return add(block, i, false);
    }

    public RecoveredElement add(Block block, int i, boolean z) {
        if (this.methodDeclaration.declarationSourceEnd > 0 && block.sourceStart > this.methodDeclaration.declarationSourceEnd) {
            resetPendingModifiers();
            return this.parent == null ? this : this.parent.add(block, i);
        }
        if (!this.foundOpeningBrace && !z) {
            this.foundOpeningBrace = true;
            this.bracketBalance++;
        }
        this.methodBody = new RecoveredBlock(block, this, i);
        return block.sourceEnd == 0 ? this.methodBody : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(FieldDeclaration fieldDeclaration, int i) {
        resetPendingModifiers();
        if ((fieldDeclaration.modifiers & (-17)) == 0 && fieldDeclaration.type != null) {
            char[][] typeName = fieldDeclaration.type.getTypeName();
            if (typeName.length != 1 || !CharOperation.equals(typeName[0], TypeBinding.VOID.sourceName())) {
                if (this.methodDeclaration.declarationSourceEnd > 0 && fieldDeclaration.declarationSourceStart > this.methodDeclaration.declarationSourceEnd) {
                    return this.parent == null ? this : this.parent.add(fieldDeclaration, i);
                }
                if (!this.foundOpeningBrace) {
                    this.foundOpeningBrace = true;
                    this.bracketBalance++;
                }
                return this;
            }
        }
        if (this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(fieldDeclaration.declarationSourceStart - 1));
        return this.parent.add(fieldDeclaration, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(LocalDeclaration localDeclaration, int i) {
        resetPendingModifiers();
        if (this.methodDeclaration.declarationSourceEnd != 0 && localDeclaration.declarationSourceStart > this.methodDeclaration.declarationSourceEnd) {
            return this.parent == null ? this : this.parent.add(localDeclaration, i);
        }
        RecoveredBlock recoveredBlock = this.methodBody;
        if (recoveredBlock == null) {
            Block block = new Block(0);
            block.sourceStart = this.methodDeclaration.bodyStart;
            RecoveredElement recoveredElementAdd = add(block, 1, localDeclaration.isArgument());
            if (this.bracketBalance > 0) {
                for (int i2 = 0; i2 < this.bracketBalance - 1; i2++) {
                    recoveredElementAdd = recoveredElementAdd.add(new Block(0), 1);
                }
                this.bracketBalance = 1;
            }
            return recoveredElementAdd.add(localDeclaration, i);
        }
        return recoveredBlock.add(localDeclaration, i, true);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(Statement statement, int i) {
        resetPendingModifiers();
        if (this.methodDeclaration.declarationSourceEnd != 0 && statement.sourceStart > this.methodDeclaration.declarationSourceEnd) {
            return this.parent == null ? this : this.parent.add(statement, i);
        }
        RecoveredBlock recoveredBlock = this.methodBody;
        if (recoveredBlock == null) {
            Block block = new Block(0);
            block.sourceStart = this.methodDeclaration.bodyStart;
            RecoveredElement recoveredElementAdd = add(block, 1);
            if (this.bracketBalance > 0) {
                for (int i2 = 0; i2 < this.bracketBalance - 1; i2++) {
                    recoveredElementAdd = recoveredElementAdd.add(new Block(0), 1);
                }
                this.bracketBalance = 1;
            }
            return recoveredElementAdd.add(statement, i);
        }
        return recoveredBlock.add(statement, i, true);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(TypeDeclaration typeDeclaration, int i) {
        if (this.methodDeclaration.declarationSourceEnd != 0 && typeDeclaration.declarationSourceStart > this.methodDeclaration.declarationSourceEnd) {
            return this.parent == null ? this : this.parent.add(typeDeclaration, i);
        }
        if ((typeDeclaration.bits & 256) != 0 || parser().methodRecoveryActivated || parser().statementRecoveryActivated) {
            if (this.methodBody == null) {
                Block block = new Block(0);
                block.sourceStart = this.methodDeclaration.bodyStart;
                add(block, 1);
            }
            this.methodBody.attachPendingModifiers(this.pendingAnnotations, this.pendingAnnotationCount, this.pendingModifiers, this.pendingModifersSourceStart);
            resetPendingModifiers();
            return this.methodBody.add(typeDeclaration, i, true);
        }
        int iKind = TypeDeclaration.kind(typeDeclaration.modifiers);
        if (iKind == 2 || iKind == 4) {
            resetPendingModifiers();
            updateSourceEndIfNecessary(previousAvailableLineEnd(typeDeclaration.declarationSourceStart - 1));
            return this.parent == null ? this : this.parent.add(typeDeclaration, i);
        }
        RecoveredType[] recoveredTypeArr = this.localTypes;
        if (recoveredTypeArr == null) {
            this.localTypes = new RecoveredType[5];
            this.localTypeCount = 0;
        } else {
            int i2 = this.localTypeCount;
            if (i2 == recoveredTypeArr.length) {
                RecoveredType[] recoveredTypeArr2 = new RecoveredType[i2 * 2];
                this.localTypes = recoveredTypeArr2;
                System.arraycopy(recoveredTypeArr, 0, recoveredTypeArr2, 0, i2);
            }
        }
        RecoveredType recoveredType = new RecoveredType(typeDeclaration, this, i);
        RecoveredType[] recoveredTypeArr3 = this.localTypes;
        int i3 = this.localTypeCount;
        this.localTypeCount = i3 + 1;
        recoveredTypeArr3[i3] = recoveredType;
        int i4 = this.pendingAnnotationCount;
        if (i4 > 0) {
            recoveredType.attach(this.pendingAnnotations, i4, this.pendingModifiers, this.pendingModifersSourceStart);
        }
        resetPendingModifiers();
        if (!this.foundOpeningBrace) {
            this.foundOpeningBrace = true;
            this.bracketBalance++;
        }
        return recoveredType;
    }

    public boolean bodyStartsAtHeaderEnd() {
        return this.methodDeclaration.bodyStart == this.methodDeclaration.sourceEnd + 1;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public ASTNode parseTree() {
        return this.methodDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void resetPendingModifiers() {
        this.pendingAnnotations = null;
        this.pendingAnnotationCount = 0;
        this.pendingModifiers = 0;
        this.pendingModifersSourceStart = -1;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public int sourceEnd() {
        return this.methodDeclaration.declarationSourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public String toString(int i) {
        StringBuffer stringBuffer = new StringBuffer(tabString(i));
        stringBuffer.append("Recovered method:\n");
        int i2 = i + 1;
        this.methodDeclaration.print(i2, stringBuffer);
        if (this.annotations != null) {
            for (int i3 = 0; i3 < this.annotationCount; i3++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.annotations[i3].toString(i2));
            }
        }
        if (this.localTypes != null) {
            for (int i4 = 0; i4 < this.localTypeCount; i4++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.localTypes[i4].toString(i2));
            }
        }
        if (this.methodBody != null) {
            stringBuffer.append("\n");
            stringBuffer.append(this.methodBody.toString(i2));
        }
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateBodyStart(int i) {
        this.foundOpeningBrace = true;
        this.methodDeclaration.bodyStart = i;
    }

    public AbstractMethodDeclaration updatedMethodDeclaration(int i, Set<TypeDeclaration> set) {
        if (this.modifiers != 0) {
            this.methodDeclaration.modifiers |= this.modifiers;
            if (this.modifiersStart < this.methodDeclaration.declarationSourceStart) {
                this.methodDeclaration.declarationSourceStart = this.modifiersStart;
            }
        }
        if (this.annotationCount > 0) {
            int length = this.methodDeclaration.annotations == null ? 0 : this.methodDeclaration.annotations.length;
            Annotation[] annotationArr = new Annotation[this.annotationCount + length];
            if (length > 0) {
                System.arraycopy(this.methodDeclaration.annotations, 0, annotationArr, this.annotationCount, length);
            }
            for (int i2 = 0; i2 < this.annotationCount; i2++) {
                annotationArr[i2] = this.annotations[i2].updatedAnnotationReference();
            }
            this.methodDeclaration.annotations = annotationArr;
            int i3 = this.annotations[0].annotation.sourceStart;
            if (i3 < this.methodDeclaration.declarationSourceStart) {
                this.methodDeclaration.declarationSourceStart = i3;
            }
        }
        RecoveredBlock recoveredBlock = this.methodBody;
        if (recoveredBlock != null) {
            Block blockUpdatedBlock = recoveredBlock.updatedBlock(i, set);
            if (blockUpdatedBlock != null) {
                this.methodDeclaration.statements = blockUpdatedBlock.statements;
                if (this.methodDeclaration.declarationSourceEnd == 0) {
                    this.methodDeclaration.declarationSourceEnd = blockUpdatedBlock.sourceEnd;
                    this.methodDeclaration.bodyEnd = blockUpdatedBlock.sourceEnd;
                }
                if (this.methodDeclaration.isConstructor()) {
                    AbstractMethodDeclaration abstractMethodDeclaration = this.methodDeclaration;
                    ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration) abstractMethodDeclaration;
                    if (abstractMethodDeclaration.statements != null && (this.methodDeclaration.statements[0] instanceof ExplicitConstructorCall)) {
                        constructorDeclaration.constructorCall = (ExplicitConstructorCall) this.methodDeclaration.statements[0];
                        int length2 = this.methodDeclaration.statements.length;
                        Statement[] statementArr = this.methodDeclaration.statements;
                        int i4 = length2 - 1;
                        Statement[] statementArr2 = new Statement[i4];
                        this.methodDeclaration.statements = statementArr2;
                        System.arraycopy(statementArr, 1, statementArr2, 0, i4);
                    }
                    if (constructorDeclaration.constructorCall == null) {
                        constructorDeclaration.constructorCall = SuperReference.implicitSuperConstructorCall();
                    }
                }
            }
        } else if (this.methodDeclaration.declarationSourceEnd == 0) {
            if (this.methodDeclaration.sourceEnd + 1 == this.methodDeclaration.bodyStart) {
                AbstractMethodDeclaration abstractMethodDeclaration2 = this.methodDeclaration;
                abstractMethodDeclaration2.declarationSourceEnd = abstractMethodDeclaration2.sourceEnd;
                AbstractMethodDeclaration abstractMethodDeclaration3 = this.methodDeclaration;
                abstractMethodDeclaration3.bodyStart = abstractMethodDeclaration3.sourceEnd;
                AbstractMethodDeclaration abstractMethodDeclaration4 = this.methodDeclaration;
                abstractMethodDeclaration4.bodyEnd = abstractMethodDeclaration4.sourceEnd;
            } else {
                AbstractMethodDeclaration abstractMethodDeclaration5 = this.methodDeclaration;
                abstractMethodDeclaration5.declarationSourceEnd = abstractMethodDeclaration5.bodyStart;
                AbstractMethodDeclaration abstractMethodDeclaration6 = this.methodDeclaration;
                abstractMethodDeclaration6.bodyEnd = abstractMethodDeclaration6.bodyStart;
            }
        }
        if (this.localTypeCount > 0) {
            this.methodDeclaration.bits |= 2;
        }
        return this.methodDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateFromParserState() {
        if (!bodyStartsAtHeaderEnd() || this.parent == null) {
            return;
        }
        Parser rVar = parser();
        if (rVar.listLength <= 0 || rVar.astLengthPtr <= 0) {
            return;
        }
        if (this.methodDeclaration.sourceEnd == rVar.rParenPos) {
            int i = rVar.astLengthStack[rVar.astLengthPtr];
            int i2 = rVar.astPtr - i;
            boolean z = i2 >= 0;
            if (z) {
                if (!(rVar.astStack[i2] instanceof AbstractMethodDeclaration)) {
                    z = false;
                }
                int i3 = i + 1;
                for (int i4 = 1; i4 < i3; i4++) {
                    if (!(rVar.astStack[i2 + i4] instanceof TypeReference)) {
                        z = false;
                    }
                }
            }
            if (z) {
                rVar.consumeMethodHeaderThrowsClause();
                return;
            } else {
                rVar.listLength = 0;
                return;
            }
        }
        if (rVar.currentToken == 24 || rVar.currentToken == 28) {
            int[] iArr = rVar.astLengthStack;
            int i5 = rVar.astLengthPtr;
            iArr[i5] = iArr[i5] - 1;
            rVar.astPtr--;
            rVar.listLength--;
            rVar.currentToken = 0;
        }
        int i6 = rVar.astLengthStack[rVar.astLengthPtr];
        int i7 = (rVar.astPtr - i6) + 1;
        boolean z2 = rVar.rParenPos < rVar.lParenPos;
        MemberValuePair[] memberValuePairArr = null;
        while (i6 > 0 && (rVar.astStack[rVar.astPtr] instanceof MemberValuePair)) {
            memberValuePairArr = new MemberValuePair[i6];
            System.arraycopy(rVar.astStack, i7, memberValuePairArr, 0, i6);
            rVar.astLengthPtr--;
            rVar.astPtr -= i6;
            i6 = rVar.astLengthStack[rVar.astLengthPtr];
            i7 = (rVar.astPtr - i6) + 1;
            z2 = true;
        }
        for (int i8 = 0; i8 < i6; i8++) {
            int i9 = i7 + i8;
            ASTNode aSTNode = rVar.astStack[i9];
            if (aSTNode instanceof Argument) {
                Argument argument = (Argument) aSTNode;
                char[][] typeName = argument.type.getTypeName();
                if ((argument.modifiers & (-17)) != 0 || (typeName.length == 1 && CharOperation.equals(typeName[0], TypeBinding.VOID.sourceName()))) {
                    rVar.astLengthStack[rVar.astLengthPtr] = i8;
                    rVar.astPtr = i9 - 1;
                    rVar.listLength = i8;
                    rVar.currentToken = 0;
                    break;
                }
                if (z2) {
                    rVar.rParenPos = argument.sourceEnd + 1;
                }
            } else {
                rVar.astLengthStack[rVar.astLengthPtr] = i8;
                rVar.astPtr = i9 - 1;
                rVar.listLength = i8;
                rVar.currentToken = 0;
                break;
            }
        }
        if (rVar.listLength > 0 && rVar.astLengthPtr > 0) {
            int i10 = rVar.astLengthStack[rVar.astLengthPtr];
            int i11 = rVar.astPtr - i10;
            boolean z3 = i11 >= 0;
            if (z3) {
                if (!(rVar.astStack[i11] instanceof AbstractMethodDeclaration)) {
                    z3 = false;
                }
                int i12 = i10 + 1;
                for (int i13 = 1; i13 < i12; i13++) {
                    if (!(rVar.astStack[i11 + i13] instanceof Argument)) {
                        z3 = false;
                    }
                }
            }
            if (z3) {
                rVar.consumeMethodHeaderRightParen();
                if (rVar.currentElement == this) {
                    if (this.methodDeclaration.arguments != null) {
                        AbstractMethodDeclaration abstractMethodDeclaration = this.methodDeclaration;
                        abstractMethodDeclaration.sourceEnd = abstractMethodDeclaration.arguments[this.methodDeclaration.arguments.length - 1].sourceEnd;
                    } else {
                        AbstractMethodDeclaration abstractMethodDeclaration2 = this.methodDeclaration;
                        abstractMethodDeclaration2.sourceEnd = abstractMethodDeclaration2.receiver.sourceEnd;
                    }
                    AbstractMethodDeclaration abstractMethodDeclaration3 = this.methodDeclaration;
                    abstractMethodDeclaration3.bodyStart = abstractMethodDeclaration3.sourceEnd + 1;
                    rVar.lastCheckPoint = this.methodDeclaration.bodyStart;
                }
            }
        }
        if (memberValuePairArr != null) {
            System.arraycopy(memberValuePairArr, 0, rVar.astStack, rVar.astPtr + 1, memberValuePairArr.length);
            rVar.astPtr += memberValuePairArr.length;
            int[] iArr2 = rVar.astLengthStack;
            int i14 = rVar.astLengthPtr + 1;
            rVar.astLengthPtr = i14;
            iArr2[i14] = memberValuePairArr.length;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnClosingBrace(int i, int i2) {
        if (this.methodDeclaration.isAnnotationMethod()) {
            updateSourceEndIfNecessary(i, i2);
            return (this.foundOpeningBrace || this.parent == null) ? this : this.parent.updateOnClosingBrace(i, i2);
        }
        if (this.parent != null && (this.parent instanceof RecoveredType) && TypeDeclaration.kind(((RecoveredType) this.parent).typeDeclaration.modifiers) == 2 && !this.foundOpeningBrace) {
            int i3 = i - 1;
            updateSourceEndIfNecessary(i3, i3);
            return this.parent.updateOnClosingBrace(i, i2);
        }
        return super.updateOnClosingBrace(i, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnOpeningBrace(int i, int i2) {
        int i3;
        if (this.bracketBalance == 0 && (i3 = parser().lastIgnoredToken) != -1 && i3 != 112) {
            this.foundOpeningBrace = true;
            this.bracketBalance = 1;
        }
        return super.updateOnOpeningBrace(i, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateParseTree() {
        updatedMethodDeclaration(0, new HashSet());
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateSourceEndIfNecessary(int i, int i2) {
        if (this.methodDeclaration.declarationSourceEnd == 0) {
            if (parser().rBraceSuccessorStart >= i2) {
                this.methodDeclaration.declarationSourceEnd = parser().rBraceEnd;
                this.methodDeclaration.bodyEnd = parser().rBraceStart;
                return;
            }
            this.methodDeclaration.declarationSourceEnd = i2;
            this.methodDeclaration.bodyEnd = i - 1;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement addAnnotationName(int i, int i2, int i3, int i4) {
        RecoveredAnnotation[] recoveredAnnotationArr = this.pendingAnnotations;
        if (recoveredAnnotationArr == null) {
            this.pendingAnnotations = new RecoveredAnnotation[5];
            this.pendingAnnotationCount = 0;
        } else {
            int i5 = this.pendingAnnotationCount;
            if (i5 == recoveredAnnotationArr.length) {
                RecoveredAnnotation[] recoveredAnnotationArr2 = new RecoveredAnnotation[i5 * 2];
                this.pendingAnnotations = recoveredAnnotationArr2;
                System.arraycopy(recoveredAnnotationArr, 0, recoveredAnnotationArr2, 0, i5);
            }
        }
        RecoveredAnnotation recoveredAnnotation = new RecoveredAnnotation(i, i2, i3, this, i4);
        RecoveredAnnotation[] recoveredAnnotationArr3 = this.pendingAnnotations;
        int i6 = this.pendingAnnotationCount;
        this.pendingAnnotationCount = i6 + 1;
        recoveredAnnotationArr3[i6] = recoveredAnnotation;
        return recoveredAnnotation;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void addModifier(int i, int i2) {
        this.pendingModifiers = i | this.pendingModifiers;
        if (this.pendingModifersSourceStart < 0) {
            this.pendingModifersSourceStart = i2;
        }
    }

    void attach(TypeParameter[] typeParameterArr, int i) {
        if (this.methodDeclaration.modifiers != 0) {
            return;
        }
        int i2 = typeParameterArr[typeParameterArr.length - 1].sourceEnd;
        Parser rVar = parser();
        Scanner scanner = rVar.scanner;
        if (Util.getLineNumber(this.methodDeclaration.declarationSourceStart, scanner.lineEnds, 0, scanner.linePtr) != Util.getLineNumber(i2, scanner.lineEnds, 0, scanner.linePtr)) {
            return;
        }
        if (rVar.modifiersSourceStart <= i2 || rVar.modifiersSourceStart >= this.methodDeclaration.declarationSourceStart) {
            AbstractMethodDeclaration abstractMethodDeclaration = this.methodDeclaration;
            if (abstractMethodDeclaration instanceof MethodDeclaration) {
                ((MethodDeclaration) abstractMethodDeclaration).typeParameters = typeParameterArr;
                this.methodDeclaration.declarationSourceStart = i;
            } else if (abstractMethodDeclaration instanceof ConstructorDeclaration) {
                ((ConstructorDeclaration) abstractMethodDeclaration).typeParameters = typeParameterArr;
                this.methodDeclaration.declarationSourceStart = i;
            }
        }
    }

    public void attach(RecoveredAnnotation[] recoveredAnnotationArr, int i, int i2, int i3) {
        if (i > 0) {
            Annotation[] annotationArr = this.methodDeclaration.annotations;
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
}
