package org.jetbrains.kotlin.contracts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.contracts.description.AbstractContractProvider;
import org.jetbrains.kotlin.contracts.description.BooleanExpression;
import org.jetbrains.kotlin.contracts.description.CallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.ConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.ContractDescription;
import org.jetbrains.kotlin.contracts.description.ContractDescriptionElement;
import org.jetbrains.kotlin.contracts.description.ContractProviderImpl;
import org.jetbrains.kotlin.contracts.description.ContractProviderKey;
import org.jetbrains.kotlin.contracts.description.EffectDeclaration;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.ReturnsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanVariableReference;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.IsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.expressions.IsNullPredicate;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalAnd;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalNot;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalOr;
import org.jetbrains.kotlin.contracts.description.expressions.VariableReference;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.serialization.deserialization.ContractDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.TypeDeserializer;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ContractDeserializerImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J:\u0010\u0007\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0012\u0004\u0012\u00020\n\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/contracts/ContractDeserializerImpl;", "Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "deserializeContractFromFunction", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "Lorg/jetbrains/kotlin/contracts/description/AbstractContractProvider;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "ownerFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "typeDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/TypeDeserializer;", "ContractDeserializationWorker", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContractDeserializerImpl implements ContractDeserializer {
    private final DeserializationConfiguration configuration;
    private final StorageManager storageManager;

    public ContractDeserializerImpl(DeserializationConfiguration configuration, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.configuration = configuration;
        this.storageManager = storageManager;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ContractDeserializer
    public Pair<CallableDescriptor.UserDataKey<?>, AbstractContractProvider> deserializeContractFromFunction(ProtoBuf.Function proto, FunctionDescriptor ownerFunction, TypeTable typeTable, TypeDeserializer typeDeserializer) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(ownerFunction, "ownerFunction");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(typeDeserializer, "typeDeserializer");
        if (!proto.hasContract() || !this.configuration.getReadDeserializedContracts()) {
            return null;
        }
        ContractDeserializationWorker contractDeserializationWorker = new ContractDeserializationWorker(typeTable, typeDeserializer, ownerFunction, this.storageManager);
        ProtoBuf.Contract contract = proto.getContract();
        Intrinsics.checkNotNullExpressionValue(contract, "getContract(...)");
        ContractDescription contractDescriptionDeserializeContract = contractDeserializationWorker.deserializeContract(contract);
        if (contractDescriptionDeserializeContract == null) {
            return null;
        }
        return TuplesKt.to(ContractProviderKey.INSTANCE, new ContractProviderImpl(contractDescriptionDeserializeContract));
    }

    /* JADX INFO: compiled from: ContractDeserializerImpl.kt */
    @Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001:\u0002+,B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0011\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0011\u001a\u00020\u0018H\u0002J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0011\u001a\u00020\u0018H\u0002J\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0011\u001a\u00020\u00152\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0011\u001a\u00020\u0015H\u0002J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u0011\u001a\u00020\u0015H\u0002J\u0012\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0011\u001a\u00020\u0015H\u0002J\u0012\u0010#\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0011\u001a\u00020\u0015H\u0002J\f\u0010$\u001a\u00020%*\u00020\u0015H\u0002J\u0014\u0010&\u001a\u00020\u0014*\u00020\u00142\u0006\u0010'\u001a\u00020%H\u0002J\u000e\u0010(\u001a\u0004\u0018\u00010)*\u00020*H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/contracts/ContractDeserializerImpl$ContractDeserializationWorker;", "", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "typeDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/TypeDeserializer;", "ownerFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/serialization/deserialization/TypeDeserializer;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "deserializeConstant", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "value", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression$ConstantValue;", "deserializeContract", "Lorg/jetbrains/kotlin/contracts/description/ContractDescription;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Contract;", "deserializeExpression", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;", "deserializePossiblyConditionalEffect", "Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect;", "deserializeSimpleEffect", "extractPrimitiveExpression", "primitiveType", "Lorg/jetbrains/kotlin/contracts/ContractDeserializerImpl$ContractDeserializationWorker$PrimitiveExpressionType;", "extractType", "Lorg/jetbrains/kotlin/types/KotlinType;", "extractVariable", "Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", "getComplexType", "Lorg/jetbrains/kotlin/contracts/ContractDeserializerImpl$ContractDeserializationWorker$ComplexExpressionType;", "getPrimitiveType", "hasType", "", "invertIfNecessary", "shouldInvert", "toDescriptorInvocationKind", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect$InvocationKind;", "ComplexExpressionType", "PrimitiveExpressionType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ContractDeserializationWorker {
        private final FunctionDescriptor ownerFunction;
        private final StorageManager storageManager;
        private final TypeDeserializer typeDeserializer;
        private final TypeTable typeTable;

        /* JADX INFO: compiled from: ContractDeserializerImpl.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/contracts/ContractDeserializerImpl$ContractDeserializationWorker$ComplexExpressionType;", "", "(Ljava/lang/String;I)V", "AND_SEQUENCE", "OR_SEQUENCE", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private enum ComplexExpressionType {
            AND_SEQUENCE,
            OR_SEQUENCE;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

            public static EnumEntries<ComplexExpressionType> getEntries() {
                return $ENTRIES;
            }
        }

        /* JADX INFO: compiled from: ContractDeserializerImpl.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/contracts/ContractDeserializerImpl$ContractDeserializationWorker$PrimitiveExpressionType;", "", "(Ljava/lang/String;I)V", "VALUE_PARAMETER_REFERENCE", "RECEIVER_REFERENCE", "CONSTANT", "INSTANCE_CHECK", "NULLABILITY_CHECK", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
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

        /* JADX INFO: compiled from: ContractDeserializerImpl.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;
            public static final /* synthetic */ int[] $EnumSwitchMapping$1;
            public static final /* synthetic */ int[] $EnumSwitchMapping$2;
            public static final /* synthetic */ int[] $EnumSwitchMapping$3;
            public static final /* synthetic */ int[] $EnumSwitchMapping$4;

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
                int[] iArr5 = new int[ProtoBuf.Expression.ConstantValue.values().length];
                try {
                    iArr5[ProtoBuf.Expression.ConstantValue.TRUE.ordinal()] = 1;
                } catch (NoSuchFieldError unused14) {
                }
                try {
                    iArr5[ProtoBuf.Expression.ConstantValue.FALSE.ordinal()] = 2;
                } catch (NoSuchFieldError unused15) {
                }
                try {
                    iArr5[ProtoBuf.Expression.ConstantValue.NULL.ordinal()] = 3;
                } catch (NoSuchFieldError unused16) {
                }
                $EnumSwitchMapping$4 = iArr5;
            }
        }

        public ContractDeserializationWorker(TypeTable typeTable, TypeDeserializer typeDeserializer, FunctionDescriptor ownerFunction, StorageManager storageManager) {
            Intrinsics.checkNotNullParameter(typeTable, "typeTable");
            Intrinsics.checkNotNullParameter(typeDeserializer, "typeDeserializer");
            Intrinsics.checkNotNullParameter(ownerFunction, "ownerFunction");
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            this.typeTable = typeTable;
            this.typeDeserializer = typeDeserializer;
            this.ownerFunction = ownerFunction;
            this.storageManager = storageManager;
        }

        public final ContractDescription deserializeContract(ProtoBuf.Contract proto) {
            Intrinsics.checkNotNullParameter(proto, "proto");
            List<ProtoBuf.Effect> effectList = proto.getEffectList();
            Intrinsics.checkNotNullExpressionValue(effectList, "getEffectList(...)");
            List<ProtoBuf.Effect> list = effectList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (ProtoBuf.Effect effect : list) {
                Intrinsics.checkNotNull(effect);
                EffectDeclaration effectDeclarationDeserializePossiblyConditionalEffect = deserializePossiblyConditionalEffect(effect);
                if (effectDeclarationDeserializePossiblyConditionalEffect == null) {
                    return null;
                }
                arrayList.add(effectDeclarationDeserializePossiblyConditionalEffect);
            }
            return new ContractDescription(arrayList, this.ownerFunction, this.storageManager);
        }

        private final EffectDeclaration deserializePossiblyConditionalEffect(ProtoBuf.Effect proto) {
            EffectDeclaration effectDeclarationDeserializeSimpleEffect;
            if (proto.hasConclusionOfConditionalEffect()) {
                ProtoBuf.Expression conclusionOfConditionalEffect = proto.getConclusionOfConditionalEffect();
                Intrinsics.checkNotNullExpressionValue(conclusionOfConditionalEffect, "getConclusionOfConditionalEffect(...)");
                BooleanExpression booleanExpressionDeserializeExpression = deserializeExpression(conclusionOfConditionalEffect);
                if (booleanExpressionDeserializeExpression == null || (effectDeclarationDeserializeSimpleEffect = deserializeSimpleEffect(proto)) == null) {
                    return null;
                }
                return new ConditionalEffectDeclaration(effectDeclarationDeserializeSimpleEffect, booleanExpressionDeserializeExpression);
            }
            return deserializeSimpleEffect(proto);
        }

        private final EffectDeclaration deserializeSimpleEffect(ProtoBuf.Effect proto) {
            ConstantReference wildcard;
            VariableReference variableReferenceExtractVariable;
            EventOccurrencesRange descriptorInvocationKind;
            if (!proto.hasEffectType()) {
                return null;
            }
            ProtoBuf.Effect.EffectType effectType = proto.getEffectType();
            Intrinsics.checkNotNull(effectType);
            int i = WhenMappings.$EnumSwitchMapping$0[effectType.ordinal()];
            if (i == 1) {
                List<ProtoBuf.Expression> effectConstructorArgumentList = proto.getEffectConstructorArgumentList();
                Intrinsics.checkNotNullExpressionValue(effectConstructorArgumentList, "getEffectConstructorArgumentList(...)");
                ProtoBuf.Expression expression = (ProtoBuf.Expression) CollectionsKt.getOrNull(effectConstructorArgumentList, 0);
                if (expression == null) {
                    wildcard = ConstantReference.INSTANCE.getWILDCARD();
                } else {
                    ContractDescriptionElement contractDescriptionElementDeserializeExpression = deserializeExpression(expression);
                    wildcard = contractDescriptionElementDeserializeExpression instanceof ConstantReference ? (ConstantReference) contractDescriptionElementDeserializeExpression : null;
                    if (wildcard == null) {
                        return null;
                    }
                }
                return new ReturnsEffectDeclaration(wildcard);
            }
            if (i == 2) {
                return new ReturnsEffectDeclaration(ConstantReference.INSTANCE.getNOT_NULL());
            }
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            List<ProtoBuf.Expression> effectConstructorArgumentList2 = proto.getEffectConstructorArgumentList();
            Intrinsics.checkNotNullExpressionValue(effectConstructorArgumentList2, "getEffectConstructorArgumentList(...)");
            ProtoBuf.Expression expression2 = (ProtoBuf.Expression) CollectionsKt.getOrNull(effectConstructorArgumentList2, 0);
            if (expression2 == null || (variableReferenceExtractVariable = extractVariable(expression2)) == null) {
                return null;
            }
            if (proto.hasKind()) {
                ProtoBuf.Effect.InvocationKind kind = proto.getKind();
                Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
                descriptorInvocationKind = toDescriptorInvocationKind(kind);
                if (descriptorInvocationKind == null) {
                    return null;
                }
            } else {
                descriptorInvocationKind = EventOccurrencesRange.UNKNOWN;
            }
            return new CallsEffectDeclaration(variableReferenceExtractVariable, descriptorInvocationKind);
        }

        private final BooleanExpression deserializeExpression(ProtoBuf.Expression proto) {
            BooleanExpression booleanExpressionExtractPrimitiveExpression = extractPrimitiveExpression(proto, getPrimitiveType(proto));
            ComplexExpressionType complexType = getComplexType(proto);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = arrayList;
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, booleanExpressionExtractPrimitiveExpression);
            int i = complexType == null ? -1 : WhenMappings.$EnumSwitchMapping$1[complexType.ordinal()];
            if (i == -1) {
                return booleanExpressionExtractPrimitiveExpression;
            }
            if (i == 1) {
                List<ProtoBuf.Expression> andArgumentList = proto.getAndArgumentList();
                Intrinsics.checkNotNullExpressionValue(andArgumentList, "getAndArgumentList(...)");
                for (ProtoBuf.Expression expression : andArgumentList) {
                    Intrinsics.checkNotNull(expression);
                    BooleanExpression booleanExpressionDeserializeExpression = deserializeExpression(expression);
                    if (booleanExpressionDeserializeExpression == null) {
                        return null;
                    }
                    arrayList2.add(booleanExpressionDeserializeExpression);
                }
                Iterator it = arrayList.iterator();
                if (!it.hasNext()) {
                    throw new UnsupportedOperationException("Empty collection can't be reduced.");
                }
                Object next = it.next();
                while (it.hasNext()) {
                    next = (BooleanExpression) new LogicalAnd((BooleanExpression) next, (BooleanExpression) it.next());
                }
                return (BooleanExpression) next;
            }
            if (i == 2) {
                List<ProtoBuf.Expression> orArgumentList = proto.getOrArgumentList();
                Intrinsics.checkNotNullExpressionValue(orArgumentList, "getOrArgumentList(...)");
                for (ProtoBuf.Expression expression2 : orArgumentList) {
                    Intrinsics.checkNotNull(expression2);
                    BooleanExpression booleanExpressionDeserializeExpression2 = deserializeExpression(expression2);
                    if (booleanExpressionDeserializeExpression2 == null) {
                        return null;
                    }
                    arrayList2.add(booleanExpressionDeserializeExpression2);
                }
                Iterator it2 = arrayList.iterator();
                if (!it2.hasNext()) {
                    throw new UnsupportedOperationException("Empty collection can't be reduced.");
                }
                Object next2 = it2.next();
                while (it2.hasNext()) {
                    next2 = (BooleanExpression) new LogicalOr((BooleanExpression) next2, (BooleanExpression) it2.next());
                }
                return (BooleanExpression) next2;
            }
            throw new NoWhenBranchMatchedException();
        }

        private final BooleanExpression extractPrimitiveExpression(ProtoBuf.Expression proto, PrimitiveExpressionType primitiveType) {
            KotlinType kotlinTypeExtractType;
            Boolean bool = Flags.IS_NEGATED.get(proto.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            boolean zBooleanValue = bool.booleanValue();
            int i = primitiveType == null ? -1 : WhenMappings.$EnumSwitchMapping$2[primitiveType.ordinal()];
            if (i == -1) {
                return null;
            }
            if (i == 1 || i == 2) {
                VariableReference variableReferenceExtractVariable = extractVariable(proto);
                BooleanVariableReference booleanVariableReference = variableReferenceExtractVariable instanceof BooleanVariableReference ? (BooleanVariableReference) variableReferenceExtractVariable : null;
                if (booleanVariableReference != null) {
                    return invertIfNecessary(booleanVariableReference, zBooleanValue);
                }
                return null;
            }
            if (i == 3) {
                ProtoBuf.Expression.ConstantValue constantValue = proto.getConstantValue();
                Intrinsics.checkNotNullExpressionValue(constantValue, "getConstantValue(...)");
                ConstantReference constantReferenceDeserializeConstant = deserializeConstant(constantValue);
                BooleanConstantReference booleanConstantReference = constantReferenceDeserializeConstant instanceof BooleanConstantReference ? (BooleanConstantReference) constantReferenceDeserializeConstant : null;
                if (booleanConstantReference != null) {
                    return invertIfNecessary(booleanConstantReference, zBooleanValue);
                }
                return null;
            }
            if (i == 4) {
                VariableReference variableReferenceExtractVariable2 = extractVariable(proto);
                if (variableReferenceExtractVariable2 == null || (kotlinTypeExtractType = extractType(proto)) == null) {
                    return null;
                }
                return new IsInstancePredicate(variableReferenceExtractVariable2, kotlinTypeExtractType, zBooleanValue);
            }
            if (i == 5) {
                VariableReference variableReferenceExtractVariable3 = extractVariable(proto);
                if (variableReferenceExtractVariable3 == null) {
                    return null;
                }
                return new IsNullPredicate(variableReferenceExtractVariable3, zBooleanValue);
            }
            throw new NoWhenBranchMatchedException();
        }

        private final BooleanExpression invertIfNecessary(BooleanExpression booleanExpression, boolean z) {
            return z ? new LogicalNot(booleanExpression) : booleanExpression;
        }

        private final VariableReference extractVariable(ProtoBuf.Expression proto) {
            ValueParameterDescriptor valueParameterDescriptor;
            if (!proto.hasValueParameterReference()) {
                return null;
            }
            if (proto.getValueParameterReference() == 0) {
                ReceiverParameterDescriptor extensionReceiverParameter = this.ownerFunction.getExtensionReceiverParameter();
                if (extensionReceiverParameter == null) {
                    return null;
                }
                valueParameterDescriptor = extensionReceiverParameter;
            } else {
                List<ValueParameterDescriptor> valueParameters = this.ownerFunction.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                ValueParameterDescriptor valueParameterDescriptor2 = (ValueParameterDescriptor) CollectionsKt.getOrNull(valueParameters, proto.getValueParameterReference() - 1);
                if (valueParameterDescriptor2 == null) {
                    return null;
                }
                valueParameterDescriptor = valueParameterDescriptor2;
            }
            if (!KotlinBuiltIns.isBoolean(valueParameterDescriptor.getType())) {
                return new VariableReference(valueParameterDescriptor);
            }
            return new BooleanVariableReference(valueParameterDescriptor);
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

        private final KotlinType extractType(ProtoBuf.Expression proto) {
            TypeDeserializer typeDeserializer = this.typeDeserializer;
            ProtoBuf.Type typeIsInstanceType = ProtoTypeTableUtilKt.isInstanceType(proto, this.typeTable);
            if (typeIsInstanceType == null) {
                return null;
            }
            return typeDeserializer.type(typeIsInstanceType);
        }

        private final ConstantReference deserializeConstant(ProtoBuf.Expression.ConstantValue value) {
            int i = WhenMappings.$EnumSwitchMapping$4[value.ordinal()];
            if (i == 1) {
                return BooleanConstantReference.INSTANCE.getTRUE();
            }
            if (i == 2) {
                return BooleanConstantReference.INSTANCE.getFALSE();
            }
            if (i == 3) {
                return ConstantReference.INSTANCE.getNULL();
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
}
