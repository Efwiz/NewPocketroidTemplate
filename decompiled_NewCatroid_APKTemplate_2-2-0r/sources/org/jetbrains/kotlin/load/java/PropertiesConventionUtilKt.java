package org.jetbrains.kotlin.load.java;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: propertiesConventionUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\u001a\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002\u001a\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0002\u001a\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\u0002\u001a\u0018\u0010\b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n\u001a0\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\n2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\rH\u0002\u001a\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002\u001a\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u0002\u001a\u000e\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0002¨\u0006\u0014"}, d2 = {"getPropertyNamesCandidatesByAccessorName", "", "Lorg/jetbrains/kotlin/name/Name;", "name", "possibleGetMethodNames", "propertyName", "propertyNameByGetMethodName", "methodName", "propertyNameBySetMethodName", "withIsPrefix", "", "propertyNameFromAccessorMethodName", "prefix", "", "removePrefix", "addPrefix", "propertyNamesByAccessorName", "propertyNamesBySetMethodName", "setMethodName", "getMethodName", "compiler.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PropertiesConventionUtilKt {
    public static final Name propertyNameByGetMethodName(Name methodName) {
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        Name namePropertyNameFromAccessorMethodName$default = propertyNameFromAccessorMethodName$default(methodName, "get", false, null, 12, null);
        return namePropertyNameFromAccessorMethodName$default == null ? propertyNameFromAccessorMethodName$default(methodName, LanguageCodeUtil.IS, false, null, 8, null) : namePropertyNameFromAccessorMethodName$default;
    }

    public static final Name propertyNameBySetMethodName(Name methodName, boolean z) {
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        return propertyNameFromAccessorMethodName$default(methodName, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, false, z ? LanguageCodeUtil.IS : null, 4, null);
    }

    public static final List<Name> propertyNamesBySetMethodName(Name methodName) {
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        return CollectionsKt.listOfNotNull((Object[]) new Name[]{propertyNameBySetMethodName(methodName, false), propertyNameBySetMethodName(methodName, true)});
    }

    public static final List<Name> propertyNamesByAccessorName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return CollectionsKt.listOfNotNull((Object[]) new Name[]{propertyNameByGetMethodName(name2), propertyNameBySetMethodName(name2, true), propertyNameBySetMethodName(name2, false)});
    }

    static /* synthetic */ Name propertyNameFromAccessorMethodName$default(Name name2, String str, boolean z, String str2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        if ((i & 8) != 0) {
            str2 = null;
        }
        return propertyNameFromAccessorMethodName(name2, str, z, str2);
    }

    private static final Name propertyNameFromAccessorMethodName(Name name2, String str, boolean z, String str2) {
        if (name2.isSpecial()) {
            return null;
        }
        String identifier = name2.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        if (!StringsKt.startsWith$default(identifier, str, false, 2, (Object) null) || identifier.length() == str.length()) {
            return null;
        }
        char cCharAt = identifier.charAt(str.length());
        if ('a' <= cCharAt && cCharAt < '{') {
            return null;
        }
        if (str2 != null) {
            return Name.identifier(str2 + StringsKt.removePrefix(identifier, (CharSequence) str));
        }
        if (!z) {
            return name2;
        }
        String strDecapitalizeSmartForCompiler = CapitalizeDecapitalizeKt.decapitalizeSmartForCompiler(StringsKt.removePrefix(identifier, (CharSequence) str), true);
        if (Name.isValidIdentifier(strDecapitalizeSmartForCompiler)) {
            return Name.identifier(strDecapitalizeSmartForCompiler);
        }
        return null;
    }

    public static final List<Name> getPropertyNamesCandidatesByAccessorName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (JvmAbi.isGetterName(strAsString)) {
            return CollectionsKt.listOfNotNull(propertyNameByGetMethodName(name2));
        }
        if (JvmAbi.isSetterName(strAsString)) {
            return propertyNamesBySetMethodName(name2);
        }
        return BuiltinSpecialProperties.INSTANCE.getPropertyNameCandidatesBySpecialGetterName(name2);
    }

    public static final List<Name> possibleGetMethodNames(Name propertyName) {
        Intrinsics.checkNotNullParameter(propertyName, "propertyName");
        if (propertyName.isSpecial()) {
            return CollectionsKt.emptyList();
        }
        String identifier = propertyName.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        if (identifier.length() == 0) {
            return CollectionsKt.emptyList();
        }
        char cCharAt = identifier.charAt(0);
        if (!Character.isJavaIdentifierStart(cCharAt) || ('A' <= cCharAt && cCharAt < '[')) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(3);
        if (JvmAbi.startsWithIsPrefix(identifier)) {
            arrayList.add(propertyName);
        }
        String strCapitalizeAsciiOnly = CapitalizeDecapitalizeKt.capitalizeAsciiOnly(identifier);
        String strCapitalizeFirstWord = CapitalizeDecapitalizeKt.capitalizeFirstWord(identifier, true);
        arrayList.add(Name.identifier("get" + strCapitalizeAsciiOnly));
        if (!Intrinsics.areEqual(strCapitalizeFirstWord, strCapitalizeAsciiOnly)) {
            arrayList.add(Name.identifier("get" + strCapitalizeFirstWord));
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (Intrinsics.areEqual(propertyNameByGetMethodName((Name) obj), propertyName)) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    public static final Name setMethodName(Name getMethodName) {
        Intrinsics.checkNotNullParameter(getMethodName, "getMethodName");
        String identifier = getMethodName.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        String str = "get";
        if (!StringsKt.startsWith$default(identifier, "get", false, 2, (Object) null)) {
            str = LanguageCodeUtil.IS;
            if (!StringsKt.startsWith$default(identifier, LanguageCodeUtil.IS, false, 2, (Object) null)) {
                throw new IllegalArgumentException();
            }
        }
        Name nameIdentifier = Name.identifier(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX + StringsKt.removePrefix(identifier, (CharSequence) str));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }
}
