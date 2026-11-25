package org.jetbrains.kotlin.checkers.diagnostics;

import kotlin.Metadata;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: AbstractTestDiagnostic.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001J\u0011\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0000H¦\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/AbstractTestDiagnostic;", "", "inferenceCompatibility", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;", "getInferenceCompatibility", "()Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;", "name", "", "getName", "()Ljava/lang/String;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "getPlatform", "compareTo", "", "other", "enhanceInferenceCompatibility", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AbstractTestDiagnostic extends Comparable<AbstractTestDiagnostic> {
    int compareTo(AbstractTestDiagnostic other);

    void enhanceInferenceCompatibility(TextDiagnostic.InferenceCompatibility inferenceCompatibility);

    TextDiagnostic.InferenceCompatibility getInferenceCompatibility();

    String getName();

    String getPlatform();
}
