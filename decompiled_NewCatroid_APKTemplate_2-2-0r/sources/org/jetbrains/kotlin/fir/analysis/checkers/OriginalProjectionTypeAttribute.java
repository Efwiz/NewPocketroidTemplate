package org.jetbrains.kotlin.fir.analysis.checkers;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.fir.types.ConeAttribute;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;

/* JADX INFO: compiled from: FirUpperBoundViolatedHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000b\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\r\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/OriginalProjectionTypeAttribute;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "data", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;)V", "getData", "()Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", "intersect", "isSubtypeOf", "", "toString", "", SchemaSymbols.ATTVAL_UNION, "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class OriginalProjectionTypeAttribute extends ConeAttribute<OriginalProjectionTypeAttribute> {
    private final ConeTypeProjection data;

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public OriginalProjectionTypeAttribute add(OriginalProjectionTypeAttribute other) {
        return other == null ? this : other;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public KClass<? extends OriginalProjectionTypeAttribute> getKey() {
        return Reflection.getOrCreateKotlinClass(OriginalProjectionTypeAttribute.class);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public OriginalProjectionTypeAttribute intersect(OriginalProjectionTypeAttribute other) {
        return other == null ? this : other;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public boolean isSubtypeOf(OriginalProjectionTypeAttribute other) {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public OriginalProjectionTypeAttribute union(OriginalProjectionTypeAttribute other) {
        return other == null ? this : other;
    }

    public OriginalProjectionTypeAttribute(ConeTypeProjection data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }

    public final ConeTypeProjection getData() {
        return this.data;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public String toString() {
        return "OriginalProjectionTypeAttribute: " + this.data;
    }
}
