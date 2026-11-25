package org.jetbrains.kotlin.fir.declarations.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedImportImpl;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirResolvedImportBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0012\u001a\u00020\u0013R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirResolvedImportBuilder;", "", "()V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "setDelegate", "(Lorg/jetbrains/kotlin/fir/declarations/FirImport;)V", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "setPackageFqName", "(Lorg/jetbrains/kotlin/name/FqName;)V", "relativeParentClassName", "getRelativeParentClassName", "setRelativeParentClassName", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedImportBuilder {
    public FirImport delegate;
    public FqName packageFqName;
    private FqName relativeParentClassName;

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

    public final FqName getPackageFqName() {
        FqName fqName = this.packageFqName;
        if (fqName != null) {
            return fqName;
        }
        Intrinsics.throwUninitializedPropertyAccessException("packageFqName");
        return null;
    }

    public final void setPackageFqName(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<set-?>");
        this.packageFqName = fqName;
    }

    public final FqName getRelativeParentClassName() {
        return this.relativeParentClassName;
    }

    public final void setRelativeParentClassName(FqName fqName) {
        this.relativeParentClassName = fqName;
    }

    public final FirResolvedImport build() {
        return new FirResolvedImportImpl(getDelegate(), getPackageFqName(), this.relativeParentClassName);
    }
}
