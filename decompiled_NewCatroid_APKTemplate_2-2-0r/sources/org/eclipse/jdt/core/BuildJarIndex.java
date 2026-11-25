package org.eclipse.jdt.core;

import java.io.IOException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.jdt.core.index.JavaIndexer;
import org.eclipse.jdt.internal.antadapter.AntAdapterMessages;

/* JADX INFO: loaded from: classes14.dex */
public class BuildJarIndex extends Task {
    private String indexPath;
    private String jarPath;

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.apache.tools.ant.BuildException */
    public void execute() throws BuildException {
        String str = this.jarPath;
        if (str == null) {
            throw new BuildException(AntAdapterMessages.getString("buildJarIndex.jarFile.cannot.be.null"));
        }
        String str2 = this.indexPath;
        if (str2 == null) {
            throw new BuildException(AntAdapterMessages.getString("buildJarIndex.indexFile.cannot.be.null"));
        }
        try {
            JavaIndexer.generateIndexForJar(str, str2);
        } catch (IOException e) {
            throw new BuildException(AntAdapterMessages.getString("buildJarIndex.ioexception.occured", e.getLocalizedMessage()));
        }
    }

    public void setJarPath(String str) {
        this.jarPath = str;
    }

    public void setIndexPath(String str) {
        this.indexPath = str;
    }
}
