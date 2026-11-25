package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.ConeAttribute;
import org.jetbrains.kotlin.fir.types.ConeAttributes;

/* JADX INFO: compiled from: ConeAttributeRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/ConeAttributeRenderer;", "", "()V", "render", "", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "ToString", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeAttributeRenderer {
    public abstract String render(ConeAttributes attributes);

    /* JADX INFO: compiled from: ConeAttributeRenderer.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/ConeAttributeRenderer$ToString;", "Lorg/jetbrains/kotlin/fir/renderer/ConeAttributeRenderer;", "()V", "render", "", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ToString extends ConeAttributeRenderer {
        public static final ToString INSTANCE = new ToString();

        private ToString() {
        }

        @Override // org.jetbrains.kotlin.fir.renderer.ConeAttributeRenderer
        public String render(ConeAttributes attributes) {
            Intrinsics.checkNotNullParameter(attributes, "attributes");
            return CollectionsKt.joinToString$default(attributes, " ", null, " ", 0, null, new Function1<ConeAttribute<?>, CharSequence>() { // from class: org.jetbrains.kotlin.fir.renderer.ConeAttributeRenderer$ToString$render$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(ConeAttribute<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.toString();
                }
            }, 26, null);
        }
    }
}
