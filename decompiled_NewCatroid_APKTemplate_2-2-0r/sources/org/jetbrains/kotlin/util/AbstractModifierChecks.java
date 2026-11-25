package org.jetbrains.kotlin.util;

import androidx.core.app.NotificationCompat;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;

/* JADX INFO: compiled from: modifierChecks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ$\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011H\u0086\bø\u0001\u0000R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X \u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/util/AbstractModifierChecks;", "", "()V", "checks", "", "Lorg/jetbrains/kotlin/util/Checks;", "getChecks$descriptors", "()Ljava/util/List;", "check", "Lorg/jetbrains/kotlin/util/CheckResult;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "ensure", "", "cond", "", NotificationCompat.CATEGORY_MESSAGE, "Lkotlin/Function0;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractModifierChecks {
    public abstract List<Checks> getChecks$descriptors();

    public final String ensure(boolean cond, Function0<String> msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (cond) {
            return null;
        }
        return msg.invoke();
    }

    public final CheckResult check(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        for (Checks checks : getChecks$descriptors()) {
            if (checks.isApplicable(functionDescriptor)) {
                return checks.checkAll(functionDescriptor);
            }
        }
        return CheckResult.IllegalFunctionName.INSTANCE;
    }
}
