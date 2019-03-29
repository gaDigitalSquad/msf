package ar.com.academy.mfs.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class EntityUtils {
	
	public static boolean checkDuplicateUsingAdd(List<Integer> input) {
        Set tempSet = new HashSet();
        for (Object str : input) {
            if (!tempSet.add(str)) {
                return true;
            }
        }
        return false;
    }


}
