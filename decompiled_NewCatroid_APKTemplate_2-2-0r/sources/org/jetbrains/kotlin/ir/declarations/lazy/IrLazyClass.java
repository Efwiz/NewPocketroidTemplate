package org.jetbrains.kotlin.ir.declarations.lazy;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.DeserializableClass;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationContext;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrLazyClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ø\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0095\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\u0018\u001a\u00020\u0016\u0012\u0006\u0010\u0019\u001a\u00020\u0016\u0012\u0006\u0010\u001a\u001a\u00020\u0016\u0012\u0006\u0010\u001b\u001a\u00020\u0016\u0012\u0006\u0010\u001c\u001a\u00020\u0016\u0012\u0006\u0010\u001d\u001a\u00020\u001e\u0012\u0006\u0010\u001f\u001a\u00020 ¢\u0006\u0002\u0010!J)\u0010\u009b\u0001\u001a\u00030\u009c\u00012\u000f\u0010\u009d\u0001\u001a\n\u0012\u0005\u0012\u00030\u009f\u00010\u009e\u00012\f\u00106\u001a\b\u0012\u0004\u0012\u00020807H\u0002J\t\u0010 \u0001\u001a\u00020\u0016H\u0016J\u0012\u0010¡\u0001\u001a\u00020\u00162\u0007\u0010\u000b\u001a\u00030\u009f\u0001H\u0002R7\u0010%\u001a\b\u0012\u0004\u0012\u00020$0#2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b*\u0010+\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010,\u001a\u00020-X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u0013\u00102\u001a\u0004\u0018\u0001038F¢\u0006\u0006\u001a\u0004\b4\u00105R!\u00106\u001a\b\u0012\u0004\u0012\u000208078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b:\u0010+\u001a\u0004\b9\u0010'R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0014\u0010\u0006\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0012\u0010?\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0004\n\u0002\u0010@R\u001a\u0010\u0015\u001a\u00020\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010A\"\u0004\bB\u0010CR\u001a\u0010\u0018\u001a\u00020\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010A\"\u0004\bD\u0010CR\u001a\u0010\u001b\u001a\u00020\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010A\"\u0004\bE\u0010CR\u001a\u0010\u0019\u001a\u00020\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010A\"\u0004\bF\u0010CR\u001a\u0010\u001c\u001a\u00020\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010A\"\u0004\bG\u0010CR\u001a\u0010\u0017\u001a\u00020\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010A\"\u0004\bH\u0010CR\u001a\u0010\u001a\u001a\u00020\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010A\"\u0004\bI\u0010CR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR(\u0010P\u001a\u0004\u0018\u00010O2\b\u0010N\u001a\u0004\u0018\u00010O8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u001a\u0010\u0013\u001a\u00020\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010V\"\u0004\bW\u0010XR\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u0013\u0010]\u001a\u0004\u0018\u00010^8F¢\u0006\u0006\u001a\u0004\b_\u0010`R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR\u001c\u0010e\u001a\u0004\u0018\u00010-X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bf\u0010/\"\u0004\bg\u00101R+\u0010i\u001a\u00020h2\u0006\u0010\"\u001a\u00020h8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bn\u0010+\u001a\u0004\bj\u0010k\"\u0004\bl\u0010mR7\u0010o\u001a\b\u0012\u0004\u0012\u00020\n0#2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0#8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\br\u0010+\u001a\u0004\bp\u0010'\"\u0004\bq\u0010)R\u0014\u0010s\u001a\u00020t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bu\u0010vR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bw\u0010>R\u0014\u0010\u001d\u001a\u00020\u001eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bx\u0010yR7\u0010{\u001a\b\u0012\u0004\u0012\u00020z0#2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020z0#8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b~\u0010+\u001a\u0004\b|\u0010'\"\u0004\b}\u0010)R\u0015\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u007f\u0010\u0080\u0001R7\u0010\u0082\u0001\u001a\u0005\u0018\u00010\u0081\u00012\t\u0010\"\u001a\u0005\u0018\u00010\u0081\u00018V@VX\u0096\u008e\u0002¢\u0006\u0017\n\u0005\b\u0087\u0001\u0010+\u001a\u0006\b\u0083\u0001\u0010\u0084\u0001\"\u0006\b\u0085\u0001\u0010\u0086\u0001R=\u0010\u0089\u0001\u001a\t\u0012\u0005\u0012\u00030\u0088\u00010#2\r\u0010\"\u001a\t\u0012\u0005\u0012\u00030\u0088\u00010#8V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u008c\u0001\u0010+\u001a\u0005\b\u008a\u0001\u0010'\"\u0005\b\u008b\u0001\u0010)R\u0016\u0010\u001f\u001a\u00020 X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u008d\u0001\u0010\u008e\u0001RE\u0010\u0091\u0001\u001a\f\u0012\u0005\u0012\u00030\u0090\u0001\u0018\u00010\u008f\u00012\u0010\u0010\"\u001a\f\u0012\u0005\u0012\u00030\u0090\u0001\u0018\u00010\u008f\u00018V@VX\u0096\u008e\u0002¢\u0006\u0017\n\u0005\b\u0096\u0001\u0010+\u001a\u0006\b\u0092\u0001\u0010\u0093\u0001\"\u0006\b\u0094\u0001\u0010\u0095\u0001R\u001e\u0010\u0011\u001a\u00020\u0012X\u0096\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0097\u0001\u0010\u0098\u0001\"\u0006\b\u0099\u0001\u0010\u009a\u0001¨\u0006¢\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyDeclarationBase;", "Lorg/jetbrains/kotlin/ir/util/DeserializableClass;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isCompanion", "", "isInner", "isData", "isExternal", "isValue", "isExpect", "isFun", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/ClassKind;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/descriptors/Modality;ZZZZZZZLorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;)V", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "attributeOwnerId", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "getAttributeOwnerId", "()Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "setAttributeOwnerId", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)V", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "getClassProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "declarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getDeclarations", "declarations$delegate", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getEndOffset", "()I", "irLoaded", "Ljava/lang/Boolean;", "()Z", "setCompanion", "(Z)V", "setData", "setExpect", "setExternal", "setFun", "setInner", "setValue", "getKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "setKind", "(Lorg/jetbrains/kotlin/descriptors/ClassKind;)V", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "metadata", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "originalBeforeInline", "getOriginalBeforeInline", "setOriginalBeforeInline", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "parent", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "parent$delegate", "sealedSubclasses", "getSealedSubclasses", "setSealedSubclasses", "sealedSubclasses$delegate", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getStartOffset", "getStubGenerator", "()Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "superTypes", "getSuperTypes", "setSuperTypes", "superTypes$delegate", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "thisReceiver", "getThisReceiver", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setThisReceiver", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "thisReceiver$delegate", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "typeParameters", "getTypeParameters", "setTypeParameters", "typeParameters$delegate", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "valueClassRepresentation", "getValueClassRepresentation", "()Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "setValueClassRepresentation", "(Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;)V", "valueClassRepresentation$delegate", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "generateChildStubs", "", "descriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "loadIr", "shouldBuildStub", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrLazyClass extends IrClass implements IrLazyDeclarationBase, DeserializableClass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyClass.class), "parent", "getParent()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyClass.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyClass.class), "thisReceiver", "getThisReceiver()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyClass.class), "declarations", "getDeclarations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyClass.class), "typeParameters", "getTypeParameters()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyClass.class), "superTypes", "getSuperTypes()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyClass.class), "sealedSubclasses", "getSealedSubclasses()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyClass.class), "valueClassRepresentation", "getValueClassRepresentation()Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;"))};

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;
    private IrAttributeContainer attributeOwnerId;

    /* JADX INFO: renamed from: declarations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty declarations;
    private final ClassDescriptor descriptor;
    private final int endOffset;
    private Boolean irLoaded;
    private boolean isCompanion;
    private boolean isData;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isFun;
    private boolean isInner;
    private boolean isValue;
    private ClassKind kind;
    private Modality modality;
    private Name name;
    private IrDeclarationOrigin origin;
    private IrAttributeContainer originalBeforeInline;

    /* JADX INFO: renamed from: parent$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty parent;

    /* JADX INFO: renamed from: sealedSubclasses$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty sealedSubclasses;
    private final int startOffset;
    private final DeclarationStubGenerator stubGenerator;

    /* JADX INFO: renamed from: superTypes$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty superTypes;
    private final IrClassSymbol symbol;

    /* JADX INFO: renamed from: thisReceiver$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty thisReceiver;

    /* JADX INFO: renamed from: typeParameters$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty typeParameters;
    private final TypeTranslator typeTranslator;

    /* JADX INFO: renamed from: valueClassRepresentation$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty valueClassRepresentation;
    private DescriptorVisibility visibility;

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, List<IrConstructorCall>> createLazyAnnotations() {
        return IrLazyDeclarationBase.DefaultImpls.createLazyAnnotations(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, IrDeclarationParent> createLazyParent() {
        return IrLazyDeclarationBase.DefaultImpls.createLazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrValueParameter generateReceiverParameterStub(ReceiverParameterDescriptor receiverParameterDescriptor) {
        return IrLazyDeclarationBase.DefaultImpls.generateReceiverParameterStub(this, receiverParameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return IrLazyDeclarationBase.DefaultImpls.getFactory(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrDeclarationParent lazyParent() {
        return IrLazyDeclarationBase.DefaultImpls.lazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrType toIrType(KotlinType kotlinType) {
        return IrLazyDeclarationBase.DefaultImpls.toIrType(this, kotlinType);
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
    public IrClassSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public ClassDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public ClassKind getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setKind(ClassKind classKind) {
        Intrinsics.checkNotNullParameter(classKind, "<set-?>");
        this.kind = classKind;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public DescriptorVisibility getVisibility() {
        return this.visibility;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public void setVisibility(DescriptorVisibility descriptorVisibility) {
        Intrinsics.checkNotNullParameter(descriptorVisibility, "<set-?>");
        this.visibility = descriptorVisibility;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public Modality getModality() {
        return this.modality;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setModality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "<set-?>");
        this.modality = modality;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isCompanion, reason: from getter */
    public boolean getIsCompanion() {
        return this.isCompanion;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setCompanion(boolean z) {
        this.isCompanion = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isInner, reason: from getter */
    public boolean getIsInner() {
        return this.isInner;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setInner(boolean z) {
        this.isInner = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isData, reason: from getter */
    public boolean getIsData() {
        return this.isData;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setData(boolean z) {
        this.isData = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration
    /* JADX INFO: renamed from: isExternal, reason: from getter */
    public boolean getIsExternal() {
        return this.isExternal;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration
    public void setExternal(boolean z) {
        this.isExternal = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isValue, reason: from getter */
    public boolean getIsValue() {
        return this.isValue;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setValue(boolean z) {
        this.isValue = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isExpect, reason: from getter */
    public boolean getIsExpect() {
        return this.isExpect;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setExpect(boolean z) {
        this.isExpect = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isFun, reason: from getter */
    public boolean getIsFun() {
        return this.isFun;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setFun(boolean z) {
        this.isFun = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public DeclarationStubGenerator getStubGenerator() {
        return this.stubGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public TypeTranslator getTypeTranslator() {
        return this.typeTranslator;
    }

    public IrLazyClass(int i, int i2, IrDeclarationOrigin origin, IrClassSymbol symbol, ClassDescriptor descriptor, Name name2, ClassKind kind, DescriptorVisibility visibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, DeclarationStubGenerator stubGenerator, TypeTranslator typeTranslator) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.descriptor = descriptor;
        this.name = name2;
        this.kind = kind;
        this.visibility = visibility;
        this.modality = modality;
        this.isCompanion = z;
        this.isInner = z2;
        this.isData = z3;
        this.isExternal = z4;
        this.isValue = z5;
        this.isExpect = z6;
        this.isFun = z7;
        this.stubGenerator = stubGenerator;
        this.typeTranslator = typeTranslator;
        getSymbol().bind(this);
        this.parent = createLazyParent();
        this.annotations = createLazyAnnotations();
        this.thisReceiver = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass$thisReceiver$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                TypeTranslator typeTranslator2 = this.this$0.getTypeTranslator();
                IrLazyClass irLazyClass = this.this$0;
                final IrLazyClass irLazyClass2 = this.this$0;
                return (IrValueParameter) typeTranslator2.buildWithScope(irLazyClass, new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass$thisReceiver$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final IrValueParameter invoke() {
                        IrLazyClass irLazyClass3 = irLazyClass2;
                        ReceiverParameterDescriptor thisAsReceiverParameter = irLazyClass3.getDescriptor().getThisAsReceiverParameter();
                        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
                        IrValueParameter irValueParameterGenerateReceiverParameterStub = irLazyClass3.generateReceiverParameterStub(thisAsReceiverParameter);
                        irValueParameterGenerateReceiverParameterStub.setParent(irLazyClass2);
                        return irValueParameterGenerateReceiverParameterStub;
                    }
                });
            }
        });
        this.declarations = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<ArrayList<IrDeclaration>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass$declarations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ArrayList<IrDeclaration> invoke() {
                final ArrayList<IrDeclaration> arrayList = new ArrayList<>();
                final IrLazyClass irLazyClass = this.this$0;
                irLazyClass.getTypeTranslator().buildWithScope(irLazyClass, new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass$declarations$2$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        IrLazyClass irLazyClass2 = irLazyClass;
                        Collection<ClassConstructorDescriptor> constructors = irLazyClass2.getDescriptor().getConstructors();
                        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
                        irLazyClass2.generateChildStubs(constructors, arrayList);
                        IrLazyClass irLazyClass3 = irLazyClass;
                        irLazyClass3.generateChildStubs(ResolutionScope.DefaultImpls.getContributedDescriptors$default(irLazyClass3.getDescriptor().getDefaultType().getMemberScope(), null, null, 3, null), arrayList);
                        IrLazyClass irLazyClass4 = irLazyClass;
                        MemberScope staticScope = irLazyClass4.getDescriptor().getStaticScope();
                        Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
                        irLazyClass4.generateChildStubs(ResolutionScope.DefaultImpls.getContributedDescriptors$default(staticScope, null, null, 3, null), arrayList);
                    }
                });
                ArrayList<IrDeclaration> arrayList2 = arrayList;
                IrLazyClass irLazyClass2 = this.this$0;
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    ((IrDeclaration) it.next()).setParent(irLazyClass2);
                }
                return arrayList2;
            }
        });
        this.typeParameters = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<List<? extends IrTypeParameter>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass$typeParameters$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrTypeParameter> invoke() {
                List<TypeParameterDescriptor> declaredTypeParameters = this.this$0.getDescriptor().getDeclaredTypeParameters();
                Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
                ArrayList arrayList = new ArrayList();
                IrLazyClass irLazyClass = this.this$0;
                for (TypeParameterDescriptor typeParameterDescriptor : declaredTypeParameters) {
                    DeclarationStubGenerator stubGenerator2 = irLazyClass.getStubGenerator();
                    Intrinsics.checkNotNull(typeParameterDescriptor);
                    arrayList.add(stubGenerator2.generateOrGetTypeParameterStub$ir_tree(typeParameterDescriptor));
                }
                return arrayList;
            }
        });
        this.superTypes = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<List<? extends IrType>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass$superTypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrType> invoke() {
                TypeTranslator typeTranslator2 = this.this$0.getTypeTranslator();
                IrLazyClass irLazyClass = this.this$0;
                final IrLazyClass irLazyClass2 = this.this$0;
                return (List) typeTranslator2.buildWithScope(irLazyClass, new Function0<ArrayList<IrType>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass$superTypes$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final ArrayList<IrType> invoke() {
                        Collection<KotlinType> collectionMo7438getSupertypes = irLazyClass2.getDescriptor().getTypeConstructor().mo7438getSupertypes();
                        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
                        ArrayList<IrType> arrayList = new ArrayList<>();
                        IrLazyClass irLazyClass3 = irLazyClass2;
                        for (KotlinType kotlinType : collectionMo7438getSupertypes) {
                            Intrinsics.checkNotNull(kotlinType);
                            IrType irType = irLazyClass3.toIrType(kotlinType);
                            if (irType != null) {
                                arrayList.add(irType);
                            }
                        }
                        return arrayList;
                    }
                });
            }
        });
        this.sealedSubclasses = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<List<? extends IrClassSymbol>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass$sealedSubclasses$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrClassSymbol> invoke() {
                Collection<ClassDescriptor> sealedSubclasses = this.this$0.getDescriptor().getSealedSubclasses();
                Intrinsics.checkNotNullExpressionValue(sealedSubclasses, "getSealedSubclasses(...)");
                Collection<ClassDescriptor> collection = sealedSubclasses;
                IrLazyClass irLazyClass = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
                for (ClassDescriptor classDescriptor : collection) {
                    DeclarationStubGenerator stubGenerator2 = irLazyClass.getStubGenerator();
                    Intrinsics.checkNotNull(classDescriptor);
                    arrayList.add(stubGenerator2.generateClassStub(classDescriptor).getSymbol());
                }
                return arrayList;
            }
        });
        this.valueClassRepresentation = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<ValueClassRepresentation<IrSimpleType>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass$valueClassRepresentation$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ValueClassRepresentation<IrSimpleType> invoke() {
                ValueClassRepresentation<SimpleType> valueClassRepresentation = this.this$0.getDescriptor().getValueClassRepresentation();
                if (valueClassRepresentation == null) {
                    return null;
                }
                final IrLazyClass irLazyClass = this.this$0;
                return valueClassRepresentation.mapUnderlyingType(new Function1<SimpleType, IrSimpleType>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass$valueClassRepresentation$2.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrSimpleType invoke(SimpleType simpleType) {
                        IrLazyClass irLazyClass2 = irLazyClass;
                        Intrinsics.checkNotNull(simpleType);
                        IrType irType = irLazyClass2.toIrType(simpleType);
                        IrSimpleType irSimpleType = irType instanceof IrSimpleType ? (IrSimpleType) irType : null;
                        if (irSimpleType != null) {
                            return irSimpleType;
                        }
                        throw new IllegalStateException(("Value class underlying type is not a simple type: " + RenderIrElementKt.render$default(irLazyClass, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                });
            }
        });
        this.attributeOwnerId = this;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationParent getParent() {
        return (IrDeclarationParent) this.parent.getValue(this, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setParent(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<set-?>");
        this.parent.setValue(this, $$delegatedProperties[0], irDeclarationParent);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return (List) this.annotations.getValue(this, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations.setValue(this, $$delegatedProperties[1], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public IrValueParameter getThisReceiver() {
        return (IrValueParameter) this.thisReceiver.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setThisReceiver(IrValueParameter irValueParameter) {
        this.thisReceiver.setValue(this, $$delegatedProperties[2], irValueParameter);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer
    public List<IrDeclaration> getDeclarations() {
        return (List) this.declarations.getValue(this, $$delegatedProperties[3]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void generateChildStubs(Collection<? extends DeclarationDescriptor> descriptors, List<IrDeclaration> declarations) {
        for (DeclarationDescriptor declarationDescriptor : descriptors) {
            IrDeclaration irDeclarationGenerateMemberStub = shouldBuildStub(declarationDescriptor) ? getStubGenerator().generateMemberStub(declarationDescriptor) : null;
            if (irDeclarationGenerateMemberStub != null) {
                declarations.add(irDeclarationGenerateMemberStub);
            }
        }
    }

    private final boolean shouldBuildStub(DeclarationDescriptor descriptor) {
        if (descriptor instanceof DeclarationDescriptorWithVisibility) {
            DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility = (DeclarationDescriptorWithVisibility) descriptor;
            if ((DescriptorVisibilities.isPrivate(declarationDescriptorWithVisibility.getVisibility()) || Intrinsics.areEqual(declarationDescriptorWithVisibility.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE)) && (!IrUtilsKt.isObject(this) || !(descriptor instanceof ClassConstructorDescriptor))) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public List<IrTypeParameter> getTypeParameters() {
        return (List) this.typeParameters.getValue(this, $$delegatedProperties[4]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public void setTypeParameters(List<? extends IrTypeParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.typeParameters.setValue(this, $$delegatedProperties[4], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public List<IrType> getSuperTypes() {
        return (List) this.superTypes.getValue(this, $$delegatedProperties[5]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setSuperTypes(List<? extends IrType> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.superTypes.setValue(this, $$delegatedProperties[5], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public List<IrClassSymbol> getSealedSubclasses() {
        return (List) this.sealedSubclasses.getValue(this, $$delegatedProperties[6]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setSealedSubclasses(List<? extends IrClassSymbol> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.sealedSubclasses.setValue(this, $$delegatedProperties[6], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public ValueClassRepresentation<IrSimpleType> getValueClassRepresentation() {
        return (ValueClassRepresentation) this.valueClassRepresentation.getValue(this, $$delegatedProperties[7]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setValueClassRepresentation(ValueClassRepresentation<IrSimpleType> valueClassRepresentation) {
        this.valueClassRepresentation.setValue(this, $$delegatedProperties[7], valueClassRepresentation);
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

    public final ProtoBuf.Class getClassProto() {
        ClassDescriptor descriptor = getDescriptor();
        DeserializedClassDescriptor deserializedClassDescriptor = descriptor instanceof DeserializedClassDescriptor ? (DeserializedClassDescriptor) descriptor : null;
        if (deserializedClassDescriptor != null) {
            return deserializedClassDescriptor.getClassProto();
        }
        return null;
    }

    public final NameResolver getNameResolver() {
        DeserializationContext c;
        ClassDescriptor descriptor = getDescriptor();
        DeserializedClassDescriptor deserializedClassDescriptor = descriptor instanceof DeserializedClassDescriptor ? (DeserializedClassDescriptor) descriptor : null;
        if (deserializedClassDescriptor == null || (c = deserializedClassDescriptor.getC()) == null) {
            return null;
        }
        return c.getNameResolver();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public SourceElement getSource() {
        SourceElement source = getDescriptor().getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        return source;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        throw new IllegalStateException("We should never need to store metadata of external declarations.".toString());
    }

    @Override // org.jetbrains.kotlin.ir.util.DeserializableClass
    public boolean loadIr() {
        boolean z = getParent() instanceof IrPackageFragment;
        Boolean bool = this.irLoaded;
        if (bool != null) {
            return bool.booleanValue();
        }
        boolean zDeserializeClass = getStubGenerator().getExtensions().deserializeClass(this, getStubGenerator(), getParent());
        this.irLoaded = Boolean.valueOf(zDeserializeClass);
        return zDeserializeClass;
    }
}
