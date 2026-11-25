package org.jetbrains.kotlin.js.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;

/* JADX INFO: compiled from: JsTypeSpecificityComparator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0014\u0010\f\u001a\u00020\r*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\nH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/JsTypeSpecificityComparatorWithoutDelegate;", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;)V", "getContext", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "isDefinitelyLessSpecific", "", "specific", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "general", "checkOnlyDynamicFlexibleType", "", ModuleXmlParser.TYPE, "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsTypeSpecificityComparatorWithoutDelegate implements TypeSpecificityComparator {
    private final TypeSystemInferenceExtensionContext context;

    public JsTypeSpecificityComparatorWithoutDelegate(TypeSystemInferenceExtensionContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final TypeSystemInferenceExtensionContext getContext() {
        return this.context;
    }

    private final void checkOnlyDynamicFlexibleType(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, KotlinTypeMarker kotlinTypeMarker) {
        if (typeSystemInferenceExtensionContext.asFlexibleType(kotlinTypeMarker) != null) {
            typeSystemInferenceExtensionContext.isDynamic(kotlinTypeMarker);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator
    public boolean isDefinitelyLessSpecific(KotlinTypeMarker specific, KotlinTypeMarker general) {
        Intrinsics.checkNotNullParameter(specific, "specific");
        Intrinsics.checkNotNullParameter(general, "general");
        TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.context;
        checkOnlyDynamicFlexibleType(typeSystemInferenceExtensionContext, specific);
        checkOnlyDynamicFlexibleType(typeSystemInferenceExtensionContext, general);
        return typeSystemInferenceExtensionContext.isDynamic(specific) && !typeSystemInferenceExtensionContext.isDynamic(general);
    }
}
