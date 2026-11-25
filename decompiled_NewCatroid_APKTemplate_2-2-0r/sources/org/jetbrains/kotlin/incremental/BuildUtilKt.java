package org.jetbrains.kotlin.incremental;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.build.GeneratedFile;
import org.jetbrains.kotlin.build.GeneratedFilesKt;
import org.jetbrains.kotlin.build.GeneratedJvmClass;
import org.jetbrains.kotlin.build.JvmSourceRoot;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.modules.KotlinModuleXmlBuilder;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.progress.CompilationCanceledStatus;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverKt;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: buildUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\"\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u001a\u001c\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u001a,\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u001an\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u00162\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00160\u00072\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00160\u00072\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00072\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00160\u00072\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00160\u00072\b\b\u0002\u0010 \u001a\u00020\n\u001a@\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00160\"2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0006\u0010$\u001a\u00020%2\u000e\b\u0002\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00160\"\u001a:\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00160\"2\u0006\u0010(\u001a\u00020)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u00072\u0006\u0010$\u001a\u00020%2\u000e\b\u0002\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00160\"\u001a\u0010\u0010,\u001a\u00020\u00012\u0006\u0010-\u001a\u00020\u0001H\u0002\u001a.\u0010.\u001a\u00020/2\f\u00100\u001a\b\u0012\u0004\u0012\u0002010\u00072\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\b\u00106\u001a\u0004\u0018\u000107\u001a\"\u00108\u001a\b\u0012\u0004\u0012\u00020\u00040\"2\u0006\u00109\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u001a&\u0010:\u001a\u00020;*\b\u0012\u0004\u0012\u00020=0<2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010$\u001a\u00020%\u001a \u0010:\u001a\u00020;*\u0002052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010$\u001a\u00020%\u001a\u0012\u0010>\u001a\u00020;*\u0002052\u0006\u0010$\u001a\u00020%\u001a.\u0010?\u001a\u00020/*\u00020)2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00160\u00072\f\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00160\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"DELETE_MODULE_FILE_PROPERTY", "", "findSealedSupertypes", "", "Lorg/jetbrains/kotlin/name/FqName;", "fqName", "caches", "", "Lorg/jetbrains/kotlin/incremental/IncrementalCacheCommon;", "isSealed", "", "makeCompileServices", "Lorg/jetbrains/kotlin/config/Services;", "incrementalCaches", "", "Lorg/jetbrains/kotlin/modules/TargetId;", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "compilationCanceledStatus", "Lorg/jetbrains/kotlin/progress/CompilationCanceledStatus;", "makeModuleFile", "Ljava/io/File;", "name", "isTest", ModuleXmlParser.OUTPUT_DIR, "sourcesToCompile", ModuleXmlParser.COMMON_SOURCES, ModuleXmlParser.JAVA_SOURCE_ROOTS, "Lorg/jetbrains/kotlin/build/JvmSourceRoot;", ModuleXmlParser.CLASSPATH, "friendDirs", "isIncrementalMode", "mapClassesFqNamesToFiles", "", "classesFqNames", "reporter", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "excludes", "mapLookupSymbolsToFiles", "lookupStorage", "Lorg/jetbrains/kotlin/incremental/LookupStorage;", "lookupSymbols", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "sanitizeJavaIdentifier", "string", "updateIncrementalCache", "", "generatedFiles", "Lorg/jetbrains/kotlin/build/GeneratedFile;", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", "javaChangesTracker", "Lorg/jetbrains/kotlin/incremental/JavaClassesTrackerImpl;", "withSubtypes", "typeFqName", "getChangedAndImpactedSymbols", "Lorg/jetbrains/kotlin/incremental/DirtyData;", "", "Lorg/jetbrains/kotlin/incremental/ChangeInfo;", "getChangedSymbols", ConfigConstants.CONFIG_KEY_UPDATE, "filesToCompile", "removedFiles", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BuildUtilKt {
    public static final String DELETE_MODULE_FILE_PROPERTY = "kotlin.delete.module.file.after.build";

    public static final File makeModuleFile(String name2, boolean z, File outputDir, Iterable<? extends File> sourcesToCompile, Iterable<? extends File> commonSources, Iterable<JvmSourceRoot> javaSourceRoots, Iterable<? extends File> classpath, Iterable<? extends File> friendDirs, boolean z2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        Intrinsics.checkNotNullParameter(sourcesToCompile, "sourcesToCompile");
        Intrinsics.checkNotNullParameter(commonSources, "commonSources");
        Intrinsics.checkNotNullParameter(javaSourceRoots, "javaSourceRoots");
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(friendDirs, "friendDirs");
        KotlinModuleXmlBuilder kotlinModuleXmlBuilder = new KotlinModuleXmlBuilder();
        String absolutePath = outputDir.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(sourcesToCompile, 10));
        Iterator<? extends File> it = sourcesToCompile.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getAbsoluteFile());
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(commonSources, 10));
        Iterator<? extends File> it2 = commonSources.iterator();
        while (it2.hasNext()) {
            arrayList3.add(it2.next().getAbsoluteFile());
        }
        kotlinModuleXmlBuilder.addModule(name2, absolutePath, arrayList2, javaSourceRoots, classpath, arrayList3, null, ModuleXmlParser.TYPE_PRODUCTION, z, SetsKt.setOf(outputDir), friendDirs, z2);
        File file = Files.createTempFile("kjps", sanitizeJavaIdentifier(name2) + ".script.xml", new FileAttribute[0]).toFile();
        Intrinsics.checkNotNull(file);
        FilesKt.writeText$default(file, kotlinModuleXmlBuilder.asText().toString(), null, 2, null);
        return file;
    }

    private static final String sanitizeJavaIdentifier(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (Character.isJavaIdentifierPart(cCharAt)) {
                if (sb.length() == 0 && !Character.isJavaIdentifierStart(cCharAt)) {
                    sb.append('_');
                }
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final Services makeCompileServices(Map<TargetId, ? extends IncrementalCache> incrementalCaches, LookupTracker lookupTracker, CompilationCanceledStatus compilationCanceledStatus) {
        Intrinsics.checkNotNullParameter(incrementalCaches, "incrementalCaches");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Services.Builder builder = new Services.Builder();
        builder.register(LookupTracker.class, lookupTracker);
        builder.register(IncrementalCompilationComponents.class, new IncrementalCompilationComponentsImpl(incrementalCaches));
        if (compilationCanceledStatus != null) {
            builder.register(CompilationCanceledStatus.class, compilationCanceledStatus);
        }
        return builder.build();
    }

    public static final void updateIncrementalCache(Iterable<? extends GeneratedFile> generatedFiles, IncrementalJvmCache cache, ChangesCollector changesCollector, JavaClassesTrackerImpl javaClassesTrackerImpl) {
        Collection<SerializedJavaClassWithSource> javaClassesUpdates;
        Intrinsics.checkNotNullParameter(generatedFiles, "generatedFiles");
        Intrinsics.checkNotNullParameter(cache, "cache");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        for (GeneratedFile generatedFile : generatedFiles) {
            if (generatedFile instanceof GeneratedJvmClass) {
                cache.saveFileToCache((GeneratedJvmClass) generatedFile, changesCollector);
            } else if (GeneratedFilesKt.isModuleMappingFile(generatedFile.getOutputFile())) {
                cache.saveModuleMappingToCache(generatedFile.getSourceFiles(), generatedFile.getOutputFile());
            }
        }
        if (javaClassesTrackerImpl != null && (javaClassesUpdates = javaClassesTrackerImpl.getJavaClassesUpdates()) != null) {
            for (SerializedJavaClassWithSource serializedJavaClassWithSource : javaClassesUpdates) {
                cache.saveJavaClassProto(serializedJavaClassWithSource.getSource(), serializedJavaClassWithSource.getProto(), changesCollector);
            }
        }
        cache.clearCacheForRemovedClasses(changesCollector);
    }

    public static final void update(LookupStorage lookupStorage, LookupTracker lookupTracker, Iterable<? extends File> filesToCompile, Iterable<? extends File> removedFiles) {
        Intrinsics.checkNotNullParameter(lookupStorage, "<this>");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(filesToCompile, "filesToCompile");
        Intrinsics.checkNotNullParameter(removedFiles, "removedFiles");
        if (!(lookupTracker instanceof LookupTrackerImpl)) {
            throw new AssertionError("Lookup tracker is expected to be LookupTrackerImpl, got " + lookupTracker.getClass());
        }
        lookupStorage.removeLookupsFrom(SequencesKt.plus(CollectionsKt.asSequence(filesToCompile), CollectionsKt.asSequence(removedFiles)));
        LookupTrackerImpl lookupTrackerImpl = (LookupTrackerImpl) lookupTracker;
        MultiMap<LookupSymbol, String> lookups = lookupTrackerImpl.getLookups();
        Set<String> values = lookupTrackerImpl.getPathInterner().getValues();
        Intrinsics.checkNotNullExpressionValue(values, "getValues(...)");
        lookupStorage.addAll(lookups, values);
    }

    public static final DirtyData getChangedSymbols(ChangesCollector changesCollector, ICReporter reporter) {
        Intrinsics.checkNotNullParameter(changesCollector, "<this>");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        return getChangedAndImpactedSymbols(changesCollector.changes(), CollectionsKt.emptyList(), reporter);
    }

    public static final DirtyData getChangedAndImpactedSymbols(ChangesCollector changesCollector, Iterable<? extends IncrementalCacheCommon> caches, ICReporter reporter) {
        Intrinsics.checkNotNullParameter(changesCollector, "<this>");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        return getChangedAndImpactedSymbols(changesCollector.changes(), caches, reporter);
    }

    public static final DirtyData getChangedAndImpactedSymbols(List<? extends ChangeInfo> list, Iterable<? extends IncrementalCacheCommon> caches, ICReporter reporter) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        HashSet hashSet3 = new HashSet();
        for (final ChangeInfo changeInfo : list) {
            ICReporterKt.debug(reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.BuildUtilKt.getChangedAndImpactedSymbols.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Process " + changeInfo;
                }
            });
            if (changeInfo instanceof ChangeInfo.SignatureChanged) {
                boolean areSubclassesAffected = ((ChangeInfo.SignatureChanged) changeInfo).getAreSubclassesAffected();
                FqName fqName = changeInfo.getFqName();
                Collection<FqName> collectionListOf = !areSubclassesAffected ? CollectionsKt.listOf(fqName) : withSubtypes(fqName, caches);
                hashSet2.addAll(collectionListOf);
                for (FqName fqName2 : collectionListOf) {
                    fqName2.isRoot();
                    String strAsString = fqName2.parent().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    String identifier = fqName2.shortName().getIdentifier();
                    Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                    hashSet.add(new LookupSymbol(identifier, strAsString));
                }
            } else if (changeInfo instanceof ChangeInfo.MembersChanged) {
                Set<FqName> setWithSubtypes = withSubtypes(changeInfo.getFqName(), caches);
                hashSet2.addAll(setWithSubtypes);
                for (String str : ((ChangeInfo.MembersChanged) changeInfo).getNames()) {
                    Iterator<T> it = setWithSubtypes.iterator();
                    while (it.hasNext()) {
                        String strAsString2 = ((FqName) it.next()).asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                        hashSet.add(new LookupSymbol(str, strAsString2));
                    }
                }
                for (FqName fqName3 : setWithSubtypes) {
                    String strAsString3 = SamConversionResolverKt.getSAM_LOOKUP_NAME().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
                    String strAsString4 = fqName3.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString4, "asString(...)");
                    hashSet.add(new LookupSymbol(strAsString3, strAsString4));
                }
            } else if (changeInfo instanceof ChangeInfo.ParentsChanged) {
                Iterator<T> it2 = ((ChangeInfo.ParentsChanged) changeInfo).getParentsChanged().iterator();
                while (it2.hasNext()) {
                    hashSet3.addAll(findSealedSupertypes((FqName) it2.next(), caches));
                }
            }
        }
        return new DirtyData(hashSet, hashSet2, hashSet3);
    }

    public static /* synthetic */ Set mapLookupSymbolsToFiles$default(LookupStorage lookupStorage, Iterable iterable, ICReporter iCReporter, Set set, int i, Object obj) {
        if ((i & 8) != 0) {
            set = SetsKt.emptySet();
        }
        return mapLookupSymbolsToFiles(lookupStorage, iterable, iCReporter, set);
    }

    public static final Set<File> mapLookupSymbolsToFiles(LookupStorage lookupStorage, Iterable<LookupSymbol> lookupSymbols, ICReporter reporter, Set<? extends File> excludes) {
        Intrinsics.checkNotNullParameter(lookupStorage, "lookupStorage");
        Intrinsics.checkNotNullParameter(lookupSymbols, "lookupSymbols");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(excludes, "excludes");
        HashSet hashSet = new HashSet();
        for (LookupSymbol lookupSymbol : lookupSymbols) {
            Collection<String> collection = lookupStorage.get(lookupSymbol);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(new File((String) it.next()));
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                if (!excludes.contains((File) obj)) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = arrayList2;
            reporter.reportMarkDirtyMember(arrayList3, lookupSymbol.getScope(), lookupSymbol.getName());
            hashSet.addAll(arrayList3);
        }
        return hashSet;
    }

    public static /* synthetic */ Set mapClassesFqNamesToFiles$default(Iterable iterable, Iterable iterable2, ICReporter iCReporter, Set set, int i, Object obj) {
        if ((i & 8) != 0) {
            set = SetsKt.emptySet();
        }
        return mapClassesFqNamesToFiles(iterable, iterable2, iCReporter, set);
    }

    public static final Set<File> mapClassesFqNamesToFiles(Iterable<? extends IncrementalCacheCommon> caches, Iterable<FqName> classesFqNames, ICReporter reporter, Set<? extends File> excludes) {
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(classesFqNames, "classesFqNames");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(excludes, "excludes");
        HashMap map = new HashMap();
        for (IncrementalCacheCommon incrementalCacheCommon : caches) {
            for (FqName fqName : classesFqNames) {
                File sourceFileIfClass = incrementalCacheCommon.getSourceFileIfClass(fqName);
                if (sourceFileIfClass != null && !excludes.contains(sourceFileIfClass) && !FileUtilsKt.isJavaFile(sourceFileIfClass)) {
                    HashMap map2 = map;
                    Object obj = map2.get(fqName);
                    if (obj == null) {
                        obj = (Set) new HashSet();
                        map2.put(fqName, obj);
                    }
                    ((Set) obj).add(sourceFileIfClass);
                }
            }
        }
        for (Map.Entry entry : map.entrySet()) {
            FqName fqName2 = (FqName) entry.getKey();
            Set set = (Set) entry.getValue();
            String strAsString = fqName2.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            reporter.reportMarkDirtyClass(set, strAsString);
        }
        Collection collectionValues = map.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        return (Set) AddToStdlibKt.flattenTo(collectionValues, new HashSet());
    }

    public static final Collection<FqName> findSealedSupertypes(FqName fqName, Iterable<? extends IncrementalCacheCommon> caches) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(caches, "caches");
        if (isSealed(fqName, caches)) {
            return CollectionsKt.listOf(fqName);
        }
        ArrayList arrayList = new ArrayList();
        for (final IncrementalCacheCommon incrementalCacheCommon : caches) {
            CollectionsKt.addAll(arrayList, SequencesKt.filter(incrementalCacheCommon.getSupertypesOf(fqName), new Function1<FqName, Boolean>() { // from class: org.jetbrains.kotlin.incremental.BuildUtilKt$findSealedSupertypes$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(FqName it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Boolean boolIsSealed = incrementalCacheCommon.isSealed(it);
                    return Boolean.valueOf(boolIsSealed != null ? boolIsSealed.booleanValue() : false);
                }
            }));
        }
        return arrayList;
    }

    public static final Set<FqName> withSubtypes(FqName typeFqName, Iterable<? extends IncrementalCacheCommon> caches) {
        Intrinsics.checkNotNullParameter(typeFqName, "typeFqName");
        Intrinsics.checkNotNullParameter(caches, "caches");
        LinkedHashSet linkedHashSet = new LinkedHashSet(CollectionsKt.listOf(typeFqName));
        final HashSet hashSet = new HashSet();
        while (!linkedHashSet.isEmpty()) {
            Iterator it = linkedHashSet.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            final FqName fqName = (FqName) it.next();
            it.remove();
            Iterator it2 = SequencesKt.filter(SequencesKt.flatMap(CollectionsKt.asSequence(caches), new Function1<IncrementalCacheCommon, Sequence<? extends FqName>>() { // from class: org.jetbrains.kotlin.incremental.BuildUtilKt.withSubtypes.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Sequence<FqName> invoke(IncrementalCacheCommon it3) {
                    Intrinsics.checkNotNullParameter(it3, "it");
                    FqName unprocessedType = fqName;
                    Intrinsics.checkNotNullExpressionValue(unprocessedType, "$unprocessedType");
                    return it3.getSubtypesOf(unprocessedType);
                }
            }), new Function1<FqName, Boolean>() { // from class: org.jetbrains.kotlin.incremental.BuildUtilKt.withSubtypes.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(FqName it3) {
                    Intrinsics.checkNotNullParameter(it3, "it");
                    return Boolean.valueOf(!hashSet.contains(it3));
                }
            }).iterator();
            while (it2.hasNext()) {
                linkedHashSet.add((FqName) it2.next());
            }
            hashSet.add(fqName);
        }
        return hashSet;
    }

    public static final boolean isSealed(FqName fqName, Iterable<? extends IncrementalCacheCommon> caches) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(caches, "caches");
        if ((caches instanceof Collection) && ((Collection) caches).isEmpty()) {
            return false;
        }
        Iterator<? extends IncrementalCacheCommon> it = caches.iterator();
        while (it.hasNext()) {
            Boolean boolIsSealed = it.next().isSealed(fqName);
            if (boolIsSealed != null ? boolIsSealed.booleanValue() : false) {
                return true;
            }
        }
        return false;
    }
}
