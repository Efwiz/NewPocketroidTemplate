package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import javax.swing.Icon;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationTargetUtil;
import org.jetbrains.kotlin.com.intellij.model.BranchableSyntheticPsiElement;
import org.jetbrains.kotlin.com.intellij.model.ModelBranch;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;
import org.jetbrains.kotlin.com.intellij.util.VisibilityIcons;

/* JADX INFO: loaded from: classes3.dex */
public final class LightRecordMethod extends LightMethod implements BranchableSyntheticPsiElement, LightRecordMember {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final PsiRecordComponent myRecordComponent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 6 || i == 7 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 6 || i == 7 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "method";
                break;
            case 2:
                objArr[0] = "containingClass";
                break;
            case 3:
                objArr[0] = "component";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightRecordMethod";
                break;
            case 8:
            case 9:
                objArr[0] = "fqn";
                break;
            case 10:
                objArr[0] = ConfigConstants.CONFIG_BRANCH_SECTION;
                break;
            case 12:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 4) {
            objArr[1] = "getRecordComponent";
        } else if (i == 5) {
            objArr[1] = "getNavigationElement";
        } else if (i == 6 || i == 7) {
            objArr[1] = "getAnnotations";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightRecordMethod";
        } else {
            objArr[1] = "obtainBranchCopy";
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
                break;
            case 8:
                objArr[2] = "hasAnnotation";
                break;
            case 9:
                objArr[2] = "getAnnotation";
                break;
            case 10:
                objArr[2] = "obtainBranchCopy";
                break;
            case 12:
                objArr[2] = "setName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6 && i != 7 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightRecordMethod(PsiManager psiManager, PsiMethod psiMethod, PsiClass psiClass, PsiRecordComponent psiRecordComponent) {
        super(psiManager, psiMethod, psiClass);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(1);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        if (psiRecordComponent == null) {
            $$$reportNull$$$0(3);
        }
        this.myRecordComponent = psiRecordComponent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordMember
    public PsiRecordComponent getRecordComponent() {
        PsiRecordComponent psiRecordComponent = this.myRecordComponent;
        if (psiRecordComponent == null) {
            $$$reportNull$$$0(4);
        }
        return psiRecordComponent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return this.myRecordComponent.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiElement navigationElement = this.myRecordComponent.getNavigationElement();
        if (navigationElement == null) {
            $$$reportNull$$$0(5);
        }
        return navigationElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return mo6092getContainingClass().getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiType getReturnType() {
        return DumbService.isDumb(this.myRecordComponent.getProject()) ? this.myRecordComponent.getType() : (PsiType) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordMethod$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6628x7158f55c();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getReturnType$2$org-jetbrains-kotlin-com-intellij-psi-impl-light-LightRecordMethod, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6628x7158f55c() {
        return CachedValueProvider.Result.create(this.myRecordComponent.getType().annotate(new TypeAnnotationProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordMethod$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
            public final PsiAnnotation[] getAnnotations() {
                return this.f$0.m6627xd4eaf8fd();
            }
        }), this);
    }

    static /* synthetic */ PsiAnnotation[] lambda$getReturnType$0(int i) {
        return new PsiAnnotation[i];
    }

    /* JADX INFO: renamed from: lambda$getReturnType$1$org-jetbrains-kotlin-com-intellij-psi-impl-light-LightRecordMethod, reason: not valid java name */
    /* synthetic */ PsiAnnotation[] m6627xd4eaf8fd() {
        return (PsiAnnotation[]) Arrays.stream(this.myRecordComponent.getAnnotations()).filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordMethod$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return LightRecordMethod.hasTargetApplicableForMethod((PsiAnnotation) obj);
            }
        }).toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordMethod$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return LightRecordMethod.lambda$getReturnType$0(i);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
    public PsiAnnotation[] getAnnotations() {
        PsiType returnType = getReturnType();
        if (returnType == null) {
            PsiAnnotation[] psiAnnotationArr = PsiAnnotation.EMPTY_ARRAY;
            if (psiAnnotationArr == null) {
                $$$reportNull$$$0(6);
            }
            return psiAnnotationArr;
        }
        PsiAnnotation[] annotations = returnType.getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(7);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        PsiType returnType = getReturnType();
        return returnType != null && returnType.hasAnnotation(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
    public PsiAnnotation getAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        PsiType returnType = getReturnType();
        if (returnType == null) {
            return null;
        }
        return returnType.findAnnotation(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        RowIcon rowIconCreateLayeredIcon = IconManager.getInstance().createLayeredIcon(this, PlatformIcons.METHOD_ICON, ElementPresentationUtil.getFlags(this, false));
        if (BitUtil.isSet(i, 1)) {
            VisibilityIcons.setVisibilityIcon(4, rowIconCreateLayeredIcon);
        }
        return rowIconCreateLayeredIcon;
    }

    public LightRecordMethod obtainBranchCopy(ModelBranch modelBranch) {
        if (modelBranch == null) {
            $$$reportNull$$$0(10);
        }
        LightRecordMethod lightRecordMethod = (LightRecordMethod) ((PsiClass) modelBranch.obtainPsiCopy(this.myContainingClass)).findMethodBySignature(this, false);
        if (lightRecordMethod == null) {
            $$$reportNull$$$0(11);
        }
        return lightRecordMethod;
    }

    @Override // org.jetbrains.kotlin.com.intellij.model.BranchableSyntheticPsiElement
    public ModelBranch getModelBranch() {
        return ModelBranch.getPsiBranch(this.myRecordComponent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasTargetApplicableForMethod(PsiAnnotation psiAnnotation) {
        return AnnotationTargetUtil.findAnnotationTarget(psiAnnotation, PsiAnnotation.TargetType.TYPE_USE, PsiAnnotation.TargetType.METHOD) != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        return this.myRecordComponent.setName(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return this.myMethod.copy();
    }
}
