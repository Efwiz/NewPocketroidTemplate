package org.jetbrains.kotlin.utils;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SmartPrinter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0005"}, d2 = {"withIndent", "", "Lorg/jetbrains/kotlin/utils/SmartPrinter;", "block", "Lkotlin/Function0;", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SmartPrinterKt {
    public static final void withIndent(SmartPrinter smartPrinter, Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(smartPrinter, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        smartPrinter.pushIndent();
        block.invoke();
        smartPrinter.popIndent();
    }
}
