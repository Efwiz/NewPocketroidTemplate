package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;

/* JADX INFO: compiled from: ConeTypeProjection.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "()V", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/types/ProjectionKind;", "getKind", "()Lorg/jetbrains/kotlin/fir/types/ProjectionKind;", "Companion", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinTypeProjection;", "Lorg/jetbrains/kotlin/fir/types/ConeStarProjection;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeTypeProjection implements TypeArgumentMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ConeTypeProjection[] EMPTY_ARRAY = new ConeTypeProjection[0];

    public /* synthetic */ ConeTypeProjection(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ProjectionKind getKind();

    private ConeTypeProjection() {
    }

    /* JADX INFO: compiled from: ConeTypeProjection.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection$Companion;", "", "()V", "EMPTY_ARRAY", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "getEMPTY_ARRAY", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ConeTypeProjection[] getEMPTY_ARRAY() {
            return ConeTypeProjection.EMPTY_ARRAY;
        }
    }
}
