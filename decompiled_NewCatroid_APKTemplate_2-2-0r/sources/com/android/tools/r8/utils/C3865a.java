package com.android.tools.r8.utils;

import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.ArchiveEntryOrigin;
import com.android.tools.r8.origin.PathOrigin;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/* JADX INFO: renamed from: com.android.tools.r8.utils.a, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3865a implements ProgramResourceProvider {
    static final /* synthetic */ boolean c = true;
    private final ArchiveEntryOrigin a;
    private final Path b;

    C3865a(Path path) {
        if (!c && !X0.a(path)) {
            throw new AssertionError();
        }
        this.a = new ArchiveEntryOrigin("classes.jar", new PathOrigin(path));
        this.b = path;
    }

    /* JADX WARN: Code duplicated, block: B:53:0x007f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private List a() throws IOException {
        ArrayList arrayList;
        try {
            ZipFile zipFileA = X0.a(this.b.toFile(), StandardCharsets.UTF_8);
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFileA.entries();
                while (true) {
                    if (!enumerationEntries.hasMoreElements()) {
                        arrayList = null;
                        break;
                    }
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                    InputStream inputStream = zipFileA.getInputStream(zipEntryNextElement);
                    try {
                        if (zipEntryNextElement.getName().equals("classes.jar")) {
                            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
                            try {
                                arrayList = new ArrayList();
                                while (true) {
                                    ZipEntry nextEntry = zipInputStream.getNextEntry();
                                    if (nextEntry == null) {
                                        break;
                                    }
                                    String name2 = nextEntry.getName();
                                    if (P3.a(name2)) {
                                        arrayList.add(C3909i2.a(new ArchiveEntryOrigin(name2, this.a), ProgramResource.Kind.CF, V5.a(zipInputStream), Collections.singleton(C3970u0.y(name2))));
                                    }
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (Throwable th) {
                                            th.addSuppressed(th);
                                        }
                                    }
                                    throw th;
                                }
                                zipInputStream.close();
                                if (inputStream == null) {
                                    break;
                                }
                                inputStream.close();
                                break;
                            } catch (Throwable th2) {
                                try {
                                    zipInputStream.close();
                                } catch (Throwable th3) {
                                    th2.addSuppressed(th3);
                                }
                                throw th2;
                            }
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (Throwable th4) {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        throw th4;
                    }
                    try {
                        zipFileA.close();
                    } catch (Throwable th5) {
                        th.addSuppressed(th5);
                    }
                    throw th;
                }
                zipFileA.close();
                return arrayList == null ? Collections.emptyList() : arrayList;
            } catch (Throwable th6) {
                zipFileA.close();
                throw th6;
            }
        } catch (ZipException e) {
            throw new C1367Tb("Zip error while reading '" + this.b + "': " + e.getMessage(), e);
        }
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final DataResourceProvider getDataResourceProvider() {
        return null;
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final Collection getProgramResources() throws ResourceException {
        try {
            return a();
        } catch (IOException e) {
            throw new ResourceException(this.a, e);
        }
    }
}
