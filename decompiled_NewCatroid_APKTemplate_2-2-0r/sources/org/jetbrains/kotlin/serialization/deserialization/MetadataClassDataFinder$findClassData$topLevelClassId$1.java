package org.jetbrains.kotlin.serialization.deserialization;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: MetadataUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class MetadataClassDataFinder$findClassData$topLevelClassId$1 extends FunctionReference implements Function1<ClassId, ClassId> {
    public static final MetadataClassDataFinder$findClassData$topLevelClassId$1 INSTANCE = new MetadataClassDataFinder$findClassData$topLevelClassId$1();

    MetadataClassDataFinder$findClassData$topLevelClassId$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getOuterClassId()Lorg/jetbrains/kotlin/name/ClassId;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "getOuterClassId";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ClassId.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final ClassId invoke(ClassId p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return p0.getOuterClassId();
    }
}
