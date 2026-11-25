package kotlin.reflect.jvm.internal.impl.load.java;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;

/* JADX INFO: compiled from: JvmAbi.kt */
/* JADX INFO: loaded from: classes12.dex */
public final class JvmAbi {
    public static final JvmAbi INSTANCE = new JvmAbi();
    public static final FqName JVM_FIELD_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmField");
    private static final ClassId REFLECTION_FACTORY_IMPL;
    private static final ClassId REPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION;

    private JvmAbi() {
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(FqName(\"kotlin.….ReflectionFactoryImpl\"))");
        REFLECTION_FACTORY_IMPL = classId;
        ClassId classIdFromString = ClassId.fromString("kotlin/jvm/internal/RepeatableContainer");
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(\"kotlin/jvm/i…nal/RepeatableContainer\")");
        REPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION = classIdFromString;
    }

    public final ClassId getREPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION() {
        return REPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION;
    }

    @JvmStatic
    public static final boolean isGetterName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return StringsKt.startsWith$default(name2, "get", false, 2, (Object) null) || StringsKt.startsWith$default(name2, LanguageCodeUtil.IS, false, 2, (Object) null);
    }

    @JvmStatic
    public static final boolean isSetterName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return StringsKt.startsWith$default(name2, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, false, 2, (Object) null);
    }

    @JvmStatic
    public static final String getterName(String propertyName) {
        Intrinsics.checkNotNullParameter(propertyName, "propertyName");
        return startsWithIsPrefix(propertyName) ? propertyName : Intrinsics.stringPlus("get", CapitalizeDecapitalizeKt.capitalizeAsciiOnly(propertyName));
    }

    @JvmStatic
    public static final String setterName(String propertyName) {
        String strCapitalizeAsciiOnly;
        Intrinsics.checkNotNullParameter(propertyName, "propertyName");
        if (startsWithIsPrefix(propertyName)) {
            strCapitalizeAsciiOnly = propertyName.substring(LanguageCodeUtil.IS.length());
            Intrinsics.checkNotNullExpressionValue(strCapitalizeAsciiOnly, "this as java.lang.String).substring(startIndex)");
        } else {
            strCapitalizeAsciiOnly = CapitalizeDecapitalizeKt.capitalizeAsciiOnly(propertyName);
        }
        return Intrinsics.stringPlus(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, strCapitalizeAsciiOnly);
    }

    @JvmStatic
    public static final boolean startsWithIsPrefix(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (!StringsKt.startsWith$default(name2, LanguageCodeUtil.IS, false, 2, (Object) null) || name2.length() == LanguageCodeUtil.IS.length()) {
            return false;
        }
        char cCharAt = name2.charAt(LanguageCodeUtil.IS.length());
        return Intrinsics.compare(97, (int) cCharAt) > 0 || Intrinsics.compare((int) cCharAt, 122) > 0;
    }
}
