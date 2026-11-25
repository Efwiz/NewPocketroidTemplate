package com.android.tools.r8.retrace;

import com.android.tools.r8.retrace.MappingSupplier;
import com.android.tools.r8.retrace.MappingSupplierBuilder;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class MappingSupplierBuilder<P extends MappingSupplier<P>, T extends MappingSupplierBuilder<P, T>> {
    public abstract P build();

    public abstract T self();

    public abstract T setAllowExperimental(boolean z);
}
