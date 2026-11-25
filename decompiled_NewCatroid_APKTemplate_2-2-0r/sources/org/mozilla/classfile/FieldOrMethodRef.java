package org.mozilla.classfile;

/* JADX INFO: loaded from: classes6.dex */
final class FieldOrMethodRef {
    private String className;
    private int hashCode = -1;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f1062name;
    private String type;

    FieldOrMethodRef(String str, String str2, String str3) {
        this.className = str;
        this.f1062name = str2;
        this.type = str3;
    }

    public String getClassName() {
        return this.className;
    }

    public String getName() {
        return this.f1062name;
    }

    public String getType() {
        return this.type;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FieldOrMethodRef)) {
            return false;
        }
        FieldOrMethodRef fieldOrMethodRef = (FieldOrMethodRef) obj;
        return this.className.equals(fieldOrMethodRef.className) && this.f1062name.equals(fieldOrMethodRef.f1062name) && this.type.equals(fieldOrMethodRef.type);
    }

    public int hashCode() {
        if (this.hashCode == -1) {
            this.hashCode = (this.className.hashCode() ^ this.f1062name.hashCode()) ^ this.type.hashCode();
        }
        return this.hashCode;
    }
}
