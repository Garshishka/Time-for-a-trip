# Time-for-a-trip
Мобильное приложение, которое показывает список авиабилетов. Тестовое задание от компании Wildberries.

Состоит из двух экранов (фрагментов):

## Список всех билетов
![time-for-a-trip1](https://github.com/Garshishka/Time-for-a-trip/assets/30876362/5d5a0ace-8208-4cbe-9804-2e0957fd7692)

Содержит список всех билетов со значком лайка. Клик на один из билетов переводит на следующий экран:

## Экран отдельного билета
![time-for-a-trip2](https://github.com/Garshishka/Time-for-a-trip/assets/30876362/ecc84ec1-676f-490b-a51c-b9ae3f32565f)

Содержит детализацию перелета с тремя кнопками:
- Кнопка возвращения назад
- Кнопка "лайка" - при нажатии перелет отмечается понравившимся и это остается при возвращении назад в список
- Кнопка перевода времени с глобального в локальное время аэропорта отправления

## Техническая сторона
[ApiService](https://github.com/Garshishka/Time-for-a-trip/blob/master/app/src/main/java/ru/garshishka/timeforatrip/api/ApiService.kt) Для вызова по api данных о билетах используется retrofit. По заданию требуется лишь 1 вызов при запуске, поэтому оформлено без DI
[ViewModel](https://github.com/Garshishka/Time-for-a-trip/blob/master/app/src/main/java/ru/garshishka/timeforatrip/TripViewModel.kt) Содержит данные о билетах, отвечает за лайки. В задании не указано, что лайки должны оставаться после выключения приложения, поэтому их состояние никак не сохраняется
[Основной фрагмент](https://github.com/Garshishka/Time-for-a-trip/blob/master/app/src/main/java/ru/garshishka/timeforatrip/fragments/RoutesListFragment.kt). Содержит [RecyclerAdapter](https://github.com/Garshishka/Time-for-a-trip/blob/master/app/src/main/java/ru/garshishka/timeforatrip/viewholder/FlightViewHolder.kt) для списка билетов.
[Фрагмент одного полета](https://github.com/Garshishka/Time-for-a-trip/blob/master/app/src/main/java/ru/garshishka/timeforatrip/fragments/OneFlightFragment.kt).
