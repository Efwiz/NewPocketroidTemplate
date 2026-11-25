package org.jetbrains.kotlin.asJava.classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;

/* JADX INFO: compiled from: ultraLightAnnotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0012\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\f"}, d2 = {"obtainLightAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "toAnnotationMemberValue", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "toLightAnnotation", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSimpleAnnotation;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "light-classes"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UltraLightAnnotationsKt {
    public static final KtUltraLightSimpleAnnotation toLightAnnotation(AnnotationDescriptor annotationDescriptor, PsiElement parent) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(parent, "parent");
        FqName fqName = annotationDescriptor.getFqName();
        String strAsString = fqName != null ? fqName.asString() : null;
        Map<Name, ConstantValue<?>> allValueArguments = annotationDescriptor.getAllValueArguments();
        ArrayList arrayList = new ArrayList(allValueArguments.size());
        for (Map.Entry<Name, ConstantValue<?>> entry : allValueArguments.entrySet()) {
            arrayList.add(TuplesKt.to(entry.getKey().asString(), entry.getValue()));
        }
        return new KtUltraLightSimpleAnnotation(strAsString, arrayList, parent, null, 8, null);
    }

    public static final List<KtLightAbstractAnnotation> obtainLightAnnotations(DeclarationDescriptor declarationDescriptor, PsiElement parent) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Annotations annotations = declarationDescriptor.getAnnotations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<AnnotationDescriptor> it = annotations.iterator();
        while (it.hasNext()) {
            arrayList.add(toLightAnnotation(it.next(), parent));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PsiAnnotationMemberValue toAnnotationMemberValue(final ConstantValue<?> constantValue, PsiElement psiElement) {
        if (constantValue instanceof AnnotationValue) {
            return toLightAnnotation(((AnnotationValue) constantValue).getValue(), psiElement);
        }
        if (constantValue instanceof ArrayValue) {
            return new KtUltraLightPsiArrayInitializerMemberValue(psiElement, new Function1<KtUltraLightPsiArrayInitializerMemberValue, List<? extends PsiAnnotationMemberValue>>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightAnnotationsKt.toAnnotationMemberValue.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final List<PsiAnnotationMemberValue> invoke(KtUltraLightPsiArrayInitializerMemberValue arrayLiteralParent) {
                    Intrinsics.checkNotNullParameter(arrayLiteralParent, "arrayLiteralParent");
                    List<? extends ConstantValue<?>> value = ((ArrayValue) constantValue).getValue();
                    ArrayList arrayList = new ArrayList();
                    Iterator<T> it = value.iterator();
                    while (it.hasNext()) {
                        PsiAnnotationMemberValue annotationMemberValue = UltraLightAnnotationsKt.toAnnotationMemberValue((ConstantValue) it.next(), arrayLiteralParent);
                        if (annotationMemberValue != null) {
                            arrayList.add(annotationMemberValue);
                        }
                    }
                    return arrayList;
                }
            });
        }
        if (constantValue instanceof ErrorValue) {
            return null;
        }
        return UltraLightUtilsKt.createPsiLiteral(constantValue, psiElement);
    }
}
