package org.jetbrains.kotlin.asJava.classes;

import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: compiled from: KtUltraLightClassForRepeatableAnnotationContainer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0000H\u0016J\n\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\n\u0010\u0016\u001a\u0004\u0018\u00010\bH\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000eH\u0016J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000eH\u0016J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J\n\u0010\u001e\u001a\u0004\u0018\u00010\u0015H\u0016J\n\u0010\u001f\u001a\u0004\u0018\u00010\u0018H\u0016J\n\u0010 \u001a\u0004\u0018\u00010!H\u0016J\n\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\u0013\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%H\u0016¢\u0006\u0002\u0010'J\b\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020)H\u0016J\b\u0010+\u001a\u00020)H\u0016J\b\u0010,\u001a\u00020)H\u0016J\u0010\u0010-\u001a\u00020)2\u0006\u0010.\u001a\u00020)H\u0016J\u0018\u0010/\u001a\u00020)2\u0006\u00100\u001a\u00020\u001c2\u0006\u00101\u001a\u00020)H\u0016J\b\u00102\u001a\u00020)H\u0016J\u0010\u00103\u001a\u00020!2\u0006\u00104\u001a\u00020\u0018H\u0016R\u001d\u0010\u0007\u001a\u0004\u0018\u00010\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\f\u001a\u0004\b\u0010\u0010\u0011¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForRepeatableAnnotationContainer;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)V", "_modifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "get_modifierList", "()Lcom/intellij/psi/PsiModifierList;", "_modifierList$delegate", "Lkotlin/Lazy;", "_ownMethods", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "get_ownMethods", "()Ljava/util/List;", "_ownMethods$delegate", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "getContainingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "getModifierList", "getName", "", "getOwnFields", "Lorg/jetbrains/kotlin/asJava/elements/KtLightField;", "getOwnInnerClasses", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "getOwnMethods", "getParent", "getQualifiedName", "getScope", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "getTypeParameters", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "()[Lcom/intellij/psi/PsiTypeParameter;", "hasTypeParameters", "", "isAnnotationType", "isDeprecated", "isEnum", "isFinal", "isFinalByPsi", "isInheritor", "baseClass", "checkDeep", "isInterface", "setName", "name", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightClassForRepeatableAnnotationContainer extends KtUltraLightClass {

    /* JADX INFO: renamed from: _modifierList$delegate, reason: from kotlin metadata */
    private final Lazy _modifierList;

    /* JADX INFO: renamed from: _ownMethods$delegate, reason: from kotlin metadata */
    private final Lazy _ownMethods;

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
        return true;
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
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInterface() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightClassForRepeatableAnnotationContainer(KtClassOrObject classOrObject, final KtUltraLightSupport support) {
        super(classOrObject, support);
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(support, "support");
        this._modifierList = ImplUtilsKt.lazyPub(new Function0<KtUltraLightModifierListForRepeatableAnnotationContainer>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForRepeatableAnnotationContainer$_modifierList$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtUltraLightModifierListForRepeatableAnnotationContainer invoke() {
                return new KtUltraLightModifierListForRepeatableAnnotationContainer(this.this$0, support);
            }
        });
        this._ownMethods = ImplUtilsKt.lazyPub(new KtUltraLightClassForRepeatableAnnotationContainer$_ownMethods$2(this, support));
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        String qualifiedName;
        KtLightClass ktLightClassMo6092getContainingClass = mo6092getContainingClass();
        if (ktLightClassMo6092getContainingClass == null || (qualifiedName = ktLightClassMo6092getContainingClass.getQualifiedName()) == null) {
            return null;
        }
        return qualifiedName + ".Container";
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        return JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public KtLightClass getMyParent() {
        return mo6092getContainingClass();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public KtLightClass mo6092getContainingClass() {
        return LightClassUtilsKt.toLightClass(getClassOrObject());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        return mo6092getContainingClass();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiClass> getOwnInnerClasses() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightField> getOwnFields() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightMethod> getOwnMethods() {
        return get_ownMethods();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return get_modifierList();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtUltraLightClassForRepeatableAnnotationContainer copy() {
        PsiElement psiElementCopy = getClassOrObject().copy();
        Intrinsics.checkNotNull(psiElementCopy, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
        return new KtUltraLightClassForRepeatableAnnotationContainer((KtClassOrObject) psiElementCopy, getSupport());
    }

    private final PsiModifierList get_modifierList() {
        return (PsiModifierList) this._modifierList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass baseClass, boolean checkDeep) {
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        return Intrinsics.areEqual(baseClass.getQualifiedName(), CommonClassNames.JAVA_LANG_ANNOTATION_ANNOTATION);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        throw new IncorrectOperationException("Impossible to rename Container");
    }

    private final List<KtLightMethod> get_ownMethods() {
        return (List) this._ownMethods.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        return new PsiTypeParameter[0];
    }
}
