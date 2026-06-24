package com.contabilidad.WsSupabase.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * <h2>Arquitectura de Deserialización Fuertemente Tipada para Meta (WhatsApp API)</h2>
 * * <p>Esta clase actúa como el POJO raíz (clase espejo) para mapear el JSON crudo 
 * enviado por los Webhooks de Meta (WhatsApp Business Account) mediante Gson.</p>
 * * <p>Se optó por un diseño de <b>clases internas estáticas anidadas</b> para replicar fielmente 
 * la jerarquía del árbol JSON en un único archivo, facilitando el mantenimiento y evitando la 
 * dispersión de archivos para estructuras que solo representan transferencia de datos .</p>
 * * <h3>Ejemplo del Payload estructurado que resuelve esta arquitectura:</h3>
 * 
 * 
 * {
    "object": "whatsapp_business_account",
    "entry": [
        {
            "id": <ID>,
            "changes": [
                {
                    "value": {
                        "messaging_product": "whatsapp",
                        "metadata": {
                            "display_phone_number": <PHONE_NUMBER>,
                            "phone_number_id": <PHONE_NUMBER_ID>
                        },
                        "contacts": [
                            {
                                "profile": {
                                    "name": <NAME>
                                },
                                "wa_id": <WA_ID>,
                                "user_id": <USER_ID>
                            }
                        ],
                        "messages": [
                            {
                                "from": <PHONE_NUMBER>,
                                "from_user_id": <USER_ID>,
                                "id": <MESSAGE_ID>,
                                "timestamp": <TIMESTAMP>,
                                "text": {
                                    "body": <MESSAGE_TEXT>
                                },
                                "type": "text"
                            }
                        ]
                    },
                    "field": "messages"
                }
            ]
        }
    ]
}
 * 
 * 
 * 
 * 
 * */


public class WSPayload {

    @SerializedName("object")
    String object;
    @SerializedName("entry")
    List<ItemEntry> entry;




    public WSPayload(List<ItemEntry> entry) {
        this.entry = entry;
    }

    public List<ItemEntry> getEntry() {
        return entry;
    }

    public void setEntry(List<ItemEntry> entry) {
        this.entry = entry;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public class ItemEntry {

        @SerializedName("id")
        String id;
        @SerializedName("changes")
        List<ItemChanges> changes;

        /*
         * public ItemEntry(List<ItemChanges> changes) {
         * this.changes = changes;
         * }
         */

        public List<ItemChanges> getChanges() {
            return changes;
        }

        public void setChanges(List<ItemChanges> changes) {
            this.changes = changes;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class ItemChanges {

        @SerializedName("field")
        String field;
        @SerializedName("value")
        Value value;

        /*
         * public ItemChanges(String field, Value value) {
         * this.field = field;
         * this.value = value;
         * }
         * public ItemChanges(Value value) {
         * this.value = value;
         * }
         */

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }

    public class Value {

        @SerializedName("messaging_product")
        String messagingProduct;
        @SerializedName("metadata")
        Metadata metadata;
        @SerializedName("contacts")
        List<ItemContacts> contacts;
        @SerializedName("messages")
        List<ItemMessages> messages;

        /*
         * public Value(List<ItemMessages> cText) {
         * messages = cText;
         * }
         */

        public List<ItemMessages> getMessages() {
            return messages;
        }

        public void setMessages(List<ItemMessages> cText) {
            messages = cText;
        }

        public String getMessagingProduct() {
            return messagingProduct;
        }

        public void setMessagingProduct(String messagingProduct) {
            this.messagingProduct = messagingProduct;
        }

        public Metadata getMetadata() {
            return metadata;
        }

        public void setMetadata(Metadata metadata) {
            this.metadata = metadata;
        }

        public List<ItemContacts> getContacts() {
            return contacts;
        }

        public void setContacts(List<ItemContacts> contacts) {
            this.contacts = contacts;
        }
    }

    public static class Metadata {

        @SerializedName("display_phone_number")
        String displayPhoneNumber;
        @SerializedName("phone_number_id")
        String phoneNumberId;

        public String getDisplayPhoneNumber() {
            return displayPhoneNumber;
        }

        public void setDisplayPhoneNumber(String displayPhoneNumber) {
            this.displayPhoneNumber = displayPhoneNumber;
        }

        public String getPhoneNumberId() {
            return phoneNumberId;
        }

        public void setPhoneNumberId(String phoneNumberId) {
            this.phoneNumberId = phoneNumberId;
        }

    }

    public static class ItemContacts {

        @SerializedName("profile")
        Profile profile;
        @SerializedName("wa_id")
        String waId;
        @SerializedName("user_id")
        String userId;

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public String getWaId() {
            return waId;
        }

        public void setWaId(String waId) {
            this.waId = waId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

    }

    public class Profile {

        @SerializedName("name")
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ItemMessages {

        @SerializedName("from")
        String from;
        @SerializedName("from_user_id")
        String FromUserId;
        @SerializedName("id")
        String id;
        @SerializedName("timestamp")
        String timestamp;
        @SerializedName("text")
        Text text;
        @SerializedName("type")
        String type;
        @SerializedName("interactive")
        Interactive interactive;

        public ItemMessages(String from, String from_user_id, String id, String timestamp, Text text, String type) {
            this.from = from;
            this.FromUserId = from_user_id;
            this.id = id;
            this.timestamp = timestamp;
            this.text = text;
            this.type = type;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getFromUserId() {
            return FromUserId;
        }

        public void setFromUserId(String from_user_id) {
            this.FromUserId = from_user_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Text getText() {
            return text;
        }

        public void setText(Text text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Interactive getInteractive() {
            return interactive;
        }

        public void setInteractive(Interactive interactive) {
            this.interactive = interactive;
        }
    }

    public class Text {
        @SerializedName("body")
        String body;

        public Text(String body) {
            this.body = body;
        }

        public String getbody() {
            return body;
        }

    }

    public static class Interactive{

        @SerializedName("type")
        String type;
        @SerializedName("button_reply")
        ButtonReply buttonReply;

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public ButtonReply getButtonReply() {
            return buttonReply;
        }
        public void setButtonReply(ButtonReply buttonReply) {
            this.buttonReply = buttonReply;
        }

    }

    public class ButtonReply {

        @SerializedName("id")
        String id;
        @SerializedName("title")
        String title;
        
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

    }
}