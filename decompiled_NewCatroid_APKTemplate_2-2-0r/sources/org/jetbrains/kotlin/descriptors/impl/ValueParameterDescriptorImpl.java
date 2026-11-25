package org.jetbrains.kotlin.descriptors.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: ValueParameterDescriptorImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 42\u00020\u00012\u00020\u0002:\u000245Ba\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u000f\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020#H\u0016J \u0010$\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u0007H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\n\u0010(\u001a\u0004\u0018\u00010)H\u0016J\b\u0010*\u001a\u00020\u0004H\u0016J\b\u0010+\u001a\u00020\u0002H\u0016J\u000e\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00020-H\u0016J\b\u0010.\u001a\u00020/H\u0016J\b\u00100\u001a\u00020\u000fH\u0016J\u0010\u00101\u001a\u00020\u00022\u0006\u00102\u001a\u000203H\u0016R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0010\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0018R\u0014\u0010\u0011\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0018R\u000e\u0010\u0005\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/ValueParameterDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/impl/VariableDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "original", "index", "", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "name", "Lorg/jetbrains/kotlin/name/Name;", "outType", "Lorg/jetbrains/kotlin/types/KotlinType;", "declaresDefaultValue", "", "isCrossinline", "isNoinline", "varargElementType", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;ILorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/types/KotlinType;ZZZLorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "getIndex", "()I", "()Z", "getVarargElementType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "cleanCompileTimeInitializerCache", "", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newOwner", "newName", "newIndex", "getCompileTimeInitializer", "", "getContainingDeclaration", "getOriginal", "getOverriddenDescriptors", "", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isVar", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "Companion", "WithDestructuringDeclaration", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ValueParameterDescriptorImpl extends VariableDescriptorImpl implements ValueParameterDescriptor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean declaresDefaultValue;
    private final int index;
    private final boolean isCrossinline;
    private final boolean isNoinline;
    private final ValueParameterDescriptor original;
    private final KotlinType varargElementType;

    @JvmStatic
    public static final ValueParameterDescriptorImpl createWithDestructuringDeclarations(CallableDescriptor callableDescriptor, ValueParameterDescriptor valueParameterDescriptor, int i, Annotations annotations, Name name2, KotlinType kotlinType, boolean z, boolean z2, boolean z3, KotlinType kotlinType2, SourceElement sourceElement, Function0<? extends List<? extends VariableDescriptor>> function0) {
        return INSTANCE.createWithDestructuringDeclarations(callableDescriptor, valueParameterDescriptor, i, annotations, name2, kotlinType, z, z2, z3, kotlinType2, sourceElement, function0);
    }

    @JvmStatic
    public static final List<VariableDescriptor> getDestructuringVariablesOrNull(ValueParameterDescriptor valueParameterDescriptor) {
        return INSTANCE.getDestructuringVariablesOrNull(valueParameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
    }

    public Void getCompileTimeInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ConstantValue mo6754getCompileTimeInitializer() {
        return (ConstantValue) getCompileTimeInitializer();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return ValueParameterDescriptor.DefaultImpls.isLateInit(this);
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    public int getIndex() {
        return this.index;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    /* JADX INFO: renamed from: isCrossinline, reason: from getter */
    public boolean getIsCrossinline() {
        return this.isCrossinline;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    /* JADX INFO: renamed from: isNoinline, reason: from getter */
    public boolean getIsNoinline() {
        return this.isNoinline;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    public KotlinType getVarargElementType() {
        return this.varargElementType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ValueParameterDescriptorImpl(CallableDescriptor containingDeclaration, ValueParameterDescriptor valueParameterDescriptor, int i, Annotations annotations, Name name2, KotlinType outType, boolean z, boolean z2, boolean z3, KotlinType kotlinType, SourceElement source) {
        super(containingDeclaration, annotations, name2, outType, source);
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(outType, "outType");
        Intrinsics.checkNotNullParameter(source, "source");
        this.index = i;
        this.declaresDefaultValue = z;
        this.isCrossinline = z2;
        this.isNoinline = z3;
        this.varargElementType = kotlinType;
        this.original = valueParameterDescriptor == null ? this : valueParameterDescriptor;
    }

    /* JADX INFO: compiled from: ValueParameterDescriptorImpl.kt */
    @Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jz\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0014\u0010\u0018\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001a\u0018\u00010\u0019H\u0007J\u0018\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001a2\u0006\u0010\u001d\u001a\u00020\bH\u0007¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/ValueParameterDescriptorImpl$Companion;", "", "()V", "createWithDestructuringDeclarations", "Lorg/jetbrains/kotlin/descriptors/impl/ValueParameterDescriptorImpl;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "original", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "index", "", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "name", "Lorg/jetbrains/kotlin/name/Name;", "outType", "Lorg/jetbrains/kotlin/types/KotlinType;", "declaresDefaultValue", "", "isCrossinline", "isNoinline", "varargElementType", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "destructuringVariables", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "getDestructuringVariablesOrNull", "valueParameterDescriptor", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final List<VariableDescriptor> getDestructuringVariablesOrNull(ValueParameterDescriptor valueParameterDescriptor) {
            Intrinsics.checkNotNullParameter(valueParameterDescriptor, "valueParameterDescriptor");
            WithDestructuringDeclaration withDestructuringDeclaration = valueParameterDescriptor instanceof WithDestructuringDeclaration ? (WithDestructuringDeclaration) valueParameterDescriptor : null;
            if (withDestructuringDeclaration != null) {
                return withDestructuringDeclaration.getDestructuringVariables();
            }
            return null;
        }

        @JvmStatic
        public final ValueParameterDescriptorImpl createWithDestructuringDeclarations(CallableDescriptor containingDeclaration, ValueParameterDescriptor original, int index, Annotations annotations, Name name2, KotlinType outType, boolean declaresDefaultValue, boolean isCrossinline, boolean isNoinline, KotlinType varargElementType, SourceElement source, Function0<? extends List<? extends VariableDescriptor>> destructuringVariables) {
            Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(outType, "outType");
            Intrinsics.checkNotNullParameter(source, "source");
            if (destructuringVariables == null) {
                return new ValueParameterDescriptorImpl(containingDeclaration, original, index, annotations, name2, outType, declaresDefaultValue, isCrossinline, isNoinline, varargElementType, source);
            }
            return new WithDestructuringDeclaration(containingDeclaration, original, index, annotations, name2, outType, declaresDefaultValue, isCrossinline, isNoinline, varargElementType, source, destructuringVariables);
        }
    }

    /* JADX INFO: compiled from: ValueParameterDescriptorImpl.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001Bw\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u000f\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0012\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00170\u0016¢\u0006\u0002\u0010\u0019J \u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u0007H\u0016R!\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001a\u0010\u001b¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/ValueParameterDescriptorImpl$WithDestructuringDeclaration;", "Lorg/jetbrains/kotlin/descriptors/impl/ValueParameterDescriptorImpl;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "original", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "index", "", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "name", "Lorg/jetbrains/kotlin/name/Name;", "outType", "Lorg/jetbrains/kotlin/types/KotlinType;", "declaresDefaultValue", "", "isCrossinline", "isNoinline", "varargElementType", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "destructuringVariables", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;ILorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/types/KotlinType;ZZZLorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/SourceElement;Lkotlin/jvm/functions/Function0;)V", "getDestructuringVariables", "()Ljava/util/List;", "destructuringVariables$delegate", "Lkotlin/Lazy;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newOwner", "newName", "newIndex", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WithDestructuringDeclaration extends ValueParameterDescriptorImpl {

        /* JADX INFO: renamed from: destructuringVariables$delegate, reason: from kotlin metadata */
        private final Lazy destructuringVariables;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public WithDestructuringDeclaration(CallableDescriptor containingDeclaration, ValueParameterDescriptor valueParameterDescriptor, int i, Annotations annotations, Name name2, KotlinType outType, boolean z, boolean z2, boolean z3, KotlinType kotlinType, SourceElement source, Function0<? extends List<? extends VariableDescriptor>> destructuringVariables) {
            super(containingDeclaration, valueParameterDescriptor, i, annotations, name2, outType, z, z2, z3, kotlinType, source);
            Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(outType, "outType");
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(destructuringVariables, "destructuringVariables");
            this.destructuringVariables = LazyKt.lazy(destructuringVariables);
        }

        public final List<VariableDescriptor> getDestructuringVariables() {
            return (List) this.destructuringVariables.getValue();
        }

        @Override // org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl, org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
        /* JADX INFO: renamed from: copy */
        public ValueParameterDescriptor mo7204copy(CallableDescriptor newOwner, Name newName, int newIndex) {
            Intrinsics.checkNotNullParameter(newOwner, "newOwner");
            Intrinsics.checkNotNullParameter(newName, "newName");
            Annotations annotations = getAnnotations();
            Intrinsics.checkNotNullExpressionValue(annotations, "<get-annotations>(...)");
            KotlinType type = getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            boolean zDeclaresDefaultValue = declaresDefaultValue();
            boolean zIsCrossinline = getIsCrossinline();
            boolean zIsNoinline = getIsNoinline();
            KotlinType varargElementType = getVarargElementType();
            SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
            return new WithDestructuringDeclaration(newOwner, null, newIndex, annotations, newName, type, zDeclaresDefaultValue, zIsCrossinline, zIsNoinline, varargElementType, NO_SOURCE, new Function0<List<? extends VariableDescriptor>>() { // from class: org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl$WithDestructuringDeclaration$copy$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends VariableDescriptor> invoke() {
                    return this.this$0.getDestructuringVariables();
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    public CallableDescriptor getPackageFragment() {
        DeclarationDescriptor containingDeclaration = super.getPackageFragment();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
        return (CallableDescriptor) containingDeclaration;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    public boolean declaresDefaultValue() {
        if (this.declaresDefaultValue) {
            CallableDescriptor moduleDescriptor = getPackageFragment();
            Intrinsics.checkNotNull(moduleDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableMemberDescriptor");
            if (((CallableMemberDescriptor) moduleDescriptor).getKind().isReal()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.VariableDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public ValueParameterDescriptor getOriginal() {
        ValueParameterDescriptor valueParameterDescriptor = this.original;
        return valueParameterDescriptor == this ? this : valueParameterDescriptor.getOriginal();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public ValueParameterDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        if (substitutor.isEmpty()) {
            return this;
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitValueParameterDescriptor(this, data);
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    /* JADX INFO: renamed from: copy */
    public ValueParameterDescriptor mo7204copy(CallableDescriptor newOwner, Name newName, int newIndex) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(newName, "newName");
        Annotations annotations = getAnnotations();
        Intrinsics.checkNotNullExpressionValue(annotations, "<get-annotations>(...)");
        KotlinType type = getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        boolean zDeclaresDefaultValue = declaresDefaultValue();
        boolean isCrossinline = getIsCrossinline();
        boolean isNoinline = getIsNoinline();
        KotlinType varargElementType = getVarargElementType();
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return new ValueParameterDescriptorImpl(newOwner, null, newIndex, annotations, newName, type, zDeclaresDefaultValue, isCrossinline, isNoinline, varargElementType, NO_SOURCE);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        return LOCAL;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.VariableDescriptorImpl, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<ValueParameterDescriptor> getOverriddenDescriptors() {
        Collection<? extends CallableDescriptor> overriddenDescriptors = getPackageFragment().getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends CallableDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((CallableDescriptor) it.next()).getValueParameters().get(getIndex()));
        }
        return arrayList;
    }
}
