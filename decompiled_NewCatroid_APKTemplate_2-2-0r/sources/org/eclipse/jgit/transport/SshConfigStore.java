package org.eclipse.jgit.transport;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes13.dex */
public interface SshConfigStore {
    public static final HostConfig EMPTY_CONFIG = new HostConfig() { // from class: org.eclipse.jgit.transport.SshConfigStore.1
        @Override // org.eclipse.jgit.transport.SshConfigStore.HostConfig
        public String getValue(String str) {
            return null;
        }

        @Override // org.eclipse.jgit.transport.SshConfigStore.HostConfig
        public List<String> getValues(String str) {
            return Collections.emptyList();
        }

        @Override // org.eclipse.jgit.transport.SshConfigStore.HostConfig
        public Map<String, String> getOptions() {
            return Collections.emptyMap();
        }

        @Override // org.eclipse.jgit.transport.SshConfigStore.HostConfig
        public Map<String, List<String>> getMultiValuedOptions() {
            return Collections.emptyMap();
        }
    };

    public interface HostConfig {
        Map<String, List<String>> getMultiValuedOptions();

        Map<String, String> getOptions();

        String getValue(String str);

        List<String> getValues(String str);
    }

    HostConfig lookup(String str, int i, String str2);
}
