package org.jetbrains.kotlin.com.intellij.patterns;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public final class StringPattern extends ObjectPattern<String, StringPattern> {
    static final StringPattern STRING_PATTERN = new StringPattern();

    /* JADX WARN: Code duplicated, block: B:12:0x0020  */
    /* JADX WARN: Code duplicated, block: B:33:0x0056  */
    /* JADX WARN: Code duplicated, block: B:49:0x008b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 3 && i != 5 && i != 7 && i != 17 && i != 9 && i != 10) {
            switch (i) {
                case 12:
                case 13:
                case 14:
                case 15:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 3 && i != 5 && i != 7 && i != 17 && i != 9 && i != 10) {
            switch (i) {
                case 12:
                case 13:
                case 14:
                case 15:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        if (i != 1 && i != 3 && i != 5 && i != 7) {
            switch (i) {
                case 9:
                case 10:
                case 12:
                case 13:
                case 14:
                case 15:
                case 17:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/patterns/StringPattern";
                    break;
                case 11:
                    objArr[0] = "pattern";
                    break;
                case 16:
                    objArr[0] = "sequence";
                    break;
                default:
                    objArr[0] = "s";
                    break;
            }
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/patterns/StringPattern";
        }
        if (i == 1) {
            objArr[1] = "startsWith";
        } else if (i == 3) {
            objArr[1] = "endsWith";
        } else if (i == 5) {
            objArr[1] = "contains";
        } else if (i == 7) {
            objArr[1] = "containsChars";
        } else if (i == 17) {
            objArr[1] = "newBombedCharSequence";
        } else if (i != 9 && i != 10) {
            switch (i) {
                case 12:
                    objArr[1] = "contains";
                    break;
                case 13:
                case 15:
                    objArr[1] = "oneOf";
                    break;
                case 14:
                    objArr[1] = "oneOfIgnoreCase";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/patterns/StringPattern";
                    break;
            }
        } else {
            objArr[1] = "matches";
        }
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
                break;
            case 2:
                objArr[2] = "endsWith";
                break;
            case 4:
            case 11:
                objArr[2] = "contains";
                break;
            case 6:
                objArr[2] = "containsChars";
                break;
            case 8:
                objArr[2] = "matches";
                break;
            case 16:
                objArr[2] = "newBombedCharSequence";
                break;
            default:
                objArr[2] = "startsWith";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 5 && i != 7 && i != 17 && i != 9 && i != 10) {
            switch (i) {
                case 12:
                case 13:
                case 14:
                case 15:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private StringPattern() {
        super(new InitialPatternCondition<String>(String.class) { // from class: org.jetbrains.kotlin.com.intellij.patterns.StringPattern.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "builder", "org/jetbrains/kotlin/com/intellij/patterns/StringPattern$1", "append"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
            public boolean accepts(Object obj, ProcessingContext processingContext) {
                return obj instanceof String;
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
            public void append(StringBuilder sb, String str) {
                if (sb == null) {
                    $$$reportNull$$$0(0);
                }
                sb.append("string()");
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern
    public StringPattern oneOf(String... strArr) {
        StringPattern stringPattern = (StringPattern) super.oneOf((Object[]) strArr);
        if (stringPattern == null) {
            $$$reportNull$$$0(13);
        }
        return stringPattern;
    }

    @Override // org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern
    public StringPattern oneOf(Collection<String> collection) {
        StringPattern stringPattern = (StringPattern) super.oneOf((Collection) collection);
        if (stringPattern == null) {
            $$$reportNull$$$0(15);
        }
        return stringPattern;
    }
}
