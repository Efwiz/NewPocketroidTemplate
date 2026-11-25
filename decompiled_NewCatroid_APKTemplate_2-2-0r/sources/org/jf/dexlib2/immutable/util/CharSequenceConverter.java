package org.jf.dexlib2.immutable.util;

import java.util.List;
import org.jf.util.ImmutableConverter;

/* JADX INFO: loaded from: classes6.dex */
public final class CharSequenceConverter {
    private static final ImmutableConverter<String, CharSequence> CONVERTER = new ImmutableConverter<String, CharSequence>() { // from class: org.jf.dexlib2.immutable.util.CharSequenceConverter.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public boolean isImmutable(CharSequence charSequence) {
            return charSequence instanceof String;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public String makeImmutable(CharSequence charSequence) {
            return charSequence.toString();
        }
    };

    private CharSequenceConverter() {
    }

    public static List<String> immutableStringList(Iterable<? extends CharSequence> iterable) {
        return CONVERTER.toList(iterable);
    }
}
