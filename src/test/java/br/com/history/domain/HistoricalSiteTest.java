package br.com.history.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.history.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoricalSiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoricalSite.class);
        HistoricalSite historicalSite1 = new HistoricalSite();
        historicalSite1.setId(1L);
        HistoricalSite historicalSite2 = new HistoricalSite();
        historicalSite2.setId(historicalSite1.getId());
        assertThat(historicalSite1).isEqualTo(historicalSite2);
        historicalSite2.setId(2L);
        assertThat(historicalSite1).isNotEqualTo(historicalSite2);
        historicalSite1.setId(null);
        assertThat(historicalSite1).isNotEqualTo(historicalSite2);
    }
}
