package org.example.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.common.exceptions.NoAccessToTheFileException;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.responses.BaseResponse;
import org.example.exceptions.MissingCommandException;
import org.example.managers.customers.ResponseSender;
import org.example.managers.customers.CommandReceiver;
import org.example.managers.database.DatabaseManager;
import org.example.util.PrepareStartRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConnectManager {
    private static final Logger log = LogManager.getLogger("logfile.txt");
    private QueriesHandler queriesHandler;
    private DatabaseManager databaseManager;
    private CollectionHandler collectionHandler;
    private CommandHandler commandHandler;
    private UserHandler userHandler;
    private RequestHandler requestHandler;
    //private CommandReceiver commandReceiver;
    //private ResponseSender responseSender;
    ServerSocketChannel serverSocketChannel;
    private Selector selector;
    //private Queue<SocketChannel> queue;

    private Executor executor;

    public ConnectManager(String[] args, DatabaseManager databaseManager, CollectionHandler collectionHandler, UserHandler userHandler, CommandHandler commandHandler) {
        try {
            this.queriesHandler = new QueriesHandler();
            int port = enterPort();
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            this.selector = Selector.open();
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
            this.databaseManager = databaseManager;
            this.databaseManager.connectDB();
            this.collectionHandler = collectionHandler;
            this.commandHandler = commandHandler;
            this.userHandler = userHandler;
            this.executor = Executors.newCachedThreadPool();
            //start(args);
            //saver.waiting();
        } catch (FileNotFoundException | NoAccessToTheFileException e) {
            this.queriesHandler.write(e.getLocalizedMessage());
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void activeCatching() {
    //public void run() {
        while (true) {
            try {
                this.selector.select();
            } catch (IOException e) {
                log.error("Ошибка селектора");
            }
            for (SelectionKey key : selector.selectedKeys()) {
                //System.out.println(123);
                try {
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        if (socketChannel != null) {
                            socketChannel.configureBlocking(false);
                            SelectionKey selectionKey = socketChannel.register(this.selector, SelectionKey.OP_READ, socketChannel);
                            log.info("Клиент подключен");
                            log.info("Отправка первого запроса");
                            //executor.execute(new ResponseSender(selectionKey, PrepareStartRequest.startPreparing(this.commandHandler)));
                            this.executor.execute(new ResponseSender(selectionKey, PrepareStartRequest.startPreparing(this.commandHandler)));
                            //this.responseSender.send(selectionKey, PrepareStartRequest.startPreparing(this.commandHandler));
                        }
                    } else if (key.isReadable()) {
                        this.executor.execute(new CommandReceiver(this.commandHandler, this.databaseManager.getDatabaseUserHandler(), this.userHandler, this.executor, key));
                    }
                } catch (IOException e) {
                    log.error("Проблемы при обмене данными между клиентом и сервером");
                } catch (CancelledKeyException ignore) {
                }/*catch (ClassNotFoundException e) {
                    log.error("С клиента пришло что-то не то, либо в common не найден класс запроса");
                }*/
            }
        }
    }

    public int enterPort() {
        while (true) {
            try {
                int port = Integer.parseInt(this.queriesHandler.query("Введите порт сервера(целое число от 1024 до 65535)"));
                if (port > 65535 | port < 1024) {
                    this.queriesHandler.write("Ошибка: число не входит в указанные границы");
                    continue;
                }
                return port;
            } catch (NumberFormatException e) {
                this.queriesHandler.write("Ошибка: введенное значение не является целым числом");
            }
        }
    }

}
