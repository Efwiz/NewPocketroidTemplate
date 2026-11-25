package org.jetbrains.kotlin.psi2ir;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Psi2IrConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/Psi2IrConfiguration;", "", "ignoreErrors", "", "partialLinkageEnabled", "skipBodies", "(ZZZ)V", "generateBodies", "getGenerateBodies", "()Z", "getIgnoreErrors", "getPartialLinkageEnabled", "getSkipBodies", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Psi2IrConfiguration {
    private final boolean ignoreErrors;
    private final boolean partialLinkageEnabled;
    private final boolean skipBodies;

    public Psi2IrConfiguration() {
        this(false, false, false, 7, null);
    }

    public Psi2IrConfiguration(boolean z, boolean z2, boolean z3) {
        this.ignoreErrors = z;
        this.partialLinkageEnabled = z2;
        this.skipBodies = z3;
    }

    public /* synthetic */ Psi2IrConfiguration(boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? false : z3);
    }

    public final boolean getIgnoreErrors() {
        return this.ignoreErrors;
    }

    public final boolean getPartialLinkageEnabled() {
        return this.partialLinkageEnabled;
    }

    public final boolean getSkipBodies() {
        return this.skipBodies;
    }

    public final boolean getGenerateBodies() {
        return !this.skipBodies;
    }
}
