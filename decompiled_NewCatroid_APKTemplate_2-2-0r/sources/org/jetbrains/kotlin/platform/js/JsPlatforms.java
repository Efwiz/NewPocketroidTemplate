package org.jetbrains.kotlin.platform.js;

import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.js.JavaScript;
import org.jetbrains.kotlin.platform.JsPlatform;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: JsPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u000b\fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/platform/js/JsPlatforms;", "", "()V", "allJsPlatforms", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getAllJsPlatforms", "()Ljava/util/List;", "defaultJsPlatform", "getDefaultJsPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "CompatJsPlatform", "DefaultSimpleJsPlatform", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsPlatforms {
    public static final JsPlatforms INSTANCE;
    private static final List<TargetPlatform> allJsPlatforms;

    private JsPlatforms() {
    }

    /* JADX INFO: compiled from: JsPlatform.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/platform/js/JsPlatforms$DefaultSimpleJsPlatform;", "Lorg/jetbrains/kotlin/platform/JsPlatform;", "()V", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultSimpleJsPlatform extends JsPlatform {
        public static final DefaultSimpleJsPlatform INSTANCE = new DefaultSimpleJsPlatform();

        private DefaultSimpleJsPlatform() {
        }
    }

    /* JADX INFO: compiled from: JsPlatform.kt */
    @Deprecated(level = DeprecationLevel.ERROR, message = "Should be accessed only by compatibility layer, other clients should use 'defaultJsPlatform'")
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/platform/js/JsPlatforms$CompatJsPlatform;", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "Lorg/jetbrains/kotlin/js/resolve/JsPlatform;", "()V", "platformName", "", "getPlatformName", "()Ljava/lang/String;", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CompatJsPlatform extends TargetPlatform implements org.jetbrains.kotlin.js.resolve.JsPlatform {
        public static final CompatJsPlatform INSTANCE = new CompatJsPlatform();

        private CompatJsPlatform() {
            super(SetsKt.setOf(DefaultSimpleJsPlatform.INSTANCE));
        }

        @Override // org.jetbrains.kotlin.resolve.TargetPlatform
        public String getPlatformName() {
            return JavaScript.NAME;
        }
    }

    public final TargetPlatform getDefaultJsPlatform() {
        return CompatJsPlatform.INSTANCE;
    }

    static {
        JsPlatforms jsPlatforms = new JsPlatforms();
        INSTANCE = jsPlatforms;
        allJsPlatforms = CollectionsKt.listOf(jsPlatforms.getDefaultJsPlatform());
    }

    public final List<TargetPlatform> getAllJsPlatforms() {
        return allJsPlatforms;
    }
}
