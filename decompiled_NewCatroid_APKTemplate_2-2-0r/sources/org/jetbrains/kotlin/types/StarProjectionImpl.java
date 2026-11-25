package org.jetbrains.kotlin.types;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: StarProjectionImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0006H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0017J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0006H\u0016R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/types/StarProjectionImpl;", "Lorg/jetbrains/kotlin/types/TypeProjectionBase;", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;)V", "_type", "Lorg/jetbrains/kotlin/types/KotlinType;", "get_type", "()Lorg/jetbrains/kotlin/types/KotlinType;", "_type$delegate", "Lkotlin/Lazy;", "getProjectionKind", "Lorg/jetbrains/kotlin/types/Variance;", "getType", "isStarProjection", "", "refine", "Lorg/jetbrains/kotlin/types/TypeProjection;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "replaceType", ModuleXmlParser.TYPE, "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StarProjectionImpl extends TypeProjectionBase {

    /* JADX INFO: renamed from: _type$delegate, reason: from kotlin metadata */
    private final Lazy _type;
    private final TypeParameterDescriptor typeParameter;

    @Override // org.jetbrains.kotlin.types.TypeProjection
    public boolean isStarProjection() {
        return true;
    }

    public StarProjectionImpl(TypeParameterDescriptor typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        this.typeParameter = typeParameter;
        this._type = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.types.StarProjectionImpl$_type$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinType invoke() {
                return StarProjectionImplKt.starProjectionType(this.this$0.typeParameter);
            }
        });
    }

    @Override // org.jetbrains.kotlin.types.TypeProjection
    public Variance getProjectionKind() {
        return Variance.OUT_VARIANCE;
    }

    private final KotlinType get_type() {
        return (KotlinType) this._type.getValue();
    }

    @Override // org.jetbrains.kotlin.types.TypeProjection
    public KotlinType getType() {
        return get_type();
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
