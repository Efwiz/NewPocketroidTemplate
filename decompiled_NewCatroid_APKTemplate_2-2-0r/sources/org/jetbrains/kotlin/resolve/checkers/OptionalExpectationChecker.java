package org.jetbrains.kotlin.resolve.checkers;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;

/* JADX INFO: compiled from: OptionalExpectationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptionalExpectationChecker;", "", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "getOptionalExpectationEntry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OptionalExpectationChecker {
    public static final OptionalExpectationChecker INSTANCE = new OptionalExpectationChecker();

    private OptionalExpectationChecker() {
    }

    public final void check(KtDeclaration declaration, DeclarationDescriptor descriptor, BindingTrace trace) {
        KtAnnotationEntry optionalExpectationEntry;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        if (((descriptor instanceof MemberDescriptor) && ((MemberDescriptor) descriptor).get$isExpect()) || (optionalExpectationEntry = getOptionalExpectationEntry(declaration, trace)) == null) {
            return;
        }
        trace.report(Errors.OPTIONAL_EXPECTATION_NOT_ON_EXPECTED.on(optionalExpectationEntry));
    }

    private final KtAnnotationEntry getOptionalExpectationEntry(KtDeclaration declaration, BindingTrace trace) {
        Object obj;
        Object next;
        AnnotationDescriptor annotationDescriptor;
        List<KtAnnotationEntry> annotationEntries = declaration.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        Iterator<T> it = annotationEntries.iterator();
        do {
            obj = null;
            if (it.hasNext()) {
                next = it.next();
                annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, (KtAnnotationEntry) next);
            }
            return (KtAnnotationEntry) obj;
        } while (!Intrinsics.areEqual(annotationDescriptor != null ? annotationDescriptor.getFqName() : null, OptionalAnnotationUtil.INSTANCE.getOPTIONAL_EXPECTATION_FQ_NAME()));
        obj = next;
        return (KtAnnotationEntry) obj;
    }
}
