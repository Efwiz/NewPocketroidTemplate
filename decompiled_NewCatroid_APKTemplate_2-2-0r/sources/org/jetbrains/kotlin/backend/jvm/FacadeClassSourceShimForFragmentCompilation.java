package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.fileClasses.JvmFileClassInfo;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.load.kotlin.FacadeClassSource;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.source.PsiSourceFile;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: FacadeClassSourceShimForFragmentCompilation.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/FacadeClassSourceShimForFragmentCompilation;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "Lorg/jetbrains/kotlin/load/kotlin/FacadeClassSource;", "containingFile", "Lorg/jetbrains/kotlin/resolve/source/PsiSourceFile;", "(Lorg/jetbrains/kotlin/resolve/source/PsiSourceFile;)V", "abiStability", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "getAbiStability", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "getClassName", "()Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "facadeClassName", "getFacadeClassName", "fileClassInfo", "Lorg/jetbrains/kotlin/fileClasses/JvmFileClassInfo;", "incompatibility", "Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "getIncompatibility", "()Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "isPreReleaseInvisible", "", "()Z", "presentableString", "", "getPresentableString", "()Ljava/lang/String;", "getContainingFile", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FacadeClassSourceShimForFragmentCompilation implements FacadeClassSource, DeserializedContainerSource {
    private final PsiSourceFile containingFile;
    private final JvmFileClassInfo fileClassInfo;

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    /* JADX INFO: renamed from: getIncompatibility */
    public IncompatibleVersionErrorData<?> mo6784getIncompatibility() {
        return null;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    /* JADX INFO: renamed from: isPreReleaseInvisible */
    public boolean getIsPreReleaseInvisible() {
        return false;
    }

    public FacadeClassSourceShimForFragmentCompilation(PsiSourceFile containingFile) {
        Intrinsics.checkNotNullParameter(containingFile, "containingFile");
        this.containingFile = containingFile;
        PsiFile psiFile = containingFile.getPsiFile();
        Intrinsics.checkNotNull(psiFile, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFile");
        this.fileClassInfo = JvmFileClassUtil.getFileClassInfoNoResolve((KtFile) psiFile);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    public DeserializedContainerAbiStability getAbiStability() {
        return DeserializedContainerAbiStability.STABLE;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    public String getPresentableString() {
        return "Fragment for " + this.containingFile;
    }

    @Override // org.jetbrains.kotlin.descriptors.SourceElement
    public SourceFile getContainingFile() {
        return this.containingFile;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FacadeClassSource
    public JvmClassName getClassName() {
        JvmClassName jvmClassNameByFqNameWithoutInnerClasses = JvmClassName.byFqNameWithoutInnerClasses(this.fileClassInfo.getFileClassFqName());
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByFqNameWithoutInnerClasses, "byFqNameWithoutInnerClasses(...)");
        return jvmClassNameByFqNameWithoutInnerClasses;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FacadeClassSource
    public JvmClassName getFacadeClassName() {
        return JvmClassName.byFqNameWithoutInnerClasses(this.fileClassInfo.getFacadeClassFqName());
    }
}
