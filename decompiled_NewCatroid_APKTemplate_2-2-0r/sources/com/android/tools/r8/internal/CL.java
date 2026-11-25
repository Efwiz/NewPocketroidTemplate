package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.retrace.MappingPartitionFromKeySupplier;
import com.android.tools.r8.retrace.internal.RetracePartitionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class CL {
    private final StringConsumer a;
    private final MappingPartitionFromKeySupplier b;
    private final byte[] c;
    private final DiagnosticsHandler d;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private StringConsumer a;
        private MappingPartitionFromKeySupplier b;
        private byte[] c;
        private DiagnosticsHandler d;

        public a a(StringConsumer stringConsumer) {
            this.a = stringConsumer;
            return this;
        }

        public a a(MappingPartitionFromKeySupplier mappingPartitionFromKeySupplier) {
            this.b = mappingPartitionFromKeySupplier;
            return this;
        }

        public a a(byte[] bArr) {
            this.c = bArr;
            return this;
        }

        public a a(DiagnosticsHandler diagnosticsHandler) {
            this.d = diagnosticsHandler;
            return this;
        }

        public CL a() {
            return new CL(this.a, this.b, this.c, this.d);
        }
    }

    private CL(StringConsumer stringConsumer, MappingPartitionFromKeySupplier mappingPartitionFromKeySupplier, byte[] bArr, DiagnosticsHandler diagnosticsHandler) {
        this.a = stringConsumer;
        this.b = mappingPartitionFromKeySupplier;
        this.c = bArr;
        this.d = diagnosticsHandler;
    }

    public static a a() {
        return new a();
    }

    public void b() throws RetracePartitionException {
        CD cdA = CD.a(this.c, MapVersion.MAP_VERSION_UNKNOWN, this.d);
        if (!cdA.c()) {
            throw new RetracePartitionException("Cannot obtain all partition keys from metadata");
        }
        C3605b c3605bA = C3605b.a().a();
        Iterator<String> it = cdA.a().iterator();
        while (it.hasNext()) {
            JN jn = new JN(new ByteArrayInputStream(this.b.get(it.next())), CM.b(), true);
            try {
                c3605bA = C3605b.a(this.d, (com.android.tools.r8.naming.M) jn, cdA.b(), true, true).a(c3605bA);
            } catch (IOException e) {
                throw new RetracePartitionException(e);
            }
        }
        c3605bA.g().a(new DL(this.a, this.d));
    }
}
