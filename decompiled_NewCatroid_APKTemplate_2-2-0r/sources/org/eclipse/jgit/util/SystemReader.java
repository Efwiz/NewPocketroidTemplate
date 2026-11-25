package org.eclipse.jgit.util;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.SystemProperties;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.time.MonotonicClock;
import org.eclipse.jgit.util.time.MonotonicSystemClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes14.dex */
public abstract class SystemReader {
    private static final SystemReader DEFAULT;
    private static volatile SystemReader INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) SystemReader.class);
    private static volatile Boolean isMacOS;
    private static volatile Boolean isWindows;
    private ObjectChecker platformChecker;
    private AtomicReference<FileBasedConfig> systemConfig = new AtomicReference<>();
    private AtomicReference<FileBasedConfig> userConfig = new AtomicReference<>();
    private AtomicReference<FileBasedConfig> jgitConfig = new AtomicReference<>();

    public abstract long getCurrentTime();

    public abstract String getHostname();

    public abstract String getProperty(String str);

    public abstract int getTimezone(long j);

    public abstract String getenv(String str);

    public abstract FileBasedConfig openJGitConfig(Config config, FS fs);

    public abstract FileBasedConfig openSystemConfig(Config config, FS fs);

    public abstract FileBasedConfig openUserConfig(Config config, FS fs);

    static {
        Default r0 = new Default(null);
        r0.init();
        DEFAULT = r0;
        INSTANCE = r0;
    }

    private static class Default extends SystemReader {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private volatile String hostname;

        private Default() {
        }

        /* synthetic */ Default(Default r1) {
            this();
        }

        @Override // org.eclipse.jgit.util.SystemReader
        public String getenv(String str) {
            return System.getenv(str);
        }

        @Override // org.eclipse.jgit.util.SystemReader
        public String getProperty(String str) {
            return System.getProperty(str);
        }

        @Override // org.eclipse.jgit.util.SystemReader
        public FileBasedConfig openSystemConfig(Config config, FS fs) {
            File gitSystemConfig;
            if (StringUtils.isEmptyOrNull(getenv(Constants.GIT_CONFIG_NOSYSTEM_KEY)) && (gitSystemConfig = fs.getGitSystemConfig()) != null) {
                return new FileBasedConfig(config, gitSystemConfig, fs);
            }
            return new FileBasedConfig(config, null, fs) { // from class: org.eclipse.jgit.util.SystemReader.Default.1
                @Override // org.eclipse.jgit.storage.file.FileBasedConfig
                public boolean isOutdated() {
                    return false;
                }

                @Override // org.eclipse.jgit.storage.file.FileBasedConfig, org.eclipse.jgit.lib.StoredConfig
                public void load() {
                }
            };
        }

        @Override // org.eclipse.jgit.util.SystemReader
        public FileBasedConfig openUserConfig(Config config, FS fs) {
            return new FileBasedConfig(config, new File(fs.userHome(), ".gitconfig"), fs);
        }

        private Path getXDGConfigHome(FS fs) {
            String absolutePath = getenv(Constants.XDG_CONFIG_HOME);
            if (StringUtils.isEmptyOrNull(absolutePath)) {
                absolutePath = new File(fs.userHome(), ".config").getAbsolutePath();
            }
            try {
                return java.nio.file.Paths.get(absolutePath, new String[0]);
            } catch (InvalidPathException e) {
                SystemReader.LOG.error(JGitText.get().logXDGConfigHomeInvalid, absolutePath, e);
                return null;
            }
        }

        @Override // org.eclipse.jgit.util.SystemReader
        public FileBasedConfig openJGitConfig(Config config, FS fs) {
            Path xDGConfigHome = getXDGConfigHome(fs);
            if (xDGConfigHome != null) {
                return new FileBasedConfig(config, xDGConfigHome.resolve("jgit").resolve("config").toFile(), fs);
            }
            return new FileBasedConfig(config, new File(fs.userHome(), ".jgitconfig"), fs);
        }

        @Override // org.eclipse.jgit.util.SystemReader
        public String getHostname() {
            if (this.hostname == null) {
                try {
                    this.hostname = InetAddress.getLocalHost().getCanonicalHostName();
                } catch (UnknownHostException unused) {
                    this.hostname = "localhost";
                }
            }
            return this.hostname;
        }

        @Override // org.eclipse.jgit.util.SystemReader
        public long getCurrentTime() {
            return System.currentTimeMillis();
        }

        @Override // org.eclipse.jgit.util.SystemReader
        public int getTimezone(long j) {
            return getTimeZone().getOffset(j) / 60000;
        }
    }

    public static SystemReader getInstance() {
        return INSTANCE;
    }

    public static void setInstance(SystemReader systemReader) {
        isMacOS = null;
        isWindows = null;
        if (systemReader == null) {
            INSTANCE = DEFAULT;
        } else {
            systemReader.init();
            INSTANCE = systemReader;
        }
    }

    private void init() {
        if (this.platformChecker == null) {
            setPlatformChecker();
        }
    }

    protected final void setPlatformChecker() {
        this.platformChecker = new ObjectChecker().setSafeForWindows(isWindows()).setSafeForMacOS(isMacOS());
    }

    public StoredConfig getUserConfig() throws ConfigInvalidException, IOException {
        FileBasedConfig fileBasedConfig = this.userConfig.get();
        if (fileBasedConfig == null) {
            LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.userConfig, null, openUserConfig(getSystemConfig(), FS.DETECTED));
            fileBasedConfig = this.userConfig.get();
        }
        updateAll(fileBasedConfig);
        return fileBasedConfig;
    }

    public StoredConfig getJGitConfig() throws ConfigInvalidException, IOException {
        FileBasedConfig fileBasedConfig = this.jgitConfig.get();
        if (fileBasedConfig == null) {
            LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.jgitConfig, null, openJGitConfig(null, FS.DETECTED));
            fileBasedConfig = this.jgitConfig.get();
        }
        updateAll(fileBasedConfig);
        return fileBasedConfig;
    }

    public StoredConfig getSystemConfig() throws ConfigInvalidException, IOException {
        FileBasedConfig fileBasedConfig = this.systemConfig.get();
        if (fileBasedConfig == null) {
            LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.systemConfig, null, openSystemConfig(getJGitConfig(), FS.DETECTED));
            fileBasedConfig = this.systemConfig.get();
        }
        updateAll(fileBasedConfig);
        return fileBasedConfig;
    }

    private void updateAll(Config config) throws ConfigInvalidException, IOException {
        if (config == null) {
            return;
        }
        updateAll(config.getBaseConfig());
        if (config instanceof FileBasedConfig) {
            FileBasedConfig fileBasedConfig = (FileBasedConfig) config;
            if (fileBasedConfig.isOutdated()) {
                LOG.debug("loading config {}", fileBasedConfig);
                fileBasedConfig.load();
            }
        }
    }

    public MonotonicClock getClock() {
        return new MonotonicSystemClock();
    }

    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    public Locale getLocale() {
        return Locale.getDefault();
    }

    public SimpleDateFormat getSimpleDateFormat(String str) {
        return new SimpleDateFormat(str);
    }

    public SimpleDateFormat getSimpleDateFormat(String str, Locale locale) {
        return new SimpleDateFormat(str, locale);
    }

    public DateFormat getDateTimeInstance(int i, int i2) {
        return DateFormat.getDateTimeInstance(i, i2);
    }

    public boolean isWindows() {
        if (isWindows == null) {
            isWindows = Boolean.valueOf(getOsName().startsWith("Windows"));
        }
        return isWindows.booleanValue();
    }

    public boolean isMacOS() {
        if (isMacOS == null) {
            String osName = getOsName();
            isMacOS = Boolean.valueOf("Mac OS X".equals(osName) || "Darwin".equals(osName));
        }
        return isMacOS.booleanValue();
    }

    private String getOsName() {
        return (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.eclipse.jgit.util.SystemReader$$ExternalSyntheticLambda0
            @Override // java.security.PrivilegedAction
            public final Object run() {
                return this.f$0.m6056lambda$0$orgeclipsejgitutilSystemReader();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$0$org-eclipse-jgit-util-SystemReader, reason: not valid java name */
    /* synthetic */ String m6056lambda$0$orgeclipsejgitutilSystemReader() {
        return getProperty(SystemProperties.OS_NAME);
    }

    public void checkPath(String str) throws CorruptObjectException {
        this.platformChecker.checkPath(str);
    }

    public void checkPath(byte[] bArr) throws CorruptObjectException {
        this.platformChecker.checkPath(bArr, 0, bArr.length);
    }
}
