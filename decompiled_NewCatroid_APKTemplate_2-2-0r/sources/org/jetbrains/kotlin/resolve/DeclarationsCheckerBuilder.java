package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor;

/* JADX INFO: compiled from: DeclarationsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DeclarationsCheckerBuilder;", "", "descriptorResolver", "Lorg/jetbrains/kotlin/resolve/DescriptorResolver;", "originalModifiersChecker", "Lorg/jetbrains/kotlin/resolve/ModifiersChecker;", "annotationChecker", "Lorg/jetbrains/kotlin/resolve/AnnotationChecker;", "identifierChecker", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "typeSpecificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "diagnosticSuppressor", "Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "upperBoundChecker", "Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "(Lorg/jetbrains/kotlin/resolve/DescriptorResolver;Lorg/jetbrains/kotlin/resolve/ModifiersChecker;Lorg/jetbrains/kotlin/resolve/AnnotationChecker;Lorg/jetbrains/kotlin/resolve/IdentifierChecker;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;)V", "withTrace", "Lorg/jetbrains/kotlin/resolve/DeclarationsChecker;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationsCheckerBuilder {
    private final AnnotationChecker annotationChecker;
    private final DescriptorResolver descriptorResolver;
    private final PlatformDiagnosticSuppressor diagnosticSuppressor;
    private final IdentifierChecker identifierChecker;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModifiersChecker originalModifiersChecker;
    private final TypeSpecificityComparator typeSpecificityComparator;
    private final UpperBoundChecker upperBoundChecker;

    public DeclarationsCheckerBuilder(DescriptorResolver descriptorResolver, ModifiersChecker originalModifiersChecker, AnnotationChecker annotationChecker, IdentifierChecker identifierChecker, LanguageVersionSettings languageVersionSettings, TypeSpecificityComparator typeSpecificityComparator, PlatformDiagnosticSuppressor diagnosticSuppressor, UpperBoundChecker upperBoundChecker) {
        Intrinsics.checkNotNullParameter(descriptorResolver, "descriptorResolver");
        Intrinsics.checkNotNullParameter(originalModifiersChecker, "originalModifiersChecker");
        Intrinsics.checkNotNullParameter(annotationChecker, "annotationChecker");
        Intrinsics.checkNotNullParameter(identifierChecker, "identifierChecker");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(typeSpecificityComparator, "typeSpecificityComparator");
        Intrinsics.checkNotNullParameter(diagnosticSuppressor, "diagnosticSuppressor");
        Intrinsics.checkNotNullParameter(upperBoundChecker, "upperBoundChecker");
        this.descriptorResolver = descriptorResolver;
        this.originalModifiersChecker = originalModifiersChecker;
        this.annotationChecker = annotationChecker;
        this.identifierChecker = identifierChecker;
        this.languageVersionSettings = languageVersionSettings;
        this.typeSpecificityComparator = typeSpecificityComparator;
        this.diagnosticSuppressor = diagnosticSuppressor;
        this.upperBoundChecker = upperBoundChecker;
    }

    public final DeclarationsChecker withTrace(BindingTrace trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        return new DeclarationsChecker(this.descriptorResolver, this.originalModifiersChecker, this.annotationChecker, this.identifierChecker, trace, this.languageVersionSettings, this.typeSpecificityComparator, this.diagnosticSuppressor, this.upperBoundChecker);
    }
}
