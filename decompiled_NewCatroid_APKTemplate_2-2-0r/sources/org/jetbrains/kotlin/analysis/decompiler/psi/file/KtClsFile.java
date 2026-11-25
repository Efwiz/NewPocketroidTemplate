package org.jetbrains.kotlin.analysis.decompiler.psi.file;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.analysis.decompiler.psi.KotlinDecompiledFileViewProvider;
import org.jetbrains.kotlin.analysis.decompiler.psi.text.BuildDecompiledTextForClassFileKt;
import org.jetbrains.kotlin.analysis.decompiler.psi.text.DecompiledText;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: compiled from: KtClsFile.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtDecompiledFile;", Constants.EXTRA_PROVIDER, "Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinDecompiledFileViewProvider;", "(Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinDecompiledFileViewProvider;)V", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtClsFile extends KtDecompiledFile {

    /* JADX INFO: renamed from: org.jetbrains.kotlin.analysis.decompiler.psi.file.KtClsFile$1, reason: invalid class name */
    /* JADX INFO: compiled from: KtClsFile.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<VirtualFile, DecompiledText> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "_init_$buildDecompiledTextForClassFile(Lcom/intellij/openapi/vfs/VirtualFile;)Lorg/jetbrains/kotlin/analysis/decompiler/psi/text/DecompiledText;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "buildDecompiledTextForClassFile";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final DecompiledText invoke(VirtualFile p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return BuildDecompiledTextForClassFileKt.buildDecompiledTextForClassFile$default(p0, null, 2, null);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtClsFile(KotlinDecompiledFileViewProvider provider) {
        super(provider, AnonymousClass1.INSTANCE);
        Intrinsics.checkNotNullParameter(provider, "provider");
    }
}
