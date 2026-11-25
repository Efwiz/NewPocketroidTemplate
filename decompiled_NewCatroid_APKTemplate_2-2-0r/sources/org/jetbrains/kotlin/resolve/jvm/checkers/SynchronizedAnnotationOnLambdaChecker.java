package org.jetbrains.kotlin.resolve.jvm.checkers;

import com.reandroid.arsc.chunk.TypeBlock;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: SynchronizedAnnotationOnLambdaChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J>\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/SynchronizedAnnotationOnLambdaChecker;", "Lorg/jetbrains/kotlin/resolve/AdditionalAnnotationChecker;", "()V", "checkEntries", "", TypeBlock.NAME_entries, "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "actualTargets", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "annotated", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SynchronizedAnnotationOnLambdaChecker implements AdditionalAnnotationChecker {
    public static final SynchronizedAnnotationOnLambdaChecker INSTANCE = new SynchronizedAnnotationOnLambdaChecker();

    private SynchronizedAnnotationOnLambdaChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker
    public void checkEntries(List<? extends KtAnnotationEntry> entries, List<? extends KotlinTarget> actualTargets, BindingTrace trace, KtAnnotated annotated, LanguageVersionSettings languageVersionSettings) {
        Object next;
        KtFunctionLiteral functionLiteral;
        AnnotationDescriptor annotationDescriptor;
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(actualTargets, "actualTargets");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (entries.isEmpty()) {
            return;
        }
        Iterator<T> it = entries.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, (KtAnnotationEntry) next);
            }
        } while (!Intrinsics.areEqual(annotationDescriptor != null ? annotationDescriptor.getFqName() : null, JvmNames.SYNCHRONIZED_ANNOTATION_FQ_NAME));
        KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) next;
        if (ktAnnotationEntry == null) {
            return;
        }
        PsiElement psiElementUnwrapParenthesesLabelsAndAnnotations = annotated != null ? PsiUtilsKt.unwrapParenthesesLabelsAndAnnotations(annotated) : null;
        KtLambdaExpression ktLambdaExpression = psiElementUnwrapParenthesesLabelsAndAnnotations instanceof KtLambdaExpression ? (KtLambdaExpression) psiElementUnwrapParenthesesLabelsAndAnnotations : null;
        if (ktLambdaExpression == null || (functionLiteral = ktLambdaExpression.getFunctionLiteral()) == null) {
            return;
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) trace.getContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, functionLiteral);
        if ((declarationDescriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) declarationDescriptor).isSuspend()) {
            trace.report(ErrorsJvm.SYNCHRONIZED_ON_SUSPEND.on(languageVersionSettings, ktAnnotationEntry));
        }
    }
}
