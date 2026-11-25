package org.jetbrains.kotlin.com.intellij.ide.plugins;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.nio.file.Path;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;

/* JADX INFO: compiled from: PluginLoadingError.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB7\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0013\u0010\u0004\u001a\u000f\u0012\t\u0012\u00070\u0006¢\u0006\u0002\b\u0007\u0018\u00010\u0005\u0012\u0011\u0010\b\u001a\r\u0012\t\u0012\u00070\u0006¢\u0006\u0002\b\t0\u0005¢\u0006\u0002\u0010\nBK\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0013\u0010\u0004\u001a\u000f\u0012\t\u0012\u00070\u0006¢\u0006\u0002\b\u0007\u0018\u00010\u0005\u0012\u0011\u0010\b\u001a\r\u0012\t\u0012\u00070\u0006¢\u0006\u0002\b\t0\u0005\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\r\u0010\u001b\u001a\u00070\u0006¢\u0006\u0002\b\u0014H\u0016R\u0011\u0010\u0010\u001a\u00020\u00068G¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0004\u001a\u000f\u0012\t\u0012\u00070\u0006¢\u0006\u0002\b\u0007\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\u00070\u0006¢\u0006\u0002\b\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u00068G¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0012R\u0019\u0010\b\u001a\r\u0012\t\u0012\u00070\u0006¢\u0006\u0002\b\t0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginLoadingError;", "", "plugin", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptor;", "detailedMessageSupplier", "Ljava/util/function/Supplier;", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsContexts$DetailedDescription;", "shortMessageSupplier", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsContexts$Label;", "(Lcom/intellij/ide/plugins/IdeaPluginDescriptor;Ljava/util/function/Supplier;Ljava/util/function/Supplier;)V", "isNotifyUser", "", "disabledDependency", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId;", "(Lcom/intellij/ide/plugins/IdeaPluginDescriptor;Ljava/util/function/Supplier;Ljava/util/function/Supplier;ZLcom/intellij/openapi/extensions/PluginId;)V", "detailedMessage", "getDetailedMessage", "()Ljava/lang/String;", "internalMessage", "Lorg/jetbrains/annotations/NonNls;", "getInternalMessage", "()Z", "getPlugin", "()Lcom/intellij/ide/plugins/IdeaPluginDescriptor;", "shortMessage", "getShortMessage", "toString", "Companion", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class PluginLoadingError {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Supplier<String> detailedMessageSupplier;
    public final PluginId disabledDependency;
    private final boolean isNotifyUser;
    private final IdeaPluginDescriptor plugin;
    private final Supplier<String> shortMessageSupplier;

    public PluginLoadingError(IdeaPluginDescriptor plugin, Supplier<String> supplier, Supplier<String> shortMessageSupplier, boolean z, PluginId pluginId) {
        Intrinsics.checkNotNullParameter(plugin, "plugin");
        Intrinsics.checkNotNullParameter(shortMessageSupplier, "shortMessageSupplier");
        this.plugin = plugin;
        this.detailedMessageSupplier = supplier;
        this.shortMessageSupplier = shortMessageSupplier;
        this.isNotifyUser = z;
        this.disabledDependency = pluginId;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ PluginLoadingError(IdeaPluginDescriptor ideaPluginDescriptor, Supplier supplier, Supplier supplier2, boolean z, PluginId pluginId, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 16) != 0) {
            pluginId = null;
        }
        this(ideaPluginDescriptor, supplier, supplier2, z, pluginId);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PluginLoadingError(IdeaPluginDescriptor plugin, Supplier<String> supplier, Supplier<String> shortMessageSupplier) {
        this(plugin, supplier, shortMessageSupplier, true, null, 16, null);
        Intrinsics.checkNotNullParameter(plugin, "plugin");
        Intrinsics.checkNotNullParameter(shortMessageSupplier, "shortMessageSupplier");
    }

    public String toString() {
        return getInternalMessage();
    }

    public final String getInternalMessage() {
        Companion companion = INSTANCE;
        IdeaPluginDescriptor ideaPluginDescriptor = this.plugin;
        Supplier<String> supplier = this.detailedMessageSupplier;
        if (supplier == null) {
            supplier = this.shortMessageSupplier;
        }
        String str = supplier.get();
        Intrinsics.checkNotNullExpressionValue(str, "(detailedMessageSupplier…ortMessageSupplier).get()");
        return companion.formatErrorMessage(ideaPluginDescriptor, str);
    }

    /* JADX INFO: compiled from: PluginLoadingError.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginLoadingError$Companion;", "", "()V", "formatErrorMessage", "", "Lorg/jetbrains/annotations/NonNls;", "descriptor", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptor;", CrashHianalyticsData.MESSAGE, "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String formatErrorMessage(IdeaPluginDescriptor descriptor, String message) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(message, "message");
            StringBuilder sb = new StringBuilder("The ");
            sb.append(descriptor.getName()).append(" (id=").append(descriptor.getId()).append(", path=");
            Path pluginPath = descriptor.getPath();
            Intrinsics.checkNotNullExpressionValue(pluginPath, "descriptor.pluginPath");
            sb.append(IdeaPluginDescriptorImplKt.pluginPathToUserString(pluginPath));
            String version = descriptor.getVersion();
            if (version != null && !descriptor.getIsBundled() && !Intrinsics.areEqual(version, PluginManagerCore.getBuildNumber().asString())) {
                sb.append(", version=").append(version);
            }
            sb.append(") plugin ").append(message);
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "builder.toString()");
            return string;
        }
    }
}
