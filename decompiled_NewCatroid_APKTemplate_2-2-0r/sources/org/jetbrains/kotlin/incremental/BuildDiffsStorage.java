package org.jetbrains.kotlin.incremental;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: BuildDiffsStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/incremental/BuildDiffsStorage;", "", "buildDiffs", "", "Lorg/jetbrains/kotlin/incremental/BuildDifference;", "(Ljava/util/List;)V", "getBuildDiffs", "()Ljava/util/List;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "Companion", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class BuildDiffsStorage {
    private static int CURRENT_VERSION = 0;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int MAX_DIFFS_ENTRIES = 10;
    private final List<BuildDifference> buildDiffs;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ BuildDiffsStorage copy$default(BuildDiffsStorage buildDiffsStorage, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = buildDiffsStorage.buildDiffs;
        }
        return buildDiffsStorage.copy(list);
    }

    public final List<BuildDifference> component1() {
        return this.buildDiffs;
    }

    public final BuildDiffsStorage copy(List<BuildDifference> buildDiffs) {
        Intrinsics.checkNotNullParameter(buildDiffs, "buildDiffs");
        return new BuildDiffsStorage(buildDiffs);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof BuildDiffsStorage) && Intrinsics.areEqual(this.buildDiffs, ((BuildDiffsStorage) other).buildDiffs);
    }

    public int hashCode() {
        return this.buildDiffs.hashCode();
    }

    public String toString() {
        return "BuildDiffsStorage(buildDiffs=" + this.buildDiffs + ')';
    }

    /* JADX INFO: compiled from: BuildDiffsStorage.kt */
    @Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u001e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0013J\f\u0010\u0019\u001a\u00020\r*\u00020\u001aH\u0002J\f\u0010\u001b\u001a\u00020\u001c*\u00020\u001aH\u0002J\u001a\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u001f0\u001ej\b\u0012\u0004\u0012\u00020\u001f` *\u00020\u001aJ\u001a\u0010!\u001a\u0012\u0012\u0004\u0012\u00020\"0\u001ej\b\u0012\u0004\u0012\u00020\"` *\u00020\u001aJ\u0014\u0010#\u001a\u00020\u0015*\u00020$2\u0006\u0010%\u001a\u00020\rH\u0002J\u0014\u0010&\u001a\u00020\u0015*\u00020$2\u0006\u0010'\u001a\u00020\u001cH\u0002J\u0018\u0010(\u001a\u00020\u0015*\u00020$2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001f0*J\u0018\u0010+\u001a\u00020\u0015*\u00020$2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\"0*R$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/incremental/BuildDiffsStorage$Companion;", "", "()V", "<set-?>", "", "CURRENT_VERSION", "getCURRENT_VERSION", "()I", "setCURRENT_VERSION", "(I)V", "MAX_DIFFS_ENTRIES", "readDiffsFromFile", "", "Lorg/jetbrains/kotlin/incremental/BuildDifference;", "file", "Ljava/io/File;", "reporter", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "readFromFile", "Lorg/jetbrains/kotlin/incremental/BuildDiffsStorage;", "writeToFile", "", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "storage", "readBuildDifference", "Ljava/io/ObjectInputStream;", "readDirtyData", "Lorg/jetbrains/kotlin/incremental/DirtyData;", "readFqNames", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/name/FqName;", "Lkotlin/collections/ArrayList;", "readLookups", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "writeBuildDifference", "Ljava/io/ObjectOutputStream;", "diff", "writeDirtyData", "dirtyData", "writeFqNames", "dirtyClassesFqNames", "", "writeLookups", "lookupSymbols", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BuildDiffsStorage readFromFile(File file, ICReporter reporter) {
            Intrinsics.checkNotNullParameter(file, "file");
            List<BuildDifference> diffsFromFile = readDiffsFromFile(file, reporter);
            if (diffsFromFile != null) {
                return new BuildDiffsStorage(diffsFromFile);
            }
            return null;
        }

        private static final void readDiffsFromFile$reportFail(ICReporter iCReporter, final File file, final String str) {
            if (iCReporter != null) {
                ICReporterKt.info(iCReporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.BuildDiffsStorage$Companion$readDiffsFromFile$reportFail$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "Could not read diff from file " + file + ": " + str;
                    }
                });
            }
        }

        public final List<BuildDifference> readDiffsFromFile(File file, ICReporter reporter) {
            Intrinsics.checkNotNullParameter(file, "file");
            if (!file.exists()) {
                return null;
            }
            try {
                InputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream instanceof BufferedInputStream ? (BufferedInputStream) fileInputStream : new BufferedInputStream(fileInputStream, 8192));
                try {
                    ObjectInputStream objectInputStream2 = objectInputStream;
                    int i = objectInputStream2.readInt();
                    if (i != BuildDiffsStorage.INSTANCE.getCURRENT_VERSION()) {
                        readDiffsFromFile$reportFail(reporter, file, "incompatible version " + i + ", actual version is " + BuildDiffsStorage.INSTANCE.getCURRENT_VERSION());
                        CloseableKt.closeFinally(objectInputStream, null);
                        return null;
                    }
                    int i2 = objectInputStream2.readInt();
                    ArrayList arrayList = new ArrayList(i2);
                    for (int i3 = 0; i3 < i2; i3++) {
                        arrayList.add(BuildDiffsStorage.INSTANCE.readBuildDifference(objectInputStream2));
                    }
                    ArrayList arrayList2 = arrayList;
                    CloseableKt.closeFinally(objectInputStream, null);
                    return arrayList2;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(objectInputStream, th);
                        throw th2;
                    }
                }
            } catch (IOException e) {
                readDiffsFromFile$reportFail(reporter, file, e.toString());
                return null;
            }
        }

        public final void writeToFile(IncrementalCompilationContext icContext, final File file, final BuildDiffsStorage storage) {
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(storage, "storage");
            file.getParentFile().mkdirs();
            try {
                CompilationTransaction transaction = icContext.getTransaction();
                Path path = file.toPath();
                Intrinsics.checkNotNullExpressionValue(path, "toPath(...)");
                CompilationTransactionKt.write(transaction, path, new Function0<Unit>() { // from class: org.jetbrains.kotlin.incremental.BuildDiffsStorage$Companion$writeToFile$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        OutputStream fileOutputStream = new FileOutputStream(file);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream instanceof BufferedOutputStream ? (BufferedOutputStream) fileOutputStream : new BufferedOutputStream(fileOutputStream, 8192));
                        BuildDiffsStorage buildDiffsStorage = storage;
                        try {
                            ObjectOutputStream objectOutputStream2 = objectOutputStream;
                            objectOutputStream2.writeInt(BuildDiffsStorage.INSTANCE.getCURRENT_VERSION());
                            List listTakeLast = CollectionsKt.takeLast(CollectionsKt.sortedWith(buildDiffsStorage.getBuildDiffs(), new Comparator() { // from class: org.jetbrains.kotlin.incremental.BuildDiffsStorage$Companion$writeToFile$1$invoke$lambda$1$$inlined$sortedBy$1
                                /* JADX WARN: Multi-variable type inference failed */
                                @Override // java.util.Comparator
                                public final int compare(T t, T t2) {
                                    return ComparisonsKt.compareValues(Long.valueOf(((BuildDifference) t).getTs()), Long.valueOf(((BuildDifference) t2).getTs()));
                                }
                            }), 10);
                            objectOutputStream2.writeInt(listTakeLast.size());
                            Iterator it = listTakeLast.iterator();
                            while (it.hasNext()) {
                                BuildDiffsStorage.INSTANCE.writeBuildDifference(objectOutputStream2, (BuildDifference) it.next());
                            }
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(objectOutputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(objectOutputStream, th);
                                throw th2;
                            }
                        }
                    }
                });
            } catch (IOException e) {
                ICReporterKt.info(icContext.getReporter(), new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.BuildDiffsStorage$Companion$writeToFile$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "Could not write diff to file " + file + ": " + e;
                    }
                });
            }
        }

        private final BuildDifference readBuildDifference(ObjectInputStream objectInputStream) {
            return new BuildDifference(objectInputStream.readLong(), objectInputStream.readBoolean(), readDirtyData(objectInputStream));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void writeBuildDifference(ObjectOutputStream objectOutputStream, BuildDifference buildDifference) throws IOException {
            objectOutputStream.writeLong(buildDifference.getTs());
            objectOutputStream.writeBoolean(buildDifference.isIncremental());
            writeDirtyData(objectOutputStream, buildDifference.getDirtyData());
        }

        private final DirtyData readDirtyData(ObjectInputStream objectInputStream) throws IOException {
            return new DirtyData(readLookups(objectInputStream), readFqNames(objectInputStream), null, 4, null);
        }

        public final ArrayList<FqName> readFqNames(ObjectInputStream objectInputStream) throws IOException {
            Intrinsics.checkNotNullParameter(objectInputStream, "<this>");
            int i = objectInputStream.readInt();
            ArrayList<FqName> arrayList = new ArrayList<>(i);
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(new FqName(objectInputStream.readUTF()));
            }
            return arrayList;
        }

        public final ArrayList<LookupSymbol> readLookups(ObjectInputStream objectInputStream) throws IOException {
            Intrinsics.checkNotNullParameter(objectInputStream, "<this>");
            int i = objectInputStream.readInt();
            ArrayList<LookupSymbol> arrayList = new ArrayList<>(i);
            for (int i2 = 0; i2 < i; i2++) {
                String utf = objectInputStream.readUTF();
                String utf2 = objectInputStream.readUTF();
                Intrinsics.checkNotNull(utf);
                Intrinsics.checkNotNull(utf2);
                arrayList.add(new LookupSymbol(utf, utf2));
            }
            return arrayList;
        }

        private final void writeDirtyData(ObjectOutputStream objectOutputStream, DirtyData dirtyData) throws IOException {
            writeLookups(objectOutputStream, dirtyData.getDirtyLookupSymbols());
            writeFqNames(objectOutputStream, dirtyData.getDirtyClassesFqNames());
        }

        public final void writeFqNames(ObjectOutputStream objectOutputStream, Collection<FqName> dirtyClassesFqNames) throws IOException {
            Intrinsics.checkNotNullParameter(objectOutputStream, "<this>");
            Intrinsics.checkNotNullParameter(dirtyClassesFqNames, "dirtyClassesFqNames");
            objectOutputStream.writeInt(dirtyClassesFqNames.size());
            Iterator<FqName> it = dirtyClassesFqNames.iterator();
            while (it.hasNext()) {
                objectOutputStream.writeUTF(it.next().asString());
            }
        }

        public final void writeLookups(ObjectOutputStream objectOutputStream, Collection<LookupSymbol> lookupSymbols) throws IOException {
            Intrinsics.checkNotNullParameter(objectOutputStream, "<this>");
            Intrinsics.checkNotNullParameter(lookupSymbols, "lookupSymbols");
            objectOutputStream.writeInt(lookupSymbols.size());
            for (LookupSymbol lookupSymbol : lookupSymbols) {
                String name2 = lookupSymbol.getName();
                String scope = lookupSymbol.getScope();
                objectOutputStream.writeUTF(name2);
                objectOutputStream.writeUTF(scope);
            }
        }

        public final int getCURRENT_VERSION() {
            return BuildDiffsStorage.CURRENT_VERSION;
        }

        public final void setCURRENT_VERSION(int i) {
            BuildDiffsStorage.CURRENT_VERSION = i;
        }
    }

    public BuildDiffsStorage(List<BuildDifference> buildDiffs) {
        Intrinsics.checkNotNullParameter(buildDiffs, "buildDiffs");
        this.buildDiffs = buildDiffs;
    }

    public final List<BuildDifference> getBuildDiffs() {
        return this.buildDiffs;
    }
}
