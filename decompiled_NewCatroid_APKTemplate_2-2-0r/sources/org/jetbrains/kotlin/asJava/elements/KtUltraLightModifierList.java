package org.jetbrains.kotlin.asJava.elements;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightSupport;
import org.jetbrains.kotlin.asJava.elements.KtLightElement;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.psi.KtModifierListOwner;

/* JADX INFO: compiled from: KtLightModifierListDescriptorBased.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0016\b\u0000\u0010\u0001 \u0001*\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0005B\u0015\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\rH\u0016J\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u0010H\u0014R\u0014\u0010\u0007\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtUltraLightModifierList;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightModifierListDescriptorBased;", "owner", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)V", "getSupport", "()Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "nonSourceAnnotationsForAnnotationType", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "sourceAnnotations", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "additionalConverter", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtUltraLightModifierList<T extends KtLightElement<? extends KtModifierListOwner, ? extends PsiModifierListOwner>> extends KtLightModifierListDescriptorBased<T> {
    private final KtUltraLightSupport support;

    protected KtLightAbstractAnnotation additionalConverter(PsiAnnotation psiAnnotation) {
        Intrinsics.checkNotNullParameter(psiAnnotation, "<this>");
        return null;
    }

    protected final KtUltraLightSupport getSupport() {
        return this.support;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightModifierList(T owner, KtUltraLightSupport support) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(support, "support");
        this.support = support;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightModifierList
    public List<KtLightAbstractAnnotation> nonSourceAnnotationsForAnnotationType(List<? extends PsiAnnotation> sourceAnnotations) {
        Intrinsics.checkNotNullParameter(sourceAnnotations, "sourceAnnotations");
        if (sourceAnnotations.isEmpty()) {
            return CollectionsKt.listOf(KtToJvmAnnotationsConverterKt.createRetentionRuntimeAnnotation(this));
        }
        ArrayList arrayList = new ArrayList();
        for (PsiAnnotation psiAnnotation : sourceAnnotations) {
            KtLightAbstractAnnotation ktLightAbstractAnnotationAdditionalConverter = additionalConverter(psiAnnotation);
            if (ktLightAbstractAnnotationAdditionalConverter == null && (ktLightAbstractAnnotationAdditionalConverter = KtToJvmAnnotationsConverterKt.tryConvertAsTarget(psiAnnotation)) == null && (ktLightAbstractAnnotationAdditionalConverter = KtToJvmAnnotationsConverterKt.tryConvertAsRetention(psiAnnotation)) == null && (ktLightAbstractAnnotationAdditionalConverter = KtToJvmAnnotationsConverterKt.tryConvertAsRepeatable(psiAnnotation, getOwner())) == null) {
                ktLightAbstractAnnotationAdditionalConverter = KtToJvmAnnotationsConverterKt.tryConvertAsMustBeDocumented(psiAnnotation);
            }
            if (ktLightAbstractAnnotationAdditionalConverter != null) {
                arrayList.add(ktLightAbstractAnnotationAdditionalConverter);
            }
        }
        ArrayList arrayList2 = arrayList;
        if ((arrayList2 instanceof Collection) && arrayList2.isEmpty()) {
            arrayList.add(KtToJvmAnnotationsConverterKt.createRetentionRuntimeAnnotation(this));
        } else {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((KtLightAbstractAnnotation) it.next()).getAnnotationFqName(), CommonClassNames.JAVA_LANG_ANNOTATION_RETENTION)) {
                }
            }
            arrayList.add(KtToJvmAnnotationsConverterKt.createRetentionRuntimeAnnotation(this));
        }
        return arrayList;
    }
}
