package org.jetbrains.kotlin.resolve.diagnostics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: OnDemandSuppressCache.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\"\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0014J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0015\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/OnDemandSuppressCache;", "Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "processedRoots", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "storage", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "ensureRootProcessed", "", "rootElement", "getClosestAnnotatedAncestorElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "excludeSelf", "", "getSuppressionAnnotations", "annotated", "PrecomputingVisitor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OnDemandSuppressCache extends KotlinSuppressCache {
    private final BindingContext context;
    private final Set<KtFile> processedRoots;
    private final Map<PsiElement, List<AnnotationDescriptor>> storage;

    public OnDemandSuppressCache(BindingContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.processedRoots = new LinkedHashSet();
        this.storage = new LinkedHashMap();
    }

    private final synchronized void ensureRootProcessed(PsiElement rootElement) {
        if (!(rootElement instanceof KtFile)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!this.processedRoots.contains(rootElement)) {
            ((KtFile) rootElement).accept(new PrecomputingVisitor(this.storage, new BindingContextSuppressCache(this.context)), null);
            this.processedRoots.add(rootElement);
        }
    }

    /* JADX INFO: compiled from: OnDemandSuppressCache.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0018\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR#\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/OnDemandSuppressCache$PrecomputingVisitor;", "Lorg/jetbrains/kotlin/psi/KtTreeVisitorVoid;", "storage", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "computer", "Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache;)V", "getComputer", "()Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache;", "getStorage", "()Ljava/util/Map;", "computeAnnotations", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "visitKtElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "visitKtFile", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class PrecomputingVisitor extends KtTreeVisitorVoid {
        private final KotlinSuppressCache computer;
        private final Map<PsiElement, List<AnnotationDescriptor>> storage;

        public final Map<PsiElement, List<AnnotationDescriptor>> getStorage() {
            return this.storage;
        }

        public final KotlinSuppressCache getComputer() {
            return this.computer;
        }

        public PrecomputingVisitor(Map<PsiElement, List<AnnotationDescriptor>> storage, KotlinSuppressCache computer) {
            Intrinsics.checkNotNullParameter(storage, "storage");
            Intrinsics.checkNotNullParameter(computer, "computer");
            this.storage = storage;
            this.computer = computer;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitKtElement(KtElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            super.visitKtElement(element);
            if (element instanceof KtAnnotated) {
                computeAnnotations((KtAnnotated) element);
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitKtFile(KtFile file) {
            Intrinsics.checkNotNullParameter(file, "file");
            super.visitKtFile(file);
            computeAnnotations(file);
        }

        private final void computeAnnotations(KtAnnotated element) {
            List<AnnotationDescriptor> suppressionAnnotations = this.computer.getSuppressionAnnotations(element);
            ArrayList arrayList = new ArrayList();
            for (Object obj : suppressionAnnotations) {
                if (Intrinsics.areEqual(((AnnotationDescriptor) obj).getFqName(), StandardNames.FqNames.suppress)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                return;
            }
            this.storage.put(element, arrayList2);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.diagnostics.KotlinSuppressCache
    public List<AnnotationDescriptor> getSuppressionAnnotations(PsiElement annotated) {
        Intrinsics.checkNotNullParameter(annotated, "annotated");
        List<AnnotationDescriptor> list = this.storage.get(annotated);
        return list == null ? CollectionsKt.emptyList() : list;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.resolve.diagnostics.KotlinSuppressCache, org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache
    public PsiElement getClosestAnnotatedAncestorElement(PsiElement element, PsiElement rootElement, boolean excludeSelf) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(rootElement, "rootElement");
        ensureRootProcessed(rootElement);
        return super.getClosestAnnotatedAncestorElement(element, rootElement, excludeSelf);
    }
}
