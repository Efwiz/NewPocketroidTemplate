package org.jetbrains.kotlin.fir;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.util.ConeTypeRegistry;
import org.jetbrains.kotlin.util.AttributeArrayOwner;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirModuleCapabilities.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \n2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\nB\u0015\b\u0002\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004¢\u0006\u0002\u0010\u0005R \u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00078TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirModuleCapabilities;", "Lorg/jetbrains/kotlin/util/AttributeArrayOwner;", "Lorg/jetbrains/kotlin/fir/FirModuleCapability;", "capabilities", "", "(Ljava/util/List;)V", "typeRegistry", "Lorg/jetbrains/kotlin/util/TypeRegistry;", "getTypeRegistry", "()Lorg/jetbrains/kotlin/util/TypeRegistry;", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirModuleCapabilities extends AttributeArrayOwner<FirModuleCapability, FirModuleCapability> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FirModuleCapabilities Empty = new FirModuleCapabilities(CollectionsKt.emptyList());

    public /* synthetic */ FirModuleCapabilities(List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(list);
    }

    private FirModuleCapabilities(List<? extends FirModuleCapability> list) {
        for (FirModuleCapability firModuleCapability : list) {
            registerComponent(firModuleCapability.getKey(), firModuleCapability);
        }
    }

    /* JADX INFO: compiled from: FirModuleCapabilities.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0014\u0010\b\u001a\u00020\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirModuleCapabilities$Companion;", "Lorg/jetbrains/kotlin/fir/util/ConeTypeRegistry;", "Lorg/jetbrains/kotlin/fir/FirModuleCapability;", "()V", "Empty", "Lorg/jetbrains/kotlin/fir/FirModuleCapabilities;", "getEmpty", "()Lorg/jetbrains/kotlin/fir/FirModuleCapabilities;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "attributes", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ConeTypeRegistry<FirModuleCapability, FirModuleCapability> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirModuleCapabilities getEmpty() {
            return FirModuleCapabilities.Empty;
        }

        public final FirModuleCapabilities create(List<? extends FirModuleCapability> attributes) {
            Intrinsics.checkNotNullParameter(attributes, "attributes");
            if (attributes.isEmpty()) {
                return getEmpty();
            }
            return new FirModuleCapabilities(attributes, null);
        }
    }

    @Override // org.jetbrains.kotlin.util.AbstractArrayMapOwner
    protected TypeRegistry<FirModuleCapability, FirModuleCapability> getTypeRegistry() {
        return INSTANCE;
    }
}
