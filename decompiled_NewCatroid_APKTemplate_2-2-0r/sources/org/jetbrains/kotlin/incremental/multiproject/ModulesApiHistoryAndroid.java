package org.jetbrains.kotlin.incremental.multiproject;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.incremental.IncrementalModuleEntry;
import org.jetbrains.kotlin.incremental.IncrementalModuleInfo;
import org.jetbrains.kotlin.incremental.util.Either;

/* JADX INFO: compiled from: ModulesApiHistory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000b0\n2\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u001c\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000b0\n2\u0006\u0010\f\u001a\u00020\u0003H\u0014J\u001c\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000b0\n2\u0006\u0010\u000f\u001a\u00020\u0003H\u0014J>\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000b0\n2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00030\u0017H\u0002J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00150\u001a2\u0006\u0010\u0011\u001a\u00020\u0003H\u0002J\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00150\u001c2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\"\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000b0\n2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistoryAndroid;", "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistoryBase;", "rootProjectDir", "Ljava/io/File;", "modulesInfo", "Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistoryJvm;", "abiSnapshot", "Lorg/jetbrains/kotlin/incremental/util/Either;", "", "jar", "getBuildHistoryFilesForJar", "getBuildHistoryForDir", "file", "getHistoryForModuleNames", "path", "Ljava/nio/file/Path;", "moduleNames", "", "", "fileLocation", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/incremental/IncrementalModuleEntry;", "getPossibleModuleNamesForDir", "", "getPossibleModuleNamesFromJar", "", "historyFilesForChangedFiles", "changedFiles", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModulesApiHistoryAndroid extends ModulesApiHistoryBase {
    private final ModulesApiHistoryJvm delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModulesApiHistoryAndroid(File rootProjectDir, IncrementalModuleInfo modulesInfo) {
        super(rootProjectDir, modulesInfo);
        Intrinsics.checkNotNullParameter(rootProjectDir, "rootProjectDir");
        Intrinsics.checkNotNullParameter(modulesInfo, "modulesInfo");
        this.delegate = new ModulesApiHistoryJvm(rootProjectDir, modulesInfo);
    }

    @Override // org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistoryBase, org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory
    public Either<Set<File>> historyFilesForChangedFiles(Set<? extends File> changedFiles) {
        Intrinsics.checkNotNullParameter(changedFiles, "changedFiles");
        Either<Set<File>> eitherHistoryFilesForChangedFiles = this.delegate.historyFilesForChangedFiles(changedFiles);
        return eitherHistoryFilesForChangedFiles instanceof Either.Success ? eitherHistoryFilesForChangedFiles : super.historyFilesForChangedFiles(changedFiles);
    }

    @Override // org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistoryBase
    protected Either<Set<File>> getBuildHistoryFilesForJar(File jar) {
        Intrinsics.checkNotNullParameter(jar, "jar");
        if (!isInProjectBuildDir(jar)) {
            return new Either.Error("Non-project jar is modified " + jar);
        }
        Path path = Paths.get(jar.getAbsolutePath(), new String[0]);
        Intrinsics.checkNotNull(path);
        return getHistoryForModuleNames(path, getPossibleModuleNamesFromJar(path), new PropertyReference1() { // from class: org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistoryAndroid.getBuildHistoryFilesForJar.1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getBuildHistoryFile()Ljava/io/File;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((IncrementalModuleEntry) obj).getBuildHistoryFile();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "buildHistoryFile";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(IncrementalModuleEntry.class);
            }
        });
    }

    @Override // org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory
    public Either<Set<File>> abiSnapshot(File jar) {
        Intrinsics.checkNotNullParameter(jar, "jar");
        Path path = Paths.get(jar.getAbsolutePath(), new String[0]);
        Intrinsics.checkNotNull(path);
        Either<Set<File>> historyForModuleNames = getHistoryForModuleNames(path, getPossibleModuleNamesFromJar(path), new PropertyReference1() { // from class: org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistoryAndroid$abiSnapshot$result$1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getAbiSnapshot()Ljava/io/File;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((IncrementalModuleEntry) obj).getAbiSnapshot();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "abiSnapshot";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(IncrementalModuleEntry.class);
            }
        });
        if (historyForModuleNames instanceof Either.Success) {
            return new Either.Success(((Either.Success) historyForModuleNames).getValue());
        }
        if (historyForModuleNames instanceof Either.Error) {
            return new Either.Error(((Either.Error) historyForModuleNames).getReason());
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistoryBase
    protected Either<Set<File>> getBuildHistoryForDir(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (!isInProjectBuildDir(file)) {
            return new Either.Error("Non-project file while looking for history " + file);
        }
        List listPlus = CollectionsKt.plus((Collection) getPossibleModuleNamesForDir(FilesKt.resolve(file, "meta-inf")), (Iterable) getPossibleModuleNamesForDir(FilesKt.resolve(file, "META-INF")));
        if (listPlus.isEmpty()) {
            if (file.getParentFile() == null) {
                return new Either.Error("Unable to find history for " + file);
            }
            File parentFile = file.getParentFile();
            Intrinsics.checkNotNullExpressionValue(parentFile, "getParentFile(...)");
            return getBuildHistoryForDir(parentFile);
        }
        Path path = file.toPath();
        Intrinsics.checkNotNullExpressionValue(path, "toPath(...)");
        return getHistoryForModuleNames(path, listPlus, new PropertyReference1() { // from class: org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistoryAndroid.getBuildHistoryForDir.1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getBuildHistoryFile()Ljava/io/File;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((IncrementalModuleEntry) obj).getBuildHistoryFile();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "buildHistoryFile";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(IncrementalModuleEntry.class);
            }
        });
    }

    private final Collection<String> getPossibleModuleNamesFromJar(Path path) {
        HashSet hashSet = new HashSet();
        try {
            ZipFile zipFile = new ZipFile(path.toFile());
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
                while (enumerationEntries.hasMoreElements()) {
                    String name2 = enumerationEntries.nextElement().getName();
                    Intrinsics.checkNotNull(name2);
                    if (StringsKt.endsWith(name2, ".kotlin_module", true)) {
                        hashSet.add(FilesKt.getNameWithoutExtension(new File(name2)));
                    }
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(zipFile, null);
                return hashSet;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(zipFile, th);
                    throw th2;
                }
            }
        } catch (Throwable unused) {
            return CollectionsKt.emptyList();
        }
    }

    private final List<String> getPossibleModuleNamesForDir(File path) {
        if (!path.isDirectory()) {
            return CollectionsKt.emptyList();
        }
        File[] fileArrListFiles = path.listFiles();
        Intrinsics.checkNotNullExpressionValue(fileArrListFiles, "listFiles(...)");
        ArrayList arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            String name2 = file.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (StringsKt.endsWith(name2, ".kotlin_module", true)) {
                arrayList.add(file);
            }
        }
        ArrayList<File> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (File file2 : arrayList2) {
            Intrinsics.checkNotNull(file2);
            arrayList3.add(FilesKt.getNameWithoutExtension(file2));
        }
        return arrayList3;
    }

    private final Either<Set<File>> getHistoryForModuleNames(Path path, Iterable<String> moduleNames, Function1<? super IncrementalModuleEntry, ? extends File> fileLocation) {
        HashSet hashSet = new HashSet();
        Iterator<String> it = moduleNames.iterator();
        while (it.hasNext()) {
            Set<IncrementalModuleEntry> setEmptySet = getModulesInfo().getNameToModules().get(it.next());
            if (setEmptySet == null) {
                setEmptySet = SetsKt.emptySet();
            }
            CollectionsKt.addAll(hashSet, setEmptySet);
        }
        HashSet hashSet2 = hashSet;
        ArrayList arrayList = new ArrayList();
        for (Object obj : hashSet2) {
            Path path2 = Paths.get(((IncrementalModuleEntry) obj).getBuildDir().getAbsolutePath(), new String[0]);
            Intrinsics.checkNotNullExpressionValue(path2, "get(...)");
            if (ModulesApiHistoryKt.isParentOf(path2, path)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return new Either.Error("Unknown module for " + path + " (candidates: " + CollectionsKt.joinToString$default(hashSet2, null, null, null, 0, null, null, 63, null) + ')');
        }
        HashSet hashSet3 = new HashSet();
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            hashSet3.add(fileLocation.invoke((IncrementalModuleEntry) it2.next()));
        }
        return new Either.Success(hashSet3);
    }
}
