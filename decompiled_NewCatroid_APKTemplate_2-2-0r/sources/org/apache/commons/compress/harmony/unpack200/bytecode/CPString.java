package org.apache.commons.compress.harmony.unpack200.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes13.dex */
public class CPString extends CPConstant {
    private int cachedHashCode;
    private boolean hashCodeComputed;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final CPUTF8 f456name;
    private transient int nameIndex;

    public CPString(CPUTF8 cputf8, int i) {
        super((byte) 8, cputf8, i);
        this.f456name = cputf8;
    }

    private void generateHashCode() {
        this.hashCodeComputed = true;
        this.cachedHashCode = 31 + this.f456name.hashCode();
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    protected ClassFileEntry[] getNestedClassFileEntries() {
        return new ClassFileEntry[]{this.f456name};
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.CPConstant, org.apache.commons.compress.harmony.unpack200.bytecode.ConstantPoolEntry, org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public int hashCode() {
        if (!this.hashCodeComputed) {
            generateHashCode();
        }
        return this.cachedHashCode;
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    protected void resolve(ClassConstantPool classConstantPool) {
        super.resolve(classConstantPool);
        this.nameIndex = classConstantPool.indexOf(this.f456name);
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public String toString() {
        return "String: " + getValue();
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ConstantPoolEntry
    protected void writeBody(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.nameIndex);
    }
}
