package org.jetbrains.kotlin.analysis.decompiler.stub;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinModifierListStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeAliasStubImpl;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: typeAliasClsStubBuilding.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"createTypeAliasStub", "", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "typeAliasProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "protoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "outerContext", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "decompiler-to-stubs"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeAliasClsStubBuildingKt {
    public static final void createTypeAliasStub(StubElement<? extends PsiElement> parent, ProtoBuf.TypeAlias typeAliasProto, ProtoContainer protoContainer, ClsStubBuilderContext outerContext) {
        ClassId classIdCreateNestedClassId;
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(typeAliasProto, "typeAliasProto");
        Intrinsics.checkNotNullParameter(protoContainer, "protoContainer");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        List<ProtoBuf.TypeParameter> typeParameterList = typeAliasProto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        ClsStubBuilderContext clsStubBuilderContextChild$default = ClsStubBuilderContextKt.child$default(outerContext, typeParameterList, null, null, null, null, 30, null);
        Name name2 = NameResolverUtilKt.getName(clsStubBuilderContextChild$default.getNameResolver(), typeAliasProto.getName());
        if (protoContainer instanceof ProtoContainer.Class) {
            classIdCreateNestedClassId = ((ProtoContainer.Class) protoContainer).getClassId().createNestedClassId(name2);
        } else {
            if (!(protoContainer instanceof ProtoContainer.Package)) {
                throw new NoWhenBranchMatchedException();
            }
            classIdCreateNestedClassId = ClassId.topLevel(((ProtoContainer.Package) protoContainer).getFqName().child(name2));
        }
        ClassId classId = classIdCreateNestedClassId;
        Intrinsics.checkNotNull(classId);
        Name shortClassName = classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        StringRef stringRefRef = ClsStubBuildingKt.ref(shortClassName);
        FqName fqNameAsSingleFqName = classId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        KotlinTypeAliasStubImpl kotlinTypeAliasStubImpl = new KotlinTypeAliasStubImpl(parent, stringRefRef, ClsStubBuildingKt.ref(fqNameAsSingleFqName), classId, !classId.isNestedClass());
        KotlinModifierListStubImpl kotlinModifierListStubImplCreateModifierListStubForDeclaration = ClsStubBuildingKt.createModifierListStubForDeclaration(kotlinTypeAliasStubImpl, typeAliasProto.getFlags(), CollectionsKt.arrayListOf(FlagsKt.getVISIBILITY()), CollectionsKt.emptyList());
        TypeClsStubBuilder typeClsStubBuilder = new TypeClsStubBuilder(clsStubBuilderContextChild$default);
        List<ProtoBuf.TypeParameter> typeParameterList2 = typeAliasProto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList2, "getTypeParameterList(...)");
        typeClsStubBuilder.createTypeParameterListStub(kotlinTypeAliasStubImpl, typeParameterList2).isEmpty();
        Boolean bool = Flags.HAS_ANNOTATIONS.get(typeAliasProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (bool.booleanValue()) {
            List<ProtoBuf.Annotation> annotationList = typeAliasProto.getAnnotationList();
            Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
            List<ProtoBuf.Annotation> list = annotationList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (ProtoBuf.Annotation annotation : list) {
                AnnotationLoader<AnnotationWithArgs> annotationLoader = clsStubBuilderContextChild$default.getComponents().getAnnotationLoader();
                Intrinsics.checkNotNull(annotation);
                arrayList.add(annotationLoader.loadAnnotation(annotation, clsStubBuilderContextChild$default.getNameResolver()));
            }
            ClsStubBuildingKt.createAnnotationStubs(arrayList, kotlinModifierListStubImplCreateModifierListStubForDeclaration);
        }
        TypeClsStubBuilder.createTypeReferenceStub$default(typeClsStubBuilder, kotlinTypeAliasStubImpl, ProtoTypeTableUtilKt.underlyingType(typeAliasProto, clsStubBuilderContextChild$default.getTypeTable()), null, null, 12, null);
    }
}
