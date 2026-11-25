package org.jetbrains.kotlin.cli.jvm.compiler;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationContainer;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: findMainClass.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u001a\u0016\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"findMainClass", "Lorg/jetbrains/kotlin/name/FqName;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "findMainFunction", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "Lorg/jetbrains/kotlin/idea/MainFunctionDetector;", "container", "Lorg/jetbrains/kotlin/psi/KtDeclarationContainer;", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FindMainClassKt {
    public static final FqName findMainClass(BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, List<? extends KtFile> files) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(files, "files");
        final MainFunctionDetector mainFunctionDetector = new MainFunctionDetector(bindingContext, languageVersionSettings);
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        for (Object obj3 : SequencesKt.map(CollectionsKt.asSequence(files), new Function1<KtFile, FqName>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.FindMainClassKt.findMainClass.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final FqName invoke(KtFile file) {
                FqName fqNameParent;
                FqName fqNameParent2;
                Intrinsics.checkNotNullParameter(file, "file");
                KtNamedFunction ktNamedFunctionFindMainFunction = FindMainClassKt.findMainFunction(mainFunctionDetector, file);
                if (ktNamedFunctionFindMainFunction == null) {
                    return null;
                }
                if (ktNamedFunctionFindMainFunction.isTopLevel()) {
                    fqNameParent = JvmFileClassUtil.getFileClassInfoNoResolve(file).getFacadeClassFqName();
                } else {
                    KtClassOrObject ktClassOrObject = (KtClassOrObject) PsiTreeUtil.getParentOfType(ktNamedFunctionFindMainFunction, KtClassOrObject.class, true);
                    if ((ktClassOrObject instanceof KtObjectDeclaration) && ((KtObjectDeclaration) ktClassOrObject).isCompanion()) {
                        FqName fqName = ktNamedFunctionFindMainFunction.mo7391getFqName();
                        if (fqName == null || (fqNameParent2 = fqName.parent()) == null) {
                            return null;
                        }
                        fqNameParent = fqNameParent2.parent();
                    } else {
                        FqName fqName2 = ktNamedFunctionFindMainFunction.mo7391getFqName();
                        if (fqName2 == null) {
                            return null;
                        }
                        fqNameParent = fqName2.parent();
                    }
                }
                return fqNameParent;
            }
        })) {
            if (((FqName) obj3) != null) {
                if (z) {
                    return (FqName) obj;
                }
                z = true;
                obj2 = obj3;
            }
        }
        if (z) {
            obj = obj2;
        }
        return (FqName) obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KtNamedFunction findMainFunction(MainFunctionDetector mainFunctionDetector, KtDeclarationContainer ktDeclarationContainer) {
        List<KtDeclaration> declarations = ktDeclarationContainer.getDeclarations();
        Intrinsics.checkNotNullExpressionValue(declarations, "getDeclarations(...)");
        ArrayList arrayList = new ArrayList();
        for (KtDeclaration ktDeclaration : declarations) {
            KtNamedFunction ktNamedFunctionFindMainFunction = null;
            if (ktDeclaration instanceof KtNamedFunction) {
                Intrinsics.checkNotNull(ktDeclaration);
                if (!MainFunctionDetector.isMain$default(mainFunctionDetector, (KtNamedFunction) ktDeclaration, false, false, 6, null)) {
                    ktDeclaration = null;
                }
                ktNamedFunctionFindMainFunction = (KtNamedFunction) ktDeclaration;
            } else if (ktDeclaration instanceof KtDeclarationContainer) {
                Intrinsics.checkNotNull(ktDeclaration);
                ktNamedFunctionFindMainFunction = findMainFunction(mainFunctionDetector, (KtDeclarationContainer) ktDeclaration);
            }
            if (ktNamedFunctionFindMainFunction != null) {
                arrayList.add(ktNamedFunctionFindMainFunction);
            }
        }
        return (KtNamedFunction) CollectionsKt.singleOrNull((List) arrayList);
    }
}
