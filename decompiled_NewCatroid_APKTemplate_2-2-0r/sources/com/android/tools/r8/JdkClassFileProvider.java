package com.android.tools.r8;

import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.C3966t0;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.X0;
import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class JdkClassFileProvider implements ClassFileResourceProvider, Closeable {
    static final /* synthetic */ boolean f = true;
    private Origin a;
    private final HashSet b;
    private final HashMap c;
    private URLClassLoader d;
    private FileSystem e;

    private JdkClassFileProvider() {
        this.b = new HashSet();
        this.c = new HashMap();
        this.a = Origin.unknown();
        a(FileSystems.newFileSystem(URI.create("jrt:/"), (Map<String, ?>) Collections.emptyMap()));
    }

    private void a(FileSystem fileSystem) {
        this.e = fileSystem;
        Files.walk(fileSystem.getPath("/modules", new String[0]), new FileVisitOption[0]).forEach(new Consumer() { // from class: com.android.tools.r8.JdkClassFileProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((Path) obj);
            }
        });
    }

    public static ClassFileResourceProvider fromJavaRuntimeJar(Path path) throws IOException {
        return new ArchiveClassFileProvider(path);
    }

    public static ClassFileResourceProvider fromJdkHome(Path path) throws IOException {
        if (Files.exists(path.resolve("lib").resolve("jrt-fs.jar"), new LinkOption[0])) {
            return fromSystemModulesJdk(path);
        }
        Path pathResolve = path.resolve("jre").resolve("lib").resolve("rt.jar");
        if (Files.exists(pathResolve, new LinkOption[0])) {
            return fromJavaRuntimeJar(pathResolve);
        }
        Path pathResolve2 = path.resolve("lib").resolve("rt.jar");
        if (Files.exists(pathResolve2, new LinkOption[0])) {
            return fromJavaRuntimeJar(pathResolve2);
        }
        throw new IOException("Path " + path + " does not look like a Java home");
    }

    public static ClassFileResourceProvider fromSystemJdk() throws IOException {
        return new JdkClassFileProvider();
    }

    public static ClassFileResourceProvider fromSystemModulesJdk(Path path) throws IOException {
        Path pathResolve = path.resolve("lib").resolve("jrt-fs.jar");
        if (Files.exists(pathResolve, new LinkOption[0])) {
            return new JdkClassFileProvider(path);
        }
        throw new NoSuchFileException(pathResolve.toString());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.e.close();
        URLClassLoader uRLClassLoader = this.d;
        if (uRLClassLoader != null) {
            uRLClassLoader.close();
        }
    }

    protected final void finalize() throws Throwable {
        close();
        super.finalize();
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public Set<String> getClassDescriptors() {
        return Collections.unmodifiableSet(this.b);
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public ProgramResource getProgramResource(String str) {
        if (!this.b.contains(str)) {
            return null;
        }
        try {
            return ProgramResource.fromBytes(Origin.unknown(), ProgramResource.Kind.CF, Files.readAllBytes(this.e.getPath("modules", (String) this.c.get(str), C3970u0.s(str))), Collections.singleton(str));
        } catch (IOException unused) {
            throw new C1367Tb("Failed to read '" + str, this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Path path) {
        if (X0.b(path)) {
            C3966t0 c3966t0X = C3970u0.x(path.toString());
            this.c.put(c3966t0X.a(), c3966t0X.b());
            this.b.add(c3966t0X.a());
        }
    }

    private JdkClassFileProvider(Path path) {
        this.b = new HashSet();
        this.c = new HashMap();
        this.a = new PathOrigin(path);
        Path pathResolve = path.resolve("lib").resolve("jrt-fs.jar");
        if (!f && !Files.exists(pathResolve, new LinkOption[0])) {
            throw new AssertionError();
        }
        this.d = new URLClassLoader(new URL[]{pathResolve.toUri().toURL()});
        a(FileSystems.newFileSystem(URI.create("jrt:/"), Collections.emptyMap(), this.d));
    }
}
