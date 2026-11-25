package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.text.MessageFormat;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

/* JADX INFO: loaded from: classes13.dex */
public class CommitConfig {
    private String commitTemplatePath;
    private String i18nCommitEncoding;
    public static final Config.SectionParser<CommitConfig> KEY = new Config.SectionParser() { // from class: org.eclipse.jgit.lib.CommitConfig$$ExternalSyntheticLambda0
        @Override // org.eclipse.jgit.lib.Config.SectionParser
        public final Object parse(Config config) {
            return CommitConfig.m6040$r8$lambda$i_uB7aSCdnGaHiqavgVFoecJCM(config);
        }
    };
    private static final Charset DEFAULT_COMMIT_MESSAGE_ENCODING = StandardCharsets.UTF_8;

    /* JADX INFO: renamed from: $r8$lambda$i_uB7aSCdnGaHiqavg-VFoecJCM, reason: not valid java name */
    public static /* synthetic */ CommitConfig m6040$r8$lambda$i_uB7aSCdnGaHiqavgVFoecJCM(Config config) {
        return new CommitConfig(config);
    }

    private CommitConfig(Config config) {
        this.commitTemplatePath = config.getString("commit", null, ConfigConstants.CONFIG_KEY_COMMIT_TEMPLATE);
        this.i18nCommitEncoding = config.getString(ConfigConstants.CONFIG_SECTION_I18N, null, ConfigConstants.CONFIG_KEY_COMMIT_ENCODING);
    }

    @Nullable
    public String getCommitTemplatePath() {
        return this.commitTemplatePath;
    }

    @Nullable
    public String getCommitEncoding() {
        return this.i18nCommitEncoding;
    }

    @Nullable
    public String getCommitTemplateContent() throws ConfigInvalidException, IOException {
        File fileResolve;
        String str = this.commitTemplatePath;
        if (str == null) {
            return null;
        }
        if (str.startsWith("~/")) {
            fileResolve = FS.DETECTED.resolve(FS.DETECTED.userHome(), this.commitTemplatePath.substring(2));
        } else {
            fileResolve = FS.DETECTED.resolve(null, this.commitTemplatePath);
        }
        return RawParseUtils.decode(getEncoding(), IO.readFully(fileResolve));
    }

    private Charset getEncoding() throws ConfigInvalidException {
        String str = this.i18nCommitEncoding;
        if (str == null) {
            return null;
        }
        try {
            return Charset.forName(str);
        } catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
            throw new ConfigInvalidException(MessageFormat.format(JGitText.get().invalidEncoding, this.i18nCommitEncoding), e);
        }
    }
}
