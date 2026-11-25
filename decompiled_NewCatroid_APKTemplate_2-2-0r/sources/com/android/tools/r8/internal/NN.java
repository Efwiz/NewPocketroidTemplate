package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.naming.C3667v0;
import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.retrace.InvalidMappingFileException;
import com.android.tools.r8.retrace.MappingSupplier;
import com.android.tools.r8.retrace.ProguardMapProducer;
import com.android.tools.r8.retrace.ProguardMappingSupplier;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class NN extends ProguardMappingSupplier {
    static final /* synthetic */ boolean g = true;
    private ProguardMapProducer a;
    private final boolean b;
    private final boolean c;
    private C3605b d;
    private final HashSet e = new HashSet();
    private final HashSet f = new HashSet();

    NN(ProguardMapProducer proguardMapProducer, boolean z, boolean z2) {
        this.a = proguardMapProducer;
        this.b = z;
        this.c = z2;
    }

    @Override // com.android.tools.r8.retrace.MappingSupplier
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final YT createRetracer(DiagnosticsHandler diagnosticsHandler) {
        NN$$ExternalSyntheticLambda0 nN$$ExternalSyntheticLambda0;
        MapVersion mapVersionQ;
        if (this.a == null) {
            if (g || this.d != null) {
                return YT.a(ED.a(this.d), diagnosticsHandler);
            }
            throw new AssertionError();
        }
        if (this.d == null || !this.e.isEmpty()) {
            try {
                if (this.c) {
                    nN$$ExternalSyntheticLambda0 = null;
                } else {
                    HashSet hashSet = this.e;
                    Objects.requireNonNull(hashSet);
                    nN$$ExternalSyntheticLambda0 = new NN$$ExternalSyntheticLambda0(hashSet);
                }
                boolean z = this.d == null;
                com.android.tools.r8.naming.M kn = this.a.isFileBacked() ? new KN(this.a.getPath(), nN$$ExternalSyntheticLambda0, z) : new JN(this.a.get(), nN$$ExternalSyntheticLambda0, z);
                C3605b c3605b = this.d;
                if (c3605b == null) {
                    mapVersionQ = MapVersion.MAP_VERSION_NONE;
                } else {
                    com.android.tools.r8.naming.mappinginformation.b bVarC = c3605b.c();
                    mapVersionQ = bVarC == null ? MapVersion.MAP_VERSION_UNKNOWN : bVarC.q();
                }
                this.d = C3605b.a(diagnosticsHandler, kn, mapVersionQ, true, this.b).a(this.d);
                this.f.addAll(this.e);
                this.e.clear();
            } catch (Exception e) {
                throw new InvalidMappingFileException(e);
            }
        }
        if (this.c) {
            this.a = null;
        }
        return YT.a(ED.a(this.d), diagnosticsHandler);
    }

    @Override // com.android.tools.r8.retrace.MappingSupplier
    public final Set getMapVersions(DiagnosticsHandler diagnosticsHandler) {
        if (this.d == null) {
            createRetracer(diagnosticsHandler);
        }
        if (g || this.d != null) {
            return this.d.d();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.retrace.MappingSupplier
    public final MappingSupplier registerClassUse(DiagnosticsHandler diagnosticsHandler, ClassReference classReference) {
        String typeName = classReference.getTypeName();
        if (!this.c && !this.f.contains(typeName)) {
            this.e.add(classReference.getTypeName());
        }
        return this;
    }

    @Override // com.android.tools.r8.retrace.MappingSupplier
    public final void verifyMappingFileHash(DiagnosticsHandler diagnosticsHandler) {
        try {
            InputStream inputStream = this.a.get();
            try {
                C3667v0.a aVarA = C3667v0.a(AbstractC2286j9.a(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
                if (aVarA.c()) {
                    diagnosticsHandler.error(new StringDiagnostic(aVarA.b()));
                    throw new RuntimeException(aVarA.b());
                }
                if (!aVarA.d()) {
                    diagnosticsHandler.warning(new StringDiagnostic(aVarA.b()));
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            diagnosticsHandler.error(new ExceptionDiagnostic(e));
            throw new RuntimeException(e);
        }
    }
}
