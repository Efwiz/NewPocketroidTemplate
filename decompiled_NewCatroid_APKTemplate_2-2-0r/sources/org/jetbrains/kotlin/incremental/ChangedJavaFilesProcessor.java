package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;

/* JADX INFO: compiled from: ChangedJavaFilesProcessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\b\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0005¢\u0006\u0002\u0010\bJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u001a\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0018H\u0002R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangedJavaFilesProcessor;", "", "reporter", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "psiFileFactory", "Lkotlin/Function1;", "Ljava/io/File;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "(Lorg/jetbrains/kotlin/build/report/ICReporter;Lkotlin/jvm/functions/Function1;)V", "allChangedSymbols", "", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "getAllChangedSymbols", "()Ljava/util/Collection;", "allSymbols", "Ljava/util/HashSet;", "process", "Lorg/jetbrains/kotlin/incremental/ChangesEither;", "filesDiff", "Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;", "addLookupSymbols", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "symbols", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ChangedJavaFilesProcessor {
    private final HashSet<LookupSymbol> allSymbols;
    private final Function1<File, PsiFile> psiFileFactory;
    private final ICReporter reporter;

    /* JADX WARN: Multi-variable type inference failed */
    public ChangedJavaFilesProcessor(ICReporter reporter, Function1<? super File, ? extends PsiFile> psiFileFactory) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(psiFileFactory, "psiFileFactory");
        this.reporter = reporter;
        this.psiFileFactory = psiFileFactory;
        this.allSymbols = new HashSet<>();
    }

    public final Collection<LookupSymbol> getAllChangedSymbols() {
        return this.allSymbols;
    }

    public final ChangesEither process(ChangedFiles.Known filesDiff) {
        Intrinsics.checkNotNullParameter(filesDiff, "filesDiff");
        List<File> modified = filesDiff.getModified();
        ArrayList arrayList = new ArrayList();
        for (Object obj : modified) {
            if (FileUtilsKt.isJavaFile((File) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList<File> arrayList2 = arrayList;
        List<File> removed = filesDiff.getRemoved();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : removed) {
            if (FileUtilsKt.isJavaFile((File) obj2)) {
                arrayList3.add(obj2);
            }
        }
        final ArrayList arrayList4 = arrayList3;
        if (CollectionsKt.any(arrayList4)) {
            ICReporterKt.info(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.ChangedJavaFilesProcessor.process.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Some java files are removed: [" + CollectionsKt.joinToString$default(arrayList4, null, null, null, 0, null, null, 63, null) + ']';
                }
            });
            return new ChangesEither.Unknown(BuildAttribute.JAVA_CHANGE_UNTRACKED_FILE_IS_REMOVED);
        }
        HashSet hashSet = new HashSet();
        for (File file : arrayList2) {
            StringsKt.equals(FilesKt.getExtension(file), SuffixConstants.EXTENSION_java, true);
            final PsiFile psiFileInvoke = this.psiFileFactory.invoke(file);
            if (!(psiFileInvoke instanceof PsiJavaFile)) {
                ICReporterKt.info(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.ChangedJavaFilesProcessor.process.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        StringBuilder sb = new StringBuilder("Expected PsiJavaFile, got ");
                        PsiFile psiFile = psiFileInvoke;
                        return sb.append(psiFile != null ? psiFile.getClass() : null).toString();
                    }
                });
                return new ChangesEither.Unknown(BuildAttribute.JAVA_CHANGE_UNEXPECTED_PSI);
            }
            PsiClass[] classes = ((PsiJavaFile) psiFileInvoke).getClasses();
            Intrinsics.checkNotNullExpressionValue(classes, "getClasses(...)");
            for (PsiClass psiClass : classes) {
                Intrinsics.checkNotNull(psiClass);
                addLookupSymbols(psiClass, hashSet);
            }
        }
        HashSet hashSet2 = hashSet;
        this.allSymbols.addAll(hashSet2);
        return new ChangesEither.Known(hashSet2, null, 2, null);
    }

    private final void addLookupSymbols(PsiClass psiClass, Set<LookupSymbol> set) {
        String strRemoveSuffix;
        String qualifiedName = psiClass.getQualifiedName();
        if (qualifiedName == null) {
            qualifiedName = "";
        }
        String name2 = psiClass.getName();
        if (name2 == null) {
            name2 = "";
        }
        if (Intrinsics.areEqual(qualifiedName, psiClass.getName())) {
            strRemoveSuffix = "";
        } else {
            StringBuilder sb = new StringBuilder(".");
            String name3 = psiClass.getName();
            Intrinsics.checkNotNull(name3);
            strRemoveSuffix = StringsKt.removeSuffix(qualifiedName, (CharSequence) sb.append(name3).toString());
        }
        set.add(new LookupSymbol(name2, strRemoveSuffix));
        PsiMethod[] methods = psiClass.getMethods();
        Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
        for (PsiMethod psiMethod : methods) {
            String str = psiMethod.getName();
            Intrinsics.checkNotNullExpressionValue(str, "getName(...)");
            set.add(new LookupSymbol(str, qualifiedName));
        }
        PsiField[] fields = psiClass.getFields();
        Intrinsics.checkNotNullExpressionValue(fields, "getFields(...)");
        for (PsiField psiField : fields) {
            String str2 = psiField.getName();
            if (str2 == null) {
                str2 = "";
            }
            set.add(new LookupSymbol(str2, qualifiedName));
        }
        PsiClass[] innerClasses = psiClass.getInnerClasses();
        Intrinsics.checkNotNullExpressionValue(innerClasses, "getInnerClasses(...)");
        for (PsiClass psiClass2 : innerClasses) {
            Intrinsics.checkNotNull(psiClass2);
            addLookupSymbols(psiClass2, set);
        }
    }
}
