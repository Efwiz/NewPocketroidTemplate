package org.apache.commons.compress.harmony.unpack200.bytecode;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes13.dex */
public class CPMember extends ClassFileEntry {
    List<Attribute> attributes;
    protected final CPUTF8 descriptor;
    transient int descriptorIndex;
    short flags;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    CPUTF8 f454name;
    transient int nameIndex;

    public CPMember(CPUTF8 cputf8, CPUTF8 cputf9, long j, List<Attribute> list) {
        this.f454name = (CPUTF8) Objects.requireNonNull(cputf8, "name");
        this.descriptor = (CPUTF8) Objects.requireNonNull(cputf9, "descriptor");
        this.flags = (short) j;
        this.attributes = list == null ? Collections.EMPTY_LIST : list;
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    protected void doWrite(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.flags);
        dataOutputStream.writeShort(this.nameIndex);
        dataOutputStream.writeShort(this.descriptorIndex);
        int size = this.attributes.size();
        dataOutputStream.writeShort(size);
        for (int i = 0; i < size; i++) {
            this.attributes.get(i).doWrite(dataOutputStream);
        }
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CPMember cPMember = (CPMember) obj;
        return this.attributes.equals(cPMember.attributes) && this.descriptor.equals(cPMember.descriptor) && this.flags == cPMember.flags && this.f454name.equals(cPMember.f454name);
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    protected ClassFileEntry[] getNestedClassFileEntries() {
        int size = this.attributes.size();
        ClassFileEntry[] classFileEntryArr = new ClassFileEntry[size + 2];
        classFileEntryArr[0] = this.f454name;
        classFileEntryArr[1] = this.descriptor;
        for (int i = 0; i < size; i++) {
            classFileEntryArr[i + 2] = this.attributes.get(i);
        }
        return classFileEntryArr;
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public int hashCode() {
        return ((((((this.attributes.hashCode() + 31) * 31) + this.descriptor.hashCode()) * 31) + this.flags) * 31) + this.f454name.hashCode();
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    protected void resolve(final ClassConstantPool classConstantPool) {
        super.resolve(classConstantPool);
        this.nameIndex = classConstantPool.indexOf(this.f454name);
        this.descriptorIndex = classConstantPool.indexOf(this.descriptor);
        this.attributes.forEach(new Consumer() { // from class: org.apache.commons.compress.harmony.unpack200.bytecode.CPMember$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Attribute) obj).resolve(classConstantPool);
            }
        });
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public String toString() {
        return "CPMember: " + this.f454name + "(" + this.descriptor + Constant.AFTER_QUTO;
    }
}
