package br.com.history.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.history.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoricalSiteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoricalSiteDTO.class);
        HistoricalSiteDTO historicalSiteDTO1 = new HistoricalSiteDTO();
        historicalSiteDTO1.setId(1L);
        HistoricalSiteDTO historicalSiteDTO2 = new HistoricalSiteDTO();
        assertThat(historicalSiteDTO1).isNotEqualTo(historicalSiteDTO2);
        historicalSiteDTO2.setId(historicalSiteDTO1.getId());
        assertThat(historicalSiteDTO1).isEqualTo(historicalSiteDTO2);
        historicalSiteDTO2.setId(2L);
        assertThat(historicalSiteDTO1).isNotEqualTo(historicalSiteDTO2);
        historicalSiteDTO1.setId(null);
        assertThat(historicalSiteDTO1).isNotEqualTo(historicalSiteDTO2);
    }
}
