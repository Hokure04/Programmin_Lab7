package org.example.managers.customers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.common.data.Vehicle;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.responses.BaseResponse;
import org.example.exceptions.MissingCommandException;
import org.example.managers.CollectionHandler;
import org.example.managers.CommandHandler;
import org.example.managers.RequestHandler;
import org.example.managers.UserHandler;
import org.example.managers.database.DatabaseUserHandler;
import org.example.util.PrepareStartRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;

public class CommandReceiver implements Runnable {
    private static final Logger log = LogManager.getLogger("logfile.txt");

    private SelectionKey selectionKey;
    //private CollectionHandler<Vehicle> collectionHandler;
    //private RequestHandler requestHandler;
    private CommandHandler commandHandler;
    private DatabaseUserHandler databaseUserHandler;
    private UserHandler userHandler;
    private Executor executor;
    public CommandReceiver(CommandHandler commandHandler, DatabaseUserHandler databaseUserHandler, UserHandler userHandler, Executor executor, SelectionKey selectionKey) {
        //this.collectionHandler = collectionHandler;
        this.selectionKey = selectionKey;
        //this.requestHandler = requestHandler;
        this.executor = executor;
        this.commandHandler = commandHandler;
        this.databaseUserHandler = databaseUserHandler;
        this.userHandler = userHandler;
    }

    public void run() {
        try {
            receive(this.selectionKey);
        } catch (IOException ignore) {
        } catch (ClassNotFoundException e) {
            log.error("С клиента пришло что-то не то, либо в common не найден класс запроса");
        }
    }

    public void receive(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        ByteBuffer buffer = ByteBuffer.allocate(65536);
        int readBytes = socketChannel.read(buffer);
        if (readBytes == -1) {
            key.cancel();
            return;
            //return null;
        }
        byte[] data = buffer.array();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        BaseRequest request = (BaseRequest) objectInputStream.readObject();
        //if (receivedObject.getObject() != null) {
        //    this.collectionHandler.setObjectId(receivedObject.getObject());
        //}
        //key.interestOps(SelectionKey.OP_WRITE);
        new Thread(new RequestHandler(this.commandHandler, this.databaseUserHandler, this.userHandler, request, this.executor, this.selectionKey)).start();

        //return receivedObject;
    }
}
