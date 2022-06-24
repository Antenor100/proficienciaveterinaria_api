package br.com.devantenor.clinivet.util;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class EntityUtils {
    public static void editEntityClassByMap(Object targetObj, LinkedHashMap<String, Object> map, Class classe) throws Exception {
        for (String key : map.keySet()) {
            if (map.get(key) != null && map.get(key).toString().isEmpty()) throw new IllegalArgumentException("O valor da chave '" + key + "' não pode ser vazio(\"\")!");

            Method setter = findMethodByName("set" + key.substring(0, 1).toUpperCase() + key.substring(1), classe);

            if (setter != null) {
                setter.invoke(targetObj, map.get(key));
            }
        }
    }

    public static Method findMethodByName(String name, Class classe) {
        for(Method method : classe.getMethods()) {
            if (name.equals(method.getName())) {
                return method;
            }
        }
        return null;
    }
}
