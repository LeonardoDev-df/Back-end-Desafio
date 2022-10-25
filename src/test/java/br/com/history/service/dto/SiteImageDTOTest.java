package br.com.history.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.history.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteImageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteImageDTO.class);
        SiteImageDTO siteImageDTO1 = new SiteImageDTO();
        siteImageDTO1.setId(1L);
        SiteImageDTO siteImageDTO2 = new SiteImageDTO();
        assertThat(siteImageDTO1).isNotEqualTo(siteImageDTO2);
        siteImageDTO2.setId(siteImageDTO1.getId());
        assertThat(siteImageDTO1).isEqualTo(siteImageDTO2);
        siteImageDTO2.setId(2L);
        assertThat(siteImageDTO1).isNotEqualTo(siteImageDTO2);
        siteImageDTO1.setId(null);
        assertThat(siteImageDTO1).isNotEqualTo(siteImageDTO2);
    }
}
