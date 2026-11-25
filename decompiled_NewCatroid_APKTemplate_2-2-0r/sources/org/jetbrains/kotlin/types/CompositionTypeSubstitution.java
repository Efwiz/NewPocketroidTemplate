package org.jetbrains.kotlin.types;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;

/* JADX INFO: compiled from: CompositionTypeSubstitution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0013\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH\u0096\u0002R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/types/CompositionTypeSubstitution;", "Lorg/jetbrains/kotlin/types/DelegatedTypeSubstitution;", "outer", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "inner", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "(Lorg/jetbrains/kotlin/types/TypeSubstitution;Ljava/util/Map;)V", "get", "Lorg/jetbrains/kotlin/types/TypeProjection;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompositionTypeSubstitution extends DelegatedTypeSubstitution {
    private final Map<TypeParameterDescriptor, TypeParameterDescriptor> inner;
    private final TypeSubstitution outer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CompositionTypeSubstitution(TypeSubstitution outer, Map<TypeParameterDescriptor, ? extends TypeParameterDescriptor> inner) {
        super(outer);
        Intrinsics.checkNotNullParameter(outer, "outer");
        Intrinsics.checkNotNullParameter(inner, "inner");
        this.outer = outer;
        this.inner = inner;
    }

    @Override // org.jetbrains.kotlin.types.DelegatedTypeSubstitution, org.jetbrains.kotlin.types.TypeSubstitution
    /* JADX INFO: renamed from: get */
    public TypeProjection mo7475get(KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        TypeParameterDescriptor typeParameterDescriptor = this.inner.get(key.getConstructor().getThis$0());
        if (typeParameterDescriptor != null) {
            TypeSubstitution typeSubstitution = this.outer;
            SimpleType defaultType = typeParameterDescriptor.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            TypeProjection typeProjectionMo7475get = typeSubstitution.mo7475get(defaultType);
            if (typeProjectionMo7475get != null) {
                return typeProjectionMo7475get;
            }
        }
        return this.outer.mo7475get(key);
    }
}
