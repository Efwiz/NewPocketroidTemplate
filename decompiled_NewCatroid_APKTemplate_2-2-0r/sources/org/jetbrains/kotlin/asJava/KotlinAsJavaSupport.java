package org.jetbrains.kotlin.asJava;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.KtFakeLightClass;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;

/* JADX INFO: compiled from: KotlinAsJavaSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u0000 +2\u00020\u0001:\u0001+B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\b2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\rH&J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\rH&J\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\rH&J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\tH&J\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\rH&J\u0012\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\u001e\u001a\u00020\tH&J\u0012\u0010#\u001a\u0004\u0018\u00010\"2\u0006\u0010$\u001a\u00020\u0015H&J\u0012\u0010%\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020 0\b2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\rH&J\u001e\u0010'\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010(\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\rH&J\u0018\u0010)\u001a\u00020*2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\rH&¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupport;", "", "()V", "createFacadeForSyntheticFile", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForFacade;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "findClassOrObjectDeclarations", "", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "searchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "findClassOrObjectDeclarationsInPackage", "packageFqName", "findFilesForFacade", "facadeFqName", "findFilesForFacadeByPackage", "findFilesForPackage", "findFilesForScript", "Lorg/jetbrains/kotlin/psi/KtScript;", "scriptFqName", "getFacadeClasses", "scope", "getFacadeClassesInPackage", "getFacadeNames", "", "getFakeLightClass", "Lorg/jetbrains/kotlin/asJava/classes/KtFakeLightClass;", "classOrObject", "getKotlinInternalClasses", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "getLightClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "getLightClassForScript", "script", "getLightFacade", "getScriptClasses", "getSubPackages", "fqn", "packageExists", "", "Companion", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinAsJavaSupport {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final KotlinAsJavaSupport getInstance(Project project) {
        return INSTANCE.getInstance(project);
    }

    public abstract KtLightClassForFacade createFacadeForSyntheticFile(KtFile file);

    public abstract Collection<KtClassOrObject> findClassOrObjectDeclarations(FqName fqName, GlobalSearchScope searchScope);

    public abstract Collection<KtClassOrObject> findClassOrObjectDeclarationsInPackage(FqName packageFqName, GlobalSearchScope searchScope);

    public abstract Collection<KtFile> findFilesForFacade(FqName facadeFqName, GlobalSearchScope searchScope);

    public abstract Collection<KtFile> findFilesForFacadeByPackage(FqName packageFqName, GlobalSearchScope searchScope);

    public abstract Collection<KtFile> findFilesForPackage(FqName packageFqName, GlobalSearchScope searchScope);

    public abstract Collection<KtScript> findFilesForScript(FqName scriptFqName, GlobalSearchScope searchScope);

    public abstract Collection<KtLightClassForFacade> getFacadeClasses(FqName facadeFqName, GlobalSearchScope scope);

    public abstract Collection<KtLightClassForFacade> getFacadeClassesInPackage(FqName packageFqName, GlobalSearchScope scope);

    public abstract Collection<String> getFacadeNames(FqName packageFqName, GlobalSearchScope scope);

    public abstract KtFakeLightClass getFakeLightClass(KtClassOrObject classOrObject);

    public abstract Collection<PsiClass> getKotlinInternalClasses(FqName fqName, GlobalSearchScope scope);

    public abstract KtLightClass getLightClass(KtClassOrObject classOrObject);

    public abstract KtLightClass getLightClassForScript(KtScript script);

    public abstract KtLightClassForFacade getLightFacade(KtFile file);

    public abstract Collection<PsiClass> getScriptClasses(FqName scriptFqName, GlobalSearchScope scope);

    public abstract Collection<FqName> getSubPackages(FqName fqn, GlobalSearchScope scope);

    public abstract boolean packageExists(FqName fqName, GlobalSearchScope scope);

    /* JADX INFO: compiled from: KotlinAsJavaSupport.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupport$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupport;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final KotlinAsJavaSupport getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(KotlinAsJavaSupport.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (KotlinAsJavaSupport) service;
        }
    }
}
