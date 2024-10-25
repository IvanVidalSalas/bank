package cat.uvic.teknos.bank.services.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Mappers {

    private static final ObjectMapper mapper;

    static  {
        final var genreTypeMapping = new SimpleModule()
                .addAbstractTypeMapping(cat.uvic.teknos.bank.models.Customer.class, cat.uvic.teknos.bank.domain.jdbc.models.Customer.class)
                .addAbstractTypeMapping(cat.uvic.teknos.bank.models.Loan.class, cat.uvic.teknos.bank.domain.jdbc.models.Loan.class)
                .addAbstractTypeMapping(cat.uvic.teknos.bank.models.Account.class, cat.uvic.teknos.bank.domain.jdbc.models.Account.class)
                .addAbstractTypeMapping(cat.uvic.teknos.bank.models.Transaction.class, cat.uvic.teknos.bank.domain.jdbc.models.Transaction.class)
                .addAbstractTypeMapping(cat.uvic.teknos.bank.models.Worker.class, cat.uvic.teknos.bank.domain.jdbc.models.Worker.class);
        mapper = new ObjectMapper();
        mapper
                .registerModule(new JavaTimeModule()) // Registered to map LocalDate (add implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.0" to build.gradle.kts) )
                .registerModule(genreTypeMapping); // Registered to map the Genre deserialization
    }

    public static ObjectMapper get() {
        return mapper;
    }
}

