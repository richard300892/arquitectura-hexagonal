package com.alediesme.joyeria.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
    packages = {
        "com.alediesme.joyeria.customer",
        "com.alediesme.joyeria.security",
        "com.alediesme.joyeria.shared"
    },
    importOptions = ImportOption.DoNotIncludeTests.class
)
class DomainArchitectureTest {

  @ArchTest
  static final ArchRule domain_must_not_depend_on_spring =
      noClasses()
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage("org.springframework..");

  @ArchTest
  static final ArchRule domain_must_not_depend_on_jdbc =
      noClasses()
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage("org.springframework.jdbc..", "java.sql..", "javax.sql..");

  @ArchTest
  static final ArchRule domain_must_not_depend_on_servlet =
      noClasses()
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage("javax.servlet..", "jakarta.servlet..");
}
