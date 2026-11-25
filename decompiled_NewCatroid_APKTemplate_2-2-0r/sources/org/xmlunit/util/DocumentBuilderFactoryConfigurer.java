package org.xmlunit.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xmlunit.ConfigurationException;

/* JADX INFO: loaded from: classes3.dex */
public class DocumentBuilderFactoryConfigurer {
    public static final DocumentBuilderFactoryConfigurer Default = builder().withDTDParsingDisabled().withDTDLoadingDisabled().withXIncludeAware(false).withExpandEntityReferences(false).build();
    private final Map<String, Object> attributes;
    private final boolean expandEntityRefs;
    private final Map<String, Boolean> features;
    private final Map<String, Object> safeAttributes;
    private final Map<String, Boolean> safeFeatures;
    private final boolean xIncludeAware;

    private DocumentBuilderFactoryConfigurer(Map<String, Object> map, Map<String, Object> map2, Map<String, Boolean> map3, Map<String, Boolean> map4, boolean z, boolean z2) {
        this.attributes = map;
        this.safeAttributes = map2;
        this.features = map3;
        this.safeFeatures = map4;
        this.xIncludeAware = z;
        this.expandEntityRefs = z2;
    }

    public DocumentBuilderFactory configure(DocumentBuilderFactory documentBuilderFactory) {
        for (Map.Entry<String, Object> entry : this.attributes.entrySet()) {
            try {
                documentBuilderFactory.setAttribute(entry.getKey(), entry.getValue());
            } catch (IllegalArgumentException e) {
                throw new ConfigurationException("Error setting attribute " + entry.getKey(), e);
            }
        }
        for (Map.Entry<String, Object> entry2 : this.safeAttributes.entrySet()) {
            try {
                documentBuilderFactory.setAttribute(entry2.getKey(), entry2.getValue());
            } catch (IllegalArgumentException unused) {
            }
        }
        for (Map.Entry<String, Boolean> entry3 : this.features.entrySet()) {
            try {
                documentBuilderFactory.setFeature(entry3.getKey(), entry3.getValue().booleanValue());
            } catch (ParserConfigurationException e2) {
                throw new ConfigurationException("Error setting feature " + entry3.getKey(), e2);
            }
        }
        for (Map.Entry<String, Boolean> entry4 : this.safeFeatures.entrySet()) {
            try {
                documentBuilderFactory.setFeature(entry4.getKey(), entry4.getValue().booleanValue());
            } catch (ParserConfigurationException unused2) {
            }
        }
        documentBuilderFactory.setXIncludeAware(this.xIncludeAware);
        documentBuilderFactory.setExpandEntityReferences(this.expandEntityRefs);
        return documentBuilderFactory;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final List<String> DTD_LOAD_DISABLERS = Arrays.asList("http://xerces.apache.org/xerces-j/features.html#external-general-entities", "http://xerces.apache.org/xerces2-j/features.html#external-general-entities", "http://xml.org/sax/features/external-general-entities", "http://apache.org/xml/features/nonvalidating/load-dtd-grammar", "http://apache.org/xml/features/nonvalidating/load-external-dtd", "http://xerces.apache.org/xerces-j/features.html#external-parameter-entities", "http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities", "http://xml.org/sax/features/external-parameter-entities");
        private static final List<String> DTD_PARSE_DISABLERS = Arrays.asList("http://xerces.apache.org/xerces2-j/features.html#disallow-doctype-decl", "http://apache.org/xml/features/disallow-doctype-decl");
        private final Map<String, Object> attributes = new HashMap();
        private final Map<String, Object> safeAttributes = new HashMap();
        private final Map<String, Boolean> features = new HashMap();
        private final Map<String, Boolean> safeFeatures = new HashMap();
        private boolean xIncludeAware = false;
        private boolean expandEntityRefs = false;

        public DocumentBuilderFactoryConfigurer build() {
            return new DocumentBuilderFactoryConfigurer(Collections.unmodifiableMap(this.attributes), Collections.unmodifiableMap(this.safeAttributes), Collections.unmodifiableMap(this.features), Collections.unmodifiableMap(this.safeFeatures), this.xIncludeAware, this.expandEntityRefs);
        }

        public Builder withAttribute(String str, Object obj) {
            this.attributes.put(str, obj);
            return this;
        }

        public Builder withSafeAttribute(String str, Object obj) {
            this.safeAttributes.put(str, obj);
            return this;
        }

        public Builder withFeature(String str, boolean z) {
            this.features.put(str, Boolean.valueOf(z));
            return this;
        }

        public Builder withSafeFeature(String str, boolean z) {
            this.safeFeatures.put(str, Boolean.valueOf(z));
            return this;
        }

        public Builder withDTDLoadingDisabled() {
            Iterator<String> it = DTD_LOAD_DISABLERS.iterator();
            while (it.hasNext()) {
                withSafeFeature(it.next(), false);
            }
            return this;
        }

        public Builder withDTDParsingDisabled() {
            Iterator<String> it = DTD_PARSE_DISABLERS.iterator();
            while (it.hasNext()) {
                withSafeFeature(it.next(), false);
            }
            return this;
        }

        public Builder withXIncludeAware(boolean z) {
            this.xIncludeAware = z;
            return this;
        }

        public Builder withExpandEntityReferences(boolean z) {
            this.expandEntityRefs = z;
            return this;
        }
    }
}
