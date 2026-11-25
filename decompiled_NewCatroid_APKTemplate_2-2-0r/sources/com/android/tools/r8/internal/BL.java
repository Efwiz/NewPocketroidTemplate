package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.retrace.InvalidMappingFileException;
import com.android.tools.r8.retrace.MappingPartitionFromKeySupplier;
import com.android.tools.r8.retrace.MappingSupplier;
import com.android.tools.r8.retrace.PartitionMappingSupplier;
import com.android.tools.r8.retrace.PrepareMappingPartitionsCallback;
import com.android.tools.r8.retrace.RegisterMappingPartitionCallback;
import com.android.tools.r8.retrace.Retracer;
import com.android.tools.r8.utils.StringDiagnostic;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BL extends PartitionMappingSupplier {
    private final byte[] a;
    private final RegisterMappingPartitionCallback b;
    private final PrepareMappingPartitionsCallback c;
    private final MappingPartitionFromKeySupplier d;
    private final boolean e;
    private final MapVersion f;
    private C3605b g;
    private final LinkedHashSet h = new LinkedHashSet();
    private final HashSet i = new HashSet();
    private CD j;

    BL(byte[] bArr, RegisterMappingPartitionCallback registerMappingPartitionCallback, PrepareMappingPartitionsCallback prepareMappingPartitionsCallback, MappingPartitionFromKeySupplier mappingPartitionFromKeySupplier, boolean z, MapVersion mapVersion) {
        this.a = bArr;
        this.b = registerMappingPartitionCallback;
        this.c = prepareMappingPartitionsCallback;
        this.d = mappingPartitionFromKeySupplier;
        this.e = z;
        this.f = mapVersion;
    }

    @Override // com.android.tools.r8.retrace.MappingSupplier
    public final Retracer createRetracer(DiagnosticsHandler diagnosticsHandler) {
        CD cdA = this.j;
        if (cdA == null) {
            cdA = CD.a(this.a, this.f, diagnosticsHandler);
            this.j = cdA;
        }
        if (!this.h.isEmpty()) {
            this.c.prepare();
        }
        Iterator it = this.h.iterator();
        while (it.hasNext()) {
            try {
                byte[] bArr = this.d.get((String) it.next());
                if (bArr != null) {
                    this.g = C3605b.a(diagnosticsHandler, (com.android.tools.r8.naming.M) new JN(new ByteArrayInputStream(bArr), CM.b(), true), cdA.b(), true, this.e).a(this.g);
                }
            } catch (IOException e) {
                throw new InvalidMappingFileException(e);
            }
        }
        this.i.addAll(this.h);
        this.h.clear();
        if (this.g == null) {
            this.g = C3605b.a().a();
        }
        return YT.a(ED.a(this.g), diagnosticsHandler);
    }

    @Override // com.android.tools.r8.retrace.MappingSupplier
    public final Set getMapVersions(DiagnosticsHandler diagnosticsHandler) {
        CD cdA = this.j;
        if (cdA == null) {
            cdA = CD.a(this.a, this.f, diagnosticsHandler);
            this.j = cdA;
        }
        return Collections.singleton(cdA.b().toMapVersionMappingInformation());
    }

    @Override // com.android.tools.r8.retrace.MappingSupplier
    public final MappingSupplier registerClassUse(DiagnosticsHandler diagnosticsHandler, ClassReference classReference) {
        CD cdA = this.j;
        if (cdA == null) {
            cdA = CD.a(this.a, this.f, diagnosticsHandler);
            this.j = cdA;
        }
        String strA = cdA.a(classReference);
        if (!this.i.contains(strA) && this.h.add(strA)) {
            this.b.register(strA);
        }
        return this;
    }

    @Override // com.android.tools.r8.retrace.MappingSupplier
    public final void verifyMappingFileHash(DiagnosticsHandler diagnosticsHandler) {
        diagnosticsHandler.error(new StringDiagnostic("Cannot verify map file hash for partitions"));
        throw new RuntimeException("Cannot verify map file hash for partitions");
    }
}
