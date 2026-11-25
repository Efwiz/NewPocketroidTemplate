package org.jetbrains.kotlin.konan.target;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibraryUtilsKt;

/* JADX INFO: compiled from: CompilerOutputKind.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\u0014\u0010\u0007\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/CompilerOutputKind;", "", "(Ljava/lang/String;I)V", "prefix", "", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "suffix", "PROGRAM", "DYNAMIC", "STATIC", "FRAMEWORK", "LIBRARY", "BITCODE", "TEST_BUNDLE", "DYNAMIC_CACHE", "STATIC_CACHE", "PRELIMINARY_CACHE", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public enum CompilerOutputKind {
    PROGRAM { // from class: org.jetbrains.kotlin.konan.target.CompilerOutputKind.PROGRAM
        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String suffix(KonanTarget target) {
            StringBuilder sb = new StringBuilder(".");
            Intrinsics.checkNotNull(target);
            return sb.append(target.getFamily().getExeSuffix()).toString();
        }
    },
    DYNAMIC { // from class: org.jetbrains.kotlin.konan.target.CompilerOutputKind.DYNAMIC
        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String suffix(KonanTarget target) {
            StringBuilder sb = new StringBuilder(".");
            Intrinsics.checkNotNull(target);
            return sb.append(target.getFamily().getDynamicSuffix()).toString();
        }

        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String prefix(KonanTarget target) {
            Intrinsics.checkNotNull(target);
            return target.getFamily().getDynamicPrefix();
        }
    },
    STATIC { // from class: org.jetbrains.kotlin.konan.target.CompilerOutputKind.STATIC
        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String suffix(KonanTarget target) {
            StringBuilder sb = new StringBuilder(".");
            Intrinsics.checkNotNull(target);
            return sb.append(target.getFamily().getStaticSuffix()).toString();
        }

        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String prefix(KonanTarget target) {
            Intrinsics.checkNotNull(target);
            return target.getFamily().getStaticPrefix();
        }
    },
    FRAMEWORK { // from class: org.jetbrains.kotlin.konan.target.CompilerOutputKind.FRAMEWORK
        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String suffix(KonanTarget target) {
            return ".framework";
        }
    },
    LIBRARY { // from class: org.jetbrains.kotlin.konan.target.CompilerOutputKind.LIBRARY
        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String suffix(KonanTarget target) {
            return KotlinLibraryUtilsKt.KLIB_FILE_EXTENSION_WITH_DOT;
        }
    },
    BITCODE { // from class: org.jetbrains.kotlin.konan.target.CompilerOutputKind.BITCODE
        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String suffix(KonanTarget target) {
            return ".bc";
        }
    },
    TEST_BUNDLE { // from class: org.jetbrains.kotlin.konan.target.CompilerOutputKind.TEST_BUNDLE
        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String suffix(KonanTarget target) {
            return ".xctest";
        }
    },
    DYNAMIC_CACHE { // from class: org.jetbrains.kotlin.konan.target.CompilerOutputKind.DYNAMIC_CACHE
        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String suffix(KonanTarget target) {
            StringBuilder sb = new StringBuilder(".");
            Intrinsics.checkNotNull(target);
            return sb.append(target.getFamily().getDynamicSuffix()).toString();
        }

        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String prefix(KonanTarget target) {
            Intrinsics.checkNotNull(target);
            return target.getFamily().getDynamicPrefix();
        }
    },
    STATIC_CACHE { // from class: org.jetbrains.kotlin.konan.target.CompilerOutputKind.STATIC_CACHE
        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String suffix(KonanTarget target) {
            StringBuilder sb = new StringBuilder(".");
            Intrinsics.checkNotNull(target);
            return sb.append(target.getFamily().getStaticSuffix()).toString();
        }

        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String prefix(KonanTarget target) {
            Intrinsics.checkNotNull(target);
            return target.getFamily().getStaticPrefix();
        }
    },
    PRELIMINARY_CACHE { // from class: org.jetbrains.kotlin.konan.target.CompilerOutputKind.PRELIMINARY_CACHE
        @Override // org.jetbrains.kotlin.konan.target.CompilerOutputKind
        public String suffix(KonanTarget target) {
            return "";
        }
    };

    /* synthetic */ CompilerOutputKind(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract String suffix(KonanTarget target);

    public static /* synthetic */ String suffix$default(CompilerOutputKind compilerOutputKind, KonanTarget konanTarget, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: suffix");
        }
        if ((i & 1) != 0) {
            konanTarget = null;
        }
        return compilerOutputKind.suffix(konanTarget);
    }

    public static /* synthetic */ String prefix$default(CompilerOutputKind compilerOutputKind, KonanTarget konanTarget, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: prefix");
        }
        if ((i & 1) != 0) {
            konanTarget = null;
        }
        return compilerOutputKind.prefix(konanTarget);
    }

    public String prefix(KonanTarget target) {
        return "";
    }
}
