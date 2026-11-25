package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.asJava.builder.LightMemberOriginForDeclaration;
import org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethodBuilder;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;

/* JADX INFO: compiled from: ultraLightMethods.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010+\u001a\u00020\u000eH\u0014J%\u0010,\u001a\b\u0012\u0004\u0012\u0002H-0%\"\u0004\b\u0000\u0010-2\u000e\b\u0004\u0010.\u001a\b\u0012\u0004\u0012\u0002H-0/H\u0082\bJ\b\u00100\u001a\u00020\u0014H\u0016R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0012\u001a\u0004\b\u0015\u0010\u0016R\u001b\u0010\u0018\u001a\u00020\u00198VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u0012\u001a\u0004\b\u001a\u0010\u001bR!\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u0012\u001a\u0004\b \u0010!R\u0018\u0010#\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030%0$X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010&\u001a\u0004\u0018\u00010'8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\u0012\u001a\u0004\b(\u0010)¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethodForDescriptor;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "methodDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightMethodBuilder;", "lightMemberOrigin", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "containingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lcom/intellij/psi/impl/light/LightMethodBuilder;Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;)V", "_buildTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "get_buildTypeParameterList", "()Lcom/intellij/psi/PsiTypeParameterList;", "_buildTypeParameterList$delegate", "Lkotlin/Lazy;", "_throwsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "get_throwsList", "()Lcom/intellij/psi/PsiReferenceList;", "_throwsList$delegate", "checkNeedToErasureParametersTypes", "", "getCheckNeedToErasureParametersTypes", "()Z", "checkNeedToErasureParametersTypes$delegate", "givenAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "getGivenAnnotations", "()Ljava/util/List;", "givenAnnotations$delegate", "lazyInitializers", "", "Lkotlin/Lazy;", "qualifiedNameForNullabilityAnnotation", "", "getQualifiedNameForNullabilityAnnotation", "()Ljava/lang/String;", "qualifiedNameForNullabilityAnnotation$delegate", "buildTypeParameterList", "getAndAddLazy", ExifInterface.GPS_DIRECTION_TRUE, "initializer", "Lkotlin/Function0;", "getThrowsList", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightMethodForDescriptor extends KtUltraLightMethod {

    /* JADX INFO: renamed from: _buildTypeParameterList$delegate, reason: from kotlin metadata */
    private final Lazy _buildTypeParameterList;

    /* JADX INFO: renamed from: _throwsList$delegate, reason: from kotlin metadata */
    private final Lazy _throwsList;

    /* JADX INFO: renamed from: checkNeedToErasureParametersTypes$delegate, reason: from kotlin metadata */
    private final Lazy checkNeedToErasureParametersTypes;

    /* JADX INFO: renamed from: givenAnnotations$delegate, reason: from kotlin metadata */
    private final Lazy givenAnnotations;
    private final List<Lazy<?>> lazyInitializers;

    /* JADX INFO: renamed from: qualifiedNameForNullabilityAnnotation$delegate, reason: from kotlin metadata */
    private final Lazy qualifiedNameForNullabilityAnnotation;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightMethodForDescriptor(final FunctionDescriptor methodDescriptor, LightMethodBuilder delegate, LightMemberOriginForDeclaration lightMemberOriginForDeclaration, final KtUltraLightSupport support, KtUltraLightClass containingClass) {
        PsiPrimitiveType psiPrimitiveTypeMapType;
        super(delegate, lightMemberOriginForDeclaration, support, containingClass, 6);
        Intrinsics.checkNotNullParameter(methodDescriptor, "methodDescriptor");
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        ArrayList arrayList = new ArrayList();
        this.lazyInitializers = arrayList;
        Lazy lazyLazyPub = ImplUtilsKt.lazyPub(new Function0<PsiTypeParameterList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightMethodForDescriptor$special$$inlined$getAndAddLazy$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiTypeParameterList invoke() {
                return UltraLightUtilsKt.buildTypeParameterListForDescriptor(methodDescriptor, this, support);
            }
        });
        arrayList.add(lazyLazyPub);
        this._buildTypeParameterList = lazyLazyPub;
        Lazy lazyLazyPub2 = ImplUtilsKt.lazyPub(new Function0<PsiReferenceList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightMethodForDescriptor$special$$inlined$getAndAddLazy$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiReferenceList invoke() {
                return this.this$0.computeThrowsList(methodDescriptor);
            }
        });
        arrayList.add(lazyLazyPub2);
        this._throwsList = lazyLazyPub2;
        Lazy lazyLazyPub3 = ImplUtilsKt.lazyPub(new Function0<List<? extends KtLightAbstractAnnotation>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightMethodForDescriptor$special$$inlined$getAndAddLazy$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KtLightAbstractAnnotation> invoke() {
                return UltraLightAnnotationsKt.obtainLightAnnotations(methodDescriptor, this);
            }
        });
        arrayList.add(lazyLazyPub3);
        this.givenAnnotations = lazyLazyPub3;
        Lazy lazyLazyPub4 = ImplUtilsKt.lazyPub(new Function0<String>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightMethodForDescriptor$special$$inlined$getAndAddLazy$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return this.this$0.computeQualifiedNameForNullabilityAnnotation(methodDescriptor.getReturnType());
            }
        });
        arrayList.add(lazyLazyPub4);
        this.qualifiedNameForNullabilityAnnotation = lazyLazyPub4;
        Lazy lazyLazyPub5 = ImplUtilsKt.lazyPub(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightMethodForDescriptor$special$$inlined$getAndAddLazy$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(this.this$0.computeCheckNeedToErasureParametersTypes(methodDescriptor));
            }
        });
        arrayList.add(lazyLazyPub5);
        this.checkNeedToErasureParametersTypes = lazyLazyPub5;
        ReceiverParameterDescriptor extensionReceiverParameter = methodDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            delegate.addParameter(new KtUltraLightParameterForDescriptor(extensionReceiverParameter, support, this));
        }
        for (ValueParameterDescriptor valueParameterDescriptor : methodDescriptor.getValueParameters()) {
            Intrinsics.checkNotNull(valueParameterDescriptor);
            delegate.addParameter(new KtUltraLightParameterForDescriptor(valueParameterDescriptor, support, this));
        }
        if (methodDescriptor instanceof ConstructorDescriptor) {
            delegate.setConstructor(true);
            psiPrimitiveTypeMapType = PsiType.VOID;
        } else {
            psiPrimitiveTypeMapType = UltraLightUtilsKt.mapType(support, methodDescriptor.getReturnType(), this, new Function2<KotlinTypeMapper, JvmSignatureWriter, Unit>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightMethodForDescriptor$returnType$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(KotlinTypeMapper kotlinTypeMapper, JvmSignatureWriter jvmSignatureWriter) {
                    invoke2(kotlinTypeMapper, jvmSignatureWriter);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(KotlinTypeMapper typeMapper, JvmSignatureWriter signatureWriter) {
                    Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
                    Intrinsics.checkNotNullParameter(signatureWriter, "signatureWriter");
                    typeMapper.mapReturnType(methodDescriptor, signatureWriter);
                }
            });
        }
        delegate.setMethodReturnType(psiPrimitiveTypeMapType);
        List<Lazy<?>> list = this.lazyInitializers;
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            ((Lazy) it.next()).getValue();
        }
        list.clear();
    }

    private final PsiTypeParameterList get_buildTypeParameterList() {
        return (PsiTypeParameterList) this._buildTypeParameterList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightMethod, org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl
    protected PsiTypeParameterList buildTypeParameterList() {
        return get_buildTypeParameterList();
    }

    private final PsiReferenceList get_throwsList() {
        return (PsiReferenceList) this._throwsList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiReferenceList getThrowsList() {
        return get_throwsList();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public List<KtLightAbstractAnnotation> getGivenAnnotations() {
        return (List) this.givenAnnotations.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public String getQualifiedNameForNullabilityAnnotation() {
        return (String) this.qualifiedNameForNullabilityAnnotation.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightMethod
    public boolean getCheckNeedToErasureParametersTypes() {
        return ((Boolean) this.checkNeedToErasureParametersTypes.getValue()).booleanValue();
    }
}
