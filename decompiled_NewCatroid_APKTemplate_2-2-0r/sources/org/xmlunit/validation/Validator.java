package org.xmlunit.validation;

import java.util.Arrays;
import javax.xml.transform.Source;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Validator {
    private Source[] sourceLocations;

    public abstract ValidationResult validateInstance(Source source);

    public abstract ValidationResult validateSchema();

    protected Validator() {
    }

    public void setSchemaSources(Source... sourceArr) {
        if (sourceArr != null) {
            this.sourceLocations = (Source[]) Arrays.copyOf(sourceArr, sourceArr.length);
        } else {
            this.sourceLocations = null;
        }
    }

    public final void setSchemaSource(Source source) {
        setSchemaSources(source == null ? null : new Source[]{source});
    }

    protected Source[] getSchemaSources() {
        Source[] sourceArr = this.sourceLocations;
        return sourceArr == null ? new Source[0] : sourceArr;
    }

    public static Validator forLanguage(String str) {
        if (!"http://www.w3.org/TR/REC-xml".equals(str)) {
            return new JAXPValidator(str);
        }
        return new ParsingValidator("http://www.w3.org/TR/REC-xml");
    }
}
