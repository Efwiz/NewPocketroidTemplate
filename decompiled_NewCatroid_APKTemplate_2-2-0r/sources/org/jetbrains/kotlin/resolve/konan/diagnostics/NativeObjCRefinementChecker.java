package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: NativeObjCRefinementChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J$\u0010\u0011\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0012*\u00020\u000eH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeObjCRefinementChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "hidesFromObjCFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getHidesFromObjCFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "refinesInSwiftFqName", "getRefinesInSwiftFqName", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "findRefinedAnnotations", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeObjCRefinementChecker implements DeclarationChecker {
    public static final NativeObjCRefinementChecker INSTANCE = new NativeObjCRefinementChecker();
    private static final FqName hidesFromObjCFqName = new FqName("kotlin.native.HidesFromObjC");
    private static final FqName refinesInSwiftFqName = new FqName("kotlin.native.RefinesInSwift");

    private NativeObjCRefinementChecker() {
    }

    public final FqName getHidesFromObjCFqName() {
        return hidesFromObjCFqName;
    }

    public final FqName getRefinesInSwiftFqName() {
        return refinesInSwiftFqName;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof CallableMemberDescriptor) {
            if ((descriptor instanceof FunctionDescriptor) || (descriptor instanceof PropertyDescriptor)) {
                Pair<List<AnnotationDescriptor>, List<AnnotationDescriptor>> pairFindRefinedAnnotations = findRefinedAnnotations(descriptor);
                List<AnnotationDescriptor> listComponent1 = pairFindRefinedAnnotations.component1();
                List<AnnotationDescriptor> listComponent2 = pairFindRefinedAnnotations.component2();
                if (!listComponent1.isEmpty() && !listComponent2.isEmpty()) {
                    Iterator<T> it = listComponent2.iterator();
                    while (it.hasNext()) {
                        KtElement sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation((AnnotationDescriptor) it.next());
                        if (sourceFromAnnotation == null) {
                            sourceFromAnnotation = declaration;
                        }
                        context.getTrace().report(ErrorsNative.REDUNDANT_SWIFT_REFINEMENT.on(sourceFromAnnotation));
                    }
                }
                NativeObjCRefinementOverridesChecker.INSTANCE.check(declaration, (CallableMemberDescriptor) descriptor, context, listComponent1, listComponent2);
            }
        }
    }

    private final Pair<List<AnnotationDescriptor>, List<AnnotationDescriptor>> findRefinedAnnotations(DeclarationDescriptor declarationDescriptor) {
        Annotations annotations;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (AnnotationDescriptor annotationDescriptor : declarationDescriptor.getAnnotations()) {
            ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
            if (annotationClass != null && (annotations = annotationClass.getAnnotations()) != null) {
                Iterator<AnnotationDescriptor> it = annotations.iterator();
                while (it.hasNext()) {
                    FqName fqName = it.next().getFqName();
                    if (Intrinsics.areEqual(fqName, hidesFromObjCFqName)) {
                        arrayList.add(annotationDescriptor);
                        break;
                    }
                    if (Intrinsics.areEqual(fqName, refinesInSwiftFqName)) {
                        arrayList2.add(annotationDescriptor);
                        break;
                    }
                }
            }
        }
        return TuplesKt.to(arrayList, arrayList2);
    }
}
