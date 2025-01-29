package net.orekhov.usersimulator.controller;

import net.orekhov.usersimulator.service.UserSimulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для управления симуляцией действий пользователя.
 * Этот контроллер предоставляет HTTP-эндпоинт для запуска симуляции взаимодействий пользователя с системой.
 */
@RestController
public class UserSimulationController {
    private static final Logger logger = LoggerFactory.getLogger(UserSimulationController.class);
    private final UserSimulationService simulationService;

    /**
     * Конструктор контроллера.
     *
     * @param simulationService Сервис для выполнения симуляции действий пользователя.
     */
    public UserSimulationController(UserSimulationService simulationService) {
        this.simulationService = simulationService;
    }

    /**
     * Запускает симуляцию действий пользователя.
     *
     * @param count Количество раз, которое будет выполнена симуляция (по умолчанию 1).
     * @return Строка с результатом выполнения симуляции.
     */
    @GetMapping("/simulate")
    public String simulateUserAction(@RequestParam(defaultValue = "1") int count) {
        logger.info("Запуск симуляции действий пользователя ({} раз)", count);

        try {
            for (int i = 0; i < count; i++) {
                simulationService.simulateUserFlow();
            }
            logger.info("Симуляция завершена успешно.");
            return "Симуляция запущена.";
        } catch (Exception e) {
            logger.error("Ошибка во время симуляции: {}", e.getMessage());
            return "Ошибка при запуске симуляции.";
        }
    }
}
