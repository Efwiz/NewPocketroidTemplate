package org.jetbrains.kotlin.fir.resolve.providers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirCachedSymbolNamesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fH\u0016J\u0018\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/FirDelegatingCachedSymbolNamesProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirCachedSymbolNamesProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;)V", "mayHaveSyntheticFunctionTypes", "", "getMayHaveSyntheticFunctionTypes", "()Z", "computePackageNamesWithTopLevelCallables", "", "", "computeTopLevelCallableNames", "Lorg/jetbrains/kotlin/name/Name;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "computeTopLevelClassifierNames", "mayHaveSyntheticFunctionType", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegatingCachedSymbolNamesProvider extends FirCachedSymbolNamesProvider {
    private final FirSymbolNamesProvider delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirDelegatingCachedSymbolNamesProvider(FirSession session, FirSymbolNamesProvider delegate) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
    public Set<String> computeTopLevelClassifierNames(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return this.delegate.getTopLevelClassifierNamesInPackage(packageFqName);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
    public Set<String> computePackageNamesWithTopLevelCallables() {
        return this.delegate.getPackageNamesWithTopLevelCallables();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
    public Set<Name> computeTopLevelCallableNames(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return this.delegate.getTopLevelCallableNamesInPackage(packageFqName);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public boolean getMayHaveSyntheticFunctionTypes() {
        return this.delegate.getMayHaveSyntheticFunctionTypes();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public boolean mayHaveSyntheticFunctionType(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return this.delegate.mayHaveSyntheticFunctionType(classId);
    }
}
