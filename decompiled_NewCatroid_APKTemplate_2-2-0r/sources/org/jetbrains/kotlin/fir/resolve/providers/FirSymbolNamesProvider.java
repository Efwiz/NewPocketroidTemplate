package org.jetbrains.kotlin.fir.resolve.providers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirSymbolNamesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bH&J\u0018\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\b2\u0006\u0010\f\u001a\u00020\rH&J\u0018\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000bH\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "", "()V", "mayHaveSyntheticFunctionTypes", "", "getMayHaveSyntheticFunctionTypes", "()Z", "getPackageNamesWithTopLevelCallables", "", "", "getTopLevelCallableNamesInPackage", "Lorg/jetbrains/kotlin/name/Name;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getTopLevelClassifierNamesInPackage", "mayHaveSyntheticFunctionType", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "mayHaveTopLevelCallable", "name", "mayHaveTopLevelClassifier", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSymbolNamesProvider {
    private final boolean mayHaveSyntheticFunctionTypes;

    public abstract Set<String> getPackageNamesWithTopLevelCallables();

    public abstract Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName);

    public abstract Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName);

    public boolean getMayHaveSyntheticFunctionTypes() {
        return this.mayHaveSyntheticFunctionTypes;
    }

    public boolean mayHaveSyntheticFunctionType(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        throw new IllegalStateException("`mayHaveSyntheticFunctionType` needs to be implemented when `mayHaveSyntheticFunctionTypes` is true.".toString());
    }

    public boolean mayHaveTopLevelClassifier(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (getMayHaveSyntheticFunctionTypes() && mayHaveSyntheticFunctionType(classId)) {
            return true;
        }
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        Set<String> topLevelClassifierNamesInPackage = getTopLevelClassifierNamesInPackage(packageFqName);
        if (topLevelClassifierNamesInPackage == null) {
            return true;
        }
        if (classId.getOuterClassId() == null) {
            Name shortClassName = classId.getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            if (!FirSymbolNamesProviderKt.mayContainTopLevelClassifier(topLevelClassifierNamesInPackage, shortClassName)) {
                return false;
            }
        } else {
            Name shortClassName2 = classId.getOutermostClassId().getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName2, "getShortClassName(...)");
            if (!FirSymbolNamesProviderKt.mayContainTopLevelClassifier(topLevelClassifierNamesInPackage, shortClassName2)) {
                return false;
            }
        }
        return true;
    }

    public boolean mayHaveTopLevelCallable(FqName packageFqName, Name name2) {
        Set<Name> topLevelCallableNamesInPackage;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (name2.isSpecial() || (topLevelCallableNamesInPackage = getTopLevelCallableNamesInPackage(packageFqName)) == null) {
            return true;
        }
        return topLevelCallableNamesInPackage.contains(name2);
    }
}
