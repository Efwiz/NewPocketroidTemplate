package org.jetbrains.kotlin.resolve.diagnostics;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPsiFactoryKt;
import org.jetbrains.kotlin.psi.KtStubbedPsiUtil;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.util.ExtensionProvider;

/* JADX INFO: compiled from: KotlinSuppressCache.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 #2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002#$B\u0005¢\u0006\u0002\u0010\u0003J\"\u0010\r\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\nH\u0014J\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0002H\u0014J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0014\u001a\u00020\u0002H&J\u0016\u0010\u0018\u001a\u00020\n2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00020\u001aH\u0014J\u0018\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\tH\u0014J\u001e\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00130!2\u0006\u0010\"\u001a\u00020\u0017H\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache;", "Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "()V", "diagnosticSuppressors", "Lorg/jetbrains/kotlin/util/ExtensionProvider;", "Lorg/jetbrains/kotlin/resolve/diagnostics/DiagnosticSuppressor;", "filter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "", "getFilter", "()Lkotlin/jvm/functions/Function1;", "getClosestAnnotatedAncestorElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "rootElement", "excludeSelf", "getSuppressingStrings", "", "", "annotated", "getSuppressionAnnotations", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "isSuppressed", "request", "Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$SuppressRequest;", "isSuppressedByExtension", "suppressor", "diagnostic", "processAnnotation", "", "builder", "Lorg/jetbrains/kotlin/com/google/common/collect/ImmutableSet$Builder;", "annotationDescriptor", "Companion", "DiagnosticSuppressRequest", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinSuppressCache extends AbstractKotlinSuppressCache<PsiElement> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ExtensionProvider<DiagnosticSuppressor> diagnosticSuppressors = ExtensionProvider.INSTANCE.create(DiagnosticSuppressor.INSTANCE.getEP_NAME());
    private final Function1<Diagnostic, Boolean> filter = new Function1<Diagnostic, Boolean>() { // from class: org.jetbrains.kotlin.resolve.diagnostics.KotlinSuppressCache$filter$1
        {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(Diagnostic diagnostic) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            return Boolean.valueOf(!this.this$0.isSuppressed(new KotlinSuppressCache.DiagnosticSuppressRequest(diagnostic)));
        }
    };

    public abstract List<AnnotationDescriptor> getSuppressionAnnotations(PsiElement annotated);

    public final Function1<Diagnostic, Boolean> getFilter() {
        return this.filter;
    }

    protected boolean isSuppressedByExtension(DiagnosticSuppressor suppressor, Diagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(suppressor, "suppressor");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return suppressor.isSuppressed(diagnostic);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache
    public Set<String> getSuppressingStrings(PsiElement annotated) {
        Intrinsics.checkNotNullParameter(annotated, "annotated");
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (AnnotationDescriptor annotationDescriptor : getSuppressionAnnotations(annotated)) {
            Intrinsics.checkNotNull(builder);
            processAnnotation(builder, annotationDescriptor);
        }
        ImmutableSet<String> immutableSetBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(immutableSetBuild, "build(...)");
        return immutableSetBuild;
    }

    private final void processAnnotation(ImmutableSet.Builder<String> builder, AnnotationDescriptor annotationDescriptor) {
        if (Intrinsics.areEqual(annotationDescriptor.getFqName(), StandardNames.FqNames.suppress)) {
            for (ConstantValue<?> constantValue : annotationDescriptor.getAllValueArguments().values()) {
                if (constantValue instanceof ArrayValue) {
                    for (ConstantValue<?> constantValue2 : ((ArrayValue) constantValue).getValue()) {
                        if (constantValue2 instanceof StringValue) {
                            String lowerCase = ((StringValue) constantValue2).getValue().toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                            builder.add(lowerCase);
                        }
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache
    protected boolean isSuppressed(AbstractKotlinSuppressCache.SuppressRequest<PsiElement> request) {
        Intrinsics.checkNotNullParameter(request, "request");
        PsiElement element = request.getElement();
        if (!element.isValid()) {
            return true;
        }
        PsiFile containingFile = element.getContainingFile();
        if ((containingFile instanceof KtFile) && KtPsiFactoryKt.getDoNotAnalyze((KtFile) containingFile) != null) {
            return true;
        }
        if (request instanceof DiagnosticSuppressRequest) {
            Iterator it = this.diagnosticSuppressors.get().iterator();
            while (it.hasNext()) {
                if (isSuppressedByExtension((DiagnosticSuppressor) it.next(), ((DiagnosticSuppressRequest) request).getDiagnostic())) {
                    return true;
                }
            }
        }
        return super.isSuppressed(request);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache
    public PsiElement getClosestAnnotatedAncestorElement(PsiElement element, PsiElement rootElement, boolean excludeSelf) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(rootElement, "rootElement");
        return KtStubbedPsiUtil.getPsiOrStubParent(element, KtAnnotated.class, excludeSelf);
    }

    /* JADX INFO: compiled from: KotlinSuppressCache.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache$DiagnosticSuppressRequest;", "Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$SuppressRequest;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "(Lorg/jetbrains/kotlin/diagnostics/Diagnostic;)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getElement", "()Lcom/intellij/psi/PsiElement;", "rootElement", "getRootElement", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "suppressKey", "", "getSuppressKey", "()Ljava/lang/String;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class DiagnosticSuppressRequest implements AbstractKotlinSuppressCache.SuppressRequest<PsiElement> {
        private final Diagnostic diagnostic;

        public DiagnosticSuppressRequest(Diagnostic diagnostic) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            this.diagnostic = diagnostic;
        }

        public final Diagnostic getDiagnostic() {
            return this.diagnostic;
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.SuppressRequest
        public PsiElement getElement() {
            return this.diagnostic.getPsiElement();
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.SuppressRequest
        public PsiElement getRootElement() {
            PsiFile containingFile = getElement().getContainingFile();
            Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
            return containingFile;
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.SuppressRequest
        public Severity getSeverity() {
            return this.diagnostic.getSeverity();
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.SuppressRequest
        public String getSuppressKey() {
            return KotlinSuppressCache.INSTANCE.getDiagnosticSuppressKey$frontend(this.diagnostic);
        }
    }

    /* JADX INFO: compiled from: KotlinSuppressCache.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache$Companion;", "", "()V", "getDiagnosticSuppressKey", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "getDiagnosticSuppressKey$frontend", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getDiagnosticSuppressKey$frontend(Diagnostic diagnostic) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            String lowerCase = diagnostic.getFactory().getName().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            return lowerCase;
        }
    }
}
