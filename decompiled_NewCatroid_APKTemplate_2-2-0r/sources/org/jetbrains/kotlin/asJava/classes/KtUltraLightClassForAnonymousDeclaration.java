package org.jetbrains.kotlin.asJava.classes;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.elements.KtLightIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.InheritanceImplUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: compiled from: ultraLightClassForAnonymousDeclaration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000f\u001a\u00020\u0000H\u0016J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\n\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\r\u0010\u0016\u001a\u00070\u0017¢\u0006\u0002\b\nH\u0016J\b\u0010\u0018\u001a\u00020\tH\u0016J\n\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\n\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\n\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\u0010\u0010#\u001a\u00020\u00112\u0006\u0010$\u001a\u00020\u001eH\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020\u0011H\u0016J\b\u0010(\u001a\u00020\u0011H\u0016J\b\u0010)\u001a\u00020\u0011H\u0016J\u0018\u0010*\u001a\u00020\u00112\u0006\u0010+\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020\u0011H\u0016J\b\u0010-\u001a\u00020\u0011H\u0016R \u0010\b\u001a\u00070\t¢\u0006\u0002\b\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForAnonymousDeclaration;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForLocalDeclaration;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnonymousClass;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)V", "_baseClassType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "Lorg/jetbrains/annotations/NotNull;", "get_baseClassType", "()Lcom/intellij/psi/PsiClassType;", "_baseClassType$delegate", "Lkotlin/Lazy;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "getArgumentList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiExpressionList;", "getBaseClassReference", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaCodeReferenceElement;", "getBaseClassType", "getContainingClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getName", "", "getNameIdentifier", "Lorg/jetbrains/kotlin/asJava/elements/KtLightIdentifier;", "getTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "hasModifierProperty", "name", "hashCode", "", "isAnnotationType", "isEnum", "isInQualifiedNew", "isInheritor", "baseClass", "checkDeep", "isInterface", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtUltraLightClassForAnonymousDeclaration extends KtUltraLightClassForLocalDeclaration implements PsiAnonymousClass {

    /* JADX INFO: renamed from: _baseClassType$delegate, reason: from kotlin metadata */
    private final Lazy _baseClassType;

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiExpressionList getArgumentList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public PsiClass getContainingClass() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public KtLightIdentifier mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isAnnotationType() {
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isEnum() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public boolean isInQualifiedNew() {
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInterface() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightClassForAnonymousDeclaration(KtClassOrObject classOrObject, KtUltraLightSupport support) {
        super(classOrObject, support);
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(support, "support");
        this._baseClassType = ImplUtilsKt.lazyPub(new Function0<PsiClassType>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForAnonymousDeclaration$_baseClassType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiClassType invoke() {
                PsiClassType psiClassType;
                PsiClassType psiClassType2;
                String firstSupertypeFQNameForAnonymousDeclaration = UltraLightClassForAnonymousDeclarationKt.getFirstSupertypeFQNameForAnonymousDeclaration(this.this$0);
                if (Intrinsics.areEqual(firstSupertypeFQNameForAnonymousDeclaration, CommonClassNames.JAVA_LANG_OBJECT)) {
                    return PsiType.getJavaLangObject(this.this$0.getKtDeclaration().getManager(), this.this$0.getResolveScope());
                }
                PsiClassType[] extendsListTypes = this.this$0.getExtendsListTypes();
                Intrinsics.checkNotNullExpressionValue(extendsListTypes, "getExtendsListTypes(...)");
                PsiClassType[] psiClassTypeArr = extendsListTypes;
                int length = psiClassTypeArr.length;
                int i = 0;
                while (true) {
                    psiClassType = null;
                    if (i >= length) {
                        psiClassType2 = null;
                        break;
                    }
                    psiClassType2 = psiClassTypeArr[i];
                    PsiClass psiClassResolve = psiClassType2.resolve();
                    if (Intrinsics.areEqual(psiClassResolve != null ? psiClassResolve.getQualifiedName() : null, firstSupertypeFQNameForAnonymousDeclaration)) {
                        break;
                    }
                    i++;
                }
                PsiClassType psiClassType3 = psiClassType2;
                if (psiClassType3 != null) {
                    return psiClassType3;
                }
                PsiClassType[] implementsListTypes = this.this$0.getImplementsListTypes();
                Intrinsics.checkNotNullExpressionValue(implementsListTypes, "getImplementsListTypes(...)");
                for (PsiClassType psiClassType4 : implementsListTypes) {
                    PsiClass psiClassResolve2 = psiClassType4.resolve();
                    if (Intrinsics.areEqual(psiClassResolve2 != null ? psiClassResolve2.getQualifiedName() : null, firstSupertypeFQNameForAnonymousDeclaration)) {
                        psiClassType = psiClassType4;
                        break;
                    }
                }
                PsiClassType psiClassType5 = psiClassType;
                return psiClassType5 != null ? psiClassType5 : PsiType.getJavaLangObject(this.this$0.getKtDeclaration().getManager(), this.this$0.getResolveScope());
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiJavaCodeReferenceElement getBaseClassReference() {
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceElementByType = JavaPsiFacade.getElementFactory(getClassOrObject().getProject()).createReferenceElementByType(getBaseClassType());
        Intrinsics.checkNotNullExpressionValue(psiJavaCodeReferenceElementCreateReferenceElementByType, "createReferenceElementByType(...)");
        return psiJavaCodeReferenceElementCreateReferenceElementByType;
    }

    private final PsiClassType get_baseClassType() {
        return (PsiClassType) this._baseClassType.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiClassType getBaseClassType() {
        PsiClassType psiClassType = get_baseClassType();
        Intrinsics.checkNotNullExpressionValue(psiClassType, "<get-_baseClassType>(...)");
        return psiClassType;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.asJava.classes.KtLightClassBase
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
            return false;
        }
        return Intrinsics.areEqual(getClassOrObject(), ((KtUltraLightClassForAnonymousDeclaration) other).getClassOrObject());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass baseClass, boolean checkDeep) {
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        if (baseClass instanceof KtLightClassForSourceDeclaration) {
            return super.isInheritor(baseClass, checkDeep);
        }
        return InheritanceImplUtil.isInheritor(this, baseClass, checkDeep);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.asJava.classes.KtLightClassBase
    public int hashCode() {
        return getClassOrObject().hashCode();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return Intrinsics.areEqual(name2, "final");
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForLocalDeclaration, org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtUltraLightClassForAnonymousDeclaration copy() {
        return new KtUltraLightClassForAnonymousDeclaration(getClassOrObject(), getSupport());
    }
}
