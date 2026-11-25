package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinPackageProvider;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.jvm.JvmPlatformKt;

/* JADX INFO: compiled from: KotlinPackageProviderBase.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J2\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\f0\u0016H\u0016J2\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\f0\u0016H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinPackageProviderBase;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinPackageProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "searchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "(Lcom/intellij/openapi/project/Project;Lcom/intellij/psi/search/GlobalSearchScope;)V", "getProject", "()Lcom/intellij/openapi/project/Project;", "getSearchScope", "()Lcom/intellij/psi/search/GlobalSearchScope;", "doesPackageExist", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "doesPlatformSpecificPackageExist", "getPlatformSpecificSubPackagesFqNames", "", "Lorg/jetbrains/kotlin/name/Name;", "nameFilter", "Lkotlin/Function1;", "getSubPackageFqNames", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinPackageProviderBase extends KotlinPackageProvider {
    private final Project project;
    private final GlobalSearchScope searchScope;

    protected final Project getProject() {
        return this.project;
    }

    protected final GlobalSearchScope getSearchScope() {
        return this.searchScope;
    }

    public KotlinPackageProviderBase(Project project, GlobalSearchScope searchScope) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        this.project = project;
        this.searchScope = searchScope;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinPackageProvider
    public boolean doesPackageExist(FqName packageFqName, TargetPlatform platform) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(platform, "platform");
        return doesPlatformSpecificPackageExist(packageFqName, platform) || doesKotlinOnlyPackageExist(packageFqName);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinPackageProvider
    public boolean doesPlatformSpecificPackageExist(FqName packageFqName, TargetPlatform platform) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(platform, "platform");
        if (JvmPlatformKt.isJvm(platform)) {
            String strAsString = packageFqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            for (PsiElementFinder psiElementFinder : PsiElementFinder.EP.getPoint(this.project).getExtensionList()) {
                if (!Intrinsics.areEqual(psiElementFinder.getClass().getName(), ImplUtilsKt.KOTLIN_JAVA_ELEMENT_FINDER_CLASS_NAME)) {
                    Intrinsics.checkNotNull(psiElementFinder);
                    if (psiElementFinder.findPackage(strAsString) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinPackageProvider
    public Set<Name> getSubPackageFqNames(FqName packageFqName, TargetPlatform platform, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        setCreateSetBuilder.addAll(getKotlinOnlySubPackagesFqNames(packageFqName, nameFilter));
        setCreateSetBuilder.addAll(getPlatformSpecificSubPackagesFqNames(packageFqName, platform, nameFilter));
        return SetsKt.build(setCreateSetBuilder);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinPackageProvider
    public Set<Name> getPlatformSpecificSubPackagesFqNames(FqName packageFqName, TargetPlatform platform, Function1<? super Name, Boolean> nameFilter) {
        Name nameIdentifierIfValid;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        if (JvmPlatformKt.isJvm(platform)) {
            String strAsString = packageFqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            Set setCreateSetBuilder = SetsKt.createSetBuilder();
            for (PsiElementFinder psiElementFinder : PsiElementFinder.EP.getPoint(this.project).getExtensionList()) {
                if (!Intrinsics.areEqual(psiElementFinder.getClass().getName(), ImplUtilsKt.KOTLIN_JAVA_ELEMENT_FINDER_CLASS_NAME)) {
                    Intrinsics.checkNotNull(psiElementFinder);
                    PsiPackage psiPackageFindPackage = psiElementFinder.findPackage(strAsString);
                    if (psiPackageFindPackage != null) {
                        Intrinsics.checkNotNull(psiPackageFindPackage);
                        PsiPackage[] subPackages = psiElementFinder.getSubPackages(psiPackageFindPackage, this.searchScope);
                        Intrinsics.checkNotNullExpressionValue(subPackages, "getSubPackages(...)");
                        for (PsiPackage psiPackage : subPackages) {
                            String name2 = psiPackage.getName();
                            if (name2 != null && (nameIdentifierIfValid = Name.identifierIfValid(name2)) != null) {
                                Intrinsics.checkNotNull(nameIdentifierIfValid);
                                if (nameFilter.invoke(nameIdentifierIfValid).booleanValue()) {
                                    setCreateSetBuilder.add(nameIdentifierIfValid);
                                }
                            }
                        }
                    }
                }
            }
            return SetsKt.build(setCreateSetBuilder);
        }
        return SetsKt.emptySet();
    }
}
