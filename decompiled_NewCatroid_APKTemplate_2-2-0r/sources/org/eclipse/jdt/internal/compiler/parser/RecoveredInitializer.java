package org.eclipse.jdt.internal.compiler.parser;

import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.Block;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Initializer;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredInitializer extends RecoveredField implements TerminalTokens {
    public RecoveredBlock initializerBody;
    public int localTypeCount;
    public RecoveredType[] localTypes;
    int pendingAnnotationCount;
    RecoveredAnnotation[] pendingAnnotations;
    int pendingModifersSourceStart;
    int pendingModifiers;

    public RecoveredInitializer(FieldDeclaration fieldDeclaration, RecoveredElement recoveredElement, int i) {
        this(fieldDeclaration, recoveredElement, i, null);
    }

    public RecoveredInitializer(FieldDeclaration fieldDeclaration, RecoveredElement recoveredElement, int i, Parser parser) {
        super(fieldDeclaration, recoveredElement, i, parser);
        this.pendingModifersSourceStart = -1;
        this.foundOpeningBrace = true;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(Block block, int i) {
        if (this.fieldDeclaration.declarationSourceEnd > 0 && block.sourceStart > this.fieldDeclaration.declarationSourceEnd) {
            resetPendingModifiers();
            return this.parent == null ? this : this.parent.add(block, i);
        }
        if (!this.foundOpeningBrace) {
            this.foundOpeningBrace = true;
            this.bracketBalance++;
        }
        this.initializerBody = new RecoveredBlock(block, this, i);
        return block.sourceEnd == 0 ? this.initializerBody : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredField, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(FieldDeclaration fieldDeclaration, int i) {
        resetPendingModifiers();
        if ((fieldDeclaration.modifiers & (-17)) == 0 && fieldDeclaration.type != null) {
            char[][] typeName = fieldDeclaration.type.getTypeName();
            if (typeName.length != 1 || !CharOperation.equals(typeName[0], TypeBinding.VOID.sourceName())) {
                return (this.fieldDeclaration.declarationSourceEnd <= 0 || fieldDeclaration.declarationSourceStart <= this.fieldDeclaration.declarationSourceEnd || this.parent == null) ? this : this.parent.add(fieldDeclaration, i);
            }
        }
        if (this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(fieldDeclaration.declarationSourceStart - 1));
        return this.parent.add(fieldDeclaration, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredField, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(LocalDeclaration localDeclaration, int i) {
        if (this.fieldDeclaration.declarationSourceEnd != 0 && localDeclaration.declarationSourceStart > this.fieldDeclaration.declarationSourceEnd) {
            resetPendingModifiers();
            return this.parent == null ? this : this.parent.add(localDeclaration, i);
        }
        Block block = new Block(0);
        block.sourceStart = ((Initializer) this.fieldDeclaration).sourceStart;
        RecoveredElement recoveredElementAdd = add(block, 1);
        RecoveredBlock recoveredBlock = this.initializerBody;
        if (recoveredBlock != null) {
            recoveredBlock.attachPendingModifiers(this.pendingAnnotations, this.pendingAnnotationCount, this.pendingModifiers, this.pendingModifersSourceStart);
        }
        resetPendingModifiers();
        return recoveredElementAdd.add(localDeclaration, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredField, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(Statement statement, int i) {
        if (this.fieldDeclaration.declarationSourceEnd != 0 && statement.sourceStart > this.fieldDeclaration.declarationSourceEnd) {
            resetPendingModifiers();
            return this.parent == null ? this : this.parent.add(statement, i);
        }
        Block block = new Block(0);
        block.sourceStart = ((Initializer) this.fieldDeclaration).sourceStart;
        RecoveredElement recoveredElementAdd = add(block, 1);
        RecoveredBlock recoveredBlock = this.initializerBody;
        if (recoveredBlock != null) {
            recoveredBlock.attachPendingModifiers(this.pendingAnnotations, this.pendingAnnotationCount, this.pendingModifiers, this.pendingModifersSourceStart);
        }
        resetPendingModifiers();
        return recoveredElementAdd.add(statement, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredField, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(TypeDeclaration typeDeclaration, int i) {
        if (this.fieldDeclaration.declarationSourceEnd != 0 && typeDeclaration.declarationSourceStart > this.fieldDeclaration.declarationSourceEnd) {
            resetPendingModifiers();
            return this.parent == null ? this : this.parent.add(typeDeclaration, i);
        }
        if ((typeDeclaration.bits & 256) != 0 || parser().methodRecoveryActivated || parser().statementRecoveryActivated) {
            Block block = new Block(0);
            block.sourceStart = ((Initializer) this.fieldDeclaration).sourceStart;
            RecoveredElement recoveredElementAdd = add(block, 1);
            RecoveredBlock recoveredBlock = this.initializerBody;
            if (recoveredBlock != null) {
                recoveredBlock.attachPendingModifiers(this.pendingAnnotations, this.pendingAnnotationCount, this.pendingModifiers, this.pendingModifersSourceStart);
            }
            resetPendingModifiers();
            return recoveredElementAdd.add(typeDeclaration, i);
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

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void resetPendingModifiers() {
        this.pendingAnnotations = null;
        this.pendingAnnotationCount = 0;
        this.pendingModifiers = 0;
        this.pendingModifersSourceStart = -1;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredField, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public String toString(int i) {
        StringBuffer stringBuffer = new StringBuffer(tabString(i));
        stringBuffer.append("Recovered initializer:\n");
        int i2 = i + 1;
        this.fieldDeclaration.print(i2, stringBuffer);
        if (this.annotations != null) {
            for (int i3 = 0; i3 < this.annotationCount; i3++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.annotations[i3].toString(i2));
            }
        }
        if (this.initializerBody != null) {
            stringBuffer.append("\n");
            stringBuffer.append(this.initializerBody.toString(i2));
        }
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredField
    public FieldDeclaration updatedFieldDeclaration(int i, Set<TypeDeclaration> set) {
        RecoveredBlock recoveredBlock = this.initializerBody;
        if (recoveredBlock != null) {
            Block blockUpdatedBlock = recoveredBlock.updatedBlock(i, set);
            if (blockUpdatedBlock != null) {
                Initializer initializer = (Initializer) this.fieldDeclaration;
                initializer.block = blockUpdatedBlock;
                if (initializer.declarationSourceEnd == 0) {
                    initializer.declarationSourceEnd = blockUpdatedBlock.sourceEnd;
                    initializer.bodyEnd = blockUpdatedBlock.sourceEnd;
                }
            }
            if (this.localTypeCount > 0) {
                this.fieldDeclaration.bits |= 2;
            }
        }
        if (this.fieldDeclaration.sourceEnd == 0) {
            this.fieldDeclaration.sourceEnd = this.fieldDeclaration.declarationSourceEnd;
        }
        return this.fieldDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredField, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnClosingBrace(int i, int i2) {
        int i3 = this.bracketBalance - 1;
        this.bracketBalance = i3;
        if (i3 > 0 || this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(i, i2);
        return this.parent;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredField, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnOpeningBrace(int i, int i2) {
        this.bracketBalance++;
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredField, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateSourceEndIfNecessary(int i, int i2) {
        if (this.fieldDeclaration.declarationSourceEnd == 0) {
            Initializer initializer = (Initializer) this.fieldDeclaration;
            if (parser().rBraceSuccessorStart >= i2) {
                if (initializer.bodyStart < parser().rBraceEnd) {
                    initializer.declarationSourceEnd = parser().rBraceEnd;
                } else {
                    initializer.declarationSourceEnd = initializer.bodyStart;
                }
                if (initializer.bodyStart < parser().rBraceStart) {
                    initializer.bodyEnd = parser().rBraceStart;
                } else {
                    initializer.bodyEnd = initializer.bodyStart;
                }
            } else {
                if (i2 < initializer.declarationSourceStart) {
                    initializer.declarationSourceEnd = initializer.declarationSourceStart;
                    initializer.bodyEnd = initializer.declarationSourceEnd;
                } else {
                    initializer.declarationSourceEnd = i2;
                    initializer.bodyEnd = i - 1;
                }
                if (initializer.bodyStart > initializer.declarationSourceEnd) {
                    initializer.bodyStart = initializer.declarationSourceEnd;
                    if (initializer.block != null) {
                        initializer.block.sourceStart = initializer.declarationSourceStart;
                    }
                }
            }
            if (initializer.block != null) {
                initializer.block.sourceEnd = initializer.declarationSourceEnd;
            }
        }
    }
}
