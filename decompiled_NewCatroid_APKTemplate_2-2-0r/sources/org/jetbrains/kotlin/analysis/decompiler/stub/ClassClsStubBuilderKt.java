package org.jetbrains.kotlin.analysis.decompiler.stub;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ClassClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a@\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e¨\u0006\u000f"}, d2 = {"createClassStub", "", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "context", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "decompiler-to-stubs"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassClsStubBuilderKt {
    public static final void createClassStub(StubElement<? extends PsiElement> parent, ProtoBuf.Class classProto, NameResolver nameResolver, ClassId classId, SourceElement sourceElement, ClsStubBuilderContext context) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(context, "context");
        new ClassClsStubBuilder(parent, classProto, nameResolver, classId, sourceElement, context).build();
    }
}
