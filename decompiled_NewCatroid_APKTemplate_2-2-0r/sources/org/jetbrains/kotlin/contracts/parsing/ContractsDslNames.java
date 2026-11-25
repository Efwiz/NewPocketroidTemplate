package org.jetbrains.kotlin.contracts.parsing;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: PsiContractsUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0017\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0006R\u0011\u0010%\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0006R\u0011\u0010'\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0006¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/ContractsDslNames;", "", "()V", "AT_LEAST_ONCE_KIND", "Lorg/jetbrains/kotlin/name/Name;", "getAT_LEAST_ONCE_KIND", "()Lorg/jetbrains/kotlin/name/Name;", "AT_MOST_ONCE_KIND", "getAT_MOST_ONCE_KIND", "CALLS_IN_PLACE", "getCALLS_IN_PLACE", "CALLS_IN_PLACE_EFFECT", "getCALLS_IN_PLACE_EFFECT", "CONDITIONAL_EFFECT", "getCONDITIONAL_EFFECT", "CONTRACT", "getCONTRACT", "CONTRACTS_DSL_ANNOTATION_FQN", "Lorg/jetbrains/kotlin/name/FqName;", "getCONTRACTS_DSL_ANNOTATION_FQN", "()Lorg/jetbrains/kotlin/name/FqName;", "EFFECT", "getEFFECT", "EXACTLY_ONCE_KIND", "getEXACTLY_ONCE_KIND", "IMPLIES", "getIMPLIES", "INVOCATION_KIND_ENUM", "getINVOCATION_KIND_ENUM", "RETURNS", "getRETURNS", "RETURNS_EFFECT", "getRETURNS_EFFECT", "RETURNS_NOT_NULL", "getRETURNS_NOT_NULL", "RETURNS_NOT_NULL_EFFECT", "getRETURNS_NOT_NULL_EFFECT", "SIMPLE_EFFECT", "getSIMPLE_EFFECT", "UNKNOWN_KIND", "getUNKNOWN_KIND", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContractsDslNames {
    private static final Name AT_LEAST_ONCE_KIND;
    private static final Name AT_MOST_ONCE_KIND;
    private static final Name CALLS_IN_PLACE;
    private static final Name CALLS_IN_PLACE_EFFECT;
    private static final Name CONDITIONAL_EFFECT;
    private static final Name CONTRACT;
    private static final Name EFFECT;
    private static final Name EXACTLY_ONCE_KIND;
    private static final Name IMPLIES;
    private static final Name INVOCATION_KIND_ENUM;
    private static final Name RETURNS;
    private static final Name RETURNS_EFFECT;
    private static final Name RETURNS_NOT_NULL;
    private static final Name RETURNS_NOT_NULL_EFFECT;
    private static final Name SIMPLE_EFFECT;
    private static final Name UNKNOWN_KIND;
    public static final ContractsDslNames INSTANCE = new ContractsDslNames();
    private static final FqName CONTRACTS_DSL_ANNOTATION_FQN = new FqName("kotlin.internal.ContractsDsl");

    private ContractsDslNames() {
    }

    static {
        Name nameIdentifier = Name.identifier("Effect");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        EFFECT = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("ConditionalEffect");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        CONDITIONAL_EFFECT = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("SimpleEffect");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        SIMPLE_EFFECT = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier("Returns");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        RETURNS_EFFECT = nameIdentifier4;
        Name nameIdentifier5 = Name.identifier("ReturnsNotNull");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        RETURNS_NOT_NULL_EFFECT = nameIdentifier5;
        Name nameIdentifier6 = Name.identifier("CallsInPlace");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        CALLS_IN_PLACE_EFFECT = nameIdentifier6;
        Name nameIdentifier7 = Name.identifier("contract");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier7, "identifier(...)");
        CONTRACT = nameIdentifier7;
        Name nameIdentifier8 = Name.identifier("implies");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier8, "identifier(...)");
        IMPLIES = nameIdentifier8;
        Name nameIdentifier9 = Name.identifier("returns");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier9, "identifier(...)");
        RETURNS = nameIdentifier9;
        Name nameIdentifier10 = Name.identifier("returnsNotNull");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier10, "identifier(...)");
        RETURNS_NOT_NULL = nameIdentifier10;
        Name nameIdentifier11 = Name.identifier("callsInPlace");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier11, "identifier(...)");
        CALLS_IN_PLACE = nameIdentifier11;
        Name nameIdentifier12 = Name.identifier("InvocationKind");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier12, "identifier(...)");
        INVOCATION_KIND_ENUM = nameIdentifier12;
        Name nameIdentifier13 = Name.identifier("EXACTLY_ONCE");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier13, "identifier(...)");
        EXACTLY_ONCE_KIND = nameIdentifier13;
        Name nameIdentifier14 = Name.identifier("AT_LEAST_ONCE");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier14, "identifier(...)");
        AT_LEAST_ONCE_KIND = nameIdentifier14;
        Name nameIdentifier15 = Name.identifier(GrsBaseInfo.CountryCodeSource.UNKNOWN);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier15, "identifier(...)");
        UNKNOWN_KIND = nameIdentifier15;
        Name nameIdentifier16 = Name.identifier("AT_MOST_ONCE");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier16, "identifier(...)");
        AT_MOST_ONCE_KIND = nameIdentifier16;
    }

    public final FqName getCONTRACTS_DSL_ANNOTATION_FQN() {
        return CONTRACTS_DSL_ANNOTATION_FQN;
    }

    public final Name getEFFECT() {
        return EFFECT;
    }

    public final Name getCONDITIONAL_EFFECT() {
        return CONDITIONAL_EFFECT;
    }

    public final Name getSIMPLE_EFFECT() {
        return SIMPLE_EFFECT;
    }

    public final Name getRETURNS_EFFECT() {
        return RETURNS_EFFECT;
    }

    public final Name getRETURNS_NOT_NULL_EFFECT() {
        return RETURNS_NOT_NULL_EFFECT;
    }

    public final Name getCALLS_IN_PLACE_EFFECT() {
        return CALLS_IN_PLACE_EFFECT;
    }

    public final Name getCONTRACT() {
        return CONTRACT;
    }

    public final Name getIMPLIES() {
        return IMPLIES;
    }

    public final Name getRETURNS() {
        return RETURNS;
    }

    public final Name getRETURNS_NOT_NULL() {
        return RETURNS_NOT_NULL;
    }

    public final Name getCALLS_IN_PLACE() {
        return CALLS_IN_PLACE;
    }

    public final Name getINVOCATION_KIND_ENUM() {
        return INVOCATION_KIND_ENUM;
    }

    public final Name getEXACTLY_ONCE_KIND() {
        return EXACTLY_ONCE_KIND;
    }

    public final Name getAT_LEAST_ONCE_KIND() {
        return AT_LEAST_ONCE_KIND;
    }

    public final Name getUNKNOWN_KIND() {
        return UNKNOWN_KIND;
    }

    public final Name getAT_MOST_ONCE_KIND() {
        return AT_MOST_ONCE_KIND;
    }
}
