package org.jetbrains.kotlin.asJava.classes;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.psi.KtEnumEntry;

/* JADX INFO: compiled from: ultraLightClassForEnumEntry.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016J\b\u0010\u0015\u001a\u00020\rH\u0016J\b\u0010\u0016\u001a\u00020\bH\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R'\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForEnumEntry;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiEnumConstantInitializer;", "enumEntry", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "enumConstant", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiEnumConstant;", "(Lorg/jetbrains/kotlin/psi/KtEnumEntry;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lcom/intellij/psi/PsiEnumConstant;)V", "baseClassReferenceAndType", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaCodeReferenceElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "getBaseClassReferenceAndType", "()Lkotlin/Pair;", "baseClassReferenceAndType$delegate", "Lkotlin/Lazy;", "getArgumentList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiExpressionList;", "getBaseClassReference", "getBaseClassType", "getEnumConstant", "isInQualifiedNew", "", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightClassForEnumEntry extends KtUltraLightClass implements PsiEnumConstantInitializer {

    /* JADX INFO: renamed from: baseClassReferenceAndType$delegate, reason: from kotlin metadata */
    private final Lazy baseClassReferenceAndType;
    private final PsiEnumConstant enumConstant;

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiExpressionList getArgumentList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public boolean isInQualifiedNew() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightClassForEnumEntry(final KtEnumEntry enumEntry, KtUltraLightSupport support, PsiEnumConstant enumConstant) {
        super(enumEntry, support);
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(enumConstant, "enumConstant");
        this.enumConstant = enumConstant;
        this.baseClassReferenceAndType = ImplUtilsKt.lazyPub(new Function0<Pair<? extends PsiJavaCodeReferenceElement, ? extends PsiClassType>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForEnumEntry$baseClassReferenceAndType$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Pair<? extends PsiJavaCodeReferenceElement, ? extends PsiClassType> invoke() {
                PsiReferenceList extendsList = super/*org.jetbrains.kotlin.asJava.classes.KtUltraLightClass*/.getExtendsList();
                if (extendsList == null) {
                    throw new IllegalStateException(("KtUltraLightClass::getExtendsList is null for " + enumEntry.mo7391getFqName()).toString());
                }
                PsiJavaCodeReferenceElement[] referenceElements = extendsList.getReferenceElements();
                Intrinsics.checkNotNullExpressionValue(referenceElements, "getReferenceElements(...)");
                PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) ArraysKt.getOrNull(referenceElements, 0);
                if (psiJavaCodeReferenceElement == null) {
                    throw new IllegalStateException(("No referenceElements found for " + enumEntry.mo7391getFqName()).toString());
                }
                PsiClassType[] referencedTypes = extendsList.getReferencedTypes();
                Intrinsics.checkNotNullExpressionValue(referencedTypes, "getReferencedTypes(...)");
                PsiClassType psiClassType = (PsiClassType) ArraysKt.getOrNull(referencedTypes, 0);
                if (psiClassType != null) {
                    return new Pair<>(psiJavaCodeReferenceElement, psiClassType);
                }
                throw new IllegalStateException(("No referencedTypes found for " + enumEntry.mo7391getFqName()).toString());
            }
        });
    }

    private final Pair<PsiJavaCodeReferenceElement, PsiClassType> getBaseClassReferenceAndType() {
        return (Pair) this.baseClassReferenceAndType.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiClassType getBaseClassType() {
        return getBaseClassReferenceAndType().getSecond();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiJavaCodeReferenceElement getBaseClassReference() {
        return getBaseClassReferenceAndType().getFirst();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer
    public PsiEnumConstant getEnumConstant() {
        return this.enumConstant;
    }
}
