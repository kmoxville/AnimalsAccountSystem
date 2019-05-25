package com.kmoxvilleEntertainmentGroup.DataProviders;

/**
 * Иточники данных(DataProvider) в canHandle определяют могут ли они работать
 * с данными, переданными в строке format(по расширению файла например)
 */
public interface DataFormatHandler {
    Object canHandle(String format, DataProviderType type);
}
