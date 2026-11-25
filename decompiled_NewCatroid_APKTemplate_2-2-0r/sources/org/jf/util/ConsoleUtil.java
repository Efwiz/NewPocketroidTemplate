package org.jf.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes6.dex */
public class ConsoleUtil {
    public static int getConsoleWidth() {
        try {
            if (System.getProperty(SystemProperties.OS_NAME).toLowerCase().contains("windows")) {
                return attemptMode();
            }
            return attemptStty();
        } catch (Exception unused) {
            return 80;
        }
    }

    private static int attemptStty() {
        String strAttemptCommand = attemptCommand(new String[]{"sh", "-c", "stty size < /dev/tty"});
        if (strAttemptCommand == null) {
            return 80;
        }
        String[] strArrSplit = strAttemptCommand.split(" ");
        if (strArrSplit.length < 2) {
            return 80;
        }
        return Integer.parseInt(strArrSplit[1]);
    }

    private static int attemptMode() {
        String strAttemptCommand = attemptCommand(new String[]{"mode", "con"});
        if (strAttemptCommand == null) {
            return 80;
        }
        Matcher matcher = Pattern.compile("Columns:[ \t]*(\\d+)").matcher(strAttemptCommand);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 80;
    }

    private static String attemptCommand(String[] strArr) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(strArr).getInputStream()));
            StringBuffer stringBuffer = null;
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer();
                }
                stringBuffer.append(line);
            }
            if (stringBuffer != null) {
                return stringBuffer.toString();
            }
        } catch (Exception unused) {
        }
        return null;
    }
}
