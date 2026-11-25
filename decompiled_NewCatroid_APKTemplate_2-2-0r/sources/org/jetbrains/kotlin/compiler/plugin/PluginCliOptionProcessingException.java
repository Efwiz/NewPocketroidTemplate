package org.jetbrains.kotlin.compiler.plugin;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CliOptions.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0006\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/compiler/plugin/PluginCliOptionProcessingException;", "Lorg/jetbrains/kotlin/compiler/plugin/CliOptionProcessingException;", "pluginId", "", "options", "", "Lorg/jetbrains/kotlin/compiler/plugin/AbstractCliOption;", CrashHianalyticsData.MESSAGE, "cause", "", "(Ljava/lang/String;Ljava/util/Collection;Ljava/lang/String;Ljava/lang/Throwable;)V", "getOptions", "()Ljava/util/Collection;", "getPluginId", "()Ljava/lang/String;", "plugin-api"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PluginCliOptionProcessingException extends CliOptionProcessingException {
    private final Collection<AbstractCliOption> options;
    private final String pluginId;

    public /* synthetic */ PluginCliOptionProcessingException(String str, Collection collection, String str2, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, collection, str2, (i & 8) != 0 ? null : th);
    }

    public final String getPluginId() {
        return this.pluginId;
    }

    public final Collection<AbstractCliOption> getOptions() {
        return this.options;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public PluginCliOptionProcessingException(String pluginId, Collection<? extends AbstractCliOption> options, String message, Throwable th) {
        super(message, th);
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(message, "message");
        this.pluginId = pluginId;
        this.options = options;
    }
}
