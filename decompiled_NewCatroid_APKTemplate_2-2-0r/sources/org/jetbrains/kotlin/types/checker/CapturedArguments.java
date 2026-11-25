package org.jetbrains.kotlin.types.checker;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.FlexibleTypeBoundsChecker;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: NewCapturedType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/CapturedArguments;", "", "capturedArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "originalType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Ljava/util/List;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getCapturedArguments", "()Ljava/util/List;", "isSuitableForType", "", ModuleXmlParser.TYPE, "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CapturedArguments {
    private final List<TypeProjection> capturedArguments;
    private final KotlinType originalType;

    /* JADX WARN: Multi-variable type inference failed */
    public CapturedArguments(List<? extends TypeProjection> capturedArguments, KotlinType originalType) {
        Intrinsics.checkNotNullParameter(capturedArguments, "capturedArguments");
        Intrinsics.checkNotNullParameter(originalType, "originalType");
        this.capturedArguments = capturedArguments;
        this.originalType = originalType;
    }

    public final List<TypeProjection> getCapturedArguments() {
        return this.capturedArguments;
    }

    public final boolean isSuitableForType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(type.getArguments());
        if (!(iterableWithIndex instanceof Collection) || !((Collection) iterableWithIndex).isEmpty()) {
            for (IndexedValue indexedValue : iterableWithIndex) {
                int index = indexedValue.getIndex();
                TypeProjection typeProjection = (TypeProjection) indexedValue.component2();
                if (this.originalType.getArguments().size() <= index || !Intrinsics.areEqual(typeProjection, this.originalType.getArguments().get(index))) {
                    return false;
                }
            }
        }
        return Intrinsics.areEqual(this.originalType.getConstructor(), type.getConstructor()) || FlexibleTypeBoundsChecker.INSTANCE.areTypesMayBeLowerAndUpperBoundsOfSameFlexibleTypeByMutability(this.originalType, type);
    }
}
