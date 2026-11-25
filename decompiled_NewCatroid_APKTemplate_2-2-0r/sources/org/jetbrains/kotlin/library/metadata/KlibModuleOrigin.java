package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.descriptors.ModuleCapability;

/* JADX INFO: compiled from: KlibModuleOrigin.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0004\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibModuleOrigin;", "", "()V", "Companion", "Lorg/jetbrains/kotlin/library/metadata/CompiledKlibModuleOrigin;", "Lorg/jetbrains/kotlin/library/metadata/SyntheticModulesOrigin;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KlibModuleOrigin {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ModuleCapability<KlibModuleOrigin> CAPABILITY = new ModuleCapability<>("KlibModuleOrigin");

    public /* synthetic */ KlibModuleOrigin(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private KlibModuleOrigin() {
    }

    /* JADX INFO: compiled from: KlibModuleOrigin.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibModuleOrigin$Companion;", "", "()V", "CAPABILITY", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "Lorg/jetbrains/kotlin/library/metadata/KlibModuleOrigin;", "getCAPABILITY", "()Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ModuleCapability<KlibModuleOrigin> getCAPABILITY() {
            return KlibModuleOrigin.CAPABILITY;
        }
    }
}
