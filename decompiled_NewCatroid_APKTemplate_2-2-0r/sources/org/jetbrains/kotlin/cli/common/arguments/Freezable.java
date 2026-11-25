package org.jetbrains.kotlin.cli.common.arguments;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: compiled from: Freezable.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0004J\b\u0010\u0007\u001a\u00020\u0000H\u0014J\r\u0010\b\u001a\u00020\u0000H\u0000¢\u0006\u0002\b\tJ\b\u0010\u0003\u001a\u00020\u0000H\u0007J\u0015\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\fJ\b\u0010\r\u001a\u00020\u0000H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;", "", "()V", "frozen", "", "checkFrozen", "", "copyOf", "copyOfInternal", "copyOfInternal$cli_common", "getInstanceWithFreezeStatus", "value", "getInstanceWithFreezeStatus$cli_common", "unfrozen", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Freezable {
    private boolean frozen;

    protected final void checkFrozen() {
        if (this.frozen) {
            throw new IllegalStateException("Instance of " + Reflection.getOrCreateKotlinClass(getClass()) + " is frozen");
        }
    }

    protected Freezable copyOf() {
        return (Freezable) ArgumentUtilsKt.copyBean(this);
    }

    public final Freezable copyOfInternal$cli_common() {
        return copyOf();
    }

    public final Freezable getInstanceWithFreezeStatus$cli_common(boolean value) {
        if (value == this.frozen) {
            return this;
        }
        Freezable freezableCopyOf = copyOf();
        freezableCopyOf.frozen = value;
        return freezableCopyOf;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Please use type safe extension functions")
    public final /* synthetic */ Freezable frozen() {
        return getInstanceWithFreezeStatus$cli_common(true);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Please use type safe extension functions")
    public final /* synthetic */ Freezable unfrozen() {
        return getInstanceWithFreezeStatus$cli_common(false);
    }
}
