package org.jetbrains.kotlin.cli.jvm.config;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: K2MetadataConfigurationKeys.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R+\u0010\u0003\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0006 \u0007*\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00050\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR+\u0010\n\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0006 \u0007*\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00050\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/config/K2MetadataConfigurationKeys;", "", "()V", "FRIEND_PATHS", "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getFRIEND_PATHS", "()Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "REFINES_PATHS", "getREFINES_PATHS", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2MetadataConfigurationKeys {
    private static final CompilerConfigurationKey<List<String>> FRIEND_PATHS;
    public static final K2MetadataConfigurationKeys INSTANCE = new K2MetadataConfigurationKeys();
    private static final CompilerConfigurationKey<List<String>> REFINES_PATHS;

    private K2MetadataConfigurationKeys() {
    }

    static {
        CompilerConfigurationKey<List<String>> compilerConfigurationKeyCreate = CompilerConfigurationKey.create("friend module paths");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate, "create(...)");
        FRIEND_PATHS = compilerConfigurationKeyCreate;
        CompilerConfigurationKey<List<String>> compilerConfigurationKeyCreate2 = CompilerConfigurationKey.create("refined module paths");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate2, "create(...)");
        REFINES_PATHS = compilerConfigurationKeyCreate2;
    }

    public final CompilerConfigurationKey<List<String>> getFRIEND_PATHS() {
        return FRIEND_PATHS;
    }

    public final CompilerConfigurationKey<List<String>> getREFINES_PATHS() {
        return REFINES_PATHS;
    }
}
