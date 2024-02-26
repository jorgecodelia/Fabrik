package com.fabrik.api.controller;

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
public class FarbrikControllerArchTest {
    private static final String MAIN_PACKAGE = "com.fabrik.api";
    private static final String CLIENT_PACKAGE = MAIN_PACKAGE + ".client";
    private static final String SERVICE_PACKAGE =  MAIN_PACKAGE + ".service";
    private static final String CONTROLLER_PACKAGE =  MAIN_PACKAGE + ".controller";
    private final JavaClasses jc = new ClassFileImporter().importPackages(MAIN_PACKAGE);


    @Test
    public void givenControllerLayer_thenWrongCheckFails() {
        ArchRule r1 = classes()
                .that()
                .resideInAPackage(CONTROLLER_PACKAGE)
                .should().onlyDependOnClassesThat()
                .resideInAPackage(CLIENT_PACKAGE);
        Assertions.assertThrows(AssertionError.class, () -> r1.check(jc));
    }


    @Test
    public void givenControllerLayer_thenCheckDependenciesSuccess() {
        ArchRule r1 = classes()
                .that()
                .resideInAPackage(CONTROLLER_PACKAGE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(MAIN_PACKAGE + "..", "java..", "org.springframework..");
        r1.check(jc);
    }

    @Test
    public void givenControllerLayer_thenNoClientLayerAccess() {
        ArchRule r1 = noClasses()
                .that()
                .resideInAPackage(CONTROLLER_PACKAGE)
                .should().dependOnClassesThat()
                .resideInAPackage(CLIENT_PACKAGE);
        r1.check(jc);
    }

    @Test
    public void givenApplicationClasses_thenNoLayerViolationsShouldExist() {
        Architectures.LayeredArchitecture arch = layeredArchitecture().consideringOnlyDependenciesInLayers()
                .layer("FabrikController").definedBy(CONTROLLER_PACKAGE)
                .layer("FabrikService").definedBy(SERVICE_PACKAGE)
                // Add constraints
                .whereLayer("FabrikService").mayOnlyBeAccessedByLayers("FabrikController");
        arch.check(jc);
    }
}
