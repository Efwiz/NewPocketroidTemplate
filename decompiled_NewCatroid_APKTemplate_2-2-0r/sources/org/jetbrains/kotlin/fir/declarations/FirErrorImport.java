package org.jetbrains.kotlin.fir.declarations;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirErrorImport.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u001c\u001a\u0002H\u001d\"\u0004\b\u0000\u0010\u001d\"\u0004\b\u0001\u0010\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u0002H\u001e0 2\u0006\u0010!\u001a\u0002H\u001eH\u0016¢\u0006\u0002\u0010\"J3\u0010#\u001a\u0002H$\"\b\b\u0000\u0010$*\u00020%\"\u0004\b\u0001\u0010\u001e2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u001e0'2\u0006\u0010!\u001a\u0002H\u001eH\u0016¢\u0006\u0002\u0010(R\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u0004\u0018\u00010\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000b¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirErrorImport;", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;", "()V", "aliasName", "Lorg/jetbrains/kotlin/name/Name;", "getAliasName", "()Lorg/jetbrains/kotlin/name/Name;", "aliasSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "getAliasSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "()Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "importedFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getImportedFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "isAllUnder", "", "()Z", FirebaseAnalytics.Param.SOURCE, "getSource", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirErrorImport extends FirImport implements FirDiagnosticHolder {
    @Override // org.jetbrains.kotlin.fir.declarations.FirImport
    public abstract Name getAliasName();

    @Override // org.jetbrains.kotlin.fir.declarations.FirImport
    public abstract KtSourceElement getAliasSource();

    public abstract FirImport getDelegate();

    @Override // org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder
    public abstract ConeDiagnostic getDiagnostic();

    @Override // org.jetbrains.kotlin.fir.declarations.FirImport
    public abstract FqName getImportedFqName();

    @Override // org.jetbrains.kotlin.fir.declarations.FirImport, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirImport
    public abstract boolean isAllUnder();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirImport, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitErrorImport(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirImport, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirImport firImportTransformErrorImport = transformer.transformErrorImport(this, data);
        Intrinsics.checkNotNull(firImportTransformErrorImport, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirErrorImport.transform");
        return firImportTransformErrorImport;
    }
}
