package org.jetbrains.kotlin.fir.declarations;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirResolvedImport.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010!\u001a\u0002H\"\"\u0004\b\u0000\u0010\"\"\u0004\b\u0001\u0010#2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u0002H\"\u0012\u0004\u0012\u0002H#0%2\u0006\u0010&\u001a\u0002H#H\u0016¢\u0006\u0002\u0010'J3\u0010(\u001a\u0002H)\"\b\b\u0000\u0010)*\u00020*\"\u0004\b\u0001\u0010#2\f\u0010+\u001a\b\u0012\u0004\u0012\u0002H#0,2\u0006\u0010&\u001a\u0002H#H\u0016¢\u0006\u0002\u0010-R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0006R\u0012\u0010\u0014\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0011R\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0011R\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u001cX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u0004\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010\n¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "()V", "aliasName", "Lorg/jetbrains/kotlin/name/Name;", "getAliasName", "()Lorg/jetbrains/kotlin/name/Name;", "aliasSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "getAliasSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "()Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "importedFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getImportedFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "importedName", "getImportedName", "isAllUnder", "", "()Z", "packageFqName", "getPackageFqName", "relativeParentClassName", "getRelativeParentClassName", "resolvedParentClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getResolvedParentClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "getSource", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirResolvedImport extends FirImport {
    @Override // org.jetbrains.kotlin.fir.declarations.FirImport
    public abstract Name getAliasName();

    @Override // org.jetbrains.kotlin.fir.declarations.FirImport
    public abstract KtSourceElement getAliasSource();

    public abstract FirImport getDelegate();

    @Override // org.jetbrains.kotlin.fir.declarations.FirImport
    public abstract FqName getImportedFqName();

    public abstract Name getImportedName();

    public abstract FqName getPackageFqName();

    public abstract FqName getRelativeParentClassName();

    public abstract ClassId getResolvedParentClassId();

    @Override // org.jetbrains.kotlin.fir.declarations.FirImport, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirImport
    public abstract boolean isAllUnder();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirImport, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitResolvedImport(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirImport, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirImport firImportTransformResolvedImport = transformer.transformResolvedImport(this, data);
        Intrinsics.checkNotNull(firImportTransformResolvedImport, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirResolvedImport.transform");
        return firImportTransformResolvedImport;
    }
}
