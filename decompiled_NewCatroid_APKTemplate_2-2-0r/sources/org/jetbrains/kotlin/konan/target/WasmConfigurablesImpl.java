package org.jetbrains.kotlin.konan.target;

import java.util.Properties;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader;

/* JADX INFO: compiled from: ConfigurablesImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/WasmConfigurablesImpl;", "Lorg/jetbrains/kotlin/konan/target/WasmConfigurables;", "Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "properties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "dependenciesRoot", "", "(Lorg/jetbrains/kotlin/konan/target/KonanTarget;Ljava/util/Properties;Ljava/lang/String;)V", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class WasmConfigurablesImpl extends KonanPropertiesLoader implements WasmConfigurables {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmConfigurablesImpl(KonanTarget target, Properties properties, String str) {
        super(target, properties, str, null, 8, null);
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(properties, "properties");
    }
}
