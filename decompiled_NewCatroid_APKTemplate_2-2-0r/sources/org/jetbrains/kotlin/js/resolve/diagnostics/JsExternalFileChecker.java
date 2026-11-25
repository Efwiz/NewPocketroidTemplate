package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JsStandardClassIds;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;

/* JADX INFO: compiled from: JsExternalFileChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsExternalFileChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "annotationFqNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsExternalFileChecker implements DeclarationChecker {
    public static final JsExternalFileChecker INSTANCE = new JsExternalFileChecker();
    private static final List<FqName> annotationFqNames;

    private JsExternalFileChecker() {
    }

    static {
        Set<ClassId> set = JsStandardClassIds.Annotations.annotationsRequiringExternal;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(((ClassId) it.next()).asSingleFqName());
        }
        annotationFqNames = arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Object next;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (AnnotationsUtils.isNativeObject(descriptor) || !DescriptorUtils.isTopLevelDeclaration(descriptor)) {
            return;
        }
        List<AnnotationDescriptor> containingFileAnnotations = AnnotationsUtils.getContainingFileAnnotations(context.getTrace().getContext(), descriptor);
        Intrinsics.checkNotNullExpressionValue(containingFileAnnotations, "getContainingFileAnnotations(...)");
        Iterator it = CollectionsKt.asSequence(containingFileAnnotations).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!CollectionsKt.contains(annotationFqNames, ((AnnotationDescriptor) next).getFqName()));
        AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) next;
        if (annotationDescriptor != null) {
            context.getTrace().report(ErrorsJs.NON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE.on(declaration, annotationDescriptor.getType()));
        }
    }
}
