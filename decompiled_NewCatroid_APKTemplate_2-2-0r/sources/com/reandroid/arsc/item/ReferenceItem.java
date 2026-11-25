package com.reandroid.arsc.item;

import com.reandroid.arsc.base.Block;

/* JADX INFO: loaded from: classes10.dex */
public interface ReferenceItem extends IntegerReference {
    <T1 extends Block> T1 getReferredParent(Class<T1> cls);
}
