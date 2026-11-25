package com.android.tools.r8;

import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.X0;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class DirectoryClassFileProvider implements ClassFileResourceProvider {
    static final /* synthetic */ boolean b = true;
    private final Path a;

    private DirectoryClassFileProvider(Path path) {
        this.a = path;
    }

    private void a(Path path, HashSet hashSet) {
        File[] fileArrListFiles;
        File file = path.toFile();
        if (!file.exists() || (fileArrListFiles = file.listFiles()) == null) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isDirectory()) {
                a(file2.toPath(), hashSet);
            } else {
                Path pathRelativize = this.a.relativize(file2.toPath());
                if (X0.b(pathRelativize)) {
                    hashSet.add(C3970u0.a(pathRelativize));
                }
            }
        }
    }

    public static ClassFileResourceProvider fromDirectory(Path path) {
        return new DirectoryClassFileProvider(path.toAbsolutePath());
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public Set<String> getClassDescriptors() {
        HashSet hashSet = new HashSet();
        a(this.a, hashSet);
        return hashSet;
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public ProgramResource getProgramResource(String str) {
        if (!b && !C3970u0.z(str)) {
            throw new AssertionError();
        }
        Path pathResolve = this.a.resolve(C3970u0.i(str) + ".class");
        if (!Files.exists(pathResolve, new LinkOption[0]) || Files.isDirectory(pathResolve, new LinkOption[0])) {
            return null;
        }
        return ProgramResource.fromFile(ProgramResource.Kind.CF, pathResolve);
    }

    public Path getRoot() {
        return this.a;
    }
}
