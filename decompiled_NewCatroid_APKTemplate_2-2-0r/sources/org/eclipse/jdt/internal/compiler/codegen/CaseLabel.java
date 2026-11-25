package org.eclipse.jdt.internal.compiler.codegen;

/* JADX INFO: loaded from: classes14.dex */
public class CaseLabel extends BranchLabel {
    public int instructionPosition;

    @Override // org.eclipse.jdt.internal.compiler.codegen.BranchLabel
    public boolean isCaseLabel() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.BranchLabel
    public boolean isStandardLabel() {
        return false;
    }

    public CaseLabel(CodeStream codeStream) {
        super(codeStream);
        this.instructionPosition = -1;
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.BranchLabel
    void branch() {
        if (this.position == -1) {
            addForwardReference(this.codeStream.position);
            this.codeStream.position += 4;
            this.codeStream.classFileOffset += 4;
            return;
        }
        this.codeStream.writeSignedWord(this.position - this.instructionPosition);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.BranchLabel
    void branchWide() {
        branch();
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.BranchLabel, org.eclipse.jdt.internal.compiler.codegen.Label
    public void place() {
        if ((this.tagBits & 2) != 0) {
            this.position = this.codeStream.getPosition();
        } else {
            this.position = this.codeStream.position;
        }
        if (this.instructionPosition != -1) {
            int i = this.position - this.instructionPosition;
            int[] iArrForwardReferences = forwardReferences();
            int iForwardReferenceCount = forwardReferenceCount();
            for (int i2 = 0; i2 < iForwardReferenceCount; i2++) {
                this.codeStream.writeSignedWord(iArrForwardReferences[i2], i);
            }
            this.codeStream.addLabel(this);
        }
    }

    void placeInstruction() {
        if (this.instructionPosition == -1) {
            this.instructionPosition = this.codeStream.position;
        }
    }
}
