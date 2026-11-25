package org.eclipse.jgit.internal.transport.ssh;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Consumer;
import net.bytebuddy.asm.Advice;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.fnmatch.FileNameMatcher;
import org.eclipse.jgit.transport.SshConfigStore;
import org.eclipse.jgit.transport.SshConstants;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.SystemReader;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
public class OpenSshConfigFile implements SshConfigStore {
    private final File configFile;
    private final File home;
    private Instant lastModified;
    private final String localUserName;
    private State state = new State(null);

    protected List<String> validate(String str, List<String> list) {
        return list;
    }

    private static class State {
        List<HostEntry> entries;
        Map<String, HostEntry> hosts;

        private State() {
            this.entries = new LinkedList();
            this.hosts = new HashMap();
        }

        /* synthetic */ State(State state) {
            this();
        }

        public String toString() {
            return "State [entries=" + this.entries + ", hosts=" + this.hosts + "]";
        }
    }

    public OpenSshConfigFile(File file, File file2, String str) {
        this.home = file;
        this.configFile = file2;
        this.localUserName = str;
    }

    @Override // org.eclipse.jgit.transport.SshConfigStore
    public HostEntry lookup(final String str, int i, String str2) {
        State stateRefresh = refresh();
        String cacheKey = toCacheKey(str, i, str2);
        HostEntry hostEntry = stateRefresh.hosts.get(cacheKey);
        if (hostEntry != null) {
            return hostEntry;
        }
        final HostEntry hostEntry2 = new HostEntry();
        Iterator<HostEntry> it = stateRefresh.entries.iterator();
        if (it.hasNext()) {
            hostEntry2.merge(it.next());
            it.forEachRemaining(new Consumer() { // from class: org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    OpenSshConfigFile.lambda$0(str, hostEntry2, (OpenSshConfigFile.HostEntry) obj);
                }
            });
        }
        hostEntry2.substitute(str, i, str2, this.localUserName, this.home);
        stateRefresh.hosts.put(cacheKey, hostEntry2);
        return hostEntry2;
    }

    static /* synthetic */ void lambda$0(String str, HostEntry hostEntry, HostEntry hostEntry2) {
        if (hostEntry2.matches(str)) {
            hostEntry.merge(hostEntry2);
        }
    }

    private String toCacheKey(String str, int i, String str2) {
        if (i > 0) {
            str = String.valueOf(str) + ':' + Integer.toString(i);
        }
        return (str2 == null || str2.isEmpty()) ? str : String.valueOf(str2) + '@' + str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private synchronized State refresh() {
        Instant instantLastModifiedInstant = FS.DETECTED.lastModifiedInstant(this.configFile);
        if (!instantLastModifiedInstant.equals(this.lastModified)) {
            Throwable th = null;
            State state = new State(0 == true ? 1 : 0);
            try {
                BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(this.configFile.toPath(), StandardCharsets.UTF_8);
                try {
                    state.entries = parse(bufferedReaderNewBufferedReader);
                    if (bufferedReaderNewBufferedReader != null) {
                        bufferedReaderNewBufferedReader.close();
                    }
                    this.lastModified = instantLastModifiedInstant;
                    this.state = state;
                } catch (Throwable th2) {
                    if (bufferedReaderNewBufferedReader != null) {
                        bufferedReaderNewBufferedReader.close();
                    }
                    throw th2;
                }
            } catch (Throwable th3) {
                if (0 == 0) {
                    throw th3;
                }
                if (null == th3) {
                    throw null;
                }
                try {
                    th.addSuppressed(th3);
                    throw null;
                } catch (IOException | RuntimeException unused) {
                }
            }
        }
        return this.state;
    }

    private List<HostEntry> parse(BufferedReader bufferedReader) throws IOException {
        LinkedList linkedList = new LinkedList();
        HostEntry hostEntry = new HostEntry();
        linkedList.add(hostEntry);
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return linkedList;
            }
            int iIndexOf = line.indexOf(35);
            if (iIndexOf >= 0) {
                line = line.substring(0, iIndexOf);
            }
            String strTrim = line.trim();
            if (!strTrim.isEmpty()) {
                String[] strArrSplit = strTrim.split("[ \t]*[= \t]", 2);
                String strDequote = dequote(strArrSplit[0].trim());
                String strTrim2 = strArrSplit.length > 1 ? strArrSplit[1].trim() : "";
                if (StringUtils.equalsIgnoreCase("Host", strDequote)) {
                    hostEntry = new HostEntry(parseList(strTrim2));
                    linkedList.add(hostEntry);
                } else if (HostEntry.isListKey(strDequote)) {
                    hostEntry.setValue(strDequote, validate(strDequote, parseList(strTrim2)));
                } else if (!strTrim2.isEmpty()) {
                    hostEntry.setValue(strDequote, validate(strDequote, dequote(strTrim2)));
                }
            }
        }
    }

    private List<String> parseList(String str) {
        int iIndexOf;
        ArrayList arrayList = new ArrayList(4);
        int length = str.length();
        int i = 0;
        while (i < length) {
            if (Character.isWhitespace(str.charAt(i))) {
                i++;
            } else {
                if (str.charAt(i) == '\"') {
                    int i2 = i + 1;
                    iIndexOf = str.indexOf(34, i2);
                    if (iIndexOf < i2) {
                        break;
                    }
                    arrayList.add(str.substring(i2, iIndexOf));
                } else {
                    iIndexOf = i + 1;
                    while (iIndexOf < length && !Character.isWhitespace(str.charAt(iIndexOf))) {
                        iIndexOf++;
                    }
                    arrayList.add(str.substring(i, iIndexOf));
                }
                i = iIndexOf + 1;
            }
        }
        return arrayList;
    }

    protected String validate(String str, String str2) {
        return String.CASE_INSENSITIVE_ORDER.compare(str, SshConstants.PREFERRED_AUTHENTICATIONS) == 0 ? stripWhitespace(str2) : str2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean patternMatchesHost(String str, String str2) {
        if (str.indexOf(42) >= 0 || str.indexOf(63) >= 0) {
            try {
                FileNameMatcher fileNameMatcher = new FileNameMatcher(str, (Character) null);
                fileNameMatcher.append(str2);
                return fileNameMatcher.isMatch();
            } catch (InvalidPatternException unused) {
                return false;
            }
        }
        return str.equals(str2);
    }

    private static String dequote(String str) {
        return (str.startsWith("\"") && str.endsWith("\"") && str.length() > 1) ? str.substring(1, str.length() - 1) : str;
    }

    private static String stripWhitespace(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (!Character.isWhitespace(cCharAt)) {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static File toFile(String str, File file) {
        if (str.startsWith("~/") || str.startsWith("~" + File.separator)) {
            return new File(file, str.substring(2));
        }
        File file2 = new File(str);
        return file2.isAbsolute() ? file2 : new File(file, str);
    }

    public static int positive(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Integer.parseUnsignedInt(str);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public static boolean flag(String str) {
        if (str == null) {
            return false;
        }
        return "yes".equals(str) || "on".equals(str) || "true".equals(str);
    }

    public String getLocalUserName() {
        return this.localUserName;
    }

    public static class HostEntry implements SshConfigStore.HostConfig {
        private static final Map<String, String> ALIASES;
        private static final Set<String> LIST_KEYS;
        private static final Set<String> MULTI_KEYS;
        private Map<String, List<String>> listOptions;
        private Map<String, List<String>> multiOptions;
        private Map<String, String> options;
        private final List<String> patterns;

        static {
            TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
            MULTI_KEYS = treeSet;
            treeSet.add(SshConstants.CERTIFICATE_FILE);
            treeSet.add(SshConstants.IDENTITY_FILE);
            treeSet.add(SshConstants.LOCAL_FORWARD);
            treeSet.add(SshConstants.REMOTE_FORWARD);
            treeSet.add(SshConstants.SEND_ENV);
            TreeSet treeSet2 = new TreeSet(String.CASE_INSENSITIVE_ORDER);
            LIST_KEYS = treeSet2;
            treeSet2.add(SshConstants.CANONICAL_DOMAINS);
            treeSet2.add(SshConstants.GLOBAL_KNOWN_HOSTS_FILE);
            treeSet2.add(SshConstants.SEND_ENV);
            treeSet2.add(SshConstants.USER_KNOWN_HOSTS_FILE);
            TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            ALIASES = treeMap;
            treeMap.put("PubkeyAcceptedKeyTypes", SshConstants.PUBKEY_ACCEPTED_ALGORITHMS);
        }

        public HostEntry() {
            this.patterns = Collections.emptyList();
        }

        public HostEntry(List<String> list) {
            this.patterns = list;
        }

        boolean matches(String str) {
            boolean z = false;
            for (String str2 : this.patterns) {
                if (str2.startsWith("!")) {
                    if (OpenSshConfigFile.patternMatchesHost(str2.substring(1), str)) {
                        return false;
                    }
                } else if (!z && OpenSshConfigFile.patternMatchesHost(str2, str)) {
                    z = true;
                }
            }
            return z;
        }

        private static String toKey(String str) {
            String str2 = ALIASES.get(str);
            return str2 != null ? str2 : str;
        }

        @Override // org.eclipse.jgit.transport.SshConfigStore.HostConfig
        public String getValue(String str) {
            String key = toKey(str);
            Map<String, String> map = this.options;
            String str2 = map != null ? map.get(key) : null;
            if (str2 != null) {
                return str2;
            }
            Map<String, List<String>> map2 = this.listOptions;
            List<String> list = map2 != null ? map2.get(key) : null;
            if (list == null) {
                Map<String, List<String>> map3 = this.multiOptions;
                list = map3 != null ? map3.get(key) : null;
            }
            return (list == null || list.isEmpty()) ? str2 : list.get(0);
        }

        @Override // org.eclipse.jgit.transport.SshConfigStore.HostConfig
        public List<String> getValues(String str) {
            String key = toKey(str);
            Map<String, List<String>> map = this.listOptions;
            List<String> list = map != null ? map.get(key) : null;
            if (list == null) {
                Map<String, List<String>> map2 = this.multiOptions;
                list = map2 != null ? map2.get(key) : null;
            }
            if (list == null || list.isEmpty()) {
                return new ArrayList();
            }
            return new ArrayList(list);
        }

        public void setValue(String str, String str2) {
            String key = toKey(str);
            if (str2 == null) {
                Map<String, List<String>> map = this.multiOptions;
                if (map != null) {
                    map.remove(key);
                }
                Map<String, List<String>> map2 = this.listOptions;
                if (map2 != null) {
                    map2.remove(key);
                }
                Map<String, String> map3 = this.options;
                if (map3 != null) {
                    map3.remove(key);
                    return;
                }
                return;
            }
            if (MULTI_KEYS.contains(key)) {
                if (this.multiOptions == null) {
                    this.multiOptions = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                }
                List<String> arrayList = this.multiOptions.get(key);
                if (arrayList == null) {
                    arrayList = new ArrayList<>(4);
                    this.multiOptions.put(key, arrayList);
                }
                arrayList.add(str2);
                return;
            }
            if (this.options == null) {
                this.options = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            }
            if (this.options.containsKey(key)) {
                return;
            }
            this.options.put(key, str2);
        }

        public void setValue(String str, List<String> list) {
            if (list.isEmpty()) {
                return;
            }
            String key = toKey(str);
            if (MULTI_KEYS.contains(key)) {
                if (this.multiOptions == null) {
                    this.multiOptions = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                }
                List<String> list2 = this.multiOptions.get(key);
                if (list2 == null) {
                    this.multiOptions.put(key, new ArrayList(list));
                    return;
                } else {
                    list2.addAll(list);
                    return;
                }
            }
            if (this.listOptions == null) {
                this.listOptions = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            }
            if (this.listOptions.containsKey(key)) {
                return;
            }
            this.listOptions.put(key, list);
        }

        public static boolean isListKey(String str) {
            return LIST_KEYS.contains(toKey(str));
        }

        void merge(HostEntry hostEntry) {
            if (hostEntry == null) {
                return;
            }
            if (hostEntry.options != null) {
                if (this.options == null) {
                    this.options = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                }
                for (Map.Entry<String, String> entry : hostEntry.options.entrySet()) {
                    if (!this.options.containsKey(entry.getKey())) {
                        this.options.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            if (hostEntry.listOptions != null) {
                if (this.listOptions == null) {
                    this.listOptions = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                }
                for (Map.Entry<String, List<String>> entry2 : hostEntry.listOptions.entrySet()) {
                    if (!this.listOptions.containsKey(entry2.getKey())) {
                        this.listOptions.put(entry2.getKey(), entry2.getValue());
                    }
                }
            }
            if (hostEntry.multiOptions != null) {
                if (this.multiOptions == null) {
                    this.multiOptions = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                }
                for (Map.Entry<String, List<String>> entry3 : hostEntry.multiOptions.entrySet()) {
                    List<String> list = this.multiOptions.get(entry3.getKey());
                    if (list == null) {
                        this.multiOptions.put(entry3.getKey(), new ArrayList(entry3.getValue()));
                    } else {
                        list.addAll(entry3.getValue());
                    }
                }
            }
        }

        private List<String> substitute(List<String> list, String str, Replacer replacer, boolean z) {
            ArrayList arrayList = new ArrayList(list.size());
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(replacer.substitute(it.next(), str, z));
            }
            return arrayList;
        }

        private List<String> replaceTilde(List<String> list, File file) {
            ArrayList arrayList = new ArrayList(list.size());
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(OpenSshConfigFile.toFile(it.next(), file).getPath());
            }
            return arrayList;
        }

        void substitute(String str, int i, String str2, String str3, File file) {
            List<String> list;
            if (i < 0) {
                i = OpenSshConfigFile.positive(getValue(SshConstants.PORT));
            }
            if (i < 0) {
                i = 22;
            }
            int i2 = i;
            if (str2 == null || str2.isEmpty()) {
                str2 = getValue(SshConstants.USER);
            }
            Replacer replacer = new Replacer(str, i2, (str2 == null || str2.isEmpty()) ? str3 : str2, str3, file);
            Map<String, String> map = this.options;
            if (map != null) {
                String str4 = map.get(SshConstants.HOST_NAME);
                if (str4 == null || str4.isEmpty()) {
                    this.options.put(SshConstants.HOST_NAME, str);
                } else {
                    String strSubstitute = replacer.substitute(str4, "h", false);
                    this.options.put(SshConstants.HOST_NAME, strSubstitute);
                    replacer.update('h', strSubstitute);
                }
            }
            Map<String, List<String>> map2 = this.multiOptions;
            if (map2 != null) {
                List<String> list2 = map2.get(SshConstants.IDENTITY_FILE);
                if (list2 != null) {
                    this.multiOptions.put(SshConstants.IDENTITY_FILE, replaceTilde(substitute(list2, "dhlru", replacer, true), file));
                }
                List<String> list3 = this.multiOptions.get(SshConstants.CERTIFICATE_FILE);
                if (list3 != null) {
                    this.multiOptions.put(SshConstants.CERTIFICATE_FILE, replaceTilde(substitute(list3, "dhlru", replacer, true), file));
                }
            }
            Map<String, List<String>> map3 = this.listOptions;
            if (map3 != null && (list = map3.get(SshConstants.USER_KNOWN_HOSTS_FILE)) != null) {
                this.listOptions.put(SshConstants.USER_KNOWN_HOSTS_FILE, replaceTilde(list, file));
            }
            Map<String, String> map4 = this.options;
            if (map4 != null) {
                String str5 = map4.get(SshConstants.IDENTITY_AGENT);
                if (str5 != null) {
                    this.options.put(SshConstants.IDENTITY_AGENT, OpenSshConfigFile.toFile(replacer.substitute(str5, "dhlru", true), file).getPath());
                }
                String str6 = this.options.get(SshConstants.CONTROL_PATH);
                if (str6 != null) {
                    this.options.put(SshConstants.CONTROL_PATH, OpenSshConfigFile.toFile(replacer.substitute(str6, "ChLlnpru", true), file).getPath());
                }
                String str7 = this.options.get(SshConstants.LOCAL_COMMAND);
                if (str7 != null) {
                    this.options.put(SshConstants.LOCAL_COMMAND, replacer.substitute(str7, "CdhlnprTu", false));
                }
                String str8 = this.options.get(SshConstants.REMOTE_COMMAND);
                if (str8 != null) {
                    this.options.put(SshConstants.REMOTE_COMMAND, replacer.substitute(str8, "Cdhlnpru", false));
                }
                String str9 = this.options.get(SshConstants.PROXY_COMMAND);
                if (str9 != null) {
                    this.options.put(SshConstants.PROXY_COMMAND, replacer.substitute(str9, "hpr", false));
                }
            }
        }

        @Override // org.eclipse.jgit.transport.SshConfigStore.HostConfig
        public Map<String, String> getOptions() {
            Map<String, String> map = this.options;
            if (map == null) {
                return Collections.emptyMap();
            }
            return Collections.unmodifiableMap(map);
        }

        @Override // org.eclipse.jgit.transport.SshConfigStore.HostConfig
        public Map<String, List<String>> getMultiValuedOptions() {
            if (this.listOptions == null && this.multiOptions == null) {
                return Collections.emptyMap();
            }
            TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            Map<String, List<String>> map = this.multiOptions;
            if (map != null) {
                treeMap.putAll(map);
            }
            Map<String, List<String>> map2 = this.listOptions;
            if (map2 != null) {
                treeMap.putAll(map2);
            }
            return Collections.unmodifiableMap(treeMap);
        }

        public String toString() {
            return "HostEntry [options=" + this.options + ", multiOptions=" + this.multiOptions + ", listOptions=" + this.listOptions + "]";
        }
    }

    private static class Replacer {
        private final Map<Character, String> replacements;

        public Replacer(String str, int i, String str2, String str3, File file) {
            HashMap map = new HashMap();
            this.replacements = map;
            map.put(Character.valueOf(MangleConstant.PLATFORM_FUNCTION_MARKER), "%");
            map.put(Character.valueOf(Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL), file.getPath());
            map.put('h', str);
            String hostname = SystemReader.getInstance().getHostname();
            map.put('l', hostname);
            int iIndexOf = hostname.indexOf(46);
            map.put('L', iIndexOf > 0 ? hostname.substring(0, iIndexOf) : hostname);
            map.put('n', str);
            map.put(Character.valueOf(Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL), Integer.toString(i));
            map.put(Character.valueOf(Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL), str2 == null ? "" : str2);
            map.put('u', str3);
            map.put(Character.valueOf(Util.C_CHAR), substitute("%l%h%p%r", "hlpr", false));
            map.put(Character.valueOf(Util.C_TYPE_VARIABLE), "NONE");
        }

        public void update(char c, String str) {
            this.replacements.put(Character.valueOf(c), str);
            if ("lhpr".indexOf(c) >= 0) {
                this.replacements.put(Character.valueOf(Util.C_CHAR), substitute("%l%h%p%r", "hlpr", false));
            }
        }

        public String substitute(String str, String str2, boolean z) {
            int i;
            int i2;
            int iIndexOf;
            if (str == null || str.length() <= 1 || (str.indexOf(37) < 0 && (!z || str.indexOf("${") < 0))) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            int length = str.length();
            int i3 = 0;
            while (i3 < length) {
                char cCharAt = str.charAt(i3);
                if (cCharAt == '$') {
                    if (z && (i2 = i3 + 2) < length) {
                        if (str.charAt(i3 + 1) != '{' || (iIndexOf = str.indexOf(125, i2)) <= i2) {
                            cCharAt = '$';
                        } else {
                            String str3 = SystemReader.getInstance().getenv(str.substring(i2, iIndexOf));
                            if (!StringUtils.isEmptyOrNull(str3)) {
                                sb.append(str3);
                            }
                            i3 = iIndexOf + 1;
                        }
                    }
                    sb.append(cCharAt);
                    i3++;
                } else if (cCharAt == '%' && (i = i3 + 1) < length) {
                    char cCharAt2 = str.charAt(i);
                    String str4 = (cCharAt2 == '%' || str2.indexOf(cCharAt2) >= 0) ? this.replacements.get(Character.valueOf(cCharAt2)) : null;
                    if (str4 == null) {
                        sb.append(MangleConstant.PLATFORM_FUNCTION_MARKER).append(cCharAt2);
                    } else {
                        sb.append(str4);
                    }
                    i3 += 2;
                } else {
                    sb.append(cCharAt);
                    i3++;
                }
            }
            return sb.toString();
        }
    }

    public String toString() {
        return "OpenSshConfig [home=" + this.home + ", configFile=" + this.configFile + ", lastModified=" + this.lastModified + ", state=" + this.state + "]";
    }
}
