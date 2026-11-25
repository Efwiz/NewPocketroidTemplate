package org.jetbrains.kotlin.resolve.calls.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SmartCastDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.UnsafeCallError;
import org.jetbrains.kotlin.resolve.calls.model.UnstableSmartCast;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: AdditionalDiagnosticReporter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J,\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014J4\u0010\u0016\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J.\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/AdditionalDiagnosticReporter;", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "createSmartCastDiagnostic", "Lorg/jetbrains/kotlin/resolve/calls/model/SmartCastDiagnostic;", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "expectedResultType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "reportAdditionalDiagnostics", "", "resultingDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "kotlinDiagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "reportSmartCastOnReceiver", "receiver", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "reportSmartCasts", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AdditionalDiagnosticReporter {
    private final LanguageVersionSettings languageVersionSettings;

    public AdditionalDiagnosticReporter(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
    }

    public final void reportAdditionalDiagnostics(ResolvedCallAtom candidate, CallableDescriptor resultingDescriptor, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(resultingDescriptor, "resultingDescriptor");
        Intrinsics.checkNotNullParameter(kotlinDiagnosticsHolder, "kotlinDiagnosticsHolder");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        reportSmartCasts(candidate, resultingDescriptor, kotlinDiagnosticsHolder, diagnostics);
    }

    private final SmartCastDiagnostic createSmartCastDiagnostic(ResolvedCallAtom candidate, KotlinCallArgument argument, UnwrappedType expectedResultType) {
        if (!(argument instanceof ExpressionKotlinCallArgument)) {
            return null;
        }
        Collection<KotlinType> collectionExpandIntersectionTypeIfNecessary = TypeUtilsKt.expandIntersectionTypeIfNecessary(expectedResultType);
        ExpressionKotlinCallArgument expressionKotlinCallArgument = (ExpressionKotlinCallArgument) argument;
        KotlinType type = expressionKotlinCallArgument.getReceiver().getReceiverValue().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        Collection<KotlinType> collection = collectionExpandIntersectionTypeIfNecessary;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(Boolean.valueOf(KotlinTypeChecker.DEFAULT.isSubtypeOf(type, (KotlinType) it.next())));
        }
        ArrayList arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                if (((Boolean) it2.next()).booleanValue()) {
                    return null;
                }
            }
        }
        return new SmartCastDiagnostic(expressionKotlinCallArgument, ((KotlinType) CollectionsKt.first(collection)).unwrap(), candidate.getAtom());
    }

    private final SmartCastDiagnostic reportSmartCastOnReceiver(ResolvedCallAtom candidate, SimpleKotlinCallArgument receiver, ReceiverParameterDescriptor parameter, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        if (receiver == null || parameter == null) {
            return null;
        }
        UnwrappedType unwrappedTypeUnwrap = parameter.getType().unwrap();
        if (receiver.getIsSafeCall()) {
            unwrappedTypeUnwrap = unwrappedTypeUnwrap.makeNullableAsSpecified(true);
        }
        SmartCastDiagnostic smartCastDiagnosticCreateSmartCastDiagnostic = createSmartCastDiagnostic(candidate, receiver, unwrappedTypeUnwrap);
        if (smartCastDiagnosticCreateSmartCastDiagnostic == null) {
            return null;
        }
        Collection<? extends KotlinCallDiagnostic> collection = diagnostics;
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (obj instanceof UnsafeCallError) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((UnsafeCallError) it.next()).getReceiver(), receiver)) {
                    return null;
                }
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : collection) {
            if (obj2 instanceof UnstableSmartCast) {
                arrayList3.add(obj2);
            }
        }
        ArrayList arrayList4 = arrayList3;
        if (!(arrayList4 instanceof Collection) || !arrayList4.isEmpty()) {
            Iterator it2 = arrayList4.iterator();
            while (it2.hasNext()) {
                if (Intrinsics.areEqual(((UnstableSmartCast) it2.next()).getArgument(), receiver)) {
                    return null;
                }
            }
        }
        return smartCastDiagnosticCreateSmartCastDiagnostic;
    }

    private final void reportSmartCasts(ResolvedCallAtom candidate, CallableDescriptor resultingDescriptor, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        List<KotlinCallArgument> arguments;
        ResolvedCallAtomsKt.addDiagnosticIfNotNull(kotlinDiagnosticsHolder, reportSmartCastOnReceiver(candidate, candidate.getExtensionReceiverArgument(), resultingDescriptor.getExtensionReceiverParameter(), diagnostics));
        ResolvedCallAtomsKt.addDiagnosticIfNotNull(kotlinDiagnosticsHolder, reportSmartCastOnReceiver(candidate, candidate.getDispatchReceiverArgument(), resultingDescriptor.mo6753getDispatchReceiverParameter(), diagnostics));
        for (ValueParameterDescriptor valueParameterDescriptor : resultingDescriptor.getValueParameters()) {
            ResolvedCallArgument resolvedCallArgument = candidate.getArgumentMappingByOriginal().get(valueParameterDescriptor.getOriginal());
            if (resolvedCallArgument != null && (arguments = resolvedCallArgument.getArguments()) != null) {
                for (KotlinCallArgument kotlinCallArgument : arguments) {
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    SmartCastDiagnostic smartCastDiagnosticCreateSmartCastDiagnostic = createSmartCastDiagnostic(candidate, kotlinCallArgument, ArgumentsUtilsKt.getExpectedType(kotlinCallArgument, valueParameterDescriptor, this.languageVersionSettings));
                    if (smartCastDiagnosticCreateSmartCastDiagnostic != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : diagnostics) {
                            if (obj instanceof UnstableSmartCast) {
                                arrayList.add(obj);
                            }
                        }
                        ArrayList arrayList2 = arrayList;
                        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                            Iterator it = arrayList2.iterator();
                            do {
                                if (it.hasNext()) {
                                }
                            } while (!Intrinsics.areEqual(((UnstableSmartCast) it.next()).getArgument(), kotlinCallArgument));
                        }
                        kotlinDiagnosticsHolder.addDiagnostic(smartCastDiagnosticCreateSmartCastDiagnostic);
                        break;
                    }
                }
            }
        }
    }
}
