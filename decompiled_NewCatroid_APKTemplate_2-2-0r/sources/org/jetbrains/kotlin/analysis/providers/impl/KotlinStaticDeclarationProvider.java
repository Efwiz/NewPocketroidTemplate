package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassLikeDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeAlias;

/* JADX INFO: compiled from: KotlinStaticDeclarationProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0017\u001a\u00020\u0015H\u0016J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00122\u0006\u0010\u001a\u001a\u00020\u0015H\u0016J\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00122\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00122\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0012\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010%\u001a\u00020&H\u0016J\u0016\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\u000f2\u0006\u0010\u0017\u001a\u00020\u0015H\u0016J\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u00122\u0006\u0010%\u001a\u00020&H\u0016J\u0016\u0010+\u001a\b\u0012\u0004\u0012\u00020(0\u000f2\u0006\u0010\u0017\u001a\u00020\u0015H\u0016J\u0016\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u00122\u0006\u0010%\u001a\u00020&H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticDeclarationProvider;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProvider;", "index", "Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticDeclarationIndex;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "(Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticDeclarationIndex;Lcom/intellij/psi/search/GlobalSearchScope;)V", "getScope", "()Lcom/intellij/psi/search/GlobalSearchScope;", "inScope", "", "Lorg/jetbrains/kotlin/psi/KtElement;", "getInScope", "(Lorg/jetbrains/kotlin/psi/KtElement;)Z", "computePackageSetWithTopLevelCallableDeclarations", "", "", "findFilesForFacade", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "findFilesForFacadeByPackage", "packageFqName", "findFilesForScript", "Lorg/jetbrains/kotlin/psi/KtScript;", "scriptFqName", "findInternalFilesForFacade", "getAllClassesByClassId", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getAllTypeAliasesByClassId", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "getClassLikeDeclarationByClassId", "Lorg/jetbrains/kotlin/psi/KtClassLikeDeclaration;", "getTopLevelCallableFiles", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "getTopLevelCallableNamesInPackage", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelFunctions", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getTopLevelKotlinClassLikeDeclarationNamesInPackage", "getTopLevelProperties", "Lorg/jetbrains/kotlin/psi/KtProperty;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinStaticDeclarationProvider extends KotlinDeclarationProvider {
    private final KotlinStaticDeclarationIndex index;
    private final GlobalSearchScope scope;

    public final GlobalSearchScope getScope() {
        return this.scope;
    }

    public KotlinStaticDeclarationProvider(KotlinStaticDeclarationIndex index, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(index, "index");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.index = index;
        this.scope = scope;
    }

    private final boolean getInScope(KtElement ktElement) {
        return this.scope.contains(ktElement.getContainingKtFile().getVirtualFile());
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public KtClassLikeDeclaration getClassLikeDeclarationByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        KtClassOrObject ktClassOrObject = (KtClassOrObject) CollectionsKt.firstOrNull(getAllClassesByClassId(classId));
        return ktClassOrObject != null ? ktClassOrObject : (KtClassLikeDeclaration) CollectionsKt.firstOrNull(getAllTypeAliasesByClassId(classId));
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtClassOrObject> getAllClassesByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Set<KtClassOrObject> set = this.index.getClassMap$analysis_api_providers().get(classId.getPackageFqName());
        if (set == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : set) {
            KtClassOrObject ktClassOrObject = (KtClassOrObject) obj;
            if (Intrinsics.areEqual(ktClassOrObject.getClassId(), classId) && getInScope(ktClassOrObject)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtTypeAlias> getAllTypeAliasesByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Set<KtTypeAlias> set = this.index.getTypeAliasMap$analysis_api_providers().get(classId.getPackageFqName());
        if (set == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : set) {
            KtTypeAlias ktTypeAlias = (KtTypeAlias) obj;
            if (Intrinsics.areEqual(ktTypeAlias.getClassId(), classId) && getInScope(ktTypeAlias)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Set<Name> getTopLevelKotlinClassLikeDeclarationNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Set<KtClassOrObject> setEmptySet = this.index.getClassMap$analysis_api_providers().get(packageFqName);
        if (setEmptySet == null) {
            setEmptySet = SetsKt.emptySet();
        }
        Set<KtTypeAlias> setEmptySet2 = this.index.getTypeAliasMap$analysis_api_providers().get(packageFqName);
        if (setEmptySet2 == null) {
            setEmptySet2 = SetsKt.emptySet();
        }
        Set setPlus = SetsKt.plus((Set) setEmptySet, (Iterable) setEmptySet2);
        ArrayList arrayList = new ArrayList();
        for (Object obj : setPlus) {
            if (getInScope((KtNamedDeclaration) obj)) {
                arrayList.add(obj);
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Name nameAsName = ((KtClassLikeDeclaration) ((KtNamedDeclaration) it.next())).getNameAsName();
            if (nameAsName != null) {
                linkedHashSet.add(nameAsName);
            }
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Set<KtProperty> setEmptySet = this.index.getTopLevelPropertyMap$analysis_api_providers().get(packageFqName);
        if (setEmptySet == null) {
            setEmptySet = SetsKt.emptySet();
        }
        Set<KtNamedFunction> setEmptySet2 = this.index.getTopLevelFunctionMap$analysis_api_providers().get(packageFqName);
        if (setEmptySet2 == null) {
            setEmptySet2 = SetsKt.emptySet();
        }
        Set setPlus = SetsKt.plus((Set) setEmptySet, (Iterable) setEmptySet2);
        ArrayList arrayList = new ArrayList();
        for (Object obj : setPlus) {
            if (getInScope((KtElement) obj)) {
                arrayList.add(obj);
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Name nameAsName = ((KtCallableDeclaration) it.next()).getNameAsName();
            if (nameAsName != null) {
                linkedHashSet.add(nameAsName);
            }
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> findFilesForFacadeByPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Set<KtFile> setEmptySet = this.index.getFacadeFileMap$analysis_api_providers().get(packageFqName);
        if (setEmptySet == null) {
            setEmptySet = SetsKt.emptySet();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : setEmptySet) {
            if (this.scope.contains(((KtFile) obj).getVirtualFile())) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> findFilesForFacade(FqName facadeFqName) {
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        if (facadeFqName.shortNameOrSpecial().isSpecial()) {
            return CollectionsKt.emptyList();
        }
        FqName fqNameParent = facadeFqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        Collection<KtFile> collectionFindFilesForFacadeByPackage = findFilesForFacadeByPackage(fqNameParent);
        ArrayList arrayList = new ArrayList();
        for (Object obj : collectionFindFilesForFacadeByPackage) {
            if (Intrinsics.areEqual(JvmFileClassUtilKt.getJavaFileFacadeFqName((KtFile) obj), facadeFqName)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> findInternalFilesForFacade(FqName facadeFqName) {
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        Set<KtFile> setEmptySet = this.index.getMultiFileClassPartMap$analysis_api_providers().get(facadeFqName);
        if (setEmptySet == null) {
            setEmptySet = SetsKt.emptySet();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : setEmptySet) {
            if (this.scope.contains(((KtFile) obj).getVirtualFile())) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtScript> findFilesForScript(FqName scriptFqName) {
        Intrinsics.checkNotNullParameter(scriptFqName, "scriptFqName");
        Set<KtScript> setEmptySet = this.index.getScriptMap$analysis_api_providers().get(scriptFqName);
        if (setEmptySet == null) {
            setEmptySet = SetsKt.emptySet();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : setEmptySet) {
            if (this.scope.contains(((KtScript) obj).getContainingKtFile().getVirtualFile())) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Set<String> computePackageSetWithTopLevelCallableDeclarations() {
        Set setPlus = SetsKt.plus((Set) this.index.getTopLevelPropertyMap$analysis_api_providers().keySet(), (Iterable) this.index.getTopLevelFunctionMap$analysis_api_providers().keySet());
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = setPlus.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((FqName) it.next()).asString());
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtProperty> getTopLevelProperties(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Set<KtProperty> set = this.index.getTopLevelPropertyMap$analysis_api_providers().get(callableId.getPackageName());
        if (set == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : set) {
            KtProperty ktProperty = (KtProperty) obj;
            if (Intrinsics.areEqual(ktProperty.getNameAsName(), callableId.getCallableName()) && getInScope(ktProperty)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtNamedFunction> getTopLevelFunctions(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Set<KtNamedFunction> set = this.index.getTopLevelFunctionMap$analysis_api_providers().get(callableId.getPackageName());
        if (set == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : set) {
            KtNamedFunction ktNamedFunction = (KtNamedFunction) obj;
            if (Intrinsics.areEqual(ktNamedFunction.getNameAsName(), callableId.getCallableName()) && getInScope(ktNamedFunction)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> getTopLevelCallableFiles(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        Iterator<T> it = getTopLevelProperties(callableId).iterator();
        while (it.hasNext()) {
            setCreateSetBuilder.add(((KtProperty) it.next()).getContainingKtFile());
        }
        Set set = setCreateSetBuilder;
        Iterator<T> it2 = getTopLevelFunctions(callableId).iterator();
        while (it2.hasNext()) {
            set.add(((KtNamedFunction) it2.next()).getContainingKtFile());
        }
        return SetsKt.build(setCreateSetBuilder);
    }
}
