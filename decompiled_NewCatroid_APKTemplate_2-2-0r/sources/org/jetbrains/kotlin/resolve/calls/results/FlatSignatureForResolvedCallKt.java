package org.jetbrains.kotlin.resolve.calls.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderImpl;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCallImpl;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.util.CancellationChecker;

/* JADX INFO: compiled from: FlatSignatureForResolvedCall.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a@\u0010\u0000\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e\u001a#\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\f\b\u0000\u0010\u0011*\u0006\u0012\u0002\b\u00030\u0002*\u0002H\u0011¢\u0006\u0002\u0010\u0012¨\u0006\u0013"}, d2 = {"createOverloadingConflictResolver", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadingConflictResolver;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "specificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "platformOverloadsSpecificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/PlatformOverloadsSpecificityComparator;", "cancellationChecker", "Lorg/jetbrains/kotlin/util/CancellationChecker;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "createFlatSignature", "Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "RC", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FlatSignatureForResolvedCallKt {
    public static final <RC extends ResolvedCall<?>> FlatSignature<RC> createFlatSignature(RC rc) {
        Intrinsics.checkNotNullParameter(rc, "<this>");
        CallableDescriptor original = rc.get$suspendPropertyDescriptor().getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        List<ValueParameterDescriptor> valueParameters = original.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        HashMap map = new HashMap();
        int i = 0;
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : rc.getValueArguments().entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            ResolvedValueArgument value = entry.getValue();
            if (value instanceof DefaultValueArgument) {
                i++;
            } else {
                ValueParameterDescriptor valueParameterDescriptor = valueParameters.get(key.getIndex());
                Intrinsics.checkNotNull(valueParameterDescriptor);
                KotlinType argumentValueType = FlatSignatureUtilsKt.getArgumentValueType(valueParameterDescriptor);
                for (ValueArgument valueArgument : value.getArguments()) {
                    Intrinsics.checkNotNull(valueArgument);
                    map.put(valueArgument, argumentValueType);
                }
            }
        }
        FlatSignature.Companion companion = FlatSignature.INSTANCE;
        List<? extends ValueArgument> valueArguments = rc.getCall().getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        List<? extends ValueArgument> list = valueArguments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((KotlinType) map.get((ValueArgument) it.next()));
        }
        return FlatSignatureUtilsKt.create(companion, rc, original, i, arrayList);
    }

    public static final OverloadingConflictResolver<ResolvedCall<?>> createOverloadingConflictResolver(KotlinBuiltIns builtIns, ModuleDescriptor module, TypeSpecificityComparator specificityComparator, PlatformOverloadsSpecificityComparator platformOverloadsSpecificityComparator, CancellationChecker cancellationChecker, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(specificityComparator, "specificityComparator");
        Intrinsics.checkNotNullParameter(platformOverloadsSpecificityComparator, "platformOverloadsSpecificityComparator");
        Intrinsics.checkNotNullParameter(cancellationChecker, "cancellationChecker");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return new OverloadingConflictResolver<>(builtIns, module, specificityComparator, platformOverloadsSpecificityComparator, cancellationChecker, AnonymousClass1.INSTANCE, new AnonymousClass2(ConstraintSystemBuilderImpl.INSTANCE), AnonymousClass3.INSTANCE, new Function1<ResolvedCall<?>, ResolvedCall<?>>() { // from class: org.jetbrains.kotlin.resolve.calls.results.FlatSignatureForResolvedCallKt.createOverloadingConflictResolver.4
            @Override // kotlin.jvm.functions.Function1
            public final ResolvedCall<?> invoke(ResolvedCall<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                VariableAsFunctionResolvedCallImpl variableAsFunctionResolvedCallImpl = it instanceof VariableAsFunctionResolvedCallImpl ? (VariableAsFunctionResolvedCallImpl) it : null;
                return variableAsFunctionResolvedCallImpl != null ? variableAsFunctionResolvedCallImpl.getVariableCall() : null;
            }
        }, new Function1<CallableDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.results.FlatSignatureForResolvedCallKt.createOverloadingConflictResolver.5
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(DescriptorToSourceUtils.descriptorToDeclaration(it) != null);
            }
        }, null, kotlinTypeRefiner);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.results.FlatSignatureForResolvedCallKt$createOverloadingConflictResolver$1, reason: invalid class name */
    /* JADX INFO: compiled from: FlatSignatureForResolvedCall.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<ResolvedCall<?>, CallableDescriptor> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getResultingDescriptor()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "getResultingDescriptor";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(ResolvedCall.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final CallableDescriptor invoke(ResolvedCall<?> p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return p0.getResultingDescriptor();
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.results.FlatSignatureForResolvedCallKt$createOverloadingConflictResolver$2, reason: invalid class name */
    /* JADX INFO: compiled from: FlatSignatureForResolvedCall.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function0<SimpleConstraintSystem> {
        AnonymousClass2(Object obj) {
            super(0, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "forSpecificity()Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "forSpecificity";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(ConstraintSystemBuilderImpl.Companion.class);
        }

        @Override // kotlin.jvm.functions.Function0
        public final SimpleConstraintSystem invoke() {
            return ((ConstraintSystemBuilderImpl.Companion) this.receiver).forSpecificity();
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.results.FlatSignatureForResolvedCallKt$createOverloadingConflictResolver$3, reason: invalid class name */
    /* JADX INFO: compiled from: FlatSignatureForResolvedCall.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass3 extends FunctionReference implements Function1<ResolvedCall<?>, FlatSignature<? extends ResolvedCall<?>>> {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        AnonymousClass3() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "createFlatSignature(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "createFlatSignature";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(FlatSignatureForResolvedCallKt.class, "frontend");
        }

        @Override // kotlin.jvm.functions.Function1
        public final FlatSignature<ResolvedCall<?>> invoke(ResolvedCall<?> p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return FlatSignatureForResolvedCallKt.createFlatSignature(p0);
        }
    }
}
