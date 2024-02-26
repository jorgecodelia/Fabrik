package com.fabrik.api.client;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.fabrik.api")
public class FabrikClientArchTest {

    private static final String MAIN_PACKAGE = "com.fabrik.api";
    private static final String CLIENT_PACKAGE = MAIN_PACKAGE + ".client";
    private static final String SERVICE_PACKAGE =  MAIN_PACKAGE + ".service";
    private static final String CONTROLLER_PACKAGE =  MAIN_PACKAGE + ".controller";
    private final JavaClasses jc = new ClassFileImporter().importPackages(MAIN_PACKAGE);


    @Test
    public void givenClientLayer_thenWrongCheckFails() {
        ArchRule r1 = classes()
                .that()
                .resideInAPackage(CLIENT_PACKAGE)
                .should()
                .onlyDependOnClassesThat()
                .resideInAPackage(CONTROLLER_PACKAGE);
        Assertions.assertThrows(AssertionError.class, () -> r1.check(jc));
    }


    @Test
    public void givenClientLayer_thenCheckDependenciesSuccess() {
        ArchRule r1 = classes()
                .that()
                .resideInAPackage(CLIENT_PACKAGE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(MAIN_PACKAGE + "..", "java..", "org.springframework..");
        r1.check(jc);
    }

    @Test
    public void givenClientLayer_thenCheckInterfacesSuccess() {
        ArchRule r1 = classes()
                .that()
                .resideInAnyPackage(CLIENT_PACKAGE)
                .should()
                .dependOnClassesThat()
                .areInterfaces();
        r1.check(jc);
    }

    @Test
    public void givenClientLayer_thenNoControllerLayerAccess() {
        ArchRule r1 = noClasses()
                .that()
                .resideInAPackage(CLIENT_PACKAGE)
                .should()
                .dependOnClassesThat()
                .resideInAPackage(SERVICE_PACKAGE);
        r1.check(jc);
    }
}
