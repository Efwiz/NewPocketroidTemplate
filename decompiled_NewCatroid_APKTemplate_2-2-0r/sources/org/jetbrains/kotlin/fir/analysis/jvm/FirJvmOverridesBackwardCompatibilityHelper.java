package org.jetbrains.kotlin.fir.analysis.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.analysis.FirOverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;

/* JADX INFO: compiled from: FirJvmOverridesBackwardCompatibilityHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0017\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/FirJvmOverridesBackwardCompatibilityHelper;", "Lorg/jetbrains/kotlin/fir/analysis/FirOverridesBackwardCompatibilityHelper;", "()V", "additionalCheck", "", "member", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Ljava/lang/Boolean;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmOverridesBackwardCompatibilityHelper extends FirOverridesBackwardCompatibilityHelper {
    public static final FirJvmOverridesBackwardCompatibilityHelper INSTANCE = new FirJvmOverridesBackwardCompatibilityHelper();

    private FirJvmOverridesBackwardCompatibilityHelper() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.FirOverridesBackwardCompatibilityHelper
    protected Boolean additionalCheck(FirCallableDeclaration member) {
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag;
        ClassId classId;
        FqName fqNameAsSingleFqName;
        FqNameUnsafe unsafe;
        Intrinsics.checkNotNullParameter(member, "member");
        if (!DeclarationUtilsKt.isJavaOrEnhancement(member) || (coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(member)) == null || (classId = coneClassLikeLookupTagContainingClassLookupTag.getClassId()) == null || (fqNameAsSingleFqName = classId.asSingleFqName()) == null || (unsafe = fqNameAsSingleFqName.toUnsafe()) == null) {
            return false;
        }
        return JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(unsafe) != null ? true : null;
    }
}
