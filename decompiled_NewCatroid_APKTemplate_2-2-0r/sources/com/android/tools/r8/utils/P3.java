package com.android.tools.r8.utils;

import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.DataDirectoryResource;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.internal.AbstractC1660b2;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C2091gb;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.references.ClassReference;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class P3 {
    static final /* synthetic */ boolean a = true;
    public static final /* synthetic */ int b = 0;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface a {
        void a(ZipEntry zipEntry, InputStream inputStream) throws IOException;
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b {
        private final Path a;
        private final ZipOutputStream b;

        private b(Path path) {
            this.a = path;
            this.b = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path, new OpenOption[0])));
        }

        public static b a(Path path) throws IOException {
            return new b(path);
        }

        public ZipOutputStream b() {
            return this.b;
        }

        public b a(String str, Path path) throws IOException {
            this.b.putNextEntry(new ZipEntry(str));
            Files.copy(path, this.b);
            this.b.closeEntry();
            return this;
        }

        public b a(Path path, Collection<Path> collection) throws IOException {
            for (Path path2 : collection) {
                this.b.putNextEntry(new ZipEntry((String) StreamSupport.stream(Spliterators.spliteratorUnknownSize(path.relativize(path2).iterator(), 16), false).map(new P3$$ExternalSyntheticLambda4()).collect(Collectors.joining("/"))));
                Files.copy(path2, this.b);
                this.b.closeEntry();
            }
            return this;
        }

        public b a(Path path, Path... pathArr) throws IOException {
            return a(path, Arrays.asList(pathArr));
        }

        public b a(String str, byte[] bArr) throws IOException {
            this.b.putNextEntry(new ZipEntry(str));
            this.b.write(bArr);
            this.b.closeEntry();
            return this;
        }

        public b a(String str, String str2) throws IOException {
            this.b.putNextEntry(new ZipEntry(str));
            this.b.write(str2.getBytes(StandardCharsets.UTF_8));
            this.b.closeEntry();
            return this;
        }

        public Path a() throws IOException {
            this.b.close();
            return this.a;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(ZipEntry zipEntry) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(ZipEntry zipEntry) {
        return true;
    }

    public static void a(List list, Set set, Set set2, C2091gb c2091gb, ZipOutputStream zipOutputStream) throws IOException {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            a(zipOutputStream, ((DataDirectoryResource) it.next()).getName(), new byte[0], 8);
        }
        Iterator it2 = set2.iterator();
        while (it2.hasNext()) {
            DataEntryResource dataEntryResource = (DataEntryResource) it2.next();
            String name2 = dataEntryResource.getName();
            byte[] bArrA = V5.a((InputStream) c2091gb.a(dataEntryResource.getByteStream()));
            int i = AbstractC1660b2.c;
            a(zipOutputStream, name2, bArrA, "resources/new_api_database.ser".equals(name2) ? 0 : 8);
        }
        Iterator it3 = list.iterator();
        while (it3.hasNext()) {
            ProgramResource programResource = (ProgramResource) it3.next();
            if (!a && programResource.getClassDescriptors().size() != 1) {
                throw new AssertionError();
            }
            a(zipOutputStream, C3970u0.j(programResource.getClassDescriptors().iterator().next()), V5.a((InputStream) c2091gb.a(programResource.getByteStream())), 8);
        }
    }

    public static void b(Path path, Path path2) throws IOException {
        List list = (List) Files.walk(path2, new FileVisitOption[0]).filter(new Predicate() { // from class: com.android.tools.r8.utils.P3$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return P3.a((Path) obj);
            }
        }).collect(Collectors.toList());
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path, new OpenOption[0])));
        try {
            a(zipOutputStream, path2, list);
            zipOutputStream.close();
        } catch (Throwable th) {
            try {
                zipOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static List<Path> b(Path path, Path path2, Predicate<ZipEntry> predicate) throws IOException {
        return a(path, path2, predicate, Function.identity());
    }

    public static boolean b(String str) {
        return str.toLowerCase().endsWith(".dex");
    }

    public static void a(String str, a aVar) throws IOException {
        a(Paths.get(str, new String[0]), aVar);
    }

    public static void a(Path path, a aVar) throws IOException {
        ZipFile zipFile = new ZipFile(path.toFile(), StandardCharsets.UTF_8);
        try {
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
            while (enumerationEntries.hasMoreElements()) {
                ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                InputStream inputStream = zipFile.getInputStream(zipEntryNextElement);
                try {
                    aVar.a(zipEntryNextElement, inputStream);
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
            zipFile.close();
        } catch (Throwable th3) {
            try {
                zipFile.close();
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
            }
            throw th3;
        }
    }

    public static Path a(Path path, Path path2, final BiFunction<ZipEntry, byte[], byte[]> biFunction) throws IOException {
        final b bVarA = b.a(path2);
        a(path, new a() { // from class: com.android.tools.r8.utils.P3$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.P3.a
            public final void a(ZipEntry zipEntry, InputStream inputStream) throws IOException {
                P3.a(bVarA, biFunction, zipEntry, inputStream);
            }
        });
        return bVarA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(b bVar, BiFunction biFunction, ZipEntry zipEntry, InputStream inputStream) throws IOException {
        bVar.a(zipEntry.getName(), (byte[]) biFunction.apply(zipEntry, V5.a(inputStream)));
    }

    public static Path a(Path path, Path path2, final Predicate<ZipEntry> predicate) throws IOException {
        final b bVarA = b.a(path2);
        a(path, new a() { // from class: com.android.tools.r8.utils.P3$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.P3.a
            public final void a(ZipEntry zipEntry, InputStream inputStream) throws IOException {
                P3.a(predicate, bVarA, zipEntry, inputStream);
            }
        });
        return bVarA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, b bVar, ZipEntry zipEntry, InputStream inputStream) throws IOException {
        if (predicate.test(zipEntry)) {
            bVar.a(zipEntry.getName(), V5.a(inputStream));
        }
    }

    public static byte[] a(Path path, String str) throws IOException {
        ZipFile zipFile = new ZipFile(path.toFile(), StandardCharsets.UTF_8);
        try {
            byte[] bArrA = V5.a(zipFile.getInputStream(zipFile.getEntry(str)));
            zipFile.close();
            return bArrA;
        } catch (Throwable th) {
            try {
                zipFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Path path) {
        return !Files.isDirectory(path, new LinkOption[0]);
    }

    public static void a(ZipOutputStream zipOutputStream, Path path, List list) throws IOException {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Path path2 = (Path) it.next();
            zipOutputStream.putNextEntry(new ZipEntry((String) StreamSupport.stream(Spliterators.spliteratorUnknownSize(path.relativize(path2).iterator(), 16), false).map(new P3$$ExternalSyntheticLambda4()).collect(Collectors.joining("/"))));
            Files.copy(path2, zipOutputStream);
            zipOutputStream.closeEntry();
        }
    }

    public static List<Path> a(Path path, Path path2) throws IOException {
        return a(path, path2, new Predicate() { // from class: com.android.tools.r8.utils.P3$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return P3.a((ZipEntry) obj);
            }
        }, Function.identity());
    }

    public static List<File> a(String str, File file) throws IOException {
        return a(Paths.get(str, new String[0]), file.toPath(), new Predicate() { // from class: com.android.tools.r8.utils.P3$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return P3.b((ZipEntry) obj);
            }
        }, new P3$$ExternalSyntheticLambda6());
    }

    public static ArrayList a(Path path, final Path path2, final Predicate predicate, final Function function) throws IOException {
        final ArrayList arrayList = new ArrayList();
        a(path, new a() { // from class: com.android.tools.r8.utils.P3$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.P3.a
            public final void a(ZipEntry zipEntry, InputStream inputStream) throws IOException {
                P3.a(predicate, path2, arrayList, function, zipEntry, inputStream);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, Path path, List list, Function function, ZipEntry zipEntry, InputStream inputStream) throws IOException {
        String name2 = zipEntry.getName();
        if (zipEntry.isDirectory() || !predicate.test(zipEntry)) {
            return;
        }
        if (!name2.contains("..")) {
            Path pathResolve = path.resolve(name2);
            pathResolve.toFile().getParentFile().mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(pathResolve.toFile());
            try {
                V5.a(inputStream, fileOutputStream);
                fileOutputStream.close();
                list.add(function.apply(pathResolve));
                return;
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        throw new C1367Tb("Invalid entry name \"" + name2 + "\"");
    }

    public static void a(ZipOutputStream zipOutputStream, String str, byte[] bArr, int i) throws IOException {
        ByteDataView byteDataViewOf = ByteDataView.of(bArr);
        byte[] buffer = byteDataViewOf.getBuffer();
        int offset = byteDataViewOf.getOffset();
        int length = byteDataViewOf.getLength();
        CRC32 crc32 = new CRC32();
        crc32.update(buffer, offset, length);
        ZipEntry zipEntry = new ZipEntry(str);
        zipEntry.setMethod(i);
        zipEntry.setSize(length);
        zipEntry.setCrc(crc32.getValue());
        zipEntry.setTime(0L);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(buffer, offset, length);
        zipOutputStream.closeEntry();
    }

    public static boolean a(String str) {
        String strI = Y2.i(str);
        if (strI.endsWith("module-info.class") || strI.startsWith("meta-inf") || strI.startsWith("/meta-inf")) {
            return false;
        }
        return strI.endsWith(".class");
    }

    public static String a(Class<?> cls) {
        return C3970u0.a(cls) + ".class";
    }

    public static String a(ClassReference classReference) {
        return classReference.getBinaryName() + ".class";
    }
}
