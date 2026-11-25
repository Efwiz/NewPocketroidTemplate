package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;

/* JADX INFO: compiled from: JvmTypeSpecificityComparator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmTypeSpecificityComparator;", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;)V", "getContext", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "isDefinitelyLessSpecific", "", "specific", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "general", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JvmTypeSpecificityComparator implements TypeSpecificityComparator {
    private final TypeSystemInferenceExtensionContext context;

    public JvmTypeSpecificityComparator(TypeSystemInferenceExtensionContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public TypeSystemInferenceExtensionContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator
    public boolean isDefinitelyLessSpecific(KotlinTypeMarker specific, KotlinTypeMarker general) {
        Intrinsics.checkNotNullParameter(specific, "specific");
        Intrinsics.checkNotNullParameter(general, "general");
        TypeSystemInferenceExtensionContext context = getContext();
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = context.asSimpleType(general);
        if (context.isFlexible(specific) && simpleTypeMarkerAsSimpleType != null) {
            FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = context.asFlexibleType(specific);
            Intrinsics.checkNotNull(flexibleTypeMarkerAsFlexibleType);
            return context.isPrimitiveType(simpleTypeMarkerAsSimpleType) && context.isPrimitiveType(context.lowerBound(flexibleTypeMarkerAsFlexibleType)) && !context.isMarkedNullable(simpleTypeMarkerAsSimpleType);
        }
        return false;
    }
}
