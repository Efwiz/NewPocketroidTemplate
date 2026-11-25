package org.jetbrains.kotlin.resolve.lazy.declarations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassOrObjectInfo;
import org.jetbrains.kotlin.resolve.lazy.data.KtScriptInfo;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;

/* JADX INFO: compiled from: CombinedPackageMemberDeclarationProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b0\u000fH\u0016J\u001a\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015H\u0016J*\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\f2\u0006\u0010\u0018\u001a\u00020\u00192\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b0\u000fH\u0016J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u000e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\n0\fH\u0016J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/CombinedPackageMemberDeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;", "providers", "", "(Ljava/util/Collection;)V", "getProviders", "()Ljava/util/Collection;", "containsFile", "", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "getAllDeclaredSubPackages", "", "Lorg/jetbrains/kotlin/name/FqName;", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "getClassOrObjectDeclarations", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassOrObjectInfo;", "name", "getDeclarationNames", "", "getDeclarations", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "getDestructuringDeclarationsEntries", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;", "getFunctionDeclarations", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getPackageFiles", "getPropertyDeclarations", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getScriptDeclarations", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtScriptInfo;", "getTypeAliasDeclarations", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CombinedPackageMemberDeclarationProvider implements PackageMemberDeclarationProvider {
    private final Collection<PackageMemberDeclarationProvider> providers;

    /* JADX WARN: Multi-variable type inference failed */
    public CombinedPackageMemberDeclarationProvider(Collection<? extends PackageMemberDeclarationProvider> providers) {
        Intrinsics.checkNotNullParameter(providers, "providers");
        this.providers = providers;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
    public /* bridge */ /* synthetic */ Collection getAllDeclaredSubPackages(Function1 function1) {
        return getAllDeclaredSubPackages((Function1<? super Name, Boolean>) function1);
    }

    public final Collection<PackageMemberDeclarationProvider> getProviders() {
        return this.providers;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
    public List<FqName> getAllDeclaredSubPackages(Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackageMemberDeclarationProvider) it.next()).getAllDeclaredSubPackages(nameFilter));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
    public List<KtFile> getPackageFiles() {
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackageMemberDeclarationProvider) it.next()).getPackageFiles());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
    public boolean containsFile(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        if ((collection instanceof Collection) && collection.isEmpty()) {
            return false;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (((PackageMemberDeclarationProvider) it.next()).containsFile(file)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtDeclaration> getDeclarations(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackageMemberDeclarationProvider) it.next()).getDeclarations(kindFilter, nameFilter));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtNamedFunction> getFunctionDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackageMemberDeclarationProvider) it.next()).getFunctionDeclarations(name2));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtProperty> getPropertyDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackageMemberDeclarationProvider) it.next()).getPropertyDeclarations(name2));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Collection<KtDestructuringDeclarationEntry> getDestructuringDeclarationsEntries(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackageMemberDeclarationProvider) it.next()).getDestructuringDeclarationsEntries(name2));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtClassOrObjectInfo<?>> getClassOrObjectDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackageMemberDeclarationProvider) it.next()).getClassOrObjectDeclarations(name2));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtScriptInfo> getScriptDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackageMemberDeclarationProvider) it.next()).getScriptDeclarations(name2));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtTypeAlias> getTypeAliasDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackageMemberDeclarationProvider) it.next()).getTypeAliasDeclarations(name2));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Set<Name> getDeclarationNames() {
        Collection<PackageMemberDeclarationProvider> collection = this.providers;
        HashSet hashSet = new HashSet();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(hashSet, ((PackageMemberDeclarationProvider) it.next()).getDeclarationNames());
        }
        return hashSet;
    }
}
