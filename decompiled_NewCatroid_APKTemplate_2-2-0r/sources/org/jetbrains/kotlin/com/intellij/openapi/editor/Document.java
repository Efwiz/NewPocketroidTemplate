package org.jetbrains.kotlin.com.intellij.openapi.editor;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;

/* JADX INFO: loaded from: classes3.dex */
public interface Document extends UserDataHolder {
    public static final Document[] EMPTY_ARRAY = new Document[0];

    /* JADX WARN: Code duplicated, block: B:10:0x0016  */
    /* JADX WARN: Code duplicated, block: B:19:0x0028  */
    /* JADX WARN: Code duplicated, block: B:31:0x004d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 5 && i != 6 && i != 7 && i != 8) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 1 && i != 5 && i != 6 && i != 7 && i != 8) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 1) {
            objArr[0] = "range";
        } else if (i == 5 || i == 6) {
            objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
        } else if (i == 7) {
            objArr[0] = "parentDisposable";
        } else if (i != 8) {
            switch (i) {
                case 10:
                case 11:
                    objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                    break;
                case 12:
                    objArr[0] = "block";
                    break;
                case 13:
                    objArr[0] = "textRange";
                    break;
                default:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/Document";
                    break;
            }
        } else {
            objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
        }
        switch (i) {
            case 1:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/Document";
                break;
            case 2:
            default:
                objArr[1] = "getText";
                break;
            case 3:
                objArr[1] = "getCharsSequence";
                break;
            case 4:
                objArr[1] = "getChars";
                break;
            case 9:
            case 14:
                objArr[1] = "createRangeMarker";
                break;
        }
        if (i == 1) {
            objArr[2] = "getText";
        } else if (i == 5 || i == 6 || i == 7) {
            objArr[2] = "addDocumentListener";
        } else if (i != 8) {
            switch (i) {
                case 10:
                    objArr[2] = "addPropertyChangeListener";
                    break;
                case 11:
                    objArr[2] = "removePropertyChangeListener";
                    break;
                case 12:
                    objArr[2] = "removeGuardedBlock";
                    break;
                case 13:
                    objArr[2] = "createRangeMarker";
                    break;
            }
        } else {
            objArr[2] = "removeDocumentListener";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 5 && i != 6 && i != 7 && i != 8) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    throw new IllegalStateException(str2);
            }
        }
        throw new IllegalArgumentException(str2);
    }

    default void addDocumentListener(DocumentListener documentListener) {
        if (documentListener == null) {
            $$$reportNull$$$0(5);
        }
    }

    CharSequence getImmutableCharSequence();

    int getLineCount();

    int getLineEndOffset(int i);

    int getLineNumber(int i);

    int getLineStartOffset(int i);

    long getModificationStamp();

    default boolean isInBulkUpdate() {
        return false;
    }

    boolean isWritable();

    void replaceString(int i, int i2, CharSequence charSequence);

    default void setReadOnly(boolean z) {
    }

    void setText(CharSequence charSequence);

    default String getText() {
        String string = getImmutableCharSequence().toString();
        if (string == null) {
            $$$reportNull$$$0(0);
        }
        return string;
    }

    default CharSequence getCharsSequence() {
        CharSequence immutableCharSequence = getImmutableCharSequence();
        if (immutableCharSequence == null) {
            $$$reportNull$$$0(3);
        }
        return immutableCharSequence;
    }

    default int getTextLength() {
        return getImmutableCharSequence().length();
    }
}
