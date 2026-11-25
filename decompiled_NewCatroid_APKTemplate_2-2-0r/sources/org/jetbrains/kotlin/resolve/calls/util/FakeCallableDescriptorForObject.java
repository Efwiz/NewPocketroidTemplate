package org.jetbrains.kotlin.resolve.calls.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: FakeCallableDescriptorForObject.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0092\u0001\u0010\f\u001a\n \u000e*\u0004\u0018\u0001H\rH\r\"\u0010\b\u0000\u0010\r*\n \u000e*\u0004\u0018\u00010\u000f0\u000f\"\u0010\b\u0001\u0010\u0010*\n \u000e*\u0004\u0018\u00010\u000f0\u000f2F\u0010\u0011\u001aB\u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\rH\r\u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\u0010H\u0010 \u000e* \u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\rH\r\u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\u0010H\u0010\u0018\u00010\u00120\u00122\u000e\u0010\u0013\u001a\n \u000e*\u0004\u0018\u0001H\u0010H\u0010H\u0096\u0001¢\u0006\u0002\u0010\u0014JQ\u0010\u0015\u001a\u00020\u00162F\u0010\u0011\u001aB\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00170\u0017\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00170\u0017 \u000e* \u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00170\u0017\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00170\u0017\u0018\u00010\u00120\u0012H\u0096\u0001J\b\u0010\u0018\u001a\u00020\u0016H\u0016J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\n\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!H\u0016J\n\u0010#\u001a\u0004\u0018\u00010\"H\u0016J\n\u0010$\u001a\u0004\u0018\u00010\"H\u0016J\t\u0010%\u001a\u00020&H\u0097\u0001J\b\u0010'\u001a\u00020(H\u0016J\u000e\u0010)\u001a\b\u0012\u0004\u0012\u00020(0*H\u0016J\b\u0010+\u001a\u00020,H\u0016J\u0006\u0010-\u001a\u00020\u0004J\n\u0010.\u001a\u0004\u0018\u00010/H\u0016J\b\u00100\u001a\u000201H\u0016J\b\u00102\u001a\u00020/H\u0016J\u000e\u00103\u001a\b\u0012\u0004\u0012\u0002040!H\u0016J%\u00105\u001a\u0004\u0018\u0001H6\"\u0004\b\u0000\u001062\u000e\u00107\u001a\n\u0012\u0004\u0012\u0002H6\u0018\u000108H\u0016¢\u0006\u0002\u00109J\u000e\u0010:\u001a\b\u0012\u0004\u0012\u00020;0!H\u0016J\t\u0010<\u001a\u00020=H\u0097\u0001J\b\u0010>\u001a\u00020\u001aH\u0016J\b\u0010?\u001a\u00020\u001aH\u0016J\b\u0010@\u001a\u00020AH\u0016J\b\u0010B\u001a\u00020\u001aH\u0016J\b\u0010C\u001a\u00020\u001aH\u0016J\b\u0010D\u001a\u00020\u001aH\u0016J\u0010\u0010E\u001a\u00020\u00002\u0006\u0010F\u001a\u00020GH\u0016R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/util/FakeCallableDescriptorForObject;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "D", "p0", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "p1", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "cleanCompileTimeInitializerCache", Namer.EQUALS_METHOD_NAME, "", "other", "getCompileTimeInitializer", "", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getContextReceiverParameters", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDispatchReceiverParameter", "getExtensionReceiverParameter", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getOverriddenDescriptors", "", "getReferencedDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "getReferencedObject", "getReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getType", "getTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "getValueParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "hasStableParameterNames", "hasSynthesizedParameterNames", "hashCode", "", "isConst", "isLateInit", "isVar", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FakeCallableDescriptorForObject implements DeclarationDescriptorWithVisibility, VariableDescriptor {
    private final /* synthetic */ ClassDescriptor $$delegate_0;
    private final ClassDescriptor classDescriptor;

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> p0, D p1) {
        return (R) this.$$delegate_0.accept(p0, p1);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> p0) {
        this.$$delegate_0.mo7454acceptVoid(p0);
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.$$delegate_0.getAnnotations();
    }

    public Void getCompileTimeInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    /* JADX INFO: renamed from: getDispatchReceiverParameter */
    public ReceiverParameterDescriptor mo6753getDispatchReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    public Name getName() {
        return this.$$delegate_0.getName();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        return this.$$delegate_0.getVisibility();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasStableParameterNames() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasSynthesizedParameterNames() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isConst() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public FakeCallableDescriptorForObject substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        return this;
    }

    public FakeCallableDescriptorForObject(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        this.classDescriptor = classDescriptor;
        this.$$delegate_0 = DescriptorUtilsKt.getClassObjectReferenceTarget(classDescriptor);
        DescriptorUtilsKt.getHasClassValueDescriptor(classDescriptor);
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    public /* bridge */ /* synthetic */ ConstantValue mo6754getCompileTimeInitializer() {
        return (ConstantValue) getCompileTimeInitializer();
    }

    public final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    public ClassifierDescriptorWithTypeParameters getReferencedDescriptor() {
        return DescriptorUtilsKt.getClassObjectReferenceTarget(this.classDescriptor);
    }

    public final ClassDescriptor getReferencedObject() {
        return DescriptorUtilsKt.getClassObjectReferenceTarget(this.classDescriptor);
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ReceiverParameterDescriptor> getContextReceiverParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<TypeParameterDescriptor> getTypeParameters() {
        List<TypeParameterDescriptor> listEmptyList = Collections.emptyList();
        Intrinsics.checkNotNullExpressionValue(listEmptyList, "emptyList(...)");
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ValueParameterDescriptor> getValueParameters() {
        List<ValueParameterDescriptor> listEmptyList = Collections.emptyList();
        Intrinsics.checkNotNullExpressionValue(listEmptyList, "emptyList(...)");
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return getType();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Set<CallableDescriptor> getOverriddenDescriptors() {
        Set<CallableDescriptor> setEmptySet = Collections.emptySet();
        Intrinsics.checkNotNullExpressionValue(setEmptySet, "emptySet(...)");
        return setEmptySet;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueDescriptor
    public KotlinType getType() {
        KotlinType classValueType = DescriptorUtilsKt.getClassValueType(this.classDescriptor);
        Intrinsics.checkNotNull(classValueType);
        return classValueType;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public CallableDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    public SourceElement getSource() {
        SourceElement source = this.classDescriptor.getSource();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        return source;
    }

    public boolean equals(Object other) {
        return (other instanceof FakeCallableDescriptorForObject) && Intrinsics.areEqual(this.classDescriptor, ((FakeCallableDescriptorForObject) other).classDescriptor);
    }

    public int hashCode() {
        return this.classDescriptor.hashCode();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    public DeclarationDescriptor getModuleDescriptor() {
        DeclarationDescriptor moduleDescriptor = DescriptorUtilsKt.getClassObjectReferenceTarget(this.classDescriptor).getModuleDescriptor();
        Intrinsics.checkNotNullExpressionValue(moduleDescriptor, "getContainingDeclaration(...)");
        return moduleDescriptor;
    }
}
