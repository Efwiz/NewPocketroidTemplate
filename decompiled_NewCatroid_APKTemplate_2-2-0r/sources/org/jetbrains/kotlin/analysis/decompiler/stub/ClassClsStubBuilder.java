package org.jetbrains.kotlin.analysis.decompiler.stub;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.stubs.elements.KtClassElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinClassStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinModifierListStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinObjectStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPlaceHolderStubImpl;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: ClassClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0002\u0018\u0000 52\u00020\u0001:\u00015B?\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\u0006\u0010\u001e\u001a\u00020\u001fJ\u0016\u0010 \u001a\u00020\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0002J\b\u0010$\u001a\u00020\u001fH\u0002J\u0010\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u0002J\u0016\u0010&\u001a\u00020\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0002J\b\u0010'\u001a\u00020\u001fH\u0002J\b\u0010(\u001a\u00020\u001fH\u0002J\u0016\u0010)\u001a\u00020\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0002J\u0016\u0010*\u001a\u00020\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0002J\u0018\u0010+\u001a\u00020,2\u000e\u0010-\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u0002J \u0010.\u001a\u00020\u001f2\u000e\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u0006\u0010/\u001a\u00020\nH\u0002J\u0016\u00100\u001a\u00020\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0002J\u0010\u00101\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u0002J\b\u00102\u001a\u000203H\u0002J\b\u00104\u001a\u000203H\u0002R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClassClsStubBuilder;", "", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "outerContext", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/descriptors/SourceElement;Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;)V", "c", "classKind", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Kind;", "classOrObjectStub", "companionObjectName", "Lorg/jetbrains/kotlin/name/Name;", "contextReceiversListStubBuilder", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ContextReceiversListStubBuilder;", "supertypeIds", "", "thisAsProtoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "typeStubBuilder", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeClsStubBuilder;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "", "createCallableMemberStubs", "classBody", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinPlaceHolderStubImpl;", "Lorg/jetbrains/kotlin/psi/KtClassBody;", "createClassBodyAndMemberStubs", "createClassOrObjectStubAndModifierListStub", "createCompanionObjectStub", "createConstructorStub", "createDelegationSpecifierList", "createEnumEntryStubs", "createInnerAndNestedClasses", "createModifierListForClass", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinModifierListStubImpl;", "parent", "createNestedClassStub", "nestedClassId", "createTypeAliasesStubs", "doCreateClassOrObjectStub", "isClass", "", "isInterface", "Companion", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ClassClsStubBuilder {
    private static final Logger LOG;
    private final ClsStubBuilderContext c;
    private final ClassId classId;
    private final ProtoBuf.Class.Kind classKind;
    private final StubElement<? extends PsiElement> classOrObjectStub;
    private final ProtoBuf.Class classProto;
    private final Name companionObjectName;
    private final ContextReceiversListStubBuilder contextReceiversListStubBuilder;
    private final StubElement<? extends PsiElement> parentStub;
    private final List<ClassId> supertypeIds;
    private final ProtoContainer.Class thisAsProtoContainer;
    private final TypeClsStubBuilder typeStubBuilder;

    /* JADX INFO: compiled from: ClassClsStubBuilder.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoBuf.Class.Kind.values().length];
            try {
                iArr[ProtoBuf.Class.Kind.ENUM_CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.Class.Kind.COMPANION_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.Class.Kind.ANNOTATION_CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProtoBuf.Class.Kind.OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ClassClsStubBuilder(StubElement<? extends PsiElement> parentStub, ProtoBuf.Class classProto, NameResolver nameResolver, ClassId classId, SourceElement sourceElement, ClsStubBuilderContext outerContext) {
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        this.parentStub = parentStub;
        this.classProto = classProto;
        this.classId = classId;
        ProtoBuf.TypeTable typeTable = classProto.getTypeTable();
        Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
        ProtoContainer.Class r8 = new ProtoContainer.Class(classProto, nameResolver, new TypeTable(typeTable), sourceElement, outerContext.getProtoContainer());
        this.thisAsProtoContainer = r8;
        this.classKind = r8.getKind();
        List<ProtoBuf.TypeParameter> typeParameterList = classProto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        ClsStubBuilderContext clsStubBuilderContextChild = ClsStubBuilderContextKt.child(outerContext, typeParameterList, classId.getShortClassName(), nameResolver, r8.getTypeTable(), r8);
        this.c = clsStubBuilderContextChild;
        this.contextReceiversListStubBuilder = new ContextReceiversListStubBuilder(clsStubBuilderContextChild);
        this.typeStubBuilder = new TypeClsStubBuilder(clsStubBuilderContextChild);
        List<ProtoBuf.Type> listSupertypes = ProtoTypeTableUtilKt.supertypes(this.classProto, this.c.getTypeTable());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSupertypes, 10));
        Iterator<T> it = listSupertypes.iterator();
        while (it.hasNext()) {
            arrayList.add(NameResolverUtilKt.getClassId(this.c.getNameResolver(), ((ProtoBuf.Type) it.next()).getClassName()));
        }
        ArrayList arrayListEmptyList = arrayList;
        ClassId classId2 = (ClassId) CollectionsKt.singleOrNull((List) arrayListEmptyList);
        if (classId2 != null && Intrinsics.areEqual(StandardNames.FqNames.any, classId2.asSingleFqName().toUnsafe())) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        this.supertypeIds = arrayListEmptyList;
        this.companionObjectName = this.classProto.hasCompanionObjectName() ? NameResolverUtilKt.getName(this.c.getNameResolver(), this.classProto.getCompanionObjectName()) : null;
        this.classOrObjectStub = createClassOrObjectStubAndModifierListStub();
    }

    public final void build() {
        TypeClsStubBuilder typeClsStubBuilder = this.typeStubBuilder;
        StubElement<? extends PsiElement> stubElement = this.classOrObjectStub;
        List<ProtoBuf.TypeParameter> typeParameterList = this.classProto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        List<Pair<Name, ProtoBuf.Type>> listCreateTypeParameterListStub = typeClsStubBuilder.createTypeParameterListStub(stubElement, typeParameterList);
        createConstructorStub();
        createDelegationSpecifierList();
        this.typeStubBuilder.createTypeConstraintListStub(this.classOrObjectStub, listCreateTypeParameterListStub);
        createClassBodyAndMemberStubs();
    }

    private final StubElement<? extends PsiElement> createClassOrObjectStubAndModifierListStub() {
        StubElement<? extends PsiElement> stubElementDoCreateClassOrObjectStub = doCreateClassOrObjectStub();
        this.contextReceiversListStubBuilder.createContextReceiverStubs(stubElementDoCreateClassOrObjectStub, ProtoTypeTableUtilKt.contextReceiverTypes(this.classProto, this.c.getTypeTable()));
        KotlinModifierListStubImpl kotlinModifierListStubImplCreateModifierListForClass = createModifierListForClass(stubElementDoCreateClassOrObjectStub);
        Boolean bool = Flags.HAS_ANNOTATIONS.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (bool.booleanValue()) {
            ClsStubBuildingKt.createAnnotationStubs(this.c.getComponents().getAnnotationLoader().loadClassAnnotations(this.thisAsProtoContainer), kotlinModifierListStubImplCreateModifierListForClass);
        }
        return stubElementDoCreateClassOrObjectStub;
    }

    private final KotlinModifierListStubImpl createModifierListForClass(StubElement<? extends PsiElement> parent) {
        List listListOf;
        ArrayList arrayListArrayListOf = CollectionsKt.arrayListOf(FlagsKt.getVISIBILITY());
        arrayListArrayListOf.add(FlagsKt.getEXTERNAL_CLASS());
        arrayListArrayListOf.add(FlagsKt.getEXPECT_CLASS());
        if (isClass()) {
            arrayListArrayListOf.add(FlagsKt.getINNER());
            arrayListArrayListOf.add(FlagsKt.getDATA());
            arrayListArrayListOf.add(FlagsKt.getMODALITY());
            arrayListArrayListOf.add(FlagsKt.getVALUE_CLASS());
        }
        if (isInterface()) {
            arrayListArrayListOf.add(FlagsKt.getFUN_INTERFACE());
        }
        int i = WhenMappings.$EnumSwitchMapping$0[this.classKind.ordinal()];
        if (i == 1) {
            listListOf = CollectionsKt.listOf(KtTokens.ENUM_KEYWORD);
        } else if (i == 2) {
            listListOf = CollectionsKt.listOf(KtTokens.COMPANION_KEYWORD);
        } else if (i == 3) {
            listListOf = CollectionsKt.listOf(KtTokens.ANNOTATION_KEYWORD);
        } else {
            listListOf = CollectionsKt.emptyList();
        }
        return ClsStubBuildingKt.createModifierListStubForDeclaration(parent, this.classProto.getFlags(), arrayListArrayListOf, listListOf);
    }

    private final StubElement<? extends PsiElement> doCreateClassOrObjectStub() {
        boolean z = this.classKind == ProtoBuf.Class.Kind.COMPANION_OBJECT;
        FqName fqNameAsSingleFqName = this.classId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        Name nameShortName = fqNameAsSingleFqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        StringRef stringRefRef = ClsStubBuildingKt.ref(nameShortName);
        List<ClassId> list = this.supertypeIds;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            FqNameUnsafe unsafe = ((ClassId) obj).asSingleFqName().toUnsafe();
            Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
            if (!FunctionTypesKt.isNumberedFunctionClassFqName(unsafe)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Name shortClassName = ((ClassId) it.next()).getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            arrayList3.add(ClsStubBuildingKt.ref(shortClassName));
        }
        StringRef[] stringRefArr = (StringRef[]) arrayList3.toArray(new StringRef[0]);
        ClassId classId = this.classId;
        if (classId.isLocal()) {
            classId = null;
        }
        ClassId classId2 = classId;
        int i = WhenMappings.$EnumSwitchMapping$0[this.classKind.ordinal()];
        if (i == 2 || i == 4) {
            return new KotlinObjectStubImpl(this.parentStub, stringRefRef, fqNameAsSingleFqName, classId2, stringRefArr, !this.classId.isNestedClass(), z, false, false);
        }
        KtClassElementType stubType = KtClassElementType.getStubType(this.classKind == ProtoBuf.Class.Kind.ENUM_ENTRY);
        Intrinsics.checkNotNullExpressionValue(stubType, "getStubType(...)");
        return new KotlinClassStubImpl(stubType, this.parentStub, ClsStubBuildingKt.ref(fqNameAsSingleFqName), classId2, stringRefRef, stringRefArr, this.classKind == ProtoBuf.Class.Kind.INTERFACE, this.classKind == ProtoBuf.Class.Kind.ENUM_ENTRY, false, !this.classId.isNestedClass());
    }

    private final void createConstructorStub() {
        Object next;
        if (isClass()) {
            List<ProtoBuf.Constructor> constructorList = this.classProto.getConstructorList();
            Intrinsics.checkNotNullExpressionValue(constructorList, "getConstructorList(...)");
            Iterator<T> it = constructorList.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (Flags.IS_SECONDARY.get(((ProtoBuf.Constructor) next).getFlags()).booleanValue());
            ProtoBuf.Constructor constructor = (ProtoBuf.Constructor) next;
            if (constructor == null) {
                return;
            }
            CallableClsStubBuilderKt.createConstructorStub(this.classOrObjectStub, constructor, this.c, this.thisAsProtoContainer);
        }
    }

    private final void createDelegationSpecifierList() {
        if (this.supertypeIds.isEmpty()) {
            return;
        }
        KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl(this.classOrObjectStub, KtStubElementTypes.SUPER_TYPE_LIST);
        for (ProtoBuf.Type type : ProtoTypeTableUtilKt.supertypes(this.classProto, this.c.getTypeTable())) {
            TypeClsStubBuilder.createTypeReferenceStub$default(this.typeStubBuilder, new KotlinPlaceHolderStubImpl(kotlinPlaceHolderStubImpl, KtStubElementTypes.SUPER_TYPE_ENTRY), type, null, null, 12, null);
        }
    }

    private final void createClassBodyAndMemberStubs() {
        KotlinPlaceHolderStubImpl<KtClassBody> kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl<>(this.classOrObjectStub, KtStubElementTypes.CLASS_BODY);
        createEnumEntryStubs(kotlinPlaceHolderStubImpl);
        createCompanionObjectStub(kotlinPlaceHolderStubImpl);
        createCallableMemberStubs(kotlinPlaceHolderStubImpl);
        createInnerAndNestedClasses(kotlinPlaceHolderStubImpl);
        createTypeAliasesStubs(kotlinPlaceHolderStubImpl);
    }

    private final void createCompanionObjectStub(KotlinPlaceHolderStubImpl<KtClassBody> classBody) {
        Name name2 = this.companionObjectName;
        if (name2 == null) {
            return;
        }
        ClassId classIdCreateNestedClassId = this.classId.createNestedClassId(name2);
        Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
        createNestedClassStub(classBody, classIdCreateNestedClassId);
    }

    private final void createEnumEntryStubs(KotlinPlaceHolderStubImpl<KtClassBody> classBody) {
        if (this.classKind != ProtoBuf.Class.Kind.ENUM_CLASS) {
            return;
        }
        List<ProtoBuf.EnumEntry> enumEntryList = this.classProto.getEnumEntryList();
        Intrinsics.checkNotNullExpressionValue(enumEntryList, "getEnumEntryList(...)");
        for (ProtoBuf.EnumEntry enumEntry : enumEntryList) {
            Name name2 = NameResolverUtilKt.getName(this.c.getNameResolver(), enumEntry.getName());
            AnnotationLoader<AnnotationWithArgs> annotationLoader = this.c.getComponents().getAnnotationLoader();
            ProtoContainer.Class r5 = this.thisAsProtoContainer;
            Intrinsics.checkNotNull(enumEntry);
            List<AnnotationWithArgs> listLoadEnumEntryAnnotations = annotationLoader.loadEnumEntryAnnotations(r5, enumEntry);
            KtClassElementType ENUM_ENTRY = KtStubElementTypes.ENUM_ENTRY;
            Intrinsics.checkNotNullExpressionValue(ENUM_ENTRY, "ENUM_ENTRY");
            FqName fqNameChild = this.c.getContainerFqName().child(name2);
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            KotlinClassStubImpl kotlinClassStubImpl = new KotlinClassStubImpl(ENUM_ENTRY, classBody, ClsStubBuildingKt.ref(fqNameChild), null, ClsStubBuildingKt.ref(name2), new StringRef[0], false, true, false, false);
            if (!listLoadEnumEntryAnnotations.isEmpty()) {
                ClsStubBuildingKt.createAnnotationStubs(listLoadEnumEntryAnnotations, ClsStubBuildingKt.createEmptyModifierListStub(kotlinClassStubImpl));
            }
        }
    }

    private final void createCallableMemberStubs(KotlinPlaceHolderStubImpl<KtClassBody> classBody) {
        for (ProtoBuf.Constructor constructor : this.classProto.getConstructorList()) {
            Boolean bool = Flags.IS_SECONDARY.get(constructor.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            if (bool.booleanValue()) {
                Intrinsics.checkNotNull(constructor);
                CallableClsStubBuilderKt.createConstructorStub(classBody, constructor, this.c, this.thisAsProtoContainer);
            }
        }
        ClsStubBuilderContext clsStubBuilderContext = this.c;
        ProtoContainer.Class r1 = this.thisAsProtoContainer;
        List<ProtoBuf.Function> functionList = this.classProto.getFunctionList();
        Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
        List<ProtoBuf.Property> propertyList = this.classProto.getPropertyList();
        Intrinsics.checkNotNullExpressionValue(propertyList, "getPropertyList(...)");
        CallableClsStubBuilderKt.createDeclarationsStubs(classBody, clsStubBuilderContext, r1, functionList, propertyList);
    }

    private final boolean isClass() {
        return this.classKind == ProtoBuf.Class.Kind.CLASS || this.classKind == ProtoBuf.Class.Kind.ENUM_CLASS || this.classKind == ProtoBuf.Class.Kind.ANNOTATION_CLASS;
    }

    private final boolean isInterface() {
        return this.classKind == ProtoBuf.Class.Kind.INTERFACE;
    }

    private final void createInnerAndNestedClasses(KotlinPlaceHolderStubImpl<KtClassBody> classBody) {
        List<Integer> nestedClassNameList = this.classProto.getNestedClassNameList();
        Intrinsics.checkNotNullExpressionValue(nestedClassNameList, "getNestedClassNameList(...)");
        for (Integer num : nestedClassNameList) {
            NameResolver nameResolver = this.c.getNameResolver();
            Intrinsics.checkNotNull(num);
            Name name2 = NameResolverUtilKt.getName(nameResolver, num.intValue());
            if (!Intrinsics.areEqual(name2, this.companionObjectName)) {
                ClassId classIdCreateNestedClassId = this.classId.createNestedClassId(name2);
                Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
                createNestedClassStub(classBody, classIdCreateNestedClassId);
            }
        }
    }

    private final void createTypeAliasesStubs(KotlinPlaceHolderStubImpl<KtClassBody> classBody) {
        ClsStubBuilderContext clsStubBuilderContext = this.c;
        ProtoContainer.Class r1 = this.thisAsProtoContainer;
        List<ProtoBuf.TypeAlias> typeAliasList = this.classProto.getTypeAliasList();
        Intrinsics.checkNotNullExpressionValue(typeAliasList, "getTypeAliasList(...)");
        CallableClsStubBuilderKt.createTypeAliasesStubs(classBody, clsStubBuilderContext, r1, typeAliasList);
    }

    private final void createNestedClassStub(StubElement<? extends PsiElement> classBody, ClassId nestedClassId) {
        String str;
        ClassData classDataFindClassData = this.c.getComponents().getClassDataFinder().findClassData(nestedClassId);
        if (classDataFindClassData != null) {
            ClassClsStubBuilderKt.createClassStub(classBody, classDataFindClassData.getClassProto(), classDataFindClassData.getNameResolver(), nestedClassId, classDataFindClassData.getSourceElement(), this.c);
            return;
        }
        VirtualFile virtualFileForDebug = this.c.getComponents().getVirtualFileForDebug();
        if (LOG.isDebugEnabled()) {
            ClassId outerClassId = nestedClassId.getOuterClassId();
            VirtualFile[] children = virtualFileForDebug.getParent().getChildren();
            Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
            List listSortedWith = ArraysKt.sortedWith(children, new Comparator() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.ClassClsStubBuilder$createNestedClassStub$lambda$12$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((VirtualFile) t).getName(), ((VirtualFile) t2).getName());
                }
            });
            String str2 = "Could not find data for nested class " + nestedClassId + " of class " + outerClassId + '\n';
            if (outerClassId != null) {
                List list = listSortedWith;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            String name2 = ((VirtualFile) it.next()).getName();
                            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                            if (StringsKt.startsWith$default(name2, outerClassId.getRelativeClassName() + "$a", false, 2, (Object) null)) {
                                str = "";
                            }
                        }
                    }
                }
                str = "Reason: obfuscation suspected (single-letter name)\n";
            } else {
                str = "";
            }
            LOG.debug(str2 + str + "Root file: " + virtualFileForDebug.getCanonicalPath() + "\nDir: " + virtualFileForDebug.getParent().getCanonicalPath() + "\nChildren:\n" + CollectionsKt.joinToString$default(listSortedWith, "\n", null, null, 0, null, new Function1<VirtualFile, CharSequence>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.ClassClsStubBuilder$createNestedClassStub$1$msg$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(VirtualFile virtualFile) {
                    return virtualFile.getName() + " (valid: " + virtualFile.isValid() + ')';
                }
            }, 30, null));
        }
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) ClassClsStubBuilder.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
