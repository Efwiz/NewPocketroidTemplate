package com.opencsv;

import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes10.dex */
public class RFC4180Parser extends AbstractCSVParser {
    private static final Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
    private final String quoteCharString;
    private final String separatorAsString;

    @Override // com.opencsv.ICSVParser
    public void setErrorLocale(Locale locale) {
    }

    public RFC4180Parser() {
        this('\"', ',', CSVReaderNullFieldIndicator.NEITHER);
    }

    RFC4180Parser(char c, char c2, CSVReaderNullFieldIndicator cSVReaderNullFieldIndicator) {
        super(c2, c, cSVReaderNullFieldIndicator);
        this.separatorAsString = SPECIAL_REGEX_CHARS.matcher(Character.toString(c2)).replaceAll("\\\\$0");
        this.quoteCharString = Character.toString(c);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.opencsv.AbstractCSVParser
    /* JADX INFO: renamed from: convertToCsvValue */
    public String m2728lambda$parseToLine$0$comopencsvAbstractCSVParser(String str, boolean z) {
        String strReplaceAll = (str != null || this.nullFieldIndicator.equals(CSVReaderNullFieldIndicator.NEITHER)) ? str : "";
        StringBuilder sb = new StringBuilder(strReplaceAll == null ? 16 : strReplaceAll.length() * 2);
        boolean z2 = true;
        boolean z3 = strReplaceAll != null && strReplaceAll.contains(Character.toString(getQuotechar()));
        if (!z && !isSurroundWithQuotes(str, z3)) {
            z2 = false;
        }
        if (z3) {
            strReplaceAll = strReplaceAll.replaceAll(Character.toString(getQuotechar()), Character.toString(getQuotechar()) + Character.toString(getQuotechar()));
        }
        if (z2) {
            sb.append(getQuotechar());
        }
        sb.append(strReplaceAll);
        if (z2) {
            sb.append(getQuotechar());
        }
        return sb.toString();
    }

    @Override // com.opencsv.AbstractCSVParser
    protected String[] parseLine(String str, boolean z) {
        if (!z && this.pending != null) {
            this.pending = null;
        }
        if (str == null) {
            if (this.pending == null) {
                return null;
            }
            String str2 = this.pending;
            this.pending = null;
            return new String[]{str2};
        }
        if (z && this.pending != null) {
            str = this.pending + str;
        }
        this.pending = null;
        if (!StringUtils.contains(str, this.quotechar)) {
            return handleEmptySeparators(tokenizeStringIntoArray(str));
        }
        String[] strArrHandleEmptySeparators = handleEmptySeparators(splitWhileNotInQuotes(str, z));
        for (int i = 0; i < strArrHandleEmptySeparators.length; i++) {
            if (StringUtils.contains(strArrHandleEmptySeparators[i], this.quotechar)) {
                strArrHandleEmptySeparators[i] = handleQuotes(strArrHandleEmptySeparators[i]);
            }
        }
        return strArrHandleEmptySeparators;
    }

    private String[] tokenizeStringIntoArray(String str) {
        return str.split(this.separatorAsString, -1);
    }

    private String[] handleEmptySeparators(String[] strArr) {
        if (this.nullFieldIndicator == CSVReaderNullFieldIndicator.EMPTY_SEPARATORS || this.nullFieldIndicator == CSVReaderNullFieldIndicator.BOTH) {
            for (int i = 0; i < strArr.length; i++) {
                if (strArr[i].isEmpty()) {
                    strArr[i] = null;
                }
            }
        }
        return strArr;
    }

    private String[] splitWhileNotInQuotes(String str, boolean z) {
        ArrayList arrayList = new ArrayList();
        int length = 0;
        while (length < str.length()) {
            int iIndexOf = str.indexOf(this.separator, length);
            int iIndexOf2 = str.indexOf(this.quotechar, length);
            if (iIndexOf == -1) {
                arrayList.add(str.substring(length));
                length = str.length();
            } else {
                if (iIndexOf2 == -1 || iIndexOf2 > iIndexOf || iIndexOf2 != length) {
                    arrayList.add(str.substring(length, iIndexOf));
                } else {
                    iIndexOf = findEndOfFieldFromPosition(str, length);
                    arrayList.add(iIndexOf >= str.length() ? str.substring(length) : str.substring(length, iIndexOf));
                }
                length = iIndexOf + 1;
            }
        }
        if (z && lastElementStartedWithQuoteButDidNotEndInOne(arrayList)) {
            this.pending = arrayList.get(arrayList.size() - 1) + "\n";
            arrayList.remove(arrayList.size() - 1);
        } else if (str.lastIndexOf(this.separator) == str.length() - 1) {
            arrayList.add("");
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private boolean lastElementStartedWithQuoteButDidNotEndInOne(List<String> list) {
        String str = list.get(list.size() - 1);
        return startsButDoesNotEndWithQuote(str) || hasOnlyOneQuote(str) || hasOddQuotes(str);
    }

    private boolean hasOddQuotes(String str) {
        return StringUtils.countMatches(str, this.quotechar) % 2 != 0;
    }

    private boolean hasOnlyOneQuote(String str) {
        return StringUtils.countMatches(str, this.quotechar) == 1;
    }

    private boolean startsButDoesNotEndWithQuote(String str) {
        return str.startsWith(Character.toString(this.quotechar)) && !str.endsWith(Character.toString(this.quotechar));
    }

    private int findEndOfFieldFromPosition(String str, int i) {
        int iIndexOf = str.indexOf(this.quotechar, i + 1);
        boolean z = false;
        while (haveNotFoundLastQuote(str, iIndexOf)) {
            if (!z) {
                int i2 = iIndexOf + 1;
                if (str.charAt(i2) == this.separator) {
                    return i2;
                }
            }
            do {
                iIndexOf = str.indexOf(this.quotechar, iIndexOf + 1);
                z = !z;
                if (!haveNotFoundLastQuote(str, iIndexOf)) {
                    break;
                }
            } while (str.charAt(iIndexOf + 1) == this.quotechar);
        }
        return str.length();
    }

    private boolean haveNotFoundLastQuote(String str, int i) {
        return i != -1 && i < str.length() - 1;
    }

    private String handleQuotes(String str) {
        if (!hasOnlyOneQuote(str) && str.startsWith(this.quoteCharString)) {
            str = StringUtils.removeEnd(StringUtils.removeStart(str, this.quoteCharString), this.quoteCharString);
        }
        String strReplace = StringUtils.replace(str, this.quoteCharString + this.quoteCharString, this.quoteCharString);
        if (!strReplace.isEmpty()) {
            return strReplace;
        }
        if (this.nullFieldIndicator == CSVReaderNullFieldIndicator.BOTH || this.nullFieldIndicator == CSVReaderNullFieldIndicator.EMPTY_QUOTES) {
            return null;
        }
        return strReplace;
    }

    @Override // com.opencsv.AbstractCSVParser, com.opencsv.ICSVParser
    public CSVReaderNullFieldIndicator nullFieldIndicator() {
        return this.nullFieldIndicator;
    }

    @Override // com.opencsv.AbstractCSVParser, com.opencsv.ICSVParser
    public String getPendingText() {
        return StringUtils.defaultString(this.pending);
    }
}
