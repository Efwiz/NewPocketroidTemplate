package org.jetbrains.kotlin.builtins.jvm;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;

/* JADX INFO: compiled from: JvmBuiltInsCustomizer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0014¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/builtins/jvm/FallbackBuiltIns;", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "()V", "getPlatformDependentDeclarationFilter", "Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter$All;", "Companion", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FallbackBuiltIns extends KotlinBuiltIns {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final KotlinBuiltIns Instance = new FallbackBuiltIns();

    public static final KotlinBuiltIns getInstance() {
        return INSTANCE.getInstance();
    }

    private FallbackBuiltIns() {
        super(new LockBasedStorageManager("FallbackBuiltIns"));
        createBuiltInsModule(true);
    }

    /* JADX INFO: compiled from: JvmBuiltInsCustomizer.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/builtins/jvm/FallbackBuiltIns$Companion;", "", "()V", "Instance", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getInstance$annotations", "getInstance", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getInstance$annotations() {
        }

        private Companion() {
        }

        public final KotlinBuiltIns getInstance() {
            return FallbackBuiltIns.Instance;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.builtins.KotlinBuiltIns
    public PlatformDependentDeclarationFilter.All getPlatformDependentDeclarationFilter() {
        return PlatformDependentDeclarationFilter.All.INSTANCE;
    }
}
