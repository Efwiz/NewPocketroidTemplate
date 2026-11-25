package org.jetbrains.kotlin.compiler.plugin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CliOptions.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\b\u0010\u0014\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/compiler/plugin/CliOptionValue;", "", "pluginId", "", "optionName", "value", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getOptionName", "()Ljava/lang/String;", "getPluginId", "getValue", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "plugin-api"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CliOptionValue {
    private final String optionName;
    private final String pluginId;
    private final String value;

    public static /* synthetic */ CliOptionValue copy$default(CliOptionValue cliOptionValue, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = cliOptionValue.pluginId;
        }
        if ((i & 2) != 0) {
            str2 = cliOptionValue.optionName;
        }
        if ((i & 4) != 0) {
            str3 = cliOptionValue.value;
        }
        return cliOptionValue.copy(str, str2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPluginId() {
        return this.pluginId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getOptionName() {
        return this.optionName;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getValue() {
        return this.value;
    }

    public final CliOptionValue copy(String pluginId, String optionName, String value) {
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        Intrinsics.checkNotNullParameter(optionName, "optionName");
        Intrinsics.checkNotNullParameter(value, "value");
        return new CliOptionValue(pluginId, optionName, value);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CliOptionValue)) {
            return false;
        }
        CliOptionValue cliOptionValue = (CliOptionValue) other;
        return Intrinsics.areEqual(this.pluginId, cliOptionValue.pluginId) && Intrinsics.areEqual(this.optionName, cliOptionValue.optionName) && Intrinsics.areEqual(this.value, cliOptionValue.value);
    }

    public int hashCode() {
        return (((this.pluginId.hashCode() * 31) + this.optionName.hashCode()) * 31) + this.value.hashCode();
    }

    public CliOptionValue(String pluginId, String optionName, String value) {
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        Intrinsics.checkNotNullParameter(optionName, "optionName");
        Intrinsics.checkNotNullParameter(value, "value");
        this.pluginId = pluginId;
        this.optionName = optionName;
        this.value = value;
    }

    public final String getPluginId() {
        return this.pluginId;
    }

    public final String getOptionName() {
        return this.optionName;
    }

    public final String getValue() {
        return this.value;
    }

    public String toString() {
        return this.pluginId + ':' + this.optionName + '=' + this.value;
    }
}
