package org.jetbrains.kotlin.codegen.serialization;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.ClassBuilderMode;
import org.jetbrains.kotlin.codegen.FakeDescriptorsForReferencesKt;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.load.java.DescriptorsJvmAbiUtil;
import org.jetbrains.kotlin.load.java.lazy.types.RawTypeImpl;
import org.jetbrains.kotlin.load.kotlin.TypeSignatureMappingKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmFlags;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.serialization.SerializerExtension;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: JvmSerializerExtension.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u008e\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ;\u0010&\u001a\u0004\u0018\u0001H'\"\b\b\u0000\u0010(*\u00020)\"\u0004\b\u0001\u0010'2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u0002H(\u0012\u0004\u0012\u0002H'0+2\u0006\u0010,\u001a\u0002H(H\u0002¢\u0006\u0002\u0010-J(\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u000207H\u0016J \u00108\u001a\u00020/2\u0006\u00100\u001a\u0002092\u0006\u00102\u001a\u00020:2\u0006\u00106\u001a\u000207H\u0016J\u0018\u0010;\u001a\u00020/2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J \u0010@\u001a\u00020/2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020?2\u0006\u0010D\u001a\u00020?H\u0016J*\u0010E\u001a\u00020/2\u0006\u00100\u001a\u00020F2\u0006\u00102\u001a\u00020G2\b\u00104\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u000207H\u0016J\u0016\u0010H\u001a\u00020/2\u0006\u00102\u001a\u00020I2\u0006\u0010J\u001a\u00020KJ\u0018\u0010L\u001a\u00020/2\u0006\u0010M\u001a\u00020N2\u0006\u00102\u001a\u00020IH\u0016J*\u0010O\u001a\u00020/2\u0006\u00100\u001a\u00020P2\u0006\u00102\u001a\u00020Q2\b\u00104\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u000207H\u0016J\u0018\u0010R\u001a\u00020/2\u0006\u0010<\u001a\u00020=2\u0006\u00102\u001a\u00020?H\u0016J\u0018\u0010S\u001a\u00020/2\u0006\u0010T\u001a\u00020U2\u0006\u00102\u001a\u00020VH\u0016J\b\u0010W\u001a\u00020\u0010H\u0016J]\u0010X\u001a\u00020/\"\u000e\b\u0000\u0010Y*\b\u0012\u0004\u0012\u0002HY0Z\"\u0014\b\u0001\u0010[*\u000e\u0012\u0004\u0012\u0002HY\u0012\u0004\u0012\u0002H[0\\2\u0006\u00102\u001a\u0002H[2\u0006\u0010]\u001a\u00020K2\u0018\u0010^\u001a\u0014\u0012\u0004\u0012\u0002HY\u0012\n\u0012\b\u0012\u0004\u0012\u00020a0`0_H\u0002¢\u0006\u0002\u0010bJ \u0010c\u001a\u00020/2\u0006\u0010d\u001a\u0002012\u0006\u0010>\u001a\u0002032\u0006\u00104\u001a\u000205H\u0002J\f\u0010e\u001a\u00020\u0010*\u00020PH\u0002J\f\u0010f\u001a\u00020\u0010*\u00020FH\u0002J \u0010g\u001a\u00020/*\u0002052\u0012\u0010h\u001a\u000e\u0012\u0004\u0012\u00020j\u0012\u0004\u0012\u00020/0iH\u0002J \u0010k\u001a\u00020/*\u0002052\u0012\u0010h\u001a\u000e\u0012\u0004\u0012\u00020j\u0012\u0004\u0012\u00020/0iH\u0002J \u0010l\u001a\u00020/*\u0002052\u0012\u0010h\u001a\u000e\u0012\u0004\u0012\u00020j\u0012\u0004\u0012\u00020/0iH\u0002R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\u00020 X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006m"}, d2 = {"Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializerExtension;", "Lorg/jetbrains/kotlin/serialization/SerializerExtension;", "bindings", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;", "(Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;)V", "approximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "classBuilderMode", "Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;", "codegenBinding", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "functionsWithInlineClassReturnTypesMangled", "", "globalBindings", "isParamAssertionsDisabled", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "moduleName", "", "signatureSerializer", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSignatureSerializerImpl;", "stringTable", "Lorg/jetbrains/kotlin/codegen/serialization/JvmCodegenStringTable;", "getStringTable", "()Lorg/jetbrains/kotlin/codegen/serialization/JvmCodegenStringTable;", "unifiedNullChecks", "useOldManglingScheme", "useTypeTable", "getBinding", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "", "slice", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings$SerializationMappingSlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings$SerializationMappingSlice;Ljava/lang/Object;)Ljava/lang/Object;", "serializeClass", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "serializeConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "serializeErrorType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "builder", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "serializeFlexibleType", "flexibleType", "Lorg/jetbrains/kotlin/types/FlexibleType;", "lowerProto", "upperProto", "serializeFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "serializeJvmPackage", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package$Builder;", "partAsmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "serializePackage", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "serializeProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "serializeType", "serializeTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Builder;", "shouldUseTypeTable", "writeLocalProperties", "MessageType", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableMessage;", "BuilderType", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableBuilder;", "classAsmType", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "(Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableBuilder;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;)V", "writeVersionRequirementForJvmDefaultIfNeeded", "classDescriptor", "isJvmFieldPropertyInInterfaceCompanion", "needsInlineParameterNullCheckRequirement", "writeFunctionNameManglingForReturnTypeRequirement", ProductAction.ACTION_ADD, "Lkotlin/Function1;", "", "writeInlineParameterNullCheckRequirement", "writeNewFunctionNameManglingRequirement", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSerializerExtension extends SerializerExtension {
    private final TypeApproximator approximator;
    private final JvmSerializationBindings bindings;
    private final ClassBuilderMode classBuilderMode;
    private final BindingContext codegenBinding;
    private final boolean functionsWithInlineClassReturnTypesMangled;
    private final JvmSerializationBindings globalBindings;
    private final boolean isParamAssertionsDisabled;
    private final JvmDefaultMode jvmDefaultMode;
    private final LanguageVersionSettings languageVersionSettings;
    private final BinaryVersion metadataVersion;
    private final String moduleName;
    private final JvmSignatureSerializerImpl signatureSerializer;
    private final JvmCodegenStringTable stringTable;
    private final KotlinTypeMapperBase typeMapper;
    private final boolean unifiedNullChecks;
    private final boolean useOldManglingScheme;
    private final boolean useTypeTable;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JvmSerializerExtension(JvmSerializationBindings bindings, GenerationState state) {
        this(bindings, state, null, 4, null);
        Intrinsics.checkNotNullParameter(bindings, "bindings");
        Intrinsics.checkNotNullParameter(state, "state");
    }

    public /* synthetic */ JvmSerializerExtension(JvmSerializationBindings jvmSerializationBindings, GenerationState generationState, KotlinTypeMapper kotlinTypeMapper, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jvmSerializationBindings, generationState, (i & 4) != 0 ? generationState.getTypeMapper() : kotlinTypeMapper);
    }

    public JvmSerializerExtension(JvmSerializationBindings bindings, GenerationState state, KotlinTypeMapperBase typeMapper) {
        Intrinsics.checkNotNullParameter(bindings, "bindings");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        this.bindings = bindings;
        this.typeMapper = typeMapper;
        this.globalBindings = state.getGlobalSerializationBindings();
        this.codegenBinding = state.getBindingContext();
        this.stringTable = new JvmCodegenStringTable(typeMapper, null, 2, null);
        this.useTypeTable = state.getConfig().getUseTypeTableInSerializer();
        this.moduleName = state.getModuleName();
        this.classBuilderMode = state.getClassBuilderMode();
        this.languageVersionSettings = state.getLanguageVersionSettings();
        this.isParamAssertionsDisabled = state.getConfig().getIsParamAssertionsDisabled();
        this.unifiedNullChecks = state.getConfig().getUnifiedNullChecks();
        this.functionsWithInlineClassReturnTypesMangled = state.getConfig().getFunctionsWithInlineClassReturnTypesMangled();
        this.metadataVersion = state.getConfig().getMetadataVersion();
        this.jvmDefaultMode = state.getJvmDefaultMode();
        this.approximator = state.getTypeApproximator();
        this.useOldManglingScheme = state.getConfig().getUseOldManglingSchemeForFunctionsWithInlineClassesInSignatures();
        this.signatureSerializer = new JvmSignatureSerializerImpl(getStringTable());
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public JvmCodegenStringTable getStringTable() {
        return this.stringTable;
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public BinaryVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    /* JADX INFO: renamed from: shouldUseTypeTable, reason: from getter */
    public boolean getUseTypeTable() {
        return this.useTypeTable;
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeClass(ClassDescriptor descriptor, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        if (!Intrinsics.areEqual(this.moduleName, "main")) {
            proto.setExtension(JvmProtoBuf.classModuleName, Integer.valueOf(getStringTable().getStringIndex(this.moduleName)));
        }
        ClassDescriptor classDescriptor = descriptor;
        Type typeMapClass = (!DescriptorUtils.isInterface(classDescriptor) || this.jvmDefaultMode.getForAllMethodsWithBody()) ? this.typeMapper.mapClass(descriptor) : this.typeMapper.mapDefaultImpls(descriptor);
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Property>> classLocalVariable = JvmProtoBuf.classLocalVariable;
        Intrinsics.checkNotNullExpressionValue(classLocalVariable, "classLocalVariable");
        writeLocalProperties(proto, typeMapClass, classLocalVariable);
        writeVersionRequirementForJvmDefaultIfNeeded(descriptor, proto, versionRequirementTable);
        if (this.jvmDefaultMode.getForAllMethodsWithBody() && DescriptorUtils.isInterface(classDescriptor)) {
            proto.setExtension(JvmProtoBuf.jvmClassFlags, Integer.valueOf(JvmFlags.INSTANCE.getClassFlags(this.jvmDefaultMode.getForAllMethodsWithBody(), (JvmDefaultMode.ALL_COMPATIBILITY == this.jvmDefaultMode && !JvmAnnotationUtilKt.hasJvmDefaultNoCompatibilityAnnotation(classDescriptor)) || (JvmDefaultMode.ALL_INCOMPATIBLE == this.jvmDefaultMode && JvmAnnotationUtilKt.hasJvmDefaultWithCompatibilityAnnotation(classDescriptor)))));
        }
    }

    private final void writeVersionRequirementForJvmDefaultIfNeeded(ClassDescriptor classDescriptor, ProtoBuf.Class.Builder builder, MutableVersionRequirementTable versionRequirementTable) {
        if (DescriptorUtils.isInterface(classDescriptor) && this.jvmDefaultMode == JvmDefaultMode.ALL_INCOMPATIBLE) {
            builder.addVersionRequirement(DescriptorSerializer.INSTANCE.writeVersionRequirement(1, 4, 0, ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION, versionRequirementTable));
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializePackage(FqName packageFqName, ProtoBuf.Package.Builder proto) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(proto, "proto");
        if (Intrinsics.areEqual(this.moduleName, "main")) {
            return;
        }
        proto.setExtension(JvmProtoBuf.packageModuleName, Integer.valueOf(getStringTable().getStringIndex(this.moduleName)));
    }

    public final void serializeJvmPackage(ProtoBuf.Package.Builder proto, Type partAsmType) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(partAsmType, "partAsmType");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, List<ProtoBuf.Property>> packageLocalVariable = JvmProtoBuf.packageLocalVariable;
        Intrinsics.checkNotNullExpressionValue(packageLocalVariable, "packageLocalVariable");
        writeLocalProperties(proto, partAsmType, packageLocalVariable);
    }

    private final <MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>> void writeLocalProperties(BuilderType proto, Type classAsmType, GeneratedMessageLite.GeneratedExtension<MessageType, List<ProtoBuf.Property>> extension) {
        List<LocalVariableDescriptor> localDelegatedProperties = CodegenBinding.getLocalDelegatedProperties(this.codegenBinding, classAsmType);
        if (localDelegatedProperties == null) {
            return;
        }
        for (LocalVariableDescriptor localVariableDescriptor : localDelegatedProperties) {
            Intrinsics.checkNotNull(localVariableDescriptor);
            ProtoBuf.Property.Builder builderPropertyProto = DescriptorSerializer.INSTANCE.createForLambda(this, this.languageVersionSettings).propertyProto(FakeDescriptorsForReferencesKt.createFreeFakeLocalPropertyDescriptor(localVariableDescriptor, this.approximator));
            ProtoBuf.Property propertyBuild = builderPropertyProto != null ? builderPropertyProto.build() : null;
            if (propertyBuild != null) {
                proto.addExtension(extension, propertyBuild);
            }
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeFlexibleType(FlexibleType flexibleType, ProtoBuf.Type.Builder lowerProto, ProtoBuf.Type.Builder upperProto) {
        Intrinsics.checkNotNullParameter(flexibleType, "flexibleType");
        Intrinsics.checkNotNullParameter(lowerProto, "lowerProto");
        Intrinsics.checkNotNullParameter(upperProto, "upperProto");
        lowerProto.setFlexibleTypeCapabilitiesId(getStringTable().getStringIndex(JvmProtoBufUtil.PLATFORM_TYPE_ID));
        if (flexibleType instanceof RawTypeImpl) {
            lowerProto.setExtension(JvmProtoBuf.isRaw, true);
            upperProto.setExtension(JvmProtoBuf.isRaw, true);
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeType(KotlinType type, ProtoBuf.Type.Builder proto) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(type).iterator();
        while (it.hasNext()) {
            proto.addExtension(JvmProtoBuf.typeAnnotation, getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeTypeParameter(TypeParameterDescriptor typeParameter, ProtoBuf.TypeParameter.Builder proto) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(typeParameter).iterator();
        while (it.hasNext()) {
            proto.addExtension(JvmProtoBuf.typeParameterAnnotation, getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeConstructor(ConstructorDescriptor descriptor, ProtoBuf.Constructor.Builder proto, DescriptorSerializer childSerializer) {
        JvmProtoBuf.JvmMethodSignature jvmMethodSignatureMethodSignature;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        JvmSerializationBindings.SerializationMappingSlice<FunctionDescriptor, Method> METHOD_FOR_FUNCTION = JvmSerializationBindings.METHOD_FOR_FUNCTION;
        Intrinsics.checkNotNullExpressionValue(METHOD_FOR_FUNCTION, "METHOD_FOR_FUNCTION");
        Method method = (Method) getBinding(METHOD_FOR_FUNCTION, descriptor);
        if (method == null || (jvmMethodSignatureMethodSignature = this.signatureSerializer.methodSignature(descriptor, descriptor.getShortName(), method)) == null) {
            return;
        }
        proto.setExtension(JvmProtoBuf.constructorSignature, jvmMethodSignatureMethodSignature);
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeFunction(FunctionDescriptor descriptor, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        JvmProtoBuf.JvmMethodSignature jvmMethodSignatureMethodSignature;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        JvmSerializationBindings.SerializationMappingSlice<FunctionDescriptor, Method> METHOD_FOR_FUNCTION = JvmSerializationBindings.METHOD_FOR_FUNCTION;
        Intrinsics.checkNotNullExpressionValue(METHOD_FOR_FUNCTION, "METHOD_FOR_FUNCTION");
        Method method = (Method) getBinding(METHOD_FOR_FUNCTION, descriptor);
        if (method != null && (jvmMethodSignatureMethodSignature = this.signatureSerializer.methodSignature(descriptor, descriptor.getShortName(), method)) != null) {
            proto.setExtension(JvmProtoBuf.methodSignature, jvmMethodSignatureMethodSignature);
        }
        if (needsInlineParameterNullCheckRequirement(descriptor) && versionRequirementTable != null) {
            writeInlineParameterNullCheckRequirement(versionRequirementTable, new AnonymousClass1(proto));
        }
        FunctionDescriptor functionDescriptor = descriptor;
        if (InlineClassManglingRulesKt.requiresFunctionNameManglingForReturnType(functionDescriptor) && !DescriptorUtils.hasJvmNameAnnotation(descriptor) && !InlineClassManglingRulesKt.requiresFunctionNameManglingForParameterTypes(functionDescriptor) && versionRequirementTable != null) {
            writeFunctionNameManglingForReturnTypeRequirement(versionRequirementTable, new AnonymousClass2(proto));
        }
        if ((!InlineClassManglingRulesKt.requiresFunctionNameManglingForReturnType(functionDescriptor) && !InlineClassManglingRulesKt.requiresFunctionNameManglingForParameterTypes(functionDescriptor)) || DescriptorUtils.hasJvmNameAnnotation(descriptor) || this.useOldManglingScheme || versionRequirementTable == null) {
            return;
        }
        writeNewFunctionNameManglingRequirement(versionRequirementTable, new AnonymousClass3(proto));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension$serializeFunction$1, reason: invalid class name */
    /* JADX INFO: compiled from: JvmSerializerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<Integer, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "serializeFunction$addVersionRequirement(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;I)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "addVersionRequirement";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
            invoke(num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(int i) {
            ((ProtoBuf.Function.Builder) this.receiver).addVersionRequirement(i);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension$serializeFunction$2, reason: invalid class name */
    /* JADX INFO: compiled from: JvmSerializerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<Integer, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "serializeFunction$addVersionRequirement$0(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;I)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "addVersionRequirement";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
            invoke(num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(int i) {
            ((ProtoBuf.Function.Builder) this.receiver).addVersionRequirement(i);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension$serializeFunction$3, reason: invalid class name */
    /* JADX INFO: compiled from: JvmSerializerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass3 extends FunctionReference implements Function1<Integer, Unit> {
        AnonymousClass3(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "serializeFunction$addVersionRequirement$1(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;I)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "addVersionRequirement";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
            invoke(num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(int i) {
            ((ProtoBuf.Function.Builder) this.receiver).addVersionRequirement(i);
        }
    }

    private final void writeInlineParameterNullCheckRequirement(MutableVersionRequirementTable mutableVersionRequirementTable, Function1<? super Integer, Unit> function1) {
        if (this.unifiedNullChecks) {
            function1.invoke(Integer.valueOf(DescriptorSerializer.INSTANCE.writeVersionRequirement(1, 3, 50, ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION, mutableVersionRequirementTable)));
        }
    }

    private final void writeFunctionNameManglingForReturnTypeRequirement(MutableVersionRequirementTable mutableVersionRequirementTable, Function1<? super Integer, Unit> function1) {
        if (this.functionsWithInlineClassReturnTypesMangled) {
            function1.invoke(Integer.valueOf(DescriptorSerializer.INSTANCE.writeVersionRequirement(1, 4, 0, ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION, mutableVersionRequirementTable)));
        }
    }

    private final void writeNewFunctionNameManglingRequirement(MutableVersionRequirementTable mutableVersionRequirementTable, Function1<? super Integer, Unit> function1) {
        function1.invoke(Integer.valueOf(DescriptorSerializer.INSTANCE.writeVersionRequirement(1, 4, 30, ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION, mutableVersionRequirementTable)));
    }

    private final boolean needsInlineParameterNullCheckRequirement(FunctionDescriptor functionDescriptor) {
        KotlinType type;
        if (functionDescriptor.isInline() && !functionDescriptor.isSuspend() && !this.isParamAssertionsDisabled && !DescriptorVisibilities.isPrivate(functionDescriptor.getVisibility())) {
            List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List<ValueParameterDescriptor> list = valueParameters;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    KotlinType type2 = ((ValueParameterDescriptor) it.next()).getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                    if (FunctionTypesKt.isFunctionType(type2)) {
                        return true;
                    }
                }
            }
            ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null && (type = extensionReceiverParameter.getType()) != null && FunctionTypesKt.isFunctionType(type)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeProperty(PropertyDescriptor descriptor, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Method method;
        Method method2;
        Type type;
        String descriptor2;
        Type type2;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        PropertyGetterDescriptor getter = descriptor.getGetter();
        PropertySetterDescriptor setter = descriptor.getSetter();
        if (getter == null) {
            method = null;
        } else {
            JvmSerializationBindings.SerializationMappingSlice<FunctionDescriptor, Method> METHOD_FOR_FUNCTION = JvmSerializationBindings.METHOD_FOR_FUNCTION;
            Intrinsics.checkNotNullExpressionValue(METHOD_FOR_FUNCTION, "METHOD_FOR_FUNCTION");
            method = (Method) getBinding(METHOD_FOR_FUNCTION, getter);
        }
        if (setter == null) {
            method2 = null;
        } else {
            JvmSerializationBindings.SerializationMappingSlice<FunctionDescriptor, Method> METHOD_FOR_FUNCTION2 = JvmSerializationBindings.METHOD_FOR_FUNCTION;
            Intrinsics.checkNotNullExpressionValue(METHOD_FOR_FUNCTION2, "METHOD_FOR_FUNCTION");
            method2 = (Method) getBinding(METHOD_FOR_FUNCTION2, setter);
        }
        JvmSerializationBindings.SerializationMappingSlice<PropertyDescriptor, Pair<Type, String>> FIELD_FOR_PROPERTY = JvmSerializationBindings.FIELD_FOR_PROPERTY;
        Intrinsics.checkNotNullExpressionValue(FIELD_FOR_PROPERTY, "FIELD_FOR_PROPERTY");
        Pair pair = (Pair) getBinding(FIELD_FOR_PROPERTY, descriptor);
        JvmSerializationBindings.SerializationMappingSlice<PropertyDescriptor, Method> SYNTHETIC_METHOD_FOR_PROPERTY = JvmSerializationBindings.SYNTHETIC_METHOD_FOR_PROPERTY;
        Intrinsics.checkNotNullExpressionValue(SYNTHETIC_METHOD_FOR_PROPERTY, "SYNTHETIC_METHOD_FOR_PROPERTY");
        Method method3 = (Method) getBinding(SYNTHETIC_METHOD_FOR_PROPERTY, descriptor);
        JvmSerializationBindings.SerializationMappingSlice<PropertyDescriptor, Method> DELEGATE_METHOD_FOR_PROPERTY = JvmSerializationBindings.DELEGATE_METHOD_FOR_PROPERTY;
        Intrinsics.checkNotNullExpressionValue(DELEGATE_METHOD_FOR_PROPERTY, "DELEGATE_METHOD_FOR_PROPERTY");
        Method method4 = (Method) getBinding(DELEGATE_METHOD_FOR_PROPERTY, descriptor);
        descriptor.getIsDelegated();
        JvmSignatureSerializerImpl jvmSignatureSerializerImpl = this.signatureSerializer;
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        JvmProtoBuf.JvmPropertySignature jvmPropertySignaturePropertySignature = jvmSignatureSerializerImpl.propertySignature(name2, pair != null ? (String) pair.getSecond() : null, (pair == null || (type2 = (Type) pair.getFirst()) == null) ? null : type2.getDescriptor(), method3 != null ? this.signatureSerializer.methodSignature(null, null, method3) : null, method4 != null ? this.signatureSerializer.methodSignature(null, null, method4) : null, method != null ? this.signatureSerializer.methodSignature(null, null, method) : null, method2 != null ? this.signatureSerializer.methodSignature(null, null, method2) : null, (pair == null || (type = (Type) pair.getFirst()) == null || (descriptor2 = type.getDescriptor()) == null) ? false : this.signatureSerializer.requiresPropertySignature(descriptor, descriptor2));
        if (jvmPropertySignaturePropertySignature != null) {
            proto.setExtension(JvmProtoBuf.propertySignature, jvmPropertySignaturePropertySignature);
        }
        if (isJvmFieldPropertyInInterfaceCompanion(descriptor) && versionRequirementTable != null) {
            proto.setExtension(JvmProtoBuf.flags, Integer.valueOf(JvmFlags.INSTANCE.getPropertyFlags(true)));
        }
        if (((getter != null && needsInlineParameterNullCheckRequirement(getter)) || (setter != null && needsInlineParameterNullCheckRequirement(setter))) && versionRequirementTable != null) {
            writeInlineParameterNullCheckRequirement(versionRequirementTable, new C44012(proto));
        }
        if (DescriptorUtils.hasJvmNameAnnotation(descriptor) || !InlineClassManglingRulesKt.requiresFunctionNameManglingForReturnType(descriptor)) {
            return;
        }
        if (!this.useOldManglingScheme && versionRequirementTable != null) {
            writeNewFunctionNameManglingRequirement(versionRequirementTable, new C44023(proto));
        }
        if (versionRequirementTable != null) {
            writeFunctionNameManglingForReturnTypeRequirement(versionRequirementTable, new AnonymousClass4(proto));
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension$serializeProperty$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: JvmSerializerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C44012 extends FunctionReference implements Function1<Integer, Unit> {
        C44012(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "serializeProperty$addVersionRequirement$5(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;I)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "addVersionRequirement";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
            invoke(num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(int i) {
            ((ProtoBuf.Property.Builder) this.receiver).addVersionRequirement(i);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension$serializeProperty$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: JvmSerializerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C44023 extends FunctionReference implements Function1<Integer, Unit> {
        C44023(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "serializeProperty$addVersionRequirement$6(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;I)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "addVersionRequirement";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
            invoke(num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(int i) {
            ((ProtoBuf.Property.Builder) this.receiver).addVersionRequirement(i);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension$serializeProperty$4, reason: invalid class name */
    /* JADX INFO: compiled from: JvmSerializerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass4 extends FunctionReference implements Function1<Integer, Unit> {
        AnonymousClass4(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "serializeProperty$addVersionRequirement$7(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;I)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "addVersionRequirement";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
            invoke(num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(int i) {
            ((ProtoBuf.Property.Builder) this.receiver).addVersionRequirement(i);
        }
    }

    private final boolean isJvmFieldPropertyInInterfaceCompanion(PropertyDescriptor propertyDescriptor) {
        if (!DescriptorsJvmAbiUtil.hasJvmFieldAnnotation(propertyDescriptor)) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (!DescriptorUtils.isCompanionObject(containingDeclaration)) {
            return false;
        }
        DeclarationDescriptor containingDeclaration2 = ((ClassDescriptor) containingDeclaration).getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
        return DescriptorUtils.isInterface(containingDeclaration2) || DescriptorUtils.isAnnotationClass(containingDeclaration2);
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeErrorType(KotlinType type, ProtoBuf.Type.Builder builder) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(builder, "builder");
        if (this.classBuilderMode == ClassBuilderMode.KAPT3) {
            builder.setClassName(getStringTable().getStringIndex(TypeSignatureMappingKt.NON_EXISTENT_CLASS_NAME));
        } else {
            super.serializeErrorType(type, builder);
        }
    }

    private final <K, V> V getBinding(JvmSerializationBindings.SerializationMappingSlice<K, V> slice, K key) {
        V v = (V) this.bindings.get(slice, key);
        return v == null ? (V) this.globalBindings.get(slice, key) : v;
    }
}
