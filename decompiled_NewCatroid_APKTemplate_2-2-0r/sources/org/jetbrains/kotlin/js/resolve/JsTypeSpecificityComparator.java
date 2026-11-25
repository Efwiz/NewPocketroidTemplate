package org.jetbrains.kotlin.js.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContextDelegate;

/* JADX INFO: compiled from: JsTypeSpecificityComparator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0019\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0096\u0001¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/JsTypeSpecificityComparator;", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;)V", "isDefinitelyLessSpecific", "", "specific", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "general", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsTypeSpecificityComparator implements TypeSpecificityComparator {
    private final /* synthetic */ JsTypeSpecificityComparatorWithoutDelegate $$delegate_0;

    @Override // org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator
    public boolean isDefinitelyLessSpecific(KotlinTypeMarker specific, KotlinTypeMarker general) {
        Intrinsics.checkNotNullParameter(specific, "specific");
        Intrinsics.checkNotNullParameter(general, "general");
        return this.$$delegate_0.isDefinitelyLessSpecific(specific, general);
    }

    public JsTypeSpecificityComparator(TypeSystemInferenceExtensionContextDelegate context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.$$delegate_0 = new JsTypeSpecificityComparatorWithoutDelegate(context);
    }
}
