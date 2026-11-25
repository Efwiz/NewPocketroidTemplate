package org.jetbrains.kotlin.fir.lazy;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
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
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.declarations.lazy.AbstractIrLazyFunction;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase;
import org.jetbrains.kotlin.ir.declarations.lazy.LazyUtilKt;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: AbstractFir2IrLazyFunction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ü\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 Ñ\u0001*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00010\u00042\u00020\u00052\u00020\u00062\u00020\u0007:\u0002Ñ\u0001B5\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J \u0010Â\u0001\u001a\u0019\u0012\u0007\u0012\u0005\u0018\u00010Ä\u0001\u0012\u000b\u0012\t\u0012\u0005\u0012\u00030Å\u00010g0Ã\u0001H\u0016J\"\u0010Æ\u0001\u001a\u00030Ç\u00012\b\u0010È\u0001\u001a\u00030É\u00012\f\b\u0002\u0010Ê\u0001\u001a\u0005\u0018\u00010Ë\u0001H\u0004J\t\u0010Ì\u0001\u001a\u00020\u0011H\u0002J\n\u0010Í\u0001\u001a\u00030\u0096\u0001H\u0016J\u0013\u0010Î\u0001\u001a\u00020\u00112\b\u0010Ï\u0001\u001a\u00030Ð\u0001H\u0004R\u0012\u0010\u0013\u001a\u00020\u0014X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R/\u0010#\u001a\u0004\u0018\u00010\"2\b\u0010!\u001a\u0004\u0018\u00010\"8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0012\u0010*\u001a\u00020+X\u0096\u0005¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0012\u0010.\u001a\u00020/X\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u00101R\u0012\u00102\u001a\u000203X\u0096\u0005¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u00109R\u0012\u0010:\u001a\u00020;X\u0096\u0005¢\u0006\u0006\u001a\u0004\b<\u0010=R\u001c\u0010>\u001a\u0004\u0018\u00010?X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u0012\u0010D\u001a\u00020EX\u0096\u0005¢\u0006\u0006\u001a\u0004\bF\u0010GR\u0012\u0010H\u001a\u00020IX\u0096\u0005¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u001a\u0010L\u001a\u00020M8VX\u0097\u0004¢\u0006\f\u0012\u0004\bN\u0010O\u001a\u0004\bP\u0010QR\u0014\u0010\u000b\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010SR\u0012\u0010T\u001a\u00020UX\u0096\u0005¢\u0006\u0006\u001a\u0004\bV\u0010WR\u0014\u0010X\u001a\u00020Y8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bZ\u0010[R\u0012\u0010\\\u001a\u00020]X\u0096\u0005¢\u0006\u0006\u001a\u0004\b^\u0010_R\u0012\u0010`\u001a\u00020aX\u0096\u0005¢\u0006\u0006\u001a\u0004\bb\u0010cR\u0012\u0010d\u001a\u00020YX\u0096\u0005¢\u0006\u0006\u001a\u0004\be\u0010[R\u0018\u0010f\u001a\b\u0012\u0004\u0012\u00020h0gX\u0096\u0005¢\u0006\u0006\u001a\u0004\bi\u0010jR\u0014\u0010k\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bk\u0010lR$\u0010n\u001a\u00020\u00112\u0006\u0010m\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bn\u0010l\"\u0004\bo\u0010pR$\u0010q\u001a\u00020\u00112\u0006\u0010m\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bq\u0010l\"\u0004\br\u0010pR\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010l\"\u0004\bs\u0010pR$\u0010t\u001a\u00020\u00112\u0006\u0010m\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bt\u0010l\"\u0004\bu\u0010pR$\u0010v\u001a\u00020\u00112\u0006\u0010m\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bv\u0010l\"\u0004\bw\u0010pR$\u0010x\u001a\u00020\u00112\u0006\u0010m\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bx\u0010l\"\u0004\by\u0010pR$\u0010z\u001a\u00020\u00112\u0006\u0010m\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bz\u0010l\"\u0004\b{\u0010pR$\u0010|\u001a\u00020\u00112\u0006\u0010m\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b|\u0010l\"\u0004\b}\u0010pR\u0014\u0010~\u001a\u00020\u007fX\u0096\u0005¢\u0006\b\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001R/\u0010\u0083\u0001\u001a\u0005\u0018\u00010\u0082\u00012\t\u0010m\u001a\u0005\u0018\u00010\u0082\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u0084\u0001\u0010\u0085\u0001\"\u0006\b\u0086\u0001\u0010\u0087\u0001R+\u0010\u0089\u0001\u001a\u00030\u0088\u00012\u0007\u0010m\u001a\u00030\u0088\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u008a\u0001\u0010\u008b\u0001\"\u0006\b\u008c\u0001\u0010\u008d\u0001R\u001e\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u008e\u0001\u0010\u008f\u0001\"\u0006\b\u0090\u0001\u0010\u0091\u0001R\u001f\u0010\u0092\u0001\u001a\u0004\u0018\u00010\u001cX\u0096\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0093\u0001\u0010\u001e\"\u0005\b\u0094\u0001\u0010 R \u0010\u0095\u0001\u001a\u00030\u0096\u0001X\u0096.¢\u0006\u0012\n\u0000\u001a\u0006\b\u0097\u0001\u0010\u0098\u0001\"\u0006\b\u0099\u0001\u0010\u009a\u0001R\u0016\u0010\u009b\u0001\u001a\u00030\u009c\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b\u009d\u0001\u0010\u009e\u0001R\u0016\u0010\u009f\u0001\u001a\u00030 \u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b¡\u0001\u0010¢\u0001R\u0016\u0010£\u0001\u001a\u00030¤\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b¥\u0001\u0010¦\u0001R\u0015\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b§\u0001\u0010SR\u0016\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b¨\u0001\u0010©\u0001R\u0016\u0010ª\u0001\u001a\u00030«\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b¬\u0001\u0010\u00ad\u0001R\u0016\u0010®\u0001\u001a\u00030¯\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b°\u0001\u0010±\u0001R%\u0010²\u0001\u001a\t\u0012\u0005\u0012\u00030³\u00010gX\u0096.¢\u0006\u0011\n\u0000\u001a\u0005\b´\u0001\u0010j\"\u0006\bµ\u0001\u0010¶\u0001R3\u0010¸\u0001\u001a\u00030·\u00012\u0007\u0010!\u001a\u00030·\u00018V@VX\u0096\u008e\u0002¢\u0006\u0017\n\u0005\b½\u0001\u0010)\u001a\u0006\b¹\u0001\u0010º\u0001\"\u0006\b»\u0001\u0010¼\u0001R\u0016\u0010¾\u0001\u001a\u00030¿\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bÀ\u0001\u0010Á\u0001¨\u0006Ò\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/lazy/AbstractFir2IrLazyFunction;", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/AbstractIrLazyFunction;", "Lorg/jetbrains/kotlin/fir/lazy/AbstractFir2IrLazyDeclaration;", "Lorg/jetbrains/kotlin/fir/lazy/Fir2IrTypeParametersContainer;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyFunctionBase;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "isFakeOverride", "", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Z)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "attributeOwnerId", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "getAttributeOwnerId", "()Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "setAttributeOwnerId", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)V", "<set-?>", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "setBody", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;)V", "body$delegate", "Lkotlin/properties/ReadWriteProperty;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "correspondingPropertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getCorrespondingPropertySymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "setCorrespondingPropertySymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getEndOffset", "()I", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "getIrFactory", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "isDeserializationEnabled", "()Z", "<anonymous parameter 0>", "isExpect", "setExpect", "(Z)V", "isExternal", "setExternal", "setFakeOverride", "isInfix", "setInfix", "isInline", "setInline", "isOperator", "setOperator", "isSuspend", "setSuspend", "isTailrec", "setTailrec", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "metadata", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "Lorg/jetbrains/kotlin/descriptors/Modality;", "modality", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "originalBeforeInline", "getOriginalBeforeInline", "setOriginalBeforeInline", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "typeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getTypeParameters", "setTypeParameters", "(Ljava/util/List;)V", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "visibility", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "visibility$delegate", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "createLazyAnnotations", "Lkotlin/properties/ReadWriteProperty;", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "createThisReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "thisType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "hasJvmStaticAnnotation", "lazyParent", "shouldHaveDispatchReceiver", "containingClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Companion", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFir2IrLazyFunction<F extends FirCallableDeclaration> extends AbstractIrLazyFunction implements Fir2IrComponents, AbstractFir2IrLazyDeclaration<F>, Fir2IrTypeParametersContainer, IrLazyFunctionBase {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(AbstractFir2IrLazyFunction.class), "body", "getBody()Lorg/jetbrains/kotlin/ir/expressions/IrBody;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(AbstractFir2IrLazyFunction.class), "visibility", "getVisibility()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;"))};
    private static final ClassId JVM_STATIC_CLASS_ID;
    private final /* synthetic */ Fir2IrComponents $$delegate_0;
    private IrAttributeContainer attributeOwnerId;

    /* JADX INFO: renamed from: body$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty body;
    private IrPropertySymbol correspondingPropertySymbol;
    private final int endOffset;
    private boolean isFakeOverride;
    private IrDeclarationOrigin origin;
    private IrAttributeContainer originalBeforeInline;
    public IrDeclarationParent parent;
    private final int startOffset;
    private final IrSimpleFunctionSymbol symbol;
    public List<? extends IrTypeParameter> typeParameters;

    /* JADX INFO: renamed from: visibility$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty visibility;

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

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public IrSimpleFunctionSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    /* JADX INFO: renamed from: isFakeOverride, reason: from getter */
    public boolean getIsFakeOverride() {
        return this.isFakeOverride;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setFakeOverride(boolean z) {
        this.isFakeOverride = z;
    }

    public AbstractFir2IrLazyFunction(final Fir2IrComponents components, int i, int i2, IrDeclarationOrigin origin, IrSimpleFunctionSymbol symbol, boolean z) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.isFakeOverride = z;
        this.$$delegate_0 = components;
        this.body = LazyUtilKt.lazyVar(getLock(), new Function0<IrBody>(this) { // from class: org.jetbrains.kotlin.fir.lazy.AbstractFir2IrLazyFunction$body$2
            final /* synthetic */ AbstractFir2IrLazyFunction<F> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrBody invoke() {
                if (this.this$0.tryLoadIr()) {
                    return this.this$0.getBody();
                }
                return null;
            }
        });
        this.visibility = LazyUtilKt.lazyVar(getLock(), new Function0<DescriptorVisibility>() { // from class: org.jetbrains.kotlin.fir.lazy.AbstractFir2IrLazyFunction$visibility$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DescriptorVisibility invoke() {
                return components.getVisibilityConverter().convertToDescriptorVisibility(((FirMemberDeclaration) this.getFir()).getStatus().getVisibility());
            }
        });
        this.attributeOwnerId = this;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    /* JADX INFO: renamed from: isTailrec */
    public boolean getIsTailrec() {
        return getFir().getStatus().isTailRec();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setTailrec(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    /* JADX INFO: renamed from: isSuspend */
    public boolean getIsSuspend() {
        return getFir().getStatus().isSuspend();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setSuspend(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    /* JADX INFO: renamed from: isOperator */
    public boolean getIsOperator() {
        return getFir().getStatus().isOperator();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setOperator(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    /* JADX INFO: renamed from: isInfix */
    public boolean getIsInfix() {
        return getFir().getStatus().isInfix();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setInfix(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public FunctionDescriptor getDescriptor() {
        return getSymbol().getDescriptor();
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

    static {
        ClassId classId = ClassId.topLevel(AnnotationUtilKt.getJVM_STATIC_ANNOTATION_FQ_NAME());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        JVM_STATIC_CLASS_ID = classId;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrBody getBody() {
        return (IrBody) this.body.getValue(this, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setBody(IrBody irBody) {
        this.body.setValue(this, $$delegatedProperties[0], irBody);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public DescriptorVisibility getVisibility() {
        return (DescriptorVisibility) this.visibility.getValue(this, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public void setVisibility(DescriptorVisibility descriptorVisibility) {
        Intrinsics.checkNotNullParameter(descriptorVisibility, "<set-?>");
        this.visibility.setValue(this, $$delegatedProperties[1], descriptorVisibility);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableMember
    public Modality getModality() {
        Modality modality = getFir().getStatus().getModality();
        Intrinsics.checkNotNull(modality);
        return modality;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableMember
    public void setModality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "<anonymous parameter 0>");
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public IrPropertySymbol getCorrespondingPropertySymbol() {
        return this.correspondingPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setCorrespondingPropertySymbol(IrPropertySymbol irPropertySymbol) {
        this.correspondingPropertySymbol = irPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getAttributeOwnerId() {
        return this.attributeOwnerId;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public void setAttributeOwnerId(IrAttributeContainer irAttributeContainer) {
        Intrinsics.checkNotNullParameter(irAttributeContainer, "<set-?>");
        this.attributeOwnerId = irAttributeContainer;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getOriginalBeforeInline() {
        return this.originalBeforeInline;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public void setOriginalBeforeInline(IrAttributeContainer irAttributeContainer) {
        this.originalBeforeInline = irAttributeContainer;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        throw new IllegalStateException("We should never need to store metadata of external declarations.".toString());
    }

    protected final boolean shouldHaveDispatchReceiver(IrClass containingClass) {
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        return (getFir().getStatus().isStatic() || IrUtilsKt.isFacadeClass(containingClass) || (IrUtilsKt.isObject(containingClass) && !containingClass.getIsCompanion() && hasJvmStaticAnnotation())) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean hasJvmStaticAnnotation() {
        FirPropertySymbol propertySymbol;
        FirProperty firProperty;
        F fir = getFir();
        ClassId classId = JVM_STATIC_CLASS_ID;
        if (FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) fir, classId, getSession())) {
            return true;
        }
        F fir2 = getFir();
        FirPropertyAccessor firPropertyAccessor = fir2 instanceof FirPropertyAccessor ? (FirPropertyAccessor) fir2 : null;
        return (firPropertyAccessor == null || (propertySymbol = firPropertyAccessor.getPropertySymbol()) == null || (firProperty = (FirProperty) propertySymbol.getFir()) == null || !FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) firProperty, classId, getSession())) ? false : true;
    }

    public static /* synthetic */ IrValueParameter createThisReceiverParameter$default(AbstractFir2IrLazyFunction abstractFir2IrLazyFunction, IrType irType, FirReceiverParameter firReceiverParameter, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createThisReceiverParameter");
        }
        if ((i & 2) != 0) {
            firReceiverParameter = null;
        }
        return abstractFir2IrLazyFunction.createThisReceiverParameter(irType, firReceiverParameter);
    }

    protected final IrValueParameter createThisReceiverParameter(IrType thisType, FirReceiverParameter explicitReceiver) {
        Intrinsics.checkNotNullParameter(thisType, "thisType");
        AbstractFir2IrLazyFunction<F> abstractFir2IrLazyFunction = this;
        getDeclarationStorage().enterScope(abstractFir2IrLazyFunction);
        IrValueParameter irValueParameterDeclareThisReceiverParameter$default = ConversionUtilsKt.declareThisReceiverParameter$default(this, this, thisType, getOrigin(), 0, 0, null, explicitReceiver, 56, null);
        getDeclarationStorage().leaveScope(abstractFir2IrLazyFunction);
        return irValueParameterDeclareThisReceiverParameter$default;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.AbstractIrLazyFunction, org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return super.getFactory();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.AbstractIrLazyFunction, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, List<IrConstructorCall>> createLazyAnnotations() {
        return super.createLazyAnnotations();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.AbstractIrLazyFunction
    public boolean isDeserializationEnabled() {
        return getExtensions().getIrNeedsDeserialization();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.AbstractIrLazyFunction, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrDeclarationParent lazyParent() {
        return super.lazyParent();
    }
}
