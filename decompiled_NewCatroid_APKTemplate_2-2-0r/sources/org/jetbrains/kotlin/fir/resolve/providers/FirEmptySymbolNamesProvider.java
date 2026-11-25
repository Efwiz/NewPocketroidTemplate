package org.jetbrains.kotlin.fir.resolve.providers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirSymbolNamesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0007H\u0016J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/FirEmptySymbolNamesProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "()V", "getPackageNamesWithTopLevelCallables", "", "", "getTopLevelCallableNamesInPackage", "Lorg/jetbrains/kotlin/name/Name;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getTopLevelClassifierNamesInPackage", "mayHaveTopLevelCallable", "", "name", "mayHaveTopLevelClassifier", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEmptySymbolNamesProvider extends FirSymbolNamesProvider {
    public static final FirEmptySymbolNamesProvider INSTANCE = new FirEmptySymbolNamesProvider();

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public boolean mayHaveTopLevelCallable(FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public boolean mayHaveTopLevelClassifier(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return false;
    }

    private FirEmptySymbolNamesProvider() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public Set<String> getPackageNamesWithTopLevelCallables() {
        return SetsKt.emptySet();
    }
}
