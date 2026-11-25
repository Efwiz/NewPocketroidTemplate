package org.jetbrains.kotlin.asJava.classes;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ultraLightParameters.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b \u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010\u0019\u001a\u00020\u000eH\u0016J\n\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0004J\n\u0010\u001c\u001a\u0004\u0018\u00010\u001dH$R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\n\u001a\u00020\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0015\u001a\u0004\u0018\u00010\u00038VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0012\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtAbstractUltraLightParameterForDeclaration;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightParameter;", "name", "", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtParameter;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "method", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "containingDeclaration", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/psi/KtParameter;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;)V", "_parameterType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "get_parameterType", "()Lcom/intellij/psi/PsiType;", "_parameterType$delegate", "Lkotlin/Lazy;", "getContainingDeclaration", "()Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "qualifiedNameForNullabilityAnnotation", "getQualifiedNameForNullabilityAnnotation", "()Ljava/lang/String;", "qualifiedNameForNullabilityAnnotation$delegate", "getType", "tryGetContainingDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "tryGetKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtAbstractUltraLightParameterForDeclaration extends KtUltraLightParameter {

    /* JADX INFO: renamed from: _parameterType$delegate, reason: from kotlin metadata */
    private final Lazy _parameterType;
    private final KtCallableDeclaration containingDeclaration;

    /* JADX INFO: renamed from: qualifiedNameForNullabilityAnnotation$delegate, reason: from kotlin metadata */
    private final Lazy qualifiedNameForNullabilityAnnotation;

    protected abstract KotlinType tryGetKotlinType();

    protected final KtCallableDeclaration getContainingDeclaration() {
        return this.containingDeclaration;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtAbstractUltraLightParameterForDeclaration(String name2, KtParameter ktParameter, KtUltraLightSupport support, KtUltraLightMethod method, KtCallableDeclaration containingDeclaration) {
        super(name2, ktParameter, support, method);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        this.containingDeclaration = containingDeclaration;
        this._parameterType = ImplUtilsKt.lazyPub(new Function0<PsiType>() { // from class: org.jetbrains.kotlin.asJava.classes.KtAbstractUltraLightParameterForDeclaration$_parameterType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiType invoke() {
                KtAbstractUltraLightParameterForDeclaration ktAbstractUltraLightParameterForDeclaration = this.this$0;
                return ktAbstractUltraLightParameterForDeclaration.computeParameterType(ktAbstractUltraLightParameterForDeclaration.tryGetKotlinType(), this.this$0.tryGetContainingDescriptor());
            }
        });
        this.qualifiedNameForNullabilityAnnotation = ImplUtilsKt.lazyPub(new Function0<String>() { // from class: org.jetbrains.kotlin.asJava.classes.KtAbstractUltraLightParameterForDeclaration$qualifiedNameForNullabilityAnnotation$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                KtAbstractUltraLightParameterForDeclaration ktAbstractUltraLightParameterForDeclaration = this.this$0;
                return ktAbstractUltraLightParameterForDeclaration.computeQualifiedNameForNullabilityAnnotation(ktAbstractUltraLightParameterForDeclaration.tryGetKotlinType());
            }
        });
    }

    protected final CallableDescriptor tryGetContainingDescriptor() {
        DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(this.containingDeclaration);
        if (declarationDescriptorResolve instanceof CallableDescriptor) {
            return (CallableDescriptor) declarationDescriptorResolve;
        }
        return null;
    }

    private final PsiType get_parameterType() {
        return (PsiType) this._parameterType.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    public PsiType getType() {
        return get_parameterType();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public String getQualifiedNameForNullabilityAnnotation() {
        return (String) this.qualifiedNameForNullabilityAnnotation.getValue();
    }
}
