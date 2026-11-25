package org.jetbrains.kotlin.asJava.classes;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: KtDescriptorBasedFakeLightClass.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u0001H\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\u0001H\u0016J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0016R\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtDescriptorBasedFakeLightClass;", "Lorg/jetbrains/kotlin/asJava/classes/KtFakeLightClass;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "_containingClass", "get_containingClass", "()Lorg/jetbrains/kotlin/asJava/classes/KtFakeLightClass;", "_containingClass$delegate", "Lkotlin/Lazy;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "getContainingClass", "isInheritor", "", "baseClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "checkDeep", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDescriptorBasedFakeLightClass extends KtFakeLightClass {

    /* JADX INFO: renamed from: _containingClass$delegate, reason: from kotlin metadata */
    private final Lazy _containingClass;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDescriptorBasedFakeLightClass(final KtClassOrObject kotlinOrigin) {
        super(kotlinOrigin);
        Intrinsics.checkNotNullParameter(kotlinOrigin, "kotlinOrigin");
        this._containingClass = LazyKt.lazy(new Function0<KtDescriptorBasedFakeLightClass>() { // from class: org.jetbrains.kotlin.asJava.classes.KtDescriptorBasedFakeLightClass$_containingClass$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtDescriptorBasedFakeLightClass invoke() {
                KtClassOrObject containingClassOrObject = KtPsiUtilKt.getContainingClassOrObject(kotlinOrigin);
                if (containingClassOrObject != null) {
                    return new KtDescriptorBasedFakeLightClass(containingClassOrObject);
                }
                return null;
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtFakeLightClass copy() {
        return new KtDescriptorBasedFakeLightClass(getKotlinOrigin());
    }

    private final KtFakeLightClass get_containingClass() {
        return (KtFakeLightClass) this._containingClass.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public KtFakeLightClass mo6092getContainingClass() {
        return get_containingClass();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.asJava.classes.KtFakeLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass baseClass, boolean checkDeep) {
        KtClassOrObject ktClassOrObject;
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        if (getManager().areElementsEquivalent(baseClass, this)) {
            return false;
        }
        LightClassInheritanceHelper.Companion companion = LightClassInheritanceHelper.INSTANCE;
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        int i = KtDescriptorBasedFakeLightClass$isInheritor$$inlined$ifSure$1$wm$ImpreciseResolveResult$WhenMappings.$EnumSwitchMapping$0[companion.getService(project).isInheritor(this, baseClass, checkDeep).ordinal()];
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return false;
        }
        if (i == 3) {
            KtLightClass ktLightClass = baseClass instanceof KtLightClass ? (KtLightClass) baseClass : null;
            if (ktLightClass == null || (ktClassOrObject = (KtClassOrObject) ktLightClass.getKotlinOrigin()) == null) {
                return false;
            }
            LightClassGenerationSupport.Companion companion2 = LightClassGenerationSupport.INSTANCE;
            Project project2 = getProject();
            Intrinsics.checkNotNullExpressionValue(project2, "getProject(...)");
            LightClassGenerationSupport companion3 = companion2.getInstance(project2);
            DeclarationDescriptor declarationDescriptorResolveToDescriptor = companion3.resolveToDescriptor(ktClassOrObject);
            ClassDescriptor classDescriptor = declarationDescriptorResolveToDescriptor instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptorResolveToDescriptor : null;
            if (classDescriptor == null) {
                return false;
            }
            DeclarationDescriptor declarationDescriptorResolveToDescriptor2 = companion3.resolveToDescriptor(getKotlinOrigin());
            ClassDescriptor classDescriptor2 = declarationDescriptorResolveToDescriptor2 instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptorResolveToDescriptor2 : null;
            if (classDescriptor2 == null) {
                return false;
            }
            String strAsString = DescriptorUtils.getFqName(classDescriptor2).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            String strAsString2 = DescriptorUtils.getFqName(classDescriptor).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            if (Intrinsics.areEqual(strAsString, strAsString2)) {
                return false;
            }
            if (checkDeep) {
                return DescriptorUtils.isSubclass(classDescriptor2, classDescriptor);
            }
            return DescriptorUtils.isDirectSubclass(classDescriptor2, classDescriptor);
        }
        throw new NoWhenBranchMatchedException();
    }
}
