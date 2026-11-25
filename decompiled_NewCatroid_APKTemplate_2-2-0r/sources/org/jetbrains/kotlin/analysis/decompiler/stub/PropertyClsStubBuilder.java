package org.jetbrains.kotlin.analysis.decompiler.stub;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsKt;
import org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsToModifiers;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinarySourceElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinModifierListStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinNameReferenceExpressionStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPropertyAccessorStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPropertyStubImpl;
import org.jetbrains.kotlin.serialization.deserialization.AnnotatedCallableKind;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: CallableClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u001b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0014J \u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020\u001eH\u0016J\b\u0010'\u001a\u00020\u001eH\u0016J \u0010(\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u0016J\u001a\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0002R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0010R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0018¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/PropertyClsStubBuilder;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/CallableClsStubBuilder;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "outerContext", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "protoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "propertyProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;)V", "contextReceiverTypes", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "getContextReceiverTypes", "()Ljava/util/List;", "isVar", "", "receiverAnnotations", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithTarget;", "getReceiverAnnotations", "receiverType", "getReceiverType", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "returnType", "getReturnType", "calcInitializer", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "createCallableSpecialParts", "", "createModifierListAndAnnotationStubsForAccessor", "accessorStub", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinPropertyAccessorStubImpl;", OverlayablePolicy.NAME_flags, "", "callableKind", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;", "createModifierListStub", "createValueParameterList", "doCreateCallableStub", "getSpecialCaseContainerClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "classFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PropertyClsStubBuilder extends CallableClsStubBuilder {
    private final boolean isVar;
    private final ProtoBuf.Property propertyProto;

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public void createValueParameterList() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public PropertyClsStubBuilder(StubElement<? extends PsiElement> parent, ClsStubBuilderContext outerContext, ProtoContainer protoContainer, ProtoBuf.Property propertyProto) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(protoContainer, "protoContainer");
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        List<ProtoBuf.TypeParameter> typeParameterList = propertyProto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        super(parent, outerContext, protoContainer, typeParameterList);
        this.propertyProto = propertyProto;
        Boolean bool = Flags.IS_VAR.get(propertyProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        this.isVar = bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public ProtoBuf.Type getReceiverType() {
        return ProtoTypeTableUtilKt.receiverType(this.propertyProto, getC().getTypeTable());
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public List<AnnotationWithTarget> getReceiverAnnotations() {
        List<AnnotationWithArgs> listLoadExtensionReceiverParameterAnnotations = getC().getComponents().getAnnotationLoader().loadExtensionReceiverParameterAnnotations(getProtoContainer(), this.propertyProto, AnnotatedCallableKind.PROPERTY_GETTER);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listLoadExtensionReceiverParameterAnnotations, 10));
        Iterator<T> it = listLoadExtensionReceiverParameterAnnotations.iterator();
        while (it.hasNext()) {
            arrayList.add(new AnnotationWithTarget((AnnotationWithArgs) it.next(), AnnotationUseSiteTarget.RECEIVER));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public ProtoBuf.Type getReturnType() {
        return ProtoTypeTableUtilKt.returnType(this.propertyProto, getC().getTypeTable());
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public List<ProtoBuf.Type> getContextReceiverTypes() {
        return ProtoTypeTableUtilKt.contextReceiverTypes(this.propertyProto, getC().getTypeTable());
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public void createModifierListStub() {
        KotlinModifierListStubImpl kotlinModifierListStubImplCreateModifierListStubForDeclaration$default = ClsStubBuildingKt.createModifierListStubForDeclaration$default(getCallableStub(), this.propertyProto.getFlags(), CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.listOf((Object[]) new FlagsToModifiers[]{FlagsKt.getVISIBILITY(), FlagsKt.getLATEINIT(), FlagsKt.getEXTERNAL_PROPERTY(), FlagsKt.getEXPECT_PROPERTY()}), (Iterable) (this.isVar ? CollectionsKt.emptyList() : CollectionsKt.listOf(FlagsKt.getCONST()))), (Iterable) (isTopLevel() ? CollectionsKt.emptyList() : CollectionsKt.listOf(FlagsKt.getMODALITY()))), null, 8, null);
        if (Flags.HAS_ANNOTATIONS.get(this.propertyProto.getFlags()).booleanValue()) {
            List<AnnotationWithArgs> listLoadCallableAnnotations = getC().getComponents().getAnnotationLoader().loadCallableAnnotations(getProtoContainer(), this.propertyProto, AnnotatedCallableKind.PROPERTY);
            List<AnnotationWithArgs> listLoadPropertyBackingFieldAnnotations = getC().getComponents().getAnnotationLoader().loadPropertyBackingFieldAnnotations(getProtoContainer(), this.propertyProto);
            List<AnnotationWithArgs> listLoadPropertyDelegateFieldAnnotations = getC().getComponents().getAnnotationLoader().loadPropertyDelegateFieldAnnotations(getProtoContainer(), this.propertyProto);
            List<AnnotationWithArgs> list = listLoadCallableAnnotations;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new AnnotationWithTarget((AnnotationWithArgs) it.next(), null));
            }
            ArrayList arrayList2 = arrayList;
            List<AnnotationWithArgs> list2 = listLoadPropertyBackingFieldAnnotations;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                arrayList3.add(new AnnotationWithTarget((AnnotationWithArgs) it2.next(), AnnotationUseSiteTarget.FIELD));
            }
            List listPlus = CollectionsKt.plus((Collection) arrayList2, (Iterable) arrayList3);
            List<AnnotationWithArgs> list3 = listLoadPropertyDelegateFieldAnnotations;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            Iterator<T> it3 = list3.iterator();
            while (it3.hasNext()) {
                arrayList4.add(new AnnotationWithTarget((AnnotationWithArgs) it3.next(), AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD));
            }
            ClsStubBuildingKt.createTargetedAnnotationStubs(CollectionsKt.plus((Collection) listPlus, (Iterable) arrayList4), kotlinModifierListStubImplCreateModifierListStubForDeclaration$default);
        }
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public StubElement<? extends PsiElement> doCreateCallableStub(StubElement<? extends PsiElement> parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Name name2 = NameResolverUtilKt.getName(getC().getNameResolver(), this.propertyProto.getName());
        ConstantValue<?> constantValueCalcInitializer = calcInitializer();
        return new KotlinPropertyStubImpl(parent, ClsStubBuildingKt.ref(name2), this.isVar, isTopLevel(), false, false, constantValueCalcInitializer != null, ProtoTypeTableUtilKt.hasReceiver(this.propertyProto), true, getC().getContainerFqName().child(name2), constantValueCalcInitializer, ClsStubBuildingKt.createStubOrigin(getProtoContainer()));
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    protected void createCallableSpecialParts() {
        StubElement<? extends PsiElement> callableStub = getCallableStub();
        Intrinsics.checkNotNull(callableStub, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub");
        if (((KotlinPropertyStub) callableStub).getHasInitializer()) {
            new KotlinNameReferenceExpressionStubImpl(getCallableStub(), StringRef.fromString("COMPILED_CODE"));
        }
        int flags = this.propertyProto.getFlags();
        Boolean bool = Flags.HAS_GETTER.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (bool.booleanValue() && this.propertyProto.hasGetterFlags()) {
            int getterFlags = this.propertyProto.getGetterFlags();
            Boolean bool2 = Flags.IS_NOT_DEFAULT.get(getterFlags);
            Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
            if (bool2.booleanValue()) {
                createModifierListAndAnnotationStubsForAccessor(new KotlinPropertyAccessorStubImpl(getCallableStub(), true, false, true), getterFlags, AnnotatedCallableKind.PROPERTY_GETTER);
            }
        }
        Boolean bool3 = Flags.HAS_SETTER.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool3, "get(...)");
        if (bool3.booleanValue() && this.propertyProto.hasSetterFlags()) {
            int setterFlags = this.propertyProto.getSetterFlags();
            Boolean bool4 = Flags.IS_NOT_DEFAULT.get(setterFlags);
            Intrinsics.checkNotNullExpressionValue(bool4, "get(...)");
            if (bool4.booleanValue()) {
                KotlinPropertyAccessorStubImpl kotlinPropertyAccessorStubImpl = new KotlinPropertyAccessorStubImpl(getCallableStub(), false, true, true);
                createModifierListAndAnnotationStubsForAccessor(kotlinPropertyAccessorStubImpl, setterFlags, AnnotatedCallableKind.PROPERTY_SETTER);
                if (this.propertyProto.hasSetterValueParameter()) {
                    ProtoBuf.Property property = this.propertyProto;
                    getTypeStubBuilder().createValueParameterListStub(kotlinPropertyAccessorStubImpl, property, CollectionsKt.listOf(property.getSetterValueParameter()), getProtoContainer(), AnnotatedCallableKind.PROPERTY_SETTER);
                }
            }
        }
    }

    private final void createModifierListAndAnnotationStubsForAccessor(KotlinPropertyAccessorStubImpl accessorStub, int flags, AnnotatedCallableKind callableKind) {
        KotlinModifierListStubImpl kotlinModifierListStubImplCreateModifierListStubForDeclaration$default = ClsStubBuildingKt.createModifierListStubForDeclaration$default(accessorStub, flags, CollectionsKt.listOf((Object[]) new FlagsToModifiers[]{FlagsKt.getVISIBILITY(), FlagsKt.getMODALITY(), FlagsKt.getINLINE_ACCESSOR(), FlagsKt.getEXTERNAL_ACCESSOR()}), null, 8, null);
        Boolean bool = Flags.HAS_ANNOTATIONS.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (bool.booleanValue()) {
            ClsStubBuildingKt.createAnnotationStubs(getC().getComponents().getAnnotationLoader().loadCallableAnnotations(getProtoContainer(), this.propertyProto, callableKind), kotlinModifierListStubImplCreateModifierListStubForDeclaration$default);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [T, org.jetbrains.kotlin.constant.ConstantValue] */
    private final ConstantValue<?> calcInitializer() {
        KotlinJvmBinaryClass binaryClass;
        KotlinClassFinder classFinder = getC().getComponents().getClassFinder();
        if (classFinder != null) {
            JvmMetadataVersion jvmMetadataVersion = getC().getComponents().getJvmMetadataVersion();
            Intrinsics.checkNotNull(jvmMetadataVersion);
            binaryClass = getSpecialCaseContainerClass(classFinder, jvmMetadataVersion);
        } else {
            binaryClass = null;
        }
        SourceElement source = getProtoContainer().getSource();
        if (binaryClass == null) {
            KotlinJvmBinarySourceElement kotlinJvmBinarySourceElement = source instanceof KotlinJvmBinarySourceElement ? (KotlinJvmBinarySourceElement) source : null;
            binaryClass = kotlinJvmBinarySourceElement != null ? kotlinJvmBinarySourceElement.getBinaryClass() : null;
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        if (binaryClass != null) {
            final Name name2 = NameResolverUtilKt.getName(getC().getNameResolver(), this.propertyProto.getName());
            binaryClass.visitMembers(new KotlinJvmBinaryClass.MemberVisitor() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.PropertyClsStubBuilder.calcInitializer.1
                private final Lazy<Name> getterName;

                {
                    this.getterName = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Name>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.PropertyClsStubBuilder$calcInitializer$1$getterName$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Name invoke() {
                            ProtoBuf.Property property = propertyClsStubBuilder.propertyProto;
                            GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, JvmProtoBuf.JvmPropertySignature> propertySignature = JvmProtoBuf.propertySignature;
                            Intrinsics.checkNotNullExpressionValue(propertySignature, "propertySignature");
                            JvmProtoBuf.JvmPropertySignature jvmPropertySignature = (JvmProtoBuf.JvmPropertySignature) ProtoBufUtilKt.getExtensionOrNull(property, propertySignature);
                            if (jvmPropertySignature == null) {
                                return null;
                            }
                            return NameResolverUtilKt.getName(propertyClsStubBuilder.getC().getNameResolver(), jvmPropertySignature.getGetter().getName());
                        }
                    });
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MemberVisitor
                public KotlinJvmBinaryClass.MethodAnnotationVisitor visitMethod(Name name3, String desc) {
                    Intrinsics.checkNotNullParameter(name3, "name");
                    Intrinsics.checkNotNullParameter(desc, "desc");
                    if (!(PropertyClsStubBuilder.this.getProtoContainer() instanceof ProtoContainer.Class) || ((ProtoContainer.Class) PropertyClsStubBuilder.this.getProtoContainer()).getKind() != ProtoBuf.Class.Kind.ANNOTATION_CLASS || !Intrinsics.areEqual(this.getterName.getValue(), name3)) {
                        return null;
                    }
                    final Ref.ObjectRef<ConstantValue<?>> objectRef2 = objectRef;
                    return new KotlinJvmBinaryClass.MethodAnnotationVisitor() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.PropertyClsStubBuilder$calcInitializer$1$visitMethod$1
                        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
                        public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(ClassId classId, SourceElement source2) {
                            Intrinsics.checkNotNullParameter(classId, "classId");
                            Intrinsics.checkNotNullParameter(source2, "source");
                            return null;
                        }

                        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
                        public void visitEnd() {
                        }

                        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MethodAnnotationVisitor
                        public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitParameterAnnotation(int index, ClassId classId, SourceElement source2) {
                            Intrinsics.checkNotNullParameter(classId, "classId");
                            Intrinsics.checkNotNullParameter(source2, "source");
                            return null;
                        }

                        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MethodAnnotationVisitor
                        public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotationMemberDefaultValue() {
                            final Ref.ObjectRef<ConstantValue<?>> objectRef3 = objectRef2;
                            return new AnnotationMemberDefaultValueVisitor() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.PropertyClsStubBuilder$calcInitializer$1$visitMethod$1$visitAnnotationMemberDefaultValue$1
                                /* JADX WARN: Type inference failed for: r1v3, types: [T, java.lang.Object] */
                                @Override // org.jetbrains.kotlin.analysis.decompiler.stub.AnnotationMemberDefaultValueVisitor, org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                                public void visitEnd() {
                                    objectRef3.element = CollectionsKt.firstOrNull(getArgs().values());
                                }
                            };
                        }
                    };
                }

                /* JADX WARN: Type inference failed for: r3v2, types: [T, org.jetbrains.kotlin.constant.ConstantValue] */
                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MemberVisitor
                public KotlinJvmBinaryClass.AnnotationVisitor visitField(Name name3, String desc, Object initializer) {
                    Intrinsics.checkNotNullParameter(name3, "name");
                    Intrinsics.checkNotNullParameter(desc, "desc");
                    if (initializer == null || !Intrinsics.areEqual(name3, name2)) {
                        return null;
                    }
                    objectRef.element = KotlinConstantValueKt.createConstantValue(initializer);
                    return null;
                }
            }, null);
        } else {
            ProtoBuf.Annotation.Argument.Value value = (ProtoBuf.Annotation.Argument.Value) ProtoBufUtilKt.getExtensionOrNull(this.propertyProto, getC().getComponents().getSerializationProtocol().getCompileTimeValue());
            if (value != null) {
                objectRef.element = KotlinConstantValueKt.createConstantValue(value, getC().getNameResolver());
            }
        }
        return (ConstantValue) objectRef.element;
    }

    private final KotlinJvmBinaryClass getSpecialCaseContainerClass(KotlinClassFinder classFinder, JvmMetadataVersion jvmMetadataVersion) {
        return AbstractBinaryClassAnnotationLoader.INSTANCE.getSpecialCaseContainerClass(getProtoContainer(), true, true, Flags.IS_CONST.get(this.propertyProto.getFlags()), JvmProtoBufUtil.isMovedFromInterfaceCompanion(this.propertyProto), classFinder, jvmMetadataVersion);
    }
}
