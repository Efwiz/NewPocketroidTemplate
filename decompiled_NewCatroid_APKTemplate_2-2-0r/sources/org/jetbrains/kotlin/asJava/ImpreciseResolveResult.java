package org.jetbrains.kotlin.asJava;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ImpreciseResolveResult.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\u0006H\u0086\bø\u0001\u0000j\u0002\b\bj\u0002\b\tj\u0002\b\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/asJava/ImpreciseResolveResult;", "", "(Ljava/lang/String;I)V", "ifSure", "", "body", "Lkotlin/Function1;", "", "MATCH", "NO_MATCH", "UNSURE", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ImpreciseResolveResult {
    MATCH,
    NO_MATCH,
    UNSURE;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: compiled from: ImpreciseResolveResult.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 176)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ImpreciseResolveResult.values().length];
            try {
                iArr[ImpreciseResolveResult.MATCH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ImpreciseResolveResult.NO_MATCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ImpreciseResolveResult.UNSURE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static EnumEntries<ImpreciseResolveResult> getEntries() {
        return $ENTRIES;
    }

    public final void ifSure(Function1<? super Boolean, Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
        if (i == 1) {
            body.invoke(true);
        } else if (i == 2) {
            body.invoke(false);
        } else if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
    }
}
