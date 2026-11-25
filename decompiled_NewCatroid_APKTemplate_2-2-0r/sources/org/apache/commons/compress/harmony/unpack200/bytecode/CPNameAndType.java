package org.apache.commons.compress.harmony.unpack200.bytecode;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.apache.commons.compress.harmony.unpack200.SegmentUtils;

/* JADX INFO: loaded from: classes13.dex */
public class CPNameAndType extends ConstantPoolEntry {
    private int cachedHashCode;
    CPUTF8 descriptor;
    transient int descriptorIndex;
    private boolean hashCodeComputed;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    CPUTF8 f455name;
    transient int nameIndex;

    public CPNameAndType(CPUTF8 cputf8, CPUTF8 cputf9, int i) {
        super((byte) 12, i);
        this.f455name = (CPUTF8) Objects.requireNonNull(cputf8, "name");
        this.descriptor = (CPUTF8) Objects.requireNonNull(cputf9, "descriptor");
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ConstantPoolEntry, org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CPNameAndType cPNameAndType = (CPNameAndType) obj;
        return this.descriptor.equals(cPNameAndType.descriptor) && this.f455name.equals(cPNameAndType.f455name);
    }

    private void generateHashCode() {
        this.hashCodeComputed = true;
        this.cachedHashCode = ((this.descriptor.hashCode() + 31) * 31) + this.f455name.hashCode();
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    protected ClassFileEntry[] getNestedClassFileEntries() {
        return new ClassFileEntry[]{this.f455name, this.descriptor};
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ConstantPoolEntry, org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public int hashCode() {
        if (!this.hashCodeComputed) {
            generateHashCode();
        }
        return this.cachedHashCode;
    }

    public int invokeInterfaceCount() {
        return SegmentUtils.countInvokeInterfaceArgs(this.descriptor.underlyingString()) + 1;
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    protected void resolve(ClassConstantPool classConstantPool) {
        super.resolve(classConstantPool);
        this.descriptorIndex = classConstantPool.indexOf(this.descriptor);
        this.nameIndex = classConstantPool.indexOf(this.f455name);
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public String toString() {
        return "NameAndType: " + this.f455name + "(" + this.descriptor + Constant.AFTER_QUTO;
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ConstantPoolEntry
    protected void writeBody(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.nameIndex);
        dataOutputStream.writeShort(this.descriptorIndex);
    }
}
