USE `dancestudio_db`;

INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"tpavlova",
	"N/xGlJZrZ9VM6azV9T1OFA==",
	'TEACHER'
);

INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"divanov",
	"jgPhc9OAmZgFxy5EHGvUPA==",
	'TEACHER'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"olesnaya",
	"SIZtBADJbA4NQm7g7ZZi6A==",
	'TEACHER'
);

INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"atalai",
	"Wo40uw2qC7rGYuyZCmDlww==",
	'TEACHER'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"MARKOVA",
	"iPj6NrCja2GZEJTZo4M/XA==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"nataliana",
	"vNSrTgDubqB7ibbYiDeIOg==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"vikkra",
	"pgSwtmJ3xGXAAr/Vwaw+ZA==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"anastaissakis",
	"21WLfIPgQ6l/dozEbphQLg==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"kirstepnov",
	"rbL+B1sF3TKkl1cXbDx0Zg==",
	'CLIENT'
);

INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"andreev",
	"3EH3MuLk3lIMClgR9HmaiQ==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"meshkov",
	"9NKXeQb9UmYVSC6odyGUFA==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"POTAPAVA",
	"VYIf8j2LGCbh6DXT3klVLQ==",
	'CLIENT'
);

INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"penkina",
	"TIQx8kCh07119BvCRCfTtQ==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"kuvshinka",
	"vg5r3gG3LvyJePgh47nAxQ==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"koblova",
	"2ZgGr9auLZD9v5Qm0tRvhg==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"Umishukova",
	"G5Eqok4l7KxKuBaq4kjiJA==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"efimenko",
	"rtyCl+v7NWm50wVonQ/7dw==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"fedorovE",
	"jXmo9oofX1ffjcavrpr6YQ==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"popova",
	"drLAHLMsZKtfqL0kLydkQw==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"shut1000",
	"le67Yy/OOkRHs1jNNo+IqA==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"kobec",
	"F2xKWmZ+tiznnZJuT7jKEA==",
	'CLIENT'
);
INSERT INTO `user` (
	`login`,
	`password`,
	`role`
) VALUES (
	"sobstkristi",
	"TcNyeTXMhSpKFWIGkm8Nkg==",
	'CLIENT'
);

INSERT INTO `teacher` (
    `id`,
    `surname`,
	`name`,
	`dancestyle`,
	`portfolio`
) VALUES (
    "2",
	"Павлова",
	"Татьяна",
	"contemporary",
	"Танцевальный опыт - 6 лет. Опыт преподавания - 3 года. Участница международных и республиканских соревнований. Многократная чемпионка в составе команды на Белорусских Чемпионатах и Конкурсах."
	);
INSERT INTO `teacher` (
    `id`,
    `surname`,
	`name`,
	`dancestyle`,
	`portfolio`
	) VALUES (
    "3",
	"Иванов",
	"Дмитрий",
	"hip-hop",
	"В танцах уже 18 лет. Опыт преподавания - 6 лет. Постановщик и участник конкурсных программ, ТВ-Шоу, музыкальных клипов, рекламных видеороликов и подтанцовок"
	);
INSERT INTO `teacher` (
    `id`,
    `surname`,
	`name`,
	`dancestyle`,
	`portfolio`
) VALUES (
    "4",
	"Лесная",
	"Ольга",
	"jazz-funk",
	"Окончила БГУКИ с отличием, эстрадное отделение. Опыт преподавания более 10 лет. Призер множества международных и республиканских соревнований."
	);
	INSERT INTO `teacher` (
    `id`,
    `surname`,
	`name`,
	`dancestyle`,
	`portfolio`
) VALUES (
    "5",
	"Талай",
	"Александра",
	"vogue",
	"Танцевальный опыт - более 15 лет! Стажировка в театре в Берлине. Участие в международных конкурсах и мастер-классах"
	);

INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "6",
	"Маркова",
	"Мария",
	"Алексеевна",
	"markovamarialex@gmail.com",
	"+375(29)768-45-36"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "7",
	"Андреева",
	"Наталья",
	"Николаевна",
	"nnandreeva85@mail.ru",
	"+375(44)741-58-96"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "8",
	"Кратова",
	"Виктория",
	"Петровна",
	"kratkovavikki@gmail.com",
	"+375(44)789-41-78"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "9",
	"Киселева",
	"Анастасия",
	"Алексеевна",
	"akiseleva89@mail.ru",
	"+375(29)698-25-63"
	);

INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "10",
	"Степнов",
	"Кирилл",
	"Александрович",
	"stepnovka@mail.ru",
	"+375(29)259-47-15"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "11",
	"Андреев",
	"Илья",
	"Александрович",
	"andreev@mail.ru",
	"+375(44)758-98-78"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "12",
	"Мешков",
	"Дмитрий",
	"Иванович",
	"meshkov@gmail.com",
	"+375(29)687-79-12"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "13",
	"Потапова",
	"Маргарита",
	"Марковна",
	"POTAPAVA@mail.ru",
	"+375(29)189-84-80"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "14",
	"Пенкина",
	"Анна",
	"Николаевна",
	"penkina@mail.ru",
	"+375(44)758-98-81"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "15",
	"Кувшинова",
	"Екатерина",
	"Михайловна",
	"kuvshinka@mail.ru",
	"+375(25)658-98-82"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "16",
	"Коблова",
	"Любовь",
	"Дмитриевна",
	"koblova@mail.ru",
	"+375(44)858-02-83"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "17",
	"Мишукова",
	"Ульяна",
	"Алексеевна",
	"Umishukova@gmail.com",
	"+375(44)245-89-36"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "18",
	"Ефименко",
	"Маргарита",
	"Сергеевна",
	"fimenko@mail.ru",
	"+375(44)714-98-85"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "19",
	"Федоров",
	"Евгений",
	"Денисович",
	"fedorovE@mail.ru",
	"+375(29)198-78-58"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "20",
	"Попова",
	"Александра",
	"Сергеевна",
	"popova@gmail.com",
	"+375(25)713-38-87"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "21",
	"Шутов",
	"Сергей",
	"Федорович",
	"shut1000@mail.ru",
	"+375(29)700-915-88"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "22",
	"Кобец",
	"Станислав",
	"Александрович",
	"kobec@mail.ru",
	"+375(44)7145-98-48"
	);
INSERT INTO `client` (
    `id`,
    `surname`,
	`name`,
	`patronymic`,
	`email`,
	`phone`
) VALUES (
    "23",
	"Собственникова",
	"Кристина",
	"Станиславовна",
	"sobstkristi@mail.ru",
	"+375(44)145-78-25"
	);