package org.jetbrains.kotlin.backend.jvm.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.FakeDescriptorsForReferencesKt;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: DescriptorMetadataSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u000bJ$\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00160\u0015H\u0016J\u0018\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u0019H\u0016J \u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u001e\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u00152\u0006\u0010\u0012\u001a\u00020 H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/metadata/DescriptorMetadataSerializer;", "Lorg/jetbrains/kotlin/backend/jvm/metadata/MetadataSerializer;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "serializationBindings", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;", "parent", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;Lorg/jetbrains/kotlin/backend/jvm/metadata/MetadataSerializer;)V", "serializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "serializerExtension", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializerExtension;", "bindFieldMetadata", "", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Property;", "signature", "Lkotlin/Pair;", "", "bindMethodMetadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Function;", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "bindPropertyMetadata", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "serialize", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorMetadataSerializer implements MetadataSerializer {
    private final JvmBackendContext context;
    private final IrClass irClass;
    private final JvmSerializationBindings serializationBindings;
    private final DescriptorSerializer serializer;
    private final JvmSerializerExtension serializerExtension;
    private final Type type;

    public DescriptorMetadataSerializer(JvmBackendContext context, IrClass irClass, Type type, JvmSerializationBindings serializationBindings, MetadataSerializer metadataSerializer) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(serializationBindings, "serializationBindings");
        this.context = context;
        this.irClass = irClass;
        this.type = type;
        this.serializationBindings = serializationBindings;
        this.serializerExtension = new JvmSerializerExtension(serializationBindings, context.getState(), context.getDefaultTypeMapper());
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(this.context.getState().getConfiguration());
        MetadataSource metadata = this.irClass.getMetadata();
        DescriptorSerializer descriptorSerializerCreateForLambda = null;
        if (metadata instanceof DescriptorMetadataSource.Class) {
            DescriptorSerializer.Companion companion = DescriptorSerializer.INSTANCE;
            ClassDescriptor descriptor = ((DescriptorMetadataSource.Class) metadata).getDescriptor();
            JvmSerializerExtension jvmSerializerExtension = this.serializerExtension;
            DescriptorMetadataSerializer descriptorMetadataSerializer = metadataSerializer instanceof DescriptorMetadataSerializer ? (DescriptorMetadataSerializer) metadataSerializer : null;
            descriptorSerializerCreateForLambda = companion.create(descriptor, jvmSerializerExtension, descriptorMetadataSerializer != null ? descriptorMetadataSerializer.serializer : null, languageVersionSettings, this.context.getState().getProject());
        } else if (metadata instanceof DescriptorMetadataSource.Script) {
            DescriptorSerializer.Companion companion2 = DescriptorSerializer.INSTANCE;
            ScriptDescriptor descriptor2 = ((DescriptorMetadataSource.Script) metadata).getDescriptor();
            JvmSerializerExtension jvmSerializerExtension2 = this.serializerExtension;
            DescriptorMetadataSerializer descriptorMetadataSerializer2 = metadataSerializer instanceof DescriptorMetadataSerializer ? (DescriptorMetadataSerializer) metadataSerializer : null;
            descriptorSerializerCreateForLambda = companion2.create(descriptor2, jvmSerializerExtension2, descriptorMetadataSerializer2 != null ? descriptorMetadataSerializer2.serializer : null, languageVersionSettings, this.context.getState().getProject());
        } else if (metadata instanceof DescriptorMetadataSource.File) {
            descriptorSerializerCreateForLambda = DescriptorSerializer.INSTANCE.createTopLevel(this.serializerExtension, languageVersionSettings, this.context.getState().getProject());
        } else if (metadata instanceof DescriptorMetadataSource.Function) {
            descriptorSerializerCreateForLambda = DescriptorSerializer.INSTANCE.createForLambda(this.serializerExtension, languageVersionSettings);
        }
        this.serializer = descriptorSerializerCreateForLambda;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer
    public Pair<MessageLite, JvmStringTable> serialize(MetadataSource metadata) {
        ProtoBuf.Function functionBuild;
        Type typeMapClass;
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        List<IrLocalDelegatedPropertySymbol> list = this.context.getLocalDelegatedProperties().get(this.irClass.getAttributeOwnerId());
        if (list != null && !list.isEmpty()) {
            BindingTrace bindingTrace = this.context.getState().getBindingTrace();
            WritableSlice<Type, List<VariableDescriptorWithAccessors>> writableSlice = CodegenBinding.DELEGATED_PROPERTIES_WITH_METADATA;
            if (IrUtilsKt.isInterface(this.irClass) && !this.context.getConfig().getJvmDefaultMode().getForAllMethodsWithBody()) {
                typeMapClass = IrTypeMappingKt.mapClass(this.context.getDefaultTypeMapper(), this.context.getCachedDeclarations().getDefaultImplsClass(this.irClass));
            } else {
                typeMapClass = this.type;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                MetadataSource metadata2 = ((IrLocalDelegatedPropertySymbol) it.next()).getOwner().getMetadata();
                DescriptorMetadataSource.LocalDelegatedProperty localDelegatedProperty = metadata2 instanceof DescriptorMetadataSource.LocalDelegatedProperty ? (DescriptorMetadataSource.LocalDelegatedProperty) metadata2 : null;
                VariableDescriptorWithAccessors descriptor = localDelegatedProperty != null ? localDelegatedProperty.getDescriptor() : null;
                if (descriptor != null) {
                    arrayList.add(descriptor);
                }
            }
            bindingTrace.record(writableSlice, typeMapClass, arrayList);
        }
        if (metadata instanceof DescriptorMetadataSource.Class) {
            DescriptorSerializer descriptorSerializer = this.serializer;
            Intrinsics.checkNotNull(descriptorSerializer);
            functionBuild = descriptorSerializer.classProto(((DescriptorMetadataSource.Class) metadata).getDescriptor()).build();
        } else if (metadata instanceof DescriptorMetadataSource.Script) {
            DescriptorSerializer descriptorSerializer2 = this.serializer;
            Intrinsics.checkNotNull(descriptorSerializer2);
            functionBuild = descriptorSerializer2.classProto(((DescriptorMetadataSource.Script) metadata).getDescriptor()).build();
        } else if (metadata instanceof DescriptorMetadataSource.File) {
            DescriptorSerializer descriptorSerializer3 = this.serializer;
            Intrinsics.checkNotNull(descriptorSerializer3);
            ProtoBuf.Package.Builder builderPackagePartProto = descriptorSerializer3.packagePartProto(IrUtilsKt.getPackageFragment((IrDeclaration) this.irClass).getPackageFqName(), ((DescriptorMetadataSource.File) metadata).getDescriptors());
            this.serializerExtension.serializeJvmPackage(builderPackagePartProto, this.type);
            functionBuild = builderPackagePartProto.build();
        } else if (metadata instanceof DescriptorMetadataSource.Function) {
            DescriptorMetadataSource.Function function = (DescriptorMetadataSource.Function) metadata;
            FunctionDescriptor functionDescriptorCreateFreeFakeLambdaDescriptor = FakeDescriptorsForReferencesKt.createFreeFakeLambdaDescriptor(function.getDescriptor(), this.context.getState().getTypeApproximator());
            Method method = (Method) this.serializationBindings.get(JvmSerializationBindings.METHOD_FOR_FUNCTION, function.getDescriptor());
            if (method != null) {
                this.serializationBindings.put(JvmSerializationBindings.METHOD_FOR_FUNCTION, functionDescriptorCreateFreeFakeLambdaDescriptor, method);
            }
            DescriptorSerializer descriptorSerializer4 = this.serializer;
            Intrinsics.checkNotNull(descriptorSerializer4);
            ProtoBuf.Function.Builder builderFunctionProto = descriptorSerializer4.functionProto(functionDescriptorCreateFreeFakeLambdaDescriptor);
            functionBuild = builderFunctionProto != null ? builderFunctionProto.build() : null;
        } else {
            functionBuild = null;
        }
        if (functionBuild == null) {
            return null;
        }
        DescriptorSerializer descriptorSerializer5 = this.serializer;
        Intrinsics.checkNotNull(descriptorSerializer5);
        Object stringTable = descriptorSerializer5.getStringTable();
        Intrinsics.checkNotNull(stringTable, "null cannot be cast to non-null type org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable");
        return TuplesKt.to(functionBuild, (JvmStringTable) stringTable);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer
    public void bindPropertyMetadata(MetadataSource.Property metadata, Method signature, IrDeclarationOrigin origin) {
        JvmSerializationBindings.SerializationMappingSlice<PropertyDescriptor, Method> serializationMappingSlice;
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(origin, "origin");
        PropertyDescriptor descriptor = ((DescriptorMetadataSource.Property) metadata).getDescriptor();
        if (Intrinsics.areEqual(origin, JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE)) {
            serializationMappingSlice = JvmSerializationBindings.SYNTHETIC_METHOD_FOR_PROPERTY;
        } else if (Intrinsics.areEqual(origin, IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE)) {
            serializationMappingSlice = JvmSerializationBindings.DELEGATE_METHOD_FOR_PROPERTY;
        } else {
            throw new IllegalStateException("invalid origin " + origin + " for property-related method " + signature);
        }
        this.context.getState().getGlobalSerializationBindings().put(serializationMappingSlice, descriptor, signature);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer
    public void bindMethodMetadata(MetadataSource.Function metadata, Method signature) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.serializationBindings.put(JvmSerializationBindings.METHOD_FOR_FUNCTION, ((DescriptorMetadataSource.Function) metadata).getDescriptor(), signature);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer
    public void bindFieldMetadata(MetadataSource.Property metadata, Pair<Type, String> signature) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.context.getState().getGlobalSerializationBindings().put(JvmSerializationBindings.FIELD_FOR_PROPERTY, ((DescriptorMetadataSource.Property) metadata).getDescriptor(), signature);
    }
}
