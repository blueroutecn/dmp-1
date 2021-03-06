package com.b5m.utils;

import org.apache.pig.data.Tuple;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaBean for a single DMP record (for tests only).
 *
 * @author Paolo D'Apice
 */
public final class Record {

    private String uuid;
    private String date;
    private Integer period;
    private Map<String, Integer> categories;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Map<String, Integer> getCategories() {
        return Collections.unmodifiableMap(categories);
    }

    public void setCategories(Map<String, Integer> categories) {
        this.categories = new HashMap<String, Integer>(categories);
    }

    public static Record fromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Record.class);
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Record)) return false;
        Record r = (Record) o;

        return uuid.equals(r.uuid)
            && date.equals(r.date)
            && period.equals(r.period)
            && categories.equals(r.categories);
    }

    @SuppressWarnings("unchecked")
    public static Record fromPig(String line) throws Exception {
        Tuple t = Tuples.fromString(line, "(chararray, chararray, int, [int])");
        Record r = new Record();
        r.setUuid((String) t.get(0));
        r.setDate((String) t.get(1));
        r.setPeriod((Integer) t.get(2));
        r.setCategories((Map<String, Integer>) t.get(3));

        return r;
    }
}

