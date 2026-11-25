package org.eclipse.jdt.internal.compiler.parser;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.Block;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredBlock extends RecoveredStatement implements TerminalTokens {
    public Block blockDeclaration;
    int pendingAnnotationCount;
    RecoveredAnnotation[] pendingAnnotations;
    public RecoveredLocalVariable pendingArgument;
    int pendingModifersSourceStart;
    int pendingModifiers;
    public boolean preserveContent;
    public int statementCount;
    public RecoveredStatement[] statements;

    public RecoveredBlock(Block block, RecoveredElement recoveredElement, int i) {
        super(block, recoveredElement, i);
        this.preserveContent = false;
        this.pendingModifersSourceStart = -1;
        this.blockDeclaration = block;
        this.foundOpeningBrace = true;
        this.preserveContent = parser().methodRecoveryActivated || parser().statementRecoveryActivated;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(AbstractMethodDeclaration abstractMethodDeclaration, int i) {
        if (this.parent != null && (this.parent instanceof RecoveredMethod)) {
            RecoveredMethod recoveredMethod = (RecoveredMethod) this.parent;
            if (recoveredMethod.methodBody == this && recoveredMethod.parent == null) {
                resetPendingModifiers();
                return this;
            }
        }
        return super.add(abstractMethodDeclaration, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(Block block, int i) {
        resetPendingModifiers();
        if (this.blockDeclaration.sourceEnd != 0 && block.sourceStart > this.blockDeclaration.sourceEnd) {
            return this.parent.add(block, i);
        }
        RecoveredBlock recoveredBlock = new RecoveredBlock(block, this, i);
        RecoveredStatement recoveredStatement = this.pendingArgument;
        if (recoveredStatement != null) {
            recoveredBlock.attach(recoveredStatement);
            this.pendingArgument = null;
        }
        if (parser().statementRecoveryActivated) {
            addBlockStatement(recoveredBlock);
        }
        attach(recoveredBlock);
        return block.sourceEnd == 0 ? recoveredBlock : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(LocalDeclaration localDeclaration, int i) {
        return add(localDeclaration, i, false);
    }

    public RecoveredElement add(LocalDeclaration localDeclaration, int i, boolean z) {
        if (localDeclaration.isRecoveredFromLoneIdentifier()) {
            return this;
        }
        if (this.blockDeclaration.sourceEnd != 0 && localDeclaration.declarationSourceStart > this.blockDeclaration.sourceEnd) {
            resetPendingModifiers();
            return z ? this : this.parent.add(localDeclaration, i);
        }
        RecoveredLocalVariable recoveredLocalVariable = new RecoveredLocalVariable(localDeclaration, this, i);
        int i2 = this.pendingAnnotationCount;
        if (i2 > 0) {
            recoveredLocalVariable.attach(this.pendingAnnotations, i2, this.pendingModifiers, this.pendingModifersSourceStart);
        }
        resetPendingModifiers();
        if (localDeclaration instanceof Argument) {
            this.pendingArgument = recoveredLocalVariable;
            return this;
        }
        attach(recoveredLocalVariable);
        return localDeclaration.declarationSourceEnd == 0 ? recoveredLocalVariable : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(Statement statement, int i) {
        return add(statement, i, false);
    }

    public RecoveredElement add(Statement statement, int i, boolean z) {
        resetPendingModifiers();
        if (this.blockDeclaration.sourceEnd != 0 && statement.sourceStart > this.blockDeclaration.sourceEnd) {
            return z ? this : this.parent.add(statement, i);
        }
        RecoveredStatement recoveredStatement = new RecoveredStatement(statement, this, i);
        attach(recoveredStatement);
        return statement.sourceEnd == 0 ? recoveredStatement : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(TypeDeclaration typeDeclaration, int i) {
        return add(typeDeclaration, i, false);
    }

    public RecoveredElement add(TypeDeclaration typeDeclaration, int i, boolean z) {
        if (this.blockDeclaration.sourceEnd != 0 && typeDeclaration.declarationSourceStart > this.blockDeclaration.sourceEnd) {
            resetPendingModifiers();
            return z ? this : this.parent.add(typeDeclaration, i);
        }
        RecoveredType recoveredType = new RecoveredType(typeDeclaration, this, i);
        int i2 = this.pendingAnnotationCount;
        if (i2 > 0) {
            recoveredType.attach(this.pendingAnnotations, i2, this.pendingModifiers, this.pendingModifersSourceStart);
        }
        resetPendingModifiers();
        attach(recoveredType);
        return typeDeclaration.declarationSourceEnd == 0 ? recoveredType : this;
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

    void attach(RecoveredStatement recoveredStatement) {
        RecoveredStatement[] recoveredStatementArr = this.statements;
        if (recoveredStatementArr == null) {
            this.statements = new RecoveredStatement[5];
            this.statementCount = 0;
        } else {
            int i = this.statementCount;
            if (i == recoveredStatementArr.length) {
                RecoveredStatement[] recoveredStatementArr2 = new RecoveredStatement[i * 2];
                this.statements = recoveredStatementArr2;
                System.arraycopy(recoveredStatementArr, 0, recoveredStatementArr2, 0, i);
            }
        }
        RecoveredStatement[] recoveredStatementArr3 = this.statements;
        int i2 = this.statementCount;
        this.statementCount = i2 + 1;
        recoveredStatementArr3[i2] = recoveredStatement;
    }

    void attachPendingModifiers(RecoveredAnnotation[] recoveredAnnotationArr, int i, int i2, int i3) {
        this.pendingAnnotations = recoveredAnnotationArr;
        this.pendingAnnotationCount = i;
        this.pendingModifiers = i2;
        this.pendingModifersSourceStart = i3;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public ASTNode parseTree() {
        return this.blockDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void resetPendingModifiers() {
        this.pendingAnnotations = null;
        this.pendingAnnotationCount = 0;
        this.pendingModifiers = 0;
        this.pendingModifersSourceStart = -1;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public String toString(int i) {
        StringBuffer stringBuffer = new StringBuffer(tabString(i));
        stringBuffer.append("Recovered block:\n");
        int i2 = i + 1;
        this.blockDeclaration.print(i2, stringBuffer);
        if (this.statements != null) {
            for (int i3 = 0; i3 < this.statementCount; i3++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.statements[i3].toString(i2));
            }
        }
        return stringBuffer.toString();
    }

    public Block updatedBlock(int i, Set<TypeDeclaration> set) {
        int i2;
        int i3;
        int i4;
        if (!this.preserveContent || (i2 = this.statementCount) == 0) {
            return null;
        }
        Statement[] statementArr = new Statement[i2];
        RecoveredStatement recoveredStatement = this.statements[i2 - 1];
        RecoveredMethod recoveredMethodEnclosingMethod = enclosingMethod();
        RecoveredInitializer recoveredInitializerEnclosingInitializer = enclosingInitializer();
        if (recoveredMethodEnclosingMethod != null) {
            i3 = recoveredMethodEnclosingMethod.methodDeclaration.bodyEnd;
            if (recoveredInitializerEnclosingInitializer != null && recoveredMethodEnclosingMethod.methodDeclaration.sourceStart < recoveredInitializerEnclosingInitializer.fieldDeclaration.sourceStart) {
                i3 = recoveredInitializerEnclosingInitializer.fieldDeclaration.declarationSourceEnd;
            }
        } else {
            i3 = recoveredInitializerEnclosingInitializer != null ? recoveredInitializerEnclosingInitializer.fieldDeclaration.declarationSourceEnd : this.blockDeclaration.sourceEnd - 1;
        }
        if (recoveredStatement instanceof RecoveredLocalVariable) {
            RecoveredLocalVariable recoveredLocalVariable = (RecoveredLocalVariable) recoveredStatement;
            if (recoveredLocalVariable.localDeclaration.declarationSourceEnd == 0) {
                recoveredLocalVariable.localDeclaration.declarationSourceEnd = i3;
                recoveredLocalVariable.localDeclaration.declarationEnd = i3;
            }
        } else if (recoveredStatement instanceof RecoveredBlock) {
            RecoveredBlock recoveredBlock = (RecoveredBlock) recoveredStatement;
            if (recoveredBlock.blockDeclaration.sourceEnd == 0) {
                recoveredBlock.blockDeclaration.sourceEnd = i3;
            }
        } else if (!(recoveredStatement instanceof RecoveredType) && recoveredStatement.statement.sourceEnd == 0) {
            recoveredStatement.statement.sourceEnd = i3;
        }
        int i5 = this.blockDeclaration.sourceStart;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            i4 = this.statementCount;
            if (i6 >= i4) {
                break;
            }
            Statement statementUpdatedStatement = this.statements[i6].updatedStatement(i, set);
            if (statementUpdatedStatement != null) {
                int i8 = 0;
                while (true) {
                    if (i8 < i6) {
                        Statement statement = statementArr[i8];
                        if (statement instanceof LocalDeclaration) {
                            LocalDeclaration localDeclaration = (LocalDeclaration) statement;
                            if (localDeclaration.initialization != null && statementUpdatedStatement.sourceStart >= localDeclaration.initialization.sourceStart && statementUpdatedStatement.sourceEnd <= localDeclaration.initialization.sourceEnd) {
                                break;
                            }
                        }
                        i8++;
                    } else {
                        int i9 = i7 + 1;
                        statementArr[i7] = statementUpdatedStatement;
                        if (statementUpdatedStatement instanceof LocalDeclaration) {
                            LocalDeclaration localDeclaration2 = (LocalDeclaration) statementUpdatedStatement;
                            if (localDeclaration2.declarationSourceEnd > i5) {
                                i5 = localDeclaration2.declarationSourceEnd;
                            }
                        } else if (statementUpdatedStatement instanceof TypeDeclaration) {
                            TypeDeclaration typeDeclaration = (TypeDeclaration) statementUpdatedStatement;
                            if (typeDeclaration.declarationSourceEnd > i5) {
                                i5 = typeDeclaration.declarationSourceEnd;
                            }
                        } else if (statementUpdatedStatement.sourceEnd > i5) {
                            i5 = statementUpdatedStatement.sourceEnd;
                        }
                        i7 = i9;
                        break;
                    }
                }
            }
            i6++;
        }
        if (i7 == 0) {
            return null;
        }
        if (i7 != i4) {
            this.blockDeclaration.statements = new Statement[i7];
            System.arraycopy(statementArr, 0, this.blockDeclaration.statements, 0, i7);
        } else {
            this.blockDeclaration.statements = statementArr;
        }
        if (this.blockDeclaration.sourceEnd == 0) {
            if (i5 < i3) {
                this.blockDeclaration.sourceEnd = i3;
            } else {
                this.blockDeclaration.sourceEnd = i5;
            }
        }
        return this.blockDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement
    public Statement updatedStatement(int i, Set<TypeDeclaration> set) {
        return updatedBlock(i, set);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnClosingBrace(int i, int i2) {
        int i3 = this.bracketBalance - 1;
        this.bracketBalance = i3;
        if (i3 > 0 || this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(i, i2);
        RecoveredMethod recoveredMethodEnclosingMethod = enclosingMethod();
        if (recoveredMethodEnclosingMethod != null && recoveredMethodEnclosingMethod.methodBody == this) {
            return this.parent.updateOnClosingBrace(i, i2);
        }
        RecoveredInitializer recoveredInitializerEnclosingInitializer = enclosingInitializer();
        if (recoveredInitializerEnclosingInitializer != null && recoveredInitializerEnclosingInitializer.initializerBody == this) {
            return this.parent.updateOnClosingBrace(i, i2);
        }
        return this.parent;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnOpeningBrace(int i, int i2) {
        Block block = new Block(0);
        block.sourceStart = parser().scanner.startPosition;
        return add(block, 1);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateParseTree() {
        updatedBlock(0, new HashSet());
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(FieldDeclaration fieldDeclaration, int i) {
        resetPendingModifiers();
        if ((fieldDeclaration.modifiers & (-17)) == 0 && fieldDeclaration.type != null) {
            char[][] typeName = fieldDeclaration.type.getTypeName();
            if (typeName.length != 1 || !CharOperation.equals(typeName[0], TypeBinding.VOID.sourceName())) {
                return (this.blockDeclaration.sourceEnd == 0 || fieldDeclaration.declarationSourceStart <= this.blockDeclaration.sourceEnd) ? this : this.parent.add(fieldDeclaration, i);
            }
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(fieldDeclaration.declarationSourceStart - 1));
        return this.parent.add(fieldDeclaration, i);
    }
}
