package org.jetbrains.kotlin.backend.common.serialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.impl.EmptyPackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.impl.IrFileImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFileSymbolImpl;
import org.jetbrains.kotlin.ir.util.NaiveSourceBasedFileEntryImpl;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: IrFileDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0018\u0010\u0007\u001a\u00020\b*\u00020\u00022\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¨\u0006\f"}, d2 = {"createFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "fileProto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFile;", "deserializeFqName", "", "fqn", "", "", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrFileDeserializerKt {

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.serialization.IrFileDeserializerKt$deserializeFqName$1, reason: invalid class name */
    /* JADX INFO: compiled from: IrFileDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<Integer, String> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "string(I)Ljava/lang/String;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "string";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IrLibraryFile.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ String invoke(Integer num) {
            return invoke(num.intValue());
        }

        public final String invoke(int i) {
            return ((IrLibraryFile) this.receiver).string(i);
        }
    }

    public static final String deserializeFqName(IrLibraryFile irLibraryFile, List<Integer> fqn) {
        Intrinsics.checkNotNullParameter(irLibraryFile, "<this>");
        Intrinsics.checkNotNullParameter(fqn, "fqn");
        return CollectionsKt.joinToString$default(fqn, ".", null, null, 0, null, new AnonymousClass1(irLibraryFile), 30, null);
    }

    public static final IrFile createFile(IrLibraryFile irLibraryFile, IrModuleFragment module, org.jetbrains.kotlin.backend.common.serialization.proto.IrFile fileProto) {
        Intrinsics.checkNotNullParameter(irLibraryFile, "<this>");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(fileProto, "fileProto");
        String name2 = fileProto.getFileEntry().getName();
        Intrinsics.checkNotNull(name2);
        List<Integer> lineStartOffsetList = fileProto.getFileEntry().getLineStartOffsetList();
        Intrinsics.checkNotNullExpressionValue(lineStartOffsetList, "getLineStartOffsetList(...)");
        NaiveSourceBasedFileEntryImpl naiveSourceBasedFileEntryImpl = new NaiveSourceBasedFileEntryImpl(name2, CollectionsKt.toIntArray(lineStartOffsetList), 0, 4, null);
        List<Integer> fqNameList = fileProto.getFqNameList();
        Intrinsics.checkNotNullExpressionValue(fqNameList, "getFqNameList(...)");
        FqName fqName = new FqName(deserializeFqName(irLibraryFile, fqNameList));
        return new IrFileImpl(naiveSourceBasedFileEntryImpl, new IrFileSymbolImpl(new EmptyPackageFragmentDescriptor(module.getDescriptor(), fqName)), fqName, module);
    }
}
