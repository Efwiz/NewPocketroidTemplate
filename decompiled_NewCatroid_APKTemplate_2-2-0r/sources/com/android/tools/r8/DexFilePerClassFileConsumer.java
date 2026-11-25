package com.android.tools.r8;

import com.android.tools.r8.internal.C2091gb;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.I0;
import com.android.tools.r8.utils.P3;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.X0;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipOutputStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface DexFilePerClassFileConsumer extends ProgramConsumer, ByteBufferProvider {
    public static final boolean SHOULD_COMBINE_SYNTHETIC_CLASSES = true;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class ArchiveConsumer extends ForwardingConsumer implements DataResourceConsumer, N {
        static final /* synthetic */ boolean e = true;
        private final com.android.tools.r8.utils.A c;
        protected final boolean d;

        public ArchiveConsumer(Path path) {
            this(path, null, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String a(String str) {
            if (!e && (str == null || !C3970u0.z(str))) {
                throw new AssertionError();
            }
            return C3970u0.i(str) + ".dex";
        }

        public static void writeResourcesForTesting(Path path, List<ProgramResource> list, Map<Resource, String> map) throws Throwable {
            OpenOption[] openOptionArr = {StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
            C2091gb c2091gbC = C2091gb.c();
            try {
                ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path, openOptionArr)));
                try {
                    for (ProgramResource programResource : list) {
                        P3.a(zipOutputStream, a(map.get(programResource)), V5.a((InputStream) c2091gbC.a(programResource.getByteStream())), 0);
                    }
                    zipOutputStream.close();
                    c2091gbC.close();
                } catch (Throwable th) {
                    try {
                        zipOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                try {
                    c2091gbC.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
                throw th3;
            }
        }

        @Override // com.android.tools.r8.DexFilePerClassFileConsumer.ForwardingConsumer, com.android.tools.r8.DexFilePerClassFileConsumer
        public void accept(String str, ByteDataView byteDataView, Set<String> set, DiagnosticsHandler diagnosticsHandler) {
            super.accept(str, byteDataView, set, diagnosticsHandler);
            this.c.a(byteDataView, a(str), diagnosticsHandler);
        }

        @Override // com.android.tools.r8.DexFilePerClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
        public void finished(DiagnosticsHandler diagnosticsHandler) {
            super.finished(diagnosticsHandler);
            this.c.a(diagnosticsHandler);
        }

        @Override // com.android.tools.r8.DexFilePerClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer
        public DataResourceConsumer getDataResourceConsumer() {
            if (this.d) {
                return this;
            }
            return null;
        }

        @Override // com.android.tools.r8.N
        public Path internalGetOutputPath() {
            return this.c.b();
        }

        public ArchiveConsumer(Path path, boolean z) {
            this(path, null, z);
        }

        public ArchiveConsumer(Path path, DexFilePerClassFileConsumer dexFilePerClassFileConsumer) {
            this(path, dexFilePerClassFileConsumer, false);
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataDirectoryResource dataDirectoryResource, DiagnosticsHandler diagnosticsHandler) {
            this.c.a(dataDirectoryResource.getName(), diagnosticsHandler);
        }

        public ArchiveConsumer(Path path, DexFilePerClassFileConsumer dexFilePerClassFileConsumer, boolean z) {
            super(dexFilePerClassFileConsumer);
            com.android.tools.r8.utils.A a = new com.android.tools.r8.utils.A(path);
            this.c = a;
            this.d = z;
            a.d();
            if (getDataResourceConsumer() != null) {
                a.d();
            }
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataEntryResource dataEntryResource, DiagnosticsHandler diagnosticsHandler) {
            this.c.a(dataEntryResource.getName(), dataEntryResource, diagnosticsHandler);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class DirectoryConsumer extends ForwardingConsumer implements DataResourceConsumer, N {
        static final /* synthetic */ boolean d = true;
        private final I0 c;

        public DirectoryConsumer(Path path) {
            this(path, null, false);
        }

        private static String a(String str) {
            if (!d && (str == null || !C3970u0.z(str))) {
                throw new AssertionError();
            }
            return C3970u0.i(str) + ".dex";
        }

        public static void writeResources(Path path, List<ProgramResource> list, Map<Resource, String> map) throws Throwable {
            C2091gb c2091gbC = C2091gb.c();
            try {
                for (ProgramResource programResource : list) {
                    Path pathResolve = path.resolve(ArchiveConsumer.a(map.get(programResource)));
                    byte[] bArrA = V5.a((InputStream) c2091gbC.a(programResource.getByteStream()));
                    Files.createDirectories(pathResolve.getParent(), new FileAttribute[0]);
                    X0.a(pathResolve, (OutputStream) null, bArrA);
                }
                c2091gbC.close();
            } catch (Throwable th) {
                try {
                    c2091gbC.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }

        @Override // com.android.tools.r8.DexFilePerClassFileConsumer.ForwardingConsumer, com.android.tools.r8.DexFilePerClassFileConsumer
        public void accept(String str, ByteDataView byteDataView, Set<String> set, DiagnosticsHandler diagnosticsHandler) {
            super.accept(str, byteDataView, set, diagnosticsHandler);
            this.c.a(byteDataView, a(str), diagnosticsHandler);
        }

        @Override // com.android.tools.r8.DexFilePerClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
        public void finished(DiagnosticsHandler diagnosticsHandler) {
            super.finished(diagnosticsHandler);
        }

        @Override // com.android.tools.r8.N
        public Path internalGetOutputPath() {
            return this.c.a();
        }

        public DirectoryConsumer(Path path, boolean z) {
            this(path, null, z);
        }

        public DirectoryConsumer(Path path, DexFilePerClassFileConsumer dexFilePerClassFileConsumer) {
            this(path, dexFilePerClassFileConsumer, false);
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataDirectoryResource dataDirectoryResource, DiagnosticsHandler diagnosticsHandler) {
            this.c.a(dataDirectoryResource.getName(), diagnosticsHandler);
        }

        public DirectoryConsumer(Path path, DexFilePerClassFileConsumer dexFilePerClassFileConsumer, boolean z) {
            super(dexFilePerClassFileConsumer);
            this.c = new I0(path);
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataEntryResource dataEntryResource, DiagnosticsHandler diagnosticsHandler) {
            this.c.a(dataEntryResource.getName(), dataEntryResource, diagnosticsHandler);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class ForwardingConsumer implements DexFilePerClassFileConsumer {
        private static final ForwardingConsumer b = new ForwardingConsumer(null);
        private final DexFilePerClassFileConsumer a;

        public ForwardingConsumer(DexFilePerClassFileConsumer dexFilePerClassFileConsumer) {
            this.a = dexFilePerClassFileConsumer;
        }

        @Override // com.android.tools.r8.DexFilePerClassFileConsumer
        public void accept(String str, ByteDataView byteDataView, Set<String> set, DiagnosticsHandler diagnosticsHandler) {
            DexFilePerClassFileConsumer dexFilePerClassFileConsumer = this.a;
            if (dexFilePerClassFileConsumer != null) {
                dexFilePerClassFileConsumer.accept(str, byteDataView, set, diagnosticsHandler);
            }
        }

        @Override // com.android.tools.r8.DexFilePerClassFileConsumer
        public boolean combineSyntheticClassesWithPrimaryClass() {
            DexFilePerClassFileConsumer dexFilePerClassFileConsumer = this.a;
            if (dexFilePerClassFileConsumer == null) {
                return true;
            }
            return dexFilePerClassFileConsumer.combineSyntheticClassesWithPrimaryClass();
        }

        @Override // com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
        public void finished(DiagnosticsHandler diagnosticsHandler) {
            DexFilePerClassFileConsumer dexFilePerClassFileConsumer = this.a;
            if (dexFilePerClassFileConsumer != null) {
                dexFilePerClassFileConsumer.finished(diagnosticsHandler);
            }
        }

        @Override // com.android.tools.r8.ProgramConsumer
        public DataResourceConsumer getDataResourceConsumer() {
            DexFilePerClassFileConsumer dexFilePerClassFileConsumer = this.a;
            if (dexFilePerClassFileConsumer != null) {
                return dexFilePerClassFileConsumer.getDataResourceConsumer();
            }
            return null;
        }
    }

    static DexFilePerClassFileConsumer emptyConsumer() {
        return ForwardingConsumer.b;
    }

    default void accept(String str, ByteDataView byteDataView, Set<String> set, DiagnosticsHandler diagnosticsHandler) {
        accept(str, byteDataView.copyByteData(), set, diagnosticsHandler);
    }

    default boolean combineSyntheticClassesWithPrimaryClass() {
        return true;
    }

    default void accept(String str, byte[] bArr, Set<String> set, DiagnosticsHandler diagnosticsHandler) {
        diagnosticsHandler.error(new StringDiagnostic("Deprecated use of DexFilePerClassFileConsumer::accept(..., byte[], ...)"));
    }
}
