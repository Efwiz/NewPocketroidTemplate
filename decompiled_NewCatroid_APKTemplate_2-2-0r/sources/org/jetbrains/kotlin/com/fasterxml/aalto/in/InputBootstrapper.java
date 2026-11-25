package org.jetbrains.kotlin.com.fasterxml.aalto.in;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.BuildConfig;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.fasterxml.aalto.WFCException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class InputBootstrapper {
    final ReaderConfig _config;
    protected int _inputProcessed = 0;
    protected int _inputRow = 0;
    protected int _inputRowStart = 0;
    int mDeclaredXmlVersion = 0;
    String mFoundEncoding;
    final char[] mKeyword;
    String mStandalone;

    public abstract XmlScanner bootstrap() throws XMLStreamException;

    protected abstract int checkKeyword(String str) throws XMLStreamException, IOException;

    protected abstract Location getLocation();

    protected abstract int getNext() throws XMLStreamException, IOException;

    protected abstract int getNextAfterWs(boolean z) throws XMLStreamException, IOException;

    protected abstract void pushback();

    protected abstract int readQuotedValue(char[] cArr, int i) throws XMLStreamException, IOException;

    protected InputBootstrapper(ReaderConfig readerConfig) {
        this._config = readerConfig;
        this.mKeyword = readerConfig.allocSmallCBuffer(60);
    }

    protected void readXmlDeclaration() throws XMLStreamException, IOException {
        int nextAfterWs = getNextAfterWs(false);
        if (nextAfterWs != 118) {
            reportUnexpectedChar(nextAfterWs, "; expected keyword 'version'");
        } else {
            this.mDeclaredXmlVersion = readXmlVersion();
            nextAfterWs = getWsOrChar(63);
        }
        if (nextAfterWs == 101) {
            this.mFoundEncoding = readXmlEncoding();
            nextAfterWs = getWsOrChar(63);
        }
        if (nextAfterWs == 115) {
            this.mStandalone = readXmlStandalone();
            nextAfterWs = getWsOrChar(63);
        }
        if (nextAfterWs != 63) {
            reportUnexpectedChar(nextAfterWs, "; expected \"?>\" end marker");
        }
        int next = getNext();
        if (next != 62) {
            reportUnexpectedChar(next, "; expected \"?>\" end marker");
        }
    }

    private final int readXmlVersion() throws XMLStreamException, IOException {
        String str;
        int iCheckKeyword = checkKeyword("version");
        if (iCheckKeyword != 0) {
            reportUnexpectedChar(iCheckKeyword, "version");
        }
        int quotedValue = readQuotedValue(this.mKeyword, handleEq("version"));
        if (quotedValue == 3) {
            char[] cArr = this.mKeyword;
            if (cArr[0] == '1' && cArr[1] == '.') {
                char c = cArr[2];
                if (c == '0') {
                    return 256;
                }
                if (c == '1') {
                    return 272;
                }
            }
        }
        if (quotedValue < 0) {
            str = FormatHelper.QUOTE + new String(this.mKeyword) + "[..]'";
        } else if (quotedValue != 0) {
            str = FormatHelper.QUOTE + new String(this.mKeyword, 0, quotedValue) + FormatHelper.QUOTE;
        } else {
            str = "<empty>";
        }
        reportPseudoAttrProblem("version", str, BuildConfig.VERSION_NAME, CompilerOptions.VERSION_1_1);
        return 0;
    }

    private final String readXmlEncoding() throws XMLStreamException, IOException {
        int iCheckKeyword = checkKeyword("encoding");
        if (iCheckKeyword != 0) {
            reportUnexpectedChar(iCheckKeyword, "encoding");
        }
        int quotedValue = readQuotedValue(this.mKeyword, handleEq("encoding"));
        if (quotedValue == 0) {
            reportPseudoAttrProblem("encoding", null, null, null);
        }
        if (quotedValue < 0) {
            return new String(this.mKeyword);
        }
        return new String(this.mKeyword, 0, quotedValue);
    }

    private final String readXmlStandalone() throws XMLStreamException, IOException {
        String str;
        int iCheckKeyword = checkKeyword(OutputKeys.STANDALONE);
        if (iCheckKeyword != 0) {
            reportUnexpectedChar(iCheckKeyword, OutputKeys.STANDALONE);
        }
        int quotedValue = readQuotedValue(this.mKeyword, handleEq(OutputKeys.STANDALONE));
        if (quotedValue == 2) {
            char[] cArr = this.mKeyword;
            if (cArr[0] == 'n' && cArr[1] == 'o') {
                return "no";
            }
        } else if (quotedValue == 3) {
            char[] cArr2 = this.mKeyword;
            if (cArr2[0] == 'y' && cArr2[1] == 'e' && cArr2[2] == 's') {
                return "yes";
            }
        }
        if (quotedValue < 0) {
            str = FormatHelper.QUOTE + new String(this.mKeyword) + "[..]'";
        } else if (quotedValue != 0) {
            str = FormatHelper.QUOTE + new String(this.mKeyword, 0, quotedValue) + FormatHelper.QUOTE;
        } else {
            str = "<empty>";
        }
        reportPseudoAttrProblem(OutputKeys.STANDALONE, str, "yes", "no");
        return str;
    }

    private final int handleEq(String str) throws XMLStreamException, IOException {
        int nextAfterWs = getNextAfterWs(false);
        if (nextAfterWs != 61) {
            reportUnexpectedChar(nextAfterWs, "; expected '=' after '" + str + FormatHelper.QUOTE);
        }
        int nextAfterWs2 = getNextAfterWs(false);
        if (nextAfterWs2 != 34 && nextAfterWs2 != 39) {
            reportUnexpectedChar(nextAfterWs2, "; expected a quote character enclosing value for '" + str + FormatHelper.QUOTE);
        }
        return nextAfterWs2;
    }

    private final int getWsOrChar(int i) throws XMLStreamException, IOException {
        int next = getNext();
        if (next == i) {
            return next;
        }
        if (next > 32) {
            reportUnexpectedChar(next, "; expected either '" + ((char) i) + "' or white space");
        }
        if (next == 10 || next == 13) {
            pushback();
        }
        return getNextAfterWs(false);
    }

    protected void reportXmlProblem(String str) throws XMLStreamException {
        throw new WFCException(str, getLocation());
    }

    protected void reportNull() throws XMLStreamException {
        reportXmlProblem("Illegal null byte/char in input stream");
    }

    protected void reportEof() throws XMLStreamException {
        reportXmlProblem("Unexpected end-of-input in xml declaration");
    }

    protected void reportUnexpectedChar(int i, String str) throws XMLStreamException {
        String str2;
        char c = (char) i;
        if (Character.isISOControl(c)) {
            str2 = "Unexpected character (CTRL-CHAR, code " + i + Constant.AFTER_QUTO + str;
        } else {
            str2 = "Unexpected character '" + c + "' (code " + i + Constant.AFTER_QUTO + str;
        }
        reportXmlProblem(str2);
    }

    protected final void reportPseudoAttrProblem(String str, String str2, String str3, String str4) throws XMLStreamException {
        String str5 = str3 == null ? "" : "; expected \"" + str3 + "\" or \"" + str4 + "\"";
        if (str2 == null || str2.length() == 0) {
            reportXmlProblem("Missing XML pseudo-attribute '" + str + "' value" + str5);
        }
        reportXmlProblem("Invalid XML pseudo-attribute '" + str + "' value " + str2 + str5);
    }
}
