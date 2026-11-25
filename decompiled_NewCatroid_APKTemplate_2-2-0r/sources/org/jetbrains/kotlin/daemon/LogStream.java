package org.jetbrains.kotlin.daemon;

import java.io.OutputStream;
import java.util.logging.Logger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: KotlinCompileDaemon.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0015\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR#\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/daemon/LogStream;", "Ljava/io/OutputStream;", "name", "", "(Ljava/lang/String;)V", "lineBuf", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "getLineBuf", "()Ljava/lang/StringBuilder;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Ljava/util/logging/Logger;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getLog", "()Ljava/util/logging/Logger;", "log$delegate", "Lkotlin/Lazy;", "flush", "", "write", "byte", "", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LogStream extends OutputStream {
    private final StringBuilder lineBuf;

    /* JADX INFO: renamed from: log$delegate, reason: from kotlin metadata */
    private final Lazy log;

    public LogStream(final String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.log = LazyKt.lazy(new Function0<Logger>() { // from class: org.jetbrains.kotlin.daemon.LogStream$log$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Logger invoke() {
                return Logger.getLogger(name2);
            }
        });
        this.lineBuf = new StringBuilder();
    }

    public final Logger getLog() {
        return (Logger) this.log.getValue();
    }

    public final StringBuilder getLineBuf() {
        return this.lineBuf;
    }

    @Override // java.io.OutputStream
    public void write(int i) {
        if (i == 10) {
            flush();
        } else {
            this.lineBuf.append((char) i);
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() {
        getLog().info(this.lineBuf.toString());
        this.lineBuf.setLength(0);
    }
}
