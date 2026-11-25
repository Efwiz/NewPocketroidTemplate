package org.jetbrains.kotlin.analysis.decompiler.stub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsKt;
import org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsToModifiers;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinFunctionStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinModifierListStubImpl;
import org.jetbrains.kotlin.serialization.deserialization.AnnotatedCallableKind;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: CallableClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001aH\u0016J \u0010\u001c\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u0016R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0010R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/FunctionClsStubBuilder;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/CallableClsStubBuilder;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "outerContext", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "protoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "functionProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;)V", "contextReceiverTypes", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "getContextReceiverTypes", "()Ljava/util/List;", "receiverAnnotations", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithTarget;", "getReceiverAnnotations", "receiverType", "getReceiverType", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "returnType", "getReturnType", "createModifierListStub", "", "createValueParameterList", "doCreateCallableStub", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FunctionClsStubBuilder extends CallableClsStubBuilder {
    private final ProtoBuf.Function functionProto;

    /* JADX WARN: Illegal instructions before constructor call */
    public FunctionClsStubBuilder(StubElement<? extends PsiElement> parent, ClsStubBuilderContext outerContext, ProtoContainer protoContainer, ProtoBuf.Function functionProto) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(protoContainer, "protoContainer");
        Intrinsics.checkNotNullParameter(functionProto, "functionProto");
        List<ProtoBuf.TypeParameter> typeParameterList = functionProto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        super(parent, outerContext, protoContainer, typeParameterList);
        this.functionProto = functionProto;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public ProtoBuf.Type getReceiverType() {
        return ProtoTypeTableUtilKt.receiverType(this.functionProto, getC().getTypeTable());
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public List<AnnotationWithTarget> getReceiverAnnotations() {
        List<AnnotationWithArgs> listLoadExtensionReceiverParameterAnnotations = getC().getComponents().getAnnotationLoader().loadExtensionReceiverParameterAnnotations(getProtoContainer(), this.functionProto, AnnotatedCallableKind.FUNCTION);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listLoadExtensionReceiverParameterAnnotations, 10));
        Iterator<T> it = listLoadExtensionReceiverParameterAnnotations.iterator();
        while (it.hasNext()) {
            arrayList.add(new AnnotationWithTarget((AnnotationWithArgs) it.next(), AnnotationUseSiteTarget.RECEIVER));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public ProtoBuf.Type getReturnType() {
        return ProtoTypeTableUtilKt.returnType(this.functionProto, getC().getTypeTable());
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public List<ProtoBuf.Type> getContextReceiverTypes() {
        return ProtoTypeTableUtilKt.contextReceiverTypes(this.functionProto, getC().getTypeTable());
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public void createValueParameterList() {
        TypeClsStubBuilder typeStubBuilder = getTypeStubBuilder();
        StubElement<? extends PsiElement> callableStub = getCallableStub();
        ProtoBuf.Function function = this.functionProto;
        List<ProtoBuf.ValueParameter> valueParameterList = function.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
        TypeClsStubBuilder.createValueParameterListStub$default(typeStubBuilder, callableStub, function, valueParameterList, getProtoContainer(), null, 16, null);
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public void createModifierListStub() {
        KotlinModifierListStubImpl kotlinModifierListStubImplCreateModifierListStubForDeclaration$default = ClsStubBuildingKt.createModifierListStubForDeclaration$default(getCallableStub(), this.functionProto.getFlags(), CollectionsKt.plus((Collection) CollectionsKt.listOf((Object[]) new FlagsToModifiers[]{FlagsKt.getVISIBILITY(), FlagsKt.getOPERATOR(), FlagsKt.getINFIX(), FlagsKt.getEXTERNAL_FUN(), FlagsKt.getINLINE(), FlagsKt.getTAILREC(), FlagsKt.getSUSPEND(), FlagsKt.getEXPECT_FUNCTION()}), (Iterable) (isTopLevel() ? CollectionsKt.emptyList() : CollectionsKt.listOf(FlagsKt.getMODALITY()))), null, 8, null);
        if (Flags.HAS_ANNOTATIONS.get(this.functionProto.getFlags()).booleanValue()) {
            ClsStubBuildingKt.createAnnotationStubs(getC().getComponents().getAnnotationLoader().loadCallableAnnotations(getProtoContainer(), this.functionProto, AnnotatedCallableKind.FUNCTION), kotlinModifierListStubImplCreateModifierListStubForDeclaration$default);
        }
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public StubElement<? extends PsiElement> doCreateCallableStub(StubElement<? extends PsiElement> parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Name name2 = NameResolverUtilKt.getName(getC().getNameResolver(), this.functionProto.getName());
        boolean zHasContract = this.functionProto.hasContract();
        StringRef stringRefRef = ClsStubBuildingKt.ref(name2);
        boolean zIsTopLevel = isTopLevel();
        FqName fqNameChild = getC().getContainerFqName().child(name2);
        boolean zHasReceiver = ProtoTypeTableUtilKt.hasReceiver(this.functionProto);
        boolean z = Flags.MODALITY.get(this.functionProto.getFlags()) != ProtoBuf.Modality.ABSTRACT;
        List<ProtoBuf.TypeParameter> typeParameterList = this.functionProto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        return new KotlinFunctionStubImpl(parent, stringRefRef, zIsTopLevel, fqNameChild, zHasReceiver, true, z, !typeParameterList.isEmpty(), zHasContract, zHasContract ? new ClsContractBuilder(getC(), getTypeStubBuilder()).loadContract(this.functionProto) : null, ClsStubBuildingKt.createStubOrigin(getProtoContainer()));
    }
}
