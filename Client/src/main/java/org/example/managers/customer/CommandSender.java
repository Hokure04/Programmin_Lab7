package org.example.managers.customer;

import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.responses.BaseResponse;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class CommandSender {
    //private ObjectOutputStream objectWriter;
    private SocketChannel objectWriter;

    public CommandSender(SocketChannel objectWriter) {
        this.objectWriter = objectWriter;
    }

    public void send(BaseRequest request) throws IOException {

//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//        objectOutputStream.writeObject(request);
//        objectOutputStream.flush();
//        byteArrayOutputStream.toByteArray();

        //this.objectWriter.writeObject(request);
        //this.objectWriter.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//        this.objectWriter.write(request.)



        ByteBuffer buffer = ByteBuffer.allocate(65536);

        // Сериализация объекта в массив байтов
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        objectOutputStream.flush();

        byte[] bytes = byteArrayOutputStream.toByteArray();
        buffer.put(bytes);

        buffer.flip(); // Переключение на режим записи
        this.objectWriter.write(buffer);
        //selectionKey.interestOps(SelectionKey.OP_READ);




//        objectOutputStream.close();
//        byteArrayOutputStream.close();

    }
}
