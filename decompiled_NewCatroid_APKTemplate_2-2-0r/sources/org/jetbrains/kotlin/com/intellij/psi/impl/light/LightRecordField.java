package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationTargetUtil;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;
import org.jetbrains.kotlin.com.intellij.util.VisibilityIcons;

/* JADX INFO: loaded from: classes3.dex */
public class LightRecordField extends LightField implements LightRecordMember {
    private final PsiRecordComponent myRecordComponent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "field";
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
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightRecordField";
                break;
            case 9:
            case 10:
                objArr[0] = "fqn";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getRecordComponent";
                break;
            case 5:
                objArr[1] = "getNavigationElement";
                break;
            case 6:
            case 7:
                objArr[1] = "getType";
                break;
            case 8:
                objArr[1] = "getAnnotations";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightRecordField";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                break;
            case 9:
                objArr[2] = "hasAnnotation";
                break;
            case 10:
                objArr[2] = "getAnnotation";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightField, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightRecordField(PsiManager psiManager, PsiField psiField, PsiClass psiClass, PsiRecordComponent psiRecordComponent) {
        super(psiManager, psiField, psiClass);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (psiField == null) {
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiClass containingClass = mo6092getContainingClass();
        if (containingClass == null) {
            return null;
        }
        return containingClass.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightField, org.jetbrains.kotlin.com.intellij.psi.PsiField
    public PsiType getType() {
        if (DumbService.isDumb(this.myRecordComponent.getProject())) {
            PsiType type = this.myRecordComponent.getType();
            if (type == null) {
                $$$reportNull$$$0(6);
            }
            return type;
        }
        PsiType psiType = (PsiType) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordField$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6626x264fc68f();
            }
        });
        if (psiType == null) {
            $$$reportNull$$$0(7);
        }
        return psiType;
    }

    /* JADX INFO: renamed from: lambda$getType$2$org-jetbrains-kotlin-com-intellij-psi-impl-light-LightRecordField, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6626x264fc68f() {
        return CachedValueProvider.Result.create(this.myRecordComponent.getType().annotate(new TypeAnnotationProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordField$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
            public final PsiAnnotation[] getAnnotations() {
                return this.f$0.m6625x4a8e4ace();
            }
        }), this);
    }

    static /* synthetic */ PsiAnnotation[] lambda$getType$0(int i) {
        return new PsiAnnotation[i];
    }

    /* JADX INFO: renamed from: lambda$getType$1$org-jetbrains-kotlin-com-intellij-psi-impl-light-LightRecordField, reason: not valid java name */
    /* synthetic */ PsiAnnotation[] m6625x4a8e4ace() {
        return (PsiAnnotation[]) Arrays.stream(this.myRecordComponent.getAnnotations()).filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordField$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return LightRecordField.hasApplicableAnnotationTarget((PsiAnnotation) obj);
            }
        }).toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordField$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return LightRecordField.lambda$getType$0(i);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] annotations = getType().getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(8);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        return getType().hasAnnotation(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
    public PsiAnnotation getAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        return getType().findAnnotation(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        RowIcon rowIconCreateLayeredIcon = IconManager.getInstance().createLayeredIcon(this, PlatformIcons.FIELD_ICON, ElementPresentationUtil.getFlags(this, false));
        if (BitUtil.isSet(i, 1)) {
            VisibilityIcons.setVisibilityIcon(1, rowIconCreateLayeredIcon);
        }
        return rowIconCreateLayeredIcon;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return mo6092getContainingClass();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightField, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        PsiClass psiClass = (PsiClass) Objects.requireNonNull(mo6092getContainingClass());
        PsiClass psiClassMo6092getContainingClass = psiClass.mo6092getContainingClass();
        while (true) {
            PsiClass psiClass2 = psiClassMo6092getContainingClass;
            PsiClass psiClass3 = psiClass;
            psiClass = psiClass2;
            if (psiClass != null) {
                psiClassMo6092getContainingClass = psiClass.mo6092getContainingClass();
            } else {
                return new LocalSearchScope(psiClass3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasApplicableAnnotationTarget(PsiAnnotation psiAnnotation) {
        return AnnotationTargetUtil.findAnnotationTarget(psiAnnotation, PsiAnnotation.TargetType.TYPE_USE, PsiAnnotation.TargetType.FIELD) != null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LightRecordField) && this.myRecordComponent.equals(((LightRecordField) obj).myRecordComponent);
    }

    public int hashCode() {
        return Objects.hash(this.myRecordComponent);
    }
}
