package org.jetbrains.kotlin.js.backend.ast;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.js.backend.JsToStringGenerationVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadataImpl;
import org.jetbrains.kotlin.js.util.TextOutputImpl;

/* JADX INFO: loaded from: classes5.dex */
abstract class AbstractNode implements JsNode, HasMetadata {
    private Internals internals = null;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        if (i == 4) {
            objArr[0] = "other";
        } else if (i != 5) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/AbstractNode";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/AbstractNode";
        } else {
            objArr[1] = "getRawMetadata";
        }
        if (i == 1) {
            objArr[2] = "setData";
        } else if (i == 2) {
            objArr[2] = "hasData";
        } else if (i == 3) {
            objArr[2] = "removeData";
        } else if (i == 4) {
            objArr[2] = "copyMetadataFrom";
        } else if (i != 5) {
            objArr[2] = "getData";
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    private static class Internals extends HasMetadataImpl {
        List<JsComment> commentsAfter;
        List<JsComment> commentsBefore;

        private Internals() {
            this.commentsBefore = null;
            this.commentsAfter = null;
        }
    }

    AbstractNode() {
    }

    private Internals getInternals() {
        if (this.internals == null) {
            this.internals = new Internals();
        }
        return this.internals;
    }

    public String toString() {
        TextOutputImpl textOutputImpl = new TextOutputImpl();
        new JsToStringGenerationVisitor(textOutputImpl).accept(this);
        return textOutputImpl.toString();
    }

    public <T extends HasMetadata & JsNode> T withMetadataFrom(T t) {
        copyMetadataFrom(t);
        T t2 = t;
        Object source = t2.getSource();
        if (source != null) {
            source(source);
        }
        setCommentsBeforeNode(t2.getCommentsBeforeNode());
        setCommentsAfterNode(t2.getCommentsAfterNode());
        return this;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public List<JsComment> getCommentsBeforeNode() {
        Internals internals = this.internals;
        if (internals == null) {
            return null;
        }
        return internals.commentsBefore;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public List<JsComment> getCommentsAfterNode() {
        Internals internals = this.internals;
        if (internals == null) {
            return null;
        }
        return internals.commentsAfter;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void setCommentsBeforeNode(List<JsComment> list) {
        getInternals().commentsBefore = list;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void setCommentsAfterNode(List<JsComment> list) {
        getInternals().commentsAfter = list;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public <T> T getData(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return (T) getInternals().getData(str);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public <T> void setData(String str, T t) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        getInternals().setData(str, t);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public boolean hasData(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        Internals internals = this.internals;
        return internals != null && internals.hasData(str);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public void removeData(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        Internals internals = this.internals;
        if (internals != null) {
            internals.removeData(str);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public void copyMetadataFrom(HasMetadata hasMetadata) {
        if (hasMetadata == null) {
            $$$reportNull$$$0(4);
        }
        if (hasMetadata.getRawMetadata().isEmpty()) {
            return;
        }
        getInternals().copyMetadataFrom(hasMetadata);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public Map<String, Object> getRawMetadata() {
        Internals internals = this.internals;
        Map<String, Object> rawMetadata = internals != null ? internals.getRawMetadata() : Collections.emptyMap();
        if (rawMetadata == null) {
            $$$reportNull$$$0(5);
        }
        return rawMetadata;
    }
}
