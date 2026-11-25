package org.eclipse.jdt.internal.compiler.impl;

/* JADX INFO: loaded from: classes14.dex */
public class StringConstant extends Constant {
    private String value;

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public int typeID() {
        return 11;
    }

    public static Constant fromValue(String str) {
        return new StringConstant(str);
    }

    private StringConstant(String str) {
        this.value = str;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public String stringValue() {
        return this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public String toString() {
        return "(String)\"" + this.value + "\"";
    }

    public int hashCode() {
        String str = this.value;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StringConstant stringConstant = (StringConstant) obj;
        String str = this.value;
        if (str == null) {
            return stringConstant.value == null;
        }
        return str.equals(stringConstant.value);
    }
}
