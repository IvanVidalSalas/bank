package cat.uvic.teknos.bank.clients.console.utils;

import cat.uvic.teknos.bank.clients.console.dto.*;
import cat.uvic.teknos.bank.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Mappers {
    private static final ObjectMapper mapper;

    static  {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()) // Registered to map LocalDate (add implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.0" to build.gradle.kts) )
                .registerModule(
                        new SimpleModule()
                            .addAbstractTypeMapping(Customer.class, CustomerDto.class) // Registered to map the Customer deserialization
                )
                .registerModule(
                        new SimpleModule()
                                .addAbstractTypeMapping(Account.class, AccountDto.class)
                )
                .registerModule(
                        new SimpleModule()
                                .addAbstractTypeMapping(Loan.class, LoanDto.class)
                )
                .registerModule(
                        new SimpleModule()
                                .addAbstractTypeMapping(Transaction.class, TransactionDto.class)
                )
                .registerModule(
                        new SimpleModule()
                                .addAbstractTypeMapping(Worker.class, WorkerDto.class)
                );
    }

    public static ObjectMapper get() {
        return mapper;
    }
}
