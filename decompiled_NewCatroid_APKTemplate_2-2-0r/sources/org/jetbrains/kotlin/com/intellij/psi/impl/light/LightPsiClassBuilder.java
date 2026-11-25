package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.ArrayList;
import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.psi.OriginInfoAwareElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;

/* JADX INFO: loaded from: classes3.dex */
public class LightPsiClassBuilder extends LightPsiClassBase implements OriginInfoAwareElement {
    private PsiClass myContainingClass;
    private final LightReferenceListBuilder myExtendsList;
    private final LightReferenceListBuilder myImplementsList;
    private final Collection<PsiMethod> myMethods;
    private final LightModifierList myModifierList;
    private String myOriginInfo;
    private PsiElement myScope;
    private final LightTypeParameterListBuilder myTypeParametersList;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "name";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightPsiClassBuilder";
                break;
            default:
                objArr[0] = "context";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getModifierList";
                break;
            case 3:
                objArr[1] = "getExtendsList";
                break;
            case 4:
                objArr[1] = "getImplementsList";
                break;
            case 5:
                objArr[1] = "getFields";
                break;
            case 6:
                objArr[1] = "getMethods";
                break;
            case 7:
                objArr[1] = "getInnerClasses";
                break;
            case 8:
                objArr[1] = "getInitializers";
                break;
            case 9:
                objArr[1] = "getTypeParameterList";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightPsiClassBuilder";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightPsiClassBuilder(PsiElement psiElement, String str) {
        super(psiElement, str);
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.myModifierList = new LightModifierList(getManager());
        this.myImplementsList = new LightReferenceListBuilder(getManager(), PsiReferenceList.Role.IMPLEMENTS_LIST);
        this.myExtendsList = new LightReferenceListBuilder(getManager(), PsiReferenceList.Role.EXTENDS_LIST);
        this.myTypeParametersList = new LightTypeParameterListBuilder(getManager(), getLanguage());
        this.myMethods = new ArrayList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.OriginInfoAwareElement
    public String getOriginInfo() {
        return this.myOriginInfo;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightPsiClassBase, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public LightModifierList mo6095getModifierList() {
        LightModifierList lightModifierList = this.myModifierList;
        if (lightModifierList == null) {
            $$$reportNull$$$0(2);
        }
        return lightModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public LightReferenceListBuilder getExtendsList() {
        LightReferenceListBuilder lightReferenceListBuilder = this.myExtendsList;
        if (lightReferenceListBuilder == null) {
            $$$reportNull$$$0(3);
        }
        return lightReferenceListBuilder;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public LightReferenceListBuilder getImplementsList() {
        LightReferenceListBuilder lightReferenceListBuilder = this.myImplementsList;
        if (lightReferenceListBuilder == null) {
            $$$reportNull$$$0(4);
        }
        return lightReferenceListBuilder;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getFields() {
        PsiField[] psiFieldArr = PsiField.EMPTY_ARRAY;
        if (psiFieldArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiFieldArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getMethods() {
        PsiMethod[] psiMethodArr = (PsiMethod[]) this.myMethods.toArray(PsiMethod.EMPTY_ARRAY);
        if (psiMethodArr == null) {
            $$$reportNull$$$0(6);
        }
        return psiMethodArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInnerClasses() {
        PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
        if (psiClassArr == null) {
            $$$reportNull$$$0(7);
        }
        return psiClassArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassInitializer[] getInitializers() {
        PsiClassInitializer[] psiClassInitializerArr = PsiClassInitializer.EMPTY_ARRAY;
        if (psiClassInitializerArr == null) {
            $$$reportNull$$$0(8);
        }
        return psiClassInitializerArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        return this.myScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        return this.myContainingClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public LightTypeParameterListBuilder mo6093getTypeParameterList() {
        LightTypeParameterListBuilder lightTypeParameterListBuilder = this.myTypeParametersList;
        if (lightTypeParameterListBuilder == null) {
            $$$reportNull$$$0(9);
        }
        return lightTypeParameterListBuilder;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return PsiClassImplUtil.isClassEquivalentTo(this, psiElement);
    }

    public LightPsiClassBuilder setOriginInfo(String str) {
        this.myOriginInfo = str;
        return this;
    }

    public LightPsiClassBuilder setScope(PsiElement psiElement) {
        this.myScope = psiElement;
        return this;
    }

    public LightPsiClassBuilder setContainingClass(PsiClass psiClass) {
        this.myContainingClass = psiClass;
        return this;
    }

    public LightPsiClassBuilder addMethod(PsiMethod psiMethod) {
        if (psiMethod instanceof LightMethodBuilder) {
            ((LightMethodBuilder) psiMethod).setContainingClass(this);
        }
        this.myMethods.add(psiMethod);
        return this;
    }
}
