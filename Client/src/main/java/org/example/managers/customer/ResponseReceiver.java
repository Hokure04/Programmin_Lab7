package org.example.managers.customer;

import org.example.common.responses.BaseResponse;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ResponseReceiver {
    //private ObjectInputStream objectReader;
    private SocketChannel objectReader;

    public ResponseReceiver(SocketChannel objectReader) {
        this.objectReader = objectReader;
    }

    public BaseResponse receive(SelectionKey selectionKey) throws IOException, ClassNotFoundException {

//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.objectReader.re);
//        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//
//        //BaseResponse response = (BaseResponse) this.objectReader.readObject();
//        BaseResponse response = (BaseResponse) objectInputStream.readObject();
//        return response;

        ByteBuffer buffer = ByteBuffer.allocate(65536);
        int readBytes = this.objectReader.read(buffer);
        if (readBytes == -1) {
            throw new EOFException();
        }
        //int readBytes = selectionKey.channel().read(buffer);
        /*for (byte i : buffer.array()) {
            if (i != 0) {
                System.out.println(i);
            }
        }*/
//        if (readBytes == -1) {
//            this.objectReader.close();
//        }
        byte[] data = buffer.array();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        BaseResponse receivedObject = (BaseResponse) objectInputStream.readObject();
        //selectionKey.interestOps(SelectionKey.OP_WRITE);
        return receivedObject;

        //Object receivedObject = deserialize(data);
        // Обработка

//            sendResponse(channel, response);
//
//            channel.register(selector, SelectionKey.OP_WRITE, serialize(response));
    }
}
