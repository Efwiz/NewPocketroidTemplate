package org.jetbrains.kotlin.analysis.providers;

import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassLikeDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeAlias;

/* JADX INFO: compiled from: KotlinDeclarationProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\nH&J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\f\u001a\u00020\nH&J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00072\u0006\u0010\u000f\u001a\u00020\nH&J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\nH&J\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00072\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00072\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00042\u0006\u0010\f\u001a\u00020\nH&J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00072\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001d0\u00042\u0006\u0010\f\u001a\u00020\nH&J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00072\u0006\u0010\u001a\u001a\u00020\u001bH&¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProvider;", "", "()V", "computePackageSetWithTopLevelCallableDeclarations", "", "", "findFilesForFacade", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "findFilesForFacadeByPackage", "packageFqName", "findFilesForScript", "Lorg/jetbrains/kotlin/psi/KtScript;", "scriptFqName", "findInternalFilesForFacade", "getAllClassesByClassId", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getAllTypeAliasesByClassId", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "getClassLikeDeclarationByClassId", "Lorg/jetbrains/kotlin/psi/KtClassLikeDeclaration;", "getTopLevelCallableFiles", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "getTopLevelCallableNamesInPackage", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelFunctions", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getTopLevelKotlinClassLikeDeclarationNamesInPackage", "getTopLevelProperties", "Lorg/jetbrains/kotlin/psi/KtProperty;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinDeclarationProvider {
    public abstract Set<String> computePackageSetWithTopLevelCallableDeclarations();

    public abstract Collection<KtFile> findFilesForFacade(FqName facadeFqName);

    public abstract Collection<KtFile> findFilesForFacadeByPackage(FqName packageFqName);

    public abstract Collection<KtScript> findFilesForScript(FqName scriptFqName);

    public abstract Collection<KtFile> findInternalFilesForFacade(FqName facadeFqName);

    public abstract Collection<KtClassOrObject> getAllClassesByClassId(ClassId classId);

    public abstract Collection<KtTypeAlias> getAllTypeAliasesByClassId(ClassId classId);

    public abstract KtClassLikeDeclaration getClassLikeDeclarationByClassId(ClassId classId);

    public abstract Collection<KtFile> getTopLevelCallableFiles(CallableId callableId);

    public abstract Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName);

    public abstract Collection<KtNamedFunction> getTopLevelFunctions(CallableId callableId);

    public abstract Set<Name> getTopLevelKotlinClassLikeDeclarationNamesInPackage(FqName packageFqName);

    public abstract Collection<KtProperty> getTopLevelProperties(CallableId callableId);
}
