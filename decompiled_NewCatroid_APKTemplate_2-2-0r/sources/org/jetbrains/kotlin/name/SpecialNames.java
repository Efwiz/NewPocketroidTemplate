package org.jetbrains.kotlin.name;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SpecialNames.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0004J\u0012\u0010\u0019\u001a\u00020\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\u0019\u001a\u00020\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0007J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001cH\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/name/SpecialNames;", "", "()V", "ANONYMOUS", "Lorg/jetbrains/kotlin/name/Name;", "ANONYMOUS_STRING", "", "ARRAY", "DEFAULT_NAME_FOR_COMPANION_OBJECT", "DESTRUCT", "ENUM_GET_ENTRIES", "IMPLICIT_SET_PARAMETER", "INIT", "ITERATOR", "LOCAL", "NO_NAME_PROVIDED", "RECEIVER", "ROOT_PACKAGE", "SAFE_IDENTIFIER_FOR_NO_NAME", "THIS", "UNARY", "UNDERSCORE_FOR_UNUSED_VAR", "isSafeIdentifier", "", "name", "safeIdentifier", "subscribeOperatorIndex", "idx", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpecialNames {
    public static final Name ANONYMOUS;
    public static final String ANONYMOUS_STRING = "<anonymous>";
    public static final Name ARRAY;
    public static final Name DEFAULT_NAME_FOR_COMPANION_OBJECT;
    public static final Name DESTRUCT;
    public static final Name ENUM_GET_ENTRIES;
    public static final Name IMPLICIT_SET_PARAMETER;
    public static final Name INIT;
    public static final SpecialNames INSTANCE = new SpecialNames();
    public static final Name ITERATOR;
    public static final Name LOCAL;
    public static final Name NO_NAME_PROVIDED;
    public static final Name RECEIVER;
    public static final Name ROOT_PACKAGE;
    public static final Name SAFE_IDENTIFIER_FOR_NO_NAME;
    public static final Name THIS;
    public static final Name UNARY;
    public static final Name UNDERSCORE_FOR_UNUSED_VAR;

    private SpecialNames() {
    }

    static {
        Name nameSpecial = Name.special("<no name provided>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        NO_NAME_PROVIDED = nameSpecial;
        Name nameSpecial2 = Name.special("<root package>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial2, "special(...)");
        ROOT_PACKAGE = nameSpecial2;
        Name nameIdentifier = Name.identifier("Companion");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        DEFAULT_NAME_FOR_COMPANION_OBJECT = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("no_name_in_PSI_3d19d79d_1ba9_4cd0_b7f5_b46aa3cd5d40");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        SAFE_IDENTIFIER_FOR_NO_NAME = nameIdentifier2;
        Name nameSpecial3 = Name.special(ANONYMOUS_STRING);
        Intrinsics.checkNotNullExpressionValue(nameSpecial3, "special(...)");
        ANONYMOUS = nameSpecial3;
        Name nameSpecial4 = Name.special("<unary>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial4, "special(...)");
        UNARY = nameSpecial4;
        Name nameSpecial5 = Name.special("<this>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial5, "special(...)");
        THIS = nameSpecial5;
        Name nameSpecial6 = Name.special("<init>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial6, "special(...)");
        INIT = nameSpecial6;
        Name nameSpecial7 = Name.special("<iterator>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial7, "special(...)");
        ITERATOR = nameSpecial7;
        Name nameSpecial8 = Name.special("<destruct>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial8, "special(...)");
        DESTRUCT = nameSpecial8;
        Name nameSpecial9 = Name.special("<local>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial9, "special(...)");
        LOCAL = nameSpecial9;
        Name nameSpecial10 = Name.special("<unused var>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial10, "special(...)");
        UNDERSCORE_FOR_UNUSED_VAR = nameSpecial10;
        Name nameSpecial11 = Name.special("<set-?>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial11, "special(...)");
        IMPLICIT_SET_PARAMETER = nameSpecial11;
        Name nameSpecial12 = Name.special("<array>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial12, "special(...)");
        ARRAY = nameSpecial12;
        Name nameSpecial13 = Name.special("<receiver>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial13, "special(...)");
        RECEIVER = nameSpecial13;
        Name nameSpecial14 = Name.special("<get-entries>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial14, "special(...)");
        ENUM_GET_ENTRIES = nameSpecial14;
    }

    @JvmStatic
    public static final Name subscribeOperatorIndex(int idx) {
        if (idx < 0) {
            throw new IllegalArgumentException(("Index should be non-negative, but was " + idx).toString());
        }
        Name nameSpecial = Name.special("<index_" + idx + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        return nameSpecial;
    }

    @JvmStatic
    public static final Name safeIdentifier(Name name2) {
        return (name2 == null || name2.isSpecial()) ? SAFE_IDENTIFIER_FOR_NO_NAME : name2;
    }

    @JvmStatic
    public static final Name safeIdentifier(String name2) {
        return safeIdentifier(name2 == null ? null : Name.identifier(name2));
    }

    public final boolean isSafeIdentifier(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString.length() > 0 && !name2.isSpecial();
    }
}
