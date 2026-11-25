package org.jetbrains.kotlin.asJava.classes;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation;
import org.jetbrains.kotlin.asJava.elements.KtToJvmAnnotationsConverterKt;
import org.jetbrains.kotlin.asJava.elements.KtUltraLightModifierList;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;

/* JADX INFO: compiled from: KtUltraLightClassForRepeatableAnnotationContainer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \u00132\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\r\u001a\u00020\u0000H\u0016J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u000e\u0010\u0011\u001a\u0004\u0018\u00010\t*\u00020\u0012H\u0014R \u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightModifierListForRepeatableAnnotationContainer;", "Lorg/jetbrains/kotlin/asJava/elements/KtUltraLightModifierList;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForSourceDeclaration;", "containingClass", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForSourceDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)V", "annotationsFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "", "getAnnotationsFilter", "()Lkotlin/jvm/functions/Function1;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "hasModifierProperty", "name", "", "additionalConverter", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "Companion", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KtUltraLightModifierListForRepeatableAnnotationContainer extends KtUltraLightModifierList<KtLightClassForSourceDeclaration> {
    private static final Set<String> allowedAnnotations;
    private static final Set<String> modifiers;
    private final Function1<KtLightAbstractAnnotation, Boolean> annotationsFilter;
    private final KtLightClassForSourceDeclaration containingClass;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightModifierListForRepeatableAnnotationContainer(KtLightClassForSourceDeclaration containingClass, KtUltraLightSupport support) {
        super(containingClass, support);
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(support, "support");
        this.containingClass = containingClass;
        this.annotationsFilter = new Function1<KtLightAbstractAnnotation, Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightModifierListForRepeatableAnnotationContainer$annotationsFilter$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtLightAbstractAnnotation it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(CollectionsKt.contains(KtUltraLightModifierListForRepeatableAnnotationContainer.allowedAnnotations, it.getAnnotationFqName()));
            }
        };
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return modifiers.contains(name2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtUltraLightModifierListForRepeatableAnnotationContainer copy() {
        return new KtUltraLightModifierListForRepeatableAnnotationContainer(this.containingClass, getSupport());
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtUltraLightModifierList
    protected KtLightAbstractAnnotation additionalConverter(PsiAnnotation psiAnnotation) {
        Intrinsics.checkNotNullParameter(psiAnnotation, "<this>");
        return KtToJvmAnnotationsConverterKt.tryConvertAsRepeatableContainer(psiAnnotation);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightModifierList
    protected Function1<KtLightAbstractAnnotation, Boolean> getAnnotationsFilter() {
        return this.annotationsFilter;
    }

    static {
        String strAsString = StandardNames.FqNames.retention.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strAsString2 = StandardNames.FqNames.target.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        allowedAnnotations = SetsKt.setOf((Object[]) new String[]{KtToJvmAnnotationsConverterKt.KOTLIN_JVM_INTERNAL_REPEATABLE_CONTAINER, CommonClassNames.JAVA_LANG_ANNOTATION_RETENTION, strAsString, CommonClassNames.JAVA_LANG_ANNOTATION_TARGET, strAsString2});
        modifiers = SetsKt.setOf((Object[]) new String[]{"public", "abstract", "static"});
    }
}
