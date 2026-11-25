package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: SourceCompilerForInline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fR\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "", "ownerClassName", "", "method", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "inlineScopeVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "file", "Ljava/io/File;", "lineNumber", "", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/commons/Method;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Ljava/io/File;I)V", "getFile", "()Ljava/io/File;", "getInlineScopeVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isInPublicInlineScope", "", "()Z", "isInlineOrInsideInline", "getLineNumber", "()I", "getMethod", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getOwnerClassName", "()Ljava/lang/String;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineCallSiteInfo {
    private final File file;
    private final DescriptorVisibility inlineScopeVisibility;
    private final int lineNumber;
    private final Method method;
    private final String ownerClassName;

    public InlineCallSiteInfo(String ownerClassName, Method method, DescriptorVisibility descriptorVisibility, File file, int i) {
        Intrinsics.checkNotNullParameter(ownerClassName, "ownerClassName");
        Intrinsics.checkNotNullParameter(method, "method");
        this.ownerClassName = ownerClassName;
        this.method = method;
        this.inlineScopeVisibility = descriptorVisibility;
        this.file = file;
        this.lineNumber = i;
    }

    public final String getOwnerClassName() {
        return this.ownerClassName;
    }

    public final Method getMethod() {
        return this.method;
    }

    public final DescriptorVisibility getInlineScopeVisibility() {
        return this.inlineScopeVisibility;
    }

    public final File getFile() {
        return this.file;
    }

    public final int getLineNumber() {
        return this.lineNumber;
    }

    public final boolean isInlineOrInsideInline() {
        return this.inlineScopeVisibility != null;
    }

    public final boolean isInPublicInlineScope() {
        DescriptorVisibility descriptorVisibility = this.inlineScopeVisibility;
        return (descriptorVisibility == null || DescriptorVisibilities.isPrivate(descriptorVisibility)) ? false : true;
    }
}
