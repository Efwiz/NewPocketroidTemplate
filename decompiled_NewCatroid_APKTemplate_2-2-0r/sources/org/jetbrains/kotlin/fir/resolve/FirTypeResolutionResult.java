package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirTypeResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirTypeResolutionResult;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FirTypeResolutionResult {
    private final ConeDiagnostic diagnostic;
    private final ConeKotlinType type;

    public static /* synthetic */ FirTypeResolutionResult copy$default(FirTypeResolutionResult firTypeResolutionResult, ConeKotlinType coneKotlinType, ConeDiagnostic coneDiagnostic, int i, Object obj) {
        if ((i & 1) != 0) {
            coneKotlinType = firTypeResolutionResult.type;
        }
        if ((i & 2) != 0) {
            coneDiagnostic = firTypeResolutionResult.diagnostic;
        }
        return firTypeResolutionResult.copy(coneKotlinType, coneDiagnostic);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConeKotlinType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ConeDiagnostic getDiagnostic() {
        return this.diagnostic;
    }

    public final FirTypeResolutionResult copy(ConeKotlinType type, ConeDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new FirTypeResolutionResult(type, diagnostic);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FirTypeResolutionResult)) {
            return false;
        }
        FirTypeResolutionResult firTypeResolutionResult = (FirTypeResolutionResult) other;
        return Intrinsics.areEqual(this.type, firTypeResolutionResult.type) && Intrinsics.areEqual(this.diagnostic, firTypeResolutionResult.diagnostic);
    }

    public int hashCode() {
        int iHashCode = this.type.hashCode() * 31;
        ConeDiagnostic coneDiagnostic = this.diagnostic;
        return iHashCode + (coneDiagnostic == null ? 0 : coneDiagnostic.hashCode());
    }

    public String toString() {
        return "FirTypeResolutionResult(type=" + this.type + ", diagnostic=" + this.diagnostic + ')';
    }

    public FirTypeResolutionResult(ConeKotlinType type, ConeDiagnostic coneDiagnostic) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.diagnostic = coneDiagnostic;
    }

    public final ConeKotlinType getType() {
        return this.type;
    }

    public final ConeDiagnostic getDiagnostic() {
        return this.diagnostic;
    }
}
