package org.jetbrains.kotlin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: KtSourceFile.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/KtInMemoryTextSourceFile;", "Lorg/jetbrains/kotlin/KtSourceFile;", "name", "", "path", "text", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/CharSequence;)V", "getName", "()Ljava/lang/String;", "getPath", "getText", "()Ljava/lang/CharSequence;", "getContentsAsStream", "Ljava/io/InputStream;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtInMemoryTextSourceFile implements KtSourceFile {
    private final String name;
    private final String path;
    private final CharSequence text;

    public KtInMemoryTextSourceFile(String name2, String str, CharSequence text) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(text, "text");
        this.name = name2;
        this.path = str;
        this.text = text;
    }

    @Override // org.jetbrains.kotlin.KtSourceFile
    public String getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.KtSourceFile
    public String getPath() {
        return this.path;
    }

    public final CharSequence getText() {
        return this.text;
    }

    @Override // org.jetbrains.kotlin.KtSourceFile
    public InputStream getContentsAsStream() {
        byte[] bytes = this.text.toString().getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return new ByteArrayInputStream(bytes);
    }
}
