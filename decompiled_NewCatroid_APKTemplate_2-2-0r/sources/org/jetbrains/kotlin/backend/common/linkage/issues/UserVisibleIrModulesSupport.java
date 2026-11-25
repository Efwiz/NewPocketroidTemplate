package org.jetbrains.kotlin.backend.common.linkage.issues;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializerKind;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.IrLibrary;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.utils.ResolvedDependencies;
import org.jetbrains.kotlin.utils.ResolvedDependenciesSupport;
import org.jetbrains.kotlin.utils.ResolvedDependency;
import org.jetbrains.kotlin.utils.ResolvedDependencyArtifactPath;
import org.jetbrains.kotlin.utils.ResolvedDependencyId;
import org.jetbrains.kotlin.utils.ResolvedDependencyVersion;

/* JADX INFO: compiled from: UserVisibleIrModulesSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 /2\u00020\u0001:\u0003/01B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u001bJ\"\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\r0&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001b0\fH\u0016J\"\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\r0)2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001b0\fH\u0004J0\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\r0&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001b0\f2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00120,H\u0014J$\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\r0&*\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020.0&H\u0002R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR!\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u00120\u0011j\b\u0012\u0004\u0012\u00020\u0012`\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u001a*\u00020\u001b8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0018\u0010\u001e\u001a\u00020\u001f*\u00020\u00128TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0018\u0010\"\u001a\u00020\u001f*\u00020\u00128TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010!¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport;", "", "externalDependenciesLoader", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport$ExternalDependenciesLoader;", "(Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport$ExternalDependenciesLoader;)V", "externalDependencies", "Lorg/jetbrains/kotlin/utils/ResolvedDependencies;", "getExternalDependencies", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencies;", "externalDependencies$delegate", "Lkotlin/Lazy;", "externalDependencyModules", "", "Lorg/jetbrains/kotlin/utils/ResolvedDependency;", "getExternalDependencyModules", "()Ljava/util/Collection;", "moduleIdComparator", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "Lkotlin/Comparator;", "getModuleIdComparator", "()Ljava/util/Comparator;", "sourceCodeModuleId", "getSourceCodeModuleId", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "asDeserializedKotlinLibrary", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "getAsDeserializedKotlinLibrary", "(Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;)Lorg/jetbrains/kotlin/library/KotlinLibrary;", "hasSimpleName", "", "getHasSimpleName", "(Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;)Z", "isKotlinLibrary", "getUserVisibleModuleId", "deserializer", "getUserVisibleModules", "", "deserializers", "mergedModules", "", "modulesFromDeserializers", "excludedModuleIds", "", "stampDependenciesWithRequestedVersionEqualToSelectedVersion", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport$ModuleWithUninitializedDependencies;", "Companion", "ExternalDependenciesLoader", "ModuleWithUninitializedDependencies", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class UserVisibleIrModulesSupport {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final UserVisibleIrModulesSupport DEFAULT = new UserVisibleIrModulesSupport(ExternalDependenciesLoader.INSTANCE.getEMPTY());
    public static final String KOTLIN_LIBRARY_PREFIX = "org.jetbrains.kotlin";

    /* JADX INFO: renamed from: externalDependencies$delegate, reason: from kotlin metadata */
    private final Lazy externalDependencies;
    private final Comparator<ResolvedDependencyId> moduleIdComparator;

    public UserVisibleIrModulesSupport(final ExternalDependenciesLoader externalDependenciesLoader) {
        Intrinsics.checkNotNullParameter(externalDependenciesLoader, "externalDependenciesLoader");
        this.externalDependencies = LazyKt.lazy(new Function0<ResolvedDependencies>() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport$externalDependencies$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ResolvedDependencies invoke() {
                return externalDependenciesLoader.load();
            }
        });
        this.moduleIdComparator = new Comparator() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return UserVisibleIrModulesSupport.moduleIdComparator$lambda$18(this.f$0, (ResolvedDependencyId) obj, (ResolvedDependencyId) obj2);
            }
        };
    }

    /* JADX INFO: compiled from: UserVisibleIrModulesSupport.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004J\b\u0010\u0002\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0005À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport$ExternalDependenciesLoader;", "", "load", "Lorg/jetbrains/kotlin/utils/ResolvedDependencies;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ExternalDependenciesLoader {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = Companion.$$INSTANCE;

        ResolvedDependencies load();

        /* JADX INFO: compiled from: UserVisibleIrModulesSupport.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport$ExternalDependenciesLoader$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport$ExternalDependenciesLoader;", "getEMPTY", "()Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport$ExternalDependenciesLoader;", TypedValues.TransitionType.S_FROM, "externalDependenciesFile", "Lorg/jetbrains/kotlin/konan/file/File;", "onMalformedExternalDependencies", "Lkotlin/Function1;", "", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            static final /* synthetic */ Companion $$INSTANCE = new Companion();
            private static final ExternalDependenciesLoader EMPTY = new ExternalDependenciesLoader() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport$ExternalDependenciesLoader$Companion$EMPTY$1
                @Override // org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport.ExternalDependenciesLoader
                public ResolvedDependencies load() {
                    return ResolvedDependencies.INSTANCE.getEMPTY();
                }
            };

            private Companion() {
            }

            public final ExternalDependenciesLoader getEMPTY() {
                return EMPTY;
            }

            public final ExternalDependenciesLoader from(final File externalDependenciesFile, final Function1<? super String, Unit> onMalformedExternalDependencies) {
                Intrinsics.checkNotNullParameter(onMalformedExternalDependencies, "onMalformedExternalDependencies");
                if (externalDependenciesFile != null) {
                    return new ExternalDependenciesLoader() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport$ExternalDependenciesLoader$Companion$from$1
                        @Override // org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport.ExternalDependenciesLoader
                        public ResolvedDependencies load() {
                            if (externalDependenciesFile.getExists()) {
                                byte[] bytes = externalDependenciesFile.readBytes();
                                Intrinsics.checkNotNullExpressionValue(bytes, "readBytes(...)");
                                String str = new String(bytes, Charsets.UTF_8);
                                ResolvedDependenciesSupport resolvedDependenciesSupport = ResolvedDependenciesSupport.INSTANCE;
                                final Function1<String, Unit> function1 = onMalformedExternalDependencies;
                                final File file = externalDependenciesFile;
                                return resolvedDependenciesSupport.deserialize(str, new Function2<Integer, String, Unit>() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport$ExternalDependenciesLoader$Companion$from$1$load$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    /* JADX WARN: Multi-variable type inference failed */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str2) {
                                        invoke(num.intValue(), str2);
                                        return Unit.INSTANCE;
                                    }

                                    public final void invoke(int i, String line) {
                                        Intrinsics.checkNotNullParameter(line, "line");
                                        function1.invoke("Malformed external dependencies at " + file + ':' + i + ": " + line);
                                    }
                                });
                            }
                            return ResolvedDependencies.INSTANCE.getEMPTY();
                        }
                    };
                }
                return EMPTY;
            }
        }
    }

    private final ResolvedDependencies getExternalDependencies() {
        return (ResolvedDependencies) this.externalDependencies.getValue();
    }

    private final Collection<ResolvedDependency> getExternalDependencyModules() {
        return getExternalDependencies().getModules();
    }

    public final ResolvedDependencyId getSourceCodeModuleId() {
        return getExternalDependencies().getSourceCodeModuleId();
    }

    public final ResolvedDependencyId getUserVisibleModuleId(IrModuleDeserializer deserializer) {
        Intrinsics.checkNotNullParameter(deserializer, "deserializer");
        String strAsStringStripSpecialMarkers = deserializer.getModuleFragment().getName().asStringStripSpecialMarkers();
        Intrinsics.checkNotNullExpressionValue(strAsStringStripSpecialMarkers, "asStringStripSpecialMarkers(...)");
        KotlinLibrary asDeserializedKotlinLibrary = getAsDeserializedKotlinLibrary(deserializer);
        return new ResolvedDependencyId(CollectionsKt.listOfNotNull((Object[]) new String[]{strAsStringStripSpecialMarkers, asDeserializedKotlinLibrary != null ? KotlinLibraryKt.getUniqueName(asDeserializedKotlinLibrary) : null}));
    }

    public Map<ResolvedDependencyId, ResolvedDependency> getUserVisibleModules(Collection<? extends IrModuleDeserializer> deserializers) {
        Intrinsics.checkNotNullParameter(deserializers, "deserializers");
        return mergedModules(deserializers);
    }

    protected Map<ResolvedDependencyId, ResolvedDependency> modulesFromDeserializers(Collection<? extends IrModuleDeserializer> deserializers, Set<ResolvedDependencyId> excludedModuleIds) {
        Pair pair;
        Intrinsics.checkNotNullParameter(deserializers, "deserializers");
        Intrinsics.checkNotNullParameter(excludedModuleIds, "excludedModuleIds");
        ArrayList arrayList = new ArrayList();
        for (IrModuleDeserializer irModuleDeserializer : deserializers) {
            ResolvedDependencyId userVisibleModuleId = getUserVisibleModuleId(irModuleDeserializer);
            if (excludedModuleIds.contains(userVisibleModuleId)) {
                pair = null;
            } else {
                ResolvedDependency resolvedDependency = new ResolvedDependency(userVisibleModuleId, false, ResolvedDependencyVersion.INSTANCE.getEMPTY(), MapsKt.hashMapOf(TuplesKt.to(ResolvedDependencyId.INSTANCE.getDEFAULT_SOURCE_CODE_MODULE_ID(), ResolvedDependencyVersion.INSTANCE.getEMPTY())), new HashSet(), 2, null);
                Collection<IrModuleDeserializer> moduleDependencies = irModuleDeserializer.getModuleDependencies();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(moduleDependencies, 10));
                Iterator<T> it = moduleDependencies.iterator();
                while (it.hasNext()) {
                    arrayList2.add(getUserVisibleModuleId((IrModuleDeserializer) it.next()));
                }
                pair = TuplesKt.to(userVisibleModuleId, new ModuleWithUninitializedDependencies(resolvedDependency, arrayList2));
            }
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return stampDependenciesWithRequestedVersionEqualToSelectedVersion(MapsKt.toMap(arrayList));
    }

    protected final Map<ResolvedDependencyId, ResolvedDependency> mergedModules(Collection<? extends IrModuleDeserializer> deserializers) {
        ResolvedDependencyId id2;
        ResolvedDependency resolvedDependency;
        ResolvedDependencyId id3;
        Intrinsics.checkNotNullParameter(deserializers, "deserializers");
        HashMap map = new HashMap();
        for (ResolvedDependency resolvedDependency2 : getExternalDependencyModules()) {
            Iterator<T> it = resolvedDependency2.getId().getUniqueNames().iterator();
            while (it.hasNext()) {
                map.put((String) it.next(), resolvedDependency2);
            }
        }
        HashMap map2 = map;
        HashMap map3 = new HashMap();
        for (ResolvedDependency resolvedDependency3 : getExternalDependencyModules()) {
            Iterator<T> it2 = resolvedDependency3.getArtifactPaths().iterator();
            while (it2.hasNext()) {
                map3.put((ResolvedDependencyArtifactPath) it2.next(), resolvedDependency3);
            }
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<ResolvedDependencyId, ResolvedDependency> entry : modulesFromDeserializers(deserializers, SetsKt.setOf(getSourceCodeModuleId())).entrySet()) {
            ResolvedDependencyId key = entry.getKey();
            ResolvedDependency value = entry.getValue();
            ResolvedDependency resolvedDependencyMergedModules$findMatchingExternalDependencyModule = mergedModules$findMatchingExternalDependencyModule(map2, key);
            if (resolvedDependencyMergedModules$findMatchingExternalDependencyModule == null) {
                Iterator<T> it3 = value.getArtifactPaths().iterator();
                do {
                    if (!it3.hasNext()) {
                        resolvedDependency = null;
                        break;
                    }
                    resolvedDependency = (ResolvedDependency) map3.get((ResolvedDependencyArtifactPath) it3.next());
                } while (resolvedDependency == null);
                ResolvedDependencyVersion selectedVersion = resolvedDependency != null ? resolvedDependency.getSelectedVersion() : null;
                if (selectedVersion != null) {
                    value.setSelectedVersion(selectedVersion);
                    Map<ResolvedDependencyId, ResolvedDependencyVersion> requestedVersionsByIncomingDependencies = value.getRequestedVersionsByIncomingDependencies();
                    ArrayList arrayList2 = new ArrayList();
                    for (Map.Entry<ResolvedDependencyId, ResolvedDependencyVersion> entry2 : requestedVersionsByIncomingDependencies.entrySet()) {
                        ResolvedDependencyId key2 = entry2.getKey();
                        if (!entry2.getValue().isEmpty()) {
                            key2 = null;
                        }
                        if (key2 != null) {
                            arrayList2.add(key2);
                        }
                    }
                    Iterator it4 = arrayList2.iterator();
                    while (it4.hasNext()) {
                        value.getRequestedVersionsByIncomingDependencies().put((ResolvedDependencyId) it4.next(), selectedVersion);
                    }
                } else if (value.getRequestedVersionsByIncomingDependencies().isEmpty()) {
                    value.getRequestedVersionsByIncomingDependencies().put(getSourceCodeModuleId(), value.getSelectedVersion());
                }
                Map<ResolvedDependencyId, ResolvedDependencyVersion> requestedVersionsByIncomingDependencies2 = value.getRequestedVersionsByIncomingDependencies();
                ArrayList<Triple> arrayList3 = new ArrayList();
                for (Map.Entry<ResolvedDependencyId, ResolvedDependencyVersion> entry3 : requestedVersionsByIncomingDependencies2.entrySet()) {
                    ResolvedDependencyId key3 = entry3.getKey();
                    ResolvedDependencyVersion value2 = entry3.getValue();
                    ResolvedDependency resolvedDependencyMergedModules$findMatchingExternalDependencyModule2 = mergedModules$findMatchingExternalDependencyModule(map2, key3);
                    Triple triple = (resolvedDependencyMergedModules$findMatchingExternalDependencyModule2 == null || (id3 = resolvedDependencyMergedModules$findMatchingExternalDependencyModule2.getId()) == null) ? null : new Triple(key3, id3, value2);
                    if (triple != null) {
                        arrayList3.add(triple);
                    }
                }
                for (Triple triple2 : arrayList3) {
                    ResolvedDependencyId resolvedDependencyId = (ResolvedDependencyId) triple2.component1();
                    ResolvedDependencyId resolvedDependencyId2 = (ResolvedDependencyId) triple2.component2();
                    ResolvedDependencyVersion resolvedDependencyVersion = (ResolvedDependencyVersion) triple2.component3();
                    value.getRequestedVersionsByIncomingDependencies().remove(resolvedDependencyId);
                    value.getRequestedVersionsByIncomingDependencies().put(resolvedDependencyId2, resolvedDependencyVersion);
                }
                arrayList.add(value);
            } else {
                for (Map.Entry<ResolvedDependencyId, ResolvedDependencyVersion> entry4 : value.getRequestedVersionsByIncomingDependencies().entrySet()) {
                    ResolvedDependencyId key4 = entry4.getKey();
                    ResolvedDependencyVersion value3 = entry4.getValue();
                    ResolvedDependency resolvedDependencyMergedModules$findMatchingExternalDependencyModule3 = mergedModules$findMatchingExternalDependencyModule(map2, key4);
                    if (resolvedDependencyMergedModules$findMatchingExternalDependencyModule3 != null && (id2 = resolvedDependencyMergedModules$findMatchingExternalDependencyModule3.getId()) != null) {
                        key4 = id2;
                    }
                    if (!resolvedDependencyMergedModules$findMatchingExternalDependencyModule.getRequestedVersionsByIncomingDependencies().containsKey(key4)) {
                        resolvedDependencyMergedModules$findMatchingExternalDependencyModule.getRequestedVersionsByIncomingDependencies().put(key4, value3);
                    }
                }
            }
        }
        List listPlus = CollectionsKt.plus((Collection) getExternalDependencyModules(), (Iterable) arrayList);
        HashMap map4 = new HashMap();
        for (Object obj : listPlus) {
            map4.put(((ResolvedDependency) obj).getId(), obj);
        }
        return map4;
    }

    private static final ResolvedDependency mergedModules$findMatchingExternalDependencyModule(Map<String, ResolvedDependency> map, ResolvedDependencyId resolvedDependencyId) {
        Iterator<T> it = resolvedDependencyId.getUniqueNames().iterator();
        while (it.hasNext()) {
            ResolvedDependency resolvedDependency = map.get((String) it.next());
            if (resolvedDependency != null) {
                return resolvedDependency;
            }
        }
        return null;
    }

    /* JADX INFO: compiled from: UserVisibleIrModulesSupport.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0084\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport$ModuleWithUninitializedDependencies;", "", "module", "Lorg/jetbrains/kotlin/utils/ResolvedDependency;", "outgoingDependencyIds", "", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "(Lorg/jetbrains/kotlin/utils/ResolvedDependency;Ljava/util/List;)V", "getModule", "()Lorg/jetbrains/kotlin/utils/ResolvedDependency;", "getOutgoingDependencyIds", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final /* data */ class ModuleWithUninitializedDependencies {
        private final ResolvedDependency module;
        private final List<ResolvedDependencyId> outgoingDependencyIds;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ModuleWithUninitializedDependencies copy$default(ModuleWithUninitializedDependencies moduleWithUninitializedDependencies, ResolvedDependency resolvedDependency, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                resolvedDependency = moduleWithUninitializedDependencies.module;
            }
            if ((i & 2) != 0) {
                list = moduleWithUninitializedDependencies.outgoingDependencyIds;
            }
            return moduleWithUninitializedDependencies.copy(resolvedDependency, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ResolvedDependency getModule() {
            return this.module;
        }

        public final List<ResolvedDependencyId> component2() {
            return this.outgoingDependencyIds;
        }

        public final ModuleWithUninitializedDependencies copy(ResolvedDependency module, List<ResolvedDependencyId> outgoingDependencyIds) {
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(outgoingDependencyIds, "outgoingDependencyIds");
            return new ModuleWithUninitializedDependencies(module, outgoingDependencyIds);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ModuleWithUninitializedDependencies)) {
                return false;
            }
            ModuleWithUninitializedDependencies moduleWithUninitializedDependencies = (ModuleWithUninitializedDependencies) other;
            return Intrinsics.areEqual(this.module, moduleWithUninitializedDependencies.module) && Intrinsics.areEqual(this.outgoingDependencyIds, moduleWithUninitializedDependencies.outgoingDependencyIds);
        }

        public int hashCode() {
            return (this.module.hashCode() * 31) + this.outgoingDependencyIds.hashCode();
        }

        public String toString() {
            return "ModuleWithUninitializedDependencies(module=" + this.module + ", outgoingDependencyIds=" + this.outgoingDependencyIds + ')';
        }

        public ModuleWithUninitializedDependencies(ResolvedDependency module, List<ResolvedDependencyId> outgoingDependencyIds) {
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(outgoingDependencyIds, "outgoingDependencyIds");
            this.module = module;
            this.outgoingDependencyIds = outgoingDependencyIds;
        }

        public final ResolvedDependency getModule() {
            return this.module;
        }

        public final List<ResolvedDependencyId> getOutgoingDependencyIds() {
            return this.outgoingDependencyIds;
        }
    }

    protected final KotlinLibrary getAsDeserializedKotlinLibrary(IrModuleDeserializer irModuleDeserializer) {
        Intrinsics.checkNotNullParameter(irModuleDeserializer, "<this>");
        if (irModuleDeserializer.getKind() != IrModuleDeserializerKind.DESERIALIZED) {
            return null;
        }
        IrLibrary klib = irModuleDeserializer.getKlib();
        if (klib instanceof KotlinLibrary) {
            return (KotlinLibrary) klib;
        }
        return null;
    }

    public final Comparator<ResolvedDependencyId> getModuleIdComparator() {
        return this.moduleIdComparator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:25:0x0056  */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:27:0x006a
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    public static final int moduleIdComparator$lambda$18(org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport r4, org.jetbrains.kotlin.utils.ResolvedDependencyId r5, org.jetbrains.kotlin.utils.ResolvedDependencyId r6) {
        /*
            java.lang.String r0 = "this$0"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
            r1 = 0
            if (r0 == 0) goto Le
            goto L93
        Le:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            boolean r0 = r4.isKotlinLibrary(r5)
            r2 = 1
            if (r0 == 0) goto L24
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            boolean r0 = r4.isKotlinLibrary(r6)
            if (r0 != 0) goto L24
        L21:
            r1 = r2
            goto L93
        L24:
            boolean r0 = r4.isKotlinLibrary(r5)
            r3 = -1
            if (r0 != 0) goto L36
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            boolean r0 = r4.isKotlinLibrary(r6)
            if (r0 == 0) goto L36
        L34:
            r1 = r3
            goto L93
        L36:
            boolean r0 = r4.getHasSimpleName(r5)
            if (r0 == 0) goto L46
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            boolean r0 = r4.getHasSimpleName(r6)
            if (r0 != 0) goto L46
            goto L34
        L46:
            boolean r0 = r4.getHasSimpleName(r5)
            if (r0 != 0) goto L56
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            boolean r4 = r4.getHasSimpleName(r6)
            if (r4 == 0) goto L56
            goto L21
        L56:
            java.util.Set r4 = r5.getUniqueNames()
            java.util.Iterator r4 = r4.iterator()
            java.util.Set r5 = r6.getUniqueNames()
            java.util.Iterator r5 = r5.iterator()
        L66:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L85
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L85
            java.lang.Object r6 = r4.next()
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r0 = r5.next()
            java.lang.String r0 = (java.lang.String) r0
            int r6 = r6.compareTo(r0)
            if (r6 == 0) goto L66
            return r6
        L85:
            boolean r4 = r4.hasNext()
            if (r4 == 0) goto L8c
            goto L21
        L8c:
            boolean r4 = r5.hasNext()
            if (r4 == 0) goto L93
            goto L34
        L93:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport.moduleIdComparator$lambda$18(org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport, org.jetbrains.kotlin.utils.ResolvedDependencyId, org.jetbrains.kotlin.utils.ResolvedDependencyId):int");
    }

    protected boolean isKotlinLibrary(ResolvedDependencyId resolvedDependencyId) {
        Intrinsics.checkNotNullParameter(resolvedDependencyId, "<this>");
        Set<String> uniqueNames = resolvedDependencyId.getUniqueNames();
        if ((uniqueNames instanceof Collection) && uniqueNames.isEmpty()) {
            return false;
        }
        Iterator<T> it = uniqueNames.iterator();
        while (it.hasNext()) {
            if (StringsKt.startsWith$default((String) it.next(), KOTLIN_LIBRARY_PREFIX, false, 2, (Object) null)) {
                return true;
            }
        }
        return false;
    }

    protected boolean getHasSimpleName(ResolvedDependencyId resolvedDependencyId) {
        Intrinsics.checkNotNullParameter(resolvedDependencyId, "<this>");
        Set<String> uniqueNames = resolvedDependencyId.getUniqueNames();
        if ((uniqueNames instanceof Collection) && uniqueNames.isEmpty()) {
            return true;
        }
        for (String str : uniqueNames) {
            for (int i = 0; i < str.length(); i++) {
                char cCharAt = str.charAt(i);
                if (cCharAt == '.' || cCharAt == ':') {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX INFO: compiled from: UserVisibleIrModulesSupport.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport;", "getDEFAULT", "()Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport;", "KOTLIN_LIBRARY_PREFIX", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final UserVisibleIrModulesSupport getDEFAULT() {
            return UserVisibleIrModulesSupport.DEFAULT;
        }
    }

    private final Map<ResolvedDependencyId, ResolvedDependency> stampDependenciesWithRequestedVersionEqualToSelectedVersion(Map<ResolvedDependencyId, ModuleWithUninitializedDependencies> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
        Iterator<T> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            ResolvedDependencyId resolvedDependencyId = (ResolvedDependencyId) entry.getKey();
            ModuleWithUninitializedDependencies moduleWithUninitializedDependencies = (ModuleWithUninitializedDependencies) entry.getValue();
            ResolvedDependency module = moduleWithUninitializedDependencies.getModule();
            Iterator<T> it2 = moduleWithUninitializedDependencies.component2().iterator();
            while (it2.hasNext()) {
                ResolvedDependency module2 = ((ModuleWithUninitializedDependencies) MapsKt.getValue(map, (ResolvedDependencyId) it2.next())).getModule();
                module2.getRequestedVersionsByIncomingDependencies().put(resolvedDependencyId, module2.getSelectedVersion());
            }
            linkedHashMap.put(key, module);
        }
        return linkedHashMap;
    }
}
