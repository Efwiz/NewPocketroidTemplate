package org.jetbrains.kotlin.descriptors;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: InlineClassRepresentation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0005H\u0016J\u0017\u0010\u0015\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0014\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR&\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00028\u00000\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "Type", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "underlyingPropertyName", "Lorg/jetbrains/kotlin/name/Name;", "underlyingType", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;)V", "getUnderlyingPropertyName", "()Lorg/jetbrains/kotlin/name/Name;", "underlyingPropertyNamesToTypes", "", "Lkotlin/Pair;", "getUnderlyingPropertyNamesToTypes", "()Ljava/util/List;", "getUnderlyingType", "()Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "containsPropertyWithName", "", "name", "getPropertyTypeByName", "(Lorg/jetbrains/kotlin/name/Name;)Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "toString", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineClassRepresentation<Type extends SimpleTypeMarker> extends ValueClassRepresentation<Type> {
    private final Name underlyingPropertyName;
    private final Type underlyingType;

    public final Name getUnderlyingPropertyName() {
        return this.underlyingPropertyName;
    }

    public final Type getUnderlyingType() {
        return this.underlyingType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InlineClassRepresentation(Name underlyingPropertyName, Type underlyingType) {
        super(null);
        Intrinsics.checkNotNullParameter(underlyingPropertyName, "underlyingPropertyName");
        Intrinsics.checkNotNullParameter(underlyingType, "underlyingType");
        this.underlyingPropertyName = underlyingPropertyName;
        this.underlyingType = underlyingType;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueClassRepresentation
    public List<Pair<Name, Type>> getUnderlyingPropertyNamesToTypes() {
        return CollectionsKt.listOf(TuplesKt.to(this.underlyingPropertyName, this.underlyingType));
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueClassRepresentation
    public boolean containsPropertyWithName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return Intrinsics.areEqual(this.underlyingPropertyName, name2);
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueClassRepresentation
    public Type getPropertyTypeByName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Type type = this.underlyingType;
        if (containsPropertyWithName(name2)) {
            return type;
        }
        return null;
    }

    public String toString() {
        return "InlineClassRepresentation(underlyingPropertyName=" + this.underlyingPropertyName + ", underlyingType=" + this.underlyingType + ')';
    }
}
