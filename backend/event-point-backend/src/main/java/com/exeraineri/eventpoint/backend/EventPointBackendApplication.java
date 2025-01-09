package com.exeraineri.eventpoint.backend;

import com.exeraineri.eventpoint.backend.entity.Category;
import com.exeraineri.eventpoint.backend.entity.Event;
import com.exeraineri.eventpoint.backend.entity.Location;
import com.exeraineri.eventpoint.backend.entity.Ticket;
import com.exeraineri.eventpoint.backend.entity.TicketType;
import com.exeraineri.eventpoint.backend.entity.UserEntity;
import com.exeraineri.eventpoint.backend.enumeration.EnumRole;
import com.exeraineri.eventpoint.backend.service.interfaces.ICategoryService;
import com.exeraineri.eventpoint.backend.service.interfaces.IEventService;
import com.exeraineri.eventpoint.backend.service.interfaces.IPaymentService;
import com.exeraineri.eventpoint.backend.service.interfaces.ITicketService;
import com.exeraineri.eventpoint.backend.service.interfaces.IUserService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class EventPointBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventPointBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(IUserService userService,
            ICategoryService categoryService,
            IEventService eventService,
            ITicketService ticketService,
            IPaymentService paymentService) {
        return new CommandLineRunner() {
            @Override
            public void run(String[] args) throws Exception {
                cargaManual();
            }

            private void cargaManual() {
                List<UserEntity> users = userService.findAll();
                if (users.isEmpty()) {
                    UserEntity userAdmin = UserEntity.builder()
                            .email("exe@gmail.com")
                            .firstName("Exequiel")
                            .lastName("Raineri")
                            .isActive(Boolean.TRUE)
                            .password("1234")
                            .location(Location.builder()
                                    .address("Aristobulo del valle")
                                    .latitude(-24.232332f)
                                    .longitude(-24.5231123f)
                                    .postalCode("4300").build()
                            )
                            .role(EnumRole.ADMIN)
                            .resetPasswordToken("1234reset")
                            .build();
                    UserEntity userUser = UserEntity.builder()
                            .email("usuario@gmail.com")
                            .firstName("Usuario")
                            .lastName("Ejemplo")
                            .isActive(Boolean.TRUE)
                            .password("1234")
                            .location(Location.builder()
                                    .address("Bolivia")
                                    .latitude(-22.232332f)
                                    .longitude(-21.5231123f)
                                    .postalCode("4100").build()
                            )
                            .role(EnumRole.USER)
                            .resetPasswordToken("1234reset")
                            .build();

                    userAdmin = userService.save(userAdmin);
                    userUser = userService.save(userUser);

                    Category categoryDeporte = Category.builder()
                            .name("Deporte")
                            .description("Descripcion de deporte")
                            .build();

                    Category categoryConcierto = Category.builder()
                            .name("Concierto")
                            .description("Descripcion de concierto")
                            .build();

                    categoryDeporte = categoryService.save(categoryDeporte);
                    categoryConcierto = categoryService.save(categoryConcierto);

                    TicketType ticketGeneral = TicketType.builder()
                            .name("GENERAL")
                            .price(BigDecimal.valueOf(15000))
                            .stock(100)
                            .build();

                    TicketType ticketVip = TicketType.builder()
                            .name("VIP")
                            .price(BigDecimal.valueOf(25000))
                            .stock(20)
                            .build();

                    Event event = Event.builder()
                            .title("Campeonato deportivo")
                            .description("Campeonato de futbol en complejo")
                            .capacity(100)
                            .organizer(userAdmin)
                            .location(Location.builder()
                                    .address("Autopista")
                                    .latitude(-23.424213f)
                                    .longitude(-24.2331233f)
                                    .postalCode("4200")
                                    .build())
                            .category(categoryDeporte)
                            .startDate(LocalDateTime.of(2025, Month.JANUARY, 10, 15, 0))
                            .endDate(LocalDateTime.of(2025, Month.JANUARY, 13, 22, 0))
                            .basePrice(BigDecimal.valueOf(15000))
                            .ticketTypes(List.of(ticketGeneral, ticketVip))
                            .build();
                    event = eventService.save(event);

                    Ticket ticket = Ticket.builder()
                            .user(userUser)
                            .event(event)
                            .ticketType(event.getTicketTypes().get(0))
                            .build();
                    
                    
                    ticket = ticketService.save(ticket);

                }
            }
        };
    }

}
