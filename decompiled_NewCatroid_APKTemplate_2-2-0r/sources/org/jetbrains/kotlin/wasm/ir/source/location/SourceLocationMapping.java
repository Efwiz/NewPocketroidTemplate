package org.jetbrains.kotlin.wasm.ir.source.location;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SourceLocationMapping.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u001b\u0010\b\u001a\u00020\t8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocationMapping;", "", "offsets", "", "Lorg/jetbrains/kotlin/wasm/ir/source/location/Box;", "sourceLocation", "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "(Ljava/util/List;Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;)V", TypedValues.CycleType.S_WAVE_OFFSET, "", "getOffset", "()I", "offset$delegate", "Lkotlin/Lazy;", "getSourceLocation", "()Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceLocationMapping {

    /* JADX INFO: renamed from: offset$delegate, reason: from kotlin metadata */
    private final Lazy offset;
    private final List<Box> offsets;
    private final SourceLocation sourceLocation;

    public SourceLocationMapping(List<Box> offsets, SourceLocation sourceLocation) {
        Intrinsics.checkNotNullParameter(offsets, "offsets");
        Intrinsics.checkNotNullParameter(sourceLocation, "sourceLocation");
        this.offsets = offsets;
        this.sourceLocation = sourceLocation;
        this.offset = LazyKt.lazy(new Function0<Integer>() { // from class: org.jetbrains.kotlin.wasm.ir.source.location.SourceLocationMapping$offset$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Integer invoke() {
                int value = 0;
                for (Box box : this.this$0.offsets) {
                    box.getValue();
                    value += box.getValue();
                }
                return Integer.valueOf(value);
            }
        });
    }

    public final SourceLocation getSourceLocation() {
        return this.sourceLocation;
    }

    public final int getOffset() {
        return ((Number) this.offset.getValue()).intValue();
    }
}
