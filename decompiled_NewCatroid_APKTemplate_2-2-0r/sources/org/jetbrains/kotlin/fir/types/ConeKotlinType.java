package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH¦\u0002J\b\u0010\u001d\u001a\u00020\u001eH&J\u0006\u0010\u001f\u001a\u00020 R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00160\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018\u0082\u0001\u0002!\"¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinTypeProjection;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "()V", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/types/ProjectionKind;", "getKind", "()Lorg/jetbrains/kotlin/fir/types/ProjectionKind;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", ModuleXmlParser.TYPE, "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeKotlinType extends ConeKotlinTypeProjection implements KotlinTypeMarker, TypeArgumentListMarker {
    public /* synthetic */ ConeKotlinType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public abstract boolean equals(Object other);

    public abstract ConeAttributes getAttributes();

    public abstract ConeNullability getNullability();

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public final ConeKotlinType getType() {
        return this;
    }

    public abstract ConeTypeProjection[] getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public abstract int hashCode();

    private ConeKotlinType() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeTypeProjection
    public final ProjectionKind getKind() {
        return ProjectionKind.INVARIANT;
    }

    public final String toString() {
        return ConeTypeUtilsKt.renderForDebugging(this);
    }
}
