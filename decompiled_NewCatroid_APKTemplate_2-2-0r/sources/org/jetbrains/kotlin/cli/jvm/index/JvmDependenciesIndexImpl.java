package org.jetbrains.kotlin.cli.jvm.index;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.apkeditor.Options;
import gnu.trove.THashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.Sequence;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaFileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.containers.IntArrayList;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JvmDependenciesIndexImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0005GHIJKB\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u001c\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00032\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00190\u0003H\u0002J6\u0010)\u001a\u0004\u0018\u00010\u001a2\u0006\u0010*\u001a\u00020\u00132\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00190\u00032\u0006\u0010,\u001a\u00020\u00132\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0003H\u0002JK\u0010-\u001a\u0004\u0018\u0001H.\"\b\b\u0000\u0010.*\u00020/2\u0006\u00100\u001a\u0002012\f\u00102\u001a\b\u0012\u0004\u0012\u000204032\u001a\u00105\u001a\u0016\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u000204\u0012\u0006\u0012\u0004\u0018\u0001H.06H\u0016¢\u0006\u0002\u00107J=\u00108\u001a\u0004\u0018\u0001H.\"\b\b\u0000\u0010.*\u00020/2\u0006\u00109\u001a\u00020:2\u001a\u0010;\u001a\u0016\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u000204\u0012\u0006\u0012\u0004\u0018\u0001H.06H\u0002¢\u0006\u0002\u0010<J>\u0010=\u001a\u0004\u0018\u00010\u001a2\u0006\u0010*\u001a\u00020\u00132\u0006\u0010>\u001a\u00020?2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00190\u00032\u0006\u0010,\u001a\u00020\u00132\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0003H\u0002J8\u0010@\u001a\u00020A2\u0006\u0010>\u001a\u00020?2\f\u00102\u001a\b\u0012\u0004\u0012\u000204032\u0018\u0010B\u001a\u0014\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020C06H\u0016J\u001e\u0010D\u001a\u0004\u0018\u00010\u001a*\u00020\u001a2\u0006\u0010E\u001a\u00020\u00192\u0006\u0010F\u001a\u000204H\u0002R!\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001c\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R1\u0010\u0016\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00180\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001b\u0010\u001cR\u001b\u0010\u001e\u001a\u00020\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u000b\u001a\u0004\b \u0010!R!\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\u000b\u001a\u0004\b$\u0010%¨\u0006L"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl;", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndex;", "_roots", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "(Ljava/util/List;)V", "indexedRoots", "Lkotlin/sequences/Sequence;", "getIndexedRoots", "()Lkotlin/sequences/Sequence;", "indexedRoots$delegate", "Lkotlin/Lazy;", "lastClassSearch", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$FindClassRequest;", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchResult;", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "maxIndex", "", "getMaxIndex", "()I", "packageCache", "", "", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getPackageCache", "()[Ljava/util/Map;", "packageCache$delegate", "rootCache", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$Cache;", "getRootCache", "()Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$Cache;", "rootCache$delegate", PsiTreeChangeEvent.PROP_ROOTS, "getRoots", "()Ljava/util/List;", "roots$delegate", "cachesPath", "path", "doTravelPath", "rootIndex", "packagesPath", "fillCachesAfter", "findClass", ExifInterface.GPS_DIRECTION_TRUE, "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "acceptedRootTypes", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "findClassGivenDirectory", "Lkotlin/Function2;", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/util/Set;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", FirebaseAnalytics.Event.SEARCH, "request", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchRequest;", "handler", "(Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchRequest;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "travelPath", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "traverseDirectoriesInPackage", "", "continueSearch", "", "findChildPackage", "subPackageName", "rootType", "Cache", "FindClassRequest", "SearchRequest", "SearchResult", "TraverseRequest", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDependenciesIndexImpl implements JvmDependenciesIndex {

    /* JADX INFO: renamed from: indexedRoots$delegate, reason: from kotlin metadata */
    private final Lazy indexedRoots;
    private Pair<FindClassRequest, ? extends SearchResult> lastClassSearch;
    private final ReentrantLock lock;

    /* JADX INFO: renamed from: packageCache$delegate, reason: from kotlin metadata */
    private final Lazy packageCache;

    /* JADX INFO: renamed from: rootCache$delegate, reason: from kotlin metadata */
    private final Lazy rootCache;

    /* JADX INFO: renamed from: roots$delegate, reason: from kotlin metadata */
    private final Lazy roots;

    /* JADX INFO: compiled from: JvmDependenciesIndexImpl.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bb\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchRequest;", "", "acceptedRootTypes", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "getAcceptedRootTypes", "()Ljava/util/Set;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private interface SearchRequest {
        Set<JavaRoot.RootType> getAcceptedRootTypes();

        FqName getPackageFqName();
    }

    /* JADX INFO: compiled from: JvmDependenciesIndexImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JavaRoot.RootType.values().length];
            try {
                iArr[JavaRoot.RootType.BINARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JavaRoot.RootType.BINARY_SIG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JavaRoot.RootType.SOURCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public JvmDependenciesIndexImpl(final List<JavaRoot> _roots) {
        Intrinsics.checkNotNullParameter(_roots, "_roots");
        this.lock = new ReentrantLock();
        this.roots = LazyKt.lazy(new Function0<List<? extends JavaRoot>>() { // from class: org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl$roots$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends JavaRoot> invoke() {
                return CollectionsKt.toList(_roots);
            }
        });
        this.rootCache = LazyKt.lazy(new Function0<Cache>() { // from class: org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl$rootCache$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JvmDependenciesIndexImpl.Cache invoke() {
                JvmDependenciesIndexImpl.Cache cache = new JvmDependenciesIndexImpl.Cache();
                JvmDependenciesIndexImpl jvmDependenciesIndexImpl = this.this$0;
                IntRange indices = CollectionsKt.getIndices(jvmDependenciesIndexImpl.getRoots());
                IntArrayList rootIndices = cache.getRootIndices();
                Iterator<Integer> it = indices.iterator();
                while (it.hasNext()) {
                    rootIndices.add(((IntIterator) it).nextInt());
                }
                cache.getRootIndices().add(jvmDependenciesIndexImpl.getMaxIndex());
                cache.getRootIndices().trimToSize();
                return cache;
            }
        });
        this.indexedRoots = LazyKt.lazy(new Function0<Sequence<? extends JavaRoot>>() { // from class: org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl$indexedRoots$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Sequence<? extends JavaRoot> invoke() {
                return CollectionsKt.asSequence(this.this$0.getRoots());
            }
        });
        this.packageCache = LazyKt.lazy(new Function0<THashMap<String, VirtualFile>[]>() { // from class: org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl$packageCache$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final THashMap<String, VirtualFile>[] invoke() {
                int size = this.this$0.getRoots().size();
                THashMap<String, VirtualFile>[] tHashMapArr = new THashMap[size];
                for (int i = 0; i < size; i++) {
                    tHashMapArr[i] = new THashMap<>();
                }
                return tHashMapArr;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<JavaRoot> getRoots() {
        return (List) this.roots.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getMaxIndex() {
        return getRoots().size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: JvmDependenciesIndexImpl.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0011\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0005H\u0086\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\u00020\u0007¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\u0002\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$Cache;", "", "()V", "innerPackageCaches", "Ljava/util/HashMap;", "", "rootIndices", "Lorg/jetbrains/kotlin/com/intellij/util/containers/IntArrayList;", "getRootIndices$annotations", "getRootIndices", "()Lcom/intellij/util/containers/IntArrayList;", "get", "name", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class Cache {
        private final HashMap<String, Cache> innerPackageCaches = new HashMap<>();
        private final IntArrayList rootIndices = new IntArrayList(2);

        public static /* synthetic */ void getRootIndices$annotations() {
        }

        public final Cache get(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            HashMap<String, Cache> map = this.innerPackageCaches;
            Cache cache = map.get(name2);
            if (cache == null) {
                cache = new Cache();
                map.put(name2, cache);
            }
            return cache;
        }

        public final IntArrayList getRootIndices() {
            return this.rootIndices;
        }
    }

    private final Cache getRootCache() {
        return (Cache) this.rootCache.getValue();
    }

    @Override // org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndex
    public Sequence<JavaRoot> getIndexedRoots() {
        return (Sequence) this.indexedRoots.getValue();
    }

    private final Map<String, VirtualFile>[] getPackageCache() {
        return (Map[]) this.packageCache.getValue();
    }

    @Override // org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndex
    public void traverseDirectoriesInPackage(FqName packageFqName, Set<? extends JavaRoot.RootType> acceptedRootTypes, final Function2<? super VirtualFile, ? super JavaRoot.RootType, Boolean> continueSearch) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(acceptedRootTypes, "acceptedRootTypes");
        Intrinsics.checkNotNullParameter(continueSearch, "continueSearch");
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndex
    public <T> T findClass(ClassId classId, Set<? extends JavaRoot.RootType> acceptedRootTypes, Function2<? super VirtualFile, ? super JavaRoot.RootType, ? extends T> findClassGivenDirectory) {
        FindClassRequest first;
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(acceptedRootTypes, "acceptedRootTypes");
        Intrinsics.checkNotNullParameter(findClassGivenDirectory, "findClassGivenDirectory");
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Pair<FindClassRequest, ? extends SearchResult> pair = this.lastClassSearch;
            T tInvoke = null;
            if (!Intrinsics.areEqual((pair == null || (first = pair.getFirst()) == null) ? null : first.getClassId(), classId)) {
                T t = (T) search(new FindClassRequest(classId, acceptedRootTypes), findClassGivenDirectory);
                reentrantLock.unlock();
                return t;
            }
            Pair<FindClassRequest, ? extends SearchResult> pair2 = this.lastClassSearch;
            Intrinsics.checkNotNull(pair2);
            FindClassRequest findClassRequestComponent1 = pair2.component1();
            SearchResult searchResultComponent2 = pair2.component2();
            if (searchResultComponent2 instanceof SearchResult.NotFound) {
                Set setMinus = SetsKt.minus((Set) acceptedRootTypes, (Iterable) findClassRequestComponent1.getAcceptedRootTypes());
                if (!setMinus.isEmpty()) {
                    tInvoke = (T) search(new FindClassRequest(classId, setMinus), findClassGivenDirectory);
                }
            } else if (searchResultComponent2 instanceof SearchResult.Found) {
                if (Intrinsics.areEqual(findClassRequestComponent1.getAcceptedRootTypes(), acceptedRootTypes)) {
                    tInvoke = findClassGivenDirectory.invoke(((SearchResult.Found) searchResultComponent2).getPackageDirectory(), ((SearchResult.Found) searchResultComponent2).getRoot().getType());
                } else {
                    tInvoke = (T) search(new FindClassRequest(classId, acceptedRootTypes), findClassGivenDirectory);
                }
            } else {
                throw new NoWhenBranchMatchedException();
            }
            reentrantLock.unlock();
            return tInvoke;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x00ad  */
    private final <T> T search(SearchRequest request, Function2<? super VirtualFile, ? super JavaRoot.RootType, ? extends T> handler) {
        int i;
        List<Name> listPathSegments = request.getPackageFqName().pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        List<Name> list = listPathSegments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            String identifierOrNullIfSpecial = ((Name) it.next()).getIdentifierOrNullIfSpecial();
            if (identifierOrNullIfSpecial == null) {
                return null;
            }
            arrayList.add(identifierOrNullIfSpecial);
        }
        ArrayList arrayList2 = arrayList;
        List<Cache> listCachesPath = cachesPath(arrayList2);
        int lastIndex = CollectionsKt.getLastIndex(listCachesPath);
        int i2 = -1;
        for (int i3 = -1; i3 < lastIndex; i3 = -1) {
            IntArrayList rootIndices = listCachesPath.get(lastIndex).getRootIndices();
            int size = rootIndices.size();
            int i4 = 0;
            while (i4 < size) {
                int i5 = rootIndices.get(i4);
                if (i5 > i2) {
                    i = i4;
                    VirtualFile virtualFileTravelPath = travelPath(i5, request.getPackageFqName(), arrayList2, lastIndex, listCachesPath);
                    if (virtualFileTravelPath != null) {
                        JavaRoot javaRoot = getRoots().get(i5);
                        if (request.getAcceptedRootTypes().contains(javaRoot.getType())) {
                            T tInvoke = handler.invoke(virtualFileTravelPath, javaRoot.getType());
                            if (tInvoke != null) {
                                if (request instanceof FindClassRequest) {
                                    this.lastClassSearch = new Pair<>(request, new SearchResult.Found(virtualFileTravelPath, javaRoot));
                                }
                                return tInvoke;
                            }
                        }
                    }
                } else {
                    i = i4;
                }
                i4 = i + 1;
            }
            if (!rootIndices.isEmpty()) {
                i2 = rootIndices.get(rootIndices.size() - 1);
            }
            lastIndex--;
        }
        if (request instanceof FindClassRequest) {
            this.lastClassSearch = new Pair<>(request, SearchResult.NotFound.INSTANCE);
        }
        return null;
    }

    private final VirtualFile travelPath(int rootIndex, FqName packageFqName, List<String> packagesPath, int fillCachesAfter, List<Cache> cachesPath) {
        if (rootIndex >= getMaxIndex()) {
            int size = cachesPath.size();
            for (int i = fillCachesAfter + 1; i < size; i++) {
                cachesPath.get(i).getRootIndices().add(getMaxIndex());
                cachesPath.get(i).getRootIndices().trimToSize();
            }
            return null;
        }
        Map<String, VirtualFile> map = getPackageCache()[rootIndex];
        String strAsString = packageFqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        VirtualFile virtualFileDoTravelPath = map.get(strAsString);
        if (virtualFileDoTravelPath == null) {
            virtualFileDoTravelPath = doTravelPath(rootIndex, packagesPath, fillCachesAfter, cachesPath);
            map.put(strAsString, virtualFileDoTravelPath);
        }
        return virtualFileDoTravelPath;
    }

    private final VirtualFile doTravelPath(int rootIndex, List<String> packagesPath, int fillCachesAfter, List<Cache> cachesPath) {
        JavaRoot javaRoot = getRoots().get(rootIndex);
        FqName prefixFqName = javaRoot.getPrefixFqName();
        List<Name> listPathSegments = prefixFqName != null ? prefixFqName.pathSegments() : null;
        VirtualFile file = javaRoot.getFile();
        int size = packagesPath.size();
        int i = 0;
        while (i < size) {
            String str = packagesPath.get(i);
            if (listPathSegments != null && i < listPathSegments.size()) {
                if (!Intrinsics.areEqual(listPathSegments.get(i).getIdentifier(), str)) {
                    return null;
                }
            } else {
                file = findChildPackage(file, str, javaRoot.getType());
                if (file == null) {
                    return null;
                }
            }
            i++;
            if (i > fillCachesAfter) {
                cachesPath.get(i).getRootIndices().add(rootIndex);
            }
        }
        return file;
    }

    private final VirtualFile findChildPackage(VirtualFile virtualFile, String str, JavaRoot.RootType rootType) {
        final String defaultExtension;
        VirtualFile virtualFileFindChild = virtualFile.findChild(str);
        if (virtualFileFindChild == null) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[rootType.ordinal()];
        if (i == 1) {
            defaultExtension = JavaClassFileType.INSTANCE.getDefaultExtension();
        } else if (i == 2) {
            defaultExtension = Options.TYPE_SIG;
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            defaultExtension = JavaFileType.INSTANCE.getDefaultExtension();
        }
        Intrinsics.checkNotNull(defaultExtension);
        VirtualFile virtualFileFindChild2 = virtualFile.findChild(str + '.' + defaultExtension);
        if (virtualFileFindChild2 != null && !virtualFileFindChild2.isDirectory()) {
            final Function1<VirtualFile, Boolean> function1 = new Function1<VirtualFile, Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl.findChildPackage.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(VirtualFile virtualFile2) {
                    return Boolean.valueOf(!Intrinsics.areEqual(virtualFile2.getExtension(), defaultExtension));
                }
            };
            if (VfsUtilCore.processFilesRecursively(virtualFileFindChild, new Processor() { // from class: org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Processor
                public final boolean process(Object obj) {
                    return JvmDependenciesIndexImpl.findChildPackage$lambda$4(function1, obj);
                }
            })) {
                return null;
            }
        }
        return virtualFileFindChild;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean findChildPackage$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private final List<Cache> cachesPath(List<String> path) {
        ArrayList arrayList = new ArrayList(path.size() + 1);
        arrayList.add(getRootCache());
        Cache rootCache = getRootCache();
        Iterator<String> it = path.iterator();
        while (it.hasNext()) {
            rootCache = rootCache.get(it.next());
            arrayList.add(rootCache);
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: JvmDependenciesIndexImpl.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$FindClassRequest;", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchRequest;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "acceptedRootTypes", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/util/Set;)V", "getAcceptedRootTypes", "()Ljava/util/Set;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class FindClassRequest implements SearchRequest {
        private final Set<JavaRoot.RootType> acceptedRootTypes;
        private final ClassId classId;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ FindClassRequest copy$default(FindClassRequest findClassRequest, ClassId classId, Set set, int i, Object obj) {
            if ((i & 1) != 0) {
                classId = findClassRequest.classId;
            }
            if ((i & 2) != 0) {
                set = findClassRequest.acceptedRootTypes;
            }
            return findClassRequest.copy(classId, set);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ClassId getClassId() {
            return this.classId;
        }

        public final Set<JavaRoot.RootType> component2() {
            return this.acceptedRootTypes;
        }

        public final FindClassRequest copy(ClassId classId, Set<? extends JavaRoot.RootType> acceptedRootTypes) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(acceptedRootTypes, "acceptedRootTypes");
            return new FindClassRequest(classId, acceptedRootTypes);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FindClassRequest)) {
                return false;
            }
            FindClassRequest findClassRequest = (FindClassRequest) other;
            return Intrinsics.areEqual(this.classId, findClassRequest.classId) && Intrinsics.areEqual(this.acceptedRootTypes, findClassRequest.acceptedRootTypes);
        }

        public int hashCode() {
            return (this.classId.hashCode() * 31) + this.acceptedRootTypes.hashCode();
        }

        public String toString() {
            return "FindClassRequest(classId=" + this.classId + ", acceptedRootTypes=" + this.acceptedRootTypes + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public FindClassRequest(ClassId classId, Set<? extends JavaRoot.RootType> acceptedRootTypes) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(acceptedRootTypes, "acceptedRootTypes");
            this.classId = classId;
            this.acceptedRootTypes = acceptedRootTypes;
        }

        @Override // org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl.SearchRequest
        public Set<JavaRoot.RootType> getAcceptedRootTypes() {
            return this.acceptedRootTypes;
        }

        public final ClassId getClassId() {
            return this.classId;
        }

        @Override // org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl.SearchRequest
        public FqName getPackageFqName() {
            FqName packageFqName = this.classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            return packageFqName;
        }
    }

    /* JADX INFO: compiled from: JvmDependenciesIndexImpl.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$TraverseRequest;", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchRequest;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "acceptedRootTypes", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/util/Set;)V", "getAcceptedRootTypes", "()Ljava/util/Set;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class TraverseRequest implements SearchRequest {
        private final Set<JavaRoot.RootType> acceptedRootTypes;
        private final FqName packageFqName;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ TraverseRequest copy$default(TraverseRequest traverseRequest, FqName fqName, Set set, int i, Object obj) {
            if ((i & 1) != 0) {
                fqName = traverseRequest.packageFqName;
            }
            if ((i & 2) != 0) {
                set = traverseRequest.acceptedRootTypes;
            }
            return traverseRequest.copy(fqName, set);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FqName getPackageFqName() {
            return this.packageFqName;
        }

        public final Set<JavaRoot.RootType> component2() {
            return this.acceptedRootTypes;
        }

        public final TraverseRequest copy(FqName packageFqName, Set<? extends JavaRoot.RootType> acceptedRootTypes) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(acceptedRootTypes, "acceptedRootTypes");
            return new TraverseRequest(packageFqName, acceptedRootTypes);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TraverseRequest)) {
                return false;
            }
            TraverseRequest traverseRequest = (TraverseRequest) other;
            return Intrinsics.areEqual(this.packageFqName, traverseRequest.packageFqName) && Intrinsics.areEqual(this.acceptedRootTypes, traverseRequest.acceptedRootTypes);
        }

        public int hashCode() {
            return (this.packageFqName.hashCode() * 31) + this.acceptedRootTypes.hashCode();
        }

        public String toString() {
            return "TraverseRequest(packageFqName=" + this.packageFqName + ", acceptedRootTypes=" + this.acceptedRootTypes + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TraverseRequest(FqName packageFqName, Set<? extends JavaRoot.RootType> acceptedRootTypes) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(acceptedRootTypes, "acceptedRootTypes");
            this.packageFqName = packageFqName;
            this.acceptedRootTypes = acceptedRootTypes;
        }

        @Override // org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl.SearchRequest
        public FqName getPackageFqName() {
            return this.packageFqName;
        }

        @Override // org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl.SearchRequest
        public Set<JavaRoot.RootType> getAcceptedRootTypes() {
            return this.acceptedRootTypes;
        }
    }

    /* JADX INFO: compiled from: JvmDependenciesIndexImpl.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchResult;", "", "()V", "Found", "NotFound", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchResult$Found;", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchResult$NotFound;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class SearchResult {
        public /* synthetic */ SearchResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: JvmDependenciesIndexImpl.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchResult$Found;", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchResult;", "packageDirectory", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "root", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "(Lcom/intellij/openapi/vfs/VirtualFile;Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;)V", "getPackageDirectory", "()Lcom/intellij/openapi/vfs/VirtualFile;", "getRoot", "()Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Found extends SearchResult {
            private final VirtualFile packageDirectory;
            private final JavaRoot root;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Found(VirtualFile packageDirectory, JavaRoot root) {
                super(null);
                Intrinsics.checkNotNullParameter(packageDirectory, "packageDirectory");
                Intrinsics.checkNotNullParameter(root, "root");
                this.packageDirectory = packageDirectory;
                this.root = root;
            }

            public final VirtualFile getPackageDirectory() {
                return this.packageDirectory;
            }

            public final JavaRoot getRoot() {
                return this.root;
            }
        }

        private SearchResult() {
        }

        /* JADX INFO: compiled from: JvmDependenciesIndexImpl.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchResult$NotFound;", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndexImpl$SearchResult;", "()V", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class NotFound extends SearchResult {
            public static final NotFound INSTANCE = new NotFound();

            private NotFound() {
                super(null);
            }
        }
    }
}
