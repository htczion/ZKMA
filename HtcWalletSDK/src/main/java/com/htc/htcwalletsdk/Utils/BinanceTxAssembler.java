package com.htc.htcwalletsdk.Utils;

import com.htc.htcwalletsdk.bnbproto.*;
import com.google.protobuf.ByteString;

import java.io.IOException;


//TODO: getSender!
//TODO: getPubKey!
public class BinanceTxAssembler {

    public class AssemberType{
        public final static String NEWORDER = "order";
        public final static String SEND = "send";
    }

    private static Send.Input toProtoInput(BinanceEncodeUtils.InputOutput input) {
        byte[] InputAddr = BinanceEncodeUtils.decodeAddress(input.getAddress());
        Send.Input.Builder inputBuilder =
                Send.Input.newBuilder().setAddress(ByteString.copyFrom(InputAddr));

        for (BinanceEncodeUtils.Token coin : input.getCoins()) {
            Send.Token protCoin = Send.Token.newBuilder()
                    .setAmount(coin.getAmount())
                    .setDenom(coin.getDenom()).build();
            inputBuilder.addCoins(protCoin);
        }
        return inputBuilder.build();
    }

    private static Send.Output toProtoOutput(BinanceEncodeUtils.InputOutput output) {
        byte[] OutputAddr = BinanceEncodeUtils.decodeAddress(output.getAddress());
        Send.Output.Builder outputBuilder =
                Send.Output.newBuilder().setAddress(ByteString.copyFrom(OutputAddr));

        for (BinanceEncodeUtils.Token coin : output.getCoins()) {
            Send.Token protCoin = Send.Token.newBuilder()
                    .setAmount(coin.getAmount())
                    .setDenom(coin.getDenom())
                    .build();
            outputBuilder.addCoins(protCoin);
        }
        return outputBuilder.build();
    }

    public static byte[] encodeSendMessage(BinanceEncodeUtils.BNBJsonObj bnb) throws IOException {

        Send.Builder builder = Send.newBuilder();

        for (BinanceEncodeUtils.InputOutput input : bnb.getInputs()) {
            builder.addInputs(toProtoInput(input));
        }
        for (BinanceEncodeUtils.InputOutput output : bnb.getOutputs()) {
            builder.addOutputs(toProtoOutput(output));
        }

        Send send = builder.build();
        return BinanceEncodeUtils.aminoWrap(
                send.toByteArray(),
                bnb.getTypePrefix(),
                false);
    }

    public static byte[] encodeNewOrderMessage(BinanceEncodeUtils.BNBJsonObj bnb) throws IOException {
        NewOrder no = NewOrder.newBuilder()
                //.setSender(ByteString.copyFrom(sender_default))
                //.setSender(ByteString.copyFromUtf8(decodedAdr))
                .setSender(ByteString.copyFrom(BinanceEncodeUtils.decodeAddress(bnb.getSender())))
                .setId(bnb.getId())
                .setSymbol(bnb.getSymbol())
                .setOrdertype(bnb.getOrdertype())
                .setSide(bnb.getSide())
                .setPrice(bnb.getPrice())
                .setQuantity(bnb.getQuantity())
                .setTimeinforce(bnb.getTimeinforce())
                .build();

        return BinanceEncodeUtils.aminoWrap(
                no.toByteArray(),
                bnb.getTypePrefix(),
                false);
    }

    public static byte[] encodeSignature(byte[] signedBytes, BinanceEncodeUtils.BNBJsonObj bnb) throws IOException {
        byte[] pubKey = JsonParser.hexToBytes(bnb.pubKeyHex);
        byte[] pubKeyPrefix = BinanceEncodeUtils.BNBMessageType.PubKey.getTypePrefixBytes();
        byte[] pubKeyForSign = new byte[pubKey.length + pubKeyPrefix.length + 1];
        System.arraycopy(pubKeyPrefix, 0, pubKeyForSign, 0, pubKeyPrefix.length);
        pubKeyForSign[pubKeyPrefix.length] = (byte) 33;
        System.arraycopy(pubKey, 0, pubKeyForSign, pubKeyPrefix.length + 1, pubKey.length);

        StdSignature stdSignature = StdSignature.newBuilder()
                .setPubKey(ByteString.copyFrom(pubKeyForSign))
                .setSignature(ByteString.copyFrom(signedBytes))
                .setAccountNumber(bnb.getAccountNum())
                .setSequence(bnb.getSequence())
                .build();

        return BinanceEncodeUtils.aminoWrap(
                stdSignature.toByteArray(),
                BinanceEncodeUtils.BNBMessageType.StdSignature.getTypePrefixBytes(),
                false);
    }

    public static byte[] encodeStdTx(byte[] signedByte, BinanceEncodeUtils.BNBJsonObj bnb) throws IOException {
        byte[] msg = new byte[2*1024];
        if(bnb.getTypeStr().equals(AssemberType.NEWORDER))
            msg = encodeNewOrderMessage(bnb);
        if(bnb.getTypeStr().equals(AssemberType.SEND))
            msg = encodeSendMessage(bnb);
        byte[] signature = encodeSignature(signedByte, bnb);

        StdTx stdTx = StdTx.newBuilder()
                .addMsgs(ByteString.copyFrom(msg))
                .addSignatures(ByteString.copyFrom(signature))
                .setMemo(bnb.getMemo())
                // .setData(ByteString.copyFrom(bnb.getData())) // Remove for equal with SignerTests.cpp test output format
                .setSource(bnb.getSource())
                .build();

        return BinanceEncodeUtils.aminoWrap(
                stdTx.toByteArray(),
                BinanceEncodeUtils.BNBMessageType.StdTx.getTypePrefixBytes(),
                true);
        }
}


