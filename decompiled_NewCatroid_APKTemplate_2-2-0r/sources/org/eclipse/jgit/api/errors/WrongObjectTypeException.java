package org.eclipse.jgit.api.errors;

import java.text.MessageFormat;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;

/* JADX INFO: loaded from: classes13.dex */
public class WrongObjectTypeException extends GitAPIException {
    private static final long serialVersionUID = 1;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f641name;
    private int type;

    public WrongObjectTypeException(ObjectId objectId, int i) {
        super(MessageFormat.format(JGitText.get().objectIsNotA, objectId.name(), Constants.typeString(i)));
        this.f641name = objectId.name();
        this.type = i;
    }

    public String getObjectId() {
        return this.f641name;
    }

    public int getExpectedType() {
        return this.type;
    }
}
