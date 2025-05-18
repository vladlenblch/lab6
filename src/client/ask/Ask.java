package client.ask;

import common.console.Console;
import common.data.*;
import server.managers.CollectionManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

/**
 * Класс, предоставляющий методы для запроса данных у пользователя.
 * Используется для создания объектов {@link City}, {@link Coordinates}, {@link Climate},
 * {@link Government}, {@link StandardOfLiving} и {@link Human} через консольный ввод.
 */
public class Ask {
    /**
     * Исключение, которое выбрасывается при вводе команды "exit" во время запроса данных.
     */
    public static class AskBreak extends Exception {}

    /**
     * Запрашивает у пользователя данные для создания объекта {@link City}.
     *
     * @param console консоль для ввода и вывода данных
     * @param collectionManager менеджер коллекции, используемый для получения свободного ID
     * @return объект {@link City}, созданный на основе введённых данных
     * @throws AskBreak если пользователь ввёл команду "exit"
     */
    public static City askCity(Console console, CollectionManager collectionManager) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (name.isEmpty()) {
                    console.printError("Вы не передали значение");
                }
                else break;
            }

            var coordinates = askCoordinates(console);

            int area;
            while (true) {
                console.print("area: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    area = Integer.parseInt(line);
                    if (area > 0) break;
                    else console.printError("Значение должно быть больше 0");
                } catch (IllegalArgumentException e) {
                    console.printError("Значение должно быть целым числом");
                }
            }

            Integer population;
            while (true) {
                console.print("population: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    population = Integer.parseInt(line);
                    if (population > 0) break;
                    else console.printError("Значение должно быть больше 0");
                } catch (IllegalArgumentException e) {
                    console.printError("Значение должно быть целым числом");
                }
            }

            int metersAboveSeaLevel;
            while (true) {
                console.print("metersAboveSeaLevel: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    metersAboveSeaLevel = Integer.parseInt(line);
                    break;
                } catch (IllegalArgumentException e) {
                    console.printError("Значение должно быть целым числом");
                }
            }

            var climate = askClimate(console);

            var government = askGovernment(console);

            var standardOfLiving = askStandardOfLiving(console);

            var governor = askHuman(console);

            return new City(collectionManager.getFreeId(), name, coordinates, area, population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Запрашивает у пользователя данные для создания объекта {@link Coordinates}.
     *
     * @param console консоль для ввода и вывода данных
     * @return объект {@link Coordinates}, созданный на основе введённых данных
     * @throws AskBreak если пользователь ввёл команду "exit"
     */
    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            long x;
            while (true) {
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    x = Long.parseLong(line);
                    break;
                } catch (IllegalArgumentException e) {
                    console.printError("Значение должно быть целым числом");
                }
            }

            Double y;
            while (true) {
                console.print("coordinates.y: ");
                var line = console.readln().trim().replace(",", ".");
                if (line.equals("exit")) throw new AskBreak();
                try {
                    y = Double.parseDouble(line);
                    if (y > -339) break;
                    else console.printError("Значение должно быть больше -339");
                } catch (IllegalArgumentException e) {
                    console.printError("Значение должно быть числом (используйте точку или запятую)");
                }
            }

            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Запрашивает у пользователя значение для перечисления {@link Climate}.
     *
     * @param console консоль для ввода и вывода данных
     * @return значение перечисления {@link Climate} или null, если canBeNull == true
     * @throws AskBreak если пользователь ввёл команду "exit"
     */
    public static Climate askClimate(Console console) throws AskBreak {
        try {
            Climate c;
            while (true) {
                console.print("Climate (" + Climate.names() + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        c = Climate.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException e) {
                        console.printError("Значение отсутствует в списке");
                    }
                }
            }

            return c;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Запрашивает у пользователя значение для перечисления {@link Government}.
     *
     * @param console консоль для ввода и вывода данных
     * @return значение перечисления {@link Government}
     * @throws AskBreak если пользователь ввёл команду "exit"
     */
    public static Government askGovernment(Console console) throws AskBreak {
        try {
            Government g;
            while (true) {
                console.print("Government (" + Government.names() + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        g = Government.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException e) {
                        console.printError("Значение отсутствует в списке");
                    }
                }
            }

            return g;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Запрашивает у пользователя значение для перечисления {@link StandardOfLiving}.
     *
     * @param console консоль для ввода и вывода данных
     * @return значение перечисления {@link StandardOfLiving}
     * @throws AskBreak если пользователь ввёл команду "exit"
     */
    public static StandardOfLiving askStandardOfLiving(Console console) throws AskBreak {
        try {
            StandardOfLiving s;
            while (true) {
                console.print("StandardOfLiving (" + StandardOfLiving.names() + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        s = StandardOfLiving.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException e) {
                        console.printError("Значение отсутствует в списке");
                    }
                }
            }

            return s;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Запрашивает у пользователя данные для создания объекта {@link Human}.
     *
     * @param console консоль для ввода и вывода данных
     * @return объект {@link Human}, созданный на основе введённых данных
     * @throws AskBreak если пользователь ввёл команду "exit"
     */
    public static Human askHuman(Console console) throws AskBreak {
        try {
            long height;
            while (true) {
                console.print("governor.height: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) {
                    console.printError("Поле не может быть пустым!");
                    continue;
                }
                try {
                    height = Long.parseLong(line);
                    if (height > 0) break;
                    else console.printError("Значение должно быть больше 0");
                } catch (IllegalArgumentException e) {
                    console.printError("Значение должно быть целым числом");
                }
            }

            LocalDateTime birthday;
            while (true) {
                console.print("governor.birthday (Пример: " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) {
                    console.printError("Поле 'birthday' обязательно!");
                    continue;
                }
                try {
                    birthday = LocalDateTime.parse(line, DateTimeFormatter.ISO_DATE_TIME);
                    break;
                } catch (DateTimeParseException e) {
                    console.printError("Ошибка формата. Пример: " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                }
            }
            return new Human(height, birthday);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}