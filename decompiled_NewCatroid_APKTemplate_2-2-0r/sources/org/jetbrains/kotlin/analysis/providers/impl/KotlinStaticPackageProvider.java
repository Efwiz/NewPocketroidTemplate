package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: KotlinStaticPackageProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0016J*\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0011\u001a\u00020\f2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00100\u0014H\u0016R \u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticPackageProvider;", "Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinPackageProviderBase;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lcom/intellij/openapi/project/Project;Lcom/intellij/psi/search/GlobalSearchScope;Ljava/util/Collection;)V", "kotlinPackageToSubPackages", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "Lorg/jetbrains/kotlin/name/Name;", "doesKotlinOnlyPackageExist", "", "packageFqName", "getKotlinOnlySubPackagesFqNames", "nameFilter", "Lkotlin/Function1;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinStaticPackageProvider extends KotlinPackageProviderBase {
    private final Map<FqName, Set<Name>> kotlinPackageToSubPackages;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinStaticPackageProvider(Project project, GlobalSearchScope scope, Collection<? extends KtFile> files) {
        super(project, scope);
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(files, "files");
        ArrayList<KtFile> arrayList = new ArrayList();
        for (Object obj : files) {
            if (scope.contains(((KtFile) obj).getVirtualFile())) {
                arrayList.add(obj);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (KtFile ktFile : arrayList) {
            FqName fqNameChild = FqName.ROOT;
            for (Name name2 : ktFile.getPackageFqName().pathSegments()) {
                Intrinsics.checkNotNull(fqNameChild);
                Object obj2 = linkedHashMap.get(fqNameChild);
                if (obj2 == null) {
                    obj2 = (Set) new LinkedHashSet();
                    linkedHashMap.put(fqNameChild, obj2);
                }
                ((Collection) obj2).add(name2);
                fqNameChild = fqNameChild.child(name2);
            }
            final KotlinStaticPackageProvider$kotlinPackageToSubPackages$1$1$2 kotlinStaticPackageProvider$kotlinPackageToSubPackages$1$1$2 = new Function1<FqName, Set<Name>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticPackageProvider$kotlinPackageToSubPackages$1$1$2
                @Override // kotlin.jvm.functions.Function1
                public final Set<Name> invoke(FqName it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new LinkedHashSet();
                }
            };
            linkedHashMap.computeIfAbsent(fqNameChild, new Function() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticPackageProvider$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj3) {
                    return KotlinStaticPackageProvider.kotlinPackageToSubPackages$lambda$4$lambda$3$lambda$2(kotlinStaticPackageProvider$kotlinPackageToSubPackages$1$1$2, obj3);
                }
            });
        }
        this.kotlinPackageToSubPackages = linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set kotlinPackageToSubPackages$lambda$4$lambda$3$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Set) tmp0.invoke(obj);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinPackageProvider
    public boolean doesKotlinOnlyPackageExist(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return packageFqName.isRoot() || this.kotlinPackageToSubPackages.containsKey(packageFqName);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinPackageProvider
    public Set<Name> getKotlinOnlySubPackagesFqNames(FqName packageFqName, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Set<Name> set = this.kotlinPackageToSubPackages.get(packageFqName);
        if (set == null) {
            return SetsKt.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Object obj : set) {
            if (nameFilter.invoke((Name) obj).booleanValue()) {
                linkedHashSet.add(obj);
            }
        }
        return linkedHashSet;
    }
}
