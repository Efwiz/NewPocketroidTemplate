package com.android.tools.r8.utils;

import com.android.tools.r8.DataDirectoryResource;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.ArchiveEntryOrigin;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.position.Position;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class ArchiveResourceProvider implements ProgramResourceProvider, DataResourceProvider {
    static final /* synthetic */ boolean d = true;
    private final PathOrigin a;
    private final com.android.tools.r8.shaking.J0 b;
    private final boolean c;

    ArchiveResourceProvider(com.android.tools.r8.shaking.J0 j0, boolean z) {
        if (!d && !X0.a(j0.b())) {
            throw new AssertionError();
        }
        this.a = new PathOrigin(j0.b());
        this.b = j0;
        this.c = z;
    }

    private ArrayList a() throws IOException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            ZipFile zipFileA = X0.a(this.b.b().toFile(), StandardCharsets.UTF_8);
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFileA.entries();
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                    InputStream inputStream = zipFileA.getInputStream(zipEntryNextElement);
                    try {
                        String name2 = zipEntryNextElement.getName();
                        ArchiveEntryOrigin archiveEntryOrigin = new ArchiveEntryOrigin(name2, this.a);
                        if (this.b.a(name2)) {
                            if (P3.b(name2)) {
                                if (!this.c) {
                                    arrayList.add(C3909i2.a(archiveEntryOrigin, ProgramResource.Kind.DEX, V5.a(inputStream), null));
                                }
                            } else if (P3.a(name2)) {
                                arrayList2.add(C3909i2.a(archiveEntryOrigin, ProgramResource.Kind.CF, V5.a(inputStream), Collections.singleton(C3970u0.y(name2))));
                            }
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
                }
                zipFileA.close();
                if (arrayList.isEmpty() || arrayList2.isEmpty()) {
                    return !arrayList.isEmpty() ? arrayList : arrayList2;
                }
                throw new C1367Tb("Cannot create android app from an archive '" + this.b + "' containing both DEX and Java-bytecode content");
            } catch (Throwable th3) {
                try {
                    zipFileA.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
                throw th3;
            }
        } catch (ZipException e) {
            throw new C1367Tb("Zip error while reading '" + this.b + "': " + e.getMessage(), e);
        }
    }

    public static ArchiveResourceProvider fromArchive(Path path, boolean z) {
        return new ArchiveResourceProvider(new com.android.tools.r8.shaking.J0(path, AbstractC1639am.i(), Origin.unknown(), Position.UNKNOWN), z);
    }

    @Override // com.android.tools.r8.DataResourceProvider
    public void accept(DataResourceProvider.Visitor visitor) throws ResourceException {
        try {
            ZipFile zipFileA = X0.a(this.b.b().toFile(), StandardCharsets.UTF_8);
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFileA.entries();
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                    String name2 = zipEntryNextElement.getName();
                    if (this.b.a(name2) && !P3.a(name2) && (!name2.toLowerCase().endsWith(".dex") || this.c)) {
                        if (zipEntryNextElement.isDirectory()) {
                            visitor.visit(DataDirectoryResource.fromZip(zipFileA, zipEntryNextElement));
                        } else {
                            visitor.visit(DataEntryResource.fromZip(zipFileA, zipEntryNextElement));
                        }
                    }
                }
                zipFileA.close();
            } catch (Throwable th) {
                try {
                    zipFileA.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (ZipException e) {
            throw new ResourceException(this.a, new C1367Tb("Zip error while reading '" + this.b + "': " + e.getMessage(), e));
        } catch (IOException e2) {
            throw new ResourceException(this.a, new C1367Tb("I/O exception while reading '" + this.b + "': " + e2.getMessage(), e2));
        }
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public DataResourceProvider getDataResourceProvider() {
        return this;
    }

    public Origin getOrigin() {
        return this.a;
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public Collection<ProgramResource> getProgramResources() throws ResourceException {
        try {
            return a();
        } catch (IOException e) {
            throw new ResourceException(this.a, e);
        }
    }

    public void accept(Consumer<ProgramResource> consumer) throws ResourceException {
        try {
            ZipFile zipFileA = X0.a(this.b.b().toFile(), StandardCharsets.UTF_8);
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFileA.entries();
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                    String name2 = zipEntryNextElement.getName();
                    if (this.b.a(name2) && (P3.a(name2) || (name2.toLowerCase().endsWith(".dex") && !this.c))) {
                        ArchiveEntryOrigin archiveEntryOrigin = new ArchiveEntryOrigin(name2, this.a);
                        InputStream inputStream = zipFileA.getInputStream(zipEntryNextElement);
                        try {
                            if (P3.b(name2)) {
                                consumer.accept(C3909i2.a(archiveEntryOrigin, ProgramResource.Kind.DEX, V5.a(inputStream), null));
                            } else if (P3.a(name2)) {
                                consumer.accept(C3909i2.a(archiveEntryOrigin, ProgramResource.Kind.CF, V5.a(inputStream), Collections.singleton(C3970u0.y(name2))));
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
                    }
                }
                zipFileA.close();
            } catch (Throwable th3) {
                try {
                    zipFileA.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
                throw th3;
            }
        } catch (ZipException e) {
            throw new ResourceException(this.a, new C1367Tb("Zip error while reading '" + this.b + "': " + e.getMessage(), e));
        } catch (IOException e2) {
            throw new ResourceException(this.a, new C1367Tb("I/O exception while reading '" + this.b + "': " + e2.getMessage(), e2));
        }
    }
}
