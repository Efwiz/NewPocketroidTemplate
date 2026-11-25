package com.android.tools.r8.internal;

import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.retrace.MappingPartitionFromKeySupplier;
import com.android.tools.r8.retrace.MappingSupplier;
import com.android.tools.r8.retrace.MappingSupplierBuilder;
import com.android.tools.r8.retrace.PartitionMappingSupplier;
import com.android.tools.r8.retrace.PrepareMappingPartitionsCallback;
import com.android.tools.r8.retrace.RegisterMappingPartitionCallback;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class AL extends PartitionMappingSupplier.Builder {
    private MappingPartitionFromKeySupplier a;
    private byte[] d;
    private final MapVersion e;
    private RegisterMappingPartitionCallback b = new RegisterMappingPartitionCallback() { // from class: com.android.tools.r8.internal.AL$$ExternalSyntheticLambda0
        @Override // com.android.tools.r8.retrace.RegisterMappingPartitionCallback
        public final void register(String str) {
            AL.a(str);
        }
    };
    private PrepareMappingPartitionsCallback c = new PrepareMappingPartitionsCallback() { // from class: com.android.tools.r8.internal.AL$$ExternalSyntheticLambda1
        @Override // com.android.tools.r8.retrace.PrepareMappingPartitionsCallback
        public final void prepare() {
            AL.a();
        }
    };
    private boolean f = false;

    public AL(MapVersion mapVersion) {
        this.e = mapVersion;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str) {
    }

    @Override // com.android.tools.r8.retrace.MappingSupplierBuilder
    public final MappingSupplier build() {
        MappingPartitionFromKeySupplier mappingPartitionFromKeySupplier = this.a;
        if (mappingPartitionFromKeySupplier != null) {
            return new BL(this.d, this.b, this.c, mappingPartitionFromKeySupplier, this.f, this.e);
        }
        throw new RuntimeException("Cannot build without providing a mapping partition from key supplier.");
    }

    @Override // com.android.tools.r8.retrace.MappingSupplierBuilder
    public final MappingSupplierBuilder self() {
        return this;
    }

    @Override // com.android.tools.r8.retrace.MappingSupplierBuilder
    public final MappingSupplierBuilder setAllowExperimental(boolean z) {
        this.f = z;
        return this;
    }

    @Override // com.android.tools.r8.retrace.PartitionMappingSupplier.NoMetadataBuilder
    public final PartitionMappingSupplier.Builder setMappingPartitionFromKeySupplier(MappingPartitionFromKeySupplier mappingPartitionFromKeySupplier) {
        this.a = mappingPartitionFromKeySupplier;
        return this;
    }

    @Override // com.android.tools.r8.retrace.PartitionMappingSupplier.Builder
    public final PartitionMappingSupplier.Builder setMetadata(byte[] bArr) {
        this.d = bArr;
        return this;
    }

    @Override // com.android.tools.r8.retrace.PartitionMappingSupplier.NoMetadataBuilder
    public final PartitionMappingSupplier.Builder setPrepareMappingPartitionsCallback(PrepareMappingPartitionsCallback prepareMappingPartitionsCallback) {
        this.c = prepareMappingPartitionsCallback;
        return this;
    }

    @Override // com.android.tools.r8.retrace.PartitionMappingSupplier.NoMetadataBuilder
    public final PartitionMappingSupplier.Builder setRegisterMappingPartitionCallback(RegisterMappingPartitionCallback registerMappingPartitionCallback) {
        this.b = registerMappingPartitionCallback;
        return this;
    }
}
