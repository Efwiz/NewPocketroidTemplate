package org.jetbrains.kotlin.fir.serialization;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.KtBinaryLogicExpression;
import org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtIsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.KtIsNullPredicate;
import org.jetbrains.kotlin.contracts.description.KtLogicalNot;
import org.jetbrains.kotlin.contracts.description.KtReturnsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtValueParameterReference;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.fir.contracts.ContractUtilsKt;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.description.ConeContractConstantValues;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: compiled from: FirContractSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirContractSerializer;", "", "()V", "serializeContractOfFunctionIfAny", "", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "parentSerializer", "Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "ContractSerializerWorker", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirContractSerializer {
    public final void serializeContractOfFunctionIfAny(FirFunction function, ProtoBuf.Function.Builder proto, FirElementSerializer parentSerializer) {
        List<FirEffectDeclaration> effects;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(parentSerializer, "parentSerializer");
        FirSimpleFunction firSimpleFunction = function instanceof FirSimpleFunction ? (FirSimpleFunction) function : null;
        FirContractDescription contractDescription = firSimpleFunction != null ? firSimpleFunction.getContractDescription() : null;
        if (contractDescription == null || (effects = ContractUtilsKt.getEffects(contractDescription)) == null || effects.isEmpty()) {
            return;
        }
        proto.setContract(new ContractSerializerWorker(parentSerializer).contractProto(contractDescription));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FirContractSerializer.kt */
    @Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0016\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000bH\u0002J(\u0010\f\u001a\u00020\r2\u0016\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u000fj\u0002`\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u0012J(\u0010\u0015\u001a\u00020\u00162\u0016\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0018j\u0002`\u00192\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J0\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00162\u0016\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0018j\u0002`\u00192\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0014\u0010!\u001a\u00020\u001b*\u00020\r2\u0006\u0010\"\u001a\u00020#H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirContractSerializer$ContractSerializerWorker;", "", "parentSerializer", "Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "(Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;)V", "constantValueProtobufEnum", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression$ConstantValue;", "constantReference", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeConstantReference;", "contractExpressionProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression$Builder;", "contractDescriptionElement", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractDescriptionElement;", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "contractProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Contract$Builder;", "effectProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect$Builder;", "effectDeclaration", "Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeEffectDeclaration;", "fillEffectProto", "", "builder", "invocationKindProtobufEnum", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect$InvocationKind;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "writeFlags", "newFlagsValue", "", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class ContractSerializerWorker {
        private final FirElementSerializer parentSerializer;

        /* JADX INFO: compiled from: FirContractSerializer.kt */
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

        public ContractSerializerWorker(FirElementSerializer parentSerializer) {
            Intrinsics.checkNotNullParameter(parentSerializer, "parentSerializer");
            this.parentSerializer = parentSerializer;
        }

        public final ProtoBuf.Contract.Builder contractProto(FirContractDescription contractDescription) {
            Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
            ProtoBuf.Contract.Builder builderNewBuilder = ProtoBuf.Contract.newBuilder();
            List<FirEffectDeclaration> effects = ContractUtilsKt.getEffects(contractDescription);
            if (effects != null) {
                Iterator<T> it = effects.iterator();
                while (it.hasNext()) {
                    builderNewBuilder.addEffect(effectProto(((FirEffectDeclaration) it.next()).getEffect(), contractDescription));
                }
            }
            Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "apply(...)");
            return builderNewBuilder;
        }

        private final ProtoBuf.Effect.Builder effectProto(KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> effectDeclaration, FirContractDescription contractDescription) {
            ProtoBuf.Effect.Builder builderNewBuilder = ProtoBuf.Effect.newBuilder();
            Intrinsics.checkNotNull(builderNewBuilder);
            fillEffectProto(builderNewBuilder, effectDeclaration, contractDescription);
            Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "apply(...)");
            return builderNewBuilder;
        }

        private final void fillEffectProto(ProtoBuf.Effect.Builder builder, KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> effectDeclaration, FirContractDescription contractDescription) {
            if (effectDeclaration instanceof KtConditionalEffectDeclaration) {
                KtConditionalEffectDeclaration ktConditionalEffectDeclaration = (KtConditionalEffectDeclaration) effectDeclaration;
                builder.setConclusionOfConditionalEffect(contractExpressionProto(ktConditionalEffectDeclaration.getCondition(), contractDescription));
                fillEffectProto(builder, ktConditionalEffectDeclaration.getEffect(), contractDescription);
                return;
            }
            if (effectDeclaration instanceof KtReturnsEffectDeclaration) {
                KtReturnsEffectDeclaration ktReturnsEffectDeclaration = (KtReturnsEffectDeclaration) effectDeclaration;
                KtConstantReference value = ktReturnsEffectDeclaration.getValue();
                if (Intrinsics.areEqual(value, ConeContractConstantValues.INSTANCE.getNOT_NULL())) {
                    builder.setEffectType(ProtoBuf.Effect.EffectType.RETURNS_NOT_NULL);
                    return;
                } else if (Intrinsics.areEqual(value, ConeContractConstantValues.INSTANCE.getWILDCARD())) {
                    builder.setEffectType(ProtoBuf.Effect.EffectType.RETURNS_CONSTANT);
                    return;
                } else {
                    builder.setEffectType(ProtoBuf.Effect.EffectType.RETURNS_CONSTANT);
                    builder.addEffectConstructorArgument(contractExpressionProto(ktReturnsEffectDeclaration.getValue(), contractDescription));
                    return;
                }
            }
            if (effectDeclaration instanceof KtCallsEffectDeclaration) {
                builder.setEffectType(ProtoBuf.Effect.EffectType.CALLS);
                KtCallsEffectDeclaration ktCallsEffectDeclaration = (KtCallsEffectDeclaration) effectDeclaration;
                builder.addEffectConstructorArgument(contractExpressionProto(ktCallsEffectDeclaration.getValueParameterReference(), contractDescription));
                ProtoBuf.Effect.InvocationKind invocationKindInvocationKindProtobufEnum = invocationKindProtobufEnum(ktCallsEffectDeclaration.getKind());
                if (invocationKindInvocationKindProtobufEnum != null) {
                    builder.setKind(invocationKindInvocationKindProtobufEnum);
                    return;
                }
                return;
            }
            throw new IllegalStateException("Unsupported effect type: " + Reflection.getOrCreateKotlinClass(effectDeclaration.getClass()).getSimpleName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ProtoBuf.Expression.Builder contractExpressionProto(KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> contractDescriptionElement, final FirContractDescription contractDescription) {
            return (ProtoBuf.Expression.Builder) contractDescriptionElement.accept(new KtContractDescriptionVisitor<ProtoBuf.Expression.Builder, Unit, ConeKotlinType, ConeDiagnostic>() { // from class: org.jetbrains.kotlin.fir.serialization.FirContractSerializer$ContractSerializerWorker$contractExpressionProto$1
                @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitLogicalBinaryOperationContractExpression(KtBinaryLogicExpression<ConeKotlinType, ConeDiagnostic> binaryLogicExpression, Unit data) {
                    Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
                    Intrinsics.checkNotNullParameter(data, "data");
                    if (binaryLogicExpression.getKind() == LogicOperationKind.AND) {
                        return visitLogicalAnd(binaryLogicExpression, data);
                    }
                    return visitLogicalOr(binaryLogicExpression, data);
                }

                private final ProtoBuf.Expression.Builder visitLogicalOr(KtBinaryLogicExpression<ConeKotlinType, ConeDiagnostic> logicalOr, Unit data) {
                    ProtoBuf.Expression.Builder builder = (ProtoBuf.Expression.Builder) logicalOr.getLeft().accept(this, data);
                    if (builder.getAndArgumentCount() != 0) {
                        ProtoBuf.Expression.Builder builderNewBuilder = ProtoBuf.Expression.newBuilder();
                        FirContractSerializer.ContractSerializerWorker contractSerializerWorker = this.this$0;
                        FirContractDescription firContractDescription = contractDescription;
                        builderNewBuilder.addOrArgument(builder);
                        builderNewBuilder.addOrArgument(contractSerializerWorker.contractExpressionProto(logicalOr.getRight(), firContractDescription));
                        Intrinsics.checkNotNull(builderNewBuilder);
                        return builderNewBuilder;
                    }
                    builder.addOrArgument(this.this$0.contractExpressionProto(logicalOr.getRight(), contractDescription));
                    return builder;
                }

                private final ProtoBuf.Expression.Builder visitLogicalAnd(KtBinaryLogicExpression<ConeKotlinType, ConeDiagnostic> logicalAnd, Unit data) {
                    ProtoBuf.Expression.Builder builder = (ProtoBuf.Expression.Builder) logicalAnd.getLeft().accept(this, data);
                    if (builder.getOrArgumentCount() != 0) {
                        ProtoBuf.Expression.Builder builderNewBuilder = ProtoBuf.Expression.newBuilder();
                        FirContractSerializer.ContractSerializerWorker contractSerializerWorker = this.this$0;
                        FirContractDescription firContractDescription = contractDescription;
                        builderNewBuilder.addAndArgument(builder);
                        builderNewBuilder.addAndArgument(contractSerializerWorker.contractExpressionProto(logicalAnd.getRight(), firContractDescription));
                        Intrinsics.checkNotNull(builderNewBuilder);
                        return builderNewBuilder;
                    }
                    builder.addAndArgument(this.this$0.contractExpressionProto(logicalAnd.getRight(), contractDescription));
                    return builder;
                }

                @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitLogicalNot(KtLogicalNot<ConeKotlinType, ConeDiagnostic> logicalNot, Unit data) {
                    Intrinsics.checkNotNullParameter(logicalNot, "logicalNot");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builder = (ProtoBuf.Expression.Builder) logicalNot.getArg().accept(this, data);
                    this.this$0.writeFlags(builder, Flags.IS_NEGATED.invert(builder.getFlags()));
                    return builder;
                }

                @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitIsInstancePredicate(KtIsInstancePredicate<ConeKotlinType, ConeDiagnostic> isInstancePredicate, Unit data) {
                    Intrinsics.checkNotNullParameter(isInstancePredicate, "isInstancePredicate");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builderVisitValueParameterReference = visitValueParameterReference(isInstancePredicate.getArg(), data);
                    builderVisitValueParameterReference.setIsInstanceTypeId(this.this$0.parentSerializer.typeId(isInstancePredicate.getType()));
                    this.this$0.writeFlags(builderVisitValueParameterReference, Flags.getContractExpressionFlags(isInstancePredicate.getIsNegated(), false));
                    return builderVisitValueParameterReference;
                }

                @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitIsNullPredicate(KtIsNullPredicate<ConeKotlinType, ConeDiagnostic> isNullPredicate, Unit data) {
                    Intrinsics.checkNotNullParameter(isNullPredicate, "isNullPredicate");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builderVisitValueParameterReference = visitValueParameterReference(isNullPredicate.getArg(), data);
                    this.this$0.writeFlags(builderVisitValueParameterReference, Flags.getContractExpressionFlags(isNullPredicate.getIsNegated(), true));
                    return builderVisitValueParameterReference;
                }

                @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitConstantDescriptor(KtConstantReference<ConeKotlinType, ConeDiagnostic> constantReference, Unit data) {
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

                @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
                public ProtoBuf.Expression.Builder visitValueParameterReference(KtValueParameterReference<ConeKotlinType, ConeDiagnostic> valueParameterReference, Unit data) {
                    Intrinsics.checkNotNullParameter(valueParameterReference, "valueParameterReference");
                    Intrinsics.checkNotNullParameter(data, "data");
                    ProtoBuf.Expression.Builder builderNewBuilder = ProtoBuf.Expression.newBuilder();
                    builderNewBuilder.setValueParameterReference(valueParameterReference.getParameterIndex() + 1);
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
        public final ProtoBuf.Expression.ConstantValue constantValueProtobufEnum(KtConstantReference<ConeKotlinType, ConeDiagnostic> constantReference) {
            if (Intrinsics.areEqual(constantReference, ConeContractConstantValues.INSTANCE.getTRUE())) {
                return ProtoBuf.Expression.ConstantValue.TRUE;
            }
            if (Intrinsics.areEqual(constantReference, ConeContractConstantValues.INSTANCE.getFALSE())) {
                return ProtoBuf.Expression.ConstantValue.FALSE;
            }
            if (Intrinsics.areEqual(constantReference, ConeContractConstantValues.INSTANCE.getNULL())) {
                return ProtoBuf.Expression.ConstantValue.NULL;
            }
            if (Intrinsics.areEqual(constantReference, ConeContractConstantValues.INSTANCE.getNOT_NULL())) {
                throw new IllegalStateException("Internal error during serialization of function contract: NOT_NULL constant isn't denotable in protobuf format. Its serialization should be handled at higher level");
            }
            if (Intrinsics.areEqual(constantReference, ConeContractConstantValues.INSTANCE.getWILDCARD())) {
                return null;
            }
            throw new IllegalArgumentException("Unknown constant: " + constantReference);
        }
    }
}
