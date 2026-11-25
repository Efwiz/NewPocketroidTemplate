package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: TypeConstructionUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a1\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\n\u001a1\u0010\u000b\u001a\u00020\u0001*\u00020\f2\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\r\u001a\"\u0010\u000e\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u001a1\u0010\u0012\u001a\u00020\u0013*\u00020\u00142\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\u0015\u001a3\u0010\u0012\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00162\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\u0017\u001a3\u0010\u0012\u001a\u00020\u0013*\u0006\u0012\u0002\b\u00030\u00182\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\u0019\u001a\n\u0010\u001a\u001a\u00020\u001b*\u00020\u0002¨\u0006\u001c"}, d2 = {"constructClassLikeType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/name/ClassId;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "isNullable", "", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "(Lorg/jetbrains/kotlin/name/ClassId;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;ZLorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "constructClassType", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "(Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;ZLorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "constructStarProjectedType", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "typeParameterNumber", "", "constructType", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;", "(Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;ZLorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;ZLorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;ZLorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "toLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/impl/ConeClassLikeLookupTagImpl;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeConstructionUtilsKt {
    public static /* synthetic */ ConeLookupTagBasedType constructType$default(ConeClassifierLookupTag coneClassifierLookupTag, ConeTypeProjection[] coneTypeProjectionArr, boolean z, ConeAttributes coneAttributes, int i, Object obj) {
        if ((i & 4) != 0) {
            coneAttributes = ConeAttributes.INSTANCE.getEmpty();
        }
        return constructType(coneClassifierLookupTag, coneTypeProjectionArr, z, coneAttributes);
    }

    public static final ConeLookupTagBasedType constructType(ConeClassifierLookupTag coneClassifierLookupTag, ConeTypeProjection[] typeArguments, boolean z, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(coneClassifierLookupTag, "<this>");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if (coneClassifierLookupTag instanceof ConeTypeParameterLookupTag) {
            return new ConeTypeParameterTypeImpl((ConeTypeParameterLookupTag) coneClassifierLookupTag, z, attributes);
        }
        if (coneClassifierLookupTag instanceof ConeClassLikeLookupTag) {
            return constructClassType((ConeClassLikeLookupTag) coneClassifierLookupTag, typeArguments, z, attributes);
        }
        throw new IllegalStateException(("! " + Reflection.getOrCreateKotlinClass(coneClassifierLookupTag.getClass())).toString());
    }

    public static /* synthetic */ ConeClassLikeType constructClassType$default(ConeClassLikeLookupTag coneClassLikeLookupTag, ConeTypeProjection[] coneTypeProjectionArr, boolean z, ConeAttributes coneAttributes, int i, Object obj) {
        if ((i & 4) != 0) {
            coneAttributes = ConeAttributes.INSTANCE.getEmpty();
        }
        return constructClassType(coneClassLikeLookupTag, coneTypeProjectionArr, z, coneAttributes);
    }

    public static final ConeClassLikeType constructClassType(ConeClassLikeLookupTag coneClassLikeLookupTag, ConeTypeProjection[] typeArguments, boolean z, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(coneClassLikeLookupTag, "<this>");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        return new ConeClassLikeTypeImpl(coneClassLikeLookupTag, typeArguments, z, attributes);
    }

    public static final ConeClassLikeLookupTagImpl toLookupTag(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "<this>");
        return new ConeClassLikeLookupTagImpl(classId);
    }

    public static /* synthetic */ ConeClassLikeType constructClassLikeType$default(ClassId classId, ConeTypeProjection[] coneTypeProjectionArr, boolean z, ConeAttributes coneAttributes, int i, Object obj) {
        if ((i & 4) != 0) {
            coneAttributes = ConeAttributes.INSTANCE.getEmpty();
        }
        return constructClassLikeType(classId, coneTypeProjectionArr, z, coneAttributes);
    }

    public static final ConeClassLikeType constructClassLikeType(ClassId classId, ConeTypeProjection[] typeArguments, boolean z, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(classId, "<this>");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        return new ConeClassLikeTypeImpl(toLookupTag(classId), typeArguments, z, attributes);
    }

    public static /* synthetic */ ConeLookupTagBasedType constructType$default(FirClassifierSymbol firClassifierSymbol, ConeTypeProjection[] coneTypeProjectionArr, boolean z, ConeAttributes coneAttributes, int i, Object obj) {
        if ((i & 4) != 0) {
            coneAttributes = ConeAttributes.INSTANCE.getEmpty();
        }
        return constructType((FirClassifierSymbol<?>) firClassifierSymbol, coneTypeProjectionArr, z, coneAttributes);
    }

    public static final ConeLookupTagBasedType constructType(FirClassifierSymbol<?> firClassifierSymbol, ConeTypeProjection[] typeArguments, boolean z, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(firClassifierSymbol, "<this>");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if (firClassifierSymbol instanceof FirTypeParameterSymbol) {
            return new ConeTypeParameterTypeImpl(((FirTypeParameterSymbol) firClassifierSymbol).getLookupTag(), z, attributes);
        }
        if (firClassifierSymbol instanceof FirClassLikeSymbol) {
            return constructType((FirClassLikeSymbol<?>) firClassifierSymbol, typeArguments, z, attributes);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static /* synthetic */ ConeClassLikeType constructType$default(FirClassLikeSymbol firClassLikeSymbol, ConeTypeProjection[] coneTypeProjectionArr, boolean z, ConeAttributes coneAttributes, int i, Object obj) {
        if ((i & 4) != 0) {
            coneAttributes = ConeAttributes.INSTANCE.getEmpty();
        }
        return constructType((FirClassLikeSymbol<?>) firClassLikeSymbol, coneTypeProjectionArr, z, coneAttributes);
    }

    public static final ConeClassLikeType constructType(FirClassLikeSymbol<?> firClassLikeSymbol, ConeTypeProjection[] typeArguments, boolean z, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        return new ConeClassLikeTypeImpl(firClassLikeSymbol.getLookupTag(), typeArguments, z, attributes);
    }

    public static /* synthetic */ ConeClassLikeType constructStarProjectedType$default(FirClassSymbol firClassSymbol, int i, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = firClassSymbol.getTypeParameterSymbols().size();
        }
        if ((i2 & 2) != 0) {
            z = false;
        }
        return constructStarProjectedType(firClassSymbol, i, z);
    }

    public static final ConeClassLikeType constructStarProjectedType(FirClassSymbol<?> firClassSymbol, int i, boolean z) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        ConeClassLikeLookupTag lookupTag = firClassSymbol.getLookupTag();
        ConeStarProjection[] coneStarProjectionArr = new ConeStarProjection[i];
        for (int i2 = 0; i2 < i; i2++) {
            coneStarProjectionArr[i2] = ConeStarProjection.INSTANCE;
        }
        return new ConeClassLikeTypeImpl(lookupTag, coneStarProjectionArr, z, null, 8, null);
    }
}
