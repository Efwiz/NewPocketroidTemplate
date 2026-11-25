package org.jetbrains.kotlin.com.intellij.util.text;

import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.CharSequenceWithStringHash;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;

/* JADX INFO: loaded from: classes3.dex */
final class ImmutableText extends ImmutableCharSequence implements CharSequenceWithStringHash, CharArrayExternalizable {
    private static final ImmutableText EMPTY = new ImmutableText("");
    private transient int hash;
    private InnerLeaf myLastLeaf;
    final CharSequence myNode;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 8 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 8 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "str";
                break;
            case 2:
                objArr[0] = "node";
                break;
            case 3:
                objArr[0] = "sequence";
                break;
            case 4:
            case 5:
                objArr[0] = "seq";
                break;
            case 6:
            case 7:
                objArr[0] = "dest";
                break;
            case 8:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/text/ImmutableText";
                break;
            case 9:
                objArr[0] = "node1";
                break;
            case 10:
                objArr[0] = "node2";
                break;
            default:
                objArr[0] = "obj";
                break;
        }
        if (i == 8) {
            objArr[1] = "toString";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/text/ImmutableText";
        } else {
            objArr[1] = "concatNodes";
        }
        switch (i) {
            case 2:
                objArr[2] = "nodeOf";
                break;
            case 3:
                objArr[2] = "concat";
                break;
            case 4:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 5:
                objArr[2] = "insert";
                break;
            case 6:
            case 7:
                objArr[2] = "getChars";
                break;
            case 8:
            case 11:
                break;
            case 9:
            case 10:
                objArr[2] = "concatNodes";
                break;
            default:
                objArr[2] = "valueOf";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 8 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private ImmutableText(CharSequence charSequence) {
        this.myNode = charSequence;
    }

    static ImmutableText valueOf(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(0);
        }
        if (obj instanceof ImmutableText) {
            return (ImmutableText) obj;
        }
        return obj instanceof CharSequence ? valueOf((CharSequence) obj) : valueOf((CharSequence) String.valueOf(obj));
    }

    private static ImmutableText valueOf(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        if (charSequence instanceof ByteArrayCharSequence) {
            return new ImmutableText(charSequence);
        }
        if (charSequence.length() == 0) {
            return EMPTY;
        }
        return new ImmutableText(charSequence.toString());
    }

    private CharSequence ensureChunked() {
        if (length() > 64) {
            CharSequence charSequence = this.myNode;
            if (!(charSequence instanceof CompositeNode)) {
                return nodeOf(charSequence, 0, length());
            }
        }
        return this.myNode;
    }

    private static CharSequence nodeOf(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        if (i2 <= 64) {
            return charSequence.subSequence(i, i2 + i).toString();
        }
        int i3 = ((i2 + 64) >> 1) & (-64);
        return new CompositeNode(nodeOf(charSequence, i, i3), nodeOf(charSequence, i + i3, i2 - i3));
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.myNode.length();
    }

    private ImmutableText concat(ImmutableText immutableText) {
        if (immutableText.length() == 0) {
            return this;
        }
        return length() == 0 ? immutableText : new ImmutableText(concatNodes(ensureChunked(), immutableText.ensureChunked()));
    }

    private ImmutableText subtext(int i) {
        return subtext(i, length());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.text.ImmutableCharSequence
    public ImmutableCharSequence replace(int i, int i2, CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        if (i == i2) {
            return insert(i, charSequence);
        }
        if (charSequence.length() == 0) {
            return delete(i, i2);
        }
        if (i > i2) {
            throw new IndexOutOfBoundsException();
        }
        return subtext(0, i).concat(valueOf(charSequence)).concat(subtext(i2));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.text.ImmutableCharSequence
    public ImmutableText insert(int i, CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(5);
        }
        return charSequence.length() == 0 ? this : subtext(0, i).concat(valueOf(charSequence)).concat(subtext(i));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.text.ImmutableCharSequence
    public ImmutableText delete(int i, int i2) {
        if (i == i2) {
            return this;
        }
        if (i > i2) {
            throw new IndexOutOfBoundsException();
        }
        return subtext(0, i).concat(subtext(i2));
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return (i == 0 && i2 == length()) ? this : new CharSequenceSubSequence(this, i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ImmutableText) {
            return CharArrayUtil.regionMatches(this, 0, (ImmutableText) obj);
        }
        return false;
    }

    public int hashCode() {
        int i = this.hash;
        if (i != 0) {
            return i;
        }
        int iStringHashCode = Strings.stringHashCode(this, 0, length());
        this.hash = iStringHashCode;
        return iStringHashCode;
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        InnerLeaf innerLeafFindLeaf = this.myLastLeaf;
        if (innerLeafFindLeaf == null || i < innerLeafFindLeaf.start || i >= innerLeafFindLeaf.end) {
            innerLeafFindLeaf = findLeaf(i);
            this.myLastLeaf = innerLeafFindLeaf;
        }
        return innerLeafFindLeaf.leafNode.charAt(i - innerLeafFindLeaf.start);
    }

    private InnerLeaf findLeaf(int i) {
        if (i < 0) {
            throw outOfRange(i);
        }
        CharSequence charSequence = this.myNode;
        int length = charSequence.length();
        int i2 = 0;
        while (i < length) {
            if (!(charSequence instanceof CompositeNode)) {
                return new InnerLeaf(charSequence, i2, length + i2);
            }
            CompositeNode compositeNode = (CompositeNode) charSequence;
            int length2 = compositeNode.head.length();
            if (i < length2) {
                charSequence = compositeNode.head;
                length = length2;
            } else {
                i2 += length2;
                i -= length2;
                charSequence = compositeNode.tail;
                length -= length2;
            }
        }
        throw outOfRange(i);
    }

    private IndexOutOfBoundsException outOfRange(int i) {
        return new IndexOutOfBoundsException("Index out of range: " + i + "; length: " + length());
    }

    private static final class InnerLeaf {
        final int end;
        final CharSequence leafNode;
        final int start;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "leafNode", "org/jetbrains/kotlin/com/intellij/util/text/ImmutableText$InnerLeaf", "<init>"));
        }

        private InnerLeaf(CharSequence charSequence, int i, int i2) {
            if (charSequence == null) {
                $$$reportNull$$$0(0);
            }
            this.leafNode = charSequence;
            this.start = i;
            this.end = i2;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.text.ImmutableCharSequence
    public ImmutableText subtext(int i, int i2) {
        if (i < 0 || i > i2 || i2 > length()) {
            throw new IndexOutOfBoundsException();
        }
        if (i == 0 && i2 == length()) {
            return this;
        }
        if (i == i2) {
            return EMPTY;
        }
        return new ImmutableText(this.myNode.subSequence(i, i2));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.text.CharArrayExternalizable
    public void getChars(int i, int i2, char[] cArr, int i3) {
        if (cArr == null) {
            $$$reportNull$$$0(6);
        }
        getChars(this.myNode, i, i2, cArr, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void getChars(CharSequence charSequence, int i, int i2, char[] cArr, int i3) {
        if (cArr == null) {
            $$$reportNull$$$0(7);
        }
        if (charSequence instanceof String) {
            ((String) charSequence).getChars(i, i2, cArr, i3);
        } else if (charSequence instanceof ByteArrayCharSequence) {
            ((ByteArrayCharSequence) charSequence).getChars(i, i2, cArr, i3);
        } else {
            ((CompositeNode) charSequence).getChars(i, i2, cArr, i3);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.text.ImmutableCharSequence, java.lang.CharSequence
    public String toString() {
        String string = this.myNode.toString();
        if (string == null) {
            $$$reportNull$$$0(8);
        }
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharSequence concatNodes(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(9);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(10);
        }
        if (charSequence.length() + charSequence2.length() <= 64) {
            String strConcat = charSequence.toString().concat(charSequence2.toString());
            if (strConcat == null) {
                $$$reportNull$$$0(11);
            }
            return strConcat;
        }
        if (shouldRebalance(charSequence, charSequence2)) {
            do {
                CompositeNode compositeNode = (CompositeNode) charSequence2;
                if (compositeNode.head.length() > compositeNode.tail.length()) {
                    charSequence2 = compositeNode.rightRotation();
                }
                CompositeNode compositeNode2 = (CompositeNode) charSequence2;
                charSequence = concatNodes(charSequence, compositeNode2.head);
                charSequence2 = compositeNode2.tail;
            } while (shouldRebalance(charSequence, charSequence2));
        } else if (shouldRebalance(charSequence2, charSequence)) {
            do {
                CompositeNode compositeNode3 = (CompositeNode) charSequence;
                if (compositeNode3.tail.length() > compositeNode3.head.length()) {
                    charSequence = compositeNode3.leftRotation();
                }
                CompositeNode compositeNode4 = (CompositeNode) charSequence;
                charSequence2 = concatNodes(compositeNode4.tail, charSequence2);
                charSequence = compositeNode4.head;
            } while (shouldRebalance(charSequence2, charSequence));
        }
        return new CompositeNode(charSequence, charSequence2);
    }

    private static boolean shouldRebalance(CharSequence charSequence, CharSequence charSequence2) {
        return (charSequence.length() << 1) < charSequence2.length() && (charSequence2 instanceof CompositeNode);
    }

    static final class CompositeNode implements CharSequence {
        final int count;
        final CharSequence head;
        final CharSequence tail;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "dest", "org/jetbrains/kotlin/com/intellij/util/text/ImmutableText$CompositeNode", "getChars"));
        }

        CompositeNode(CharSequence charSequence, CharSequence charSequence2) {
            this.count = charSequence.length() + charSequence2.length();
            this.head = charSequence;
            this.tail = charSequence2;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.count;
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            int length = this.head.length();
            return i < length ? this.head.charAt(i) : this.tail.charAt(i - length);
        }

        CompositeNode rightRotation() {
            CharSequence charSequence = this.head;
            if (!(charSequence instanceof CompositeNode)) {
                return this;
            }
            CompositeNode compositeNode = (CompositeNode) charSequence;
            return new CompositeNode(compositeNode.head, new CompositeNode(compositeNode.tail, this.tail));
        }

        CompositeNode leftRotation() {
            CharSequence charSequence = this.tail;
            if (!(charSequence instanceof CompositeNode)) {
                return this;
            }
            CompositeNode compositeNode = (CompositeNode) charSequence;
            CharSequence charSequence2 = compositeNode.head;
            return new CompositeNode(new CompositeNode(this.head, charSequence2), compositeNode.tail);
        }

        void getChars(int i, int i2, char[] cArr, int i3) {
            if (cArr == null) {
                $$$reportNull$$$0(0);
            }
            int length = this.head.length();
            if (i2 <= length) {
                ImmutableText.getChars(this.head, i, i2, cArr, i3);
            } else if (i >= length) {
                ImmutableText.getChars(this.tail, i - length, i2 - length, cArr, i3);
            } else {
                ImmutableText.getChars(this.head, i, length, cArr, i3);
                ImmutableText.getChars(this.tail, 0, i2 - length, cArr, (i3 + length) - i);
            }
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            int length = this.head.length();
            if (i2 <= length) {
                return this.head.subSequence(i, i2);
            }
            if (i >= length) {
                return this.tail.subSequence(i - length, i2 - length);
            }
            if (i == 0 && i2 == this.count) {
                return this;
            }
            int i3 = i2 - i;
            if (i3 >= 64) {
                return ImmutableText.concatNodes(this.head.subSequence(i, length), this.tail.subSequence(0, i2 - length));
            }
            char[] cArr = new char[i3];
            ImmutableText.getChars(this.head, i, length, cArr, 0);
            ImmutableText.getChars(this.tail, 0, i2 - length, cArr, length - i);
            return new String(cArr);
        }

        @Override // java.lang.CharSequence
        public String toString() {
            int length = length();
            char[] cArr = new char[length];
            getChars(0, length, cArr, 0);
            return new String(cArr);
        }
    }
}
