package name.antonsmirnov.firmata.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import name.antonsmirnov.firmata.message.I2cReplyMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import name.antonsmirnov.firmata.message.SysexMessage;
import name.antonsmirnov.firmata.serial.SerialException;

/* JADX INFO: loaded from: classes12.dex */
public class MessagesHistoryWrapper implements IFirmata, IFirmata.Listener {
    private IFirmata firmata;
    private IMessagePropertyManager[] propertyManagers;
    private List<MessageWithProperties> messages = new ArrayList();
    private DirectionMessagePropertyManager receivedPropertyManager = new DirectionMessagePropertyManager(true);
    private IMessageFilter receivedFilter = new DirectionMessageFilter(this.receivedPropertyManager);
    private DirectionMessagePropertyManager sentPropertyManager = new DirectionMessagePropertyManager(false);
    private IMessageFilter sentFilter = new DirectionMessageFilter(this.sentPropertyManager);
    private IMessageFilter stubMessageFilter = new StubMessageFilter();

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onUnknownByteReceived(int i) {
    }

    public MessagesHistoryWrapper(IFirmata iFirmata, IMessagePropertyManager... iMessagePropertyManagerArr) {
        this.firmata = iFirmata;
        this.propertyManagers = iMessagePropertyManagerArr;
        iFirmata.addListener(this);
        clear();
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void addListener(IFirmata.Listener listener) {
        this.firmata.addListener(listener);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void removeListener(IFirmata.Listener listener) {
        this.firmata.removeListener(listener);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public boolean containsListener(IFirmata.Listener listener) {
        return this.firmata.containsListener(listener);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void clearListeners() {
        this.firmata.clearListeners();
    }

    protected void addCommonProperties(MessageWithProperties messageWithProperties) {
        for (IMessagePropertyManager iMessagePropertyManager : this.propertyManagers) {
            iMessagePropertyManager.set(messageWithProperties);
        }
    }

    protected void rememberReceivedMessage(Message message) {
        MessageWithProperties messageWithProperties = new MessageWithProperties(message);
        addCommonProperties(messageWithProperties);
        this.receivedPropertyManager.set(messageWithProperties);
        this.messages.add(messageWithProperties);
    }

    private void rememberSentMessage(Message message) {
        MessageWithProperties messageWithProperties = new MessageWithProperties(message);
        addCommonProperties(messageWithProperties);
        this.sentPropertyManager.set(messageWithProperties);
        this.messages.add(messageWithProperties);
    }

    public List<MessageWithProperties> getMessages() {
        return getMessages(this.stubMessageFilter);
    }

    public List<MessageWithProperties> getMessages(IMessageFilter iMessageFilter) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        for (MessageWithProperties messageWithProperties : this.messages) {
            if (iMessageFilter.isAllowed(messageWithProperties)) {
                copyOnWriteArrayList.add(messageWithProperties);
            }
        }
        return copyOnWriteArrayList;
    }

    public List<MessageWithProperties> getReceivedMessages() {
        return getMessages(this.receivedFilter);
    }

    public List<MessageWithProperties> getSentMessages() {
        return getMessages(this.sentFilter);
    }

    public MessageWithProperties getLastReceivedMessageWithProperties() {
        List<MessageWithProperties> receivedMessages = getReceivedMessages();
        if (receivedMessages.size() > 0) {
            return receivedMessages.get(receivedMessages.size() - 1);
        }
        return null;
    }

    public void clear() {
        this.messages.clear();
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onAnalogMessageReceived(AnalogMessage analogMessage) {
        rememberReceivedMessage(analogMessage);
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onDigitalMessageReceived(DigitalMessage digitalMessage) {
        rememberReceivedMessage(digitalMessage);
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onFirmwareVersionMessageReceived(FirmwareVersionMessage firmwareVersionMessage) {
        rememberReceivedMessage(firmwareVersionMessage);
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onProtocolVersionMessageReceived(ProtocolVersionMessage protocolVersionMessage) {
        rememberReceivedMessage(protocolVersionMessage);
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onSysexMessageReceived(SysexMessage sysexMessage) {
        rememberReceivedMessage(sysexMessage);
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onStringSysexMessageReceived(StringSysexMessage stringSysexMessage) {
        rememberReceivedMessage(stringSysexMessage);
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onI2cMessageReceived(I2cReplyMessage i2cReplyMessage) {
        rememberReceivedMessage(i2cReplyMessage);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void send(Message message) throws SerialException {
        this.firmata.send(message);
        rememberSentMessage(message);
    }

    @Override // name.antonsmirnov.firmata.IFirmata
    public void onDataReceived(int i) {
        this.firmata.onDataReceived(i);
    }
}
