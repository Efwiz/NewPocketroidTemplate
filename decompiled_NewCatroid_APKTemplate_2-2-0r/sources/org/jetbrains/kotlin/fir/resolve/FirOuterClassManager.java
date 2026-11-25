package org.jetbrains.kotlin.fir.resolve;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirOuterClassManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u001a\u0010\u0004\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00062\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u0006J\u0010\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\rR\"\u0010\u0004\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirOuterClassManager;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "outerLocalClassForNested", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "(Lorg/jetbrains/kotlin/fir/FirSession;Ljava/util/Map;)V", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "outerClass", "classSymbol", "outerType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "classLikeType", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOuterClassManager {
    private final Map<FirClassLikeSymbol<?>, FirClassLikeSymbol<?>> outerLocalClassForNested;
    private final FirSession session;
    private final FirSymbolProvider symbolProvider;

    /* JADX WARN: Multi-variable type inference failed */
    public FirOuterClassManager(FirSession session, Map<FirClassLikeSymbol<?>, ? extends FirClassLikeSymbol<?>> outerLocalClassForNested) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(outerLocalClassForNested, "outerLocalClassForNested");
        this.session = session;
        this.outerLocalClassForNested = outerLocalClassForNested;
        this.symbolProvider = FirSymbolProviderKt.getSymbolProvider(session);
    }

    public final FirClassLikeSymbol<?> outerClass(FirClassLikeSymbol<?> classSymbol) {
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        if (!(classSymbol instanceof FirClassSymbol)) {
            return null;
        }
        ClassId classId = classSymbol.getClassId();
        if (classId.isLocal()) {
            return this.outerLocalClassForNested.get(classSymbol);
        }
        ClassId outerClassId = classId.getOuterClassId();
        if (outerClassId == null) {
            return null;
        }
        return this.symbolProvider.getClassLikeSymbolByClassId(outerClassId);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x007c  */
    /* JADX WARN: Multi-variable type inference failed */
    public final ConeClassLikeType outerType(ConeClassLikeType classLikeType) {
        FirClassLikeSymbol<?> firClassLikeSymbolOuterClass;
        int i;
        FirRegularClass firRegularClass;
        List<FirTypeParameterRef> typeParameters;
        Intrinsics.checkNotNullParameter(classLikeType, "classLikeType");
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(classLikeType, this.session, null, 2, null);
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeTypeFullyExpandedType$default.getLookupTag(), this.session);
        if (symbol == null) {
            return null;
        }
        boolean z = symbol instanceof FirRegularClassSymbol;
        if ((z && !((FirMemberDeclaration) ((FirRegularClassSymbol) symbol).getFir()).getStatus().isInner()) || (firClassLikeSymbolOuterClass = outerClass(symbol)) == null) {
            return null;
        }
        FirRegularClassSymbol firRegularClassSymbol = z ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null || (firRegularClass = (FirRegularClass) firRegularClassSymbol.getFir()) == null || (typeParameters = firRegularClass.getTypeParameters()) == null) {
            i = 0;
        } else {
            List<FirTypeParameterRef> list = typeParameters;
            if ((list instanceof Collection) && list.isEmpty()) {
                i = 0;
            } else {
                Iterator<T> it = list.iterator();
                i = 0;
                while (it.hasNext()) {
                    if ((((FirTypeParameterRef) it.next()) instanceof FirTypeParameter) && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
        }
        return TypeConstructionUtilsKt.constructType$default((FirClassLikeSymbol) firClassLikeSymbolOuterClass, (ConeTypeProjection[]) ArraysKt.drop(coneClassLikeTypeFullyExpandedType$default.getTypeArguments(), i).toArray(new ConeTypeProjection[0]), false, (ConeAttributes) null, 4, (Object) null);
    }
}
