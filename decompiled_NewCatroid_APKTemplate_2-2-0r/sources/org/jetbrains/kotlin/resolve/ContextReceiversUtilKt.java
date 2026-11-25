package org.jetbrains.kotlin.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtContextReceiverList;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ContextReceiversUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a$\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¨\u0006\f"}, d2 = {"checkContextReceiversAreEnabled", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "contextReceiverList", "Lorg/jetbrains/kotlin/psi/KtContextReceiverList;", "checkSubtypingBetweenContextReceivers", "contextReceiverTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ContextReceiversUtilKt {
    public static final void checkContextReceiversAreEnabled(BindingTrace trace, LanguageVersionSettings languageVersionSettings, KtContextReceiverList contextReceiverList) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(contextReceiverList, "contextReceiverList");
        if (languageVersionSettings.supportsFeature(LanguageFeature.ContextReceivers)) {
            return;
        }
        trace.report(Errors.UNSUPPORTED_FEATURE.on(contextReceiverList, TuplesKt.to(LanguageFeature.ContextReceivers, languageVersionSettings)));
    }

    private static final KotlinType checkSubtypingBetweenContextReceivers$prepared(KotlinType kotlinType) {
        if (TypeUtilsKt.isTypeParameter(kotlinType)) {
            return (KotlinType) CollectionsKt.first(TypeUtilsKt.supertypes(kotlinType));
        }
        return TypeUtilsKt.containsTypeParameter(kotlinType) ? TypeUtilsKt.replaceArgumentsWithStarProjections(kotlinType) : kotlinType;
    }

    public static final void checkSubtypingBetweenContextReceivers(BindingTrace trace, KtContextReceiverList contextReceiverList, List<? extends KotlinType> contextReceiverTypes) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(contextReceiverList, "contextReceiverList");
        Intrinsics.checkNotNullParameter(contextReceiverTypes, "contextReceiverTypes");
        int lastIndex = CollectionsKt.getLastIndex(contextReceiverTypes);
        int i = 0;
        while (i < lastIndex) {
            KotlinType kotlinTypeCheckSubtypingBetweenContextReceivers$prepared = checkSubtypingBetweenContextReceivers$prepared(contextReceiverTypes.get(i));
            i++;
            int size = contextReceiverTypes.size();
            for (int i2 = i; i2 < size; i2++) {
                KotlinType kotlinTypeCheckSubtypingBetweenContextReceivers$prepared2 = checkSubtypingBetweenContextReceivers$prepared(contextReceiverTypes.get(i2));
                if (NewKotlinTypeChecker.INSTANCE.getDefault().isSubtypeOf(kotlinTypeCheckSubtypingBetweenContextReceivers$prepared, kotlinTypeCheckSubtypingBetweenContextReceivers$prepared2) || NewKotlinTypeChecker.INSTANCE.getDefault().isSubtypeOf(kotlinTypeCheckSubtypingBetweenContextReceivers$prepared2, kotlinTypeCheckSubtypingBetweenContextReceivers$prepared)) {
                    trace.report(Errors.SUBTYPING_BETWEEN_CONTEXT_RECEIVERS.on(contextReceiverList));
                    return;
                }
            }
        }
    }
}
