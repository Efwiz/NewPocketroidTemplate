package org.jetbrains.kotlin.fir.references.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirExplicitSuperReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010\u0011\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u0013\"\u0004\b\u0001\u0010\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u00140\u00162\u0006\u0010\u0017\u001a\u0002H\u0014H\u0016¢\u0006\u0002\u0010\u0018J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0016J)\u0010\u001b\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00140\u001d2\u0006\u0010\u0017\u001a\u0002H\u0014H\u0016¢\u0006\u0002\u0010\u001eR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/references/impl/FirExplicitSuperReference;", "Lorg/jetbrains/kotlin/fir/references/FirSuperReference;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "labelName", "", "superTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/lang/String;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "getLabelName", "()Ljava/lang/String;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getSuperTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setSuperTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceSuperTypeRef", "newSuperTypeRef", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/impl/FirExplicitSuperReference;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExplicitSuperReference extends FirSuperReference {
    private final String labelName;
    private final KtSourceElement source;
    private FirTypeRef superTypeRef;

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.references.FirSuperReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirSuperReference
    public String getLabelName() {
        return this.labelName;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirSuperReference
    public FirTypeRef getSuperTypeRef() {
        return this.superTypeRef;
    }

    public void setSuperTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.superTypeRef = firTypeRef;
    }

    public FirExplicitSuperReference(KtSourceElement ktSourceElement, String str, FirTypeRef superTypeRef) {
        Intrinsics.checkNotNullParameter(superTypeRef, "superTypeRef");
        this.source = ktSourceElement;
        this.labelName = str;
        this.superTypeRef = superTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getSuperTypeRef().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirExplicitSuperReference transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setSuperTypeRef((FirTypeRef) getSuperTypeRef().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirSuperReference
    public void replaceSuperTypeRef(FirTypeRef newSuperTypeRef) {
        Intrinsics.checkNotNullParameter(newSuperTypeRef, "newSuperTypeRef");
        setSuperTypeRef(newSuperTypeRef);
    }
}
