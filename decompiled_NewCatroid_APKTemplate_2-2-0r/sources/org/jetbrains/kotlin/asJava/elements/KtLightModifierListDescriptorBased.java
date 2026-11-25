package org.jetbrains.kotlin.asJava.elements;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightNullabilityAnnotation;
import org.jetbrains.kotlin.asJava.elements.KtLightElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.psi.KtModifierListOwner;

/* JADX INFO: compiled from: KtLightModifierListDescriptorBased.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\u0016\b\u0000\u0010\u0001 \u0001*\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightModifierListDescriptorBased;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightModifierList;", "owner", "(Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;)V", "computeAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightModifierListDescriptorBased<T extends KtLightElement<? extends KtModifierListOwner, ? extends PsiModifierListOwner>> extends KtLightModifierList<T> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightModifierListDescriptorBased(T owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightModifierList
    public List<KtLightAbstractAnnotation> computeAnnotations() {
        KtUltraLightNullabilityAnnotation ktLightNullabilityAnnotation;
        List<KtLightAbstractAnnotation> givenAnnotations = getOwner().getGivenAnnotations();
        if (givenAnnotations == null) {
            givenAnnotations = KtLightModifierListDescriptorBasedKt.lightAnnotationsForEntries(this);
        }
        List<KtLightAbstractAnnotation> list = givenAnnotations;
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            ((KtLightAbstractAnnotation) it.next()).setParent(this);
        }
        KtLightElement parent = getLightParent();
        if ((parent instanceof KtLightClassForSourceDeclaration) && ((KtLightClassForSourceDeclaration) parent).isAnnotationType()) {
            List<KtLightAbstractAnnotation> listNonSourceAnnotationsForAnnotationType = nonSourceAnnotationsForAnnotationType(givenAnnotations);
            ArrayList arrayList = new ArrayList();
            for (Object obj : listNonSourceAnnotationsForAnnotationType) {
                KtLightAbstractAnnotation ktLightAbstractAnnotation = (KtLightAbstractAnnotation) obj;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it2 = list.iterator();
                    do {
                        if (it2.hasNext()) {
                        }
                    } while (!Intrinsics.areEqual(ktLightAbstractAnnotation.getQualifiedName(), ((KtLightAbstractAnnotation) it2.next()).getQualifiedName()));
                }
                arrayList.add(obj);
            }
            return CollectionsKt.plus((Collection) givenAnnotations, (Iterable) arrayList);
        }
        if (!LightClassUtilsKt.fastCheckIsNullabilityApplied(parent)) {
            return givenAnnotations;
        }
        if (parent instanceof KtUltraLightElementWithNullabilityAnnotation) {
            ktLightNullabilityAnnotation = new KtUltraLightNullabilityAnnotation((KtUltraLightElementWithNullabilityAnnotation) parent, this);
        } else {
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.asJava.elements.KtLightElement<*, com.intellij.psi.PsiModifierListOwner>");
            ktLightNullabilityAnnotation = new KtLightNullabilityAnnotation(parent, this);
        }
        return CollectionsKt.plus((Collection) givenAnnotations, (Iterable) (ktLightNullabilityAnnotation.getQualifiedName() != null ? CollectionsKt.listOf(ktLightNullabilityAnnotation) : CollectionsKt.emptyList()));
    }
}
