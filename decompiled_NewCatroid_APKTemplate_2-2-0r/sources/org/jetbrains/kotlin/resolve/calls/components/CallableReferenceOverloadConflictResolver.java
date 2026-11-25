package org.jetbrains.kotlin.resolve.calls.components;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignature;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignatureUtilsKt;
import org.jetbrains.kotlin.resolve.calls.results.OverloadingConflictResolver;
import org.jetbrains.kotlin.resolve.calls.results.PlatformOverloadsSpecificityComparator;
import org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.util.CancellationChecker;

/* JADX INFO: compiled from: CallableReferenceOverloadConflictResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00142\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0014BE\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceOverloadConflictResolver;", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadingConflictResolver;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "specificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "platformOverloadsSpecificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/PlatformOverloadsSpecificityComparator;", "cancellationChecker", "Lorg/jetbrains/kotlin/util/CancellationChecker;", "statelessCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionStatelessCallbacks;", "constraintInjector", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;Lorg/jetbrains/kotlin/resolve/calls/results/PlatformOverloadsSpecificityComparator;Lorg/jetbrains/kotlin/util/CancellationChecker;Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionStatelessCallbacks;Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceOverloadConflictResolver extends OverloadingConflictResolver<CallableReferenceResolutionCandidate> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CallableReferenceOverloadConflictResolver(final KotlinBuiltIns builtIns, ModuleDescriptor module, TypeSpecificityComparator specificityComparator, PlatformOverloadsSpecificityComparator platformOverloadsSpecificityComparator, CancellationChecker cancellationChecker, final KotlinResolutionStatelessCallbacks statelessCallbacks, final ConstraintInjector constraintInjector, KotlinTypeRefiner kotlinTypeRefiner) {
        super(builtIns, module, specificityComparator, platformOverloadsSpecificityComparator, cancellationChecker, new Function1<CallableReferenceResolutionCandidate, CallableDescriptor>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CallableReferenceOverloadConflictResolver.1
            @Override // kotlin.jvm.functions.Function1
            public final CallableDescriptor invoke(CallableReferenceResolutionCandidate it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getCandidate();
            }
        }, new Function0<SimpleConstraintSystem>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CallableReferenceOverloadConflictResolver.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleConstraintSystem invoke() {
                return statelessCallbacks.createConstraintSystemForOverloadResolution(constraintInjector, builtIns);
            }
        }, new AnonymousClass3(INSTANCE), new Function1<CallableReferenceResolutionCandidate, CallableReferenceResolutionCandidate>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CallableReferenceOverloadConflictResolver.4
            @Override // kotlin.jvm.functions.Function1
            public final CallableReferenceResolutionCandidate invoke(CallableReferenceResolutionCandidate it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return null;
            }
        }, new Function1<CallableDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CallableReferenceOverloadConflictResolver.5
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(statelessCallbacks.isDescriptorFromSource(it));
            }
        }, null, kotlinTypeRefiner);
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(specificityComparator, "specificityComparator");
        Intrinsics.checkNotNullParameter(platformOverloadsSpecificityComparator, "platformOverloadsSpecificityComparator");
        Intrinsics.checkNotNullParameter(cancellationChecker, "cancellationChecker");
        Intrinsics.checkNotNullParameter(statelessCallbacks, "statelessCallbacks");
        Intrinsics.checkNotNullParameter(constraintInjector, "constraintInjector");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.components.CallableReferenceOverloadConflictResolver$3, reason: invalid class name */
    /* JADX INFO: compiled from: CallableReferenceOverloadConflictResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass3 extends FunctionReference implements Function1<CallableReferenceResolutionCandidate, FlatSignature<? extends CallableReferenceResolutionCandidate>> {
        AnonymousClass3(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "createFlatSignature(Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;)Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "createFlatSignature";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Companion.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final FlatSignature<CallableReferenceResolutionCandidate> invoke(CallableReferenceResolutionCandidate p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((Companion) this.receiver).createFlatSignature(p0);
        }
    }

    /* JADX INFO: compiled from: CallableReferenceOverloadConflictResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0002¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceOverloadConflictResolver$Companion;", "", "()V", "createFlatSignature", "Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "candidate", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final FlatSignature<CallableReferenceResolutionCandidate> createFlatSignature(CallableReferenceResolutionCandidate candidate) {
            return FlatSignatureUtilsKt.createFromReflectionType(FlatSignature.INSTANCE, candidate, candidate.getCandidate(), candidate.getNumDefaults(), candidate.getExtensionReceiver() != null, candidate.getReflectionCandidateType());
        }
    }
}
