package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.util.StringUtils;

/* JADX INFO: loaded from: classes13.dex */
public class PushConfig {

    public enum PushRecurseSubmodulesMode implements Config.ConfigEnum {
        CHECK("check"),
        ON_DEMAND("on-demand"),
        NO("false");

        private final String configValue;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static PushRecurseSubmodulesMode[] valuesCustom() {
            PushRecurseSubmodulesMode[] pushRecurseSubmodulesModeArrValuesCustom = values();
            int length = pushRecurseSubmodulesModeArrValuesCustom.length;
            PushRecurseSubmodulesMode[] pushRecurseSubmodulesModeArr = new PushRecurseSubmodulesMode[length];
            System.arraycopy(pushRecurseSubmodulesModeArrValuesCustom, 0, pushRecurseSubmodulesModeArr, 0, length);
            return pushRecurseSubmodulesModeArr;
        }

        PushRecurseSubmodulesMode(String str) {
            this.configValue = str;
        }

        @Override // org.eclipse.jgit.lib.Config.ConfigEnum
        public String toConfigValue() {
            return this.configValue;
        }

        @Override // org.eclipse.jgit.lib.Config.ConfigEnum
        public boolean matchConfigValue(String str) {
            if (StringUtils.isEmptyOrNull(str)) {
                return false;
            }
            String strReplace = str.replace('-', '_');
            return name().equalsIgnoreCase(strReplace) || this.configValue.equalsIgnoreCase(strReplace);
        }
    }
}
