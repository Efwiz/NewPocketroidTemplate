package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: compiled from: ExtensionDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0001\fB5\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bR\u0014\u0010\t\u001a\u0004\u0018\u00010\n8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionDescriptor;", "", "implementation", "", "os", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionDescriptor$Os;", "orderId", "order", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/LoadingOrder;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/util/XmlElement;", "(Ljava/lang/String;Lcom/intellij/openapi/extensions/ExtensionDescriptor$Os;Ljava/lang/String;Lcom/intellij/openapi/extensions/LoadingOrder;Lcom/intellij/util/XmlElement;)V", "Os", "intellij.platform.extensions"}, k = 1, mv = {1, 5, 1})
public final class ExtensionDescriptor {
    public XmlElement element;
    public final String implementation;
    public final LoadingOrder order;
    public final String orderId;
    public final Os os;

    /* JADX INFO: compiled from: ExtensionDescriptor.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionDescriptor$Os;", "", "(Ljava/lang/String;I)V", "mac", "linux", "windows", "unix", "freebsd", "intellij.platform.extensions"}, k = 1, mv = {1, 5, 1})
    public enum Os {
        mac,
        linux,
        windows,
        unix,
        freebsd
    }

    public ExtensionDescriptor(String str, Os os, String str2, LoadingOrder order, XmlElement xmlElement) {
        Intrinsics.checkNotNullParameter(order, "order");
        this.implementation = str;
        this.os = os;
        this.orderId = str2;
        this.order = order;
        this.element = xmlElement;
    }
}
