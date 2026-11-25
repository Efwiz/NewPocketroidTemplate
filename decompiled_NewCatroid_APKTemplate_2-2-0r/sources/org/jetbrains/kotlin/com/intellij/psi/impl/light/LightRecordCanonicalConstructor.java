package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.Objects;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.SyntheticElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;
import org.jetbrains.kotlin.com.intellij.util.VisibilityIcons;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class LightRecordCanonicalConstructor extends LightMethod implements SyntheticElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "containingClass";
        } else if (i != 2) {
            objArr[0] = "method";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightRecordCanonicalConstructor";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightRecordCanonicalConstructor";
        } else {
            objArr[1] = "getNavigationElement";
        }
        if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightRecordCanonicalConstructor(PsiMethod psiMethod, PsiClass psiClass) {
        super(psiMethod.getManager(), psiMethod, psiClass);
        if (psiMethod == null) {
            $$$reportNull$$$0(0);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return mo6092getContainingClass();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return mo6092getContainingClass().mo7392getNameIdentifier();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return getNavigationElement().getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiClass containingClass = mo6092getContainingClass();
        if (containingClass == null) {
            $$$reportNull$$$0(2);
        }
        return containingClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return mo6092getContainingClass().getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        RowIcon rowIconCreateLayeredIcon = IconManager.getInstance().createLayeredIcon(this, PlatformIcons.METHOD_ICON, ElementPresentationUtil.getFlags(this, false));
        if (BitUtil.isSet(i, 1)) {
            VisibilityIcons.setVisibilityIcon(mo6092getContainingClass().mo6095getModifierList(), rowIconCreateLayeredIcon);
        }
        return rowIconCreateLayeredIcon;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordCanonicalConstructor$1, reason: invalid class name */
    class AnonymousClass1 extends LightParameterListWrapper {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightRecordCanonicalConstructor$1", "getParameters"));
        }

        AnonymousClass1(PsiParameterList psiParameterList, PsiSubstitutor psiSubstitutor) {
            super(psiParameterList, psiSubstitutor);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: getParent */
        public PsiElement getMyParent() {
            return LightRecordCanonicalConstructor.this;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiFile getContainingFile() {
            return getMyParent().getContainingFile();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListWrapper, org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
        public PsiParameter[] getParameters() {
            PsiParameter[] psiParameterArr = (PsiParameter[]) ContainerUtil.map2Array(super.getParameters(), PsiParameter.class, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordCanonicalConstructor$1$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return this.f$0.m6624xa31c11d0((PsiParameter) obj);
                }
            });
            if (psiParameterArr == null) {
                $$$reportNull$$$0(0);
            }
            return psiParameterArr;
        }

        /* JADX INFO: renamed from: lambda$getParameters$0$org-jetbrains-kotlin-com-intellij-psi-impl-light-LightRecordCanonicalConstructor$1, reason: not valid java name */
        /* synthetic */ PsiParameter m6624xa31c11d0(PsiParameter psiParameter) {
            return new LightRecordConstructorParameter(psiParameter, this, LightRecordCanonicalConstructor.this.mySubstitutor, null);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiParameterList getParameterList() {
        return new AnonymousClass1(super.getParameterList(), this.mySubstitutor);
    }

    public static class LightRecordConstructorParameter extends LightParameterWrapper {
        private final LightParameterListWrapper myWrapper;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "wrapper";
            } else if (i == 2) {
                objArr[0] = "substitutor";
            } else if (i == 3 || i == 4) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightRecordCanonicalConstructor$LightRecordConstructorParameter";
            } else {
                objArr[0] = "p";
            }
            if (i == 3 || i == 4) {
                objArr[1] = "getNavigationElement";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightRecordCanonicalConstructor$LightRecordConstructorParameter";
            }
            if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        /* synthetic */ LightRecordConstructorParameter(PsiParameter psiParameter, LightParameterListWrapper lightParameterListWrapper, PsiSubstitutor psiSubstitutor, AnonymousClass1 anonymousClass1) {
            this(psiParameter, lightParameterListWrapper, psiSubstitutor);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private LightRecordConstructorParameter(PsiParameter psiParameter, LightParameterListWrapper lightParameterListWrapper, PsiSubstitutor psiSubstitutor) {
            super(psiParameter, psiSubstitutor);
            if (psiParameter == null) {
                $$$reportNull$$$0(0);
            }
            if (lightParameterListWrapper == null) {
                $$$reportNull$$$0(1);
            }
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(2);
            }
            this.myWrapper = lightParameterListWrapper;
        }

        public PsiRecordComponent getRecordComponent() {
            PsiClass psiClass = (PsiClass) PsiTreeUtil.getParentOfType(this, PsiClass.class);
            if (psiClass == null) {
                return null;
            }
            for (PsiRecordComponent psiRecordComponent : psiClass.getRecordComponents()) {
                if (Objects.equals(psiRecordComponent.getMyName(), getMyName())) {
                    return psiRecordComponent;
                }
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterWrapper, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
        /* JADX INFO: renamed from: getModifierList */
        public PsiModifierList mo6095getModifierList() {
            final PsiRecordComponent recordComponent = getRecordComponent();
            if (recordComponent == null) {
                return super.mo6095getModifierList();
            }
            return new LightModifierList(getPrototype()) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordCanonicalConstructor.LightRecordConstructorParameter.1
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightRecordCanonicalConstructor$LightRecordConstructorParameter$1", "getAnnotations"));
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
                public PsiAnnotation[] getAnnotations() {
                    PsiAnnotation[] annotations = recordComponent.getAnnotations();
                    if (annotations == null) {
                        $$$reportNull$$$0(0);
                    }
                    return annotations;
                }
            };
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getNavigationElement() {
            PsiRecordComponent recordComponent = getRecordComponent();
            if (recordComponent != null) {
                if (recordComponent == null) {
                    $$$reportNull$$$0(3);
                }
                return recordComponent;
            }
            PsiElement navigationElement = super.getNavigationElement();
            if (navigationElement == null) {
                $$$reportNull$$$0(4);
            }
            return navigationElement;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: getParent */
        public PsiElement getMyParent() {
            return this.myWrapper;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.Iconable
        public Icon getIcon(int i) {
            return getPrototype().getIcon(i);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiFile getContainingFile() {
            return getMyParent().getContainingFile();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return getPrototype().equals(((LightRecordConstructorParameter) obj).getPrototype());
        }

        public int hashCode() {
            return getPrototype().hashCode();
        }
    }
}
