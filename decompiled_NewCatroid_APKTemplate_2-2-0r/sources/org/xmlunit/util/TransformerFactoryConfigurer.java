package org.xmlunit.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import org.xmlunit.ConfigurationException;

/* JADX INFO: loaded from: classes3.dex */
public class TransformerFactoryConfigurer {
    public static final TransformerFactoryConfigurer Default = builder().withDTDLoadingDisabled().build();
    public static final TransformerFactoryConfigurer NoExternalAccess = builder().withDTDLoadingDisabled().withExternalStylesheetLoadingDisabled().build();
    private final Map<String, Object> attributes;
    private final Map<String, Boolean> features;
    private final Map<String, Object> safeAttributes;
    private final Map<String, Boolean> safeFeatures;

    private TransformerFactoryConfigurer(Map<String, Object> map, Map<String, Object> map2, Map<String, Boolean> map3, Map<String, Boolean> map4) {
        this.attributes = map;
        this.safeAttributes = map2;
        this.features = map3;
        this.safeFeatures = map4;
    }

    public TransformerFactory configure(TransformerFactory transformerFactory) {
        for (Map.Entry<String, Object> entry : this.attributes.entrySet()) {
            try {
                transformerFactory.setAttribute(entry.getKey(), entry.getValue());
            } catch (IllegalArgumentException e) {
                throw new ConfigurationException("Error setting attribute " + entry.getKey(), e);
            }
        }
        for (Map.Entry<String, Object> entry2 : this.safeAttributes.entrySet()) {
            try {
                transformerFactory.setAttribute(entry2.getKey(), entry2.getValue());
            } catch (IllegalArgumentException unused) {
            }
        }
        for (Map.Entry<String, Boolean> entry3 : this.features.entrySet()) {
            try {
                transformerFactory.setFeature(entry3.getKey(), entry3.getValue().booleanValue());
            } catch (TransformerConfigurationException e2) {
                throw new ConfigurationException("Error setting feature " + entry3.getKey(), e2);
            }
        }
        for (Map.Entry<String, Boolean> entry4 : this.safeFeatures.entrySet()) {
            try {
                transformerFactory.setFeature(entry4.getKey(), entry4.getValue().booleanValue());
            } catch (TransformerConfigurationException unused2) {
            }
        }
        return transformerFactory;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<String, Object> attributes = new HashMap();
        private final Map<String, Object> safeAttributes = new HashMap();
        private final Map<String, Boolean> features = new HashMap();
        private final Map<String, Boolean> safeFeatures = new HashMap();

        public TransformerFactoryConfigurer build() {
            return new TransformerFactoryConfigurer(Collections.unmodifiableMap(this.attributes), Collections.unmodifiableMap(this.safeAttributes), Collections.unmodifiableMap(this.features), Collections.unmodifiableMap(this.safeFeatures));
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
            return withSafeAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
        }

        public Builder withExternalStylesheetLoadingDisabled() {
            return withSafeAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "");
        }
    }
}
