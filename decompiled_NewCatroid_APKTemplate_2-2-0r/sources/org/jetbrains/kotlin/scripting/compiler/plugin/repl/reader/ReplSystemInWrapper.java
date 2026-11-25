package org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.repl.ReplUtilKt;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplSystemInWrapper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\tH\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/reader/ReplSystemInWrapper;", "Ljava/io/InputStream;", "stdin", "replWriter", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "(Ljava/io/InputStream;Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;)V", "byteBuilder", "Ljava/io/ByteArrayOutputStream;", "curBytePos", "", "inputByteArray", "", "isAtBufferEnd", "", "()Z", "isLastByteProcessed", "isReadLineStartSent", "isReplScriptExecuting", "setReplScriptExecuting", "(Z)V", "isXmlIncomplete", "parseInput", "", "read", "resetBufferIfNeeded", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplSystemInWrapper extends InputStream {
    private ByteArrayOutputStream byteBuilder;
    private int curBytePos;
    private byte[] inputByteArray;
    private boolean isLastByteProcessed;
    private boolean isReadLineStartSent;
    private volatile boolean isReplScriptExecuting;
    private boolean isXmlIncomplete;
    private final ReplWriter replWriter;
    private final InputStream stdin;

    public ReplSystemInWrapper(InputStream stdin, ReplWriter replWriter) {
        Intrinsics.checkNotNullParameter(stdin, "stdin");
        Intrinsics.checkNotNullParameter(replWriter, "replWriter");
        this.stdin = stdin;
        this.replWriter = replWriter;
        this.isXmlIncomplete = true;
        this.byteBuilder = new ByteArrayOutputStream();
        this.inputByteArray = new byte[0];
    }

    private final boolean isAtBufferEnd() {
        return this.curBytePos == this.inputByteArray.length;
    }

    /* JADX INFO: renamed from: isReplScriptExecuting, reason: from getter */
    public final boolean getIsReplScriptExecuting() {
        return this.isReplScriptExecuting;
    }

    public final void setReplScriptExecuting(boolean z) {
        this.isReplScriptExecuting = z;
    }

    @Override // java.io.InputStream
    public int read() {
        if (this.isLastByteProcessed) {
            if (this.isReplScriptExecuting) {
                this.isReadLineStartSent = false;
                this.replWriter.notifyReadLineEnd();
            }
            this.isLastByteProcessed = false;
            return -1;
        }
        while (this.isXmlIncomplete) {
            if (!this.isReadLineStartSent && this.isReplScriptExecuting) {
                this.replWriter.notifyReadLineStart();
                this.isReadLineStartSent = true;
            }
            this.byteBuilder.write(this.stdin.read());
            String string = this.byteBuilder.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            if (StringsKt.endsWith$default((CharSequence) string, '\n', false, 2, (Object) null)) {
                this.isXmlIncomplete = false;
                this.isLastByteProcessed = false;
                byte[] bytes = parseInput().getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                this.inputByteArray = bytes;
            }
        }
        byte[] bArr = this.inputByteArray;
        int i = this.curBytePos;
        this.curBytePos = i + 1;
        byte b = bArr[i];
        resetBufferIfNeeded();
        return b;
    }

    private final String parseInput() {
        String string = this.byteBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String xml = ReplSystemInWrapperKt.parseXml(string);
        return this.isReplScriptExecuting ? ReplUtilKt.replUnescapeLineBreaks(xml) : xml;
    }

    private final void resetBufferIfNeeded() {
        if (isAtBufferEnd()) {
            this.isXmlIncomplete = true;
            this.byteBuilder = new ByteArrayOutputStream();
            this.curBytePos = 0;
            this.isLastByteProcessed = true;
        }
    }
}
