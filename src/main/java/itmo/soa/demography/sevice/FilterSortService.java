package itmo.soa.demography.sevice;

import itmo.soa.demography.model.Country;
import itmo.soa.demography.util.Filter;
import itmo.soa.demography.util.Sign;
import itmo.soa.demography.util.Sort;
import jakarta.ejb.Stateless;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class FilterSortService {

    private static final Pattern pattern = Pattern.compile("(.*)\\[(.*)]=(.*)");
    private static final Map<String, Class<?>> filterDict = new HashMap<>() {{
        put("id", Long.class);
        put("name", String.class);
        //put("coordinates.x", int.class);
        put("coordinates.y", Integer.class);
        put("creationDate", Date.class);
        put("height", Long.class);
        put("birthday", ZonedDateTime.class);
        put("weight", Double.class);
        put("nationality", Country.class);
        put("location.x", Integer.class);
        put("location.y", Integer.class);
        put("location.name", String.class);
        put("hairColor", String.class);
    }};



    public Filter parseFilter(String filterStr) {
        Matcher matcher = pattern.matcher(filterStr);
        try {
            matcher.find();
            String fieldName = matcher.group(1);
            Sign sign = Sign.valueOf(matcher.group(2).toUpperCase());
            String valStr = matcher.group(3);
            return new Filter(fieldName, valStr, sign);
        }
        catch (Exception e){
            return null;
        }
    }

    public Sort parseSort(String sortStr) {
        String[] str = sortStr.split(",");
        Sort sort = new Sort();
        if(str.length > 2) {
            throw new IllegalArgumentException("Слишком много параметров сортировки");
        }
        String fieldName = str[0];
        if(filterDict.get(fieldName) == null) {
            throw new IllegalArgumentException("Поле сортировки отсутствует");
        }
        sort.setPathToField(Arrays.stream(fieldName.split("\\."))
                .collect(Collectors.toList()));
        if(str.length == 2 && str[1].equals("desc")) {
            sort.setAsc(false);
            return sort;
        }
        sort.setAsc(true);
        return sort;
    }
}

