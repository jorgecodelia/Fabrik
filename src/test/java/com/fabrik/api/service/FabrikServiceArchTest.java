package com.fabrik.api.service;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.fabrik.api")
public class FabrikServiceArchTest {

    private static final String MAIN_PACKAGE = "com.fabrik.api";
    private static final String CLIENT_PACKAGE = MAIN_PACKAGE + ".client";
    private static final String SERVICE_PACKAGE =  MAIN_PACKAGE + ".service";
    private static final String CONTROLLER_PACKAGE =  MAIN_PACKAGE + ".controller";
    private final JavaClasses jc = new ClassFileImporter().importPackages(MAIN_PACKAGE);


    @Test
    public void givenServiceLayer_thenWrongCheckFails() {
        ArchRule r1 = classes()
                .that()
                .resideInAPackage(SERVICE_PACKAGE)
                .should().onlyDependOnClassesThat()
                .resideInAPackage(CONTROLLER_PACKAGE);
        Assertions.assertThrows(AssertionError.class, () -> r1.check(jc));
    }


    @Test
    public void givenServiceLayer_thenCheckDependenciesSuccess() {
        ArchRule r1 = classes()
                .that()
                .resideInAPackage(SERVICE_PACKAGE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(MAIN_PACKAGE + "..", "java..", "org.springframework..");
        r1.check(jc);
    }

    @Test
    public void givenServiceLayer_thenNoControllerLayerAccess() {
        ArchRule r1 = noClasses()
                .that()
                .resideInAPackage(SERVICE_PACKAGE)
                .should().dependOnClassesThat()
                .resideInAPackage(CONTROLLER_PACKAGE);
        r1.check(jc);
    }

    @Test
    public void givenApplicationClasses_thenNoLayerViolationsShouldExist() {
        Architectures.LayeredArchitecture arch = layeredArchitecture().consideringOnlyDependenciesInLayers()
                .layer("FabrikService").definedBy(SERVICE_PACKAGE)
                .layer("FabrikClient").definedBy(CLIENT_PACKAGE)
                .whereLayer("FabrikClient").mayOnlyBeAccessedByLayers("FabrikService");
        arch.check(jc);
    }
}
