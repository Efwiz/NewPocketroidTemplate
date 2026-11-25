package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: NativeObjCRefinementOverridesChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\fJ \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\bH\u0002J\u0018\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\bH\u0002J8\u0010\u0015\u001a\u00020\u0004*\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u0002¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeObjCRefinementOverridesChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declarationToReport", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "objCAnnotations", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "swiftAnnotations", "declaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "hasRefinedAnnotations", "Lkotlin/Pair;", "", "inheritsRefinedAnnotations", "reportIncompatibleOverride", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "annotations", "notRefinedSupers", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeObjCRefinementOverridesChecker implements DeclarationChecker {
    public static final NativeObjCRefinementOverridesChecker INSTANCE = new NativeObjCRefinementOverridesChecker();

    private NativeObjCRefinementOverridesChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof ClassDescriptor) {
            for (DeclarationDescriptor declarationDescriptor : ((ClassDescriptor) descriptor).getDefaultType().getMemberScope().getContributedDescriptors(DescriptorKindFilter.ALL, MemberScope.INSTANCE.getALL_NAME_FILTER())) {
                if (declarationDescriptor instanceof CallableMemberDescriptor) {
                    CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
                    if (!callableMemberDescriptor.getKind().isReal()) {
                        INSTANCE.check(declaration, callableMemberDescriptor, context, CollectionsKt.emptyList(), CollectionsKt.emptyList());
                    }
                }
            }
        }
    }

    public final void check(KtDeclaration declarationToReport, CallableMemberDescriptor descriptor, DeclarationCheckerContext context, List<? extends AnnotationDescriptor> objCAnnotations, List<? extends AnnotationDescriptor> swiftAnnotations) {
        Intrinsics.checkNotNullParameter(declarationToReport, "declarationToReport");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(objCAnnotations, "objCAnnotations");
        Intrinsics.checkNotNullParameter(swiftAnnotations, "swiftAnnotations");
        if (descriptor.getOverriddenDescriptors().isEmpty()) {
            return;
        }
        boolean z = !objCAnnotations.isEmpty();
        boolean z2 = !swiftAnnotations.isEmpty();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean z3 = z2;
        for (CallableMemberDescriptor callableMemberDescriptor : descriptor.getOverriddenDescriptors()) {
            Intrinsics.checkNotNull(callableMemberDescriptor);
            Pair<Boolean, Boolean> pairInheritsRefinedAnnotations = inheritsRefinedAnnotations(callableMemberDescriptor);
            boolean zBooleanValue = pairInheritsRefinedAnnotations.component1().booleanValue();
            boolean zBooleanValue2 = pairInheritsRefinedAnnotations.component2().booleanValue();
            if (zBooleanValue) {
                z = true;
            } else {
                arrayList.add(callableMemberDescriptor);
            }
            if (zBooleanValue2) {
                z3 = true;
            } else {
                arrayList2.add(callableMemberDescriptor);
            }
        }
        if (z && !arrayList.isEmpty()) {
            reportIncompatibleOverride(context.getTrace(), declarationToReport, descriptor, objCAnnotations, arrayList);
        }
        if (!z3 || arrayList2.isEmpty()) {
            return;
        }
        reportIncompatibleOverride(context.getTrace(), declarationToReport, descriptor, swiftAnnotations, arrayList2);
    }

    private final Pair<Boolean, Boolean> inheritsRefinedAnnotations(CallableMemberDescriptor callableMemberDescriptor) {
        Pair<Boolean, Boolean> pairHasRefinedAnnotations = hasRefinedAnnotations(callableMemberDescriptor);
        boolean zBooleanValue = pairHasRefinedAnnotations.component1().booleanValue();
        boolean zBooleanValue2 = pairHasRefinedAnnotations.component2().booleanValue();
        boolean z = true;
        if (zBooleanValue && zBooleanValue2) {
            return TuplesKt.to(true, true);
        }
        if (callableMemberDescriptor.getOverriddenDescriptors().isEmpty()) {
            return TuplesKt.to(Boolean.valueOf(zBooleanValue), Boolean.valueOf(zBooleanValue2));
        }
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Object objFirst = CollectionsKt.first(overriddenDescriptors);
        Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
        Pair<Boolean, Boolean> pairInheritsRefinedAnnotations = inheritsRefinedAnnotations((CallableMemberDescriptor) objFirst);
        boolean zBooleanValue3 = pairInheritsRefinedAnnotations.component1().booleanValue();
        boolean zBooleanValue4 = pairInheritsRefinedAnnotations.component2().booleanValue();
        Boolean boolValueOf = Boolean.valueOf(zBooleanValue || zBooleanValue3);
        if (!zBooleanValue2 && !zBooleanValue4) {
            z = false;
        }
        return TuplesKt.to(boolValueOf, Boolean.valueOf(z));
    }

    private final Pair<Boolean, Boolean> hasRefinedAnnotations(CallableMemberDescriptor callableMemberDescriptor) {
        Annotations annotations;
        Iterator<AnnotationDescriptor> it = callableMemberDescriptor.getAnnotations().iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(it.next());
            if (annotationClass != null && (annotations = annotationClass.getAnnotations()) != null) {
                Iterator<AnnotationDescriptor> it2 = annotations.iterator();
                while (it2.hasNext()) {
                    FqName fqName = it2.next().getFqName();
                    if (Intrinsics.areEqual(fqName, NativeObjCRefinementChecker.INSTANCE.getHidesFromObjCFqName())) {
                        z = true;
                        break;
                    }
                    if (Intrinsics.areEqual(fqName, NativeObjCRefinementChecker.INSTANCE.getRefinesInSwiftFqName())) {
                        z2 = true;
                        break;
                    }
                }
                if (z && z2) {
                    return TuplesKt.to(true, true);
                }
            }
        }
        return TuplesKt.to(Boolean.valueOf(z), Boolean.valueOf(z2));
    }

    private final void reportIncompatibleOverride(BindingTrace bindingTrace, KtDeclaration ktDeclaration, CallableMemberDescriptor callableMemberDescriptor, List<? extends AnnotationDescriptor> list, List<? extends CallableMemberDescriptor> list2) {
        List<? extends CallableMemberDescriptor> list3 = list2;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator<T> it = list3.iterator();
        while (it.hasNext()) {
            arrayList.add(((CallableMemberDescriptor) it.next()).getContainingModule());
        }
        ArrayList arrayList2 = arrayList;
        if (list.isEmpty()) {
            bindingTrace.report(ErrorsNative.INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE.on(ktDeclaration, callableMemberDescriptor, arrayList2));
            return;
        }
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            KtElement sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation((AnnotationDescriptor) it2.next());
            if (sourceFromAnnotation == null) {
                sourceFromAnnotation = ktDeclaration;
            }
            bindingTrace.report(ErrorsNative.INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE.on(sourceFromAnnotation, callableMemberDescriptor, arrayList2));
        }
    }
}
