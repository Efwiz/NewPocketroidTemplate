package org.jetbrains.kotlin.js.resolve.diagnostics;

import com.reandroid.arsc.chunk.TypeBlock;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.js.JsHelpersKt;
import org.jetbrains.kotlin.name.JsStandardClassIds;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;

/* JADX INFO: compiled from: JsQualifierChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J>\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsQualifierChecker;", "Lorg/jetbrains/kotlin/resolve/AdditionalAnnotationChecker;", "()V", "checkEntries", "", TypeBlock.NAME_entries, "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "actualTargets", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "annotated", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsQualifierChecker implements AdditionalAnnotationChecker {
    public static final JsQualifierChecker INSTANCE = new JsQualifierChecker();

    private JsQualifierChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker
    public void checkEntries(List<? extends KtAnnotationEntry> entries, List<? extends KotlinTarget> actualTargets, BindingTrace trace, KtAnnotated annotated, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(actualTargets, "actualTargets");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        for (KtAnnotationEntry ktAnnotationEntry : entries) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) context.get(BindingContext.ANNOTATION, ktAnnotationEntry);
            if (annotationDescriptor != null && Intrinsics.areEqual(annotationDescriptor.getFqName(), JsStandardClassIds.Annotations.JsQualifier.asSingleFqName())) {
                ConstantValue constantValue = (ConstantValue) CollectionsKt.singleOrNull(annotationDescriptor.getAllValueArguments().values());
                Object value = constantValue != null ? constantValue.getValue() : null;
                String str = value instanceof String ? (String) value : null;
                if (str != null && !JsHelpersKt.validateQualifier(str)) {
                    KtValueArgumentList valueArgumentList = ktAnnotationEntry.getValueArgumentList();
                    Intrinsics.checkNotNull(valueArgumentList);
                    trace.report(ErrorsJs.WRONG_JS_QUALIFIER.on(valueArgumentList.getArguments().get(0)));
                }
            }
        }
    }
}
