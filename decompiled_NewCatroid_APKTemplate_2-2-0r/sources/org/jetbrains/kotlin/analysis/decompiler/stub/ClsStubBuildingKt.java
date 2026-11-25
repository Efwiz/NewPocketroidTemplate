package org.jetbrains.kotlin.analysis.decompiler.stub;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsToModifiers;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.load.kotlin.FacadeClassSource;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.psi.stubs.KotlinUserTypeStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinAnnotationEntryStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinAnnotationUseSiteTargetStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinFileStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinModifierListStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinNameReferenceExpressionStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPlaceHolderStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinStubBaseImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinStubOrigin;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinUserTypeStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.ModifierMaskUtils;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.serialization.deserialization.AnnotatedCallableKind;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;

/* JADX INFO: compiled from: clsStubBuilding.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Ö\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b\u001a\u0012\u0010\f\u001a\u00020\r2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b\u001a\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015\u001a\u0016\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0019\u001a\u0006\u0010\u001a\u001a\u00020\u000f\u001a&\u0010\u001b\u001a\u0004\u0018\u00010\r2\u000e\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f\u001a>\u0010!\u001a\u00020\r2\u000e\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\u001c2\u0006\u0010\"\u001a\u00020#2\u000e\b\u0002\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\b2\u000e\b\u0002\u0010&\u001a\b\u0012\u0004\u0012\u00020 0\b\u001a,\u0010'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010,\u001a\u00020-\u001a\u001e\u0010.\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015\u001a\u001c\u0010/\u001a\u00020\u00062\f\u00100\u001a\b\u0012\u0004\u0012\u000202012\u0006\u0010\u0017\u001a\u00020\u0013\u001aT\u00103\u001a\u0002042\u0006\u00105\u001a\u0002062\u000e\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\u001c2\u0018\b\u0002\u00107\u001a\u0012\u0012\u0004\u0012\u00020#\u0012\u0006\u0012\u0004\u0018\u000109\u0018\u0001082\u001a\b\u0002\u0010:\u001a\u0014\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00060;\u001a\u0012\u0010<\u001a\u0004\u0018\u00010=2\u0006\u0010>\u001a\u00020?H\u0000\u001a \u0010@\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020A0\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b\u001a0\u0010B\u001a\u00020\u000f2\u0006\u0010C\u001a\u0002062\u0006\u0010D\u001a\u00020E2\b\u0010F\u001a\u0004\u0018\u00010G2\u0006\u0010H\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019\u001a\u0018\u0010I\u001a\u00020\u00062\u0006\u0010J\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0013H\u0002\u001a\n\u0010K\u001a\u00020L*\u00020\u0013\u001a\n\u0010K\u001a\u00020L*\u00020M\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006N"}, d2 = {"annotatedCallableKind", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "getAnnotatedCallableKind", "(Lorg/jetbrains/kotlin/protobuf/MessageLite;)Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;", "createAnnotationStubs", "", "annotations", "", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithArgs;", "parent", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "createEmptyModifierListStub", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinModifierListStubImpl;", "createFileFacadeStub", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinFileStubImpl;", "packageProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "c", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "createFileStub", "packageFqName", "isScript", "", "createIncompatibleAbiVersionFileStub", "createModifierListStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "modifiers", "", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "createModifierListStubForDeclaration", OverlayablePolicy.NAME_flags, "", "flagsToTranslate", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/flags/FlagsToModifiers;", "additionalModifiers", "createMultifileClassStub", "header", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "partFiles", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "components", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderComponents;", "createPackageFacadeStub", "createStubForPackageName", "packageDirectiveStub", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinPlaceHolderStubImpl;", "Lorg/jetbrains/kotlin/psi/KtPackageDirective;", "createStubForTypeName", "Lorg/jetbrains/kotlin/psi/stubs/KotlinUserTypeStub;", "typeClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "upperBoundFun", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "bindTypeArguments", "Lkotlin/Function2;", "createStubOrigin", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;", "protoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "createTargetedAnnotationStubs", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithTarget;", "createTopLevelClassStub", "classId", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "context", "setupFileStub", "fileStub", "ref", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "Lorg/jetbrains/kotlin/name/Name;", "decompiler-to-stubs"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClsStubBuildingKt {
    public static final KotlinFileStubImpl createTopLevelClassStub(ClassId classId, ProtoBuf.Class classProto, SourceElement sourceElement, ClsStubBuilderContext context, boolean z) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(context, "context");
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        KotlinFileStubImpl kotlinFileStubImplCreateFileStub = createFileStub(packageFqName, z);
        ClassClsStubBuilderKt.createClassStub(kotlinFileStubImplCreateFileStub, classProto, context.getNameResolver(), classId, sourceElement, context);
        return kotlinFileStubImplCreateFileStub;
    }

    public static final KotlinFileStubImpl createPackageFacadeStub(ProtoBuf.Package packageProto, FqName packageFqName, ClsStubBuilderContext c) {
        Intrinsics.checkNotNullParameter(packageProto, "packageProto");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(c, "c");
        KotlinFileStubImpl kotlinFileStubImplForFile = KotlinFileStubImpl.INSTANCE.forFile(packageFqName, false);
        setupFileStub(kotlinFileStubImplForFile, packageFqName);
        CallableClsStubBuilderKt.createPackageDeclarationsStubs(kotlinFileStubImplForFile, c, new ProtoContainer.Package(packageFqName, c.getNameResolver(), c.getTypeTable(), null), packageProto);
        return kotlinFileStubImplForFile;
    }

    public static final KotlinFileStubImpl createFileFacadeStub(ProtoBuf.Package packageProto, FqName facadeFqName, ClsStubBuilderContext c) {
        Intrinsics.checkNotNullParameter(packageProto, "packageProto");
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        Intrinsics.checkNotNullParameter(c, "c");
        FqName fqNameParent = facadeFqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        KotlinFileStubImpl kotlinFileStubImplForFileFacadeStub = KotlinFileStubImpl.INSTANCE.forFileFacadeStub(facadeFqName);
        setupFileStub(kotlinFileStubImplForFileFacadeStub, fqNameParent);
        NameResolver nameResolver = c.getNameResolver();
        TypeTable typeTable = c.getTypeTable();
        JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(ClassId.topLevel(facadeFqName));
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByClassId, "byClassId(...)");
        CallableClsStubBuilderKt.createPackageDeclarationsStubs(kotlinFileStubImplForFileFacadeStub, c, new ProtoContainer.Package(fqNameParent, nameResolver, typeTable, new JvmPackagePartSource(jvmClassNameByClassId, null, packageProto, c.getNameResolver(), null, false, null, null, 240, null)), packageProto);
        return kotlinFileStubImplForFileFacadeStub;
    }

    public static final KotlinFileStubImpl createMultifileClassStub(KotlinClassHeader header, List<? extends KotlinJvmBinaryClass> partFiles, FqName facadeFqName, ClsStubBuilderComponents clsStubBuilderComponents) {
        FqName fqNameParent;
        List listAsList;
        ClsStubBuilderComponents components = clsStubBuilderComponents;
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(partFiles, "partFiles");
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        Intrinsics.checkNotNullParameter(components, "components");
        String packageName = header.getPackageName();
        if (packageName != null) {
            fqNameParent = new FqName(packageName);
        } else {
            fqNameParent = facadeFqName.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        }
        String[] data = header.getData();
        ArrayList arrayList = null;
        if (data != null && (listAsList = ArraysKt.asList(data)) != null) {
            List list = listAsList;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(StringsKt.substringAfterLast$default((String) it.next(), '/', (String) null, 2, (Object) null));
            }
            arrayList = arrayList2;
        }
        KotlinFileStubImpl kotlinFileStubImplForMultifileClassStub = KotlinFileStubImpl.INSTANCE.forMultifileClassStub(fqNameParent, facadeFqName, arrayList);
        setupFileStub(kotlinFileStubImplForMultifileClassStub, fqNameParent);
        for (KotlinJvmBinaryClass kotlinJvmBinaryClass : partFiles) {
            KotlinClassHeader classHeader = kotlinJvmBinaryClass.getClassHeader();
            String[] data2 = classHeader.getData();
            Intrinsics.checkNotNull(data2);
            String[] strings = classHeader.getStrings();
            Intrinsics.checkNotNull(strings);
            Pair<JvmNameResolver, ProtoBuf.Package> packageDataFrom = JvmProtoBufUtil.readPackageDataFrom(data2, strings);
            JvmNameResolver jvmNameResolverComponent1 = packageDataFrom.component1();
            ProtoBuf.Package packageComponent2 = packageDataFrom.component2();
            JvmNameResolver jvmNameResolver = jvmNameResolverComponent1;
            ProtoBuf.TypeTable typeTable = packageComponent2.getTypeTable();
            Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
            ClsStubBuilderContext clsStubBuilderContextCreateContext = components.createContext(jvmNameResolver, fqNameParent, new TypeTable(typeTable));
            CallableClsStubBuilderKt.createPackageDeclarationsStubs(kotlinFileStubImplForMultifileClassStub, clsStubBuilderContextCreateContext, new ProtoContainer.Package(fqNameParent, clsStubBuilderContextCreateContext.getNameResolver(), clsStubBuilderContextCreateContext.getTypeTable(), new JvmPackagePartSource(kotlinJvmBinaryClass, packageComponent2, (NameResolver) jvmNameResolver, (IncompatibleVersionErrorData) null, false, (DeserializedContainerAbiStability) null, 56, (DefaultConstructorMarker) null)), packageComponent2);
            components = clsStubBuilderComponents;
        }
        return kotlinFileStubImplForMultifileClassStub;
    }

    public static final KotlinFileStubImpl createIncompatibleAbiVersionFileStub() {
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        return createFileStub(ROOT, false);
    }

    public static final KotlinFileStubImpl createFileStub(FqName packageFqName, boolean z) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        KotlinFileStubImpl kotlinFileStubImplForFile = KotlinFileStubImpl.INSTANCE.forFile(packageFqName, z);
        setupFileStub(kotlinFileStubImplForFile, packageFqName);
        return kotlinFileStubImplForFile;
    }

    private static final void setupFileStub(KotlinFileStubImpl kotlinFileStubImpl, FqName fqName) {
        KotlinFileStubImpl kotlinFileStubImpl2 = kotlinFileStubImpl;
        createStubForPackageName(new KotlinPlaceHolderStubImpl(kotlinFileStubImpl2, KtStubElementTypes.PACKAGE_DIRECTIVE), fqName);
        new KotlinPlaceHolderStubImpl(kotlinFileStubImpl2, KtStubElementTypes.IMPORT_LIST);
    }

    public static final void createStubForPackageName(KotlinPlaceHolderStubImpl<KtPackageDirective> packageDirectiveStub, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageDirectiveStub, "packageDirectiveStub");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        List<Name> listPathSegments = packageFqName.pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        createStubForPackageName$recCreateStubForPackageName(listPathSegments.listIterator(listPathSegments.size()), packageDirectiveStub);
    }

    private static final void createStubForPackageName$recCreateStubForPackageName(ListIterator<Name> listIterator, StubElement<? extends PsiElement> stubElement) {
        int iPreviousIndex = listIterator.previousIndex();
        if (iPreviousIndex != -1) {
            if (iPreviousIndex == 0) {
                Name namePrevious = listIterator.previous();
                Intrinsics.checkNotNullExpressionValue(namePrevious, "previous(...)");
                new KotlinNameReferenceExpressionStubImpl(stubElement, ref(namePrevious));
            } else {
                Name namePrevious2 = listIterator.previous();
                KotlinPlaceHolderStubImpl kotlinPlaceHolderStubImpl = new KotlinPlaceHolderStubImpl(stubElement, KtStubElementTypes.DOT_QUALIFIED_EXPRESSION);
                createStubForPackageName$recCreateStubForPackageName(listIterator, kotlinPlaceHolderStubImpl);
                Intrinsics.checkNotNull(namePrevious2);
                new KotlinNameReferenceExpressionStubImpl(kotlinPlaceHolderStubImpl, ref(namePrevious2));
            }
        }
    }

    public static /* synthetic */ KotlinUserTypeStub createStubForTypeName$default(ClassId classId, StubElement stubElement, Function1 function1, Function2 function2, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = null;
        }
        if ((i & 8) != 0) {
            function2 = new Function2<KotlinUserTypeStub, Integer, Unit>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.ClsStubBuildingKt.createStubForTypeName.1
                public final void invoke(KotlinUserTypeStub kotlinUserTypeStub, int i2) {
                    Intrinsics.checkNotNullParameter(kotlinUserTypeStub, "<anonymous parameter 0>");
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(KotlinUserTypeStub kotlinUserTypeStub, Integer num) {
                    invoke(kotlinUserTypeStub, num.intValue());
                    return Unit.INSTANCE;
                }
            };
        }
        return createStubForTypeName(classId, stubElement, function1, function2);
    }

    public static final KotlinUserTypeStub createStubForTypeName(ClassId typeClassId, StubElement<? extends PsiElement> parent, Function1<? super Integer, ? extends KotlinTypeBean> function1, Function2<? super KotlinUserTypeStub, ? super Integer, Unit> bindTypeArguments) {
        FqNameUnsafe unsafe;
        Intrinsics.checkNotNullParameter(typeClassId, "typeClassId");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(bindTypeArguments, "bindTypeArguments");
        boolean zIsLocal = typeClassId.isLocal();
        if (zIsLocal) {
            unsafe = StandardNames.FqNames.any;
        } else {
            unsafe = typeClassId.asSingleFqName().toUnsafe();
            Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        }
        List<Name> listPathSegments = unsafe.pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        List listAsReversedMutable = CollectionsKt.asReversedMutable(listPathSegments);
        listAsReversedMutable.isEmpty();
        return createStubForTypeName$recCreateStubForType(listAsReversedMutable, function1, listAsReversedMutable.size() - (zIsLocal ? 1 : typeClassId.getPackageFqName().pathSegments().size()), zIsLocal, bindTypeArguments, parent, 0);
    }

    private static final KotlinUserTypeStub createStubForTypeName$recCreateStubForType(List<Name> list, Function1<? super Integer, ? extends KotlinTypeBean> function1, int i, boolean z, Function2<? super KotlinUserTypeStub, ? super Integer, Unit> function2, StubElement<? extends PsiElement> stubElement, int i2) {
        Name name2 = list.get(i2);
        KotlinUserTypeStubImpl kotlinUserTypeStubImpl = new KotlinUserTypeStubImpl(stubElement, function1 != null ? function1.invoke(Integer.valueOf(i2)) : null);
        int i3 = i2 + 1;
        if (i3 < list.size()) {
            createStubForTypeName$recCreateStubForType(list, function1, i, z, function2, kotlinUserTypeStubImpl, i3);
        }
        KotlinUserTypeStubImpl kotlinUserTypeStubImpl2 = kotlinUserTypeStubImpl;
        Intrinsics.checkNotNull(name2);
        new KotlinNameReferenceExpressionStubImpl(kotlinUserTypeStubImpl2, ref(name2), i2 < i);
        if (!z) {
            function2.invoke(kotlinUserTypeStubImpl, Integer.valueOf(i2));
        }
        return kotlinUserTypeStubImpl;
    }

    public static /* synthetic */ KotlinModifierListStubImpl createModifierListStubForDeclaration$default(StubElement stubElement, int i, List list, List list2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((i2 & 8) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        return createModifierListStubForDeclaration(stubElement, i, list, list2);
    }

    public static final KotlinModifierListStubImpl createModifierListStubForDeclaration(StubElement<? extends PsiElement> parent, int i, List<? extends FlagsToModifiers> flagsToTranslate, List<KtModifierKeywordToken> additionalModifiers) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(flagsToTranslate, "flagsToTranslate");
        Intrinsics.checkNotNullParameter(additionalModifiers, "additionalModifiers");
        flagsToTranslate.isEmpty();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = flagsToTranslate.iterator();
        while (it.hasNext()) {
            KtModifierKeywordToken modifiers = ((FlagsToModifiers) it.next()).getModifiers(i);
            if (modifiers != null) {
                arrayList.add(modifiers);
            }
        }
        KotlinModifierListStubImpl kotlinModifierListStubImplCreateModifierListStub = createModifierListStub(parent, CollectionsKt.plus((Collection) arrayList, (Iterable) additionalModifiers));
        Intrinsics.checkNotNull(kotlinModifierListStubImplCreateModifierListStub);
        return kotlinModifierListStubImplCreateModifierListStub;
    }

    public static final KotlinModifierListStubImpl createModifierListStub(StubElement<? extends PsiElement> parent, final Collection<KtModifierKeywordToken> modifiers) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(modifiers, "modifiers");
        if (modifiers.isEmpty()) {
            return null;
        }
        return new KotlinModifierListStubImpl(parent, ModifierMaskUtils.computeMask(new Function1<KtModifierKeywordToken, Boolean>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.ClsStubBuildingKt.createModifierListStub.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtModifierKeywordToken it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(modifiers.contains(it));
            }
        }), KtStubElementTypes.MODIFIER_LIST);
    }

    public static final KotlinModifierListStubImpl createEmptyModifierListStub(KotlinStubBaseImpl<?> parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        return new KotlinModifierListStubImpl(parent, ModifierMaskUtils.computeMask(new Function1<KtModifierKeywordToken, Boolean>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.ClsStubBuildingKt.createEmptyModifierListStub.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtModifierKeywordToken it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return false;
            }
        }), KtStubElementTypes.MODIFIER_LIST);
    }

    public static final void createAnnotationStubs(List<AnnotationWithArgs> annotations, KotlinStubBaseImpl<?> parent) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(parent, "parent");
        List<AnnotationWithArgs> list = annotations;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new AnnotationWithTarget((AnnotationWithArgs) it.next(), null));
        }
        createTargetedAnnotationStubs(arrayList, parent);
    }

    public static final void createTargetedAnnotationStubs(List<AnnotationWithTarget> annotations, KotlinStubBaseImpl<?> parent) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (annotations.isEmpty()) {
            return;
        }
        for (AnnotationWithTarget annotationWithTarget : annotations) {
            AnnotationWithArgs annotationWithArgs = annotationWithTarget.getAnnotationWithArgs();
            AnnotationUseSiteTarget target = annotationWithTarget.getTarget();
            Name shortClassName = annotationWithArgs.getClassId().getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            KotlinAnnotationEntryStubImpl kotlinAnnotationEntryStubImpl = new KotlinAnnotationEntryStubImpl(parent, ref(shortClassName), false, annotationWithArgs.getArgs());
            if (target != null) {
                StringRef stringRefFromString = StringRef.fromString(target.name());
                Intrinsics.checkNotNull(stringRefFromString);
                new KotlinAnnotationUseSiteTargetStubImpl(kotlinAnnotationEntryStubImpl, stringRefFromString);
            }
            createStubForTypeName$default(annotationWithArgs.getClassId(), new KotlinPlaceHolderStubImpl(new KotlinPlaceHolderStubImpl(kotlinAnnotationEntryStubImpl, KtStubElementTypes.CONSTRUCTOR_CALLEE), KtStubElementTypes.TYPE_REFERENCE), null, null, 12, null);
        }
    }

    public static final KotlinStubOrigin createStubOrigin(ProtoContainer protoContainer) {
        Intrinsics.checkNotNullParameter(protoContainer, "protoContainer");
        if (protoContainer instanceof ProtoContainer.Package) {
            SourceElement source = protoContainer.getSource();
            if (source instanceof FacadeClassSource) {
                FacadeClassSource facadeClassSource = (FacadeClassSource) source;
                String internalName = facadeClassSource.getClassName().getInternalName();
                Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                JvmClassName facadeClassName = facadeClassSource.getFacadeClassName();
                String internalName2 = facadeClassName != null ? facadeClassName.getInternalName() : null;
                if (internalName2 != null) {
                    return new KotlinStubOrigin.MultiFileFacade(internalName, internalName2);
                }
                return new KotlinStubOrigin.Facade(internalName);
            }
        }
        return null;
    }

    public static final AnnotatedCallableKind getAnnotatedCallableKind(MessageLite messageLite) {
        Intrinsics.checkNotNullParameter(messageLite, "<this>");
        if (messageLite instanceof ProtoBuf.Property) {
            return AnnotatedCallableKind.PROPERTY;
        }
        if (messageLite instanceof ProtoBuf.Function ? true : messageLite instanceof ProtoBuf.Constructor) {
            return AnnotatedCallableKind.FUNCTION;
        }
        throw new IllegalStateException("Unsupported message: " + messageLite);
    }

    public static final StringRef ref(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<this>");
        StringRef stringRefFromString = StringRef.fromString(name2.asString());
        Intrinsics.checkNotNull(stringRefFromString);
        return stringRefFromString;
    }

    public static final StringRef ref(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        StringRef stringRefFromString = StringRef.fromString(fqName.asString());
        Intrinsics.checkNotNull(stringRefFromString);
        return stringRefFromString;
    }
}
