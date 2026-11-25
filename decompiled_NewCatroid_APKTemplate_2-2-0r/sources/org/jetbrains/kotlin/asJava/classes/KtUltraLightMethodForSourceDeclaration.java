package org.jetbrains.kotlin.asJava.classes;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.asJava.builder.LightMemberOriginForDeclaration;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeParameterListBuilder;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ultraLightMethods.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B/\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fB9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\u0011J\b\u0010$\u001a\u00020%H\u0014J\u0013\u0010&\u001a\u00020\u00102\b\u0010'\u001a\u0004\u0018\u00010(H\u0096\u0002J\b\u0010)\u001a\u00020\u0013H\u0016J\b\u0010*\u001a\u00020\u000bH\u0016R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0018\u001a\u00020\u00108VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0017\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u001d8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0016\u0010 \u001a\u0004\u0018\u00010!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethodForSourceDeclaration;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "containingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "methodIndex", "", "(Lcom/intellij/psi/PsiMethod;Lorg/jetbrains/kotlin/psi/KtDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;I)V", "lightMemberOrigin", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;", "forceToSkipNullabilityAnnotation", "", "(Lcom/intellij/psi/PsiMethod;Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;ZI)V", "_throwsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "get_throwsList", "()Lcom/intellij/psi/PsiReferenceList;", "_throwsList$delegate", "Lkotlin/Lazy;", "checkNeedToErasureParametersTypes", "getCheckNeedToErasureParametersTypes", "()Z", "checkNeedToErasureParametersTypes$delegate", "methodDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getMethodDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "qualifiedNameForNullabilityAnnotation", "", "getQualifiedNameForNullabilityAnnotation", "()Ljava/lang/String;", "buildTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", Namer.EQUALS_METHOD_NAME, "other", "", "getThrowsList", "hashCode", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightMethodForSourceDeclaration extends KtUltraLightMethod {

    /* JADX INFO: renamed from: _throwsList$delegate, reason: from kotlin metadata */
    private final Lazy _throwsList;

    /* JADX INFO: renamed from: checkNeedToErasureParametersTypes$delegate, reason: from kotlin metadata */
    private final Lazy checkNeedToErasureParametersTypes;
    private final boolean forceToSkipNullabilityAnnotation;

    public /* synthetic */ KtUltraLightMethodForSourceDeclaration(PsiMethod psiMethod, LightMemberOriginForDeclaration lightMemberOriginForDeclaration, KtUltraLightSupport ktUltraLightSupport, KtLightClass ktLightClass, boolean z, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(psiMethod, lightMemberOriginForDeclaration, ktUltraLightSupport, ktLightClass, (i2 & 16) != 0 ? false : z, i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightMethodForSourceDeclaration(PsiMethod delegate, LightMemberOriginForDeclaration lightMemberOriginForDeclaration, KtUltraLightSupport support, KtLightClass containingClass, boolean z, int i) {
        super(delegate, lightMemberOriginForDeclaration, support, containingClass, i);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        this.forceToSkipNullabilityAnnotation = z;
        this._throwsList = ImplUtilsKt.lazyPub(new Function0<PsiReferenceList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightMethodForSourceDeclaration$_throwsList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiReferenceList invoke() {
                KtUltraLightMethodForSourceDeclaration ktUltraLightMethodForSourceDeclaration = this.this$0;
                return ktUltraLightMethodForSourceDeclaration.computeThrowsList(ktUltraLightMethodForSourceDeclaration.getMethodDescriptor());
            }
        });
        this.checkNeedToErasureParametersTypes = ImplUtilsKt.lazyPub(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightMethodForSourceDeclaration$checkNeedToErasureParametersTypes$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                KtUltraLightMethodForSourceDeclaration ktUltraLightMethodForSourceDeclaration = this.this$0;
                return Boolean.valueOf(ktUltraLightMethodForSourceDeclaration.computeCheckNeedToErasureParametersTypes(ktUltraLightMethodForSourceDeclaration.getMethodDescriptor()));
            }
        });
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KtUltraLightMethodForSourceDeclaration(PsiMethod delegate, KtDeclaration declaration, KtUltraLightSupport support, KtLightClass containingClass, int i) {
        this(delegate, new LightMemberOriginForDeclaration(declaration, JvmDeclarationOriginKind.OTHER, null, null, 12, null), support, containingClass, false, i);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public String getQualifiedNameForNullabilityAnnotation() {
        KtDeclaration kotlinOrigin;
        KotlinType kotlinType = null;
        if (!this.forceToSkipNullabilityAnnotation && (kotlinOrigin = getKotlinOrigin()) != null) {
            kotlinType = UltraLightUtilsKt.getKotlinType(kotlinOrigin);
        }
        return computeQualifiedNameForNullabilityAnnotation(kotlinType);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightMethod, org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl
    protected PsiTypeParameterList buildTypeParameterList() {
        KtDeclaration kotlinOrigin = getKotlinOrigin();
        if ((kotlinOrigin instanceof KtFunction) || (kotlinOrigin instanceof KtProperty)) {
            return UltraLightUtilsKt.buildTypeParameterListForSourceDeclaration((KtTypeParameterListOwner) kotlinOrigin, this, getSupport());
        }
        return new LightTypeParameterListBuilder(getManager(), getLanguage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FunctionDescriptor getMethodDescriptor() {
        KtDeclaration kotlinOrigin = getKotlinOrigin();
        PropertyGetterDescriptor getter = null;
        DeclarationDescriptor declarationDescriptorResolve = kotlinOrigin != null ? UltraLightUtilsKt.resolve(kotlinOrigin) : null;
        if (declarationDescriptorResolve instanceof FunctionDescriptor) {
            return (FunctionDescriptor) declarationDescriptorResolve;
        }
        if (!(declarationDescriptorResolve instanceof PropertyDescriptor)) {
            return null;
        }
        int methodIndex = getMethodIndex();
        if (methodIndex == 1) {
            getter = ((PropertyDescriptor) declarationDescriptorResolve).getGetter();
        } else if (methodIndex == 2) {
            getter = ((PropertyDescriptor) declarationDescriptorResolve).getSetter();
        }
        return getter;
    }

    private final PsiReferenceList get_throwsList() {
        return (PsiReferenceList) this._throwsList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiReferenceList getThrowsList() {
        return get_throwsList();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightMethod
    public boolean getCheckNeedToErasureParametersTypes() {
        return ((Boolean) this.checkNeedToErasureParametersTypes.getValue()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightMethod, org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl
    public boolean equals(Object other) {
        return other == this || ((other instanceof KtUltraLightMethodForSourceDeclaration) && ((KtUltraLightMethodForSourceDeclaration) other).forceToSkipNullabilityAnnotation == this.forceToSkipNullabilityAnnotation && super.equals(other));
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightMethod, org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl
    public int hashCode() {
        return (super.hashCode() * 31) + Boolean.hashCode(this.forceToSkipNullabilityAnnotation);
    }
}
