package messages;

public final class Messages {

    private Messages() {
        // Utility class: запрещён конструктор
    }

    // Регистрация
    public static final String REGISTRATION_SUCCESS = "Пользователь успешно зарегистрирован";
    public static final String REGISTRATION_FAILED = "Регистрация не выполнена";

    // Авторизация
    public static final String LOGIN_SUCCESS = "Авторизация выполнена";
    public static final String LOGIN_FAILED = "Ошибка авторизации";

    // Записи
    public static final String ENTRY_CREATED = "Запись сохранена";
    public static final String ENTRY_DELETED = "Запись удалена";
    public static final String NO_ENTRIES = "Записей не найдено";

    // Поиск
    public static final String SEARCH_FOUND = "Результаты найдены";
    public static final String SEARCH_NOT_FOUND = "Нет совпадений";

    // Настройки
    public static final String LANGUAGE_CHANGED = "Язык интерфейса изменён";

    // Календарь
    public static final String ENTRIES_FOR_DATE = "Записи за выбранную дату отображены";

    // Системные
    public static final String ERROR = "Произошла ошибка";
    public static final String ACTION_SUCCESS = "Операция выполнена успешно";
    public static final String ACTION_FAILED = "Операция не выполнена";

}
