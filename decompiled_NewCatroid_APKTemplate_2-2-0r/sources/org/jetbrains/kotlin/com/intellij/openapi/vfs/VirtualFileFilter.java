package org.jetbrains.kotlin.com.intellij.openapi.vfs;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface VirtualFileFilter {
    public static final VirtualFileFilter ALL = new VirtualFileFilter() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "file", "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileFilter$1", "accept"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter
        public boolean accept(VirtualFile virtualFile) {
            if (virtualFile != null) {
                return true;
            }
            $$$reportNull$$$0(0);
            return true;
        }

        public String toString() {
            return "ALL";
        }
    };
    public static final VirtualFileFilter NONE = new VirtualFileFilter() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter.2
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "file", "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileFilter$2", "accept"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter
        public boolean accept(VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(0);
            }
            return false;
        }

        public String toString() {
            return "NONE";
        }
    };

    boolean accept(VirtualFile virtualFile);
}
