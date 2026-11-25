package org.jetbrains.kotlin.fir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SyntheticCallableId.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/SyntheticCallableId;", "", "()V", "ACCEPT_SPECIFIC_TYPE", "Lorg/jetbrains/kotlin/name/CallableId;", "getACCEPT_SPECIFIC_TYPE", "()Lorg/jetbrains/kotlin/name/CallableId;", BuiltInOperatorNames.CHECK_NOT_NULL, "getCHECK_NOT_NULL", "ELVIS_NOT_NULL", "getELVIS_NOT_NULL", SchemaSymbols.ATTVAL_ID, "getID", "TRY", "getTRY", "WHEN", "getWHEN", "syntheticPackageName", "Lorg/jetbrains/kotlin/name/FqName;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntheticCallableId {
    private static final CallableId ACCEPT_SPECIFIC_TYPE;
    private static final CallableId CHECK_NOT_NULL;
    private static final CallableId ELVIS_NOT_NULL;
    private static final CallableId ID;
    public static final SyntheticCallableId INSTANCE = new SyntheticCallableId();
    private static final CallableId TRY;
    private static final CallableId WHEN;
    private static final FqName syntheticPackageName;

    private SyntheticCallableId() {
    }

    static {
        FqName fqName = new FqName("_synthetic");
        syntheticPackageName = fqName;
        Name nameIdentifier = Name.identifier("WHEN_CALL");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        WHEN = new CallableId(fqName, nameIdentifier);
        Name nameIdentifier2 = Name.identifier("TRY_CALL");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        TRY = new CallableId(fqName, nameIdentifier2);
        Name nameIdentifier3 = Name.identifier("CHECK_NOT_NULL_CALL");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        CHECK_NOT_NULL = new CallableId(fqName, nameIdentifier3);
        Name nameIdentifier4 = Name.identifier("ELVIS_CALL");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        ELVIS_NOT_NULL = new CallableId(fqName, nameIdentifier4);
        Name nameIdentifier5 = Name.identifier("ID_CALL");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        ID = new CallableId(fqName, nameIdentifier5);
        Name nameIdentifier6 = Name.identifier("ACCEPT_SPECIFIC_TYPE_CALL");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        ACCEPT_SPECIFIC_TYPE = new CallableId(fqName, nameIdentifier6);
    }

    public final CallableId getWHEN() {
        return WHEN;
    }

    public final CallableId getTRY() {
        return TRY;
    }

    public final CallableId getCHECK_NOT_NULL() {
        return CHECK_NOT_NULL;
    }

    public final CallableId getELVIS_NOT_NULL() {
        return ELVIS_NOT_NULL;
    }

    public final CallableId getID() {
        return ID;
    }

    public final CallableId getACCEPT_SPECIFIC_TYPE() {
        return ACCEPT_SPECIFIC_TYPE;
    }
}
