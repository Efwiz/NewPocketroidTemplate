package org.jetbrains.kotlin.resolve.jvm.checkers;

import com.reandroid.arsc.chunk.TypeBlock;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;

/* JADX INFO: compiled from: JvmSerializableLambdaAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J>\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmSerializableLambdaAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/AdditionalAnnotationChecker;", "()V", "checkEntries", "", TypeBlock.NAME_entries, "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "actualTargets", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "annotated", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSerializableLambdaAnnotationChecker implements AdditionalAnnotationChecker {
    public static final JvmSerializableLambdaAnnotationChecker INSTANCE = new JvmSerializableLambdaAnnotationChecker();

    private JvmSerializableLambdaAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker
    public void checkEntries(List<? extends KtAnnotationEntry> entries, List<? extends KotlinTarget> actualTargets, BindingTrace trace, KtAnnotated annotated, LanguageVersionSettings languageVersionSettings) {
        Object obj;
        String description;
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(actualTargets, "actualTargets");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Iterator<T> it = entries.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, (KtAnnotationEntry) next);
            if (Intrinsics.areEqual(annotationDescriptor != null ? annotationDescriptor.getFqName() : null, JvmAnnotationUtilKt.JVM_SERIALIZABLE_LAMBDA_ANNOTATION_FQ_NAME)) {
                obj = next;
                break;
            }
        }
        KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) obj;
        if (ktAnnotationEntry == null || actualTargets.contains(KotlinTarget.LAMBDA_EXPRESSION) || actualTargets.contains(KotlinTarget.ANONYMOUS_FUNCTION)) {
            return;
        }
        DiagnosticFactory1<KtAnnotationEntry, String> diagnosticFactory1 = Errors.WRONG_ANNOTATION_TARGET;
        KtAnnotationEntry ktAnnotationEntry2 = ktAnnotationEntry;
        KotlinTarget kotlinTarget = (KotlinTarget) CollectionsKt.firstOrNull((List) actualTargets);
        if (kotlinTarget == null || (description = kotlinTarget.getDescription()) == null) {
            description = "unidentified target";
        }
        trace.report(diagnosticFactory1.on(ktAnnotationEntry2, description));
    }
}
