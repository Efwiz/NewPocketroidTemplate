package org.jetbrains.kotlin.konan.target;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader;

/* JADX INFO: compiled from: ConfigurablesImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\u0010\u0006\u001a\u00060\u0007j\u0002`\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/GccConfigurablesImpl;", "Lorg/jetbrains/kotlin/konan/target/GccConfigurables;", "Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;", "Lorg/jetbrains/kotlin/konan/target/ConfigurablesWithEmulator;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "properties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "dependenciesRoot", "", "(Lorg/jetbrains/kotlin/konan/target/KonanTarget;Ljava/util/Properties;Ljava/lang/String;)V", "dependencies", "", "getDependencies", "()Ljava/util/List;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class GccConfigurablesImpl extends KonanPropertiesLoader implements ConfigurablesWithEmulator, GccConfigurables {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GccConfigurablesImpl(KonanTarget target, Properties properties, String str) {
        super(target, properties, str, null, 8, null);
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(properties, "properties");
    }

    @Override // org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader
    public List<String> getDependencies() {
        return CollectionsKt.plus((Collection) super.getDependencies(), (Iterable) CollectionsKt.listOfNotNull(getEmulatorDependency()));
    }
}
