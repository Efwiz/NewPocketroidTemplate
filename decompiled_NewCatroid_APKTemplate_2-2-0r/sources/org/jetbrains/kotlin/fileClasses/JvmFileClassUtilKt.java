package org.jetbrains.kotlin.fileClasses;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: JvmFileClassUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0013\u001a\u00020\u000e*\u00020\u0014\"\u0013\u0010\u0000\u001a\u00070\u0001¢\u0006\u0002\b\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0015\u0010\u0003\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\"\u0015\u0010\b\u001a\u00020\t*\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u0015\u0010\r\u001a\u00020\u000e*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000f\"\u0015\u0010\u0010\u001a\u00020\n*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0015"}, d2 = {"LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "fileClassInfo", "Lorg/jetbrains/kotlin/fileClasses/JvmFileClassInfo;", "Lorg/jetbrains/kotlin/psi/KtFile;", "getFileClassInfo", "(Lorg/jetbrains/kotlin/psi/KtFile;)Lorg/jetbrains/kotlin/fileClasses/JvmFileClassInfo;", "internalNameWithoutInnerClasses", "", "Lorg/jetbrains/kotlin/name/FqName;", "getInternalNameWithoutInnerClasses", "(Lorg/jetbrains/kotlin/name/FqName;)Ljava/lang/String;", "isJvmMultifileClassFile", "", "(Lorg/jetbrains/kotlin/psi/KtFile;)Z", "javaFileFacadeFqName", "getJavaFileFacadeFqName", "(Lorg/jetbrains/kotlin/psi/KtFile;)Lorg/jetbrains/kotlin/name/FqName;", "isInsideJvmMultifileClassFile", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "frontend.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmFileClassUtilKt {
    private static final Logger LOG;

    public static final JvmFileClassInfo getFileClassInfo(final KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        Object cachedValue = CachedValuesManager.getCachedValue((PsiElement) ktFile, (CachedValueProvider<Object>) new CachedValueProvider() { // from class: org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return JvmFileClassUtilKt._get_fileClassInfo_$lambda$0(ktFile);
            }
        });
        Intrinsics.checkNotNullExpressionValue(cachedValue, "getCachedValue(...)");
        return (JvmFileClassInfo) cachedValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValueProvider.Result _get_fileClassInfo_$lambda$0(KtFile this_fileClassInfo) {
        Intrinsics.checkNotNullParameter(this_fileClassInfo, "$this_fileClassInfo");
        return new CachedValueProvider.Result(JvmFileClassUtil.getFileClassInfoNoResolve(this_fileClassInfo), this_fileClassInfo);
    }

    public static final FqName getJavaFileFacadeFqName(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        FqName fqNameChild = ktFile.getIsCompiled() ? ktFile.getPackageFqName().child(Name.identifier(ktFile.getVirtualFile().getNameWithoutExtension())) : getFileClassInfo(ktFile).getFacadeClassFqName();
        Intrinsics.checkNotNull(fqNameChild);
        if (!Name.isValidIdentifier(fqNameChild.shortName().getIdentifier())) {
            LOG.error("An invalid fqName `" + fqNameChild + "` with short name `" + fqNameChild.shortName() + "` is created for file `" + ktFile.getName() + "` (isCompiled = " + ktFile.getIsCompiled() + ')');
        }
        return fqNameChild;
    }

    public static final boolean isJvmMultifileClassFile(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        return JvmFileClassUtil.findAnnotationEntryOnFileNoResolve(ktFile, JvmNames.INSTANCE.getJVM_MULTIFILE_CLASS_SHORT()) != null;
    }

    static {
        Logger logger = Logger.getInstance("JvmFileClassUtil");
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }

    public static final boolean isInsideJvmMultifileClassFile(KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        KtFile containingKtFile = ktDeclaration.getContainingKtFile();
        Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
        return isJvmMultifileClassFile(containingKtFile);
    }

    public static final String getInternalNameWithoutInnerClasses(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        String internalName = JvmClassName.byFqNameWithoutInnerClasses(fqName).getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return internalName;
    }
}
