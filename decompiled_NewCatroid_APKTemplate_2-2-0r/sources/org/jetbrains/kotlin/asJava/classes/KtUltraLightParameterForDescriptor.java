package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.KtDescriptorUtilKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ultraLightParameters.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010'\u001a\u00020\u00102\b\u0010(\u001a\u0004\u0018\u00010)H\u0096\u0002J%\u0010*\u001a\b\u0012\u0004\u0012\u0002H+0!\"\u0004\b\u0000\u0010+2\u000e\b\u0004\u0010,\u001a\b\u0012\u0004\u0012\u0002H+0-H\u0082\bJ\b\u0010.\u001a\u00020\u0015H\u0016J\b\u0010/\u001a\u00020\nH\u0016J\b\u00100\u001a\u00020\u0010H\u0016R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u000e\u001a\u0004\b\u0016\u0010\u0017R!\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u000e\u001a\u0004\b\u001c\u0010\u001dR\u0018\u0010\u001f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030!0 X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\"\u001a\u0004\u0018\u00010#8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\u000e\u001a\u0004\b$\u0010%¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightParameterForDescriptor;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightParameter;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "method", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "(Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;)V", "_index", "", "get_index", "()I", "_index$delegate", "Lkotlin/Lazy;", "_isVarArgs", "", "get_isVarArgs", "()Z", "_isVarArgs$delegate", "_parameterType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "get_parameterType", "()Lcom/intellij/psi/PsiType;", "_parameterType$delegate", "givenAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "getGivenAnnotations", "()Ljava/util/List;", "givenAnnotations$delegate", "lazyInitializers", "", "Lkotlin/Lazy;", "qualifiedNameForNullabilityAnnotation", "", "getQualifiedNameForNullabilityAnnotation", "()Ljava/lang/String;", "qualifiedNameForNullabilityAnnotation$delegate", Namer.EQUALS_METHOD_NAME, "other", "", "getAndAddLazy", ExifInterface.GPS_DIRECTION_TRUE, "initializer", "Lkotlin/Function0;", "getType", "hashCode", "isVarArgs", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightParameterForDescriptor extends KtUltraLightParameter {

    /* JADX INFO: renamed from: _index$delegate, reason: from kotlin metadata */
    private final Lazy _index;

    /* JADX INFO: renamed from: _isVarArgs$delegate, reason: from kotlin metadata */
    private final Lazy _isVarArgs;

    /* JADX INFO: renamed from: _parameterType$delegate, reason: from kotlin metadata */
    private final Lazy _parameterType;

    /* JADX INFO: renamed from: givenAnnotations$delegate, reason: from kotlin metadata */
    private final Lazy givenAnnotations;
    private final List<Lazy<?>> lazyInitializers;

    /* JADX INFO: renamed from: qualifiedNameForNullabilityAnnotation$delegate, reason: from kotlin metadata */
    private final Lazy qualifiedNameForNullabilityAnnotation;

    /* JADX WARN: Illegal instructions before constructor call */
    public KtUltraLightParameterForDescriptor(final ParameterDescriptor descriptor, KtUltraLightSupport support, KtUltraLightMethod method) {
        String identifier;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(method, "method");
        if (descriptor.getShortName().isSpecial()) {
            identifier = "$self";
        } else {
            identifier = descriptor.getShortName().getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        }
        super(identifier, null, support, method);
        ArrayList arrayList = new ArrayList();
        this.lazyInitializers = arrayList;
        Lazy lazyLazyPub = ImplUtilsKt.lazyPub(new Function0<String>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightParameterForDescriptor$special$$inlined$getAndAddLazy$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return this.this$0.computeQualifiedNameForNullabilityAnnotation(descriptor.getType());
            }
        });
        arrayList.add(lazyLazyPub);
        this.qualifiedNameForNullabilityAnnotation = lazyLazyPub;
        Lazy lazyLazyPub2 = ImplUtilsKt.lazyPub(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightParameterForDescriptor$special$$inlined$getAndAddLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                ParameterDescriptor parameterDescriptor = descriptor;
                ValueParameterDescriptor valueParameterDescriptor = parameterDescriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) parameterDescriptor : null;
                return Boolean.valueOf((valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null) != null);
            }
        });
        arrayList.add(lazyLazyPub2);
        this._isVarArgs = lazyLazyPub2;
        Lazy lazyLazyPub3 = ImplUtilsKt.lazyPub(new Function0<List<? extends KtLightAbstractAnnotation>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightParameterForDescriptor$special$$inlined$getAndAddLazy$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KtLightAbstractAnnotation> invoke() {
                return UltraLightAnnotationsKt.obtainLightAnnotations(descriptor, this);
            }
        });
        arrayList.add(lazyLazyPub3);
        this.givenAnnotations = lazyLazyPub3;
        Lazy lazyLazyPub4 = ImplUtilsKt.lazyPub(new Function0<PsiType>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightParameterForDescriptor$special$$inlined$getAndAddLazy$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiType invoke() {
                KtUltraLightParameterForDescriptor ktUltraLightParameterForDescriptor = this.this$0;
                KotlinType type = descriptor.getType();
                DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
                return ktUltraLightParameterForDescriptor.computeParameterType(type, containingDeclaration instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) containingDeclaration : null);
            }
        });
        arrayList.add(lazyLazyPub4);
        this._parameterType = lazyLazyPub4;
        Lazy lazyLazyPub5 = ImplUtilsKt.lazyPub(new Function0<Integer>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightParameterForDescriptor$special$$inlined$getAndAddLazy$5
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Integer invoke() {
                return Integer.valueOf(KtDescriptorUtilKt.indexOrMinusOne(descriptor));
            }
        });
        arrayList.add(lazyLazyPub5);
        this._index = lazyLazyPub5;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Lazy) it.next()).getValue();
        }
        arrayList.clear();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public String getQualifiedNameForNullabilityAnnotation() {
        return (String) this.qualifiedNameForNullabilityAnnotation.getValue();
    }

    private final boolean get_isVarArgs() {
        return ((Boolean) this._isVarArgs.getValue()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter, org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: isVarArgs */
    public boolean getMyVarArgs() {
        return get_isVarArgs();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public List<KtLightAbstractAnnotation> getGivenAnnotations() {
        return (List) this.givenAnnotations.getValue();
    }

    private final PsiType get_parameterType() {
        return (PsiType) this._parameterType.getValue();
    }

    private final int get_index() {
        return ((Number) this._index.getValue()).intValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    public PsiType getType() {
        return get_parameterType();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter
    public boolean equals(Object other) {
        if (other != this) {
            if (other instanceof KtUltraLightParameterForDescriptor) {
                KtUltraLightParameterForDescriptor ktUltraLightParameterForDescriptor = (KtUltraLightParameterForDescriptor) other;
                if (!Intrinsics.areEqual(ktUltraLightParameterForDescriptor.getMyName(), getMyName()) || ktUltraLightParameterForDescriptor.get_index() != get_index() || !Intrinsics.areEqual(ktUltraLightParameterForDescriptor.getMethod(), getMethod())) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter
    public int hashCode() {
        return getMyName().hashCode();
    }
}
