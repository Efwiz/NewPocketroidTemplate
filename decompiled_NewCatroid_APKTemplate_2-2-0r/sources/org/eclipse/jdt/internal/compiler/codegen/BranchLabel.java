package org.eclipse.jdt.internal.compiler.codegen;

import java.util.Arrays;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class BranchLabel extends Label {
    public static final int USED = 2;
    public static final int WIDE = 1;
    BranchLabel delegate;
    private int forwardReferenceCount;
    private int[] forwardReferences;
    public int tagBits;

    public boolean isCaseLabel() {
        return false;
    }

    public boolean isStandardLabel() {
        return true;
    }

    public BranchLabel() {
        this.forwardReferences = new int[10];
        this.forwardReferenceCount = 0;
    }

    public BranchLabel(CodeStream codeStream) {
        super(codeStream);
        this.forwardReferences = new int[10];
        this.forwardReferenceCount = 0;
    }

    void addForwardReference(int i) {
        BranchLabel branchLabel = this.delegate;
        if (branchLabel != null) {
            branchLabel.addForwardReference(i);
            return;
        }
        int i2 = this.forwardReferenceCount;
        if (i2 >= 1) {
            int[] iArr = this.forwardReferences;
            int i3 = iArr[i2 - 1];
            if (i3 < i) {
                int length = iArr.length;
                if (i2 >= length) {
                    int[] iArr2 = new int[length * 2];
                    this.forwardReferences = iArr2;
                    System.arraycopy(iArr, 0, iArr2, 0, length);
                }
                int[] iArr3 = this.forwardReferences;
                int i4 = this.forwardReferenceCount;
                this.forwardReferenceCount = i4 + 1;
                iArr3[i4] = i;
                return;
            }
            if (i3 > i) {
                for (int i5 = 0; i5 < i2; i5++) {
                    if (iArr[i5] == i) {
                        return;
                    }
                }
                int length2 = iArr.length;
                if (i2 >= length2) {
                    int[] iArr4 = new int[length2 * 2];
                    this.forwardReferences = iArr4;
                    System.arraycopy(iArr, 0, iArr4, 0, length2);
                }
                int[] iArr5 = this.forwardReferences;
                int i6 = this.forwardReferenceCount;
                int i7 = i6 + 1;
                this.forwardReferenceCount = i7;
                iArr5[i6] = i;
                Arrays.sort(iArr5, 0, i7);
                return;
            }
            return;
        }
        int[] iArr6 = this.forwardReferences;
        int length3 = iArr6.length;
        if (i2 >= length3) {
            int[] iArr7 = new int[length3 * 2];
            this.forwardReferences = iArr7;
            System.arraycopy(iArr6, 0, iArr7, 0, length3);
        }
        int[] iArr8 = this.forwardReferences;
        int i8 = this.forwardReferenceCount;
        this.forwardReferenceCount = i8 + 1;
        iArr8[i8] = i;
    }

    public void becomeDelegateFor(BranchLabel branchLabel) {
        int i;
        branchLabel.delegate = this;
        int i2 = branchLabel.forwardReferenceCount;
        if (i2 == 0) {
            return;
        }
        int i3 = this.forwardReferenceCount;
        int[] iArr = new int[i3 + i2];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = this.forwardReferences[i6];
            while (true) {
                if (i5 < i2) {
                    int i8 = branchLabel.forwardReferences[i5];
                    if (i7 < i8) {
                        i = i4 + 1;
                        iArr[i4] = i7;
                        break;
                    } else if (i7 == i8) {
                        i = i4 + 1;
                        iArr[i4] = i7;
                        i5++;
                        break;
                    } else {
                        iArr[i4] = i8;
                        i5++;
                        i4++;
                    }
                } else {
                    i = i4 + 1;
                    iArr[i4] = i7;
                    break;
                }
            }
            i4 = i;
        }
        while (i5 < i2) {
            iArr[i4] = branchLabel.forwardReferences[i5];
            i5++;
            i4++;
        }
        this.forwardReferences = iArr;
        this.forwardReferenceCount = i4;
    }

    void branch() {
        this.tagBits |= 2;
        BranchLabel branchLabel = this.delegate;
        if (branchLabel != null) {
            branchLabel.branch();
            return;
        }
        if (this.position == -1) {
            addForwardReference(this.codeStream.position);
            this.codeStream.position += 2;
            this.codeStream.classFileOffset += 2;
            return;
        }
        this.codeStream.writePosition(this);
    }

    void branchWide() {
        this.tagBits |= 2;
        BranchLabel branchLabel = this.delegate;
        if (branchLabel != null) {
            branchLabel.branchWide();
            return;
        }
        if (this.position == -1) {
            addForwardReference(this.codeStream.position);
            this.tagBits |= 1;
            this.codeStream.position += 4;
            this.codeStream.classFileOffset += 4;
            return;
        }
        this.codeStream.writeWidePosition(this);
    }

    public int forwardReferenceCount() {
        BranchLabel branchLabel = this.delegate;
        if (branchLabel != null) {
            branchLabel.forwardReferenceCount();
        }
        return this.forwardReferenceCount;
    }

    public int[] forwardReferences() {
        BranchLabel branchLabel = this.delegate;
        if (branchLabel != null) {
            branchLabel.forwardReferences();
        }
        return this.forwardReferences;
    }

    public void initialize(CodeStream codeStream) {
        this.codeStream = codeStream;
        this.position = -1;
        this.forwardReferenceCount = 0;
        this.delegate = null;
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.Label
    public void place() {
        boolean z;
        if (this.position == -1) {
            this.position = this.codeStream.position;
            this.codeStream.addLabel(this);
            int i = this.position;
            int i2 = this.forwardReferenceCount;
            if (i2 != 0) {
                z = this.forwardReferences[i2 - 1] + 2 == this.position && this.codeStream.bCodeStream[this.codeStream.classFileOffset + (-3)] == -89;
                if (z) {
                    if (this.codeStream.lastAbruptCompletion == this.position) {
                        this.codeStream.lastAbruptCompletion = -1;
                    }
                    CodeStream codeStream = this.codeStream;
                    int i3 = this.position - 3;
                    this.position = i3;
                    codeStream.position = i3;
                    this.codeStream.classFileOffset -= 3;
                    this.forwardReferenceCount--;
                    if (this.codeStream.lastEntryPC == i) {
                        this.codeStream.lastEntryPC = this.position;
                    }
                    if ((this.codeStream.generateAttributes & 28) != 0) {
                        for (LocalVariableBinding localVariableBinding : this.codeStream.locals) {
                            if (localVariableBinding != null && localVariableBinding.initializationCount > 0) {
                                if (localVariableBinding.initializationPCs[((localVariableBinding.initializationCount - 1) << 1) + 1] == i) {
                                    localVariableBinding.initializationPCs[((localVariableBinding.initializationCount - 1) << 1) + 1] = this.position;
                                }
                                if (localVariableBinding.initializationPCs[(localVariableBinding.initializationCount - 1) << 1] == i) {
                                    localVariableBinding.initializationPCs[(localVariableBinding.initializationCount - 1) << 1] = this.position;
                                }
                            }
                        }
                    }
                    if ((this.codeStream.generateAttributes & 2) != 0) {
                        this.codeStream.removeUnusedPcToSourceMapEntries();
                    }
                }
            } else {
                z = false;
            }
            for (int i4 = 0; i4 < this.forwardReferenceCount; i4++) {
                this.codeStream.writePosition(this, this.forwardReferences[i4]);
            }
            if (z) {
                this.codeStream.optimizeBranch(i, this);
            }
        }
    }

    public String toString() {
        int i;
        String name2 = getClass().getName();
        StringBuffer stringBuffer = new StringBuffer(name2.substring(name2.lastIndexOf(46) + 1));
        stringBuffer.append('@').append(Integer.toHexString(hashCode()));
        stringBuffer.append("(position=").append(this.position);
        if (this.delegate != null) {
            stringBuffer.append("delegate=").append(this.delegate);
        }
        stringBuffer.append(", forwards = [");
        int i2 = 0;
        while (true) {
            i = this.forwardReferenceCount;
            if (i2 >= i - 1) {
                break;
            }
            stringBuffer.append(String.valueOf(this.forwardReferences[i2]) + ", ");
            i2++;
        }
        if (i >= 1) {
            stringBuffer.append(this.forwardReferences[i - 1]);
        }
        stringBuffer.append("] )");
        return stringBuffer.toString();
    }
}
