package org.jetbrains.kotlin.fir.types;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindExtractor;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirFunctionTypeKindService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH&J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\r\u001a\u00020\u000eH&J \u0010\u000f\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\bH&J\u0016\u0010\u0014\u001a\u0004\u0018\u00010\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH&J\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016J\u0018\u0010\u0018\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u0019\u001a\u00020\u0017R\u0012\u0010\u0003\u001a\u00020\u0004X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeKindService;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "extractor", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKindExtractor;", "getExtractor", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKindExtractor;", "extractAllSpecialKindsForFunction", "", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "extractAllSpecialKindsForFunctionTypeRef", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "extractSingleExtensionKindForDeserializedConeType", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "annotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "extractSingleSpecialKindForFunction", "getFunctionKindPackageNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "getKindByClassNamePrefix", "packageFqName", "className", "", "hasExtensionKinds", "", "hasKindWithSpecificPackage", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirFunctionTypeKindService implements FirSessionComponent {
    public abstract List<FunctionTypeKind> extractAllSpecialKindsForFunction(FirFunctionSymbol<?> functionSymbol);

    public abstract List<FunctionTypeKind> extractAllSpecialKindsForFunctionTypeRef(FirFunctionTypeRef typeRef);

    public abstract FunctionTypeKind extractSingleExtensionKindForDeserializedConeType(ClassId classId, List<? extends FirAnnotation> annotations);

    public abstract FunctionTypeKind extractSingleSpecialKindForFunction(FirFunctionSymbol<?> functionSymbol);

    protected abstract FunctionTypeKindExtractor getExtractor();

    public final FunctionTypeKind getKindByClassNamePrefix(FqName packageFqName, String className) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(className, "className");
        FunctionTypeKindExtractor.KindWithArity functionalClassKindWithArity = getExtractor().getFunctionalClassKindWithArity(packageFqName, className);
        if (functionalClassKindWithArity != null) {
            return functionalClassKindWithArity.getKind();
        }
        return null;
    }

    public final boolean hasKindWithSpecificPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return getExtractor().hasKindWithSpecificPackage(packageFqName);
    }

    public final Set<FqName> getFunctionKindPackageNames() {
        return getExtractor().getFunctionKindPackageNames();
    }

    public final boolean hasExtensionKinds() {
        return getExtractor().hasExtensionKinds();
    }
}
