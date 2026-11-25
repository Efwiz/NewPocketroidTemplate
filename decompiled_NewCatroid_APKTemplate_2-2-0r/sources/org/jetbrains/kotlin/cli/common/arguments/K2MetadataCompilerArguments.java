package org.jetbrains.kotlin.cli.common.arguments;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.AnalysisFlag;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersion;

/* JADX INFO: compiled from: K2MetadataCompilerArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\r\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 12\u00020\u0001:\u00011B\u0005¢\u0006\u0002\u0010\u0002J(\u0010 \u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\"\u0012\u0004\u0012\u00020#0!2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0016J,\u0010(\u001a\u00020)2\"\u0010*\u001a\u001e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020-0+j\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020-`.H\u0014J\b\u0010/\u001a\u000200H\u0014R*\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR*\u0010\n\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR&\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R8\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00132\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00138\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R*\u0010\u001a\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0007\"\u0004\b\u001c\u0010\tR8\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00132\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00138\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b\u001e\u0010\u0016\"\u0004\b\u001f\u0010\u0018¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/K2MetadataCompilerArguments;", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "()V", "value", "", ModuleXmlParser.CLASSPATH, "getClasspath", "()Ljava/lang/String;", "setClasspath", "(Ljava/lang/String;)V", FirebaseAnalytics.Param.DESTINATION, "getDestination", "setDestination", "", "enabledInJps", "getEnabledInJps", "()Z", "setEnabledInJps", "(Z)V", "", "friendPaths", "getFriendPaths", "()[Ljava/lang/String;", "setFriendPaths", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "moduleName", "getModuleName", "setModuleName", "refinesPaths", "getRefinesPaths", "setRefinesPaths", "configureAnalysisFlags", "", "Lorg/jetbrains/kotlin/config/AnalysisFlag;", "", "collector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "languageVersion", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "configureExtraLanguageFeatures", "", "map", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "Lorg/jetbrains/kotlin/config/LanguageFeature$State;", "Lkotlin/collections/HashMap;", "copyOf", "Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2MetadataCompilerArguments extends CommonCompilerArguments {
    private static final long serialVersionUID = 0;

    @Argument(description = "Paths where to find library .kotlin_metadata files", shortName = "-cp", value = "-classpath", valueDescription = "<path>")
    private String classpath;

    @Argument(description = "Destination for generated .kotlin_metadata files", value = "-d", valueDescription = "<directory|jar>")
    private String destination;

    @Argument(description = "Enable in JPS", value = "-Xjps")
    private boolean enabledInJps;

    @Argument(description = "Paths to output directories for friend modules (whose internals should be visible)", value = "-Xfriend-paths", valueDescription = "<path>")
    private String[] friendPaths;

    @Argument(description = "Name of the generated .kotlin_module file", value = "-module-name", valueDescription = "<name>")
    private String moduleName;

    @Argument(description = "Paths to output directories for refined modules (whose expects this module can actualize)", value = "-Xrefines-paths", valueDescription = "<path>")
    private String[] refinesPaths;

    public final String getDestination() {
        return this.destination;
    }

    public final void setDestination(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.destination = str;
    }

    public final String getClasspath() {
        return this.classpath;
    }

    public final void setClasspath(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.classpath = str;
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final void setModuleName(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.moduleName = str;
    }

    public final boolean getEnabledInJps() {
        return this.enabledInJps;
    }

    public final void setEnabledInJps(boolean z) {
        checkFrozen();
        this.enabledInJps = z;
    }

    public final String[] getFriendPaths() {
        return this.friendPaths;
    }

    public final void setFriendPaths(String[] strArr) {
        checkFrozen();
        this.friendPaths = strArr;
    }

    public final String[] getRefinesPaths() {
        return this.refinesPaths;
    }

    public final void setRefinesPaths(String[] strArr) {
        checkFrozen();
        this.refinesPaths = strArr;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.Freezable
    protected Freezable copyOf() {
        return K2MetadataCompilerArgumentsCopyGeneratedKt.copyK2MetadataCompilerArguments(this, new K2MetadataCompilerArguments());
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    public Map<AnalysisFlag<?>, Object> configureAnalysisFlags(MessageCollector collector, LanguageVersion languageVersion) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(languageVersion, "languageVersion");
        Map<AnalysisFlag<?>, Object> mapConfigureAnalysisFlags = super.configureAnalysisFlags(collector, languageVersion);
        mapConfigureAnalysisFlags.put(AnalysisFlags.getMetadataCompilation(), true);
        return mapConfigureAnalysisFlags;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    protected void configureExtraLanguageFeatures(HashMap<LanguageFeature, LanguageFeature.State> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        map.put(LanguageFeature.MultiPlatformProjects, LanguageFeature.State.ENABLED);
    }
}
