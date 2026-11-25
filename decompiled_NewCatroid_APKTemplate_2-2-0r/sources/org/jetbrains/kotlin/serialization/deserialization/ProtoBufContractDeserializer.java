package org.jetbrains.kotlin.serialization.deserialization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.KtBinaryLogicExpression;
import org.jetbrains.kotlin.contracts.description.KtBooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.KtBooleanExpression;
import org.jetbrains.kotlin.contracts.description.KtBooleanValueParameterReference;
import org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtIsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.KtIsNullPredicate;
import org.jetbrains.kotlin.contracts.description.KtLogicalNot;
import org.jetbrains.kotlin.contracts.description.KtReturnsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtValueParameterReference;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: compiled from: ProtoBufContractDeserializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\u00020\u0004:\u0002-.B\u0005¢\u0006\u0002\u0010\u0005J5\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00028\u0002H\u0002¢\u0006\u0002\u0010\rJ\u0017\u0010\u000e\u001a\u0004\u0018\u00018\u00002\u0006\u0010\b\u001a\u00020\tH&¢\u0006\u0002\u0010\u000fJ+\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\f\u001a\u00028\u0002H&¢\u0006\u0002\u0010\u0014J+\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00028\u0002H\u0002¢\u0006\u0002\u0010\u0015J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\b\u001a\u00020\tH\u0002J\u0014\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0019H&J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0014\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0019H&J\u001c\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00192\u0006\u0010\u001d\u001a\u00020\u001eH&J+\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00028\u0002H\u0002¢\u0006\u0002\u0010 J+\u0010!\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\"2\u0006\u0010\b\u001a\u00020#2\u0006\u0010\f\u001a\u00028\u0002H\u0004¢\u0006\u0002\u0010$J+\u0010%\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\"2\u0006\u0010\b\u001a\u00020#2\u0006\u0010\f\u001a\u00028\u0002H\u0002¢\u0006\u0002\u0010$J\f\u0010&\u001a\u00020'*\u00020\tH\u0002J,\u0010(\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007*\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00072\u0006\u0010)\u001a\u00020'H\u0002J\f\u0010*\u001a\u00020+*\u00020,H\u0002¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBufContractDeserializer;", "Type", "Diagnostic", "Owner", "", "()V", "extractPrimitiveExpression", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;", "primitiveType", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBufContractDeserializer$PrimitiveExpressionType;", "owner", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBufContractDeserializer$PrimitiveExpressionType;Ljava/lang/Object;)Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "extractType", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;)Ljava/lang/Object;", "extractVariable", "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "valueParameterIndex", "", "(ILjava/lang/Object;)Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "getComplexType", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBufContractDeserializer$ComplexExpressionType;", "getNotNull", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "getPrimitiveType", "getWildcard", "loadConstant", "value", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression$ConstantValue;", "loadExpression", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "loadPossiblyConditionalEffect", "Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect;Ljava/lang/Object;)Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "loadSimpleEffect", "hasType", "", "invertIfNecessary", "shouldInvert", "toDescriptorInvocationKind", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect$InvocationKind;", "ComplexExpressionType", "PrimitiveExpressionType", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ProtoBufContractDeserializer<Type, Diagnostic, Owner> {

    /* JADX INFO: compiled from: ProtoBufContractDeserializer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBufContractDeserializer$ComplexExpressionType;", "", "(Ljava/lang/String;I)V", "AND_SEQUENCE", "OR_SEQUENCE", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum ComplexExpressionType {
        AND_SEQUENCE,
        OR_SEQUENCE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ComplexExpressionType> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: ProtoBufContractDeserializer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBufContractDeserializer$PrimitiveExpressionType;", "", "(Ljava/lang/String;I)V", "VALUE_PARAMETER_REFERENCE", "RECEIVER_REFERENCE", "CONSTANT", "INSTANCE_CHECK", "NULLABILITY_CHECK", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum PrimitiveExpressionType {
        VALUE_PARAMETER_REFERENCE,
        RECEIVER_REFERENCE,
        CONSTANT,
        INSTANCE_CHECK,
        NULLABILITY_CHECK;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<PrimitiveExpressionType> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: ProtoBufContractDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;

        static {
            int[] iArr = new int[ProtoBuf.Effect.EffectType.values().length];
            try {
                iArr[ProtoBuf.Effect.EffectType.RETURNS_CONSTANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.Effect.EffectType.RETURNS_NOT_NULL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.Effect.EffectType.CALLS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ComplexExpressionType.values().length];
            try {
                iArr2[ComplexExpressionType.AND_SEQUENCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ComplexExpressionType.OR_SEQUENCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[PrimitiveExpressionType.values().length];
            try {
                iArr3[PrimitiveExpressionType.VALUE_PARAMETER_REFERENCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr3[PrimitiveExpressionType.RECEIVER_REFERENCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr3[PrimitiveExpressionType.CONSTANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr3[PrimitiveExpressionType.INSTANCE_CHECK.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr3[PrimitiveExpressionType.NULLABILITY_CHECK.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$2 = iArr3;
            int[] iArr4 = new int[ProtoBuf.Effect.InvocationKind.values().length];
            try {
                iArr4[ProtoBuf.Effect.InvocationKind.AT_MOST_ONCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr4[ProtoBuf.Effect.InvocationKind.EXACTLY_ONCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr4[ProtoBuf.Effect.InvocationKind.AT_LEAST_ONCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            $EnumSwitchMapping$3 = iArr4;
        }
    }

    public abstract Type extractType(ProtoBuf.Expression proto);

    public abstract KtValueParameterReference<Type, Diagnostic> extractVariable(int valueParameterIndex, Owner owner);

    public abstract KtConstantReference<Type, Diagnostic> getNotNull();

    public abstract KtConstantReference<Type, Diagnostic> getWildcard();

    public abstract KtConstantReference<Type, Diagnostic> loadConstant(ProtoBuf.Expression.ConstantValue value);

    protected final KtEffectDeclaration<Type, Diagnostic> loadPossiblyConditionalEffect(ProtoBuf.Effect proto, Owner owner) {
        KtEffectDeclaration<Type, Diagnostic> ktEffectDeclarationLoadSimpleEffect;
        Intrinsics.checkNotNullParameter(proto, "proto");
        if (proto.hasConclusionOfConditionalEffect()) {
            ProtoBuf.Expression conclusionOfConditionalEffect = proto.getConclusionOfConditionalEffect();
            Intrinsics.checkNotNullExpressionValue(conclusionOfConditionalEffect, "getConclusionOfConditionalEffect(...)");
            KtBooleanExpression<Type, Diagnostic> ktBooleanExpressionLoadExpression = loadExpression(conclusionOfConditionalEffect, owner);
            if (ktBooleanExpressionLoadExpression == null || (ktEffectDeclarationLoadSimpleEffect = loadSimpleEffect(proto, owner)) == null) {
                return null;
            }
            return new KtConditionalEffectDeclaration(ktEffectDeclarationLoadSimpleEffect, ktBooleanExpressionLoadExpression);
        }
        return loadSimpleEffect(proto, owner);
    }

    private final KtEffectDeclaration<Type, Diagnostic> loadSimpleEffect(ProtoBuf.Effect proto, Owner owner) {
        KtConstantReference<Type, Diagnostic> wildcard;
        KtValueParameterReference<Type, Diagnostic> ktValueParameterReferenceExtractVariable;
        EventOccurrencesRange descriptorInvocationKind;
        if (!proto.hasEffectType()) {
            return null;
        }
        ProtoBuf.Effect.EffectType effectType = proto.getEffectType();
        Intrinsics.checkNotNullExpressionValue(effectType, "getEffectType(...)");
        int i = WhenMappings.$EnumSwitchMapping$0[effectType.ordinal()];
        if (i == 1) {
            List<ProtoBuf.Expression> effectConstructorArgumentList = proto.getEffectConstructorArgumentList();
            Intrinsics.checkNotNullExpressionValue(effectConstructorArgumentList, "getEffectConstructorArgumentList(...)");
            ProtoBuf.Expression expression = (ProtoBuf.Expression) CollectionsKt.firstOrNull((List) effectConstructorArgumentList);
            if (expression == null) {
                wildcard = getWildcard();
            } else {
                KtBooleanExpression<Type, Diagnostic> ktBooleanExpressionLoadExpression = loadExpression(expression, owner);
                wildcard = ktBooleanExpressionLoadExpression instanceof KtConstantReference ? (KtConstantReference) ktBooleanExpressionLoadExpression : null;
                if (wildcard == null) {
                    return null;
                }
            }
            return new KtReturnsEffectDeclaration(wildcard);
        }
        if (i == 2) {
            return new KtReturnsEffectDeclaration(getNotNull());
        }
        if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
        List<ProtoBuf.Expression> effectConstructorArgumentList2 = proto.getEffectConstructorArgumentList();
        Intrinsics.checkNotNullExpressionValue(effectConstructorArgumentList2, "getEffectConstructorArgumentList(...)");
        ProtoBuf.Expression expression2 = (ProtoBuf.Expression) CollectionsKt.firstOrNull((List) effectConstructorArgumentList2);
        if (expression2 == null || (ktValueParameterReferenceExtractVariable = extractVariable(expression2, owner)) == null) {
            return null;
        }
        if (proto.hasKind()) {
            ProtoBuf.Effect.InvocationKind kind = proto.getKind();
            Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
            descriptorInvocationKind = toDescriptorInvocationKind(kind);
        } else {
            descriptorInvocationKind = EventOccurrencesRange.UNKNOWN;
        }
        return new KtCallsEffectDeclaration(ktValueParameterReferenceExtractVariable, descriptorInvocationKind);
    }

    private final KtBooleanExpression<Type, Diagnostic> loadExpression(ProtoBuf.Expression proto, Owner owner) {
        KtBooleanExpression<Type, Diagnostic> ktBooleanExpressionExtractPrimitiveExpression = extractPrimitiveExpression(proto, getPrimitiveType(proto), owner);
        ComplexExpressionType complexType = getComplexType(proto);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = arrayList;
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, ktBooleanExpressionExtractPrimitiveExpression);
        int i = complexType == null ? -1 : WhenMappings.$EnumSwitchMapping$1[complexType.ordinal()];
        if (i == -1) {
            return ktBooleanExpressionExtractPrimitiveExpression;
        }
        if (i == 1) {
            List<ProtoBuf.Expression> andArgumentList = proto.getAndArgumentList();
            Intrinsics.checkNotNullExpressionValue(andArgumentList, "getAndArgumentList(...)");
            for (ProtoBuf.Expression expression : andArgumentList) {
                Intrinsics.checkNotNull(expression);
                KtBooleanExpression<Type, Diagnostic> ktBooleanExpressionLoadExpression = loadExpression(expression, owner);
                if (ktBooleanExpressionLoadExpression == null) {
                    return null;
                }
                arrayList2.add(ktBooleanExpressionLoadExpression);
            }
            Iterator it = arrayList.iterator();
            if (!it.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object next = it.next();
            while (it.hasNext()) {
                next = (KtBooleanExpression) new KtBinaryLogicExpression((KtBooleanExpression) next, (KtBooleanExpression) it.next(), LogicOperationKind.AND);
            }
            return (KtBooleanExpression) next;
        }
        if (i == 2) {
            List<ProtoBuf.Expression> orArgumentList = proto.getOrArgumentList();
            Intrinsics.checkNotNullExpressionValue(orArgumentList, "getOrArgumentList(...)");
            for (ProtoBuf.Expression expression2 : orArgumentList) {
                Intrinsics.checkNotNull(expression2);
                KtBooleanExpression<Type, Diagnostic> ktBooleanExpressionLoadExpression2 = loadExpression(expression2, owner);
                if (ktBooleanExpressionLoadExpression2 == null) {
                    return null;
                }
                arrayList2.add(ktBooleanExpressionLoadExpression2);
            }
            Iterator it2 = arrayList.iterator();
            if (!it2.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object next2 = it2.next();
            while (it2.hasNext()) {
                next2 = (KtBooleanExpression) new KtBinaryLogicExpression((KtBooleanExpression) next2, (KtBooleanExpression) it2.next(), LogicOperationKind.OR);
            }
            return (KtBooleanExpression) next2;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final KtBooleanExpression<Type, Diagnostic> extractPrimitiveExpression(ProtoBuf.Expression proto, PrimitiveExpressionType primitiveType, Owner owner) {
        Type typeExtractType;
        Boolean bool = Flags.IS_NEGATED.get(proto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        boolean zBooleanValue = bool.booleanValue();
        int i = primitiveType == null ? -1 : WhenMappings.$EnumSwitchMapping$2[primitiveType.ordinal()];
        if (i == -1) {
            return null;
        }
        if (i == 1 || i == 2) {
            KtValueParameterReference<Type, Diagnostic> ktValueParameterReferenceExtractVariable = extractVariable(proto, owner);
            KtBooleanValueParameterReference ktBooleanValueParameterReference = ktValueParameterReferenceExtractVariable instanceof KtBooleanValueParameterReference ? (KtBooleanValueParameterReference) ktValueParameterReferenceExtractVariable : null;
            if (ktBooleanValueParameterReference != null) {
                return invertIfNecessary(ktBooleanValueParameterReference, zBooleanValue);
            }
            return null;
        }
        if (i == 3) {
            ProtoBuf.Expression.ConstantValue constantValue = proto.getConstantValue();
            Intrinsics.checkNotNullExpressionValue(constantValue, "getConstantValue(...)");
            KtConstantReference<Type, Diagnostic> ktConstantReferenceLoadConstant = loadConstant(constantValue);
            KtBooleanConstantReference ktBooleanConstantReference = ktConstantReferenceLoadConstant instanceof KtBooleanConstantReference ? (KtBooleanConstantReference) ktConstantReferenceLoadConstant : null;
            if (ktBooleanConstantReference != null) {
                return invertIfNecessary(ktBooleanConstantReference, zBooleanValue);
            }
            return null;
        }
        if (i == 4) {
            KtValueParameterReference<Type, Diagnostic> ktValueParameterReferenceExtractVariable2 = extractVariable(proto, owner);
            if (ktValueParameterReferenceExtractVariable2 == null || (typeExtractType = extractType(proto)) == null) {
                return null;
            }
            return new KtIsInstancePredicate(ktValueParameterReferenceExtractVariable2, typeExtractType, zBooleanValue);
        }
        if (i == 5) {
            KtValueParameterReference<Type, Diagnostic> ktValueParameterReferenceExtractVariable3 = extractVariable(proto, owner);
            if (ktValueParameterReferenceExtractVariable3 == null) {
                return null;
            }
            return new KtIsNullPredicate(ktValueParameterReferenceExtractVariable3, zBooleanValue);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final KtBooleanExpression<Type, Diagnostic> invertIfNecessary(KtBooleanExpression<Type, Diagnostic> ktBooleanExpression, boolean z) {
        return z ? new KtLogicalNot(ktBooleanExpression) : ktBooleanExpression;
    }

    private final KtValueParameterReference<Type, Diagnostic> extractVariable(ProtoBuf.Expression proto, Owner owner) {
        if (proto.hasValueParameterReference()) {
            return extractVariable(proto.getValueParameterReference() - 1, owner);
        }
        return null;
    }

    private final EventOccurrencesRange toDescriptorInvocationKind(ProtoBuf.Effect.InvocationKind invocationKind) {
        int i = WhenMappings.$EnumSwitchMapping$3[invocationKind.ordinal()];
        if (i == 1) {
            return EventOccurrencesRange.AT_MOST_ONCE;
        }
        if (i == 2) {
            return EventOccurrencesRange.EXACTLY_ONCE;
        }
        if (i == 3) {
            return EventOccurrencesRange.AT_LEAST_ONCE;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final ComplexExpressionType getComplexType(ProtoBuf.Expression proto) {
        boolean z = proto.getOrArgumentCount() != 0;
        boolean z2 = proto.getAndArgumentCount() != 0;
        if (z && z2) {
            return null;
        }
        if (z) {
            return ComplexExpressionType.OR_SEQUENCE;
        }
        if (z2) {
            return ComplexExpressionType.AND_SEQUENCE;
        }
        return null;
    }

    private final PrimitiveExpressionType getPrimitiveType(ProtoBuf.Expression proto) {
        ArrayList arrayList = new ArrayList();
        if (proto.hasValueParameterReference() && hasType(proto)) {
            arrayList.add(PrimitiveExpressionType.INSTANCE_CHECK);
        } else if (proto.hasValueParameterReference()) {
            Boolean bool = Flags.IS_NULL_CHECK_PREDICATE.get(proto.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            if (bool.booleanValue()) {
                arrayList.add(PrimitiveExpressionType.NULLABILITY_CHECK);
            }
        }
        if (!arrayList.isEmpty()) {
            return (PrimitiveExpressionType) CollectionsKt.singleOrNull((List) arrayList);
        }
        if (proto.hasValueParameterReference() && proto.getValueParameterReference() > 0) {
            arrayList.add(PrimitiveExpressionType.VALUE_PARAMETER_REFERENCE);
        } else if (proto.hasValueParameterReference() && proto.getValueParameterReference() == 0) {
            arrayList.add(PrimitiveExpressionType.RECEIVER_REFERENCE);
        } else if (proto.hasConstantValue()) {
            arrayList.add(PrimitiveExpressionType.CONSTANT);
        }
        return (PrimitiveExpressionType) CollectionsKt.singleOrNull((List) arrayList);
    }

    private final boolean hasType(ProtoBuf.Expression expression) {
        return expression.hasIsInstanceType() || expression.hasIsInstanceTypeId();
    }
}
