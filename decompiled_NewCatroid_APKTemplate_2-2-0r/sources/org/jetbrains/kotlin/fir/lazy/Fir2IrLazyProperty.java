package org.jetbrains.kotlin.fir.lazy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.ConversionTypeOrigin;
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
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.Fir2IrPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.Fir2IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.declarations.lazy.LazyUtilKt;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Fir2IrLazyProperty.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ü\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\u00020\u0004BG\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0018\u0010Û\u0001\u001a\u0005\u0018\u00010Ü\u00012\n\u0010Ý\u0001\u001a\u0005\u0018\u00010Þ\u0001H\u0002R\u0012\u0010\u0013\u001a\u00020\u0014X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R7\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00188V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0012\u0010!\u001a\u00020\"X\u0096\u0005¢\u0006\u0006\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R/\u0010,\u001a\u0004\u0018\u00010+2\b\u0010\u0017\u001a\u0004\u0018\u00010+8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b1\u0010 \u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u0012\u00102\u001a\u000203X\u0096\u0005¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u00109R\u0012\u0010:\u001a\u00020;X\u0096\u0005¢\u0006\u0006\u001a\u0004\b<\u0010=R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010>\u001a\u00020?X\u0096\u0005¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0016\u0010B\u001a\u0004\u0018\u00010C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u0012\u0010H\u001a\u00020IX\u0096\u0005¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u0012\u0010L\u001a\u00020MX\u0096\u0005¢\u0006\u0006\u001a\u0004\bN\u0010OR\u0012\u0010P\u001a\u00020QX\u0096\u0005¢\u0006\u0006\u001a\u0004\bR\u0010SR\u001a\u0010T\u001a\u00020U8VX\u0097\u0004¢\u0006\f\u0012\u0004\bV\u0010W\u001a\u0004\bX\u0010YR\u0014\u0010\b\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010[R\u0012\u0010\\\u001a\u00020]X\u0096\u0005¢\u0006\u0006\u001a\u0004\b^\u0010_R\u0012\u0010`\u001a\u00020aX\u0096\u0005¢\u0006\u0006\u001a\u0004\bb\u0010cR\u0014\u0010\u000b\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bd\u0010eR/\u0010g\u001a\u0004\u0018\u00010f2\b\u0010\u0017\u001a\u0004\u0018\u00010f8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bl\u0010 \u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u0012\u0010m\u001a\u00020nX\u0096\u0005¢\u0006\u0006\u001a\u0004\bo\u0010pR\u0012\u0010q\u001a\u00020rX\u0096\u0005¢\u0006\u0006\u001a\u0004\bs\u0010tR\u0018\u0010u\u001a\b\u0012\u0004\u0012\u00020v0\u0018X\u0096\u0005¢\u0006\u0006\u001a\u0004\bw\u0010\u001cR$\u0010y\u001a\u00020\u00112\u0006\u0010x\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\by\u0010z\"\u0004\b{\u0010|R$\u0010}\u001a\u00020\u00112\u0006\u0010x\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b}\u0010z\"\u0004\b~\u0010|R%\u0010\u007f\u001a\u00020\u00112\u0006\u0010x\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\r\u001a\u0004\b\u007f\u0010z\"\u0005\b\u0080\u0001\u0010|R'\u0010\u0081\u0001\u001a\u00020\u00112\u0006\u0010x\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\u000e\u001a\u0005\b\u0081\u0001\u0010z\"\u0005\b\u0082\u0001\u0010|R\u001b\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000f\n\u0000\u001a\u0004\b\u0010\u0010z\"\u0005\b\u0083\u0001\u0010|R'\u0010\u0084\u0001\u001a\u00020\u00112\u0006\u0010x\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\u000e\u001a\u0005\b\u0084\u0001\u0010z\"\u0005\b\u0085\u0001\u0010|R'\u0010\u0086\u0001\u001a\u00020\u00112\u0006\u0010x\u001a\u00020\u00118V@VX\u0096\u000e¢\u0006\u000e\u001a\u0005\b\u0086\u0001\u0010z\"\u0005\b\u0087\u0001\u0010|R\u0016\u0010\u0088\u0001\u001a\u00030\u0089\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b\u008a\u0001\u0010\u008b\u0001R/\u0010\u008d\u0001\u001a\u0005\u0018\u00010\u008c\u00012\t\u0010x\u001a\u0005\u0018\u00010\u008c\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u008e\u0001\u0010\u008f\u0001\"\u0006\b\u0090\u0001\u0010\u0091\u0001R+\u0010\u0093\u0001\u001a\u00030\u0092\u00012\u0007\u0010x\u001a\u00030\u0092\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u0094\u0001\u0010\u0095\u0001\"\u0006\b\u0096\u0001\u0010\u0097\u0001R+\u0010\u0099\u0001\u001a\u00030\u0098\u00012\u0007\u0010x\u001a\u00030\u0098\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u009a\u0001\u0010\u009b\u0001\"\u0006\b\u009c\u0001\u0010\u009d\u0001R\u001e\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u009e\u0001\u0010\u009f\u0001\"\u0006\b \u0001\u0010¡\u0001R\u001f\u0010¢\u0001\u001a\u0004\u0018\u00010&X\u0096\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b£\u0001\u0010(\"\u0005\b¤\u0001\u0010*R=\u0010¦\u0001\u001a\t\u0012\u0005\u0012\u00030¥\u00010\u00182\r\u0010\u0017\u001a\t\u0012\u0005\u0012\u00030¥\u00010\u00188V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b©\u0001\u0010 \u001a\u0005\b§\u0001\u0010\u001c\"\u0005\b¨\u0001\u0010\u001eR \u0010ª\u0001\u001a\u00030«\u0001X\u0096.¢\u0006\u0012\n\u0000\u001a\u0006\b¬\u0001\u0010\u00ad\u0001\"\u0006\b®\u0001\u0010¯\u0001R\u0016\u0010°\u0001\u001a\u00030±\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b²\u0001\u0010³\u0001R\u0016\u0010´\u0001\u001a\u00030µ\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b¶\u0001\u0010·\u0001R3\u0010¸\u0001\u001a\u0004\u0018\u00010f2\b\u0010\u0017\u001a\u0004\u0018\u00010f8V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b»\u0001\u0010 \u001a\u0005\b¹\u0001\u0010i\"\u0005\bº\u0001\u0010kR\u0016\u0010¼\u0001\u001a\u00030½\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b¾\u0001\u0010¿\u0001R\u0015\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÀ\u0001\u0010[R\u0016\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bÁ\u0001\u0010Â\u0001R\u0016\u0010Ã\u0001\u001a\u00030Ä\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bÅ\u0001\u0010Æ\u0001R!\u0010Ç\u0001\u001a\u00030È\u00018BX\u0082\u0084\u0002¢\u0006\u0010\n\u0006\bË\u0001\u0010Ì\u0001\u001a\u0006\bÉ\u0001\u0010Ê\u0001R\u0016\u0010Í\u0001\u001a\u00030Î\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bÏ\u0001\u0010Ð\u0001R+\u0010Ò\u0001\u001a\u00030Ñ\u00012\u0007\u0010x\u001a\u00030Ñ\u0001@VX\u0096\u000e¢\u0006\u0012\n\u0000\u001a\u0006\bÓ\u0001\u0010Ô\u0001\"\u0006\bÕ\u0001\u0010Ö\u0001R\u0016\u0010×\u0001\u001a\u00030Ø\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bÙ\u0001\u0010Ú\u0001¨\u0006ß\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/lazy/Fir2IrLazyProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/fir/lazy/AbstractFir2IrLazyDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "fir", "containingClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/Fir2IrPropertySymbol;", "isFakeOverride", "", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/fir/symbols/Fir2IrPropertySymbol;Z)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "attributeOwnerId", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "getAttributeOwnerId", "()Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "setAttributeOwnerId", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)V", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "backingField", "getBackingField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setBackingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "backingField$delegate", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainingClass", "()Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getEndOffset", "()I", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getter", "getGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setGetter", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "getter$delegate", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "<anonymous parameter 0>", "isConst", "()Z", "setConst", "(Z)V", "isDelegated", "setDelegated", "isExpect", "setExpect", "isExternal", "setExternal", "setFakeOverride", "isLateinit", "setLateinit", "isVar", "setVar", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "metadata", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "Lorg/jetbrains/kotlin/descriptors/Modality;", "modality", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "Lorg/jetbrains/kotlin/name/Name;", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "originalBeforeInline", "getOriginalBeforeInline", "setOriginalBeforeInline", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "overriddenSymbols", "getOverriddenSymbols", "setOverriddenSymbols", "overriddenSymbols$delegate", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "setter", "getSetter", "setSetter", "setter$delegate", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/Fir2IrPropertySymbol;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "type$delegate", "Lkotlin/Lazy;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "visibility", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "toIrInitializer", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "initializer", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrLazyProperty extends IrProperty implements Fir2IrComponents, AbstractFir2IrLazyDeclaration<FirProperty> {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyProperty.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyProperty.class), "backingField", "getBackingField()Lorg/jetbrains/kotlin/ir/declarations/IrField;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyProperty.class), "getter", "getGetter()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyProperty.class), "setter", "getSetter()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyProperty.class), "overriddenSymbols", "getOverriddenSymbols()Ljava/util/List;"))};

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;
    private IrAttributeContainer attributeOwnerId;

    /* JADX INFO: renamed from: backingField$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty backingField;
    private final Fir2IrComponents components;
    private final FirRegularClass containingClass;
    private final int endOffset;
    private final FirProperty fir;

    /* JADX INFO: renamed from: getter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty getter;
    private boolean isFakeOverride;
    private IrDeclarationOrigin origin;
    private IrAttributeContainer originalBeforeInline;

    /* JADX INFO: renamed from: overriddenSymbols$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty overriddenSymbols;
    public IrDeclarationParent parent;

    /* JADX INFO: renamed from: setter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty setter;
    private final int startOffset;
    private final Fir2IrPropertySymbol symbol;

    /* JADX INFO: renamed from: type$delegate, reason: from kotlin metadata */
    private final Lazy type;
    private DescriptorVisibility visibility;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public AnnotationGenerator getAnnotationGenerator() {
        return this.components.getAnnotationGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrAnnotationsFromPluginRegistrar getAnnotationsFromPluginRegistrar() {
        return this.components.getAnnotationsFromPluginRegistrar();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrBuiltIns getBuiltIns() {
        return this.components.getBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public CallAndReferenceGenerator getCallGenerator() {
        return this.components.getCallGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrClassifierStorage getClassifierStorage() {
        return this.components.getClassifierStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConfiguration getConfiguration() {
        return this.components.getConfiguration();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConverter getConverter() {
        return this.components.getConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrDeclarationStorage getDeclarationStorage() {
        return this.components.getDeclarationStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public DelegatedMemberGenerator getDelegatedMemberGenerator() {
        return this.components.getDelegatedMemberGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrExtensions getExtensions() {
        return this.components.getExtensions();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FakeOverrideGenerator getFakeOverrideGenerator() {
        return this.components.getFakeOverrideGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrBuiltInsOverFir getIrBuiltIns() {
        return this.components.getIrBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrFactory getIrFactory() {
        return this.components.getIrFactory();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public List<IrProvider> getIrProviders() {
        return this.components.getIrProviders();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrLock getLock() {
        return this.components.getLock();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public ScopeSession getScopeSession() {
        return this.components.getScopeSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirSession getSession() {
        return this.components.getSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirBasedSignatureComposer getSignatureComposer() {
        return this.components.getSignatureComposer();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public SymbolTable getSymbolTable() {
        return this.components.getSymbolTable();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrTypeConverter getTypeConverter() {
        return this.components.getTypeConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrVisibilityConverter getVisibilityConverter() {
        return this.components.getVisibilityConverter();
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
    public FirProperty getFir() {
        return this.fir;
    }

    public final FirRegularClass getContainingClass() {
        return this.containingClass;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public Fir2IrPropertySymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    /* JADX INFO: renamed from: isFakeOverride, reason: from getter */
    public boolean getIsFakeOverride() {
        return this.isFakeOverride;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setFakeOverride(boolean z) {
        this.isFakeOverride = z;
    }

    public Fir2IrLazyProperty(Fir2IrComponents components, int i, int i2, IrDeclarationOrigin origin, FirProperty fir, FirRegularClass firRegularClass, Fir2IrPropertySymbol symbol, boolean z) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(fir, "fir");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.components = components;
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.fir = fir;
        this.containingClass = firRegularClass;
        this.symbol = symbol;
        this.isFakeOverride = z;
        getSymbol().bind(this);
        getClassifierStorage().preCacheTypeParameters$fir2ir(getFir(), getSymbol());
        this.annotations = createLazyAnnotations();
        this.visibility = components.getVisibilityConverter().convertToDescriptorVisibility(getFir().getStatus().getVisibility());
        this.type = LazyKt.lazy(new Function0<IrType>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyProperty$type$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                return Fir2IrTypeConverter.toIrType$default(this.this$0.getTypeConverter(), this.this$0.getFir().getReturnTypeRef(), null, 1, null);
            }
        });
        this.backingField = LazyUtilKt.lazyVar(getLock(), new Function0<IrField>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyProperty$backingField$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrField invoke() {
                IrField irFieldCreateBackingField$fir2ir;
                List<FirAnnotation> annotations;
                FirExpression initializer;
                Visibility visibility;
                FirTypeRef returnTypeRef;
                ArrayList arrayListEmptyList = null;
                if (DeclarationAttributesKt.getHasExplicitBackingField(this.this$0.getFir())) {
                    Fir2IrDeclarationStorage declarationStorage = this.this$0.getDeclarationStorage();
                    Fir2IrLazyProperty fir2IrLazyProperty = this.this$0;
                    Fir2IrTypeConverter typeConverter = declarationStorage.getTypeConverter();
                    FirBackingField backingField = fir2IrLazyProperty.getFir().getBackingField();
                    IrType irType$default = (backingField == null || (returnTypeRef = backingField.getReturnTypeRef()) == null) ? null : Fir2IrTypeConverter.toIrType$default(typeConverter, returnTypeRef, null, 1, null);
                    FirBackingField backingField2 = fir2IrLazyProperty.getFir().getBackingField();
                    if (backingField2 == null || (initializer = backingField2.getInitializer()) == null) {
                        initializer = fir2IrLazyProperty.getFir().getInitializer();
                    }
                    FirExpression firExpression = initializer;
                    FirBackingField backingField3 = fir2IrLazyProperty.getFir().getBackingField();
                    if (backingField3 == null || (visibility = backingField3.getStatus().getVisibility()) == null) {
                        visibility = fir2IrLazyProperty.getFir().getStatus().getVisibility();
                    }
                    irFieldCreateBackingField$fir2ir = declarationStorage.createBackingField$fir2ir(fir2IrLazyProperty, fir2IrLazyProperty.getFir(), IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE, fir2IrLazyProperty.components.getVisibilityConverter().convertToDescriptorVisibility(visibility), fir2IrLazyProperty.getFir().getName(), fir2IrLazyProperty.getFir().getIsVal(), firExpression, irType$default);
                    irFieldCreateBackingField$fir2ir.setInitializer(fir2IrLazyProperty.toIrInitializer(firExpression));
                } else if (DeclarationAttributesKt.getHasBackingField(this.this$0.getFir()) && !Intrinsics.areEqual(this.this$0.getOrigin(), IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE)) {
                    Fir2IrDeclarationStorage declarationStorage2 = this.this$0.getDeclarationStorage();
                    Fir2IrLazyProperty fir2IrLazyProperty2 = this.this$0;
                    irFieldCreateBackingField$fir2ir = declarationStorage2.createBackingField$fir2ir(fir2IrLazyProperty2, fir2IrLazyProperty2.getFir(), IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE, fir2IrLazyProperty2.components.getVisibilityConverter().convertToDescriptorVisibility(fir2IrLazyProperty2.getFir().getStatus().getVisibility()), fir2IrLazyProperty2.getFir().getName(), fir2IrLazyProperty2.getFir().getIsVal(), fir2IrLazyProperty2.getFir().getInitializer(), fir2IrLazyProperty2.getType());
                    irFieldCreateBackingField$fir2ir.setInitializer(fir2IrLazyProperty2.toIrInitializer(fir2IrLazyProperty2.getFir().getInitializer()));
                } else if (this.this$0.getFir().getDelegate() != null) {
                    Fir2IrDeclarationStorage declarationStorage3 = this.this$0.getDeclarationStorage();
                    Fir2IrLazyProperty fir2IrLazyProperty3 = this.this$0;
                    irFieldCreateBackingField$fir2ir = declarationStorage3.createBackingField$fir2ir(fir2IrLazyProperty3, fir2IrLazyProperty3.getFir(), IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE, fir2IrLazyProperty3.components.getVisibilityConverter().convertToDescriptorVisibility(fir2IrLazyProperty3.getFir().getStatus().getVisibility()), NameUtils.propertyDelegateName(fir2IrLazyProperty3.getFir().getName()), true, fir2IrLazyProperty3.getFir().getDelegate(), (64 & 64) != 0 ? null : null);
                } else {
                    irFieldCreateBackingField$fir2ir = null;
                }
                if (irFieldCreateBackingField$fir2ir == null) {
                    return null;
                }
                Fir2IrLazyProperty fir2IrLazyProperty4 = this.this$0;
                irFieldCreateBackingField$fir2ir.setParent(fir2IrLazyProperty4.getParent());
                FirBackingField backingField4 = fir2IrLazyProperty4.getFir().getBackingField();
                if (backingField4 != null && (annotations = backingField4.getAnnotations()) != null) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<T> it = annotations.iterator();
                    while (it.hasNext()) {
                        IrExpression irExpressionConvertToIrConstructorCall = fir2IrLazyProperty4.getCallGenerator().convertToIrConstructorCall((FirAnnotation) it.next());
                        IrConstructorCall irConstructorCall = irExpressionConvertToIrConstructorCall instanceof IrConstructorCall ? (IrConstructorCall) irExpressionConvertToIrConstructorCall : null;
                        if (irConstructorCall != null) {
                            arrayList.add(irConstructorCall);
                        }
                    }
                    arrayListEmptyList = arrayList;
                }
                if (arrayListEmptyList == null) {
                    arrayListEmptyList = CollectionsKt.emptyList();
                }
                irFieldCreateBackingField$fir2ir.setAnnotations(arrayListEmptyList);
                return irFieldCreateBackingField$fir2ir;
            }
        });
        this.getter = LazyUtilKt.lazyVar(getLock(), new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyProperty$getter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                FirRegularClassSymbol symbol2;
                FirBasedSignatureComposer signatureComposer = this.this$0.getSignatureComposer();
                FirProperty fir2 = this.this$0.getFir();
                FirRegularClass containingClass = this.this$0.getContainingClass();
                final IdSignature idSignatureComposeAccessorSignature = signatureComposer.composeAccessorSignature(fir2, false, (containingClass == null || (symbol2 = containingClass.getSymbol()) == null) ? null : symbol2.getLookupTag());
                Intrinsics.checkNotNull(idSignatureComposeAccessorSignature);
                SymbolTable symbolTable = this.this$0.getSymbolTable();
                Function0<IrSimpleFunctionSymbol> function0 = new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyProperty$getter$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final IrSimpleFunctionSymbol invoke() {
                        return new Fir2IrSimpleFunctionSymbol(idSignatureComposeAccessorSignature);
                    }
                };
                final Fir2IrLazyProperty fir2IrLazyProperty = this.this$0;
                IrSimpleFunction irSimpleFunctionDeclareSimpleFunction = symbolTable.declareSimpleFunction(idSignatureComposeAccessorSignature, function0, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyProperty$getter$2.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrSimpleFunction invoke(IrSimpleFunctionSymbol symbol3) {
                        IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR origin2;
                        Intrinsics.checkNotNullParameter(symbol3, "symbol");
                        Fir2IrComponents fir2IrComponents = fir2IrLazyProperty.components;
                        int startOffset = fir2IrLazyProperty.getStartOffset();
                        int endOffset = fir2IrLazyProperty.getEndOffset();
                        if (Intrinsics.areEqual(fir2IrLazyProperty.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE)) {
                            origin2 = fir2IrLazyProperty.getOrigin();
                        } else if (fir2IrLazyProperty.getFir().getDelegate() != null) {
                            origin2 = IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE;
                        } else {
                            origin2 = (Intrinsics.areEqual(fir2IrLazyProperty.getOrigin(), IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) || Intrinsics.areEqual(fir2IrLazyProperty.getOrigin(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE) || !(fir2IrLazyProperty.getFir().getGetter() instanceof FirDefaultPropertyGetter)) ? fir2IrLazyProperty.getOrigin() : IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE;
                        }
                        return new Fir2IrLazyPropertyAccessor(fir2IrComponents, startOffset, endOffset, origin2, fir2IrLazyProperty.getFir().getGetter(), false, fir2IrLazyProperty.getFir(), fir2IrLazyProperty.getContainingClass(), symbol3, fir2IrLazyProperty.getIsFakeOverride());
                    }
                });
                Fir2IrLazyProperty fir2IrLazyProperty2 = this.this$0;
                irSimpleFunctionDeclareSimpleFunction.setParent(fir2IrLazyProperty2.getParent());
                irSimpleFunctionDeclareSimpleFunction.setCorrespondingPropertySymbol(fir2IrLazyProperty2.getSymbol());
                fir2IrLazyProperty2.getClassifierStorage().setTypeParameters$fir2ir(irSimpleFunctionDeclareSimpleFunction, fir2IrLazyProperty2.getFir(), ConversionTypeOrigin.DEFAULT);
                return irSimpleFunctionDeclareSimpleFunction;
            }
        });
        this.setter = LazyUtilKt.lazyVar(getLock(), new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyProperty$setter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                FirRegularClassSymbol symbol2;
                ConeClassLikeLookupTag lookupTag = null;
                if (!this.this$0.getFir().getIsVar()) {
                    return null;
                }
                FirBasedSignatureComposer signatureComposer = this.this$0.getSignatureComposer();
                FirProperty fir2 = this.this$0.getFir();
                FirRegularClass containingClass = this.this$0.getContainingClass();
                if (containingClass != null && (symbol2 = containingClass.getSymbol()) != null) {
                    lookupTag = symbol2.getLookupTag();
                }
                final IdSignature idSignatureComposeAccessorSignature = signatureComposer.composeAccessorSignature(fir2, true, lookupTag);
                Intrinsics.checkNotNull(idSignatureComposeAccessorSignature);
                SymbolTable symbolTable = this.this$0.getSymbolTable();
                Function0<IrSimpleFunctionSymbol> function0 = new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyProperty$setter$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final IrSimpleFunctionSymbol invoke() {
                        return new Fir2IrSimpleFunctionSymbol(idSignatureComposeAccessorSignature);
                    }
                };
                final Fir2IrLazyProperty fir2IrLazyProperty = this.this$0;
                return symbolTable.declareSimpleFunction(idSignatureComposeAccessorSignature, function0, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyProperty$setter$2.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrSimpleFunction invoke(IrSimpleFunctionSymbol symbol3) {
                        IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR origin2;
                        Intrinsics.checkNotNullParameter(symbol3, "symbol");
                        Fir2IrComponents fir2IrComponents = fir2IrLazyProperty.components;
                        int startOffset = fir2IrLazyProperty.getStartOffset();
                        int endOffset = fir2IrLazyProperty.getEndOffset();
                        if (Intrinsics.areEqual(fir2IrLazyProperty.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE)) {
                            origin2 = fir2IrLazyProperty.getOrigin();
                        } else if (fir2IrLazyProperty.getFir().getDelegate() != null) {
                            origin2 = IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE;
                        } else {
                            origin2 = (Intrinsics.areEqual(fir2IrLazyProperty.getOrigin(), IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) || Intrinsics.areEqual(fir2IrLazyProperty.getOrigin(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE) || !(fir2IrLazyProperty.getFir().getSetter() instanceof FirDefaultPropertySetter)) ? fir2IrLazyProperty.getOrigin() : IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE;
                        }
                        Fir2IrLazyPropertyAccessor fir2IrLazyPropertyAccessor = new Fir2IrLazyPropertyAccessor(fir2IrComponents, startOffset, endOffset, origin2, fir2IrLazyProperty.getFir().getSetter(), true, fir2IrLazyProperty.getFir(), fir2IrLazyProperty.getContainingClass(), symbol3, fir2IrLazyProperty.getIsFakeOverride());
                        Fir2IrLazyProperty fir2IrLazyProperty2 = fir2IrLazyProperty;
                        fir2IrLazyPropertyAccessor.setParent(fir2IrLazyProperty2.getParent());
                        fir2IrLazyPropertyAccessor.setCorrespondingPropertySymbol(fir2IrLazyProperty2.getSymbol());
                        fir2IrLazyPropertyAccessor.getClassifierStorage().setTypeParameters$fir2ir(fir2IrLazyPropertyAccessor, fir2IrLazyProperty2.getFir(), ConversionTypeOrigin.SETTER);
                        return fir2IrLazyPropertyAccessor;
                    }
                });
            }
        });
        this.overriddenSymbols = LazyUtilKt.lazyVar(getLock(), new Function0<List<? extends IrPropertySymbol>>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyProperty$overriddenSymbols$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrPropertySymbol> invoke() {
                if (this.this$0.getContainingClass() == null) {
                    return CollectionsKt.emptyList();
                }
                Fir2IrLazyProperty fir2IrLazyProperty = this.this$0;
                return ConversionUtilsKt.generateOverriddenPropertySymbols(fir2IrLazyProperty, fir2IrLazyProperty.getFir(), this.this$0.getContainingClass());
            }
        });
        this.attributeOwnerId = this;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public PropertyDescriptor getDescriptor() {
        return getSymbol().getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    /* JADX INFO: renamed from: isVar */
    public boolean getIsVar() {
        return getFir().getIsVar();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setVar(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    /* JADX INFO: renamed from: isConst */
    public boolean getIsConst() {
        return getFir().getStatus().isConst();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setConst(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    /* JADX INFO: renamed from: isLateinit */
    public boolean getIsLateinit() {
        return getFir().getStatus().isLateInit();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setLateinit(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    /* JADX INFO: renamed from: isDelegated */
    public boolean getIsDelegated() {
        return getFir().getDelegate() != null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setDelegated(boolean z) {
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    /* JADX INFO: renamed from: isExpect */
    public boolean getIsExpect() {
        return getFir().getStatus().isExpect();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setExpect(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public Name getName() {
        return getFir().getName();
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

    /* JADX INFO: Access modifiers changed from: private */
    public final IrType getType() {
        return (IrType) this.type.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpressionBody toIrInitializer(FirExpression initializer) {
        ClassKind classKind;
        FirRegularClass firRegularClass = this.containingClass;
        if (firRegularClass != null && (classKind = firRegularClass.getClassKind()) != null && classKind == ClassKind.ANNOTATION_CLASS) {
            if (initializer != null) {
                return ConversionUtilsKt.asCompileTimeIrInitializer(initializer, this.components);
            }
            return null;
        }
        if (!(initializer instanceof FirConstExpression)) {
            return null;
        }
        FirConstExpression firConstExpression = (FirConstExpression) initializer;
        return IrFactoryHelpersKt.createExpressionBody(getFactory(), ConversionUtilsKt.toIrConst(firConstExpression, Fir2IrTypeConverter.toIrType$default(getTypeConverter(), firConstExpression.getTypeRef(), null, 1, null)));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public IrField getBackingField() {
        return (IrField) this.backingField.getValue(this, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setBackingField(IrField irField) {
        this.backingField.setValue(this, $$delegatedProperties[1], irField);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public IrSimpleFunction getGetter() {
        return (IrSimpleFunction) this.getter.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setGetter(IrSimpleFunction irSimpleFunction) {
        this.getter.setValue(this, $$delegatedProperties[2], irSimpleFunction);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public IrSimpleFunction getSetter() {
        return (IrSimpleFunction) this.setter.getValue(this, $$delegatedProperties[3]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setSetter(IrSimpleFunction irSimpleFunction) {
        this.setter.setValue(this, $$delegatedProperties[3], irSimpleFunction);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public List<IrPropertySymbol> getOverriddenSymbols() {
        return (List) this.overriddenSymbols.getValue(this, $$delegatedProperties[4]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setOverriddenSymbols(List<? extends IrPropertySymbol> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.overriddenSymbols.setValue(this, $$delegatedProperties[4], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        throw new IllegalStateException("We should never need to store metadata of external declarations.".toString());
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        return getFir().getContainerSource();
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
}
