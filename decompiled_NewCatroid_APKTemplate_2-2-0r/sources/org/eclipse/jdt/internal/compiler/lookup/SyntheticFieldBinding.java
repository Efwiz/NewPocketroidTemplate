package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.impl.Constant;

/* JADX INFO: loaded from: classes14.dex */
public class SyntheticFieldBinding extends FieldBinding {
    public int index;

    public SyntheticFieldBinding(char[] cArr, TypeBinding typeBinding, int i, ReferenceBinding referenceBinding, Constant constant, int i2) {
        super(cArr, typeBinding, i, referenceBinding, constant);
        this.index = i2;
        this.tagBits |= 25769803776L;
    }
}
