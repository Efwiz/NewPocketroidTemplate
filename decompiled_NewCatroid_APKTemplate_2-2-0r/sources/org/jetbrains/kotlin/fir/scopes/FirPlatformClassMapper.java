package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirPlatformClassMapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\nB\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H&J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H&¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirPlatformClassMapper;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "getCorrespondingKotlinClass", "Lorg/jetbrains/kotlin/name/ClassId;", "classId", "getCorrespondingPlatformClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "Default", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirPlatformClassMapper implements FirSessionComponent {
    public abstract ClassId getCorrespondingKotlinClass(ClassId classId);

    public abstract FirRegularClass getCorrespondingPlatformClass(FirClassLikeDeclaration declaration);

    public abstract ClassId getCorrespondingPlatformClass(ClassId classId);

    /* JADX INFO: compiled from: FirPlatformClassMapper.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0016J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirPlatformClassMapper$Default;", "Lorg/jetbrains/kotlin/fir/scopes/FirPlatformClassMapper;", "()V", "getCorrespondingKotlinClass", "Lorg/jetbrains/kotlin/name/ClassId;", "classId", "getCorrespondingPlatformClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default extends FirPlatformClassMapper {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper
        public ClassId getCorrespondingKotlinClass(ClassId classId) {
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper
        public FirRegularClass getCorrespondingPlatformClass(FirClassLikeDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper
        public ClassId getCorrespondingPlatformClass(ClassId classId) {
            return null;
        }

        private Default() {
        }
    }
}
