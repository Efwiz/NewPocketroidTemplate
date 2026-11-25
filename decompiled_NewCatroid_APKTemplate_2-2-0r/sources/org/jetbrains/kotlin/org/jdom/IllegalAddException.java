package org.jetbrains.kotlin.org.jdom;

import com.huawei.hms.framework.common.ContainerUtils;

/* JADX INFO: loaded from: classes4.dex */
public class IllegalAddException extends IllegalArgumentException {
    private static final long serialVersionUID = 200;

    IllegalAddException(Element element, Attribute attribute, String str) {
        super("The attribute \"" + attribute.getQualifiedName() + "\" could not be added to the element \"" + element.getQualifiedName() + "\": " + str);
    }

    IllegalAddException(Element element, String str) {
        super("The element \"" + element.getQualifiedName() + "\" could not be added as the root of the document: " + str);
    }

    IllegalAddException(Element element, Namespace namespace, String str) {
        super("The namespace xmlns" + (namespace.getPrefix().equals("") ? ContainerUtils.KEY_VALUE_DELIMITER : ":" + namespace.getPrefix() + ContainerUtils.KEY_VALUE_DELIMITER) + "\"" + namespace.getURI() + "\" could not be added as a namespace to \"" + element.getQualifiedName() + "\": " + str);
    }

    public IllegalAddException(String str) {
        super(str);
    }
}
