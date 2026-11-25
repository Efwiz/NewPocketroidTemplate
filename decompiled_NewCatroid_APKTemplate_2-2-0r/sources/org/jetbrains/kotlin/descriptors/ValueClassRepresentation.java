package org.jetbrains.kotlin.descriptors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: ValueClassRepresentation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH&J\u0017\u0010\u000e\u001a\u0004\u0018\u00018\u00002\u0006\u0010\r\u001a\u00020\bH&¢\u0006\u0002\u0010\u000fJ*\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0000\"\b\b\u0001\u0010\u0011*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u0002H\u00110\u0013R$\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00028\u00000\u00070\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0001\u0002\u0014\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Type", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "", "()V", "underlyingPropertyNamesToTypes", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/Name;", "getUnderlyingPropertyNamesToTypes", "()Ljava/util/List;", "containsPropertyWithName", "", "name", "getPropertyTypeByName", "(Lorg/jetbrains/kotlin/name/Name;)Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "mapUnderlyingType", "Other", "transform", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ValueClassRepresentation<Type extends SimpleTypeMarker> {
    public /* synthetic */ ValueClassRepresentation(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract boolean containsPropertyWithName(Name name2);

    public abstract Type getPropertyTypeByName(Name name2);

    public abstract List<Pair<Name, Type>> getUnderlyingPropertyNamesToTypes();

    private ValueClassRepresentation() {
    }

    public final <Other extends SimpleTypeMarker> ValueClassRepresentation<Other> mapUnderlyingType(Function1<? super Type, ? extends Other> transform) {
        Intrinsics.checkNotNullParameter(transform, "transform");
        if (this instanceof InlineClassRepresentation) {
            InlineClassRepresentation inlineClassRepresentation = (InlineClassRepresentation) this;
            return new InlineClassRepresentation(inlineClassRepresentation.getUnderlyingPropertyName(), transform.invoke(inlineClassRepresentation.getUnderlyingType()));
        }
        if (!(this instanceof MultiFieldValueClassRepresentation)) {
            throw new NoWhenBranchMatchedException();
        }
        List<Pair<Name, Type>> underlyingPropertyNamesToTypes = getUnderlyingPropertyNamesToTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(underlyingPropertyNamesToTypes, 10));
        Iterator<T> it = underlyingPropertyNamesToTypes.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayList.add(TuplesKt.to((Name) pair.component1(), transform.invoke((SimpleTypeMarker) pair.component2())));
        }
        return new MultiFieldValueClassRepresentation(arrayList);
    }
}
