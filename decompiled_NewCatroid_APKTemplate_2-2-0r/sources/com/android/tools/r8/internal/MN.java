package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.MappingSupplier;
import com.android.tools.r8.retrace.MappingSupplierBuilder;
import com.android.tools.r8.retrace.ProguardMapProducer;
import com.android.tools.r8.retrace.ProguardMappingSupplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class MN extends ProguardMappingSupplier.Builder {
    private ProguardMapProducer a;
    private boolean b = false;
    private boolean c = true;

    @Override // com.android.tools.r8.retrace.MappingSupplierBuilder
    public final MappingSupplier build() {
        return new NN(this.a, this.b, this.c);
    }

    @Override // com.android.tools.r8.retrace.MappingSupplierBuilder
    public final MappingSupplierBuilder self() {
        return this;
    }

    @Override // com.android.tools.r8.retrace.MappingSupplierBuilder
    public final MappingSupplierBuilder setAllowExperimental(boolean z) {
        this.b = z;
        return this;
    }

    @Override // com.android.tools.r8.retrace.ProguardMappingSupplier.Builder
    public final ProguardMappingSupplier.Builder setLoadAllDefinitions(boolean z) {
        this.c = z;
        return this;
    }

    @Override // com.android.tools.r8.retrace.ProguardMappingSupplier.Builder
    public final ProguardMappingSupplier.Builder setProguardMapProducer(ProguardMapProducer proguardMapProducer) {
        this.a = proguardMapProducer;
        return this;
    }
}
