package com.android.tools.r8.utils;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.ClassFileResourceProvider;
import com.android.tools.r8.DataDirectoryResource;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DataResource;
import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.DexFilePerClassFileConsumer;
import com.android.tools.r8.DexIndexedConsumer;
import com.android.tools.r8.DirectoryClassFileProvider;
import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.OutputMode;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.Resource;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.Version;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.BW;
import com.android.tools.r8.internal.C1031Ha;
import com.android.tools.r8.internal.C1036Hf;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C2091gb;
import com.android.tools.r8.internal.C3089ts;
import com.android.tools.r8.internal.C3220va;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.C3451yh;
import com.android.tools.r8.internal.PI;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.ArchiveEntryOrigin;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.profile.art.ArtProfileProvider;
import com.android.tools.r8.startup.StartupProfileProvider;
import com.android.tools.r8.tracereferences.TraceReferences$$ExternalSyntheticLambda2;
import com.android.tools.r8.tracereferences.TraceReferences$$ExternalSyntheticLambda3;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.utils.p, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3945p {
    static final /* synthetic */ boolean j = true;
    private final AbstractC1639am a;
    private final AbstractC2178hm b;
    private final AbstractC1639am c;
    private final AbstractC1639am d;
    private final AbstractC1639am e;
    private final com.android.tools.r8.d0 f;
    private final com.android.tools.r8.d0 g;
    private final List h;
    private final List i;

    /* JADX INFO: renamed from: com.android.tools.r8.utils.p$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean n = true;
        private final ArrayList a;
        private final ArrayList b;
        private final ArrayList c;
        private final HashMap d;
        private final ArrayList e;
        private final ArrayList f;
        private final ArrayList g;
        private List h;
        private List i;
        private boolean j;
        private com.android.tools.r8.d0 k;
        private com.android.tools.r8.d0 l;
        private final O2 m;

        private a(O2 o2) {
            this.a = new ArrayList();
            this.b = new ArrayList();
            this.c = new ArrayList();
            this.d = new HashMap();
            this.e = new ArrayList();
            this.f = new ArrayList();
            this.g = new ArrayList();
            this.h = new ArrayList();
            this.i = new ArrayList();
            this.j = false;
            this.m = o2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(Origin origin, ZipEntry zipEntry, InputStream inputStream) throws IOException {
            String name2 = zipEntry.getName();
            if (name2.equals("r8-version")) {
                System.out.println("Dump produced by R8 version: ".concat(new String(V5.a(inputStream), StandardCharsets.UTF_8)));
                return;
            }
            if (name2.equals("program.jar")) {
                a(origin, inputStream);
                return;
            }
            if (name2.equals("classpath.jar")) {
                a(origin, inputStream, new TraceReferences$$ExternalSyntheticLambda3(this), ModuleXmlParser.CLASSPATH);
            } else {
                if (name2.equals("library.jar")) {
                    a(origin, inputStream, new TraceReferences$$ExternalSyntheticLambda2(this), "library");
                    return;
                }
                System.out.println("WARNING: Unexpected dump file entry: " + zipEntry.getName());
            }
        }

        public a b(Path path) throws IOException {
            System.out.println("Reading dump from file: " + path);
            final PathOrigin pathOrigin = new PathOrigin(path);
            P3.a(path.toString(), new P3.a() { // from class: com.android.tools.r8.utils.p$a$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.P3.a
                public final void a(ZipEntry zipEntry, InputStream inputStream) throws IOException {
                    this.f$0.a(pathOrigin, zipEntry, inputStream);
                }
            });
            return this;
        }

        public final O2 c() {
            return this.m;
        }

        public a d(Collection<com.android.tools.r8.shaking.J0> collection) {
            for (com.android.tools.r8.shaking.J0 j0 : collection) {
                if (X0.a(j0.b())) {
                    try {
                        Y0 y0 = new Y0(j0);
                        this.g.add(y0);
                        this.f.add(y0);
                    } catch (IOException e) {
                        this.m.error(new ExceptionDiagnostic(e, new PathOrigin(j0.b())));
                    }
                } else {
                    this.m.error(new StringDiagnostic("Unexpected input type. Only archive types are supported, e.g., .jar, .zip, etc.", j0.a(), j0.c()));
                }
            }
            return this;
        }

        public a e(Collection<com.android.tools.r8.shaking.J0> collection) {
            Iterator<com.android.tools.r8.shaking.J0> it = collection.iterator();
            while (it.hasNext()) {
                a(it.next());
            }
            return this;
        }

        public a f(Collection<Path> collection) {
            Iterator<Path> it = collection.iterator();
            while (it.hasNext()) {
                a(it.next(), this.f);
            }
            return this;
        }

        public final void g(Collection collection) {
            this.i.addAll(collection);
        }

        public final a h(Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                Path path = (Path) it.next();
                if (!Files.exists(path, new LinkOption[0])) {
                    throw new NoSuchFileException(path.toString());
                }
                this.h.add(com.android.tools.r8.d0.a(path));
            }
            return this;
        }

        public a i(Collection<Path> collection) {
            Iterator<Path> it = collection.iterator();
            while (it.hasNext()) {
                d(it.next());
            }
            return this;
        }

        public a c(Path path) {
            a(path, this.f);
            return this;
        }

        public final a c(Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                a(ProgramResource.fromBytes(Origin.unknown(), ProgramResource.Kind.DEX, (byte[]) it.next(), null));
            }
            return this;
        }

        public final void e(Path path) {
            this.l = com.android.tools.r8.d0.a(path);
        }

        public a b(Path... pathArr) {
            return i(Arrays.asList(pathArr));
        }

        public a b(Collection<Path> collection) {
            Iterator<Path> it = collection.iterator();
            while (it.hasNext()) {
                a(it.next(), this.e);
            }
            return this;
        }

        public a b(ClassFileResourceProvider classFileResourceProvider) {
            if (classFileResourceProvider instanceof C3953q1) {
                this.g.add((C3953q1) classFileResourceProvider);
            }
            this.f.add(classFileResourceProvider);
            return this;
        }

        private static void a(Origin origin, InputStream inputStream, Consumer consumer, String str) throws IOException {
            HashMap map = new HashMap();
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            while (true) {
                try {
                    ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry == null) {
                        break;
                    }
                    String name2 = nextEntry.getName();
                    if (P3.a(name2)) {
                        ArchiveEntryOrigin archiveEntryOrigin = new ArchiveEntryOrigin(name2, origin);
                        String strY = C3970u0.y(name2);
                        map.put(strY, C3909i2.a(archiveEntryOrigin, ProgramResource.Kind.CF, V5.a(zipInputStream), Collections.singleton(strY)));
                    } else if (name2.endsWith(".dup")) {
                        System.out.println("WARNING: Duplicate " + str + " resource: " + name2);
                    } else {
                        System.out.println("WARNING: Unexpected " + str + " resource: " + name2);
                    }
                } catch (Throwable th) {
                    try {
                        zipInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            zipInputStream.close();
            if (map.isEmpty()) {
                return;
            }
            consumer.accept(C3945p.a(map));
        }

        public a b(byte[] bArr, Origin origin) {
            a(ProgramResource.fromBytes(origin, ProgramResource.Kind.DEX, bArr, null));
            return this;
        }

        public final ArrayList b() {
            if (!this.b.isEmpty() || !this.c.isEmpty()) {
                this.a.add(new C3940o(AbstractC1639am.a(this.b), AbstractC1639am.a(this.c)));
                this.b.clear();
                this.c.clear();
            }
            return this.a;
        }

        public final boolean d() {
            return (this.h.isEmpty() && this.i.isEmpty()) ? false : true;
        }

        public a d(Path path) {
            if (!Files.exists(path, new LinkOption[0])) {
                this.m.error(new ExceptionDiagnostic(new NoSuchFileException(path.toString()), new PathOrigin(path)));
            }
            if (X0.d(path)) {
                a(ProgramResource.fromFile(ProgramResource.Kind.DEX, path));
            } else if (X0.b(path)) {
                a(ProgramResource.fromFile(ProgramResource.Kind.CF, path));
            } else {
                String string = path.getFileName().toString();
                char[] cArr = Y2.a;
                if (string.toLowerCase(Locale.ROOT).endsWith(".aar")) {
                    a(new C3865a(path));
                } else if (X0.a(path)) {
                    a(ArchiveResourceProvider.fromArchive(path, this.j));
                } else {
                    throw new C1367Tb(new PathOrigin(path), "Unsupported source file type", null);
                }
            }
            return this;
        }

        private void a(Origin origin, InputStream inputStream) throws IOException {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            while (true) {
                try {
                    ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry == null) {
                        break;
                    }
                    String name2 = nextEntry.getName();
                    if (P3.a(name2)) {
                        arrayList.add(C3909i2.a(new ArchiveEntryOrigin(name2, origin), ProgramResource.Kind.CF, V5.a(zipInputStream), Collections.singleton(C3970u0.y(name2))));
                    } else if (P3.b(name2)) {
                        arrayList.add(C3909i2.a(new ArchiveEntryOrigin(name2, origin), ProgramResource.Kind.DEX, V5.a(zipInputStream), null));
                    } else if (name2.endsWith(".dup")) {
                        System.out.println("WARNING: Duplicate program resource: " + name2);
                    } else {
                        arrayList2.add(DataEntryResource.fromBytes(V5.a(zipInputStream), name2, origin));
                    }
                } catch (Throwable th) {
                    try {
                        zipInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            zipInputStream.close();
            if (arrayList.isEmpty() && arrayList2.isEmpty()) {
                return;
            }
            a(new C3930m(arrayList, arrayList2));
        }

        public final ArchiveResourceProvider a(com.android.tools.r8.shaking.J0 j0) {
            if (X0.a(j0.b())) {
                ArchiveResourceProvider archiveResourceProvider = new ArchiveResourceProvider(j0, this.j);
                a(archiveResourceProvider);
                return archiveResourceProvider;
            }
            this.m.error(new StringDiagnostic("Unexpected input type. Only archive types are supported, e.g., .jar, .zip, etc.", j0.a(), j0.c()));
            return null;
        }

        public a a(ProgramResourceProvider programResourceProvider) {
            if (!n && programResourceProvider == null) {
                throw new AssertionError();
            }
            this.a.add(programResourceProvider);
            return this;
        }

        public final void a(Path path) {
            a(path, this.e);
        }

        public a a(ClassFileResourceProvider classFileResourceProvider) {
            this.e.add(classFileResourceProvider);
            return this;
        }

        public a a(Path... pathArr) {
            return f(Arrays.asList(pathArr));
        }

        public final void a(Set set, byte[] bArr) {
            a(ProgramResource.fromBytes(Origin.unknown(), ProgramResource.Kind.DEX, bArr, set));
        }

        public final void a(String str, Set set, byte[] bArr) {
            ProgramResource programResourceFromBytes = ProgramResource.fromBytes(Origin.unknown(), ProgramResource.Kind.DEX, bArr, set);
            this.b.add(programResourceFromBytes);
            this.d.put(programResourceFromBytes, str);
        }

        public a a(byte[]... bArr) {
            return a(Arrays.asList(bArr));
        }

        public a a(Collection<byte[]> collection) {
            Iterator<byte[]> it = collection.iterator();
            while (it.hasNext()) {
                a(it.next(), Origin.unknown());
            }
            return this;
        }

        public a a(byte[] bArr, Origin origin) {
            return a(bArr, origin, (Set<String>) null);
        }

        public a a(byte[] bArr, Origin origin, Set<String> set) {
            a(ProgramResource.fromBytes(origin, ProgramResource.Kind.CF, bArr, set));
            return this;
        }

        public a a(DataResource dataResource) {
            this.c.addAll(Arrays.asList(dataResource));
            return this;
        }

        public a a(String str) {
            this.k = str == null ? null : com.android.tools.r8.d0.a(str, Origin.unknown());
            return this;
        }

        public final void a(String... strArr) {
            this.i.addAll(Arrays.asList(strArr));
        }

        public final void a(boolean z) {
            this.j = z;
        }

        public C3945p a() {
            if (!this.b.isEmpty() || !this.c.isEmpty()) {
                this.a.add(new C3940o(AbstractC1639am.a(this.b), AbstractC1639am.a(this.c)));
                this.b.clear();
                this.c.clear();
            }
            return new C3945p(AbstractC1639am.a(this.a), AbstractC2178hm.a(this.d), AbstractC1639am.a(this.e), AbstractC1639am.a(this.f), AbstractC1639am.a(this.g), this.k, this.l, this.h, this.i);
        }

        private void a(ProgramResource... programResourceArr) {
            this.b.addAll(Arrays.asList(programResourceArr));
        }

        private void a(Path path, ArrayList arrayList) {
            if (!Files.exists(path, new LinkOption[0])) {
                this.m.error(new ExceptionDiagnostic(new NoSuchFileException(path.toString()), new PathOrigin(path)));
            }
            if (X0.a(path)) {
                try {
                    C3953q1 c3953q1 = new C3953q1(path);
                    this.g.add(c3953q1);
                    arrayList.add(c3953q1);
                    return;
                } catch (IOException e) {
                    this.m.error(new ExceptionDiagnostic(e, new PathOrigin(path)));
                    return;
                }
            }
            if (Files.isDirectory(path, new LinkOption[0])) {
                arrayList.add(DirectoryClassFileProvider.fromDirectory(path));
                return;
            }
            throw new C1367Tb("Unsupported source file type", null, new PathOrigin(path), Position.UNKNOWN);
        }
    }

    private C3945p(AbstractC1639am abstractC1639am, AbstractC2178hm abstractC2178hm, AbstractC1639am abstractC1639am2, AbstractC1639am abstractC1639am3, AbstractC1639am abstractC1639am4, com.android.tools.r8.d0 d0Var, com.android.tools.r8.d0 d0Var2, List list, List list2) {
        this.a = abstractC1639am;
        this.b = abstractC2178hm;
        this.c = abstractC1639am2;
        this.d = abstractC1639am3;
        this.e = abstractC1639am4;
        this.f = d0Var;
        this.g = d0Var2;
        this.h = list;
        this.i = list2;
        boolean z = j;
        if (!z && !a(abstractC1639am2, abstractC1639am4)) {
            throw new AssertionError();
        }
        if (!z && !a(abstractC1639am3, abstractC1639am4)) {
            throw new AssertionError();
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x007b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int a(final com.android.tools.r8.internal.C3451yh r23, java.util.zip.ZipOutputStream r24, final com.android.tools.r8.utils.A1 r25) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 461
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.utils.C3945p.a(com.android.tools.r8.internal.yh, java.util.zip.ZipOutputStream, com.android.tools.r8.utils.A1):int");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ZipOutputStream a(ZipOutputStream zipOutputStream, String str) {
        return zipOutputStream;
    }

    public static a b() {
        return a(new O2());
    }

    public final void c() throws IOException {
        AbstractC2963s10 it = this.e.iterator();
        while (it.hasNext()) {
            ((C3953q1) it.next()).close();
        }
    }

    public final ArrayList d() {
        ArrayList arrayList = new ArrayList();
        AbstractC2963s10 it = this.a.iterator();
        while (it.hasNext()) {
            arrayList.addAll(((ProgramResourceProvider) it.next()).getProgramResources());
        }
        return arrayList;
    }

    public List<ProgramResource> e() throws IOException {
        try {
            AbstractC1639am abstractC1639am = this.a;
            ProgramResource.Kind kind = ProgramResource.Kind.CF;
            ArrayList arrayList = new ArrayList();
            Iterator it = abstractC1639am.iterator();
            while (it.hasNext()) {
                for (ProgramResource programResource : ((ProgramResourceProvider) it.next()).getProgramResources()) {
                    if (programResource.getKind() == kind) {
                        arrayList.add(programResource);
                    }
                }
            }
            return arrayList;
        } catch (ResourceException e) {
            if (e.getCause() instanceof IOException) {
                throw ((IOException) e.getCause());
            }
            throw new C3089ts(e);
        }
    }

    public List<ClassFileResourceProvider> f() {
        return this.c;
    }

    public final C3979v2 g() throws ResourceException {
        TreeSet treeSet = new TreeSet(Comparator.comparing(new Function() { // from class: com.android.tools.r8.utils.p$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DataDirectoryResource) obj).getName();
            }
        }));
        TreeSet treeSet2 = new TreeSet(Comparator.comparing(new Function() { // from class: com.android.tools.r8.utils.p$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DataEntryResource) obj).getName();
            }
        }));
        Iterator<ProgramResourceProvider> it = l().iterator();
        while (it.hasNext()) {
            DataResourceProvider dataResourceProvider = it.next().getDataResourceProvider();
            if (dataResourceProvider != null) {
                dataResourceProvider.accept(new C3901h(treeSet, treeSet2));
            }
        }
        return new C3979v2(treeSet, treeSet2);
    }

    public List<ProgramResource> h() throws IOException {
        try {
            AbstractC1639am abstractC1639am = this.a;
            ProgramResource.Kind kind = ProgramResource.Kind.DEX;
            ArrayList arrayList = new ArrayList();
            Iterator it = abstractC1639am.iterator();
            while (it.hasNext()) {
                for (ProgramResource programResource : ((ProgramResourceProvider) it.next()).getProgramResources()) {
                    if (programResource.getKind() == kind) {
                        arrayList.add(programResource);
                    }
                }
            }
            return arrayList;
        } catch (ResourceException e) {
            if (e.getCause() instanceof IOException) {
                throw ((IOException) e.getCause());
            }
            throw new C3089ts(e);
        }
    }

    public List<ClassFileResourceProvider> i() {
        return this.d;
    }

    public final List j() {
        return this.i;
    }

    public final List k() {
        return this.h;
    }

    public List<ProgramResourceProvider> l() {
        return this.a;
    }

    public com.android.tools.r8.d0 m() {
        return this.g;
    }

    public com.android.tools.r8.d0 n() {
        return this.f;
    }

    public boolean o() {
        return (this.h.isEmpty() && this.i.isEmpty()) ? false : true;
    }

    public boolean p() {
        return !this.h.isEmpty();
    }

    public final void q() {
        Iterator<ProgramResourceProvider> it = l().iterator();
        while (it.hasNext()) {
            try {
                for (ProgramResource programResource : it.next().getProgramResources()) {
                    try {
                        if (programResource.getKind() != ProgramResource.Kind.DEX) {
                            new C1031Ha(programResource.getBytes()).a(8, new C3919k(new C3220va()));
                        }
                    } catch (Throwable th) {
                        throw new C1367Tb("Failed validating " + programResource.getOrigin(), th);
                    }
                }
            } catch (ResourceException e) {
                throw new C1367Tb("Resource exception in validation", e);
            }
        }
    }

    public final C3945p r() {
        return new C3945p(this.a, this.b, this.c, this.d, this.e, this.f, this.g, AbstractC1639am.i(), AbstractC1639am.i());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            if (!this.a.isEmpty()) {
                sb.append("  Program resources:").append(System.lineSeparator());
                Iterator<E> it = this.a.iterator();
                while (it.hasNext()) {
                    for (ProgramResource programResource : ((ProgramResourceProvider) it.next()).getProgramResources()) {
                        sb.append("    ").append(programResource.getOrigin());
                        Set<String> classDescriptors = programResource.getClassDescriptors();
                        if (classDescriptors != null && !classDescriptors.isEmpty()) {
                            sb.append(" contains ");
                            Y2.a(sb, classDescriptors);
                        }
                        sb.append(System.lineSeparator());
                    }
                }
            }
            if (!this.c.isEmpty()) {
                sb.append("  Classpath resources:").append(System.lineSeparator());
                for (ClassFileResourceProvider classFileResourceProvider : this.c) {
                    Iterator<String> it2 = classFileResourceProvider.getClassDescriptors().iterator();
                    while (it2.hasNext()) {
                        ProgramResource programResource2 = classFileResourceProvider.getProgramResource(it2.next());
                        sb.append("    ").append(programResource2.getOrigin());
                        Set<String> classDescriptors2 = programResource2.getClassDescriptors();
                        if (classDescriptors2 != null && !classDescriptors2.isEmpty()) {
                            sb.append(" contains ");
                            Y2.a(sb, classDescriptors2);
                        }
                        sb.append(System.lineSeparator());
                    }
                }
            }
            if (!this.d.isEmpty()) {
                sb.append("  Library resources:").append(System.lineSeparator());
                for (ClassFileResourceProvider classFileResourceProvider2 : this.d) {
                    Iterator<String> it3 = classFileResourceProvider2.getClassDescriptors().iterator();
                    while (it3.hasNext()) {
                        ProgramResource programResource3 = classFileResourceProvider2.getProgramResource(it3.next());
                        sb.append("    ").append(programResource3.getOrigin());
                        Set<String> classDescriptors3 = programResource3.getClassDescriptors();
                        if (classDescriptors3 != null && !classDescriptors3.isEmpty()) {
                            sb.append(" contains ");
                            Y2.a(sb, classDescriptors3);
                        }
                        sb.append(System.lineSeparator());
                    }
                }
            }
        } catch (ResourceException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void b(Path path, OutputMode outputMode) throws Throwable {
        List<ProgramResource> listH = h();
        try {
            if (outputMode == OutputMode.DexIndexed) {
                DexIndexedConsumer.DirectoryConsumer.writeResources(path, listH);
            } else {
                DexFilePerClassFileConsumer.DirectoryConsumer.writeResources(path, listH, this.b);
            }
        } catch (ResourceException e) {
            throw new IOException("Resource Error", e);
        }
    }

    public void c(Path path, OutputMode outputMode) throws Throwable {
        try {
            if (outputMode == OutputMode.DexIndexed) {
                C3979v2 c3979v2G = g();
                DexIndexedConsumer.ArchiveConsumer.writeResourcesForTesting(path, h(), (Set) c3979v2G.a(), (Set) c3979v2G.b());
                return;
            }
            if (outputMode != OutputMode.DexFilePerClassFile && outputMode != OutputMode.DexFilePerClass) {
                if (outputMode == OutputMode.ClassFile) {
                    C3979v2 c3979v2G2 = g();
                    ClassFileConsumer.ArchiveConsumer.writeResourcesForTesting(path, e(), (Set) c3979v2G2.a(), (Set) c3979v2G2.b());
                    return;
                }
                throw new C3405y10("Unsupported output-mode for writing: " + outputMode);
            }
            DexFilePerClassFileConsumer.ArchiveConsumer.writeResourcesForTesting(path, h(), this.b);
        } catch (ResourceException e) {
            throw new IOException("Resource Error", e);
        }
    }

    public final void b(O2 o2) throws IOException {
        AbstractC2963s10 it = this.a.iterator();
        while (it.hasNext()) {
            ((ProgramResourceProvider) it.next()).finished(o2);
        }
        AbstractC2963s10 it2 = this.c.iterator();
        while (it2.hasNext()) {
            ((ClassFileResourceProvider) it2.next()).finished(o2);
        }
        AbstractC2963s10 it3 = this.d.iterator();
        while (it3.hasNext()) {
            ((ClassFileResourceProvider) it3.next()).finished(o2);
        }
    }

    private static IdentityHashMap a(C3451yh c3451yh) {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        if (c3451yh != null) {
            Iterator it = c3451yh.b().iterator();
            int i = 1;
            while (it.hasNext()) {
                identityHashMap.put((FeatureSplit) it.next(), "feature-" + i + ".jar");
                i++;
            }
        }
        return identityHashMap;
    }

    private static boolean a(AbstractC1639am abstractC1639am, final AbstractC1639am abstractC1639am2) {
        return abstractC1639am.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.utils.p$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3945p.a(abstractC1639am2, (ClassFileResourceProvider) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC1639am abstractC1639am, ClassFileResourceProvider classFileResourceProvider) {
        return !(classFileResourceProvider instanceof C3953q1) || abstractC1639am.contains(classFileResourceProvider);
    }

    public static a a(C3945p c3945p) {
        return a(c3945p, new O2());
    }

    public static a a(O2 o2) {
        return new a(o2);
    }

    public static a a(C3945p c3945p, O2 o2) {
        a aVar = new a(o2);
        aVar.a.addAll(c3945p.a);
        aVar.e.addAll(c3945p.c);
        aVar.f.addAll(c3945p.d);
        aVar.g.addAll(c3945p.e);
        aVar.h = c3945p.h;
        aVar.i = c3945p.i;
        aVar.l = c3945p.g;
        return aVar;
    }

    public int a() throws Throwable {
        if (!j && h().size() != 0 && e().size() != 0) {
            throw new AssertionError();
        }
        C2091gb c2091gbC = C2091gb.c();
        try {
            Iterator<ProgramResource> it = h().iterator();
            int length = 0;
            while (it.hasNext()) {
                length += V5.a((InputStream) c2091gbC.a(it.next().getByteStream())).length;
            }
            Iterator<ProgramResource> it2 = e().iterator();
            while (it2.hasNext()) {
                length += V5.a((InputStream) c2091gbC.a(it2.next().getByteStream())).length;
            }
            c2091gbC.close();
            return length;
        } catch (Throwable th) {
            try {
                c2091gbC.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void a(Path path, OutputMode outputMode) throws Throwable {
        if (X0.a(path)) {
            c(path, outputMode);
        } else {
            b(path, outputMode);
        }
    }

    public String a(Resource resource) {
        if (j || (resource instanceof ProgramResource)) {
            return (String) this.b.get(resource);
        }
        throw new AssertionError();
    }

    public void a(Path path, C1036Hf c1036Hf, A1 a1) {
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
            try {
                P3.a(zipOutputStream, "r8-version", Version.getVersionString().getBytes(), 8);
                P3.a(zipOutputStream, "build.properties", c1036Hf.d().getBytes(), 8);
                if (c1036Hf.e() != null) {
                    P3.a(zipOutputStream, "desugared-library.json", c1036Hf.e().getBytes(), 8);
                    if (c1036Hf.a()) {
                        a1.e.c("Dumping a compilation with desugared library on a file may prevent reproduction, use dumpInputToDirectory property instead.");
                    }
                }
                if (c1036Hf.i() != null) {
                    P3.a(zipOutputStream, "proguard.config", c1036Hf.i().getBytes(), 8);
                }
                if (this.g != null) {
                    a1.e.c("Dumping proguard map input data may have side effects due to I/O on Paths.");
                    P3.a(zipOutputStream, "proguard_input.config", this.g.a().getBytes(), 8);
                }
                if (o()) {
                    ArrayList arrayList = new ArrayList();
                    if (p()) {
                        a1.e.c("Dumping main dex list resources may have side effects due to I/O on Paths.");
                        Iterator it = this.h.iterator();
                        while (it.hasNext()) {
                            arrayList.add(((com.android.tools.r8.d0) it.next()).a());
                        }
                    }
                    Iterator it2 = this.i.iterator();
                    while (it2.hasNext()) {
                        arrayList.add(((String) it2.next()).replace(".", "/") + ".class");
                    }
                    P3.a(zipOutputStream, "main-dex-list.txt", Y2.a("\n", arrayList).getBytes(), 8);
                }
                if (c1036Hf.l()) {
                    P3.a(zipOutputStream, "main-dex-rules.txt", Y2.a((Collection) c1036Hf.g()).getBytes(), 8);
                }
                if (c1036Hf.k()) {
                    a(c1036Hf.b(), zipOutputStream);
                }
                if (c1036Hf.m()) {
                    a(c1036Hf.j(), a1, zipOutputStream);
                }
                a("library.jar", a("classpath.jar", a(c1036Hf.f(), zipOutputStream, a1), zipOutputStream, this.c), zipOutputStream, this.d);
                zipOutputStream.close();
            } catch (Throwable th) {
                try {
                    zipOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (ResourceException | IOException e) {
            a1.e.a(new ExceptionDiagnostic(e));
            throw null;
        }
    }

    private static void a(Collection collection, ZipOutputStream zipOutputStream) throws IOException {
        Iterator it = collection.iterator();
        int i = 1;
        while (it.hasNext()) {
            P3.a(zipOutputStream, "art-profile-" + i + Constants.TEXT_FILE_EXTENSION, com.android.tools.r8.internal.I3.a((ArtProfileProvider) it.next()).getBytes(), 8);
            i++;
        }
    }

    private static void a(Collection collection, A1 a1, ZipOutputStream zipOutputStream) throws IOException {
        Iterator it = collection.iterator();
        int i = 1;
        while (it.hasNext()) {
            P3.a(zipOutputStream, "startup-profile-" + i + Constants.TEXT_FILE_EXTENSION, BW.a(a1, (StartupProfileProvider) it.next()).getBytes(), 8);
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ClassFileResourceProvider a(HashMap map) {
        return new C3911j(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ZipOutputStream a(C3451yh c3451yh, A1 a1, C1121Ka c1121Ka, Map map, ZipOutputStream zipOutputStream, String str) {
        FeatureSplit featureSplitA;
        return (c3451yh == null || (featureSplitA = c1121Ka.a(a1.j0().e(str), a1, AbstractC3135uW.b(), (com.android.tools.r8.synthesis.D) null)) == null || featureSplitA.isBase()) ? zipOutputStream : (ZipOutputStream) map.get(featureSplitA);
    }

    private static int a(String str, int i, ZipOutputStream zipOutputStream, AbstractC1639am abstractC1639am) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            final ZipOutputStream zipOutputStream2 = new ZipOutputStream(byteArrayOutputStream);
            try {
                PI pi = new PI(0);
                AbstractC2963s10 it = abstractC1639am.iterator();
                while (it.hasNext()) {
                    ClassFileResourceProvider classFileResourceProvider = (ClassFileResourceProvider) it.next();
                    Iterator<String> it2 = classFileResourceProvider.getClassDescriptors().iterator();
                    while (it2.hasNext()) {
                        int iA = a(pi, i, new Function() { // from class: com.android.tools.r8.utils.p$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return C3945p.a(zipOutputStream2, (String) obj);
                            }
                        }, zipOutputStream2, classFileResourceProvider.getProgramResource(it2.next()));
                        if (!j && iA != i) {
                            throw new AssertionError();
                        }
                        i = iA;
                    }
                }
                zipOutputStream2.close();
                P3.a(zipOutputStream, str, byteArrayOutputStream.toByteArray(), 8);
                byteArrayOutputStream.close();
                return i;
            } catch (Throwable th) {
                try {
                    zipOutputStream2.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Throwable th3) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
            }
            throw th3;
        }
    }

    private static int a(PI pi, int i, Function function, ZipOutputStream zipOutputStream, ProgramResource programResource) throws IOException {
        String strB;
        byte[] bArrA = W2.a(programResource.getByteStream());
        if (programResource.getKind() == ProgramResource.Kind.CF) {
            Set<String> classDescriptors = programResource.getClassDescriptors();
            if (classDescriptors != null && classDescriptors.size() == 1) {
                strB = classDescriptors.iterator().next();
            } else {
                int length = bArrA.length;
                C1031Ha c1031Ha = new C1031Ha(bArrA, 0);
                C3906i c3906i = new C3906i();
                c1031Ha.a(7, c3906i);
                strB = c3906i.b();
            }
            String strJ = C3970u0.j(strB);
            int iIntValue = ((Integer) pi.getOrDefault(strB, 0)).intValue();
            pi.a(iIntValue + 1, strB);
            if (iIntValue != 0) {
                strJ = strJ + "." + iIntValue + ".dup";
            }
            P3.a((ZipOutputStream) function.apply(strB), strJ, bArrA, 8);
            return i;
        }
        if (!j && programResource.getKind() != ProgramResource.Kind.DEX) {
            throw new AssertionError();
        }
        int i2 = i + 1;
        P3.a(zipOutputStream, "classes" + i + ".dex", bArrA, 8);
        return i2;
    }
}
