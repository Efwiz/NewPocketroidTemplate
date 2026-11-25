package org.jetbrains.kotlin.konan.target;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.analyzer.ResolverForProject;
import org.jetbrains.kotlin.konan.KonanExternalToolFailure;
import org.jetbrains.kotlin.konan.MissingXcodeException;
import org.jetbrains.kotlin.konan.exec.Command;
import org.jetbrains.kotlin.konan.file.File;

/* JADX INFO: compiled from: Xcode.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\"\n\u0002\u0010\u0011\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010-\u001a\u00020\u00042\u0006\u0010.\u001a\u00020\u0004H\u0002J\u0010\u0010/\u001a\u00020\u00042\u0006\u00100\u001a\u00020\u0004H\u0002J!\u00101\u001a\u00020\u00042\u0012\u00102\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000403\"\u00020\u0004H\u0002¢\u0006\u0002\u00104J\f\u00105\u001a\u00020\u0010*\u00020\u0004H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006R\u001b\u0010\f\u001a\u00020\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\b\u001a\u0004\b\r\u0010\u0006R\u0014\u0010\u000f\u001a\u00020\u00108@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0013\u001a\u00020\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\b\u001a\u0004\b\u0014\u0010\u0006R\u001b\u0010\u0016\u001a\u00020\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\b\u001a\u0004\b\u0017\u0010\u0006R\u001b\u0010\u0019\u001a\u00020\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\b\u001a\u0004\b\u001a\u0010\u0006R\u001b\u0010\u001c\u001a\u00020\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\b\u001a\u0004\b\u001d\u0010\u0006R\u001b\u0010\u001f\u001a\u00020\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\b\u001a\u0004\b \u0010\u0006R\u001b\u0010\"\u001a\u00020\u00108VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\b\u001a\u0004\b#\u0010\u0012R\u001b\u0010%\u001a\u00020\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\b\u001a\u0004\b&\u0010\u0006R\u001b\u0010(\u001a\u00020\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\b\u001a\u0004\b)\u0010\u0006R\u0014\u0010+\u001a\u00020\u00108@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\u0012¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/CurrentXcode;", "Lorg/jetbrains/kotlin/konan/target/Xcode;", "()V", "additionalTools", "", "getAdditionalTools", "()Ljava/lang/String;", "additionalTools$delegate", "Lkotlin/Lazy;", "appletvosSdk", "getAppletvosSdk", "appletvosSdk$delegate", "appletvsimulatorSdk", "getAppletvsimulatorSdk", "appletvsimulatorSdk$delegate", "bundleVersion", "Lorg/jetbrains/kotlin/konan/target/XcodeVersion;", "getBundleVersion$kotlin_native_utils", "()Lorg/jetbrains/kotlin/konan/target/XcodeVersion;", "iphoneosSdk", "getIphoneosSdk", "iphoneosSdk$delegate", "iphonesimulatorSdk", "getIphonesimulatorSdk", "iphonesimulatorSdk$delegate", "macosxSdk", "getMacosxSdk", "macosxSdk$delegate", "simulatorRuntimes", "getSimulatorRuntimes", "simulatorRuntimes$delegate", "toolchain", "getToolchain", "toolchain$delegate", "version", "getVersion", "version$delegate", "watchosSdk", "getWatchosSdk", "watchosSdk$delegate", "watchsimulatorSdk", "getWatchsimulatorSdk", "watchsimulatorSdk$delegate", "xcodebuildVersion", "getXcodebuildVersion$kotlin_native_utils", "bash", "command", "getSdkPath", ResolverForProject.resolverForSdkName, "xcrun", "args", "", "([Ljava/lang/String;)Ljava/lang/String;", "parseXcodeVersion", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CurrentXcode implements Xcode {

    /* JADX INFO: renamed from: toolchain$delegate, reason: from kotlin metadata */
    private final Lazy toolchain = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$toolchain$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return new File(this.this$0.xcrun("-f", "ld")).getParentFile().getParentFile().getParentFile().getAbsolutePath();
        }
    });

    /* JADX INFO: renamed from: additionalTools$delegate, reason: from kotlin metadata */
    private final Lazy additionalTools = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$additionalTools$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return new File(this.this$0.xcrun("-f", "bitcode-build-tool")).getParentFile().getParentFile().getAbsolutePath();
        }
    });

    /* JADX INFO: renamed from: simulatorRuntimes$delegate, reason: from kotlin metadata */
    private final Lazy simulatorRuntimes = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$simulatorRuntimes$2
        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return CollectionsKt.joinToString$default(Command.getOutputLines$default(new Command("/usr/bin/xcrun", "simctl", SchemaSymbols.ATTVAL_LIST, "runtimes", "-j"), false, 1, null), "\n", null, null, 0, null, null, 62, null);
        }
    });

    /* JADX INFO: renamed from: macosxSdk$delegate, reason: from kotlin metadata */
    private final Lazy macosxSdk = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$macosxSdk$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return this.this$0.getSdkPath("macosx");
        }
    });

    /* JADX INFO: renamed from: iphoneosSdk$delegate, reason: from kotlin metadata */
    private final Lazy iphoneosSdk = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$iphoneosSdk$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return this.this$0.getSdkPath("iphoneos");
        }
    });

    /* JADX INFO: renamed from: iphonesimulatorSdk$delegate, reason: from kotlin metadata */
    private final Lazy iphonesimulatorSdk = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$iphonesimulatorSdk$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return this.this$0.getSdkPath("iphonesimulator");
        }
    });

    /* JADX INFO: renamed from: appletvosSdk$delegate, reason: from kotlin metadata */
    private final Lazy appletvosSdk = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$appletvosSdk$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return this.this$0.getSdkPath("appletvos");
        }
    });

    /* JADX INFO: renamed from: appletvsimulatorSdk$delegate, reason: from kotlin metadata */
    private final Lazy appletvsimulatorSdk = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$appletvsimulatorSdk$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return this.this$0.getSdkPath("appletvsimulator");
        }
    });

    /* JADX INFO: renamed from: watchosSdk$delegate, reason: from kotlin metadata */
    private final Lazy watchosSdk = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$watchosSdk$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return this.this$0.getSdkPath("watchos");
        }
    });

    /* JADX INFO: renamed from: watchsimulatorSdk$delegate, reason: from kotlin metadata */
    private final Lazy watchsimulatorSdk = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$watchsimulatorSdk$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return this.this$0.getSdkPath("watchsimulator");
        }
    });

    /* JADX INFO: renamed from: version$delegate, reason: from kotlin metadata */
    private final Lazy version = LazyKt.lazy(new Function0<XcodeVersion>() { // from class: org.jetbrains.kotlin.konan.target.CurrentXcode$version$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final XcodeVersion invoke() {
            try {
                return this.this$0.getBundleVersion$kotlin_native_utils();
            } catch (KonanExternalToolFailure unused) {
                return this.this$0.getXcodebuildVersion$kotlin_native_utils();
            }
        }
    });

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public String getToolchain() {
        return (String) this.toolchain.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public String getAdditionalTools() {
        return (String) this.additionalTools.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public String getSimulatorRuntimes() {
        return (String) this.simulatorRuntimes.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public String getMacosxSdk() {
        return (String) this.macosxSdk.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public String getIphoneosSdk() {
        return (String) this.iphoneosSdk.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public String getIphonesimulatorSdk() {
        return (String) this.iphonesimulatorSdk.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public String getAppletvosSdk() {
        return (String) this.appletvosSdk.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public String getAppletvsimulatorSdk() {
        return (String) this.appletvsimulatorSdk.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public String getWatchosSdk() {
        return (String) this.watchosSdk.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public String getWatchsimulatorSdk() {
        return (String) this.watchsimulatorSdk.getValue();
    }

    public final XcodeVersion getXcodebuildVersion$kotlin_native_utils() {
        return parseXcodeVersion(StringsKt.removePrefix(xcrun("xcodebuild", "-version"), (CharSequence) "Xcode "));
    }

    public final XcodeVersion getBundleVersion$kotlin_native_utils() {
        return parseXcodeVersion(bash("/usr/libexec/PlistBuddy \"$(xcode-select -print-path)/../Info.plist\" -c \"Print :CFBundleShortVersionString\""));
    }

    @Override // org.jetbrains.kotlin.konan.target.Xcode
    public XcodeVersion getVersion() {
        return (XcodeVersion) this.version.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String xcrun(String... args) throws MissingXcodeException {
        try {
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add("/usr/bin/xcrun");
            spreadBuilder.addSpread(args);
            return (String) CollectionsKt.first(Command.getOutputLines$default(new Command((String[]) spreadBuilder.toArray(new String[spreadBuilder.size()])), false, 1, null));
        } catch (KonanExternalToolFailure e) {
            throw new MissingXcodeException(StringsKt.trimIndent("\n                An error occurred during an xcrun execution. Make sure that Xcode and its command line tools are properly installed.\n                Failed command: /usr/bin/xcrun " + ArraysKt.joinToString$default(args, " ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null) + "\n                Try running this command in Terminal and fix the errors by making Xcode (and its command line tools) configuration correct.\n            "), e);
        }
    }

    private final String bash(String command) {
        return CollectionsKt.joinToString$default(Command.getOutputLines$default(new Command("/bin/bash", "-c", command), false, 1, null), "\n", null, null, 0, null, null, 62, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getSdkPath(String sdk) {
        return xcrun("--sdk", sdk, "--show-sdk-path");
    }

    private final XcodeVersion parseXcodeVersion(String str) throws MissingXcodeException {
        XcodeVersion xcodeVersion = XcodeVersion.INSTANCE.parse(str);
        if (xcodeVersion != null) {
            return xcodeVersion;
        }
        throw new MissingXcodeException("Couldn't parse Xcode version from '" + str + '\'', null, 2, null);
    }
}
