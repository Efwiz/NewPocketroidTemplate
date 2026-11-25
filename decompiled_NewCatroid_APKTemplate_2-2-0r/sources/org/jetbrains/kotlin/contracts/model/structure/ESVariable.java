package org.jetbrains.kotlin.contracts.model.structure;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J!\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\nH\u0016¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESVariable;", "Lorg/jetbrains/kotlin/contracts/model/structure/AbstractESValue;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "accept", ExifInterface.GPS_DIRECTION_TRUE, "visitor", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "(Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;)Ljava/lang/Object;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ESVariable extends AbstractESValue {
    private final ValueDescriptor descriptor;

    /* JADX WARN: Illegal instructions before constructor call */
    public ESVariable(ValueDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        super(TypesKt.toESType(type));
        this.descriptor = descriptor;
    }

    public final ValueDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpression
    public <T> T accept(ESExpressionVisitor<? extends T> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitVariable(this);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(other != null ? other.getClass() : null, getClass())) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.model.structure.ESVariable");
        return Intrinsics.areEqual(this.descriptor, ((ESVariable) other).descriptor);
    }

    public int hashCode() {
        return this.descriptor.hashCode();
    }

    public String toString() {
        return this.descriptor.toString();
    }
}
