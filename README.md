<h1 align="center">Java Web Development, EPAM<br>
Final Task. Web Project:<br>
Dance studio/Студия танца</h1>

# Содержание
+ [Предметная область (кратко)](#предметная_область)
+	[Описание функциональности](#функциональность)
+	[Применяемые технологии](#технологии)
+	[Реализованные требования](#требования)
+	[Схема БД](#схема)

<a name="предметная_область"></a>
# Предметная область
Веб-приложение для клиентов, педагогов и администраторов танцевальной студии. Позволяет просматривать всю необходимую информацию о занятиях, группах и педагогах, подбирать занятия по определенным критериям, записываться на занятия, покупать абонементы, отмечать присутствие на занятиях, просматривать статистику посещений, а также информацию по приобретенным абонементам и др.
<a name="функциональность"></a>
# Описание функциональности
Без регистрации всем пользователям-гостям доступен просмотр расписания занятий, информации о педагогах, а также видах и стоимости абонементов. 
Для доступа в личный кабинет клиент должен зарегистрироваться в приложении и затем авторизоваться. Регистрацию педагогов осуществляет администратор.<br><br>
В зависимости от роли (КЛИЕНТ, ПЕДАГОГ или АДМИНИСТРАТОР) после входа в личный кабинет приложение предоставляет разный функционал:<br><br>
**КЛИЕНТ** может подобрать себе группу для занятий (по танцевальному направлению, уровню подготовки или по расписанию), приобрести абонемент и записаться на занятие. В личном кабинете ему также доступна информация о планируемых занятиях, на которые он записался, с возможностью отмены записи, информация о своей посещаемости за выбранный период, о действующих абонементах и абонементах, приобретенных за выбранный период. Клиент также может редактировать информацию о себе и менять пароль.<br><br>
**ПЕДАГОГ** с помощью приложения отмечает присутствие клиентов, записавшихся на занятие с указанием «присутствовал»/«не присутствовал». При проставлении отметки у клиента автоматически уменьшается количество оставшихся занятий по абонементу. Педагог также может просматривать список клиентов, посетивших занятия педагога за выбранную дату с возможностью отмены проставленной отметки о посещении (в случае ошибки, например), после чего запись вновь появляется в списке неотмеченных занятий. Педагог также может просматривать статистку (количество) посещений занятий за выбранный период в разрезе свои групп. Доступна также опция смены пароля для входа в личный кабинет.<br><br>
**АДМИНИСТРАТОРУ** доступен весь список клиентов, педагогов и групп с возможностью добавления новых, а также редактирования и удаления имеющихся. С помощью приложения администратор открывает запись на занятие в группе(ах) на выбранную дату. Далее он может просматривать количество уже записавшихся на занятие клиентов и в случае необходимости может досрочно закрыть запись на занятие. Администратор может просматривать статистику по проданным абонементам: количестве и сумме всех абонементах, проданных за выбранный период. Также администратор может просмотреть статистику посещений в разрезе групп за выбранный период для отслеживания спроса на занятия в определенных группах.
<a name="технологии"></a>
# Применяемые технологии
MySQL, Servlet и JSP (в т.ч. теги библиотеки JSTL), Bootstrap4, CSS, JS, HTML5, Maven, TestNg
<a name="требования"></a>
# Реализованные требования
+	Aрхитектура приложения соответствует шаблонам Layered architecture и MVC 
+	Для работы с БД используется JDBC с потокобезопасным пулом соединений
+	 В БД заданы индексы для ускорения поиска, создана хранимая процедура для таблицы `membership`. Реализован механизм транзакций для операций по проставлению отметки о присутсвии клиента на занятии и уменьшению  количества оставшихся занятий в абонементе этого клиента
+	В проекте применены шаблоны: Factory Method, Command, Builder,  Singleton, а также реализован шаблон веб проектирования PRG (Post/Redirect/Get), который предотвращает повторное выполнение запроса нажатием F5
+	Интерфейс приложения локализован, имеется выбор из языков: EN|RU|BY.
+	Для хранения пользовательской информации между запросами используется сессия
+	Для перехвата и корректировки объектов запроса (request) и ответа (response) применяются фильтры
+	Реализованы собственные теги
+	Просмотр длинного списка клиентов реализован в постраничном режиме (clients.jsp)
<a name="схема"></a>
# Схема БД 
<p align="center"><img  src="./readme_img/1.png"></p>

