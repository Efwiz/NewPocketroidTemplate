package org.eclipse.jdt.internal.compiler.parser;

import java.util.HashSet;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Block;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;
import org.eclipse.jdt.internal.compiler.ast.Initializer;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredUnit extends RecoveredElement {
    public int importCount;
    public RecoveredImport[] imports;
    int pendingAnnotationCount;
    RecoveredAnnotation[] pendingAnnotations;
    int pendingModifersSourceStart;
    int pendingModifiers;
    public int typeCount;
    public RecoveredType[] types;
    public CompilationUnitDeclaration unitDeclaration;

    public RecoveredUnit(CompilationUnitDeclaration compilationUnitDeclaration, int i, Parser parser) {
        super(null, i, parser);
        this.pendingModifersSourceStart = -1;
        this.unitDeclaration = compilationUnitDeclaration;
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
    public RecoveredElement add(AbstractMethodDeclaration abstractMethodDeclaration, int i) {
        int i2 = this.typeCount;
        if (i2 <= 0) {
            return this;
        }
        RecoveredType recoveredType = this.types[i2 - 1];
        int i3 = recoveredType.bodyEnd;
        int i4 = recoveredType.typeDeclaration.bodyEnd;
        recoveredType.bodyEnd = 0;
        recoveredType.typeDeclaration.declarationSourceEnd = 0;
        recoveredType.typeDeclaration.bodyEnd = 0;
        int iKind = TypeDeclaration.kind(recoveredType.typeDeclaration.modifiers);
        if (i3 > 0 && i3 < i4 && iKind != 2 && iKind != 4) {
            Block block = new Block(0);
            block.sourceEnd = i4;
            block.sourceStart = i4;
            Initializer initializer = new Initializer(block, 0);
            initializer.bodyStart = i4;
            initializer.bodyEnd = i4;
            initializer.declarationSourceStart = i4;
            initializer.declarationSourceEnd = i4;
            initializer.sourceStart = i4;
            initializer.sourceEnd = i4;
            recoveredType.add((FieldDeclaration) initializer, i);
        }
        resetPendingModifiers();
        return recoveredType.add(abstractMethodDeclaration, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(FieldDeclaration fieldDeclaration, int i) {
        int i2 = this.typeCount;
        if (i2 <= 0) {
            return this;
        }
        RecoveredType recoveredType = this.types[i2 - 1];
        recoveredType.bodyEnd = 0;
        recoveredType.typeDeclaration.declarationSourceEnd = 0;
        recoveredType.typeDeclaration.bodyEnd = 0;
        resetPendingModifiers();
        return recoveredType.add(fieldDeclaration, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(ImportReference importReference, int i) {
        resetPendingModifiers();
        RecoveredImport[] recoveredImportArr = this.imports;
        if (recoveredImportArr == null) {
            this.imports = new RecoveredImport[5];
            this.importCount = 0;
        } else {
            int i2 = this.importCount;
            if (i2 == recoveredImportArr.length) {
                RecoveredImport[] recoveredImportArr2 = new RecoveredImport[i2 * 2];
                this.imports = recoveredImportArr2;
                System.arraycopy(recoveredImportArr, 0, recoveredImportArr2, 0, i2);
            }
        }
        RecoveredImport recoveredImport = new RecoveredImport(importReference, this, i);
        RecoveredImport[] recoveredImportArr3 = this.imports;
        int i3 = this.importCount;
        this.importCount = i3 + 1;
        recoveredImportArr3[i3] = recoveredImport;
        return importReference.declarationSourceEnd == 0 ? recoveredImport : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement add(TypeDeclaration typeDeclaration, int i) {
        int i2;
        if ((typeDeclaration.bits & 512) != 0 && (i2 = this.typeCount) > 0) {
            RecoveredType recoveredType = this.types[i2 - 1];
            recoveredType.bodyEnd = 0;
            recoveredType.typeDeclaration.bodyEnd = 0;
            recoveredType.typeDeclaration.declarationSourceEnd = 0;
            recoveredType.bracketBalance = recoveredType.bracketBalance + 1;
            resetPendingModifiers();
            return recoveredType.add(typeDeclaration, i);
        }
        RecoveredType[] recoveredTypeArr = this.types;
        if (recoveredTypeArr == null) {
            this.types = new RecoveredType[5];
            this.typeCount = 0;
        } else {
            int i3 = this.typeCount;
            if (i3 == recoveredTypeArr.length) {
                RecoveredType[] recoveredTypeArr2 = new RecoveredType[i3 * 2];
                this.types = recoveredTypeArr2;
                System.arraycopy(recoveredTypeArr, 0, recoveredTypeArr2, 0, i3);
            }
        }
        RecoveredType recoveredType2 = new RecoveredType(typeDeclaration, this, i);
        RecoveredType[] recoveredTypeArr3 = this.types;
        int i4 = this.typeCount;
        this.typeCount = i4 + 1;
        recoveredTypeArr3[i4] = recoveredType2;
        int i5 = this.pendingAnnotationCount;
        if (i5 > 0) {
            recoveredType2.attach(this.pendingAnnotations, i5, this.pendingModifiers, this.pendingModifersSourceStart);
        }
        resetPendingModifiers();
        return typeDeclaration.declarationSourceEnd == 0 ? recoveredType2 : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public ASTNode parseTree() {
        return this.unitDeclaration;
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
        return this.unitDeclaration.sourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public String toString(int i) {
        StringBuffer stringBuffer = new StringBuffer(tabString(i));
        stringBuffer.append("Recovered unit: [\n");
        int i2 = i + 1;
        this.unitDeclaration.print(i2, stringBuffer);
        stringBuffer.append(tabString(i2));
        stringBuffer.append("]");
        if (this.imports != null) {
            for (int i3 = 0; i3 < this.importCount; i3++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.imports[i3].toString(i2));
            }
        }
        if (this.types != null) {
            for (int i4 = 0; i4 < this.typeCount; i4++) {
                stringBuffer.append("\n");
                stringBuffer.append(this.types[i4].toString(i2));
            }
        }
        return stringBuffer.toString();
    }

    public CompilationUnitDeclaration updatedCompilationUnitDeclaration() {
        int i;
        int i2 = this.importCount;
        if (i2 > 0) {
            ImportReference[] importReferenceArr = new ImportReference[i2];
            for (int i3 = 0; i3 < this.importCount; i3++) {
                importReferenceArr[i3] = this.imports[i3].updatedImportReference();
            }
            this.unitDeclaration.imports = importReferenceArr;
        }
        if (this.typeCount > 0) {
            int length = this.unitDeclaration.types == null ? 0 : this.unitDeclaration.types.length;
            TypeDeclaration[] typeDeclarationArr = new TypeDeclaration[this.typeCount + length];
            if (length > 0) {
                System.arraycopy(this.unitDeclaration.types, 0, typeDeclarationArr, 0, length);
            }
            if (this.types[this.typeCount - 1].typeDeclaration.declarationSourceEnd == 0) {
                this.types[this.typeCount - 1].typeDeclaration.declarationSourceEnd = this.unitDeclaration.sourceEnd;
                this.types[this.typeCount - 1].typeDeclaration.bodyEnd = this.unitDeclaration.sourceEnd;
            }
            HashSet hashSet = new HashSet();
            int i4 = length;
            int i5 = 0;
            while (true) {
                i = this.typeCount;
                if (i5 >= i) {
                    break;
                }
                TypeDeclaration typeDeclarationUpdatedTypeDeclaration = this.types[i5].updatedTypeDeclaration(0, hashSet);
                if (typeDeclarationUpdatedTypeDeclaration != null && (typeDeclarationUpdatedTypeDeclaration.bits & 256) == 0) {
                    typeDeclarationArr[i4] = typeDeclarationUpdatedTypeDeclaration;
                    i4++;
                }
                i5++;
            }
            if (i4 != i) {
                int i6 = length + i4;
                TypeDeclaration[] typeDeclarationArr2 = new TypeDeclaration[i6];
                System.arraycopy(typeDeclarationArr, 0, typeDeclarationArr2, 0, i6);
                typeDeclarationArr = typeDeclarationArr2;
            }
            this.unitDeclaration.types = typeDeclarationArr;
        }
        return this.unitDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateParseTree() {
        updatedCompilationUnitDeclaration();
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateSourceEndIfNecessary(int i, int i2) {
        if (this.unitDeclaration.sourceEnd == 0) {
            this.unitDeclaration.sourceEnd = i2;
        }
    }
}
