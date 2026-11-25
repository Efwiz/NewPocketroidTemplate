package com.android.tools.r8;

import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.ArchiveEntryOrigin;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.P3;
import com.android.tools.r8.utils.X0;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ArchiveProgramResourceProvider implements ProgramResourceProvider {
    static final /* synthetic */ boolean d = true;
    private final Origin a;
    private final ZipFileSupplier b;
    private final Predicate c;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface ZipFileSupplier {
        ZipFile open() throws IOException;
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    interface a {
        void a(ArchiveEntryOrigin archiveEntryOrigin, InputStream inputStream) throws IOException;
    }

    private ArchiveProgramResourceProvider(Origin origin, ZipFileSupplier zipFileSupplier, Predicate predicate) {
        boolean z = d;
        if (!z && origin == null) {
            throw new AssertionError();
        }
        if (!z && zipFileSupplier == null) {
            throw new AssertionError();
        }
        if (!z && predicate == null) {
            throw new AssertionError();
        }
        this.a = origin;
        this.b = zipFileSupplier;
        this.c = predicate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ZipFile a(Path path) {
        return X0.a(path.toFile(), StandardCharsets.UTF_8);
    }

    public static ArchiveProgramResourceProvider fromArchive(Path path) {
        return fromArchive(path, new ArchiveProgramResourceProvider$$ExternalSyntheticLambda1());
    }

    public static ArchiveProgramResourceProvider fromSupplier(Origin origin, ZipFileSupplier zipFileSupplier) {
        return fromSupplier(origin, zipFileSupplier, new ArchiveProgramResourceProvider$$ExternalSyntheticLambda1());
    }

    public static boolean includeClassFileEntries(String str) {
        return P3.a(str);
    }

    public static boolean includeClassFileOrDexEntries(String str) {
        return P3.a(str) || str.toLowerCase().endsWith(".dex");
    }

    public static boolean includeDexEntries(String str) {
        int i = P3.b;
        return str.toLowerCase().endsWith(".dex");
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public Collection<ProgramResource> getProgramResources() throws ResourceException {
        try {
            final ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            a(new a() { // from class: com.android.tools.r8.ArchiveProgramResourceProvider$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.ArchiveProgramResourceProvider.a
                public final void a(ArchiveEntryOrigin archiveEntryOrigin, InputStream inputStream) {
                    this.f$0.a(arrayList, arrayList2, archiveEntryOrigin, inputStream);
                }
            });
            if (!arrayList.isEmpty() && !arrayList2.isEmpty()) {
                throw new C1367Tb(this.a, "Cannot create android app from an archive containing both DEX and Java-bytecode content.", null);
            }
            return !arrayList.isEmpty() ? arrayList : arrayList2;
        } catch (IOException e) {
            throw new ResourceException(this.a, e);
        }
    }

    public static ArchiveProgramResourceProvider fromArchive(final Path path, Predicate<String> predicate) {
        return fromSupplier(new PathOrigin(path), new ZipFileSupplier() { // from class: com.android.tools.r8.ArchiveProgramResourceProvider$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.ArchiveProgramResourceProvider.ZipFileSupplier
            public final ZipFile open() {
                return ArchiveProgramResourceProvider.a(path);
            }
        }, predicate);
    }

    public static ArchiveProgramResourceProvider fromSupplier(Origin origin, ZipFileSupplier zipFileSupplier, Predicate<String> predicate) {
        return new ArchiveProgramResourceProvider(origin, zipFileSupplier, predicate);
    }

    void a(a aVar) throws IOException {
        try {
            ZipFile zipFileOpen = this.b.open();
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFileOpen.entries();
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                    InputStream inputStream = zipFileOpen.getInputStream(zipEntryNextElement);
                    try {
                        aVar.a(new ArchiveEntryOrigin(zipEntryNextElement.getName(), this.a), inputStream);
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
                }
                zipFileOpen.close();
            } catch (Throwable th3) {
                if (zipFileOpen != null) {
                    try {
                        zipFileOpen.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        } catch (ZipException e) {
            throw new C1367Tb(this.a, "Zip error while reading archive" + e.getMessage(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, List list2, ArchiveEntryOrigin archiveEntryOrigin, InputStream inputStream) {
        String entryName = archiveEntryOrigin.getEntryName();
        if (this.c.test(entryName)) {
            if (P3.b(entryName)) {
                list.add(ProgramResource.fromBytes(archiveEntryOrigin, ProgramResource.Kind.DEX, V5.a(inputStream), null));
            } else if (P3.a(entryName)) {
                list2.add(ProgramResource.fromBytes(archiveEntryOrigin, ProgramResource.Kind.CF, V5.a(inputStream), Collections.singleton(C3970u0.y(entryName))));
            }
        }
    }
}
