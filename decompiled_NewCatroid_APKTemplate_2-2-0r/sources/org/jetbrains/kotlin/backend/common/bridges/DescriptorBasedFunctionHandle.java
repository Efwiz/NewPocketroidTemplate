package org.jetbrains.kotlin.backend.common.bridges;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.util.DeclarationUtilKt;

/* JADX INFO: compiled from: impl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0003H\u0014J\u0013\u0010\u0015\u001a\u00020\u000e2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0096\u0002J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00000\u0006H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00000\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/bridges/DescriptorBasedFunctionHandle;", "Lorg/jetbrains/kotlin/backend/common/bridges/FunctionHandle;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "_overridden", "", "get_overridden", "()Ljava/util/List;", "_overridden$delegate", "Lkotlin/Lazy;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isAbstract", "", "()Z", "isDeclaration", "mayBeUsedAsSuperImplementation", "getMayBeUsedAsSuperImplementation", "createHandleForOverridden", "overridden", Namer.EQUALS_METHOD_NAME, "other", "", "getOverridden", "hashCode", "", "toString", "", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DescriptorBasedFunctionHandle implements FunctionHandle {

    /* JADX INFO: renamed from: _overridden$delegate, reason: from kotlin metadata */
    private final Lazy _overridden;
    private final FunctionDescriptor descriptor;
    private final boolean isAbstract;
    private final boolean isDeclaration;
    private final boolean mayBeUsedAsSuperImplementation;

    public DescriptorBasedFunctionHandle(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.descriptor = descriptor;
        this._overridden = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<List<? extends DescriptorBasedFunctionHandle>>() { // from class: org.jetbrains.kotlin.backend.common.bridges.DescriptorBasedFunctionHandle$_overridden$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends DescriptorBasedFunctionHandle> invoke() {
                Collection<? extends FunctionDescriptor> overriddenDescriptors = this.this$0.getDescriptor().getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
                DescriptorBasedFunctionHandle descriptorBasedFunctionHandle = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
                Iterator<T> it = collection.iterator();
                while (it.hasNext()) {
                    FunctionDescriptor original = ((FunctionDescriptor) it.next()).getOriginal();
                    Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                    arrayList.add(descriptorBasedFunctionHandle.createHandleForOverridden(original));
                }
                return arrayList;
            }
        });
        this.isDeclaration = descriptor.getKind().isReal() || DeclarationUtilKt.findInterfaceImplementation$default(descriptor, false, 2, null) != null;
        this.isAbstract = descriptor.getModality() == Modality.ABSTRACT;
        this.mayBeUsedAsSuperImplementation = !DescriptorUtils.isInterface(descriptor.getModuleDescriptor());
    }

    public final FunctionDescriptor getDescriptor() {
        return this.descriptor;
    }

    private final List<DescriptorBasedFunctionHandle> get_overridden() {
        return (List) this._overridden.getValue();
    }

    protected DescriptorBasedFunctionHandle createHandleForOverridden(FunctionDescriptor overridden) {
        Intrinsics.checkNotNullParameter(overridden, "overridden");
        return new DescriptorBasedFunctionHandle(overridden);
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    /* JADX INFO: renamed from: isDeclaration, reason: from getter */
    public boolean getIsDeclaration() {
        return this.isDeclaration;
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    /* JADX INFO: renamed from: isAbstract, reason: from getter */
    public boolean getIsAbstract() {
        return this.isAbstract;
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    public boolean getMayBeUsedAsSuperImplementation() {
        return this.mayBeUsedAsSuperImplementation;
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    public List<DescriptorBasedFunctionHandle> getOverridden() {
        return get_overridden();
    }

    public int hashCode() {
        return this.descriptor.hashCode();
    }

    public boolean equals(Object other) {
        return (other instanceof DescriptorBasedFunctionHandle) && Intrinsics.areEqual(this.descriptor, ((DescriptorBasedFunctionHandle) other).descriptor);
    }

    public String toString() {
        return this.descriptor.toString();
    }
}
