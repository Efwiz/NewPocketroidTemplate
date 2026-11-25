package org.jetbrains.kotlin.descriptors;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: MultiFieldValueClassRepresentation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001f\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u00000\u00060\u0005¢\u0006\u0002\u0010\bJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H\u0016J\u0017\u0010\u0010\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000f\u001a\u00020\u0007H\u0016¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u00000\nX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u00000\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "Type", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "underlyingPropertyNamesToTypes", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/Name;", "(Ljava/util/List;)V", "map", "", "getUnderlyingPropertyNamesToTypes", "()Ljava/util/List;", "containsPropertyWithName", "", "name", "getPropertyTypeByName", "(Lorg/jetbrains/kotlin/name/Name;)Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "toString", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MultiFieldValueClassRepresentation<Type extends SimpleTypeMarker> extends ValueClassRepresentation<Type> {
    private final Map<Name, Type> map;
    private final List<Pair<Name, Type>> underlyingPropertyNamesToTypes;

    @Override // org.jetbrains.kotlin.descriptors.ValueClassRepresentation
    public List<Pair<Name, Type>> getUnderlyingPropertyNamesToTypes() {
        return this.underlyingPropertyNamesToTypes;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public MultiFieldValueClassRepresentation(List<? extends Pair<Name, ? extends Type>> underlyingPropertyNamesToTypes) {
        super(null);
        Intrinsics.checkNotNullParameter(underlyingPropertyNamesToTypes, "underlyingPropertyNamesToTypes");
        this.underlyingPropertyNamesToTypes = underlyingPropertyNamesToTypes;
        Map<Name, Type> map = MapsKt.toMap(getUnderlyingPropertyNamesToTypes());
        if (map.size() == getUnderlyingPropertyNamesToTypes().size()) {
            this.map = map;
            return;
        }
        throw new IllegalArgumentException("Some properties have the same names".toString());
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueClassRepresentation
    public boolean containsPropertyWithName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.map.containsKey(name2);
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueClassRepresentation
    public Type getPropertyTypeByName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.map.get(name2);
    }

    public String toString() {
        return "MultiFieldValueClassRepresentation(underlyingPropertyNamesToTypes=" + getUnderlyingPropertyNamesToTypes() + ')';
    }
}
