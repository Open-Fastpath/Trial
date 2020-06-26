package jp.core.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InterfaceData {

    private final Map<String, Object> map = new HashMap<>();

    public void set(final String key, final Object value){
        map.put(key, value);
    }

    public String getString(final String key){
        return (String)map.get(key);
    }

    public String[] getStringArray(final String key){
        return (String[])map.get(key);
    }

    public Object get(final String key){
        return map.get(key);
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    public String toString(){

        final StringBuffer values = new StringBuffer();

        values.append("[MAP]");

        for (final Map.Entry<String, Object> entry : map.entrySet()){

            if (entry.getValue() instanceof String){
                values.append(entry.getKey() + ":" + entry.getValue() + "|"); 
            }
            else if (entry.getValue() instanceof String[]){
                final String[] list= (String[])entry.getValue();
                for (int i=0; i<list.length; i++){
                    values.append(entry.getKey() + "(" + i + "):" + list[i] + "|");  
                }      
            }
            else{
                values.append(entry.getKey() + ":" + entry.getValue().getClass().getName() + "|");
            }      
        }

        return values.toString();
    }
}