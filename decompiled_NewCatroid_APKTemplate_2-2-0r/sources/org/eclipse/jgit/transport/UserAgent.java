package org.eclipse.jgit.transport;

import java.util.Set;
import org.eclipse.jgit.util.StringUtils;

/* JADX INFO: loaded from: classes13.dex */
public class UserAgent {
    private static volatile String userAgent = computeUserAgent();

    private static String computeUserAgent() {
        return clean("JGit/" + computeVersion());
    }

    private static String computeVersion() {
        Package r0 = UserAgent.class.getPackage();
        if (r0 != null) {
            String implementationVersion = r0.getImplementationVersion();
            return !StringUtils.isEmptyOrNull(implementationVersion) ? implementationVersion : "unknown";
        }
        return "unknown";
    }

    static String clean(String str) {
        String strTrim = str.trim();
        StringBuilder sb = new StringBuilder(strTrim.length());
        for (int i = 0; i < strTrim.length(); i++) {
            char cCharAt = strTrim.charAt(i);
            if (cCharAt <= ' ' || cCharAt >= 127) {
                if (sb.length() <= 0 || sb.charAt(sb.length() - 1) != '.') {
                    cCharAt = '.';
                    sb.append(cCharAt);
                }
            } else {
                sb.append(cCharAt);
            }
        }
        if (sb.length() > 0) {
            return sb.toString();
        }
        return null;
    }

    public static String get() {
        return userAgent;
    }

    public static void set(String str) {
        userAgent = StringUtils.isEmptyOrNull(str) ? null : clean(str);
    }

    static String getAgent(Set<String> set, String str) {
        if (set == null || set.isEmpty()) {
            return str;
        }
        for (String str2 : set) {
            if (str2.startsWith(GitProtocolConstants.OPTION_AGENT) && str2.length() > GitProtocolConstants.OPTION_AGENT.length() && str2.charAt(GitProtocolConstants.OPTION_AGENT.length()) == '=') {
                return str2.substring(GitProtocolConstants.OPTION_AGENT.length() + 1);
            }
        }
        return str;
    }

    static boolean hasAgent(Set<String> set) {
        return getAgent(set, null) != null;
    }

    private UserAgent() {
    }
}
