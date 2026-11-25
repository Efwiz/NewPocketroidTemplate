package com.reandroid.commons.utils.log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes10.dex */
public class ConsoleUtil {
    private static Boolean succeedOnce;

    public static int getConsoleWidth() {
        if (System.getProperty(SystemProperties.OS_NAME).toLowerCase().contains("windows")) {
            try {
                return attemptMode();
            } catch (Exception unused) {
                succeedOnce = false;
                return 80;
            }
        }
        try {
            return attemptStty();
        } catch (Exception unused2) {
            succeedOnce = false;
            return 80;
        }
    }

    public static Boolean getSucceedOnce() {
        return succeedOnce;
    }

    private static int attemptStty() {
        String strAttemptCommand = attemptCommand(new String[]{"sh", "-c", "stty size < /dev/tty"});
        if (strAttemptCommand == null) {
            succeedOnce = false;
            return 80;
        }
        String[] strArrSplit = strAttemptCommand.split(" ");
        if (strArrSplit.length < 2) {
            succeedOnce = false;
            return 80;
        }
        int i = Integer.parseInt(strArrSplit[1]);
        Boolean bool = succeedOnce;
        if (bool == null || !bool.booleanValue()) {
            succeedOnce = true;
        }
        return i;
    }

    private static int attemptMode() {
        String strAttemptCommand = attemptCommand(new String[]{"mode", "con"});
        if (strAttemptCommand == null) {
            succeedOnce = false;
            return 80;
        }
        Matcher matcher = Pattern.compile("Columns:[ \t]*(\\d+)").matcher(strAttemptCommand);
        if (!matcher.find()) {
            succeedOnce = false;
            return 80;
        }
        int i = Integer.parseInt(matcher.group(1));
        Boolean bool = succeedOnce;
        if (bool == null || !bool.booleanValue()) {
            succeedOnce = true;
        }
        return i;
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
