package org.jetbrains.kotlin.resolve.jvm.checkers;

import com.reandroid.arsc.chunk.TypeBlock;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: ExplicitMetadataChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J>\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/ExplicitMetadataChecker;", "Lorg/jetbrains/kotlin/resolve/AdditionalAnnotationChecker;", "()V", "METADATA_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "checkEntries", "", TypeBlock.NAME_entries, "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "actualTargets", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "annotated", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExplicitMetadataChecker implements AdditionalAnnotationChecker {
    public static final ExplicitMetadataChecker INSTANCE = new ExplicitMetadataChecker();
    private static final FqName METADATA_FQ_NAME = new FqName("kotlin.Metadata");

    private ExplicitMetadataChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker
    public void checkEntries(List<? extends KtAnnotationEntry> entries, List<? extends KotlinTarget> actualTargets, BindingTrace trace, KtAnnotated annotated, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(actualTargets, "actualTargets");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        for (KtAnnotationEntry ktAnnotationEntry : entries) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, ktAnnotationEntry);
            if (annotationDescriptor != null && Intrinsics.areEqual(annotationDescriptor.getFqName(), METADATA_FQ_NAME)) {
                trace.report(ErrorsJvm.EXPLICIT_METADATA_IS_DISALLOWED.on(ktAnnotationEntry));
            }
        }
    }
}
