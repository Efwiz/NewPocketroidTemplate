package com.android.tools.r8.retrace;

import com.android.tools.r8.internal.MN;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class ProguardMappingSupplier extends MappingSupplier<ProguardMappingSupplier> {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class Builder extends MappingSupplierBuilder<ProguardMappingSupplier, Builder> {
        public abstract Builder setLoadAllDefinitions(boolean z);

        public abstract Builder setProguardMapProducer(ProguardMapProducer proguardMapProducer);
    }

    public static Builder builder() {
        return new MN();
    }
}
