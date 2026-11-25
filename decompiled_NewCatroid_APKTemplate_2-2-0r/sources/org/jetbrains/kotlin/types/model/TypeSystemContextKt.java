package org.jetbrains.kotlin.types.model;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a#\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a.\u0010\u0006\u001a\u00020\u0003*\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0014\b\u0004\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u000bH\u0086\bø\u0001\u0000\u001a\n\u0010\r\u001a\u00020\u000e*\u00020\u000f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0010"}, d2 = {"requireOrDescribe", "", "condition", "", "value", "", DevModeOverwritingStrategies.ALL, "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "convertVariance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "Lorg/jetbrains/kotlin/types/Variance;", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeSystemContextKt {

    /* JADX INFO: compiled from: TypeSystemContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final TypeVariance convertVariance(Variance variance) {
        Intrinsics.checkNotNullParameter(variance, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
        if (i == 1) {
            return TypeVariance.INV;
        }
        if (i == 2) {
            return TypeVariance.IN;
        }
        if (i == 3) {
            return TypeVariance.OUT;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final boolean all(TypeArgumentListMarker typeArgumentListMarker, TypeSystemContext context, Function1<? super TypeArgumentMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int size = context.size(typeArgumentListMarker);
        for (int i = 0; i < size; i++) {
            if (!predicate.invoke(context.get(typeArgumentListMarker, i)).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public static final void requireOrDescribe(boolean z, Object obj) {
        String str;
        if (z) {
            return;
        }
        if (obj != null) {
            str = ", type = '" + Reflection.getOrCreateKotlinClass(obj.getClass()) + '\'';
        } else {
            str = "";
        }
        throw new IllegalArgumentException(("Unexpected: value = '" + obj + '\'' + str).toString());
    }
}
