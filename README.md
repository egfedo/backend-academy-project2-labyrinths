
# Требования

- Программа была протестирована на Unix-подобных ОС (MacOS и Ubuntu). При запуске на Windows возможно неправильное поведение из-за отсутствия некоторых возможностей JLine;

- Размер терминала может быть любой, интерфейс адаптируется под него при запуске. Дальнейшее изменение размера во время работы не предусмотрено. Рекомендуемые размеры: 80x24;

- Нужно сменить раскладку клавиатуры на английскую;

- Крайне не советуется запускать программу в консоли внутри IDE (не будет работать из-за отсутствия функций JLine).
# Работа с программой

## Начальное меню

При запуске программы вас встретит меню выбора алгоритма генерации:

![Начальное меню](https://sun9-39.userapi.com/impg/0F6omxKSqVwZdlYrqdnvgrzCHS9oBrUSkq5d9g/4XYzeXniTUs.jpg?size=1684x1156&quality=96&sign=49bb3b141ba736cecba2e70ceb4bc429&type=album)

После выбора алгоритма нужно выбрать размер лабиринта:

![Выбор размеров лабиринта](https://sun9-6.userapi.com/impg/6bhEKgY_jG5xR6IAQ572x0iJPW0DP_Gq4mROag/QZSirXCjLkQ.jpg?size=1684x1156&quality=96&sign=dcfeafa4161b664d05348ebbbb3494ef&type=album)


## Первый просмотр лабиринта

После первого меню откроется просмотр лабиринта:

![Просмотр лабиринта](https://sun9-57.userapi.com/impg/k4F3eCqQzrjs4TfDCGI0VSlVidkyJ9o9UkqGqg/7u4v_ivYsfY.jpg?size=1684x1156&quality=96&sign=cdae8432075437c8e27f10e38f9ad343&type=album)

В режиме просмотра лабиринта будет отображаться весь лабиринт или его часть. Для перемещения используются клавиши WASD:

![Перемещение по лабиринту](https://sun9-77.userapi.com/impg/jGmlAmpvYtfta1JUXvaBNB-ivj8i0q6SlArgrA/TLZwkhoAT_4.jpg?size=1684x1156&quality=96&sign=71b1ca1299000e6d830ac9a747414437&type=album)

Из просмотра можно выйти, использовав клавишу ESC.

## Выбор алгоритма

После первого просмотра лабиринта откроется меню выбора алгоритма поиска пути:

![Меню выбора](https://sun9-24.userapi.com/impg/cXzryyWgh_kzb3_Nsc9rTAcA00rd1o8e51NJIg/JMzkVCey8RI.jpg?size=1684x1156&quality=96&sign=fd2ea066f7416452425fbdafe5ba1f21&type=album)

После выбора всех опций просмотр лабиринта откроется во второй раз.

## Просмотр лабиринта с путём

Управление не отличается от первого просмотра. При этом показывается путь:

![Начало пути](https://sun9-23.userapi.com/impg/L9QTPJ21KzuE4vmZngx7A2zWxQhSgr4AwQA0WA/C9UxIgtWsxs.jpg?size=1684x1156&quality=96&sign=c5b043d72ccc55ee3f0f8b7423a82242&type=album)

Начало пути отображается красным кружком, сам путь - цветом, меняющимся в зависимости от покрытия. Конец пути отображается зелёным:

![Конец пути](https://sun9-41.userapi.com/impg/Ayet04a_iQeFFtVFbiOyjXOWH3fXT2OSqTwMOA/1qD9n-Gbrcs.jpg?size=1684x1156&quality=96&sign=d4b49514ee6145172b7bba77b86a6087&type=album)

