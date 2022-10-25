package br.com.history.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class HistoricalSiteMapperTest {

    private HistoricalSiteMapper historicalSiteMapper;

    @BeforeEach
    public void setUp() {
        historicalSiteMapper = new HistoricalSiteMapperImpl();
    }
}
