package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;

/* JADX INFO: compiled from: DisjointKeysUnionTypeSubstitution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\u0018\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/types/DisjointKeysUnionTypeSubstitution;", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "first", "second", "(Lorg/jetbrains/kotlin/types/TypeSubstitution;Lorg/jetbrains/kotlin/types/TypeSubstitution;)V", "approximateCapturedTypes", "", "approximateContravariantCapturedTypes", "filterAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "annotations", "get", "Lorg/jetbrains/kotlin/types/TypeProjection;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/types/KotlinType;", "isEmpty", "prepareTopLevelType", "topLevelType", "position", "Lorg/jetbrains/kotlin/types/Variance;", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DisjointKeysUnionTypeSubstitution extends TypeSubstitution {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final TypeSubstitution first;
    private final TypeSubstitution second;

    public /* synthetic */ DisjointKeysUnionTypeSubstitution(TypeSubstitution typeSubstitution, TypeSubstitution typeSubstitution2, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeSubstitution, typeSubstitution2);
    }

    @JvmStatic
    public static final TypeSubstitution create(TypeSubstitution typeSubstitution, TypeSubstitution typeSubstitution2) {
        return INSTANCE.create(typeSubstitution, typeSubstitution2);
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public boolean isEmpty() {
        return false;
    }

    /* JADX INFO: compiled from: DisjointKeysUnionTypeSubstitution.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/types/DisjointKeysUnionTypeSubstitution$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "first", "second", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final TypeSubstitution create(TypeSubstitution first, TypeSubstitution second) {
            Intrinsics.checkNotNullParameter(first, "first");
            Intrinsics.checkNotNullParameter(second, "second");
            if (first.isEmpty()) {
                return second;
            }
            return second.isEmpty() ? first : new DisjointKeysUnionTypeSubstitution(first, second, null);
        }
    }

    private DisjointKeysUnionTypeSubstitution(TypeSubstitution typeSubstitution, TypeSubstitution typeSubstitution2) {
        this.first = typeSubstitution;
        this.second = typeSubstitution2;
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    /* JADX INFO: renamed from: get */
    public TypeProjection mo7475get(KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        TypeProjection typeProjectionMo7475get = this.first.mo7475get(key);
        return typeProjectionMo7475get == null ? this.second.mo7475get(key) : typeProjectionMo7475get;
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public KotlinType prepareTopLevelType(KotlinType topLevelType, Variance position) {
        Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
        Intrinsics.checkNotNullParameter(position, "position");
        return this.second.prepareTopLevelType(this.first.prepareTopLevelType(topLevelType, position), position);
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public boolean approximateCapturedTypes() {
        return this.first.approximateCapturedTypes() || this.second.approximateCapturedTypes();
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    /* JADX INFO: renamed from: approximateContravariantCapturedTypes */
    public boolean getApproximateContravariantCapturedTypes() {
        return this.first.getApproximateContravariantCapturedTypes() || this.second.getApproximateContravariantCapturedTypes();
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public Annotations filterAnnotations(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        return this.second.filterAnnotations(this.first.filterAnnotations(annotations));
    }
}
