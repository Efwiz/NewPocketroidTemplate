package org.jetbrains.kotlin.analyzer.common;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.platform.CommonPlatforms;
import org.jetbrains.kotlin.resolve.TargetPlatform;

/* JADX INFO: compiled from: CommonPlatform.kt */
/* JADX INFO: loaded from: classes2.dex */
@Deprecated(level = DeprecationLevel.ERROR, message = "This class is deprecated and will be removed soon, use API from 'org.jetbrains.kotlin.platform.*' packages instead", replaceWith = @ReplaceWith(expression = "CommonPlatforms.defaultCommonPlatform", imports = {"org.jetbrains.kotlin.platform.CommonPlatforms"}))
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u0000 \u00022\u00020\u0001:\u0001\u0002ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0003À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/common/CommonPlatform;", "Lorg/jetbrains/kotlin/resolve/TargetPlatform;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CommonPlatform extends TargetPlatform {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final CommonPlatform INSTANCE = CommonPlatforms.CompatCommonPlatform.INSTANCE;

    /* JADX INFO: compiled from: CommonPlatform.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0001¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/common/CommonPlatform$Companion;", "", "()V", JvmAbi.INSTANCE_FIELD, "Lorg/jetbrains/kotlin/analyzer/common/CommonPlatform;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }
}
