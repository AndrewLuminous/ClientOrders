# „ClientOrders“
ClientOrders - консольная система управления клиентской базой с использованием Hibernate ORM и PostgreSQL. Включает функционал работы с клиентами, заказами, профилями и системой купонов. Особое внимание уделено оптимизации запросов и решению проблемы N+1.
-
Этот проект явлется выполнением порученого мне задания в нем используется такой стек технологий:
<ul>
    <li>Java 21</li>
    <li>Maven</li>
    <li>Hibernate</li>
    <li>PostgreSQL</li>
    <li>Docker</li>
</ul>

---
## Инфологическая модель базы данных
![infologdatabase](https://github.com/AndrewLuminous/ClientOrders/blob/main/src/images/infologdatabase.png)

## Демонстрация важных элементов моего проекта
### <p style="text-align: center;">N + 1</p>
В моем проекте я избавился от N + 1 и я попробовал три способа:
* собственные jpql запросы с `Join Fetch`
* Порционная загрузка - `BatchSize`
* `EntityGraph` - как современное решение проблемы

Решение этой задачи позволило понять глубже `hibernate` его типы загрузки `eagle`, `lazy` много других механизмов, даже удалось встретить декартово произведение.

![dekart](https://github.com/AndrewLuminous/ClientOrders/blob/main/src/images/dekart.png)

На этом скриншоте явно продемонстрировано проблема декартового произведения которое не дает в свою очередь решить N + 1 мы ее решили.

Визуализация проблемы N+1

![ninactive](https://github.com/AndrewLuminous/ClientOrders/blob/main/src/images/n+1inactive.png)

N+1 проблема проявляется когда для загрузки N сущностей выполняется N+1 запросов к базе данных. Вместо эффективного JOIN, Hibernate делает отдельные SELECT'ы
### <p style="text-align: center;">Демонстрация работы приложения ClientsOrder</p>
В данном отрезке я посчитал удобным вставить gif(кликабельно)

![peek2](https://github.com/AndrewLuminous/ClientOrders/blob/main/src/images/peek2.gif)

![peek1](https://github.com/AndrewLuminous/ClientOrders/blob/main/src/images/peek1.gif)

На этих коротких анимаций я быстро показываю основной интересный функционал моей программы с таблицами от `PostgreSQL` 
### <p style="text-align: center;">Запросы JPQL</p>

В проекте активно используются `JPQL` (Java Persistence Query Language) запросы для оптимизации и для удобства и на мой взгляд открывает новый уровень контроля над производительностью приложения. В отличие от стандартных методов `Hibernate`

![jpql](https://github.com/AndrewLuminous/ClientOrders/blob/main/src/images/jpql.png)


