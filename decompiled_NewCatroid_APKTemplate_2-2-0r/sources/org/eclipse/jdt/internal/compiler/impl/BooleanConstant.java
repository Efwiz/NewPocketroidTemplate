package org.eclipse.jdt.internal.compiler.impl;

import com.gaurav.avnc.vnc.XKeySym;

/* JADX INFO: loaded from: classes14.dex */
public class BooleanConstant extends Constant {
    private boolean value;
    private static final BooleanConstant TRUE = new BooleanConstant(true);
    private static final BooleanConstant FALSE = new BooleanConstant(false);

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public int typeID() {
        return 5;
    }

    public static Constant fromValue(boolean z) {
        return z ? TRUE : FALSE;
    }

    private BooleanConstant(boolean z) {
        this.value = z;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public boolean booleanValue() {
        return this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public String stringValue() {
        return String.valueOf(this.value);
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public String toString() {
        return "(boolean)" + this.value;
    }

    public int hashCode() {
        return this.value ? XKeySym.XK_kana_MA : XKeySym.XK_kana_YU;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        obj.getClass();
        return false;
    }
}
