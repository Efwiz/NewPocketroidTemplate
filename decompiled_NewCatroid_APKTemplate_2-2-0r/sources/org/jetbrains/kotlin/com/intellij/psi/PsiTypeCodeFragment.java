package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiTypeCodeFragment extends JavaCodeFragment {
    PsiType getType() throws TypeSyntaxException, NoTypeException;

    boolean isVoidValid();

    public static class IncorrectTypeException extends Exception {
        public IncorrectTypeException(String str) {
            super(str);
        }
    }

    public static class TypeSyntaxException extends IncorrectTypeException {
        private final int myErrorOffset;

        public TypeSyntaxException(String str) {
            this(str, -1);
        }

        public TypeSyntaxException(String str, int i) {
            super(str);
            this.myErrorOffset = i;
        }

        public int getErrorOffset() {
            return this.myErrorOffset;
        }
    }

    public static class NoTypeException extends IncorrectTypeException {
        public NoTypeException(String str) {
            super(str);
        }
    }
}
