package br.com.history.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.history.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteImage.class);
        SiteImage siteImage1 = new SiteImage();
        siteImage1.setId(1L);
        SiteImage siteImage2 = new SiteImage();
        siteImage2.setId(siteImage1.getId());
        assertThat(siteImage1).isEqualTo(siteImage2);
        siteImage2.setId(2L);
        assertThat(siteImage1).isNotEqualTo(siteImage2);
        siteImage1.setId(null);
        assertThat(siteImage1).isNotEqualTo(siteImage2);
    }
}
