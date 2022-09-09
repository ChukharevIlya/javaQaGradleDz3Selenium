//package ru.netology;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import io.github.bonigarcia.wdm.WebDriverManager;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class OrderCardTest {
//
//    //1. сначала создаем переменную с вебдрайвером
//    private WebDriver driver;
//
//
//    //2. пропишем в setProperty путь до нашего хромдрайвера. с @BeforeAll чтобы запускался перед выполнением всех тестов
//    @BeforeAll
//    static void setUpAll() {
//        WebDriverManager.chromedriver().setup();
//        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
//    }
//
//    //3. создадим  метод c @Beforeach, чтобы перед каждым тестом создавать новый объект с типом WebDriver
//    @BeforeEach
//    void setUp() {
//        // ниже Включение headless режима при использовании selenium
////        ChromeOptions options = new ChromeOptions();
////        options.addArguments("--disable-dev-shm-usage");
////        options.addArguments("--no-sandbox");
////        options.addArguments("--headless");
////        driver = new ChromeDriver(options);
//        //4. проинициализируем с помощью конструктора переменную driver
//        driver = new ChromeDriver();
//    }
//
//    //5. еще один метод, в котором будем закрывать браузер и обнулять наш драйвер по сле каждого теста
//    @AfterEach
//    void tearsDown() {
//        driver.quit(); // закрыли браузер
//        driver=null; // обнулили драйвер
//    }
//
//    // ЗАДАНИЕ №1
//    @Test
//    void happyPathTest() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//
//        String text = driver.findElement(By.className("paragraph")).getText();
//        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
//    }
//
//    // ЗАДАНИЕ №2
//    // поле фамилия и имя: латиница
//    @Test
//    void test2() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Ilya Chukharev");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: цифры
//    @Test
//    void test3() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("111 2222");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: кирилица с дефисом в середине
//    @Test
//    void test4() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев-Иванов");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//
//        String text = driver.findElement(By.className("paragraph")).getText();
//        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
//    }
//    // поле фамилия и имя: кирилица с дефисом в начале
//    @Test
//    void test5() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("-Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: кирилица с дефисом в конце
//    @Test
//    void test6() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев-");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: кирилица+апостроф
//    @Test
//    void test7() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья О'Генри");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//
//        String text = driver.findElement(By.className("paragraph")).getText();
//        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
//    }
//    // поле фамилия и имя: кирилица+спецсимвол например "!"
//    @Test
//    void test8() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев!");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: кирилица+ё
//    @Test
//    void test9() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарёв");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//
//        String text = driver.findElement(By.className("paragraph")).getText();
//        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
//    }
//    // поле фамилия и имя: кирилица+мягкий знак вначале
//    @Test
//    void test10() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("ьИлья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: кирилица+твердый знак вначале
//    @Test
//    void test11() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("ъИлья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: только имя
//    @Test
//    void test12() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: только фамилия
//    @Test
//    void test13() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: лишний пробел перед фамилией
//    @Test
//    void test14() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья  Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: лишний пробел перед именем
//    @Test
//    void test15() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys(" Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: имя из одной буквы, фамилия Чухарев
//    @Test
//    void test16() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("И Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: имя из двух букв, фамилия Чухарев
//    @Test
//    void test17() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Ян Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//
//        String text = driver.findElement(By.className("paragraph")).getText();
//        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
//    }
//    // поле фамилия и имя: фамилия из одной буквы, имя Илья
//    @Test
//    void test18() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Ч");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//    }
//    // поле фамилия и имя: фамилия из двух букв, имя Илья
//    @Test
//    void test19() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Хо");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//
//        String text = driver.findElement(By.className("paragraph")).getText();
//        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
//    }
//    // поле фамилия и имя: поле пустое
//    @Test
//    void test20() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(0).getText();
//        assertEquals("Поле обязательно для заполнения", text.trim());
//    }
//
//    // поле телефон: латиница
//    @Test
//    void test21() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("telephone");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//    // поле телефон: кирилица
//    @Test
//    void test22() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("телефон");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//    // поле телефон: специмвол, кроме "+"
//    @Test
//    void test23() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7911555257$");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//    // поле телефон: номер без "+"
//    @Test
//    void test24() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("79115552575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//    // поле телефон: + в конце
//    @Test
//    void test25() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("79115552575+");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//    // поле телефон: + в середине
//    @Test
//    void test26() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("791155+52575");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//    // поле телефон: пустое поле
//    @Test
//    void test27() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Поле обязательно для заполнения", text.trim());
//    }
//    // поле телефон: только +
//    @Test
//    void test28() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//    // поле телефон: + и одна цифра
//    @Test
//    void test29() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//    // поле телефон: + и две цифры
//    @Test
//    void test30() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//    // поле телефон: + и 10 цифр
//    @Test
//    void test31() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7911555257");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//    // поле телефон: + и 12 цифр
//    @Test
//    void test32() {
//        driver.get("http://localhost:9999/"); // запустили браузер на странице localhost:9999
//        driver.findElement(By.cssSelector("input")).sendKeys("Илья Чухарев");
//        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+791155525757");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button__text")).click();
//        List<WebElement> elements = driver.findElements(By.className("input__sub"));
//        String text = elements.get(1).getText();
//        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
//    }
//
//}
