package io.github.rosemoe.sora.text;

import java.text.CharacterIterator;

/* JADX INFO: loaded from: classes11.dex */
public class CharSequenceIterator implements CharacterIterator {
    private int index;
    private final CharSequence src;

    @Override // java.text.CharacterIterator
    public int getBeginIndex() {
        return 0;
    }

    public CharSequenceIterator(CharSequence charSequence) {
        this.src = charSequence;
    }

    @Override // java.text.CharacterIterator
    public char first() {
        this.index = 0;
        return current();
    }

    @Override // java.text.CharacterIterator
    public char last() {
        int length = this.src.length() - 1;
        this.index = length;
        if (length < 0) {
            this.index = 0;
        }
        return current();
    }

    @Override // java.text.CharacterIterator
    public char current() {
        if (this.index == getEndIndex()) {
            return (char) 65535;
        }
        return this.src.charAt(this.index);
    }

    @Override // java.text.CharacterIterator
    public char next() {
        this.index++;
        return current();
    }

    @Override // java.text.CharacterIterator
    public char previous() {
        int i = this.index - 1;
        this.index = i;
        if (i < 0) {
            this.index = 0;
        }
        return current();
    }

    @Override // java.text.CharacterIterator
    public char setIndex(int i) {
        this.index = i;
        return current();
    }

    @Override // java.text.CharacterIterator
    public int getEndIndex() {
        return this.src.length();
    }

    @Override // java.text.CharacterIterator
    public int getIndex() {
        return this.index;
    }

    @Override // java.text.CharacterIterator
    public Object clone() {
        CharSequenceIterator charSequenceIterator = new CharSequenceIterator(this.src);
        charSequenceIterator.index = this.index;
        return charSequenceIterator;
    }
}
