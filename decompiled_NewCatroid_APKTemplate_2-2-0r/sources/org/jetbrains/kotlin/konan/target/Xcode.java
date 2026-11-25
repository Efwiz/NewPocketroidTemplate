package org.jetbrains.kotlin.konan.target;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Xcode.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\n\bf\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eJ\u0010\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0003H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0012\u0010\n\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0012\u0010\f\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005R\u0012\u0010\u000e\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0012\u0010\u0010\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0012\u0010\u0012\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005R\u0012\u0010\u0014\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005R\u0012\u0010\u001a\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Xcode;", "", "additionalTools", "", "getAdditionalTools", "()Ljava/lang/String;", "appletvosSdk", "getAppletvosSdk", "appletvsimulatorSdk", "getAppletvsimulatorSdk", "iphoneosSdk", "getIphoneosSdk", "iphonesimulatorSdk", "getIphonesimulatorSdk", "macosxSdk", "getMacosxSdk", "simulatorRuntimes", "getSimulatorRuntimes", "toolchain", "getToolchain", "version", "Lorg/jetbrains/kotlin/konan/target/XcodeVersion;", "getVersion", "()Lorg/jetbrains/kotlin/konan/target/XcodeVersion;", "watchosSdk", "getWatchosSdk", "watchsimulatorSdk", "getWatchsimulatorSdk", "pathToPlatformSdk", "platformName", "Companion", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface Xcode {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    String getAdditionalTools();

    String getAppletvosSdk();

    String getAppletvsimulatorSdk();

    String getIphoneosSdk();

    String getIphonesimulatorSdk();

    String getMacosxSdk();

    String getSimulatorRuntimes();

    String getToolchain();

    XcodeVersion getVersion();

    String getWatchosSdk();

    String getWatchsimulatorSdk();

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    default String pathToPlatformSdk(String platformName) {
        Intrinsics.checkNotNullParameter(platformName, "platformName");
        String lowerCase = platformName.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        switch (lowerCase.hashCode()) {
            case -2093168064:
                if (lowerCase.equals("appletvos")) {
                    return getAppletvosSdk();
                }
                break;
            case -1081748635:
                if (lowerCase.equals("macosx")) {
                    return getMacosxSdk();
                }
                break;
            case -865216769:
                if (lowerCase.equals("iphonesimulator")) {
                    return getIphonesimulatorSdk();
                }
                break;
            case -586471384:
                if (lowerCase.equals("appletvsimulator")) {
                    return getAppletvsimulatorSdk();
                }
                break;
            case 913012757:
                if (lowerCase.equals("watchsimulator")) {
                    return getWatchsimulatorSdk();
                }
                break;
            case 1125964531:
                if (lowerCase.equals("watchos")) {
                    return getWatchosSdk();
                }
                break;
            case 2010976073:
                if (lowerCase.equals("iphoneos")) {
                    return getIphoneosSdk();
                }
                break;
        }
        throw new IllegalStateException(("Unknown Apple platform: " + platformName).toString());
    }

    /* JADX INFO: compiled from: Xcode.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u00048FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Xcode$Companion;", "", "()V", "current", "Lorg/jetbrains/kotlin/konan/target/Xcode;", "getCurrent$annotations", "getCurrent", "()Lorg/jetbrains/kotlin/konan/target/Xcode;", "findCurrent", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        @Deprecated(level = DeprecationLevel.WARNING, message = "", replaceWith = @ReplaceWith(expression = "this.findCurrent()", imports = {}))
        public static /* synthetic */ void getCurrent$annotations() {
        }

        private Companion() {
        }

        public final Xcode getCurrent() {
            return findCurrent();
        }

        public final Xcode findCurrent() {
            return new CurrentXcode();
        }
    }
}
