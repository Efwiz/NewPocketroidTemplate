package org.jetbrains.kotlin.fir.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;

/* JADX INFO: compiled from: FirExtensionRegistrarAdapter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b&\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrarAdapter;", "", "()V", "Companion", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirExtensionRegistrarAdapter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: compiled from: FirExtensionRegistrarAdapter.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrarAdapter$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrarAdapter;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<FirExtensionRegistrarAdapter> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
            super("org.jetbrains.kotlin.fir.extensions.firExtensionRegistrar", FirExtensionRegistrarAdapter.class);
        }
    }
}
