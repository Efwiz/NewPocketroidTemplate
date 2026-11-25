package org.jetbrains.kotlin.analysis.providers.impl.declarationProviders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassLikeDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeAlias;

/* JADX INFO: compiled from: CompositeKotlinDeclarationProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 '2\u00020\u0001:\u0001'B\u0015\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000eH\u0016J\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\u0006\u0010\u0013\u001a\u00020\u000eH\u0016J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000b2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000b2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\b2\u0006\u0010\u0010\u001a\u00020\u000eH\u0016J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u000b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020!0\b2\u0006\u0010\u0010\u001a\u00020\u000eH\u0016J\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u000b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/declarationProviders/CompositeKotlinDeclarationProvider;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProvider;", "providers", "", "(Ljava/util/List;)V", "getProviders", "()Ljava/util/List;", "computePackageSetWithTopLevelCallableDeclarations", "", "", "findFilesForFacade", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "findFilesForFacadeByPackage", "packageFqName", "findFilesForScript", "Lorg/jetbrains/kotlin/psi/KtScript;", "scriptFqName", "findInternalFilesForFacade", "getAllClassesByClassId", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getAllTypeAliasesByClassId", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "getClassLikeDeclarationByClassId", "Lorg/jetbrains/kotlin/psi/KtClassLikeDeclaration;", "getTopLevelCallableFiles", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "getTopLevelCallableNamesInPackage", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelFunctions", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getTopLevelKotlinClassLikeDeclarationNamesInPackage", "getTopLevelProperties", "Lorg/jetbrains/kotlin/psi/KtProperty;", "Companion", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompositeKotlinDeclarationProvider extends KotlinDeclarationProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<KotlinDeclarationProvider> providers;

    public /* synthetic */ CompositeKotlinDeclarationProvider(List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(list);
    }

    public final List<KotlinDeclarationProvider> getProviders() {
        return this.providers;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private CompositeKotlinDeclarationProvider(List<? extends KotlinDeclarationProvider> list) {
        this.providers = list;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public KtClassLikeDeclaration getClassLikeDeclarationByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Iterator<T> it = this.providers.iterator();
        while (it.hasNext()) {
            KtClassLikeDeclaration classLikeDeclarationByClassId = ((KotlinDeclarationProvider) it.next()).getClassLikeDeclarationByClassId(classId);
            if (classLikeDeclarationByClassId != null) {
                return classLikeDeclarationByClassId;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtClassOrObject> getAllClassesByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        List<KotlinDeclarationProvider> list = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((KotlinDeclarationProvider) it.next()).getAllClassesByClassId(classId));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtTypeAlias> getAllTypeAliasesByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        List<KotlinDeclarationProvider> list = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((KotlinDeclarationProvider) it.next()).getAllTypeAliasesByClassId(classId));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Set<Name> getTopLevelKotlinClassLikeDeclarationNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        List<KotlinDeclarationProvider> list = this.providers;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(linkedHashSet, ((KotlinDeclarationProvider) it.next()).getTopLevelKotlinClassLikeDeclarationNamesInPackage(packageFqName));
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtProperty> getTopLevelProperties(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        List<KotlinDeclarationProvider> list = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((KotlinDeclarationProvider) it.next()).getTopLevelProperties(callableId));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtNamedFunction> getTopLevelFunctions(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        List<KotlinDeclarationProvider> list = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((KotlinDeclarationProvider) it.next()).getTopLevelFunctions(callableId));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> getTopLevelCallableFiles(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        List<KotlinDeclarationProvider> list = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((KotlinDeclarationProvider) it.next()).getTopLevelCallableFiles(callableId));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        List<KotlinDeclarationProvider> list = this.providers;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(linkedHashSet, ((KotlinDeclarationProvider) it.next()).getTopLevelCallableNamesInPackage(packageFqName));
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> findFilesForFacadeByPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        List<KotlinDeclarationProvider> list = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((KotlinDeclarationProvider) it.next()).findFilesForFacadeByPackage(packageFqName));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> findFilesForFacade(FqName facadeFqName) {
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        List<KotlinDeclarationProvider> list = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((KotlinDeclarationProvider) it.next()).findFilesForFacade(facadeFqName));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> findInternalFilesForFacade(FqName facadeFqName) {
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        List<KotlinDeclarationProvider> list = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((KotlinDeclarationProvider) it.next()).findInternalFilesForFacade(facadeFqName));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtScript> findFilesForScript(FqName scriptFqName) {
        Intrinsics.checkNotNullParameter(scriptFqName, "scriptFqName");
        List<KotlinDeclarationProvider> list = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((KotlinDeclarationProvider) it.next()).findFilesForScript(scriptFqName));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Set<String> computePackageSetWithTopLevelCallableDeclarations() {
        List<KotlinDeclarationProvider> list = this.providers;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(linkedHashSet, ((KotlinDeclarationProvider) it.next()).computePackageSetWithTopLevelCallableDeclarations());
        }
        return linkedHashSet;
    }

    /* JADX INFO: compiled from: CompositeKotlinDeclarationProvider.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006J\u0014\u0010\u0007\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006J\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/declarationProviders/CompositeKotlinDeclarationProvider$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProvider;", "providers", "", "createFlattened", "flatten", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinDeclarationProvider create(List<? extends KotlinDeclarationProvider> providers) {
            Intrinsics.checkNotNullParameter(providers, "providers");
            int size = providers.size();
            if (size == 0) {
                return EmptyKotlinDeclarationProvider.INSTANCE;
            }
            if (size == 1) {
                return (KotlinDeclarationProvider) CollectionsKt.single((List) providers);
            }
            return new CompositeKotlinDeclarationProvider(providers, null);
        }

        public final KotlinDeclarationProvider createFlattened(List<? extends KotlinDeclarationProvider> providers) {
            Intrinsics.checkNotNullParameter(providers, "providers");
            if (providers.size() > 1) {
                providers = flatten(providers);
            }
            return create(providers);
        }

        public final List<KotlinDeclarationProvider> flatten(List<? extends KotlinDeclarationProvider> providers) {
            Intrinsics.checkNotNullParameter(providers, "providers");
            ArrayList arrayList = new ArrayList();
            for (KotlinDeclarationProvider kotlinDeclarationProvider : providers) {
                CollectionsKt.addAll(arrayList, kotlinDeclarationProvider instanceof CompositeKotlinDeclarationProvider ? ((CompositeKotlinDeclarationProvider) kotlinDeclarationProvider).getProviders() : CollectionsKt.listOf(kotlinDeclarationProvider));
            }
            return arrayList;
        }
    }
}
