package org.jetbrains.kotlin.descriptors.annotations;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.bytebuddy.implementation.MethodDelegation;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: AnnotationUseSiteTarget.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0013\b\u0002\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "", "renderName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getRenderName", "()Ljava/lang/String;", "FIELD", "FILE", "PROPERTY", "PROPERTY_GETTER", "PROPERTY_SETTER", "RECEIVER", "CONSTRUCTOR_PARAMETER", "SETTER_PARAMETER", "PROPERTY_DELEGATE_FIELD", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum AnnotationUseSiteTarget {
    FIELD(null, 1, null),
    FILE(null, 1, null),
    PROPERTY(null, 1, null),
    PROPERTY_GETTER("get"),
    PROPERTY_SETTER(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX),
    RECEIVER(null, 1, null),
    CONSTRUCTOR_PARAMETER("param"),
    SETTER_PARAMETER("setparam"),
    PROPERTY_DELEGATE_FIELD(MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String renderName;

    public static EnumEntries<AnnotationUseSiteTarget> getEntries() {
        return $ENTRIES;
    }

    AnnotationUseSiteTarget(String str) {
        this.renderName = str == null ? CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(name()) : str;
    }

    /* synthetic */ AnnotationUseSiteTarget(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }

    public final String getRenderName() {
        return this.renderName;
    }
}
