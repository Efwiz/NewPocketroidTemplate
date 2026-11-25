package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.DetailedReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;

/* JADX INFO: compiled from: ErrorCandidateFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u001a*\u0010\t\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u001a\u0014\u0010\n\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0002¨\u0006\u0013"}, d2 = {"collectErrorCandidatesForFunction", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/ErrorCandidate;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "name", "Lorg/jetbrains/kotlin/name/Name;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "collectErrorCandidatesForVariable", "asClassifierCall", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/ErrorCandidateContext;", "asFunction", "", "getWrongResolutionToClassifier", "Lorg/jetbrains/kotlin/resolve/calls/tower/WrongResolutionToClassifier;", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ErrorCandidateFactoryKt {

    /* JADX INFO: compiled from: ErrorCandidateFactory.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClassKind.CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final Collection<ErrorCandidate<?>> collectErrorCandidatesForFunction(ImplicitScopeTower scopeTower, Name name2, DetailedReceiver detailedReceiver) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(name2, "name");
        ErrorCandidateContext errorCandidateContext = new ErrorCandidateContext(scopeTower, name2, detailedReceiver);
        asClassifierCall(errorCandidateContext, true);
        return errorCandidateContext.getResult();
    }

    public static final Collection<ErrorCandidate<?>> collectErrorCandidatesForVariable(ImplicitScopeTower scopeTower, Name name2, DetailedReceiver detailedReceiver) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(name2, "name");
        ErrorCandidateContext errorCandidateContext = new ErrorCandidateContext(scopeTower, name2, detailedReceiver);
        asClassifierCall(errorCandidateContext, false);
        return errorCandidateContext.getResult();
    }

    private static final void asClassifierCall(ErrorCandidateContext errorCandidateContext, boolean z) {
        ClassifierDescriptor classifierDescriptorFindClassifier;
        WrongResolutionToClassifier wrongResolutionToClassifier;
        DetailedReceiver explicitReceiver = errorCandidateContext.getExplicitReceiver();
        if (explicitReceiver instanceof ReceiverValueWithSmartCastInfo) {
            classifierDescriptorFindClassifier = ((ReceiverValueWithSmartCastInfo) errorCandidateContext.getExplicitReceiver()).getReceiverValue().getType().getMemberScope().mo7465getContributedClassifier(errorCandidateContext.getName(), errorCandidateContext.getScopeTower().getLocation());
        } else if (explicitReceiver instanceof QualifierReceiver) {
            classifierDescriptorFindClassifier = ((QualifierReceiver) errorCandidateContext.getExplicitReceiver()).getStaticScope().mo7465getContributedClassifier(errorCandidateContext.getName(), errorCandidateContext.getScopeTower().getLocation());
        } else {
            classifierDescriptorFindClassifier = ScopeUtilsKt.findClassifier(errorCandidateContext.getScopeTower().getLexicalScope(), errorCandidateContext.getName(), errorCandidateContext.getScopeTower().getLocation());
        }
        if (classifierDescriptorFindClassifier == null || (wrongResolutionToClassifier = getWrongResolutionToClassifier(errorCandidateContext, classifierDescriptorFindClassifier, z)) == null) {
            return;
        }
        errorCandidateContext.add(new ErrorCandidate.Classifier(classifierDescriptorFindClassifier, wrongResolutionToClassifier));
    }

    private static final WrongResolutionToClassifier getWrongResolutionToClassifier(ErrorCandidateContext errorCandidateContext, ClassifierDescriptor classifierDescriptor, boolean z) {
        if (classifierDescriptor instanceof TypeAliasDescriptor) {
            ClassDescriptor classDescriptor = ((TypeAliasDescriptor) classifierDescriptor).getClassDescriptor();
            if (classDescriptor != null) {
                return getWrongResolutionToClassifier(errorCandidateContext, classDescriptor, z);
            }
            return null;
        }
        if (classifierDescriptor instanceof TypeParameterDescriptor) {
            return z ? WrongResolutionToClassifier.TYPE_PARAMETER_AS_FUNCTION : WrongResolutionToClassifier.TYPE_PARAMETER_AS_VALUE;
        }
        if (!(classifierDescriptor instanceof ClassDescriptor)) {
            return null;
        }
        ClassDescriptor classDescriptor2 = (ClassDescriptor) classifierDescriptor;
        int i = WhenMappings.$EnumSwitchMapping$0[classDescriptor2.getKind().ordinal()];
        if (i == 1) {
            return z ? WrongResolutionToClassifier.INTERFACE_AS_FUNCTION : WrongResolutionToClassifier.INTERFACE_AS_VALUE;
        }
        if (i == 2) {
            if (z) {
                return WrongResolutionToClassifier.OBJECT_AS_FUNCTION;
            }
            return null;
        }
        if (i != 3) {
            return null;
        }
        if (z) {
            DetailedReceiver explicitReceiver = errorCandidateContext.getExplicitReceiver();
            if ((explicitReceiver != null ? explicitReceiver instanceof QualifierReceiver : true) && classDescriptor2.getIsInner()) {
                return WrongResolutionToClassifier.INNER_CLASS_CONSTRUCTOR_NO_RECEIVER;
            }
        }
        if (z && classDescriptor2.get$isExpect()) {
            return WrongResolutionToClassifier.EXPECT_CLASS_AS_FUNCTION;
        }
        if (z) {
            return null;
        }
        return WrongResolutionToClassifier.CLASS_AS_VALUE;
    }
}
