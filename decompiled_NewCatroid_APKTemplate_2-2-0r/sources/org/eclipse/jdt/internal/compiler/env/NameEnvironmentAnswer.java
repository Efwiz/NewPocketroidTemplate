package org.eclipse.jdt.internal.compiler.env;

/* JADX INFO: loaded from: classes14.dex */
public class NameEnvironmentAnswer {
    AccessRestriction accessRestriction;
    IBinaryType binaryType;
    ICompilationUnit compilationUnit;
    String externalAnnotationPath;
    ISourceType[] sourceTypes;

    public NameEnvironmentAnswer(IBinaryType iBinaryType, AccessRestriction accessRestriction) {
        this.binaryType = iBinaryType;
        this.accessRestriction = accessRestriction;
    }

    public NameEnvironmentAnswer(ICompilationUnit iCompilationUnit, AccessRestriction accessRestriction) {
        this.compilationUnit = iCompilationUnit;
        this.accessRestriction = accessRestriction;
    }

    public NameEnvironmentAnswer(ISourceType[] iSourceTypeArr, AccessRestriction accessRestriction, String str) {
        this.sourceTypes = iSourceTypeArr;
        this.accessRestriction = accessRestriction;
        this.externalAnnotationPath = str;
    }

    public AccessRestriction getAccessRestriction() {
        return this.accessRestriction;
    }

    public IBinaryType getBinaryType() {
        return this.binaryType;
    }

    public ICompilationUnit getCompilationUnit() {
        return this.compilationUnit;
    }

    public String getExternalAnnotationPath() {
        return this.externalAnnotationPath;
    }

    public ISourceType[] getSourceTypes() {
        return this.sourceTypes;
    }

    public boolean isBinaryType() {
        return this.binaryType != null;
    }

    public boolean isCompilationUnit() {
        return this.compilationUnit != null;
    }

    public boolean isSourceType() {
        return this.sourceTypes != null;
    }

    public boolean ignoreIfBetter() {
        AccessRestriction accessRestriction = this.accessRestriction;
        return accessRestriction != null && accessRestriction.ignoreIfBetter();
    }

    public boolean isBetter(NameEnvironmentAnswer nameEnvironmentAnswer) {
        AccessRestriction accessRestriction;
        if (nameEnvironmentAnswer == null || (accessRestriction = this.accessRestriction) == null) {
            return true;
        }
        return nameEnvironmentAnswer.accessRestriction != null && accessRestriction.getProblemId() < nameEnvironmentAnswer.accessRestriction.getProblemId();
    }
}
