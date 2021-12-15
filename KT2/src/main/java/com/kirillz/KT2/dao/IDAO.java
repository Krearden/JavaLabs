package com.kirillz.KT2.dao;

import java.util.List;
import java.util.UUID;

public interface IDAO<T> {
    T get(UUID id);

    List<T> getAll();

    void save(T t);

    void update(T t);//, String[] params);

    void delete(T t);
}
