package com.android.tools.r8;

import com.android.tools.r8.internal.C2091gb;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.F0;
import com.android.tools.r8.utils.I0;
import com.android.tools.r8.utils.P3;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.X0;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipOutputStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface DexIndexedConsumer extends ProgramConsumer, ByteBufferProvider {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class ArchiveConsumer extends ForwardingConsumer implements DataResourceConsumer, N {
        protected final com.android.tools.r8.utils.A c;
        protected final boolean d;

        public ArchiveConsumer(Path path) {
            this(path, null, false);
        }

        public static void writeResourcesForTesting(Path path, List<ProgramResource> list, Set<DataDirectoryResource> set, Set<DataEntryResource> set2) throws ResourceException, IOException {
            OpenOption[] openOptionArr = {StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
            C2091gb c2091gbC = C2091gb.c();
            try {
                ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path, openOptionArr)));
                for (int i = 0; i < list.size(); i++) {
                    try {
                        P3.a(zipOutputStream, F0.a(i), V5.a((InputStream) c2091gbC.a(list.get(i).getByteStream())), 0);
                    } catch (Throwable th) {
                        try {
                            zipOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                Iterator<DataDirectoryResource> it = set.iterator();
                while (it.hasNext()) {
                    P3.a(zipOutputStream, it.next().getName(), new byte[0], 0);
                }
                for (DataEntryResource dataEntryResource : set2) {
                    P3.a(zipOutputStream, dataEntryResource.getName(), V5.a((InputStream) c2091gbC.a(dataEntryResource.getByteStream())), 0);
                }
                zipOutputStream.close();
                c2091gbC.close();
            } catch (Throwable th3) {
                try {
                    c2091gbC.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
                throw th3;
            }
        }

        @Override // com.android.tools.r8.DexIndexedConsumer.ForwardingConsumer, com.android.tools.r8.DexIndexedConsumer
        public void accept(int i, ByteDataView byteDataView, Set<String> set, DiagnosticsHandler diagnosticsHandler) {
            super.accept(i, byteDataView, set, diagnosticsHandler);
            this.c.a(i, F0.a(i), byteDataView, diagnosticsHandler);
        }

        @Override // com.android.tools.r8.DexIndexedConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
        public void finished(DiagnosticsHandler diagnosticsHandler) {
            super.finished(diagnosticsHandler);
            this.c.a(diagnosticsHandler);
        }

        @Override // com.android.tools.r8.DexIndexedConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer
        public DataResourceConsumer getDataResourceConsumer() {
            if (this.d) {
                return this;
            }
            return null;
        }

        public Origin getOrigin() {
            return this.c.a();
        }

        @Override // com.android.tools.r8.N
        public Path internalGetOutputPath() {
            return this.c.b();
        }

        public ArchiveConsumer(Path path, boolean z) {
            this(path, null, z);
        }

        public ArchiveConsumer(Path path, DexIndexedConsumer dexIndexedConsumer) {
            this(path, dexIndexedConsumer, false);
        }

        public ArchiveConsumer(Path path, DexIndexedConsumer dexIndexedConsumer, boolean z) {
            super(dexIndexedConsumer);
            com.android.tools.r8.utils.A a = new com.android.tools.r8.utils.A(path);
            this.c = a;
            this.d = z;
            a.d();
            if (getDataResourceConsumer() != null) {
                a.d();
            }
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataDirectoryResource dataDirectoryResource, DiagnosticsHandler diagnosticsHandler) {
            this.c.a(dataDirectoryResource.getName(), diagnosticsHandler);
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataEntryResource dataEntryResource, DiagnosticsHandler diagnosticsHandler) {
            this.c.a(dataEntryResource.getName(), dataEntryResource, diagnosticsHandler);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class DirectoryConsumer extends ForwardingConsumer implements DataResourceConsumer, N {
        private final Path c;
        private boolean d;
        private final I0 e;
        protected final boolean f;

        public DirectoryConsumer(Path path) {
            this(path, null, false);
        }

        public static void writeResources(Path path, List<ProgramResource> list) throws ResourceException, IOException {
            Stream<Path> list2 = Files.list(path);
            try {
                for (Path path2 : (List) list2.collect(Collectors.toList())) {
                    if (X0.c(path2)) {
                        Files.delete(path2);
                    }
                }
                list2.close();
                C2091gb c2091gbC = C2091gb.c();
                for (int i = 0; i < list.size(); i++) {
                    try {
                        ProgramResource programResource = list.get(i);
                        Path pathResolve = path.resolve(F0.a(i));
                        byte[] bArrA = V5.a((InputStream) c2091gbC.a(programResource.getByteStream()));
                        Files.createDirectories(pathResolve.getParent(), new FileAttribute[0]);
                        X0.a(pathResolve, (OutputStream) null, bArrA);
                    } catch (Throwable th) {
                        try {
                            c2091gbC.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                c2091gbC.close();
            } catch (Throwable th3) {
                if (list2 != null) {
                    try {
                        list2.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        @Override // com.android.tools.r8.DexIndexedConsumer.ForwardingConsumer, com.android.tools.r8.DexIndexedConsumer
        public void accept(int i, ByteDataView byteDataView, Set<String> set, DiagnosticsHandler diagnosticsHandler) {
            super.accept(i, byteDataView, set, diagnosticsHandler);
            try {
                synchronized (this) {
                    try {
                        if (!this.d) {
                            this.d = true;
                            Stream<Path> list = Files.list(this.c);
                            try {
                                for (Path path : (List) list.collect(Collectors.toList())) {
                                    if (X0.c(path)) {
                                        Files.delete(path);
                                    }
                                }
                                list.close();
                            } catch (Throwable th) {
                                if (list != null) {
                                    try {
                                        list.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                }
            } catch (IOException e) {
                diagnosticsHandler.error(new ExceptionDiagnostic(e, new PathOrigin(this.c)));
            }
            this.e.a(byteDataView, F0.a(i), diagnosticsHandler);
        }

        @Override // com.android.tools.r8.DexIndexedConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
        public void finished(DiagnosticsHandler diagnosticsHandler) {
            super.finished(diagnosticsHandler);
            this.e.getClass();
        }

        @Override // com.android.tools.r8.DexIndexedConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer
        public DataResourceConsumer getDataResourceConsumer() {
            if (this.f) {
                return this;
            }
            return null;
        }

        @Override // com.android.tools.r8.N
        public Path internalGetOutputPath() {
            return this.e.a();
        }

        public DirectoryConsumer(Path path, boolean z) {
            this(path, null, z);
        }

        public DirectoryConsumer(Path path, DexIndexedConsumer dexIndexedConsumer) {
            this(path, dexIndexedConsumer, false);
        }

        public DirectoryConsumer(Path path, DexIndexedConsumer dexIndexedConsumer, boolean z) {
            super(dexIndexedConsumer);
            this.d = false;
            this.c = path;
            this.e = new I0(path);
            this.f = z;
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataDirectoryResource dataDirectoryResource, DiagnosticsHandler diagnosticsHandler) {
            this.e.a(dataDirectoryResource.getName(), diagnosticsHandler);
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataEntryResource dataEntryResource, DiagnosticsHandler diagnosticsHandler) {
            this.e.a(dataEntryResource.getName(), dataEntryResource, diagnosticsHandler);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class ForwardingConsumer implements DexIndexedConsumer {
        private static final ForwardingConsumer b = new ForwardingConsumer(null);
        private final DexIndexedConsumer a;

        public ForwardingConsumer(DexIndexedConsumer dexIndexedConsumer) {
            this.a = dexIndexedConsumer;
        }

        @Override // com.android.tools.r8.DexIndexedConsumer
        public void accept(int i, ByteDataView byteDataView, Set<String> set, DiagnosticsHandler diagnosticsHandler) {
            DexIndexedConsumer dexIndexedConsumer = this.a;
            if (dexIndexedConsumer != null) {
                dexIndexedConsumer.accept(i, byteDataView, set, diagnosticsHandler);
            }
        }

        @Override // com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
        public void finished(DiagnosticsHandler diagnosticsHandler) {
            DexIndexedConsumer dexIndexedConsumer = this.a;
            if (dexIndexedConsumer != null) {
                dexIndexedConsumer.finished(diagnosticsHandler);
            }
        }

        @Override // com.android.tools.r8.ProgramConsumer
        public DataResourceConsumer getDataResourceConsumer() {
            DexIndexedConsumer dexIndexedConsumer = this.a;
            if (dexIndexedConsumer != null) {
                return dexIndexedConsumer.getDataResourceConsumer();
            }
            return null;
        }
    }

    static DexIndexedConsumer emptyConsumer() {
        return ForwardingConsumer.b;
    }

    default void accept(int i, ByteDataView byteDataView, Set<String> set, DiagnosticsHandler diagnosticsHandler) {
        accept(i, byteDataView.copyByteData(), set, diagnosticsHandler);
    }

    default void accept(int i, byte[] bArr, Set<String> set, DiagnosticsHandler diagnosticsHandler) {
        diagnosticsHandler.error(new StringDiagnostic("Deprecated use of DexIndexedConsumer::accept(..., byte[], ...)"));
    }
}
