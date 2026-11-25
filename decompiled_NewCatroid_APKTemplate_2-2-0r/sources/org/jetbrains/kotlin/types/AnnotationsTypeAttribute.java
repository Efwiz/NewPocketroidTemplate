package org.jetbrains.kotlin.types;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: AnnotationsTypeAttribute.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000b\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\f\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u0013\u001a\u00020\u000e2\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016J\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/types/AnnotationsTypeAttribute;", "Lorg/jetbrains/kotlin/types/TypeAttribute;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "(Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;)V", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", Namer.EQUALS_METHOD_NAME, "", "", "hashCode", "", "intersect", "isSubtypeOf", SchemaSymbols.ATTVAL_UNION, "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsTypeAttribute extends TypeAttribute<AnnotationsTypeAttribute> {
    private final Annotations annotations;

    @Override // org.jetbrains.kotlin.types.TypeAttribute
    public KClass<? extends AnnotationsTypeAttribute> getKey() {
        return Reflection.getOrCreateKotlinClass(AnnotationsTypeAttribute.class);
    }

    @Override // org.jetbrains.kotlin.types.TypeAttribute
    public boolean isSubtypeOf(AnnotationsTypeAttribute other) {
        return true;
    }

    public AnnotationsTypeAttribute(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        this.annotations = annotations;
    }

    public final Annotations getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.types.TypeAttribute
    public AnnotationsTypeAttribute union(AnnotationsTypeAttribute other) {
        if (Intrinsics.areEqual(other, this)) {
            return this;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeAttribute
    public AnnotationsTypeAttribute intersect(AnnotationsTypeAttribute other) {
        if (Intrinsics.areEqual(other, this)) {
            return this;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeAttribute
    public AnnotationsTypeAttribute add(AnnotationsTypeAttribute other) {
        return other == null ? this : new AnnotationsTypeAttribute(AnnotationsKt.composeAnnotations(this.annotations, other.annotations));
    }

    public boolean equals(Object other) {
        if (other instanceof AnnotationsTypeAttribute) {
            return Intrinsics.areEqual(((AnnotationsTypeAttribute) other).annotations, this.annotations);
        }
        return false;
    }

    public int hashCode() {
        return this.annotations.hashCode();
    }
}
