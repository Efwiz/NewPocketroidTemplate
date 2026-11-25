package org.eclipse.jgit.lib;

import java.net.URISyntaxException;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;

/* JADX INFO: loaded from: classes13.dex */
public class BranchConfig {
    public static final String LOCAL_REPOSITORY = ".";
    private final String branchName;
    private final Config config;

    public enum BranchRebaseMode implements Config.ConfigEnum {
        REBASE("true"),
        PRESERVE("preserve"),
        INTERACTIVE("interactive"),
        NONE("false");

        private final String configValue;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static BranchRebaseMode[] valuesCustom() {
            BranchRebaseMode[] branchRebaseModeArrValuesCustom = values();
            int length = branchRebaseModeArrValuesCustom.length;
            BranchRebaseMode[] branchRebaseModeArr = new BranchRebaseMode[length];
            System.arraycopy(branchRebaseModeArrValuesCustom, 0, branchRebaseModeArr, 0, length);
            return branchRebaseModeArr;
        }

        BranchRebaseMode(String str) {
            this.configValue = str;
        }

        @Override // org.eclipse.jgit.lib.Config.ConfigEnum
        public String toConfigValue() {
            return this.configValue;
        }

        @Override // org.eclipse.jgit.lib.Config.ConfigEnum
        public boolean matchConfigValue(String str) {
            return this.configValue.equals(str);
        }
    }

    public BranchConfig(Config config, String str) {
        this.config = config;
        this.branchName = str;
    }

    public String getTrackingBranch() {
        String remoteOrDefault = getRemoteOrDefault();
        String merge = getMerge();
        if (remoteOrDefault == null || merge == null) {
            return null;
        }
        return isRemoteLocal() ? merge : findRemoteTrackingBranch(remoteOrDefault, merge);
    }

    public String getRemoteTrackingBranch() {
        String remoteOrDefault = getRemoteOrDefault();
        String merge = getMerge();
        if (remoteOrDefault == null || merge == null) {
            return null;
        }
        return findRemoteTrackingBranch(remoteOrDefault, merge);
    }

    public boolean isRemoteLocal() {
        return ".".equals(getRemote());
    }

    public String getRemote() {
        return this.config.getString(ConfigConstants.CONFIG_BRANCH_SECTION, this.branchName, "remote");
    }

    public String getMerge() {
        return this.config.getString(ConfigConstants.CONFIG_BRANCH_SECTION, this.branchName, "merge");
    }

    public boolean isRebase() {
        return getRebaseMode() != BranchRebaseMode.NONE;
    }

    public BranchRebaseMode getRebaseMode() {
        return (BranchRebaseMode) this.config.getEnum(BranchRebaseMode.valuesCustom(), ConfigConstants.CONFIG_BRANCH_SECTION, this.branchName, "rebase", BranchRebaseMode.NONE);
    }

    private String findRemoteTrackingBranch(String str, String str2) {
        try {
            for (RefSpec refSpec : new RemoteConfig(this.config, str).getFetchRefSpecs()) {
                if (refSpec.matchSource(str2)) {
                    return refSpec.expandFromSource(str2).getDestination();
                }
            }
            return null;
        } catch (URISyntaxException unused) {
            return null;
        }
    }

    private String getRemoteOrDefault() {
        String remote = getRemote();
        return remote == null ? "origin" : remote;
    }
}
