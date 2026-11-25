package org.jetbrains.kotlin.serialization;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.AbstractContractProvider;
import org.jetbrains.kotlin.contracts.description.CallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.ConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.ContractDescription;
import org.jetbrains.kotlin.contracts.description.ContractDescriptionElement;
import org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor;
import org.jetbrains.kotlin.contracts.description.ContractProviderKey;
import org.jetbrains.kotlin.contracts.description.EffectDeclaration;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.ReturnsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.IsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.expressions.IsNullPredicate;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalAnd;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalNot;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalOr;
import org.jetbrains.kotlin.contracts.description.expressions.VariableReference;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: compiled from: ContractSerializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/serialization/ContractSerializer;", "", "()V", "serializeContractOfFunctionIfAny", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "parentSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "ContractSerializerWorker", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContractSerializer {
    public final void serializeContractOfFunctionIfAny(FunctionDescriptor functionDescriptor, ProtoBuf.Function.Builder proto, DescriptorSerializer parentSerializer) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(parentSerializer, "parentSerializer");
        AbstractContractProvider abstractContractProvider = (AbstractContractProvider) functionDescriptor.getUserData(ContractProviderKey.INSTANCE);
        ContractDescription contractDescription = abstractContractProvider != null ? abstractContractProvider.getContractDescription() : null;
        if (contractDescription != null) {
            proto.setContract(new ContractSerializerWorker(parentSerializer).contractProto(contractDescription));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ContractSerializer.kt */
    @Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eJ\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0014\u0010\u001c\u001a\u00020\u0016*\u00020\n2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/serialization/ContractSerializer$ContractSerializerWorker;", "", "parentSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "(Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;)V", "constantValueProtobufEnum", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression$ConstantValue;", "constantReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "contractExpressionProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression$Builder;", "contractDescriptionElement", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionElement;", "contractDescription", "Lorg/jetbrains/kotlin/contracts/description/ContractDescription;", "contractProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Contract$Builder;", "effectProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect$Builder;", "effectDeclaration", "Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "fillEffectProto", "", "builder", "invocationKindProtobufEnum", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect$InvocationKind;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "writeFlags", "newFlagsValue", "", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class ContractSerializerWorker {
        private final DescriptorSerializer parentSerializer;

        /* JADX INFO: compiled from: ContractSerializer.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[EventOccurrencesRange.values().length];
                try {
                    iArr[EventOccurrencesRange.AT_MOST_ONCE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[EventOccurrencesRange.EXACTLY_ONCE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[EventOccurrencesRange.AT_LEAST_ONCE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public ContractSerializerWorker(DescriptorSerializer parentSerializer) {
            Intrinsics.checkNotNullParameter(parentSerializer, "parentSerializer");
            this.parentSerializer = parentSerializer;
        }

        public final ProtoBuf.Contract.Builder contractProto(ContractDescription contractDescription) {
            Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
            ProtoBuf.Contract.Builder builderNewBuilder = ProtoBuf.Contract.newBuilder();
            Iterator<T> it = contractDescription.getEffects().iterator();
            while (it.hasNext()) {
                builderNewBuilder.addEffect(effectProto((EffectDeclaration) it.next(), contractDescription));
            }
            Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "apply(...)");
            return builderNewBuilder;
        }

        private final ProtoBuf.Effect.Builder effectProto(EffectDeclaration effectDeclaration, ContractDescription contractDescription) {
            ProtoBuf.Effect.Builder builderNewBuilder = ProtoBuf.Effect.newBuilder();
            Intrinsics.checkNotNull(builderNewBuilder);
            fillEffectProto(builderNewBuilder, effectDeclaration, contractDescription);
            Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "apply(...)");
            return builderNewBuilder;
        }

        private final void fillEffectProto(ProtoBuf.Effect.Builder builder, EffectDeclaration effectDeclaration, ContractDescription contractDescription) {
            if (effectDeclaration instanceof ConditionalEffectDeclaration) {
                ConditionalEffectDeclaration conditionalEffectDeclaration = (ConditionalEffectDeclaration) effectDeclaration;
                builder.setConclusionOfConditionalEffect(contractExpressionProto(conditionalEffectDeclaration.getCondition(), contractDescription));
                fillEffectProto(builder, conditionalEffectDeclaration.getEffect(), contractDescription);
                return;
            }
            if (effectDeclaration instanceof ReturnsEffectDeclaration) {
                ReturnsEffectDeclaration returnsEffectDeclaration = (ReturnsEffectDeclaration) effectDeclaration;
                if (Intrinsics.areEqual(returnsEffectDeclaration.getValue(), ConstantReference.INSTANCE.getNOT_NULL())) {
                    builder.setEffectType(ProtoBuf.Effect.EffectType.RETURNS_NOT_NULL);
                    return;
                } else if (Intrinsics.areEqual(returnsEffectDeclaration.getValue(), ConstantReference.INSTANCE.getWILDCARD())) {
                    builder.setEffectType(ProtoBuf.Effect.EffectType.RETURNS_CONSTANT);
                    return;
                } else {
                    builder.setEffectType(ProtoBuf.Effect.EffectType.RETURNS_CONSTANT);
                    builder.addEffectConstructorArgument(contractExpressionProto(returnsEffectDeclaration.getValue(), contractDescription));
                    return;
                }
            }
            if (effectDeclaration instanceof CallsEffectDeclaration) {
                builder.setEffectType(ProtoBuf.Effect.EffectType.CALLS);
                CallsEffectDeclaration callsEffectDeclaration = (CallsEffectDeclaration) effectDeclaration;
                builder.addEffectConstructorArgument(contractExpressionProto(callsEffectDeclaration.getVariableReference(), contractDescription));
                ProtoBuf.Effect.InvocationKind invocationKindInvocationKindProtobufEnum = invocationKindProtobufEnum(callsEffectDeclaration.getKind());
                if (invocationKindInvocationKindProtobufEnum != null) {
                    builder.setKind(invocationKindInvocationKindProtobufEnum);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ProtoBuf.Expression.Builder contractExpressionProto(ContractDescriptionElement contractDescriptionElement, final ContractDescription contractDescription) {
            return (ProtoBuf.Expression.Builder) contractDescriptionElement.accept(new ContractDescriptionVisitor<ProtoBuf.Expression.Builder, Unit>() { // from class: org.jetbrains.kotlin.serialization.ContractSerializer$ContractSerializerWorker$contractExpressionProto$1
                @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitLogicalOr(LogicalOr logicalOr, Unit data) {
                    Intrinsics.checkNotNullParameter(logicalOr, "logicalOr");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builder = (ProtoBuf.Expression.Builder) logicalOr.getLeft().accept(this, data);
                    if (builder.getAndArgumentCount() != 0) {
                        ProtoBuf.Expression.Builder builderNewBuilder = ProtoBuf.Expression.newBuilder();
                        ContractSerializer.ContractSerializerWorker contractSerializerWorker = this.this$0;
                        ContractDescription contractDescription2 = contractDescription;
                        builderNewBuilder.addOrArgument(builder);
                        builderNewBuilder.addOrArgument(contractSerializerWorker.contractExpressionProto(logicalOr.getRight(), contractDescription2));
                        Intrinsics.checkNotNull(builderNewBuilder);
                        return builderNewBuilder;
                    }
                    builder.addOrArgument(this.this$0.contractExpressionProto(logicalOr.getRight(), contractDescription));
                    return builder;
                }

                @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitLogicalAnd(LogicalAnd logicalAnd, Unit data) {
                    Intrinsics.checkNotNullParameter(logicalAnd, "logicalAnd");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builder = (ProtoBuf.Expression.Builder) logicalAnd.getLeft().accept(this, data);
                    if (builder.getOrArgumentCount() != 0) {
                        ProtoBuf.Expression.Builder builderNewBuilder = ProtoBuf.Expression.newBuilder();
                        ContractSerializer.ContractSerializerWorker contractSerializerWorker = this.this$0;
                        ContractDescription contractDescription2 = contractDescription;
                        builderNewBuilder.addAndArgument(builder);
                        builderNewBuilder.addAndArgument(contractSerializerWorker.contractExpressionProto(logicalAnd.getRight(), contractDescription2));
                        Intrinsics.checkNotNull(builderNewBuilder);
                        return builderNewBuilder;
                    }
                    builder.addAndArgument(this.this$0.contractExpressionProto(logicalAnd.getRight(), contractDescription));
                    return builder;
                }

                @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitLogicalNot(LogicalNot logicalNot, Unit data) {
                    Intrinsics.checkNotNullParameter(logicalNot, "logicalNot");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builder = (ProtoBuf.Expression.Builder) logicalNot.getArg().accept(this, data);
                    this.this$0.writeFlags(builder, Flags.IS_NEGATED.invert(builder.getFlags()));
                    return builder;
                }

                @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitIsInstancePredicate(IsInstancePredicate isInstancePredicate, Unit data) {
                    Intrinsics.checkNotNullParameter(isInstancePredicate, "isInstancePredicate");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builderVisitVariableReference = visitVariableReference(isInstancePredicate.getArg(), data);
                    builderVisitVariableReference.setIsInstanceTypeId(this.this$0.parentSerializer.typeId(isInstancePredicate.getType()));
                    this.this$0.writeFlags(builderVisitVariableReference, Flags.getContractExpressionFlags(isInstancePredicate.getIsNegated(), false));
                    return builderVisitVariableReference;
                }

                @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitIsNullPredicate(IsNullPredicate isNullPredicate, Unit data) {
                    Intrinsics.checkNotNullParameter(isNullPredicate, "isNullPredicate");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builderVisitVariableReference = visitVariableReference(isNullPredicate.getArg(), data);
                    this.this$0.writeFlags(builderVisitVariableReference, Flags.getContractExpressionFlags(isNullPredicate.getIsNegated(), true));
                    return builderVisitVariableReference;
                }

                @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitConstantDescriptor(ConstantReference constantReference, Unit data) {
                    Intrinsics.checkNotNullParameter(constantReference, "constantReference");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builderNewBuilder = ProtoBuf.Expression.newBuilder();
                    ProtoBuf.Expression.ConstantValue constantValueConstantValueProtobufEnum = this.this$0.constantValueProtobufEnum(constantReference);
                    if (constantValueConstantValueProtobufEnum != null) {
                        builderNewBuilder.setConstantValue(constantValueConstantValueProtobufEnum);
                    }
                    Intrinsics.checkNotNull(builderNewBuilder);
                    return builderNewBuilder;
                }

                /* JADX WARN: Code duplicated, block: B:13:0x004c  */
                @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitVariableReference(VariableReference variableReference, Unit data) {
                    Integer numValueOf;
                    Intrinsics.checkNotNullParameter(variableReference, "variableReference");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builderNewBuilder = ProtoBuf.Expression.newBuilder();
                    ParameterDescriptor descriptor = variableReference.getDescriptor();
                    if (descriptor instanceof ReceiverParameterDescriptor) {
                        numValueOf = 0;
                    } else if (descriptor instanceof ValueParameterDescriptor) {
                        Integer numValueOf2 = Integer.valueOf(contractDescription.getOwnerFunction().getValueParameters().indexOf(descriptor));
                        if (numValueOf2.intValue() == -1) {
                            numValueOf2 = null;
                        }
                        if (numValueOf2 != null) {
                            numValueOf = Integer.valueOf(numValueOf2.intValue() + 1);
                        } else {
                            numValueOf = null;
                        }
                    } else {
                        numValueOf = null;
                    }
                    if (numValueOf != null) {
                        builderNewBuilder.setValueParameterReference(numValueOf.intValue());
                        Intrinsics.checkNotNull(builderNewBuilder);
                        return builderNewBuilder;
                    }
                    Intrinsics.checkNotNull(builderNewBuilder);
                    return builderNewBuilder;
                }
            }, Unit.INSTANCE);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void writeFlags(ProtoBuf.Expression.Builder builder, int i) {
            if (builder.getFlags() != i) {
                builder.setFlags(i);
            }
        }

        private final ProtoBuf.Effect.InvocationKind invocationKindProtobufEnum(EventOccurrencesRange kind) {
            int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
            if (i == 1) {
                return ProtoBuf.Effect.InvocationKind.AT_MOST_ONCE;
            }
            if (i == 2) {
                return ProtoBuf.Effect.InvocationKind.EXACTLY_ONCE;
            }
            if (i != 3) {
                return null;
            }
            return ProtoBuf.Effect.InvocationKind.AT_LEAST_ONCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ProtoBuf.Expression.ConstantValue constantValueProtobufEnum(ConstantReference constantReference) {
            if (Intrinsics.areEqual(constantReference, BooleanConstantReference.INSTANCE.getTRUE())) {
                return ProtoBuf.Expression.ConstantValue.TRUE;
            }
            if (Intrinsics.areEqual(constantReference, BooleanConstantReference.INSTANCE.getFALSE())) {
                return ProtoBuf.Expression.ConstantValue.FALSE;
            }
            if (Intrinsics.areEqual(constantReference, ConstantReference.INSTANCE.getNULL())) {
                return ProtoBuf.Expression.ConstantValue.NULL;
            }
            if (Intrinsics.areEqual(constantReference, ConstantReference.INSTANCE.getNOT_NULL())) {
                throw new IllegalStateException("Internal error during serialization of function contract: NOT_NULL constant isn't denotable in protobuf format. Its serialization should be handled at higher level");
            }
            if (Intrinsics.areEqual(constantReference, ConstantReference.INSTANCE.getWILDCARD())) {
                return null;
            }
            throw new IllegalArgumentException("Unknown constant: " + constantReference);
        }
    }
}
