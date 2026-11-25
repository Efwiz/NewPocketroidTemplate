package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.fileClasses.JvmFileClassInfo;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: CodegenFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u0019\u001aB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J&\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J$\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\u001e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00042\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J\u0010\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00172\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/DefaultCodegenFactory;", "Lorg/jetbrains/kotlin/codegen/CodegenFactory;", "()V", "convertToIr", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$BackendInput;", "input", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$IrConversionInput;", "generateMultifileClass", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "multifileClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "generatePackage", "packageFqName", "ktFiles", "getModuleChunkBackendInput", "wholeBackendInput", "sourceFiles", "invokeCodegen", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$CodegenInput;", "invokeLowerings", "DummyOldCodegenInput", "OldBackendInput", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultCodegenFactory implements CodegenFactory {
    public static final DefaultCodegenFactory INSTANCE = new DefaultCodegenFactory();

    @Override // org.jetbrains.kotlin.codegen.CodegenFactory
    public void invokeCodegen(CodegenFactory.CodegenInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
    }

    /* JADX INFO: compiled from: CodegenFactory.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/DefaultCodegenFactory$OldBackendInput;", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$BackendInput;", "ktFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Ljava/util/Collection;)V", "getKtFiles", "()Ljava/util/Collection;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class OldBackendInput implements CodegenFactory.BackendInput {
        private final Collection<KtFile> ktFiles;

        /* JADX WARN: Multi-variable type inference failed */
        public OldBackendInput(Collection<? extends KtFile> ktFiles) {
            Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
            this.ktFiles = ktFiles;
        }

        public final Collection<KtFile> getKtFiles() {
            return this.ktFiles;
        }
    }

    private DefaultCodegenFactory() {
    }

    /* JADX INFO: compiled from: CodegenFactory.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/DefaultCodegenFactory$DummyOldCodegenInput;", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$CodegenInput;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class DummyOldCodegenInput implements CodegenFactory.CodegenInput {
        private final GenerationState state;

        public DummyOldCodegenInput(GenerationState state) {
            Intrinsics.checkNotNullParameter(state, "state");
            this.state = state;
        }

        @Override // org.jetbrains.kotlin.codegen.CodegenFactory.CodegenInput
        public GenerationState getState() {
            return this.state;
        }
    }

    @Override // org.jetbrains.kotlin.codegen.CodegenFactory
    public CodegenFactory.BackendInput convertToIr(CodegenFactory.IrConversionInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new OldBackendInput(input.getFiles());
    }

    @Override // org.jetbrains.kotlin.codegen.CodegenFactory
    public CodegenFactory.BackendInput getModuleChunkBackendInput(CodegenFactory.BackendInput wholeBackendInput, Collection<? extends KtFile> sourceFiles) {
        Intrinsics.checkNotNullParameter(wholeBackendInput, "wholeBackendInput");
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        return new OldBackendInput(sourceFiles);
    }

    @Override // org.jetbrains.kotlin.codegen.CodegenFactory
    public CodegenFactory.CodegenInput invokeLowerings(GenerationState state, CodegenFactory.BackendInput input) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(input, "input");
        MultiMap multiMap = new MultiMap();
        MultiMap multiMap2 = new MultiMap();
        for (KtFile ktFile : ((OldBackendInput) input).getKtFiles()) {
            JvmFileClassInfo fileClassInfoNoResolve = JvmFileClassUtil.getFileClassInfoNoResolve(ktFile);
            if (fileClassInfoNoResolve.getWithJvmMultifileClass()) {
                multiMap2.putValue(fileClassInfoNoResolve.getFacadeClassFqName(), ktFile);
            } else {
                multiMap.putValue(ktFile.getPackageFqName(), ktFile);
            }
        }
        HashSet hashSet = new HashSet(state.getObsoleteMultifileClasses());
        Set setKeySet = multiMap2.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "keySet(...)");
        for (FqName fqName : SetsKt.plus(setKeySet, (Iterable) hashSet)) {
            CodegenFactory.INSTANCE.doCheckCancelled(state);
            Intrinsics.checkNotNull(fqName);
            Collection<? extends KtFile> collection = multiMap2.get(fqName);
            Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
            generateMultifileClass(state, fqName, collection);
        }
        HashSet hashSet2 = new HashSet(state.getPackagesWithObsoleteParts());
        Set setKeySet2 = multiMap.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet2, "keySet(...)");
        for (FqName fqName2 : SetsKt.plus((Set) hashSet2, (Iterable) setKeySet2)) {
            CodegenFactory.INSTANCE.doCheckCancelled(state);
            Intrinsics.checkNotNull(fqName2);
            Collection<? extends KtFile> collection2 = multiMap.get(fqName2);
            Intrinsics.checkNotNullExpressionValue(collection2, "get(...)");
            generatePackage(state, fqName2, collection2);
        }
        return new DummyOldCodegenInput(state);
    }

    private final void generateMultifileClass(GenerationState state, FqName multifileClassFqName, Collection<? extends KtFile> files) {
        state.getFactory().forMultifileClass(multifileClassFqName, files).generate();
    }

    public final void generatePackage(GenerationState state, FqName packageFqName, Collection<? extends KtFile> ktFiles) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        state.getFactory().forPackage(packageFqName, ktFiles).generate();
    }
}
