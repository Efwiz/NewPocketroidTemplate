package org.jetbrains.kotlin.fir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirErrorImport;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirErrorImportImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010\u001e\u001a\u00020\u001f\"\u0004\b\u0000\u0010 \"\u0004\b\u0001\u0010!2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u0002H \u0012\u0004\u0012\u0002H!0#2\u0006\u0010$\u001a\u0002H!H\u0016¢\u0006\u0002\u0010%J)\u0010&\u001a\u00020\u0000\"\u0004\b\u0000\u0010!2\f\u0010'\u001a\b\u0012\u0004\u0012\u0002H!0(2\u0006\u0010$\u001a\u0002H!H\u0016¢\u0006\u0002\u0010)R\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001bR\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u000e¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirErrorImportImpl;", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorImport;", "aliasSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;Lorg/jetbrains/kotlin/fir/declarations/FirImport;)V", "aliasName", "Lorg/jetbrains/kotlin/name/Name;", "getAliasName", "()Lorg/jetbrains/kotlin/name/Name;", "getAliasSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "setDelegate", "(Lorg/jetbrains/kotlin/fir/declarations/FirImport;)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "importedFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getImportedFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "isAllUnder", "", "()Z", FirebaseAnalytics.Param.SOURCE, "getSource", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirErrorImportImpl;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirErrorImportImpl extends FirErrorImport {
    private final KtSourceElement aliasSource;
    private FirImport delegate;
    private final ConeDiagnostic diagnostic;

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorImport, org.jetbrains.kotlin.fir.declarations.FirImport
    public KtSourceElement getAliasSource() {
        return this.aliasSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorImport, org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder
    public ConeDiagnostic getDiagnostic() {
        return this.diagnostic;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorImport
    public FirImport getDelegate() {
        return this.delegate;
    }

    public void setDelegate(FirImport firImport) {
        Intrinsics.checkNotNullParameter(firImport, "<set-?>");
        this.delegate = firImport;
    }

    public FirErrorImportImpl(KtSourceElement ktSourceElement, ConeDiagnostic diagnostic, FirImport delegate) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.aliasSource = ktSourceElement;
        this.diagnostic = diagnostic;
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorImport, org.jetbrains.kotlin.fir.declarations.FirImport, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return getDelegate().getSource();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorImport, org.jetbrains.kotlin.fir.declarations.FirImport
    public FqName getImportedFqName() {
        return getDelegate().getImportedFqName();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorImport, org.jetbrains.kotlin.fir.declarations.FirImport
    /* JADX INFO: renamed from: isAllUnder */
    public boolean getIsAllUnder() {
        return getDelegate().getIsAllUnder();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorImport, org.jetbrains.kotlin.fir.declarations.FirImport
    public Name getAliasName() {
        return getDelegate().getAliasName();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getDelegate().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirErrorImportImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setDelegate((FirImport) getDelegate().transform(transformer, data));
        return this;
    }
}
