package org.jetbrains.kotlin.ir.backend.js.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ConversionNames.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/ConversionNames;", "", "()V", "TO_BYTE", "Lorg/jetbrains/kotlin/name/Name;", "getTO_BYTE", "()Lorg/jetbrains/kotlin/name/Name;", "TO_CHAR", "getTO_CHAR", "TO_DOUBLE", "getTO_DOUBLE", "TO_FLOAT", "getTO_FLOAT", "TO_INT", "getTO_INT", "TO_LONG", "getTO_LONG", "TO_SHORT", "getTO_SHORT", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConversionNames {
    public static final ConversionNames INSTANCE = new ConversionNames();
    private static final Name TO_BYTE;
    private static final Name TO_CHAR;
    private static final Name TO_DOUBLE;
    private static final Name TO_FLOAT;
    private static final Name TO_INT;
    private static final Name TO_LONG;
    private static final Name TO_SHORT;

    private ConversionNames() {
    }

    static {
        Name nameIdentifier = Name.identifier("toByte");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        TO_BYTE = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("toChar");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        TO_CHAR = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("toDouble");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        TO_DOUBLE = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier("toFloat");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        TO_FLOAT = nameIdentifier4;
        Name nameIdentifier5 = Name.identifier("toInt");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        TO_INT = nameIdentifier5;
        Name nameIdentifier6 = Name.identifier("toLong");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        TO_LONG = nameIdentifier6;
        Name nameIdentifier7 = Name.identifier("toShort");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier7, "identifier(...)");
        TO_SHORT = nameIdentifier7;
    }

    public final Name getTO_BYTE() {
        return TO_BYTE;
    }

    public final Name getTO_CHAR() {
        return TO_CHAR;
    }

    public final Name getTO_DOUBLE() {
        return TO_DOUBLE;
    }

    public final Name getTO_FLOAT() {
        return TO_FLOAT;
    }

    public final Name getTO_INT() {
        return TO_INT;
    }

    public final Name getTO_LONG() {
        return TO_LONG;
    }

    public final Name getTO_SHORT() {
        return TO_SHORT;
    }
}
