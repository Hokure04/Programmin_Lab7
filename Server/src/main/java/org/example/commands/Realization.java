package org.example.commands;

import org.example.common.data.FuelType;
import org.example.common.data.Vehicle;
import org.example.common.requests.BaseRequest;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.RegisterRequest;
import org.example.common.responses.ResponseRegistration;
import org.example.common.responses.ResponseResult;
import org.example.common.responses.TypesResponse;
import org.example.managers.CollectionHandler;
import org.example.managers.CommandHandler;
import org.example.managers.UserHandler;
import org.example.managers.database.DatabaseManager;
import org.example.util.Encryptor;

import java.util.*;

/**
 * Класс реализаций команд(receiver)
 */
public class Realization {

    private CollectionHandler<Vehicle> collectionHandler;
    private UserHandler userHandler;
    //private QueriesHandler queriesHandler;
    private CommandHandler commandHandler;
    private DatabaseManager databaseManager;
    //private FileHandler fileHandler;
    //private VehicleBuilder elementBuilder;

    /**
     * @param collectionHandler объект collectionHandler
     //* @param queriesHandler объект queriesHandler
     * @param commandHandler объект commandHandler
     //* @param fileHandler объект fileHandler
     */
    public Realization(CollectionHandler<Vehicle> collectionHandler, UserHandler userHandler, CommandHandler commandHandler, DatabaseManager databaseManager) {
        this.collectionHandler = collectionHandler;
        this.userHandler = userHandler;
        //this.queriesHandler = queriesHandler;
        this.commandHandler = commandHandler;
        this.databaseManager = databaseManager;
        //this.fileHandler = fileHandler;
        //this.elementBuilder = new VehicleBuilder(queriesHandler);
    }
    /**
     * Реализация команды clear
     //* @param line строка с аргументами
     //* @param countArgs число аргументов
     */
    public ResponseResult clear(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
        //Validator.validateCountArgs(args, countArgs);
        this.databaseManager.getDatabaseCollectionHandler().clearCollection(request.getUser().getLogin());
        //this.collectionHandler.setNewCollection();
        this.collectionHandler.getCollection().keySet().forEach(x -> {
            if (this.collectionHandler.getCollection().get(x).getCreator().equals(request.getUser().getLogin())) {
                this.collectionHandler.removeElement(x);
            }
        });
        return new ResponseResult(1, null, TypesResponse.RESULT, null);
    }
    /**
     * Реализация команды executeScript
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    /*public void executeScript(String line, int countArgs) throws UnknownCommandException, FileNotFoundException, NoAccessToTheFileException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

//        if (this.queriesHandler.getType() == QueriesHandlerType.CONSOLE) {
//            this.queriesHandler.setFileType(line);
//        } else if (this.queriesHandler.getType() == QueriesHandlerType.FILE) {
//            throw new UnknownCommandException("executeScript нельзя вызвать из файла");
//        }
        this.queriesHandler.setFileType(line);
    }*/
    /**
     * Реализация команды exit
     //* @param line строка с аргументами
     //* @param countArgs число аргументов
     */
    public ResponseResult exit(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
        //Validator.validateCountArgs(args, countArgs);

        return new ResponseResult(3, null, TypesResponse.RESULT, null);
    }
    /**
     * Реализация команды filterGreaterThanFuelType
     //* @param line строка с аргументами
     //* @param countArgs число аргументов
     */
    public ResponseResult filterGreaterThanFuelType(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();

//        String var = "Возможные значения:";
//        for (FuelType i : FuelType.values()) {
//            var += " " + i.name();
//        }
//        String rowFuelType = this.queriesHandler.query("Введите тип топлива. Значение должно быть одной из заранее определенных констант.\n"+var);

//        try {
//            Validator.validateEnum(rowFuelType, FuelType.class);
//        } catch (UnknownCommandException e) {
//            //System.out.println(e.getLocalizedMessage());
//            throw new TroubleObjectCreationException(e.getLocalizedMessage());
//            //filterGreaterThanFuelType(line, countArgs);
//            //return;
//        }

        // FuelType fuelType = FuelType.valueOf(rowFuelType.toUpperCase());
        FuelType fuelType = request.getFuelType();

        String[] names = new String[]{"Id", "Name", "Coordinates", "Creation date", "Engine power", "Type", "Fuel type", "Creator"};

        Integer[] widths = createWidth(names);

        //ArrayList<String> response = new ArrayList<String>();
        Comparator<FuelType> comparator = Enum::compareTo;
        //for (Vehicle i : this.collectionHandler.getCollection().values()) {
        for (Vehicle i : this.collectionHandler.getCollection().values().stream().filter(x -> comparator.compare(x.getFuelType(), fuelType) > 0).toList()) {
            //if (comparator.compare(i.getFuelType(), fuelType) > 0) {
            String[] el = i.getAll();
            for (int z = 0; z <= 7; z++) {
                if (el[z].length() + 10 > widths[z]) {
                    widths[z] = el[z].length() + 10;
                }
            }
            //}
        }
        String s = "";
        for (int i = 0; i <= 7; i++) {
            s += names[i] + func(widths[i] - names[i].length());
        }

        String reply = "";
        reply += "Выведены элементы, у которых FuelType больше заданного\n" + s + "\n ";
//        this.queriesHandler.write("Выведены элементы, у которых FuelType больше заданного");
//        this.queriesHandler.write(s);
//        this.queriesHandler.write("");

        for (Vehicle i : this.collectionHandler.getCollection().values().stream().filter(x -> comparator.compare(x.getFuelType(), fuelType) > 0).toList()) {
            //if (comparator.compare(i.getFuelType(), fuelType) > 0) {
            s = "";
            String[] el = i.getAll();
            for (int z = 0; z <= 7; z++) {
                s += el[z] + func(widths[z] - el[z].length());
            }
            reply += "\n" + s;
            //this.queriesHandler.write(s);
            //}
        }

        //reply += "\nРезультат: " + String.join(", ", response);
        return new ResponseResult(1, null, TypesResponse.RESULT, reply);
        //this.queriesHandler.write("Результат: " + String.join(", ", response));
        //System.out.println("Результат: " + String.join(", ", response));
    }
    /**
     * Реализация команды groupCountingById
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult groupCountingById(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
        //Validator.validateCountArgs(args, countArgs);

        Map<Integer, ArrayList<String>> map = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, new ArrayList<String>());
        }
        this.collectionHandler.getCollection().values().forEach(x -> map.get(Integer.parseInt(String.valueOf(x.getId().toString().charAt(0)))).add(x.getName()));
        /*for (Vehicle i : this.collectionHandler.getCollection().values()) {
            String fir = String.valueOf(i.getId().toString().charAt(0));
            map.get(Integer.parseInt(fir)).add(i.getName());
        }*/
        String reply = "";
        reply += "Группировка производится по первой цифре ID";
        //this.queriesHandler.write("Группировка производится по первой цифре ID");
        for (int i : map.keySet()) {
            if (map.get(i).size() != 0) {
                reply += "\n" + i+": "+map.get(i).size();
                //this.queriesHandler.write(i+": "+map.get(i).size());
                //System.out.println(i+": "+String.join(", ", map.get(i)));
            }
        }
        return new ResponseResult(1, null, TypesResponse.RESULT, reply);
    }
    /**
     * Реализация команды help
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult help(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
        //Validator.validateCountArgs(args, countArgs);

        String reply = "";
        for (Command i : this.commandHandler.getCommands().values()) {
            String name = i.getName();
            String description = i.getDescription();
            String arg = i.getArgs();
            reply += name + " " + arg + ": " + description + "\n";
            //this.queriesHandler.write(name + ": " + description);
            //System.out.println(name + ": " + description);
        }
        return new ResponseResult(1, null, TypesResponse.RESULT, reply);
    }
    /**
     * Реализация команды info
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult info(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
        //Validator.validateCountArgs(args, countArgs);

        String reply = "";
        reply += "Тип: " + this.collectionHandler.getCollection().getClass().getSimpleName();
        reply += "\nДата инициализации: " + this.collectionHandler.getCreateDate();
        reply += "\nЧисло элементов: " + this.collectionHandler.getCollection().size();
//        this.queriesHandler.write("Тип: " + this.collectionHandler.getCollection().getClass().getSimpleName());
//        this.queriesHandler.write("Дата инициализации: " + this.collectionHandler.getCreateDate());
//        this.queriesHandler.write("Число элементов: " + this.collectionHandler.getCollection().size());
//        this.queriesHandler.write("Путь до файла базы данных: " + this.collectionHandler.getPathToFile());

//        System.out.println("Тип: " + this.collectionHandler.getCollection().getClass().getSimpleName());
//        System.out.println("Дата инициализации: " + this.collectionHandler.getCreateDate());
//        System.out.println("Число элементов: " + this.collectionHandler.getCollection().size());
//        System.out.println("Путь до файла базы данных: " + this.collectionHandler.getPathToFile());
        return new ResponseResult(1, null, TypesResponse.RESULT, reply);
    }
    /**
     * Реализация команды insert
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult insert(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
        //Validator.validateCountArgs(args, countArgs);
        //Validator.validateKey(args[0]);

        int key = Integer.parseInt(args[0]);
        if (this.collectionHandler.getCollection().get(key) != null) {
            //throw new UnknownCommandException("Элемент с таким ключем уже существует");
            return new ResponseResult(0, "Элемент с таким ключем уже существует", TypesResponse.RESULT, null);
            //System.out.println("Элемент с таким ключем уже существует");
        } else {
            //Vehicle element = this.elementBuilder.build();
            Vehicle element = request.getObject();
            this.databaseManager.getDatabaseCollectionHandler().insertCollectionByKey(key, element);
            element.setId(this.databaseManager.getDatabaseCollectionHandler().getIdFromCollection(key));
            this.collectionHandler.addElement(key, element);
        }
        return new ResponseResult(1, null, TypesResponse.RESULT, null);
    }
    /**
     * Реализация команды printDescending
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult printDescending(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
        //Validator.validateCountArgs(args, countArgs);

        String response = "";

        String[] names = new String[]{"Id", "Name", "Coordinates", "Creation date", "Engine power", "Type", "Fuel type", "Creator"};

        Integer[] widths = createWidth(names);

        for (String[] i : this.collectionHandler.getCollection().values().stream().sorted(Collections.reverseOrder()).map(x -> x.getAll()).toList()) {
            //String[] el = i.getAll();
            for (int z = 0; z <= 7; z++) {
                if (i[z].length() + 10 > widths[z]) {
                    widths[z] = i[z].length() + 10;
                }
            }
        }
        String s = "";
        for (int i = 0; i <= 7; i++) {
            s += names[i] + func(widths[i] - names[i].length());
        }

        String reply = "";
        reply += "Элементы выведены сверху вниз в порядке убывания Engine power\n" + s + "\n ";
//        this.queriesHandler.write("Элементы выведены сверху вниз в порядке убывания Engine power");
//        this.queriesHandler.write(s);
//        this.queriesHandler.write("");

        for (String[] i : this.collectionHandler.getCollection().values().stream().sorted(Collections.reverseOrder()).map(x -> x.getAll()).toList()) {
            s = "";
            //String[] el = i.getAll();
            for (int z = 0; z <= 7; z++) {
                s += i[z] + func(widths[z] - i[z].length());
            }
            reply += "\n" + s;
            //this.queriesHandler.write(s);
        }

        //Stream<String> arr = this.collectionHandler.getCollection().values().stream().sorted(Collections.reverseOrder()).map(Vehicle::getName);
        //response = String.join(" > ", arr.toList());
        //this.queriesHandler.writeCommand(response);
//        System.out.println(response);
        return new ResponseResult(1, null, TypesResponse.RESULT, reply);
    }
    /**
     * Реализация команды removeGreater
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult removeGreater(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
        //Validator.validateCountArgs(args, countArgs);

        //Vehicle element = this.elementBuilder.build();

        Vehicle element = request.getObject();

        this.databaseManager.getDatabaseCollectionHandler().removeCollectionGreaterThan(element, request.getUser().getLogin());

        this.collectionHandler.getCollection().keySet().stream().parallel().forEach(x -> {
            Vehicle el = this.collectionHandler.getCollection().get(x);
            if (el.compareTo(element) > 0 & el.getCreator().equals(request.getUser().getLogin())) {
                this.collectionHandler.removeElement(x);
            }
        });

        /*ArrayList<Integer> keys = new ArrayList<Integer>();
        for (int i : this.collectionHandler.getCollection().keySet()) {
            if (this.collectionHandler.getCollection().get(i).compareTo(element) > 0) {
                keys.add(i);
            }
        }
        for (int i : keys) {
            this.collectionHandler.removeElement(i);
        }*/
        return new ResponseResult(1, null, TypesResponse.RESULT, null);
    }
    /**
     * Реализация команды removeKey
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult removeKey(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
//        Validator.validateCountArgs(args, countArgs);
//        Validator.validateKey(args[0]);

        int key = Integer.parseInt(args[0]);
        if (this.collectionHandler.getCollection().containsKey(key)) {
            this.databaseManager.getDatabaseCollectionHandler().removeCollectionByKey(key, request.getUser().getLogin());
            if (this.collectionHandler.getCollection().get(key).getCreator().equals(request.getUser().getLogin())) {
                this.collectionHandler.removeElement(key);
            } else {
                return new ResponseResult(0, "Элемента под таким ключем создан не вами.", TypesResponse.RESULT, null);
            }
        } else {
            // throw new UnknownCommandException("Элемента под таким ключем нет.");
            return new ResponseResult(0, "Элемента под таким ключем нет.", TypesResponse.RESULT, null);
            //System.out.println("Элемента под таким ключем нет.");
        }
        return new ResponseResult(1, null, TypesResponse.RESULT, null);
    }
    /**
     * Реализация команды removeLowerKey
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult removeLowerKey(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
//        Validator.validateCountArgs(args, countArgs);
//        Validator.validateKey(args[0]);

        int key = Integer.parseInt(args[0]);
        //ArrayList<Integer> keys = new ArrayList<Integer>();
        this.databaseManager.getDatabaseCollectionHandler().removeCollectionByLowerKey(key, request.getUser().getLogin());
        this.collectionHandler.getCollection().keySet().removeIf(x -> x < key & this.collectionHandler.getCollection().get(x).getCreator().equals(request.getUser().getLogin()));
        /*for (int i : this.collectionHandler.getCollection().keySet().removeIf(x -> x < key)) {
            if (i < key) {
                keys.add(i);
            }
        }
        for (int i : keys) {
            this.collectionHandler.removeElement(i);
        }*/
        return new ResponseResult(1, null, TypesResponse.RESULT, null);
    }
    /**
     * Реализация команды replaceIfLower
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult replaceIfLower(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
//        Validator.validateCountArgs(args, countArgs);
//        Validator.validateKey(args[0]);

        int key = Integer.parseInt(args[0]);

        if (this.collectionHandler.getCollection().containsKey(key)) {
            //Vehicle element = this.elementBuilder.build();
            Vehicle element = request.getObject();
            if (this.collectionHandler.getCollection().get(key).getCreator().equals(request.getUser().getLogin())) {
                if (this.collectionHandler.getCollection().get(key).compareTo(element) > 0) {
                    this.databaseManager.getDatabaseCollectionHandler().replaceCollectionByKey(key, element, request.getUser().getLogin());
                        element.setId(this.databaseManager.getDatabaseCollectionHandler().getIdFromCollection(key));
                        this.collectionHandler.replaceElement(key, element);
                }
            } else {
                return new ResponseResult(0, "Элемента под указанным ключом создан не вами", TypesResponse.RESULT, null);
            }
        } else {
            // throw new UnknownCommandException("Элемента под указанным ключом нет.");
            return new ResponseResult(0, "Элемента под указанным ключом нет.", TypesResponse.RESULT, null);
            //System.out.println("Элемента под указанным ключом нет.");
        }
        return new ResponseResult(1, null, TypesResponse.RESULT, null);
    }
    /**
     * Реализация команды save
     * @param line строка с аргументами
     * @param countArgs число аргументов
     *//*
    public void save(String line, int countArgs) throws UnknownCommandException, NoAccessToTheFileException, FileNotFoundException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        this.fileHandler.serial();
    }*/
    /**
     * Реализация команды show
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult show(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
        //Validator.validateCountArgs(args, countArgs);

        String[] names = new String[]{"Key", "Id", "Name", "Coordinates", "Creation date", "Engine power", "Type", "Fuel type", "Creator"};

        Integer[] widths = createWidth(names);

        for (Integer z : this.collectionHandler.getCollection().keySet()) {
            if (z.toString().length() > widths[0]) {
                widths[0] = z.toString().length() + 10;
            }
            String[] el = this.collectionHandler.getCollection().get(z).getAll();
            for (int i = 0; i <= 7; i++) {
                if (el[i].length() + 10 > widths[i]) {
                    widths[i + 1] = el[i].length() + 10;
                }
            }
        }
        String s = "";
        for (int i = 0; i <= 8; i++) {
            s += names[i] + func(widths[i] - names[i].length());
        }

        String reply = "";
        reply += s + "\n ";
//        this.queriesHandler.write(s);
//        this.queriesHandler.write("");
        //System.out.println(s);
        //System.out.println("");
        for (Integer z : this.collectionHandler.getCollection().keySet()) {
            s = "";
            s += z + func(widths[0]-z.toString().length());
            String[] el = this.collectionHandler.getCollection().get(z).getAll();
            for (int i = 0; i <= 7; i++) {
                s += el[i] + func(widths[i+1] - el[i].length());
            }
            reply += "\n" + s;
            // this.queriesHandler.write(s);
            //System.out.println(s);
        }
        return new ResponseResult(1, null, TypesResponse.RESULT, reply);
    }

    public Integer[] createWidth(String[] names) {
        Integer[] widths = new Integer[]{0,0,0,0,0,0,0,0,0};

        for (int i = 0; i < names.length; i++) {
            if (names[i].length() + 10 > widths[i]) {
                widths[i] = names[i].length() + 10;
            }
        }

        return widths;
    }

    private String func(int a) {
        String s = "";
        for (int i = 0; i < a; i++) {
            s += " ";
        }
        //System.out.println("1"+s+"1");
        return s;
    }
    /**
     * Реализация команды update
//     * @param line строка с аргументами
//     * @param countArgs число аргументов
     */
    public ResponseResult update(BaseRequest baseRequest) {
        CommandRequest request = (CommandRequest) baseRequest;
        String[] args = request.getArgs();
//        Validator.validateCountArgs(args, countArgs);
//        Validator.validateId(args[0]);

        int id = Integer.parseInt(args[0]);
        Vehicle element = request.getObject();
        if (this.collectionHandler.getCollection().keySet().stream().filter(x -> this.collectionHandler.getCollection().get(x).getId() == id).toList().size() != 0) {
            element.setId(id);
            this.databaseManager.getDatabaseCollectionHandler().replaceCollectionById(id, element, request.getUser().getLogin());

            if (this.collectionHandler.getCollection().keySet().stream().filter(x -> this.collectionHandler.getCollection().get(x).getId() == id & this.collectionHandler.getCollection().get(x).getCreator().equals(request.getUser().getLogin())).toList().size() == 0) {
                return new ResponseResult(0, "Элемент с таким id создан не вами", TypesResponse.RESULT, null);
            }
            this.collectionHandler.getCollection().keySet().forEach(x -> {
                Vehicle el = this.collectionHandler.getCollection().get(x);
                if (el.getId() == id & el.getCreator().equals(request.getUser().getLogin())) {
                    this.collectionHandler.replaceElement(x, element);
                }
            });
            return new ResponseResult(1, null, TypesResponse.RESULT, null);
        }
        /*for (int i : this.collectionHandler.getCollection().keySet()) {
            if (this.collectionHandler.getCollection().get(i).getId() == id) {
                //Vehicle element = this.elementBuilder.build(id);
                //Vehicle element = request.getObject();
                this.collectionHandler.replaceElement(i, element);
                return new Response(1, null, null);
            }
        }*/
        return new ResponseResult(0, "Элемента с таким id нет в коллекции", TypesResponse.RESULT, null);
    }

    public ResponseRegistration register(BaseRequest baseRequest) {
        RegisterRequest request = (RegisterRequest) baseRequest;
        if (!this.userHandler.checkUserLoginExist(request.getUser().getLogin())) {
            this.databaseManager.getDatabaseUserHandler().insertUser(request.getUser());
            this.userHandler.addUser(request.getUser().getLogin());
            return new ResponseRegistration(1, null, "Регистрация успешна", TypesResponse.REQISTER, request.getUser());
        }
        return new ResponseRegistration(0, "Пользователь с таким логином уже существует", null, TypesResponse.REQISTER,null);
    }

    public ResponseRegistration enter(BaseRequest baseRequest) {
        RegisterRequest request = (RegisterRequest) baseRequest;
        if (this.userHandler.checkUserLoginExist(request.getUser().getLogin())) {
            String[] els = this.databaseManager.getDatabaseUserHandler().getPassword(request.getUser().getLogin());
            if (Encryptor.getSHA384Password(request.getUser().getPassword(), els[1]).equals(els[0])) {
                return new ResponseRegistration(1, null, "Вход успешен", TypesResponse.REQISTER, request.getUser());
            } else {
                return new ResponseRegistration(0, "Пароль введен неверно", null, TypesResponse.REQISTER,null);
            }
        }
        return new ResponseRegistration(0, "Пользователя с таким логином не существует", null, TypesResponse.REQISTER,null);
    }
}
