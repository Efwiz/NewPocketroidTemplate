package org.eclipse.jdt.internal.compiler.classfmt;

import org.eclipse.jdt.internal.compiler.env.IBinaryNestedType;

/* JADX INFO: loaded from: classes14.dex */
public class InnerClassInfo extends ClassFileStruct implements IBinaryNestedType {
    private int accessFlags;
    private char[] innerClassName;
    int innerClassNameIndex;
    private char[] innerName;
    int innerNameIndex;
    private char[] outerClassName;
    int outerClassNameIndex;
    private boolean readInnerClassName;
    private boolean readInnerName;
    private boolean readOuterClassName;

    public InnerClassInfo(byte[] bArr, int[] iArr, int i) {
        super(bArr, iArr, i);
        this.innerClassNameIndex = -1;
        this.outerClassNameIndex = -1;
        this.innerNameIndex = -1;
        this.accessFlags = -1;
        this.readInnerClassName = false;
        this.readOuterClassName = false;
        this.readInnerName = false;
        this.innerClassNameIndex = u2At(0);
        this.outerClassNameIndex = u2At(2);
        this.innerNameIndex = u2At(4);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryNestedType
    public char[] getEnclosingTypeName() {
        if (!this.readOuterClassName) {
            this.readOuterClassName = true;
            if (this.outerClassNameIndex != 0) {
                int i = this.constantPoolOffsets[u2At((this.constantPoolOffsets[this.outerClassNameIndex] - this.structOffset) + 1)] - this.structOffset;
                this.outerClassName = utf8At(i + 3, u2At(i + 1));
            }
        }
        return this.outerClassName;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryNestedType
    public int getModifiers() {
        if (this.accessFlags == -1) {
            this.accessFlags = u2At(6);
        }
        return this.accessFlags;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryNestedType
    public char[] getName() {
        if (!this.readInnerClassName) {
            this.readInnerClassName = true;
            if (this.innerClassNameIndex != 0) {
                int i = this.constantPoolOffsets[u2At((this.constantPoolOffsets[this.innerClassNameIndex] - this.structOffset) + 1)] - this.structOffset;
                this.innerClassName = utf8At(i + 3, u2At(i + 1));
            }
        }
        return this.innerClassName;
    }

    public char[] getSourceName() {
        if (!this.readInnerName) {
            this.readInnerName = true;
            if (this.innerNameIndex != 0) {
                int i = this.constantPoolOffsets[this.innerNameIndex] - this.structOffset;
                this.innerName = utf8At(i + 3, u2At(i + 1));
            }
        }
        return this.innerName;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (getName() != null) {
            stringBuffer.append(getName());
        }
        stringBuffer.append("\n");
        if (getEnclosingTypeName() != null) {
            stringBuffer.append(getEnclosingTypeName());
        }
        stringBuffer.append("\n");
        if (getSourceName() != null) {
            stringBuffer.append(getSourceName());
        }
        return stringBuffer.toString();
    }

    void initialize() {
        getModifiers();
        getName();
        getSourceName();
        getEnclosingTypeName();
        reset();
    }
}
