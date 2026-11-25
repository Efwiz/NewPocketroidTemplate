package org.jetbrains.kotlin.ir.backend.js;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: JsCommonBackendContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a&\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b*\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000eH\u0000¨\u0006\u000f"}, d2 = {"findClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "name", "Lorg/jetbrains/kotlin/name/Name;", "findFunctions", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "lazy2", "Lkotlin/Lazy;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "fn", "Lkotlin/Function0;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsCommonBackendContextKt {
    public static final <T> Lazy<T> lazy2(final BackendContext backendContext, final Function0<? extends T> fn) {
        Intrinsics.checkNotNullParameter(backendContext, "<this>");
        Intrinsics.checkNotNullParameter(fn, "fn");
        return LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<T>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContextKt.lazy2.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final T invoke() {
                return (T) backendContext.getIrFactory().getStageController().withInitialIr(fn);
            }
        });
    }

    public static final ClassDescriptor findClass(MemberScope memberScope, Name name2) {
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        Intrinsics.checkNotNullParameter(name2, "name");
        ClassifierDescriptor contributedClassifier = memberScope.mo7465getContributedClassifier(name2, NoLookupLocation.FROM_BACKEND);
        Intrinsics.checkNotNull(contributedClassifier, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        return (ClassDescriptor) contributedClassifier;
    }

    public static final List<SimpleFunctionDescriptor> findFunctions(MemberScope memberScope, Name name2) {
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        Intrinsics.checkNotNullParameter(name2, "name");
        return CollectionsKt.toList(memberScope.getContributedFunctions(name2, NoLookupLocation.FROM_BACKEND));
    }
}
