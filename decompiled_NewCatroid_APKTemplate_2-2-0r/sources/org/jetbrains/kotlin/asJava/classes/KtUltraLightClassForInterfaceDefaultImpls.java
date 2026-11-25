package org.jetbrains.kotlin.asJava.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;

/* JADX INFO: compiled from: KtUltraLightClassForInterfaceDefaultImpls.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0011\u001a\u00020\u0012H\u0014J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0014J\b\u0010\u0016\u001a\u00020\u0000H\u0016J\n\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\n\u0010\u0019\u001a\u0004\u0018\u00010\u0018H\u0014J\n\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\u0013\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016¢\u0006\u0002\u0010\u001fJ\b\u0010 \u001a\u00020\u0015H\u0016J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\tH\u0016J\u000e\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001e0\tH\u0016J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0016J\n\u0010%\u001a\u0004\u0018\u00010\u001bH\u0016J\n\u0010&\u001a\u0004\u0018\u00010\u0015H\u0016J\n\u0010'\u001a\u0004\u0018\u00010\u001eH\u0016J\u0013\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u001dH\u0016¢\u0006\u0002\u0010*J\u0013\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016¢\u0006\u0002\u0010\u001fJ\n\u0010,\u001a\u0004\u0018\u00010-H\u0016J\u0013\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u001dH\u0016¢\u0006\u0002\u00100J\b\u00101\u001a\u00020\u0012H\u0016J\b\u00102\u001a\u00020\u0012H\u0016J\b\u00103\u001a\u00020\u0012H\u0016J\b\u00104\u001a\u00020\u0012H\u0016J\u0010\u00105\u001a\u00020\u00122\u0006\u00106\u001a\u00020\u0012H\u0016J\u0018\u00107\u001a\u00020\u00122\u0006\u00108\u001a\u00020\u001e2\u0006\u00109\u001a\u00020\u0012H\u0016J\b\u0010:\u001a\u00020\u0012H\u0016J\u000e\u0010;\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u0015H\u0016R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u0006?²\u0006\u000f\u0010@\u001a\u00070)¢\u0006\u0002\bAX\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForInterfaceDefaultImpls;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)V", "_ownMethods", "Lorg/jetbrains/kotlin/com/intellij/psi/util/CachedValue;", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "membersBuilder", "Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;", "getMembersBuilder", "()Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;", "membersBuilder$delegate", "Lkotlin/Lazy;", "computeIsFinal", "", "computeModifiersByPsi", "", "", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "createExtendsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "createImplementsList", "getContainingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "getInterfaces", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "()[Lcom/intellij/psi/PsiClass;", "getName", "getOwnFields", "Lorg/jetbrains/kotlin/asJava/elements/KtLightField;", "getOwnInnerClasses", "getOwnMethods", "getParent", "getQualifiedName", "getSuperClass", "getSuperTypes", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "()[Lcom/intellij/psi/PsiClassType;", "getSupers", "getTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "()[Lcom/intellij/psi/PsiTypeParameter;", "hasTypeParameters", "isAnnotationType", "isDeprecated", "isEnum", "isFinal", "isFinalByPsi", "isInheritor", "baseClass", "checkDeep", "isInterface", "ownMethods", "setName", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "name", "light-classes", "interfaceType", "Lorg/jetbrains/annotations/NotNull;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightClassForInterfaceDefaultImpls extends KtUltraLightClass {
    private final CachedValue<List<KtLightMethod>> _ownMethods;

    /* JADX INFO: renamed from: membersBuilder$delegate, reason: from kotlin metadata */
    private final Lazy membersBuilder;

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl
    protected boolean computeIsFinal() {
        return true;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration
    protected PsiReferenceList createExtendsList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration
    protected PsiReferenceList createImplementsList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isAnnotationType() {
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isEnum() {
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration
    public boolean isFinal(boolean isFinalByPsi) {
        return true;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInterface() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightClassForInterfaceDefaultImpls(final KtClassOrObject classOrObject, final KtUltraLightSupport support) {
        super(classOrObject, support);
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(support, "support");
        this.membersBuilder = ImplUtilsKt.lazyPub(new Function0<UltraLightMembersCreator>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForInterfaceDefaultImpls$membersBuilder$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final UltraLightMembersCreator invoke() {
                return new UltraLightMembersCreator(this.this$0, false, classOrObject.hasModifier(KtTokens.SEALED_KEYWORD), true, support);
            }
        });
        CachedValue<List<KtLightMethod>> cachedValueCreateCachedValue = CachedValuesManager.getManager(getProject()).createCachedValue(new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForInterfaceDefaultImpls$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return KtUltraLightClassForInterfaceDefaultImpls._ownMethods$lambda$5(this.f$0, classOrObject);
            }
        }, false);
        Intrinsics.checkNotNullExpressionValue(cachedValueCreateCachedValue, "createCachedValue(...)");
        this._ownMethods = cachedValueCreateCachedValue;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        String qualifiedName;
        KtLightClass ktLightClassMo6092getContainingClass = getContainingClass();
        if (ktLightClassMo6092getContainingClass == null || (qualifiedName = ktLightClassMo6092getContainingClass.getQualifiedName()) == null) {
            return null;
        }
        return qualifiedName + ".DefaultImpls";
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String get$name() {
        return JvmAbi.DEFAULT_IMPLS_CLASS_NAME;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public KtLightClass getLightParent() {
        return getContainingClass();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtUltraLightClassForInterfaceDefaultImpls copy() {
        PsiElement psiElementCopy = getClassOrObject().copy();
        Intrinsics.checkNotNull(psiElementCopy, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
        return new KtUltraLightClassForInterfaceDefaultImpls((KtClassOrObject) psiElementCopy, getSupport());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass getSuperClass() {
        return PsiClassImplUtil.getSuperClass(this);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getSupers() {
        PsiClass superClass = PsiClassImplUtil.getSuperClass(this);
        return superClass != null ? new PsiClass[]{superClass} : new PsiClass[0];
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getSuperTypes() {
        PsiClassType javaLangObject = PsiType.getJavaLangObject(getManager(), getResolveScope());
        Intrinsics.checkNotNullExpressionValue(javaLangObject, "getJavaLangObject(...)");
        return new PsiClassType[]{javaLangObject};
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl
    protected Set<String> computeModifiersByPsi() {
        return KtUltraLightClassForInterfaceDefaultImplsKt.publicStaticFinal;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightField> getOwnFields() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass baseClass, boolean checkDeep) {
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        return Intrinsics.areEqual(baseClass.getQualifiedName(), CommonClassNames.JAVA_LANG_OBJECT);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        throw new IncorrectOperationException("Impossible to rename DefaultImpls");
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public KtLightClass getContainingClass() {
        return LightClassUtilsKt.toLightClass(getClassOrObject());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiClass> getOwnInnerClasses() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightMethod> getOwnMethods() {
        List<KtLightMethod> value = this._ownMethods.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return value;
    }

    private final UltraLightMembersCreator getMembersBuilder() {
        return (UltraLightMembersCreator) this.membersBuilder.getValue();
    }

    private final List<KtLightMethod> ownMethods() {
        Lazy lazy = LazyKt.lazy(new Function0<PsiClassType>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForInterfaceDefaultImpls$ownMethods$interfaceType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiClassType invoke() {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(this.this$0.getProject());
                KtLightClass containingClass = this.this$0.getContainingClass();
                Intrinsics.checkNotNull(containingClass);
                return elementFactory.createType(containingClass);
            }
        });
        ArrayList arrayList = new ArrayList();
        KtLightClass ktLightClassMo6092getContainingClass = getContainingClass();
        if (ktLightClassMo6092getContainingClass != null) {
            ownMethods$processClass(arrayList, this, lazy, ktLightClassMo6092getContainingClass);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PsiClassType ownMethods$lambda$2(Lazy<? extends PsiClassType> lazy) {
        return lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValueProvider.Result _ownMethods$lambda$5(KtUltraLightClassForInterfaceDefaultImpls this$0, KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(classOrObject, "$classOrObject");
        return CachedValueProvider.Result.create(this$0.ownMethods(), ImplUtilsKt.getExternalDependencies(classOrObject));
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInterfaces() {
        return new PsiClass[0];
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        return new PsiTypeParameter[0];
    }

    private static final void ownMethods$processClass(List<KtLightMethod> list, final KtUltraLightClassForInterfaceDefaultImpls ktUltraLightClassForInterfaceDefaultImpls, final Lazy<? extends PsiClassType> lazy, PsiClass psiClass) {
        KtClassOrObject kotlinOrigin;
        List<KtDeclaration> declarations;
        List<KtDeclaration> listEmptyList = null;
        KtUltraLightClass ktUltraLightClass = (KtUltraLightClass) (!(psiClass instanceof KtUltraLightClass) ? null : psiClass);
        if (ktUltraLightClass != null && (kotlinOrigin = ktUltraLightClass.getKotlinOrigin()) != null && (declarations = kotlinOrigin.getDeclarations()) != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (!UltraLightUtilsKt.isHiddenByDeprecation((KtDeclaration) obj, ktUltraLightClassForInterfaceDefaultImpls.getSupport())) {
                    arrayList.add(obj);
                }
            }
            listEmptyList = arrayList;
        }
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        for (KtDeclaration ktDeclaration : listEmptyList) {
            if (ktDeclaration instanceof KtNamedFunction) {
                if (((KtNamedFunction) ktDeclaration).hasBody()) {
                    list.addAll(UltraLightMembersCreator.createMethods$default(ktUltraLightClassForInterfaceDefaultImpls.getMembersBuilder(), (KtFunction) ktDeclaration, true, false, true, new Function1<KtUltraLightMethod, KtUltraLightParameter>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForInterfaceDefaultImpls$ownMethods$processClass$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final KtUltraLightParameter invoke(KtUltraLightMethod it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            KtUltraLightSupport support$light_classes = this.this$0.getSupport();
                            final Lazy<PsiClassType> lazy2 = lazy;
                            return new KtUltraLightReceiverParameterForDefaultImpls(support$light_classes, it, new Function0<PsiType>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForInterfaceDefaultImpls$ownMethods$processClass$1.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                /* JADX WARN: Multi-variable type inference failed */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final PsiType invoke() {
                                    PsiClassType psiClassTypeOwnMethods$lambda$2 = KtUltraLightClassForInterfaceDefaultImpls.ownMethods$lambda$2(lazy2);
                                    Intrinsics.checkNotNullExpressionValue(psiClassTypeOwnMethods$lambda$2, "access$ownMethods$lambda$2(...)");
                                    return psiClassTypeOwnMethods$lambda$2;
                                }
                            });
                        }
                    }, 4, null));
                }
            } else if (ktDeclaration instanceof KtProperty) {
                KtProperty ktProperty = (KtProperty) ktDeclaration;
                List<KtPropertyAccessor> accessors = ktProperty.getAccessors();
                Intrinsics.checkNotNullExpressionValue(accessors, "getAccessors(...)");
                List<KtPropertyAccessor> list2 = accessors;
                if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                    Iterator<T> it = list2.iterator();
                    while (it.hasNext()) {
                        if (((KtPropertyAccessor) it.next()).hasBody()) {
                            list.addAll(ktUltraLightClassForInterfaceDefaultImpls.getMembersBuilder().propertyAccessors((KtCallableDeclaration) ktDeclaration, ktProperty.isVar(), true, false, (48 & 16) != 0 ? false : false, (48 & 32) != 0 ? false : false, (48 & 64) != 0 ? false : true, (48 & 128) != 0 ? null : new Function1<KtUltraLightMethod, KtUltraLightParameter>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForInterfaceDefaultImpls$ownMethods$processClass$3
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                /* JADX WARN: Multi-variable type inference failed */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final KtUltraLightParameter invoke(KtUltraLightMethod it2) {
                                    Intrinsics.checkNotNullParameter(it2, "it");
                                    KtUltraLightSupport support$light_classes = this.this$0.getSupport();
                                    final Lazy<PsiClassType> lazy2 = lazy;
                                    return new KtUltraLightReceiverParameterForDefaultImpls(support$light_classes, it2, new Function0<PsiType>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForInterfaceDefaultImpls$ownMethods$processClass$3.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        /* JADX WARN: Multi-variable type inference failed */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final PsiType invoke() {
                                            PsiClassType psiClassTypeOwnMethods$lambda$2 = KtUltraLightClassForInterfaceDefaultImpls.ownMethods$lambda$2(lazy2);
                                            Intrinsics.checkNotNullExpressionValue(psiClassTypeOwnMethods$lambda$2, "access$ownMethods$lambda$2(...)");
                                            return psiClassTypeOwnMethods$lambda$2;
                                        }
                                    });
                                }
                            }));
                            break;
                        }
                    }
                }
            }
        }
        PsiClass[] interfaces = psiClass.getInterfaces();
        Intrinsics.checkNotNullExpressionValue(interfaces, "getInterfaces(...)");
        for (PsiClass psiClass2 : interfaces) {
            Intrinsics.checkNotNull(psiClass2);
            ownMethods$processClass(list, ktUltraLightClassForInterfaceDefaultImpls, lazy, psiClass2);
        }
    }
}
