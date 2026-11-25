package org.jetbrains.kotlin.konan.target;

import java.util.Properties;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConfigurablesImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b¨\u0006\t"}, d2 = {"loadConfigurables", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "properties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "dependenciesRoot", "", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class ConfigurablesImplKt {

    /* JADX INFO: compiled from: ConfigurablesImpl.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Family.values().length];
            try {
                iArr[Family.LINUX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Family.TVOS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Family.WATCHOS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Family.IOS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Family.OSX.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Family.ANDROID.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Family.MINGW.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[Family.WASM.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[Family.ZEPHYR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final Configurables loadConfigurables(KonanTarget target, Properties properties, String str) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(properties, "properties");
        switch (WhenMappings.$EnumSwitchMapping$0[target.getFamily().ordinal()]) {
            case 1:
                return new GccConfigurablesImpl(target, properties, str);
            case 2:
            case 3:
            case 4:
            case 5:
                return new AppleConfigurablesImpl(target, properties, str);
            case 6:
                return new AndroidConfigurablesImpl(target, properties, str);
            case 7:
                return new MingwConfigurablesImpl(target, properties, str);
            case 8:
                return new WasmConfigurablesImpl(target, properties, str);
            case 9:
                return new ZephyrConfigurablesImpl(target, properties, str);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
