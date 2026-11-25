package org.jetbrains.kotlin.org.jline.utils;

import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DiffHelper {

    public enum Operation {
        DELETE,
        INSERT,
        EQUAL
    }

    public static class Diff {
        public final Operation operation;
        public final AttributedString text;

        public Diff(Operation operation, AttributedString attributedString) {
            this.operation = operation;
            this.text = attributedString;
        }

        public String toString() {
            return "Diff(" + this.operation + ",\"" + ((Object) this.text) + "\")";
        }
    }

    public static List<Diff> diff(AttributedString attributedString, AttributedString attributedString2) {
        int length = attributedString.length();
        int length2 = attributedString2.length();
        int iMin = Math.min(length, length2);
        int i = 0;
        int i2 = -1;
        while (i < iMin && attributedString.charAt(i) == attributedString2.charAt(i) && attributedString.styleAt(i).equals(attributedString2.styleAt(i))) {
            if (!attributedString.isHidden(i)) {
                i2 = -1;
            } else if (i2 < 0) {
                i2 = i;
            }
            i++;
        }
        if (i2 >= 0 && ((i == length || !attributedString.isHidden(i)) && (i == length2 || !attributedString2.isHidden(i)))) {
            i = i2;
        }
        int i3 = 0;
        int i4 = -1;
        while (i3 < iMin - i) {
            int i5 = (length - i3) - 1;
            int i6 = (length2 - i3) - 1;
            if (attributedString.charAt(i5) != attributedString2.charAt(i6) || !attributedString.styleAt(i5).equals(attributedString2.styleAt(i6))) {
                break;
            }
            if (!attributedString.isHidden(i5)) {
                i4 = -1;
            } else if (i4 < 0) {
                i4 = i3;
            }
            i3++;
        }
        if (i4 >= 0) {
            i3 = i4;
        }
        LinkedList linkedList = new LinkedList();
        if (i > 0) {
            linkedList.add(new Diff(Operation.EQUAL, attributedString.subSequence(0, i)));
        }
        int i7 = i + i3;
        if (length2 > i7) {
            linkedList.add(new Diff(Operation.INSERT, attributedString2.subSequence(i, length2 - i3)));
        }
        if (length > i7) {
            linkedList.add(new Diff(Operation.DELETE, attributedString.subSequence(i, length - i3)));
        }
        if (i3 > 0) {
            linkedList.add(new Diff(Operation.EQUAL, attributedString.subSequence(length - i3, length)));
        }
        return linkedList;
    }
}
