package com.andresouza.dscatalog.utils;

import com.andresouza.dscatalog.entities.Product;
import com.andresouza.dscatalog.projection.IdProjection;
import com.andresouza.dscatalog.projection.ProductProjection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    public static <ID> List<? extends IdProjection<ID>>
    replace(List<? extends IdProjection<ID>> ordered, List<? extends IdProjection<ID>> unordered){

        Map <ID, IdProjection<ID>> map = new HashMap<>();

        for (IdProjection<ID> obl: unordered) {
            map.put(obl.getId(), obl);
        }
        List<IdProjection<ID>> result = new ArrayList<>();
        for (IdProjection<ID> obj : ordered){
            result.add(map.get(obj.getId()));
        }

        return result;
    }
}
