package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.types.AbstractTypePreparator;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ConeTypePreparator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0010\u0010\u0007\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeTypePreparator;", "Lorg/jetbrains/kotlin/types/AbstractTypePreparator;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "prepareType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypePreparator extends AbstractTypePreparator {
    private final FirSession session;

    public ConeTypePreparator(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    public final FirSession getSession() {
        return this.session;
    }

    private final ConeSimpleKotlinType prepareType(ConeSimpleKotlinType type) {
        return type instanceof ConeClassLikeType ? TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) type, this.session, null, 2, null) : type;
    }

    @Override // org.jetbrains.kotlin.types.AbstractTypePreparator
    public KotlinTypeMarker prepareType(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof ConeKotlinType)) {
            throw new AssertionError("Unexpected type in ConeTypePreparator: " + getClass());
        }
        ConeKotlinType coneKotlinType = (ConeKotlinType) type;
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) type;
            ConeSimpleKotlinType coneSimpleKotlinTypePrepareType = prepareType(coneFlexibleType.getLowerBound());
            return coneSimpleKotlinTypePrepareType == coneFlexibleType.getLowerBound() ? type : new ConeFlexibleType(coneSimpleKotlinTypePrepareType, prepareType(coneFlexibleType.getUpperBound()));
        }
        if (coneKotlinType instanceof ConeSimpleKotlinType) {
            return prepareType((ConeSimpleKotlinType) type);
        }
        throw new NoWhenBranchMatchedException();
    }
}
