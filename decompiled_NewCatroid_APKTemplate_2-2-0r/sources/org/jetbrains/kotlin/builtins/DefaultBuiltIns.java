package org.jetbrains.kotlin.builtins;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;

/* JADX INFO: compiled from: DefaultBuiltIns.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/builtins/DefaultBuiltIns;", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "loadBuiltInsFromCurrentClassLoader", "", "(Z)V", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultBuiltIns extends KotlinBuiltIns {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<DefaultBuiltIns> Instance$delegate = LazyKt.lazy(new Function0<DefaultBuiltIns>() { // from class: org.jetbrains.kotlin.builtins.DefaultBuiltIns$Companion$Instance$2
        @Override // kotlin.jvm.functions.Function0
        public final DefaultBuiltIns invoke() {
            return new DefaultBuiltIns(false, 1, null);
        }
    });

    public DefaultBuiltIns() {
        this(false, 1, null);
    }

    public static final DefaultBuiltIns getInstance() {
        return INSTANCE.getInstance();
    }

    public DefaultBuiltIns(boolean z) {
        super(new LockBasedStorageManager("DefaultBuiltIns"));
        if (z) {
            createBuiltInsModule(false);
        }
    }

    public /* synthetic */ DefaultBuiltIns(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z);
    }

    /* JADX INFO: compiled from: DefaultBuiltIns.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\u00020\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\b\u0010\t\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/builtins/DefaultBuiltIns$Companion;", "", "()V", "Instance", "Lorg/jetbrains/kotlin/builtins/DefaultBuiltIns;", "getInstance$annotations", "getInstance", "()Lorg/jetbrains/kotlin/builtins/DefaultBuiltIns;", "Instance$delegate", "Lkotlin/Lazy;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getInstance$annotations() {
        }

        private Companion() {
        }

        public final DefaultBuiltIns getInstance() {
            return (DefaultBuiltIns) DefaultBuiltIns.Instance$delegate.getValue();
        }
    }
}
