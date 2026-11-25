package antlr.debug;

/* JADX INFO: loaded from: classes.dex */
public interface MessageListener extends ListenerBase {
    void reportError(MessageEvent messageEvent);

    void reportWarning(MessageEvent messageEvent);
}
