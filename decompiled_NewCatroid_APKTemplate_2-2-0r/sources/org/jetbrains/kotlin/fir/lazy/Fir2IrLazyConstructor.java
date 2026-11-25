package org.jetbrains.kotlin.fir.lazy;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrAnnotationsFromPluginRegistrar;
import org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns;
import org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverterKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.Fir2IrConstructorSymbol;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.declarations.lazy.LazyUtilKt;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Fir2IrLazyConstructor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000È\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\u00020\u00042\u00020\u0005B5\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ@\u0010É\u0001\u001a\u0003HÊ\u0001\"\u0005\b\u0000\u0010Ê\u0001\"\u0005\b\u0001\u0010Ë\u00012\u0016\u0010Ì\u0001\u001a\u0011\u0012\u0005\u0012\u0003HÊ\u0001\u0012\u0005\u0012\u0003HË\u00010Í\u00012\b\u0010Î\u0001\u001a\u0003HË\u0001H\u0016¢\u0006\u0003\u0010Ï\u0001R\u0012\u0010\u0010\u001a\u00020\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R7\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0012\u0010\u001e\u001a\u00020\u001fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b \u0010!R\u001c\u0010\"\u001a\u0004\u0018\u00010#X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0012\u0010(\u001a\u00020)X\u0096\u0005¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0012\u0010,\u001a\u00020-X\u0096\u0005¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0012\u00100\u001a\u000201X\u0096\u0005¢\u0006\u0006\u001a\u0004\b2\u00103R\u0012\u00104\u001a\u000205X\u0096\u0005¢\u0006\u0006\u001a\u0004\b6\u00107R\u0016\u00108\u001a\u0004\u0018\u0001098VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u001a\u0010<\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u0012\u0010A\u001a\u00020BX\u0096\u0005¢\u0006\u0006\u001a\u0004\bC\u0010DR\u0012\u0010E\u001a\u00020FX\u0096\u0005¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0012\u0010I\u001a\u00020JX\u0096\u0005¢\u0006\u0006\u001a\u0004\bK\u0010LR\u001a\u0010M\u001a\u00020N8VX\u0097\u0004¢\u0006\f\u0012\u0004\bO\u0010P\u001a\u0004\bQ\u0010RR/\u0010T\u001a\u0004\u0018\u00010S2\b\u0010\u0014\u001a\u0004\u0018\u00010S8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bY\u0010\u001d\u001a\u0004\bU\u0010V\"\u0004\bW\u0010XR\u0014\u0010\t\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010>R\u001c\u0010[\u001a\u0004\u0018\u00010SX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010V\"\u0004\b]\u0010XR\u0012\u0010^\u001a\u00020_X\u0096\u0005¢\u0006\u0006\u001a\u0004\b`\u0010aR\u0012\u0010b\u001a\u00020cX\u0096\u0005¢\u0006\u0006\u001a\u0004\bd\u0010eR\u0014\u0010\f\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bf\u0010gR\u0012\u0010h\u001a\u00020iX\u0096\u0005¢\u0006\u0006\u001a\u0004\bj\u0010kR\u0012\u0010l\u001a\u00020mX\u0096\u0005¢\u0006\u0006\u001a\u0004\bn\u0010oR\u0018\u0010p\u001a\b\u0012\u0004\u0012\u00020q0\u0015X\u0096\u0005¢\u0006\u0006\u001a\u0004\br\u0010\u0019R$\u0010u\u001a\u00020t2\u0006\u0010s\u001a\u00020t8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bu\u0010v\"\u0004\bw\u0010xR$\u0010y\u001a\u00020t2\u0006\u0010s\u001a\u00020t8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\by\u0010v\"\u0004\bz\u0010xR$\u0010{\u001a\u00020t2\u0006\u0010s\u001a\u00020t8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b{\u0010v\"\u0004\b|\u0010xR$\u0010}\u001a\u00020t2\u0006\u0010s\u001a\u00020t8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b}\u0010v\"\u0004\b~\u0010xR\u0015\u0010\u007f\u001a\u00030\u0080\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b\u0081\u0001\u0010\u0082\u0001R/\u0010\u0084\u0001\u001a\u0005\u0018\u00010\u0083\u00012\t\u0010s\u001a\u0005\u0018\u00010\u0083\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u0085\u0001\u0010\u0086\u0001\"\u0006\b\u0087\u0001\u0010\u0088\u0001R+\u0010\u008a\u0001\u001a\u00030\u0089\u00012\u0007\u0010s\u001a\u00030\u0089\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001\"\u0006\b\u008d\u0001\u0010\u008e\u0001R\u001e\u0010\n\u001a\u00020\u000bX\u0096\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u008f\u0001\u0010\u0090\u0001\"\u0006\b\u0091\u0001\u0010\u0092\u0001R \u0010\u0093\u0001\u001a\u00030\u0094\u0001X\u0096.¢\u0006\u0012\n\u0000\u001a\u0006\b\u0095\u0001\u0010\u0096\u0001\"\u0006\b\u0097\u0001\u0010\u0098\u0001R3\u0010\u009a\u0001\u001a\u00030\u0099\u00012\u0007\u0010\u0014\u001a\u00030\u0099\u00018V@VX\u0096\u008e\u0002¢\u0006\u0017\n\u0005\b\u009f\u0001\u0010\u001d\u001a\u0006\b\u009b\u0001\u0010\u009c\u0001\"\u0006\b\u009d\u0001\u0010\u009e\u0001R\u0016\u0010 \u0001\u001a\u00030¡\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b¢\u0001\u0010£\u0001R\u0016\u0010¤\u0001\u001a\u00030¥\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b¦\u0001\u0010§\u0001R\u0016\u0010¨\u0001\u001a\u00030©\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bª\u0001\u0010«\u0001R\u0015\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¬\u0001\u0010>R\u0016\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u00ad\u0001\u0010®\u0001R\u0016\u0010¯\u0001\u001a\u00030°\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b±\u0001\u0010²\u0001R\u0016\u0010³\u0001\u001a\u00030´\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bµ\u0001\u0010¶\u0001R$\u0010·\u0001\u001a\t\u0012\u0005\u0012\u00030¸\u00010\u0015X\u0096.¢\u0006\u0010\n\u0000\u001a\u0005\b¹\u0001\u0010\u0019\"\u0005\bº\u0001\u0010\u001bR;\u0010»\u0001\u001a\b\u0012\u0004\u0012\u00020S0\u00152\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020S0\u00158V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b¾\u0001\u0010\u001d\u001a\u0005\b¼\u0001\u0010\u0019\"\u0005\b½\u0001\u0010\u001bR+\u0010À\u0001\u001a\u00030¿\u00012\u0007\u0010s\u001a\u00030¿\u0001@VX\u0096\u000e¢\u0006\u0012\n\u0000\u001a\u0006\bÁ\u0001\u0010Â\u0001\"\u0006\bÃ\u0001\u0010Ä\u0001R\u0016\u0010Å\u0001\u001a\u00030Æ\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bÇ\u0001\u0010È\u0001¨\u0006Ð\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/lazy/Fir2IrLazyConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/fir/lazy/AbstractFir2IrLazyDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/fir/lazy/Fir2IrTypeParametersContainer;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "fir", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/Fir2IrConstructorSymbol;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;Lorg/jetbrains/kotlin/fir/symbols/Fir2IrConstructorSymbol;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "setBody", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;)V", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceiverParametersCount", "getContextReceiverParametersCount", "()I", "setContextReceiverParametersCount", "(I)V", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "dispatchReceiverParameter", "getDispatchReceiverParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setDispatchReceiverParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "dispatchReceiverParameter$delegate", "getEndOffset", "extensionReceiverParameter", "getExtensionReceiverParameter", "setExtensionReceiverParameter", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "<anonymous parameter 0>", "", "isExpect", "()Z", "setExpect", "(Z)V", "isExternal", "setExternal", "isInline", "setInline", "isPrimary", "setPrimary", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "metadata", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "Lorg/jetbrains/kotlin/name/Name;", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "Lorg/jetbrains/kotlin/ir/types/IrType;", "returnType", "getReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setReturnType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "returnType$delegate", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/Fir2IrConstructorSymbol;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "typeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getTypeParameters", "setTypeParameters", "valueParameters", "getValueParameters", "setValueParameters", "valueParameters$delegate", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "visibility", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrLazyConstructor extends IrConstructor implements Fir2IrComponents, AbstractFir2IrLazyDeclaration<FirConstructor>, Fir2IrTypeParametersContainer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyConstructor.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyConstructor.class), "returnType", "getReturnType()Lorg/jetbrains/kotlin/ir/types/IrType;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyConstructor.class), "dispatchReceiverParameter", "getDispatchReceiverParameter()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyConstructor.class), "valueParameters", "getValueParameters()Ljava/util/List;"))};
    private final /* synthetic */ Fir2IrComponents $$delegate_0;

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;
    private IrBody body;
    private int contextReceiverParametersCount;

    /* JADX INFO: renamed from: dispatchReceiverParameter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty dispatchReceiverParameter;
    private final int endOffset;
    private IrValueParameter extensionReceiverParameter;
    private final FirConstructor fir;
    private IrDeclarationOrigin origin;
    public IrDeclarationParent parent;

    /* JADX INFO: renamed from: returnType$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty returnType;
    private final int startOffset;
    private final Fir2IrConstructorSymbol symbol;
    public List<? extends IrTypeParameter> typeParameters;

    /* JADX INFO: renamed from: valueParameters$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty valueParameters;
    private DescriptorVisibility visibility;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public AnnotationGenerator getAnnotationGenerator() {
        return this.$$delegate_0.getAnnotationGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrAnnotationsFromPluginRegistrar getAnnotationsFromPluginRegistrar() {
        return this.$$delegate_0.getAnnotationsFromPluginRegistrar();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrBuiltIns getBuiltIns() {
        return this.$$delegate_0.getBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public CallAndReferenceGenerator getCallGenerator() {
        return this.$$delegate_0.getCallGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrClassifierStorage getClassifierStorage() {
        return this.$$delegate_0.getClassifierStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConfiguration getConfiguration() {
        return this.$$delegate_0.getConfiguration();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConverter getConverter() {
        return this.$$delegate_0.getConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrDeclarationStorage getDeclarationStorage() {
        return this.$$delegate_0.getDeclarationStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public DelegatedMemberGenerator getDelegatedMemberGenerator() {
        return this.$$delegate_0.getDelegatedMemberGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrExtensions getExtensions() {
        return this.$$delegate_0.getExtensions();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FakeOverrideGenerator getFakeOverrideGenerator() {
        return this.$$delegate_0.getFakeOverrideGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrBuiltInsOverFir getIrBuiltIns() {
        return this.$$delegate_0.getIrBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrFactory getIrFactory() {
        return this.$$delegate_0.getIrFactory();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public List<IrProvider> getIrProviders() {
        return this.$$delegate_0.getIrProviders();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrLock getLock() {
        return this.$$delegate_0.getLock();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public ScopeSession getScopeSession() {
        return this.$$delegate_0.getScopeSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirSession getSession() {
        return this.$$delegate_0.getSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirBasedSignatureComposer getSignatureComposer() {
        return this.$$delegate_0.getSignatureComposer();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public SymbolTable getSymbolTable() {
        return this.$$delegate_0.getSymbolTable();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrTypeConverter getTypeConverter() {
        return this.$$delegate_0.getTypeConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrVisibilityConverter getVisibilityConverter() {
        return this.$$delegate_0.getVisibilityConverter();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, IrDeclarationParent> createLazyParent() {
        return AbstractFir2IrLazyDeclaration.DefaultImpls.createLazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrValueParameter generateReceiverParameterStub(ReceiverParameterDescriptor receiverParameterDescriptor) {
        return AbstractFir2IrLazyDeclaration.DefaultImpls.generateReceiverParameterStub(this, receiverParameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrType toIrType(KotlinType kotlinType) {
        return AbstractFir2IrLazyDeclaration.DefaultImpls.toIrType(this, kotlinType);
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setOrigin(IrDeclarationOrigin irDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(irDeclarationOrigin, "<set-?>");
        this.origin = irDeclarationOrigin;
    }

    @Override // org.jetbrains.kotlin.fir.lazy.AbstractFir2IrLazyDeclaration
    public FirConstructor getFir() {
        return this.fir;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public Fir2IrConstructorSymbol getSymbol() {
        return this.symbol;
    }

    public Fir2IrLazyConstructor(Fir2IrComponents components, int i, int i2, IrDeclarationOrigin origin, FirConstructor fir, Fir2IrConstructorSymbol symbol) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(fir, "fir");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.fir = fir;
        this.symbol = symbol;
        this.$$delegate_0 = components;
        getSymbol().bind(this);
        getClassifierStorage().preCacheTypeParameters$fir2ir(getFir(), getSymbol());
        this.annotations = createLazyAnnotations();
        this.visibility = components.getVisibilityConverter().convertToDescriptorVisibility(getFir().getStatus().getVisibility());
        this.returnType = LazyUtilKt.lazyVar(getLock(), new Function0<IrType>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyConstructor$returnType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                return Fir2IrTypeConverterKt.toIrType$default(this.this$0.getFir().getReturnTypeRef(), this.this$0.getTypeConverter(), null, 2, null);
            }
        });
        this.dispatchReceiverParameter = LazyUtilKt.lazyVar(getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyConstructor$dispatchReceiverParameter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                IrDeclarationParent parent = this.this$0.getParent();
                IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                IrClass parentClassOrNull = irClass != null ? IrUtilsKt.getParentClassOrNull(irClass) : null;
                if (irClass == null || !irClass.getIsInner() || parentClassOrNull == null) {
                    return null;
                }
                this.this$0.getDeclarationStorage().enterScope(this.this$0);
                Fir2IrLazyConstructor fir2IrLazyConstructor = this.this$0;
                Fir2IrLazyConstructor fir2IrLazyConstructor2 = fir2IrLazyConstructor;
                Fir2IrLazyConstructor fir2IrLazyConstructor3 = fir2IrLazyConstructor;
                IrValueParameter thisReceiver = parentClassOrNull.getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver);
                IrValueParameter irValueParameterDeclareThisReceiverParameter$default = ConversionUtilsKt.declareThisReceiverParameter$default(fir2IrLazyConstructor2, fir2IrLazyConstructor3, thisReceiver.getType(), this.this$0.getOrigin(), 0, 0, null, null, 120, null);
                Fir2IrLazyConstructor fir2IrLazyConstructor4 = this.this$0;
                fir2IrLazyConstructor4.getDeclarationStorage().leaveScope(fir2IrLazyConstructor4);
                return irValueParameterDeclareThisReceiverParameter$default;
            }
        });
        this.contextReceiverParametersCount = getFir().getContextReceivers().size();
        this.valueParameters = LazyUtilKt.lazyVar(getLock(), new Function0<List<? extends IrValueParameter>>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyConstructor$valueParameters$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrValueParameter> invoke() {
                this.this$0.getDeclarationStorage().enterScope(this.this$0);
                Fir2IrLazyConstructor fir2IrLazyConstructor = this.this$0;
                List<IrValueParameter> listCreateListBuilder = CollectionsKt.createListBuilder();
                fir2IrLazyConstructor.getDeclarationStorage().addContextReceiverParametersTo(fir2IrLazyConstructor.getFir().getContextReceivers(), fir2IrLazyConstructor, listCreateListBuilder);
                int i3 = 0;
                for (Object obj : fir2IrLazyConstructor.getFir().getValueParameters()) {
                    List<IrValueParameter> list = listCreateListBuilder;
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    FirValueParameter firValueParameter = (FirValueParameter) obj;
                    Fir2IrDeclarationStorage declarationStorage = fir2IrLazyConstructor.getDeclarationStorage();
                    int contextReceiverParametersCount = i3 + fir2IrLazyConstructor.getContextReceiverParametersCount();
                    IrDeclarationParent parent = fir2IrLazyConstructor.getParent();
                    IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                    boolean z = !Intrinsics.areEqual(irClass != null ? irClass.getName() : null, Name.identifier("Enum"));
                    IrDeclarationParent parent2 = fir2IrLazyConstructor.getParent();
                    IrClass irClass2 = parent2 instanceof IrClass ? (IrClass) parent2 : null;
                    IrValueParameter irValueParameterCreateIrParameter$fir2ir$default = Fir2IrDeclarationStorage.createIrParameter$fir2ir$default(declarationStorage, firValueParameter, contextReceiverParametersCount, z, null, false, irClass2 != null && IrUtilsKt.isAnnotationClass(irClass2), 24, null);
                    irValueParameterCreateIrParameter$fir2ir$default.setParent(fir2IrLazyConstructor);
                    list.add(irValueParameterCreateIrParameter$fir2ir$default);
                    i3 = i4;
                }
                List<? extends IrValueParameter> listBuild = CollectionsKt.build(listCreateListBuilder);
                Fir2IrLazyConstructor fir2IrLazyConstructor2 = this.this$0;
                fir2IrLazyConstructor2.getDeclarationStorage().leaveScope(fir2IrLazyConstructor2);
                return listBuild;
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return (List) this.annotations.getValue(this, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations.setValue(this, $$delegatedProperties[0], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public List<IrTypeParameter> getTypeParameters() {
        List list = this.typeParameters;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("typeParameters");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public void setTypeParameters(List<? extends IrTypeParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.typeParameters = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationParent getParent() {
        IrDeclarationParent irDeclarationParent = this.parent;
        if (irDeclarationParent != null) {
            return irDeclarationParent;
        }
        Intrinsics.throwUninitializedPropertyAccessException("parent");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setParent(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<set-?>");
        this.parent = irDeclarationParent;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrConstructor
    /* JADX INFO: renamed from: isPrimary */
    public boolean getIsPrimary() {
        return getFir().getIsPrimary();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrConstructor
    public void setPrimary(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction, org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public ClassConstructorDescriptor getDescriptor() {
        return (ClassConstructorDescriptor) getSymbol().getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    /* JADX INFO: renamed from: isInline */
    public boolean getIsInline() {
        return getFir().getStatus().isInline();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setInline(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration
    /* JADX INFO: renamed from: isExternal */
    public boolean getIsExternal() {
        return getFir().getStatus().isExternal();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration
    public void setExternal(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    /* JADX INFO: renamed from: isExpect */
    public boolean getIsExpect() {
        return getFir().getStatus().isExpect();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setExpect(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrBody getBody() {
        return this.body;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setBody(IrBody irBody) {
        this.body = irBody;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public Name getName() {
        return SpecialNames.INIT;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<anonymous parameter 0>");
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public DescriptorVisibility getVisibility() {
        return this.visibility;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public void setVisibility(DescriptorVisibility descriptorVisibility) {
        Intrinsics.checkNotNullParameter(descriptorVisibility, "<anonymous parameter 0>");
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrType getReturnType() {
        return (IrType) this.returnType.getValue(this, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setReturnType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.returnType.setValue(this, $$delegatedProperties[1], irType);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrValueParameter getDispatchReceiverParameter() {
        return (IrValueParameter) this.dispatchReceiverParameter.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setDispatchReceiverParameter(IrValueParameter irValueParameter) {
        this.dispatchReceiverParameter.setValue(this, $$delegatedProperties[2], irValueParameter);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrValueParameter getExtensionReceiverParameter() {
        return this.extensionReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setExtensionReceiverParameter(IrValueParameter irValueParameter) {
        this.extensionReceiverParameter = irValueParameter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public int getContextReceiverParametersCount() {
        return this.contextReceiverParametersCount;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setContextReceiverParametersCount(int i) {
        this.contextReceiverParametersCount = i;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public List<IrValueParameter> getValueParameters() {
        return (List) this.valueParameters.getValue(this, $$delegatedProperties[3]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setValueParameters(List<? extends IrValueParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.valueParameters.setValue(this, $$delegatedProperties[3], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        throw new IllegalStateException("We should never need to store metadata of external declarations.".toString());
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        return getFir().getContainerSource();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.declarations.IrConstructor, org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitConstructor2(this, data);
    }
}
