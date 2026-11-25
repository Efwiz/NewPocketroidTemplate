package org.jetbrains.kotlin.resolve.calls.results;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.container.PlatformSpecificExtension;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: TypeSpecificityComparator.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = NONE.class)
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0007J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "isDefinitelyLessSpecific", "", "specific", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "general", "NONE", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeSpecificityComparator extends PlatformSpecificExtension<TypeSpecificityComparator> {
    boolean isDefinitelyLessSpecific(KotlinTypeMarker specific, KotlinTypeMarker general);

    /* JADX INFO: compiled from: TypeSpecificityComparator.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator$NONE;", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "()V", "isDefinitelyLessSpecific", "", "specific", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "general", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NONE implements TypeSpecificityComparator {
        public static final NONE INSTANCE = new NONE();

        @Override // org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator
        public boolean isDefinitelyLessSpecific(KotlinTypeMarker specific, KotlinTypeMarker general) {
            Intrinsics.checkNotNullParameter(specific, "specific");
            Intrinsics.checkNotNullParameter(general, "general");
            return false;
        }

        private NONE() {
        }
    }
}
