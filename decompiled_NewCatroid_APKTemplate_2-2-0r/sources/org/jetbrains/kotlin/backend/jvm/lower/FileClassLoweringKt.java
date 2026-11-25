package org.jetbrains.kotlin.backend.jvm.lower;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.fileClasses.JvmFileClassInfo;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.fileClasses.JvmMultifileClassPartInfo;
import org.jetbrains.kotlin.fileClasses.JvmSimpleFileClassInfo;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.PsiIrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.NaiveSourceBasedFileEntryImpl;
import org.jetbrains.kotlin.load.kotlin.PackagePartClassUtils;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: FileClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0000\u001a\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u000f\u001a\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0014\u001a\u00020\u000bH\u0002\u001a\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\f\u001a\u00020\rH\u0002\u001a\n\u0010\u0017\u001a\u00020\u0011*\u00020\r\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0018"}, d2 = {"fileClassPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getFileClassPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "checkAllFileLevelDeclarationsAreClasses", "", "irModuleFragment", "findAnnotationEntryOnFileNoResolve", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "shortName", "", "getFileClassInfoFromIrFile", "Lorg/jetbrains/kotlin/fileClasses/JvmFileClassInfo;", PsiTreeChangeEvent.PROP_FILE_NAME, "getLiteralStringFromAnnotation", "annotationCall", "parseJvmNameOnFileNoResolve", "Lorg/jetbrains/kotlin/backend/jvm/lower/ParsedJvmFileClassAnnotations;", "getFileClassInfo", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FileClassLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> fileClassPhase = PhaseBuildersKt.makeIrModulePhase(FileClassLoweringKt$fileClassPhase$1.INSTANCE, "FileClass", "Put file level function and property declaration into a class", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : SetsKt.setOf(FileClassLoweringKt$fileClassPhase$2.INSTANCE), (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getFileClassPhase() {
        return fileClassPhase;
    }

    public static final void checkAllFileLevelDeclarationsAreClasses(IrModuleFragment irModuleFragment) {
        Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
        List<IrFile> files = irModuleFragment.getFiles();
        if ((files instanceof Collection) && files.isEmpty()) {
            return;
        }
        Iterator<T> it = files.iterator();
        while (it.hasNext()) {
            List<IrDeclaration> declarations = ((IrFile) it.next()).getDeclarations();
            if (!(declarations instanceof Collection) || !declarations.isEmpty()) {
                Iterator<T> it2 = declarations.iterator();
                while (it2.hasNext()) {
                    if (!(((IrDeclaration) it2.next()) instanceof IrClass)) {
                        return;
                    }
                }
            }
        }
    }

    public static final JvmFileClassInfo getFileClassInfo(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        IrFileEntry fileEntry = irFile.getFileEntry();
        if (fileEntry instanceof PsiIrFileEntry) {
            PsiFile psiFile = ((PsiIrFileEntry) fileEntry).getPsiFile();
            Intrinsics.checkNotNull(psiFile, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFile");
            return JvmFileClassUtil.getFileClassInfoNoResolve((KtFile) psiFile);
        }
        if (fileEntry instanceof NaiveSourceBasedFileEntryImpl) {
            String name2 = new File(fileEntry.getName()).getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            return getFileClassInfoFromIrFile(irFile, name2);
        }
        throw new IllegalStateException(("unknown kind of file entry: " + fileEntry).toString());
    }

    public static final JvmFileClassInfo getFileClassInfoFromIrFile(IrFile file, String fileName) {
        FqName packageFqName;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        ParsedJvmFileClassAnnotations jvmNameOnFileNoResolve = parseJvmNameOnFileNoResolve(file);
        if (jvmNameOnFileNoResolve == null || (packageFqName = jvmNameOnFileNoResolve.getJvmPackageName()) == null) {
            packageFqName = file.getPackageFqName();
        }
        if (jvmNameOnFileNoResolve != null) {
            String jvmName = jvmNameOnFileNoResolve.getJvmName();
            if (jvmName == null) {
                jvmName = PackagePartClassUtils.getFilePartShortName(fileName);
            }
            FqName fqNameChild = packageFqName.child(Name.identifier(jvmName));
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            if (jvmNameOnFileNoResolve.getIsMultifileClass()) {
                FqName fqNameChild2 = packageFqName.child(Name.identifier(JvmFileClassUtil.manglePartName(jvmName, fileName)));
                Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
                return new JvmMultifileClassPartInfo(fqNameChild2, fqNameChild);
            }
            return new JvmSimpleFileClassInfo(fqNameChild, true);
        }
        return new JvmSimpleFileClassInfo(PackagePartClassUtils.getPackagePartFqName(packageFqName, fileName), false);
    }

    private static final ParsedJvmFileClassAnnotations parseJvmNameOnFileNoResolve(IrFile irFile) {
        String literalStringFromAnnotation;
        String literalStringFromAnnotation2;
        IrConstructorCall irConstructorCallFindAnnotationEntryOnFileNoResolve = findAnnotationEntryOnFileNoResolve(irFile, JvmNames.INSTANCE.getJVM_NAME_SHORT());
        if (irConstructorCallFindAnnotationEntryOnFileNoResolve == null || (literalStringFromAnnotation = getLiteralStringFromAnnotation(irConstructorCallFindAnnotationEntryOnFileNoResolve)) == null || !Name.isValidIdentifier(literalStringFromAnnotation)) {
            literalStringFromAnnotation = null;
        }
        IrConstructorCall irConstructorCallFindAnnotationEntryOnFileNoResolve2 = findAnnotationEntryOnFileNoResolve(irFile, JvmNames.INSTANCE.getJVM_PACKAGE_NAME_SHORT());
        FqName fqName = (irConstructorCallFindAnnotationEntryOnFileNoResolve2 == null || (literalStringFromAnnotation2 = getLiteralStringFromAnnotation(irConstructorCallFindAnnotationEntryOnFileNoResolve2)) == null) ? null : new FqName(literalStringFromAnnotation2);
        if (literalStringFromAnnotation == null && fqName == null) {
            return null;
        }
        return new ParsedJvmFileClassAnnotations(literalStringFromAnnotation, fqName, findAnnotationEntryOnFileNoResolve(irFile, JvmNames.INSTANCE.getJVM_MULTIFILE_CLASS_SHORT()) != null);
    }

    private static final IrConstructorCall findAnnotationEntryOnFileNoResolve(IrFile irFile, String str) {
        Object objAsString;
        Object next;
        Name nameShortName;
        Iterator<T> it = irFile.getAnnotations().iterator();
        do {
            objAsString = null;
            if (it.hasNext()) {
                next = it.next();
                FqName classFqName = IrTypesKt.getClassFqName(((IrConstructorCall) next).getType());
                if (classFqName != null && (nameShortName = classFqName.shortName()) != null) {
                    objAsString = nameShortName.asString();
                }
            }
            return (IrConstructorCall) objAsString;
        } while (!Intrinsics.areEqual(objAsString, str));
        objAsString = next;
        return (IrConstructorCall) objAsString;
    }

    private static final String getLiteralStringFromAnnotation(IrConstructorCall irConstructorCall) {
        IrExpression valueArgument;
        if (irConstructorCall.getValueArgumentsCount() < 1 || (valueArgument = irConstructorCall.getValueArgument(0)) == null || !(valueArgument instanceof IrConst)) {
            return null;
        }
        IrConst irConst = (IrConst) valueArgument;
        if (!Intrinsics.areEqual(irConst.getKind(), IrConstKind.String.INSTANCE)) {
            return null;
        }
        Object value = irConst.getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.String");
        return (String) value;
    }
}
