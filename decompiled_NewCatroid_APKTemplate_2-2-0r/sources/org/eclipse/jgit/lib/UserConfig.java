package org.eclipse.jgit.lib;

import org.eclipse.jgit.util.SystemReader;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes13.dex */
public class UserConfig {
    public static final Config.SectionParser<UserConfig> KEY = new Config.SectionParser() { // from class: org.eclipse.jgit.lib.UserConfig$$ExternalSyntheticLambda0
        @Override // org.eclipse.jgit.lib.Config.SectionParser
        public final Object parse(Config config) {
            return UserConfig.m6044$r8$lambda$UAchCGY9SNlMqqBVFIXeOChkiw(config);
        }
    };
    private String authorEmail;
    private String authorName;
    private String committerEmail;
    private String committerName;
    private boolean isAuthorEmailImplicit;
    private boolean isAuthorNameImplicit;
    private boolean isCommitterEmailImplicit;
    private boolean isCommitterNameImplicit;

    /* JADX INFO: renamed from: $r8$lambda$UAchCGY9SNlM-qqBVFIXeOChkiw, reason: not valid java name */
    public static /* synthetic */ UserConfig m6044$r8$lambda$UAchCGY9SNlMqqBVFIXeOChkiw(Config config) {
        return new UserConfig(config);
    }

    private UserConfig(Config config) {
        String nameInternal = getNameInternal(config, Constants.GIT_AUTHOR_NAME_KEY);
        this.authorName = nameInternal;
        if (nameInternal == null) {
            this.authorName = getDefaultUserName();
            this.isAuthorNameImplicit = true;
        }
        String emailInternal = getEmailInternal(config, Constants.GIT_AUTHOR_EMAIL_KEY);
        this.authorEmail = emailInternal;
        if (emailInternal == null) {
            this.authorEmail = getDefaultEmail();
            this.isAuthorEmailImplicit = true;
        }
        String nameInternal2 = getNameInternal(config, Constants.GIT_COMMITTER_NAME_KEY);
        this.committerName = nameInternal2;
        if (nameInternal2 == null) {
            this.committerName = getDefaultUserName();
            this.isCommitterNameImplicit = true;
        }
        String emailInternal2 = getEmailInternal(config, Constants.GIT_COMMITTER_EMAIL_KEY);
        this.committerEmail = emailInternal2;
        if (emailInternal2 == null) {
            this.committerEmail = getDefaultEmail();
            this.isCommitterEmailImplicit = true;
        }
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public String getCommitterName() {
        return this.committerName;
    }

    public String getAuthorEmail() {
        return this.authorEmail;
    }

    public String getCommitterEmail() {
        return this.committerEmail;
    }

    public boolean isAuthorNameImplicit() {
        return this.isAuthorNameImplicit;
    }

    public boolean isAuthorEmailImplicit() {
        return this.isAuthorEmailImplicit;
    }

    public boolean isCommitterNameImplicit() {
        return this.isCommitterNameImplicit;
    }

    public boolean isCommitterEmailImplicit() {
        return this.isCommitterEmailImplicit;
    }

    private static String getNameInternal(Config config, String str) {
        String string = system().getenv(str);
        if (string == null) {
            string = config.getString(ConfigConstants.CONFIG_USER_SECTION, null, "name");
        }
        return stripInvalidCharacters(string);
    }

    private static String getDefaultUserName() {
        String property = system().getProperty("user.name");
        return property == null ? Constants.UNKNOWN_USER_DEFAULT : property;
    }

    private static String getEmailInternal(Config config, String str) {
        String string = system().getenv(str);
        if (string == null) {
            string = config.getString(ConfigConstants.CONFIG_USER_SECTION, null, "email");
        }
        return stripInvalidCharacters(string);
    }

    private static String stripInvalidCharacters(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("<|>|\n", "");
    }

    private static String getDefaultEmail() {
        return String.valueOf(getDefaultUserName()) + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + system().getHostname();
    }

    private static SystemReader system() {
        return SystemReader.getInstance();
    }
}
