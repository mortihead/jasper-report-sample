package ru.mortihead.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты для {@link VersionController}.
 */
@SpringBootTest(
        classes = VersionController.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@Import({JacksonAutoConfiguration.class, WebMvcAutoConfiguration.class})
class VersionControllerTest {

    private static final String VERSION_ENDPOINT = "/api/v1/version";

    /**
     * Сценарий: Проверка успешного получения версии приложения через REST API.
     * <p>
     * Given приложение запущено с версией из конфигурации
     * When клиент отправляет GET запрос на /api/v1/version
     * Then ответ имеет HTTP статус 200 OK
     * And Content-Type заголовок равен application/json
     * And тело ответа содержит JSON объект с ключом "version"
     */
    @Nested
    class WithConfiguredVersion {

        @Autowired
        private MockMvc mockMvc;

        @Test
        void shouldReturnVersionFromConfiguration() throws Exception {
            mockMvc.perform(get(VERSION_ENDPOINT))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.version").exists());
        }
    }

    /**
     * Сценарий: Проверка обработки пустого значения версии.
     * <p>
     * Given приложение настроено с пустой строкой в качестве версии
     * When клиент отправляет GET запрос на /api/v1/version
     * Then ответ имеет HTTP статус 200 OK
     * And тело ответа содержит поле "version" с пустой строкой
     */
    @Nested
    @TestPropertySource(properties = "application.version=")
    class WithEmptyVersion {

        @Autowired
        private MockMvc mockMvc;

        @Test
        void shouldReturnEmptyVersionWhenConfiguredWithEmptyString() throws Exception {
            mockMvc.perform(get(VERSION_ENDPOINT))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.version").exists())
                    .andExpect(jsonPath("$.version").value(""));
        }
    }
}
