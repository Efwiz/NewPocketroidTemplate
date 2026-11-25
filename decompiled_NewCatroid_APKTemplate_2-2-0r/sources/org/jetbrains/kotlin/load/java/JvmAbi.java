package org.jetbrains.kotlin.load.java;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: JvmAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u0004H\u0007J\u0010\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004H\u0007J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0004H\u0007J\u0010\u0010*\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0004H\u0007J\u0010\u0010+\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004H\u0007J\u0010\u0010,\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u00148\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u00168\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0019\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JvmAbi;", "", "()V", "ANNOTATED_PROPERTY_METHOD_NAME_SUFFIX", "", "ANNOTATED_TYPEALIAS_METHOD_NAME_SUFFIX", "ANNOTATIONS_SUFFIX", "DEFAULT_IMPLS_CLASS_NAME", "DEFAULT_IMPLS_SUFFIX", "DEFAULT_PARAMS_IMPL_SUFFIX", "DELEGATED_PROPERTIES_ARRAY_NAME", "DELEGATED_PROPERTY_NAME_SUFFIX", "DELEGATE_SUPER_FIELD_PREFIX", "ERASED_INLINE_CONSTRUCTOR_NAME", "GET_PREFIX", "HIDDEN_INSTANCE_FIELD", "IMPL_SUFFIX_FOR_INLINE_CLASS_MEMBERS", "INSTANCE_FIELD", "IS_PREFIX", "JVM_FIELD_ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "JVM_FIELD_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "LOCAL_VARIABLE_NAME_PREFIX_INLINE_ARGUMENT", "LOCAL_VARIABLE_NAME_PREFIX_INLINE_FUNCTION", "REFLECTION_FACTORY_IMPL", "getREFLECTION_FACTORY_IMPL", "()Lorg/jetbrains/kotlin/name/ClassId;", "REPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION", "getREPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION", "REPEATABLE_ANNOTATION_CONTAINER_NAME", "SET_PREFIX", "getSyntheticMethodNameForAnnotatedProperty", "baseName", "getSyntheticMethodNameForAnnotatedTypeAlias", "typeAliasName", "Lorg/jetbrains/kotlin/name/Name;", "getterName", "propertyName", "isGetterName", "", "name", "isSetterName", "setterName", "startsWithIsPrefix", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmAbi {
    public static final String ANNOTATED_PROPERTY_METHOD_NAME_SUFFIX = "$annotations";
    public static final String DEFAULT_IMPLS_CLASS_NAME = "DefaultImpls";
    public static final String DEFAULT_IMPLS_SUFFIX = "$DefaultImpls";
    public static final String DEFAULT_PARAMS_IMPL_SUFFIX = "$default";
    public static final String DELEGATED_PROPERTIES_ARRAY_NAME = "$$delegatedProperties";
    public static final String DELEGATED_PROPERTY_NAME_SUFFIX = "$delegate";
    public static final String DELEGATE_SUPER_FIELD_PREFIX = "$$delegate_";
    public static final String ERASED_INLINE_CONSTRUCTOR_NAME = "constructor";
    public static final String HIDDEN_INSTANCE_FIELD = "$$INSTANCE";
    public static final String IMPL_SUFFIX_FOR_INLINE_CLASS_MEMBERS = "-impl";
    public static final JvmAbi INSTANCE = new JvmAbi();
    public static final String INSTANCE_FIELD = "INSTANCE";
    public static final ClassId JVM_FIELD_ANNOTATION_CLASS_ID;
    public static final FqName JVM_FIELD_ANNOTATION_FQ_NAME;
    public static final String LOCAL_VARIABLE_NAME_PREFIX_INLINE_ARGUMENT = "$i$a$";
    public static final String LOCAL_VARIABLE_NAME_PREFIX_INLINE_FUNCTION = "$i$f$";
    private static final ClassId REFLECTION_FACTORY_IMPL;
    private static final ClassId REPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION;
    public static final String REPEATABLE_ANNOTATION_CONTAINER_NAME = "Container";

    private JvmAbi() {
    }

    static {
        FqName fqName = new FqName("kotlin.jvm.JvmField");
        JVM_FIELD_ANNOTATION_FQ_NAME = fqName;
        ClassId classId = ClassId.topLevel(fqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        JVM_FIELD_ANNOTATION_CLASS_ID = classId;
        ClassId classId2 = ClassId.topLevel(new FqName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl"));
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        REFLECTION_FACTORY_IMPL = classId2;
        ClassId classIdFromString = ClassId.fromString("kotlin/jvm/internal/RepeatableContainer");
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        REPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION = classIdFromString;
    }

    public final ClassId getREFLECTION_FACTORY_IMPL() {
        return REFLECTION_FACTORY_IMPL;
    }

    public final ClassId getREPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION() {
        return REPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION;
    }

    @JvmStatic
    public static final String getSyntheticMethodNameForAnnotatedProperty(String baseName) {
        Intrinsics.checkNotNullParameter(baseName, "baseName");
        return baseName + ANNOTATED_PROPERTY_METHOD_NAME_SUFFIX;
    }

    @JvmStatic
    public static final String getSyntheticMethodNameForAnnotatedTypeAlias(Name typeAliasName) {
        Intrinsics.checkNotNullParameter(typeAliasName, "typeAliasName");
        return typeAliasName.asString() + ANNOTATED_PROPERTY_METHOD_NAME_SUFFIX;
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
        return startsWithIsPrefix(propertyName) ? propertyName : "get" + CapitalizeDecapitalizeKt.capitalizeAsciiOnly(propertyName);
    }

    @JvmStatic
    public static final String setterName(String propertyName) {
        String strCapitalizeAsciiOnly;
        Intrinsics.checkNotNullParameter(propertyName, "propertyName");
        StringBuilder sb = new StringBuilder(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);
        if (startsWithIsPrefix(propertyName)) {
            strCapitalizeAsciiOnly = propertyName.substring(2);
            Intrinsics.checkNotNullExpressionValue(strCapitalizeAsciiOnly, "substring(...)");
        } else {
            strCapitalizeAsciiOnly = CapitalizeDecapitalizeKt.capitalizeAsciiOnly(propertyName);
        }
        return sb.append(strCapitalizeAsciiOnly).toString();
    }

    @JvmStatic
    public static final boolean startsWithIsPrefix(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (!StringsKt.startsWith$default(name2, LanguageCodeUtil.IS, false, 2, (Object) null) || name2.length() == 2) {
            return false;
        }
        char cCharAt = name2.charAt(2);
        return Intrinsics.compare(97, (int) cCharAt) > 0 || Intrinsics.compare((int) cCharAt, 122) > 0;
    }
}
