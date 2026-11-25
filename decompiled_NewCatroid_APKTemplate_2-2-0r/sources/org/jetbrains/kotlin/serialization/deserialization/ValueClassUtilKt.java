package org.jetbrains.kotlin.serialization.deserialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: ValueClassUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aV\u0010\u0000\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u0002H\u00020\n2\u0014\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\n¨\u0006\u000e"}, d2 = {"loadValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "typeDeserializer", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "typeOfPublicProperty", "Lorg/jetbrains/kotlin/name/Name;", "deserialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ValueClassUtilKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T extends SimpleTypeMarker> ValueClassRepresentation<T> loadValueClassRepresentation(ProtoBuf.Class r5, NameResolver nameResolver, TypeTable typeTable, Function1<? super ProtoBuf.Type, ? extends T> typeDeserializer, Function1<? super Name, ? extends T> typeOfPublicProperty) {
        T tInvoke;
        ArrayList multiFieldValueClassUnderlyingTypeList;
        Intrinsics.checkNotNullParameter(r5, "<this>");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(typeDeserializer, "typeDeserializer");
        Intrinsics.checkNotNullParameter(typeOfPublicProperty, "typeOfPublicProperty");
        if (r5.getMultiFieldValueClassUnderlyingNameCount() > 0) {
            List<Integer> multiFieldValueClassUnderlyingNameList = r5.getMultiFieldValueClassUnderlyingNameList();
            Intrinsics.checkNotNullExpressionValue(multiFieldValueClassUnderlyingNameList, "getMultiFieldValueClassUnderlyingNameList(...)");
            List<Integer> list = multiFieldValueClassUnderlyingNameList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (Integer num : list) {
                Intrinsics.checkNotNull(num);
                arrayList.add(NameResolverUtilKt.getName(nameResolver, num.intValue()));
            }
            ArrayList arrayList2 = arrayList;
            Pair pair = TuplesKt.to(Integer.valueOf(r5.getMultiFieldValueClassUnderlyingTypeIdCount()), Integer.valueOf(r5.getMultiFieldValueClassUnderlyingTypeCount()));
            if (!Intrinsics.areEqual(pair, TuplesKt.to(Integer.valueOf(arrayList2.size()), 0))) {
                if (!Intrinsics.areEqual(pair, TuplesKt.to(0, Integer.valueOf(arrayList2.size())))) {
                    throw new IllegalStateException((ExternalAnnotationProvider.CLASS_PREFIX + NameResolverUtilKt.getName(nameResolver, r5.getFqName()) + " has illegal multi-field value class representation").toString());
                }
                multiFieldValueClassUnderlyingTypeList = r5.getMultiFieldValueClassUnderlyingTypeList();
            } else {
                List<Integer> multiFieldValueClassUnderlyingTypeIdList = r5.getMultiFieldValueClassUnderlyingTypeIdList();
                Intrinsics.checkNotNullExpressionValue(multiFieldValueClassUnderlyingTypeIdList, "getMultiFieldValueClassUnderlyingTypeIdList(...)");
                List<Integer> list2 = multiFieldValueClassUnderlyingTypeIdList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                for (Integer num2 : list2) {
                    Intrinsics.checkNotNull(num2);
                    arrayList3.add(typeTable.get(num2.intValue()));
                }
                multiFieldValueClassUnderlyingTypeList = arrayList3;
            }
            Intrinsics.checkNotNull(multiFieldValueClassUnderlyingTypeList);
            List<ProtoBuf.Type> list3 = multiFieldValueClassUnderlyingTypeList;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            Iterator<T> it = list3.iterator();
            while (it.hasNext()) {
                arrayList4.add(typeDeserializer.invoke(it.next()));
            }
            return new MultiFieldValueClassRepresentation(CollectionsKt.zip(arrayList2, arrayList4));
        }
        if (!r5.hasInlineClassUnderlyingPropertyName()) {
            return null;
        }
        Name name2 = NameResolverUtilKt.getName(nameResolver, r5.getInlineClassUnderlyingPropertyName());
        ProtoBuf.Type typeInlineClassUnderlyingType = ProtoTypeTableUtilKt.inlineClassUnderlyingType(r5, typeTable);
        if ((typeInlineClassUnderlyingType == null || (tInvoke = typeDeserializer.invoke(typeInlineClassUnderlyingType)) == null) && (tInvoke = typeOfPublicProperty.invoke(name2)) == null) {
            throw new IllegalStateException(("cannot determine underlying type for value class " + NameResolverUtilKt.getName(nameResolver, r5.getFqName()) + " with property " + name2).toString());
        }
        return new InlineClassRepresentation(name2, tInvoke);
    }
}
