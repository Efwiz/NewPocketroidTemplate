package com.android.tools.r8;

import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.ArchiveEntryOrigin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.P3;
import com.android.tools.r8.utils.X0;
import java.io.Closeable;
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

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ArchiveClassFileProvider implements ClassFileResourceProvider, Closeable {
    static final /* synthetic */ boolean f = true;
    private final Path a;
    private final PathOrigin b;
    private final Predicate c;
    private ZipFile d;
    private HashSet e;

    public ArchiveClassFileProvider(Path path) throws IOException {
        this(path, new Predicate() { // from class: com.android.tools.r8.ArchiveClassFileProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ArchiveClassFileProvider.c((String) obj);
            }
        });
    }

    private ZipEntry b(String str) {
        return c().getEntry(AbstractC0402a.a(str, 1, 1) + ".class");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(String str) {
        return true;
    }

    private void d() throws IOException {
        boolean z = f;
        if (!z && this.d != null) {
            throw new AssertionError();
        }
        if (!z && this.e != null) {
            throw new AssertionError();
        }
        try {
            this.d = X0.a(this.a.toFile(), StandardCharsets.UTF_8);
            this.e = new HashSet();
            Enumeration<? extends ZipEntry> enumerationEntries = this.d.entries();
            while (enumerationEntries.hasMoreElements()) {
                String name2 = enumerationEntries.nextElement().getName();
                if (P3.a(name2) && this.c.test(name2)) {
                    this.e.add(C3970u0.y(name2));
                }
            }
        } catch (IOException e) {
            if (!Files.exists(this.a, new LinkOption[0])) {
                throw new NoSuchFileException(this.a.toString());
            }
            throw e;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        ZipFile zipFile = this.d;
        if (zipFile != null) {
            zipFile.close();
        }
        this.d = null;
        this.e = null;
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public void finished(DiagnosticsHandler diagnosticsHandler) throws IOException {
        close();
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public Set<String> getClassDescriptors() {
        c();
        return Collections.unmodifiableSet(this.e);
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public ProgramResource getProgramResource(String str) {
        c();
        if (!Collections.unmodifiableSet(this.e).contains(str)) {
            return null;
        }
        ZipFile zipFileC = c();
        ZipEntry zipEntryB = b(str);
        try {
            InputStream inputStream = zipFileC.getInputStream(zipEntryB);
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

    public ArchiveClassFileProvider(Path path, Predicate<String> predicate) throws IOException {
        this.d = null;
        this.e = null;
        if (!f && !X0.a(path)) {
            throw new AssertionError();
        }
        this.a = path;
        this.c = predicate;
        this.b = new PathOrigin(path);
        c();
    }

    private ZipFile c() {
        if (this.d == null) {
            try {
                d();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.d;
    }
}
