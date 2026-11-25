package org.apache.commons.compress.harmony.unpack200.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/* JADX INFO: loaded from: classes13.dex */
public class SourceFileAttribute extends Attribute {
    private static CPUTF8 attributeName;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final CPUTF8 f457name;
    private int nameIndex;

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.Attribute
    protected int getLength() {
        return 2;
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.Attribute
    public boolean isSourceFileAttribute() {
        return true;
    }

    public static void setAttributeName(CPUTF8 cputf8) {
        attributeName = cputf8;
    }

    public SourceFileAttribute(CPUTF8 cputf8) {
        super(attributeName);
        this.f457name = cputf8;
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.Attribute, org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return super.equals(obj) && getClass() == obj.getClass() && Objects.equals(this.f457name, ((SourceFileAttribute) obj).f457name);
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.Attribute, org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    protected ClassFileEntry[] getNestedClassFileEntries() {
        return new ClassFileEntry[]{getAttributeName(), this.f457name};
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.Attribute, org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public int hashCode() {
        int iHashCode = super.hashCode() * 31;
        CPUTF8 cputf8 = this.f457name;
        return iHashCode + (cputf8 == null ? 0 : cputf8.hashCode());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.Attribute, org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public void resolve(ClassConstantPool classConstantPool) {
        super.resolve(classConstantPool);
        this.nameIndex = classConstantPool.indexOf(this.f457name);
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public String toString() {
        return "SourceFile: " + this.f457name;
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.Attribute
    protected void writeBody(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.nameIndex);
    }
}
