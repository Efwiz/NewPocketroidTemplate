package org.jetbrains.jps.model.java.impl;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;

/* JADX INFO: loaded from: classes14.dex */
public final class JavaSdkUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "org/jetbrains/jps/model/java/impl/JavaSdkUtil";
        } else {
            objArr[0] = "home";
        }
        if (i == 1 || i == 3) {
            objArr[1] = "getJdkClassesRoots";
        } else {
            objArr[1] = "org/jetbrains/jps/model/java/impl/JavaSdkUtil";
        }
        if (i != 1 && i != 3) {
            objArr[2] = "getJdkClassesRoots";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static List<Path> getJdkClassesRoots(Path path, boolean z) {
        Path[] pathArr;
        String canonicalPath;
        if (path == null) {
            $$$reportNull$$$0(2);
        }
        if ("Home".equals(path.getFileName().toString()) && Files.exists(path.resolve("../Classes/classes.jar"), new LinkOption[0])) {
            Path pathResolve = path.resolve("lib");
            pathArr = new Path[]{pathResolve.resolve("endorsed"), pathResolve, path.resolveSibling("Classes"), pathResolve.resolve("ext")};
        } else if (Files.exists(path.resolve("lib/jrt-fs.jar"), new LinkOption[0])) {
            pathArr = new Path[0];
        } else {
            Path pathResolve2 = path.resolve(z ? "lib" : "jre/lib");
            pathArr = new Path[]{pathResolve2.resolve("endorsed"), pathResolve2, pathResolve2.resolve("ext")};
        }
        Set<String> setCreateFilePathSet = CollectionFactory.createFilePathSet();
        ArrayList arrayList = new ArrayList();
        if (Registry.is("project.structure.add.tools.jar.to.new.jdk", false)) {
            Path pathResolve3 = path.resolve("lib/tools.jar");
            if (Files.isRegularFile(pathResolve3, new LinkOption[0])) {
                arrayList.add(pathResolve3);
            }
        }
        for (Path path2 : pathArr) {
            if (path2 != null && Files.isDirectory(path2, new LinkOption[0])) {
                try {
                    DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(path2, "*.jar");
                    try {
                        for (Path path3 : directoryStreamNewDirectoryStream) {
                            String string = path3.getFileName().toString();
                            if (!string.equals("alt-rt.jar") && !string.equals("alt-string.jar") && (canonicalPath = getCanonicalPath(path3)) != null && setCreateFilePathSet.add(canonicalPath)) {
                                arrayList.add(path3);
                            }
                        }
                        if (directoryStreamNewDirectoryStream != null) {
                            directoryStreamNewDirectoryStream.close();
                        }
                    } catch (Throwable th) {
                        if (directoryStreamNewDirectoryStream != null) {
                            try {
                                directoryStreamNewDirectoryStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IOException unused) {
                    continue;
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(path.resolve(z ? "bin" : "jre/bin"));
        try {
            DirectoryStream<Path> directoryStreamNewDirectoryStream2 = Files.newDirectoryStream(path.resolve(z ? "lib" : "jre/lib"), (DirectoryStream.Filter<? super Path>) new DirectoryStream.Filter() { // from class: org.jetbrains.jps.model.java.impl.JavaSdkUtil$$ExternalSyntheticLambda0
                @Override // java.nio.file.DirectoryStream.Filter
                public final boolean accept(Object obj) {
                    return Files.isDirectory((Path) obj, new LinkOption[0]);
                }
            });
            try {
                Iterator<Path> it = directoryStreamNewDirectoryStream2.iterator();
                while (it.hasNext()) {
                    arrayList2.add(it.next());
                }
                if (directoryStreamNewDirectoryStream2 != null) {
                    directoryStreamNewDirectoryStream2.close();
                }
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    try {
                        DirectoryStream<Path> directoryStreamNewDirectoryStream3 = Files.newDirectoryStream((Path) it2.next(), (DirectoryStream.Filter<? super Path>) new DirectoryStream.Filter() { // from class: org.jetbrains.jps.model.java.impl.JavaSdkUtil$$ExternalSyntheticLambda1
                            @Override // java.nio.file.DirectoryStream.Filter
                            public final boolean accept(Object obj) {
                                return JavaSdkUtil.lambda$getJdkClassesRoots$1((Path) obj);
                            }
                        });
                        try {
                            Iterator<Path> it3 = directoryStreamNewDirectoryStream3.iterator();
                            while (it3.hasNext()) {
                                Path pathResolve4 = it3.next().resolve("vm.jar");
                                if (Files.isRegularFile(pathResolve4, new LinkOption[0])) {
                                    arrayList.add(pathResolve4);
                                }
                            }
                            if (directoryStreamNewDirectoryStream3 != null) {
                                directoryStreamNewDirectoryStream3.close();
                            }
                        } catch (Throwable th3) {
                            if (directoryStreamNewDirectoryStream3 != null) {
                                try {
                                    directoryStreamNewDirectoryStream3.close();
                                } catch (Throwable th4) {
                                    th3.addSuppressed(th4);
                                }
                            }
                            throw th3;
                        }
                    } catch (IOException unused2) {
                        continue;
                    }
                }
                Path pathResolve5 = path.resolve("lib/classes.zip");
                if (Files.isRegularFile(pathResolve5, new LinkOption[0])) {
                    arrayList.add(pathResolve5);
                }
                if (arrayList.isEmpty()) {
                    Path pathResolve6 = path.resolve("classes");
                    if (Files.isDirectory(pathResolve6, new LinkOption[0])) {
                        arrayList.add(pathResolve6);
                    }
                }
                return arrayList;
            } catch (Throwable th5) {
                if (directoryStreamNewDirectoryStream2 != null) {
                    try {
                        directoryStreamNewDirectoryStream2.close();
                    } catch (Throwable th6) {
                        th5.addSuppressed(th6);
                    }
                }
                throw th5;
            }
        } catch (IOException unused3) {
        }
    }

    static /* synthetic */ boolean lambda$getJdkClassesRoots$1(Path path) throws IOException {
        return path.getFileName().toString().startsWith("jclSC") && Files.isDirectory(path, new LinkOption[0]);
    }

    private static String getCanonicalPath(Path path) {
        try {
            return path.toRealPath(new LinkOption[0]).toString();
        } catch (IOException unused) {
            return null;
        }
    }
}
