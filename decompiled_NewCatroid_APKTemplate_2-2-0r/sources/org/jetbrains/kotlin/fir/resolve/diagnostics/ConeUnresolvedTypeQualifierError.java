package org.jetbrains.kotlin.fir.resolve.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnosticWithNullability;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\r¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeUnresolvedTypeQualifierError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeUnresolvedError;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnosticWithNullability;", "qualifiers", "", "Lorg/jetbrains/kotlin/fir/types/FirQualifierPart;", "isNullable", "", "(Ljava/util/List;Z)V", "()Z", "qualifier", "", "getQualifier", "()Ljava/lang/String;", "getQualifiers", "()Ljava/util/List;", "reason", "getReason", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeUnresolvedTypeQualifierError implements ConeDiagnosticWithNullability, ConeUnresolvedError {
    private final boolean isNullable;
    private final List<FirQualifierPart> qualifiers;

    /* JADX WARN: Multi-variable type inference failed */
    public ConeUnresolvedTypeQualifierError(List<? extends FirQualifierPart> qualifiers, boolean z) {
        Intrinsics.checkNotNullParameter(qualifiers, "qualifiers");
        this.qualifiers = qualifiers;
        this.isNullable = z;
    }

    public final List<FirQualifierPart> getQualifiers() {
        return this.qualifiers;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnosticWithNullability
    /* JADX INFO: renamed from: isNullable, reason: from getter */
    public boolean getIsNullable() {
        return this.isNullable;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedError
    public String getQualifier() {
        return CollectionsKt.joinToString$default(this.qualifiers, ".", null, null, 0, null, new Function1<FirQualifierPart, CharSequence>() { // from class: org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedTypeQualifierError$qualifier$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(FirQualifierPart it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String strAsString = it.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return strAsString;
            }
        }, 30, null);
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Symbol not found for " + getQualifier() + (getIsNullable() ? TypeDescription.Generic.OfWildcardType.SYMBOL : "");
    }
}
