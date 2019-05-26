# AnimalsAccountSystem
Description

usage: accountanimals [OPTIONS] <RULE>
 -a,--animals-source <arg>      Source of animals(json, xml, cvs file)
 -p,--properties-source <arg>   Source of properties(json, xml, cvs file)
  
Простая система учета животных. Через аргументы коммандной строки передаются имена файлов со свойствами животных и перечень животных с указанием их свойств. 
Формат файлов - json
Примеры файлов со свойствами:
AnimalsAccountSystem/src/main/resources/good-resources/animals.json
AnimalsAccountSystem/src/main/resources/good-resources/properties.json

Система правил - аналогична where clause из sql, например "Вес='Тяжелый' AND Рост='Высокий'"

Паттернами "Фасад", "Мост", "Фабрика" логика работы с внешними источниками отделена от остальной программы.
Для добавления новых источников данных(xml, psql, etc..) достаточно реализовать интерфейл AnimalsDataProvider или PropertiesDataProvider соответсвенно без изменения кода вызова

В MainTest.java различные сценарии тестирования и пример с предопределенными данными из AnimalsAccountSystem/src/main/resources/good-resources/ (по умолчанию выключен)
