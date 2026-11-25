package org.jetbrains.kotlin.name;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: NameUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0003J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\u0007J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0004H\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\rH\u0007J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\rH\u0007J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0004H\u0007R\u001c\u0010\u0003\u001a\u00020\u00048\u0006X\u0087D¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/name/NameUtils;", "", "()V", "CONTEXT_RECEIVER_PREFIX", "", "getCONTEXT_RECEIVER_PREFIX$annotations", "getCONTEXT_RECEIVER_PREFIX", "()Ljava/lang/String;", "SANITIZE_AS_JAVA_INVALID_CHARACTERS", "Lkotlin/text/Regex;", "capitalizeAsJavaClassName", "str", "contextReceiverName", "Lorg/jetbrains/kotlin/name/Name;", "index", "", "delegateFieldName", "getPackagePartClassNamePrefix", "shortFileName", "getScriptNameForFile", "filePath", "hasName", "", "name", "propertyDelegateName", "propertyName", "sanitizeAsJavaIdentifier", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NameUtils {
    public static final NameUtils INSTANCE = new NameUtils();
    private static final Regex SANITIZE_AS_JAVA_INVALID_CHARACTERS = new Regex("[^\\p{L}\\p{Digit}]");
    private static final String CONTEXT_RECEIVER_PREFIX = "$context_receiver";

    @JvmStatic
    public static /* synthetic */ void getCONTEXT_RECEIVER_PREFIX$annotations() {
    }

    private NameUtils() {
    }

    public static final String getCONTEXT_RECEIVER_PREFIX() {
        return CONTEXT_RECEIVER_PREFIX;
    }

    @JvmStatic
    public static final String sanitizeAsJavaIdentifier(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return SANITIZE_AS_JAVA_INVALID_CHARACTERS.replace(name2, "_");
    }

    @JvmStatic
    public static final String getPackagePartClassNamePrefix(String shortFileName) {
        Intrinsics.checkNotNullParameter(shortFileName, "shortFileName");
        if (shortFileName.length() == 0) {
            return "_";
        }
        return capitalizeAsJavaClassName(sanitizeAsJavaIdentifier(shortFileName));
    }

    @JvmStatic
    private static final String capitalizeAsJavaClassName(String str) {
        if (Character.isJavaIdentifierStart(str.charAt(0))) {
            StringBuilder sb = new StringBuilder();
            String strValueOf = String.valueOf(str.charAt(0));
            Intrinsics.checkNotNull(strValueOf, "null cannot be cast to non-null type java.lang.String");
            String upperCase = strValueOf.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            StringBuilder sbAppend = sb.append(upperCase);
            String strSubstring = str.substring(1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return sbAppend.append(strSubstring).toString();
        }
        return "_" + str;
    }

    @JvmStatic
    public static final Name getScriptNameForFile(String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Name nameIdentifier = Name.identifier(getPackagePartClassNamePrefix(StringsKt.substringBeforeLast$default(StringsKt.substringAfterLast$default(filePath, '/', (String) null, 2, (Object) null), '.', (String) null, 2, (Object) null)));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @JvmStatic
    public static final boolean hasName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return (Intrinsics.areEqual(name2, SpecialNames.NO_NAME_PROVIDED) || Intrinsics.areEqual(name2, SpecialNames.ANONYMOUS)) ? false : true;
    }

    @JvmStatic
    public static final Name delegateFieldName(int index) {
        Name nameIdentifier = Name.identifier(JvmAbi.DELEGATE_SUPER_FIELD_PREFIX + index);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @JvmStatic
    public static final Name propertyDelegateName(Name propertyName) {
        Intrinsics.checkNotNullParameter(propertyName, "propertyName");
        Name nameIdentifier = Name.identifier(propertyName.asString() + JvmAbi.DELEGATED_PROPERTY_NAME_SUFFIX);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @JvmStatic
    public static final Name contextReceiverName(int index) {
        Name nameIdentifier = Name.identifier(CONTEXT_RECEIVER_PREFIX + '_' + index);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }
}
