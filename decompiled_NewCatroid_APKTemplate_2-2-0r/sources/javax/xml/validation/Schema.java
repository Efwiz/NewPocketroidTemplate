package javax.xml.validation;

/* JADX INFO: loaded from: classes11.dex */
public abstract class Schema {
    protected Schema() {
    }

    public abstract Validator newValidator();

    public abstract ValidatorHandler newValidatorHandler();
}
