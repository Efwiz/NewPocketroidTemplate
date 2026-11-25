package org.eclipse.jdt.internal.compiler.parser;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredField extends RecoveredElement {
    boolean alreadyCompletedFieldInitialization;
    public int annotationCount;
    public RecoveredAnnotation[] annotations;
    public int anonymousTypeCount;
    public RecoveredType[] anonymousTypes;
    public FieldDeclaration fieldDeclaration;
    public int modifiers;
    public int modifiersStart;

    public RecoveredField(FieldDeclaration fieldDeclaration, RecoveredElement recoveredElement, int i) {
        this(fieldDeclaration, recoveredElement, i, null);
    }

    public RecoveredField(FieldDeclaration fieldDeclaration, RecoveredElement recoveredElement, int i, Parser parser) {
        super(recoveredElement, i, parser);
        this.fieldDeclaration = fieldDeclaration;
        this.alreadyCompletedFieldInitialization = fieldDeclaration.initialization != null;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(LocalDeclaration localDeclaration, int i) {
        return this.lambdaNestLevel > 0 ? this : super.add(localDeclaration, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(FieldDeclaration fieldDeclaration, int i) {
        resetPendingModifiers();
        if (this.parent == null) {
            return this;
        }
        if (this.fieldDeclaration.declarationSourceStart == fieldDeclaration.declarationSourceStart) {
            if (this.fieldDeclaration.initialization != null) {
                updateSourceEndIfNecessary(this.fieldDeclaration.initialization.sourceEnd);
            } else {
                updateSourceEndIfNecessary(this.fieldDeclaration.sourceEnd);
            }
        } else {
            updateSourceEndIfNecessary(previousAvailableLineEnd(fieldDeclaration.declarationSourceStart - 1));
        }
        return this.parent.add(fieldDeclaration, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(Statement statement, int i) {
        if (this.alreadyCompletedFieldInitialization || !(statement instanceof Expression)) {
            return super.add(statement, i);
        }
        if (statement.sourceEnd > 0) {
            this.alreadyCompletedFieldInitialization = true;
        }
        this.fieldDeclaration.initialization = (Expression) statement;
        this.fieldDeclaration.declarationSourceEnd = statement.sourceEnd;
        this.fieldDeclaration.declarationEnd = statement.sourceEnd;
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(TypeDeclaration typeDeclaration, int i) {
        if (this.alreadyCompletedFieldInitialization || (typeDeclaration.bits & 512) == 0 || (this.fieldDeclaration.declarationSourceEnd != 0 && typeDeclaration.sourceStart > this.fieldDeclaration.declarationSourceEnd)) {
            return super.add(typeDeclaration, i);
        }
        RecoveredType[] recoveredTypeArr = this.anonymousTypes;
        if (recoveredTypeArr == null) {
            this.anonymousTypes = new RecoveredType[5];
            this.anonymousTypeCount = 0;
        } else {
            int i2 = this.anonymousTypeCount;
            if (i2 == recoveredTypeArr.length) {
                RecoveredType[] recoveredTypeArr2 = new RecoveredType[i2 * 2];
                this.anonymousTypes = recoveredTypeArr2;
                System.arraycopy(recoveredTypeArr, 0, recoveredTypeArr2, 0, i2);
            }
        }
        RecoveredType recoveredType = new RecoveredType(typeDeclaration, this, i);
        RecoveredType[] recoveredTypeArr3 = this.anonymousTypes;
        int i3 = this.anonymousTypeCount;
        this.anonymousTypeCount = i3 + 1;
        recoveredTypeArr3[i3] = recoveredType;
        return recoveredType;
    }

    public void attach(RecoveredAnnotation[] recoveredAnnotationArr, int i, int i2, int i3) {
        if (i > 0) {
            Annotation[] annotationArr = this.fieldDeclaration.annotations;
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

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public ASTNode parseTree() {
        return this.fieldDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public int sourceEnd() {
        return this.fieldDeclaration.declarationSourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public String toString(int i) {
        StringBuffer stringBuffer = new StringBuffer(tabString(i));
        stringBuffer.append("Recovered field:\n");
        int i2 = i + 1;
        this.fieldDeclaration.print(i2, stringBuffer);
        if (this.annotations != null) {
            for (int i3 = 0; i3 < this.annotationCount; i3++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.annotations[i3].toString(i2));
            }
        }
        if (this.anonymousTypes != null) {
            for (int i4 = 0; i4 < this.anonymousTypeCount; i4++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.anonymousTypes[i4].toString(i2));
            }
        }
        return stringBuffer.toString();
    }

    public FieldDeclaration updatedFieldDeclaration(int i, Set<TypeDeclaration> set) {
        ArrayInitializer arrayInitializer;
        int i2;
        TypeDeclaration typeDeclarationUpdatedTypeDeclaration;
        if (this.modifiers != 0) {
            this.fieldDeclaration.modifiers |= this.modifiers;
            if (this.modifiersStart < this.fieldDeclaration.declarationSourceStart) {
                this.fieldDeclaration.declarationSourceStart = this.modifiersStart;
            }
        }
        int i3 = 0;
        if (this.annotationCount > 0) {
            int length = this.fieldDeclaration.annotations == null ? 0 : this.fieldDeclaration.annotations.length;
            Annotation[] annotationArr = new Annotation[this.annotationCount + length];
            if (length > 0) {
                System.arraycopy(this.fieldDeclaration.annotations, 0, annotationArr, this.annotationCount, length);
            }
            for (int i4 = 0; i4 < this.annotationCount; i4++) {
                annotationArr[i4] = this.annotations[i4].updatedAnnotationReference();
            }
            this.fieldDeclaration.annotations = annotationArr;
            int i5 = this.annotations[0].annotation.sourceStart;
            if (i5 < this.fieldDeclaration.declarationSourceStart) {
                this.fieldDeclaration.declarationSourceStart = i5;
            }
        }
        if (this.anonymousTypes != null) {
            if (this.fieldDeclaration.initialization == null) {
                if (this.anonymousTypeCount > 1) {
                    arrayInitializer = new ArrayInitializer();
                    arrayInitializer.expressions = new Expression[this.anonymousTypeCount];
                } else {
                    arrayInitializer = null;
                }
                int i6 = 0;
                while (true) {
                    i2 = this.anonymousTypeCount;
                    if (i3 >= i2) {
                        break;
                    }
                    RecoveredType recoveredType = this.anonymousTypes[i3];
                    TypeDeclaration typeDeclaration = recoveredType.typeDeclaration;
                    if (typeDeclaration.declarationSourceEnd == 0) {
                        typeDeclaration.declarationSourceEnd = this.fieldDeclaration.declarationSourceEnd;
                        typeDeclaration.bodyEnd = this.fieldDeclaration.declarationSourceEnd;
                    }
                    if (recoveredType.preserveContent && (typeDeclarationUpdatedTypeDeclaration = recoveredType.updatedTypeDeclaration(i + 1, set)) != null) {
                        if (this.anonymousTypeCount > 1) {
                            if (i6 == 0) {
                                this.fieldDeclaration.initialization = arrayInitializer;
                            }
                            arrayInitializer.expressions[i6] = typeDeclarationUpdatedTypeDeclaration.allocation;
                            i6++;
                        } else {
                            this.fieldDeclaration.initialization = typeDeclarationUpdatedTypeDeclaration.allocation;
                        }
                        int i7 = typeDeclarationUpdatedTypeDeclaration.declarationSourceEnd;
                        if (i7 > this.fieldDeclaration.declarationSourceEnd) {
                            this.fieldDeclaration.declarationSourceEnd = i7;
                            this.fieldDeclaration.declarationEnd = i7;
                        }
                    }
                    i3++;
                }
                if (i2 > 0) {
                    this.fieldDeclaration.bits |= 2;
                }
            } else if (this.fieldDeclaration.getKind() == 3) {
                while (i3 < this.anonymousTypeCount) {
                    RecoveredType recoveredType2 = this.anonymousTypes[i3];
                    TypeDeclaration typeDeclaration2 = recoveredType2.typeDeclaration;
                    if (typeDeclaration2.declarationSourceEnd == 0) {
                        typeDeclaration2.declarationSourceEnd = this.fieldDeclaration.declarationSourceEnd;
                        typeDeclaration2.bodyEnd = this.fieldDeclaration.declarationSourceEnd;
                    }
                    recoveredType2.updatedTypeDeclaration(i, set);
                    i3++;
                }
            }
        }
        return this.fieldDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnClosingBrace(int i, int i2) {
        if (this.bracketBalance > 0) {
            this.bracketBalance--;
            if (this.bracketBalance == 0) {
                if (this.fieldDeclaration.getKind() == 3) {
                    updateSourceEndIfNecessary(i2);
                    return this.parent;
                }
                if (this.fieldDeclaration.declarationSourceEnd > 0) {
                    this.alreadyCompletedFieldInitialization = true;
                }
            }
            return this;
        }
        if (this.bracketBalance == 0) {
            this.alreadyCompletedFieldInitialization = true;
            updateSourceEndIfNecessary(i2 - 1);
        }
        return this.parent != null ? this.parent.updateOnClosingBrace(i, i2) : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnOpeningBrace(int i, int i2) {
        if (this.fieldDeclaration.declarationSourceEnd == 0) {
            if ((this.fieldDeclaration.type instanceof ArrayTypeReference) || (this.fieldDeclaration.type instanceof ArrayQualifiedTypeReference)) {
                if (!this.alreadyCompletedFieldInitialization) {
                    this.bracketBalance++;
                    return null;
                }
            } else {
                this.bracketBalance++;
                return null;
            }
        }
        if (this.fieldDeclaration.declarationSourceEnd == 0 && this.fieldDeclaration.getKind() == 3) {
            this.bracketBalance++;
            return null;
        }
        updateSourceEndIfNecessary(i - 1, i2 - 1);
        return this.parent.updateOnOpeningBrace(i, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateParseTree() {
        updatedFieldDeclaration(0, new HashSet());
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateSourceEndIfNecessary(int i, int i2) {
        if (this.fieldDeclaration.declarationSourceEnd == 0) {
            this.fieldDeclaration.declarationSourceEnd = i2;
            this.fieldDeclaration.declarationEnd = i2;
        }
    }
}
