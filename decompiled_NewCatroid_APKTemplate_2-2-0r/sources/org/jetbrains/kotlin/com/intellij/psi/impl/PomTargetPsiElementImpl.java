package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.huawei.hms.ml.language.common.utils.Constant;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.ide.IconProvider;
import org.jetbrains.kotlin.com.intellij.ide.TypePresentationService;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.pom.PomIconProvider;
import org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget;
import org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget;
import org.jetbrains.kotlin.com.intellij.pom.PomTarget;
import org.jetbrains.kotlin.com.intellij.pom.PomTargetPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTarget;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PomTargetPsiElementImpl extends RenameableFakePsiElement implements PomTargetPsiElement {
    private final Project myProject;
    private final PomTarget myTarget;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 6:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
                i2 = 2;
                break;
            case 6:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "project";
                break;
            case 2:
            default:
                objArr[0] = "target";
                break;
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PomTargetPsiElementImpl";
                break;
            case 6:
                objArr[0] = "name";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "getTarget";
                break;
            case 4:
            case 5:
                objArr[1] = "getNavigationElement";
                break;
            case 6:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PomTargetPsiElementImpl";
                break;
            case 7:
            case 8:
                objArr[1] = "getLanguage";
                break;
            case 9:
                objArr[1] = "getProject";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
                break;
            case 6:
                objArr[2] = "setName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
                throw new IllegalStateException(str2);
            case 6:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.RenameableFakePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PomTargetPsiElementImpl(PsiTarget psiTarget) {
        this(psiTarget.getNavigationElement().getProject(), psiTarget);
        if (psiTarget == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PomTargetPsiElementImpl(Project project, PomTarget pomTarget) {
        super(null);
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        if (pomTarget == null) {
            $$$reportNull$$$0(2);
        }
        this.myProject = project;
        this.myTarget = pomTarget;
    }

    public PomTarget getTarget() {
        PomTarget pomTarget = this.myTarget;
        if (pomTarget == null) {
            $$$reportNull$$$0(3);
        }
        return pomTarget;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.RenameableFakePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        PomTarget pomTarget = this.myTarget;
        if (pomTarget instanceof PomNamedTarget) {
            return ((PomNamedTarget) pomTarget).getName();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        PomTarget pomTarget = this.myTarget;
        if (pomTarget instanceof PomRenameableTarget) {
            return ((PomRenameableTarget) pomTarget).isWritable();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.meta.PsiPresentableMetaData
    public String getTypeName() {
        throw new UnsupportedOperationException("Method getTypeName is not yet implemented for " + this.myTarget.getClass().getName() + "; see PomDescriptionProvider");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PomTarget pomTarget = this.myTarget;
        if (pomTarget instanceof PsiTarget) {
            PsiElement navigationElement = ((PsiTarget) pomTarget).getNavigationElement();
            if (navigationElement == null) {
                $$$reportNull$$$0(4);
            }
            return navigationElement;
        }
        PsiElement navigationElement2 = super.getNavigationElement();
        if (navigationElement2 == null) {
            $$$reportNull$$$0(5);
        }
        return navigationElement2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.meta.PsiPresentableMetaData
    public Icon getIcon() {
        Icon icon;
        for (IconProvider iconProvider : IconProvider.EXTENSION_POINT_NAME.getExtensionList()) {
            if ((iconProvider instanceof PomIconProvider) && (icon = ((PomIconProvider) iconProvider).getIcon(this.myTarget, 0)) != null) {
                return icon;
            }
        }
        Icon icon2 = TypePresentationService.getService().getIcon(this.myTarget);
        if (icon2 != null) {
            return icon2;
        }
        PomTarget pomTarget = this.myTarget;
        if (pomTarget instanceof PsiTarget) {
            return ((PsiTarget) pomTarget).getNavigationElement().getIcon(0);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        PomTarget pomTarget = this.myTarget;
        if (pomTarget instanceof PsiTarget) {
            return ((PsiTarget) pomTarget).getNavigationElement().isValid();
        }
        return pomTarget.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.FakePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        PomTarget pomTarget = this.myTarget;
        if (pomTarget instanceof PomRenameableTarget) {
            ((PomRenameableTarget) pomTarget).setName(str);
            return this;
        }
        throw new UnsupportedOperationException("Cannot rename " + this.myTarget);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.myTarget.equals(((PomTargetPsiElementImpl) obj).myTarget);
    }

    public int hashCode() {
        return this.myTarget.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        if (!equals(psiElement)) {
            if (psiElement != null) {
                PomTarget pomTarget = this.myTarget;
                if (!(pomTarget instanceof PsiTarget) || !psiElement.isEquivalentTo(((PsiTarget) pomTarget).getNavigationElement())) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        PomTarget pomTarget = this.myTarget;
        if (pomTarget instanceof PsiTarget) {
            return ((PsiTarget) pomTarget).getNavigationElement();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public void navigate(boolean z) {
        this.myTarget.navigate(z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigate() {
        return this.myTarget.canNavigate();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigateToSource() {
        return this.myTarget.canNavigateToSource();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.RenameableFakePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PomTarget pomTarget = this.myTarget;
        if (pomTarget instanceof PsiTarget) {
            return ((PsiTarget) pomTarget).getNavigationElement().getContainingFile();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.RenameableFakePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.FakePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        PomTarget pomTarget = this.myTarget;
        if (pomTarget instanceof PsiTarget) {
            Language language = ((PsiTarget) pomTarget).getNavigationElement().getLanguage();
            if (language == null) {
                $$$reportNull$$$0(7);
            }
            return language;
        }
        Language language2 = Language.ANY;
        if (language2 == null) {
            $$$reportNull$$$0(8);
        }
        return language2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.RenameableFakePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Project getProject() {
        Project project = this.myProject;
        if (project == null) {
            $$$reportNull$$$0(9);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
    public String getLocationString() {
        PsiFile containingFile;
        PomTarget pomTarget = this.myTarget;
        if ((pomTarget instanceof PsiTarget) && (containingFile = ((PsiTarget) pomTarget).getNavigationElement().getContainingFile()) != null) {
            return "(" + containingFile.getName() + Constant.AFTER_QUTO;
        }
        return super.getLocationString();
    }
}
