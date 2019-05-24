package com.kmoxvilleEntertainmentGroup.DataProviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * "Мост" между данными из внешних источников(напрмер файлов) и MainEngine
 * @param <T>
 */
public interface DataProvider<T> {
    T readAllData() throws DataProviderException;
}
