package org.jetbrains.kotlin.scripting.resolve;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.host.FileBasedScriptSource;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: refineCompilationConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0096\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0014\u001a\u00020\u00058VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/VirtualFileScriptSource;", "Lkotlin/script/experimental/host/FileBasedScriptSource;", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "preloadedText", "", "(Lcom/intellij/openapi/vfs/VirtualFile;Ljava/lang/String;)V", "externalLocation", "Ljava/net/URL;", "getExternalLocation", "()Ljava/net/URL;", "file", "Ljava/io/File;", "getFile", "()Ljava/io/File;", "locationId", "getLocationId", "()Ljava/lang/String;", "name", "getName", "text", "getText", "text$delegate", "Lkotlin/Lazy;", "getVirtualFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class VirtualFileScriptSource extends FileBasedScriptSource {
    private final String preloadedText;

    /* JADX INFO: renamed from: text$delegate, reason: from kotlin metadata */
    private final Lazy text;
    private final VirtualFile virtualFile;

    public /* synthetic */ VirtualFileScriptSource(VirtualFile virtualFile, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(virtualFile, (i & 2) != 0 ? null : str);
    }

    public final VirtualFile getVirtualFile() {
        return this.virtualFile;
    }

    public VirtualFileScriptSource(VirtualFile virtualFile, String str) {
        Intrinsics.checkNotNullParameter(virtualFile, "virtualFile");
        this.virtualFile = virtualFile;
        this.preloadedText = str;
        this.text = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.scripting.resolve.VirtualFileScriptSource$text$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() throws IOException {
                String str2 = this.this$0.preloadedText;
                if (str2 != null) {
                    return str2;
                }
                InputStream inputStream = this.this$0.getVirtualFile().getInputStream();
                Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                Reader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
                return TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192));
            }
        });
    }

    @Override // kotlin.script.experimental.host.FileBasedScriptSource
    public File getFile() {
        return new File(this.virtualFile.getPath());
    }

    @Override // kotlin.script.experimental.api.ExternalSourceCode
    public URL getExternalLocation() {
        return new URL(this.virtualFile.getUrl());
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getText() {
        return (String) this.text.getValue();
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getName() {
        return this.virtualFile.getName();
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getLocationId() {
        return this.virtualFile.getPath();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        VirtualFileScriptSource virtualFileScriptSource = other instanceof VirtualFileScriptSource ? (VirtualFileScriptSource) other : null;
        return virtualFileScriptSource != null && Intrinsics.areEqual(this.virtualFile, virtualFileScriptSource.virtualFile);
    }

    public int hashCode() {
        return this.virtualFile.hashCode();
    }
}
