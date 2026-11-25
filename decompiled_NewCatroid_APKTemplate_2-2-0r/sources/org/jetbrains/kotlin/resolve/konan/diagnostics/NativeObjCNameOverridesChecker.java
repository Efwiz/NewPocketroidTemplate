package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: NativeObjCNameOverridesChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\f\u001a\u00020\r*\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e0\u000eH\u0002J\f\u0010\u0010\u001a\u00020\b*\u00020\bH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeObjCNameOverridesChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "allNamesEquals", "", "", "Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeObjCNameChecker$ObjCName;", "getFirstBaseDescriptor", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeObjCNameOverridesChecker implements DeclarationChecker {
    public static final NativeObjCNameOverridesChecker INSTANCE = new NativeObjCNameOverridesChecker();

    private NativeObjCNameOverridesChecker() {
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
                        INSTANCE.check(declaration, callableMemberDescriptor, context);
                    }
                }
            }
        }
    }

    public final void check(KtDeclaration declaration, CallableMemberDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor.getOverriddenDescriptors().isEmpty()) {
            return;
        }
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = descriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (CallableMemberDescriptor callableMemberDescriptor : collection) {
            NativeObjCNameChecker nativeObjCNameChecker = NativeObjCNameChecker.INSTANCE;
            NativeObjCNameOverridesChecker nativeObjCNameOverridesChecker = INSTANCE;
            Intrinsics.checkNotNull(callableMemberDescriptor);
            arrayList.add(nativeObjCNameChecker.getObjCNames(nativeObjCNameOverridesChecker.getFirstBaseDescriptor(callableMemberDescriptor)));
        }
        if (allNamesEquals(arrayList)) {
            return;
        }
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors2 = descriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors2, "getOverriddenDescriptors(...)");
        Collection<? extends CallableMemberDescriptor> collection2 = overriddenDescriptors2;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            arrayList2.add(((CallableMemberDescriptor) it.next()).getContainingDeclaration());
        }
        context.getTrace().report(ErrorsNative.INCOMPATIBLE_OBJC_NAME_OVERRIDE.on(declaration, descriptor, arrayList2));
    }

    private final CallableMemberDescriptor getFirstBaseDescriptor(CallableMemberDescriptor callableMemberDescriptor) {
        if (callableMemberDescriptor.getOverriddenDescriptors().isEmpty()) {
            return callableMemberDescriptor;
        }
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Object objFirst = CollectionsKt.first(overriddenDescriptors);
        Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
        return getFirstBaseDescriptor((CallableMemberDescriptor) objFirst);
    }

    private final boolean allNamesEquals(List<? extends List<NativeObjCNameChecker.ObjCName>> list) {
        List<NativeObjCNameChecker.ObjCName> list2 = list.get(0);
        int size = list.size();
        for (int i = 1; i < size; i++) {
            if (!Intrinsics.areEqual(list2, list.get(i))) {
                return false;
            }
        }
        return true;
    }
}
