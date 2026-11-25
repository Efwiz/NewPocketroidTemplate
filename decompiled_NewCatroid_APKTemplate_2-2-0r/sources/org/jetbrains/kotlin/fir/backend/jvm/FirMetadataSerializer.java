package org.jetbrains.kotlin.fir.backend.jvm;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.backend.common.actualizer.IrActualizedResult;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.backend.FirMetadataSource;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.serialization.FirElementSerializer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.types.AbstractTypeApproximator;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: FirMetadataSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ$\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016H\u0016J\u0018\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u001bH\u0016J \u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u001e\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!\u0018\u00010\u00162\u0006\u0010\u0013\u001a\u00020\"H\u0016R\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirMetadataSerializer;", "Lorg/jetbrains/kotlin/backend/jvm/metadata/MetadataSerializer;", "globalSerializationBindings", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;", "serializationBindings", "approximator", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "serializer", "Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "irActualizedResult", "Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;", "(Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;)V", "actualizedExpectDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getSerializer$jvm_backend", "()Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "bindFieldMetadata", "", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Property;", "signature", "Lkotlin/Pair;", "Lorg/jetbrains/org/objectweb/asm/Type;", "", "bindMethodMetadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Function;", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "bindPropertyMetadata", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "serialize", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirMetadataSerializer implements MetadataSerializer {
    private final Set<FirDeclaration> actualizedExpectDeclarations;
    private final AbstractTypeApproximator approximator;
    private final JvmSerializationBindings globalSerializationBindings;
    private final JvmSerializationBindings serializationBindings;
    private final FirElementSerializer serializer;

    public FirMetadataSerializer(JvmSerializationBindings globalSerializationBindings, JvmSerializationBindings serializationBindings, AbstractTypeApproximator approximator, FirElementSerializer firElementSerializer, IrActualizedResult irActualizedResult) {
        Intrinsics.checkNotNullParameter(globalSerializationBindings, "globalSerializationBindings");
        Intrinsics.checkNotNullParameter(serializationBindings, "serializationBindings");
        Intrinsics.checkNotNullParameter(approximator, "approximator");
        this.globalSerializationBindings = globalSerializationBindings;
        this.serializationBindings = serializationBindings;
        this.approximator = approximator;
        this.serializer = firElementSerializer;
        this.actualizedExpectDeclarations = ConversionUtilsKt.extractFirDeclarations(irActualizedResult);
    }

    /* JADX INFO: renamed from: getSerializer$jvm_backend, reason: from getter */
    public final FirElementSerializer getSerializer() {
        return this.serializer;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer
    public Pair<MessageLite, JvmStringTable> serialize(MetadataSource metadata) {
        ProtoBuf.Function functionBuild;
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        if (metadata instanceof FirMetadataSource.Class) {
            FirElementSerializer firElementSerializer = this.serializer;
            Intrinsics.checkNotNull(firElementSerializer);
            functionBuild = firElementSerializer.classProto(((FirMetadataSource.Class) metadata).getFir()).build();
        } else if (metadata instanceof FirMetadataSource.File) {
            FirElementSerializer firElementSerializer2 = this.serializer;
            Intrinsics.checkNotNull(firElementSerializer2);
            FirMetadataSource.File file = (FirMetadataSource.File) metadata;
            functionBuild = firElementSerializer2.packagePartProto(UtilsKt.getPackageFqName((FirFile) CollectionsKt.first((List) file.getFiles())), file.getFiles(), this.actualizedExpectDeclarations).build();
        } else if (metadata instanceof FirMetadataSource.Function) {
            FirMetadataSource.Function function = (FirMetadataSource.Function) metadata;
            FirAnonymousFunction firAnonymousFunctionCopyToFreeAnonymousFunction = FirMetadataSerializerKt.copyToFreeAnonymousFunction(function.getFir(), this.approximator);
            Method method = (Method) this.serializationBindings.get(FirJvmSerializerExtension.INSTANCE.getMETHOD_FOR_FIR_FUNCTION(), function.getFir());
            if (method != null) {
                this.serializationBindings.put(FirJvmSerializerExtension.INSTANCE.getMETHOD_FOR_FIR_FUNCTION(), firAnonymousFunctionCopyToFreeAnonymousFunction, method);
            }
            FirElementSerializer firElementSerializer3 = this.serializer;
            Intrinsics.checkNotNull(firElementSerializer3);
            ProtoBuf.Function.Builder builderFunctionProto = firElementSerializer3.functionProto(firAnonymousFunctionCopyToFreeAnonymousFunction);
            functionBuild = builderFunctionProto != null ? builderFunctionProto.build() : null;
        } else {
            functionBuild = null;
        }
        if (functionBuild == null) {
            return null;
        }
        FirElementSerializer firElementSerializer4 = this.serializer;
        Intrinsics.checkNotNull(firElementSerializer4);
        Object stringTable = firElementSerializer4.getStringTable();
        Intrinsics.checkNotNull(stringTable, "null cannot be cast to non-null type org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable");
        return TuplesKt.to(functionBuild, (JvmStringTable) stringTable);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer
    public void bindPropertyMetadata(MetadataSource.Property metadata, Method signature, IrDeclarationOrigin origin) {
        JvmSerializationBindings.SerializationMappingSlice<FirVariable, Method> delegate_method_for_fir_variable;
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(origin, "origin");
        FirProperty fir = ((FirMetadataSource.Property) metadata).getFir();
        if (Intrinsics.areEqual(origin, JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE)) {
            delegate_method_for_fir_variable = FirJvmSerializerExtension.INSTANCE.getSYNTHETIC_METHOD_FOR_FIR_VARIABLE();
        } else if (Intrinsics.areEqual(origin, IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE)) {
            delegate_method_for_fir_variable = FirJvmSerializerExtension.INSTANCE.getDELEGATE_METHOD_FOR_FIR_VARIABLE();
        } else {
            throw new IllegalStateException("invalid origin " + origin + " for property-related method " + signature);
        }
        this.globalSerializationBindings.put(delegate_method_for_fir_variable, fir, signature);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer
    public void bindMethodMetadata(MetadataSource.Function metadata, Method signature) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.serializationBindings.put(FirJvmSerializerExtension.INSTANCE.getMETHOD_FOR_FIR_FUNCTION(), ((FirMetadataSource.Function) metadata).getFir(), signature);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer
    public void bindFieldMetadata(MetadataSource.Property metadata, Pair<Type, String> signature) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(signature, "signature");
        if (metadata instanceof FirMetadataSource.Property) {
            this.globalSerializationBindings.put(FirJvmSerializerExtension.INSTANCE.getFIELD_FOR_PROPERTY(), ((FirMetadataSource.Property) metadata).getFir(), signature);
            return;
        }
        if (!(metadata instanceof FirMetadataSource.Field)) {
            throw new IllegalStateException(("Unexpected metadata: " + metadata).toString());
        }
        FirField fir = ((FirMetadataSource.Field) metadata).getFir();
        KtSourceElement source = fir.getSource();
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.ClassDelegationField.INSTANCE)) {
            return;
        }
        StringBuilder sbAppend = new StringBuilder("Expected delegate field, got ").append(UtilsKt.render(fir)).append(" with source kind ");
        KtSourceElement source2 = fir.getSource();
        throw new IllegalArgumentException(sbAppend.append(source2 != null ? source2.getKind() : null).toString().toString());
    }
}
