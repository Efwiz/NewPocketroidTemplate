package org.jetbrains.kotlin.analysis.decompiler.stub;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: CallableClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B3\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ\u0006\u0010,\u001a\u00020-J\b\u0010.\u001a\u00020-H\u0014J\b\u0010/\u001a\u00020-H&J\b\u00100\u001a\u00020-H\u0002J\b\u00101\u001a\u00020-H\u0002J\b\u00102\u001a\u00020-H&J \u00103\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H&R\u0014\u0010\r\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR#\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00038DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\u00020\u001c8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001dR\u0014\u0010\u0007\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0018\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u0018R\u0014\u0010#\u001a\u0004\u0018\u00010\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u0004\u0018\u00010\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010%R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010(\u001a\u00020)X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/CallableClsStubBuilder;", "", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "outerContext", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "protoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "typeParameters", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;Ljava/util/List;)V", "c", "getC", "()Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "callableStub", "getCallableStub", "()Lcom/intellij/psi/stubs/StubElement;", "callableStub$delegate", "Lkotlin/Lazy;", "contextReceiverTypes", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "getContextReceiverTypes", "()Ljava/util/List;", "contextReceiversListStubBuilder", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ContextReceiversListStubBuilder;", "isTopLevel", "", "()Z", "getProtoContainer", "()Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "receiverAnnotations", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithTarget;", "getReceiverAnnotations", "receiverType", "getReceiverType", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "returnType", "getReturnType", "typeStubBuilder", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeClsStubBuilder;", "getTypeStubBuilder", "()Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeClsStubBuilder;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "", "createCallableSpecialParts", "createModifierListStub", "createReceiverTypeReferenceStub", "createReturnTypeStub", "createValueParameterList", "doCreateCallableStub", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CallableClsStubBuilder {
    private final ClsStubBuilderContext c;

    /* JADX INFO: renamed from: callableStub$delegate, reason: from kotlin metadata */
    private final Lazy callableStub;
    private final ContextReceiversListStubBuilder contextReceiversListStubBuilder;
    private final ProtoContainer protoContainer;
    private final List<ProtoBuf.TypeParameter> typeParameters;
    private final TypeClsStubBuilder typeStubBuilder;

    protected void createCallableSpecialParts() {
    }

    public abstract void createModifierListStub();

    public abstract void createValueParameterList();

    public abstract StubElement<? extends PsiElement> doCreateCallableStub(StubElement<? extends PsiElement> parent);

    public abstract List<ProtoBuf.Type> getContextReceiverTypes();

    public abstract List<AnnotationWithTarget> getReceiverAnnotations();

    public abstract ProtoBuf.Type getReceiverType();

    public abstract ProtoBuf.Type getReturnType();

    public CallableClsStubBuilder(final StubElement<? extends PsiElement> parent, ClsStubBuilderContext outerContext, ProtoContainer protoContainer, List<ProtoBuf.TypeParameter> typeParameters) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(protoContainer, "protoContainer");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        this.protoContainer = protoContainer;
        this.typeParameters = typeParameters;
        ClsStubBuilderContext clsStubBuilderContextChild$default = ClsStubBuilderContextKt.child$default(outerContext, typeParameters, null, null, null, null, 30, null);
        this.c = clsStubBuilderContextChild$default;
        this.typeStubBuilder = new TypeClsStubBuilder(clsStubBuilderContextChild$default);
        this.contextReceiversListStubBuilder = new ContextReceiversListStubBuilder(clsStubBuilderContextChild$default);
        this.callableStub = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<StubElement<? extends PsiElement>>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder$callableStub$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final StubElement<? extends PsiElement> invoke() {
                return this.this$0.doCreateCallableStub(parent);
            }
        });
    }

    protected final ProtoContainer getProtoContainer() {
        return this.protoContainer;
    }

    protected final ClsStubBuilderContext getC() {
        return this.c;
    }

    protected final TypeClsStubBuilder getTypeStubBuilder() {
        return this.typeStubBuilder;
    }

    protected final boolean isTopLevel() {
        return this.protoContainer instanceof ProtoContainer.Package;
    }

    protected final StubElement<? extends PsiElement> getCallableStub() {
        return (StubElement) this.callableStub.getValue();
    }

    public final void build() {
        this.contextReceiversListStubBuilder.createContextReceiverStubs(getCallableStub(), getContextReceiverTypes());
        createModifierListStub();
        List<Pair<Name, ProtoBuf.Type>> listCreateTypeParameterListStub = this.typeStubBuilder.createTypeParameterListStub(getCallableStub(), this.typeParameters);
        createReceiverTypeReferenceStub();
        createValueParameterList();
        createReturnTypeStub();
        this.typeStubBuilder.createTypeConstraintListStub(getCallableStub(), listCreateTypeParameterListStub);
        createCallableSpecialParts();
    }

    private final void createReceiverTypeReferenceStub() {
        ProtoBuf.Type receiverType = getReceiverType();
        if (receiverType != null) {
            TypeClsStubBuilder.createTypeReferenceStub$default(this.typeStubBuilder, getCallableStub(), receiverType, new PropertyReference0(this) { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilder$createReceiverTypeReferenceStub$1$1
                @Override // kotlin.jvm.internal.CallableReference
                public String getSignature() {
                    return "getReceiverAnnotations()Ljava/util/List;";
                }

                @Override // kotlin.reflect.KProperty0
                public Object get() {
                    return ((CallableClsStubBuilder) this.receiver).getReceiverAnnotations();
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public String getName() {
                    return "receiverAnnotations";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(CallableClsStubBuilder.class);
                }
            }, null, 8, null);
        }
    }

    private final void createReturnTypeStub() {
        ProtoBuf.Type returnType = getReturnType();
        if (returnType != null) {
            TypeClsStubBuilder.createTypeReferenceStub$default(this.typeStubBuilder, getCallableStub(), returnType, null, null, 12, null);
        }
    }
}
