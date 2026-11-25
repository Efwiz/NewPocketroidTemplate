package org.jetbrains.kotlin.types;

import kotlin.Metadata;

/* JADX INFO: compiled from: FunctionPlaceholders.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0017\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0004"}, d2 = {"isFunctionPlaceholder", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)Z", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionPlaceholdersKt {
    public static final boolean isFunctionPlaceholder(KotlinType kotlinType) {
        return kotlinType != null && (kotlinType.getConstructor() instanceof FunctionPlaceholderTypeConstructor);
    }
}
