package org.eclipse.jdt.internal.compiler.parser;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Block;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Initializer;
import org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredType extends RecoveredStatement implements TerminalTokens {
    public static final int MAX_TYPE_DEPTH = 256;
    public int annotationCount;
    public RecoveredAnnotation[] annotations;
    public int bodyEnd;
    public int fieldCount;
    public RecoveredField[] fields;
    public boolean insideEnumConstantPart;
    public int memberTypeCount;
    public RecoveredType[] memberTypes;
    public int methodCount;
    public RecoveredMethod[] methods;
    public int modifiers;
    public int modifiersStart;
    int pendingAnnotationCount;
    RecoveredAnnotation[] pendingAnnotations;
    int pendingModifersSourceStart;
    int pendingModifiers;
    public TypeParameter[] pendingTypeParameters;
    public int pendingTypeParametersStart;
    public boolean preserveContent;
    public TypeDeclaration typeDeclaration;

    public RecoveredType(TypeDeclaration typeDeclaration, RecoveredElement recoveredElement, int i) {
        super(typeDeclaration, recoveredElement, i);
        this.preserveContent = false;
        this.insideEnumConstantPart = false;
        this.pendingModifersSourceStart = -1;
        this.typeDeclaration = typeDeclaration;
        if (typeDeclaration.allocation != null && typeDeclaration.allocation.type == null) {
            this.foundOpeningBrace = true;
        } else {
            this.foundOpeningBrace = !bodyStartsAtHeaderEnd();
        }
        this.insideEnumConstantPart = TypeDeclaration.kind(typeDeclaration.modifiers) == 3;
        if (this.foundOpeningBrace) {
            this.bracketBalance++;
        }
        this.preserveContent = parser().methodRecoveryActivated || parser().statementRecoveryActivated;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(AbstractMethodDeclaration abstractMethodDeclaration, int i) {
        if (this.typeDeclaration.declarationSourceEnd != 0 && abstractMethodDeclaration.declarationSourceStart > this.typeDeclaration.declarationSourceEnd) {
            this.pendingTypeParameters = null;
            resetPendingModifiers();
            return this.parent.add(abstractMethodDeclaration, i);
        }
        RecoveredMethod[] recoveredMethodArr = this.methods;
        if (recoveredMethodArr == null) {
            this.methods = new RecoveredMethod[5];
            this.methodCount = 0;
        } else {
            int i2 = this.methodCount;
            if (i2 == recoveredMethodArr.length) {
                RecoveredMethod[] recoveredMethodArr2 = new RecoveredMethod[i2 * 2];
                this.methods = recoveredMethodArr2;
                System.arraycopy(recoveredMethodArr, 0, recoveredMethodArr2, 0, i2);
            }
        }
        RecoveredMethod recoveredMethod = new RecoveredMethod(abstractMethodDeclaration, this, i, this.recoveringParser);
        RecoveredMethod[] recoveredMethodArr3 = this.methods;
        int i3 = this.methodCount;
        this.methodCount = i3 + 1;
        recoveredMethodArr3[i3] = recoveredMethod;
        TypeParameter[] typeParameterArr = this.pendingTypeParameters;
        if (typeParameterArr != null) {
            recoveredMethod.attach(typeParameterArr, this.pendingTypeParametersStart);
            this.pendingTypeParameters = null;
        }
        int i4 = this.pendingAnnotationCount;
        if (i4 > 0 || this.pendingModifiers != 0) {
            recoveredMethod.attach(this.pendingAnnotations, i4, this.pendingModifiers, this.pendingModifersSourceStart);
        }
        resetPendingModifiers();
        this.insideEnumConstantPart = false;
        if (!this.foundOpeningBrace) {
            this.foundOpeningBrace = true;
            this.bracketBalance++;
        }
        return abstractMethodDeclaration.declarationSourceEnd == 0 ? recoveredMethod : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(Block block, int i) {
        this.pendingTypeParameters = null;
        resetPendingModifiers();
        return add((FieldDeclaration) new Initializer(block, parser().recoveredStaticInitializerStart != 0 ? 8 : 0), i);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(FieldDeclaration fieldDeclaration, int i) {
        RecoveredField recoveredField;
        this.pendingTypeParameters = null;
        if (this.typeDeclaration.declarationSourceEnd != 0 && fieldDeclaration.declarationSourceStart > this.typeDeclaration.declarationSourceEnd) {
            resetPendingModifiers();
            return this.parent.add(fieldDeclaration, i);
        }
        RecoveredField[] recoveredFieldArr = this.fields;
        if (recoveredFieldArr == null) {
            this.fields = new RecoveredField[5];
            this.fieldCount = 0;
        } else {
            int i2 = this.fieldCount;
            if (i2 == recoveredFieldArr.length) {
                RecoveredField[] recoveredFieldArr2 = new RecoveredField[i2 * 2];
                this.fields = recoveredFieldArr2;
                System.arraycopy(recoveredFieldArr, 0, recoveredFieldArr2, 0, i2);
            }
        }
        int kind = fieldDeclaration.getKind();
        if (kind == 1) {
            recoveredField = new RecoveredField(fieldDeclaration, this, i);
        } else if (kind != 2) {
            if (kind != 3) {
                return this;
            }
            recoveredField = new RecoveredField(fieldDeclaration, this, i);
        } else {
            recoveredField = new RecoveredInitializer(fieldDeclaration, this, i);
        }
        RecoveredField[] recoveredFieldArr3 = this.fields;
        int i3 = this.fieldCount;
        this.fieldCount = i3 + 1;
        recoveredFieldArr3[i3] = recoveredField;
        int i4 = this.pendingAnnotationCount;
        if (i4 > 0) {
            recoveredField.attach(this.pendingAnnotations, i4, this.pendingModifiers, this.pendingModifersSourceStart);
        }
        resetPendingModifiers();
        if (!this.foundOpeningBrace) {
            this.foundOpeningBrace = true;
            this.bracketBalance++;
        }
        return fieldDeclaration.declarationSourceEnd == 0 ? recoveredField : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(TypeDeclaration typeDeclaration, int i) {
        this.pendingTypeParameters = null;
        if (this.typeDeclaration.declarationSourceEnd != 0 && typeDeclaration.declarationSourceStart > this.typeDeclaration.declarationSourceEnd) {
            resetPendingModifiers();
            return this.parent.add(typeDeclaration, i);
        }
        this.insideEnumConstantPart = false;
        if ((typeDeclaration.bits & 512) != 0) {
            int i2 = this.methodCount;
            if (i2 <= 0) {
                return this;
            }
            RecoveredMethod recoveredMethod = this.methods[i2 - 1];
            recoveredMethod.methodDeclaration.bodyEnd = 0;
            recoveredMethod.methodDeclaration.declarationSourceEnd = 0;
            recoveredMethod.bracketBalance = recoveredMethod.bracketBalance + 1;
            resetPendingModifiers();
            return recoveredMethod.add(typeDeclaration, i);
        }
        RecoveredType[] recoveredTypeArr = this.memberTypes;
        if (recoveredTypeArr == null) {
            this.memberTypes = new RecoveredType[5];
            this.memberTypeCount = 0;
        } else {
            int i3 = this.memberTypeCount;
            if (i3 == recoveredTypeArr.length) {
                RecoveredType[] recoveredTypeArr2 = new RecoveredType[i3 * 2];
                this.memberTypes = recoveredTypeArr2;
                System.arraycopy(recoveredTypeArr, 0, recoveredTypeArr2, 0, i3);
            }
        }
        RecoveredType recoveredType = new RecoveredType(typeDeclaration, this, i);
        RecoveredType[] recoveredTypeArr3 = this.memberTypes;
        int i4 = this.memberTypeCount;
        this.memberTypeCount = i4 + 1;
        recoveredTypeArr3[i4] = recoveredType;
        int i5 = this.pendingAnnotationCount;
        if (i5 > 0) {
            recoveredType.attach(this.pendingAnnotations, i5, this.pendingModifiers, this.pendingModifersSourceStart);
        }
        resetPendingModifiers();
        if (!this.foundOpeningBrace) {
            this.foundOpeningBrace = true;
            this.bracketBalance++;
        }
        return typeDeclaration.declarationSourceEnd == 0 ? recoveredType : this;
    }

    public void add(TypeParameter[] typeParameterArr, int i) {
        this.pendingTypeParameters = typeParameterArr;
        this.pendingTypeParametersStart = i;
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

    public void attach(RecoveredAnnotation[] recoveredAnnotationArr, int i, int i2, int i3) {
        if (i > 0) {
            Annotation[] annotationArr = this.typeDeclaration.annotations;
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

    public int bodyEnd() {
        int i = this.bodyEnd;
        return i == 0 ? this.typeDeclaration.declarationSourceEnd : i;
    }

    public boolean bodyStartsAtHeaderEnd() {
        if (this.typeDeclaration.superInterfaces != null) {
            return this.typeDeclaration.bodyStart == this.typeDeclaration.superInterfaces[this.typeDeclaration.superInterfaces.length - 1].sourceEnd + 1;
        }
        if (this.typeDeclaration.superclass != null) {
            return this.typeDeclaration.bodyStart == this.typeDeclaration.superclass.sourceEnd + 1;
        }
        if (this.typeDeclaration.typeParameters == null) {
            return this.typeDeclaration.bodyStart == this.typeDeclaration.sourceEnd + 1;
        }
        return this.typeDeclaration.bodyStart == this.typeDeclaration.typeParameters[this.typeDeclaration.typeParameters.length - 1].sourceEnd + 1;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredType enclosingType() {
        for (RecoveredElement recoveredElement = this.parent; recoveredElement != null; recoveredElement = recoveredElement.parent) {
            if (recoveredElement instanceof RecoveredType) {
                return (RecoveredType) recoveredElement;
            }
        }
        return null;
    }

    public int lastMemberEnd() {
        int i = this.typeDeclaration.bodyStart;
        int i2 = this.fieldCount;
        if (i2 > 0) {
            FieldDeclaration fieldDeclaration = this.fields[i2 - 1].fieldDeclaration;
            if (i < fieldDeclaration.declarationSourceEnd && fieldDeclaration.declarationSourceEnd != 0) {
                i = fieldDeclaration.declarationSourceEnd;
            }
        }
        int i3 = this.methodCount;
        if (i3 > 0) {
            AbstractMethodDeclaration abstractMethodDeclaration = this.methods[i3 - 1].methodDeclaration;
            if (i < abstractMethodDeclaration.declarationSourceEnd && abstractMethodDeclaration.declarationSourceEnd != 0) {
                i = abstractMethodDeclaration.declarationSourceEnd;
            }
        }
        int i4 = this.memberTypeCount;
        if (i4 <= 0) {
            return i;
        }
        TypeDeclaration typeDeclaration = this.memberTypes[i4 - 1].typeDeclaration;
        return (i >= typeDeclaration.declarationSourceEnd || typeDeclaration.declarationSourceEnd == 0) ? i : typeDeclaration.declarationSourceEnd;
    }

    public char[] name() {
        return this.typeDeclaration.f619name;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public ASTNode parseTree() {
        return this.typeDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void resetPendingModifiers() {
        this.pendingAnnotations = null;
        this.pendingAnnotationCount = 0;
        this.pendingModifiers = 0;
        this.pendingModifersSourceStart = -1;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public int sourceEnd() {
        return this.typeDeclaration.declarationSourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public String toString(int i) {
        StringBuffer stringBuffer = new StringBuffer(tabString(i));
        stringBuffer.append("Recovered type:\n");
        if ((this.typeDeclaration.bits & 512) != 0) {
            stringBuffer.append(tabString(i));
            stringBuffer.append(" ");
        }
        int i2 = i + 1;
        this.typeDeclaration.print(i2, stringBuffer);
        if (this.annotations != null) {
            for (int i3 = 0; i3 < this.annotationCount; i3++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.annotations[i3].toString(i2));
            }
        }
        if (this.memberTypes != null) {
            for (int i4 = 0; i4 < this.memberTypeCount; i4++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.memberTypes[i4].toString(i2));
            }
        }
        if (this.fields != null) {
            for (int i5 = 0; i5 < this.fieldCount; i5++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.fields[i5].toString(i2));
            }
        }
        if (this.methods != null) {
            for (int i6 = 0; i6 < this.methodCount; i6++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.methods[i6].toString(i2));
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateBodyStart(int i) {
        this.foundOpeningBrace = true;
        this.typeDeclaration.bodyStart = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement
    public Statement updatedStatement(int i, Set set) {
        if ((this.typeDeclaration.bits & 512) != 0 && !this.preserveContent) {
            return null;
        }
        TypeDeclaration typeDeclarationUpdatedTypeDeclaration = updatedTypeDeclaration(i + 1, set);
        if (typeDeclarationUpdatedTypeDeclaration == null || (typeDeclarationUpdatedTypeDeclaration.bits & 512) == 0) {
            return typeDeclarationUpdatedTypeDeclaration;
        }
        QualifiedAllocationExpression qualifiedAllocationExpression = typeDeclarationUpdatedTypeDeclaration.allocation;
        if (qualifiedAllocationExpression.statementEnd == -1) {
            qualifiedAllocationExpression.statementEnd = typeDeclarationUpdatedTypeDeclaration.declarationSourceEnd;
        }
        return qualifiedAllocationExpression;
    }

    public TypeDeclaration updatedTypeDeclaration(int i, Set<TypeDeclaration> set) {
        boolean zCheckConstructors;
        boolean z;
        boolean z2;
        int i2;
        int i3;
        if (i >= 256 || set.contains(this.typeDeclaration)) {
            return null;
        }
        set.add(this.typeDeclaration);
        int i4 = this.typeDeclaration.bodyStart;
        if (this.modifiers != 0) {
            this.typeDeclaration.modifiers |= this.modifiers;
            if (this.modifiersStart < this.typeDeclaration.declarationSourceStart) {
                this.typeDeclaration.declarationSourceStart = this.modifiersStart;
            }
        }
        if (this.annotationCount > 0) {
            int length = this.typeDeclaration.annotations == null ? 0 : this.typeDeclaration.annotations.length;
            Annotation[] annotationArr = new Annotation[this.annotationCount + length];
            if (length > 0) {
                System.arraycopy(this.typeDeclaration.annotations, 0, annotationArr, this.annotationCount, length);
            }
            for (int i5 = 0; i5 < this.annotationCount; i5++) {
                annotationArr[i5] = this.annotations[i5].updatedAnnotationReference();
            }
            this.typeDeclaration.annotations = annotationArr;
            int i6 = this.annotations[0].annotation.sourceStart;
            if (i6 < this.typeDeclaration.declarationSourceStart) {
                this.typeDeclaration.declarationSourceStart = i6;
            }
        }
        if (this.memberTypeCount > 0) {
            int length2 = this.typeDeclaration.memberTypes == null ? 0 : this.typeDeclaration.memberTypes.length;
            TypeDeclaration[] typeDeclarationArr = new TypeDeclaration[this.memberTypeCount + length2];
            if (length2 > 0) {
                System.arraycopy(this.typeDeclaration.memberTypes, 0, typeDeclarationArr, 0, length2);
            }
            if (this.memberTypes[this.memberTypeCount - 1].typeDeclaration.declarationSourceEnd == 0) {
                int iBodyEnd = bodyEnd();
                this.memberTypes[this.memberTypeCount - 1].typeDeclaration.declarationSourceEnd = iBodyEnd;
                this.memberTypes[this.memberTypeCount - 1].typeDeclaration.bodyEnd = iBodyEnd;
            }
            int i7 = 0;
            int i8 = 0;
            while (true) {
                i3 = this.memberTypeCount;
                if (i7 >= i3) {
                    break;
                }
                TypeDeclaration typeDeclarationUpdatedTypeDeclaration = this.memberTypes[i7].updatedTypeDeclaration(i + 1, set);
                if (typeDeclarationUpdatedTypeDeclaration != null) {
                    typeDeclarationArr[i8 + length2] = typeDeclarationUpdatedTypeDeclaration;
                    i8++;
                }
                i7++;
            }
            if (i8 < i3) {
                int i9 = length2 + i8;
                TypeDeclaration[] typeDeclarationArr2 = new TypeDeclaration[i9];
                System.arraycopy(typeDeclarationArr, 0, typeDeclarationArr2, 0, i9);
                typeDeclarationArr = typeDeclarationArr2;
            }
            if (typeDeclarationArr.length > 0) {
                this.typeDeclaration.memberTypes = typeDeclarationArr;
                if (typeDeclarationArr[typeDeclarationArr.length - 1].declarationSourceEnd > i4) {
                    i4 = typeDeclarationArr[typeDeclarationArr.length - 1].declarationSourceEnd;
                }
            }
        }
        if (this.fieldCount > 0) {
            int length3 = this.typeDeclaration.fields == null ? 0 : this.typeDeclaration.fields.length;
            int i10 = this.fieldCount + length3;
            FieldDeclaration[] fieldDeclarationArr = new FieldDeclaration[i10];
            if (length3 > 0) {
                System.arraycopy(this.typeDeclaration.fields, 0, fieldDeclarationArr, 0, length3);
            }
            if (this.fields[this.fieldCount - 1].fieldDeclaration.declarationSourceEnd == 0) {
                int iBodyEnd2 = bodyEnd();
                this.fields[this.fieldCount - 1].fieldDeclaration.declarationSourceEnd = iBodyEnd2;
                this.fields[this.fieldCount - 1].fieldDeclaration.declarationEnd = iBodyEnd2;
            }
            int i11 = 0;
            while (true) {
                i2 = this.fieldCount;
                if (i11 >= i2) {
                    break;
                }
                fieldDeclarationArr[length3 + i11] = this.fields[i11].updatedFieldDeclaration(i, set);
                i11++;
            }
            for (int i12 = i2 - 1; i12 > 0; i12--) {
                int i13 = length3 + i12;
                int i14 = i13 - 1;
                if (fieldDeclarationArr[i14].declarationSourceStart == fieldDeclarationArr[i13].declarationSourceStart) {
                    fieldDeclarationArr[i14].declarationSourceEnd = fieldDeclarationArr[i13].declarationSourceEnd;
                    fieldDeclarationArr[i14].declarationEnd = fieldDeclarationArr[i13].declarationEnd;
                }
            }
            this.typeDeclaration.fields = fieldDeclarationArr;
            int i15 = i10 - 1;
            if (fieldDeclarationArr[i15].declarationSourceEnd > i4) {
                i4 = fieldDeclarationArr[i15].declarationSourceEnd;
            }
        }
        int length4 = this.typeDeclaration.methods == null ? 0 : this.typeDeclaration.methods.length;
        int i16 = this.methodCount;
        int i17 = -1;
        if (i16 > 0) {
            int i18 = i16 + length4;
            AbstractMethodDeclaration[] abstractMethodDeclarationArr = new AbstractMethodDeclaration[i18];
            boolean z3 = false;
            for (int i19 = 0; i19 < length4; i19++) {
                AbstractMethodDeclaration abstractMethodDeclaration = this.typeDeclaration.methods[i19];
                if (abstractMethodDeclaration.isDefaultConstructor()) {
                    i17 = i19;
                }
                if (abstractMethodDeclaration.isAbstract()) {
                    z3 = true;
                }
                abstractMethodDeclarationArr[i19] = abstractMethodDeclaration;
            }
            if (this.methods[this.methodCount - 1].methodDeclaration.declarationSourceEnd == 0) {
                int iBodyEnd3 = bodyEnd();
                this.methods[this.methodCount - 1].methodDeclaration.declarationSourceEnd = iBodyEnd3;
                this.methods[this.methodCount - 1].methodDeclaration.bodyEnd = iBodyEnd3;
            }
            int i20 = length4;
            z = false;
            boolean z4 = z3;
            for (int i21 = 0; i21 < this.methodCount; i21++) {
                int i22 = 0;
                while (true) {
                    if (i22 < length4) {
                        if (abstractMethodDeclarationArr[i22] == this.methods[i21].methodDeclaration) {
                            break;
                        }
                        i22++;
                    } else {
                        AbstractMethodDeclaration abstractMethodDeclarationUpdatedMethodDeclaration = this.methods[i21].updatedMethodDeclaration(i, set);
                        if (abstractMethodDeclarationUpdatedMethodDeclaration.isConstructor()) {
                            z = true;
                        }
                        if (abstractMethodDeclarationUpdatedMethodDeclaration.isAbstract()) {
                            z4 = true;
                        }
                        abstractMethodDeclarationArr[i20] = abstractMethodDeclarationUpdatedMethodDeclaration;
                        i20++;
                        break;
                    }
                }
            }
            if (i20 != i18) {
                AbstractMethodDeclaration[] abstractMethodDeclarationArr2 = new AbstractMethodDeclaration[i20];
                System.arraycopy(abstractMethodDeclarationArr, 0, abstractMethodDeclarationArr2, 0, i20);
                abstractMethodDeclarationArr = abstractMethodDeclarationArr2;
            }
            this.typeDeclaration.methods = abstractMethodDeclarationArr;
            if (abstractMethodDeclarationArr[abstractMethodDeclarationArr.length - 1].declarationSourceEnd > i4) {
                i4 = abstractMethodDeclarationArr[abstractMethodDeclarationArr.length - 1].declarationSourceEnd;
            }
            if (z4) {
                this.typeDeclaration.bits |= 2048;
            }
            zCheckConstructors = this.typeDeclaration.checkConstructors(parser());
        } else {
            zCheckConstructors = false;
            for (int i23 = 0; i23 < length4; i23++) {
                if (this.typeDeclaration.methods[i23].isConstructor()) {
                    zCheckConstructors = true;
                }
            }
            z = false;
        }
        if (this.typeDeclaration.needClassInitMethod()) {
            int i24 = 0;
            while (true) {
                if (i24 < length4) {
                    if (this.typeDeclaration.methods[i24].isClinit()) {
                        break;
                    }
                    i24++;
                } else {
                    this.typeDeclaration.addClinit();
                    break;
                }
            }
        }
        if (i17 >= 0 && z) {
            AbstractMethodDeclaration[] abstractMethodDeclarationArr3 = new AbstractMethodDeclaration[this.typeDeclaration.methods.length - 1];
            if (i17 != 0) {
                System.arraycopy(this.typeDeclaration.methods, 0, abstractMethodDeclarationArr3, 0, i17);
            }
            if (i17 != this.typeDeclaration.methods.length - 1) {
                System.arraycopy(this.typeDeclaration.methods, i17 + 1, abstractMethodDeclarationArr3, i17, (this.typeDeclaration.methods.length - i17) - 1);
            }
            this.typeDeclaration.methods = abstractMethodDeclarationArr3;
        } else {
            int iKind = TypeDeclaration.kind(this.typeDeclaration.modifiers);
            if (!zCheckConstructors && iKind != 2 && iKind != 4 && this.typeDeclaration.allocation == null) {
                RecoveredElement recoveredElement = this.parent;
                while (true) {
                    if (recoveredElement == null) {
                        z2 = false;
                        break;
                    }
                    if (recoveredElement instanceof RecoveredField) {
                        z2 = true;
                        break;
                    }
                    recoveredElement = recoveredElement.parent;
                }
                this.typeDeclaration.createDefaultConstructor(!parser().diet || z2, true);
            }
        }
        if (this.parent instanceof RecoveredType) {
            this.typeDeclaration.bits |= 1024;
        } else if (this.parent instanceof RecoveredMethod) {
            this.typeDeclaration.bits |= 256;
        }
        if (this.typeDeclaration.declarationSourceEnd == 0) {
            this.typeDeclaration.declarationSourceEnd = i4;
            this.typeDeclaration.bodyEnd = i4;
        }
        return this.typeDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateFromParserState() {
        if (bodyStartsAtHeaderEnd() && this.typeDeclaration.allocation == null) {
            Parser rVar = parser();
            if (rVar.listLength > 0 && rVar.astLengthPtr > 0) {
                int i = rVar.astLengthStack[rVar.astLengthPtr];
                int i2 = rVar.astPtr - i;
                boolean z = i2 >= 0;
                if (z) {
                    if (!(rVar.astStack[i2] instanceof TypeDeclaration)) {
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
                    rVar.consumeClassHeaderImplements();
                    return;
                }
                return;
            }
            if (rVar.listTypeParameterLength > 0) {
                int i5 = rVar.listTypeParameterLength;
                int i6 = rVar.genericsPtr;
                boolean z2 = i6 + 1 >= i5 && rVar.astPtr > -1;
                if (z2) {
                    if (!(rVar.astStack[rVar.astPtr] instanceof TypeDeclaration)) {
                        z2 = false;
                    }
                    int i7 = i6;
                    while (i7 + 1 > i5 && !(rVar.genericsStack[i7] instanceof TypeParameter)) {
                        i7--;
                    }
                    for (int i8 = 0; i8 < i5; i8++) {
                        if (!(rVar.genericsStack[i7 - i8] instanceof TypeParameter)) {
                            z2 = false;
                        }
                    }
                    i6 = i7;
                }
                if (z2) {
                    TypeDeclaration typeDeclaration = (TypeDeclaration) rVar.astStack[rVar.astPtr];
                    TypeParameter[] typeParameterArr = new TypeParameter[i5];
                    typeDeclaration.typeParameters = typeParameterArr;
                    System.arraycopy(rVar.genericsStack, (i6 - i5) + 1, typeParameterArr, 0, i5);
                    typeDeclaration.bodyStart = typeDeclaration.typeParameters[i5 - 1].declarationSourceEnd + 1;
                    rVar.listTypeParameterLength = 0;
                    rVar.lastCheckPoint = typeDeclaration.bodyStart;
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnClosingBrace(int i, int i2) {
        int i3 = this.bracketBalance - 1;
        this.bracketBalance = i3;
        if (i3 > 0 || this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(i, i2);
        this.bodyEnd = i - 1;
        return this.parent;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x001a  */
    /* JADX WARN: Code duplicated, block: B:15:0x001f A[FALL_THROUGH] */
    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnOpeningBrace(int i, int i2) {
        Initializer initializer;
        if (this.bracketBalance == 0) {
            Parser rVar = parser();
            int i3 = rVar.lastIgnoredToken;
            if (i3 == -1 || i3 == 96 || i3 == 114) {
                if (rVar.recoveredStaticInitializerStart != 0) {
                    this.foundOpeningBrace = true;
                    this.bracketBalance = 1;
                }
            } else {
                switch (i3) {
                    case 14:
                    case 15:
                    case 16:
                        if (rVar.recoveredStaticInitializerStart != 0) {
                        }
                    default:
                        this.foundOpeningBrace = true;
                        this.bracketBalance = 1;
                        break;
                }
            }
        }
        if (this.bracketBalance == 1) {
            Block block = new Block(0);
            Parser rVar2 = parser();
            block.sourceStart = rVar2.scanner.startPosition;
            if (rVar2.recoveredStaticInitializerStart == 0) {
                initializer = new Initializer(block, 0);
            } else {
                initializer = new Initializer(block, 8);
                initializer.declarationSourceStart = rVar2.recoveredStaticInitializerStart;
            }
            initializer.bodyStart = rVar2.scanner.currentPosition;
            return add((FieldDeclaration) initializer, 1);
        }
        return super.updateOnOpeningBrace(i, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateParseTree() {
        updatedTypeDeclaration(0, new HashSet());
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredStatement, org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateSourceEndIfNecessary(int i, int i2) {
        if (this.typeDeclaration.declarationSourceEnd == 0) {
            this.bodyEnd = 0;
            this.typeDeclaration.declarationSourceEnd = i2;
            this.typeDeclaration.bodyEnd = i2;
        }
    }

    public void annotationsConsumed(Annotation[] annotationArr) {
        int i = this.pendingAnnotationCount;
        RecoveredAnnotation[] recoveredAnnotationArr = new RecoveredAnnotation[i];
        int length = annotationArr.length;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Annotation annotation = this.pendingAnnotations[i3].annotation;
            int i4 = 0;
            while (true) {
                if (i4 < length) {
                    if (annotationArr[i4] == annotation) {
                        break;
                    } else {
                        i4++;
                    }
                } else {
                    recoveredAnnotationArr[i2] = this.pendingAnnotations[i3];
                    i2++;
                    break;
                }
            }
        }
        if (i2 != this.pendingAnnotationCount) {
            this.pendingAnnotations = recoveredAnnotationArr;
            this.pendingAnnotationCount = i2;
        }
    }
}
