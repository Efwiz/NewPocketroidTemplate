package org.jetbrains.kotlin.cli.plugins;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.compiler.plugin.CliOptionValue;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PluginsOptionsParser.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0005HÆ\u0003J3\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0005HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/cli/plugins/PluginClasspathAndOptions;", "", "rawArgument", "", ModuleXmlParser.CLASSPATH, "", "options", "Lorg/jetbrains/kotlin/compiler/plugin/CliOptionValue;", "(Ljava/lang/String;Ljava/util/List;Ljava/util/List;)V", "getClasspath", "()Ljava/util/List;", "getOptions", "getRawArgument", "()Ljava/lang/String;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class PluginClasspathAndOptions {
    private final List<String> classpath;
    private final List<CliOptionValue> options;
    private final String rawArgument;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ PluginClasspathAndOptions copy$default(PluginClasspathAndOptions pluginClasspathAndOptions, String str, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = pluginClasspathAndOptions.rawArgument;
        }
        if ((i & 2) != 0) {
            list = pluginClasspathAndOptions.classpath;
        }
        if ((i & 4) != 0) {
            list2 = pluginClasspathAndOptions.options;
        }
        return pluginClasspathAndOptions.copy(str, list, list2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getRawArgument() {
        return this.rawArgument;
    }

    public final List<String> component2() {
        return this.classpath;
    }

    public final List<CliOptionValue> component3() {
        return this.options;
    }

    public final PluginClasspathAndOptions copy(String rawArgument, List<String> classpath, List<CliOptionValue> options) {
        Intrinsics.checkNotNullParameter(rawArgument, "rawArgument");
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(options, "options");
        return new PluginClasspathAndOptions(rawArgument, classpath, options);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PluginClasspathAndOptions)) {
            return false;
        }
        PluginClasspathAndOptions pluginClasspathAndOptions = (PluginClasspathAndOptions) other;
        return Intrinsics.areEqual(this.rawArgument, pluginClasspathAndOptions.rawArgument) && Intrinsics.areEqual(this.classpath, pluginClasspathAndOptions.classpath) && Intrinsics.areEqual(this.options, pluginClasspathAndOptions.options);
    }

    public int hashCode() {
        return (((this.rawArgument.hashCode() * 31) + this.classpath.hashCode()) * 31) + this.options.hashCode();
    }

    public String toString() {
        return "PluginClasspathAndOptions(rawArgument=" + this.rawArgument + ", classpath=" + this.classpath + ", options=" + this.options + ')';
    }

    public PluginClasspathAndOptions(String rawArgument, List<String> classpath, List<CliOptionValue> options) {
        Intrinsics.checkNotNullParameter(rawArgument, "rawArgument");
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(options, "options");
        this.rawArgument = rawArgument;
        this.classpath = classpath;
        this.options = options;
    }

    public final String getRawArgument() {
        return this.rawArgument;
    }

    public final List<String> getClasspath() {
        return this.classpath;
    }

    public final List<CliOptionValue> getOptions() {
        return this.options;
    }
}
