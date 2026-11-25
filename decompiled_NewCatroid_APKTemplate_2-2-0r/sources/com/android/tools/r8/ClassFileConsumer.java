package com.android.tools.r8;

import com.android.tools.r8.internal.C2091gb;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.I0;
import com.android.tools.r8.utils.P3;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipOutputStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ClassFileConsumer extends ProgramConsumer {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class ArchiveConsumer extends ForwardingConsumer implements DataResourceConsumer, N {
        private final com.android.tools.r8.utils.A c;
        protected final boolean d;

        public ArchiveConsumer(Path path) {
            this(path, null, false);
        }

        public static void writeResourcesForTesting(Path path, List<ProgramResource> list, Set<DataDirectoryResource> set, Set<DataEntryResource> set2) throws ResourceException, IOException {
            OpenOption[] openOptionArr = {StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
            C2091gb c2091gbC = C2091gb.c();
            try {
                ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path, openOptionArr)));
                try {
                    P3.a(list, set, set2, c2091gbC, zipOutputStream);
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

        @Override // com.android.tools.r8.ClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ClassFileConsumer
        public void accept(ByteDataView byteDataView, String str, DiagnosticsHandler diagnosticsHandler) {
            super.accept(byteDataView, str, diagnosticsHandler);
            this.c.a(byteDataView, C3970u0.j(str), diagnosticsHandler);
        }

        @Override // com.android.tools.r8.ClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
        public void finished(DiagnosticsHandler diagnosticsHandler) {
            super.finished(diagnosticsHandler);
            this.c.a(diagnosticsHandler);
        }

        @Override // com.android.tools.r8.ClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer
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

        public ArchiveConsumer(Path path, ClassFileConsumer classFileConsumer) {
            this(path, classFileConsumer, false);
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataDirectoryResource dataDirectoryResource, DiagnosticsHandler diagnosticsHandler) {
            this.c.a(dataDirectoryResource.getName(), diagnosticsHandler);
        }

        public ArchiveConsumer(Path path, ClassFileConsumer classFileConsumer, boolean z) {
            super(classFileConsumer);
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
    public static class DirectoryConsumer extends ForwardingConsumer implements N {
        private final I0 c;
        protected final boolean d;

        public DirectoryConsumer(Path path) {
            this(path, null, false);
        }

        @Override // com.android.tools.r8.ClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ClassFileConsumer
        public void accept(ByteDataView byteDataView, String str, DiagnosticsHandler diagnosticsHandler) {
            super.accept(byteDataView, str, diagnosticsHandler);
            this.c.a(byteDataView, C3970u0.j(str), diagnosticsHandler);
        }

        @Override // com.android.tools.r8.ClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
        public void finished(DiagnosticsHandler diagnosticsHandler) {
            super.finished(diagnosticsHandler);
        }

        @Override // com.android.tools.r8.ClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer
        public DataResourceConsumer getDataResourceConsumer() {
            if (this.d) {
                return this;
            }
            return null;
        }

        @Override // com.android.tools.r8.N
        public Path internalGetOutputPath() {
            return this.c.a();
        }

        public DirectoryConsumer(Path path, boolean z) {
            this(path, null, z);
        }

        public DirectoryConsumer(Path path, ClassFileConsumer classFileConsumer) {
            this(path, classFileConsumer, false);
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataDirectoryResource dataDirectoryResource, DiagnosticsHandler diagnosticsHandler) {
            this.c.a(dataDirectoryResource.getName(), diagnosticsHandler);
        }

        public DirectoryConsumer(Path path, ClassFileConsumer classFileConsumer, boolean z) {
            super(classFileConsumer);
            this.c = new I0(path);
            this.d = z;
        }

        @Override // com.android.tools.r8.DataResourceConsumer
        public void accept(DataEntryResource dataEntryResource, DiagnosticsHandler diagnosticsHandler) {
            this.c.a(dataEntryResource.getName(), dataEntryResource, diagnosticsHandler);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class ForwardingConsumer implements ClassFileConsumer {
        private static final ForwardingConsumer b = new ForwardingConsumer(null);
        private final ClassFileConsumer a;

        public ForwardingConsumer(ClassFileConsumer classFileConsumer) {
            this.a = classFileConsumer;
        }

        @Override // com.android.tools.r8.ClassFileConsumer
        public void accept(ByteDataView byteDataView, String str, DiagnosticsHandler diagnosticsHandler) {
            ClassFileConsumer classFileConsumer = this.a;
            if (classFileConsumer != null) {
                classFileConsumer.accept(byteDataView, str, diagnosticsHandler);
            }
        }

        @Override // com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
        public void finished(DiagnosticsHandler diagnosticsHandler) {
            ClassFileConsumer classFileConsumer = this.a;
            if (classFileConsumer != null) {
                classFileConsumer.finished(diagnosticsHandler);
            }
        }

        @Override // com.android.tools.r8.ProgramConsumer
        public DataResourceConsumer getDataResourceConsumer() {
            ClassFileConsumer classFileConsumer = this.a;
            if (classFileConsumer != null) {
                return classFileConsumer.getDataResourceConsumer();
            }
            return null;
        }
    }

    static ClassFileConsumer emptyConsumer() {
        return ForwardingConsumer.b;
    }

    void accept(ByteDataView byteDataView, String str, DiagnosticsHandler diagnosticsHandler);
}
