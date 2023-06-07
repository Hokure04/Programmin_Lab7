package org.example.managers.customers;

import org.example.common.responses.BaseResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ResponseSender implements Runnable{
    private SelectionKey key;
    private BaseResponse response;

    public ResponseSender(SelectionKey key, BaseResponse response) {
        this.key = key;
        this.response = response;
    }

    public void run() {
        try {
            send(this.key, this.response);
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
    }
    public void send(SelectionKey key, BaseResponse response) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(65536);

        // Сериализация объекта в массив байтов
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();

        byte[] bytes = byteArrayOutputStream.toByteArray();
        buffer.put(bytes);

        buffer.flip(); // Переключение на режим записи

        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.write(buffer);
        key.interestOps(SelectionKey.OP_READ);
    }
}
