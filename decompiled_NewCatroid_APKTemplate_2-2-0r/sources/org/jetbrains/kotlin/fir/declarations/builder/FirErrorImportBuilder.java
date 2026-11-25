package org.jetbrains.kotlin.fir.declarations.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.FirErrorImport;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.impl.FirErrorImportImpl;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;

/* JADX INFO: compiled from: FirErrorImportBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0015\u001a\u00020\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirErrorImportBuilder;", "", "()V", "aliasSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "getAliasSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setAliasSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "setDelegate", "(Lorg/jetbrains/kotlin/fir/declarations/FirImport;)V", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "setDiagnostic", "(Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirErrorImport;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirErrorImportBuilder {
    private KtSourceElement aliasSource;
    public FirImport delegate;
    public ConeDiagnostic diagnostic;

    public final KtSourceElement getAliasSource() {
        return this.aliasSource;
    }

    public final void setAliasSource(KtSourceElement ktSourceElement) {
        this.aliasSource = ktSourceElement;
    }

    public final ConeDiagnostic getDiagnostic() {
        ConeDiagnostic coneDiagnostic = this.diagnostic;
        if (coneDiagnostic != null) {
            return coneDiagnostic;
        }
        Intrinsics.throwUninitializedPropertyAccessException("diagnostic");
        return null;
    }

    public final void setDiagnostic(ConeDiagnostic coneDiagnostic) {
        Intrinsics.checkNotNullParameter(coneDiagnostic, "<set-?>");
        this.diagnostic = coneDiagnostic;
    }

    public final FirImport getDelegate() {
        FirImport firImport = this.delegate;
        if (firImport != null) {
            return firImport;
        }
        Intrinsics.throwUninitializedPropertyAccessException(MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX);
        return null;
    }

    public final void setDelegate(FirImport firImport) {
        Intrinsics.checkNotNullParameter(firImport, "<set-?>");
        this.delegate = firImport;
    }

    public final FirErrorImport build() {
        return new FirErrorImportImpl(this.aliasSource, getDiagnostic(), getDelegate());
    }
}
