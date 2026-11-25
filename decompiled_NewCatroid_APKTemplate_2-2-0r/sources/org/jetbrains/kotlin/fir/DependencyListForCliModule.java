package org.jetbrains.kotlin.fir;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.reandroid.apk.UncompressedFiles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.deserialization.LibraryPathFilter;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.deserialization.MultipleModuleDataProvider;
import org.jetbrains.kotlin.fir.deserialization.SingleModuleDataProvider;

/* JADX INFO: compiled from: DependencyListForCliModule.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u00112\u00020\u0001:\u0002\u0010\u0011B7\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/DependencyListForCliModule;", "", "regularDependencies", "", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "dependsOnDependencies", "friendsDependencies", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;)V", "getDependsOnDependencies", "()Ljava/util/List;", "getFriendsDependencies", "getModuleDataProvider", "()Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "getRegularDependencies", "Builder", "Companion", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DependencyListForCliModule {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<FirModuleData> dependsOnDependencies;
    private final List<FirModuleData> friendsDependencies;
    private final ModuleDataProvider moduleDataProvider;
    private final List<FirModuleData> regularDependencies;

    /* JADX WARN: Multi-variable type inference failed */
    public DependencyListForCliModule(List<? extends FirModuleData> regularDependencies, List<? extends FirModuleData> dependsOnDependencies, List<? extends FirModuleData> friendsDependencies, ModuleDataProvider moduleDataProvider) {
        Intrinsics.checkNotNullParameter(regularDependencies, "regularDependencies");
        Intrinsics.checkNotNullParameter(dependsOnDependencies, "dependsOnDependencies");
        Intrinsics.checkNotNullParameter(friendsDependencies, "friendsDependencies");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        this.regularDependencies = regularDependencies;
        this.dependsOnDependencies = dependsOnDependencies;
        this.friendsDependencies = friendsDependencies;
        this.moduleDataProvider = moduleDataProvider;
    }

    public final List<FirModuleData> getRegularDependencies() {
        return this.regularDependencies;
    }

    public final List<FirModuleData> getDependsOnDependencies() {
        return this.dependsOnDependencies;
    }

    public final List<FirModuleData> getFriendsDependencies() {
        return this.friendsDependencies;
    }

    public final ModuleDataProvider getModuleDataProvider() {
        return this.moduleDataProvider;
    }

    /* JADX INFO: compiled from: DependencyListForCliModule.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J/\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0019\b\u0002\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000bH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/DependencyListForCliModule$Companion;", "", "()V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/DependencyListForCliModule;", "binaryModuleData", "Lorg/jetbrains/kotlin/fir/BinaryModuleData;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/DependencyListForCliModule$Builder;", "", "Lkotlin/ExtensionFunctionType;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ DependencyListForCliModule build$default(Companion companion, BinaryModuleData binaryModuleData, Function1 init, int i, Object obj) {
            if ((i & 2) != 0) {
                init = new Function1<Builder, Unit>() { // from class: org.jetbrains.kotlin.fir.DependencyListForCliModule$Companion$build$1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(DependencyListForCliModule.Builder builder) {
                        Intrinsics.checkNotNullParameter(builder, "$this$null");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DependencyListForCliModule.Builder builder) {
                        invoke2(builder);
                        return Unit.INSTANCE;
                    }
                };
            }
            Intrinsics.checkNotNullParameter(binaryModuleData, "binaryModuleData");
            Intrinsics.checkNotNullParameter(init, "init");
            Builder builder = new Builder(binaryModuleData);
            init.invoke(builder);
            return builder.build();
        }

        public final DependencyListForCliModule build(BinaryModuleData binaryModuleData, Function1<? super Builder, Unit> init) {
            Intrinsics.checkNotNullParameter(binaryModuleData, "binaryModuleData");
            Intrinsics.checkNotNullParameter(init, "init");
            Builder builder = new Builder(binaryModuleData);
            init.invoke(builder);
            return builder.build();
        }
    }

    /* JADX INFO: compiled from: DependencyListForCliModule.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0010\u001a\u00020\u0011J\u0014\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015J\u001b\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0007¢\u0006\u0002\b\u0017J#\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00072\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0007¢\u0006\u0002\b\u0017J\u001f\u0010\u0019\u001a\u00020\u00132\u0012\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u001b\"\u00020\u000f¢\u0006\u0002\u0010\u001cJ\u001f\u0010\u0019\u001a\u00020\u00132\u0012\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00160\u001b\"\u00020\u0016¢\u0006\u0002\u0010\u001dJ'\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00072\u0012\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u001b\"\u00020\u000f¢\u0006\u0002\u0010\u001eJ\u0014\u0010\u001f\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015J\u001b\u0010\u001f\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0007¢\u0006\u0002\b J\u0014\u0010!\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015J\u001b\u0010!\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0007¢\u0006\u0002\b\"J\u0014\u0010#\u001a\u00020\u00132\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070\u0015J\u0014\u0010%\u001a\u00020\u00132\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070\u0015R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/DependencyListForCliModule$Builder;", "", "binaryModuleData", "Lorg/jetbrains/kotlin/fir/BinaryModuleData;", "(Lorg/jetbrains/kotlin/fir/BinaryModuleData;)V", "allDependsOnDependencies", "", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "allFriendsDependencies", "allRegularDependencies", "getBinaryModuleData", "()Lorg/jetbrains/kotlin/fir/BinaryModuleData;", "filtersMap", "", "", "Ljava/nio/file/Path;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/DependencyListForCliModule;", "dependencies", "", UncompressedFiles.NAME_paths, "", "", "dependenciesString", "moduleData", "dependency", "path", "", "([Ljava/nio/file/Path;)V", "([Ljava/lang/String;)V", "(Lorg/jetbrains/kotlin/fir/FirModuleData;[Ljava/nio/file/Path;)V", "dependsOnDependencies", "dependsOnDependenciesString", "friendDependencies", "friendDependenciesString", "sourceDependsOnDependencies", "modules", "sourceFriendsDependencies", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Builder {
        private final List<FirModuleData> allDependsOnDependencies;
        private final List<FirModuleData> allFriendsDependencies;
        private final List<FirModuleData> allRegularDependencies;
        private final BinaryModuleData binaryModuleData;
        private final Map<FirModuleData, Set<Path>> filtersMap;

        public Builder(BinaryModuleData binaryModuleData) {
            Intrinsics.checkNotNullParameter(binaryModuleData, "binaryModuleData");
            this.binaryModuleData = binaryModuleData;
            this.allRegularDependencies = new ArrayList();
            this.allFriendsDependencies = new ArrayList();
            this.allDependsOnDependencies = new ArrayList();
            List listListOf = CollectionsKt.listOf((Object[]) new FirModuleData[]{binaryModuleData.getDependsOn(), binaryModuleData.getFriends(), binaryModuleData.getRegular()});
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : listListOf) {
                linkedHashMap.put(obj, new LinkedHashSet());
            }
            this.filtersMap = linkedHashMap;
        }

        public final BinaryModuleData getBinaryModuleData() {
            return this.binaryModuleData;
        }

        public final void dependency(Path... path) {
            Intrinsics.checkNotNullParameter(path, "path");
            CollectionsKt.addAll((Collection) MapsKt.getValue(this.filtersMap, this.binaryModuleData.getRegular()), path);
        }

        public final void dependency(FirModuleData moduleData, Path... path) {
            Intrinsics.checkNotNullParameter(moduleData, "moduleData");
            Intrinsics.checkNotNullParameter(path, "path");
            Map<FirModuleData, Set<Path>> map = this.filtersMap;
            LinkedHashSet linkedHashSet = map.get(moduleData);
            if (linkedHashSet == null) {
                this.allRegularDependencies.add(moduleData);
                linkedHashSet = new LinkedHashSet();
                map.put(moduleData, linkedHashSet);
            }
            CollectionsKt.addAll(linkedHashSet, path);
        }

        public final void dependency(String... path) {
            Intrinsics.checkNotNullParameter(path, "path");
            Collection collection = (Collection) MapsKt.getValue(this.filtersMap, this.binaryModuleData.getRegular());
            for (String str : path) {
                collection.add(Paths.get(str, new String[0]));
            }
        }

        public final void dependenciesString(Collection<String> paths) {
            Intrinsics.checkNotNullParameter(paths, "paths");
            Collection collection = (Collection) MapsKt.getValue(this.filtersMap, this.binaryModuleData.getRegular());
            Iterator<T> it = paths.iterator();
            while (it.hasNext()) {
                collection.add(Paths.get((String) it.next(), new String[0]));
            }
        }

        public final void dependenciesString(FirModuleData moduleData, Collection<String> paths) {
            Intrinsics.checkNotNullParameter(moduleData, "moduleData");
            Intrinsics.checkNotNullParameter(paths, "paths");
            Collection<String> collection = paths;
            Map<FirModuleData, Set<Path>> map = this.filtersMap;
            LinkedHashSet linkedHashSet = map.get(moduleData);
            if (linkedHashSet == null) {
                this.allRegularDependencies.add(moduleData);
                linkedHashSet = new LinkedHashSet();
                map.put(moduleData, linkedHashSet);
            }
            Set<Path> set = linkedHashSet;
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                set.add(Paths.get((String) it.next(), new String[0]));
            }
        }

        public final void friendDependenciesString(Collection<String> paths) {
            Intrinsics.checkNotNullParameter(paths, "paths");
            Collection collection = (Collection) MapsKt.getValue(this.filtersMap, this.binaryModuleData.getFriends());
            Iterator<T> it = paths.iterator();
            while (it.hasNext()) {
                collection.add(Paths.get((String) it.next(), new String[0]));
            }
        }

        public final void dependsOnDependenciesString(Collection<String> paths) {
            Intrinsics.checkNotNullParameter(paths, "paths");
            Collection collection = (Collection) MapsKt.getValue(this.filtersMap, this.binaryModuleData.getDependsOn());
            Iterator<T> it = paths.iterator();
            while (it.hasNext()) {
                collection.add(Paths.get((String) it.next(), new String[0]));
            }
        }

        public final void dependencies(Collection<? extends Path> paths) {
            Intrinsics.checkNotNullParameter(paths, "paths");
            CollectionsKt.addAll((Collection) MapsKt.getValue(this.filtersMap, this.binaryModuleData.getRegular()), paths);
        }

        public final void friendDependencies(Collection<? extends Path> paths) {
            Intrinsics.checkNotNullParameter(paths, "paths");
            CollectionsKt.addAll((Collection) MapsKt.getValue(this.filtersMap, this.binaryModuleData.getFriends()), paths);
        }

        public final void dependsOnDependencies(Collection<? extends Path> paths) {
            Intrinsics.checkNotNullParameter(paths, "paths");
            CollectionsKt.addAll((Collection) MapsKt.getValue(this.filtersMap, this.binaryModuleData.getDependsOn()), paths);
        }

        public final void sourceFriendsDependencies(Collection<? extends FirModuleData> modules) {
            Intrinsics.checkNotNullParameter(modules, "modules");
            CollectionsKt.addAll(this.allFriendsDependencies, modules);
        }

        public final void sourceDependsOnDependencies(Collection<? extends FirModuleData> modules) {
            Intrinsics.checkNotNullParameter(modules, "modules");
            CollectionsKt.addAll(this.allDependsOnDependencies, modules);
        }

        public final DependencyListForCliModule build() {
            Map<FirModuleData, Set<Path>> map = this.filtersMap;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<FirModuleData, Set<Path>> entry : map.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            LinkedHashMap linkedHashMap3 = new LinkedHashMap(MapsKt.mapCapacity(linkedHashMap2.size()));
            for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
                linkedHashMap3.put(entry2.getKey(), new LibraryPathFilter.LibraryList((Set) entry2.getValue()));
            }
            Map mutableMap = MapsKt.toMutableMap(linkedHashMap3);
            this.allRegularDependencies.add(this.binaryModuleData.getRegular());
            if (mutableMap.isEmpty()) {
                return new DependencyListForCliModule(this.allRegularDependencies, this.allDependsOnDependencies, this.allFriendsDependencies, new SingleModuleDataProvider(this.binaryModuleData.getRegular()));
            }
            if (mutableMap.containsKey(this.binaryModuleData.getFriends())) {
                this.allFriendsDependencies.add(this.binaryModuleData.getFriends());
            }
            if (mutableMap.containsKey(this.binaryModuleData.getDependsOn())) {
                this.allDependsOnDependencies.add(this.binaryModuleData.getDependsOn());
            }
            MultipleModuleDataProvider multipleModuleDataProvider = new MultipleModuleDataProvider(mutableMap);
            mutableMap.putIfAbsent(this.binaryModuleData.getRegular(), LibraryPathFilter.TakeAll.INSTANCE);
            return new DependencyListForCliModule(this.allRegularDependencies, this.allDependsOnDependencies, this.allFriendsDependencies, multipleModuleDataProvider);
        }
    }
}
