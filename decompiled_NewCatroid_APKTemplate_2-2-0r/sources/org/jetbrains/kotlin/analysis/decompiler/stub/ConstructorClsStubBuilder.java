package org.jetbrains.kotlin.analysis.decompiler.stub;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.elements.KtPrimaryConstructorElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtSecondaryConstructorElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinConstructorStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinModifierListStubImpl;
import org.jetbrains.kotlin.serialization.deserialization.AnnotatedCallableKind;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: CallableClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001aH\u0016J \u0010\u001c\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0010R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/ConstructorClsStubBuilder;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/CallableClsStubBuilder;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "outerContext", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "protoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "constructorProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;)V", "contextReceiverTypes", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "getContextReceiverTypes", "()Ljava/util/List;", "receiverAnnotations", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithTarget;", "getReceiverAnnotations", "receiverType", "getReceiverType", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "returnType", "getReturnType", "createModifierListStub", "", "createValueParameterList", "doCreateCallableStub", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ConstructorClsStubBuilder extends CallableClsStubBuilder {
    private final ProtoBuf.Constructor constructorProto;

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public ProtoBuf.Type getReceiverType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public ProtoBuf.Type getReturnType() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConstructorClsStubBuilder(StubElement<? extends PsiElement> parent, ClsStubBuilderContext outerContext, ProtoContainer protoContainer, ProtoBuf.Constructor constructorProto) {
        super(parent, outerContext, protoContainer, CollectionsKt.emptyList());
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(protoContainer, "protoContainer");
        Intrinsics.checkNotNullParameter(constructorProto, "constructorProto");
        this.constructorProto = constructorProto;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public List<AnnotationWithTarget> getReceiverAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public List<ProtoBuf.Type> getContextReceiverTypes() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public void createValueParameterList() {
        TypeClsStubBuilder typeStubBuilder = getTypeStubBuilder();
        StubElement<? extends PsiElement> callableStub = getCallableStub();
        ProtoBuf.Constructor constructor = this.constructorProto;
        List<ProtoBuf.ValueParameter> valueParameterList = constructor.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
        TypeClsStubBuilder.createValueParameterListStub$default(typeStubBuilder, callableStub, constructor, valueParameterList, getProtoContainer(), null, 16, null);
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public void createModifierListStub() {
        KotlinModifierListStubImpl kotlinModifierListStubImplCreateModifierListStubForDeclaration$default = ClsStubBuildingKt.createModifierListStubForDeclaration$default(getCallableStub(), this.constructorProto.getFlags(), CollectionsKt.listOf(FlagsKt.getVISIBILITY()), null, 8, null);
        if (Flags.HAS_ANNOTATIONS.get(this.constructorProto.getFlags()).booleanValue()) {
            ClsStubBuildingKt.createAnnotationStubs(getC().getComponents().getAnnotationLoader().loadCallableAnnotations(getProtoContainer(), this.constructorProto, AnnotatedCallableKind.FUNCTION), kotlinModifierListStubImplCreateModifierListStubForDeclaration$default);
        }
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder
    public StubElement<? extends PsiElement> doCreateCallableStub(StubElement<? extends PsiElement> parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ProtoContainer protoContainer = getProtoContainer();
        Intrinsics.checkNotNull(protoContainer, "null cannot be cast to non-null type org.jetbrains.kotlin.serialization.deserialization.ProtoContainer.Class");
        Name shortClassName = ((ProtoContainer.Class) protoContainer).getClassId().getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        StringRef stringRefRef = ClsStubBuildingKt.ref(shortClassName);
        Boolean bool = Flags.IS_SECONDARY.get(this.constructorProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (bool.booleanValue()) {
            KtSecondaryConstructorElementType SECONDARY_CONSTRUCTOR = KtStubElementTypes.SECONDARY_CONSTRUCTOR;
            Intrinsics.checkNotNullExpressionValue(SECONDARY_CONSTRUCTOR, "SECONDARY_CONSTRUCTOR");
            return new KotlinConstructorStubImpl(parent, SECONDARY_CONSTRUCTOR, stringRefRef, true, false);
        }
        KtPrimaryConstructorElementType PRIMARY_CONSTRUCTOR = KtStubElementTypes.PRIMARY_CONSTRUCTOR;
        Intrinsics.checkNotNullExpressionValue(PRIMARY_CONSTRUCTOR, "PRIMARY_CONSTRUCTOR");
        return new KotlinConstructorStubImpl(parent, PRIMARY_CONSTRUCTOR, stringRefRef, false, false);
    }
}
