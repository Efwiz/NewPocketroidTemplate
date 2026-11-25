package org.jetbrains.kotlin.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinJavascriptMetadataUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/utils/KotlinJavascriptMetadata;", "", "version", "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "moduleName", "", "body", "", "(Lorg/jetbrains/kotlin/utils/JsMetadataVersion;Ljava/lang/String;[B)V", "getBody", "()[B", "getModuleName", "()Ljava/lang/String;", "getVersion", "()Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinJavascriptMetadata {
    private final byte[] body;
    private final String moduleName;
    private final JsMetadataVersion version;

    public KotlinJavascriptMetadata(JsMetadataVersion version, String moduleName, byte[] body) {
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(body, "body");
        this.version = version;
        this.moduleName = moduleName;
        this.body = body;
    }

    public final byte[] getBody() {
        return this.body;
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final JsMetadataVersion getVersion() {
        return this.version;
    }
}
