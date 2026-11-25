package com.android.tools.r8.retrace;

import com.android.tools.r8.internal.AL;
import com.android.tools.r8.naming.MapVersion;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class PartitionMappingSupplier extends MappingSupplier<PartitionMappingSupplier> {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class Builder extends NoMetadataBuilder<Builder> {
        public abstract Builder setMetadata(byte[] bArr);
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class NoMetadataBuilder<B extends NoMetadataBuilder<B>> extends MappingSupplierBuilder<PartitionMappingSupplier, B> {
        public abstract Builder setMappingPartitionFromKeySupplier(MappingPartitionFromKeySupplier mappingPartitionFromKeySupplier);

        public abstract Builder setPrepareMappingPartitionsCallback(PrepareMappingPartitionsCallback prepareMappingPartitionsCallback);

        public abstract Builder setRegisterMappingPartitionCallback(RegisterMappingPartitionCallback registerMappingPartitionCallback);
    }

    public static Builder builder() {
        return new AL(MapVersion.MAP_VERSION_NONE);
    }

    public static NoMetadataBuilder<?> noMetadataBuilder(MapVersion mapVersion) {
        return new AL(mapVersion);
    }
}
