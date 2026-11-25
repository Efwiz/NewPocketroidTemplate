package org.jetbrains.kotlin.metadata.deserialization;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: protoTypeTableUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005*\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005*\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005*\u00020\b2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\t\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u000b\u001a\u0004\u0018\u00010\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\n\u0010\f\u001a\u00020\r*\u00020\u0007\u001a\n\u0010\f\u001a\u00020\r*\u00020\b\u001a\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u0001*\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u0001*\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u0001*\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u0001*\u00020\b2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0013\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0013\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005*\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u0001*\u00020\u00162\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0015\u001a\u00020\u0001*\u00020\u00172\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0018\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0018\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005*\u00020\u001a2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u0001*\u00020\u00172\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u001c"}, d2 = {"abbreviatedType", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "contextReceiverTypes", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "expandedType", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "flexibleUpperBound", "hasReceiver", "", "inlineClassUnderlyingType", "isInstanceType", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;", "outerType", "receiverType", "returnType", "supertypes", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "underlyingType", "upperBounds", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "varargElementType", "metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProtoTypeTableUtilKt {
    public static final List<ProtoBuf.Type> supertypes(ProtoBuf.Class r2, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(r2, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        List<ProtoBuf.Type> supertypeList = r2.getSupertypeList();
        if (supertypeList.isEmpty()) {
            supertypeList = null;
        }
        if (supertypeList != null) {
            return supertypeList;
        }
        List<Integer> supertypeIdList = r2.getSupertypeIdList();
        Intrinsics.checkNotNullExpressionValue(supertypeIdList, "getSupertypeIdList(...)");
        List<Integer> list = supertypeIdList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Integer num : list) {
            Intrinsics.checkNotNull(num);
            arrayList.add(typeTable.get(num.intValue()));
        }
        return arrayList;
    }

    public static final ProtoBuf.Type inlineClassUnderlyingType(ProtoBuf.Class r1, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(r1, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (r1.hasInlineClassUnderlyingType()) {
            return r1.getInlineClassUnderlyingType();
        }
        if (r1.hasInlineClassUnderlyingTypeId()) {
            return typeTable.get(r1.getInlineClassUnderlyingTypeId());
        }
        return null;
    }

    public static final ProtoBuf.Type type(ProtoBuf.Type.Argument argument, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(argument, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (argument.hasType()) {
            return argument.getType();
        }
        if (argument.hasTypeId()) {
            return typeTable.get(argument.getTypeId());
        }
        return null;
    }

    public static final ProtoBuf.Type flexibleUpperBound(ProtoBuf.Type type, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(type, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (type.hasFlexibleUpperBound()) {
            return type.getFlexibleUpperBound();
        }
        if (type.hasFlexibleUpperBoundId()) {
            return typeTable.get(type.getFlexibleUpperBoundId());
        }
        return null;
    }

    public static final List<ProtoBuf.Type> upperBounds(ProtoBuf.TypeParameter typeParameter, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(typeParameter, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        List<ProtoBuf.Type> upperBoundList = typeParameter.getUpperBoundList();
        if (upperBoundList.isEmpty()) {
            upperBoundList = null;
        }
        if (upperBoundList != null) {
            return upperBoundList;
        }
        List<Integer> upperBoundIdList = typeParameter.getUpperBoundIdList();
        Intrinsics.checkNotNullExpressionValue(upperBoundIdList, "getUpperBoundIdList(...)");
        List<Integer> list = upperBoundIdList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Integer num : list) {
            Intrinsics.checkNotNull(num);
            arrayList.add(typeTable.get(num.intValue()));
        }
        return arrayList;
    }

    public static final ProtoBuf.Type returnType(ProtoBuf.Function function, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(function, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (function.hasReturnType()) {
            ProtoBuf.Type returnType = function.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            return returnType;
        }
        if (function.hasReturnTypeId()) {
            return typeTable.get(function.getReturnTypeId());
        }
        throw new IllegalStateException("No returnType in ProtoBuf.Function".toString());
    }

    public static final boolean hasReceiver(ProtoBuf.Function function) {
        Intrinsics.checkNotNullParameter(function, "<this>");
        return function.hasReceiverType() || function.hasReceiverTypeId();
    }

    public static final ProtoBuf.Type receiverType(ProtoBuf.Function function, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(function, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (function.hasReceiverType()) {
            return function.getReceiverType();
        }
        if (function.hasReceiverTypeId()) {
            return typeTable.get(function.getReceiverTypeId());
        }
        return null;
    }

    public static final ProtoBuf.Type returnType(ProtoBuf.Property property, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(property, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (property.hasReturnType()) {
            ProtoBuf.Type returnType = property.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            return returnType;
        }
        if (property.hasReturnTypeId()) {
            return typeTable.get(property.getReturnTypeId());
        }
        throw new IllegalStateException("No returnType in ProtoBuf.Property".toString());
    }

    public static final boolean hasReceiver(ProtoBuf.Property property) {
        Intrinsics.checkNotNullParameter(property, "<this>");
        return property.hasReceiverType() || property.hasReceiverTypeId();
    }

    public static final ProtoBuf.Type receiverType(ProtoBuf.Property property, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(property, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (property.hasReceiverType()) {
            return property.getReceiverType();
        }
        if (property.hasReceiverTypeId()) {
            return typeTable.get(property.getReceiverTypeId());
        }
        return null;
    }

    public static final ProtoBuf.Type type(ProtoBuf.ValueParameter valueParameter, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(valueParameter, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (valueParameter.hasType()) {
            ProtoBuf.Type type = valueParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return type;
        }
        if (valueParameter.hasTypeId()) {
            return typeTable.get(valueParameter.getTypeId());
        }
        throw new IllegalStateException("No type in ProtoBuf.ValueParameter".toString());
    }

    public static final ProtoBuf.Type varargElementType(ProtoBuf.ValueParameter valueParameter, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(valueParameter, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (valueParameter.hasVarargElementType()) {
            return valueParameter.getVarargElementType();
        }
        if (valueParameter.hasVarargElementTypeId()) {
            return typeTable.get(valueParameter.getVarargElementTypeId());
        }
        return null;
    }

    public static final ProtoBuf.Type outerType(ProtoBuf.Type type, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(type, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (type.hasOuterType()) {
            return type.getOuterType();
        }
        if (type.hasOuterTypeId()) {
            return typeTable.get(type.getOuterTypeId());
        }
        return null;
    }

    public static final ProtoBuf.Type abbreviatedType(ProtoBuf.Type type, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(type, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (type.hasAbbreviatedType()) {
            return type.getAbbreviatedType();
        }
        if (type.hasAbbreviatedTypeId()) {
            return typeTable.get(type.getAbbreviatedTypeId());
        }
        return null;
    }

    public static final ProtoBuf.Type underlyingType(ProtoBuf.TypeAlias typeAlias, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(typeAlias, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (typeAlias.hasUnderlyingType()) {
            ProtoBuf.Type underlyingType = typeAlias.getUnderlyingType();
            Intrinsics.checkNotNullExpressionValue(underlyingType, "getUnderlyingType(...)");
            return underlyingType;
        }
        if (typeAlias.hasUnderlyingTypeId()) {
            return typeTable.get(typeAlias.getUnderlyingTypeId());
        }
        throw new IllegalStateException("No underlyingType in ProtoBuf.TypeAlias".toString());
    }

    public static final ProtoBuf.Type expandedType(ProtoBuf.TypeAlias typeAlias, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(typeAlias, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (typeAlias.hasExpandedType()) {
            ProtoBuf.Type expandedType = typeAlias.getExpandedType();
            Intrinsics.checkNotNullExpressionValue(expandedType, "getExpandedType(...)");
            return expandedType;
        }
        if (typeAlias.hasExpandedTypeId()) {
            return typeTable.get(typeAlias.getExpandedTypeId());
        }
        throw new IllegalStateException("No expandedType in ProtoBuf.TypeAlias".toString());
    }

    public static final ProtoBuf.Type isInstanceType(ProtoBuf.Expression expression, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(expression, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (expression.hasIsInstanceType()) {
            return expression.getIsInstanceType();
        }
        if (expression.hasIsInstanceTypeId()) {
            return typeTable.get(expression.getIsInstanceTypeId());
        }
        return null;
    }

    public static final List<ProtoBuf.Type> contextReceiverTypes(ProtoBuf.Class r2, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(r2, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        List<ProtoBuf.Type> contextReceiverTypeList = r2.getContextReceiverTypeList();
        if (contextReceiverTypeList.isEmpty()) {
            contextReceiverTypeList = null;
        }
        if (contextReceiverTypeList != null) {
            return contextReceiverTypeList;
        }
        List<Integer> contextReceiverTypeIdList = r2.getContextReceiverTypeIdList();
        Intrinsics.checkNotNullExpressionValue(contextReceiverTypeIdList, "getContextReceiverTypeIdList(...)");
        List<Integer> list = contextReceiverTypeIdList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Integer num : list) {
            Intrinsics.checkNotNull(num);
            arrayList.add(typeTable.get(num.intValue()));
        }
        return arrayList;
    }

    public static final List<ProtoBuf.Type> contextReceiverTypes(ProtoBuf.Function function, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(function, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        List<ProtoBuf.Type> contextReceiverTypeList = function.getContextReceiverTypeList();
        if (contextReceiverTypeList.isEmpty()) {
            contextReceiverTypeList = null;
        }
        if (contextReceiverTypeList != null) {
            return contextReceiverTypeList;
        }
        List<Integer> contextReceiverTypeIdList = function.getContextReceiverTypeIdList();
        Intrinsics.checkNotNullExpressionValue(contextReceiverTypeIdList, "getContextReceiverTypeIdList(...)");
        List<Integer> list = contextReceiverTypeIdList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Integer num : list) {
            Intrinsics.checkNotNull(num);
            arrayList.add(typeTable.get(num.intValue()));
        }
        return arrayList;
    }

    public static final List<ProtoBuf.Type> contextReceiverTypes(ProtoBuf.Property property, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(property, "<this>");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        List<ProtoBuf.Type> contextReceiverTypeList = property.getContextReceiverTypeList();
        if (contextReceiverTypeList.isEmpty()) {
            contextReceiverTypeList = null;
        }
        if (contextReceiverTypeList != null) {
            return contextReceiverTypeList;
        }
        List<Integer> contextReceiverTypeIdList = property.getContextReceiverTypeIdList();
        Intrinsics.checkNotNullExpressionValue(contextReceiverTypeIdList, "getContextReceiverTypeIdList(...)");
        List<Integer> list = contextReceiverTypeIdList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Integer num : list) {
            Intrinsics.checkNotNull(num);
            arrayList.add(typeTable.get(num.intValue()));
        }
        return arrayList;
    }
}
