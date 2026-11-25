package org.jetbrains.kotlin.asJava.builder;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;

/* JADX INFO: compiled from: LightElementOrigin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\u0013\u0010\u0014\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\b\u0010\u0016\u001a\u00020\u0001H\u0016J=\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\u0012\u0010\u001d\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001eH\u0016J\u0012\u0010\u001d\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u0010\u001f\u001a\u00020\u0018H\u0016J\t\u0010 \u001a\u00020!HÖ\u0001R\u0016\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u001e\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOrigin;", "originalElement", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "originKind", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "parametersForJvmOverloads", "", "Lorg/jetbrains/kotlin/psi/KtParameter;", "auxiliaryOriginalElement", "(Lorg/jetbrains/kotlin/psi/KtDeclaration;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;Ljava/util/List;Lorg/jetbrains/kotlin/psi/KtDeclaration;)V", "getAuxiliaryOriginalElement", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getOriginKind", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "getOriginalElement", "getParametersForJvmOverloads", "()Ljava/util/List;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "isEquivalentTo", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isValid", "toString", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LightMemberOriginForDeclaration implements LightMemberOrigin {
    private final KtDeclaration auxiliaryOriginalElement;
    private final JvmDeclarationOriginKind originKind;
    private final KtDeclaration originalElement;
    private final List<KtParameter> parametersForJvmOverloads;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ LightMemberOriginForDeclaration copy$default(LightMemberOriginForDeclaration lightMemberOriginForDeclaration, KtDeclaration ktDeclaration, JvmDeclarationOriginKind jvmDeclarationOriginKind, List list, KtDeclaration ktDeclaration2, int i, Object obj) {
        if ((i & 1) != 0) {
            ktDeclaration = lightMemberOriginForDeclaration.originalElement;
        }
        if ((i & 2) != 0) {
            jvmDeclarationOriginKind = lightMemberOriginForDeclaration.originKind;
        }
        if ((i & 4) != 0) {
            list = lightMemberOriginForDeclaration.parametersForJvmOverloads;
        }
        if ((i & 8) != 0) {
            ktDeclaration2 = lightMemberOriginForDeclaration.auxiliaryOriginalElement;
        }
        return lightMemberOriginForDeclaration.copy(ktDeclaration, jvmDeclarationOriginKind, list, ktDeclaration2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KtDeclaration getOriginalElement() {
        return this.originalElement;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final JvmDeclarationOriginKind getOriginKind() {
        return this.originKind;
    }

    public final List<KtParameter> component3() {
        return this.parametersForJvmOverloads;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final KtDeclaration getAuxiliaryOriginalElement() {
        return this.auxiliaryOriginalElement;
    }

    public final LightMemberOriginForDeclaration copy(KtDeclaration originalElement, JvmDeclarationOriginKind originKind, List<? extends KtParameter> parametersForJvmOverloads, KtDeclaration auxiliaryOriginalElement) {
        Intrinsics.checkNotNullParameter(originalElement, "originalElement");
        Intrinsics.checkNotNullParameter(originKind, "originKind");
        return new LightMemberOriginForDeclaration(originalElement, originKind, parametersForJvmOverloads, auxiliaryOriginalElement);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LightMemberOriginForDeclaration)) {
            return false;
        }
        LightMemberOriginForDeclaration lightMemberOriginForDeclaration = (LightMemberOriginForDeclaration) other;
        return Intrinsics.areEqual(this.originalElement, lightMemberOriginForDeclaration.originalElement) && this.originKind == lightMemberOriginForDeclaration.originKind && Intrinsics.areEqual(this.parametersForJvmOverloads, lightMemberOriginForDeclaration.parametersForJvmOverloads) && Intrinsics.areEqual(this.auxiliaryOriginalElement, lightMemberOriginForDeclaration.auxiliaryOriginalElement);
    }

    public int hashCode() {
        int iHashCode = ((this.originalElement.hashCode() * 31) + this.originKind.hashCode()) * 31;
        List<KtParameter> list = this.parametersForJvmOverloads;
        int iHashCode2 = (iHashCode + (list == null ? 0 : list.hashCode())) * 31;
        KtDeclaration ktDeclaration = this.auxiliaryOriginalElement;
        return iHashCode2 + (ktDeclaration != null ? ktDeclaration.hashCode() : 0);
    }

    public String toString() {
        return "LightMemberOriginForDeclaration(originalElement=" + this.originalElement + ", originKind=" + this.originKind + ", parametersForJvmOverloads=" + this.parametersForJvmOverloads + ", auxiliaryOriginalElement=" + this.auxiliaryOriginalElement + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LightMemberOriginForDeclaration(KtDeclaration originalElement, JvmDeclarationOriginKind originKind, List<? extends KtParameter> list, KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(originalElement, "originalElement");
        Intrinsics.checkNotNullParameter(originKind, "originKind");
        this.originalElement = originalElement;
        this.originKind = originKind;
        this.parametersForJvmOverloads = list;
        this.auxiliaryOriginalElement = ktDeclaration;
    }

    public /* synthetic */ LightMemberOriginForDeclaration(KtDeclaration ktDeclaration, JvmDeclarationOriginKind jvmDeclarationOriginKind, List list, KtDeclaration ktDeclaration2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktDeclaration, jvmDeclarationOriginKind, (i & 4) != 0 ? null : list, (i & 8) != 0 ? null : ktDeclaration2);
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightElementOrigin
    public KtDeclaration getOriginalElement() {
        return this.originalElement;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin, org.jetbrains.kotlin.asJava.builder.LightElementOrigin
    public JvmDeclarationOriginKind getOriginKind() {
        return this.originKind;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
    public List<KtParameter> getParametersForJvmOverloads() {
        return this.parametersForJvmOverloads;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
    public KtDeclaration getAuxiliaryOriginalElement() {
        return this.auxiliaryOriginalElement;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
    public boolean isValid() {
        return getOriginalElement().isValid();
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
    public boolean isEquivalentTo(LightMemberOrigin other) {
        if (other instanceof LightMemberOriginForDeclaration) {
            return isEquivalentTo(((LightMemberOriginForDeclaration) other).getOriginalElement());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
    public boolean isEquivalentTo(PsiElement other) {
        return getOriginalElement().isEquivalentTo(other);
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
    public LightMemberOrigin copy() {
        PsiElement psiElementCopy = getOriginalElement().copy();
        Intrinsics.checkNotNull(psiElementCopy, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtDeclaration");
        return new LightMemberOriginForDeclaration((KtDeclaration) psiElementCopy, getOriginKind(), getParametersForJvmOverloads(), null, 8, null);
    }
}
