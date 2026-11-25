package org.jetbrains.kotlin.types.expressions;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.context.GlobalContext;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.lazy.AbsentDescriptorHandler;
import org.jetbrains.kotlin.resolve.lazy.LazyDeclarationResolver;
import org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider;

/* JADX INFO: compiled from: LocalClassifierAnalyzer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/LocalLazyDeclarationResolver;", "Lorg/jetbrains/kotlin/resolve/lazy/LazyDeclarationResolver;", "globalContext", "Lorg/jetbrains/kotlin/context/GlobalContext;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "localClassDescriptorManager", "Lorg/jetbrains/kotlin/types/expressions/LocalClassDescriptorHolder;", "topLevelDescriptorProvider", "Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;", "absentDescriptorHandler", "Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "(Lorg/jetbrains/kotlin/context/GlobalContext;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/types/expressions/LocalClassDescriptorHolder;Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;)V", "getClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getClassDescriptorIfAny", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalLazyDeclarationResolver extends LazyDeclarationResolver {
    private final LocalClassDescriptorHolder localClassDescriptorManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalLazyDeclarationResolver(GlobalContext globalContext, BindingTrace trace, LocalClassDescriptorHolder localClassDescriptorManager, TopLevelDescriptorProvider topLevelDescriptorProvider, AbsentDescriptorHandler absentDescriptorHandler) {
        super(globalContext, trace, topLevelDescriptorProvider, absentDescriptorHandler);
        Intrinsics.checkNotNullParameter(globalContext, "globalContext");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(localClassDescriptorManager, "localClassDescriptorManager");
        Intrinsics.checkNotNullParameter(topLevelDescriptorProvider, "topLevelDescriptorProvider");
        Intrinsics.checkNotNullParameter(absentDescriptorHandler, "absentDescriptorHandler");
        this.localClassDescriptorManager = localClassDescriptorManager;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyDeclarationResolver
    public ClassDescriptor getClassDescriptor(KtClassOrObject classOrObject, LookupLocation location) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(location, "location");
        if (this.localClassDescriptorManager.isMyClass(classOrObject)) {
            return this.localClassDescriptorManager.getClassDescriptor(classOrObject, getScopeProvider());
        }
        return super.getClassDescriptor(classOrObject, location);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyDeclarationResolver
    public ClassDescriptor getClassDescriptorIfAny(KtClassOrObject classOrObject, LookupLocation location) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(location, "location");
        if (this.localClassDescriptorManager.isMyClass(classOrObject)) {
            return this.localClassDescriptorManager.getClassDescriptor(classOrObject, getScopeProvider());
        }
        return super.getClassDescriptorIfAny(classOrObject, location);
    }
}
