package com.fabrik.api.common.util;

import com.fabrik.api.common.ApiConstants;
import com.fabrik.api.common.exception.NoFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * Search for an Api class name and return it.
     *
     * @param classNameToSearch The class name to search.
     * @return java object.
     */
    public static Object searchApiClass(String classNameToSearch) {
        List<Class<?>> classes = findClassesInDirectory();

        for (Class<?> clazz : classes) {
            if (clazz.getSimpleName().equals(classNameToSearch)) {
                return clazz;
            }
        }
        return null;
    }

    /**
     * Recursively searches a directory for class files and loads their corresponding Class objects.
     *
     * @return A list of Class objects found in the directory.
     */
    private static List<Class<?>> findClassesInDirectory() {
        File directory = new File(ApiConstants.DOMAIN_CLASS_SRC);

        if (directory.exists() && directory.isDirectory()) {
            List<Class<?>> clazzList = new ArrayList<>();
            searchDirectoryForClasses(directory, "", clazzList);
            return clazzList;
        } else {
            String msgEx = "Directory does not exist or is not a directory.";
            LOGGER.error(msgEx);
            throw new NoFoundException(msgEx);
        }
    }

    /**
     * Recursively searches a directory for class files and loads their corresponding Class objects.
     *
     * @param directory   The directory to search.
     * @param packageName The package name of classes found so far.
     * @param classes     List to store Class objects.
     */
    private static void searchDirectoryForClasses(File directory, String packageName, List<Class<?>> classes) {
        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                searchDirectoryForClasses(file, packageName + file.getName() + ".", classes);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + file.getName().replace(".class", "");
                try {
                    Class<?> clazz = Class.forName(className);
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                    String msgEx = "Failed to load class: " + className;
                    LOGGER.error(msgEx);
                    LOGGER.error(e.getMessage());
                    throw new NoFoundException(msgEx, e);
                }
            }
        }
    }

}
