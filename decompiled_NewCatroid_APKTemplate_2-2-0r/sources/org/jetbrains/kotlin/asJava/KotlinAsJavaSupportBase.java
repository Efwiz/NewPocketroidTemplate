package org.jetbrains.kotlin.asJava;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analyzer.KotlinModificationTrackerService;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclarationKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KotlinDeclarationNavigationPolicy;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPsiFactoryKt;
import org.jetbrains.kotlin.psi.KtScript;

/* JADX INFO: compiled from: KotlinAsJavaSupportBase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0002JKB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\b\b\u0001\u0010\u0011*\u00020\u00122\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u0002H\u0011\u0018\u00010\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001a\u001a\u00020\u001bH$J \u0010\u001c\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00180 H$J\u0012\u0010!\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001a\u001a\u00020\u001bH$J\u001e\u0010\"\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00180 H$J\u0012\u0010#\u001a\u0004\u0018\u00010\u00122\u0006\u0010$\u001a\u00020%H$J\u0016\u0010&\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00142\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u0010'\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00142\u0006\u0010\u0017\u001a\u00020\u0018J\u0016\u0010(\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00142\u0006\u0010$\u001a\u00020%J\u0012\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010\u0017\u001a\u00020\u0018H$J\u001d\u0010+\u001a\u00020\n2\u0006\u0010,\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\u0018H$¢\u0006\u0002\u0010-J\u001e\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00160/2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u00100\u001a\u00020\fH\u0016J\u001e\u00101\u001a\b\u0012\u0004\u0012\u00020\u00160/2\u0006\u00102\u001a\u00020\u001e2\u0006\u00100\u001a\u00020\fH\u0016J\u001e\u00103\u001a\b\u0012\u0004\u0012\u0002040/2\u0006\u00102\u001a\u00020\u001e2\u0006\u00100\u001a\u00020\fH\u0016J\u0012\u00105\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0012\u00106\u001a\u0004\u0018\u00010\u00122\u0006\u0010$\u001a\u00020%H\u0016J\u0012\u00107\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u001e\u00108\u001a\b\u0012\u0004\u0012\u0002090/2\u0006\u0010:\u001a\u00020\u001e2\u0006\u00100\u001a\u00020\fH\u0016J$\u0010;\u001a\u0004\u0018\u0001H\u0011\"\u0004\b\u0001\u0010\u00112\f\u0010<\u001a\b\u0012\u0004\u0012\u0002H\u00110=H\u0082\b¢\u0006\u0002\u0010>J\u0010\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020BH$J\u0010\u0010C\u001a\u00020@2\u0006\u0010A\u001a\u00020BH\u0014J\b\u0010D\u001a\u00020@H\u0014J\f\u0010E\u001a\u00020\n*\u00020\u0018H\u0002J\f\u0010F\u001a\u00020\n*\u00020\u0018H\u0002J\u0011\u0010G\u001a\u00028\u0000*\u00020\u0018H$¢\u0006\u0002\u0010HJ\u0018\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00160 *\b\u0012\u0004\u0012\u00020\u00180/H\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\u00020\f*\u00028\u0000X¤\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006L"}, d2 = {"Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupportBase;", "TModule", "Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupport;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "getProject", "()Lcom/intellij/openapi/project/Project;", "recursiveGuard", "Ljava/lang/ThreadLocal;", "", "contentSearchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "getContentSearchScope", "(Ljava/lang/Object;)Lcom/intellij/psi/search/GlobalSearchScope;", "cachedValueResult", "Lorg/jetbrains/kotlin/com/intellij/psi/util/CachedValueProvider$Result;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "lightClassCachedValue", "Lorg/jetbrains/kotlin/asJava/LightClassCachedValue;", "createFacadeForSyntheticFile", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForFacade;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "createInstanceOfDecompiledLightClass", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "createInstanceOfDecompiledLightFacade", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "files", "", "createInstanceOfLightClass", "createInstanceOfLightFacade", "createInstanceOfLightScript", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "createLightClass", "createLightFacade", "createLightScript", "declarationLocation", "Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupportBase$DeclarationLocation;", "facadeIsApplicable", "module", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/psi/KtFile;)Z", "getFacadeClasses", "", "scope", "getFacadeClassesInPackage", "packageFqName", "getFacadeNames", "", "getLightClass", "getLightClassForScript", "getLightFacade", "getScriptClasses", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "scriptFqName", "guardedRun", "body", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "librariesTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "outOfBlockModificationTracker", "projectWideOutOfBlockModificationTracker", "canHaveAdditionalFilesInFacade", "facadeIsPossible", "findModule", "(Lorg/jetbrains/kotlin/psi/KtFile;)Ljava/lang/Object;", "toFacadeClasses", "DeclarationLocation", "FacadeKey", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinAsJavaSupportBase<TModule> extends KotlinAsJavaSupport {
    private final Project project;
    private final ThreadLocal<Boolean> recursiveGuard;

    /* JADX INFO: compiled from: KotlinAsJavaSupportBase.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0084\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupportBase$DeclarationLocation;", "", "(Ljava/lang/String;I)V", "ProjectSources", "LibraryClasses", "LibrarySources", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected enum DeclarationLocation {
        ProjectSources,
        LibraryClasses,
        LibrarySources;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<DeclarationLocation> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: KotlinAsJavaSupportBase.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeclarationLocation.values().length];
            try {
                iArr[DeclarationLocation.ProjectSources.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeclarationLocation.LibraryClasses.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeclarationLocation.LibrarySources.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected abstract KtLightClass createInstanceOfDecompiledLightClass(KtClassOrObject classOrObject);

    protected abstract KtLightClassForFacade createInstanceOfDecompiledLightFacade(FqName facadeFqName, List<? extends KtFile> files);

    protected abstract KtLightClass createInstanceOfLightClass(KtClassOrObject classOrObject);

    protected abstract KtLightClassForFacade createInstanceOfLightFacade(FqName facadeFqName, List<? extends KtFile> files);

    protected abstract KtLightClass createInstanceOfLightScript(KtScript script);

    protected abstract DeclarationLocation declarationLocation(KtFile file);

    protected abstract boolean facadeIsApplicable(TModule module, KtFile file);

    protected abstract TModule findModule(KtFile ktFile);

    protected abstract GlobalSearchScope getContentSearchScope(TModule tmodule);

    protected abstract ModificationTracker librariesTracker(PsiElement element);

    public KotlinAsJavaSupportBase(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
        this.recursiveGuard = new ThreadLocal<>();
    }

    protected final Project getProject() {
        return this.project;
    }

    public final LightClassCachedValue<KtLightClassForFacade> createLightFacade(KtFile file) {
        ArrayList arrayListListOf;
        Intrinsics.checkNotNullParameter(file, "file");
        if (!facadeIsPossible(file)) {
            return null;
        }
        TModule tmoduleFindModule = findModule(file);
        if (!facadeIsApplicable(tmoduleFindModule, file)) {
            tmoduleFindModule = null;
        }
        if (tmoduleFindModule == null) {
            return null;
        }
        FqName javaFileFacadeFqName = JvmFileClassUtilKt.getJavaFileFacadeFqName(file);
        if (canHaveAdditionalFilesInFacade(file)) {
            Collection<KtFile> collectionFindFilesForFacade = findFilesForFacade(javaFileFacadeFqName, getContentSearchScope(tmoduleFindModule));
            ArrayList arrayList = new ArrayList();
            for (Object obj : collectionFindFilesForFacade) {
                if (JvmFileClassUtilKt.isJvmMultifileClassFile((KtFile) obj)) {
                    arrayList.add(obj);
                }
            }
            arrayListListOf = arrayList;
        } else {
            arrayListListOf = CollectionsKt.listOf(file);
        }
        List<? extends KtFile> list = arrayListListOf;
        boolean z = list instanceof Collection;
        if (z && list.isEmpty()) {
            return null;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((KtFile) it.next()).hasTopLevelCallables()) {
                if (!z || !list.isEmpty()) {
                    Iterator<T> it2 = list.iterator();
                    while (it2.hasNext()) {
                        if (((KtFile) it2.next()).getIsCompiled()) {
                            if (!z || !list.isEmpty()) {
                                Iterator<T> it3 = list.iterator();
                                while (it3.hasNext()) {
                                    if (!((KtFile) it3.next()).getIsCompiled()) {
                                        throw new IllegalStateException(("Source and compiled files are mixed: " + arrayListListOf).toString());
                                    }
                                }
                            }
                            return new LightClassCachedValue<>(createInstanceOfDecompiledLightFacade(javaFileFacadeFqName, arrayListListOf), librariesTracker(file));
                        }
                    }
                }
                return new LightClassCachedValue<>(createInstanceOfLightFacade(javaFileFacadeFqName, arrayListListOf), outOfBlockModificationTracker(file));
            }
        }
        return null;
    }

    private final boolean facadeIsPossible(KtFile ktFile) {
        if (ktFile.getIsCompiled()) {
            String name2 = ktFile.get$name();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (!StringsKt.endsWith$default(name2, ".class", false, 2, (Object) null)) {
                return false;
            }
        }
        if (ktFile.isScript()) {
            return false;
        }
        if (canHaveAdditionalFilesInFacade(ktFile)) {
            return true;
        }
        return ktFile.hasTopLevelCallables();
    }

    private final boolean canHaveAdditionalFilesInFacade(KtFile ktFile) {
        return !ktFile.getIsCompiled() && JvmFileClassUtilKt.isJvmMultifileClassFile(ktFile);
    }

    protected ModificationTracker projectWideOutOfBlockModificationTracker() {
        return KotlinModificationTrackerService.INSTANCE.getInstance(this.project).getOutOfBlockModificationTracker();
    }

    protected ModificationTracker outOfBlockModificationTracker(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return projectWideOutOfBlockModificationTracker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValueProvider.Result getLightFacade$lambda$5$lambda$4(KotlinAsJavaSupportBase this$0, KtFile file) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(file, "$file");
        return this$0.cachedValueResult(this$0.createLightFacade(file));
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public KtLightClassForFacade createFacadeForSyntheticFile(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return createInstanceOfLightFacade(JvmFileClassUtilKt.getJavaFileFacadeFqName(file), CollectionsKt.listOf(file));
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public Collection<KtLightClassForFacade> getFacadeClassesInPackage(FqName packageFqName, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return toFacadeClasses(findFilesForFacadeByPackage(packageFqName, scope));
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public Collection<KtLightClassForFacade> getFacadeClasses(FqName facadeFqName, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return toFacadeClasses(findFilesForFacade(facadeFqName, scope));
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public Collection<String> getFacadeNames(FqName packageFqName, GlobalSearchScope scope) {
        FqName javaFileFacadeFqName;
        Name nameShortName;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Collection<KtFile> collectionFindFilesForFacadeByPackage = findFilesForFacadeByPackage(packageFqName, scope);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (KtFile ktFile : collectionFindFilesForFacadeByPackage) {
            String strAsString = null;
            KtFile ktFile2 = facadeIsPossible(ktFile) ? ktFile : null;
            if (ktFile2 != null) {
                if (!facadeIsApplicable(findModule(ktFile2), ktFile)) {
                    ktFile2 = null;
                }
                if (ktFile2 != null && (javaFileFacadeFqName = JvmFileClassUtilKt.getJavaFileFacadeFqName(ktFile2)) != null && (nameShortName = javaFileFacadeFqName.shortName()) != null) {
                    strAsString = nameShortName.asString();
                }
            }
            if (strAsString != null) {
                linkedHashSet.add(strAsString);
            }
        }
        return CollectionsKt.toSet(linkedHashSet);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<KtLightClassForFacade> toFacadeClasses(Collection<? extends KtFile> collection) {
        Object next;
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (facadeIsPossible((KtFile) obj)) {
                arrayList.add(obj);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : arrayList) {
            KtFile ktFile = (KtFile) obj2;
            FacadeKey facadeKey = new FacadeKey(JvmFileClassUtilKt.getJavaFileFacadeFqName(ktFile), JvmFileClassUtilKt.isJvmMultifileClassFile(ktFile), findModule(ktFile));
            Object obj3 = linkedHashMap.get(facadeKey);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap.put(facadeKey, obj3);
            }
            ((List) obj3).add(obj2);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            FacadeKey facadeKey2 = (FacadeKey) entry.getKey();
            Iterator it = ((List) entry.getValue()).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!facadeIsApplicable(facadeKey2.getModule(), (KtFile) next));
            KtFile ktFile2 = (KtFile) next;
            KtLightClassForFacade lightFacade = ktFile2 != null ? getLightFacade(ktFile2) : null;
            if (lightFacade != null) {
                arrayList2.add(lightFacade);
            }
        }
        return arrayList2;
    }

    /* JADX INFO: compiled from: KotlinAsJavaSupportBase.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J\u000e\u0010\u0011\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\rJ2\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u0013J\u0013\u0010\u0014\u001a\u00020\u00062\b\u0010\u0015\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u000bR\u0013\u0010\u0007\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\r¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupportBase$FacadeKey;", "TModule", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "isMultifile", "", "module", "(Lorg/jetbrains/kotlin/name/FqName;ZLjava/lang/Object;)V", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "()Z", "getModule", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/name/FqName;ZLjava/lang/Object;)Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupportBase$FacadeKey;", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class FacadeKey<TModule> {
        private final FqName fqName;
        private final boolean isMultifile;
        private final TModule module;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ FacadeKey copy$default(FacadeKey facadeKey, FqName fqName, boolean z, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                fqName = facadeKey.fqName;
            }
            if ((i & 2) != 0) {
                z = facadeKey.isMultifile;
            }
            if ((i & 4) != 0) {
                obj = facadeKey.module;
            }
            return facadeKey.copy(fqName, z, obj);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FqName getFqName() {
            return this.fqName;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getIsMultifile() {
            return this.isMultifile;
        }

        public final TModule component3() {
            return this.module;
        }

        public final FacadeKey<TModule> copy(FqName fqName, boolean isMultifile, TModule module) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            return new FacadeKey<>(fqName, isMultifile, module);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FacadeKey)) {
                return false;
            }
            FacadeKey facadeKey = (FacadeKey) other;
            return Intrinsics.areEqual(this.fqName, facadeKey.fqName) && this.isMultifile == facadeKey.isMultifile && Intrinsics.areEqual(this.module, facadeKey.module);
        }

        public int hashCode() {
            int iHashCode = ((this.fqName.hashCode() * 31) + Boolean.hashCode(this.isMultifile)) * 31;
            TModule tmodule = this.module;
            return iHashCode + (tmodule == null ? 0 : tmodule.hashCode());
        }

        public String toString() {
            return "FacadeKey(fqName=" + this.fqName + ", isMultifile=" + this.isMultifile + ", module=" + this.module + ')';
        }

        public FacadeKey(FqName fqName, boolean z, TModule tmodule) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            this.fqName = fqName;
            this.isMultifile = z;
            this.module = tmodule;
        }

        public final FqName getFqName() {
            return this.fqName;
        }

        public final TModule getModule() {
            return this.module;
        }

        public final boolean isMultifile() {
            return this.isMultifile;
        }
    }

    public final LightClassCachedValue<KtLightClass> createLightClass(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        KtLightClass ktLightClass = null;
        if (KtLightClassForSourceDeclarationKt.shouldNotBeVisibleAsLightClass(classOrObject)) {
            return null;
        }
        KtFile containingKtFile = classOrObject.getContainingKtFile();
        Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
        DeclarationLocation declarationLocation = declarationLocation(containingKtFile);
        int i = declarationLocation == null ? -1 : WhenMappings.$EnumSwitchMapping$0[declarationLocation.ordinal()];
        if (i == 1) {
            return new LightClassCachedValue<>(createInstanceOfLightClass(classOrObject), outOfBlockModificationTracker(classOrObject));
        }
        if (i == 2) {
            return new LightClassCachedValue<>(createInstanceOfDecompiledLightClass(classOrObject), librariesTracker(classOrObject));
        }
        if (i == 3) {
            KotlinDeclarationNavigationPolicy kotlinDeclarationNavigationPolicy = (KotlinDeclarationNavigationPolicy) ApplicationManager.getApplication().getService(KotlinDeclarationNavigationPolicy.class);
            KtElement originalElement = kotlinDeclarationNavigationPolicy != null ? kotlinDeclarationNavigationPolicy.getOriginalElement(classOrObject) : null;
            KtClassOrObject ktClassOrObject = originalElement instanceof KtClassOrObject ? (KtClassOrObject) originalElement : null;
            if (ktClassOrObject != null) {
                if (classOrObject.equals(ktClassOrObject)) {
                    ktClassOrObject = null;
                }
                if (ktClassOrObject != null && !Intrinsics.areEqual((Object) this.recursiveGuard.get(), (Object) true)) {
                    try {
                        this.recursiveGuard.set(true);
                        KtLightClass lightClass = getLightClass(ktClassOrObject);
                        this.recursiveGuard.set(false);
                        ktLightClass = lightClass;
                    } catch (Throwable th) {
                        this.recursiveGuard.set(false);
                        throw th;
                    }
                }
            }
            return new LightClassCachedValue<>(ktLightClass, librariesTracker(classOrObject));
        }
        if (KtPsiFactoryKt.getAnalysisContext(containingKtFile) == null && containingKtFile.getOriginalFile().getVirtualFile() == null) {
            return null;
        }
        return new LightClassCachedValue<>(createInstanceOfLightClass(classOrObject), outOfBlockModificationTracker(classOrObject));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValueProvider.Result getLightClass$lambda$18$lambda$17(KotlinAsJavaSupportBase this$0, KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(classOrObject, "$classOrObject");
        return this$0.cachedValueResult(this$0.createLightClass(classOrObject));
    }

    public final LightClassCachedValue<KtLightClass> createLightScript(KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        PsiFile containingFile = script.getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        if (containingFile instanceof KtCodeFragment) {
            return null;
        }
        return new LightClassCachedValue<>(createInstanceOfLightScript(script), projectWideOutOfBlockModificationTracker());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValueProvider.Result getLightClassForScript$lambda$20$lambda$19(KotlinAsJavaSupportBase this$0, KtScript script) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(script, "$script");
        return this$0.cachedValueResult(this$0.createLightScript(script));
    }

    private final <T extends KtLightClass> CachedValueProvider.Result<T> cachedValueResult(LightClassCachedValue<T> lightClassCachedValue) {
        ModificationTracker modificationTrackerProjectWideOutOfBlockModificationTracker;
        KtLightClass value = lightClassCachedValue != null ? lightClassCachedValue.getValue() : null;
        if (lightClassCachedValue == null || (modificationTrackerProjectWideOutOfBlockModificationTracker = lightClassCachedValue.getTracker()) == null) {
            modificationTrackerProjectWideOutOfBlockModificationTracker = projectWideOutOfBlockModificationTracker();
        }
        CachedValueProvider.Result<T> resultCreateSingleDependency = CachedValueProvider.Result.createSingleDependency(value, modificationTrackerProjectWideOutOfBlockModificationTracker);
        Intrinsics.checkNotNullExpressionValue(resultCreateSingleDependency, "createSingleDependency(...)");
        return resultCreateSingleDependency;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public Collection<PsiClass> getScriptClasses(FqName scriptFqName, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(scriptFqName, "scriptFqName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        if (scriptFqName.isRoot()) {
            return CollectionsKt.emptyList();
        }
        Collection<KtScript> collectionFindFilesForScript = findFilesForScript(scriptFqName, scope);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collectionFindFilesForScript.iterator();
        while (it.hasNext()) {
            KtLightClass lightClassForScript = getLightClassForScript((KtScript) it.next());
            if (lightClassForScript != null) {
                arrayList.add(lightClassForScript);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public KtLightClassForFacade getLightFacade(final KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        KtFile ktFile = file;
        if (ktFile.isValid()) {
            return (KtLightClassForFacade) CachedValuesManager.getCachedValue((PsiElement) ktFile, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return KotlinAsJavaSupportBase.getLightFacade$lambda$5$lambda$4(this.f$0, file);
                }
            });
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public KtLightClass getLightClass(final KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        KtClassOrObject ktClassOrObject = classOrObject;
        if (ktClassOrObject.isValid()) {
            return (KtLightClass) CachedValuesManager.getCachedValue((PsiElement) ktClassOrObject, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return KotlinAsJavaSupportBase.getLightClass$lambda$18$lambda$17(this.f$0, classOrObject);
                }
            });
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public KtLightClass getLightClassForScript(final KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        KtScript ktScript = script;
        if (ktScript.isValid()) {
            return (KtLightClass) CachedValuesManager.getCachedValue((PsiElement) ktScript, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return KotlinAsJavaSupportBase.getLightClassForScript$lambda$20$lambda$19(this.f$0, script);
                }
            });
        }
        return null;
    }
}
