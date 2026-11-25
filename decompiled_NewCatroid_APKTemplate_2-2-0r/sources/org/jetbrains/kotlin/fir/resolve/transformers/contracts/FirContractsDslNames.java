package org.jetbrains.kotlin.fir.resolve.transformers.contracts;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirContractsDslNames.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0018\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u000eH\u0002J\u0010\u0010 \u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u000eH\u0002J\u0018\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0002J'\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u000e2\b\u0010\"\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0000¢\u0006\u0002\b#J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u000eH\u0002J\u0010\u0010%\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u000eH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u000e\u0010\r\u001a\u00020\u000eX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0006R\u0011\u0010\u0012\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0006R\u0011\u0010\u0014\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0006R\u0011\u0010\u0016\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0006R\u0011\u0010\u0018\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0006R\u0011\u0010\u001a\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0006R\u0011\u0010\u001c\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0006¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirContractsDslNames;", "", "()V", "AT_LEAST_ONCE_KIND", "Lorg/jetbrains/kotlin/name/CallableId;", "getAT_LEAST_ONCE_KIND", "()Lorg/jetbrains/kotlin/name/CallableId;", "AT_MOST_ONCE_KIND", "getAT_MOST_ONCE_KIND", "CALLS_IN_PLACE", "getCALLS_IN_PLACE", "CONTRACT", "getCONTRACT", "CONTRACT_BUILDER", "", "CONTRACT_PACKAGE", "EXACTLY_ONCE_KIND", "getEXACTLY_ONCE_KIND", "IMPLIES", "getIMPLIES", "INVOCATION_KIND_ENUM", "getINVOCATION_KIND_ENUM", "RETURNS", "getRETURNS", "RETURNS_NOT_NULL", "getRETURNS_NOT_NULL", "SIMPLE_EFFECT", "getSIMPLE_EFFECT", "UNKNOWN_KIND", "getUNKNOWN_KIND", "contractBuilder", "name", "id", "packageName", "className", "id$resolve", "invocationKind", "simpleEffect", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirContractsDslNames {
    private static final CallableId AT_LEAST_ONCE_KIND;
    private static final CallableId AT_MOST_ONCE_KIND;
    private static final CallableId CALLS_IN_PLACE;
    private static final CallableId CONTRACT;
    private static final CallableId EXACTLY_ONCE_KIND;
    private static final CallableId IMPLIES;
    public static final FirContractsDslNames INSTANCE;
    private static final CallableId INVOCATION_KIND_ENUM;
    private static final CallableId RETURNS;
    private static final CallableId RETURNS_NOT_NULL;
    private static final CallableId SIMPLE_EFFECT;
    private static final CallableId UNKNOWN_KIND;

    private FirContractsDslNames() {
    }

    static {
        FirContractsDslNames firContractsDslNames = new FirContractsDslNames();
        INSTANCE = firContractsDslNames;
        SIMPLE_EFFECT = firContractsDslNames.id("SimpleEffect");
        CONTRACT = firContractsDslNames.id("contract");
        IMPLIES = firContractsDslNames.simpleEffect("implies");
        RETURNS = firContractsDslNames.contractBuilder("returns");
        RETURNS_NOT_NULL = firContractsDslNames.contractBuilder("returnsNotNull");
        CALLS_IN_PLACE = firContractsDslNames.contractBuilder("callsInPlace");
        INVOCATION_KIND_ENUM = firContractsDslNames.id("InvocationKind");
        EXACTLY_ONCE_KIND = firContractsDslNames.invocationKind("EXACTLY_ONCE");
        AT_LEAST_ONCE_KIND = firContractsDslNames.invocationKind("AT_LEAST_ONCE");
        UNKNOWN_KIND = firContractsDslNames.invocationKind(GrsBaseInfo.CountryCodeSource.UNKNOWN);
        AT_MOST_ONCE_KIND = firContractsDslNames.invocationKind("AT_MOST_ONCE");
    }

    public final CallableId getSIMPLE_EFFECT() {
        return SIMPLE_EFFECT;
    }

    public final CallableId getCONTRACT() {
        return CONTRACT;
    }

    public final CallableId getIMPLIES() {
        return IMPLIES;
    }

    public final CallableId getRETURNS() {
        return RETURNS;
    }

    public final CallableId getRETURNS_NOT_NULL() {
        return RETURNS_NOT_NULL;
    }

    public final CallableId getCALLS_IN_PLACE() {
        return CALLS_IN_PLACE;
    }

    public final CallableId getINVOCATION_KIND_ENUM() {
        return INVOCATION_KIND_ENUM;
    }

    public final CallableId getEXACTLY_ONCE_KIND() {
        return EXACTLY_ONCE_KIND;
    }

    public final CallableId getAT_LEAST_ONCE_KIND() {
        return AT_LEAST_ONCE_KIND;
    }

    public final CallableId getUNKNOWN_KIND() {
        return UNKNOWN_KIND;
    }

    public final CallableId getAT_MOST_ONCE_KIND() {
        return AT_MOST_ONCE_KIND;
    }

    private final CallableId contractBuilder(String name2) {
        return id$resolve("kotlin.contracts", "ContractBuilder", name2);
    }

    private final CallableId invocationKind(String name2) {
        return id$resolve("kotlin.contracts", INVOCATION_KIND_ENUM.getCallableName().asString(), name2);
    }

    private final CallableId simpleEffect(String name2) {
        return id$resolve("kotlin.contracts", SIMPLE_EFFECT.getCallableName().asString(), name2);
    }

    private final CallableId id(String name2) {
        return id("kotlin.contracts", name2);
    }

    private final CallableId id(String packageName, String name2) {
        return id$resolve(packageName, null, name2);
    }

    public final CallableId id$resolve(String packageName, String className, String name2) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(name2, "name");
        FqName fqName = new FqName(packageName);
        FqName fqName2 = className != null ? new FqName(className) : null;
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return new CallableId(fqName, fqName2, nameIdentifier, null, 8, null);
    }
}
