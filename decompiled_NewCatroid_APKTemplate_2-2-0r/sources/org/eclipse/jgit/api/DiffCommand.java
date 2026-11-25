package org.eclipse.jgit.api;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.io.NullOutputStream;

/* JADX INFO: loaded from: classes13.dex */
public class DiffCommand extends GitCommand<List<DiffEntry>> {
    private boolean cached;
    private int contextLines;
    private String destinationPrefix;
    private ProgressMonitor monitor;
    private AbstractTreeIterator newTree;
    private AbstractTreeIterator oldTree;
    private OutputStream out;
    private TreeFilter pathFilter;
    private boolean showNameAndStatusOnly;
    private String sourcePrefix;

    protected DiffCommand(Repository repository) {
        super(repository);
        this.pathFilter = TreeFilter.ALL;
        this.contextLines = -1;
        this.monitor = NullProgressMonitor.INSTANCE;
    }

    private DiffFormatter getDiffFormatter() {
        if (this.out != null && !this.showNameAndStatusOnly) {
            return new DiffFormatter(new BufferedOutputStream(this.out));
        }
        return new DiffFormatter(NullOutputStream.INSTANCE);
    }

    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public List<DiffEntry> call() throws GitAPIException {
        Throwable th = null;
        try {
            DiffFormatter diffFormatter = getDiffFormatter();
            try {
                diffFormatter.setRepository(this.repo);
                diffFormatter.setProgressMonitor(this.monitor);
                if (this.cached) {
                    if (this.oldTree == null) {
                        ObjectId objectIdResolve = this.repo.resolve("HEAD^{tree}");
                        if (objectIdResolve == null) {
                            throw new NoHeadException(JGitText.get().cannotReadTree);
                        }
                        CanonicalTreeParser canonicalTreeParser = new CanonicalTreeParser();
                        try {
                            ObjectReader objectReaderNewObjectReader = this.repo.newObjectReader();
                            try {
                                canonicalTreeParser.reset(objectReaderNewObjectReader, objectIdResolve);
                                if (objectReaderNewObjectReader != null) {
                                    objectReaderNewObjectReader.close();
                                }
                                this.oldTree = canonicalTreeParser;
                            } catch (Throwable th2) {
                                if (objectReaderNewObjectReader != null) {
                                    objectReaderNewObjectReader.close();
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
                            th.addSuppressed(th3);
                            throw null;
                        }
                    }
                    this.newTree = new DirCacheIterator(this.repo.readDirCache());
                } else {
                    if (this.oldTree == null) {
                        this.oldTree = new DirCacheIterator(this.repo.readDirCache());
                    }
                    if (this.newTree == null) {
                        this.newTree = new FileTreeIterator(this.repo);
                    }
                }
                diffFormatter.setPathFilter(this.pathFilter);
                List<DiffEntry> listScan = diffFormatter.scan(this.oldTree, this.newTree);
                if (this.showNameAndStatusOnly) {
                    if (diffFormatter != null) {
                        diffFormatter.close();
                    }
                    return listScan;
                }
                int i = this.contextLines;
                if (i >= 0) {
                    diffFormatter.setContext(i);
                }
                String str = this.destinationPrefix;
                if (str != null) {
                    diffFormatter.setNewPrefix(str);
                }
                String str2 = this.sourcePrefix;
                if (str2 != null) {
                    diffFormatter.setOldPrefix(str2);
                }
                diffFormatter.format(listScan);
                diffFormatter.flush();
                if (diffFormatter != null) {
                    diffFormatter.close();
                }
                return listScan;
            } catch (Throwable th4) {
                if (diffFormatter != null) {
                    diffFormatter.close();
                }
                throw th4;
            }
        } catch (Throwable th5) {
            if (0 == 0) {
                throw th5;
            }
            if (null == th5) {
                throw null;
            }
            try {
                th.addSuppressed(th5);
                throw null;
            } catch (IOException e) {
                throw new JGitInternalException(e.getMessage(), e);
            }
            throw new JGitInternalException(e.getMessage(), e);
        }
    }

    public DiffCommand setCached(boolean z) {
        this.cached = z;
        return this;
    }

    public DiffCommand setPathFilter(TreeFilter treeFilter) {
        this.pathFilter = treeFilter;
        return this;
    }

    public DiffCommand setOldTree(AbstractTreeIterator abstractTreeIterator) {
        this.oldTree = abstractTreeIterator;
        return this;
    }

    public DiffCommand setNewTree(AbstractTreeIterator abstractTreeIterator) {
        this.newTree = abstractTreeIterator;
        return this;
    }

    public DiffCommand setShowNameAndStatusOnly(boolean z) {
        this.showNameAndStatusOnly = z;
        return this;
    }

    public DiffCommand setOutputStream(OutputStream outputStream) {
        this.out = outputStream;
        return this;
    }

    public DiffCommand setContextLines(int i) {
        this.contextLines = i;
        return this;
    }

    public DiffCommand setSourcePrefix(String str) {
        this.sourcePrefix = str;
        return this;
    }

    public DiffCommand setDestinationPrefix(String str) {
        this.destinationPrefix = str;
        return this;
    }

    public DiffCommand setProgressMonitor(ProgressMonitor progressMonitor) {
        if (progressMonitor == null) {
            progressMonitor = NullProgressMonitor.INSTANCE;
        }
        this.monitor = progressMonitor;
        return this;
    }
}
