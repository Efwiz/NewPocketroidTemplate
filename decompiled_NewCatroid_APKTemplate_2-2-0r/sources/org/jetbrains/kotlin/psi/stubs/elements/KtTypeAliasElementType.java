package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinClassStub;
import org.jetbrains.kotlin.psi.stubs.KotlinFileStub;
import org.jetbrains.kotlin.psi.stubs.KotlinTypeAliasStub;
import org.jetbrains.kotlin.psi.stubs.StubUtils;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeAliasStubImpl;

/* JADX INFO: compiled from: KtTypeAliasElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0002J\u001e\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00032\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nH\u0016J\u001e\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0017H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtTypeAliasElementType;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtStubElementType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinTypeAliasStub;", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "debugName", "", "(Ljava/lang/String;)V", "createNestedClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "typeAlias", "createStub", "psi", "deserialize", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "indexStub", "", "stub", "sink", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/IndexSink;", "serialize", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtTypeAliasElementType extends KtStubElementType<KotlinTypeAliasStub, KtTypeAlias> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtTypeAlias) psiElement, (StubElement<?>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeAliasElementType(String debugName) {
        super(debugName, KtTypeAlias.class, KotlinTypeAliasStub.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
    }

    public KotlinTypeAliasStub createStub(KtTypeAlias psi, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        StringRef stringRefFromString = StringRef.fromString(psi.getName());
        FqName fqNameSafeFqNameForLazyResolve = KtPsiUtilKt.safeFqNameForLazyResolve(psi);
        return new KotlinTypeAliasStubImpl(parentStub, stringRefFromString, StringRef.fromString(fqNameSafeFqNameForLazyResolve != null ? fqNameSafeFqNameForLazyResolve.asString() : null), parentStub != null ? createNestedClassId(parentStub, psi) : null, psi.isTopLevel());
    }

    private final ClassId createNestedClassId(StubElement<?> parentStub, KtTypeAlias typeAlias) {
        ClassId classId;
        Name nameAsName = typeAlias.getNameAsName();
        if (nameAsName == null) {
            return null;
        }
        if (Intrinsics.areEqual(parentStub.getStubType(), KtStubElementTypes.CLASS_BODY)) {
            StubElement parentStub2 = parentStub.getParentStub();
            KotlinClassStub kotlinClassStub = parentStub2 instanceof KotlinClassStub ? (KotlinClassStub) parentStub2 : null;
            if (kotlinClassStub == null || (classId = kotlinClassStub.getClassId()) == null) {
                return null;
            }
            return classId.createNestedClassId(nameAsName);
        }
        if (parentStub instanceof KotlinFileStub) {
            return new ClassId(((KotlinFileStub) parentStub).getPackageFqName(), nameAsName);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinTypeAliasStub stub, StubOutputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        dataStream.writeName(stub.getName());
        FqName fqName = stub.getFqName();
        dataStream.writeName(fqName != null ? fqName.asString() : null);
        StubUtils.serializeClassId(dataStream, stub.getClassId());
        dataStream.writeBoolean(stub.getIsTopLevel());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinTypeAliasStub deserialize(StubInputStream dataStream, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        return new KotlinTypeAliasStubImpl(parentStub, dataStream.readName(), dataStream.readName(), StubUtils.deserializeClassId(dataStream), dataStream.readBoolean());
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType
    public void indexStub(KotlinTypeAliasStub stub, IndexSink sink) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(sink, "sink");
        StubIndexService.INSTANCE.getInstance().indexTypeAlias(stub, sink);
    }
}
