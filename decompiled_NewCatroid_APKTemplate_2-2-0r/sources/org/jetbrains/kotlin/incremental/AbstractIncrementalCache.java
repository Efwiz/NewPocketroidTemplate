package org.jetbrains.kotlin.incremental;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.com.intellij.util.io.EnumeratorStringDescriptor;
import org.jetbrains.kotlin.incremental.storage.AbstractDirtyClassesMap;
import org.jetbrains.kotlin.incremental.storage.AbstractSourceToOutputMap;
import org.jetbrains.kotlin.incremental.storage.BasicMapsOwner;
import org.jetbrains.kotlin.incremental.storage.BasicStringMap;
import org.jetbrains.kotlin.incremental.storage.ClassAttributesMap;
import org.jetbrains.kotlin.incremental.storage.ComplementarySourceFilesMap;
import org.jetbrains.kotlin.incremental.storage.ICClassesAttributes;
import org.jetbrains.kotlin.incremental.storage.LazyStorage;
import org.jetbrains.kotlin.incremental.storage.PathStringDescriptor;
import org.jetbrains.kotlin.incremental.storage.SubtypesMap;
import org.jetbrains.kotlin.incremental.storage.SupertypesMap;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;

/* JADX INFO: compiled from: AbstractIncrementalCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 I*\u0004\b\u0000\u0010\u00012\u00020\u00022\u00020\u0003:\u0002HIB\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0014\u0010(\u001a\u00020)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000J\u001a\u0010+\u001a\u00020)2\u0006\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\u0005H\u0004J\u001c\u0010/\u001a\b\u0012\u0004\u0012\u000201002\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u00050#H\u0016J\u001c\u00103\u001a\b\u0012\u0004\u0012\u00020\u0005002\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u000500H\u0016J\u0012\u00105\u001a\u0004\u0018\u00010\u00052\u0006\u00106\u001a\u000201H\u0016J\u0016\u00107\u001a\b\u0012\u0004\u0012\u000201082\u0006\u00109\u001a\u000201H\u0016J\u0016\u0010:\u001a\b\u0012\u0004\u0012\u000201082\u0006\u00109\u001a\u000201H\u0016J\u0017\u0010;\u001a\u0004\u0018\u00010<2\u0006\u00109\u001a\u000201H\u0016¢\u0006\u0002\u0010=J\u0013\u0010>\u001a\u00020)2\u0006\u00109\u001a\u00028\u0000¢\u0006\u0002\u0010?J\u0016\u0010>\u001a\u00020)2\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000500H\u0016J\u001e\u0010A\u001a\u00020)2\f\u0010B\u001a\b\u0012\u0004\u0012\u000201002\u0006\u0010C\u001a\u00020DH\u0004J\u001e\u0010E\u001a\u00020)2\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u0005002\u0006\u0010F\u001a\u00020GH\u0016R\u0014\u0010\t\u001a\u00020\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000eX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0013\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00000\u0014j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0000`\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017X \u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0018\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u001bX \u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R'\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00000#8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b$\u0010%¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/incremental/AbstractIncrementalCache;", "ClassName", "Lorg/jetbrains/kotlin/incremental/storage/BasicMapsOwner;", "Lorg/jetbrains/kotlin/incremental/IncrementalCacheCommon;", "workingDir", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "classAttributesMap", "Lorg/jetbrains/kotlin/incremental/storage/ClassAttributesMap;", "getClassAttributesMap$kotlin_build_common", "()Lorg/jetbrains/kotlin/incremental/storage/ClassAttributesMap;", "classFqNameToSourceMap", "Lorg/jetbrains/kotlin/incremental/AbstractIncrementalCache$ClassFqNameToSourceMap;", "getClassFqNameToSourceMap", "()Lorg/jetbrains/kotlin/incremental/AbstractIncrementalCache$ClassFqNameToSourceMap;", "complementaryFilesMap", "Lorg/jetbrains/kotlin/incremental/storage/ComplementarySourceFilesMap;", "dependents", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "dirtyOutputClassesMap", "Lorg/jetbrains/kotlin/incremental/storage/AbstractDirtyClassesMap;", "getDirtyOutputClassesMap$kotlin_build_common", "()Lorg/jetbrains/kotlin/incremental/storage/AbstractDirtyClassesMap;", "sourceToClassesMap", "Lorg/jetbrains/kotlin/incremental/storage/AbstractSourceToOutputMap;", "getSourceToClassesMap$kotlin_build_common", "()Lorg/jetbrains/kotlin/incremental/storage/AbstractSourceToOutputMap;", "subtypesMap", "Lorg/jetbrains/kotlin/incremental/storage/SubtypesMap;", "supertypesMap", "Lorg/jetbrains/kotlin/incremental/storage/SupertypesMap;", "thisWithDependentCaches", "", "getThisWithDependentCaches", "()Ljava/lang/Iterable;", "thisWithDependentCaches$delegate", "Lkotlin/Lazy;", "addDependentCache", "", ResponseCacheMiddleware.CACHE, "addToClassStorage", "classProtoData", "Lorg/jetbrains/kotlin/incremental/ClassProtoData;", "srcFile", "classesFqNamesBySources", "", "Lorg/jetbrains/kotlin/name/FqName;", "files", "getComplementaryFilesRecursive", "dirtyFiles", "getSourceFileIfClass", "fqName", "getSubtypesOf", "Lkotlin/sequences/Sequence;", "className", "getSupertypesOf", "isSealed", "", "(Lorg/jetbrains/kotlin/name/FqName;)Ljava/lang/Boolean;", "markDirty", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "removedAndCompiledSources", "removeAllFromClassStorage", "removedClasses", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", "updateComplementaryFiles", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/ExpectActualTrackerImpl;", "ClassFqNameToSourceMap", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractIncrementalCache<ClassName> extends BasicMapsOwner implements IncrementalCacheCommon {
    private final ClassAttributesMap classAttributesMap;
    private final ClassFqNameToSourceMap classFqNameToSourceMap;
    private final ComplementarySourceFilesMap complementaryFilesMap;
    private final ArrayList<AbstractIncrementalCache<ClassName>> dependents;
    private final SubtypesMap subtypesMap;
    private final SupertypesMap supertypesMap;

    /* JADX INFO: renamed from: thisWithDependentCaches$delegate, reason: from kotlin metadata */
    private final Lazy thisWithDependentCaches;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String SOURCE_TO_CLASSES = "source-to-classes";
    private static final String DIRTY_OUTPUT_CLASSES = "dirty-output-classes";

    protected static final String getDIRTY_OUTPUT_CLASSES() {
        return INSTANCE.getDIRTY_OUTPUT_CLASSES();
    }

    protected static final String getSOURCE_TO_CLASSES() {
        return INSTANCE.getSOURCE_TO_CLASSES();
    }

    public abstract AbstractDirtyClassesMap<ClassName> getDirtyOutputClassesMap$kotlin_build_common();

    public abstract AbstractSourceToOutputMap<ClassName> getSourceToClassesMap$kotlin_build_common();

    /* JADX INFO: compiled from: AbstractIncrementalCache.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u00020\u00048\u0004X\u0085D¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\u0002\u001a\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u00020\u00048\u0004X\u0085D¢\u0006\u000e\n\u0000\u0012\u0004\b\f\u0010\u0002\u001a\u0004\b\r\u0010\nR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/incremental/AbstractIncrementalCache$Companion;", "", "()V", "CLASS_ATTRIBUTES", "", "CLASS_FQ_NAME_TO_SOURCE", "COMPLEMENTARY_FILES", "DIRTY_OUTPUT_CLASSES", "getDIRTY_OUTPUT_CLASSES$annotations", "getDIRTY_OUTPUT_CLASSES", "()Ljava/lang/String;", "SOURCE_TO_CLASSES", "getSOURCE_TO_CLASSES$annotations", "getSOURCE_TO_CLASSES", "SUBTYPES", "SUPERTYPES", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        protected static /* synthetic */ void getDIRTY_OUTPUT_CLASSES$annotations() {
        }

        @JvmStatic
        protected static /* synthetic */ void getSOURCE_TO_CLASSES$annotations() {
        }

        private Companion() {
        }

        protected final String getSOURCE_TO_CLASSES() {
            return AbstractIncrementalCache.SOURCE_TO_CLASSES;
        }

        protected final String getDIRTY_OUTPUT_CLASSES() {
            return AbstractIncrementalCache.DIRTY_OUTPUT_CLASSES;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractIncrementalCache(File workingDir, IncrementalCompilationContext icContext) {
        super(workingDir);
        Intrinsics.checkNotNullParameter(workingDir, "workingDir");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        this.dependents = new ArrayList<>();
        this.thisWithDependentCaches = LazyKt.lazy(new Function0<ArrayList<AbstractIncrementalCache<ClassName>>>(this) { // from class: org.jetbrains.kotlin.incremental.AbstractIncrementalCache$thisWithDependentCaches$2
            final /* synthetic */ AbstractIncrementalCache<ClassName> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final ArrayList<AbstractIncrementalCache<ClassName>> invoke() {
                ArrayList<AbstractIncrementalCache<ClassName>> arrayListArrayListOf = CollectionsKt.arrayListOf(this.this$0);
                arrayListArrayListOf.addAll(((AbstractIncrementalCache) this.this$0).dependents);
                return arrayListArrayListOf;
            }
        });
        this.classAttributesMap = (ClassAttributesMap) registerMap(new ClassAttributesMap(getStorageFile("class-attributes"), icContext));
        this.subtypesMap = (SubtypesMap) registerMap(new SubtypesMap(getStorageFile("subtypes"), icContext));
        this.supertypesMap = (SupertypesMap) registerMap(new SupertypesMap(getStorageFile("supertypes"), icContext));
        this.classFqNameToSourceMap = (ClassFqNameToSourceMap) registerMap(new ClassFqNameToSourceMap(getStorageFile("class-fq-name-to-source"), icContext));
        this.complementaryFilesMap = (ComplementarySourceFilesMap) registerMap(new ComplementarySourceFilesMap(getStorageFile("complementary-files"), icContext));
    }

    public final void addDependentCache(AbstractIncrementalCache<ClassName> cache) {
        Intrinsics.checkNotNullParameter(cache, "cache");
        this.dependents.add(cache);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public Iterable<AbstractIncrementalCache<ClassName>> getThisWithDependentCaches() {
        return (Iterable) this.thisWithDependentCaches.getValue();
    }

    /* JADX INFO: renamed from: getClassAttributesMap$kotlin_build_common, reason: from getter */
    public final ClassAttributesMap getClassAttributesMap() {
        return this.classAttributesMap;
    }

    protected final ClassFqNameToSourceMap getClassFqNameToSourceMap() {
        return this.classFqNameToSourceMap;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public Collection<FqName> classesFqNamesBySources(Iterable<? extends File> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        HashSet hashSet = new HashSet();
        Iterator<? extends File> it = files.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(hashSet, getSourceToClassesMap$kotlin_build_common().getFqNames(it.next()));
        }
        return hashSet;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public Sequence<FqName> getSubtypesOf(FqName className) {
        Intrinsics.checkNotNullParameter(className, "className");
        return CollectionsKt.asSequence(this.subtypesMap.get(className));
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public Sequence<FqName> getSupertypesOf(FqName className) {
        Intrinsics.checkNotNullParameter(className, "className");
        return CollectionsKt.asSequence(this.supertypesMap.get(className));
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public Boolean isSealed(FqName className) {
        Intrinsics.checkNotNullParameter(className, "className");
        ICClassesAttributes iCClassesAttributes = this.classAttributesMap.get(className);
        if (iCClassesAttributes != null) {
            return Boolean.valueOf(iCClassesAttributes.isSealed());
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public File getSourceFileIfClass(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.classFqNameToSourceMap.get(fqName);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public void markDirty(Collection<? extends File> removedAndCompiledSources) {
        Intrinsics.checkNotNullParameter(removedAndCompiledSources, "removedAndCompiledSources");
        for (File file : removedAndCompiledSources) {
            Iterator it = getSourceToClassesMap$kotlin_build_common().get(file).iterator();
            while (it.hasNext()) {
                markDirty(it.next());
            }
            getSourceToClassesMap$kotlin_build_common().clearOutputsForSource(file);
        }
    }

    public final void markDirty(ClassName className) {
        getDirtyOutputClassesMap$kotlin_build_common().markDirty(className);
    }

    protected final void addToClassStorage(ClassProtoData classProtoData, File srcFile) {
        Intrinsics.checkNotNullParameter(classProtoData, "classProtoData");
        ProtoBuf.Class proto = classProtoData.getProto();
        NameResolver nameResolver = classProtoData.getNameResolver();
        ProtoBuf.TypeTable typeTable = proto.getTypeTable();
        Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
        List<ProtoBuf.Type> listSupertypes = ProtoTypeTableUtilKt.supertypes(proto, new TypeTable(typeTable));
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSupertypes, 10));
        Iterator<T> it = listSupertypes.iterator();
        while (it.hasNext()) {
            arrayList.add(NameResolverUtilKt.getClassId(nameResolver, ((ProtoBuf.Type) it.next()).getClassName()).asSingleFqName());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (!Intrinsics.areEqual(((FqName) obj).asString(), "kotlin.Any")) {
                arrayList2.add(obj);
            }
        }
        Set<FqName> set = CollectionsKt.toSet(arrayList2);
        FqName fqNameAsSingleFqName = NameResolverUtilKt.getClassId(nameResolver, proto.getFqName()).asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        for (FqName fqName : set) {
            SubtypesMap subtypesMap = this.subtypesMap;
            Intrinsics.checkNotNull(fqName);
            subtypesMap.add(fqName, fqNameAsSingleFqName);
        }
        Collection<FqName> collection = this.supertypesMap.get(fqNameAsSingleFqName);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : collection) {
            if (!set.contains((FqName) obj2)) {
                arrayList3.add(obj2);
            }
        }
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            this.subtypesMap.removeValues((FqName) it2.next(), SetsKt.setOf(fqNameAsSingleFqName));
        }
        this.supertypesMap.set(fqNameAsSingleFqName, set);
        if (srcFile != null) {
            this.classFqNameToSourceMap.set(fqNameAsSingleFqName, srcFile);
        }
        this.classAttributesMap.set(fqNameAsSingleFqName, new ICClassesAttributes(ProtoBuf.Modality.SEALED == Flags.MODALITY.get(proto.getFlags())));
    }

    protected final void removeAllFromClassStorage(Collection<FqName> removedClasses, ChangesCollector changesCollector) {
        Intrinsics.checkNotNullParameter(removedClasses, "removedClasses");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        if (removedClasses.isEmpty()) {
            return;
        }
        Set<FqName> set = CollectionsKt.toSet(removedClasses);
        Iterator<FqName> it = set.iterator();
        while (it.hasNext()) {
            Iterator<FqName> it2 = BuildUtilKt.withSubtypes(it.next(), getThisWithDependentCaches()).iterator();
            while (it2.hasNext()) {
                changesCollector.collectSignature(it2.next(), false);
            }
        }
        for (AbstractIncrementalCache<ClassName> abstractIncrementalCache : getThisWithDependentCaches()) {
            HashSet<FqName> hashSet = new HashSet();
            HashSet<FqName> hashSet2 = new HashSet();
            for (FqName fqName : set) {
                hashSet.addAll(abstractIncrementalCache.supertypesMap.get(fqName));
                hashSet2.addAll(abstractIncrementalCache.subtypesMap.get(fqName));
                abstractIncrementalCache.supertypesMap.remove(fqName);
                abstractIncrementalCache.subtypesMap.remove(fqName);
            }
            for (FqName fqName2 : hashSet2) {
                SupertypesMap supertypesMap = abstractIncrementalCache.supertypesMap;
                Intrinsics.checkNotNull(fqName2);
                supertypesMap.removeValues(fqName2, set);
            }
            for (FqName fqName3 : hashSet) {
                SubtypesMap subtypesMap = abstractIncrementalCache.subtypesMap;
                Intrinsics.checkNotNull(fqName3);
                subtypesMap.removeValues(fqName3, set);
            }
        }
        for (FqName fqName4 : set) {
            this.classFqNameToSourceMap.remove(fqName4);
            this.classAttributesMap.remove(fqName4);
        }
    }

    /* JADX INFO: compiled from: AbstractIncrementalCache.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u0002H\u0014J\u0013\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0086\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fJ\u0019\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0004H\u0086\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/incremental/AbstractIncrementalCache$ClassFqNameToSourceMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "value", "get", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", ProductAction.ACTION_REMOVE, "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "sourceFile", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class ClassFqNameToSourceMap extends BasicStringMap<String> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
        public String dumpValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return value;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ClassFqNameToSourceMap(File storageFile, IncrementalCompilationContext icContext) {
            super(storageFile, new EnumeratorStringDescriptor(), PathStringDescriptor.INSTANCE, icContext);
            Intrinsics.checkNotNullParameter(storageFile, "storageFile");
            Intrinsics.checkNotNullParameter(icContext, "icContext");
        }

        public final void set(FqName fqName, File sourceFile) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
            LazyStorage<K, V> storage = getStorage();
            String strAsString = fqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            storage.set(strAsString, getPathConverter().toPath(sourceFile));
        }

        public final File get(FqName fqName) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            LazyStorage<K, V> storage = getStorage();
            String strAsString = fqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            String str = (String) storage.get(strAsString);
            if (str != null) {
                return getPathConverter().toFile(str);
            }
            return null;
        }

        public final void remove(FqName fqName) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            LazyStorage<K, V> storage = getStorage();
            String strAsString = fqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            storage.remove(strAsString);
        }
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public Collection<File> getComplementaryFilesRecursive(Collection<? extends File> dirtyFiles) {
        Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
        HashSet hashSet = new HashSet();
        ArrayDeque arrayDeque = new ArrayDeque(dirtyFiles);
        HashSet hashSet2 = new HashSet();
        HashSet hashSet3 = new HashSet();
        while (!arrayDeque.isEmpty()) {
            File file = (File) arrayDeque.pollFirst();
            if (!hashSet3.contains(file)) {
                hashSet3.add(file);
                ComplementarySourceFilesMap complementarySourceFilesMap = this.complementaryFilesMap;
                Intrinsics.checkNotNull(file);
                for (File file2 : complementarySourceFilesMap.get(file)) {
                    if (hashSet.add(file2) && !hashSet3.contains(file2)) {
                        arrayDeque.add(file2);
                    }
                }
                Collection<FqName> fqNames = getSourceToClassesMap$kotlin_build_common().getFqNames(file);
                ArrayList<FqName> arrayList = new ArrayList();
                for (Object obj : fqNames) {
                    if (!hashSet2.contains((FqName) obj)) {
                        arrayList.add(obj);
                    }
                }
                for (FqName fqName : arrayList) {
                    hashSet2.add(fqName);
                    Collection<FqName> collectionFindSealedSupertypes = BuildUtilKt.findSealedSupertypes(fqName, CollectionsKt.listOf(this));
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<T> it = collectionFindSealedSupertypes.iterator();
                    while (it.hasNext()) {
                        CollectionsKt.addAll(arrayList2, BuildUtilKt.withSubtypes((FqName) it.next(), CollectionsKt.listOf(this)));
                    }
                    ArrayList arrayList3 = arrayList2;
                    hashSet2.addAll(arrayList3);
                    ArrayList arrayList4 = new ArrayList();
                    Iterator it2 = arrayList3.iterator();
                    while (it2.hasNext()) {
                        File file3 = this.classFqNameToSourceMap.get((FqName) it2.next());
                        if (file3 != null) {
                            arrayList4.add(file3);
                        }
                    }
                    ArrayList arrayList5 = new ArrayList();
                    for (Object obj2 : arrayList4) {
                        if (!hashSet3.contains((File) obj2)) {
                            arrayList5.add(obj2);
                        }
                    }
                    arrayDeque.addAll(arrayList5);
                }
            }
        }
        hashSet.addAll(hashSet3);
        hashSet.removeAll(dirtyFiles);
        return hashSet;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public void updateComplementaryFiles(Collection<? extends File> dirtyFiles, ExpectActualTrackerImpl expectActualTracker) {
        Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
        Intrinsics.checkNotNullParameter(expectActualTracker, "expectActualTracker");
        Iterator<T> it = dirtyFiles.iterator();
        while (it.hasNext()) {
            this.complementaryFilesMap.remove((File) it.next());
        }
        HashMap map = new HashMap();
        for (Map.Entry<File, Set<File>> entry : expectActualTracker.getExpectToActualMap().entrySet()) {
            File key = entry.getKey();
            Set<File> value = entry.getValue();
            for (File file : value) {
                HashMap map2 = map;
                Object obj = map2.get(file);
                if (obj == null) {
                    obj = (Set) new HashSet();
                    map2.put(file, obj);
                }
                ((Set) obj).add(key);
            }
            ComplementarySourceFilesMap complementarySourceFilesMap = this.complementaryFilesMap;
            complementarySourceFilesMap.set(key, CollectionsKt.union(value, complementarySourceFilesMap.get(key)));
        }
        for (Map.Entry entry2 : map.entrySet()) {
            File file2 = (File) entry2.getKey();
            Set set = (Set) entry2.getValue();
            ComplementarySourceFilesMap complementarySourceFilesMap2 = this.complementaryFilesMap;
            complementarySourceFilesMap2.set(file2, CollectionsKt.union(set, complementarySourceFilesMap2.get(file2)));
        }
    }
}
