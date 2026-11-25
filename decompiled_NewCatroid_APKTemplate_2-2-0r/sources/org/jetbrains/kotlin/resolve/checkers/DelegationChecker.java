package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DelegationResolver;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DelegationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J(\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/DelegationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkDescriptor", "classDeclaration", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "delegatedDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "delegatedToDescriptor", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegationChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtTypeReference typeReference;
        KotlinType kotlinType;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof ClassDescriptor) && (declaration instanceof KtClassOrObject)) {
            KtClassOrObject ktClassOrObject = (KtClassOrObject) declaration;
            for (KtSuperTypeListEntry ktSuperTypeListEntry : ktClassOrObject.getSuperTypeListEntries()) {
                if ((ktSuperTypeListEntry instanceof KtDelegatedSuperTypeEntry) && (typeReference = ((KtDelegatedSuperTypeEntry) ktSuperTypeListEntry).getTypeReference()) != null && (kotlinType = (KotlinType) context.getTrace().get(BindingContext.TYPE, typeReference)) != null) {
                    ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                    ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
                    if (classDescriptor != null) {
                        for (Map.Entry entry : DelegationResolver.Companion.getDelegates$default(DelegationResolver.INSTANCE, (ClassDescriptor) descriptor, classDescriptor, null, 4, null).entrySet()) {
                            checkDescriptor(ktClassOrObject, (CallableMemberDescriptor) entry.getKey(), (CallableMemberDescriptor) entry.getValue(), context.getTrace());
                        }
                    }
                }
            }
        }
    }

    private final void checkDescriptor(KtClassOrObject classDeclaration, CallableMemberDescriptor delegatedDescriptor, CallableMemberDescriptor delegatedToDescriptor, DiagnosticSink diagnosticHolder) {
        Set allOverriddenDescriptors = DescriptorUtils.getAllOverriddenDescriptors(delegatedDescriptor);
        Intrinsics.checkNotNullExpressionValue(allOverriddenDescriptors, "getAllOverriddenDescriptors(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : allOverriddenDescriptors) {
            if (((CallableMemberDescriptor) obj).getKind().isReal()) {
                arrayList.add(obj);
            }
        }
        Set setFilterOutOverridden = OverridingUtil.filterOutOverridden(CollectionsKt.toSet(arrayList));
        Intrinsics.checkNotNullExpressionValue(setFilterOutOverridden, "filterOutOverridden(...)");
        Set setMinus = SetsKt.minus((Set<? extends CallableMemberDescriptor>) setFilterOutOverridden, DescriptorUtils.unwrapFakeOverride(delegatedToDescriptor).getOriginal());
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : setMinus) {
            if (((CallableMemberDescriptor) obj2).getModality() == Modality.OPEN) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = arrayList2;
        if (arrayList3.isEmpty()) {
            return;
        }
        diagnosticHolder.report(Errors.DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE.on(classDeclaration, delegatedDescriptor, arrayList3));
    }
}
