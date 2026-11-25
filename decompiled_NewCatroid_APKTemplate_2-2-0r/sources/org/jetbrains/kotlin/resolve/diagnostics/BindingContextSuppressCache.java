package org.jetbrains.kotlin.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: KotlinSuppressCache.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/BindingContextSuppressCache;", "Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "getContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getSuppressionAnnotations", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "annotated", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isSuppressedByExtension", "", "suppressor", "Lorg/jetbrains/kotlin/resolve/diagnostics/DiagnosticSuppressor;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BindingContextSuppressCache extends KotlinSuppressCache {
    private final BindingContext context;

    public BindingContextSuppressCache(BindingContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final BindingContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.KotlinSuppressCache
    public List<AnnotationDescriptor> getSuppressionAnnotations(PsiElement annotated) {
        List<KtAnnotationEntry> annotationEntries;
        Annotations annotations;
        List<AnnotationDescriptor> list;
        Intrinsics.checkNotNullParameter(annotated, "annotated");
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.context.get(BindingContext.DECLARATION_TO_DESCRIPTOR, annotated);
        if (declarationDescriptor != null && (annotations = declarationDescriptor.getAnnotations()) != null && (list = CollectionsKt.toList(annotations)) != null) {
            return list;
        }
        KtAnnotated ktAnnotated = annotated instanceof KtAnnotated ? (KtAnnotated) annotated : null;
        if (ktAnnotated == null || (annotationEntries = ktAnnotated.getAnnotationEntries()) == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = annotationEntries.iterator();
        while (it.hasNext()) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) this.context.get(BindingContext.ANNOTATION, (KtAnnotationEntry) it.next());
            if (annotationDescriptor != null) {
                arrayList.add(annotationDescriptor);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.KotlinSuppressCache
    protected boolean isSuppressedByExtension(DiagnosticSuppressor suppressor, Diagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(suppressor, "suppressor");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return suppressor.isSuppressed(diagnostic, this.context);
    }
}
