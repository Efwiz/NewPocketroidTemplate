package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ResolvedUsingDeprecatedVisibility;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "baseSourceScope", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "lookupLocation", "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "(Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;Lorg/jetbrains/kotlin/incremental/components/LookupLocation;)V", "getBaseSourceScope", "()Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "getLookupLocation", "()Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedUsingDeprecatedVisibility extends ResolutionDiagnostic {
    private final ResolutionScope baseSourceScope;
    private final LookupLocation lookupLocation;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResolvedUsingDeprecatedVisibility(ResolutionScope baseSourceScope, LookupLocation lookupLocation) {
        super(CandidateApplicability.RESOLVED);
        Intrinsics.checkNotNullParameter(baseSourceScope, "baseSourceScope");
        Intrinsics.checkNotNullParameter(lookupLocation, "lookupLocation");
        this.baseSourceScope = baseSourceScope;
        this.lookupLocation = lookupLocation;
    }

    public final ResolutionScope getBaseSourceScope() {
        return this.baseSourceScope;
    }

    public final LookupLocation getLookupLocation() {
        return this.lookupLocation;
    }
}
