package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: StarProjectionImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0017J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/types/StarProjectionForAbsentTypeParameter;", "Lorg/jetbrains/kotlin/types/TypeProjectionBase;", "kotlinBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "nullableAnyType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getProjectionKind", "Lorg/jetbrains/kotlin/types/Variance;", "getType", "isStarProjection", "", "refine", "Lorg/jetbrains/kotlin/types/TypeProjection;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "replaceType", ModuleXmlParser.TYPE, "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StarProjectionForAbsentTypeParameter extends TypeProjectionBase {
    private final KotlinType nullableAnyType;

    @Override // org.jetbrains.kotlin.types.TypeProjection
    public boolean isStarProjection() {
        return true;
    }

    public StarProjectionForAbsentTypeParameter(KotlinBuiltIns kotlinBuiltIns) {
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "kotlinBuiltIns");
        SimpleType nullableAnyType = kotlinBuiltIns.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
        this.nullableAnyType = nullableAnyType;
    }

    @Override // org.jetbrains.kotlin.types.TypeProjection
    public Variance getProjectionKind() {
        return Variance.OUT_VARIANCE;
    }

    @Override // org.jetbrains.kotlin.types.TypeProjection
    /* JADX INFO: renamed from: getType, reason: from getter */
    public KotlinType getNullableAnyType() {
        return this.nullableAnyType;
    }

    @Override // org.jetbrains.kotlin.types.TypeProjection
    public TypeProjection refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @Override // org.jetbrains.kotlin.types.TypeProjection
    public TypeProjection replaceType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        throw new UnsupportedOperationException("Replacing type for star projection is unsupported");
    }
}
