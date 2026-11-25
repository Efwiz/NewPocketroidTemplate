package org.eclipse.jdt.internal.compiler.problem;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.util.HashtableOfInt;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class DefaultProblemFactory implements IProblemFactory {
    private static HashtableOfInt DEFAULT_LOCALE_TEMPLATES;
    private Locale locale;
    public HashtableOfInt messageTemplates;
    private static final char[] DOUBLE_QUOTES = "''".toCharArray();
    private static final char[] SINGLE_QUOTE = FormatHelper.QUOTE.toCharArray();
    private static final char[] FIRST_ARGUMENT = "{0}".toCharArray();

    private static final int keyFromID(int i) {
        return i + 1;
    }

    public DefaultProblemFactory() {
        this(Locale.getDefault());
    }

    public DefaultProblemFactory(Locale locale) {
        setLocale(locale);
    }

    @Override // org.eclipse.jdt.internal.compiler.IProblemFactory
    public CategorizedProblem createProblem(char[] cArr, int i, String[] strArr, String[] strArr2, int i2, int i3, int i4, int i5, int i6) {
        return new DefaultProblem(cArr, getLocalizedMessage(i, strArr2), i, strArr, i2, i3, i4, i5, i6);
    }

    @Override // org.eclipse.jdt.internal.compiler.IProblemFactory
    public CategorizedProblem createProblem(char[] cArr, int i, String[] strArr, int i2, String[] strArr2, int i3, int i4, int i5, int i6, int i7) {
        return new DefaultProblem(cArr, getLocalizedMessage(i, i2, strArr2), i, strArr, i3, i4, i5, i6, i7);
    }

    @Override // org.eclipse.jdt.internal.compiler.IProblemFactory
    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        if (locale == this.locale) {
            return;
        }
        this.locale = locale;
        if (Locale.getDefault().equals(locale)) {
            if (DEFAULT_LOCALE_TEMPLATES == null) {
                DEFAULT_LOCALE_TEMPLATES = loadMessageTemplates(locale);
            }
            this.messageTemplates = DEFAULT_LOCALE_TEMPLATES;
            return;
        }
        this.messageTemplates = loadMessageTemplates(locale);
    }

    @Override // org.eclipse.jdt.internal.compiler.IProblemFactory
    public final String getLocalizedMessage(int i, String[] strArr) {
        return getLocalizedMessage(i, 0, strArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.IProblemFactory
    public final String getLocalizedMessage(int i, int i2, String[] strArr) {
        StringBuffer stringBuffer;
        int i3 = 16777215 & i;
        String str = (String) this.messageTemplates.get(keyFromID(i3));
        if (str == null) {
            return "Unable to retrieve the error message for problem id: " + i3 + ". Check compiler resources.";
        }
        char[] charArray = str.toCharArray();
        if (i2 != 0) {
            String str2 = (String) this.messageTemplates.get(keyFromID(i2));
            if (str2 == null) {
                return "Unable to retrieve the error message elaboration for elaboration id: " + i2 + ". Check compiler resources.";
            }
            charArray = CharOperation.replace(charArray, FIRST_ARGUMENT, str2.toCharArray());
        }
        char[] cArrReplace = CharOperation.replace(charArray, DOUBLE_QUOTES, SINGLE_QUOTE);
        if (strArr == null) {
            return new String(cArrReplace);
        }
        int length = cArrReplace.length;
        int i4 = 0;
        if ((i & Integer.MIN_VALUE) != 0) {
            stringBuffer = new StringBuffer(length + 10 + (strArr.length * 20));
            stringBuffer.append((String) this.messageTemplates.get(keyFromID(514)));
        } else {
            stringBuffer = null;
        }
        while (true) {
            int iIndexOf = CharOperation.indexOf('{', cArrReplace, i4);
            if (iIndexOf <= -1) {
                if (stringBuffer == null) {
                    return new String(cArrReplace);
                }
                stringBuffer.append(cArrReplace, i4, length - i4);
                break;
            }
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer((strArr.length * 20) + length);
            }
            stringBuffer.append(cArrReplace, i4, iIndexOf - i4);
            int i5 = iIndexOf + 1;
            int iIndexOf2 = CharOperation.indexOf('}', cArrReplace, i5);
            if (iIndexOf2 > -1) {
                int i6 = iIndexOf2 - iIndexOf;
                try {
                    stringBuffer.append(strArr[CharOperation.parseInt(cArrReplace, i5, i6 - 1)]);
                } catch (ArrayIndexOutOfBoundsException unused) {
                    return "Cannot bind message for problem (id: " + i3 + ") \"" + new String(cArrReplace) + "\" with arguments: {" + Util.toString(strArr) + "}";
                } catch (NumberFormatException unused2) {
                    stringBuffer.append(cArrReplace, i5, i6);
                }
                i4 = iIndexOf2 + 1;
            } else {
                stringBuffer.append(cArrReplace, iIndexOf, length);
                break;
            }
        }
        return new String(stringBuffer.toString());
    }

    public final String localizedMessage(CategorizedProblem categorizedProblem) {
        return getLocalizedMessage(categorizedProblem.getID(), categorizedProblem.getArguments());
    }

    public static HashtableOfInt loadMessageTemplates(Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("org.eclipse.jdt.internal.compiler.problem.messages", locale);
            HashtableOfInt hashtableOfInt = new HashtableOfInt(700);
            Enumeration<String> keys = bundle.getKeys();
            while (keys.hasMoreElements()) {
                String strNextElement = keys.nextElement();
                try {
                    hashtableOfInt.put(keyFromID(Integer.parseInt(strNextElement)), bundle.getString(strNextElement));
                } catch (NumberFormatException | MissingResourceException unused) {
                }
            }
            return hashtableOfInt;
        } catch (MissingResourceException e) {
            System.out.println("Missing resource : " + "org.eclipse.jdt.internal.compiler.problem.messages".replace('.', '/') + ".properties for locale " + locale);
            throw e;
        }
    }
}
