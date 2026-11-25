package org.jetbrains.kotlin.platform;

import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.analyzer.common.CommonPlatform;
import org.jetbrains.kotlin.platform.js.JsPlatforms;
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms;
import org.jetbrains.kotlin.platform.konan.NativePlatforms;
import org.jetbrains.kotlin.platform.wasm.WasmPlatforms;

/* JADX INFO: compiled from: CommonPlatforms.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\rB\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/platform/CommonPlatforms;", "", "()V", "allDefaultTargetPlatforms", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getAllDefaultTargetPlatforms", "()Ljava/util/List;", "allSimplePlatforms", "getAllSimplePlatforms", "defaultCommonPlatform", "getDefaultCommonPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "CompatCommonPlatform", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonPlatforms {
    public static final CommonPlatforms INSTANCE = new CommonPlatforms();

    private CommonPlatforms() {
    }

    /* JADX INFO: compiled from: CommonPlatforms.kt */
    @Deprecated(level = DeprecationLevel.ERROR, message = "Should be accessed only by compatibility layer, other clients should use 'unspecifiedJvmPlatform'")
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/platform/CommonPlatforms$CompatCommonPlatform;", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "Lorg/jetbrains/kotlin/analyzer/common/CommonPlatform;", "()V", "platformName", "", "getPlatformName", "()Ljava/lang/String;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CompatCommonPlatform extends TargetPlatform implements CommonPlatform {
        public static final CompatCommonPlatform INSTANCE = new CompatCommonPlatform();

        /* JADX WARN: Multi-variable type inference failed */
        private CompatCommonPlatform() {
            super(SetsKt.setOf((Object[]) new SimplePlatform[]{CollectionsKt.single(JvmPlatforms.INSTANCE.getUnspecifiedJvmPlatform()), CollectionsKt.single(JsPlatforms.INSTANCE.getDefaultJsPlatform()), CollectionsKt.single(WasmPlatforms.Default.INSTANCE), CollectionsKt.single(NativePlatforms.INSTANCE.getUnspecifiedNativePlatform())}));
        }

        @Override // org.jetbrains.kotlin.resolve.TargetPlatform
        public String getPlatformName() {
            return "Default";
        }
    }

    public final TargetPlatform getDefaultCommonPlatform() {
        return CompatCommonPlatform.INSTANCE;
    }

    public final List<TargetPlatform> getAllSimplePlatforms() {
        return SequencesKt.toList(SequencesKt.sequence(new CommonPlatforms$allSimplePlatforms$1(null)));
    }

    public final List<TargetPlatform> getAllDefaultTargetPlatforms() {
        return SequencesKt.toList(SequencesKt.sequence(new CommonPlatforms$allDefaultTargetPlatforms$1(null)));
    }
}
