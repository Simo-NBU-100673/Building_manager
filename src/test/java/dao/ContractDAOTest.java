package dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContractDAOTest {

    @BeforeAll
    public static void init() {
        System.setProperty("test.env", "true");
    }

    @AfterAll
    public static void close() {
//        System.setProperty("test.env", "false");
    }

    @Test
    void getContractsByCompany() {
        fail("Not yet implemented");
    }

    @Test
    void getBuildingsByCompany() {
        fail("Not yet implemented");
    }

    @Test
    void getCountOfContractsOfCompany() {
        fail("Not yet implemented");
    }

    @Test
    void ensureNotNull() {
        fail("Not yet implemented");
    }
}