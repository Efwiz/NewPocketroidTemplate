package org.jetbrains.kotlin.library.metadata.resolver.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.SearchPathResolver;
import org.jetbrains.kotlin.library.SearchPathResolverKt;
import org.jetbrains.kotlin.library.UnresolvedLibrary;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolver;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary;
import org.jetbrains.kotlin.util.Logger;
import org.jetbrains.kotlin.util.WithLogger;

/* JADX INFO: compiled from: KotlinLibraryResolverImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\u001d\b\u0000\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ4\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00132\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\bH\u0002J4\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00132\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\bH\u0016J\u0016\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002J\u0016\u0010\u001f\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002J\u0018\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00020\u0013*\b\u0012\u0004\u0012\u00020\u00020\u0013H\u0002J\u0018\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00020\u0013*\b\u0012\u0004\u0012\u00020\u00020\u0013H\u0002J\u0012\u0010\"\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00020\u0013H\u0016R\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/resolver/impl/KotlinLibraryResolverImpl;", "L", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolver;", "Lorg/jetbrains/kotlin/util/WithLogger;", "searchPathResolver", "Lorg/jetbrains/kotlin/library/SearchPathResolver;", "resolveManifestDependenciesLenient", "", "(Lorg/jetbrains/kotlin/library/SearchPathResolver;Z)V", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "getLogger", "()Lorg/jetbrains/kotlin/util/Logger;", "getResolveManifestDependenciesLenient", "()Z", "getSearchPathResolver", "()Lorg/jetbrains/kotlin/library/SearchPathResolver;", "findLibraries", "", "unresolvedLibraries", "Lorg/jetbrains/kotlin/library/UnresolvedLibrary;", "noStdLib", "noDefaultLibs", "noEndorsedLibs", "resolveWithoutDependencies", "warnOnLibraryDuplicateNames", "", "duplicatedPaths", "", "", "warnOnLibraryDuplicates", "leaveDistinct", "omitDuplicateNames", "resolveDependencies", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolveResult;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinLibraryResolverImpl<L extends KotlinLibrary> implements KotlinLibraryResolver<L>, WithLogger {
    private final boolean resolveManifestDependenciesLenient;
    private final SearchPathResolver<L> searchPathResolver;

    @Override // org.jetbrains.kotlin.util.WithLogger
    public Logger getLogger() {
        return this.searchPathResolver.getLogger();
    }

    public KotlinLibraryResolverImpl(SearchPathResolver<L> searchPathResolver, boolean z) {
        Intrinsics.checkNotNullParameter(searchPathResolver, "searchPathResolver");
        this.searchPathResolver = searchPathResolver;
        this.resolveManifestDependenciesLenient = z;
    }

    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolver
    public SearchPathResolver<L> getSearchPathResolver() {
        return this.searchPathResolver;
    }

    public final boolean getResolveManifestDependenciesLenient() {
        return this.resolveManifestDependenciesLenient;
    }

    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolver
    public List<KotlinLibrary> resolveWithoutDependencies(List<? extends UnresolvedLibrary> unresolvedLibraries, boolean noStdLib, boolean noDefaultLibs, boolean noEndorsedLibs) {
        Intrinsics.checkNotNullParameter(unresolvedLibraries, "unresolvedLibraries");
        return omitDuplicateNames(leaveDistinct(findLibraries(unresolvedLibraries, noStdLib, noDefaultLibs, noEndorsedLibs)));
    }

    private final List<KotlinLibrary> findLibraries(List<? extends UnresolvedLibrary> unresolvedLibraries, boolean noStdLib, boolean noDefaultLibs, boolean noEndorsedLibs) {
        return CollectionsKt.plus((Collection) SequencesKt.toList(SequencesKt.mapNotNull(CollectionsKt.asSequence(unresolvedLibraries), new Function1<UnresolvedLibrary, L>(this) { // from class: org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImpl$findLibraries$userProvidedLibraries$1
            final /* synthetic */ KotlinLibraryResolverImpl<L> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            /* JADX WARN: Incorrect return type in method signature: (Lorg/jetbrains/kotlin/library/UnresolvedLibrary;)TL; */
            @Override // kotlin.jvm.functions.Function1
            public final KotlinLibrary invoke(UnresolvedLibrary it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return SearchPathResolverKt.resolve(this.this$0.getSearchPathResolver(), it);
            }
        })), (Iterable) getSearchPathResolver().defaultLinks(noStdLib, noDefaultLibs, noEndorsedLibs));
    }

    private final List<KotlinLibrary> leaveDistinct(List<? extends KotlinLibrary> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list) {
            String absolutePath = ((KotlinLibrary) obj).getLibraryFile().getAbsolutePath();
            Object obj2 = linkedHashMap.get(absolutePath);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(absolutePath, obj2);
            }
            ((List) obj2).add(obj);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (((List) entry.getValue()).size() > 1) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        warnOnLibraryDuplicates(linkedHashMap2.keySet());
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add((KotlinLibrary) CollectionsKt.first((List) ((Map.Entry) it.next()).getValue()));
        }
        return arrayList;
    }

    private final List<KotlinLibrary> omitDuplicateNames(List<? extends KotlinLibrary> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list) {
            String uniqueName = KotlinLibraryKt.getUniqueName((KotlinLibrary) obj);
            Object obj2 = linkedHashMap.get(uniqueName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(uniqueName, obj2);
            }
            ((List) obj2).add(obj);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (((List) entry.getValue()).size() > 1) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        warnOnLibraryDuplicateNames(linkedHashMap2.keySet());
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add((KotlinLibrary) CollectionsKt.first((List) ((Map.Entry) it.next()).getValue()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolver
    public KotlinLibraryResolveResult resolveDependencies(List<? extends KotlinLibrary> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<? extends KotlinLibrary> list2 = list;
        List arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(new KotlinResolvedLibraryImpl((KotlinLibrary) it.next()));
        }
        List listFlatten = arrayList;
        KotlinLibraryResolverResultImpl kotlinLibraryResolverResultImpl = new KotlinLibraryResolverResultImpl(listFlatten);
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<KotlinResolvedLibraryImpl> list3 = listFlatten;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        for (KotlinResolvedLibraryImpl kotlinResolvedLibraryImpl : list3) {
            arrayList2.add(TuplesKt.to(kotlinResolvedLibraryImpl.getLibrary().getLibraryFile().getCanonicalFile(), kotlinResolvedLibraryImpl));
        }
        MapsKt.putAll(linkedHashMap, arrayList2);
        do {
            List<KotlinResolvedLibraryImpl> list4 = listFlatten;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
            for (final KotlinResolvedLibraryImpl kotlinResolvedLibraryImpl2 : list4) {
                arrayList3.add(SequencesKt.toList(SequencesKt.filterNotNull(SequencesKt.map(SequencesKt.mapNotNull(SequencesKt.filterNot(CollectionsKt.asSequence(KotlinLibraryKt.unresolvedDependencies(kotlinResolvedLibraryImpl2.getLibrary(), this.resolveManifestDependenciesLenient)), new Function1<UnresolvedLibrary, Boolean>(this) { // from class: org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImpl$resolveDependencies$2$1
                    final /* synthetic */ KotlinLibraryResolverImpl<L> this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                        this.this$0 = this;
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(UnresolvedLibrary it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return Boolean.valueOf(this.this$0.getSearchPathResolver().isProvidedByDefault(it2));
                    }
                }), new Function1<UnresolvedLibrary, KotlinResolvedLibraryImpl>(this) { // from class: org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImpl$resolveDependencies$2$2
                    final /* synthetic */ KotlinLibraryResolverImpl<L> this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                        this.this$0 = this;
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final KotlinResolvedLibraryImpl invoke(UnresolvedLibrary it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        KotlinLibrary kotlinLibraryResolve = SearchPathResolverKt.resolve(this.this$0.getSearchPathResolver(), it2);
                        if (kotlinLibraryResolve != null) {
                            return new KotlinResolvedLibraryImpl(kotlinLibraryResolve);
                        }
                        return null;
                    }
                }), new Function1<KotlinResolvedLibraryImpl, KotlinResolvedLibraryImpl>() { // from class: org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImpl$resolveDependencies$2$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final KotlinResolvedLibraryImpl invoke(KotlinResolvedLibraryImpl resolved) {
                        Intrinsics.checkNotNullParameter(resolved, "resolved");
                        File canonicalFile = resolved.getLibrary().getLibraryFile().getCanonicalFile();
                        if (linkedHashMap.containsKey(canonicalFile)) {
                            KotlinResolvedLibraryImpl kotlinResolvedLibraryImpl3 = kotlinResolvedLibraryImpl2;
                            KotlinResolvedLibrary kotlinResolvedLibrary = linkedHashMap.get(canonicalFile);
                            Intrinsics.checkNotNull(kotlinResolvedLibrary);
                            kotlinResolvedLibraryImpl3.addDependency$kotlin_util_klib_metadata(kotlinResolvedLibrary);
                            return null;
                        }
                        linkedHashMap.put(canonicalFile, resolved);
                        kotlinResolvedLibraryImpl2.addDependency$kotlin_util_klib_metadata(resolved);
                        return resolved;
                    }
                }))));
            }
            listFlatten = CollectionsKt.flatten(arrayList3);
        } while (!listFlatten.isEmpty());
        return kotlinLibraryResolverResultImpl;
    }

    private final void warnOnLibraryDuplicates(Iterable<String> duplicatedPaths) {
        Iterator<String> it = duplicatedPaths.iterator();
        while (it.hasNext()) {
            getLogger().warning("library included more than once: " + it.next());
        }
    }

    private final void warnOnLibraryDuplicateNames(Iterable<String> duplicatedPaths) {
        Iterator<String> it = duplicatedPaths.iterator();
        while (it.hasNext()) {
            getLogger().warning("duplicate library name: " + it.next());
        }
    }
}
