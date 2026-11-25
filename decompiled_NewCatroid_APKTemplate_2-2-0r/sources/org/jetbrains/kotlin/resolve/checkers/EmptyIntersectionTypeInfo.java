package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.EmptyIntersectionTypeKind;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: EmptyIntersectionTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\"\u00020\u0006¢\u0006\u0002\u0010\u0007R\u001b\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/EmptyIntersectionTypeInfo;", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;", "casingTypes", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "(Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;[Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)V", "getCasingTypes", "()[Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "[Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getKind", "()Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyIntersectionTypeInfo {
    private final KotlinTypeMarker[] casingTypes;
    private final EmptyIntersectionTypeKind kind;

    public EmptyIntersectionTypeInfo(EmptyIntersectionTypeKind kind, KotlinTypeMarker... casingTypes) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(casingTypes, "casingTypes");
        this.kind = kind;
        this.casingTypes = casingTypes;
    }

    public final KotlinTypeMarker[] getCasingTypes() {
        return this.casingTypes;
    }

    public final EmptyIntersectionTypeKind getKind() {
        return this.kind;
    }
}
