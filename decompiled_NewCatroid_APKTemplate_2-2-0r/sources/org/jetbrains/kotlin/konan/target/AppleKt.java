package org.jetbrains.kotlin.konan.target;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Apple.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"platformName", "", "Lorg/jetbrains/kotlin/konan/target/AppleConfigurables;", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class AppleKt {

    /* JADX INFO: compiled from: Apple.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Family.values().length];
            try {
                iArr[Family.OSX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Family.IOS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Family.TVOS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Family.WATCHOS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final String platformName(AppleConfigurables appleConfigurables) {
        Intrinsics.checkNotNullParameter(appleConfigurables, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[appleConfigurables.getTarget().getFamily().ordinal()];
        if (i == 1) {
            return "MacOSX";
        }
        if (i == 2) {
            if (TargetTripleKt.isSimulator(appleConfigurables.getTargetTriple())) {
                return "iPhoneSimulator";
            }
            return "iPhoneOS";
        }
        if (i == 3) {
            if (TargetTripleKt.isSimulator(appleConfigurables.getTargetTriple())) {
                return "AppleTVSimulator";
            }
            return "AppleTVOS";
        }
        if (i == 4) {
            if (TargetTripleKt.isSimulator(appleConfigurables.getTargetTriple())) {
                return "WatchSimulator";
            }
            return "WatchOS";
        }
        throw new IllegalStateException(("Not an Apple target: " + appleConfigurables.getTarget()).toString());
    }
}
