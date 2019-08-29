package com.htc.htcwalletsdk.Utils;

import android.util.Log;

import com.google.protobuf.CodedOutputStream;
import com.htc.htcwalletsdk.bnbproto.Send;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BinanceEncodeUtils {
    public enum BNBMessageType {
        Send("2A2C87FA"),
        NewOrder("CE6DC043"),
        CancelOrder("166E681B"),
        TokenFreeze("E774B32D"),
        TokenUnfreeze("6515FF0D"),
        StdSignature(null),
        PubKey("EB5AE987"),
        StdTx("F0625DEE");
        /*Vote("A1CADD36"),
        Issue("17EFAB80"),
        Burn("7ED2D2A0"),
        Mint("467E0829"),
        SubmitProposal("B42D614E"),
        Deposit("A18A56E5"),
        CreateValidator("DB6A19FD"),
        RemoveValidator("C1AFE85F"),
        Listing("B41DE13F"),
        TimeLock("07921531"),
        TimeUnlock("C4050C6C"),
        TimeRelock("504711DA");*/

        private byte[] typePrefixBytes;

        BNBMessageType(String typePrefix) {
            if (typePrefix == null) {
                this.typePrefixBytes = new byte[0];
            } else
                this.typePrefixBytes = JsonParser.hexToBytes(typePrefix);
        }

        public byte[] getTypePrefixBytes() {
            return typePrefixBytes;
        }

    }

    public static byte[] aminoWrap(byte[] raw, byte[] typePrefix, boolean isPrefixLength) throws IOException {
        int totalLen = raw.length + typePrefix.length;
        if (isPrefixLength)
            totalLen += CodedOutputStream.computeUInt64SizeNoTag(totalLen);

        byte[] msg = new byte[totalLen];
        CodedOutputStream cos = CodedOutputStream.newInstance(msg);
        if (isPrefixLength)
            cos.writeUInt64NoTag(raw.length + typePrefix.length);
        cos.write(typePrefix, 0, typePrefix.length);
        cos.write(raw, 0, raw.length);
        cos.flush();
        return msg;
    }

    public static byte[] toByteArray(int[] ia) {
        byte[] ba = new byte[ia.length];
        for (int i = 0; i < ia.length; i++) {
            ba[i] = (byte) ia[i];
        }
        return ba;
    }

    public static class Bech32Data {
        final String hrp;
        final byte[] data;

        private Bech32Data(final String hrp, final byte[] data) {
            this.hrp = hrp;
            this.data = data;
        }

        public String getHrp() {
            return hrp;
        }

        public byte[] getData() {
            return data;
        }
    }

    private static int polymod(final byte[] values) {
        int c = 1;
        for (byte v_i : values) {
            int c0 = (c >>> 25) & 0xff;
            c = ((c & 0x1ffffff) << 5) ^ (v_i & 0xff);
            if ((c0 & 1) != 0) c ^= 0x3b6a57b2;
            if ((c0 & 2) != 0) c ^= 0x26508e6d;
            if ((c0 & 4) != 0) c ^= 0x1ea119fa;
            if ((c0 & 8) != 0) c ^= 0x3d4233dd;
            if ((c0 & 16) != 0) c ^= 0x2a1462b3;
        }
        return c;
    }

    private static byte[] expandHrp(final String hrp) {
        int hrpLength = hrp.length();
        byte ret[] = new byte[hrpLength * 2 + 1];
        for (int i = 0; i < hrpLength; ++i) {
            int c = hrp.charAt(i) & 0x7f; // Limit to standard 7-bit ASCII
            ret[i] = (byte) ((c >>> 5) & 0x07);
            ret[i + hrpLength + 1] = (byte) (c & 0x1f);
        }
        ret[hrpLength] = 0;
        return ret;
    }

    private static boolean verifyChecksum(final String hrp, final byte[] values) {
        byte[] hrpExpanded = expandHrp(hrp);
        byte[] combined = new byte[hrpExpanded.length + values.length];
        System.arraycopy(hrpExpanded, 0, combined, 0, hrpExpanded.length);
        System.arraycopy(values, 0, combined, hrpExpanded.length, values.length);
        return polymod(combined) == 1;
    }

    public static Bech32Data Bech32Decode(final String str) {
        final byte[] CHARSET_REV = {
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                15, -1, 10, 17, 21, 20, 26, 30, 7, 5, -1, -1, -1, -1, -1, -1,
                -1, 29, -1, 24, 13, 25, 9, 8, 23, -1, 18, 22, 31, 27, 19, -1,
                1, 0, 3, 16, 11, 28, 12, 14, 6, 4, 2, -1, -1, -1, -1, -1,
                -1, 29, -1, 24, 13, 25, 9, 8, 23, -1, 18, 22, 31, 27, 19, -1,
                1, 0, 3, 16, 11, 28, 12, 14, 6, 4, 2, -1, -1, -1, -1, -1
        };

        boolean lower = false, upper = false;
        if (str.length() < 8)
            Log.e("Address Error", "Input too short: " + str.length());
        if (str.length() > 90)
            Log.e("Address Error", "Input too long: " + str.length());
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c < 33 || c > 126)
                Log.e("Address Error", "InvalidCharacter");
            if (c >= 'a' && c <= 'z') {
                if (upper)
                    Log.e("Address Error", "InvalidCharacter");
                lower = true;
            }
            if (c >= 'A' && c <= 'Z') {
                if (lower)
                    Log.e("Address Error", "InvalidCharacter");
                upper = true;
            }
        }
        final int pos = str.lastIndexOf('1');
        if (pos < 1) Log.e("Address Error", "Missing human-readable part");
        final int dataPartLength = str.length() - 1 - pos;
        if (dataPartLength < 6)
            Log.e("Address Error", "Data part too short: " + dataPartLength);
        byte[] values = new byte[dataPartLength];
        for (int i = 0; i < dataPartLength; ++i) {
            char c = str.charAt(i + pos + 1);
            if (CHARSET_REV[c] == -1) Log.e("Address Error", "InvalidCharacter");
            values[i] = CHARSET_REV[c];
        }
        String hrp = str.substring(0, pos).toLowerCase(Locale.ROOT);
        if (!verifyChecksum(hrp, values)) Log.e("Address Error", "InvalidCheckSum");
        return new Bech32Data(hrp, Arrays.copyOfRange(values, 0, values.length - 6));
    }

    public static byte[] convertBits(final byte[] in, final int inStart, final int inLen,
                                     final int fromBits, final int toBits, final boolean pad){
        int acc = 0;
        int bits = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream(64);
        final int maxv = (1 << toBits) - 1;
        final int max_acc = (1 << (fromBits + toBits - 1)) - 1;
        for (int i = 0; i < inLen; i++) {
            int value = in[i + inStart] & 0xff;
            if ((value >>> fromBits) != 0) {
                Log.e("Address Error","Input value " + value + " exceeds" + fromBits + "bit size");
            }
            acc = ((acc << fromBits) | value) & max_acc;
            bits += fromBits;
            while (bits >= toBits) {
                bits -= toBits;
                out.write((acc >>> bits) & maxv);
            }
        }
        if (pad) {
            if (bits > 0) out.write((acc << (toBits - bits)) & maxv);
        } else if (bits >= fromBits || ((acc << (toBits - bits)) & maxv) != 0) {
            Log.e("Address Error","Could not convert bits, invalid padding");
        }
        return out.toByteArray();
    }

    public static byte[] decodeAddress(String address) {
        byte[] dec = Bech32Decode(address).getData();
        return convertBits(dec, 0, dec.length, 5, 8, false);
    }

    public static class Token {
        private String denom;
        private Long amount;

        public static Token of(Token source) {
            Token token = new Token();
            token.setDenom(source.getDenom());
            token.setAmount(source.getAmount());
            return token;
        }

        public static Token of(Send.Token sendToken){
            Token token = new Token();
            token.setDenom(sendToken.getDenom());
            token.setAmount(sendToken.getAmount());
            return token;
        }

        public String getDenom() {
            return denom;
        }

        public void setDenom(String denom) {
            this.denom = denom;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }
    }
    public static class InputOutput {
        private String address;
        private List<Token> coins;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<Token> getCoins() {
            return coins;
        }

        public void setCoins(List<Token> coins) {
            this.coins = coins;
        }
    }

    private class JsonDataKey_signMsgBNB {
        //standSign
        public static final String PATH = "path";
        public static final String TYPE = "type";
        public static final String FEE = "fee";
        public static final String BNB = "bnb";
        public static final String SIGN_BYTES = "signbytes";

        //msgs
        public static final String MSGS = "msgs";
        public static final String MEMO = "memo";
        public static final String ACCOUNT_NUM = "account_number";
        public static final String SEQUENCE = "sequence";
        public static final String DATA = "data";
        public static final String SOURCE = "source";

        //Neworder
        public static final String SENDER = "sender";
        public static final String ID = "id";
        public static final String SYMBOL = "symbol";
        public static final String ORDER_TYPE = "ordertype";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
        public static final String SIDE = "side";
        public static final String TIME_INFORCE = "timeinforce";
        //Neworder-Extra
        public static final String EXTRA = "extra";
        public static final String DISPLAYNAME = "displayname";
        public static final String DST_ICON = "dst_icon";

        //send
        public static final String INPUTS = "inputs";
        public static final String OUTPUTS = "outputs";
        public static final String ADDRESS = "address";
        public static final String COINS = "coins";
        public static final String AMOUNT = "amount";
        public static final String DEMON = "denom";
    }

    public static class BNBJsonObj{
        //standSign
        private  String strJson;
        Map<String, String> jsonMap;
        private  String path;
        public   String pubKeyHex;
        private  String type;
        private  String fee;
        private  String extra;
        private  String strSign;

        //msgs
        Map<String, String> bnbMap;
        private  String msgs;
        private  String memo;
        private  long accountNum;
        private  long sequence;
        private  String data;
        private  long source;

        Map<String, String> extraMap;
        private  String displayname;
        private  String dst_icon;

        //Neworder
        Map<String, String> msgMap;
        private  String sender;
        private  String id;
        private  String symbol;
        private  long ordertype;
        private  long price;
        private  long quantity;
        private  long side;
        private  long timeinforce;

        //Send
        private List<InputOutput> inputs;
        private List<InputOutput> outputs;

        public BNBJsonObj(String strJson) throws JSONException {
            this.strJson = strJson;

            jsonMap = JsonParser.JsonStrToMap(this.strJson);
            path = jsonMap.get(JsonDataKey_signMsgBNB.PATH);
            type = jsonMap.get(JsonDataKey_signMsgBNB.TYPE);
            fee = jsonMap.get(JsonDataKey_signMsgBNB.FEE);
            extra = jsonMap.get(JsonDataKey_signMsgBNB.EXTRA);
            strSign = jsonMap.get(JsonDataKey_signMsgBNB.BNB);
            strSign.replaceAll("\\s+","");


            bnbMap = JsonParser.JsonStrToMap(strSign);
            msgs = bnbMap.get(JsonDataKey_signMsgBNB.MSGS);
            msgs = msgs.substring(1, msgs.length()-1);
            memo = bnbMap.get(JsonDataKey_signMsgBNB.MEMO);
            accountNum = Long.parseLong(bnbMap.get(JsonDataKey_signMsgBNB.ACCOUNT_NUM));
            sequence = Long.parseLong(bnbMap.get(JsonDataKey_signMsgBNB.SEQUENCE));
            data = bnbMap.get(JsonDataKey_signMsgBNB.DATA);
            source = Long.parseLong(bnbMap.get(JsonDataKey_signMsgBNB.SOURCE));

            msgMap = JsonParser.JsonStrToMap(msgs);
            if(type.equals("order")){
                sender = msgMap.get(JsonDataKey_signMsgBNB.SENDER);
                id = msgMap.get(JsonDataKey_signMsgBNB.ID);
                symbol = msgMap.get(JsonDataKey_signMsgBNB.SYMBOL);
                ordertype = Long.parseLong(msgMap.get(JsonDataKey_signMsgBNB.ORDER_TYPE));
                price = Long.parseLong(msgMap.get(JsonDataKey_signMsgBNB.PRICE));
                quantity = Long.parseLong(msgMap.get(JsonDataKey_signMsgBNB.QUANTITY));
                side = Long.parseLong(msgMap.get(JsonDataKey_signMsgBNB.SIDE));
                timeinforce = Long.parseLong(msgMap.get(JsonDataKey_signMsgBNB.TIME_INFORCE));

                extraMap = JsonParser.JsonStrToMap(extra);
                if(extraMap!= null) {
                    displayname = extraMap.get(JsonDataKey_signMsgBNB.DISPLAYNAME);
                    dst_icon = extraMap.get(JsonDataKey_signMsgBNB.DST_ICON);
                }

            }
            if(type.equals("send")){
                inputs = new ArrayList<InputOutput>();
                outputs = new ArrayList<InputOutput>();
                JSONObject jsonObj= new JSONObject(msgs);
                JSONArray inputsJ = jsonObj.getJSONArray(JsonDataKey_signMsgBNB.INPUTS);
                JSONArray outputsJ = jsonObj.getJSONArray(JsonDataKey_signMsgBNB.OUTPUTS);

                for(int i = 0; i < inputsJ.length(); i++){
                    JSONObject tempJObj = inputsJ.getJSONObject(i);
                    InputOutput io = new InputOutput();
                    io.setAddress((String) tempJObj.get(JsonDataKey_signMsgBNB.ADDRESS));
                    JSONArray inputCoins = tempJObj.getJSONArray(JsonDataKey_signMsgBNB.COINS);

                    List<Token> ic = new ArrayList<Token>();
                    for(int j = 0; j < inputCoins.length(); j++){
                        JSONObject tempJObj2 = inputCoins.getJSONObject(j);
                        Token t = new Token();
                        String x = tempJObj2.get(JsonDataKey_signMsgBNB.AMOUNT).toString();
                        t.setAmount(Long.parseLong(x));
                        t.setDenom((String) tempJObj2.get(JsonDataKey_signMsgBNB.DEMON));
                        ic.add(t);
                    }
                    io.setCoins(ic);
                    this.inputs.add(io);
                }


                for(int i = 0; i < outputsJ.length(); i++){
                    JSONObject tempJObj = outputsJ.getJSONObject(i);
                    InputOutput io = new InputOutput();
                    io.setAddress((String) tempJObj.get(JsonDataKey_signMsgBNB.ADDRESS));
                    JSONArray outputCoins = tempJObj.getJSONArray(JsonDataKey_signMsgBNB.COINS);

                    List<Token> oc = new ArrayList<Token>();
                    for(int j = 0; j < outputCoins.length(); j++){
                        JSONObject tempJObj2 = outputCoins.getJSONObject(j);
                        Token t = new Token();
                        String x = tempJObj2.get(JsonDataKey_signMsgBNB.AMOUNT).toString();
                        t.setAmount(Long.parseLong(x));
                        t.setDenom((String) tempJObj2.get(JsonDataKey_signMsgBNB.DEMON));
                        oc.add(t);
                    }
                    io.setCoins(oc);
                    this.outputs.add(io);
                }

                extraMap = JsonParser.JsonStrToMap(extra);
                if(extraMap!= null) {
                    displayname = extraMap.get(JsonDataKey_signMsgBNB.DISPLAYNAME);
                    dst_icon = extraMap.get(JsonDataKey_signMsgBNB.DST_ICON);
                }
            }

        }

        public String getPath() { return path; }

        public String getSignHex(){
            return JsonParser.stringToHex(strSign);
        }

        public String getTypeStr(){
            return type;
        }

        public String getSignJsonStr(){
            String signJsonStr;
            if(this.extra != null && this.displayname != null && this.dst_icon != null) {
                signJsonStr = "{\n" +
                    "  \"path\": \""+ this.path + "\",\n" +
                    "  \"fee\": \""+ this.fee +"\",\n" +
                    "  \"type\": \""+ this.type +"\",\n" +
                    "  \"signbytes\": \""+ getSignHex() +"\",\n" +
                    "  \"extra\": "+"{\n"+
                        "  \"displayname\": \""+ this.displayname +"\",\n" +
                        "  \"dst_icon\": \""+ this.dst_icon + "\"\n" +
                        "  }\n"+
                    "}";
            } else {
                signJsonStr = "{\n" +
                        "  \"path\": \""+ this.path + "\",\n" +
                        "  \"fee\": \""+ this.fee +"\",\n" +
                        "  \"type\": \""+ this.type +"\",\n" +
                        "  \"signbytes\": \""+ getSignHex() +"\"\n" +
                        "}";
            }
            return signJsonStr;
        }

        public byte[] getTypePrefix(){
            switch (type){
                case BinanceTxAssembler.AssemberType.SEND:
                    return BNBMessageType.Send.getTypePrefixBytes();
                case BinanceTxAssembler.AssemberType.NEWORDER:
                    return BNBMessageType.NewOrder.getTypePrefixBytes();
                case "Cancel":
                    return BNBMessageType.CancelOrder.getTypePrefixBytes();
                case "Freeze":
                    return BNBMessageType.TokenFreeze.getTypePrefixBytes();
                case "UnFreeze":
                    return BNBMessageType.TokenUnfreeze.getTypePrefixBytes();
            }
            return null;
        }

        public byte[] getMsgs(){
            return JsonParser.hexToBytes(JsonParser.stringToHex(msgs));

        }

        public String getMemo(){
            return memo;
        }

        public byte[] getData(){
            return JsonParser.hexToBytes(JsonParser.stringToHex(data));
        }

        public long getAccountNum(){
            return accountNum;
        }

        public long getSequence(){
            return sequence;
        }

        public long getSource(){
            return source;
        }

        public String getSender() {
            return sender;
        }

        public String getId() {
            return id;
        }

        public String getSymbol() {
            return symbol;
        }

        public long getOrdertype() {
            return ordertype;
        }

        public long getPrice() {
            return price;
        }

        public long getQuantity() {
            return quantity;
        }

        public long getSide() {
            return side;
        }

        public long getTimeinforce() {
            return timeinforce;
        }

        public List<InputOutput> getInputs() {
            return inputs;
        }

        public List<InputOutput> getOutputs() {
            return outputs;
        }

    }
}
