package com.reandroid.dex.smali;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.dex.smali.formatters.ClassComment;
import com.reandroid.dex.smali.formatters.MethodComment;
import com.reandroid.dex.smali.formatters.ResourceIdComment;
import com.reandroid.utils.collection.ArrayCollection;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliWriterSetting {
    private List<ClassComment> classCommentList;
    private List<MethodComment> methodCommentList;
    private ResourceIdComment resourceIdComment;
    private boolean sequentialLabel = true;
    private boolean commentUnicodeStrings = false;

    public boolean isSequentialLabel() {
        return this.sequentialLabel;
    }

    public void setSequentialLabel(boolean z) {
        this.sequentialLabel = z;
    }

    public boolean isCommentUnicodeStrings() {
        return this.commentUnicodeStrings;
    }

    public void setCommentUnicodeStrings(boolean z) {
        this.commentUnicodeStrings = z;
    }

    public void writeResourceIdComment(SmaliWriter smaliWriter, long j) throws IOException {
        ResourceIdComment resourceIdComment = getResourceIdComment();
        if (resourceIdComment != null) {
            resourceIdComment.writeComment(smaliWriter, (int) j);
        }
    }

    public void writeResourceIdComment(SmaliWriter smaliWriter, int i) throws IOException {
        ResourceIdComment resourceIdComment = getResourceIdComment();
        if (resourceIdComment != null) {
            resourceIdComment.writeComment(smaliWriter, i);
        }
    }

    public ResourceIdComment getResourceIdComment() {
        return this.resourceIdComment;
    }

    public void setResourceIdComment(ResourceIdComment resourceIdComment) {
        this.resourceIdComment = resourceIdComment;
    }

    public void setResourceIdComment(PackageBlock packageBlock) {
        setResourceIdComment(new ResourceIdComment.ResourceTableComment(packageBlock));
    }

    public void writeMethodComment(SmaliWriter smaliWriter, MethodKey methodKey) throws IOException {
        List<MethodComment> methodCommentList = getMethodCommentList();
        if (methodCommentList != null) {
            Iterator<MethodComment> it = methodCommentList.iterator();
            while (it.hasNext()) {
                it.next().writeComment(smaliWriter, methodKey);
            }
        }
    }

    public List<MethodComment> getMethodCommentList() {
        return this.methodCommentList;
    }

    public void clearMethodComments() {
        List<MethodComment> list = this.methodCommentList;
        if (list != null) {
            list.clear();
        }
    }

    public void addMethodComment(MethodComment methodComment) {
        if (methodComment == null) {
            return;
        }
        List arrayCollection = this.methodCommentList;
        if (arrayCollection == null) {
            arrayCollection = new ArrayCollection();
            this.methodCommentList = arrayCollection;
        }
        if (arrayCollection.contains(methodComment)) {
            return;
        }
        arrayCollection.add(methodComment);
    }

    public void addMethodComments(DexClassRepository dexClassRepository) {
        addMethodComment(new MethodComment.MethodOverrideComment(dexClassRepository));
        addMethodComment(new MethodComment.MethodImplementComment(dexClassRepository));
    }

    public void writeClassComment(SmaliWriter smaliWriter, TypeKey typeKey) throws IOException {
        List<ClassComment> classCommentList = getClassCommentList();
        if (classCommentList != null) {
            Iterator<ClassComment> it = classCommentList.iterator();
            while (it.hasNext()) {
                it.next().writeComment(smaliWriter, typeKey);
            }
        }
    }

    public List<ClassComment> getClassCommentList() {
        return this.classCommentList;
    }

    public void clearClassComments() {
        List<ClassComment> list = this.classCommentList;
        if (list != null) {
            list.clear();
        }
    }

    public void addClassComment(ClassComment classComment) {
        if (classComment == null) {
            return;
        }
        List arrayCollection = this.classCommentList;
        if (arrayCollection == null) {
            arrayCollection = new ArrayCollection();
            this.classCommentList = arrayCollection;
        }
        if (arrayCollection.contains(classComment)) {
            return;
        }
        arrayCollection.add(classComment);
    }

    public void addClassComments(DexClassRepository dexClassRepository) {
        addClassComment(new ClassComment.ClassExtendComment(dexClassRepository));
        addClassComment(new ClassComment.ClassImplementComment(dexClassRepository));
    }
}
