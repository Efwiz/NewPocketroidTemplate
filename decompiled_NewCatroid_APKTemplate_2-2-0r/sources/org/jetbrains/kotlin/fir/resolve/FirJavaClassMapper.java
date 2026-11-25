package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;

/* JADX INFO: compiled from: FirJavaClassMapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\b\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirJavaClassMapper;", "Lorg/jetbrains/kotlin/fir/scopes/FirPlatformClassMapper;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "getCorrespondingKotlinClass", "Lorg/jetbrains/kotlin/name/ClassId;", "classId", "getCorrespondingPlatformClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaClassMapper extends FirPlatformClassMapper {
    private final FirSession session;

    public FirJavaClassMapper(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper
    public FirRegularClass getCorrespondingPlatformClass(FirClassLikeDeclaration declaration) {
        FirClassLikeSymbol<?> classLikeSymbolByClassId;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        ClassId correspondingPlatformClass = getCorrespondingPlatformClass(declaration.getSymbol().getClassId());
        FirClassLikeDeclaration firClassLikeDeclaration = (correspondingPlatformClass == null || (classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(this.session).getClassLikeSymbolByClassId(correspondingPlatformClass)) == null) ? null : (FirClassLikeDeclaration) classLikeSymbolByClassId.getFir();
        if (firClassLikeDeclaration instanceof FirRegularClass) {
            return (FirRegularClass) firClassLikeDeclaration;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper
    public ClassId getCorrespondingPlatformClass(ClassId classId) {
        if (classId == null) {
            return null;
        }
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqNameUnsafe unsafe = classId.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        return javaToKotlinClassMap.mapKotlinToJava(unsafe);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper
    public ClassId getCorrespondingKotlinClass(ClassId classId) {
        if (classId == null) {
            return null;
        }
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqName fqNameAsSingleFqName = classId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        return javaToKotlinClassMap.mapJavaToKotlin(fqNameAsSingleFqName);
    }
}
