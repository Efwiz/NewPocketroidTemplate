package io.github.rosemoe.sora.text;

import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class TextReference implements CharSequence {
    private final int end;
    private final CharSequence ref;
    private final int start;
    private Validator validator;

    public interface Validator {
        void validate();
    }

    public TextReference(CharSequence charSequence) {
        this(charSequence, 0, charSequence.length());
    }

    public TextReference(CharSequence charSequence, int i, int i2) {
        this.ref = (CharSequence) Objects.requireNonNull(charSequence);
        this.start = i;
        this.end = i2;
        if (i > i2) {
            throw new IllegalArgumentException("start > end");
        }
        if (i < 0) {
            throw new StringIndexOutOfBoundsException(i);
        }
        if (i2 > charSequence.length()) {
            throw new StringIndexOutOfBoundsException(i2);
        }
    }

    public CharSequence getReference() {
        return this.ref;
    }

    @Override // java.lang.CharSequence
    public int length() {
        validateAccess();
        return this.end - this.start;
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        if (i < 0 || i >= length()) {
            throw new StringIndexOutOfBoundsException(i);
        }
        validateAccess();
        return this.ref.charAt(this.start + i);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return this.ref.subSequence(this.start, this.end).toString();
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        if (i < 0 || i >= length()) {
            throw new StringIndexOutOfBoundsException(i);
        }
        if (i2 < 0 || i2 >= length()) {
            throw new StringIndexOutOfBoundsException(i2);
        }
        validateAccess();
        CharSequence charSequence = this.ref;
        int i3 = this.start;
        return new TextReference(charSequence, i + i3, i3 + i2).setValidator(this.validator);
    }

    public TextReference setValidator(Validator validator) {
        this.validator = validator;
        return this;
    }

    public void validateAccess() {
        Validator validator = this.validator;
        if (validator != null) {
            validator.validate();
        }
    }

    public static class ValidateFailedException extends RuntimeException {
        public ValidateFailedException() {
        }

        public ValidateFailedException(String str) {
            super(str);
        }
    }
}
