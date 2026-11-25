package org.jetbrains.kotlin.fir.types;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;

/* JADX INFO: compiled from: TypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000fR\u001b\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CapturedArguments;", "", "capturedArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "originalType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "([Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "getCapturedArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "isSuitableForType", "", ModuleXmlParser.TYPE, "context", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CapturedArguments {
    private final ConeTypeProjection[] capturedArguments;
    private final ConeKotlinType originalType;

    public CapturedArguments(ConeTypeProjection[] capturedArguments, ConeKotlinType originalType) {
        Intrinsics.checkNotNullParameter(capturedArguments, "capturedArguments");
        Intrinsics.checkNotNullParameter(originalType, "originalType");
        this.capturedArguments = capturedArguments;
        this.originalType = originalType;
    }

    public final ConeTypeProjection[] getCapturedArguments() {
        return this.capturedArguments;
    }

    public final boolean isSuitableForType(ConeKotlinType type, ConeTypeContext context) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        Iterable<IndexedValue> iterableWithIndex = ArraysKt.withIndex(type.getTypeArguments());
        if (!(iterableWithIndex instanceof Collection) || !((Collection) iterableWithIndex).isEmpty()) {
            for (IndexedValue indexedValue : iterableWithIndex) {
                int index = indexedValue.getIndex();
                ConeTypeProjection coneTypeProjection = (ConeTypeProjection) indexedValue.component2();
                if (this.originalType.getTypeArguments().length <= index || !Intrinsics.areEqual(coneTypeProjection, this.originalType.getTypeArguments()[index])) {
                    return false;
                }
            }
        }
        ConeTypeContext coneTypeContext = context;
        return Intrinsics.areEqual(TypeSystemContextHelpersKt.typeConstructor(this.originalType, coneTypeContext), TypeSystemContextHelpersKt.typeConstructor(type, coneTypeContext)) || ConeFlexibleTypeBoundsChecker.INSTANCE.areTypesMayBeLowerAndUpperBoundsOfSameFlexibleTypeByMutability(this.originalType, type);
    }
}
