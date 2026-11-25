package org.eclipse.jgit.internal.storage.file;

import com.google.android.gms.cast.MediaTrack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.attributes.AttributesNodeProvider;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.events.ConfigChangedEvent;
import org.eclipse.jgit.events.ConfigChangedListener;
import org.eclipse.jgit.events.IndexChangedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.BaseRepositoryBuilder;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.SystemReader;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes13.dex */
public class FileRepository extends Repository {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) FileRepository.class);
    private static final String UNNAMED = "Unnamed repository; edit this file to name it for gitweb.";
    private final ObjectDirectory objectDatabase;
    private RefDatabase refs;
    private final FileBasedConfig repoConfig;
    private FileSnapshot snapshot;
    private final Object snapshotLock;

    public FileRepository(File file) throws IOException {
        this(new FileRepositoryBuilder().setGitDir(file).setup());
    }

    public FileRepository(String str) throws IOException {
        this(new File(str));
    }

    public FileRepository(BaseRepositoryBuilder baseRepositoryBuilder) throws IOException {
        super(baseRepositoryBuilder);
        this.snapshotLock = new Object();
        try {
            FileBasedConfig fileBasedConfig = new FileBasedConfig(SystemReader.getInstance().getUserConfig(), getFS().resolve(getDirectory(), "config"), getFS());
            this.repoConfig = fileBasedConfig;
            loadRepoConfig();
            fileBasedConfig.addChangeListener(new ConfigChangedListener() { // from class: org.eclipse.jgit.internal.storage.file.FileRepository$$ExternalSyntheticLambda0
                @Override // org.eclipse.jgit.events.ConfigChangedListener
                public final void onConfigChanged(ConfigChangedEvent configChangedEvent) {
                    this.f$0.fireEvent(configChangedEvent);
                }
            });
            long j = getConfig().getLong(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION, 0L);
            String string = fileBasedConfig.getString("extensions", null, "refStorage");
            if (j >= 1 && string != null) {
                if (StringUtils.equalsIgnoreCase(string, "reftable")) {
                    this.refs = new FileReftableDatabase(this);
                } else {
                    throw new IOException(JGitText.get().unknownRepositoryFormat);
                }
            } else {
                this.refs = new RefDirectory(this);
            }
            ObjectDirectory objectDirectory = new ObjectDirectory(fileBasedConfig, baseRepositoryBuilder.getObjectDirectory(), baseRepositoryBuilder.getAlternateObjectDirectories(), getFS(), new File(getDirectory(), "shallow"));
            this.objectDatabase = objectDirectory;
            if (objectDirectory.exists() && j > 1) {
                throw new IOException(MessageFormat.format(JGitText.get().unknownRepositoryFormat2, Long.valueOf(j)));
            }
            if (isBare()) {
                return;
            }
            this.snapshot = FileSnapshot.save(getIndexFile());
        } catch (ConfigInvalidException e) {
            LOG.error(e.getMessage(), (Throwable) e);
            throw new IOException(e.getMessage(), e);
        }
    }

    private void loadRepoConfig() throws IOException {
        try {
            this.repoConfig.load();
        } catch (ConfigInvalidException e) {
            throw new IOException(JGitText.get().unknownRepositoryFormat, e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x00cf  */
    @Override // org.eclipse.jgit.lib.Repository
    public void create(boolean z) throws IOException {
        boolean z2;
        FileBasedConfig config = getConfig();
        if (config.getFile().exists()) {
            throw new IllegalStateException(MessageFormat.format(JGitText.get().repositoryAlreadyExists, getDirectory()));
        }
        FileUtils.mkdirs(getDirectory(), true);
        if (((CoreConfig.HideDotFiles) getConfig().getEnum(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_HIDEDOTFILES, CoreConfig.HideDotFiles.DOTGITONLY)) != CoreConfig.HideDotFiles.FALSE && !isBare() && getDirectory().getName().startsWith(".")) {
            getFS().setHidden(getDirectory(), true);
        }
        this.refs.create();
        this.objectDatabase.create();
        FileUtils.mkdir(new File(getDirectory(), "branches"));
        FileUtils.mkdir(new File(getDirectory(), Constants.HOOKS));
        RefUpdate refUpdateUpdateRef = updateRef("HEAD");
        refUpdateUpdateRef.disableRefLog();
        refUpdateUpdateRef.link(Constants.R_HEADS + getInitialBranch());
        if (getFS().supportsExecute()) {
            File fileCreateTempFile = File.createTempFile(PsiKeyword.TRY, "execute", getDirectory());
            getFS().setExecute(fileCreateTempFile, true);
            boolean zCanExecute = getFS().canExecute(fileCreateTempFile);
            getFS().setExecute(fileCreateTempFile, false);
            boolean zCanExecute2 = getFS().canExecute(fileCreateTempFile);
            FileUtils.delete(fileCreateTempFile);
            if (!zCanExecute || zCanExecute2) {
                z2 = false;
            } else {
                z2 = true;
            }
        } else {
            z2 = false;
        }
        CoreConfig.SymLinks symLinks = CoreConfig.SymLinks.FALSE;
        if (getFS().supportsSymlinks()) {
            File file = new File(getDirectory(), "tmplink");
            try {
                getFS().createSymLink(file, "target");
                try {
                    FileUtils.delete(file);
                } catch (IOException unused) {
                }
                symLinks = null;
            } catch (IOException unused2) {
            }
        }
        if (symLinks != null) {
            config.setString(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_SYMLINKS, symLinks.name().toLowerCase(Locale.ROOT));
        }
        config.setInt(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION, 0);
        config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_FILEMODE, z2);
        if (z) {
            config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_BARE, true);
        }
        config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES, !z);
        if (SystemReader.getInstance().isMacOS()) {
            config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_PRECOMPOSEUNICODE, true);
        }
        if (!z) {
            File workTree = getWorkTree();
            if (!getDirectory().getParentFile().equals(workTree)) {
                config.setString(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_WORKTREE, getWorkTree().getAbsolutePath());
                LockFile lockFile = new LockFile(new File(workTree, ".git"));
                try {
                    if (lockFile.lock()) {
                        lockFile.write(Constants.encode(Constants.GITDIR + getDirectory().getAbsolutePath()));
                        lockFile.commit();
                    }
                    lockFile.unlock();
                } catch (Throwable th) {
                    lockFile.unlock();
                    throw th;
                }
            }
        }
        config.save();
    }

    public File getObjectsDirectory() {
        return this.objectDatabase.getDirectory();
    }

    @Override // org.eclipse.jgit.lib.Repository
    public ObjectDirectory getObjectDatabase() {
        return this.objectDatabase;
    }

    @Override // org.eclipse.jgit.lib.Repository
    public RefDatabase getRefDatabase() {
        return this.refs;
    }

    @Override // org.eclipse.jgit.lib.Repository
    public String getIdentifier() {
        File directory = getDirectory();
        if (directory != null) {
            return directory.getPath();
        }
        throw new IllegalStateException();
    }

    @Override // org.eclipse.jgit.lib.Repository
    public FileBasedConfig getConfig() {
        try {
            SystemReader.getInstance().getUserConfig();
            if (this.repoConfig.isOutdated()) {
                loadRepoConfig();
            }
            return this.repoConfig;
        } catch (IOException | ConfigInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // org.eclipse.jgit.lib.Repository
    @Nullable
    public String getGitwebDescription() throws IOException {
        try {
            String strDecode = RawParseUtils.decode(IO.readFully(descriptionFile()));
            if (strDecode != null) {
                strDecode = strDecode.trim();
                if (strDecode.isEmpty() || UNNAMED.equals(strDecode)) {
                    return null;
                }
            }
            return strDecode;
        } catch (FileNotFoundException unused) {
            return null;
        }
    }

    @Override // org.eclipse.jgit.lib.Repository
    public void setGitwebDescription(@Nullable String str) throws IOException {
        String strTrim;
        if (Objects.equals(getGitwebDescription(), str)) {
            return;
        }
        File fileDescriptionFile = descriptionFile();
        LockFile lockFile = new LockFile(fileDescriptionFile);
        if (!lockFile.lock()) {
            throw new IOException(MessageFormat.format(JGitText.get().lockError, fileDescriptionFile.getAbsolutePath()));
        }
        if (str != null) {
            try {
                strTrim = str.trim();
                if (!strTrim.isEmpty()) {
                    strTrim = String.valueOf(strTrim) + '\n';
                }
            } finally {
                lockFile.unlock();
            }
        } else {
            strTrim = "";
        }
        lockFile.write(Constants.encode(strTrim));
        lockFile.commit();
    }

    private File descriptionFile() {
        return new File(getDirectory(), MediaTrack.ROLE_DESCRIPTION);
    }

    @Override // org.eclipse.jgit.lib.Repository
    public Set<ObjectId> getAdditionalHaves() {
        return getAdditionalHaves(null);
    }

    private Set<ObjectId> getAdditionalHaves(Set<ObjectDirectory.AlternateHandle.Id> set) {
        HashSet hashSet = new HashSet();
        Set<ObjectDirectory.AlternateHandle.Id> setAddMe = this.objectDatabase.addMe(set);
        for (ObjectDirectory.AlternateHandle alternateHandle : this.objectDatabase.myAlternates()) {
            if ((alternateHandle instanceof ObjectDirectory.AlternateRepository) && !setAddMe.contains(alternateHandle.getId())) {
                FileRepository fileRepository = ((ObjectDirectory.AlternateRepository) alternateHandle).repository;
                for (Ref ref : fileRepository.getAllRefs().values()) {
                    if (ref.getObjectId() != null) {
                        hashSet.add(ref.getObjectId());
                    }
                    if (ref.getPeeledObjectId() != null) {
                        hashSet.add(ref.getPeeledObjectId());
                    }
                }
                hashSet.addAll(fileRepository.getAdditionalHaves(setAddMe));
            }
        }
        return hashSet;
    }

    public void openPack(File file) throws IOException {
        this.objectDatabase.openPack(file);
    }

    @Override // org.eclipse.jgit.lib.Repository
    public void scanForRepoChanges() throws IOException {
        getRefDatabase().getRefs();
        detectIndexChanges();
    }

    private void detectIndexChanges() {
        if (isBare()) {
            return;
        }
        File indexFile = getIndexFile();
        synchronized (this.snapshotLock) {
            FileSnapshot fileSnapshot = this.snapshot;
            if (fileSnapshot == null) {
                this.snapshot = FileSnapshot.save(indexFile);
            } else if (fileSnapshot.isModified(indexFile)) {
                notifyIndexChanged(false);
            }
        }
    }

    @Override // org.eclipse.jgit.lib.Repository
    public void notifyIndexChanged(boolean z) {
        synchronized (this.snapshotLock) {
            this.snapshot = FileSnapshot.save(getIndexFile());
        }
        fireEvent(new IndexChangedEvent(z));
    }

    @Override // org.eclipse.jgit.lib.Repository
    public ReflogReader getReflogReader(String str) throws IOException {
        RefDatabase refDatabase = this.refs;
        if (refDatabase instanceof FileReftableDatabase) {
            return ((FileReftableDatabase) refDatabase).getReflogReader(str);
        }
        Ref refFindRef = findRef(str);
        if (refFindRef == null) {
            return null;
        }
        return new ReflogReaderImpl(this, refFindRef.getName());
    }

    @Override // org.eclipse.jgit.lib.Repository
    public AttributesNodeProvider createAttributesNodeProvider() {
        return new AttributesNodeProviderImpl(this);
    }

    static class AttributesNodeProviderImpl implements AttributesNodeProvider {
        private AttributesNode globalAttributesNode;
        private AttributesNode infoAttributesNode;

        protected AttributesNodeProviderImpl(Repository repository) {
            this.infoAttributesNode = new InfoAttributesNode(repository);
            this.globalAttributesNode = new GlobalAttributesNode(repository);
        }

        @Override // org.eclipse.jgit.attributes.AttributesNodeProvider
        public AttributesNode getInfoAttributesNode() throws IOException {
            AttributesNode attributesNode = this.infoAttributesNode;
            if (attributesNode instanceof InfoAttributesNode) {
                this.infoAttributesNode = ((InfoAttributesNode) attributesNode).load();
            }
            return this.infoAttributesNode;
        }

        @Override // org.eclipse.jgit.attributes.AttributesNodeProvider
        public AttributesNode getGlobalAttributesNode() throws IOException {
            AttributesNode attributesNode = this.globalAttributesNode;
            if (attributesNode instanceof GlobalAttributesNode) {
                this.globalAttributesNode = ((GlobalAttributesNode) attributesNode).load();
            }
            return this.globalAttributesNode;
        }

        static void loadRulesFromFile(AttributesNode attributesNode, File file) throws IOException {
            if (file.exists()) {
                Throwable th = null;
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        attributesNode.parse(fileInputStream);
                    } finally {
                        fileInputStream.close();
                    }
                } catch (Throwable th2) {
                    if (0 == 0) {
                        throw th2;
                    }
                    if (null != th2) {
                        th.addSuppressed(th2);
                    }
                    throw null;
                }
            }
        }
    }

    private boolean shouldAutoDetach() {
        return getConfig().getBoolean(ConfigConstants.CONFIG_GC_SECTION, ConfigConstants.CONFIG_KEY_AUTODETACH, true);
    }

    @Override // org.eclipse.jgit.lib.Repository
    public void autoGC(ProgressMonitor progressMonitor) {
        GC gc = new GC(this);
        gc.setPackConfig(new PackConfig(this));
        gc.setProgressMonitor(progressMonitor);
        gc.setAuto(true);
        gc.setBackground(shouldAutoDetach());
        try {
            gc.gc();
        } catch (IOException | ParseException e) {
            throw new JGitInternalException(JGitText.get().gcFailed, e);
        }
    }

    void convertToPackedRefs(boolean z, boolean z2) throws IOException {
        List<Ref> refs = this.refs.getRefs();
        File file = new File(getDirectory(), Constants.PACKED_REFS);
        if (file.exists()) {
            throw new IOException(MessageFormat.format(JGitText.get().fileAlreadyExists, file.getName()));
        }
        File file2 = new File(getDirectory(), "refs");
        File file3 = new File(file2, "heads");
        File file4 = new File(getDirectory(), "HEAD");
        FileReftableDatabase fileReftableDatabase = (FileReftableDatabase) this.refs;
        file3.delete();
        file2.delete();
        file4.delete();
        RefDirectory refDirectory = new RefDirectory(this);
        this.refs = refDirectory;
        refDirectory.create();
        ReflogWriter reflogWriterNewLogWriter = refDirectory.newLogWriter(true);
        ArrayList<Ref> arrayList = new ArrayList();
        BatchRefUpdate batchRefUpdateNewBatchUpdate = this.refs.newBatchUpdate();
        for (Ref ref : refs) {
            if (ref.isSymbolic()) {
                arrayList.add(ref);
            } else {
                batchRefUpdateNewBatchUpdate.addCommand(new ReceiveCommand(ObjectId.zeroId(), ref.getObjectId(), ref.getName()));
            }
            if (z) {
                List<ReflogEntry> reverseEntries = fileReftableDatabase.getReflogReader(ref.getName()).getReverseEntries();
                Collections.reverse(reverseEntries);
                Iterator<ReflogEntry> it = reverseEntries.iterator();
                while (it.hasNext()) {
                    reflogWriterNewLogWriter.log(ref.getName(), it.next());
                }
            }
        }
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(this);
            try {
                batchRefUpdateNewBatchUpdate.execute(revWalk, NullProgressMonitor.INSTANCE);
                revWalk.close();
                ArrayList arrayList2 = new ArrayList();
                for (ReceiveCommand receiveCommand : batchRefUpdateNewBatchUpdate.getCommands()) {
                    if (receiveCommand.getResult() != ReceiveCommand.Result.OK) {
                        arrayList2.add(String.valueOf(receiveCommand.getRefName()) + ": " + receiveCommand.getResult());
                    }
                }
                if (!arrayList2.isEmpty()) {
                    throw new IOException(String.format("%s: %s", JGitText.get().failedToConvert, StringUtils.join(arrayList2, ", ")));
                }
                for (Ref ref2 : arrayList) {
                    RefUpdate refUpdateNewUpdate = this.refs.newUpdate(ref2.getName(), false);
                    refUpdateNewUpdate.setForceUpdate(true);
                    RefUpdate.Result resultLink = refUpdateNewUpdate.link(ref2.getTarget().getName());
                    if (resultLink != RefUpdate.Result.NEW && resultLink != RefUpdate.Result.NO_CHANGE) {
                        throw new IOException(String.format("ref %s: %s", ref2.getName(), resultLink));
                    }
                }
                if (!z2) {
                    FileUtils.delete(new File(getDirectory(), "reftable"), 9);
                }
                this.repoConfig.unset("extensions", null, "refStorage");
                this.repoConfig.save();
            } catch (Throwable th2) {
                revWalk.close();
                throw th2;
            }
        } catch (Throwable th3) {
            if (0 == 0) {
                throw th3;
            }
            if (null == th3) {
                throw null;
            }
            th.addSuppressed(th3);
        }
    }

    void convertToReftable(boolean z, boolean z2) throws IOException {
        File file = new File(getDirectory(), "reftable");
        File file2 = new File(getDirectory(), "HEAD");
        if (file.exists() && FileUtils.hasFiles(file.toPath())) {
            throw new IOException(JGitText.get().reftableDirExists);
        }
        FileReftableDatabase.convertFrom(this, z);
        File file3 = new File(getDirectory(), "refs");
        File file4 = new File(getDirectory(), Constants.PACKED_REFS);
        File file5 = new File(getDirectory(), Constants.LOGS);
        List<String> list = (List) getRefDatabase().getAdditionalRefs().stream().map(new FileRepository$$ExternalSyntheticLambda1()).collect(Collectors.toList());
        list.add("HEAD");
        if (z2) {
            FileUtils.rename(file3, new File(getDirectory(), "refs.old"));
            if (file4.exists()) {
                FileUtils.rename(file4, new File(getDirectory(), "packed-refs.old"));
            }
            if (file5.exists()) {
                FileUtils.rename(file5, new File(getDirectory(), "logs.old"));
            }
            for (String str : list) {
                FileUtils.rename(new File(getDirectory(), str), new File(getDirectory(), String.valueOf(str) + ".old"));
            }
        } else {
            FileUtils.delete(file4, 4);
            FileUtils.delete(file2, 4);
            FileUtils.delete(file5, 5);
            FileUtils.delete(file3, 5);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                new File(getDirectory(), (String) it.next()).delete();
            }
        }
        FileUtils.mkdir(file3, true);
        Throwable th = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                fileOutputStream.write(Constants.encodeASCII("ref: refs/heads/.invalid"));
                fileOutputStream.close();
                FileUtils.createNewFile(new File(file3, "heads"));
                this.repoConfig.setString("extensions", null, "refStorage", "reftable");
                this.repoConfig.setLong(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION, 1L);
                this.repoConfig.save();
                this.refs.close();
                this.refs = new FileReftableDatabase(this);
            } catch (Throwable th2) {
                fileOutputStream.close();
                throw th2;
            }
        } catch (Throwable th3) {
            if (0 == 0) {
                throw th3;
            }
            if (null != th3) {
                th.addSuppressed(th3);
            }
            throw null;
        }
    }

    public void convertRefStorage(String str, boolean z, boolean z2) throws IOException {
        if (str.equals("reftable")) {
            if (this.refs instanceof RefDirectory) {
                convertToReftable(z, z2);
            }
        } else {
            if (str.equals("refdir")) {
                if (this.refs instanceof FileReftableDatabase) {
                    convertToPackedRefs(z, z2);
                    return;
                }
                return;
            }
            throw new IOException(MessageFormat.format(JGitText.get().unknownRefStorageFormat, str));
        }
    }
}
