package br.com.history;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("br.com.history");

        noClasses()
            .that()
            .resideInAnyPackage("br.com.history.service..")
            .or()
            .resideInAnyPackage("br.com.history.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..br.com.history.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
