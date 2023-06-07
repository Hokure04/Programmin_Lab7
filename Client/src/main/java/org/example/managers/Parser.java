package org.example.managers;

import org.example.common.data.User;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.responses.BaseResponse;
import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.exceptions.UnknownCommandException;
import org.example.common.responses.StartRequest;
import org.example.exceptions.UnauthorisedUserException;
import org.example.managers.customer.CommandSender;
import org.example.managers.customer.ResponseReceiver;
import org.example.util.PrepareRequest;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.NoSuchElementException;

/**
 * Класс для обработки вводимых команд
 */
public class Parser {
    private QueriesHandler queriesHandler;
    private CommandSender commandSender;
    private ResponseReceiver responseReceiver;
    private StartRequest startRequest;
    private Selector selector;
    private SelectionKey selectionKey;
    private InetSocketAddress socketAddress;
    private SocketChannel socketChannel;
    private ResponesHandler responesHandler;
    private User user = null;

    /**
     *
     * @param queriesHandler объект queriesHandler
     //* @param fileHandler объект fileHandler
     //* @param collectionHandler объект collectionHandler
     //* @param commandHandler объект commandHandler
     */
    public Parser(QueriesHandler queriesHandler, CommandSender commandSender, ResponseReceiver responseReceiver, InetSocketAddress socketAddress, SocketChannel socketChannel) {
        this.queriesHandler = queriesHandler;
        this.commandSender = commandSender;
        this.responseReceiver = responseReceiver;
        this.socketAddress = socketAddress;
        this.socketChannel = socketChannel;
        this.responesHandler = new ResponesHandler(this.queriesHandler, this);
        //this.selector = selector;
        //this.selectionKey = selectionKey;
    }
    /**
     * Запуск парсинга
     */
    public void Active() throws IOException {
        //this.selector.select();
//        while (true) {
//            if (this.selectionKey.isReadable()) {
//                System.out.println(1234131);
//                break;
//            }
//        }

        //Set<SelectionKey> selectionKeys = selector.selectedKeys();
        //Iterator<SelectionKey> iter = selectionKeys.iterator();
        //this.selectionKey.interestOps(SelectionKey.OP_READ);
        //SocketChannel channel = (SocketChannel) this.selectionKey.channel();
        //channel.register(selector, SelectionKey.OP_READ);
        //channel.register(selector, SelectionKey.OP_READ, channel);
//        if (this.selectionKey.isValid()) {
//            System.out.println(this.selectionKey.isConnectable());
//            System.out.println(this.selectionKey.isWritable());
//            System.out.println(this.selectionKey.isReadable());
//            System.out.println(this.selectionKey.isAcceptable());
//        }
        try {
            this.startRequest = (StartRequest) this.responseReceiver.receive(this.selectionKey);
        } catch (IOException e) {
            System.out.printf(e.getMessage());
        } catch (ClassNotFoundException e) {
            this.queriesHandler.write("Сервер выключен :(");
        }

        PrepareRequest preparer = new PrepareRequest(this.queriesHandler, this.startRequest);
        this.queriesHandler.write("Для получения справки по командам введите help");
        while (true) {
            String line;
            try {
                line = this.queriesHandler.query("Введите команду: ") + " ";
            } catch (NoSuchElementException e) {
                if (this.queriesHandler.getType() == QueriesHandlerType.FILE) {
                    if (this.queriesHandler.getQueue().size() > 1) {
                        this.queriesHandler.setOldScanner();
                    } else {
                        this.queriesHandler.setConsoleType();
                    }
                    continue;
                } else {
                    System.exit(0);
                    continue;
                }
            }
            String command = line.split(" ", 2)[0];
            String args = line.split(" ", 2)[1].trim();
            try {
                //this.commandHandler.runCommand(command, args);
                //preparer = new PrepareRequest(command, args, this.queriesHandler, startRequest);
                if (command.equals("execute_script")) {
                    //Validator.validateCountArgs(args.split(" +", 2), 0);
                    this.queriesHandler.setFileType(args.split(" +", 2)[0]);
                    continue;
                }
                BaseRequest commandRequest = preparer.startPreparing(command, args, this.user);
                this.commandSender.send(commandRequest);

                BaseResponse response = this.responseReceiver.receive(this.selectionKey);

                this.responesHandler.handle(command, response);
                /*if (response.getStatus() == 1) {
                    if (response.getValue() != null) {
                        this.queriesHandler.write(response.getValue());
                    }
                    if (this.queriesHandler.getType() == QueriesHandlerType.FILE & (!command.equals("execute_script") | this.queriesHandler.getQueue().size() != 1)) {
                        this.queriesHandler.write(command + ": выполнено");
                    }
                } else if (response.getStatus() == 0) {
                    this.queriesHandler.write(response.getError());
                } else if (response.getStatus() == 3) {
                    System.exit(1);
                }*/

//                if (this.queriesHandler.getType() == QueriesHandlerType.FILE & (!command.equals("execute_script") | this.queriesHandler.getQueue().size() != 1)) {
//                    this.queriesHandler.write(command + ": выполнено");
//                } //else {
//                    //this.queriesHandler.write("Команда успешно выполнена");
//                //}
//                //execute_script src/main/java/org/example/files/file.txt
            } catch (NoSuchElementException e) {
                //System.out.println(e.getMessage());
                System.exit(1);
            } catch (UnknownCommandException | FileNotFoundException e) {
                //System.out.println(e.getMessage());
                if (this.queriesHandler.getType() == QueriesHandlerType.CONSOLE) {
                    this.queriesHandler.write(e.getLocalizedMessage());
                } else if (this.queriesHandler.getType() == QueriesHandlerType.FILE) {
                    this.queriesHandler.write("Скрипт некорректно записан.");
                    this.queriesHandler.write("Ошибка: " + e.getMessage() + "\nКоманда: " + line);
                    if (this.queriesHandler.getQueue().size() > 1) {
                        this.queriesHandler.setOldScanner();
                    } else {
                        this.queriesHandler.setConsoleType();
                    }
                }
            } catch (TroubleObjectCreationException | IllegalArgumentException e) {
                //System.out.println(e.getMessage());
                if (this.queriesHandler.getType() == QueriesHandlerType.CONSOLE) {
                    this.queriesHandler.write(e.getLocalizedMessage());
                    while (true) {
                        try {
                            //this.commandHandler.runCommand(command, args);
                            BaseRequest commandRequest = preparer.startPreparing(command, args, this.user);
                            this.commandSender.send(commandRequest);

                            BaseResponse response = this.responseReceiver.receive(this.selectionKey);

                            this.responesHandler.handle(command, response);
                            /*Response response = (Response) this.responseReceiver.receive(this.selectionKey);
                            if (response.getStatus() == 1) {
                                if (response.getValue() != null) {
                                    this.queriesHandler.write(response.getValue());
                                }
                                if (this.queriesHandler.getType() == QueriesHandlerType.FILE & (!command.equals("execute_script") | this.queriesHandler.getQueue().size() != 1)) {
                                    this.queriesHandler.write(command + ": выполнено");
                                }
                            } else if (response.getStatus() == 0) {
                                this.queriesHandler.write(response.getError());
                            } else if (response.getStatus() == 3) {
                                System.exit(1);
                            }*/
                            //this.queriesHandler.write("Команда успешно выполнена");
                            break;
                        } catch (NoSuchElementException z) {
                            System.exit(1);
                        } catch (Exception z) {
                            this.queriesHandler.write(z.getMessage());
                        }
                    }
                } else if (this.queriesHandler.getType() == QueriesHandlerType.FILE) {
                    this.queriesHandler.write("Скрипт некорректно записан.");
                    this.queriesHandler.write("Ошибка: " + e.getMessage() + "\nКоманда: " + line);
                    if (this.queriesHandler.getQueue().size() > 1) {
                        this.queriesHandler.setOldScanner();
                    } else {
                        this.queriesHandler.setConsoleType();
                    }
                }
            } catch (EOFException | SocketException e) {
                //System.out.println(e.getMessage());
//                ReConnect reConnect = new ReConnect(this.queriesHandler, this.commandSender, this.responseReceiver, socketAddress, socketChannel);
//                reConnect.reConnect();
//                break;
                throw new EOFException();
            } catch (UnauthorisedUserException e) {
                this.queriesHandler.write(e.getLocalizedMessage());
            } catch (Exception e) {
                //System.out.println(e.getMessage());
                if (this.queriesHandler.getType() == QueriesHandlerType.FILE) {
                    this.queriesHandler.write("Скрипт некорректно записан.");
                    this.queriesHandler.write("Ошибка: " + e.getMessage() + "\nКоманда: " + line);
                    if (this.queriesHandler.getQueue().size() > 1) {
                        this.queriesHandler.setOldScanner();
                    } else {
                        this.queriesHandler.setConsoleType();
                    }
                }
            }
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
