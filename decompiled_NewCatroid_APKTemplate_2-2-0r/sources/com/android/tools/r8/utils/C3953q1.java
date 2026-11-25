package com.android.tools.r8.utils;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.ClassFileResourceProvider;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.ArchiveEntryOrigin;
import com.android.tools.r8.origin.PathOrigin;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: renamed from: com.android.tools.r8.utils.q1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
class C3953q1 implements ClassFileResourceProvider, AutoCloseable {
    static final /* synthetic */ boolean e = true;
    private final Path a;
    private final PathOrigin b;
    private final HashSet c;
    private ZipFile d;

    public C3953q1(Path path) {
        this(path, new Predicate() { // from class: com.android.tools.r8.utils.q1$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3953q1.c((String) obj);
            }
        });
    }

    private ZipEntry b(String str) throws IOException {
        return c().getEntry(AbstractC0402a.a(str, 1, 1) + ".class");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(String str) {
        return true;
    }

    @Override // java.lang.AutoCloseable
    public final void close() throws IOException {
        ZipFile zipFile = this.d;
        if (zipFile != null) {
            zipFile.close();
            this.d = null;
        }
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final void finished(DiagnosticsHandler diagnosticsHandler) throws IOException {
        close();
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final Set getClassDescriptors() {
        return Collections.unmodifiableSet(this.c);
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final ProgramResource getProgramResource(String str) {
        if (!this.c.contains(str)) {
            return null;
        }
        try {
            ZipEntry zipEntryB = b(str);
            InputStream inputStream = c().getInputStream(zipEntryB);
            try {
                ProgramResource programResourceFromBytes = ProgramResource.fromBytes(new ArchiveEntryOrigin(zipEntryB.getName(), this.b), ProgramResource.Kind.CF, V5.a(inputStream), Collections.singleton(str));
                inputStream.close();
                return programResourceFromBytes;
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
        } catch (IOException unused) {
            throw new C1367Tb("Failed to read '" + str, this.b);
        }
    }

    public C3953q1(Path path, Predicate predicate) {
        this.c = new HashSet();
        this.d = null;
        if (!e && !X0.a(path)) {
            throw new AssertionError();
        }
        this.a = path;
        this.b = new PathOrigin(path);
        Enumeration<? extends ZipEntry> enumerationEntries = c().entries();
        while (enumerationEntries.hasMoreElements()) {
            String name2 = enumerationEntries.nextElement().getName();
            if (P3.a(name2) && predicate.test(name2)) {
                this.c.add(C3970u0.y(name2));
            }
        }
    }

    private ZipFile c() throws IOException {
        if (this.d == null) {
            try {
                this.d = X0.a(this.a.toFile(), StandardCharsets.UTF_8);
            } catch (IOException e2) {
                if (Files.exists(this.a, new LinkOption[0])) {
                    throw e2;
                }
                throw new NoSuchFileException(this.a.toString());
            }
        }
        return this.d;
    }
}
