package org.eclipse.jgit.lib;

/* JADX INFO: loaded from: classes13.dex */
public class CoreConfig {
    public static final Config.SectionParser<CoreConfig> KEY = new Config.SectionParser() { // from class: org.eclipse.jgit.lib.CoreConfig$$ExternalSyntheticLambda0
        @Override // org.eclipse.jgit.lib.Config.SectionParser
        public final Object parse(Config config) {
            return CoreConfig.m6041$r8$lambda$thbCEp4DVGYBcSB7wQUdTxFoRw(config);
        }
    };
    private final String attributesfile;
    private final int compression;
    private final String excludesfile;
    private final LogRefUpdates logAllRefUpdates;
    private final int packIndexVersion;

    public enum AutoCRLF {
        FALSE,
        TRUE,
        INPUT;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static AutoCRLF[] valuesCustom() {
            AutoCRLF[] autoCRLFArrValuesCustom = values();
            int length = autoCRLFArrValuesCustom.length;
            AutoCRLF[] autoCRLFArr = new AutoCRLF[length];
            System.arraycopy(autoCRLFArrValuesCustom, 0, autoCRLFArr, 0, length);
            return autoCRLFArr;
        }
    }

    public enum CheckStat {
        MINIMAL,
        DEFAULT;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static CheckStat[] valuesCustom() {
            CheckStat[] checkStatArrValuesCustom = values();
            int length = checkStatArrValuesCustom.length;
            CheckStat[] checkStatArr = new CheckStat[length];
            System.arraycopy(checkStatArrValuesCustom, 0, checkStatArr, 0, length);
            return checkStatArr;
        }
    }

    public enum EOL {
        CRLF,
        LF,
        NATIVE;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static EOL[] valuesCustom() {
            EOL[] eolArrValuesCustom = values();
            int length = eolArrValuesCustom.length;
            EOL[] eolArr = new EOL[length];
            System.arraycopy(eolArrValuesCustom, 0, eolArr, 0, length);
            return eolArr;
        }
    }

    public enum EolStreamType {
        TEXT_CRLF,
        TEXT_LF,
        AUTO_CRLF,
        AUTO_LF,
        DIRECT;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static EolStreamType[] valuesCustom() {
            EolStreamType[] eolStreamTypeArrValuesCustom = values();
            int length = eolStreamTypeArrValuesCustom.length;
            EolStreamType[] eolStreamTypeArr = new EolStreamType[length];
            System.arraycopy(eolStreamTypeArrValuesCustom, 0, eolStreamTypeArr, 0, length);
            return eolStreamTypeArr;
        }
    }

    public enum HideDotFiles {
        FALSE,
        TRUE,
        DOTGITONLY;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static HideDotFiles[] valuesCustom() {
            HideDotFiles[] hideDotFilesArrValuesCustom = values();
            int length = hideDotFilesArrValuesCustom.length;
            HideDotFiles[] hideDotFilesArr = new HideDotFiles[length];
            System.arraycopy(hideDotFilesArrValuesCustom, 0, hideDotFilesArr, 0, length);
            return hideDotFilesArr;
        }
    }

    public enum LogRefUpdates {
        FALSE,
        TRUE,
        ALWAYS;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static LogRefUpdates[] valuesCustom() {
            LogRefUpdates[] logRefUpdatesArrValuesCustom = values();
            int length = logRefUpdatesArrValuesCustom.length;
            LogRefUpdates[] logRefUpdatesArr = new LogRefUpdates[length];
            System.arraycopy(logRefUpdatesArrValuesCustom, 0, logRefUpdatesArr, 0, length);
            return logRefUpdatesArr;
        }
    }

    public enum SymLinks {
        FALSE,
        TRUE;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static SymLinks[] valuesCustom() {
            SymLinks[] symLinksArrValuesCustom = values();
            int length = symLinksArrValuesCustom.length;
            SymLinks[] symLinksArr = new SymLinks[length];
            System.arraycopy(symLinksArrValuesCustom, 0, symLinksArr, 0, length);
            return symLinksArr;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$thbCEp4DVG-YBcSB7wQUdTxFoRw, reason: not valid java name */
    public static /* synthetic */ CoreConfig m6041$r8$lambda$thbCEp4DVGYBcSB7wQUdTxFoRw(Config config) {
        return new CoreConfig(config);
    }

    private CoreConfig(Config config) {
        this.compression = config.getInt(ConfigConstants.CONFIG_CORE_SECTION, ConfigConstants.CONFIG_KEY_COMPRESSION, -1);
        this.packIndexVersion = config.getInt(ConfigConstants.CONFIG_PACK_SECTION, ConfigConstants.CONFIG_KEY_INDEXVERSION, 2);
        this.logAllRefUpdates = (LogRefUpdates) config.getEnum(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES, LogRefUpdates.TRUE);
        this.excludesfile = config.getString(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_EXCLUDESFILE);
        this.attributesfile = config.getString(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_ATTRIBUTESFILE);
    }

    public int getCompression() {
        return this.compression;
    }

    public int getPackIndexVersion() {
        return this.packIndexVersion;
    }

    @Deprecated
    public boolean isLogAllRefUpdates() {
        return !LogRefUpdates.FALSE.equals(this.logAllRefUpdates);
    }

    public String getExcludesFile() {
        return this.excludesfile;
    }

    public String getAttributesFile() {
        return this.attributesfile;
    }
}
