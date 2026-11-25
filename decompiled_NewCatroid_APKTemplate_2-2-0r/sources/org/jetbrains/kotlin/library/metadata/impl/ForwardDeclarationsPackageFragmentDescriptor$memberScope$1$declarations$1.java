package org.jetbrains.kotlin.library.metadata.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: KlibResolvedModuleDescriptorsFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ForwardDeclarationsPackageFragmentDescriptor$memberScope$1$declarations$1 extends FunctionReference implements Function1<Name, ClassDescriptor> {
    ForwardDeclarationsPackageFragmentDescriptor$memberScope$1$declarations$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "createDeclaration(Lorg/jetbrains/kotlin/name/Name;)Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "createDeclaration";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ForwardDeclarationsPackageFragmentDescriptor$memberScope$1.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final ClassDescriptor invoke(Name p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((ForwardDeclarationsPackageFragmentDescriptor$memberScope$1) this.receiver).createDeclaration(p0);
    }
}
