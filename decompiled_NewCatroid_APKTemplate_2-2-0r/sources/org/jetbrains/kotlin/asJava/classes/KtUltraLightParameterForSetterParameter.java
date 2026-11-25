package org.jetbrains.kotlin.asJava.classes;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ultraLightParameters.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0013H\u0016J\n\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014R\u001c\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightParameterForSetterParameter;", "Lorg/jetbrains/kotlin/asJava/classes/KtAbstractUltraLightParameterForDeclaration;", "name", "", "property", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "method", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "containingDeclaration", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/psi/KtDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;)V", "givenAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "getGivenAnnotations", "()Ljava/util/List;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "isVarArgs", "tryGetKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightParameterForSetterParameter extends KtAbstractUltraLightParameterForDeclaration {
    private final KtDeclaration property;

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter, org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: isVarArgs */
    public boolean getMyVarArgs() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightParameterForSetterParameter(String name2, KtDeclaration property, KtUltraLightSupport support, KtUltraLightMethod method, KtCallableDeclaration containingDeclaration) {
        super(name2, null, support, method, containingDeclaration);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        this.property = property;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtAbstractUltraLightParameterForDeclaration
    protected KotlinType tryGetKotlinType() {
        return UltraLightUtilsKt.getKotlinType(this.property);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public List<KtLightAbstractAnnotation> getGivenAnnotations() {
        List<KtAnnotationEntry> annotationEntries = this.property.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        return UltraLightUtilsKt.toLightAnnotations(annotationEntries, this, AnnotationUseSiteTarget.SETTER_PARAMETER);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter
    public boolean equals(Object other) {
        if (other != this) {
            if (other instanceof KtUltraLightParameterForSetterParameter) {
                KtUltraLightParameterForSetterParameter ktUltraLightParameterForSetterParameter = (KtUltraLightParameterForSetterParameter) other;
                if (!Intrinsics.areEqual(ktUltraLightParameterForSetterParameter.getName(), getName()) || !Intrinsics.areEqual(ktUltraLightParameterForSetterParameter.property, this.property)) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter
    public int hashCode() {
        return getName().hashCode();
    }
}
