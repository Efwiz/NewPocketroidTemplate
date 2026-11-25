package org.jetbrains.kotlin.asJava.classes;

import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.KotlinModificationTrackerService;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: compiled from: KtLightClassBase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0014J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u000eH¦\u0002J\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0010H\u0016J\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0010H\u0016J#\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0010H\u0016¢\u0006\u0002\u0010\u001cJ;\u0010\u001d\u001a0\u0012\f\u0012\n \u001e*\u0004\u0018\u00010\u00130\u0013 \u001e*\u0017\u0012\u000e\b\u0001\u0012\n \u001e*\u0004\u0018\u00010\u00130\u00130\u001a¢\u0006\u0002\b\u001f0\u001a¢\u0006\u0002\b\u001fH\u0016¢\u0006\u0002\u0010 J;\u0010!\u001a0\u0012\f\u0012\n \u001e*\u0004\u0018\u00010\u00180\u0018 \u001e*\u0017\u0012\u000e\b\u0001\u0012\n \u001e*\u0004\u0018\u00010\u00180\u00180\u001a¢\u0006\u0002\b\u001f0\u001a¢\u0006\u0002\b\u001fH\u0016¢\u0006\u0002\u0010\"J;\u0010#\u001a0\u0012\f\u0012\n \u001e*\u0004\u0018\u00010\u001b0\u001b \u001e*\u0017\u0012\u000e\b\u0001\u0012\n \u001e*\u0004\u0018\u00010\u001b0\u001b0\u001a¢\u0006\u0002\b\u001f0\u001a¢\u0006\u0002\b\u001fH\u0016¢\u0006\u0002\u0010$J\u0013\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0016¢\u0006\u0002\u0010$J\u0010\u0010&\u001a\n \u001e*\u0004\u0018\u00010'0'H\u0016J\b\u0010(\u001a\u00020)H\u0016J\u0013\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00130\u001aH\u0016¢\u0006\u0002\u0010 J\u0015\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00180\u001aH\u0016¢\u0006\u0002\u0010\"J\b\u0010,\u001a\u00020-H\u0016J\u0013\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0016¢\u0006\u0002\u0010$J\u000e\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00130\rH&J\u000e\u00100\u001a\b\u0012\u0004\u0012\u00020\u001b0\rH&J\u000f\u00101\u001a\t\u0018\u000102¢\u0006\u0002\b3H\u0016J\b\u00104\u001a\u00020\u0015H\u0016J\b\u00105\u001a\u000206H&J\u0012\u00107\u001a\u00020\u00102\b\u00108\u001a\u0004\u0018\u00010'H\u0016R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtLightClassBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/AbstractLightClass;", "Lorg/jetbrains/kotlin/asJava/classes/KtExtensibleLightClass;", "manager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "(Lcom/intellij/psi/PsiManager;)V", "myInnersCache", "Lorg/jetbrains/kotlin/asJava/classes/KotlinClassInnerStuffCache;", "getMyInnersCache", "()Lorg/jetbrains/kotlin/asJava/classes/KotlinClassInnerStuffCache;", "myInnersCache$delegate", "Lkotlin/Lazy;", "cacheDependencies", "", "", Namer.EQUALS_METHOD_NAME, "", "other", "findFieldByName", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiField;", "name", "", "checkBases", "findInnerClassByName", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "findMethodsByName", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "(Ljava/lang/String;Z)[Lcom/intellij/psi/PsiMethod;", "getAllFields", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/annotations/NotNull;", "()[Lcom/intellij/psi/PsiField;", "getAllInnerClasses", "()[Lcom/intellij/psi/PsiClass;", "getAllMethods", "()[Lcom/intellij/psi/PsiMethod;", "getConstructors", "getContext", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getDelegate", "", "getFields", "getInnerClasses", "getLanguage", "Lorg/jetbrains/kotlin/idea/KotlinLanguage;", "getMethods", "getOwnFields", "getOwnMethods", "getPresentation", "Lorg/jetbrains/kotlin/com/intellij/navigation/ItemPresentation;", "Lorg/jetbrains/annotations/Nullable;", "getText", "hashCode", "", "isEquivalentTo", "another", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightClassBase extends AbstractLightClass implements KtExtensibleLightClass {

    /* JADX INFO: renamed from: myInnersCache$delegate, reason: from kotlin metadata */
    private final Lazy myInnersCache;

    public abstract boolean equals(Object other);

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public abstract List<PsiField> getOwnFields();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public abstract List<PsiMethod> getOwnMethods();

    public abstract int hashCode();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass
    /* JADX INFO: renamed from: getDelegate, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ PsiClass mo6090getDelegate() {
        return (PsiClass) getDelegate();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected KtLightClassBase(PsiManager manager) {
        super(manager, KotlinLanguage.INSTANCE);
        Intrinsics.checkNotNullParameter(manager, "manager");
        this.myInnersCache = ImplUtilsKt.lazyPub(new Function0<KotlinClassInnerStuffCache>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassBase$myInnersCache$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinClassInnerStuffCache invoke() {
                KtLightClassBase ktLightClassBase = this.this$0;
                List<Object> listCacheDependencies = ktLightClassBase.cacheDependencies();
                Project project = this.this$0.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                return new KotlinClassInnerStuffCache(ktLightClassBase, listCacheDependencies, new LightClassesLazyCreator(project), false, 8, null);
            }
        });
    }

    private final KotlinClassInnerStuffCache getMyInnersCache() {
        return (KotlinClassInnerStuffCache) this.myInnersCache.getValue();
    }

    protected List<Object> cacheDependencies() {
        KotlinModificationTrackerService.Companion companion = KotlinModificationTrackerService.INSTANCE;
        Project project = getManager().getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return CollectionsKt.listOf(companion.getInstance(project).getOutOfBlockModificationTracker());
    }

    public Void getDelegate() {
        throw new UnsupportedOperationException("Cls delegate shouldn't be loaded for ultra-light classes!");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getFields() {
        return getMyInnersCache().getFields();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getMethods() {
        return getMyInnersCache().getMethods();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getConstructors() {
        return getMyInnersCache().getConstructors();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInnerClasses() {
        return getMyInnersCache().getInnerClasses();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getAllFields() {
        PsiField[] allFields = PsiClassImplUtil.getAllFields(this);
        Intrinsics.checkNotNullExpressionValue(allFields, "getAllFields(...)");
        return allFields;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getAllMethods() {
        PsiMethod[] allMethods = PsiClassImplUtil.getAllMethods(this);
        Intrinsics.checkNotNullExpressionValue(allMethods, "getAllMethods(...)");
        return allMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getAllInnerClasses() {
        PsiClass[] allInnerClasses = PsiClassImplUtil.getAllInnerClasses(this);
        Intrinsics.checkNotNullExpressionValue(allInnerClasses, "getAllInnerClasses(...)");
        return allInnerClasses;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField findFieldByName(String name2, boolean checkBases) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getMyInnersCache().findFieldByName(name2, checkBases);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] findMethodsByName(String name2, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getMyInnersCache().findMethodsByName(name2, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass findInnerClassByName(String name2, boolean checkBases) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getMyInnersCache().findInnerClassByName(name2, checkBases);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        KtClassOrObject ktClassOrObject = (KtClassOrObject) getKotlinOrigin();
        String text = ktClassOrObject != null ? ktClassOrObject.getText() : null;
        return text == null ? "" : text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KotlinLanguage getLanguage() {
        KotlinLanguage INSTANCE = KotlinLanguage.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
        return INSTANCE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return getMyParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        return PsiClassImplUtil.isClassEquivalentTo(this, another);
    }
}
