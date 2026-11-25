package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedParentInImport;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ImportUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"resolveToPackageOrClass", "Lorg/jetbrains/kotlin/fir/resolve/transformers/PackageResolutionResult;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ImportUtilsKt {
    public static final PackageResolutionResult resolveToPackageOrClass(FirSymbolProvider symbolProvider, FqName fqName) {
        Intrinsics.checkNotNullParameter(symbolProvider, "symbolProvider");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        List<Name> listPathSegments = fqName.pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        int size = listPathSegments.size();
        FqName fqNameParent = fqName;
        while (!fqNameParent.isRoot() && size > 0 && symbolProvider.getPackage(fqNameParent) == null) {
            fqNameParent = fqNameParent.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            size--;
        }
        if (Intrinsics.areEqual(fqNameParent, fqName)) {
            return new PackageResolutionResult.PackageOrClass(fqNameParent, null, null);
        }
        IntRange intRangeUntil = RangesKt.until(size, listPathSegments.size());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it = intRangeUntil.iterator();
        while (it.hasNext()) {
            arrayList.add(listPathSegments.get(((IntIterator) it).nextInt()).asString());
        }
        FqName fqNameFromSegments = FqName.fromSegments(arrayList);
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
        ClassId classId = new ClassId(fqNameParent, fqNameFromSegments, false);
        FirClassLikeSymbol<?> classLikeSymbolByClassId = symbolProvider.getClassLikeSymbolByClassId(classId);
        if (classLikeSymbolByClassId == null) {
            return new PackageResolutionResult.Error(new ConeUnresolvedParentInImport(classId));
        }
        return new PackageResolutionResult.PackageOrClass(fqNameParent, fqNameFromSegments, classLikeSymbolByClassId);
    }
}
