package com.fabrik.api.common.util;

import com.fabrik.api.common.exception.ServiceException;
import com.google.gson.internal.LinkedTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FabrikUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FabrikUtil.class);


    /**
     * Maps fields from a list of generic objects to the fields of a target object and returns a list of mapped target objects.
     *
     * @param <T>         The type of the target object.
     * @param objectList  The list of generic objects whose fields are to be mapped.
     * @param targetClass The class of the target object to which fields will be mapped.
     * @return A list containing target objects with fields mapped from the input list of generic objects.
     * @throws IllegalAccessException If access to the fields of the objects is denied.
     */
    public static <T> List<T> mapToObjectList(List<Object> objectList, Class<T> targetClass) throws IllegalAccessException {
        List<T> resultList = new ArrayList<>();
        for (Object obj : objectList) {
            Field[] fields = targetClass.getDeclaredFields();
            T targetObject;
            try {
                targetObject = targetClass.getDeclaredConstructor().newInstance();
                for (Field field : fields) {
                    field.setAccessible(true); //NOPMD
                    Field sourceField;
                    sourceField = targetClass.getDeclaredField(field.getName());
                    sourceField.setAccessible(true); //NOPMD
                    sourceField.set(targetObject, ((LinkedTreeMap<?, ?>) obj).get(field.getName()));
                }
                resultList.add(targetObject);
            } catch (Exception e) {
                String msgEx = "mapToObjectList: " + targetClass.getSimpleName();
                LOGGER.error(msgEx);
                LOGGER.error(e.getMessage());
                throw new ServiceException(msgEx,e);
            }
        }
        return resultList;
    }


    /**
     * Maps fields from a source object to a target object and returns the mapped target object.
     *
     * @param <T>         The type of the target object.
     * @param object      The source object whose fields are to be mapped.
     * @param targetClass The class of the target object to which fields will be mapped.
     * @return The target object with fields mapped from the source object.
     * @throws RuntimeException If an error occurs during the mapping process.
     */
    public static <T> T mapToObject(Object object, Class<T> targetClass) {
        try {
            T targetObject = targetClass.getDeclaredConstructor().newInstance();
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); //NOPMD
                if (object instanceof LinkedTreeMap<?, ?> linkedTreeMap) {
                    Object value = linkedTreeMap.get(field.getName());
                    if (value != null) {
                        field.set(targetObject, value);
                    }
                }
            }
            return targetObject;
        } catch (Exception e) {
            String msgEx = "Error mapping object to class: " + targetClass.getSimpleName();
            LOGGER.error(msgEx);
            LOGGER.error(e.getMessage());
            throw new ServiceException(msgEx, e);
        }

    }

    /**
     * Extracts the error substring from the given message.
     *
     * @param message The input message from which to extract the error substring.
     * @return The error substring extracted from the message.
     * @throws ServiceException If the error substring is not found in the message.
     */
    public static String extractErrorFromResponse(String message) {

        Pattern pattern = Pattern.compile("(\\{(.*?)}})");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            return matcher.group();
        }
        String msgEx = "extractErrorFromResponse - Error Substring error not found";
        LOGGER.error(msgEx);
        throw new ServiceException(msgEx);
    }


}