package org.jetbrains.kotlin.fir.backend.jvm;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
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
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.ClassBuilderMode;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.ConstValueProviderImpl;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.FirMetadataSource;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.serialization.FirAdditionalMetadataAnnotationsProvider;
import org.jetbrains.kotlin.fir.serialization.FirElementAwareStringTable;
import org.jetbrains.kotlin.fir.serialization.FirElementSerializer;
import org.jetbrains.kotlin.fir.serialization.FirSerializerExtension;
import org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeRawType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.TypeSignatureMappingKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmFlags;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.types.AbstractTypeApproximator;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: FirJvmSerializerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ª\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 x2\u00020\u0001:\u0001xBO\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013B\u0099\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0005\u0012\u0006\u0010\u0017\u001a\u00020\u0018\u0012\u0006\u0010\u0019\u001a\u00020\u001a\u0012\u0006\u0010\u001b\u001a\u00020\u001c\u0012\u0006\u0010\u001d\u001a\u00020\u0018\u0012\u0006\u0010\u001e\u001a\u00020\u0018\u0012\u0006\u0010\u001f\u001a\u00020 \u0012\u0006\u0010!\u001a\u00020\"\u0012\u0006\u0010#\u001a\u00020$\u0012\b\u0010%\u001a\u0004\u0018\u00010&\u0012\b\u0010'\u001a\u0004\u0018\u00010(¢\u0006\u0002\u0010)J?\u00106\u001a\u0004\u0018\u0001H7\"\b\b\u0000\u00108*\u000209\"\b\b\u0001\u00107*\u0002092\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H70;2\u0006\u0010<\u001a\u0002H8H\u0002¢\u0006\u0002\u0010=J(\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020GH\u0016J \u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020J2\u0006\u0010B\u001a\u00020K2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010L\u001a\u00020?2\u0006\u0010M\u001a\u00020N2\u0006\u0010O\u001a\u00020PH\u0016J \u0010Q\u001a\u00020?2\u0006\u0010M\u001a\u00020R2\u0006\u0010S\u001a\u00020P2\u0006\u0010T\u001a\u00020PH\u0016J*\u0010U\u001a\u00020?2\u0006\u0010V\u001a\u00020W2\u0006\u0010B\u001a\u00020X2\b\u0010D\u001a\u0004\u0018\u00010E2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010Y\u001a\u00020?2\u0006\u0010Z\u001a\u00020[2\u0006\u0010B\u001a\u00020\\H\u0016J*\u0010]\u001a\u00020?2\u0006\u0010^\u001a\u00020\f2\u0006\u0010B\u001a\u00020_2\b\u0010D\u001a\u0004\u0018\u00010E2\u0006\u0010F\u001a\u00020GH\u0016J\u001e\u0010`\u001a\u00020?2\f\u0010a\u001a\b\u0012\u0004\u0012\u00020b0\u000b2\u0006\u0010B\u001a\u00020PH\u0016J\u0018\u0010c\u001a\u00020?2\u0006\u0010d\u001a\u00020e2\u0006\u0010B\u001a\u00020fH\u0016J\b\u0010g\u001a\u00020\u0018H\u0016JU\u0010h\u001a\u00020?\"\u000e\b\u0000\u0010i*\b\u0012\u0004\u0012\u0002Hi0j\"\u0014\b\u0001\u0010k*\u000e\u0012\u0004\u0012\u0002Hi\u0012\u0004\u0012\u0002Hk0l2\u0006\u0010B\u001a\u0002Hk2\u0018\u0010m\u001a\u0014\u0012\u0004\u0012\u0002Hi\u0012\n\u0012\b\u0012\u0004\u0012\u00020o0\u000b0nH\u0002¢\u0006\u0002\u0010pJ \u0010q\u001a\u00020?2\u0006\u0010@\u001a\u00020A2\u0006\u0010O\u001a\u00020C2\u0006\u0010D\u001a\u00020EH\u0002J\f\u0010r\u001a\u00020\u0018*\u00020\fH\u0002J\f\u0010s\u001a\u00020\u0018*\u00020WH\u0002J \u0010t\u001a\u00020?*\u00020E2\u0012\u0010u\u001a\u000e\u0012\u0004\u0012\u00020w\u0012\u0004\u0012\u00020?0vH\u0002R\u0016\u0010'\u001a\u0004\u0018\u00010(X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010%\u001a\u0004\u0018\u00010&X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u000e\u0010\u0016\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\u00020 X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u000e\u00102\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\u00020$X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u000e\u0010\u001e\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006y"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmSerializerExtension;", "Lorg/jetbrains/kotlin/fir/serialization/FirSerializerExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "bindings", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "localDelegatedProperties", "", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "approximator", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "typeMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;Ljava/util/List;Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "globalBindings", "useTypeTable", "", "moduleName", "", "classBuilderMode", "Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;", "isParamAssertionsDisabled", "unifiedNullChecks", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "stringTable", "Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "constValueProvider", "Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "additionalAnnotationsProvider", "Lorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;Ljava/util/List;Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;ZLjava/lang/String;Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;ZZLorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lorg/jetbrains/kotlin/config/JvmDefaultMode;Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;Lorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;)V", "getAdditionalAnnotationsProvider", "()Lorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;", "getConstValueProvider", "()Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureSerializer", "Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmSignatureSerializer;", "getStringTable", "()Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "getBinding", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "", "slice", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings$SerializationMappingSlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings$SerializationMappingSlice;Ljava/lang/Object;)Ljava/lang/Object;", "serializeClass", "", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "serializeConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "serializeErrorType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeErrorType;", "builder", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "serializeFlexibleType", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "lowerProto", "upperProto", "serializeFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "serializePackage", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package$Builder;", "serializeProperty", "property", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "serializeTypeAnnotations", "annotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "serializeTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Builder;", "shouldUseTypeTable", "writeLocalProperties", "MessageType", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableMessage;", "BuilderType", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableBuilder;", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "(Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableBuilder;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;)V", "writeVersionRequirementForJvmDefaultIfNeeded", "isJvmFieldPropertyInInterfaceCompanion", "needsInlineParameterNullCheckRequirement", "writeInlineParameterNullCheckRequirement", ProductAction.ACTION_ADD, "Lkotlin/Function1;", "", "Companion", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmSerializerExtension extends FirSerializerExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final JvmSerializationBindings.SerializationMappingSlice<FirVariable, Method> DELEGATE_METHOD_FOR_FIR_VARIABLE;
    private static final JvmSerializationBindings.SerializationMappingSlice<FirProperty, Pair<Type, String>> FIELD_FOR_PROPERTY;
    private static final ClassId JVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID;
    private static final FqName JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME;
    private static final ClassId JVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID;
    private static final FqName JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME;
    private static final JvmSerializationBindings.SerializationMappingSlice<FirFunction, Method> METHOD_FOR_FIR_FUNCTION;
    private static final JvmSerializationBindings.SerializationMappingSlice<FirVariable, Method> SYNTHETIC_METHOD_FOR_FIR_VARIABLE;
    private final FirAdditionalMetadataAnnotationsProvider additionalAnnotationsProvider;
    private final AbstractTypeApproximator approximator;
    private final JvmSerializationBindings bindings;
    private final ClassBuilderMode classBuilderMode;
    private final ConstValueProvider constValueProvider;
    private final JvmSerializationBindings globalBindings;
    private final boolean isParamAssertionsDisabled;
    private final JvmDefaultMode jvmDefaultMode;
    private final List<FirProperty> localDelegatedProperties;
    private final MetadataSource metadata;
    private final BinaryVersion metadataVersion;
    private final String moduleName;
    private final ScopeSession scopeSession;
    private final FirSession session;
    private final FirJvmSignatureSerializer signatureSerializer;
    private final FirElementAwareStringTable stringTable;
    private final boolean unifiedNullChecks;
    private final boolean useTypeTable;

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public BinaryVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public FirElementAwareStringTable getStringTable() {
        return this.stringTable;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    protected ConstValueProvider getConstValueProvider() {
        return this.constValueProvider;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    protected FirAdditionalMetadataAnnotationsProvider getAdditionalAnnotationsProvider() {
        return this.additionalAnnotationsProvider;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FirJvmSerializerExtension(FirSession session, JvmSerializationBindings bindings, MetadataSource metadataSource, List<? extends FirProperty> localDelegatedProperties, AbstractTypeApproximator approximator, ScopeSession scopeSession, JvmSerializationBindings globalBindings, boolean z, String moduleName, ClassBuilderMode classBuilderMode, boolean z2, boolean z3, BinaryVersion metadataVersion, JvmDefaultMode jvmDefaultMode, FirElementAwareStringTable stringTable, ConstValueProvider constValueProvider, FirAdditionalMetadataAnnotationsProvider firAdditionalMetadataAnnotationsProvider) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(bindings, "bindings");
        Intrinsics.checkNotNullParameter(localDelegatedProperties, "localDelegatedProperties");
        Intrinsics.checkNotNullParameter(approximator, "approximator");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(globalBindings, "globalBindings");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(classBuilderMode, "classBuilderMode");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        this.session = session;
        this.bindings = bindings;
        this.metadata = metadataSource;
        this.localDelegatedProperties = localDelegatedProperties;
        this.approximator = approximator;
        this.scopeSession = scopeSession;
        this.globalBindings = globalBindings;
        this.useTypeTable = z;
        this.moduleName = moduleName;
        this.classBuilderMode = classBuilderMode;
        this.isParamAssertionsDisabled = z2;
        this.unifiedNullChecks = z3;
        this.metadataVersion = metadataVersion;
        this.jvmDefaultMode = jvmDefaultMode;
        this.stringTable = stringTable;
        this.constValueProvider = constValueProvider;
        this.additionalAnnotationsProvider = firAdditionalMetadataAnnotationsProvider;
        this.signatureSerializer = new FirJvmSignatureSerializer(getStringTable());
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FirJvmSerializerExtension(FirSession session, JvmSerializationBindings bindings, GenerationState state, MetadataSource metadataSource, List<? extends FirProperty> localDelegatedProperties, AbstractTypeApproximator approximator, IrTypeMapper typeMapper, Fir2IrComponents components) {
        this(session, bindings, metadataSource, localDelegatedProperties, approximator, components.getScopeSession(), state.getGlobalSerializationBindings(), state.getConfig().getUseTypeTableInSerializer(), state.getModuleName(), state.getClassBuilderMode(), state.getConfig().getIsParamAssertionsDisabled(), state.getConfig().getUnifiedNullChecks(), state.getConfig().getMetadataVersion(), state.getJvmDefaultMode(), new FirJvmElementAwareStringTable(typeMapper, components, null, 4, null), new ConstValueProviderImpl(components), components.getAnnotationsFromPluginRegistrar().createMetadataAnnotationsProvider());
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(bindings, "bindings");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(localDelegatedProperties, "localDelegatedProperties");
        Intrinsics.checkNotNullParameter(approximator, "approximator");
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        Intrinsics.checkNotNullParameter(components, "components");
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    /* JADX INFO: renamed from: shouldUseTypeTable, reason: from getter */
    public boolean getUseTypeTable() {
        return this.useTypeTable;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeClass(FirClass klass, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        MetadataSource metadataSource = this.metadata;
        Intrinsics.checkNotNull(metadataSource, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.backend.FirMetadataSource.Class");
        Intrinsics.areEqual(((FirMetadataSource.Class) metadataSource).getFir(), klass);
        if (!Intrinsics.areEqual(this.moduleName, "main")) {
            proto.setExtension(JvmProtoBuf.classModuleName, Integer.valueOf(getStringTable().getStringIndex(this.moduleName)));
        }
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Property>> classLocalVariable = JvmProtoBuf.classLocalVariable;
        Intrinsics.checkNotNullExpressionValue(classLocalVariable, "classLocalVariable");
        writeLocalProperties(proto, classLocalVariable);
        writeVersionRequirementForJvmDefaultIfNeeded(klass, proto, versionRequirementTable);
        if (this.jvmDefaultMode.getForAllMethodsWithBody() && (klass instanceof FirRegularClass) && klass.getClassKind() == ClassKind.INTERFACE) {
            proto.setExtension(JvmProtoBuf.jvmClassFlags, Integer.valueOf(JvmFlags.INSTANCE.getClassFlags(this.jvmDefaultMode.getForAllMethodsWithBody(), (JvmDefaultMode.ALL_COMPATIBILITY == this.jvmDefaultMode && !FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) klass, JVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID, getSession())) || (JvmDefaultMode.ALL_INCOMPATIBLE == this.jvmDefaultMode && FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) klass, JVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID, getSession())))));
        }
    }

    private final void writeVersionRequirementForJvmDefaultIfNeeded(FirClass klass, ProtoBuf.Class.Builder builder, MutableVersionRequirementTable versionRequirementTable) {
        if ((klass instanceof FirRegularClass) && klass.getClassKind() == ClassKind.INTERFACE && this.jvmDefaultMode == JvmDefaultMode.ALL_INCOMPATIBLE) {
            builder.addVersionRequirement(DescriptorSerializer.INSTANCE.writeVersionRequirement(1, 4, 0, ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION, versionRequirementTable));
        }
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializePackage(FqName packageFqName, ProtoBuf.Package.Builder proto) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(proto, "proto");
        if (!Intrinsics.areEqual(this.moduleName, "main")) {
            proto.setExtension(JvmProtoBuf.packageModuleName, Integer.valueOf(getStringTable().getStringIndex(this.moduleName)));
        }
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, List<ProtoBuf.Property>> packageLocalVariable = JvmProtoBuf.packageLocalVariable;
        Intrinsics.checkNotNullExpressionValue(packageLocalVariable, "packageLocalVariable");
        writeLocalProperties(proto, packageLocalVariable);
    }

    private final <MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>> void writeLocalProperties(BuilderType proto, GeneratedMessageLite.GeneratedExtension<MessageType, List<ProtoBuf.Property>> extension) {
        LanguageVersionSettings languageVersionSettings = FirLanguageSettingsComponentKt.getLanguageVersionSettings(getSession());
        Iterator<FirProperty> it = this.localDelegatedProperties.iterator();
        while (it.hasNext()) {
            ProtoBuf.Property.Builder builderPropertyProto = FirElementSerializer.INSTANCE.createForLambda(getSession(), this.scopeSession, this, this.approximator, languageVersionSettings).propertyProto(it.next());
            ProtoBuf.Property propertyBuild = builderPropertyProto != null ? builderPropertyProto.build() : null;
            if (propertyBuild != null) {
                proto.addExtension(extension, propertyBuild);
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeFlexibleType(ConeFlexibleType type, ProtoBuf.Type.Builder lowerProto, ProtoBuf.Type.Builder upperProto) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(lowerProto, "lowerProto");
        Intrinsics.checkNotNullParameter(upperProto, "upperProto");
        lowerProto.setFlexibleTypeCapabilitiesId(getStringTable().getStringIndex(JvmProtoBufUtil.PLATFORM_TYPE_ID));
        if (type instanceof ConeRawType) {
            lowerProto.setExtension(JvmProtoBuf.isRaw, true);
            upperProto.setExtension(JvmProtoBuf.isRaw, true);
        }
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeTypeAnnotations(List<? extends FirAnnotation> annotations, ProtoBuf.Type.Builder proto) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Iterator<? extends FirAnnotation> it = annotations.iterator();
        while (it.hasNext()) {
            proto.addExtension(JvmProtoBuf.typeAnnotation, getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeTypeParameter(FirTypeParameter typeParameter, ProtoBuf.TypeParameter.Builder proto) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Iterator<FirAnnotation> it = FirAnnotationUtilsKt.nonSourceAnnotations(typeParameter, getSession()).iterator();
        while (it.hasNext()) {
            proto.addExtension(JvmProtoBuf.typeParameterAnnotation, getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeConstructor(FirConstructor constructor, ProtoBuf.Constructor.Builder proto, FirElementSerializer childSerializer) {
        JvmProtoBuf.JvmMethodSignature jvmMethodSignatureMethodSignature;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Method method = (Method) getBinding(METHOD_FOR_FIR_FUNCTION, constructor);
        if (method == null || (jvmMethodSignatureMethodSignature = this.signatureSerializer.methodSignature(constructor, null, method)) == null) {
            return;
        }
        proto.setExtension(JvmProtoBuf.constructorSignature, jvmMethodSignatureMethodSignature);
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeFunction(FirFunction function, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Method method = (Method) getBinding(METHOD_FOR_FIR_FUNCTION, function);
        if (method != null) {
            FirJvmSignatureSerializer firJvmSignatureSerializer = this.signatureSerializer;
            FirSimpleFunction firSimpleFunction = function instanceof FirSimpleFunction ? (FirSimpleFunction) function : null;
            JvmProtoBuf.JvmMethodSignature jvmMethodSignatureMethodSignature = firJvmSignatureSerializer.methodSignature(function, firSimpleFunction != null ? firSimpleFunction.getName() : null, method);
            if (jvmMethodSignatureMethodSignature != null) {
                proto.setExtension(JvmProtoBuf.methodSignature, jvmMethodSignatureMethodSignature);
            }
        }
        if (!needsInlineParameterNullCheckRequirement(function) || versionRequirementTable == null) {
            return;
        }
        writeInlineParameterNullCheckRequirement(versionRequirementTable, new AnonymousClass1(proto));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.backend.jvm.FirJvmSerializerExtension$serializeFunction$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirJvmSerializerExtension.kt */
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

    private final void writeInlineParameterNullCheckRequirement(MutableVersionRequirementTable mutableVersionRequirementTable, Function1<? super Integer, Unit> function1) {
        if (this.unifiedNullChecks) {
            function1.invoke(Integer.valueOf(DescriptorSerializer.INSTANCE.writeVersionRequirement(1, 3, 50, ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION, mutableVersionRequirementTable)));
        }
    }

    private final boolean needsInlineParameterNullCheckRequirement(FirFunction firFunction) {
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        if (firFunction instanceof FirSimpleFunction) {
            FirFunction firFunction2 = firFunction;
            if (firFunction2.getStatus().isInline() && !firFunction2.getStatus().isSuspend() && !this.isParamAssertionsDisabled && !Visibilities.INSTANCE.isPrivate(firFunction2.getStatus().getVisibility())) {
                List<FirValueParameter> valueParameters = firFunction.getValueParameters();
                if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
                    Iterator<T> it = valueParameters.iterator();
                    while (it.hasNext()) {
                        if (FunctionalTypeUtilsKt.isSomeFunctionType(FirTypeUtilsKt.getConeType(((FirValueParameter) it.next()).getReturnTypeRef()), getSession())) {
                            return true;
                        }
                    }
                }
                FirReceiverParameter receiverParameter = firFunction.getReceiverParameter();
                if (receiverParameter != null && (typeRef = receiverParameter.getTypeRef()) != null && (coneType = FirTypeUtilsKt.getConeType(typeRef)) != null && FunctionalTypeUtilsKt.isSomeFunctionType(coneType, getSession())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeProperty(FirProperty property, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Type type;
        String descriptor;
        Type type2;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        FirPropertyAccessor getter = property.getGetter();
        FirPropertyAccessor setter = property.getSetter();
        Method method = getter == null ? null : (Method) getBinding(METHOD_FOR_FIR_FUNCTION, getter);
        Method method2 = setter == null ? null : (Method) getBinding(METHOD_FOR_FIR_FUNCTION, setter);
        Pair pair = (Pair) getBinding(FIELD_FOR_PROPERTY, property);
        Method method3 = (Method) getBinding(SYNTHETIC_METHOD_FOR_FIR_VARIABLE, property);
        Method method4 = (Method) getBinding(DELEGATE_METHOD_FOR_FIR_VARIABLE, property);
        property.getDelegate();
        JvmProtoBuf.JvmPropertySignature jvmPropertySignaturePropertySignature = this.signatureSerializer.propertySignature(property.getName(), pair != null ? (String) pair.getSecond() : null, (pair == null || (type2 = (Type) pair.getFirst()) == null) ? null : type2.getDescriptor(), method3 != null ? this.signatureSerializer.methodSignature(null, null, method3) : null, method4 != null ? this.signatureSerializer.methodSignature(null, null, method4) : null, method != null ? this.signatureSerializer.methodSignature(null, null, method) : null, method2 != null ? this.signatureSerializer.methodSignature(null, null, method2) : null, (pair == null || (type = (Type) pair.getFirst()) == null || (descriptor = type.getDescriptor()) == null) ? false : this.signatureSerializer.requiresPropertySignature(property, descriptor));
        if (jvmPropertySignaturePropertySignature != null) {
            proto.setExtension(JvmProtoBuf.propertySignature, jvmPropertySignaturePropertySignature);
        }
        if (isJvmFieldPropertyInInterfaceCompanion(property) && versionRequirementTable != null) {
            proto.setExtension(JvmProtoBuf.flags, Integer.valueOf(JvmFlags.INSTANCE.getPropertyFlags(true)));
        }
        if (((getter == null || !needsInlineParameterNullCheckRequirement(getter)) && (setter == null || !needsInlineParameterNullCheckRequirement(setter))) || versionRequirementTable == null) {
            return;
        }
        writeInlineParameterNullCheckRequirement(versionRequirementTable, new AnonymousClass2(proto));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.backend.jvm.FirJvmSerializerExtension$serializeProperty$2, reason: invalid class name */
    /* JADX INFO: compiled from: FirJvmSerializerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<Integer, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "serializeProperty$addVersionRequirement$3(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;I)V";
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

    private final boolean isJvmFieldPropertyInInterfaceCompanion(FirProperty firProperty) {
        List<FirAnnotation> annotations;
        ConeClassLikeLookupTag lookupTag;
        FirSession session = getSession();
        FirBackingField backingField = firProperty.getBackingField();
        if (backingField == null || (annotations = backingField.getAnnotations()) == null) {
            return false;
        }
        List<FirAnnotation> list = annotations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (FirAnnotationUtilsKt.isJvmFieldAnnotation((FirAnnotation) it.next(), session)) {
                ConeSimpleKotlinType dispatchReceiverType = firProperty.getDispatchReceiverType();
                FirRegularClass firRegularClass = null;
                ConeClassLikeType coneClassLikeType = dispatchReceiverType instanceof ConeClassLikeType ? (ConeClassLikeType) dispatchReceiverType : null;
                FirRegularClassSymbol firRegularClassSymbol = (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? null : LookupTagUtilsKt.toFirRegularClassSymbol(lookupTag, getSession());
                if (firRegularClassSymbol == null) {
                    return false;
                }
                FirRegularClassSymbol firRegularClassSymbol2 = firRegularClassSymbol;
                if (!firRegularClassSymbol2.getRawStatus().isCompanion() || firRegularClassSymbol2.getClassId().isLocal()) {
                    return false;
                }
                ClassId outerClassId = firRegularClassSymbol.getClassId().getOuterClassId();
                if (outerClassId != null) {
                    FirClassLikeDeclaration firClassifierByFqName = FirProviderKt.getFirProvider(getSession()).getFirClassifierByFqName(outerClassId);
                    if (firClassifierByFqName instanceof FirRegularClass) {
                        firRegularClass = (FirRegularClass) firClassifierByFqName;
                    }
                }
                if (firRegularClass != null) {
                    return firRegularClass.getClassKind() == ClassKind.INTERFACE || firRegularClass.getClassKind() == ClassKind.ANNOTATION_CLASS;
                }
                return false;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeErrorType(ConeErrorType type, ProtoBuf.Type.Builder builder) {
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

    /* JADX INFO: compiled from: FirJvmSerializerExtension.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R-\u0010\u0003\u001a\u001e\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tRE\u0010\n\u001a6\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u000b0\u000b\u0012$\u0012\"\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e \u0006*\u0010\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0018\u00010\f0\f0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R-\u0010\u0016\u001a\u001e\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00170\u0017\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\tR-\u0010\u0019\u001a\u001e\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\t¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmSerializerExtension$Companion;", "", "()V", "DELEGATE_METHOD_FOR_FIR_VARIABLE", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings$SerializationMappingSlice;", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getDELEGATE_METHOD_FOR_FIR_VARIABLE", "()Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings$SerializationMappingSlice;", "FIELD_FOR_PROPERTY", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lkotlin/Pair;", "Lorg/jetbrains/org/objectweb/asm/Type;", "", "getFIELD_FOR_PROPERTY", "JVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "JVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID", "JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME", "METHOD_FOR_FIR_FUNCTION", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "getMETHOD_FOR_FIR_FUNCTION", "SYNTHETIC_METHOD_FOR_FIR_VARIABLE", "getSYNTHETIC_METHOD_FOR_FIR_VARIABLE", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JvmSerializationBindings.SerializationMappingSlice<FirFunction, Method> getMETHOD_FOR_FIR_FUNCTION() {
            return FirJvmSerializerExtension.METHOD_FOR_FIR_FUNCTION;
        }

        public final JvmSerializationBindings.SerializationMappingSlice<FirProperty, Pair<Type, String>> getFIELD_FOR_PROPERTY() {
            return FirJvmSerializerExtension.FIELD_FOR_PROPERTY;
        }

        public final JvmSerializationBindings.SerializationMappingSlice<FirVariable, Method> getSYNTHETIC_METHOD_FOR_FIR_VARIABLE() {
            return FirJvmSerializerExtension.SYNTHETIC_METHOD_FOR_FIR_VARIABLE;
        }

        public final JvmSerializationBindings.SerializationMappingSlice<FirVariable, Method> getDELEGATE_METHOD_FOR_FIR_VARIABLE() {
            return FirJvmSerializerExtension.DELEGATE_METHOD_FOR_FIR_VARIABLE;
        }
    }

    static {
        JvmSerializationBindings.SerializationMappingSlice<FirFunction, Method> serializationMappingSliceCreate = JvmSerializationBindings.SerializationMappingSlice.create();
        Intrinsics.checkNotNullExpressionValue(serializationMappingSliceCreate, "create(...)");
        METHOD_FOR_FIR_FUNCTION = serializationMappingSliceCreate;
        JvmSerializationBindings.SerializationMappingSlice<FirProperty, Pair<Type, String>> serializationMappingSliceCreate2 = JvmSerializationBindings.SerializationMappingSlice.create();
        Intrinsics.checkNotNullExpressionValue(serializationMappingSliceCreate2, "create(...)");
        FIELD_FOR_PROPERTY = serializationMappingSliceCreate2;
        JvmSerializationBindings.SerializationMappingSlice<FirVariable, Method> serializationMappingSliceCreate3 = JvmSerializationBindings.SerializationMappingSlice.create();
        Intrinsics.checkNotNullExpressionValue(serializationMappingSliceCreate3, "create(...)");
        SYNTHETIC_METHOD_FOR_FIR_VARIABLE = serializationMappingSliceCreate3;
        JvmSerializationBindings.SerializationMappingSlice<FirVariable, Method> serializationMappingSliceCreate4 = JvmSerializationBindings.SerializationMappingSlice.create();
        Intrinsics.checkNotNullExpressionValue(serializationMappingSliceCreate4, "create(...)");
        DELEGATE_METHOD_FOR_FIR_VARIABLE = serializationMappingSliceCreate4;
        FqName fqName = new FqName("kotlin.jvm.JvmDefaultWithoutCompatibility");
        JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME = fqName;
        FqName fqName2 = new FqName("kotlin.jvm.JvmDefaultWithCompatibility");
        JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME = fqName2;
        ClassId classId = ClassId.topLevel(fqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        JVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID = classId;
        ClassId classId2 = ClassId.topLevel(fqName2);
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        JVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID = classId2;
    }
}
