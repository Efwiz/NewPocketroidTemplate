package org.jetbrains.kotlin.analysis.decompiler.stub;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.KtBooleanValueParameterReference;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtValueParameterReference;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinContractConstantValues;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectType;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer;

/* JADX INFO: compiled from: ClsContractBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00040\u0001B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0012\u0010\n\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0016J&\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0004H\u0016J\u0016\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0013H\u0016J\u0016\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0013H\u0016J\u001e\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00132\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J$\u0010\u0018\u001a\u0018\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u001a\u0018\u00010\u00192\u0006\u0010\u000b\u001a\u00020\u0004R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsContractBuilder;", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBufContractDeserializer;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "c", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "typeStubBuilder", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeClsStubBuilder;", "(Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeClsStubBuilder;)V", "extractType", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;", "extractVariable", "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "valueParameterIndex", "", "owner", "getNotNull", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "getWildcard", "loadConstant", "value", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression$ConstantValue;", "loadContract", "", "Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClsContractBuilder extends ProtoBufContractDeserializer {
    private final ClsStubBuilderContext c;
    private final TypeClsStubBuilder typeStubBuilder;

    /* JADX INFO: compiled from: ClsContractBuilder.kt */
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

    public ClsContractBuilder(ClsStubBuilderContext c, TypeClsStubBuilder typeStubBuilder) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(typeStubBuilder, "typeStubBuilder");
        this.c = c;
        this.typeStubBuilder = typeStubBuilder;
    }

    public final List<KtEffectDeclaration> loadContract(ProtoBuf.Function proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        List<ProtoBuf.Effect> effectList = proto.getContract().getEffectList();
        Intrinsics.checkNotNullExpressionValue(effectList, "getEffectList(...)");
        List<ProtoBuf.Effect> list = effectList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ProtoBuf.Effect effect : list) {
            Intrinsics.checkNotNull(effect);
            KtEffectDeclaration ktEffectDeclarationLoadPossiblyConditionalEffect = loadPossiblyConditionalEffect(effect, proto);
            if (ktEffectDeclarationLoadPossiblyConditionalEffect == null) {
                return null;
            }
            arrayList.add(ktEffectDeclarationLoadPossiblyConditionalEffect);
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer
    public KtValueParameterReference extractVariable(int valueParameterIndex, ProtoBuf.Function owner) {
        ProtoBuf.Type type;
        Intrinsics.checkNotNullParameter(owner, "owner");
        if (valueParameterIndex < 0) {
            type = owner.getReceiverType();
        } else {
            type = owner.getValueParameterList().get(valueParameterIndex).getType();
        }
        if (type.hasClassName() && Intrinsics.areEqual(NameResolverUtilKt.getClassId(this.c.getNameResolver(), type.getClassName()), StandardClassIds.INSTANCE.getBoolean())) {
            return new KtBooleanValueParameterReference(valueParameterIndex, KotlinContractEffectType.IGNORE_REFERENCE_PARAMETER_NAME);
        }
        return new KtValueParameterReference(valueParameterIndex, KotlinContractEffectType.IGNORE_REFERENCE_PARAMETER_NAME);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer
    public KotlinTypeBean extractType(ProtoBuf.Expression proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        return this.typeStubBuilder.createKotlinTypeBean(ProtoTypeTableUtilKt.isInstanceType(proto, this.c.getTypeTable()));
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer
    public KtConstantReference loadConstant(ProtoBuf.Expression.ConstantValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        int i = WhenMappings.$EnumSwitchMapping$0[value.ordinal()];
        if (i == 1) {
            return KotlinContractConstantValues.INSTANCE.getTRUE();
        }
        if (i == 2) {
            return KotlinContractConstantValues.INSTANCE.getFALSE();
        }
        if (i == 3) {
            return KotlinContractConstantValues.INSTANCE.getNULL();
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer
    public KtConstantReference getNotNull() {
        return KotlinContractConstantValues.INSTANCE.getNOT_NULL();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoBufContractDeserializer
    public KtConstantReference getWildcard() {
        return KotlinContractConstantValues.INSTANCE.getWILDCARD();
    }
}
