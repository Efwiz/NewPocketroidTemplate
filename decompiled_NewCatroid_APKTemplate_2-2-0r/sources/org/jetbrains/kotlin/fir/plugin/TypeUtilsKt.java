package org.jetbrains.kotlin.fir.plugin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: TypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"createConeType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/name/ClassId;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "nullable", "", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/fir/FirSession;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;Z)Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "plugin-utils"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeUtilsKt {
    public static final ConeClassLikeType createConeType(ClassId classId, FirSession session, ConeTypeProjection[] typeArguments, boolean z) {
        Intrinsics.checkNotNullParameter(classId, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(session).getClassLikeSymbolByClassId(classId);
        FirClassSymbol firClassSymbol = classLikeSymbolByClassId instanceof FirClassSymbol ? (FirClassSymbol) classLikeSymbolByClassId : null;
        if (firClassSymbol != null) {
            return typeArguments.length == 0 ? TypeConstructionUtilsKt.constructStarProjectedType$default(firClassSymbol, 0, z, 1, null) : TypeConstructionUtilsKt.constructType$default((FirClassLikeSymbol) firClassSymbol, typeArguments, z, (ConeAttributes) null, 4, (Object) null);
        }
        return new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(classId), typeArguments, z, null, 8, null);
    }

    public static /* synthetic */ ConeClassLikeType createConeType$default(ClassId classId, FirSession firSession, ConeTypeProjection[] coneTypeProjectionArr, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            coneTypeProjectionArr = new ConeTypeProjection[0];
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return createConeType(classId, firSession, coneTypeProjectionArr, z);
    }
}
