package org.jetbrains.kotlin.analysis.decompiler.stub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.constant.StringValue;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.psi.KtProjectionKind;
import org.jetbrains.kotlin.psi.stubs.KotlinUserTypeStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinClassTypeBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinFlexibleTypeBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinModifierListStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinNameReferenceExpressionStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinParameterStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPlaceHolderStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinStubBaseImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeArgumentBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeParameterStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeParameterTypeBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeProjectionStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinUserTypeStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.ModifierMaskUtils;
import org.jetbrains.kotlin.serialization.deserialization.AnnotatedCallableKind;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.utils.FunctionsKt;

/* JADX INFO: compiled from: TypeClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J&\u0010\u000e\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J8\u0010\u0013\u001a\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u00142\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u00122\b\u0010\t\u001a\u0004\u0018\u00010\nJ*\u0010\u001c\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\u001e\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\fH\u0002J0\u0010\"\u001a\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u00142\u0018\u0010#\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\n0$0\fJ6\u0010&\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\n0$0\f2\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u00142\f\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\fJ\u0018\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020(H\u0002J2\u0010-\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010.\u001a\u00020%2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002JP\u0010/\u001a\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u00142\u0006\u0010\t\u001a\u00020\n2\u0014\b\u0002\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f012\u001a\b\u0002\u00102\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002040\f03J>\u00105\u001a\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u00142\u0006\u00106\u001a\u0002072\f\u00108\u001a\b\u0012\u0004\u0012\u0002090\f2\u0006\u0010:\u001a\u00020;2\b\b\u0002\u0010<\u001a\u00020=J\u0016\u0010>\u001a\b\u0012\u0004\u0012\u00020@0?2\u0006\u0010\t\u001a\u00020\nH\u0002J \u0010A\u001a\u0006\u0012\u0002\b\u00030\b2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\f\u0010B\u001a\u00020\u0017*\u00020\nH\u0002J\f\u0010C\u001a\u00020D*\u00020EH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeClsStubBuilder;", "", "c", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "(Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;)V", "createClassReferenceTypeStub", "", "parent", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "annotations", "", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithTarget;", "createDefinitelyNotNullTypeStub", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "upperBoundType", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "createFunctionTypeStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isExtensionFunctionType", "", "isSuspend", "numContextReceivers", "", "createKotlinTypeBean", "createTypeAnnotationStubs", "createTypeArgumentListStub", "typeStub", "Lorg/jetbrains/kotlin/psi/stubs/KotlinUserTypeStub;", "typeArgumentProtoList", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument;", "createTypeConstraintListStub", "protosForTypeConstraintList", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/Name;", "createTypeParameterListStub", "typeParameterProtoList", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "createTypeParameterModifierListStub", "typeParameterStub", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeParameterStubImpl;", "typeParameterProto", "createTypeParameterStub", "name", "createTypeReferenceStub", "additionalAnnotations", "Lkotlin/Function0;", "loadTypeAnnotations", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithArgs;", "createValueParameterListStub", "callableProto", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "parameters", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "container", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "callableKind", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;", "getTypeModifiersAsWritten", "", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "nullableTypeParent", "isDefaultUpperBound", "toProjectionKind", "Lorg/jetbrains/kotlin/psi/KtProjectionKind;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument$Projection;", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeClsStubBuilder {
    private final ClsStubBuilderContext c;

    /* JADX INFO: compiled from: TypeClsStubBuilder.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ProtoBuf.Type.Argument.Projection.values().length];
            try {
                iArr[ProtoBuf.Type.Argument.Projection.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.Type.Argument.Projection.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.Type.Argument.Projection.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProtoBuf.Type.Argument.Projection.STAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProtoBuf.TypeParameter.Variance.values().length];
            try {
                iArr2[ProtoBuf.TypeParameter.Variance.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ProtoBuf.TypeParameter.Variance.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ProtoBuf.TypeParameter.Variance.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public TypeClsStubBuilder(ClsStubBuilderContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.c = c;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void createTypeReferenceStub$default(TypeClsStubBuilder typeClsStubBuilder, StubElement stubElement, ProtoBuf.Type type, Function0 function0, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function0 = new Function0<List<? extends AnnotationWithTarget>>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.TypeClsStubBuilder.createTypeReferenceStub.1
                @Override // kotlin.jvm.functions.Function0
                public final List<? extends AnnotationWithTarget> invoke() {
                    return CollectionsKt.emptyList();
                }
            };
        }
        if ((i & 8) != 0) {
            function1 = new Function1<ProtoBuf.Type, List<? extends AnnotationWithArgs>>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.TypeClsStubBuilder.createTypeReferenceStub.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final List<AnnotationWithArgs> invoke(ProtoBuf.Type it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return TypeClsStubBuilder.this.c.getComponents().getAnnotationLoader().loadTypeAnnotations(it, TypeClsStubBuilder.this.c.getNameResolver());
                }
            };
        }
        typeClsStubBuilder.createTypeReferenceStub(stubElement, type, function0, function1);
    }

    public final void createTypeReferenceStub(StubElement<? extends PsiElement> parent, ProtoBuf.Type type, Function0<? extends List<AnnotationWithTarget>> additionalAnnotations, Function1<? super ProtoBuf.Type, ? extends List<AnnotationWithArgs>> loadTypeAnnotations) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
        Intrinsics.checkNotNullParameter(loadTypeAnnotations, "loadTypeAnnotations");
        ProtoBuf.Type typeAbbreviatedType = ProtoTypeTableUtilKt.abbreviatedType(type, this.c.getTypeTable());
        if (typeAbbreviatedType != null) {
            createTypeReferenceStub$default(this, parent, typeAbbreviatedType, additionalAnnotations, null, 8, null);
            return;
        }
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl(parent, KtStubElementTypes.TYPE_REFERENCE);
        List<AnnotationWithArgs> listInvoke = loadTypeAnnotations.invoke(type);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listInvoke) {
            AnnotationWithArgs annotationWithArgs = (AnnotationWithArgs) obj;
            if (annotationWithArgs.getClassId().isNestedClass() || !TypeClsStubBuilderKt.ANNOTATIONS_NOT_LOADED_FOR_TYPES.contains(annotationWithArgs.getClassId().asSingleFqName())) {
                arrayList.add(obj);
            }
        }
        List<AnnotationWithTarget> listInvoke2 = additionalAnnotations.invoke();
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(new AnnotationWithTarget((AnnotationWithArgs) it.next(), null));
        }
        List<AnnotationWithTarget> listPlus = CollectionsKt.plus((Collection) listInvoke2, (Iterable) arrayList3);
        if (type.hasClassName() || type.hasTypeAliasName()) {
            createClassReferenceTypeStub(kotlinPlaceHolderStubImpl, type, listPlus);
            return;
        }
        if (type.hasTypeParameter()) {
            createTypeParameterStub(kotlinPlaceHolderStubImpl, type, this.c.getTypeParameters().get(type.getTypeParameter()), listPlus);
        } else if (type.hasTypeParameterName()) {
            createTypeParameterStub(kotlinPlaceHolderStubImpl, type, NameResolverUtilKt.getName(this.c.getNameResolver(), type.getTypeParameterName()), listPlus);
        } else {
            FunctionsKt.m7486doNothing();
        }
    }

    private final KotlinStubBaseImpl<?> nullableTypeParent(KotlinStubBaseImpl<?> parent, ProtoBuf.Type type) {
        return type.getNullable() ? new KotlinPlaceHolderStubImpl(parent, KtStubElementTypes.NULLABLE_TYPE) : parent;
    }

    private final void createTypeParameterStub(KotlinStubBaseImpl<?> parent, ProtoBuf.Type type, Name name2, List<AnnotationWithTarget> annotations) {
        final KotlinTypeBean kotlinTypeBeanCreateKotlinTypeBean;
        createTypeAnnotationStubs(parent, type, annotations);
        if (type.hasFlexibleTypeCapabilitiesId()) {
            ProtoBuf.Type typeFlexibleUpperBound = ProtoTypeTableUtilKt.flexibleUpperBound(type, this.c.getTypeTable());
            Intrinsics.checkNotNull(typeFlexibleUpperBound);
            kotlinTypeBeanCreateKotlinTypeBean = createKotlinTypeBean(typeFlexibleUpperBound);
        } else {
            kotlinTypeBeanCreateKotlinTypeBean = null;
        }
        ClassId classId = ClassId.topLevel(FqName.topLevel(name2));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        Boolean bool = Flags.DEFINITELY_NOT_NULL_TYPE.get(type.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (bool.booleanValue()) {
            createDefinitelyNotNullTypeStub(parent, classId, kotlinTypeBeanCreateKotlinTypeBean);
        } else {
            ClsStubBuildingKt.createStubForTypeName$default(classId, nullableTypeParent(parent, type), new Function1<Integer, KotlinTypeBean>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.TypeClsStubBuilder.createTypeParameterStub.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ KotlinTypeBean invoke(Integer num) {
                    return invoke(num.intValue());
                }

                public final KotlinTypeBean invoke(int i) {
                    return kotlinTypeBeanCreateKotlinTypeBean;
                }
            }, null, 8, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void createDefinitelyNotNullTypeStub(KotlinStubBaseImpl<?> parent, ClassId classId, final KotlinTypeBean upperBoundType) {
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl(parent, KtStubElementTypes.INTERSECTION_TYPE);
        ClsStubBuildingKt.createStubForTypeName$default(classId, new KotlinPlaceHolderStubImpl(kotlinPlaceHolderStubImpl, KtStubElementTypes.TYPE_REFERENCE), new Function1<Integer, KotlinTypeBean>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.TypeClsStubBuilder.createDefinitelyNotNullTypeStub.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ KotlinTypeBean invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final KotlinTypeBean invoke(int i) {
                return upperBoundType;
            }
        }, null, 8, null);
        KotlinUserTypeStubImpl kotlinUserTypeStubImpl = new KotlinUserTypeStubImpl(new KotlinPlaceHolderStubImpl(kotlinPlaceHolderStubImpl, KtStubElementTypes.TYPE_REFERENCE), null, 2, 0 == true ? 1 : 0);
        Name nameShortName = StandardNames.FqNames.any.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        new KotlinNameReferenceExpressionStubImpl(kotlinUserTypeStubImpl, ClsStubBuildingKt.ref(nameShortName), true);
    }

    private final void createClassReferenceTypeStub(KotlinStubBaseImpl<?> parent, final ProtoBuf.Type type, List<AnnotationWithTarget> annotations) {
        KotlinStubBaseImpl<?> kotlinStubBaseImplNullableTypeParent;
        Object next;
        int intValue;
        if (type.hasFlexibleTypeCapabilitiesId() && Intrinsics.areEqual(this.c.getNameResolver().getString(type.getFlexibleTypeCapabilitiesId()), "kotlin.DynamicType")) {
            new KotlinPlaceHolderStubImpl(nullableTypeParent(parent, type), KtStubElementTypes.DYNAMIC_TYPE);
            return;
        }
        if (!type.hasClassName()) {
            type.hasTypeAliasName();
        }
        ClassId classId = NameResolverUtilKt.getClassId(this.c.getNameResolver(), type.hasClassName() ? type.getClassName() : type.getTypeAliasName());
        if (FunctionTypesKt.isBuiltinFunctionClass(classId)) {
            List<ProtoBuf.Type.Argument> argumentList = type.getArgumentList();
            Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
            List<ProtoBuf.Type.Argument> list = argumentList;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (((ProtoBuf.Type.Argument) it.next()).getProjection() == ProtoBuf.Type.Argument.Projection.STAR) {
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : annotations) {
                if (Intrinsics.areEqual(((AnnotationWithTarget) obj).getAnnotationWithArgs().getClassId().asSingleFqName(), StandardNames.FqNames.extensionFunctionType)) {
                    arrayList.add(obj);
                } else {
                    arrayList2.add(obj);
                }
            }
            Pair pair = new Pair(arrayList, arrayList2);
            List list2 = (List) pair.component1();
            List list3 = (List) pair.component2();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (Object obj2 : list3) {
                if (Intrinsics.areEqual(((AnnotationWithTarget) obj2).getAnnotationWithArgs().getClassId().asSingleFqName(), StandardNames.FqNames.contextFunctionTypeParams)) {
                    arrayList3.add(obj2);
                } else {
                    arrayList4.add(obj2);
                }
            }
            Pair pair2 = new Pair(arrayList3, arrayList4);
            List list4 = (List) pair2.component1();
            List<AnnotationWithTarget> list5 = (List) pair2.component2();
            boolean z = !list2.isEmpty();
            Boolean bool = Flags.SUSPEND_TYPE.get(type.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            boolean zBooleanValue = bool.booleanValue();
            if (zBooleanValue) {
                kotlinStubBaseImplNullableTypeParent = nullableTypeParent(parent, type);
                createTypeAnnotationStubs(kotlinStubBaseImplNullableTypeParent, type, list5);
            } else {
                createTypeAnnotationStubs(parent, type, list5);
                kotlinStubBaseImplNullableTypeParent = nullableTypeParent(parent, type);
            }
            if (list4.isEmpty()) {
                intValue = 0;
            } else {
                Object extension = type.getExtension(JvmProtoBuf.typeAnnotation);
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                Iterator it2 = ((Iterable) extension).iterator();
                do {
                    if (!it2.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it2.next();
                } while (!Intrinsics.areEqual(NameResolverUtilKt.getClassId(this.c.getNameResolver(), ((ProtoBuf.Annotation) next).getId()).asSingleFqName(), StandardNames.FqNames.contextFunctionTypeParams));
                Intrinsics.checkNotNull(next);
                intValue = (int) ((ProtoBuf.Annotation) next).getArgument(0).getValue().getIntValue();
            }
            createFunctionTypeStub(kotlinStubBaseImplNullableTypeParent, type, z, zBooleanValue, intValue);
            return;
        }
        createTypeAnnotationStubs(parent, type, annotations);
        final List list6 = SequencesKt.toList(SequencesKt.generateSequence(type, new Function1<ProtoBuf.Type, ProtoBuf.Type>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.TypeClsStubBuilder$createClassReferenceTypeStub$outerTypeChain$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ProtoBuf.Type invoke(ProtoBuf.Type it3) {
                Intrinsics.checkNotNullParameter(it3, "it");
                return ProtoTypeTableUtilKt.outerType(it3, this.this$0.c.getTypeTable());
            }
        }));
        ClsStubBuildingKt.createStubForTypeName(classId, nullableTypeParent(parent, type), new Function1<Integer, KotlinTypeBean>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.TypeClsStubBuilder.createClassReferenceTypeStub.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ KotlinTypeBean invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final KotlinTypeBean invoke(int i) {
                if (i == 0) {
                    TypeClsStubBuilder typeClsStubBuilder = TypeClsStubBuilder.this;
                    return typeClsStubBuilder.createKotlinTypeBean(ProtoTypeTableUtilKt.flexibleUpperBound(type, typeClsStubBuilder.c.getTypeTable()));
                }
                TypeClsStubBuilder typeClsStubBuilder2 = TypeClsStubBuilder.this;
                ProtoBuf.Type type2 = (ProtoBuf.Type) CollectionsKt.getOrNull(list6, i);
                return typeClsStubBuilder2.createKotlinTypeBean(type2 != null ? ProtoTypeTableUtilKt.flexibleUpperBound(type2, TypeClsStubBuilder.this.c.getTypeTable()) : null);
            }
        }, new Function2<KotlinUserTypeStub, Integer, Unit>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.TypeClsStubBuilder.createClassReferenceTypeStub.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(KotlinUserTypeStub kotlinUserTypeStub, Integer num) {
                invoke(kotlinUserTypeStub, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(KotlinUserTypeStub userTypeStub, int i) {
                Intrinsics.checkNotNullParameter(userTypeStub, "userTypeStub");
                ProtoBuf.Type type2 = (ProtoBuf.Type) CollectionsKt.getOrNull(list6, i);
                if (type2 != null) {
                    TypeClsStubBuilder typeClsStubBuilder = this;
                    List<ProtoBuf.Type.Argument> argumentList2 = type2.getArgumentList();
                    Intrinsics.checkNotNullExpressionValue(argumentList2, "getArgumentList(...)");
                    typeClsStubBuilder.createTypeArgumentListStub(userTypeStub, argumentList2);
                }
            }
        });
    }

    public final KotlinTypeBean createKotlinTypeBean(ProtoBuf.Type type) {
        SimpleTypeMarker kotlinClassTypeBean;
        KotlinTypeBean kotlinTypeBeanCreateKotlinTypeBean;
        if (type == null) {
            return null;
        }
        Boolean bool = Flags.DEFINITELY_NOT_NULL_TYPE.get(type.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        boolean zBooleanValue = bool.booleanValue();
        if (type.hasTypeParameter()) {
            String strAsString = this.c.getTypeParameters().get(type.getTypeParameter()).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            kotlinClassTypeBean = new KotlinTypeParameterTypeBean(strAsString, type.getNullable(), zBooleanValue);
        } else if (type.hasTypeParameterName()) {
            kotlinClassTypeBean = new KotlinTypeParameterTypeBean(this.c.getNameResolver().getString(type.getTypeParameterName()), type.getNullable(), zBooleanValue);
        } else {
            ClassId classId = NameResolverUtilKt.getClassId(this.c.getNameResolver(), type.hasClassName() ? type.getClassName() : type.getTypeAliasName());
            List<ProtoBuf.Type.Argument> argumentList = type.getArgumentList();
            Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
            List<ProtoBuf.Type.Argument> list = argumentList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (ProtoBuf.Type.Argument argument : list) {
                ProtoBuf.Type.Argument.Projection projection = argument.getProjection();
                Intrinsics.checkNotNullExpressionValue(projection, "getProjection(...)");
                KtProjectionKind projectionKind = toProjectionKind(projection);
                if (projectionKind == KtProjectionKind.STAR) {
                    kotlinTypeBeanCreateKotlinTypeBean = null;
                } else {
                    Intrinsics.checkNotNull(argument);
                    kotlinTypeBeanCreateKotlinTypeBean = createKotlinTypeBean(ProtoTypeTableUtilKt.type(argument, this.c.getTypeTable()));
                }
                arrayList.add(new KotlinTypeArgumentBean(projectionKind, kotlinTypeBeanCreateKotlinTypeBean));
            }
            kotlinClassTypeBean = new KotlinClassTypeBean(classId, arrayList, type.getNullable());
        }
        KotlinTypeBean kotlinTypeBeanCreateKotlinTypeBean2 = createKotlinTypeBean(ProtoTypeTableUtilKt.flexibleUpperBound(type, this.c.getTypeTable()));
        if (kotlinTypeBeanCreateKotlinTypeBean2 != null) {
            return new KotlinFlexibleTypeBean((KotlinTypeBean) kotlinClassTypeBean, kotlinTypeBeanCreateKotlinTypeBean2);
        }
        return (KotlinTypeBean) kotlinClassTypeBean;
    }

    private final void createTypeAnnotationStubs(KotlinStubBaseImpl<?> parent, ProtoBuf.Type type, List<AnnotationWithTarget> annotations) {
        final Set<KtModifierKeywordToken> typeModifiersAsWritten = getTypeModifiersAsWritten(type);
        if (annotations.isEmpty() && typeModifiersAsWritten.isEmpty()) {
            return;
        }
        ClsStubBuildingKt.createTargetedAnnotationStubs(annotations, new KotlinModifierListStubImpl(parent, ModifierMaskUtils.computeMask(new Function1<KtModifierKeywordToken, Boolean>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.TypeClsStubBuilder$createTypeAnnotationStubs$typeModifiersMask$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtModifierKeywordToken it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(typeModifiersAsWritten.contains(it));
            }
        }), KtStubElementTypes.MODIFIER_LIST));
    }

    private final Set<KtModifierKeywordToken> getTypeModifiersAsWritten(ProtoBuf.Type type) {
        if (!type.hasClassName() && !type.hasTypeAliasName()) {
            return SetsKt.emptySet();
        }
        HashSet hashSet = new HashSet();
        Boolean bool = Flags.SUSPEND_TYPE.get(type.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (bool.booleanValue()) {
            hashSet.add(KtTokens.SUSPEND_KEYWORD);
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void createTypeArgumentListStub(KotlinUserTypeStub typeStub, List<ProtoBuf.Type.Argument> typeArgumentProtoList) {
        if (typeArgumentProtoList.isEmpty()) {
            return;
        }
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl(typeStub, KtStubElementTypes.TYPE_ARGUMENT_LIST);
        for (ProtoBuf.Type.Argument argument : typeArgumentProtoList) {
            ProtoBuf.Type.Argument.Projection projection = argument.getProjection();
            Intrinsics.checkNotNullExpressionValue(projection, "getProjection(...)");
            KtProjectionKind projectionKind = toProjectionKind(projection);
            KotlinTypeProjectionStubImpl kotlinTypeProjectionStubImpl = new KotlinTypeProjectionStubImpl(kotlinPlaceHolderStubImpl, projectionKind.ordinal());
            if (projectionKind != KtProjectionKind.STAR) {
                KtSingleValueToken token = projectionKind.getToken();
                KotlinTypeProjectionStubImpl kotlinTypeProjectionStubImpl2 = kotlinTypeProjectionStubImpl;
                ClsStubBuildingKt.createModifierListStub(kotlinTypeProjectionStubImpl2, CollectionsKt.listOfNotNull(token instanceof KtModifierKeywordToken ? (KtModifierKeywordToken) token : null));
                ProtoBuf.Type type = ProtoTypeTableUtilKt.type(argument, this.c.getTypeTable());
                Intrinsics.checkNotNull(type);
                createTypeReferenceStub$default(this, kotlinTypeProjectionStubImpl2, type, null, null, 12, null);
            }
        }
    }

    private final KtProjectionKind toProjectionKind(ProtoBuf.Type.Argument.Projection projection) {
        int i = WhenMappings.$EnumSwitchMapping$0[projection.ordinal()];
        if (i == 1) {
            return KtProjectionKind.IN;
        }
        if (i == 2) {
            return KtProjectionKind.OUT;
        }
        if (i == 3) {
            return KtProjectionKind.NONE;
        }
        if (i == 4) {
            return KtProjectionKind.STAR;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final void createFunctionTypeStub(StubElement<? extends PsiElement> parent, ProtoBuf.Type type, boolean isExtensionFunctionType, boolean isSuspend, int numContextReceivers) {
        int i;
        List<ProtoBuf.Type.Argument> argumentList = type.getArgumentList();
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl(parent, KtStubElementTypes.FUNCTION_TYPE);
        if (numContextReceivers != 0) {
            ContextReceiversListStubBuilder contextReceiversListStubBuilder = new ContextReceiversListStubBuilder(this.c);
            KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl2 = kotlinPlaceHolderStubImpl;
            List<ProtoBuf.Type.Argument> listSubList = argumentList.subList(0, numContextReceivers);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSubList, 10));
            for (ProtoBuf.Type.Argument argument : listSubList) {
                Intrinsics.checkNotNull(argument);
                ProtoBuf.Type type2 = ProtoTypeTableUtilKt.type(argument, this.c.getTypeTable());
                Intrinsics.checkNotNull(type2);
                arrayList.add(type2);
            }
            contextReceiversListStubBuilder.createContextReceiverStubs(kotlinPlaceHolderStubImpl2, arrayList);
            i = numContextReceivers;
        } else {
            i = 0;
        }
        if (isExtensionFunctionType) {
            KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl3 = new KotlinPlaceHolderStubImpl(kotlinPlaceHolderStubImpl, KtStubElementTypes.FUNCTION_TYPE_RECEIVER);
            ProtoBuf.Type.Argument argument2 = argumentList.get(i);
            Intrinsics.checkNotNullExpressionValue(argument2, "get(...)");
            ProtoBuf.Type type3 = ProtoTypeTableUtilKt.type(argument2, this.c.getTypeTable());
            Intrinsics.checkNotNull(type3);
            createTypeReferenceStub$default(this, kotlinPlaceHolderStubImpl3, type3, null, null, 12, null);
            i++;
        }
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl4 = kotlinPlaceHolderStubImpl;
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl5 = new KotlinPlaceHolderStubImpl(kotlinPlaceHolderStubImpl4, KtStubElementTypes.VALUE_PARAMETER_LIST);
        List<ProtoBuf.Type.Argument> listSubList2 = argumentList.subList(i, argumentList.size() - 1);
        ProtoBuf.Type type4 = null;
        int i2 = 0;
        for (ProtoBuf.Type.Argument argument3 : listSubList2) {
            int i3 = i2 + 1;
            Intrinsics.checkNotNull(argument3);
            ProtoBuf.Type type5 = ProtoTypeTableUtilKt.type(argument3, this.c.getTypeTable());
            Intrinsics.checkNotNull(type5);
            if (isSuspend && i2 == listSubList2.size() - 1 && type5.hasClassName() && type5.getArgumentCount() == 1) {
                FqName fqNameAsSingleFqName = NameResolverUtilKt.getClassId(this.c.getNameResolver(), type5.getClassName()).asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                if (!Intrinsics.areEqual(fqNameAsSingleFqName, new FqName("kotlin.coroutines.Continuation"))) {
                    Intrinsics.areEqual(fqNameAsSingleFqName, new FqName("kotlin.coroutines.experimental.Continuation"));
                }
                type4 = type5;
            } else {
                final List<AnnotationWithArgs> listLoadTypeAnnotations = this.c.getComponents().getAnnotationLoader().loadTypeAnnotations(type5, this.c.getNameResolver());
                createTypeReferenceStub$default(this, new KotlinParameterStubImpl(kotlinPlaceHolderStubImpl5, null, null, false, false, false, createFunctionTypeStub$getFunctionTypeParameterName(listLoadTypeAnnotations)), type5, null, new Function1<ProtoBuf.Type, List<? extends AnnotationWithArgs>>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.TypeClsStubBuilder.createFunctionTypeStub.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final List<AnnotationWithArgs> invoke(ProtoBuf.Type it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return listLoadTypeAnnotations;
                    }
                }, 4, null);
            }
            i2 = i3;
        }
        if (type4 == null) {
            Intrinsics.checkNotNull(argumentList);
            Object objLast = CollectionsKt.last((List<? extends Object>) argumentList);
            Intrinsics.checkNotNullExpressionValue(objLast, "last(...)");
            ProtoBuf.Type type6 = ProtoTypeTableUtilKt.type((ProtoBuf.Type.Argument) objLast, this.c.getTypeTable());
            Intrinsics.checkNotNull(type6);
            createTypeReferenceStub$default(this, kotlinPlaceHolderStubImpl4, type6, null, null, 12, null);
            return;
        }
        ProtoBuf.Type.Argument argument4 = type4.getArgument(0);
        Intrinsics.checkNotNullExpressionValue(argument4, "getArgument(...)");
        ProtoBuf.Type type7 = ProtoTypeTableUtilKt.type(argument4, this.c.getTypeTable());
        Intrinsics.checkNotNull(type7);
        createTypeReferenceStub$default(this, kotlinPlaceHolderStubImpl4, type7, null, null, 12, null);
    }

    private static final String createFunctionTypeStub$getFunctionTypeParameterName(List<AnnotationWithArgs> list) {
        for (AnnotationWithArgs annotationWithArgs : list) {
            if (Intrinsics.areEqual(annotationWithArgs.getClassId().asSingleFqName(), StandardNames.FqNames.parameterName)) {
                Object objFirstOrNull = CollectionsKt.firstOrNull(annotationWithArgs.getArgs().values());
                StringValue stringValue = objFirstOrNull instanceof StringValue ? (StringValue) objFirstOrNull : null;
                if (stringValue != null) {
                    return stringValue.getValue();
                }
                return null;
            }
        }
        return null;
    }

    public static /* synthetic */ void createValueParameterListStub$default(TypeClsStubBuilder typeClsStubBuilder, StubElement stubElement, MessageLite messageLite, List list, ProtoContainer protoContainer, AnnotatedCallableKind annotatedCallableKind, int i, Object obj) {
        if ((i & 16) != 0) {
            annotatedCallableKind = ClsStubBuildingKt.getAnnotatedCallableKind(messageLite);
        }
        typeClsStubBuilder.createValueParameterListStub(stubElement, messageLite, list, protoContainer, annotatedCallableKind);
    }

    public final void createValueParameterListStub(StubElement<? extends PsiElement> parent, MessageLite callableProto, List<ProtoBuf.ValueParameter> parameters, ProtoContainer container, AnnotatedCallableKind callableKind) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(callableProto, "callableProto");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(callableKind, "callableKind");
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl(parent, KtStubElementTypes.VALUE_PARAMETER_LIST);
        int i = 0;
        for (ProtoBuf.ValueParameter valueParameter : parameters) {
            int i2 = i + 1;
            Name name2 = NameResolverUtilKt.getName(this.c.getNameResolver(), valueParameter.getName());
            if (Intrinsics.areEqual(name2, SpecialNames.IMPLICIT_SET_PARAMETER)) {
                name2 = StandardNames.DEFAULT_VALUE_PARAMETER;
            }
            Boolean bool = Flags.DECLARES_DEFAULT_VALUE.get(valueParameter.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            boolean zBooleanValue = bool.booleanValue();
            KotlinParameterStubImpl kotlinParameterStubImpl = new KotlinParameterStubImpl(kotlinPlaceHolderStubImpl, null, ClsStubBuildingKt.ref(name2), false, false, zBooleanValue, null, 64, null);
            ProtoBuf.Type typeVarargElementType = ProtoTypeTableUtilKt.varargElementType(valueParameter, this.c.getTypeTable());
            ProtoBuf.Type type = typeVarargElementType == null ? ProtoTypeTableUtilKt.type(valueParameter, this.c.getTypeTable()) : typeVarargElementType;
            ArrayList arrayList = new ArrayList();
            if (typeVarargElementType != null) {
                arrayList.add(KtTokens.VARARG_KEYWORD);
            }
            Boolean bool2 = Flags.IS_CROSSINLINE.get(valueParameter.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
            if (bool2.booleanValue()) {
                arrayList.add(KtTokens.CROSSINLINE_KEYWORD);
            }
            Boolean bool3 = Flags.IS_NOINLINE.get(valueParameter.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool3, "get(...)");
            if (bool3.booleanValue()) {
                arrayList.add(KtTokens.NOINLINE_KEYWORD);
            }
            KotlinParameterStubImpl kotlinParameterStubImpl2 = kotlinParameterStubImpl;
            KotlinModifierListStubImpl kotlinModifierListStubImplCreateModifierListStub = ClsStubBuildingKt.createModifierListStub(kotlinParameterStubImpl2, arrayList);
            Boolean bool4 = Flags.HAS_ANNOTATIONS.get(valueParameter.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool4, "get(...)");
            if (bool4.booleanValue()) {
                List<AnnotationWithArgs> listLoadValueParameterAnnotations = this.c.getComponents().getAnnotationLoader().loadValueParameterAnnotations(container, callableProto, callableKind, i, valueParameter);
                if (!listLoadValueParameterAnnotations.isEmpty()) {
                    if (kotlinModifierListStubImplCreateModifierListStub == null) {
                        kotlinModifierListStubImplCreateModifierListStub = ClsStubBuildingKt.createEmptyModifierListStub(kotlinParameterStubImpl);
                    }
                    ClsStubBuildingKt.createAnnotationStubs(listLoadValueParameterAnnotations, kotlinModifierListStubImplCreateModifierListStub);
                }
            }
            KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl2 = kotlinPlaceHolderStubImpl;
            createTypeReferenceStub$default(this, kotlinParameterStubImpl2, type, null, null, 12, null);
            if (zBooleanValue) {
                new KotlinNameReferenceExpressionStubImpl(kotlinParameterStubImpl2, StringRef.fromString("COMPILED_CODE"));
            }
            kotlinPlaceHolderStubImpl = kotlinPlaceHolderStubImpl2;
            i = i2;
        }
    }

    public final List<Pair<Name, ProtoBuf.Type>> createTypeParameterListStub(StubElement<? extends PsiElement> parent, List<ProtoBuf.TypeParameter> typeParameterProtoList) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(typeParameterProtoList, "typeParameterProtoList");
        if (typeParameterProtoList.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl(parent, KtStubElementTypes.TYPE_PARAMETER_LIST);
        ArrayList arrayList = new ArrayList();
        for (ProtoBuf.TypeParameter typeParameter : typeParameterProtoList) {
            Name name2 = NameResolverUtilKt.getName(this.c.getNameResolver(), typeParameter.getName());
            KotlinTypeParameterStubImpl kotlinTypeParameterStubImpl = new KotlinTypeParameterStubImpl(kotlinPlaceHolderStubImpl, ClsStubBuildingKt.ref(name2), typeParameter.getVariance() == ProtoBuf.TypeParameter.Variance.IN, typeParameter.getVariance() == ProtoBuf.TypeParameter.Variance.OUT);
            createTypeParameterModifierListStub(kotlinTypeParameterStubImpl, typeParameter);
            List<ProtoBuf.Type> listUpperBounds = ProtoTypeTableUtilKt.upperBounds(typeParameter, this.c.getTypeTable());
            if (!listUpperBounds.isEmpty()) {
                ProtoBuf.Type type = (ProtoBuf.Type) CollectionsKt.first((List) listUpperBounds);
                if (!isDefaultUpperBound(type)) {
                    createTypeReferenceStub$default(this, kotlinTypeParameterStubImpl, type, null, null, 12, null);
                }
                List listDrop = CollectionsKt.drop(listUpperBounds, 1);
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDrop, 10));
                Iterator it = listDrop.iterator();
                while (it.hasNext()) {
                    arrayList2.add(new Pair(name2, (ProtoBuf.Type) it.next()));
                }
                arrayList.addAll(arrayList2);
            }
        }
        return arrayList;
    }

    public final void createTypeConstraintListStub(StubElement<? extends PsiElement> parent, List<Pair<Name, ProtoBuf.Type>> protosForTypeConstraintList) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(protosForTypeConstraintList, "protosForTypeConstraintList");
        if (protosForTypeConstraintList.isEmpty()) {
            return;
        }
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl(parent, KtStubElementTypes.TYPE_CONSTRAINT_LIST);
        for (Pair<Name, ProtoBuf.Type> pair : protosForTypeConstraintList) {
            Name nameComponent1 = pair.component1();
            ProtoBuf.Type typeComponent2 = pair.component2();
            KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl2 = new KotlinPlaceHolderStubImpl(kotlinPlaceHolderStubImpl, KtStubElementTypes.TYPE_CONSTRAINT);
            new KotlinNameReferenceExpressionStubImpl(kotlinPlaceHolderStubImpl2, ClsStubBuildingKt.ref(nameComponent1));
            createTypeReferenceStub$default(this, kotlinPlaceHolderStubImpl2, typeComponent2, null, null, 12, null);
        }
    }

    private final void createTypeParameterModifierListStub(KotlinTypeParameterStubImpl typeParameterStub, ProtoBuf.TypeParameter typeParameterProto) {
        ArrayList arrayList = new ArrayList();
        ProtoBuf.TypeParameter.Variance variance = typeParameterProto.getVariance();
        int i = variance == null ? -1 : WhenMappings.$EnumSwitchMapping$1[variance.ordinal()];
        if (i == 1) {
            arrayList.add(KtTokens.IN_KEYWORD);
        } else if (i == 2) {
            arrayList.add(KtTokens.OUT_KEYWORD);
        }
        if (typeParameterProto.getReified()) {
            arrayList.add(KtTokens.REIFIED_KEYWORD);
        }
        KotlinModifierListStubImpl kotlinModifierListStubImplCreateModifierListStub = ClsStubBuildingKt.createModifierListStub(typeParameterStub, arrayList);
        List<AnnotationWithArgs> listLoadTypeParameterAnnotations = this.c.getComponents().getAnnotationLoader().loadTypeParameterAnnotations(typeParameterProto, this.c.getNameResolver());
        if (listLoadTypeParameterAnnotations.isEmpty()) {
            return;
        }
        if (kotlinModifierListStubImplCreateModifierListStub == null) {
            kotlinModifierListStubImplCreateModifierListStub = ClsStubBuildingKt.createEmptyModifierListStub(typeParameterStub);
        }
        ClsStubBuildingKt.createAnnotationStubs(listLoadTypeParameterAnnotations, kotlinModifierListStubImplCreateModifierListStub);
    }

    private final boolean isDefaultUpperBound(ProtoBuf.Type type) {
        if (type.hasClassName()) {
            if (Intrinsics.areEqual(StandardNames.FqNames.any, NameResolverUtilKt.getClassId(this.c.getNameResolver(), type.getClassName()).asSingleFqName().toUnsafe()) && type.getNullable()) {
                return true;
            }
        }
        return false;
    }
}
