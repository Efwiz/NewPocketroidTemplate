package org.jetbrains.kotlin.fir.deserialization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.contracts.description.KtBooleanValueParameterReference;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtValueParameterReference;
import org.jetbrains.kotlin.fir.contracts.ContractUtilsKt;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.builder.FirResolvedContractDescriptionBuilder;
import org.jetbrains.kotlin.fir.contracts.description.ConeContractConstantValues;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer;

/* JADX INFO: compiled from: FirContractDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\t\u001a\u00020\nH\u0016J&\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0004H\u0016J\u0014\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0011H\u0016J\u0014\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0011H\u0016J\u001c\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\t\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u0004R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/FirContractDeserializer;", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBufContractDeserializer;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "c", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "(Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;)V", "extractType", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;", "extractVariable", "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "valueParameterIndex", "", "owner", "getNotNull", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "getWildcard", "loadConstant", "value", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression$ConstantValue;", "loadContract", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Contract;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirContractDeserializer extends ProtoBufContractDeserializer<ConeKotlinType, ConeDiagnostic, FirContractDescriptionOwner> {
    private final FirDeserializationContext c;

    /* JADX INFO: compiled from: FirContractDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoBuf.Expression.ConstantValue.values().length];
            try {
                iArr[ProtoBuf.Expression.ConstantValue.TRUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.Expression.ConstantValue.FALSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.Expression.ConstantValue.NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FirContractDeserializer(FirDeserializationContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.c = c;
    }

    public final FirContractDescription loadContract(ProtoBuf.Contract proto, FirContractDescriptionOwner owner) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(owner, "owner");
        List<ProtoBuf.Effect> effectList = proto.getEffectList();
        Intrinsics.checkNotNullExpressionValue(effectList, "getEffectList(...)");
        List<ProtoBuf.Effect> list = effectList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ProtoBuf.Effect effect : list) {
            Intrinsics.checkNotNull(effect);
            KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> ktEffectDeclarationLoadPossiblyConditionalEffect = loadPossiblyConditionalEffect(effect, owner);
            if (ktEffectDeclarationLoadPossiblyConditionalEffect == null) {
                return null;
            }
            arrayList.add(ktEffectDeclarationLoadPossiblyConditionalEffect);
        }
        FirResolvedContractDescriptionBuilder firResolvedContractDescriptionBuilder = new FirResolvedContractDescriptionBuilder();
        List<FirEffectDeclaration> effects = firResolvedContractDescriptionBuilder.getEffects();
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(ContractUtilsKt.toFirElement$default((KtEffectDeclaration) it.next(), (KtSourceElement) null, 1, (Object) null));
        }
        CollectionsKt.addAll(effects, arrayList3);
        return firResolvedContractDescriptionBuilder.build();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer
    public ConeKotlinType extractType(ProtoBuf.Expression proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        FirTypeDeserializer typeDeserializer = this.c.getTypeDeserializer();
        ProtoBuf.Type typeIsInstanceType = ProtoTypeTableUtilKt.isInstanceType(proto, this.c.getTypeTable());
        if (typeIsInstanceType == null) {
            return null;
        }
        return typeDeserializer.type(typeIsInstanceType);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer
    public KtValueParameterReference<ConeKotlinType, ConeDiagnostic> extractVariable(int valueParameterIndex, FirContractDescriptionOwner owner) {
        String strAsString;
        FirTypeRef returnTypeRef;
        Intrinsics.checkNotNullParameter(owner, "owner");
        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) owner;
        if (valueParameterIndex < 0) {
            FirReceiverParameter receiverParameter = firSimpleFunction.getReceiverParameter();
            strAsString = "this";
            returnTypeRef = receiverParameter != null ? receiverParameter.getTypeRef() : null;
        } else {
            FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.getOrNull(firSimpleFunction.getValueParameters(), valueParameterIndex);
            if (firValueParameter == null) {
                return null;
            }
            strAsString = firValueParameter.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            returnTypeRef = firValueParameter.getReturnTypeRef();
        }
        if (returnTypeRef == null) {
            return null;
        }
        if (!FirTypeUtilsKt.isBoolean(returnTypeRef)) {
            return new KtValueParameterReference<>(valueParameterIndex, strAsString);
        }
        return new KtBooleanValueParameterReference(valueParameterIndex, strAsString);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer
    public KtConstantReference<ConeKotlinType, ConeDiagnostic> loadConstant(ProtoBuf.Expression.ConstantValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        int i = WhenMappings.$EnumSwitchMapping$0[value.ordinal()];
        if (i == 1) {
            return ConeContractConstantValues.INSTANCE.getTRUE();
        }
        if (i == 2) {
            return ConeContractConstantValues.INSTANCE.getFALSE();
        }
        if (i == 3) {
            return ConeContractConstantValues.INSTANCE.getNULL();
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer
    public KtConstantReference<ConeKotlinType, ConeDiagnostic> getNotNull() {
        return ConeContractConstantValues.INSTANCE.getNOT_NULL();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer
    public KtConstantReference<ConeKotlinType, ConeDiagnostic> getWildcard() {
        return ConeContractConstantValues.INSTANCE.getWILDCARD();
    }
}
