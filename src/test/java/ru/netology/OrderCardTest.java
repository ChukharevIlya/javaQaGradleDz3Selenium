package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCardTest {

    //1. сначала создаем переменную с вебдрайвером
    private WebDriver driver;


    //2. пропишем в setProperty путь до нашего хромдрайвера. с @BeforeAll, чтобы запускался перед выполнением всех тестов.
    // В этом случае не используется WebDriverManager, поэтому драйвер в папке driver должен быть
//    @BeforeAll
//    static void setUpAll() {
//        WebDriverManager.chromedriver().setup();
//        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
//    }

    //2. тот же шаг, но при условии использования библиотеки WebDriverManager.
    // Значит в папке driver драйвер не нужен и ручное подключение удаляем(System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");)
    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    //3. создадим  метод c @Beforeach, чтобы перед каждым тестом создавать новый объект с типом WebDriver
    @BeforeEach
    void setUp() {
        // ниже Включение headless режима при использовании selenium
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        // 5.1 создадим метод, который будет открывать браузер перед каждым тестом. тут работает. Почему?
        driver.get("http://localhost:9999/");

        // Для selenide headless режим активируется при запуске тестов с определенным параметром:
        // gradlew test -Dselenide.headless=true;

        //4. проинициализируем с помощью конструктора переменную driver. Это если не использовать headless режим
//        driver = new ChromeDriver();
    }

    //5.2 еще один метод, в котором будем закрывать браузер и обнулять наш драйвер по сле каждого теста
    // запустили браузер на странице localhost:9999
    @AfterEach
    void teardown() {
        driver.quit(); // закрыли браузер
        driver=null; // обнулили драйвер
    }

    // ЗАДАНИЕ №1
    @Test
    void happyPathTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // ЗАДАНИЕ №2
    // поле фамилия и имя: латиница
    @Test
    void shouldNotBeEnteredLatinLettersInFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id='name'] .input__control")).sendKeys("Ilya Chukharev");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: цифры
    @Test
    void shouldNotBeEnteredNumbersInFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id='name'] .input__control")).sendKeys("111 2222");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: кирилица с дефисом в середине
    @Test
    void shouldPassHyphenInTheMiddleOfTheFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id='name'] .input__control")).sendKeys("Илья Чухарев-Иванов");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    // поле фамилия и имя: кирилица с дефисом в начале
    @Test
    void shouldNotPassHyphenBeforeTheFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id='name'] .input__control")).sendKeys("-Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: кирилица с дефисом в конце
    @Test
    void shouldNotPassHyphenAtTheEndOfTheFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев-");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: кирилица+апостроф
    @Test
    void shouldPassApostropheInTheFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья О'Генри");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    // поле фамилия и имя: кирилица+спецсимвол например "!"
    @Test
    void shouldNotPassSpecialCharacterInTheFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев!");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: кирилица+ё
    @Test
    void shouldPassLetterYoInTheFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарёв");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    // поле фамилия и имя: кирилица+мягкий знак вначале
    @Test
    void shouldNotPassSoftSignIfBeforeTheFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("ьИлья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: кирилица+твердый знак вначале
    @Test
    void shouldNotPassSolidMarkIfBeforeTheFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("ъИлья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: только имя
    @Test
    void shouldNotPassNameOnlyInTheFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: только фамилия
    @Test
    void shouldNotPassSurnameOnlyInTheFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: лишний пробел перед фамилией
    @Test
    void shouldNotPass2SpacesBetweenNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья  Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: лишний пробел перед именем
    @Test
    void shouldNotPassSpaceBeforeNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys(" Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: имя из одной буквы, фамилия Чухарев
    @Test
    void shouldNotPassIfNameContainsOnly1Letter() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("И Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: имя из двух букв, фамилия Чухарев
    @Test
    void shouldPassIfNameContains2Letter() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Ян Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    // поле фамилия и имя: фамилия из одной буквы, имя Илья
    @Test
    void shouldNotPassIfSurnameContainsOnly1Letter() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Ч");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    // поле фамилия и имя: фамилия из двух букв, имя Илья
    @Test
    void shouldPassIfSurnameContains2Letter() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Хо");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    // поле фамилия и имя: поле пустое
    @Test
    void shouldNotPassEmptyFieldNameAndSurname() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    // поле телефон: латиница
    @Test
    void shouldNotBeEnteredLatinLettersInFieldPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("telephone");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    // поле телефон: кирилица
    @Test
    void shouldNotBeEnteredCyrillicInFieldPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("телефон");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    // поле телефон: специмвол, кроме "+"
    @Test
    void shouldNotPassSpecialCharacterInFieldPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7911555257$");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    // поле телефон: номер без "+"
    @Test
    void shouldNotPassPhoneNumberWithoutPlusAtTheBeginning() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("79115552575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    // поле телефон: + в конце
    @Test
    void shouldNotPassPhoneNumberWithPlusAtTheEnd() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("79115552575+");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    // поле телефон: + в середине
    @Test
    void shouldNotPassPhoneNumberWithPlusInTheMiddle() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("791155+52575");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    // поле телефон: пустое поле
    @Test
    void shouldNotPassEmptyFieldPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    // поле телефон: только +
    @Test
    void shouldNotPassOnlyPlusInTheFieldPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    // поле телефон: + и одна цифра
    @Test
    void shouldNotPass1NumberWithPlusInTheFieldPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    // поле телефон: + и две цифры
    @Test
    void shouldNotPass2NumbersWithPlusInTheFieldPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    // поле телефон: + и 10 цифр
    @Test
    void shouldNotPass10NumbersWithPlusInTheFieldPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7911555257");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    // поле телефон: + и 12 цифр
    @Test
    void shouldNotPass12NumbersWithPlusInTheFieldPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+791155525757");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldNotPassWithoutCheckBoxClick() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Илья Чухарев");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"agreement\"].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }
}
