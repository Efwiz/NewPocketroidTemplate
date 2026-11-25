package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.calls.DslMarkerUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;

/* JADX INFO: compiled from: DslScopeViolationCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J,\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/DslScopeViolationCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkCallImplicitReceiver", "callImplicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DslScopeViolationCallChecker implements CallChecker {
    public static final DslScopeViolationCallChecker INSTANCE = new DslScopeViolationCallChecker();

    private DslScopeViolationCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.DslMarkersSupport)) {
            ArrayList implicitReceivers = ResolvedCallUtilKt.getImplicitReceivers(resolvedCall);
            if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.NewInference)) {
                Collection<ReceiverValue> collection = implicitReceivers;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
                Iterator<T> it = collection.iterator();
                while (it.hasNext()) {
                    arrayList.add(((ReceiverValue) it.next()).getOriginal());
                }
                implicitReceivers = arrayList;
            }
            Iterator<ReceiverValue> it2 = implicitReceivers.iterator();
            while (it2.hasNext()) {
                checkCallImplicitReceiver(it2.next(), resolvedCall, reportOn, context);
            }
        }
    }

    private final void checkCallImplicitReceiver(final ReceiverValue callImplicitReceiver, ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Object obj;
        Object next;
        DiagnosticFactory1<PsiElement, CallableDescriptor> diagnosticFactory1;
        final boolean zSupportsFeature = context.getLanguageVersionSettings().supportsFeature(LanguageFeature.NewInference);
        Sequence sequenceFilter = SequencesKt.filter(ScopeUtilsKt.getParentsWithSelf(context.getScope()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.checkers.DslScopeViolationCallChecker$checkCallImplicitReceiver$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj2) {
                return Boolean.valueOf(obj2 instanceof LexicalScope);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        List list = SequencesKt.toList(SequencesKt.takeWhile(SequencesKt.map(SequencesKt.flatMapIterable(sequenceFilter, new Function1<LexicalScope, List<? extends ReceiverParameterDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.calls.checkers.DslScopeViolationCallChecker$checkCallImplicitReceiver$receiversUntilOneFromTheCall$1
            @Override // kotlin.jvm.functions.Function1
            public final List<ReceiverParameterDescriptor> invoke(LexicalScope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(it.getImplicitReceiver()), (Iterable) it.getContextReceiversGroup());
            }
        }), new Function1<ReceiverParameterDescriptor, ReceiverValue>() { // from class: org.jetbrains.kotlin.resolve.calls.checkers.DslScopeViolationCallChecker$checkCallImplicitReceiver$receiversUntilOneFromTheCall$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ReceiverValue invoke(ReceiverParameterDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                boolean z = zSupportsFeature;
                ReceiverValue value = it.getValue();
                return z ? value.getOriginal() : value;
            }
        }), new Function1<ReceiverValue, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.checkers.DslScopeViolationCallChecker$checkCallImplicitReceiver$receiversUntilOneFromTheCall$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ReceiverValue it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!Intrinsics.areEqual(it, callImplicitReceiver));
            }
        }));
        if (list.isEmpty()) {
            return;
        }
        DslMarkerUtils.DslMarkersFromReceiver dslMarkersFromReceiverExtractDslMarkerFqNames = DslMarkerUtils.INSTANCE.extractDslMarkerFqNames(callImplicitReceiver);
        Set<FqName> setComponent1 = dslMarkersFromReceiverExtractDslMarkerFqNames.component1();
        Set<FqName> setComponent2 = dslMarkersFromReceiverExtractDslMarkerFqNames.component2();
        if (setComponent1.isEmpty() && setComponent2.isEmpty()) {
            return;
        }
        List list2 = list;
        DslMarkerUtils dslMarkerUtils = DslMarkerUtils.INSTANCE;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(dslMarkerUtils.extractDslMarkerFqNames((ReceiverValue) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        Iterator it2 = arrayList2.iterator();
        loop1: while (true) {
            obj = null;
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
            Set<FqName> setComponent3 = ((DslMarkerUtils.DslMarkersFromReceiver) next).component1();
            if (!(setComponent3 instanceof Collection) || !setComponent3.isEmpty()) {
                Iterator<T> it3 = setComponent3.iterator();
                while (it3.hasNext()) {
                    if (setComponent1.contains((FqName) it3.next())) {
                        break loop1;
                    }
                }
            }
        }
        if (((DslMarkerUtils.DslMarkersFromReceiver) next) != null) {
            context.getTrace().report(Errors.DSL_SCOPE_VIOLATION.on(reportOn, resolvedCall.getResultingDescriptor()));
            return;
        }
        Set setPlus = SetsKt.plus((Set) setComponent1, (Iterable) setComponent2);
        loop3: for (Object obj2 : arrayList2) {
            DslMarkerUtils.DslMarkersFromReceiver dslMarkersFromReceiver = (DslMarkerUtils.DslMarkersFromReceiver) obj2;
            Set setPlus2 = SetsKt.plus((Set) dslMarkersFromReceiver.component1(), (Iterable) dslMarkersFromReceiver.component2());
            Set set = setPlus;
            if (!(set instanceof Collection) || !set.isEmpty()) {
                Iterator it4 = set.iterator();
                while (it4.hasNext()) {
                    if (setPlus2.contains((FqName) it4.next())) {
                        obj = obj2;
                        break loop3;
                    }
                }
            }
        }
        if (((DslMarkerUtils.DslMarkersFromReceiver) obj) != null) {
            if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.DslMarkerOnFunctionTypeReceiver)) {
                diagnosticFactory1 = Errors.DSL_SCOPE_VIOLATION;
            } else {
                diagnosticFactory1 = Errors.DSL_SCOPE_VIOLATION_WARNING;
            }
            context.getTrace().report(diagnosticFactory1.on(reportOn, resolvedCall.getResultingDescriptor()));
        }
    }
}
