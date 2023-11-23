package local.example.catalogservice.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import local.example.catalogservice.config.BaseConfig;
import local.example.catalogservice.domain.Book;

@JsonTest
@Import(BaseConfig.class)
@ContextConfiguration(classes = BaseConfig.class)
class BookJsonTests {
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private JacksonTester<Book> json;

	@Test
    void testSerialize() throws Exception {
        var now = LocalDateTime.now();
        
        var book = new Book(394L, "1234567890", "Title", "Author", 9.90, "Polarsophia", now, now, "jenny", "eline", 21);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
                .isEqualTo(book.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());
        assertThat(jsonContent).extractingJsonPathStringValue("@.publisher")
                .isEqualTo(book.publisher());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate")
                .isEqualTo(book.createdDate().format(DateTimeFormatter.ISO_DATE_TIME));
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate")
                .isEqualTo(book.lastModifiedDate().format(DateTimeFormatter.ISO_DATE_TIME));
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version")
                .isEqualTo(book.version());
    }

    @Test
    void testDeserialize() throws Exception {
        var instant = LocalDateTime.parse("2021-09-07T22:50:37.135029");
        var content = """
                {
                    "id": 394,
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.90,
                    "publisher": "Polarsophia",
                    "createdDate": "2021-09-07T22:50:37.135029Z",
                    "lastModifiedDate": "2021-09-07T22:50:37.135029Z",
                    "version": 21
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book(394L, "1234567890", "Title", "Author", 9.90, "Polarsophia", LocalDateTime.now(), LocalDateTime.now(), "jenny", "eline", 21));
    }

}
