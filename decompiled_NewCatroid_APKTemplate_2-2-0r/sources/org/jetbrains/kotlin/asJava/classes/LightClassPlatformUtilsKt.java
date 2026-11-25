package org.jetbrains.kotlin.asJava.classes;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.asJava.LightClassUtil;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClass;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightField;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: lightClassPlatformUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0000\u001a\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00032\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"collectAugments", "", "Psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, ModuleXmlParser.TYPE, "Ljava/lang/Class;", "getParentForLocalDeclaration", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "light-classes-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LightClassPlatformUtilsKt {
    public static final <Psi extends PsiElement> List<Psi> collectAugments(PsiElement element, Class<? extends Psi> type) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(type, "type");
        List<Psi> listCollectAugments = PsiAugmentProvider.collectAugments(element, type, null);
        Intrinsics.checkNotNullExpressionValue(listCollectAugments, "collectAugments(...)");
        return listCollectAugments;
    }

    private static final <T extends PsiMember> T getParentForLocalDeclaration$wrapMember(KtClassOrObject ktClassOrObject, T t, boolean z, Function2<? super T, ? super PsiClass, ? extends T> function2) {
        final PsiClass containingClass = t.getContainingClass();
        if (containingClass == null) {
            return null;
        }
        if (!(containingClass instanceof KtLightClassForFacade)) {
            return z ? function2.invoke(t, containingClass) : t;
        }
        final String name2 = ktClassOrObject.getContainingFile().get$name();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        final KotlinLanguage kotlinLanguage = KotlinLanguage.INSTANCE;
        return function2.invoke(t, new LightClass(containingClass, name2, kotlinLanguage) { // from class: org.jetbrains.kotlin.asJava.classes.LightClassPlatformUtilsKt$getParentForLocalDeclaration$wrapMember$wrappedClass$1
            final /* synthetic */ String $facadeClassName;

            {
                KotlinLanguage kotlinLanguage2 = kotlinLanguage;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
            /* JADX INFO: renamed from: getName, reason: from getter */
            public String get$name() {
                return this.$facadeClassName;
            }
        });
    }

    static /* synthetic */ PsiMethod getParentForLocalDeclaration$wrapMethod$default(KtClassOrObject ktClassOrObject, PsiMethod psiMethod, String str, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            str = psiMethod.get$name();
            Intrinsics.checkNotNullExpressionValue(str, "getName(...)");
        }
        return getParentForLocalDeclaration$wrapMethod(ktClassOrObject, psiMethod, str, z);
    }

    private static final PsiMethod getParentForLocalDeclaration$wrapMethod(KtClassOrObject ktClassOrObject, PsiMethod psiMethod, final String str, boolean z) {
        return (PsiMethod) getParentForLocalDeclaration$wrapMember(ktClassOrObject, psiMethod, z, new Function2<PsiMethod, PsiClass, PsiMethod>() { // from class: org.jetbrains.kotlin.asJava.classes.LightClassPlatformUtilsKt$getParentForLocalDeclaration$wrapMethod$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final PsiMethod invoke(PsiMethod method, PsiClass containingClass) {
                Intrinsics.checkNotNullParameter(method, "method");
                Intrinsics.checkNotNullParameter(containingClass, "containingClass");
                return new LightMethod(method, containingClass, str, containingClass.getManager(), KotlinLanguage.INSTANCE) { // from class: org.jetbrains.kotlin.asJava.classes.LightClassPlatformUtilsKt$getParentForLocalDeclaration$wrapMethod$1.1
                    final /* synthetic */ PsiClass $containingClass;
                    final /* synthetic */ String $name;

                    {
                        this.$containingClass = containingClass;
                        this.$name = str;
                        KotlinLanguage kotlinLanguage = kotlinLanguage;
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
                    /* JADX INFO: renamed from: getParent */
                    public PsiElement getLightParent() {
                        return this.$containingClass;
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
                    /* JADX INFO: renamed from: getName, reason: from getter */
                    public String get$name() {
                        return this.$name;
                    }
                };
            }
        });
    }

    private static final PsiField getParentForLocalDeclaration$wrapField(KtClassOrObject ktClassOrObject, PsiField psiField, boolean z) {
        return (PsiField) getParentForLocalDeclaration$wrapMember(ktClassOrObject, psiField, z, new Function2<PsiField, PsiClass, PsiField>() { // from class: org.jetbrains.kotlin.asJava.classes.LightClassPlatformUtilsKt$getParentForLocalDeclaration$wrapField$1
            @Override // kotlin.jvm.functions.Function2
            public final PsiField invoke(PsiField field, PsiClass containingClass) {
                Intrinsics.checkNotNullParameter(field, "field");
                Intrinsics.checkNotNullParameter(containingClass, "containingClass");
                return new LightField(field, containingClass, containingClass.getManager()) { // from class: org.jetbrains.kotlin.asJava.classes.LightClassPlatformUtilsKt$getParentForLocalDeclaration$wrapField$1.1
                    final /* synthetic */ PsiClass $containingClass;

                    {
                        this.$containingClass = containingClass;
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
                    /* JADX INFO: renamed from: getParent */
                    public PsiElement getLightParent() {
                        return this.$containingClass;
                    }
                };
            }
        });
    }

    private static final PsiElement getParentForLocalDeclaration$map(KtClassOrObject ktClassOrObject, KtElement ktElement) {
        if (ktElement instanceof KtFunction) {
            KtFunction ktFunction = (KtFunction) ktElement;
            PsiMethod lightClassMethod = LightClassUtil.INSTANCE.getLightClassMethod(ktFunction);
            if (lightClassMethod == null) {
                return null;
            }
            String name2 = ktFunction.get$name();
            if (name2 == null) {
                name2 = lightClassMethod.get$name();
            }
            Intrinsics.checkNotNull(name2);
            return getParentForLocalDeclaration$wrapMethod(ktClassOrObject, lightClassMethod, name2, false);
        }
        if (ktElement instanceof KtPropertyAccessor) {
            PsiMethod lightClassAccessorMethod = LightClassUtil.INSTANCE.getLightClassAccessorMethod((KtPropertyAccessor) ktElement);
            if (lightClassAccessorMethod != null) {
                return getParentForLocalDeclaration$wrapMethod$default(ktClassOrObject, lightClassAccessorMethod, null, true, 4, null);
            }
            return null;
        }
        if (ktElement instanceof KtProperty) {
            KtProperty ktProperty = (KtProperty) ktElement;
            if (ktProperty.isLocal()) {
                return null;
            }
            LightClassUtil.PropertyAccessorsPsiMethods lightClassPropertyMethods = LightClassUtil.INSTANCE.getLightClassPropertyMethods(ktProperty);
            PsiMethod getter = lightClassPropertyMethods.getGetter();
            if (getter != null) {
                return getParentForLocalDeclaration$wrapMethod$default(ktClassOrObject, getter, null, true, 4, null);
            }
            PsiField backingField = lightClassPropertyMethods.getBackingField();
            if (backingField != null) {
                return getParentForLocalDeclaration$wrapField(ktClassOrObject, backingField, false);
            }
            return null;
        }
        if (ktElement instanceof KtAnonymousInitializer) {
            PsiElement parent = ((KtAnonymousInitializer) ktElement).getLightParent();
            PsiElement lightParent = parent.getLightParent();
            if ((parent instanceof KtClassBody) && (lightParent instanceof KtClassOrObject)) {
                return LightClassUtilsKt.toLightClass((KtClassOrObject) lightParent);
            }
            return null;
        }
        if (ktElement instanceof KtClass) {
            return LightClassUtilsKt.toLightClass((KtClassOrObject) ktElement);
        }
        return null;
    }

    public static final PsiElement getParentForLocalDeclaration(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Sequence sequenceFilter = SequencesKt.filter(PsiUtilsKt.getParents(classOrObject), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.LightClassPlatformUtilsKt$getParentForLocalDeclaration$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof KtElement);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        Iterator it = sequenceFilter.iterator();
        while (it.hasNext()) {
            PsiElement parentForLocalDeclaration$map = getParentForLocalDeclaration$map(classOrObject, (KtElement) it.next());
            if (parentForLocalDeclaration$map != null) {
                return parentForLocalDeclaration$map;
            }
        }
        return null;
    }
}
