package org.jetbrains.kotlin.fir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirResolvedImportImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J5\u0010$\u001a\u00020%\"\u0004\b\u0000\u0010&\"\u0004\b\u0001\u0010'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H&\u0012\u0004\u0012\u0002H'0)2\u0006\u0010*\u001a\u0002H'H\u0016¢\u0006\u0002\u0010+J)\u0010,\u001a\u00020\u0000\"\u0004\b\u0000\u0010'2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H'0.2\u0006\u0010*\u001a\u0002H'H\u0016¢\u0006\u0002\u0010/R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u000bR\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0016\u0010\"\u001a\u0004\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u000f¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedImportImpl;", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "relativeParentClassName", "(Lorg/jetbrains/kotlin/fir/declarations/FirImport;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/FqName;)V", "aliasName", "Lorg/jetbrains/kotlin/name/Name;", "getAliasName", "()Lorg/jetbrains/kotlin/name/Name;", "aliasSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "getAliasSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "setDelegate", "(Lorg/jetbrains/kotlin/fir/declarations/FirImport;)V", "importedFqName", "getImportedFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "importedName", "getImportedName", "isAllUnder", "", "()Z", "getPackageFqName", "getRelativeParentClassName", "resolvedParentClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getResolvedParentClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "getSource", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedImportImpl;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedImportImpl extends FirResolvedImport {
    private FirImport delegate;
    private final FqName packageFqName;
    private final FqName relativeParentClassName;

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirResolvedImportImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedImport
    public FirImport getDelegate() {
        return this.delegate;
    }

    public void setDelegate(FirImport firImport) {
        Intrinsics.checkNotNullParameter(firImport, "<set-?>");
        this.delegate = firImport;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedImport
    public FqName getPackageFqName() {
        return this.packageFqName;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedImport
    public FqName getRelativeParentClassName() {
        return this.relativeParentClassName;
    }

    public FirResolvedImportImpl(FirImport delegate, FqName packageFqName, FqName fqName) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        this.delegate = delegate;
        this.packageFqName = packageFqName;
        this.relativeParentClassName = fqName;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedImport, org.jetbrains.kotlin.fir.declarations.FirImport, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return getDelegate().getSource();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedImport, org.jetbrains.kotlin.fir.declarations.FirImport
    public FqName getImportedFqName() {
        return getDelegate().getImportedFqName();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedImport, org.jetbrains.kotlin.fir.declarations.FirImport
    /* JADX INFO: renamed from: isAllUnder */
    public boolean getIsAllUnder() {
        return getDelegate().getIsAllUnder();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedImport, org.jetbrains.kotlin.fir.declarations.FirImport
    public Name getAliasName() {
        return getDelegate().getAliasName();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedImport, org.jetbrains.kotlin.fir.declarations.FirImport
    public KtSourceElement getAliasSource() {
        return getDelegate().getAliasSource();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedImport
    public ClassId getResolvedParentClassId() {
        FqName relativeParentClassName = getRelativeParentClassName();
        if (relativeParentClassName != null) {
            return new ClassId(getPackageFqName(), relativeParentClassName, false);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedImport
    public Name getImportedName() {
        FqName importedFqName = getImportedFqName();
        if (importedFqName != null) {
            return importedFqName.shortName();
        }
        return null;
    }
}
